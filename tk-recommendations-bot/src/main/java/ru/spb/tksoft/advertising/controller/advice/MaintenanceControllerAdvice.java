package ru.spb.tksoft.advertising.controller.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.spb.tksoft.recommendations.controller.advice.AbstractBaseControllerAdvice;

/**
 * Перехват исключений MaintenanceController.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MaintenanceControllerAdvice extends AbstractBaseControllerAdvice {

    /**
     * Конструктор по умолчанию.
     */
    private MaintenanceControllerAdvice() {
        super();
    }
}
