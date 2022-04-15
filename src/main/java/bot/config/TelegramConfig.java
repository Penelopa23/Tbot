package bot.config;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * Конфигурация телеграмма
 */
@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramConfig {
    @Value("${telegram.webhook-path}")
    String webHookPath;
    @Value("${telegram.bot-name}")
    String userName;
    @Value("${telegram.bot-token}")
    String botToken;
}
