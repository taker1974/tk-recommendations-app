package ru.spb.tksoft.advertising.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.proxy.UserRecommendationProxy;
import ru.spb.tksoft.recommendations.dto.user.HistoryUserDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendationsDto;

/**
 * Класс-обертка над клиентом REST API получения рекомендаций пользователей. Данные кешируются.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Service
@RequiredArgsConstructor
public class UserRecommendationServiceCached {

    private final UserRecommendationProxy proxy;

    /** Сброс кэшей. */
    @CacheEvict(value = "users", allEntries = true)
    public void clearCaches() {
        clearRecommendationsCache();
    }

    @CacheEvict(value = "recommendations", allEntries = true)
    private void clearRecommendationsCache() {
        // ...
    }

    /**
     * Возвращает информацию о пользователе по его идентификатору.
     * 
     * @param userId Идентификатор пользователя.
     * @return Информация о пользователе.
     */
    @Cacheable(value = "users", unless = "#result == null || #result.isEmpty()")
    public Optional<HistoryUserDto> getUserInfo(final UUID userId) {
        try {
            return Optional.of(proxy.getUserInfo(userId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Возвращает информацию о пользователе по его имени.
     * 
     * @param userName Имя пользователя.
     * @return Информация о пользователе.
     */
    @Cacheable(value = "users", unless = "#result == null || #result.isEmpty()")
    public Optional<HistoryUserDto> getUserInfo(final String userName) {
        try {
            return Optional.of(proxy.getUserInfo(userName));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Возвращает рекомендации пользователя по его идентификатору.
     * 
     * @param userId Идентификатор пользователя.
     * @return DTO рекомендаций пользователя.
     */
    @Cacheable(value = "recommendations", unless = "#result == null || #result.isEmpty()")
    public Optional<UserRecommendationsDto> getRecommendations(final UUID userId) {
        try {
            return Optional.of(proxy.getRecommendations(userId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
