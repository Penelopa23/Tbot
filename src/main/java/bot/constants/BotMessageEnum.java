package bot.constants;


/**
 * Текстовые сообщения, посылаемые ботом
 */

public enum BotMessageEnum {

    START_MESSAGE("\uD83D\uDC4B Привет! Воспользуйтесь клавиатурой, чтобы начать работу\uD83D\uDC47"),
    ADMIN_START_BUTTON("Добро пожаловть в панель администратора, выбери команду\uD83D\uDC47"),
    CREATE_URL("Поздравляю!\ud83e\udd73 Вот ваша ссылка!"),
    CHANGE_URL("Чтобы поменять ссылку впишите команду $change-url и через восклицательный знак укажите новую ссылку" +
            "Например: $change-url!http://newUrl.com"),
    LOOK_URL("Вам уже была выдана ссылка\uD83D\uDC47"),
    ADD_ADMIN("Выбери необходимую команду и Id будущего администратора, и напиши  через восклицательный " +
            "знак необходимую команду. $add-new-admin добавить или $delete-admin чтобы удалить," +
            "например $add-new-admin!1233456!Тест"),
    ADMIN_CHANGES("Изменения в список администраторов успешно внесены"),
    NON_COMMAND_MESSAGE("Пожалуйста, воспользуйтесь клавиатурой\uD83D\uDC47"),
    EXCEPTION_BAD_BUTTON_NAME_MESSAGE("Неверное значение кнопки. Крайне странно. Попробуйте позже"),
    EXCEPTION_ILLEGAL_MESSAGE("Нет, к такому меня не готовили!"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так. Обратитесь к программисту");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
