package ru.spb.tksoft.advertising.handler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Обработчик сообщений. Базовый класс для всех обработчиков сообщений.
 */
public abstract class MessageHandler {

    /** Сообщение слишком короткое. */
    public static final String MESSAGE_TOO_SHORT =
            "Сообщение равно null, или пустая строка, или только пробелы, или слишком короткая строка";
    /** Сообщение слишком длинное. */
    public static final String MESSAGE_TOO_LONG = "Сообщение слишком длинное";

    /** Сообщение некорректно. */
    public static final String WRONG_REQUEST = """
            Пожалуйста, уточните ваш запрос.
            Введите /help для получения справки.
            """;

    protected final List<String> commands;

    /**
     * Конструктор.
     * 
     * @param commands Список команд, которые обрабатывает обработчик.
     */
    protected MessageHandler(final List<String> commands) {
        this.commands = commands;
    }

    /**
     * Получение списка команд.
     *
     * @return Список команд, которые обрабатывает обработчик.
     */
    public List<String> getCommands() {
        return Collections.unmodifiableList(null == commands ? Collections.emptyList() : commands);
    }

    /**
     * Обязательство получения справки по командам обработчика.
     * 
     * @return Справка по командам обработчика.
     */
    public abstract String getHelp();

    /**
     * Может ли обработчик обработать сообщение.
     * 
     * @param message Сообщение целиком
     * @return true, если обработчик может обработать сообщение.
     */
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

    /** Максимальная длина сырого сообщения. */
    public static final int MESSAGE_LENGTH_MAX_RAW = 100;

    /**
     * Проверка сообщения на максимальную длину.
     * 
     * @param rawMessage Сообщение.
     * @return Если сообщение слишком длинное, то вернется сообщение об ошибке.
     */
    protected Optional<String> checkMessageTooLong(String rawMessage) {
        if (rawMessage != null && rawMessage.length() > MESSAGE_LENGTH_MAX_RAW) {
            return Optional.of(MESSAGE_TOO_LONG);
        }
        return Optional.empty();
    }

    /** Минимальная длина минимально очищенного сообщения. */
    public static final int MESSAGE_LENGTH_MIN_TRIMMED = 1;

    /**
     * Проверка сообщения на минимальную длину.
     * 
     * @param message Сообщение.
     * @return Если сообщение слишком короткое, то вернется сообщение об ошибке.
     */
    protected Optional<String> checkMessageTooShort(String message) {
        if (message == null || message.isBlank() || message.isEmpty() ||
                message.length() < MESSAGE_LENGTH_MIN_TRIMMED) {
            return Optional.of(MESSAGE_TOO_SHORT);
        }
        return Optional.empty();
    }

    /**
     * Удаление префикса команды из сообщения.
     * 
     * @param message Сообщение с командой
     * @return Сообщение без префикса команды.
     */
    protected String removeCommand(String message) {
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

    /**
     * Обязательство обработки сообщения.
     * 
     * @param chatId ID чата.
     * @param messageId ID сообщения.
     * @param message Сообщение.
     * @return Ответ на сообщение.
     */
    public abstract String handle(final long chatId, final int messageId,
            final String message);
}
