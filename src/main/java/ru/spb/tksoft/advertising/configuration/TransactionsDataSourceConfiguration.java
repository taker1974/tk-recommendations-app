package ru.spb.tksoft.advertising.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class TransactionsDataSourceConfiguration {

    @Value("${transactions.datasource.url}")
    private String datasourceUrl;

    @Value("${transactions.datasource.username}")
    private String datasourceUsername;

    @Value("${transactions.datasource.password}")
    private String datasourcePassword;

    @Bean(name = "transactionsDataSource")
    public DataSource transactionsDataSource() {

        var hikariConfig = new HikariConfig();

        hikariConfig.setReadOnly(true);
        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setJdbcUrl(datasourceUrl);
        hikariConfig.setUsername(datasourceUsername);
        hikariConfig.setPassword(datasourcePassword);
        
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setConnectionTimeout(60000);

        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "transactionsJdbcTemplate")
    public JdbcTemplate transactionsJdbcTemplate(
        @Qualifier("transactionsDataSource") DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }
}