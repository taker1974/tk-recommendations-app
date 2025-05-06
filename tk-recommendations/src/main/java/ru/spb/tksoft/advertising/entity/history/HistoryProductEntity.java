package ru.spb.tksoft.advertising.entity.history;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Данные по продукту из базы транзакций.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@AllArgsConstructor
public class HistoryProductEntity {

    /** Идентификатор продукта. */
    private UUID id;

    /** Тип продукта. */
    private String type;
    /** Название продукта. */
    private String name;
}
