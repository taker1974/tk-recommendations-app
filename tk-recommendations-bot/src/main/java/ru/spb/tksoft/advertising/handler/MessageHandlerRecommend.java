package ru.spb.tksoft.advertising.handler;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import ru.spb.tksoft.advertising.tools.StringEx;

/**
 * Handle command /recommend
 */
@Component
public class MessageHandlerRecommend extends MessageHandler {

    final Logger log = LoggerFactory.getLogger(MessageHandlerRecommend.class);

    public MessageHandlerRecommend() {

        super(List.of("/recommend", "recommend", "/r")); // main name and aliases
    }

    @PostConstruct
    private void postConstruct() {
        // ...
    }

    @Override
    public String getHelp() {
        return "/recommend <UUID> или /r <UUID> - рекомендовать продукты для пользователя с указанным UUID";
    }

    @Override
    public String handle(final long chatId, final int messageId, final String rawMessage) {

        Optional<String> checkResponse = checkMessageTooLong(rawMessage);
        if (checkResponse.isPresent()) {
            return checkResponse.get();
        }

        final String messageTrimmed = StringEx.removeAdjacentSpaces(rawMessage);
        checkResponse = checkMessageTooShort(messageTrimmed);
        if (checkResponse.isPresent()) {
            return checkResponse.get();
        }

        return "This is a stub, but it works";
    }
}
