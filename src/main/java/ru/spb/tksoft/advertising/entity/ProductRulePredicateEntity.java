package ru.spb.tksoft.advertising.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.tools.StringListConverter;

/**
 * @see ru.spb.tksoft.advertising.model.ProductRulePredicate
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "product_rules")
public class ProductRulePredicateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(nullable = false, length = 128)
    private String query;

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    private List<String> arguments;

    @Column(nullable = false)
    boolean negate;

    public ProductRulePredicateEntity(String query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }
}
