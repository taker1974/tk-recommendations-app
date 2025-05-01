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
import lombok.NoArgsConstructor;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiBooleanMethod;
import ru.spb.tksoft.advertising.tools.StringListConverter;

/**
 * Класс рекомендования продукта с основным методом isUserSuitable(). По сути этот класс - это
 * описание RMI в виде "query:имя_метода,arguments:аргумент_метода_1;.." и ссылка на класс, который
 * реализует RMI через реализацию {@link DynamicApiBooleanMethod}.
 * 
 * @see ru.spb.tksoft.advertising.model.ProductRulePredicate
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_rules")
public class ProductRulePredicateEntity {

    /** Уникальный идентификатор БД записи предиката. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Продукт, к которому относится правило. */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    /** Имя запроса к API-интерфейсу. Имя RMI. */
    @Column(nullable = false, length = 128)
    private String query;

    /** Список параметров запроса к API-интерфейсу. Аргументы RMI. */
    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    private List<String> arguments;

    /** Если true, то результат предиката инвертируется перед использованием. */
    @Column(nullable = false)
    boolean negate;

    /**
     * Конструктор.
     * 
     * @param query Имя запроса к API-интерфейсу. Имя RMI.
     * @param arguments Список параметров запроса к API-интерфейсу. Аргументы RMI.
     * @param negate Если true, то результат предиката инвертируется перед использованием.
     */
    public ProductRulePredicateEntity(String query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }
}
