package ru.spb.tksoft.advertising.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Конфигурация источников данных плюс JdbcTemplate для истории транзакций пользователя.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Configuration
public class CommonDatabaseConfig {

    /**
     * Конструктор по умолчанию.
     */
    private CommonDatabaseConfig() {}

    /**
     * Основной источник данных.
     * 
     * @return Основной источник данных.
     */
    @Bean(name = "recommendationDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.recommendation")
    public DataSource recommendationDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Источник данных для истории транзакций пользователя.
     * 
     * @return Источник данных.
     */
    @Bean(name = "transactionDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.transaction")
    public DataSource transactionDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * JdbcTemplate для работы с историей транзакций пользователя.
     * 
     * @param dataSource Источник данных.
     * @return JdbcTemplate.
     */
    @Bean(name = "transactionJdbcTemplate")
    public JdbcTemplate transactionJdbcTemplate(
            @Qualifier("transactionDataSource") DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }
}
