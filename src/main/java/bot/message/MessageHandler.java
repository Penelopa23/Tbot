package bot.message;

import bot.constants.BotMessageEnum;
import bot.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;


/**
 * Класс принимающий сообщения пользователей
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {

    private static HashMap<String, String> users = new HashMap<>();
    private static HashMap<String, Boolean> admins = new HashMap<>();
    private static String mainUrl = "https://qrga.me/go/5";
    private final AdminMessageHandler adminMessageHandler;


    public BotApiMethod<?> answerMessage(Message message) {

        /**
         * Берём идентификатор отправителя
         */
        String chatId = message.getChatId().toString();

        /**
         * Добавление админа и включение доступа к панели админа
         */
        if (message.getText().toLowerCase().equals("yaadmin")) {
            admins.put(message.getChatId().toString(), true);
            return adminMessageHandler.getAdminPanel(chatId, message);
        }


        /**
         * Берём текст сообщения
         */
        String inputText = message.getText();


        /**
         * Обрабатываем сообщение и отдаём ответ
         */
        switch (inputText) {
            case ("/start"):
                if (!users.containsKey(chatId)) {
                    return getStartMessage(chatId);
                } else {
                    return getReplyUrl(chatId);
                }
            case ("/admin"):
                if (admins.containsKey(chatId)) {
                    admins.replace(chatId, false, true);
                    return adminMessageHandler.getAdminPanel(chatId, message);
                }
            default:
                return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }

    }

    /**
     * Добавляем постоянную клавиатуру
     */
    public SendMessage getStartMessage(String chatId) {
        String firstUrl = Utils.getUrl(mainUrl);
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.START_MESSAGE.getMessage() + firstUrl);
        sendMessage.enableMarkdown(true);
        users.put(chatId, firstUrl);
        return sendMessage;
    }


    /**
     * Формируем ответ на повторный запрос участника который уже получил ссылку
     */
    private SendMessage getReplyUrl(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.LOOK_URL.getMessage() + "\n" + users.get(chatId));
        sendMessage.enableMarkdown(true);
        return sendMessage;
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
