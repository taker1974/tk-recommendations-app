package ru.spb.tksoft.advertising.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.FutureTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.dto.UserRecommendationsDto;
import ru.spb.tksoft.advertising.entity.RecommendationEntity;
import ru.spb.tksoft.advertising.mapper.RecommendationMapper;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Сервис выдачи рекомендаций для пользователя с указанным useId.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationServiceCached recommendationServiceCached;

    public void clearCacheAll() {
        recommendationServiceCached.clearCacheAll();
    }

    private final Object getRecommendationsLock = new Object();

    public UserRecommendationsDto getRecommendations(UUID userId) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, "userId = " + userId);

        synchronized (getRecommendationsLock) {
            var result = new UserRecommendationsDto(userId, new ArrayList<>());

            FutureTask<Optional<RecommendationEntity>> invest500RecommendationFutureTask = new FutureTask<>({
                return recommendationServiceCached.getRecommendationByName("Invest 500");
            });

            FutureTask<Optional<RecommendationEntity>> topSavingRecommendationFutureTask = new FutureTask<>({
                return recommendationServiceCached.getRecommendationByName("Top Saving");
            });

            FutureTask<Optional<RecommendationEntity>> commonCreditRecommendationFutureTask = new FutureTask<>({
                return recommendationServiceCached.getRecommendationByName("Простой кредит");
            });

            invest500RecommendationFutureTask.run();
            topSavingRecommendationFutureTask.run();
            commonCreditRecommendationFutureTask.run();

            if (recommendationServiceCached.isFitsInvest500(userId)) {
                var recommendation =
                        recommendationServiceCached.getRecommendationByName("Invest 500")
                                .orElseThrow(IllegalArgumentException::new);
                result.getRecommendations().add(RecommendationMapper.toDto(recommendation));
            }

            if (recommendationServiceCached.isFitsTopSaving(userId)) {
                var recommendation =
                        recommendationServiceCached.getRecommendationByName("Top Saving")
                                .orElseThrow(IllegalArgumentException::new);
                result.getRecommendations().add(RecommendationMapper.toDto(recommendation));
            }

            if (recommendationServiceCached.isFitsCommonCredit(userId)) {
                var recommendation =
                        recommendationServiceCached.getRecommendationByName("Простой кредит")
                                .orElseThrow(IllegalArgumentException::new);
                result.getRecommendations().add(RecommendationMapper.toDto(recommendation));
            }

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING, "userId = " + userId);
            return result;
        }
    }
}
