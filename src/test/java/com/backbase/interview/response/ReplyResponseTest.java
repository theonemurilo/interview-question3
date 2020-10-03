package com.backbase.interview.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.backbase.interview.domain.Question;
import com.backbase.interview.fixture.QuestionFixture;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link ReplyResponse}
 */
class ReplyResponseTest {
    
    @Test
    public void should_convert_question_domain_to_reply_response() {
        Question question = QuestionFixture.getQuestionWithinParent();
    
        ReplyResponse replyResponse = ReplyResponse.fromDomain(question);
        
        assertThat(replyResponse).isNotNull();
        assertThat(replyResponse.getQuestionId()).isEqualTo(question.getParentQuestion().get().getId());
        assertThat(replyResponse.getId()).isEqualTo(question.getId());
        assertThat(replyResponse.getAuthor()).isEqualTo(question.getAuthor());
        assertThat(replyResponse.getMessage()).isEqualTo(question.getMessage());
    }
}