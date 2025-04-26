package ru.spb.tksoft.advertising.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.service.UserRecommendationServiceCached;
import ru.spb.tksoft.recommendations.dto.maintenance.MaintenanceInfoDto;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Служебный контроллер.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@RestController
@RequestMapping(value = "/maintenance")
@Tag(name = "Обслуживание бота")
@RequiredArgsConstructor
public class MaintenanceController {

    private final BuildProperties buildProperties;
    private final UserRecommendationServiceCached userRecommendationService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "О приложении")
    @GetMapping("/info")
    public MaintenanceInfoDto getInfo() {

        return new MaintenanceInfoDto(
                buildProperties.getName(), buildProperties.getVersion());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Сброс всех кэшей")
    @PostMapping("/clear-caches")
    public void clearCaches() {

        userRecommendationService.clearCaches();
    }
}
