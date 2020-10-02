package com.backbase.interview.response;

import com.backbase.interview.domain.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ReplyResponse {
    
    private Long id;
    private Long questionId;
    private String author;
    private String message;
    
    public static ReplyResponse fromDomain(Question question) {
        ReplyResponse replyResponse = new ReplyResponse();
        replyResponse.setId(question.getId());
        question.getParentQuestion().ifPresent(q -> replyResponse.setQuestionId(q.getId()));
        replyResponse.setAuthor(question.getAuthor());
        replyResponse.setMessage(question.getMessage());
        
        return replyResponse;
    }
}
