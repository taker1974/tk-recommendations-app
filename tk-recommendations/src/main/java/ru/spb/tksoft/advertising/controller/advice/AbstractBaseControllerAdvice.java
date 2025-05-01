package ru.spb.tksoft.advertising.controller.advice;

import jakarta.validation.constraints.NotNull;

/**
 * Базовый класс для перехвата исключений на уровне контроллера.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public abstract class AbstractBaseControllerAdvice {

    /** Префикс сообщения для всех перехваченных исключений. */
    public static final String MESSAGE_PREFIX = "Перехвачено исключение";

    /**
     * Возвращает сообщение по умолчанию для перехваченного исключения.
     * 
     * @param obj Любой объект, имя которого нужно укзать в тексте описания исключения
     * @return Стандартное сообщение по умолчанию для перехваченного исключения.
     */
    protected String getCommonMessage(@NotNull Object obj) {

        return String.format("%s %s", MESSAGE_PREFIX, obj.getClass().getSimpleName());
    }
}
