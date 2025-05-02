package ru.spb.tksoft.advertising.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.spb.tksoft.advertising.entity.ProductEntity;

/**
 * Репозиторий для сущности ProductEntity.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {

    /**
     * @return Cписок продуктов.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM products")
    List<ProductEntity> findAllProductsOnly();

    /**
     * Возвращает продукт по имени.
     * 
     * @param productName Имя продукта.
     * @return Продукт.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE product_name = :productName")
    Optional<ProductEntity> findProductOnlyByName(String productName);

    /**
     * Возвращает продукт по id.
     * 
     * @param id Идентификатор продукта.
     * @return Продукт.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE id = :id")
    Optional<ProductEntity> findProductOnlyById(UUID id);
}
