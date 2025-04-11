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
 * Продукт с правилами рекомендования. Если правила пусты, то считаем, что правила его
 * рекомендования заложены в код.
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
    private final List<ProductRule> rules;

    @NotNull
    public List<ProductRule> getRules() {

        return Collections.unmodifiableList(null == rules ? new ArrayList<>() : rules);
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

        for (ProductRule productRule : rules) {
            if (!productRule.isUserSuitable(userId)) {
                return false;
            }
        }
        return true;
    }
}
