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
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Repository
public interface ProductHitsCounterRepository
                extends JpaRepository<ProductHitsCounterEntity, Long> {

        /**
         * Получение сущности ProductHitsCounterEntity по идентификатору продукта.
         * 
         * @param productId
         * @return
         */
        @Query(nativeQuery = true,
                        value = "SELECT * FROM product_hits_counters WHERE product_id = :productId")
        Optional<ProductHitsCounterEntity> findByProductId(UUID productId);
}
