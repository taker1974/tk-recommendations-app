package ru.spb.tksoft.advertising.entity.history;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Данные по транзакции из базы транзакций.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@AllArgsConstructor
public class HistoryTransactionEntity {

    private UUID id;

    private UUID productId;
    private UUID userId;

    private String type;
    private int amount;
}
