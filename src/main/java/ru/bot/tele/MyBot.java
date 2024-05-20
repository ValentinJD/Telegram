package ru.bot.tele;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class MyBot extends TelegramLongPollingCommandBot {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Настройки файла для разных пользователей. Ключ - уникальный id чата
     */
    @Getter
    private static Map<Long, String> userSettings;

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    public MyBot(String botName, String botToken) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;

        register(new StartCommand("start", "Старт"));
        logger.debug("Команда start создана");


        register(new HelpCommand());
        logger.debug("Команда help создана");

        userSettings = new HashMap<>();
        logger.info("Бот создан!");
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = Utils.getUserName(msg);

        String answer = nonCommandExecute(chatId, userName, msg.getText());
        setAnswer(chatId, userName, answer);
    }

    private String nonCommandExecute(Long chatId, String userName, String text) {
        return String.format("test msg chatId %s userName %s text %s",
                chatId, userName, text);
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param userName имя пользователя
     * @param text текст ответа
     */
    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }
    }
}
