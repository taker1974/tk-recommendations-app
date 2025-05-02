package ru.spb.tksoft.recommendations.dto.stat;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Контейнер для передачи данных статистики.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {

    @NotNull
    private List<ProductHitCounterDto> stats = new ArrayList<>();
}
