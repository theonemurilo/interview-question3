package com.backbase.interview.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.backbase.interview.domain.Question;
import com.backbase.interview.fixture.QuestionFixture;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link QuestionResponse}
 */
class QuestionResponseTest {
    
    @Test
    public void should_convert_question_domain_to_question_response() {
        Question question = QuestionFixture.getQuestionWithinReplies();
        
        QuestionResponse questionResponse = QuestionResponse.fromDomain(question);
        
        assertThat(questionResponse).isNotNull();
        assertThat(questionResponse.getId()).isEqualTo(question.getId());
        assertThat(questionResponse.getMessage()).isEqualTo(question.getMessage());
        assertThat(questionResponse.getAuthor()).isEqualTo(question.getAuthor());
        assertThat(questionResponse.getReplies()).isEqualTo(question.getReplies().size());
    }
}