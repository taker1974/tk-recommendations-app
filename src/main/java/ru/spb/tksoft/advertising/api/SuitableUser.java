package ru.spb.tksoft.advertising.api;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

/**
 * Проверка соответствия пользователя требованиям.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@FunctionalInterface
public interface SuitableUser {

    /**
     * Проверяет, соответствует ли пользователь требованиям. Это могут быть требования по
     * вызываемому методу или их совокупности
     * 
     * @param userId Идентификатор пользователя.
     * @return true, если пользователь соответствует требованиям.
     */
    boolean isUserSuitable(@NotNull UUID userId);
}
