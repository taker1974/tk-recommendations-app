package ru.spb.tksoft.advertising.entity.history;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Данные по транзакции из базы транзакций.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@AllArgsConstructor
public class HistoryTransactionEntity {

    /** Уникальный идентификатор БД записи о продукте. Не идентификатор продукта. */
    private UUID id;

    /** Идентификатор продукта. */
    private UUID productId;
    /** Идентификатор пользователя. */
    private UUID userId;

    /** Тип транзакции. */
    private String type;
    /** Сумма транзакции. */
    private int amount;
}
