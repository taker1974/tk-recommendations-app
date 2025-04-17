package ru.spb.tksoft.advertising.api.dynamic;

import java.util.List;
import java.util.Optional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.spb.tksoft.advertising.api.HistoryService;

/**
 * Динамический API для работы с пользователями. Менеджер динамических методов реализации правил.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public interface DynamicApiManager {

    public record DynamicApiBooleanMethodInfo(
            int argsCount,
            Class<? extends DynamicApiBooleanMethod> methodClass) {
    }

    /**
     * Добавление реализации динамического метода по строке запроса и списку аргументов. Список
     * аргументов передаётся только для его возможной валидации.
     * 
     * @param query - Запрос (имя метода).
     * @param methodInfo - Информация об аргументах и реализации метода.
     */
    void addMethodInstance(@NotBlank String query,
            @NotNull DynamicApiBooleanMethodInfo methodInfo);

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
    Optional<DynamicApiBooleanMethodInfo> getMethodInstance(@NotBlank String query,
            @NotNull List<String> args, @NotNull HistoryService historyService);
}
