package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.utils.log.LogEx;

/**
 * Ошибка преобразования аргумента метода.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public class ArgumentConversionException extends IllegalArgumentException {

    private static final Logger log = LoggerFactory.getLogger(ArgumentConversionException.class);

    /** Код ошибки. */
    public static final int CODE = 6582;

    /** Сообщение об ошибке. */
    public static final String MESSAGE = "Ошибка преобразования аргумента";

    /**
     * Конструктор с сообщением.
     * 
     * @param message Дополнительное сообщение об ошибке.
     */
    public ArgumentConversionException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(log, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    /**
     * Конструктор с указанием аргумента, который не удалось преобразовать и типа преобразования.
     * 
     * @param argument Аргумент, который не удалось преобразовать.
     * @param toType Тип преобразования.
     */
    public ArgumentConversionException(@NotBlank final String argument,
            @NotBlank final String toType) {

        super(MESSAGE + " " + argument + " -> " + toType);
        LogEx.error(log, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    /**
     * Конструктор с указанием индекса аргумента, который не удалось преобразовать и типа
     * преобразования.
     * 
     * @param argumentIndex Индекс аргумента, который не удалось преобразовать.
     * @param toType Тип преобразования.
     */
    public ArgumentConversionException(final int argumentIndex, @NotBlank final String toType) {

        super(MESSAGE + " " + argumentIndex + " -> " + toType);
        LogEx.error(log, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    /**
     * Конструктор с указанием индекса аргумента, который не удалось преобразовать.
     * 
     * @param argumentIndex Индекс аргумента, который не удалось преобразовать.
     */
    public ArgumentConversionException(final int argumentIndex) {

        super(MESSAGE + " " + argumentIndex);
        LogEx.error(log, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
