package ru.spb.tksoft.advertising.service.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import ru.spb.tksoft.advertising.exception.ProductNotFoundApiException;
import ru.spb.tksoft.advertising.mapper.UserRecommendationMapper;
import ru.spb.tksoft.advertising.model.Product;
import ru.spb.tksoft.advertising.service.manager.ProductManagerServiceCached;
import ru.spb.tksoft.advertising.tools.LogEx;
import ru.spb.tksoft.recommendations.dto.stat.ShallowViewDto;
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
    private final UserRecommendationServiceCached userRecommendationServiceCached;

    @NotNull
    private final ProductManagerServiceCached productManagerServiceCached;

    /** Сброс кэшей. */
    public void clearCaches() {
        userRecommendationServiceCached.clearCaches();
    }

    /** Название продукта "Invest 500". */
    public static final String INVEST_500 = "Invest 500";
    /** Название продукта "Top Saving". */
    public static final String TOP_SAVING = "Top Saving";
    /** Название продукта "Простой кредит". */
    public static final String COMMON_CREDIT = "Простой кредит";

    @NotNull
    private final Map<String, Predicate<UUID>> recommendationChecks;

    @NotNull
    private Map<String, Predicate<UUID>> initRecommendationChecks() {
        return ImmutableMap.<String, Predicate<UUID>>builder()
                .put(INVEST_500, userRecommendationServiceCached::isFitsInvest500)
                .put(TOP_SAVING, userRecommendationServiceCached::isFitsTopSaving)
                .put(COMMON_CREDIT, userRecommendationServiceCached::isFitsCommonCredit)
                .build();
    }

    /**
     * Конструктор класса. В этом конструкторе инициализтруются структуры данных, которые
     * используются при выполнении проверок для фиксированных рекомендаций.
     * 
     * @param recommendationServiceCached Сервис рекомендаций с кэшированными методами.
     * @param productManagerServiceCached Сервис управления продуктами.
     */
    public UserRecommendationService(
            @NotNull final UserRecommendationServiceCached recommendationServiceCached,
            @NotNull final ProductManagerServiceCached productManagerServiceCached) {

        this.userRecommendationServiceCached = recommendationServiceCached;
        this.productManagerServiceCached = productManagerServiceCached;

        this.recommendationChecks = initRecommendationChecks();
    }

    private final Object getRecommendationsLock = new Object();

    /**
     * Получение рекомендаций для пользователя с указанным userId.
     * 
     * @param userId Идентификатор пользователя.
     * @return Объект рекомендаций пользователя.
     * @throws ProductNotFoundApiException Исключение в случае, если не найден продукт.
     */
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
                productManagerServiceCached.incrementProductHitsCounter(productId);
            }

            LogEx.trace(log, methodName, LogEx.STOPPING, userId);
            return result;
        }
    }

    private void checkFixedProducts(@NotNull final UUID userId,
            @NotNull final UserRecommendationsDto dto) throws ProductNotFoundApiException {

        try {
            recommendationChecks.entrySet().stream().forEach(entry -> {

                var key = entry.getKey();
                var value = entry.getValue();

                if (!value.test(userId)) {
                    return;
                }

                var recommendation = userRecommendationServiceCached
                        .getRecommendationByName(key)
                        .orElseThrow(() -> new ProductNotFoundApiException(key));

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
     * @param userId Идентификатор пользователя.
     * @param dto Объект рекомендаций пользователя.
     */
    private void checkDynamicProducts(@NotNull final UUID userId,
            @NotNull final UserRecommendationsDto dto) {

        Set<UUID> alreadyAddedIds = dto.getRecommendations().stream()
                .map(UserRecommendedProductDto::getId)
                .collect(Collectors.toSet());

        for (Product product : productManagerServiceCached.getAllProducts()) {

            boolean fits = product.isUserSuitable(userId);
            if (!fits || alreadyAddedIds.contains(product.getId())) {
                continue;
            }

            var recommendation = UserRecommendationMapper.toDto(product);
            dto.getRecommendations().add(recommendation);
        }
    }

    /**
     * Служебный метод для отладки или для проверки правил. Получение списка вида "пользователь ->
     * количество рекомендаций".
     * 
     * @return Список вида "пользователь -> количество рекомендаций".
     */
    public List<ShallowViewDto> getShallowView() {

        List<ShallowViewDto> result = new ArrayList<>();
        List<UUID> ids = userRecommendationServiceCached.getAllIds();
        for (UUID id : ids) {
            var recommendations = getRecommendations(id);
            int count = recommendations.getRecommendations().size();
            var view = new ShallowViewDto(id, count);
            result.add(view);
        }
        return result;
    }
}
