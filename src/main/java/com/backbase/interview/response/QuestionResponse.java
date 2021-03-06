package com.backbase.interview.response;

import com.backbase.interview.domain.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * Sub Question Response Object (see {@link BasicQuestionResponse}) which also includes the number
 * of replies.
 */
@Data
@JsonInclude(Include.NON_NULL)
public class QuestionResponse extends BasicQuestionResponse {
    
    private Integer replies;
    
    public static QuestionResponse fromDomain(Question question) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setAuthor(question.getAuthor());
        questionResponse.setMessage(question.getMessage());
        questionResponse.setReplies(question.getReplies().size());
        
        return questionResponse;
    }
}
