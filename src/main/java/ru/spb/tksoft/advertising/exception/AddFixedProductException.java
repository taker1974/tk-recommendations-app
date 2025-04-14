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

    public static final Logger logger = LoggerFactory.getLogger(AddFixedProductException.class);

    public static final int CODE = 6982;

    public static final String MESSAGE = "Попытка добавления фиксированного продукта";

    public AddFixedProductException() {

        super(MESSAGE);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    public AddFixedProductException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
