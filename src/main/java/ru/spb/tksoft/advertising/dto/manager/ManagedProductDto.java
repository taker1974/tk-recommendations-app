package ru.spb.tksoft.advertising.dto.manager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see ru.spb.tksoft.advertising.model.Product
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private List<ManagedProductRuleDto> rules = Arrays.asList();
}
