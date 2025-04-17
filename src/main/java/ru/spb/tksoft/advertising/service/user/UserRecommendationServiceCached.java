package ru.spb.tksoft.advertising.service.user;

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

/**
 * Кэшированные методы сервиса истории транзакций.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@RequiredArgsConstructor
@ThreadSafe
public class UserRecommendationServiceCached {

    Logger log = LoggerFactory.getLogger(UserRecommendationServiceCached.class);

    @NotNull
    private final HistoryTransactionServiceCached historyTransactionService;

    @NotNull
    private final ProductsRepository recomendationRepository;

    @CacheEvict(value = "recommendations", allEntries = true)
    public void clearCacheAll() {

        clearFitsCache();
        historyTransactionService.cacheClearAll();
    }

    @CacheEvict(value = "isFitsCache", allEntries = true)
    private void clearFitsCache() {
        // ...
    }

    private final Object getRecommendationByNameLock = new Object();

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
     * @param userId Идентификатор пользователя.
     * @return Подходит или нет.
     */
    @Cacheable(value = "isFitsCache", key = "#userId + '-' + 'Invest500'")
    public boolean isFitsInvest500(@NotNull final UUID userId) {

        synchronized (isFitsInvest500Lock) {
            boolean cond1 = historyTransactionService.isUsingProduct(userId,
                    HistoryProductType.DEBIT, false);
            boolean cond2 = !historyTransactionService.isUsingProduct(userId,
                    HistoryProductType.INVEST, false);
            boolean cond3 = historyTransactionService.getProductSum(userId,
                    HistoryProductType.SAVING, HistoryTransactionType.DEPOSIT) > 1000;
            return cond1 && cond2 && cond3;
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
     * @param userId Идентификатор пользователя.
     * @return Подходит или нет.
     */
    @Cacheable(value = "isFitsCache", key = "#userId + '-' + 'TopSaving'")
    public boolean isFitsTopSaving(@NotNull final UUID userId) {

        synchronized (isFitsTopSavingLock) {
            boolean cond1 = historyTransactionService.isUsingProduct(userId,
                    HistoryProductType.DEBIT, false);

            double sumDebit = historyTransactionService.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.DEPOSIT);
            double sumSaving = historyTransactionService.getProductSum(userId,
                    HistoryProductType.SAVING, HistoryTransactionType.DEPOSIT);
            double lim = 50_000;
            boolean cond2 = sumDebit >= lim || sumSaving >= lim;

            double sumDeposit = historyTransactionService.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.DEPOSIT);
            double sumWithdraw = historyTransactionService.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.WITHDRAW);
            boolean cond3 = sumDeposit > sumWithdraw;

            return cond1 && cond2 && cond3;
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
            boolean cond1 = !historyTransactionService.isUsingProduct(userId,
                    HistoryProductType.CREDIT, false);

            double sumDeposit = historyTransactionService.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.DEPOSIT);
            double sumWithdraw = historyTransactionService.getProductSum(userId,
                    HistoryProductType.DEBIT, HistoryTransactionType.WITHDRAW);
            boolean cond2 = sumDeposit > sumWithdraw;
            boolean cond3 = sumWithdraw > 100_000;

            return cond1 && cond2 && cond3;
        }
    }
}
