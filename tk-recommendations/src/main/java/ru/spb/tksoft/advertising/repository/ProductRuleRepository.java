package ru.spb.tksoft.advertising.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spb.tksoft.advertising.entity.ProductRulePredicateEntity;

/**
 * Репозиторий для сущности ProductRuleEntity.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Repository
public interface ProductRuleRepository extends JpaRepository<ProductRulePredicateEntity, Long> {

}
