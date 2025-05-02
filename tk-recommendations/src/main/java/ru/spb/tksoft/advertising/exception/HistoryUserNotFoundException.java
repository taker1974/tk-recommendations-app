package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Пользователь с указанным uuid не найден в БД транзакций.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public class HistoryUserNotFoundException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(HistoryUserNotFoundException.class);

    /** Код ошибки. */
    public static final int CODE = 3805;

    /** Сообщение об ошибке. */
    public static final String MESSAGE = "Пользователь не найден";

    /** Конструктор по умолчанию. */
    public HistoryUserNotFoundException() {

        super(MESSAGE);
        LogEx.error(log, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }

    /**
     * Конструктор с указанием сообщения об ошибке.
     */
    public HistoryUserNotFoundException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(log, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, CODE, this);
    }
}
