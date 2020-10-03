package com.backbase.interview.response;

import static java.util.stream.Collectors.toList;

import com.backbase.interview.domain.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/**
 * Sub Question Response Object (see {@link BasicQuestionResponse}) which also includes all of its
 * replies ({@link BasicQuestionResponse}).
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ThreadResponse extends BasicQuestionResponse {
    
    private List<BasicQuestionResponse> replies;
    
    public static ThreadResponse fromDomain(Question question) {
        ThreadResponse threadResponse = new ThreadResponse();
        threadResponse.setId(question.getId());
        threadResponse.setAuthor(question.getAuthor());
        threadResponse.setMessage(question.getMessage());
        threadResponse.setReplies(
            question.getReplies().stream().map(BasicQuestionResponse::fromDomain)
                .collect(toList()));
        
        return threadResponse;
    }
}
