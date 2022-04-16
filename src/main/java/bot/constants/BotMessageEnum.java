package bot.constants;


/**
 * Текстовые сообщения, посылаемые ботом
 */

public enum BotMessageEnum {

    /**
     * Приветственное сообщение
     */
    START_MESSAGE("\uD83D\uDC4B Привет! Воспользуйтесь клавиатурой, чтобы начать работу\uD83D\uDC47"),

    CREATE_URL("Поздравляем!\ud83e\udd73 Вот ваша ссылка!"),
    LOOK_URL("Вам уже была выдана ссылка\uD83D\uDC47"),

    /**
     * Ошибки кнопки
     */
    NON_COMMAND_MESSAGE("Пожалуйста, воспользуйтесь клавиатурой\uD83D\uDC47"),
    EXCEPTION_BAD_BUTTON_NAME_MESSAGE("Неверное значение кнопки. Крайне странно. Попробуйте позже"),

    /**
     * Остальные ошибки
     */
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
