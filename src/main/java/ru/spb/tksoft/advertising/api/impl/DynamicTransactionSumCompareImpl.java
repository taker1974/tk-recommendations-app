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
 * Динамический API для работы с пользователями. Реализация TRANSACTION_SUM_COMPARE.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@RequiredArgsConstructor
public class DynamicTransactionSumCompareImpl implements DynamicApiBooleanMethod {

    private final HistoryService historyService;

    @Override
    /**
     * Этот запрос сравнивает сумму всех транзакций типа Y по продуктам типа X с некоторой
     * константой C. Где X — первый аргумент запроса, Y — второй аргумент запроса, а C — четвертый
     * аргумент запроса. Сама операция сравнения — O — может быть одной из пяти операций:
     * 
     * > — сумма строго больше числа C. < — сумма строго меньше числа C. = — сумма строго равна
     * числу C. >= — сумма больше или равна числу C. <= — сумма меньше или равна числу C.
     * 
     * Таким образом, если запрос выглядит так:
     * 
     * { "query": "TRANSACTION_SUM_COMPARE", "arguments": [ "DEBIT", "DEPOSIT", ">", "100000" ],
     * "negate": false }
     * 
     * Это означает, что мы должны с помощью SQL-запроса посчитать сумму всех транзакций типа
     * DEPOSIT для всех продуктов типа DEBIT данного пользователя и сравнить ее с помощью оператора
     * > с числом 100 000.
     * 
     * Данный запрос принимает четыре аргумента:
     * 
     * Первый аргумент — тип продукта: DEBIT, CREDIT, INVEST, SAVING.
     * 
     * Второй аргумент — тип транзакции: WITHDRAW, DEPOSIT.
     * 
     * Третий аргумент — тип сравнения (все возможные варианты перечислены выше).
     * 
     * Четвертый аргумент — число C, с которым сравниваем сумму, может являться только
     * неотрицательным целым числом (больше либо равным нулю), которое умещается в тип int.
     * 
     * @param userId Идентификатор пользователя.
     * @param args Аргументы, которые передаются в правилах.
     * @return true, если правило выполняется.
     */
    public boolean invoke(@NotNull UUID userId, @NotNull List<String> args) {

        try {
            var productType = HistoryProductType.valueOf(args.get(0).toUpperCase());
            var transactionType = HistoryTransactionType.valueOf(args.get(1).toUpperCase());
            var compareType = DynamicCompareType.valueOf(args.get(2).toUpperCase());
            var limit = Double.parseDouble(args.get(3));

            double sum = historyService.getProductSum(userId, productType, transactionType);
            return DynamicCompareType.doCompare(compareType, sum, limit);

        } catch (Exception e) {
            throw new ArgumentConversionException(0);
        }
    }
}
