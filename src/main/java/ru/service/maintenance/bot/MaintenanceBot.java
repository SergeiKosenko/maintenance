package ru.service.maintenance.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.service.maintenance.dtos.RegionesDto;
import ru.service.maintenance.dtos.WorkSiteDto;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class MaintenanceBot extends TelegramLongPollingBot {

    private final String botToken;
    private final String botUsername;
    private final String apiBaseUrl;
    private final RestTemplate restTemplate;
    private final Map<Long, UserSession> userSessions = new HashMap<>();

    public MaintenanceBot(String botToken, String botUsername, String apiBaseUrl) {
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.apiBaseUrl = apiBaseUrl;
        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleMessage(update.getMessage().getChatId(), update.getMessage().getText());
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(
                    update.getCallbackQuery().getMessage().getChatId(),
                    update.getCallbackQuery().getData()
            );
        }
    }

    private void handleMessage(Long chatId, String messageText) {
        if (messageText.equals("/start")) {
            sendWelcomeMessage(chatId);
        } else if (messageText.contains(":")) {
            handleAuthentication(chatId, messageText);
        } else {
            sendMessage(chatId, "Неизвестная команда. Введите /start для начала работы.");
        }
    }

    private void handleCallbackQuery(Long chatId, String callbackData) {
        UserSession session = userSessions.get(chatId);
        if (session == null || session.getToken() == null) {
            sendMessage(chatId, "Сессия истекла. Введите /start для начала работы.");
            return;
        }

        if ("get_regions".equals(callbackData)) {
            getUserRegion(chatId, session);
        } else if (callbackData.startsWith("action_")) {
            String[] parts = callbackData.split("_");
            String action = parts[1];
            Long siteId = Long.parseLong(parts[2]);
            handleWorkSiteAction(chatId, action, siteId, session);
        }
    }

    private void sendWelcomeMessage(Long chatId) {
        sendMessage(chatId,
                "Добро пожаловать в Service+ Bot!\n\n" +
                        "Для авторизации введите ваш логин и пароль в формате:\n" +
                        "логин:пароль\n\n" +
                        "Пример: login:password");
    }

    private void handleAuthentication(Long chatId, String credentials) {
        try {
            String[] parts = credentials.split(":");
            if (parts.length != 2) {
                sendMessage(chatId, "Неверный формат. Введите логин и пароль в формате: логин:пароль");
                return;
            }

            String username = parts[0].trim();
            String password = parts[1].trim();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> authRequest = Map.of(
                    "username", username,
                    "password", password
            );

            HttpEntity<Map<String, String>> request = new HttpEntity<>(authRequest, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    apiBaseUrl + "/auth",
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> authResponse = response.getBody();
                String token = (String) authResponse.get("token");
                Long regionId = authResponse.get("regionId") != null ?
                        Long.parseLong(authResponse.get("regionId").toString()) : null;

                UserSession session = new UserSession();
                session.setToken(token);
                session.setUsername(username);
                session.setRegionId(regionId);
                userSessions.put(chatId, session);

                sendMessage(chatId, "Авторизация успешна!");
                showMainMenu(chatId);
            }
        } catch (Exception e) {
            sendMessage(chatId, "Ошибка авторизации: " + e.getMessage());
            log.error("Authentication error", e);
        }
    }

    private void showMainMenu(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Главное меню:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = List.of(
                List.of(InlineKeyboardButton.builder()
                        .text("Мой регион")
                        .callbackData("get_regions")
                        .build())
        );

        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error showing main menu", e);
        }
    }

    private void getUserRegion(Long chatId, UserSession session) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + session.getToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<RegionesDto> response = restTemplate.exchange(
                    apiBaseUrl + "/api/v1/regiones",
                    HttpMethod.GET,
                    entity,
                    RegionesDto.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                displayWorkSites(chatId, response.getBody().getId(), session);
            }
        } catch (Exception e) {
            sendMessage(chatId, "Ошибка при получении региона: " + e.getMessage());
            log.error("Error getting region", e);
        }
    }

    private void displayWorkSites(Long chatId, Long regionId, UserSession session) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + session.getToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<WorkSiteDto>> activeResponse = restTemplate.exchange(
                    apiBaseUrl + "/api/v1/worksites/region/" + regionId,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<WorkSiteDto>>() {}
            );

            ResponseEntity<List<WorkSiteDto>> notDoneResponse = restTemplate.exchange(
                    apiBaseUrl + "/api/v1/worksites/regionnodone/" + regionId,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<WorkSiteDto>>() {}
            );

            if (activeResponse.getStatusCode() == HttpStatus.OK && notDoneResponse.getStatusCode() == HttpStatus.OK) {
                sendMessage(chatId, "📋 Активные объекты:");
                displayWorkSiteList(chatId, activeResponse.getBody(), session);

                sendMessage(chatId, "\n📋 Не выполненные в прошлом месяце:");
                displayWorkSiteList(chatId, notDoneResponse.getBody(), session);
            }
        } catch (Exception e) {
            sendMessage(chatId, "Ошибка при получении объектов: " + e.getMessage());
            log.error("Error displaying work sites", e);
        }
    }



    private void displayWorkSiteList(Long chatId, List<WorkSiteDto> sites, UserSession session) throws TelegramApiException {
        if (sites == null || sites.isEmpty()) {
            sendMessage(chatId, "Нет объектов для отображения");
            return;
        }

        Map<String, List<WorkSiteDto>> byDistrict = sites.stream()
                .collect(Collectors.groupingBy(WorkSiteDto::getDistrictTitle));

        for (Map.Entry<String, List<WorkSiteDto>> entry : byDistrict.entrySet()) {
            StringBuilder districtMessage = new StringBuilder();
            districtMessage.append("\n🏙 <b>").append(entry.getKey()).append("</b>\n");

            for (WorkSiteDto site : entry.getValue()) {
                StringBuilder siteMessage = new StringBuilder();
                siteMessage.append("\n📍 ").append(formatAddress(site)).append("\n");
                siteMessage.append("🛠 ").append(site.getManufactureTitle()).append("\n");
                siteMessage.append("⚙ Статус: ").append(getStatusText(site)).append("\n");

                if (site.getAtWork()) {
                    siteMessage.append("👷 ").append(site.getUserAtWork()).append("\n");
                }

                List<InlineKeyboardButton> buttons = new ArrayList<>();

                if (!site.getDone()) {
                    if (site.getAtWork()) {
                        buttons.add(InlineKeyboardButton.builder()
                                .text("❌ Отменить")
                                .callbackData("action_cancel_" + site.getId())
                                .build());

                        buttons.add(InlineKeyboardButton.builder()
                                .text("✔️ Сделано")
                                .callbackData("action_done_" + site.getId())
                                .build());
                    } else {
                        buttons.add(InlineKeyboardButton.builder()
                                .text("✅ Взять в работу")
                                .callbackData("action_take_" + site.getId())
                                .build());
                    }
                }

                if (site.getNoDone()) {
                    buttons.add(InlineKeyboardButton.builder()
                            .text("✔️ Отметить выполненным")
                            .callbackData("action_markdone_" + site.getId())
                            .build());
                }

                if (!buttons.isEmpty()) {
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    markup.setKeyboard(List.of(buttons));

                    SendMessage message = new SendMessage();
                    message.setChatId(chatId.toString());
                    message.setText(districtMessage.toString() + siteMessage.toString());
                    message.setParseMode("HTML");
                    message.setReplyMarkup(markup);

                    execute(message);
                    districtMessage.setLength(0); // Очищаем сообщение района после первого объекта
                }
            }
        }
    }

    private void handleWorkSiteAction(Long chatId, String action, Long siteId, UserSession session) {
        try {
            Map<String, Object> updateFields = new LinkedHashMap<>();
            String successMessage = prepareUpdateFields(action, session, updateFields);

            String requestBody = new ObjectMapper().writeValueAsString(updateFields);
            log.debug("Sending PATCH to {} with data: {}",
                    apiBaseUrl + "/api/v1/worksites/" + siteId,
                    requestBody);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + session.getToken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Void> response = restTemplate.exchange(
                    apiBaseUrl + "/api/v1/worksites/" + siteId,
                    HttpMethod.PATCH,
                    requestEntity,
                    Void.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                sendMessage(chatId, successMessage);
                getUserRegion(chatId, session);
            } else {
                sendMessage(chatId, "⚠️ Не удалось обновить объект. Статус: " + response.getStatusCode());
            }
        } catch (Exception e) {
            sendMessage(chatId, "❌ Ошибка: " + e.getMessage());
            log.error("Error handling work site action", e);
        }
    }

    private String prepareUpdateFields(String action, UserSession session, Map<String, Object> updateFields) {
        switch (action) {
            case "take":
                updateFields.put("atWork", true);
                updateFields.put("userAtWork", session.getUsername());
                return "✅ Объект взят в работу";
            case "cancel":
                updateFields.put("atWork", false);
                updateFields.put("userAtWork", "");
                return "🔄 Работа отменена";
            case "done":
                updateFields.put("done", true);
                updateFields.put("atWork", false);
                updateFields.put("noDone", false);
                updateFields.put("userAtWork", session.getUsername());
                return "✔️ Объект выполнен";
            case "markdone":
                updateFields.put("noDone", false);
                updateFields.put("userAtWork", session.getUsername());
                return "✔️ Отмечено как выполнено";
            default:
                throw new IllegalArgumentException("Неизвестное действие: " + action);
        }
    }

    private String formatAddress(WorkSiteDto site) {
        StringBuilder address = new StringBuilder();
        if (site.getStreetTitle() != null) {
            address.append(site.getStreetTitle());
        }
        if (site.getHouse() != null) {
            address.append(", д. ").append(site.getHouse());
        }
        if (site.getFrame() != null) {
            address.append(", корп. ").append(site.getFrame());
        }
        return address.toString();
    }

    private String getStatusText(WorkSiteDto site) {
        if (site.getDone()) {
            return "✅ Завершено";
        } else if (site.getAtWork()) {
            return "🟡 В работе";
        } else if (site.getNoDone()) {
            return "🔴 Не выполнено";
        }
        return "⚪ Не начато";
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
        }
    }

    private static class UserSession {

        private String token;
        private String username;
        private String firstName;
        private String lastName;
        private Long regionId;
        private LocalDateTime lastActivity;

        private UserSession() {
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public Long getRegionId() { return regionId; }
        public void setRegionId(Long regionId) { this.regionId = regionId; }
        public LocalDateTime getLastActivity() { return lastActivity; }
        public void setLastActivity(LocalDateTime lastActivity) { this.lastActivity = lastActivity; }
//        userService.findByUsername(principal.getName()).get().getRegiones().getId()
    }
}
