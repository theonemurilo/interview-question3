package com.backbase.interview.service;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import com.backbase.interview.domain.Question;
import com.backbase.interview.exception.NotFoundException;
import com.backbase.interview.exception.UnprocessableEntityException;
import com.backbase.interview.repository.QuestionsRepository;
import com.backbase.interview.request.QuestionRequest;
import com.backbase.interview.response.QuestionResponse;
import com.backbase.interview.response.ReplyResponse;
import com.backbase.interview.response.ThreadResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service that manages all Question Operations.
 */
@AllArgsConstructor
@Transactional
@Service
public class QuestionsService {
    
    private final QuestionsRepository repository;
    
    /**
     * Creates a {@link Question}.
     *
     * @return a created {@link Question} converted in the {@link QuestionResponse}
     */
    public QuestionResponse createQuestion(QuestionRequest questionRequest) {
        Question question = repository.save(questionRequest.toDomain());
        
        return QuestionResponse.fromDomain(question);
    }
    
    /**
     * Creates a reply for an existent {@link Question}
     *
     * @param questionId the existent {@link Question}
     * @return a created reply {@link Question} converted in the {@link ReplyResponse}
     */
    public ReplyResponse createReply(Long questionId, QuestionRequest questionRequest) {
        Question question = findQuestionById(questionId);
        
        if (question.isReply()) {
            throw new UnprocessableEntityException("it is not possible reply another reply!");
        }
        
        Question reply = questionRequest.toDomain();
        reply.setParentQuestion(question);
        Question savedReply = repository.save(reply);
        
        return ReplyResponse.fromDomain(savedReply);
    }
    
    /**
     * Return a {@link Question} and its replies
     *
     * @param questionId the question to be returned
     * @return a {@link Question} within all its replies converted in {@link ThreadResponse}
     */
    @Transactional(readOnly = true)
    public ThreadResponse getThreadByQuestionId(Long questionId) {
        Question question = findQuestionById(questionId);
        
        if (question.isReply()) {
            throw new UnprocessableEntityException(
                format("the questionId=%d is a reply, not a thread!", questionId));
        }
        
        return ThreadResponse.fromDomain(question);
    }
    
    /**
     * Returns all questions
     *
     * @return a {@link List} of {@link QuestionResponse}
     */
    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestions() {
        return repository.findAll().stream().map(QuestionResponse::fromDomain).collect(toList());
    }
    
    private Question findQuestionById(Long questionId) {
        return repository.findById(questionId).orElseThrow(() ->
            new NotFoundException(format("questionId=%d does not exist.", questionId)));
    }
}
