package ru.spb.tksoft.advertising.controller.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.spb.tksoft.recommendations.dto.service.ServiceErrorResponseDto;
import java.util.Arrays;

/**
 * Перехват базовых, стандартных исключений на уровне контроллера. ВАЖЕН ПОРЯДОК
 * ПЕРЕХВАТА! @see @Order()
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ControllerAdvice
@Order()
public class CommonControllerAdvice extends AbstractBaseControllerAdvice {

    /** Код исключения по умолчанию. */
    public static final int E_CODE = 160;

    /**
     * Обработка исключения по умолчанию.
     * 
     * @param e Исключение.
     * @return DTO ошибки.
     */
    @ExceptionHandler(Exception.class)
    @Order()
    public ResponseEntity<ServiceErrorResponseDto> handleException(Exception e) {
        return new ResponseEntity<>(
                new ServiceErrorResponseDto(E_CODE, e.getMessage(),
                        Arrays.toString(e.getStackTrace())),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Код исключения RuntimeException. */
    public static final int RTE_CODE = 427;

    /**
     * Обработка исключения RuntimeException.
     * 
     * @param e Исключение.
     * @return DTO ошибки.
     */
    @ExceptionHandler(RuntimeException.class)
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    public ResponseEntity<ServiceErrorResponseDto> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(
                new ServiceErrorResponseDto(RTE_CODE, e.getMessage(),
                        Arrays.toString(e.getStackTrace())),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Код исключения NullPointerException. */
    public static final int NPE_CODE = 467;

    /**
     * Обработка исключения NullPointerException.
     * 
     * @param e Исключение.
     * @return DTO ошибки.
     */
    @ExceptionHandler(NullPointerException.class)
    @Order(Ordered.LOWEST_PRECEDENCE - 2)
    public ResponseEntity<ServiceErrorResponseDto> handleNpe(NullPointerException e) {
        return new ResponseEntity<>(
                new ServiceErrorResponseDto(NPE_CODE, e.getMessage(),
                        Arrays.toString(e.getStackTrace())),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Код исключения IllegalArgumentException. */
    public static final int IAE_CODE = 881;

    /**
     * Обработка исключения IllegalArgumentException.
     * 
     * @param e Исключение.
     * @return DTO ошибки.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @Order(Ordered.LOWEST_PRECEDENCE - 3)
    public ResponseEntity<ServiceErrorResponseDto> handleIAE(IllegalArgumentException e) {
        return new ResponseEntity<>(
                new ServiceErrorResponseDto(IAE_CODE, e.getMessage(),
                        Arrays.toString(e.getStackTrace())),
                HttpStatus.NOT_ACCEPTABLE);
    }
}
