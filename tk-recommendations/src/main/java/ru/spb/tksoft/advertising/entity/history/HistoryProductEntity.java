package ru.spb.tksoft.advertising.entity.history;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Данные по продукту из базы транзакций.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@AllArgsConstructor
public class HistoryProductEntity {

    private UUID id;

    private String type;
    private String name;
}
