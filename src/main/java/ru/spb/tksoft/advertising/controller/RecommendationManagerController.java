package ru.spb.tksoft.advertising.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.dto.RecommendationDto;
import ru.spb.tksoft.advertising.dto.RecommendationsListDto;
import ru.spb.tksoft.advertising.entity.RecommendationEntity;
import ru.spb.tksoft.advertising.mapper.RecommendationMapper;
import ru.spb.tksoft.advertising.service.RecommendationManagerService;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Управление рекомендациями.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@RestController
@RequestMapping(value = "/rule")
@Tag(name = "Управление рекомендациями")
@RequiredArgsConstructor
public class RecommendationManagerController {

    private final RecommendationManagerService managerService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Добавить рекомендацию")
    @PostMapping("/add")
    public RecommendationDto addRecommendation(final RecommendationDto dto) {
        return RecommendationMapper.toDto(
                managerService.addRecommendation(RecommendationMapper.toModel(dto)));
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить данные по всем рекомендациям")
    @GetMapping
    public RecommendationsListDto getListOfAllRecommendations() {
        return new RecommendationsListDto(
                managerService.getListOfAllRecommendations().stream()
                        .map(RecommendationMapper::toDto).toList());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить рекомендацию")
    @DeleteMapping(value = "/{recommendationId}")
    public void deleteRecommendation(@PathVariable final UUID recommendationId) {
        managerService.deleteRecommendation(recommendationId);
    }
}
