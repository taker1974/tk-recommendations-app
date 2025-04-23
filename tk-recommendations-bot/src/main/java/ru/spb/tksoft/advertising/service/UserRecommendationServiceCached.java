package ru.spb.tksoft.advertising.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.proxy.UserRecommendationProxy;
import ru.spb.tksoft.recommendations.dto.user.HistoryUserDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendationsDto;

@Service
@RequiredArgsConstructor
public class UserRecommendationServiceCached {

    private final UserRecommendationProxy proxy;

    @Cacheable(value = "users", unless = "#result == null || #result.isEmpty()")
    public Optional<HistoryUserDto> getUserInfo(final UUID userId) {
        try {
            return Optional.of(proxy.getUserInfo(userId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<UserRecommendationsDto> getRecommendations(final UUID userId) {
        try {
            return Optional.of(proxy.getRecommendations(userId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
