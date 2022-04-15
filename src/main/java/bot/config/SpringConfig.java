package bot.config;

import bot.controller.Bot;
import bot.message.CallbackQueryHandler;
import bot.message.MessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

/**
 * Конфигурация спринга
 */

@Configuration
@AllArgsConstructor
public class SpringConfig {

    private final TelegramConfig botConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebHookPath()).build();
    }

    @Bean
    public Bot springWebhookBot(SetWebhook setWebhook,
                                MessageHandler messageHandler,
                                CallbackQueryHandler callbackQueryHandler) {
        Bot bot = new Bot(setWebhook, messageHandler, callbackQueryHandler);
        bot.setBotToken(botConfig.getBotToken());
        bot.setBotUsername(botConfig.getUserName());
        bot.setBotPath(botConfig.getWebHookPath());

        return bot;
    }
}
