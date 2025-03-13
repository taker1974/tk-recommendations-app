package ru.spb.tksoft.advertising.service.transaction;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.transaction.Transaction;
import ru.spb.tksoft.advertising.repository.transaction.TransactionRepository;
import ru.spb.tksoft.advertising.tools.LogEx;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    public List<Transaction> getTestTransactions(int limit) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN, "limit = ", limit);
        return transactionRepository.getTestTransactions(limit);
    }

    @CacheEvict(value = "usageCache", allEntries = true)
    public void clearCache() {
        // ...
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

    @Cacheable(value = "usageCache", key = "#userId + '-' + #productType + '-' + #transactionType")
    public double getProductSum(final UUID userId, final ProductType productType,
            final TransactionType transactionType) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN,
                "userId = ", userId, "productType = ", productType, "transactionType = ", transactionType);

        return transactionRepository.getProductSum(userId, productType.toString(), transactionType.toString());
    }
}
