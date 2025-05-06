package ru.spb.tksoft.advertising.proxy;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.spb.tksoft.recommendations.dto.user.HistoryUserDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendationsDto;

/**
 * Клиент для работы с REST API рекомендаций пользователей.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@FeignClient(name = "recommendation", url = "${service.recommendation.base-url}")
public interface UserRecommendationProxy {

    @GetMapping("/info/id/{userId}")
    HistoryUserDto getUserInfo(@PathVariable UUID userId);

    @GetMapping("/info/name/{userName}")
    HistoryUserDto getUserInfo(@PathVariable String userName);

    @GetMapping("/{userId}")
    UserRecommendationsDto getRecommendations(@PathVariable UUID userId);
}
