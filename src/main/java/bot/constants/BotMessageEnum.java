package bot.constants;


/**
 * Текстовые сообщения, посылаемые ботом
 */

public enum BotMessageEnum {

    START_MESSAGE("Привет, ссылка на чат \ud83d\udcac твоей команды "),
    ADMIN_START_BUTTON("Добро пожаловть в панель администратора, выбери команду\uD83D\uDC47"),
    CREATE_URL("Поздравляю!\ud83e\udd73 Вот ваша ссылка!"),
    CHANGE_URL("Отправьте новуюю ссылку ответом(Reply) на это сообщение"),
    LOOK_URL("Вам уже была выдана ссылка\uD83D\uDC47"),
    ADD_ADMIN("Перешли боту любоее сообщение от пользователя котрого надо добавить в админы"),
    DELETE_ADMIN("Перешли боту сообщение от того кого надо удалить из списка админов"),
    ADMIN_CHANGES("Изменения в список администраторов успешно внесены"),
    NON_COMMAND_MESSAGE("Пожалуйста, воспользуйтесь этой кнопкой \ud83d\udc49 /start"),
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
