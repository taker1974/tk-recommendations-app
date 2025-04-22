package ru.spb.tksoft.recommendations.dto.maintenance;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информация о приложении (о сервисе).
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceInfoDto {

    @NotBlank
    private String name;

    @NotBlank
    private String version;
}
