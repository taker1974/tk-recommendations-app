package ru.spb.tksoft.advertising.api.impl;

import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.api.HistoryProductType;
import ru.spb.tksoft.advertising.api.HistoryService;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiBooleanMethod;
import ru.spb.tksoft.advertising.exception.ArgumentConversionException;

/**
 * Динамический API для работы с пользователями. Реализация ACTIVE_USER_OF.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@RequiredArgsConstructor
public class DynamicActiveUserOfImpl implements DynamicApiBooleanMethod {

    private final HistoryService historyService;

    @Override
    /**
     * Этот запрос проверяет, является ли пользователь, для которого ведется поиск рекомендаций,
     * активным пользователем продукта X, где X — это первый аргумент запроса.
     * 
     * Активный пользователь продукта X — это пользователь, у которого есть хотя бы пять транзакций
     * по продуктам данного типа X.
     * 
     * { "query": "ACTIVE_USER_OF", "arguments": [ "DEBIT" ], "negate": false }
     * 
     * Данный запрос принимает только один аргумент: Первый аргумент — тип продукта: DEBIT, CREDIT,
     * INVEST, SAVING.
     * 
     * @param userId Идентификатор пользователя.
     * @param args Аргументы, которые передаются в правилах.
     * @return true, если пользователь является активным пользователем данного продукта.
     */
    public boolean test(@NotNull UUID userId, @NotNull List<String> args) {

        try {
            HistoryProductType productType = HistoryProductType.valueOf(args.get(0).toUpperCase());
            return historyService.isUsingProduct(userId, productType, true);

        } catch (Exception e) {
            throw new ArgumentConversionException(0);
        }
    }
}
