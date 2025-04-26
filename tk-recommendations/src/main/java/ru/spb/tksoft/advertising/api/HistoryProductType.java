package ru.spb.tksoft.advertising.api;

import jakarta.validation.constraints.NotBlank;

/**
 * Нормализованный тип продукта.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public enum HistoryProductType {
    DEBIT("DEBIT"), CREDIT("CREDIT"), SAVING("SAVING"), INVEST("INVEST");

    @NotBlank
    private final String name;

    HistoryProductType(@NotBlank String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

