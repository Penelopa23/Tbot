package bot.constants;


/**
 * Названия кнопок основной клавиатуры
 */

public enum ButtonNameEnum {

    /**
     * Стартовая кнопка
     */
    START_BUTTON("Погнали!");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
