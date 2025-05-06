package ru.spb.tksoft.advertising.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.springframework.lang.Nullable;
import com.google.common.collect.ImmutableMap;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.api.HistoryService;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiBooleanMethod;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiManager;

/**
 * Динамический API для работы с пользователями. Реализация менеджера динамических правил.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@RequiredArgsConstructor
public class DynamicApiManagerImpl implements DynamicApiManager {

    /** Имя RMI USER_OF */
    public static final String USER_OF = "USER_OF";
    /** Имя RMI ACTIVE_USER_OF */
    public static final String ACTIVE_USER_OF = "ACTIVE_USER_OF";
    /** Имя RMI TRANSACTION_SUM_COMPARE */
    public static final String TRANSACTION_SUM_COMPARE = "TRANSACTION_SUM_COMPARE";
    /** Имя RMI TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW */
    public static final String TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW =
            "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";

    private static final Map<String, DynamicApiBooleanMethodInfo> METHODS_MAP =
            ImmutableMap.<String, DynamicApiBooleanMethodInfo>builder()
                    .put(USER_OF,
                            new DynamicApiBooleanMethodInfo(1, DynamicUserOfImpl.class))
                    .put(ACTIVE_USER_OF,
                            new DynamicApiBooleanMethodInfo(1, DynamicActiveUserOfImpl.class))
                    .put(TRANSACTION_SUM_COMPARE,
                            new DynamicApiBooleanMethodInfo(4,
                                    DynamicTransactionSumCompareImpl.class))
                    .put(TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW,
                            new DynamicApiBooleanMethodInfo(2,
                                    DynamicTransactionSumCompareDepositWithdrawImpl.class))
                    .build();

    /**
     * Поверхностная проверка корректности запроса на наличие необходимого метода и корректность
     * переданных аргументов.
     * 
     * @param methodName Имя метода.
     * @param args Список аргументов.
     * @return true, если запрошенный метод существует в API.
     */
    public static boolean isMethodValidShallow(@Nullable final String methodName,
            @Nullable final List<String> args) {

        if (methodName == null || methodName.isBlank() || args == null) {
            return false;
        }

        if (!METHODS_MAP.containsKey(methodName)) {
            return false;
        }

        // Это поверхностная проверка, без валидации аргументов.
        return args.size() == METHODS_MAP.get(methodName).argsCount();
    }

    /**
     * Получение нового экземпляра реализации метода по строке запроса и списку аргументов. Список
     * аргументов передаётся только для его возможной валидации.
     * 
     * Возвращается новый экземпляр.
     * 
     * @param query Запрос (имя метода).
     * @param args Список аргументов.
     * @param historyService Сервис истории транзакций.
     * @return Информация о реализации метода, если запрошенный метод существует в API.
     */
    public static Optional<DynamicApiBooleanMethod> newMethodInstance(
            String query, List<String> args, HistoryService historyService) {

        if (query == null || query.isEmpty()) {
            return Optional.empty();
        }

        if (!isMethodValidShallow(query, args)) {
            return Optional.empty();
        }

        try {
            var newInstance = ConstructorUtils.invokeConstructor(
                    METHODS_MAP.get(query).methodClass(), historyService);
            return Optional.of(newInstance);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @NotNull
    private final Map<String, DynamicApiBooleanMethodInfo> methods = new HashMap<>();

    /**
     * Добавление реализации динамического метода по строке запроса и списку аргументов. Список
     * аргументов передаётся только для его возможной валидации.
     * 
     * @param query Запрос (имя метода).
     * @param methodInfo Информация об аргументах и реализации метода.
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
     * @param query Запрос (имя метода).
     * @param args Список аргументов.
     * @param historyService Сервис истории транзакций.
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
