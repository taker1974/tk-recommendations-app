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
import lombok.RequiredArgsConstructor;

/**
 * @see ru.spb.tksoft.advertising.model.Recommendation
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "recommendations")
public class RecommendationEntity {

    @Id
    @Column(nullable = false, unique = true)
    private UUID id;

    @Column(name = "product_name", nullable = false, length = 128)
    private String productName;

    @Column(name = "product_text", nullable = false, length = 4096)
    private String productText;

    @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RuleEntity> rules;
}
