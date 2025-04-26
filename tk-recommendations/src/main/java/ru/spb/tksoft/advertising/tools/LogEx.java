package ru.spb.tksoft.advertising.tools;

import org.slf4j.Logger;
import org.slf4j.event.Level;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * Extended/wrapped logging.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 * @version 0.1
 */
public final class LogEx {

    public static final String STARTING = "starting";
    public static final String STOPPING = "finishing";
    public static final String STOPPED = "finished";
    public static final String SHORT_RUN = "starting -> finishing";

    public static final String EXCEPTION_THROWN = "exception thrown";

    private LogEx() {}

    public static String getThisMethodName() {

        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        final int currentFrameIndex = 2; // 0 - getStackTrace(), 1 - getCurrentMethodName()

        if (stackTraceElements.length > currentFrameIndex) {
            return stackTraceElements[currentFrameIndex].getMethodName();
        } else {
            throw new IllegalStateException("Call stack too short");
        }
    }

    public static void log(@NotNull Logger logger, @NotNull Level level, @NotNull Object[] parts) {

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

    public static void trace(@NotNull Logger logger, Object... parts) {
        
        log(logger, Level.TRACE, parts);
    }

    public static void debug(@NotNull Logger logger, Object... parts) {
        
        log(logger, Level.DEBUG, parts);
    }

    public static void info(@NotNull Logger logger, Object... parts) {
        
        log(logger, Level.INFO, parts);
    }

    public static void warn(@NotNull Logger logger, Object... parts) {
        
        log(logger, Level.WARN, parts);
    }

    public static void error(@NotNull Logger logger, Object... parts) {

        log(logger, Level.ERROR, parts);
    }
}
