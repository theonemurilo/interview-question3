package com.backbase.interview.response;

import com.backbase.interview.domain.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * Basic Object for all Question Responses.
 */
@Data
@JsonInclude(Include.NON_NULL)
public class BasicQuestionResponse {
    
    private Long id;
    private String author;
    private String message;
    
    public static BasicQuestionResponse fromDomain(Question question) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setAuthor(question.getAuthor());
        questionResponse.setMessage(question.getMessage());
        
        return questionResponse;
    }
}
