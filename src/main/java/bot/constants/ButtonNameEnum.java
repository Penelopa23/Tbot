package bot.constants;


/**
 * Названия кнопок основной клавиатуры
 */

public enum ButtonNameEnum {

    START_BUTTON("Погнали!"),
    CHANGE_URL("Изменить ссылку"),
    CLEAR_MAP("Очистить список игроков получивших ссылку"),
    CHANGE_ADMINS("Удалить или добавить администраторов"),
    TO_USER_MENU("Открыть панель пользователя"),
    LOOK_ID("Узнать id пользователя"),
    URL("Ссылка на вашу команду!");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
