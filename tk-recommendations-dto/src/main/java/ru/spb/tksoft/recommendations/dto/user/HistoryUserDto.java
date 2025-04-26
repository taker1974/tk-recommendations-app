package ru.spb.tksoft.recommendations.dto.user;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные по пользователю из базы транзакций.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"user_id", "user_name", "first_name", "last_name"})
public class HistoryUserDto {

    @NotNull
    @JsonProperty("user_id")
    private UUID id;

    @NotBlank
    @JsonProperty("user_name")
    private String userName;

    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank
    @JsonProperty("last_name")
    private String lastName;
}
