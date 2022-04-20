package bot.scenarios.hakaton;

import bot.config.BotConfig;
import bot.constants.BotMessageEnum;
import bot.keyboard.InlineAdminKeyboard;
import bot.message.AdminMessageHandler;
import bot.message.MessageHandler;
import bot.scenarios.dao.User;
import bot.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.polls.StopPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Component
@AllArgsConstructor
public class MessageHandlerHakaton {

    private final User user;
//    private int messageIdPoll;
    private final InlineAdminKeyboard inlineAdminKeyboard;
    private static HashMap<String, String> users = new HashMap<>();
    private static HashMap<String, Boolean> admins = new HashMap<>();
    private static String mainUrl = "https://qrga.me/go/5";
    private final AdminMessageHandler adminMessageHandler;


    public BotApiMethod<?> answerMessage(Update update, BotConfig botConfig) throws TelegramApiException, InterruptedException {

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

        try {
            String[] fio = inputText.split(" ");
            if (fio.length >= 3) {
                StopPoll stopPoll = new StopPoll();
                stopPoll.setMessageId(update.getMessage().getMessageId()-2);
                stopPoll.setChatId(chatId);
                botConfig.execute(stopPoll);
                user.setFio(inputText);
                return new SendMessage(chatId, "Напишите ваш возраст");
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        try {
            int age = Integer.parseInt(inputText);
            if (age >= 10 && age <= 99) {
                user.setAge(age);
                return new SendMessage(chatId, "Напишите пожалуйста место вашего проживания. " +
                        "Регион и населённый пункт.");
            }
            }catch (Exception e) {
            log.error(e.toString());
        }




        /**
         * Обрабатываем сообщение и отдаём ответ
         */
        switch (inputText) {
            case ("/start"):
                if (!users.containsKey(chatId)) {
                    botConfig.execute(new SendMessage(chatId, "Мотивационое письмо!"));
                    Thread.sleep(10000);
                    return getPoll(chatId);
                } else {
                    return getReply(chatId);
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
        return new SendMessage(chatId, "Привет дорогой друг, мы рады приветствовать тебя на Хакатоне!" +
                " Нажми кнопку старт чтобы начать!");
    }

    public SendMessage setAnswerPoll(String chatId, Update update) {
        int index = update.getPollAnswer().getOptionIds().get(0);
        user.setAnswerPoll(getPollOptions().get(index));
        return new SendMessage(chatId, "Напишите своё ФИО, например: Иванов Иван Иванович");
    }

    public SendPoll getPoll(String chatId) {
        SendPoll sendPoll = new SendPoll(chatId, "Выберите пункт", getPollOptions());
        sendPoll.setIsAnonymous(false);
        return sendPoll;
    }

    private ArrayList<String> getPollOptions() {
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
        return ll;
    }


    /**
     * Формируем ответ на повторный запрос участника который уже получил ссылку
     */
    private SendMessage getReply(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.LOOK_URL.getMessage());
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
        MessageHandler.setMainUrl(mainUrl);
    }
}
