package ru.spb.tksoft.advertising.mapper;

import javax.annotation.concurrent.ThreadSafe;
import ru.spb.tksoft.advertising.dto.stat.ProductHitCounterDto;
import ru.spb.tksoft.advertising.entity.ProductHitsCounterEntity;
import ru.spb.tksoft.advertising.model.ProductHitsCounter;

/**
 * Маппер для ProductHitCounter*.
 *
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
public final class ProductHitCounterMapper {

    private ProductHitCounterMapper() {}

    // model <- entity
    public static ProductHitsCounter toModel(final ProductHitsCounterEntity entity) {

        return new ProductHitsCounter(
                ManagedProductMapper.toModel(entity.getProduct()), entity.getHitsCount());
    }

    // model -> entity
    public static ProductHitsCounterEntity toEntity(final ProductHitsCounter model,
            final long counterId) {

        return new ProductHitsCounterEntity(counterId,
                ManagedProductMapper.toEntity(model.getProduct()), model.getHitsCount());
    }

    // dto <- model
    public static ProductHitCounterDto toDto(final ProductHitsCounter model) {

        return new ProductHitCounterDto(model.getProduct().getId(), model.getHitsCount());
    }

    // dto <--- entity
    public static ProductHitCounterDto toDto(final ProductHitsCounterEntity entity) {

        return new ProductHitCounterDto(entity.getProduct().getId(), entity.getHitsCount());
    }
}
