package ru.spb.tksoft.advertising.mapper;

import javax.annotation.concurrent.ThreadSafe;
import ru.spb.tksoft.advertising.entity.ProductEntity;
import ru.spb.tksoft.advertising.model.Product;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendedProductDto;

/**
 * Маппер для User*.
 *
 * Здесь нужны только преобразования dto <- ...
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
public final class UserRecommendationMapper {

    private UserRecommendationMapper() {}

    // dto <- model
    public static UserRecommendedProductDto toDto(final Product model) {

        return new UserRecommendedProductDto(
                model.getId(), model.getProductName(), model.getProductText());
    }

    // dto <- entity
    public static UserRecommendedProductDto toDto(final ProductEntity entity) {

        return new UserRecommendedProductDto(
                entity.getId(), entity.getProductName(), entity.getProductText());
    }
}
