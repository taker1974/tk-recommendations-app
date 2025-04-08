package ru.spb.tksoft.advertising.dto.manager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Тестовый класс.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagedProductDtoTest {

    @JsonProperty("product_id")
    private UUID productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_text")
    private String productText;

    @JsonProperty("arguments")
    private List<String> arguments = Arrays.asList();
}
