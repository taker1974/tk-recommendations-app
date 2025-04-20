package ru.spb.tksoft.advertising.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spb.tksoft.advertising.entity.ProductHitsCounterEntity;

/**
 * Репозиторий для сущности ProductHitsCounterEntity.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Repository
public interface ProductHitsCounterRepository
        extends JpaRepository<ProductHitsCounterEntity, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM product_hits_counter WHERE product_id = :productId")
    Optional<ProductHitsCounterEntity> findByProductId(UUID productId);
}
