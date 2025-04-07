package ru.spb.tksoft.advertising.dto.user;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserRecommendedProductDto {

    @NotNull
    @JsonProperty("id")
    private UUID id;

    @NotBlank
    @JsonProperty("name")
    private String productName;

    @NotBlank
    @JsonProperty("text")
    private String productText;
}
