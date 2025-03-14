package ru.spb.tksoft.advertising.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.recommendation.RecommendationsDto;
import ru.spb.tksoft.advertising.service.recommendation.RecommendationService;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(value = "/recommendation")
@Tag(name = "Рекомендации для пользователя")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Список рекомендаций для пользователя")
    @GetMapping("/{userId}")
    public RecommendationsDto getRecommendations(@PathVariable UUID userId) {
        return recommendationService.getRecommendations(userId);
    }
}
