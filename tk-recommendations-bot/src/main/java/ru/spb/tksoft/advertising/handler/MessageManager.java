package ru.spb.tksoft.advertising.handler;

import java.util.List;

public class MessageManager {

    private final List<MessageHandler> handlers;
    private final MessageHandler defaultHandler;

    public MessageManager(List<MessageHandler> handlers,
            MessageHandler defaultHandler) {

        if (handlers == null || handlers.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.handlers = handlers;
        this.defaultHandler = defaultHandler;
    }

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
