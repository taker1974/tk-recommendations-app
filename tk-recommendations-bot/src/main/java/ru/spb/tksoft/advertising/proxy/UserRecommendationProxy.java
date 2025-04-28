package ru.spb.tksoft.advertising.proxy;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import ru.spb.tksoft.recommendations.dto.user.HistoryUserDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendationsDto;

@FeignClient(name = "recommendation", url = "${service.recommendation.base-url}")
public interface UserRecommendationProxy {

    @GetMapping("/info/id/{userId}")
    HistoryUserDto getUserInfo(@PathVariable UUID userId);

    @GetMapping("/info/name/{userName}")
    HistoryUserDto getUserInfo(@PathVariable String userName);

    @GetMapping("/{userId}")
    UserRecommendationsDto getRecommendations(@PathVariable UUID userId);
}
