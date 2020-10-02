package com.backbase.interview.mother;

import com.backbase.interview.request.QuestionRequest;

public class QuestionRequestMother {
    
    private QuestionRequestMother() {
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
