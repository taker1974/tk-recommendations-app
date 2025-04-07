package ru.spb.tksoft.advertising.dto.service;

import jakarta.annotation.Nullable;

/**
 * Описание ошибки, возвращаемое контроллером.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public record ServiceErrorResponseDto(int code, String message, @Nullable String details) {

    public ServiceErrorResponseDto(int code, String message) {
        this(code, message, "");
    }
}
