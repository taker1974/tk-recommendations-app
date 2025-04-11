package ru.spb.tksoft.advertising.service.history;

import java.util.List;
import java.util.UUID;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.api.HistoryProductType;
import ru.spb.tksoft.advertising.api.HistoryService;
import ru.spb.tksoft.advertising.api.HistoryTransactionType;
import ru.spb.tksoft.advertising.entity.history.HistoryTransaction;
import ru.spb.tksoft.advertising.repository.HistoryTransactionRepository;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Сервис для работы с историей транзакций: кэшированные методы.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
@Service
@RequiredArgsConstructor
public class HistoryTransactionServiceCached implements HistoryService {

    private final Logger log = LoggerFactory.getLogger(HistoryTransactionServiceCached.class);

    @NotNull
    private final HistoryTransactionRepository transactionRepository;

    @CacheEvict(value = "usage", allEntries = true)
    public void cacheClearAll() {
        cacheClearSum();
    }

    @CacheEvict(value = "sum", allEntries = true)
    private void cacheClearSum() {
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
    public List<HistoryTransaction> getTestTransactions(int limit) {

        synchronized (getTestTransactionsLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN, "limit = ", limit);
            return transactionRepository.getTestTransactions(limit);
        }
    }

    private final Object isUsingProductLock = new Object();

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
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN, "userId = ", userId,
                    "productType = ", productType);

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
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN, "userId = ", userId,
                    "productType = ", productType, "transactionType = ", transactionType);

            return transactionRepository
                    .getProductSum(userId, productType.toString(), transactionType.toString());
        }
    }
}
