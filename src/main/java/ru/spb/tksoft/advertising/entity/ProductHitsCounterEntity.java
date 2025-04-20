package ru.spb.tksoft.advertising.entity;

import jakarta.persistence.Column;
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

/**
 * @see ru.spb.tksoft.advertising.model.ProductHitCounter
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_hits_counters")
public class ProductHitsCounterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь с ProductEntity. Использована @ManyToOne, а не @OneToOne, чтобы не засорять
    // ProductEntity лишними связями. В данной реализации всё равно реализована связь 1:1.
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", unique = true)
    // Здесь может быть @OnDelete(action = OnDeleteAction.CASCADE), но применено удаление
    // ON DELETE CASCADE в миграции, так как это кажется более универсальным подходом.
    private ProductEntity product;

    @Column(name = "hits_count", nullable = false)
    private long hitsCount;

    public ProductHitsCounterEntity(ProductEntity product, long hitsCount) {

        this.product = product;
        this.hitsCount = hitsCount;
    }
}
