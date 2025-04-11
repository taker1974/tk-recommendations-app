package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Ошибка идентификации метода.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public class MethodIdentificationException extends IllegalArgumentException {

    public static final Logger logger =
            LoggerFactory.getLogger(MethodIdentificationException.class);

    public static final int CODE = 9186;

    public static final String MESSAGE = "Ошибка идентификации метода";

    public MethodIdentificationException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    public MethodIdentificationException() {

        super(MESSAGE);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
