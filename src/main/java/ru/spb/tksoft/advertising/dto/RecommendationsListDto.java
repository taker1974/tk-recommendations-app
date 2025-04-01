package ru.spb.tksoft.advertising.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Листинг рекомендаций с правилами, возвращаемый контроллером по GET(/rule).
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@AllArgsConstructor
public class RecommendationsListDto {

    @NotNull
    @JsonProperty("data")
    List<RecommendationDto> recommendations;
}
