package com.backbase.interview.request;

import com.backbase.interview.domain.Question;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionRequest {
    
    @NotBlank
    private String author;
    
    @NotBlank
    private String message;
    
    public Question toDomain() {
        Question question = new Question();
        question.setAuthor(author);
        question.setMessage(message);
        return question;
    }
}
