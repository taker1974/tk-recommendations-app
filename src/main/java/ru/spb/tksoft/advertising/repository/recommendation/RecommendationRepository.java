package ru.spb.tksoft.advertising.repository.recommendation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.spb.tksoft.advertising.entity.recommendation.Recommendation;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM recommendations")
    List<Recommendation> findAllRecommendations();

    @Query(nativeQuery = true, value = "SELECT * FROM recommendations WHERE id = :id")
    List<Recommendation> findRecommendationById(UUID id);

}
