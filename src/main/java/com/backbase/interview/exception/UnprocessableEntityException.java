package com.backbase.interview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends BusinessException {
    
    public UnprocessableEntityException(String message) {
        super(message);
    }
}