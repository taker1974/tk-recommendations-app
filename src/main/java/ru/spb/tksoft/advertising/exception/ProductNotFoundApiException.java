package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Продукт не найден.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public class ProductNotFoundApiException extends IllegalArgumentException {

    public static final Logger logger =
            LoggerFactory.getLogger(ProductNotFoundApiException.class);

    public static final int CODE = 6463;

    public static final String MESSAGE = "Продукт не найден";

    public ProductNotFoundApiException() {

        super(MESSAGE);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    public ProductNotFoundApiException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
