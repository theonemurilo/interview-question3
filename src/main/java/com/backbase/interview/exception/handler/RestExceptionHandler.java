package com.backbase.interview.exception.handler;

import static java.util.stream.Collectors.joining;

import com.backbase.interview.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handler responsible to map exceptions in the application and return a user friendly response to
 * consumers.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Autowired
    private ObjectMapper mapper;
    
    /**
     * Handle business exceptions of the application.
     */
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleError(Exception ex, WebRequest request)
        throws JsonProcessingException {
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        
        ErrorResponse exceptionResponse = new ErrorResponse(ex.getMessage(),
            responseStatus.code().value());
        
        String bodyOfResponse = mapper.writeValueAsString(exceptionResponse);
        return handleExceptionInternal(
            ex, bodyOfResponse, new HttpHeaders(), responseStatus.code(), request);
    }
    
    /**
     * Handle request validations for the application validated by javax.validations annotations.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String messages = fieldErrors.stream()
            .filter(Objects::nonNull)
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(joining(", "));
        
        return new ResponseEntity<>(new ErrorResponse(messages, status.value()), status);
    }
}
