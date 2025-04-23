package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotBlank;
import ru.spb.tksoft.advertising.tools.LogEx;

public class UserNotFoundException extends RuntimeException {

    public static final Logger logger = LoggerFactory
            .getLogger(UserNotFoundException.class);

    public static final int CODE = 3121;

    public static final String MESSAGE = "Пользователь не найден";

    public UserNotFoundException() {

        super(MESSAGE);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, this);
    }

    public UserNotFoundException(@NotBlank final String message) {

        super(MESSAGE + ": " + message);
        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, this);
    }
}
