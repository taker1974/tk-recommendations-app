package ru.spb.tksoft.recommendations.dto.manager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Продукт с правилом рекомендования. Правило - это набор методов-предикатов, объединяемых затем по
 * "И". Если список предикатов пуст, то считаем, что правило рекомендования продукта заложено в код.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"product_id", "product_name", "product_text", "rule"})
public class ManagedProductDto {

    @NotBlank
    @JsonProperty("product_id")
    private UUID productId;

    @NotBlank
    @JsonProperty("product_name")
    private String productName;

    @NotBlank
    @JsonProperty("product_text")
    private String productText;

    @Valid
    @NotNull
    @JsonProperty("rule")
    private List<ManagedProductRulePredicateDto> rule = Arrays.asList();
}
