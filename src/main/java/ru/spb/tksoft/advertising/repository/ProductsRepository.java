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
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM products")
    List<ProductEntity> findAllProducts();

    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE product_name = :productName")
    Optional<ProductEntity> findProductByName(String productName);

    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE id = :id")
    Optional<ProductEntity> findProductById(UUID id);

}
