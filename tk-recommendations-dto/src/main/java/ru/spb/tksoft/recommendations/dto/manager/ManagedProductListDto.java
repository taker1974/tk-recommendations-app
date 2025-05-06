package ru.spb.tksoft.recommendations.dto.manager;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Листинг продуктов с правилами рекомендования, возвращаемый контроллером по GET(/rule).
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagedProductListDto {

    /** Обёртка над списком продуктов. */
    @Valid
    @NotNull
    @JsonProperty("data")
    List<ManagedProductDto> products = Arrays.asList();
}
