package com.backbase.interview.controller;

import static com.backbase.interview.fixture.QuestionFixture.getQuestion;
import static com.backbase.interview.fixture.QuestionRequestFixture.getQuestionRequest;
import static com.backbase.interview.fixture.QuestionRequestFixture.getReplyRequest;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backbase.interview.QuestionsApplicationTests;
import com.backbase.interview.domain.Question;
import com.backbase.interview.repository.QuestionsRepository;
import com.backbase.interview.request.QuestionRequest;
import com.backbase.interview.response.QuestionResponse;
import com.backbase.interview.response.ReplyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integrated test for {@link QuestionsController}. </br> All layers (service and repository) being
 * tested
 */
@AutoConfigureMockMvc
public class QuestionsControllerIT extends QuestionsApplicationTests {
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private QuestionsRepository repository;
    
    @Autowired
    private ObjectMapper mapper;
    
    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }
    
    /**
     * Happy path for creating a question
     */
    @Test
    public void should_call_create_question() throws Exception {
        QuestionRequest request = getQuestionRequest();
        Question question = getQuestion(1L, request);
        String payload = mapper.writeValueAsString(request);
        
        mvc.perform(post("/questions").content(payload).contentType(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(question.getId()))
            .andExpect(jsonPath("$.author").value(question.getAuthor()))
            .andExpect(jsonPath("$.message").value(question.getMessage()))
            .andExpect(jsonPath("$.replies").value(0));
    }
    
    /**
     * Happy path for creating a reply of a question
     */
    @Test
    public void should_call_create_reply() throws Exception {
        QuestionResponse parentQuestion = createQuestion();
        QuestionRequest request = getReplyRequest();
        String payload = mapper.writeValueAsString(request);
        
        mvc.perform(post("/questions/" + parentQuestion.getId()).content(payload)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.author").value(request.getAuthor()))
            .andExpect(jsonPath("$.message").value(request.getMessage()))
            .andExpect(jsonPath("$.questionId").value(parentQuestion.getId()));
    }
    
    /**
     * Happy path for getting a thread by question id
     */
    @Test
    public void should_call_get_thread() throws Exception {
        QuestionResponse question = createQuestion();
        ReplyResponse reply = createReply(question);
        
        mvc.perform(get("/questions/" + question.getId()).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(question.getId()))
            .andExpect(jsonPath("$.author").value(question.getAuthor()))
            .andExpect(jsonPath("$.message").value(question.getMessage()))
            .andExpect(jsonPath("$.replies").isNotEmpty())
            .andExpect(jsonPath("$.replies[0].id").value(reply.getId()))
            .andExpect(jsonPath("$.replies[0].author").value(reply.getAuthor()))
            .andExpect(jsonPath("$.replies[0].message").value(reply.getMessage()));
    }
    
    /**
     * Happy path for getting all questions
     */
    @Test
    public void should_call_get_all_questions() throws Exception {
        QuestionResponse question1 = createQuestion();
        QuestionResponse question2 = createQuestion();
        createReply(question1);
        createReply(question2);
        
        mvc.perform(get("/questions").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(question1.getId()))
            .andExpect(jsonPath("$[0].author").value(question1.getAuthor()))
            .andExpect(jsonPath("$[0].message").value(question1.getMessage()))
            .andExpect(jsonPath("$[0].replies").value(1))
            .andExpect(jsonPath("$[1].id").value(question2.getId()))
            .andExpect(jsonPath("$[1].author").value(question2.getAuthor()))
            .andExpect(jsonPath("$[1].message").value(question2.getMessage()))
            .andExpect(jsonPath("$[1].replies").value(1));
    }
    
    private QuestionResponse createQuestion() throws Exception {
        QuestionRequest createRequest = getQuestionRequest();
        String createPayload = mapper.writeValueAsString(createRequest);
        String questionResponse = mvc
            .perform(post("/questions").content(createPayload).contentType(APPLICATION_JSON))
            .andReturn().getResponse().getContentAsString();
        return mapper.readValue(questionResponse, QuestionResponse.class);
    }
    
    private ReplyResponse createReply(QuestionResponse question) throws Exception {
        QuestionRequest request = getReplyRequest();
        String payload = mapper.writeValueAsString(request);
        String replyResponse = mvc.perform(
            post("/questions/" + question.getId()).content(payload)
                .contentType(APPLICATION_JSON))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return mapper.readValue(replyResponse, ReplyResponse.class);
    }
}
