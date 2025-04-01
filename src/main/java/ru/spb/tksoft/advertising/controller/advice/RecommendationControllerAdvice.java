package ru.spb.tksoft.advertising.controller.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.spb.tksoft.advertising.dto.ErrorResponseDto;
import ru.spb.tksoft.advertising.exception.HistoryUserNotFoundException;

/**
 * Перехват исключений RecommendationController.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RecommendationControllerAdvice extends AbstractBaseControllerAdvice {

    @ExceptionHandler(HistoryUserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleStudentNotFoundException(
            HistoryUserNotFoundException e) {
        return new ResponseEntity<>(
                new ErrorResponseDto(HistoryUserNotFoundException.CODE, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
