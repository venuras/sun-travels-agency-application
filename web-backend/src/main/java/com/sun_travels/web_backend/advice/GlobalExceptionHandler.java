package com.sun_travels.web_backend.advice;

import com.sun_travels.web_backend.COMPILE_CONDITION;
import com.sun_travels.web_backend.exception.ContractNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private Map<String, List<String>> getErrosMap(List<String> errors)
    {
        if(COMPILE_CONDITION.DEBUG_LOGGING)
        {
            errors.forEach(error -> logger.error(error));
        }
        Map<String, List<String>> errorsMap = new HashMap<>();
        errorsMap.put("errors", errors);
        return errorsMap;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException exception)
    {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        return new ResponseEntity<>(getErrosMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleContractNoteFoundException(ContractNotFoundException exception)
    {
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ResponseEntity<>(getErrosMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, List<String>>> handleRuntimeException(RuntimeException exception)
    {
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ResponseEntity<>(getErrosMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, List<String>>> handleException(Exception exception)
    {
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ResponseEntity<>(getErrosMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
