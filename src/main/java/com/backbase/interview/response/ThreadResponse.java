package com.backbase.interview.response;

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
}
