package ru.spb.tksoft.advertising.service.recommendation;

import java.util.ArrayList;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.recommendation.RecommendationsDto;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationServiceCached recommendationServiceCached;

    public void clearCacheAll() {
        recommendationServiceCached.clearCacheAll();
    }

    public RecommendationsDto getRecommendations(UUID userId) {

        var result = new RecommendationsDto(userId, new ArrayList<>());

        if (recommendationServiceCached.isFitsInvest500(userId)) {
            var recommendation = recommendationServiceCached.getRecommendationByName("Invest 500")
                    .orElseThrow(IllegalArgumentException::new);
            result.getRecommendations().add(recommendation);
        }

        if (recommendationServiceCached.isFitsTopSaving(userId)) {
            var recommendation = recommendationServiceCached.getRecommendationByName("Top Saving")
                    .orElseThrow(IllegalArgumentException::new);
            result.getRecommendations().add(recommendation);
        }

        if (recommendationServiceCached.isFitsCommonCredit(userId)) {
            var recommendation = recommendationServiceCached.getRecommendationByName("Простой кредит")
                    .orElseThrow(IllegalArgumentException::new);
            result.getRecommendations().add(recommendation);
        }

        return result;
    }
}
