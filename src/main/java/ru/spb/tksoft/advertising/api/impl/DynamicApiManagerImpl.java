package ru.spb.tksoft.advertising.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.api.HistoryService;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiManager;

/**
 * Динамический API для работы с пользователями. Реализация менеджера динамических правил.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@RequiredArgsConstructor
public class DynamicApiManagerImpl implements DynamicApiManager {

    /**
     * Получение нового экземпляра реализации метода по строке запроса и списку аргументов. Список
     * аргументов передаётся только для его возможной валидации.
     * 
     * Возвращается новый экземпляр.
     * 
     * @param query - Запрос (имя метода).
     * @param args - Список аргументов.
     * @param historyService - Сервис истории транзакций.
     * @return Информация о реализации метода, если запрошенный метод существует в API.
     */
    public static Optional<DynamicApiBooleanMethodInfo> newMethodInstance(
            String query, List<String> args, HistoryService historyService) {

        if (query == null || query.isEmpty()) {
            return Optional.empty();
        }

        int argsSize = args.size();

        if (query.equals("USER_OF")) {
            if (argsSize != 1)
                return Optional.empty();
            return Optional.of(new DynamicApiBooleanMethodInfo(query, argsSize,
                    new DynamicUserOfImpl(historyService)));
        }

        if (query.equals("ACTIVE_USER_OF")) {
            if (argsSize != 1)
                return Optional.empty();
            return Optional.of(new DynamicApiBooleanMethodInfo(query, argsSize,
                    new DynamicActiveUserOfImpl(historyService)));
        }

        if (query.equals("TRANSACTION_SUM_COMPARE")) {
            if (argsSize != 4)
                return Optional.empty();
            return Optional.of(new DynamicApiBooleanMethodInfo(query, argsSize,
                    new DynamicTransactionSumCompareImpl(historyService)));
        }

        if (query.equals("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")) {
            if (argsSize != 4)
                return Optional.empty();
            return Optional.of(new DynamicApiBooleanMethodInfo(query, argsSize,
                    new DynamicTransactionSumCompareDepositWithdrawImpl(historyService)));
        }

        return Optional.empty();
    }

    @NotNull
    private final Map<String, DynamicApiBooleanMethodInfo> methods = new HashMap<>();

    /**
     * Добавление реализации динамического метода по строке запроса и списку аргументов. Список
     * аргументов передаётся только для его возможной валидации.
     * 
     * @param query - Запрос (имя метода).
     * @param methodInfo - Информация об аргументах и реализации метода.
     */
    public void addMethodInstance(@NotBlank String query,
            @NotNull DynamicApiBooleanMethodInfo methodInfo) {

        methods.put(query, methodInfo);
    }

    @Override
    /**
     * Получение уже добавленной реализации метода по строке запроса и списку аргументов. Список
     * аргументов передаётся только для его возможной валидации.
     * 
     * Возвращается уже существующий экземпляр реализации класса! Для использования необходимо
     * убедиться в потокобезопасности решения!
     * 
     * @param query - Запрос (имя метода).
     * @param args - Список аргументов.
     * @param historyService - Сервис истории транзакций.
     * @return Информация о реализации метода, если запрошенный метод существует в API.
     */
    public Optional<DynamicApiBooleanMethodInfo> getMethodInstance(@NotBlank String query,
            @NotNull List<String> args, @NotNull HistoryService historyService) {

        if (methods.containsKey(query)) {
            return Optional.of(methods.get(query));
        }
        return Optional.empty();
    }
}
