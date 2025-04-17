package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Ошибка преобразования аргумента метода.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public class ArgumentConversionException extends IllegalArgumentException {

    public static final Logger logger = LoggerFactory.getLogger(ArgumentConversionException.class);

    public static final int CODE = 6582;

    public static final String MESSAGE = "Ошибка преобразования аргумента";

    public ArgumentConversionException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    public ArgumentConversionException(@NotBlank final String argument,
            @NotBlank final String toType) {

        super(MESSAGE + " " + argument + " -> " + toType);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    public ArgumentConversionException(final int argumentIndex, @NotBlank final String toType) {

        super(MESSAGE + " " + argumentIndex + " -> " + toType);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    public ArgumentConversionException(final int argumentIndex) {

        super(MESSAGE + " " + argumentIndex);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
