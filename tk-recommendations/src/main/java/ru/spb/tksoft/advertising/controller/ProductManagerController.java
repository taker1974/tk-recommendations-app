package ru.spb.tksoft.advertising.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.mapper.ManagedProductMapper;
import ru.spb.tksoft.advertising.service.manager.ProductManagerServiceCached;
import ru.spb.tksoft.recommendations.dto.manager.ManagedProductDto;
import ru.spb.tksoft.recommendations.dto.manager.ManagedProductListDto;
import ru.spb.tksoft.recommendations.dto.stat.StatsDto;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Управление рекомендациями.
 * 
 * Реализация CRUD для продуктов и правил.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@RestController
@RequestMapping(value = "/rule")
@Tag(name = "Управление правилами рекомендования")
@RequiredArgsConstructor
public class ProductManagerController {

    private final ProductManagerServiceCached managerService;

    /**
     * Добавление продукта.
     * 
     * @param dto Данные по продукту.
     * @return Добавленный продукт.
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Добавить продукт")
    @PostMapping("/add")
    public ManagedProductDto addProduct(@Valid @RequestBody ManagedProductDto dto) {

        // См. пример запроса в конце Technical-task-phase-2.md
        return managerService.addProduct(dto);
    }

    /**
     * Список всех продуктов.
     * 
     * @return Список продуктов.
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить данные по всем продуктам")
    @GetMapping
    public ManagedProductListDto getAllProducts() {

        return new ManagedProductListDto(managerService.getAllProducts().stream()
                .map(ManagedProductMapper::toDto).toList());
    }

    /**
     * Удаление продукта.
     * 
     * @param productId Идентификатор продукта.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить продукт")
    @DeleteMapping(value = "/{productId}")
    public void deleteProduct(@Valid @PathVariable final UUID productId) {

        managerService.deleteProduct(productId);
    }

    /**
     * Получение статистики по срабатываниям правил рекомендаций.
     * 
     * @return Статистика по срабатываниям.
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить статистику по срабатываниям правил рекомендаций")
    @GetMapping("/stats")
    public StatsDto getStats() {

        return managerService.getStats();
    }

    /**
     * Сброс статистики по срабатываниям правил рекомендаций.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Сбросить статистику по срабатываниям правил рекомендаций")
    @PostMapping("/stats/reset")
    public void resetStats() {

        managerService.resetStats();
    }
}
