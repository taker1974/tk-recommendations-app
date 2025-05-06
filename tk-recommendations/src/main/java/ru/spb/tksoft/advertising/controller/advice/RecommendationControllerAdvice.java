package ru.spb.tksoft.advertising.controller.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.spb.tksoft.recommendations.exception.HistoryUserNotFoundException;
import ru.spb.tksoft.recommendations.controller.advice.AbstractBaseControllerAdvice;
import ru.spb.tksoft.recommendations.dto.service.ServiceErrorResponseDto;

/**
 * Перехват исключений RecommendationController.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RecommendationControllerAdvice extends AbstractBaseControllerAdvice {

    /**
     * Конструктор по умолчанию.
     */
    private RecommendationControllerAdvice() {
        super();
    }

    // TODO Специфически обработать все исключения, достигающие уровня контроллера.

    /**
     * Обработка исключения при отсутствии пользователя в истории запросов.
     * 
     * @param e Исключение.
     * @return DTO ошибки.
     */
    @ExceptionHandler(HistoryUserNotFoundException.class)
    public ResponseEntity<ServiceErrorResponseDto> handleHistoryUserNotFoundException(
            HistoryUserNotFoundException e) {

        return new ResponseEntity<>(
                new ServiceErrorResponseDto(HistoryUserNotFoundException.CODE, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
