package ru.spb.tksoft.advertising.service.recommendation;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.recommendation.Recommendation;
import ru.spb.tksoft.advertising.entity.recommendation.RecommendationsDto;
import ru.spb.tksoft.advertising.repository.recommendation.RecommendationRepository;
import ru.spb.tksoft.advertising.service.transaction.TransactionService;
import ru.spb.tksoft.advertising.service.transaction.TransactionService.ProductType;
import ru.spb.tksoft.advertising.service.transaction.TransactionService.TransactionType;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationRepository recomendationRepository;
    private final TransactionService transactionService;

    public RecommendationsDto getRecommendations(UUID userId) {

        List<Recommendation> recommendations = recomendationRepository.findAllRecommendations();
        var result = new RecommendationsDto(userId, recommendations);
        return result;
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
    @Cacheable(value = "isFitsInvest500Cache", key = "#userId")
    private boolean isFitsInvest500(final UUID userId) {

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
    @Cacheable(value = "isFitsTopSavingsCache", key = "#userId")
    private boolean isFitsTopSaving(final UUID userId) {
        return true;
    }

    @Cacheable(value = "isFitsCommonCreditCache", key = "#userId")
    private boolean isFitsCommonCredit(final UUID userId) {
        return true;
    }
}
