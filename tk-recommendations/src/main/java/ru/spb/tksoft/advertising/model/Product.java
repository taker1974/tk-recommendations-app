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
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ThreadSafe
@Value
public class Product implements SuitableUser {

    /** Идентификатор продукта. */
    @NotNull
    private final UUID id;

    /** Название продукта. */
    @NotBlank
    private final String productName;

    /** Описание продукта. */
    @NotBlank
    private final String productText;

    /** Правило рекомендации продукта. */
    @NotNull
    private final List<ProductRulePredicate> rule;

    /**
     * @return Правило рекомендации продукта, т.е. список предикатов.
     */
    @NotNull
    public List<ProductRulePredicate> getRule() {
        return Collections.unmodifiableList(null == rule ? new ArrayList<>() : rule);
    }

    /**
     * Конструктор с полным набором параметров.
     * 
     * @param id Уникальный идентификатор продукта.
     * @param productName Название продукта.
     * @param productText Описание продукта.
     * @param rule Правило рекомендации продукта.
     */
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
     * @param id Уникальный идентификатор продукта.
     * @param productName Название продукта.
     * @param productText Описание продукта.
     */
    public Product(@NotNull final UUID id, @NotBlank final String productName,
            @NotBlank final String productText) {

        this.id = id;
        this.productName = productName;
        this.productText = productText;
        this.rule = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotBlank
    public String toString() {
        return String.format("%s: %s. %s", id, productName, productText);
    }

    /**
     * Аналог toString(), но с усеченным описанием продукта.
     * 
     * @param descriptionLength Максимальная длина описания продукта.
     * @return Строка с информацией о продукте.
     */
    @NotBlank
    public String toStringShort(final int descriptionLength) {
        return String.format("%s: %s. %s", id, productName,
                productText.length() <= descriptionLength ? productText
                        : productText.substring(0, descriptionLength - 1));
    }

    /**
     * Проверяет, подходит ли пользователь под данный продукт.
     * 
     * @param userId Идентификатор пользователя.
     * @return Если список предикатов пуст, то возвращает false. Иначе возвращает результат.
     */
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
