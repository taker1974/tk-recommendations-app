package ru.spb.tksoft.advertising.service.user;

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
import ru.spb.tksoft.advertising.api.HistoryTransactionType;
import ru.spb.tksoft.advertising.entity.ProductEntity;
import ru.spb.tksoft.advertising.repository.ProductsRepository;
import ru.spb.tksoft.advertising.service.history.HistoryTransactionServiceCached;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Кэшированные фиксированные методы сервиса истории транзакций.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@RequiredArgsConstructor
@ThreadSafe
public class UserRecommendationServiceCached {

    private final Logger log = LoggerFactory.getLogger(UserRecommendationServiceCached.class);

    @NotNull
    private final HistoryTransactionServiceCached historyTransactionServiceCached;

    @NotNull
    private final ProductsRepository recomendationRepository;

    /** Сброс кэшей. */
    @CacheEvict(value = "recommendations", allEntries = true)
    public void clearCaches() {

        clearFitsCache();
        historyTransactionServiceCached.clearCaches();
    }

    @CacheEvict(value = "isFitsCache", allEntries = true)
    private void clearFitsCache() {
        // ...
    }

    /**
     * Получение идентификаторов всех транзакций пользователя.
     * 
     * @return Список идентификаторов.
     */
    public List<UUID> getAllIds() {

        return historyTransactionServiceCached.getAllIds();
    }

    private final Object getRecommendationByNameLock = new Object();

    /**
     * Получение рекомендации по названию.
     * 
     * @param name Название рекомендации.
     * @return Рекомендация, если есть, иначе пустой Optional.
     */
    @Cacheable(value = "recommendations", key = "#name")
    @NotNull
    public Optional<ProductEntity> getRecommendationByName(@NotBlank final String name) {

        synchronized (getRecommendationByNameLock) {
            return recomendationRepository.findProductOnlyByName(name);
        }
    }

    private final Object isFitsInvest500Lock = new Object();

    /**
     * Подходит ли пользовательская активность под предложение "Invest 500": 1. Пользователь
     * использует как минимум один продукт с типом DEBIT. 2. Пользователь не использует продукты с
     * типом INVEST. 3. Сумма пополнений продуктов с типом SAVING больше 1000.
     * 
     * Здесь предпочтение отдано читаемости кода и добавлен ранний возврат результата.
     * 
     * @param userId Идентификатор пользователя.
     * @return Подходит или нет.
     */
    @Cacheable(value = "isFitsCache", key = "#userId + '-' + 'Invest500'")
    public boolean isFitsInvest500(@NotNull final UUID userId) {

        synchronized (isFitsInvest500Lock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, userId);

            boolean fits = historyTransactionServiceCached.isUsingProduct(userId,
                    HistoryProductType.DEBIT, false);
            if (!fits) {
                LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPED, false);
                return false;
            }

            fits = !historyTransactionServiceCached.isUsingProduct(userId,
                    HistoryProductType.INVEST, false);
            if (!fits) {
                LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPED, false);
                return false;
            }

            final boolean result = historyTransactionServiceCached.getProductSum(userId,
                    HistoryProductType.SAVING, HistoryTransactionType.DEPOSIT) > 1000;

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING, result);
            return result;
        }
    }

    private final Object isFitsTopSavingLock = new Object();

    /**
     * Подходит ли пользовательская активность под предложение "Top Saving": 1. Пользователь
     * использует как минимум один продукт с типом DEBIT. 2. Сумма пополнений (DEPOSIT) по всем
     * продуктам типа DEBIT больше или равна 50 000 ИЛИ Сумма пополнений (DEPOSIT) по всем продуктам
     * типа SAVING больше или равна 50 000. 3. Сумма пополнений (DEPOSIT) по всем продуктам типа
     * DEBIT больше, чем сумма трат (WITHDRAW) по всем продуктам типа DEBIT.
     * 
     * Здесь предпочтение отдано читаемости кода и добавлен ранний возврат результата.
     * 
     * @param userId Идентификатор пользователя.
     * @return Подходит или нет.
     */
    @Cacheable(value = "isFitsCache", key = "#userId + '-' + 'TopSaving'")
    public boolean isFitsTopSaving(@NotNull final UUID userId) {

        synchronized (isFitsTopSavingLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, userId);

            boolean fits = historyTransactionServiceCached.isUsingProduct(userId,
                    HistoryProductType.DEBIT, false);
            if (!fits) {
                LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPED, false);
                return false;
            }

            double sumDebit = historyTransactionServiceCached.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.DEPOSIT);
            double sumSaving = historyTransactionServiceCached.getProductSum(userId,
                    HistoryProductType.SAVING, HistoryTransactionType.DEPOSIT);
            double lim = 50_000;
            fits = sumDebit >= lim || sumSaving >= lim;
            if (!fits) {
                LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPED, false);
                return false;
            }

            double sumDeposit = historyTransactionServiceCached.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.DEPOSIT);
            double sumWithdraw = historyTransactionServiceCached.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.WITHDRAW);

            final boolean result = sumDeposit > sumWithdraw;

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING, result);
            return result;
        }
    }

    private final Object isFitsCommonCreditLock = new Object();

    /**
     * Подходит ли пользовательская активность под предложение "Простой кредит": 1. Пользователь не
     * использует продукты с типом CREDIT. 2. Сумма пополнений (DEPOSIT) по всем продуктам типа
     * DEBIT больше, чем сумма (WITHDRAW) трат повсем продуктам типа DEBIT. 3. Сумма трат (WITHDRAW)
     * по всем продуктам типа DEBIT больше, чем 100 000.
     * 
     * @param userId Идентификатор пользователя.
     * @return Подходит или нет.
     */
    @Cacheable(value = "isFitsCache", key = "#userId + '-' + 'CommonCredit'")
    public boolean isFitsCommonCredit(@NotNull final UUID userId) {

        synchronized (isFitsCommonCreditLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, userId);

            boolean fits = !historyTransactionServiceCached.isUsingProduct(userId,
                    HistoryProductType.CREDIT, false);
            if (!fits) {
                LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPED, false);
                return false;
            }

            double sumDeposit = historyTransactionServiceCached.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.DEPOSIT);
            double sumWithdraw = historyTransactionServiceCached.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.WITHDRAW);
            fits = sumDeposit > sumWithdraw;
            if (!fits) {
                LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPED, false);
                return false;
            }

            final boolean result = sumWithdraw > 100_000;

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING, result);
            return result;
        }
    }
}
