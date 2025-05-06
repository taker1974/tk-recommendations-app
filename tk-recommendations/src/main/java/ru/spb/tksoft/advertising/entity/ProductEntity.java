package ru.spb.tksoft.advertising.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Продукт с правилом рекомендования. Правило - это набор методов-предикатов, объединяемых затем по
 * "И". Смотри код isUserSuitable(). Если список предикатов пуст, то считаем, что правило
 * рекомендования продукта заложено в код. *
 * 
 * @see ru.spb.tksoft.advertising.model.Product
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity {

    /** Идентификатор продукта. */
    @Id
    @Column(nullable = false, unique = true)
    private UUID id;

    /** Название, имя продукта. */
    @Column(name = "product_name", nullable = false, length = 128)
    private String productName;

    /** Текст продукта, краткое описание. Допустимо многострочное описание. */
    @Column(name = "product_text", nullable = false, length = 4096)
    private String productText;

    /** Список правил рекомендации для продукта. */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductRulePredicateEntity> rule;
}
