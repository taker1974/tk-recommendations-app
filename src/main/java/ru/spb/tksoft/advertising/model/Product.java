package ru.spb.tksoft.advertising.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Продукт с правилами рекомендования.
 * Если правила пусты, то считаем, что правила его рекомендования заложены в код.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Value
@RequiredArgsConstructor
public class Product {

    private final UUID id;

    private final String productName;
    private final String productText;

    private final List<ProductRule> rules;

    public List<ProductRule> getRules() {
        return Collections.unmodifiableList(null == rules ? new ArrayList<>() : rules);
    }

    @Override
    public String toString() {
        return String.format("%s: %s. %s", id, productName, productText);
    }

    public String toStringShort(int descriptionLength) {
        return String.format("%s: %s. %s", id, productName,
                productText.length() <= descriptionLength ? productText
                        : productText.substring(0, descriptionLength - 1));
    }
}
