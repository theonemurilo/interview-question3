package com.backbase.interview.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response Object to map business errors of the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    
    private String message;
    private int code;
}
