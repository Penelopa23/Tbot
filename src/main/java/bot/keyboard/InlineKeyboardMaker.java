package bot.keyboard;

import bot.constants.ButtonNameEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс формирования кнопок
 */

@Component
public class InlineKeyboardMaker {
    /**
     * Формируем клавиатуру и добавляем кнопки
     */
    public InlineKeyboardMarkup getInlineMessageButtonsWithTemplate(String url) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getInlineMessageButtons(url);
        inlineKeyboardMarkup.getKeyboard().add(getButton(
                ButtonNameEnum.URL.getButtonName(),
                url
        ));
        return inlineKeyboardMarkup;
    }

    /**
     * Формируем кнопки, если в клавиатуре уже есть кнопки, то добавляем новые (задел на будущее),
     * если нет, то возвращаем пустой набор
     */
    public InlineKeyboardMarkup getInlineMessageButtons(String prefix) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        if (!rowList.isEmpty()) {
            rowList.add(getButton(
                    "Как так-то, а?",
                    prefix
            ));
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    /**
     * Создаём кнопку
     */
    private List<InlineKeyboardButton> getButton(String buttonName, String url) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setUrl(url);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}