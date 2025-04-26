package ru.spb.tksoft.advertising.api.impl;

import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.api.HistoryProductType;
import ru.spb.tksoft.advertising.api.HistoryService;
import ru.spb.tksoft.advertising.api.HistoryTransactionType;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiBooleanMethod;
import ru.spb.tksoft.advertising.api.dynamic.DynamicCompareType;
import ru.spb.tksoft.advertising.exception.ArgumentConversionException;

/**
 * Динамический API для работы с пользователями. Реализация
 * TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@RequiredArgsConstructor
public class DynamicTransactionSumCompareDepositWithdrawImpl implements DynamicApiBooleanMethod {

    private final HistoryService historyService;

    @Override
    /**
     * Этот запрос сравнивает сумму всех транзакций типа DEPOSIT с суммой всех транзакций типа
     * WITHDRAW по продукту X.
     * 
     * Где X — первый аргумент запроса, а операция сравнения — второй аргумент запроса. Таким
     * образом, если запрос выглядит так:
     * 
     * { "query": "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW", "arguments": [ "DEBIT", ">" ],
     * "negate": false }
     * 
     * Это означает, что мы должны посчитать
     * 
     * - сумму всех транзакций с типом DEPOSIT по продуктам типа DEBIT,
     * 
     * - сумму всех транзакций с типом WITHDRAW по продуктам типа DEBIT и
     * 
     * - сравнить их между собой, используя операцию сравнения >.
     * 
     * Данный запрос принимает два аргумента:
     * 
     * Первый аргумент — тип продукта: DEBIT, CREDIT, INVEST, SAVING.
     * 
     * Второй аргумент — тип сравнения (все возможные варианты перечислены выше).
     * 
     * @param userId Идентификатор пользователя.
     * @param args Аргументы, которые передаются в правилах.
     * @return true, если правило выполняется.
     */
    public boolean test(@NotNull UUID userId, @NotNull List<String> args) {

        try {
            var productType = HistoryProductType.valueOf(args.get(0).toUpperCase());
            var compareType = DynamicCompareType.valueOf(args.get(2).toUpperCase());

            double sumDeposit = historyService
                    .getProductSum(userId, productType, HistoryTransactionType.DEPOSIT);
            double sumWithdraw = historyService
                    .getProductSum(userId, productType, HistoryTransactionType.WITHDRAW);

            return DynamicCompareType.doCompare(compareType, sumDeposit, sumWithdraw);

        } catch (Exception e) {
            throw new ArgumentConversionException(0);
        }
    }
}
