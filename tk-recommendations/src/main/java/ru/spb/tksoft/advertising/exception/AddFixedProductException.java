package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Попытка добавления фиксированного продукта.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public class AddFixedProductException extends RuntimeException {

    /** Логгер. */
    public static final Logger logger = LoggerFactory.getLogger(AddFixedProductException.class);

    /** Код ошибки. */
    public static final int CODE = 6982;

    /** Сообщение об ошибке. */
    public static final String MESSAGE = "Попытка добавления фиксированного продукта";

    /** Конструктор по умолчанию. */
    public AddFixedProductException() {

        super(MESSAGE);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    /**
     * Конструктор с дополнительным сообщением об ошибке.
     */
    public AddFixedProductException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
