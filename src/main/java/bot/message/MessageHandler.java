package bot.message;

import bot.constants.BotMessageEnum;
import bot.keyboard.BotKeyboard;
import bot.keyboard.InlineKeyboardMaker;
import bot.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;


/**
 * Класс принимающий сообщения пользователей
 */

@Component
@RequiredArgsConstructor
public class MessageHandler {

    private static HashMap<String, String> users = new HashMap<>();
    private static HashMap<String, Boolean> admins = new HashMap<>();
    private static String mainUrl = "https://qrga.me/go/1";
    private final AdminMessageHandler adminMessageHandler;
    private final BotKeyboard replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;


    public BotApiMethod<?> answerMessage(Message message) {

        /**
         * Берём идентификатор отправителя
         */
        String chatId = message.getChatId().toString();

        /**
         * Проверка на админа и включённый доступ к панели админа
         */
        if (admins.containsKey(chatId)) {
            if (admins.get(chatId).equals(true)) {
                return adminMessageHandler.getAdminPanel(chatId, message);
            }
        }

        /**
         * Проверка меня для отладки
         */
        if (!admins.containsKey(chatId)) admins.put("179755741", false);

        /**
         * Берём текст сообщения
         */
        String inputText = message.getText();


        /**
         * Обрабатываем сообщение и отдаём ответ
         */
        switch (inputText) {
            case ("/start"):
                return getStartMessage(chatId);
            case ("Погнали!"):
                if (users.containsKey(chatId)) {
                    return getReplyUrl(chatId);
                } else {
                    return getUrlMessage(chatId);
                }
            case ("/admin"):
                if (admins.containsKey(chatId)) {
                    admins.replace(chatId, false, true);
                    return adminMessageHandler.getAdminPanel(chatId, message);
                }
            default:
                SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
                sendMessage.enableMarkdown(true);
                sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
                return sendMessage;
        }

    }

    /**
     * Добавляем постоянную клавиатуру
     */
    public SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.START_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(this.replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    /**
     * Формируем первый ответ с ссылкой
     */
    SendMessage getUrlMessage(String chatId) {
        String firstUrl = Utils.getUrl(mainUrl);
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.CREATE_URL.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtonsWithTemplate(firstUrl));
        users.put(chatId, firstUrl);
        return sendMessage;
    }


    /**
     * Формируем ответ на повторный запрос участника который уже получил ссылку
     */
    private SendMessage getReplyUrl(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.LOOK_URL.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtonsWithTemplate(users.get(chatId)));
        return sendMessage;
    }


    public static HashMap<String, String> getUsers() {
        return users;
    }

    public static HashMap<String, Boolean> getAdmins() {
        return admins;
    }

    public static void setAdmins(String id, boolean condition) {
        admins.put(id, condition);
    }

    public static String getMainUrl() {
        return mainUrl;
    }

    public static void setMainUrl(String mainUrl) {
        MessageHandler.mainUrl = mainUrl;
    }
}
