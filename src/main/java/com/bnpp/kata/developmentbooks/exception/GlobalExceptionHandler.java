package com.bnpp.kata.developmentbooks.exception;

import com.bnpp.kata.developmentbooks.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidBookException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBasket(InvalidBookException ex) {
        ErrorResponse error = new ErrorResponse("INVALID_BOOK", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

}
