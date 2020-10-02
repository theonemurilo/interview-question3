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

@AllArgsConstructor
@Transactional
@Service
public class QuestionsService {
    
    private final QuestionsRepository repository;
    
    public QuestionResponse createQuestion(QuestionRequest questionRequest) {
        Question question = repository.save(questionRequest.toDomain());
        
        return QuestionResponse.fromDomain(question);
    }
    
    public ReplyResponse createReply(Long questionId, QuestionRequest questionRequest) {
        Question question = findQuestionById(questionId);
    
        validateIfNotThread(question);
    
        Question reply = questionRequest.toDomain();
        reply.setParentQuestion(question);
        Question savedReply = repository.save(reply);
        
        return ReplyResponse.fromDomain(savedReply);
    }
    
    @Transactional(readOnly = true)
    public ThreadResponse getThreadByQuestionId(Long questionId) {
        Question question = findQuestionById(questionId);
        
        validateIfNotThread(question);
        
        return ThreadResponse.fromDomain(question);
    }
    
    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestions() {
        return repository.findAll().stream().map(QuestionResponse::fromDomain).collect(toList());
    }
    
    private Question findQuestionById(Long questionId) {
        return repository.findById(questionId).orElseThrow(() ->
            new NotFoundException(format("questionId=%d does not exist.", questionId)));
    }
    
    private void validateIfNotThread(Question question) {
        if (question.isReply()) {
            throw new UnprocessableEntityException("you cannot reply another reply.");
        }
    }
}
