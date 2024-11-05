package com.learn.different_db_fetches.advice;

import com.learn.different_db_fetches.enums.FetchType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method was introduced to prevent from getting error when user enters an invalid fetch type.
     * In such a situation MethodArgumentTypeMismatchException is thrown and it get caught here.
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
            .badRequest()
            .body("Invalid value for status. Allowed values are: " + Arrays.toString(FetchType.values()));
    }
}