package ru.spb.tksoft.advertising.api.dynamic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Нормализованный тип сравнения.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public enum DynamicCompareType {
    LT("<"), LE("<="), EQ("="), GE(">="), GT(">");

    @NotBlank
    private final String name;

    DynamicCompareType(@NotBlank String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean doCompare(@NotNull DynamicCompareType compareType,
            double d1, double d2) {

        return switch (compareType) {
            case LT -> d1 < d2;
            case LE -> d1 <= d2;
            case EQ -> d1 == d2;
            case GE -> d1 >= d2;
            case GT -> d1 > d2;
            default -> false;
        };
    }
}

