package com.backbase.interview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception responsible to map the HTTP 404 code.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends BusinessException {
    
    public NotFoundException(String message) {
        super(message);
    }
}
