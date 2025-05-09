package ru.spb.tksoft.advertising.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.concurrent.ThreadSafe;
import org.springframework.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import ru.spb.tksoft.advertising.api.HistoryService;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiBooleanMethod;
import ru.spb.tksoft.advertising.api.impl.DynamicApiManagerImpl;
import ru.spb.tksoft.advertising.entity.ProductEntity;
import ru.spb.tksoft.advertising.entity.ProductRulePredicateEntity;
import ru.spb.tksoft.advertising.model.Product;
import ru.spb.tksoft.advertising.model.ProductRulePredicate;
import ru.spb.tksoft.recommendations.dto.manager.ManagedProductDto;
import ru.spb.tksoft.recommendations.dto.manager.ManagedProductRulePredicateDto;
import ru.spb.tksoft.recommendations.exception.MethodIdentificationException;

/**
 * Маппер для Managed*.
 *
 * Преобразования {@code dto <-> model <-> entity, dto <-> entity}.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ThreadSafe
public final class ManagedProductMapper {

        private ManagedProductMapper() {}

        // model <- entity
        @NotNull
        private static ProductRulePredicate toModel(
                        @NotNull final ProductRulePredicateEntity entity,
                        @NotNull final DynamicApiBooleanMethod testMethodImplementation) {

                return new ProductRulePredicate(
                                entity.getQuery(), new ArrayList<>(entity.getArguments()),
                                entity.isNegate(), testMethodImplementation);
        }

        /**
         * Новая модель из переданной сущности с передачей истории транзакций, если нужно работать с
         * правилами
         * 
         * @param entity Сущность.
         * @param historyService Сервис истории транзакций.
         * @return Новая модель.
         * 
         * @throws MethodIdentificationException в случае невозможности сопоставления запроса из
         *         ProductRuleEntity с известной реализацией метода-предиката.
         */
        @NotNull
        public static Product toModel(@NotNull final ProductEntity entity,
                        @NotNull final HistoryService historyService) {

                List<ProductRulePredicate> rules = new ArrayList<>(entity.getRule().size());
                for (ProductRulePredicateEntity ruleEntity : entity.getRule()) {

                        DynamicApiBooleanMethod testMethodImplementation =
                                        DynamicApiManagerImpl.newMethodInstance(
                                                        ruleEntity.getQuery(),
                                                        new ArrayList<>(ruleEntity.getArguments()),
                                                        historyService)
                                                        .orElseThrow(MethodIdentificationException::new);

                        rules.add(ManagedProductMapper.toModel(ruleEntity,
                                        testMethodImplementation));
                }

                return new Product(
                                entity.getId(), entity.getProductName(),
                                entity.getProductText(), rules);
        }

        /**
         * Новая модель из переданной сущности, когда не нужно работать с правилами.
         * 
         * @param entity Сущность.
         * @return Новая модель.
         * 
         * @throws MethodIdentificationException в случае невозможности сопоставления запроса из
         *         ProductRuleEntity с известной реализацией метода-предиката.
         */
        @NotNull
        public static Product toModel(@NotNull final ProductEntity entity) {

                return new Product(
                                entity.getId(), entity.getProductName(),
                                entity.getProductText());
        }

        // model -> entity
        @NotNull
        private static ProductRulePredicateEntity toEntity(
                        @NotNull final ProductRulePredicate model) {

                return new ProductRulePredicateEntity(
                                model.getQuery(), new ArrayList<>(model.getArguments()),
                                model.isNegate());
        }

        /**
         * Новая сущность из модели.
         * 
         * @param model Модель.
         * @return Новая сущность.
         */
        @NotNull
        public static ProductEntity toEntity(@NotNull final Product model) {

                var entity = new ProductEntity(
                                model.getId(), model.getProductName(), model.getProductText(),
                                model.getRule().stream().map(ManagedProductMapper::toEntity)
                                                .toList());

                entity.getRule().forEach(r -> r.setProduct(entity));
                return entity;
        }

        // dto -> model
        @NotNull
        private static ProductRulePredicate toModel(
                        @NotNull final ManagedProductRulePredicateDto dto,
                        @Nullable final DynamicApiBooleanMethod testMethodImplementation) {

                return new ProductRulePredicate(
                                dto.getQuery(), new ArrayList<>(dto.getArguments()),
                                dto.isNegate(), testMethodImplementation);
        }

        /**
         * Новая модель из DTO.
         * 
         * @param dto Сущность.
         * @param historyService Сервис истории транзакций.
         * @return Новый DTO.
         * 
         * @throws MethodIdentificationException в случае невозможности сопоставления запроса из
         *         ProductRuleEntity с известной реализацией метода-предиката.
         */
        @NotNull
        public static Product toModel(@NotNull final ManagedProductDto dto,
                        @Nullable final HistoryService historyService) {

                List<ProductRulePredicate> rules = new ArrayList<>();
                for (ManagedProductRulePredicateDto ruleDto : dto.getRule()) {

                        DynamicApiBooleanMethod testMethodImplementation =
                                        DynamicApiManagerImpl.newMethodInstance(
                                                        ruleDto.getQuery(),
                                                        new ArrayList<>(ruleDto.getArguments()),
                                                        historyService)
                                                        .orElseThrow(MethodIdentificationException::new);

                        rules.add(ManagedProductMapper.toModel(ruleDto, testMethodImplementation));
                }

                return new Product(
                                dto.getProductId(), dto.getProductName(),
                                dto.getProductText(), rules);
        }

        // dto <- model
        @NotNull
        private static ManagedProductRulePredicateDto toDto(
                        @NotNull final ProductRulePredicate model) {

                return new ManagedProductRulePredicateDto(
                                model.getQuery(), new ArrayList<>(model.getArguments()),
                                model.isNegate());
        }

        /**
         * Новый DTO из модели.
         * 
         * @param model Модель.
         * @return Новый DTO.
         */
        @NotNull
        public static ManagedProductDto toDto(@NotNull final Product model) {

                return new ManagedProductDto(
                                model.getId(), model.getProductName(), model.getProductText(),
                                model.getRule().stream().map(ManagedProductMapper::toDto).toList());
        }

        // dto -> entity
        @NotNull
        private static ProductRulePredicateEntity toEntity(
                        @NotNull final ManagedProductRulePredicateDto dto) {

                return new ProductRulePredicateEntity(
                                dto.getQuery(), new ArrayList<>(dto.getArguments()),
                                dto.isNegate());
        }

        /**
         * Новая сущность из DTO.
         * 
         * @param dto DTO.
         * @return Новая сущность.
         */
        @NotNull
        public static ProductEntity toEntity(@NotNull final ManagedProductDto dto) {

                var entity = new ProductEntity(
                                dto.getProductId(), dto.getProductName(), dto.getProductText(),
                                dto.getRule().stream().map(ManagedProductMapper::toEntity)
                                                .toList());

                entity.getRule().forEach(r -> r.setProduct(entity));
                return entity;
        }

        // dto <- entity
        @NotNull
        private static ManagedProductRulePredicateDto toDto(
                        @NotNull final ProductRulePredicateEntity entity) {

                return new ManagedProductRulePredicateDto(
                                entity.getQuery(), new ArrayList<>(entity.getArguments()),
                                entity.isNegate());
        }

        /**
         * Новый DTO из сущности.
         * 
         * @param entity Сущность.
         * @return Новый DTO.
         */
        @NotNull
        public static ManagedProductDto toDto(@NotNull final ProductEntity entity) {

                return new ManagedProductDto(
                                entity.getId(), entity.getProductName(), entity.getProductText(),
                                entity.getRule().stream().map(ManagedProductMapper::toDto)
                                                .toList());
        }
}
