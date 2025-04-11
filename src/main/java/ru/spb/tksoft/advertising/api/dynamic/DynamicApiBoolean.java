package ru.spb.tksoft.advertising.api.dynamic;

import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Динамический API для работы с пользователями. Идея в том, что в метод передаётся напрямую то, что
 * хранится в правилах.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@FunctionalInterface
public interface DynamicApiBoolean {

    boolean invoke(@NotNull UUID userId, @NotBlank String query, @NotNull List<String> args);
}
