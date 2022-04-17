package bot.message;


import bot.constants.BotMessageEnum;
import bot.constants.ButtonNameEnum;
import bot.keyboard.AdminBotKeyboard;
import bot.keyboard.InlineAdminKeyboard;
import bot.keyboard.InlineKeyboardMaker;
import bot.utils.Utils;
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
        String[] commnad = Utils.splitInputText(inputText);
        switch (commnad[0]) {
            case ("Изменить ссылку"):
                return getChangeUrl(chatId);
            case ("Очистить список игроков получивших ссылку"):
                return clearMap(chatId);
            case ("Удалить или добавить администраторов"):
                return getChangeAdmins(chatId);
            case ("Открыть панель пользователя"):
                MessageHandler.getAdmins().replace(chatId, true, false);
                return new SendMessage(chatId, "/start <- нажми чтобы открыть панель пользователя");
            case ("Узнать id пользователя"):
                return new SendMessage(chatId, "Перешли боту сообщение от нужного пользователя");
            case ("$change-url"):
                return changeUrl(chatId, inputText);
            case ("$add-new-admin"):
            case ("$delete-admin"):
                return changeAdmins(chatId, inputText);
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
    private SendMessage changeUrl(String chatId, String inputText) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.CREATE_URL.getMessage());
        sendMessage.enableMarkdown(true);
        String[] url = Utils.splitInputText(inputText);
        MessageHandler.setMainUrl(url[1]);
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

    /**
     * Панель добавления администраторов
     */
    private SendMessage getChangeAdmins(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.ADD_ADMIN.getMessage());
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    /**
     * Панель редактирования списка администраторов
     */
    private SendMessage changeAdmins(String chatId, String inputText) {
        String[] result = Utils.splitInputText(inputText);

        switch (result[0]) {
            case ("$add-new-admin"):
                MessageHandler.setAdmins(result[1], false);
                break;
            case ("$delete-admin"):
                MessageHandler.getAdmins().remove(result[1]);
                break;
            default:
                return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }

        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.ADMIN_CHANGES.getMessage());
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

}
