package ru.spb.tksoft.advertising.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.ProductEntity;
import ru.spb.tksoft.advertising.mapper.ManagedProductMapper;
import ru.spb.tksoft.advertising.model.Product;
import ru.spb.tksoft.advertising.repository.ProductsRepository;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Сервис управления рекомендациями. Позволяет добавлять, просматривать и удалять рекомендации с
 * правилами рекомендования.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@RequiredArgsConstructor
public class RecommendationManagerService {

    private Logger log = LoggerFactory.getLogger(RecommendationManagerService.class);

    private final ProductsRepository productRepository;

    public Product addProduct(final Product recommendation) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, recommendation);

        ProductEntity entity = ManagedProductMapper.toEntity(recommendation);
        ProductEntity saved = productRepository.save(entity);
        Product savedModel = ManagedProductMapper.toModel(saved);

        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
        return savedModel;
    }

    public List<Product> getAllProducts() {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING);

        List<ProductEntity> entities = productRepository.findAll();
        List<Product> products = entities.stream().map(ManagedProductMapper::toModel).toList();

        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
        return products;
    }

    public void deleteProduct(final UUID productId) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN,
                "recommendationId = " + productId);

        productRepository.deleteById(productId);
    }
}
