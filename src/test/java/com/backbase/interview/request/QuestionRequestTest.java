package com.backbase.interview.request;

import static com.backbase.interview.fixture.QuestionRequestFixture.getQuestionRequest;
import static org.assertj.core.api.Assertions.assertThat;

import com.backbase.interview.domain.Question;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link QuestionRequest}
 */
class QuestionRequestTest {
    
    @Test
    public void should_convert_question_request_to_domain() {
        QuestionRequest questionRequest = getQuestionRequest();
        
        Question question = questionRequest.toDomain();
        
        assertThat(question).isNotNull();
        assertThat(question.getAuthor()).isEqualTo(questionRequest.getAuthor());
        assertThat(question.getMessage()).isEqualTo(questionRequest.getMessage());
    }
}