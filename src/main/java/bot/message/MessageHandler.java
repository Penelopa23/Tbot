package bot.message;

import bot.constants.BotMessageEnum;
import bot.keyboard.BotKeyboard;
import bot.keyboard.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


/**
 * Класс принимающий сообщения пользователей
 */

@Component
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {


    private final BotKeyboard replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private HashMap<String, String> users = new HashMap<>();
    private String mainUrl = "https://qrga.me/go/1";

    public BotApiMethod<?> answerMessage(Message message) {

        /**
         * Берём идентификатор отправителя
         */
        String chatId = message.getChatId().toString();

        /**
         * Берём текст сообщения
         */
        String[] inputText = message.getText().split(" ");
        /**
         * Обрабатываем сообщение и отдаём ответ
         */
        switch (inputText[0]) {
            case ("/start"):
                return getStartMessage(chatId);
            case ("/create-base-url"):
                return setUrl(chatId, inputText[1]);
            case ("/clear-all-user"):
                return clearMap(chatId);
            case ("Погнали!"):
                if (users.containsKey(chatId)) {
                    return geteplyUrl(chatId);
                } else {
                    return getUrlMessage(chatId);
                }
            default:
                return new SendMessage(chatId, BotMessageEnum.EXCEPTION_WHAT_THE_FUCK.getMessage());
        }
    }

    /**
     * Формируем первый ответ с ссылкой
     */
    private SendMessage getUrlMessage(String chatId) {
        String firstUrl = getUrl();
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.CREATE_URL.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtonsWithTemplate(firstUrl));
        users.put(chatId, firstUrl);
        return sendMessage;
    }

    /**
     * Добавляем постоянную клавиатуру
     */
    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.START_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    /**
     * Формируем ответ на повторный запрос участника который уже получил ссылку
     */
    private SendMessage geteplyUrl(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.LOOK_URL.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtonsWithTemplate(users.get(chatId)));
        return sendMessage;
    }

    /**
     * Устанавливаем новую ссылку
     */
    private SendMessage setUrl(String chatId, String url) {
        mainUrl = url;
        SendMessage sendMessage = new SendMessage(chatId, "Url was set");
        return sendMessage;
    }

    /**
     * Очищаем мапу с юзерами
     */
    private SendMessage clearMap(String chatId) {
        users.clear();
        SendMessage sendMessage = new SendMessage(chatId, "All users was deleted");
        return sendMessage;
    }

    /**
     * Запращиваем ссылку для пользователя
     */
    @SneakyThrows
    private String getUrl() {
        URL obj = new URL(mainUrl);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
