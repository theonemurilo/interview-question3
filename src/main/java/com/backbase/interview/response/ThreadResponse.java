package com.backbase.interview.response;

import static java.util.stream.Collectors.toList;

import com.backbase.interview.domain.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ThreadResponse {
    
    private Long id;
    private String author;
    private String message;
    private List<ReplyResponse> replies;
    
    public static ThreadResponse fromDomain(Question question) {
        ThreadResponse threadResponse = new ThreadResponse();
        threadResponse.setId(question.getId());
        threadResponse.setAuthor(question.getAuthor());
        threadResponse.setMessage(question.getMessage());
        threadResponse.setReplies(
            question.getReplies().stream().map(ReplyResponse::fromDomain).collect(toList()));
        
        return threadResponse;
    }
}
