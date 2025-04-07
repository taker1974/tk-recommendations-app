package ru.spb.tksoft.advertising.dto.user;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Рекомендации для пользователя, возвращаемые контроллером по
 * GET(/recommendation/{uuid_пользователя}).
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRecommendationsDto {

    @NotNull
    @JsonProperty("user_id")
    private UUID userId;

    @NotNull
    @JsonProperty("recommendations")
    private List<UserRecommendedProductDto> recommendations;
}
