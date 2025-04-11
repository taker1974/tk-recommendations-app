package ru.spb.tksoft.advertising.dto.manager;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Описание правила рекомендования. По сути - описание RMI в виде
 * "query:имя_метода,arguments:аргумент_метода_1;.."
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagedProductRuleDto {

    @NotBlank
    @JsonProperty("query")
    private String query;

    @NotNull
    @JsonProperty("arguments")
    private List<String> arguments = Arrays.asList();

    @JsonProperty("negate")
    boolean negate;
}
