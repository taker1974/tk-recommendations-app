package ru.spb.tksoft.advertising.dto.stat;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see ru.spb.tksoft.advertising.model.ProductHitCounter
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
