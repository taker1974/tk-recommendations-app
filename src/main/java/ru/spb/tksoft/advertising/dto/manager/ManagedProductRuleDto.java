package ru.spb.tksoft.advertising.dto.manager;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Описание правила рекомендования. По сути - описание RMI в виде
 * "query:имя_метода,arguments:аргумент_метода_1;.."
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ManagedProductRuleDto {

    @JsonIgnore
    private long id;

    @NotBlank
    private String query;

    @NotNull
    private List<String> arguments;

    @NotNull
    boolean negate;
}
