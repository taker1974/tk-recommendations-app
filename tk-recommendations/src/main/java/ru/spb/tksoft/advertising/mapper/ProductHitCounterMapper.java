package ru.spb.tksoft.advertising.mapper;

import javax.annotation.concurrent.ThreadSafe;
import ru.spb.tksoft.advertising.entity.ProductHitsCounterEntity;
import ru.spb.tksoft.advertising.model.ProductHitsCounter;
import ru.spb.tksoft.recommendations.dto.stat.ProductHitCounterDto;

/**
 * Маппер для ProductHitCounter*.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ThreadSafe
public final class ProductHitCounterMapper {

    private ProductHitCounterMapper() {}

    /**
     * Новая модель из сущности.
     * 
     * @param entity Сущность.
     * @return Новая модель.
     */
    public static ProductHitsCounter toModel(final ProductHitsCounterEntity entity) {

        return new ProductHitsCounter(
                ManagedProductMapper.toModel(entity.getProduct()), entity.getHitsCount());
    }

    /**
     * Новая сущность из модели.
     * 
     * @param model Модель.
     * @param counterId ID счётчика. Совпадает с ИД продукта.
     * @return Новая сущность.
     */
    public static ProductHitsCounterEntity toEntity(final ProductHitsCounter model,
            final long counterId) {

        return new ProductHitsCounterEntity(counterId,
                ManagedProductMapper.toEntity(model.getProduct()), model.getHitsCount());
    }

    /**
     * Новый DTO из модели.
     * 
     * @param model Модель.
     * @return Новый DTO.
     */
    public static ProductHitCounterDto toDto(final ProductHitsCounter model) {

        return new ProductHitCounterDto(model.getProduct().getId(), model.getHitsCount());
    }

    /**
     * Новый DTO из сущности.
     * 
     * @param entity Сущность.
     * @return Новый DTO.
     */
    public static ProductHitCounterDto toDto(final ProductHitsCounterEntity entity) {

        return new ProductHitCounterDto(entity.getProduct().getId(), entity.getHitsCount());
    }
}
