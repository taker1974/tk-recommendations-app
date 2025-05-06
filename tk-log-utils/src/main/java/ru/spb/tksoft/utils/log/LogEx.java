package ru.spb.tksoft.utils.log;

import org.slf4j.Logger;
import org.slf4j.event.Level;
import java.util.Arrays;

/**
 * Extended/wrapped logging.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public final class LogEx {

    /** Common phrase 'starting'. */
    public static final String STARTING = "starting";
    /** Common phrase 'finishing'. */
    public static final String STOPPING = "finishing";
    /** Common phrase 'finished'. */
    public static final String STOPPED = "finished";
    /** Common phrase 'starting -> finishing'. */
    public static final String SHORT_RUN = "starting -> finishing";

    /** Common phrase 'exception thrown'. */
    public static final String EXCEPTION_THROWN = "exception thrown";

    private LogEx() {}

    /**
     * Get the current method name.
     * 
     * @return The name of the method that called this method.
     */
    public static String getThisMethodName() {

        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        final int currentFrameIndex = 2; // 0 - getStackTrace(), 1 - getCurrentMethodName()

        if (stackTraceElements.length > currentFrameIndex) {
            return stackTraceElements[currentFrameIndex].getMethodName();
        } else {
            throw new IllegalStateException("Call stack too short");
        }
    }

    /**
     * Log the current method.
     * 
     * @param logger The logger.
     * @param level The logging level.
     * @param parts The message parts.
     */
    public static void log(Logger logger, Level level, Object[] parts) {

        final String[] strings = Arrays.stream(parts)
                .map(String::valueOf)
                .toArray(String[]::new);

        final String message = String.join(": ", strings);
        switch (level) {
            case TRACE -> logger.trace(message);
            case DEBUG -> logger.debug(message);
            case INFO -> logger.info(message);
            case WARN -> logger.warn(message);
            case ERROR -> logger.error(message);
        }
    }

    /**
     * Log the current method with 'trace' severity
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void trace(Logger logger, Object... parts) {

        log(logger, Level.TRACE, parts);
    }

    /**
     * Log the current method with 'debug' severity.
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void debug(Logger logger, Object... parts) {

        log(logger, Level.DEBUG, parts);
    }

    /**
     * Log the current method with 'info' severity.
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void info(Logger logger, Object... parts) {

        log(logger, Level.INFO, parts);
    }

    /**
     * Log the current method with 'warn' severity.
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void warn(Logger logger, Object... parts) {

        log(logger, Level.WARN, parts);
    }

    /**
     * Log the current method with 'error' severity.
     * 
     * @param logger The logger.
     * @param parts The message parts.
     */
    public static void error(Logger logger, Object... parts) {

        log(logger, Level.ERROR, parts);
    }
}
