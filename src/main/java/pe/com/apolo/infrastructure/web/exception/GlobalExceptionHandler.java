package pe.com.apolo.infrastructure.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.com.apolo.domain.exception.*;
import pe.com.apolo.infrastructure.web.dto.response.ErrorResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            BookNotAvailableException.class,
            BookAlreadyAvailableException.class,
            LoanAlreadyReturnedException.class,
            FineAlreadyPaidException.class,
            InactiveUserException.class,
            MaxActiveLoansExceededException.class,
            UnderageUserException.class,
            UserHasPendingFinesException.class
    })
    public ErrorResponse handleBusinessException(
            RuntimeException ex,
            HttpServletRequest request
    ) {

        return new ErrorResponse(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.BAD_REQUEST.value(),
                "Business Rule Violation",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {

        return new ErrorResponse(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Argument",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation error");

        return new ErrorResponse(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                message,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleUnknown(
            Exception ex,
            HttpServletRequest request
    ) {

        return new ErrorResponse(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}