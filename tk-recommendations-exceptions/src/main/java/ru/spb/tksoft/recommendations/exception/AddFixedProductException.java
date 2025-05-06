package ru.spb.tksoft.recommendations.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.utils.log.LogEx;

/**
 * Попытка добавления фиксированного продукта.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public class AddFixedProductException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(AddFixedProductException.class);

    /** Код ошибки. */
    public static final int CODE = 6982;

    /** Сообщение об ошибке. */
    public static final String MESSAGE = "Попытка добавления фиксированного продукта";

    /** Конструктор по умолчанию. */
    public AddFixedProductException() {

        super(MESSAGE);
        LogEx.error(log, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    /**
     * Конструктор с дополнительным сообщением об ошибке.
     * 
     * @param message Дополнительное сообщение об ошибке.
     */
    public AddFixedProductException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(log, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
