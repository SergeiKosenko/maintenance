package ru.service.maintenance.core.bot;

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
import ru.service.maintenance.core.dtos.RegionesDto;
import ru.service.maintenance.core.dtos.WorkSiteDto;
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
        } else if (callbackData.startsWith("district_")) {
            Long districtId = Long.parseLong(callbackData.substring("district_".length()));
            displayDistrictObjects(chatId, districtId, session);
        } else if ("back_to_districts".equals(callbackData)) {
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

                UserSession session = new UserSession();
                session.setToken(token);
                session.setUsername(username);
                // regionId больше не устанавливаем здесь, он будет получен через getUserRegion
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

            // 1. Получаем регион пользователя
            ResponseEntity<RegionesDto> regionResponse = restTemplate.exchange(
                    apiBaseUrl + "/api/v1/regiones",
                    HttpMethod.GET,
                    entity,
                    RegionesDto.class
            );

            if (regionResponse.getStatusCode() == HttpStatus.OK && regionResponse.getBody() != null) {
                Long regionId = regionResponse.getBody().getId();
                session.setRegionId(regionId);

                // 2. Получаем активные объекты для группировки по районам
                ResponseEntity<List<WorkSiteDto>> activeResponse = restTemplate.exchange(
                        apiBaseUrl + "/api/v1/worksites/region/" + regionId,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<WorkSiteDto>>() {}
                );

                // 3. Получаем невыполненные в прошлом месяце
                ResponseEntity<List<WorkSiteDto>> notDoneResponse = restTemplate.exchange(
                        apiBaseUrl + "/api/v1/worksites/regionnodone/" + regionId,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<WorkSiteDto>>() {}
                );

                if (activeResponse.getStatusCode() == HttpStatus.OK && activeResponse.getBody() != null) {
                    // Показываем список районов
                    List<WorkSiteDto> activeSites = activeResponse.getBody();
                    Map<String, List<WorkSiteDto>> districts = activeSites.stream()
                            .collect(Collectors.groupingBy(WorkSiteDto::getDistrictTitle));

                    if (!districts.isEmpty()) {
                        SendMessage districtsMessage = new SendMessage();
                        districtsMessage.setChatId(chatId.toString());
                        districtsMessage.setText("Выберите район:");

                        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

                        districts.forEach((districtName, districtSites) -> {
                            Long districtId = districtSites.get(0).getDistrictId();
                            keyboard.add(List.of(
                                    InlineKeyboardButton.builder()
                                            .text(districtName)
                                            .callbackData("district_" + districtId)
                                            .build()
                            ));
                        });

                        markup.setKeyboard(keyboard);
                        districtsMessage.setReplyMarkup(markup);
                        execute(districtsMessage);
                    }
                    sendMessage(chatId, "\n📋 Не сделано: " + activeSites.size() + " объекта");
                    // Показываем невыполненные объекты
                    if (notDoneResponse.getStatusCode() == HttpStatus.OK &&
                            notDoneResponse.getBody() != null &&
                            !notDoneResponse.getBody().isEmpty()) {
                        sendMessage(chatId, "\n📋 Не выполненные в прошлом месяце:");
                        displayWorkSiteList(chatId, notDoneResponse.getBody(), session);
                    }
                }
            }
        } catch (Exception e) {
            sendMessage(chatId, "Сессия истекла!\n\n" +
                            "Для авторизации введите ваш логин и пароль в формате:\n" +
                            "логин:пароль\n\n" +
                            "Пример: login:password");
//            + e.getMessage());

            log.error("Error getting region data", e);
        }
    }

    private void displayDistrictObjects(Long chatId, Long districtId, UserSession session) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + session.getToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<WorkSiteDto>> response = restTemplate.exchange(
                    apiBaseUrl + "/api/v1/worksites/district/" + districtId,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<WorkSiteDto>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<WorkSiteDto> sites = response.getBody();

                if (sites.isEmpty()) {
                    sendMessage(chatId, "В этом районе нет объектов");
                    return;
                }

                String districtName = sites.get(0).getDistrictTitle();

                // Сначала отправляем заголовок с названием района
                sendMessage(chatId, "🏙 " + districtName + "");

                for (WorkSiteDto site : sites) {
                    StringBuilder messageText = new StringBuilder();
                    messageText.append("\n📍 ").append(formatAddress(site)).append("\n");
                    messageText.append("🛠 ").append(site.getManufactureTitle()).append("\n");
                    messageText.append("⚙ Статус: ").append(getStatusText(site)).append("\n");

                    if (site.getAtWork()) {
                        messageText.append("👷 ").append(site.getUserAtWork()).append("\n");
                    }

                    List<InlineKeyboardButton> buttons = new ArrayList<>();

                    if ((!site.getDone()) & (!site.getNoDone())) {
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

                    if ((!site.getDone()) & (site.getNoDone())){
                        if (site.getAtWork()) {
                            buttons.add(InlineKeyboardButton.builder()
                                    .text("❌ Отменить")
                                    .callbackData("action_cancelno_" + site.getId())
                                    .build());

                            buttons.add(InlineKeyboardButton.builder()
                                    .text("✔️ Отметить выполненным")
                                    .callbackData("action_doneno_" + site.getId())
                                    .build());
                        } else {
                            buttons.add(InlineKeyboardButton.builder()
                                    .text("✅ Взять в работу")
                                    .callbackData("action_takeno_" + site.getId())
                                    .build());
                        }
                    }

                    if (!buttons.isEmpty()) {
                        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                        markup.setKeyboard(List.of(buttons));

                        SendMessage message = new SendMessage();
                        message.setChatId(chatId.toString());
                        message.setText(messageText.toString());
                        message.setParseMode("HTML");
                        message.setReplyMarkup(markup);

                        execute(message);
                    }
                }

                // Добавляем кнопку возврата отдельным сообщением
                InlineKeyboardMarkup backMarkup = new InlineKeyboardMarkup();
                backMarkup.setKeyboard(List.of(
                        List.of(InlineKeyboardButton.builder()
                                .text("◀️ К выбору района")
                                .callbackData("back_to_districts")
                                .build())
                ));

                SendMessage backMessage = new SendMessage();
                backMessage.setChatId(chatId.toString());
                backMessage.setText("Выберите действие:");
                backMessage.setReplyMarkup(backMarkup);

                execute(backMessage);
            }
        } catch (Exception e) {
            sendMessage(chatId, "Ошибка при получении объектов: " + e.getMessage());
            log.error("Error displaying district objects", e);
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

                if ((site.getDone()) & (site.getNoDone())) {
                    if (site.getAtWork()) {
                        buttons.add(InlineKeyboardButton.builder()
                                .text("❌ Отменить")
                                .callbackData("action_cancelnodone_" + site.getId())
                                .build());

                        buttons.add(InlineKeyboardButton.builder()
                                .text("✔️ Сделано")
                                .callbackData("action_marknodone_" + site.getId())
                                .build());
                    } else {
                        buttons.add(InlineKeyboardButton.builder()
                                .text("✅ Взять в работу")
                                .callbackData("action_takenodone_" + site.getId())
                                .build());
                    }
                }

                if ((!site.getDone()) & (site.getNoDone())) {
                    if (site.getAtWork()) {
                        buttons.add(InlineKeyboardButton.builder()
                                .text("❌ Отменить")
                                .callbackData("action_canceldone_" + site.getId())  //canceldone
                                .build());

                        buttons.add(InlineKeyboardButton.builder()
                                .text("✔️ Выполнено")
                                .callbackData("action_markdone_" + site.getId())    //markdone
                                .build());
                    } else {
                        buttons.add(InlineKeyboardButton.builder()
                                .text("✅ Взять в работу")
                                .callbackData("action_takedone_" + site.getId())  //takedone
                                .build());
                    }

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
                getUserRegion(chatId, session); // Обновляем список через getUserRegion
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
                updateFields.put("done", false);
                updateFields.put("atWork", true);
                updateFields.put("noDone", false);
                updateFields.put("userAtWork", session.getUsername());
                return "✅ Объект взят в работу";
            case "cancel":
                updateFields.put("done", false);
                updateFields.put("atWork", false);
                updateFields.put("noDone", false);
                updateFields.put("userAtWork", "");
                return "🔄 Работа отменена";
            case "done":
                updateFields.put("done", true);
                updateFields.put("atWork", false);
                updateFields.put("noDone", false);
                updateFields.put("userAtWork", "");
                return "✔️ Объект выполнен";

            case "takeno":
                updateFields.put("done", false);
                updateFields.put("atWork", true);
                updateFields.put("noDone", true);
                updateFields.put("userAtWork", session.getUsername());
                return "✅ Объект взят в работу";
            case "cancelno":
                updateFields.put("done", false);
                updateFields.put("atWork", false);
                updateFields.put("noDone", true);
                updateFields.put("userAtWork", "");
                return "🔄 Работа отменена";
            case "doneno":
                updateFields.put("done", true);
                updateFields.put("atWork", false);
                updateFields.put("noDone", true);
                updateFields.put("userAtWork", "");
                return "✔️ Объект выполнен";


            case "takenodone":
                updateFields.put("done", true);
                updateFields.put("atWork", true);
                updateFields.put("noDone", true);
                updateFields.put("userAtWork", session.getUsername());
                return "✅ Объект взят в работу";
            case "cancelnodone":
                updateFields.put("done", true);
                updateFields.put("atWork", false);
                updateFields.put("noDone", true);
                updateFields.put("userAtWork", "");
                return "🔄 Работа отменена";
            case "marknodone":
                updateFields.put("done", true);
                updateFields.put("atWork", false);
                updateFields.put("noDone", false);
                updateFields.put("userAtWork", "");
                return "✔️ Объект выполнен";


            case "takedone":
                updateFields.put("done", false);
                updateFields.put("atWork", true);
                updateFields.put("noDone", true);
                updateFields.put("userAtWork", session.getUsername());
                return "✅ Объект взят в работу";
            case "canceldone":
                updateFields.put("done", false);
                updateFields.put("atWork", false);
                updateFields.put("noDone", true);
                updateFields.put("userAtWork", "");
                return "🔄 Работа отменена";
            case "markdone":
                updateFields.put("done", false);
                updateFields.put("atWork", false);
                updateFields.put("noDone", false);
                updateFields.put("userAtWork", "");
                return "✔️ Объект выполнен";


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
        if ((!site.getNoDone()) & (site.getDone())) {
            return "✅ Завершено";
        } else if (site.getAtWork()) {
            return "🟡 В работе";
        } else if ((site.getNoDone()) & (!site.getDone())) {
            return "🔴 Не выполнено";
        } else if ((site.getNoDone()) & (site.getDone())) {
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
    }
}
