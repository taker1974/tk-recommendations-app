package ru.spb.tksoft.advertising.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.spb.tksoft.advertising.entity.RecommendationEntity;

/**
 * Репозиторий для сущности Recommendation.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Repository
public interface RecommendationRepository extends JpaRepository<RecommendationEntity, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM products")
    List<RecommendationEntity> findAllProducts();

    @Query(nativeQuery = true, value = "SELECT * FROM recommendations WHERE name = :name")
    Optional<RecommendationEntity> findRecommendationByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM recommendations WHERE id = :id")
    Optional<RecommendationEntity> findRecommendationById(UUID id);

}
