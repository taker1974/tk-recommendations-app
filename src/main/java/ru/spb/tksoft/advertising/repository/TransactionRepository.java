package ru.spb.tksoft.advertising.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.spb.tksoft.advertising.entity.history.HistoryTransaction;

/**
 * Репозиторий на основе JdbcTemplate для чтения данных о транзакциях пользователя.
 * 
 * @see ru.spb.tksoft.advertising.configuration.CommonDatabaseConfig
 * @see ru.spb.tksoft.advertising.configuration.RecommendationDatabaseConfig
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */

@Repository
public class TransactionRepository {

    Logger log = LoggerFactory.getLogger(TransactionRepository.class);
    public static final String LOG_QUERY_FAILED = "Query failed";

    private final JdbcTemplate transactionJdbcTemplate;

    public TransactionRepository(
            @Qualifier("transactionJdbcTemplate") JdbcTemplate transactionJdbcTemplate) {

        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    public List<HistoryTransaction> getTestTransactions(int limit) {

        String sql = "SELECT * FROM TRANSACTIONS LIMIT ?";
        List<HistoryTransaction> list = new ArrayList<>();

        try {
            RowMapper<HistoryTransaction> mapper = (r, i) -> new HistoryTransaction(
                    UUID.fromString(r.getString("ID")),
                    UUID.fromString(r.getString("PRODUCT_ID")),
                    UUID.fromString(r.getString("USER_ID")),
                    r.getString("TYPE"),
                    r.getInt("AMOUNT"));

            return transactionJdbcTemplate.query(sql, mapper, limit);

        } catch (Exception e) {
            log.error(LOG_QUERY_FAILED, e);
            list.clear();
            return list;
        }
    }

    public int getProductUsageCount(final UUID userId, final String productType) {

        String sql = """
                SELECT COUNT(t.AMOUNT) FROM TRANSACTIONS t
                JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
                WHERE t.USER_ID = ? AND p."TYPE" = ?""";

        try {
            Integer count = transactionJdbcTemplate
                    .queryForObject(sql, Integer.class, userId, productType);
            if (count == null) {
                return 0;
            }
            return count;

        } catch (Exception e) {
            log.error(LOG_QUERY_FAILED, e);
            return 0;
        }
    }

    public double getProductSum(final UUID userId, final String productType,
            final String transactionType) {

        String sql = """
                SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t
                JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
                WHERE USER_ID = ? AND p."TYPE" = ? AND t."TYPE" = ?""";

        try {
            Double sum = transactionJdbcTemplate
                    .queryForObject(sql, Double.class, userId, productType, transactionType);
            if (sum == null) {
                return 0.0;
            }
            return sum;

        } catch (Exception e) {
            log.error(LOG_QUERY_FAILED, e);
            return 0.0;
        }
    }
}
