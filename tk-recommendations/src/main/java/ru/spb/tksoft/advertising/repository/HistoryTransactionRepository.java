package ru.spb.tksoft.advertising.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.spb.tksoft.advertising.entity.history.HistoryTransactionEntity;
import ru.spb.tksoft.advertising.entity.history.HistoryUserEntity;

/**
 * Репозиторий на основе JdbcTemplate для чтения данных о транзакциях пользователя.
 * 
 * @see ru.spb.tksoft.advertising.configuration.CommonDatabaseConfig
 * @see ru.spb.tksoft.advertising.configuration.RecommendationDatabaseConfig
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Repository
public class HistoryTransactionRepository {

    private final Logger log = LoggerFactory.getLogger(HistoryTransactionRepository.class);

    /** Запись ошибки в лог при неудачном выполнении запроса. */
    public static final String LOG_QUERY_FAILED = "Query failed";

    @NotNull
    private final JdbcTemplate transactionJdbcTemplate;

    /**
     * Конструктор репозитория на основе JdbcTemplate.
     * 
     * @param transactionJdbcTemplate JdbcTemplate для работы с базой данных.
     */
    public HistoryTransactionRepository(
            @NotNull @Qualifier("transactionJdbcTemplate") JdbcTemplate transactionJdbcTemplate) {

        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    private final Object getTestTransactionsLock = new Object();
    private static final String TEST_TRANSACTIONS_QUERY = "SELECT * FROM TRANSACTIONS LIMIT ?";

    /**
     * Служебный метод для проверки правильности настройки всего тракта получения данных о
     * транзакциях.
     * 
     * @param limit Количество записей для получения.
     * @return Список транзакций.
     */
    @NotNull
    public List<HistoryTransactionEntity> getTestTransactions(int limit) {

        synchronized (getTestTransactionsLock) {
            try {
                RowMapper<HistoryTransactionEntity> mapper = (r, i) -> new HistoryTransactionEntity(
                        UUID.fromString(r.getString("ID")),
                        UUID.fromString(r.getString("PRODUCT_ID")),
                        UUID.fromString(r.getString("USER_ID")),
                        r.getString("TYPE"),
                        r.getInt("AMOUNT"));

                return transactionJdbcTemplate.query(TEST_TRANSACTIONS_QUERY, mapper, limit);

            } catch (Exception e) {
                log.error(LOG_QUERY_FAILED, e);
                return Arrays.asList();
            }
        }
    }

    private final Object getProductUsageCountLock = new Object();

    private static final String PRODUCT_USAGE_COUNT_QUERY = """
            SELECT COUNT(t.PRODUCT_ID) FROM TRANSACTIONS t
            JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
            WHERE USER_ID = ? AND p."TYPE" = ?""";

    /**
     * Получение количества транзакций с указанным продуктом.
     * 
     * @param userId Иидентификатор пользователя.
     * @param productType Тип продукта.
     * @return Количество транзакций.
     */
    public int getProductUsageCount(@NotNull final UUID userId,
            @NotBlank final String productType) {

        synchronized (getProductUsageCountLock) {
            try {
                final Integer count = transactionJdbcTemplate
                        .queryForObject(PRODUCT_USAGE_COUNT_QUERY, Integer.class,
                                userId, productType);
                if (count == null) {
                    return 0;
                }
                return count;

            } catch (Exception e) {
                log.error(LOG_QUERY_FAILED, e);
                return 0;
            }
        }
    }

    private final Object getProductSumLock = new Object();

    private static final String PRODUCT_SUM_QUERY = """
            SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t
            JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
            WHERE USER_ID = ? AND p."TYPE" = ? AND t."TYPE" = ?""";

    /**
     * Получение суммы транзакций по указанному типу продукта и типу транзакции.
     * 
     * @param userId Иидентификатор пользователя.
     * @param productType Тип продукта.
     * @param transactionType Тип транзакции.
     * @return Сумма транзакций.
     */
    public double getProductSum(@NotNull final UUID userId, @NotBlank final String productType,
            @NotBlank final String transactionType) {

        synchronized (getProductSumLock) {
            try {
                final Double sum = transactionJdbcTemplate
                        .queryForObject(PRODUCT_SUM_QUERY, Double.class,
                                userId, productType, transactionType);
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

    private final Object getUserInfoByIdLock = new Object();

    private static final String USER_INFO_BY_ID_QUERY =
            "SELECT * FROM USERS u WHERE ID = ?";

    /**
     * Получение информации о пользователе по его идентификатору.
     * 
     * @param userId Идентификатор пользователя.
     * @return Информация о пользователе.
     */
    public Optional<HistoryUserEntity> getUserInfo(@NotNull final UUID userId) {

        synchronized (getUserInfoByIdLock) {
            try {
                RowMapper<HistoryUserEntity> mapper = (r, i) -> new HistoryUserEntity(
                        UUID.fromString(r.getString("ID")),
                        r.getString("USERNAME"),
                        r.getString("FIRST_NAME"),
                        r.getString("LAST_NAME"));

                return Optional.of(
                        transactionJdbcTemplate.queryForObject(USER_INFO_BY_ID_QUERY, mapper,
                                userId.toString()));

            } catch (Exception e) {
                log.error(LOG_QUERY_FAILED, e);
                return Optional.empty();
            }
        }
    }

    private final Object getUserInfoByNameLock = new Object();

    private static final String USER_INFO_BY_NAME_QUERY =
            "SELECT * FROM USERS u WHERE USERNAME = ?";

    /**
     * Получение информации о пользователе по его нику.
     * 
     * @param userName Ник пользователя.
     * @return Информация о пользователе.
     */
    public Optional<HistoryUserEntity> getUserInfo(@NotBlank final String userName) {

        synchronized (getUserInfoByNameLock) {
            try {
                RowMapper<HistoryUserEntity> mapper = (r, i) -> new HistoryUserEntity(
                        UUID.fromString(r.getString("ID")),
                        r.getString("USERNAME"),
                        r.getString("FIRST_NAME"),
                        r.getString("LAST_NAME"));

                return Optional.of(
                        transactionJdbcTemplate.queryForObject(USER_INFO_BY_NAME_QUERY, mapper,
                                userName));

            } catch (Exception e) {
                log.error(LOG_QUERY_FAILED, e);
                return Optional.empty();
            }
        }
    }

    private final Object getAllIdsLock = new Object();

    private static final String ALL_IDS_QUERY =
            "SELECT ID FROM USERS;";

    /**
     * Получение всех идентификаторов пользователей.
     * 
     * @return Список идентификаторов пользователей.
     */
    public List<UUID> getAllIds() {

        synchronized (getAllIdsLock) {
            try {
                RowMapper<UUID> mapper = (r, i) -> UUID.fromString(r.getString("ID"));

                return transactionJdbcTemplate.query(ALL_IDS_QUERY, mapper);

            } catch (Exception e) {
                log.error(LOG_QUERY_FAILED, e);
                return Arrays.asList();
            }
        }
    }
}
