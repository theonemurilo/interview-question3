package com.backbase.interview.service;

import static com.backbase.interview.fixture.QuestionFixture.getQuestion;
import static com.backbase.interview.fixture.QuestionFixture.getQuestionWithinParent;
import static com.backbase.interview.fixture.QuestionFixture.getQuestionWithinReplies;
import static com.backbase.interview.fixture.QuestionRequestFixture.getQuestionRequest;
import static com.backbase.interview.fixture.QuestionRequestFixture.getReplyRequest;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.backbase.interview.domain.Question;
import com.backbase.interview.exception.NotFoundException;
import com.backbase.interview.exception.UnprocessableEntityException;
import com.backbase.interview.repository.QuestionsRepository;
import com.backbase.interview.request.QuestionRequest;
import com.backbase.interview.response.QuestionResponse;
import com.backbase.interview.response.ReplyResponse;
import com.backbase.interview.response.ThreadResponse;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link QuestionsService}
 */
@ExtendWith(MockitoExtension.class)
class QuestionsServiceTest {
    
    @InjectMocks
    private QuestionsService service;
    
    @Mock(stubOnly = true)
    private QuestionsRepository repository;
    
    /**
     * Happy path for creating a question
     */
    @Test
    public void should_create_question() {
        QuestionRequest request = getQuestionRequest();
        Question question = getQuestion(1L, request);
        given(repository.save(any(Question.class))).willReturn(question);
        
        QuestionResponse response = service.createQuestion(request);
        
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(question.getId());
        assertThat(response.getAuthor()).isEqualTo(question.getAuthor());
        assertThat(response.getMessage()).isEqualTo(question.getMessage());
        assertThat(response.getReplies()).isEqualTo(0);
    }
    
    /**
     * Happy path for creating a reply of a question
     */
    @Test
    public void should_create_reply() {
        QuestionRequest replyRequest = getReplyRequest();
        Question parentQuestion = getQuestion();
        Question replySaved = getQuestion(2L, replyRequest);
        replySaved.setParentQuestion(parentQuestion);
        given(repository.findById(parentQuestion.getId())).willReturn(of(parentQuestion));
        given(repository.save(any(Question.class))).willReturn(replySaved);
        
        ReplyResponse replyResponse = service.createReply(1L, replyRequest);
        
        assertThat(replyResponse).isNotNull();
        assertThat(replyResponse.getId()).isEqualTo(replySaved.getId());
        assertThat(replyResponse.getAuthor()).isEqualTo(replySaved.getAuthor());
        assertThat(replyResponse.getMessage()).isEqualTo(replySaved.getMessage());
        assertThat(replyResponse.getQuestionId())
            .isEqualTo(replySaved.getParentQuestion().get().getId());
    }
    
    /**
     * Validates if the question does not exist when trying to create a reply
     */
    @Test
    public void should_throw_not_found_when_creating_reply_and_parent_question_does_not_exist() {
        QuestionRequest replyRequest = getReplyRequest();
        Long parentQuestionId = 1L;
        given(repository.findById(parentQuestionId)).willReturn(empty());
        
        assertThrows(NotFoundException.class,
            () -> service.createReply(parentQuestionId, replyRequest));
    }
    
    /**
     * Validates if the question existent is also a reply when creating a reply
     */
    @Test
    public void should_throw_unprocessable_entity_when_creating_reply_and_parent_question_is_also_a_reply() {
        Long parentQuestionId = 1L;
        QuestionRequest replyRequest = getReplyRequest();
        Question question = getQuestionWithinParent();
        given(repository.findById(parentQuestionId)).willReturn(of(question));
        
        assertThrows(UnprocessableEntityException.class,
            () -> service.createReply(parentQuestionId, replyRequest));
    }
    
    /**
     * Happy path for get a thread by question id
     */
    @Test
    public void should_get_thread_by_question_id() {
        Long questionId = 1L;
        Question thread = getQuestionWithinReplies();
        given(repository.findById(1L)).willReturn(of(thread));
        
        ThreadResponse threadResponse = service.getThreadByQuestionId(questionId);
        
        assertThat(threadResponse.getId()).isEqualTo(thread.getId());
        assertThat(threadResponse.getAuthor()).isEqualTo(thread.getAuthor());
        assertThat(threadResponse.getMessage()).isEqualTo(thread.getMessage());
        assertThat(threadResponse.getReplies()).hasSize(thread.getReplies().size());
    }
    
    /**
     * Validates if the question does not exist when getting the thread by question id
     */
    @Test
    public void should_throw_not_found_when_trying_to_get_thread_and_question_does_not_exist() {
        Long questionId = 1L;
        given(repository.findById(1L)).willReturn(empty());
        
        assertThrows(NotFoundException.class, () -> service.getThreadByQuestionId(questionId));
    }
    
    /**
     * Validates if the question existent is actually a reply when trying to get the thread
     */
    @Test
    public void should_throw_unprocessable_entity_when_trying_to_get_thread_and_question_found_is_a_reply() {
        Long questionId = 1L;
        Question question = getQuestionWithinParent();
        given(repository.findById(1L)).willReturn(of(question));
        
        assertThrows(UnprocessableEntityException.class,
            () -> service.getThreadByQuestionId(questionId));
    }
    
    /**
     * Happy path for getting all questions
     */
    @Test
    public void should_get_questions() {
        Question question = getQuestionWithinReplies();
        given(repository.findAll()).willReturn(Collections.singletonList(question));
        
        List<QuestionResponse> questions = service.getQuestions();
        
        assertThat(questions).isNotNull();
        assertThat(questions).isNotEmpty();
        assertThat(questions).extracting(QuestionResponse::getId).containsExactly(question.getId());
        assertThat(questions).extracting(QuestionResponse::getAuthor)
            .containsExactly(question.getAuthor());
        assertThat(questions).extracting(QuestionResponse::getMessage)
            .containsExactly(question.getMessage());
        assertThat(questions).extracting(QuestionResponse::getReplies)
            .containsExactly(question.getReplies().size());
    }
    
}