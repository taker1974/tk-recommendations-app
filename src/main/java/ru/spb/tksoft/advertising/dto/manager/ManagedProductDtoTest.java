package ru.spb.tksoft.advertising.dto.manager;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

/**
 * Тестовый класс.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@NoArgsConstructor
public class ManagedProductDtoTest {

    // @JsonProperty("product_id")
    // @JsonAlias("productId")
    private UUID productId;

    // @JsonProperty("product_name")
    // @JsonAlias("productName")
    private String productName;

    // @JsonProperty("product_text")
    // @JsonAlias("productText")
    private String productText;

    @JsonProperty("product_id")
    public UUID getProductId() {
        return productId;
    }

    @JsonProperty("product_id")
    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    @JsonProperty("product_name")
    public String getProductName() {
        return productName;
    }

    @JsonProperty("product_name")
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @JsonProperty("product_text")
    public String getProductText() {
        return productText;
    }

    @JsonProperty("product_text")
    public void setProductText(String productText) {
        this.productText = productText;
    }
}
