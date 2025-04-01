package ru.spb.tksoft.advertising.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Рекомендация (продукт) для пользователя с набором описаний правил, в соответствии с которыми
 * продукт будет рекомендован пользователю.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Value
@RequiredArgsConstructor
public class Recommendation {

    private final UUID id;

    private final String productName;
    private final String productText;

    private final List<Rule> rules;

    public List<Rule> getRules() {
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
