package ru.spb.tksoft.advertising.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.dto.UserRecommendationsDto;
import ru.spb.tksoft.advertising.entity.RecommendationEntity;
import ru.spb.tksoft.advertising.exception.TaskFailedException;
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

    private interface IsFitsFunc {
        boolean invoke(UUID userId);
    }

    private FutureTask<Optional<RecommendationEntity>> createFutureTask(
            IsFitsFunc func, UUID userId, String name) {

        return new FutureTask<>(() -> {
            if (func.invoke(userId)) {
                try {
                    return recommendationServiceCached.getRecommendationByName(name);
                } catch (Exception ex) {
                    log.error("Ошибка при проверке соответствия пользователя к рекомендации "
                            + name + ": ",
                            ex);
                    throw new TaskFailedException();
                }
            }
            return Optional.empty();
        });
    }

    public UserRecommendationsDto getRecommendations(final UUID userId) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, "userId = " + userId);

        synchronized (getRecommendationsLock) {
            var result = new UserRecommendationsDto(userId, new ArrayList<>());

            // Проверка соответствий пользователя к рекомендациям разделена здесь на
            // параллельные потоки в учебных целях.
            // Автор вполне осознаёт, что в реальных приложениях даже архитектура
            // классов с поддержкой конкурентности должна быть немного другой,
            // и общее количество потоков для эффективного использования процессоров
            // обычно должно быть не более N + 1,
            // где N - количество ядер процессора.

            final var taskInvest500 = createFutureTask(
                    recommendationServiceCached::isFitsInvest500,
                    userId, "Invest 500");

            final var taskTopSaving = createFutureTask(
                    recommendationServiceCached::isFitsTopSaving,
                    userId, "Top Saving");

            final var taskCommonCredit =
                    createFutureTask(recommendationServiceCached::isFitsCommonCredit,
                            userId, "Простой кредит");

            try {
                taskInvest500.run();
                taskTopSaving.run();
                taskCommonCredit.run();

                taskInvest500.get().ifPresent(
                        r -> result.getRecommendations().add(RecommendationMapper.toDto(r)));

                taskTopSaving.get().ifPresent(
                        r -> result.getRecommendations().add(RecommendationMapper.toDto(r)));

                taskCommonCredit.get().ifPresent(
                        r -> result.getRecommendations().add(RecommendationMapper.toDto(r)));

            } catch (TaskFailedException | InterruptedException | ExecutionException ex) {
                Thread.currentThread().interrupt();
            }

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING, "userId = " + userId);
            return result;
        }
    }
}
