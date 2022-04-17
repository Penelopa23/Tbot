package bot.keyboard;

import bot.constants.ButtonNameEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminBotKeyboard {

    /**
     * Создаём основную клавиатуру администратора
     */
    public ReplyKeyboardMarkup getAdminMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNameEnum.CHANGE_URL.getButtonName()));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(ButtonNameEnum.CLEAR_MAP.getButtonName()));
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton(ButtonNameEnum.CHANGE_ADMINS.getButtonName()));
        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton(ButtonNameEnum.TO_USER_MENU.getButtonName()));
        KeyboardRow row5 = new KeyboardRow();
        row5.add(new KeyboardButton(ButtonNameEnum.LOOK_ID.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}
