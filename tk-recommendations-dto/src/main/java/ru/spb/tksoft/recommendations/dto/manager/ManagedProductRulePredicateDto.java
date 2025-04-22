package ru.spb.tksoft.recommendations.dto.manager;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Описание предиката/запроса RMI для правила рекомендования.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagedProductRulePredicateDto {

    @NotBlank
    @JsonProperty("query")
    private String query;

    @NotNull
    @JsonProperty("arguments")
    private List<String> arguments = Arrays.asList();

    @JsonProperty("negate")
    boolean negate;
}
