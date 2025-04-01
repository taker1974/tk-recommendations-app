package ru.spb.tksoft.advertising.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.dto.UserRecommendationsDto;
import ru.spb.tksoft.advertising.service.RecommendationService;

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

    private final RecommendationService recommendationService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить рекомендации")
    @GetMapping("/{userId}")
    public UserRecommendationsDto getRecommendations(@PathVariable UUID userId) {
        return recommendationService.getRecommendations(userId);
    }
}
