package com.backbase.interview.fixture;

import static java.util.Arrays.asList;

import com.backbase.interview.domain.Question;
import com.backbase.interview.request.QuestionRequest;

public class QuestionFixture {
    
    private QuestionFixture() {
    }
    
    public static Question getQuestion() {
        return new Question() {{
            setId(1L);
            setAuthor("Tony Stark");
            setMessage("Where's my suit?");
        }};
    }
    
    public static Question getQuestionWithinParent() {
        return new Question() {{
            setId(2L);
            setAuthor("Pepper Pots");
            setMessage("Beats me!");
            setParentQuestion(getQuestion());
        }};
    }
    
    public static Question getQuestionWithinReplies() {
        return new Question() {{
            setId(2L);
            setAuthor("Pepper Pots");
            setMessage("Beats me!");
            setReplies(asList(getQuestion(), getQuestion()));
        }};
    }
    
    public static Question getQuestion(Long questionId, QuestionRequest request) {
        return new Question() {{
            setId(questionId);
            setAuthor(request.getAuthor());
            setMessage(request.getMessage());
        }};
    }
}
