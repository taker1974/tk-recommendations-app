package ru.spb.tksoft.recommendations.dto.service;

import jakarta.validation.constraints.NotBlank;

/**
 * Описание ошибки, возвращаемое контроллером.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public record ServiceErrorResponseDto(int code, String message, String details) {

    public ServiceErrorResponseDto(int code, @NotBlank String message) {

        this(code, message, "");
    }
}
