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

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Component
@AllArgsConstructor
public class MessageHandlerHakaton {

    private static HashMap<String, User> users = new HashMap<>();
    private static HashMap<String, Boolean> admins = new HashMap<>();
    private static String mainUrl = "https://qrga.me/go/5";
    private final AdminMessageHandler adminMessageHandler;


    public BotApiMethod<?> answerMessage(Update update, BotConfig botConfig) throws TelegramApiException, InterruptedException {

        /**
         * Берём идентификатор отправителя
         */
        String chatId = update.getMessage().getChatId().toString();

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

        if(users.containsKey(chatId)) {
            if(users.get(chatId).getFio() == null) return setFio(chatId, inputText, botConfig, update);
            if(users.get(chatId).getAge() == null) return setAge(chatId, inputText);
            if(users.get(chatId).getPlaceOfResidence() == null) return setPlaceOfResidence(chatId, inputText);
            if(users.get(chatId).getPlaceOfStudy() == null) return setPlaceOfStudy(chatId, inputText);
            if(users.get(chatId).getCareer() == null ) return setCareer(chatId);
            if(users.get(chatId).getWhatThePoint() == null ) return setWhatThePoint(chatId, inputText, botConfig, update);
            if(users.get(chatId).getProductCreationExperience() == null) return setProductCreationExperience(chatId);
        }

        /**
         * Обрабатываем сообщение и отдаём ответ
         */
        switch (inputText) {
            case ("/start"):
                if (!users.containsKey(chatId)) {
                    botConfig.execute(new SendMessage(chatId, "Мотивационое письмо!"));
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
                " Нажми кнопку /start чтобы начать!");
    }

    public SendMessage setAnswerPoll(String chatId, Update update) {
        int index = update.getPollAnswer().getOptionIds().get(0);
        User user = new User();
        user.setUserId(update.getPollAnswer().getUser().getId());
        user.setAnswerPoll(getPollOptions().get(index));
        users.put(chatId, user);
        return new SendMessage(chatId, "Напишите своё ФИО, например: Иванов Иван Иванович");
    }

    public SendPoll getPoll(String chatId) {
        SendPoll sendPoll = new SendPoll(chatId, "Выберите пункт", getPollOptions());
        sendPoll.setIsAnonymous(false);
        return sendPoll;
    }

    private static ArrayList<String> getPollOptions() {
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

    public SendMessage setFio(String chatId, String inputText, BotConfig botConfig, Update update) throws TelegramApiException {
       StopPoll stopPoll = new StopPoll();
       stopPoll.setMessageId(update.getMessage().getMessageId()-2);
       stopPoll.setChatId(chatId);
       botConfig.execute(stopPoll);
       users.get(chatId).setFio(inputText);
       return new SendMessage(chatId, "Напишите ваш возраст");
    }

    private SendMessage setAge(String chatId, String inputText) {
        try {
            int age = Integer.parseInt(inputText);
            if (age >= 10 && age <= 99) {
                users.get(chatId).setAge(age);
                return new SendMessage(chatId, "Напишите пожалуйста место вашего проживания. " +
                        "Регион и населённый пункт.");
            }
        }catch (Exception e) {
            log.error(e.toString());
        }

        return new SendMessage(chatId, "Проверьте правильность введённых данных");
    }

    private SendMessage setPlaceOfResidence(String chatId, String inputText) {
        users.get(chatId).setPlaceOfResidence(inputText);
        return new SendMessage(chatId, "Напишите пожалуйста место ваше место работы/учёбы?");
    }
    private SendPoll setPlaceOfStudy(String chatId, String inputText) {
        users.get(chatId).setPlaceOfStudy(inputText);
        SendPoll sendPoll = new SendPoll(chatId, "Укажите ваш род деятельности", getPollOptionsCareer());
        sendPoll.setIsAnonymous(false);
        return sendPoll;
    }

    private SendPoll setCareer(String chatId) {
        SendPoll sendPoll = new SendPoll(chatId, "Укажите ваш род деятельности", getPollOptionsCareer());
        sendPoll.setIsAnonymous(false);
        return sendPoll;
    }

    private ArrayList<String> getPollOptionsCareer() {
        ArrayList<String> ll = new ArrayList<>();
        ll.add("IT");
        ll.add("Проджект менеджмент");
        ll.add("Продукт менеджмент");
        ll.add("Маркетинг");
        ll.add("Другое");
        return ll;
    }

    public SendMessage setAnswerCareer(String chatId, Update update, BotConfig botConfig) throws TelegramApiException {
        int index = update.getPollAnswer().getOptionIds().get(0);
        users.get(chatId).setUserId(update.getPollAnswer().getUser().getId());
        users.get(chatId).setCareer(getPollOptionsCareer().get(index));
        return new SendMessage(chatId, "Какая твоя цель участия в Хакатоне?");
    }

    private SendPoll setWhatThePoint(String chatId, String inputText, BotConfig botConfig, Update update) throws TelegramApiException {
        StopPoll stopPoll = new StopPoll();
        stopPoll.setMessageId(update.getMessage().getMessageId()-2);
        stopPoll.setChatId(chatId);
        botConfig.execute(stopPoll);
        users.get(chatId).setWhatThePoint(inputText);
        SendPoll sendPoll = new SendPoll(chatId, "В каких направлениях у вас есть опыт создания продуктов? " +
                "(можно отметить несколько)", getPollOptionsproductCreationExperience());
        sendPoll.setIsAnonymous(false);
        sendPoll.setAllowMultipleAnswers(true);
        return sendPoll;
    }

    public StopPoll setAnswerProductCreationExperience(String chatId, Update update, BotConfig botConfig) throws TelegramApiException {
        List<Integer> index = update.getPollAnswer().getOptionIds();
        List<String> result = new ArrayList<>();
        for (int i : index) {
            result.add(getPollOptionsproductCreationExperience().get(i));
        }
        users.get(chatId).setProductCreationExperience(result);
        return new StopPoll(chatId, update.getMessage().getMessageId()-2);
    }

    private  ArrayList<String> getPollOptionsproductCreationExperience() {
        ArrayList<String> ll = new ArrayList<>();
        ll.add("Экология и благотворительность (НКО, экологические проблемы)");
        ll.add("Социально-гуманитарные науки");
        ll.add("Карьера & Soft skills");
        ll.add("Медиа & Маркетинг");
        ll.add("Здоровье и спорт");
        ll.add("Культура и искусство");
        ll.add("Безопасность в сети/кибербезопасность");
        ll.add("Экономика");
        ll.add("Патриотическое воспитание");
        return ll;
    }

    public SendMessage setProductCreationExperience(String chatId) {
        String url = Utils.getUrl(mainUrl);
        users.get(chatId).setUrl(url);
        return new SendMessage(chatId, "Вот ваша ссылка на чат! " + url);
    }

    /**
     * Формируем ответ на повторный запрос участника который уже получил ссылку
     */
    private SendMessage getReply(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.LOOK_URL.getMessage());
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }


    public static HashMap<String, User> getUsers() {
        return users;
    }

    public static HashMap<String, Boolean> getAdmins() {
        return admins;
    }
}
