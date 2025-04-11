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
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@FunctionalInterface
public interface DynamicApiBooleanMethod {

    boolean invoke(@NotNull UUID userId, @NotNull List<String> args);
}
