package ru.spb.tksoft.advertising.handler;

import java.util.List;

/**
 * Менеджер обработчиков сообщений.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public class MessageManager {

    /** Список обработчиков сообщений. */
    private final List<MessageHandler> handlers;

    /** Обработчик по умолчанию. Это ссылка на один из обработчиков из списка. */
    private final MessageHandler defaultHandler;

    /**
     * Конструктор.
     * 
     * @param handlers Список обработчиков сообщений.
     * @param defaultHandler Обработчик по умолчанию.
     */
    public MessageManager(List<MessageHandler> handlers,
            MessageHandler defaultHandler) {

        if (handlers == null || handlers.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.handlers = handlers;
        this.defaultHandler = defaultHandler;
    }

    /**
     * Возвращает текст помощи для всех обработчиков.
     * 
     * @return Текст помощи.
     */
    public String getHelp() {

        var sb = new StringBuilder();

        final int count = handlers.size();
        final int last = count - 1;

        for (int i = 0; i < count; i++) {
            sb.append(handlers.get(i).getHelp())
                    .append(i < last ? ";\n" : ".");
        }
        return sb.toString();
    }

    /**
     * Акт управления сообщением через вызов обработчиков сообщений.
     * 
     * @param chatId Идентификатор чата.
     * @param messageId Идентификатор сообщения.
     * @param message Сообщение.
     * @return Результат выполнения обработчика сообщения.
     */
    public String manage(final long chatId, final int messageId, final String message) {

        for (var handler : handlers) {
            if (handler == defaultHandler) {
                continue;
            }

            if (handler.canHandle(message)) {
                return handler.handle(chatId, messageId, message);
            }
        }

        // Обработчик по умолчанию вызываем последним.
        return defaultHandler.handle(chatId, messageId, message);
    }
}
