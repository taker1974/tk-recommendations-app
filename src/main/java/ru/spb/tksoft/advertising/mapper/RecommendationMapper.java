package ru.spb.tksoft.advertising.mapper;

import java.util.ArrayList;
import ru.spb.tksoft.advertising.dto.RecommendationDto;
import ru.spb.tksoft.advertising.dto.RuleDto;
import ru.spb.tksoft.advertising.entity.RecommendationEntity;
import ru.spb.tksoft.advertising.entity.RuleEntity;
import ru.spb.tksoft.advertising.model.Recommendation;
import ru.spb.tksoft.advertising.model.Rule;

/**
 * Маппер для Recommendation* и Rule*.<br>
 *
 * Реализованы не только преобразования вида dto <-> model <-> entity, но и короткие преобразования
 * вида dto <-> entity.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
public final class RecommendationMapper {

    private RecommendationMapper() {}

    // dto -> model
    public static Rule toModel(final RuleDto dto) {
        return new Rule(
                dto.getId(), dto.getQuery(),
                new ArrayList<>(dto.getArguments()), dto.isNegate());
    }

    public static Recommendation toModel(final RecommendationDto dto) {
        return new Recommendation(
                dto.getId(), dto.getProductName(), dto.getProductText(),
                dto.getRules().stream().map(RecommendationMapper::toModel).toList());
    }

    // dto <- model
    public static RuleDto toDto(final Rule model) {
        return new RuleDto(
                model.getId(), model.getQuery(),
                new ArrayList<>(model.getArguments()), model.isNegate());
    }

    public static RecommendationDto toDto(final Recommendation model) {
        return new RecommendationDto(
                model.getId(), model.getProductName(), model.getProductText(),
                model.getRules().stream().map(RecommendationMapper::toDto).toList());
    }

    // model -> entity
    /**
     * @param Model
     * @return RuleEntity БЕЗ РЕКОМЕНДАЦИИ!
     */
    public static RuleEntity toEntity(final Rule model) {
        return new RuleEntity(
                model.getId(), model.getQuery(),
                new ArrayList<>(model.getArguments()), model.isNegate(),
                null);
    }

    public static RecommendationEntity toEntity(final Recommendation model) {
        var entity = new RecommendationEntity(
                model.getId(), model.getProductName(), model.getProductText(),
                model.getRules().stream().map(RecommendationMapper::toEntity).toList());
        entity.getRules().forEach(r -> r.setRecommendation(entity));
        return entity;
    }

    // model <- entity
    public static Rule toModel(final RuleEntity entity) {
        return new Rule(
                entity.getId(), entity.getQuery(),
                new ArrayList<>(entity.getArguments()), entity.isNegate());
    }

    public static Recommendation toModel(final RecommendationEntity entity) {
        return new Recommendation(
                entity.getId(), entity.getProductName(), entity.getProductText(),
                entity.getRules().stream().map(RecommendationMapper::toModel).toList());
    }

    // dto -> entity
    /**
     * @param DTO
     * @return RuleEntity БЕЗ РЕКОМЕНДАЦИИ!
     */
    public static RuleEntity toEntity(final RuleDto dto) {
        return new RuleEntity(dto.getId(), dto.getQuery(),
                new ArrayList<>(dto.getArguments()), dto.isNegate(),
                null);
    }

    public static RecommendationEntity toEntity(final RecommendationDto dto) {
        var entity = new RecommendationEntity(
                dto.getId(), dto.getProductName(), dto.getProductText(),
                dto.getRules().stream().map(RecommendationMapper::toEntity).toList());
        entity.getRules().forEach(r -> r.setRecommendation(entity));
        return entity;
    }

    // dto <- entity
    public static RuleDto toDto(final RuleEntity entity) {
        return new RuleDto(entity.getId(), entity.getQuery(),
                new ArrayList<>(entity.getArguments()), entity.isNegate());
    }

    public static RecommendationDto toDto(final RecommendationEntity entity) {
        return new RecommendationDto(
                entity.getId(), entity.getProductName(), entity.getProductText(),
                entity.getRules().stream().map(RecommendationMapper::toDto).toList());
    }
}
