package ru.service.maintenance.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.service.maintenance.bot.MaintenanceBot;

@Configuration
public class TelegramBotConfig {

//    @Bean
//    public TelegramBotsApi telegramBotsApi(
//            @Value("${telegram.bot.token}") String botToken,
//            @Value("${telegram.bot.username}") String botUsername,
//            @Value("${telegram.api.base-url}") String apiBaseUrl) throws TelegramApiException {
//
//        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//        botsApi.registerBot(new MaintenanceBot(botToken, botUsername, apiBaseUrl));
//        return botsApi;
//    }
}
