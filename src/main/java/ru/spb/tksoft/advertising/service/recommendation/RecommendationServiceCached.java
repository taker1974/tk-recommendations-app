package ru.spb.tksoft.advertising.service.recommendation;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.recommendation.Recommendation;
import ru.spb.tksoft.advertising.repository.recommendation.RecommendationRepository;
import ru.spb.tksoft.advertising.service.transaction.TransactionService;
import ru.spb.tksoft.advertising.service.transaction.TransactionService.ProductType;
import ru.spb.tksoft.advertising.service.transaction.TransactionService.TransactionType;

@Service
@RequiredArgsConstructor
public class RecommendationServiceCached {

    Logger log = LoggerFactory.getLogger(RecommendationServiceCached.class);

    private final RecommendationRepository recomendationRepository;
    private final TransactionService transactionService;

    @Cacheable(value = "recommendationCache", key = "#name")
    public Optional<Recommendation> getRecommendationByName(final String name) {
        return recomendationRepository.findRecommendationByName(name);
    }

    @CacheEvict(value = "recommendationCache", allEntries = true)
    public void clearCacheAll() {
        clearFitsCache();
        transactionService.clearCacheAll();
    }

    @CacheEvict(value = "isFitsCache", allEntries = true)
    private void clearFitsCache() {
        // ...
    }

    /**
     * Подходит ли пользовательская активность под предложение "Invest 500":
     * 1. Пользователь использует как минимум один продукт с типом DEBIT.
     * 2. Пользователь не использует продукты с типом INVEST.
     * 3. Сумма пополнений продуктов с типом SAVING больше 1000.
     * 
     * @param userId
     * @return подходит или нет
     */
    @Cacheable(value = "isFitsCache", key = "#userId + '-' + 'Invest500'")
    public boolean isFitsInvest500(final UUID userId) {

        return transactionService.isUsingProduct(userId, ProductType.DEBIT) &&
                !transactionService.isUsingProduct(userId, ProductType.INVEST) &&
                transactionService.getProductSum(userId, ProductType.SAVING, TransactionType.DEPOSIT) > 1000;
    }

    /**
     * Подходит ли пользовательская активность под предложение "Top Saving":
     * 1. Пользователь использует как минимум один продукт с типом DEBIT.
     * 2. Сумма пополнений (DEPOSIT) по всем продуктам типа DEBIT
     * больше или равна 50 000
     * ИЛИ
     * Сумма пополнений (DEPOSIT) по всем продуктам типа SAVING
     * больше или равна 50 000.
     * 3. Сумма пополнений (DEPOSIT) по всем продуктам типа DEBIT
     * больше, чем сумма трат (WITHDRAW) по всем продуктам типа DEBIT.
     * 
     * @param userId
     * @return подходит или нет
     */
    @Cacheable(value = "isFitsCache", key = "#userId + '-' + 'TopSaving'")
    public boolean isFitsTopSaving(final UUID userId) {

        boolean cond1 = transactionService.isUsingProduct(userId, ProductType.DEBIT);

        double sumDebit = transactionService.getProductSum(userId, ProductType.DEBIT, TransactionType.DEPOSIT);
        double sumSaving = transactionService.getProductSum(userId, ProductType.SAVING, TransactionType.DEPOSIT);
        double lim = 50_000;
        boolean cond2 = sumDebit >= lim || sumSaving >= lim;

        double sumDeposit = transactionService.getProductSum(userId, ProductType.DEBIT, TransactionType.DEPOSIT);
        double sumWithdraw = transactionService.getProductSum(userId, ProductType.DEBIT, TransactionType.WITHDRAW);
        boolean cond3 = sumDeposit > sumWithdraw;

        return cond1 && cond2 && cond3;
    }

    /**
     * Подходит ли пользовательская активность под предложение "Простой кредит":
     * 1. Пользователь не использует продукты с типом CREDIT.
     * 2. Сумма пополнений (DEPOSIT) по всем продуктам типа DEBIT больше,
     * чем сумма (WITHDRAW) трат повсем продуктам типа DEBIT.
     * 3. Сумма трат (WITHDRAW) по всем продуктам типа DEBIT больше, чем 100 000.
     * 
     * @param userId
     * @return подходит или нет
     */
    @Cacheable(value = "isFitsCache", key = "#userId + '-' + 'CommonCredit'")
    public boolean isFitsCommonCredit(final UUID userId) {

        boolean cond1 = !transactionService.isUsingProduct(userId, ProductType.CREDIT);

        double sumDeposit = transactionService.getProductSum(userId, ProductType.DEBIT, TransactionType.DEPOSIT);
        double sumWithdraw = transactionService.getProductSum(userId, ProductType.DEBIT, TransactionType.WITHDRAW);
        boolean cond2 = sumDeposit > sumWithdraw;

        boolean cond3 = sumWithdraw > 100_000;

        return cond1 && cond2 && cond3;
    }
}
