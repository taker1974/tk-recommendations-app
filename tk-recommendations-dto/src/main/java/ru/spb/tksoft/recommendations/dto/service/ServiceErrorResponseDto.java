package ru.spb.tksoft.recommendations.dto.service;

import jakarta.validation.constraints.NotBlank;

/**
 * Описание ошибки, возвращаемое контроллером.
 * 
 * @param code Код ошибки.
 * @param message Описание ошибки.
 * @param details Дополнительная информация об ошибке.
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public record ServiceErrorResponseDto(int code, String message, String details) {

    public ServiceErrorResponseDto(int code, @NotBlank String message) {

        this(code, message, "");
    }
}
