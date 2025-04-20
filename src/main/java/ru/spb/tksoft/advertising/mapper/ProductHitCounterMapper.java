package ru.spb.tksoft.advertising.mapper;

import javax.annotation.concurrent.ThreadSafe;
import jakarta.validation.constraints.NotNull;
import ru.spb.tksoft.advertising.api.HistoryService;
import ru.spb.tksoft.advertising.dto.stat.ProductHitCounterDto;
import ru.spb.tksoft.advertising.entity.ProductHitsCounterEntity;
import ru.spb.tksoft.advertising.model.ProductHitCounter;

/**
 * Маппер для ProductHitCounter*.
 *
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
public final class ProductHitCounterMapper {

    private ProductHitCounterMapper() {}

    // model <- entity
    public static ProductHitCounter toModel(final ProductHitsCounterEntity entity,
            @NotNull final HistoryService historyService) {

        return new ProductHitCounter(
                ManagedProductMapper.toModel(entity.getProduct(), historyService));
    }

    // model -> entity
    public static ProductHitsCounterEntity toEntity(final ProductHitCounter model) {

        return new ProductHitsCounterEntity(
                ManagedProductMapper.toEntity(model.getProduct()), model.getHitsCount());
    }

    // dto <- model
    public static ProductHitCounterDto toDto(final ProductHitCounter model) {

        return new ProductHitCounterDto(model.getProduct().getId(), model.getHitsCount());
    }

    // dto <--- entity
    public static ProductHitCounterDto toDto(final ProductHitsCounterEntity entity) {

        return new ProductHitCounterDto(entity.getProduct().getId(), entity.getHitsCount());
    }
}
