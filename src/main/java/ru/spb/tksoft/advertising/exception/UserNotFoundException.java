package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.spb.tksoft.advertising.tools.LogEx;

public class UserNotFoundException extends RuntimeException {

    public static final Logger logger = LoggerFactory.getLogger(UserNotFoundException.class);

    public static final int CODE = 3805;

    public UserNotFoundException() {

        super("Пользователь не найден");

        LogEx.error(logger, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN, "CODE = " + CODE, this);
    }
}
