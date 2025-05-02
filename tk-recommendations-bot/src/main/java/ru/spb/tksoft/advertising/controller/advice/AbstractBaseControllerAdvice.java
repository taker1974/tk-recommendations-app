package ru.spb.tksoft.advertising.controller.advice;

import jakarta.validation.constraints.NotNull;

/**
 * Базовый класс для перехвата исключений на уровне контроллера.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public abstract class AbstractBaseControllerAdvice {

    public static final String MESSAGE_PREFIX = "Перехвачено исключение";

    protected String getCommonMessage(@NotNull Object obj) {

        return String.format("%s %s", MESSAGE_PREFIX, obj.getClass().getSimpleName());
    }
}
