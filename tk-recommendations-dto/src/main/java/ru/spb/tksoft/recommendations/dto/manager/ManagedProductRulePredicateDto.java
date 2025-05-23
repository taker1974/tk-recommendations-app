package ru.spb.tksoft.recommendations.dto.manager;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Описание предиката/запроса RMI для правила рекомендования.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"query", "arguments", "negate"})
public class ManagedProductRulePredicateDto {

    /** Запрос - имя удалённого метода. */
    @NotBlank
    @JsonProperty("query")
    private String query;

    /** Аргументы удалённого метода. */
    @NotNull
    @JsonProperty("arguments")
    private List<String> arguments = Arrays.asList();

    /** Инверсия результата выполнения запроса. */
    @JsonProperty("negate")
    boolean negate;
}
