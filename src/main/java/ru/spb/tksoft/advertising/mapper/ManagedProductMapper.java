package ru.spb.tksoft.advertising.mapper;

import java.util.ArrayList;
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
public final class ManagedProductMapper {

    private ManagedProductMapper() {}

    // model <- entity
    private static ProductRule toModel(final ProductRuleEntity entity) {
        return new ProductRule(
                entity.getId(), entity.getQuery(),
                new ArrayList<>(entity.getArguments()), entity.isNegate());
    }

    public static Product toModel(final ProductEntity entity) {
        return new Product(
                entity.getId(), entity.getProductName(), entity.getProductText(),
                entity.getRules().stream().map(ManagedProductMapper::toModel).toList());
    }

    // model -> entity
    private static ProductRuleEntity toEntity(final ProductRule model) {
        return new ProductRuleEntity(
                model.getQuery(), new ArrayList<>(model.getArguments()), model.isNegate());
    }

    public static ProductEntity toEntity(final Product model) {
        var entity = new ProductEntity(
                model.getId(), model.getProductName(), model.getProductText(),
                model.getRules().stream().map(ManagedProductMapper::toEntity).toList());
        entity.getRules().forEach(r -> r.setProduct(entity));
        return entity;
    }

    // dto -> model
    private static ProductRule toModel(final ManagedProductRuleDto dto) {
        return new ProductRule(
                dto.getId(), dto.getQuery(),
                new ArrayList<>(dto.getArguments()), dto.isNegate());
    }

    public static Product toModel(final ManagedProductDto dto) {
        return new Product(
                dto.getId(), dto.getProductName(), dto.getProductText(),
                dto.getRules().stream().map(ManagedProductMapper::toModel).toList());
    }

    // dto <- model
    private static ManagedProductRuleDto toDto(final ProductRule model) {
        return new ManagedProductRuleDto(
                model.getId(), model.getQuery(),
                new ArrayList<>(model.getArguments()), model.isNegate());
    }

    public static ManagedProductDto toDto(final Product model) {
        return new ManagedProductDto(
                model.getId(), model.getProductName(), model.getProductText(),
                model.getRules().stream().map(ManagedProductMapper::toDto).toList());
    }

    // dto -> entity
    private static ProductRuleEntity toEntity(final ManagedProductRuleDto dto) {
        return new ProductRuleEntity(
                dto.getQuery(), new ArrayList<>(dto.getArguments()), dto.isNegate());
    }

    public static ProductEntity toEntity(final ManagedProductDto dto) {
        var entity = new ProductEntity(
                dto.getId(), dto.getProductName(), dto.getProductText(),
                dto.getRules().stream().map(ManagedProductMapper::toEntity).toList());
        entity.getRules().forEach(r -> r.setProduct(entity));
        return entity;
    }

    // dto <- entity
    private static ManagedProductRuleDto toDto(final ProductRuleEntity entity) {
        return new ManagedProductRuleDto(entity.getId(), entity.getQuery(),
                new ArrayList<>(entity.getArguments()), entity.isNegate());
    }

    public static ManagedProductDto toDto(final ProductEntity entity) {
        return new ManagedProductDto(
                entity.getId(), entity.getProductName(), entity.getProductText(),
                entity.getRules().stream().map(ManagedProductMapper::toDto).toList());
    }
}
