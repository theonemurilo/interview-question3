package com.backbase.interview.response;

import static com.backbase.interview.fixture.QuestionFixture.getQuestionWithinReplies;
import static org.assertj.core.api.Assertions.assertThat;

import com.backbase.interview.domain.Question;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link BasicQuestionResponse}
 */
class BasicQuestionResponseTest {
    
    @Test
    public void should_convert_question_domain_to_basic_response() {
        Question question = getQuestionWithinReplies();
        
        BasicQuestionResponse response = BasicQuestionResponse.fromDomain(question);
        
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(question.getId());
        assertThat(response.getAuthor()).isEqualTo(question.getAuthor());
        assertThat(response.getMessage()).isEqualTo(question.getMessage());
    }
    
}