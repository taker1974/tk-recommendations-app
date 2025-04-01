package ru.spb.tksoft.advertising.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spb.tksoft.advertising.entity.RuleEntity;

/**
 * Репозиторий для сущности Rule.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Repository
public interface RuleRepository extends JpaRepository<RuleEntity, Long> {

}
