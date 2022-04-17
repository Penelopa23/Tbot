package bot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс формирования кнопок
 */

@Component
public class InlineAdminKeyboard {

    /**
     * Формируем клавиатуру и добавляем кнопки
     */
    public InlineKeyboardMarkup getInlineMessageButtonsChangeUrl() {
        InlineKeyboardMarkup inlineKeyboardMarkup = getButtonsForChangeUrl();

        return inlineKeyboardMarkup;
    }

    /**
     * Формируем линию кнопок и возвращаем в клаивиатуру
     */
    public InlineKeyboardMarkup getButtonsForChangeUrl() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getButton("Новая ссылка","newUrl"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    /**
     * Создаём кнопку
     */
    private List<InlineKeyboardButton> getButton(String buttonName, String dataButton) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(dataButton);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
