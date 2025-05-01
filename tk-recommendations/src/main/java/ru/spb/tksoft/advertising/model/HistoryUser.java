package ru.spb.tksoft.advertising.model;

import java.util.UUID;
import javax.annotation.concurrent.ThreadSafe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * Данные по пользователю из базы транзакций.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
@Value
public class HistoryUser {

    /** Идентификатор пользователя. */
    @NotNull
    private UUID id;

    /** Логин пользователя, ник. */
    @NotEmpty
    private String userName;

    /** Имя пользователя. */
    @NotNull
    private String firstName;

    /** Фамилия пользователя. */
    @NotNull
    private String lastName;

    /**
     * {@inheritDoc}
     */
    @Override
    @NotBlank
    public String toString() {
        return String.format("%s: %s, %s %s",
                id.toString(), userName, firstName, lastName);
    }
}
