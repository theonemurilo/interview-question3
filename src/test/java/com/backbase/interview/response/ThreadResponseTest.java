package com.backbase.interview.response;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import com.backbase.interview.domain.Question;
import com.backbase.interview.fixture.QuestionFixture;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link ThreadResponse}
 */
class ThreadResponseTest {
    
    @Test
    public void should_convert_question_domain_to_thread_response() {
        Question question = QuestionFixture.getQuestionWithinReplies();
        
        ThreadResponse threadResponse = ThreadResponse.fromDomain(question);
        
        assertThat(threadResponse).isNotNull();
        assertThat(threadResponse.getId()).isEqualTo(question.getId());
        assertThat(threadResponse.getAuthor()).isEqualTo(question.getAuthor());
        assertThat(threadResponse.getMessage()).isEqualTo(question.getMessage());
        assertThat(threadResponse.getReplies()).isNotEmpty();
        assertThat(threadResponse.getReplies()).extracting(BasicQuestionResponse::getAuthor)
            .containsAll(question.getReplies().stream().map(Question::getAuthor).collect(toList()));
        assertThat(threadResponse.getReplies()).extracting(BasicQuestionResponse::getId)
            .containsAll(question.getReplies().stream().map(Question::getId).collect(toList()));
        assertThat(threadResponse.getReplies()).extracting(BasicQuestionResponse::getMessage)
            .containsAll(
                question.getReplies().stream().map(Question::getMessage).collect(toList()));
    }
}