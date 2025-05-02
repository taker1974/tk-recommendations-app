package ru.spb.tksoft.advertising.api.dynamic;

import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

/**
 * Динамический API для работы с пользователями.
 * 
 * Интерфейс вызова метода, получающего список параметров в виде строк и возвращающего булевский
 * результат.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@FunctionalInterface
public interface DynamicApiBooleanMethod {

    /**
     * Проверяет, соответствует ли пользователь требованиям.
     * 
     * @param userId Идентификатор пользователя.
     * @param args Список аргументов.
     * @return true, если пользователь соответствует требованиям.
     */
    boolean test(@NotNull UUID userId, @NotNull List<String> args);
}
