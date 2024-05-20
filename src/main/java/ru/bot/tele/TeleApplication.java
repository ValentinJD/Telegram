package ru.bot.tele;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TeleApplication {

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyBot("MagTestBot",
                    "9965446601:AAHYUaTwV5V5xPT0dudkuiqv8eW0ncB0v9Q"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
