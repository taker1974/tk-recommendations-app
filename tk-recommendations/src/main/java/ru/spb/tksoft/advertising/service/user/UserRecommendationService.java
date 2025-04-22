package ru.spb.tksoft.advertising.service.user;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.common.collect.ImmutableMap;
import jakarta.validation.constraints.NotNull;
import ru.spb.tksoft.advertising.entity.ProductHitsCounterEntity;
import ru.spb.tksoft.advertising.exception.ProductNotFoundApiException;
import ru.spb.tksoft.advertising.mapper.ProductHitCounterMapper;
import ru.spb.tksoft.advertising.mapper.UserRecommendationMapper;
import ru.spb.tksoft.advertising.model.ProductHitsCounter;
import ru.spb.tksoft.advertising.repository.ProductHitsCounterRepository;
import ru.spb.tksoft.advertising.service.manager.ProductManagerServiceCached;
import ru.spb.tksoft.advertising.tools.LogEx;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendationsDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendedProductDto;

/**
 * Сервис выдачи рекомендаций для пользователя с указанным userId.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@ThreadSafe
public class UserRecommendationService {

    private final Logger log = LoggerFactory.getLogger(UserRecommendationService.class);

    @NotNull
    private final UserRecommendationServiceCached recommendationServiceCached;

    @NotNull
    private final ProductManagerServiceCached productManagerServiceCached;

    @NotNull
    private final ProductHitsCounterRepository productHitsCounterRepository;

    public void clearCaches() {
        recommendationServiceCached.clearCaches();
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
            @NotNull final UserRecommendationServiceCached recommendationServiceCached,
            @NotNull final ProductManagerServiceCached productManagerServiceCached,
            @NotNull final ProductHitsCounterRepository productHitsCounterRepository) {

        this.recommendationServiceCached = recommendationServiceCached;
        this.productManagerServiceCached = productManagerServiceCached;
        this.productHitsCounterRepository = productHitsCounterRepository;

        this.recommendationChecks = initRecommendationChecks();
    }

    private final Object getRecommendationsLock = new Object();

    @NotNull
    public UserRecommendationsDto getRecommendations(@NotNull final UUID userId) {

        synchronized (getRecommendationsLock) {
            final String methodName = LogEx.getThisMethodName();
            LogEx.trace(log, methodName, LogEx.STARTING, userId);

            final var result = new UserRecommendationsDto(userId, new HashSet<>());

            checkFixedProducts(userId, result);
            checkDynamicProducts(userId, result);

            for (var recommendation : result.getRecommendations()) {

                final UUID productId = recommendation.getId();

                ProductHitsCounterEntity entity = productHitsCounterRepository
                        .findByProductId(productId)
                        .orElseThrow(() -> new ProductNotFoundApiException(
                                methodName + ", " + productId));

                ProductHitsCounter counter = ProductHitCounterMapper.toModel(entity);
                counter.increment();
                productHitsCounterRepository
                        .save(ProductHitCounterMapper.toEntity(counter, entity.getId()));
            }

            LogEx.trace(log, methodName, LogEx.STOPPING, userId);
            return result;
        }
    }

    private void checkFixedProducts(@NotNull final UUID userId,
            @NotNull final UserRecommendationsDto dto) {

        try {
            recommendationChecks.entrySet().stream().forEach(entry -> {

                var key = entry.getKey();
                var value = entry.getValue();

                boolean isFits = value.test(userId);
                if (!isFits) {
                    return;
                }

                var recommendation = recommendationServiceCached
                        .getRecommendationByName(key)
                        .orElseThrow(IllegalArgumentException::new);

                var recommendations = dto.getRecommendations();

                if (recommendations.stream()
                        .noneMatch(data -> data.getProductName().equals(key))) {

                    dto.getRecommendations()
                            .add(UserRecommendationMapper.toDto(recommendation));
                }
            });
        } catch (Exception ex) {
            LogEx.error(log, LogEx.getThisMethodName(), LogEx.EXCEPTION_THROWN,
                    "userId", userId, ex);
        }
    }

    /**
     * Дополнение рекомендаций для пользователя рекомендациями по динамическим правилам.
     * 
     * @param userId идентификатор пользователя.
     * @param dto объект рекомендаций пользователя.
     */
    private void checkDynamicProducts(@NotNull final UUID userId,
            @NotNull final UserRecommendationsDto dto) {

        Set<UUID> alreadyAddedIds = dto.getRecommendations().stream()
                .map(UserRecommendedProductDto::getId)
                .collect(Collectors.toSet());

        productManagerServiceCached.getAllProducts().stream().filter(Objects::nonNull)
                .filter(product -> product.isUserSuitable(userId))
                .filter(product -> !alreadyAddedIds.contains(product.getId()))
                .forEach(product -> dto.getRecommendations()
                        .add(UserRecommendationMapper.toDto(product)));
    }
}
