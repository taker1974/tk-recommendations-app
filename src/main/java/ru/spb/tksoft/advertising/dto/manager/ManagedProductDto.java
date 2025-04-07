package ru.spb.tksoft.advertising.dto.manager;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @see ru.spb.tksoft.advertising.model.Product
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ManagedProductDto {

    @NotNull
    @JsonProperty("product_id")
    private UUID id;

    @NotBlank
    @JsonProperty("product_name")
    private String productName;

    @NotBlank
    @JsonProperty("product_text")
    private String productText;

    @NotNull
    @JsonProperty("rule")
    private List<ManagedProductRuleDto> rules;
}
