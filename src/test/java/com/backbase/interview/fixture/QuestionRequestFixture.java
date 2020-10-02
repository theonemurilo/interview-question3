package com.backbase.interview.fixture;

import com.backbase.interview.request.QuestionRequest;

public class QuestionRequestFixture {
    
    private QuestionRequestFixture() {
    }
    
    public static QuestionRequest getQuestionRequest() {
        return new QuestionRequest() {{
            setAuthor("Tony Stark");
            setMessage("Where's my suit?");
        }};
    }
    
    public static QuestionRequest getReplyRequest() {
        return new QuestionRequest() {{
            setAuthor("Pepper Potts");
            setMessage("Beats me!");
        }};
    }
}
