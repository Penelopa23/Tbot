package bot.message;


import bot.constants.BotMessageEnum;
import bot.keyboard.AdminBotKeyboard;
import bot.keyboard.InlineAdminKeyboard;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Класс принимающий сообщения пользователей
 */

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AdminMessageHandler {

    private final InlineAdminKeyboard inlineAdminKeyboard;
    private final AdminBotKeyboard adminBotKeyboard;

    public SendMessage getAdminPanel(String chatId, Message message) {
        if (message.getForwardDate() != null) {
            return new SendMessage(chatId, message.getForwardFrom().getId().toString());
        }
        String inputText = message.getText();

        if (message.isReply()) {
            inputText = message.getReplyToMessage().getText();
            switch (inputText) {
                case ("Отправьте новуюю ссылку ответом(Reply) на это сообщение"):
                    return changeUrl(chatId, message.getText());
            }
        }

        switch (inputText) {
            case ("Изменить ссылку"):
                return getChangeUrl(chatId);
            case ("Очистить список игроков получивших ссылку"):
                return clearMap(chatId);
            case ("Открыть панель пользователя"):
                MessageHandler.getAdmins().replace(chatId, true, false);
                return new SendMessage(chatId, "/start <- нажми чтобы открыть панель пользователя");
            default:
                return getMainAdminPanel(chatId);
        }
    }

    /**
     * Основная панель администратора
     */
    private SendMessage getMainAdminPanel(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.ADMIN_START_BUTTON.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(adminBotKeyboard.getAdminMenuKeyboard());
        return sendMessage;
    }

    /**
     * Панель изменения ссылки
     */
    private SendMessage getChangeUrl(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.CHANGE_URL.getMessage());
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    /**
     * Панель редактирования ссылки
     */
    private SendMessage changeUrl(String chatId, String url) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.CREATE_URL.getMessage());
        sendMessage.enableMarkdown(true);
        MessageHandler.setMainUrl(url);
        sendMessage.setReplyMarkup(inlineAdminKeyboard.getInlineMessageButtonsChangeUrl());
        return sendMessage;
    }

    /**
     * Очищаем мапу с юзерами
     */
    private SendMessage clearMap(String chatId) {
        MessageHandler.getUsers().clear();
        SendMessage sendMessage = new SendMessage(chatId, "All users was deleted? " + MessageHandler.getUsers().isEmpty());
        return sendMessage;
    }
}
