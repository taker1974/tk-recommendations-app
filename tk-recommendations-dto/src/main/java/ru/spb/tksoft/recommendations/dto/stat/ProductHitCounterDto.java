package ru.spb.tksoft.recommendations.dto.stat;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see ru.spb.tksoft.advertising.model.ProductHitsCounter
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"rule_id"})
public class ProductHitCounterDto {

    // В условии задания неоднозначность, неточность:
    // для 2-го этапа было уточнено, что одному продукту соответствует только одно правило.
    // (Правило может состоять из нескольких предикатов и их аргументов.)
    // Поэтому нет отдельной идентификации такой сущности, как правило - есть один идентификатор для
    // продукта, который также является идентификатором правила.
    @NotBlank
    @JsonProperty("rule_id")
    private UUID productId;

    @NotNull
    private long count;
}
