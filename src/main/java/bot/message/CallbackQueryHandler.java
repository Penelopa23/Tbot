package bot.message;

import bot.constants.BotMessageEnum;
import bot.utils.Utils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

/**
 * Класс обработки кнопок с клавиатуры (Задел на будущее)
 */
@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {


    /**
     * Обработка сообщения от кнопки с клавиатуры по изменению ссылки
     */
    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final String chatId = buttonQuery.getMessage().getChatId().toString();
        switch (buttonQuery.getData()) {
            case ("newUrl"):
                return new SendMessage(chatId, MessageHandler.getMainUrl());
        }
        return new SendMessage(chatId, "Поздравляю вы долбаёб");
    }
}
