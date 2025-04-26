package ru.spb.tksoft.advertising.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import jakarta.annotation.PostConstruct;
import ru.spb.tksoft.advertising.handler.MessageHandler;
import ru.spb.tksoft.advertising.handler.MessageHandlerRecommend;
import ru.spb.tksoft.advertising.handler.MessageManager;
import ru.spb.tksoft.advertising.proxy.UserRecommendationProxy;
import ru.spb.tksoft.advertising.tools.LogEx;
import ru.spb.tksoft.advertising.tools.StringEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RecommendationsBot
        implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    protected static final List<String> COMMANDS_START =
            Arrays.asList("/start", "start");

    protected static final List<String> COMMANDS_HELP =
            Arrays.asList("/help", "help", "-h", "?");

    public static final String START_TEXT = """
            Бот рекомендаций работает.
            Используйте /help для получения инструкций.
            """;

    private final Logger log = LoggerFactory.getLogger(RecommendationsBot.class);

    private final TelegramClient client;
    private final String token;

    private final UserRecommendationProxy userRecommendationService;

    private MessageManager messageManager;

    @Override
    public String getBotToken() {
        return token;
    }

    @SuppressWarnings("java:S6813")
    @Autowired(required = true)
    private MessageHandlerRecommend handlerRecommend;

    public RecommendationsBot(
            @Value("${telegram.bot.token}") final String tokenRaw,
            @Value("${telegram.bot.token-variable}") final String tokenVariable,
            UserRecommendationProxy userRecommendationService) {

        String tokenTrimmed = tokenRaw.trim();
        if (tokenTrimmed != null && !tokenTrimmed.isEmpty()
                && !tokenTrimmed.isBlank()) {
            this.token = tokenTrimmed;
        } else {
            this.token = System.getenv(tokenVariable);
        }

        client = new OkHttpTelegramClient(getBotToken());

        this.userRecommendationService = userRecommendationService;
    }

    @PostConstruct
    private void postConstruct() {

        final ArrayList<MessageHandler> handlers = new ArrayList<>();
        handlers.add(handlerRecommend);

        messageManager = new MessageManager(handlers, handlerRecommend);
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        LogEx.info(log, LogEx.getThisMethodName(),
                "Registered bot running state is: " + botSession.isRunning());
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processMessage(update);
        }
    }

    private void processMessage(Update update) {
        try {
            long chatId = update.getMessage().getChatId();
            int messageId = update.getMessage().getMessageId();
            String messageText = update.getMessage().getText();

            LogEx.info(log, LogEx.getThisMethodName(),
                    StringEx.replace("{chatId}: {message}", chatId,
                            messageText));

            String response = "";

            for (String command : COMMANDS_START) {
                if (messageText.startsWith(command)) {
                    response = START_TEXT;
                    break;
                }
            }

            if (response.isEmpty()) {
                for (String command : COMMANDS_HELP) {
                    if (messageText.startsWith(command)) {
                        response = messageManager.getHelp();
                        break;
                    }
                }
            }

            if (response.isEmpty()) {
                response = messageManager.manage(chatId, messageId, messageText);
            }

            if (!response.isEmpty() && !response.isBlank()) {

                SendMessage newMessage = SendMessage.builder()
                        .chatId(chatId)
                        .text(response)
                        .build();
                client.execute(newMessage);
            }

        } catch (Exception e) {
            LogEx.error(log, LogEx.getThisMethodName(), e.getMessage());
        }
    }
}
