package ru.spb.tksoft.advertising.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.annotation.concurrent.ThreadSafe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.spb.tksoft.advertising.api.SuitableUser;

/**
 * Продукт с правилом рекомендования. Правило - это набор методов-предикатов
 * {@link ProductRulePredicate}, объединяемых затем по "И". Смотри код isUserSuitable(). Если список
 * предикатов пуст, то считаем, что правило рекомендования продукта заложено в код.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
@Value
public class Product implements SuitableUser {

    @NotNull
    private final UUID id;

    @NotBlank
    private final String productName;

    @NotBlank
    private final String productText;

    @NotNull
    private final List<ProductRulePredicate> rule;

    @NotNull
    public List<ProductRulePredicate> getRule() {
        return Collections.unmodifiableList(null == rule ? new ArrayList<>() : rule);
    }

    public Product(@NotNull final UUID id, @NotBlank final String productName,
            @NotBlank final String productText, @NotNull final List<ProductRulePredicate> rule) {

        this.id = id;
        this.productName = productName;
        this.productText = productText;
        this.rule = rule;
    }

    /**
     * Этот конструктор нужен тогда, когда не нужно использовать правила для рекомендации продукта.
     * 
     * @param id уникальный идентификатор продукта.
     * @param productName название продукта.
     * @param productText описание продукта.
     */
    public Product(@NotNull final UUID id, @NotBlank final String productName,
            @NotBlank final String productText) {

        this.id = id;
        this.productName = productName;
        this.productText = productText;
        this.rule = new ArrayList<>();
    }

    @Override
    @NotBlank
    public String toString() {
        return String.format("%s: %s. %s", id, productName, productText);
    }

    @NotBlank
    public String toStringShort(final int descriptionLength) {
        return String.format("%s: %s. %s", id, productName,
                productText.length() <= descriptionLength ? productText
                        : productText.substring(0, descriptionLength - 1));
    }

    public boolean isUserSuitable(@NotNull final UUID userId) {

        if (null == rule || rule.isEmpty()) {
            return false;
        }

        for (ProductRulePredicate productRule : rule) {
            if (!productRule.isUserSuitable(userId)) {
                return false;
            }
        }
        return true;
    }
}
