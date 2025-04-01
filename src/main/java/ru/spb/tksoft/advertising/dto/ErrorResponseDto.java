package ru.spb.tksoft.advertising.dto;

import jakarta.annotation.Nullable;

/**
 * Описание ошибки, возвращаемое контроллером.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public record ErrorResponseDto(int code, String message, @Nullable String details) {

    public ErrorResponseDto(int code, String message) {
        this(code, message, "");
    }
}
