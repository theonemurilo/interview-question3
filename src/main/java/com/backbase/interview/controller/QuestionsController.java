package com.backbase.interview.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.backbase.interview.request.QuestionRequest;
import com.backbase.interview.response.QuestionResponse;
import com.backbase.interview.response.ReplyResponse;
import com.backbase.interview.response.ThreadResponse;
import com.backbase.interview.service.QuestionsService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class responsible to control the requests for {@link com.backbase.interview.domain.Question}
 * operations.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionsController {
    
    private final QuestionsService service;
    
    @PostMapping
    @ResponseStatus(CREATED)
    public QuestionResponse createQuestion(@RequestBody @Valid QuestionRequest questionRequest) {
        return service.createQuestion(questionRequest);
    }
    
    @PostMapping("/{questionId}")
    @ResponseStatus(CREATED)
    public ReplyResponse createReply(@PathVariable("questionId") Long questionId,
        @RequestBody @Valid QuestionRequest questionRequest) {
        return service.createReply(questionId, questionRequest);
    }
    
    @GetMapping("/{questionId}")
    public ThreadResponse getThread(@PathVariable("questionId") Long questionId) {
        return service.getThreadByQuestionId(questionId);
    }
    
    @GetMapping
    public List<QuestionResponse> getQuestions() {
        return service.getQuestions();
    }
    
}
