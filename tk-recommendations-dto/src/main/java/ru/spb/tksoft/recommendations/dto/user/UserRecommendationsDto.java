package ru.spb.tksoft.recommendations.dto.user;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Рекомендации для пользователя, возвращаемые контроллером по
 * GET(/recommendation/{uuid_пользователя}).
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"user_id", "recommendations"})
public class UserRecommendationsDto {

    /** Идентификатор пользователя. */
    @NotNull
    @JsonProperty("user_id")
    private UUID userId;

    /** Список рекомендаций для пользователя. */
    @NotNull
    @JsonProperty("recommendations")
    private Set<UserRecommendedProductDto> recommendations = HashSet.newHashSet(0);
}
