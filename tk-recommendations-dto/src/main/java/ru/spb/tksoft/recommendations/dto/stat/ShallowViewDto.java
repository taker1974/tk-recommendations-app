package ru.spb.tksoft.recommendations.dto.stat;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Служебный/отладочный DTO для перечисления всех UUID пользователей с количеством возможных
 * рекомендаций.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "user_id", "recommendations_count" })
public class ShallowViewDto {

    @NotNull
    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("recommendations_count")
    private int recommendationsCount;
}
