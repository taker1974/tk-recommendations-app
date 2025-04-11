package ru.spb.tksoft.advertising.service.user;

import java.util.ArrayList;
import java.util.UUID;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.dto.user.UserRecommendationsDto;
import ru.spb.tksoft.advertising.mapper.UserRecommendationMapper;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Сервис выдачи рекомендаций для пользователя с указанным useId.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@RequiredArgsConstructor
@ThreadSafe
public class UserRecommendationService {

    private final Logger log = LoggerFactory.getLogger(UserRecommendationService.class);

    @NotNull
    private final UserRecommendationServiceCached recommendationServiceCached;

    public void clearCacheAll() {

        recommendationServiceCached.clearCacheAll();
    }

    public static final String INVEST_500 = "Invest 500";
    public static final String TOP_SAVING = "Top Saving";
    public static final String COMMON_CREDIT = "Простой кредит";

    private final Object getRecommendationsLock = new Object();

    @NotNull
    public UserRecommendationsDto getRecommendations(@NotNull final UUID userId) {

        synchronized (getRecommendationsLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, "userId = " + userId);
            
            final var result = new UserRecommendationsDto(userId, new ArrayList<>());

            if (recommendationServiceCached.isFitsInvest500(userId)) {
                var recommendation = recommendationServiceCached
                        .getRecommendationByName(INVEST_500)
                        .orElseThrow(IllegalArgumentException::new);
                result.getRecommendations()
                        .add(UserRecommendationMapper.toDto(recommendation));
            }

            if (recommendationServiceCached.isFitsTopSaving(userId)) {
                var recommendation = recommendationServiceCached
                        .getRecommendationByName(TOP_SAVING)
                        .orElseThrow(IllegalArgumentException::new);
                result.getRecommendations()
                        .add(UserRecommendationMapper.toDto(recommendation));
            }

            if (recommendationServiceCached.isFitsCommonCredit(userId)) {
                var recommendation = recommendationServiceCached
                        .getRecommendationByName(COMMON_CREDIT)
                        .orElseThrow(IllegalArgumentException::new);
                result.getRecommendations()
                        .add(UserRecommendationMapper.toDto(recommendation));
            }

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING, "userId = " + userId);
            return result;
        }
    }
}
