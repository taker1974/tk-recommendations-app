package ru.spb.tksoft.advertising.handler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * Handles message. A base class that echoes a request.
 */
@Component
public class MessageHandler {

    public static final String MESSAGE_TOO_SHORT =
            "Сообщение равно null, или пустая строка, или только пробелы, или слишком короткая строка";
    public static final String MESSAGE_TOO_LONG = "Сообщение слишком длинное";

    public static final String WRONG_REQUEST = """
            Пожалуйста, уточните ваш запрос.
            Введите /help для получения справки.
            """;

    protected final List<String> commands;

    public MessageHandler(final List<String> commands) {
        this.commands = commands;
    }

    public List<String> getCommands() {
        return Collections.unmodifiableList(null == commands ? Collections.emptyList() : commands);
    }

    public String getHelp() {
        return "";
    }

    public boolean canHandle(final String message) {
        if (message == null) {
            return false;
        }

        for (final String command : commands) {
            if (message.startsWith(command)) {
                return true;
            }
        }
        return false;
    }

    public static final int MESSAGE_LENGTH_MAX_RAW = 100;

    public Optional<String> checkMessageTooLong(String rawMessage) {
        if (rawMessage != null && rawMessage.length() > MESSAGE_LENGTH_MAX_RAW) {
            return Optional.of(MESSAGE_TOO_LONG);
        }
        return Optional.empty();
    }

    public static final int MESSAGE_LENGTH_MIN_TRIMMED = 1;

    public Optional<String> checkMessageTooShort(String message) {
        if (message == null || message.isBlank() || message.isEmpty() ||
                message.length() < MESSAGE_LENGTH_MIN_TRIMMED) {
            return Optional.of(MESSAGE_TOO_SHORT);
        }
        return Optional.empty();
    }

    public String removeCommand(String message) {
        // Expected [already trimmed] message format:
        // <command-prefix>[spaces]<parameter>
        // So: remove command prefix (one of), trim again.
        for (String command : commands) {
            if (message.startsWith(command)) {
                message = message.replace(command, "").trim(); // replace is
                                                               // fine
                break;
            }
        }
        return message;
    }

    public String handle(final long chatId, final int messageId,
            final String message) {

        return String.format("%d, %d: %s", chatId, messageId, message);
    }
}
