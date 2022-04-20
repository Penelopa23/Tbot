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

        rowList.add(getButton("Экология и благотворительность (НКО, экологические проблемы)","newUrl"));
        rowList.add(getButton("Социально-гуманитарные науки (история, философия, политология, социология, психология, логика)","newUrl"));
        rowList.add(getButton("Карьера и Soft skills (профориентация, тайм-менеджмент, лидерство, карьерные треки, экономика, финансовая грамотность)","newUrl"));
        rowList.add(getButton("Наука и IT (астрономия, математика, информатика, робототехника, безопасность в сети/кибербезопасность)","newUrl"));
        rowList.add(getButton("Естественные науки (биология, физика, химия, геология, космонавтика, астрономия)","newUrl"));
        rowList.add(getButton("Медиа и Маркетинг (новые и старые медиа, журналистика, подкасты, социальные сети, SMM, маркетинговые исследования, PR","newUrl"));
        rowList.add(getButton("Здоровье (в том числе ментальное) и спорт (нутрициология, танцы, профилактика заболеваний)","newUrl"));
        rowList.add(getButton("Культура и искусство (живопись, актерское мастерство, кино, архитектура)","newUrl"));
        rowList.add(getButton("Патриотическое воспитание","newUrl"));
        rowList.add(getButton("Другое","newUrl"));
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
