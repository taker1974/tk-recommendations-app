package ru.spb.tksoft.recommendations.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.recommendations.tools.LogEx;

/**
 * Продукт не найден.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public class ProductNotFoundApiException extends IllegalArgumentException {

    /** Логгер. */
    public static final Logger logger =
            LoggerFactory.getLogger(ProductNotFoundApiException.class);

    /** Код ошибки. */
    public static final int CODE = 6463;

    /** Сообщение об ошибке. */
    public static final String MESSAGE = "Продукт не найден";

    /**
     * Конструктор по умолчанию.
     */
    public ProductNotFoundApiException() {

        super(MESSAGE);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    /**
     * Конструктор с дополнительным сообщением.
     * 
     * @param message Дополнительное сообщение об ошибке.
     */
    public ProductNotFoundApiException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
