package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Пользователь с указанным uuid не найден в БД транзакций.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public class HistoryUserNotFoundException extends RuntimeException {

    public static final Logger logger = LoggerFactory.getLogger(HistoryUserNotFoundException.class);

    public static final int CODE = 3805;

    public static final String MESSAGE = "Пользователь не найден";

    public HistoryUserNotFoundException() {

        super(MESSAGE);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    public HistoryUserNotFoundException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
