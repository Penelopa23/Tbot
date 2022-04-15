package bot.message;

import bot.constants.BotMessageEnum;
import bot.constants.ButtonNameEnum;
import bot.keyboard.BotKeyboard;
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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {

    private HashMap<String, String> users = new HashMap<>();
    private final BotKeyboard replyKeyboardMaker;

    public BotApiMethod<?> answerMessage(Message message) {

        /**
         * Берём идентификатор отправителя
         */
        String chatId = message.getChatId().toString();

        /**
         * Берём текст сообщения
         */
        String inputText = message.getText();

        /**
         * Обрабатываем сообщение и отдаём ответ
         */
        if (inputText == null) {
            throw new IllegalArgumentException();
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.START_BUTTON.getButtonName())) {
            return answer(chatId);
        } else {
            return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }
    }


    private SendMessage answer(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        if (users.containsKey(chatId)) {
            sendMessage.setText("Ранее вы уже получили ссылку: " + users.get(chatId));
        } else {
            String url = getUrl();
            sendMessage.setText(url);
            users.put(chatId, url);
        }
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
     * Запращиваем ссылку для пользователя
     */
    @SneakyThrows
    private String getUrl() {
        URL obj = new URL("https://qrga.me/go/1");
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
