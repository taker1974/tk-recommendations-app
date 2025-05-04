package ru.spb.tksoft.advertising.controller.advice;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.spb.tksoft.recommendations.controller.advice.CommonControllerAdvice;

/**
 * Перехват базовых, стандартных исключений на уровне контроллера. ВАЖЕН ПОРЯДОК
 * ПЕРЕХВАТА! @see @Order()
 * 
 * @see ru.spb.tksoft.recommendations.controller.advice.CommonControllerAdvice
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ControllerAdvice
@Order()
public class BotCommonControllerAdvice extends CommonControllerAdvice {

    /**
     * Конструктор по умолчанию.
     */
    private BotCommonControllerAdvice() {
        super();
    }
}
