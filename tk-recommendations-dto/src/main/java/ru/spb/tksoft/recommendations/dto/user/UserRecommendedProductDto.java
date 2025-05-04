package ru.spb.tksoft.recommendations.dto.user;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Описание рекомендованного продукта.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "name", "text"})
public class UserRecommendedProductDto {

    /** Идентификатор рекомендованного продукта. */
    @NotNull
    @JsonProperty("id")
    private UUID id;

    /** Наименование рекомендованного продукта. */
    @NotBlank
    @JsonProperty("name")
    private String productName;

    /** Описание рекомендованного продукта. */
    @NotBlank
    @JsonProperty("text")
    private String productText;
}
