package ru.spb.tksoft.advertising.api;

import jakarta.validation.constraints.NotBlank;

/**
 * Нормализованный тип транзакции.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public enum HistoryTransactionType {

    DEPOSIT("DEPOSIT"), WITHDRAW("WITHDRAW");

    @NotBlank
    private final String name;

    /**
     * Конструктор.
     * 
     * @param name Имя типа транзакции.
     */
    HistoryTransactionType(@NotBlank String name) {
        this.name = name;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return name;
    }
}

