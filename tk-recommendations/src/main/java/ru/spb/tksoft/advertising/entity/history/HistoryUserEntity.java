package ru.spb.tksoft.advertising.entity.history;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Данные по пользователю из базы транзакций.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@AllArgsConstructor
public class HistoryUserEntity {

    /** Идентификатор продукта. */
    @JsonProperty("user_id")
    private UUID id;

    /** Логин пользователя, ник.  */
    @JsonProperty("user_name")
    private String userName;

    /** Имя пользователя. */
    @JsonProperty("first_name")
    private String firstName;

    /** Фамилия пользователя. */
    @JsonProperty("last_name")
    private String lastName;
}
