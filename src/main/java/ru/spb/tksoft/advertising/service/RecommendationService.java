package ru.spb.tksoft.advertising.service;

import java.util.ArrayList;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.dto.UserRecommendationsDto;
import ru.spb.tksoft.advertising.mapper.RecommendationMapper;

/**
 * Сервис выдачи рекомендаций для пользователя с указанным useId.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@RequiredArgsConstructor
public class RecommendationService {

    Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationServiceCached recommendationServiceCached;

    public void clearCacheAll() {
        recommendationServiceCached.clearCacheAll();
    }

    public UserRecommendationsDto getRecommendations(UUID userId) {

        var result = new UserRecommendationsDto(userId, new ArrayList<>());

        if (recommendationServiceCached.isFitsInvest500(userId)) {
            var recommendation = recommendationServiceCached.getRecommendationByName("Invest 500")
                    .orElseThrow(IllegalArgumentException::new);
            result.getRecommendations().add(RecommendationMapper.toDto(recommendation));
        }

        if (recommendationServiceCached.isFitsTopSaving(userId)) {
            var recommendation = recommendationServiceCached.getRecommendationByName("Top Saving")
                    .orElseThrow(IllegalArgumentException::new);
            result.getRecommendations().add(RecommendationMapper.toDto(recommendation));
        }

        if (recommendationServiceCached.isFitsCommonCredit(userId)) {
            var recommendation =
                    recommendationServiceCached.getRecommendationByName("Простой кредит")
                            .orElseThrow(IllegalArgumentException::new);
            result.getRecommendations().add(RecommendationMapper.toDto(recommendation));
        }

        return result;
    }
}
