package com.backbase.interview.service;

import com.backbase.interview.request.QuestionRequest;
import com.backbase.interview.response.QuestionResponse;
import com.backbase.interview.response.ReplyResponse;
import com.backbase.interview.response.ThreadResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionsService {
    
    public QuestionResponse createQuestion(QuestionRequest questionRequest) {
        return null;
    }
    
    public ReplyResponse createReply(QuestionRequest questionRequest) {
        return null;
    }
    
    public ThreadResponse getThreadByQuestionId(Long questionId) {
        return null;
    }
    
    public List<QuestionResponse> getQuestions() {
        return null;
    }
}
