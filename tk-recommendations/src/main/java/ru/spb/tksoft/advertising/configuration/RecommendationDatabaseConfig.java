package ru.spb.tksoft.advertising.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

/**
 * Конфигурация БД для JPA. Здесь могут находиться конфигурации и других БД для JPA.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {
                "ru.spb.tksoft.advertising.entity",
                "ru.spb.tksoft.advertising.entity.history"})
@EnableJpaRepositories(
                entityManagerFactoryRef = "recommendationEntityManagerFactory",
                transactionManagerRef = "recommendationTransactionManager",
                basePackages = {"ru.spb.tksoft.advertising.repository"})
@RequiredArgsConstructor
public class RecommendationDatabaseConfig {

        private final Environment environment;

        /**
         * Создает фабрику для управления сущностями и сессиями для рекомендательной системы.
         * 
         * @param builder Экземпляр EntityManagerFactoryBuilder.
         * @param dataSource Источник данных для рекомендательной системы.
         * @return Экземпляр фабрики сущностей и сессий для рекомендательной системы.
         */
        @Bean(name = "recommendationEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
                        EntityManagerFactoryBuilder builder,
                        @Qualifier("recommendationDataSource") DataSource dataSource) {

                HashMap<String, Object> properties = new HashMap<>();
                properties.put("hibernate.dialect",
                                environment.getProperty("spring.jpa.database-platform"));

                return builder.dataSource(dataSource)
                                .packages("ru.spb.tksoft.advertising.entity",
                                                "ru.spb.tksoft.advertising.entity.history")
                                .properties(properties).build();
        }

        /**
         * Создает менеджер транзакций для рекомендательной системы.
         * 
         * @param entityManagerFactory Фабрика для управления сущностями и сессиями для
         *        рекомендательной системы.
         * @return Экземпляр менеджера транзакций для рекомендательной системы.
         */
        @Bean(name = "recommendationTransactionManager")
        public PlatformTransactionManager postgresTransactionManager(
                        @Qualifier("recommendationEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

                return new JpaTransactionManager(entityManagerFactory);
        }
}
