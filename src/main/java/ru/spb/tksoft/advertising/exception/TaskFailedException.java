package ru.spb.tksoft.advertising.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Задача - FutureTask или подобная - не была выполнена из-за ошибки.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public class TaskFailedException extends RuntimeException {

    public static final Logger logger = LoggerFactory.getLogger(TaskFailedException.class);

    public static final int CODE = 2563;

    public TaskFailedException() {

        super("Задача не выполнена");

        LogEx.error(logger, LogEx.getThisMethodName(),
                LogEx.EXCEPTION_THROWN, "CODE = " + CODE, this);
    }
}
