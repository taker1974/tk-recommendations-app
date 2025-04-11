package ru.spb.tksoft.advertising.service.manager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.ProductEntity;
import ru.spb.tksoft.advertising.mapper.ManagedProductMapper;
import ru.spb.tksoft.advertising.model.Product;
import ru.spb.tksoft.advertising.repository.ProductsRepository;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Кэшированный сервис управления продуктами. Позволяет добавлять, просматривать и удалять продукты
 * с правилами рекомендования.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@RequiredArgsConstructor
@ThreadSafe
public class ProductManagerServiceCached {

    private Logger log = LoggerFactory.getLogger(ProductManagerServiceCached.class);

    @NotNull
    private final ProductsRepository productRepository;

    private static final List<UUID> FIXED_PRODUCTS = Arrays.asList(
            UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
            UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
            UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));

    private static boolean isFixedProduct(@NotNull final UUID productId) {

        return FIXED_PRODUCTS.contains(productId);
    }

    /**
     * Очистка кэша.
     */
    @CacheEvict(value = "products", allEntries = true)
    public void clearCacheAll() {
        // ...
    }

    private final Object addProductLock = new Object();

    /**
     * Добавляет рекомендацию в базу данных. Если рекомендация является фиксированной, то она не
     * будет добавлена. В противном случае рекомендация будет добавлена в базу данных после
     * валидации.
     * 
     * @param newProduct Новый продукт с правилами рекомендования.
     * @return Добавленный продукт с правилами рекомендования.
     */
    @CachePut(value = "products")
    @NotNull
    public Product addProduct(@NotNull final Product newProduct) {

        synchronized (addProductLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, newProduct);

            if (isFixedProduct(newProduct.getId())) {
                LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING,
                        "Попытка добавления фиксированной рекомендации: " + newProduct.getId());
                return newProduct;
            }

            // for (var rule : recommendation.getRules()) {
            // if (!isValidRmi(rule.getQuery())) {
            // LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING,
            // "Попытка добавления рекомендации с недопустимым именем метода: "
            // + rule.getQuery());
            // return recommendation;
            // }
            // }

            ProductEntity entity = ManagedProductMapper.toEntity(newProduct);
            ProductEntity saved = productRepository.save(entity);
            Product savedModel = ManagedProductMapper.toModel(saved);

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
            return savedModel;
        }
    }

    private final Object getAllProductsLock = new Object();

    /**
     * @return Список всех продуктов с правилами рекомендования.
     */
    @Cacheable(value = "products", unless = "#result.isEmpty()")
    @Transactional
    @NotNull
    public List<Product> getAllProducts() {

        synchronized (getAllProductsLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING);

            List<ProductEntity> entities = productRepository.findAll();
            List<Product> products = entities.stream()
                    .map(ManagedProductMapper::toModel).toList();

            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
            return products;
        }
    }

    private final Object deleteProductLock = new Object();

    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(@NotNull final UUID productId) {

        synchronized (deleteProductLock) {
            LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN,
                    "recommendationId = " + productId);

            if (!isFixedProduct(productId)) {
                productRepository.deleteById(productId);
            }
        }
    }
}
