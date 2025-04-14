package ru.spb.tksoft.advertising.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.concurrent.ThreadSafe;
import org.springframework.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiBoolean;
import ru.spb.tksoft.advertising.dto.manager.ManagedProductDto;
import ru.spb.tksoft.advertising.dto.manager.ManagedProductRuleDto;
import ru.spb.tksoft.advertising.entity.ProductEntity;
import ru.spb.tksoft.advertising.entity.ProductRuleEntity;
import ru.spb.tksoft.advertising.model.Product;
import ru.spb.tksoft.advertising.model.ProductRule;

/**
 * Маппер для Managed*.
 *
 * Преобразования dto <-> model <-> entity, dto <-> entity.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
public final class ManagedProductMapper {

    private ManagedProductMapper() {}

    // model <- entity
    @NotNull
    private static ProductRule toModel(@NotNull final ProductRuleEntity entity) {

        return new ProductRule(
                entity.getQuery(), new ArrayList<>(entity.getArguments()),
                entity.isNegate(), null);
    }

    @NotNull
    private static ProductRule toModel(@NotNull final ProductRuleEntity entity,
            @NotNull final DynamicApiBoolean dynamicApiBoolean) {

        return new ProductRule(
                entity.getQuery(), new ArrayList<>(entity.getArguments()),
                entity.isNegate(), dynamicApiBoolean);
    }

    @NotNull
    public static Product toModel(@NotNull final ProductEntity entity) {

        return new Product(
                entity.getId(), entity.getProductName(),
                entity.getProductText(), 
                entity.getRules().stream().map(ManagedProductMapper::toModel).toList());
    }

    @NotNull
    public static Product toModel(@NotNull final ProductEntity entity,
            @NotNull final DynamicApiBoolean dynamicApiBoolean) {

        List<ProductRule> rules = new ArrayList<>();
        for (ProductRuleEntity ruleEntity : entity.getRules()) {
            rules.add(ManagedProductMapper.toModel(ruleEntity, dynamicApiBoolean));
        }

        return new Product(
                entity.getId(), entity.getProductName(),
                entity.getProductText(), rules);
    }

    // model -> entity
    @NotNull
    private static ProductRuleEntity toEntity(@NotNull final ProductRule model) {

        return new ProductRuleEntity(
                model.getQuery(), new ArrayList<>(model.getArguments()),
                model.isNegate());
    }

    @NotNull
    public static ProductEntity toEntity(@NotNull final Product model) {

        var entity = new ProductEntity(
                model.getId(), model.getProductName(), model.getProductText(),
                model.getRules().stream().map(ManagedProductMapper::toEntity).toList());

        entity.getRules().forEach(r -> r.setProduct(entity));
        return entity;
    }

    // dto -> model
    @NotNull
    private static ProductRule toModel(@NotNull final ManagedProductRuleDto dto,
            @Nullable final DynamicApiBoolean dynamicApiBoolean) {

        return new ProductRule(
                dto.getQuery(), new ArrayList<>(dto.getArguments()),
                dto.isNegate(), dynamicApiBoolean);
    }

    @NotNull
    public static Product toModel(@NotNull final ManagedProductDto dto,
            @Nullable final DynamicApiBoolean dynamicApiBoolean) {

        List<ProductRule> rules = new ArrayList<>();
        for (ManagedProductRuleDto ruleDto : dto.getRules()) {
            rules.add(ManagedProductMapper.toModel(ruleDto, dynamicApiBoolean));
        }

        return new Product(
                dto.getProductId(), dto.getProductName(),
                dto.getProductText(), rules);
    }

    // dto <- model
    @NotNull
    private static ManagedProductRuleDto toDto(@NotNull final ProductRule model) {

        return new ManagedProductRuleDto(
                model.getQuery(), new ArrayList<>(model.getArguments()),
                model.isNegate());
    }

    @NotNull
    public static ManagedProductDto toDto(@NotNull final Product model) {

        return new ManagedProductDto(
                model.getId(), model.getProductName(), model.getProductText(),
                model.getRules().stream().map(ManagedProductMapper::toDto).toList());
    }

    // dto -> entity
    @NotNull
    private static ProductRuleEntity toEntity(@NotNull final ManagedProductRuleDto dto) {

        return new ProductRuleEntity(
                dto.getQuery(), new ArrayList<>(dto.getArguments()),
                dto.isNegate());
    }

    @NotNull
    public static ProductEntity toEntity(@NotNull final ManagedProductDto dto) {

        var entity = new ProductEntity(
                dto.getProductId(), dto.getProductName(), dto.getProductText(),
                dto.getRules().stream().map(ManagedProductMapper::toEntity).toList());

        entity.getRules().forEach(r -> r.setProduct(entity));
        return entity;
    }

    // dto <- entity
    @NotNull
    private static ManagedProductRuleDto toDto(@NotNull final ProductRuleEntity entity) {

        return new ManagedProductRuleDto(
                entity.getQuery(), new ArrayList<>(entity.getArguments()),
                entity.isNegate());
    }

    @NotNull
    public static ManagedProductDto toDto(@NotNull final ProductEntity entity) {

        return new ManagedProductDto(
                entity.getId(), entity.getProductName(), entity.getProductText(),
                entity.getRules().stream().map(ManagedProductMapper::toDto).toList());
    }
}
