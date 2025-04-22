package ru.spb.tksoft.advertising.dto.manager;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiBooleanMethod;

/**
 * Класс рекомендования продукта с основным методом isUserSuitable(). По сути этот класс - это
 * описание RMI в виде "query:имя_метода,arguments:аргумент_метода_1;.." и ссылка на класс, который
 * реализует RMI через реализацию {@link DynamicApiBooleanMethod}.
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
