package ru.spb.tksoft.advertising.dto;

import jakarta.annotation.Nullable;

public record ErrorResponseDto(int code, String message, @Nullable String details) {

    public ErrorResponseDto(int code, String message) {
        this(code, message, "");
    }
}
