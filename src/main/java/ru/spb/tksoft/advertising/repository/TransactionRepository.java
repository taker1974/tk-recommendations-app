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

import ru.spb.tksoft.advertising.entity.Transaction;

@Repository
public class TransactionRepository {

    Logger log = LoggerFactory.getLogger(TransactionRepository.class);

    private final JdbcTemplate transactionsJdbcTemplate;

    public TransactionRepository(
            @Qualifier("transactionsJdbcTemplate") JdbcTemplate transactionsJdbcTemplate) {

        this.transactionsJdbcTemplate = transactionsJdbcTemplate;
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

            return transactionsJdbcTemplate.query(sql, mapper, limit);
        } catch (Exception e) {
            log.error("query failed:", e);
            list.clear();
        }
        return list;
    }
}