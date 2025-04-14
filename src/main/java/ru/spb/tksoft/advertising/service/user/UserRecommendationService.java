package ru.spb.tksoft.advertising.service.user;

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.common.collect.ImmutableMap;
import jakarta.validation.constraints.NotNull;
import ru.spb.tksoft.advertising.dto.user.UserRecommendationsDto;
import ru.spb.tksoft.advertising.mapper.UserRecommendationMapper;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Сервис выдачи рекомендаций для пользователя с указанным useId.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
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

    @NotNull
    private final Map<String, Predicate<UUID>> recommendationChecks;

    @NotNull
    private Map<String, Predicate<UUID>> initRecommendationChecks() {
        return ImmutableMap.<String, Predicate<UUID>>builder()
                .put(INVEST_500, recommendationServiceCached::isFitsInvest500)
                .put(TOP_SAVING, recommendationServiceCached::isFitsTopSaving)
                .put(COMMON_CREDIT, recommendationServiceCached::isFitsCommonCredit)
                .build();
    }

    public UserRecommendationService(
            @NotNull final UserRecommendationServiceCached recommendationServiceCached) {

        this.recommendationServiceCached = recommendationServiceCached;
        this.recommendationChecks = initRecommendationChecks();
    }

    private final Object getRecommendationsLock = new Object();

    @NotNull
    public UserRecommendationsDto getRecommendations(@NotNull final UUID userId) {

        synchronized (getRecommendationsLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, "userId = " + userId);

            final var result = new UserRecommendationsDto(userId, new HashSet<>());

            checkFixedProducts(userId, result);
            checkDynamicProducts(userId, result);

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING, "userId = " + userId);
            return result;
        }
    }

    private void checkFixedProducts(@NotNull final UUID userId,
            @NotNull final UserRecommendationsDto dto) {

        recommendationChecks.entrySet().stream().forEach(entry -> {
            if (entry.getValue().test(userId)) {

                var recommendation = recommendationServiceCached
                        .getRecommendationByName(entry.getKey())
                        .orElseThrow(IllegalArgumentException::new);

                var recommendations = dto.getRecommendations();

                if (!recommendations.stream()
                        .noneMatch(data -> data.getProductName().equals(entry.getKey()))) {

                    dto.getRecommendations()
                            .add(UserRecommendationMapper.toDto(recommendation));
                }
            }
        });
    }

    private void checkDynamicProducts(@NotNull final UUID userId,
            @NotNull final UserRecommendationsDto dto) {
                
                // ...
            }
}
