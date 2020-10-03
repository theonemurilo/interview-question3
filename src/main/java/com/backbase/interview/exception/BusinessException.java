package com.backbase.interview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception which maps HTTP 500 code.
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
}
