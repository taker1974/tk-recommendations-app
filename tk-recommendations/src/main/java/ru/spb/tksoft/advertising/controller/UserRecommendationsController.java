package ru.spb.tksoft.advertising.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.exception.HistoryUserNotFoundException;
import ru.spb.tksoft.advertising.mapper.HistoryMapper;
import ru.spb.tksoft.advertising.service.history.HistoryTransactionServiceCached;
import ru.spb.tksoft.advertising.service.user.UserRecommendationService;
import ru.spb.tksoft.recommendations.dto.stat.ShallowViewDto;
import ru.spb.tksoft.recommendations.dto.user.HistoryUserDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendationsDto;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Получение рекомендаций по id пользователя на основе данных о предыдущих транзакциях.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@RestController
@RequestMapping(value = "/recommendation")
@Tag(name = "Рекомендации для пользователя")
@RequiredArgsConstructor
public class UserRecommendationsController {

    private final HistoryTransactionServiceCached historyTransactionServiceCached;
    private final UserRecommendationService userRecommendationService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить сведения о пользователе по его ID")
    @GetMapping("/info/id/{userId}")
    public HistoryUserDto getUserInfo(@PathVariable UUID userId) {

        return HistoryMapper.toDto(historyTransactionServiceCached.getUserInfo(userId)
                .orElseThrow(
                        () -> new HistoryUserNotFoundException(userId.toString())));
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить сведения о пользователе по его имени")
    @GetMapping("/info/name/{userName}")
    public HistoryUserDto getUserInfo(@PathVariable String userName) {

        return HistoryMapper.toDto(historyTransactionServiceCached.getUserInfo(userName)
                .orElseThrow(
                        () -> new HistoryUserNotFoundException(userName)));
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить список вида [ID пользователя : количество рекоменованных продуктов]")
    @GetMapping("/view/shallow")
    public List<ShallowViewDto> getShallowView() {

        return userRecommendationService.getShallowView();
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить рекомендации")
    @GetMapping("/{userId}")
    public UserRecommendationsDto getRecommendations(@PathVariable UUID userId) {

        return userRecommendationService.getRecommendations(userId);
    }
}
