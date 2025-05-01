package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Ссылка на реализацию {@link ru.spb.tksoft.advertising.api.dynamic.DynamicApiBoolean} равна
 * {@code null}.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public class NullDynamicApiException extends IllegalArgumentException {

    /** Логгер. */
    public static final Logger logger =
            LoggerFactory.getLogger(NullDynamicApiException.class);

    /** Код ошибки. */
    public static final int CODE = 1247;

    /** Сообщение об ошибке. */
    public static final String MESSAGE = "Ссылка на реализацию DynamicApiBoolean равна null";

    /**
     * Конструктор по умолчанию.
     */
    public NullDynamicApiException() {

        super(MESSAGE);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    /**
     * Конструктор с дополнительным сообщением об ошибке.
     * 
     * @param message Дополнительное сообщение об ошибке.
     */
    public NullDynamicApiException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
