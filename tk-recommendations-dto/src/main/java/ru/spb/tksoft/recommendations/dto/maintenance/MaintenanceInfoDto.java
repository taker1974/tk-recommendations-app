package ru.spb.tksoft.recommendations.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информация о приложении (о сервисе).
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"name", "version"})
public class MaintenanceInfoDto {

    /** Идентификатор приложения.*/
    @NotBlank
    private String name;

    /** Версия приложения.*/
    @NotBlank
    private String version;
}
