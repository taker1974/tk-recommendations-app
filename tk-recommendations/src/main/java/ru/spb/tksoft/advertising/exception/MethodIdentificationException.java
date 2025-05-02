package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Ошибка идентификации метода.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public class MethodIdentificationException extends IllegalArgumentException {

    /** Логгер. */
    public static final Logger logger =
            LoggerFactory.getLogger(MethodIdentificationException.class);

    /** Код ошибки. */
    public static final int CODE = 9186;

    /** Сообщение об ошибке. */
    public static final String MESSAGE = "Ошибка идентификации метода";

    /**
     * Конструктор по умолчанию.
     */
    public MethodIdentificationException() {

        super(MESSAGE);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    /**
     * Конструктор с дополнительным сообщением.
     * 
     * @param message Дополнительное сообщение об ошибке
     */
    public MethodIdentificationException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
