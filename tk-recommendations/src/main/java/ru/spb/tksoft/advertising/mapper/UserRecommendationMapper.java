package ru.spb.tksoft.advertising.mapper;

import javax.annotation.concurrent.ThreadSafe;
import ru.spb.tksoft.advertising.entity.ProductEntity;
import ru.spb.tksoft.advertising.model.Product;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendedProductDto;

/**
 * Маппер для User*.
 *
 * Здесь нужны только преобразования {@code dto <- ...}.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ThreadSafe
public final class UserRecommendationMapper {

    private UserRecommendationMapper() {}

    /**
     * Новый DTO по модели.
     * 
     * @param model Модель.
     * @return Новый DTO.
     */
    public static UserRecommendedProductDto toDto(final Product model) {

        return new UserRecommendedProductDto(
                model.getId(), model.getProductName(), model.getProductText());
    }

    /**
     * Новый DTO по сущности.
     * 
     * @param entity Сущность.
     * @return Новый DTO.
     */
    public static UserRecommendedProductDto toDto(final ProductEntity entity) {

        return new UserRecommendedProductDto(
                entity.getId(), entity.getProductName(), entity.getProductText());
    }
}
