package ru.spb.tksoft.advertising.repository.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.spb.tksoft.advertising.entity.transaction.Transaction;

@Repository
public class TransactionRepository {

    Logger log = LoggerFactory.getLogger(TransactionRepository.class);

    private final JdbcTemplate transactionJdbcTemplate;

    public TransactionRepository(
            @Qualifier("transactionJdbcTemplate") JdbcTemplate transactionJdbcTemplate) {

        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    public List<Transaction> getTestTransactions(int limit) {

        String sql = "SELECT * FROM TRANSACTIONS LIMIT ?";
        List<Transaction> list = new ArrayList<>();

        try {
            RowMapper<Transaction> mapper = (r, i) -> new Transaction(
                    UUID.fromString(r.getString("ID")),
                    UUID.fromString(r.getString("PRODUCT_ID")),
                    UUID.fromString(r.getString("USER_ID")),
                    r.getString("TYPE"),
                    r.getInt("AMOUNT"));

            return transactionJdbcTemplate.query(sql, mapper, limit);
        } catch (Exception e) {
            log.error("query failed:", e);
            list.clear();
        }
        return list;
    }
}