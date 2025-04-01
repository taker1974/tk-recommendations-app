package ru.spb.tksoft.advertising.tools;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.Arrays;

/**
 * Extended/wrapped logging.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 * @version 0.1
 */
public class LogEx {

    public static final String STARTING = "starting";
    public static final String STOPPING = "finishing";
    public static final String STOPPED = "finished";
    public static final String SHORT_RUN = "starting -> finishing";

    public static final String EXCEPTION_THROWN = "exception thrown";

    private LogEx() {}

    public static String getThisMethodName() {

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        int currentFrameIndex = 2; // 0 - getStackTrace(), 1 - getCurrentMethodName()
        if (stackTraceElements.length > currentFrameIndex) {
            return stackTraceElements[currentFrameIndex].getMethodName();
        } else {
            throw new IllegalStateException("Call stack too short");
        }
    }

    public static void log(Logger logger, Level level, Object[] parts) {
        final String[] strings = Arrays.stream(parts).map(String::valueOf).toArray(String[]::new);
        String message = String.join(": ", strings);
        switch (level) {
            case TRACE -> logger.trace(message);
            case DEBUG -> logger.debug(message);
            case INFO -> logger.info(message);
            case WARN -> logger.warn(message);
            case ERROR -> logger.error(message);
        }
    }

    public static void trace(Logger logger, Object... parts) {
        log(logger, Level.TRACE, parts);
    }

    public static void debug(Logger logger, Object... parts) {
        log(logger, Level.DEBUG, parts);
    }

    public static void info(Logger logger, Object... parts) {
        log(logger, Level.INFO, parts);
    }

    public static void warn(Logger logger, Object... parts) {
        log(logger, Level.WARN, parts);
    }

    public static void error(Logger logger, Object... parts) {
        log(logger, Level.ERROR, parts);
    }
}
