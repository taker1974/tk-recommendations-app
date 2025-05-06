package ru.spb.tksoft.advertising.api;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

/**
 * Интерфейс сервис для работы с историей транзакций.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public interface HistoryService {

    /**
     * Проверяет, использует ли пользователь продукт.
     * 
     * @param isActiveUser Если true, то проверяем на минимальное количество транзакций, иначе -
     *        просто на любое количество.
     * @return true, если пользователь использует продукт, иначе false.
     */
    boolean isUsingProduct(@NotNull UUID userId,
            @NotNull HistoryProductType productType, boolean isActiveUser);

    /**
     * Сумма транзакций заданного типа по указанному типу продукта.
     * 
     * @return Сумма транзакций.
     */
    double getProductSum(@NotNull UUID userId,
            @NotNull HistoryProductType productType,
            @NotNull HistoryTransactionType transactionType);
}
