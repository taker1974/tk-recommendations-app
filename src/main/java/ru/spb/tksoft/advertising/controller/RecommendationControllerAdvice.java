package ru.spb.tksoft.advertising.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.spb.tksoft.advertising.dto.ErrorResponseDto;
import ru.spb.tksoft.advertising.exception.UserNotFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RecommendationControllerAdvice extends AbstractBaseControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleStudentNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponseDto(UserNotFoundException.CODE, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
