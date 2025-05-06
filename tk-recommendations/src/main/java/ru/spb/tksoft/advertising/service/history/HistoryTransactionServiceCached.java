package ru.spb.tksoft.advertising.service.history;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.api.HistoryProductType;
import ru.spb.tksoft.advertising.api.HistoryService;
import ru.spb.tksoft.advertising.api.HistoryTransactionType;
import ru.spb.tksoft.advertising.entity.history.HistoryTransactionEntity;
import ru.spb.tksoft.advertising.mapper.HistoryMapper;
import ru.spb.tksoft.advertising.model.HistoryUser;
import ru.spb.tksoft.advertising.repository.HistoryTransactionRepository;
import ru.spb.tksoft.utils.log.LogEx;

/**
 * Сервис для работы с историей транзакций: кэшированные методы.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ThreadSafe
@Service
@RequiredArgsConstructor
public class HistoryTransactionServiceCached implements HistoryService {

    private final Logger log = LoggerFactory.getLogger(HistoryTransactionServiceCached.class);

    @NotNull
    private final HistoryTransactionRepository transactionRepository;

    /** Сброс кэшей. */
    @CacheEvict(value = "usage", allEntries = true)
    public void clearCaches() {
        clearSumCache();
    }

    @CacheEvict(value = "sum", allEntries = true)
    private void clearSumCache() {
        // ...
    }

    private final Object getTestTransactionsLock = new Object();

    /**
     * Тестовый метод для получения ограниченного списка транзакций. Служит для проверки работы БД
     * транзакций.
     * 
     * @param limit Количество записей для выборки.
     * @return Список транзакций.
     */
    @NotNull
    public List<HistoryTransactionEntity> getTestTransactions(int limit) {

        synchronized (getTestTransactionsLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN, "limit = ", limit);
            return transactionRepository.getTestTransactions(limit);
        }
    }

    private final Object isUsingProductLock = new Object();

    /**
     * Минимальное количество транзакций, необходимое для определения того, что пользователь активно
     * использует продукт.
     */
    public static final int ACTIVE_PRODUCT_USER_MIN = 5;

    /**
     * Проверяет, использует ли пользователь продукт.
     * 
     * @param isActiveUser Если true, то проверяем на минимальное количество транзакций, иначе -
     *        просто на любое количество.
     * @return true, если пользователь использует продукт, иначе false.
     */
    @Cacheable(value = "usage", key = "#userId + '-' + #productType" + '-' + "#isActiveUser")
    @Override
    public boolean isUsingProduct(
            @NotNull final UUID userId, @NotNull final HistoryProductType productType,
            final boolean isActiveUser) {

        synchronized (isUsingProductLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN,
                    userId, productType);

            final int count = transactionRepository
                    .getProductUsageCount(userId, productType.toString());

            return isActiveUser ? count >= ACTIVE_PRODUCT_USER_MIN : count > 0;
        }
    }

    private final Object getProductSumLock = new Object();

    /**
     * Сумма транзакций заданного типа по указанному типу продукта.
     * 
     * @return Сумма транзакций.
     */
    @Cacheable(value = "sum", key = "#userId + '-' + #productType + '-' + #transactionType")
    @Override
    public double getProductSum(
            @NotNull final UUID userId, @NotNull final HistoryProductType productType,
            @NotNull final HistoryTransactionType transactionType) {

        synchronized (getProductSumLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN,
                    userId, productType, transactionType);

            return transactionRepository
                    .getProductSum(userId, productType.toString(), transactionType.toString());
        }
    }

    private final Object getUserInfoByIdLock = new Object();

    /**
     * Получение информации о пользователе по идентификатору.
     * 
     * @param userId Идентификатор пользователя.
     * @return Информация о пользователе.
     */
    public Optional<HistoryUser> getUserInfo(@NotNull final UUID userId) {

        synchronized (getUserInfoByIdLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN, userId);

            var user = transactionRepository.getUserInfo(userId);
            if (user.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(HistoryMapper.toModel(user.get()));
        }
    }

    private final Object getUserInfoByNameLock = new Object();

    /**
     * Получение информации о пользователе по имени.
     * 
     * @param userName Имя пользователя.
     * @return Информация о пользователе.
     */
    public Optional<HistoryUser> getUserInfo(@NotBlank final String userName) {

        synchronized (getUserInfoByNameLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN, userName);

            var user = transactionRepository.getUserInfo(userName);
            if (user.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(HistoryMapper.toModel(user.get()));
        }
    }

    private final Object getAllIdsLock = new Object();

    /**
     * Служебный/отладочный метод получения всех идентификаторов пользователей.
     * 
     * @return Список идентификаторов пользователей.
     */
    public List<UUID> getAllIds() {

        synchronized (getAllIdsLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN);
            return transactionRepository.getAllIds();
        }
    }
}
