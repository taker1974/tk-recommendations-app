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
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Configuration
public class CommonDatabaseConfig {

    @Bean(name = "recommendationDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.recommendation")
    public DataSource recommendationDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "transactionDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.transaction")
    public DataSource transactionDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "transactionJdbcTemplate")
    public JdbcTemplate transactionJdbcTemplate(
            @Qualifier("transactionDataSource") DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }
}
