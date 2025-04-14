package ru.spb.tksoft.advertising.dto.user;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Рекомендации для пользователя, возвращаемые контроллером по
 * GET(/recommendation/{uuid_пользователя}).
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRecommendationsDto {

    @NotNull
    @JsonProperty("user_id")
    private UUID userId;

    @NotNull
    @JsonProperty("recommendations")
    private Set<UserRecommendedProductDto> recommendations = HashSet.newHashSet(0);
}
