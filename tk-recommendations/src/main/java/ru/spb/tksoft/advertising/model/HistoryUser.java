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

    @NotNull
    private UUID id;

    @NotEmpty
    private String userName;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Override
    @NotBlank
    public String toString() {
        return String.format("%s: %s, %s %s",
                id.toString(), userName, firstName, lastName);
    }
}
