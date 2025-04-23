package ru.spb.tksoft.advertising.handler;

import java.util.Collection;

public class MessageManager {

    private final Collection<MessageHandler> handlers;
    private final MessageHandler defaultHandler;

    public MessageManager(Collection<MessageHandler> handlers,
            MessageHandler defaultHandler) {

        if (handlers == null || handlers.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.handlers = handlers;
        this.defaultHandler = defaultHandler;
    }

    public String getHelp() {
        var sb = new StringBuilder();
        
        sb.append(defaultHandler.getHelp());
        sb.append("\n");

        for (var handler : handlers) {
            sb.append(handler.getHelp());
            sb.append("\n");
        }
        return sb.toString();
    }

    public String manage(final long chatId, final int messageId, final String message) {

        for (var handler : handlers) {
            if (handler.canHandle(message)) {
                return handler.handle(chatId, messageId, message);
            }
        }

        return defaultHandler.handle(chatId, messageId, message);
    }
}
