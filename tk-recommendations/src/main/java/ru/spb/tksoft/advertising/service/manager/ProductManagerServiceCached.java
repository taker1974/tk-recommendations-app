package ru.spb.tksoft.advertising.service.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.api.HistoryService;
import ru.spb.tksoft.advertising.api.impl.DynamicApiManagerImpl;
import ru.spb.tksoft.advertising.entity.ProductEntity;
import ru.spb.tksoft.advertising.exception.AddFixedProductException;
import ru.spb.tksoft.advertising.exception.MethodIdentificationException;
import ru.spb.tksoft.advertising.mapper.ManagedProductMapper;
import ru.spb.tksoft.advertising.mapper.ProductHitCounterMapper;
import ru.spb.tksoft.advertising.model.Product;
import ru.spb.tksoft.advertising.model.ProductHitsCounter;
import ru.spb.tksoft.advertising.repository.ProductHitsCounterRepository;
import ru.spb.tksoft.advertising.repository.ProductsRepository;
import ru.spb.tksoft.advertising.tools.LogEx;
import ru.spb.tksoft.recommendations.dto.manager.ManagedProductDto;
import ru.spb.tksoft.recommendations.dto.stat.StatsDto;

/**
 * Кэшированный сервис управления продуктами. Позволяет добавлять, просматривать и удалять продукты
 * с правилами рекомендования.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 * @Service
 */
@Service
@RequiredArgsConstructor
@ThreadSafe
public class ProductManagerServiceCached {

    private static final List<UUID> FIXED_PRODUCTS = Arrays.asList(
            UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
            UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
            UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));

    private static boolean isFixedProduct(@NotNull final UUID productId) {
        return FIXED_PRODUCTS.contains(productId);
    }

    private Logger log = LoggerFactory.getLogger(ProductManagerServiceCached.class);

    @NotNull
    private final ProductsRepository productRepository;

    @NotNull
    private final ProductHitsCounterRepository productHitsCounterRepository;

    @NotNull
    private final HistoryService historyService;

    @CacheEvict(value = "allProducts", allEntries = true)
    public void clearCaches() {
        // ...
    }

    private final Object addProductLock = new Object();

    /**
     * Добавляет рекомендацию в базу данных. Если рекомендация является фиксированной, то она не
     * будет добавлена. В противном случае рекомендация будет добавлена в базу данных после
     * валидации.
     * 
     * Не кэшируется, т.к. это не нужно.
     * 
     * @param newProduct Новый продукт с правилами рекомендования.
     * @return Добавленный продукт с правилами рекомендования.
     */
    @NotNull
    public ManagedProductDto addProduct(@NotNull final ManagedProductDto dto) {

        synchronized (addProductLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, dto.getProductId());

            if (isFixedProduct(dto.getProductId())) {
                throw new AddFixedProductException(dto.getProductId().toString());
            }

            // Здесь надо просто валидировать имена и параметры методов
            // и преобразовать DTO сразу в entity.

            for (var rule : dto.getRule()) {
                if (!DynamicApiManagerImpl.isMethodValidShallow(
                        rule.getQuery(), rule.getArguments())) {
                    throw new MethodIdentificationException(rule.getQuery());
                }
            }

            ProductEntity entity = ManagedProductMapper.toEntity(dto);
            ManagedProductDto dtoSaved = ManagedProductMapper.toDto(productRepository.save(entity));

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
            return dtoSaved;
        }
    }

    private final Object getAllProductsLock = new Object();

    /**
     * Возвращает все рекомендации из базы данных. Все объекты новые и неизменяемые.
     * 
     * В процессе маппирования из Entity в модель ProductRulePredicate внедряется реализация метода,
     * соответствующая запросу.
     * 
     * @return Список всех продуктов с правилами рекомендования.
     */
    @Cacheable(value = "allProducts", unless = "#result.isEmpty()")
    @Transactional
    @NotNull
    public List<Product> getAllProducts() {

        synchronized (getAllProductsLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING);

            List<ProductEntity> entities = productRepository.findAll();
            List<Product> products = new ArrayList<>(entities.size());
            entities.stream().forEach(
                    entity -> products.add(
                            ManagedProductMapper.toModel(entity, historyService)));

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
            return products;
        }
    }

    private final Object deleteProductLock = new Object();

    @CacheEvict(value = "allProducts", allEntries = true)
    public void deleteProduct(@NotNull final UUID productId) {

        synchronized (deleteProductLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN,
                    "recommendationId = " + productId);

            if (!isFixedProduct(productId)) {
                productRepository.deleteById(productId);
            }
        }
    }

    private final Object getStatsLock = new Object();

    public StatsDto getStats() {

        synchronized (getStatsLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING);

            StatsDto dto = new StatsDto(
                    productHitsCounterRepository.findAll().stream().filter(Objects::nonNull)
                            .map(ProductHitCounterMapper::toDto).toList());

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
            return dto;
        }
    }

    private final Object resetStatsLock = new Object();

    public void resetStats() {
        synchronized (resetStatsLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING);

            productHitsCounterRepository.findAll().stream().filter(Objects::nonNull)
                    .forEach(entity -> {

                        ProductHitsCounter model = ProductHitCounterMapper.toModel(entity);
                        model.reset();
                        productHitsCounterRepository
                                .save(ProductHitCounterMapper.toEntity(model, entity.getId()));
                    });

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
        }
    }
}
