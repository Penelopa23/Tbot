package bot.config;

import bot.constants.BotMessageEnum;
import bot.message.CallbackQueryHandler;
import bot.message.MessageHandler;
import bot.scenarios.hakaton.MessageHandlerHakaton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.StopPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;


/**
 * Класс конфигурации бота
 */

@Getter
@Setter
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotConfig extends SpringWebhookBot {

    String botPath;
    String botUsername;
    String botToken;

    MessageHandlerHakaton messageHandler;
    CallbackQueryHandler callbackQueryHandler;

    public BotConfig(SetWebhook setWebhook, MessageHandlerHakaton messageHandler, CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update, this);
        } catch (IllegalArgumentException e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
        } catch (Exception e) {
            System.out.println(e.toString());
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessageEnum.EXCEPTION_WHAT_THE_FUCK.getMessage());
        }
    }

    private BotApiMethod<?> handleUpdate(Update update, BotConfig botConfig) throws TelegramApiException, InterruptedException {
        if(update.hasPollAnswer()) {
            String chatId = update.getPollAnswer().getUser().getId().toString();
            if(MessageHandlerHakaton.getUsers().containsKey(chatId)) {
                if(MessageHandlerHakaton.getUsers().get(chatId).getWhatThePoint() != null ) {
                    return messageHandler.setAnswerProductCreationExperience(chatId, update, this);
                }
                return messageHandler.setAnswerCareer(chatId, update, this);
            }
            return messageHandler.setAnswerPoll(chatId, update);
        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.answerMessage(update, botConfig);
            }
        }
        return null;
    }
}
