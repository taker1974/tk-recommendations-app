package ru.spb.tksoft.advertising.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.history.HistoryTransaction;
import ru.spb.tksoft.advertising.repository.TransactionRepository;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Сервис для работы с историей транзакций.
 * 
 * @see TransactionRepository
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@RequiredArgsConstructor
public class HistoryTransactionService {

    private final Logger log = LoggerFactory.getLogger(HistoryTransactionService.class);

    private final TransactionRepository transactionRepository;

    public List<HistoryTransaction> getTestTransactions(int limit) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN, "limit = ", limit);
        return transactionRepository.getTestTransactions(limit);
    }

    @CacheEvict(value = "usageCache", allEntries = true)
    public void clearCacheAll() {
        clearSumCache();
    }

    public enum ProductType {
        DEBIT("DEBIT"), CREDIT("CREDIT"), SAVING("SAVING"), INVEST("INVEST");

        private final String name;

        ProductType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Cacheable(value = "usageCache", key = "#userId + '-' + #productType")
    public boolean isUsingProduct(final UUID userId,
            final ProductType productType) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN,
                "userId = ", userId, "productType = ", productType);

        return transactionRepository.getProductUsageCount(userId, productType.toString()) > 0;
    }

    public enum TransactionType {
        DEPOSIT("DEPOSIT"), WITHDRAW("WITHDRAW");

        private final String name;

        TransactionType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @CacheEvict(value = "sumCache", allEntries = true)
    private void clearSumCache() {
        // ...
    }

    @Cacheable(value = "sumCache", key = "#userId + '-' + #productType + '-' + #transactionType")
    public double getProductSum(final UUID userId, final ProductType productType,
            final TransactionType transactionType) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN,
                "userId = ", userId, "productType = ", productType, "transactionType = ",
                transactionType);

        return transactionRepository.getProductSum(userId, productType.toString(),
                transactionType.toString());
    }
}
