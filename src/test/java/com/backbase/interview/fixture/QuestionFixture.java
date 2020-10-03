package com.backbase.interview.fixture;

import static java.util.Arrays.asList;

import com.backbase.interview.domain.Question;
import com.backbase.interview.request.QuestionRequest;

/**
 * Fixture for {@link Question}. Use this class to create {@link Question} objects and use them on
 * your tests.
 */
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
    
    public static Question getQuestion(Long questionId, Question parent) {
        return new Question() {{
            setId(questionId);
            setAuthor("Pepper Pots");
            setMessage("Beats me!");
            setParentQuestion(parent);
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
    
    public static Question getQuestionWithinParent(Long questionId, Question parent) {
        return new Question() {{
            setId(questionId);
            setAuthor("Pepper Pots");
            setMessage("Beats me!");
            setParentQuestion(parent);
        }};
    }
    
    public static Question getQuestionWithinReplies() {
        return new Question() {{
            setId(1L);
            setAuthor("Tony Stark");
            setMessage("Where's my suit?");
            setReplies(asList(getQuestion(2L, this), getQuestion(3L, this)));
        }};
    }
    
    public static Question getQuestionWithinReplies(Long questionId) {
        return new Question() {{
            setId(questionId);
            setAuthor("Tony Stark");
            setMessage("Where's my suit?");
            setReplies(
                asList(getQuestion(questionId + 1, this), getQuestion(questionId + 2, this)));
        }};
    }
    
    public static Question getQuestion(Long questionId, QuestionRequest request) {
        return new Question() {{
            setId(questionId);
            setAuthor(request.getAuthor());
            setMessage(request.getMessage());
        }};
    }
    
    public static Question getQuestionWithinParent(Long questionId, Question parent,
        QuestionRequest request) {
        return new Question() {{
            setId(questionId);
            setAuthor(request.getAuthor());
            setMessage(request.getMessage());
            setParentQuestion(parent);
        }};
    }
}
