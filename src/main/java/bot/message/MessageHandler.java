package bot.message;

import bot.config.BotConfig;
import bot.constants.BotMessageEnum;
import bot.keyboard.InlineAdminKeyboard;
import bot.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.polls.StopPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Класс принимающий сообщения пользователей
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final InlineAdminKeyboard inlineAdminKeyboard;
    private static HashMap<String, String> users = new HashMap<>();
    private static HashMap<String, Boolean> admins = new HashMap<>();
    private static String mainUrl = "https://qrga.me/go/5";
    private final AdminMessageHandler adminMessageHandler;


    public BotApiMethod<?> answerMessage(Update update, BotConfig botConfig) throws TelegramApiException, InterruptedException {
//        bot.execute(new StopPoll(message.getChatId().toString(), message.getMessageId()));


        /**
         * Берём идентификатор отправителя
         */
        String chatId = update.getMessage().getChatId().toString();
        System.out.println(chatId);
        /**
         * Добавление админа и включение доступа к панели админа
         */
        if (update.getMessage().getText().toLowerCase().equals("yaadmin")) {
            admins.put(update.getMessage().getChatId().toString(), true);
            return adminMessageHandler.getAdminPanel(chatId, update.getMessage());
        }


        /**
         * Берём текст сообщения
         */
        String inputText = update.getMessage().getText();

        /**
         * Обрабатываем сообщение и отдаём ответ
         */
        switch (inputText) {
            case ("/start"):
                if (!users.containsKey(chatId)) {
                    return getPoll(chatId, botConfig);
                } else {
                    return getReplyUrl(chatId);
                }
            case ("/admin"):
                if (admins.containsKey(chatId)) {
                    admins.replace(chatId, false, true);
                    return adminMessageHandler.getAdminPanel(chatId, update.getMessage());
                }
            default:
                return getStartMessage(chatId);
        }

    }

    /**
     * Добавляем постоянную клавиатуру
     */
    public SendMessage getStartMessage(String chatId) {
        return new SendMessage(chatId, "Приветствие! Нажми кнопку старт чтобы начать!");
    }
    public SendMessage getStart(String chatId) {

        return new SendMessage(chatId, "Мотивационный текст на заполнение информации");
        }

    public SendPoll getPoll(String chatId, BotConfig botConfig) throws TelegramApiException, InterruptedException {
    botConfig.execute(new SendMessage(chatId, "Мотивационный текст на заполнение информации"));
    Thread.sleep(6000);
    ArrayList<String> ll = new ArrayList<>();
    ll.add("Экология и благотворительность");
    ll.add("Социально-гуманитарные науки");
    ll.add("Карьера и Soft skills");
    ll.add("Наука и IT");
    ll.add("Естественные науки");
    ll.add("Медиа и Маркетинг");
    ll.add("Здоровье (в том числе ментальное) и спорт");
    ll.add("Культура и искусство");
    ll.add("Патриотическое воспитание");
    ll.add("Другое");


    SendPoll sendPoll = new SendPoll(chatId, "Выберите пункт", ll);
    sendPoll.setIsAnonymous(false);
    sendPoll.setOpenPeriod(30);//seconds
    return sendPoll;
    }


    /**
     * Формируем ответ на повторный запрос участника который уже получил ссылку
     */
    private SendMessage getReplyUrl(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.LOOK_URL.getMessage() + "\n" + users.get(chatId));
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendMessage getTestMain(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.START_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);

        sendMessage.setReplyMarkup(inlineAdminKeyboard.getInlineMessageButtonsChangeUrl());
        return sendMessage;
    }

    public void execute(String chatId) {

            String url = "https://api.telegram.org/bot1970922099:AAFgu3OzMmbM5QAE0ww_JRXkMVoQjaP10Hc/sendMessage?" +
                    "chat_id=" + chatId + "&text=" ;

            Utils.getUrl(url);
        }


    public static HashMap<String, String> getUsers() {
        return users;
    }

    public static HashMap<String, Boolean> getAdmins() {
        return admins;
    }

    public static String getMainUrl() {
        return mainUrl;
    }

    public static void setMainUrl(String mainUrl) {
        MessageHandler.mainUrl = mainUrl;
    }
}
