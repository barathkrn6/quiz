package com.saarthi.quiz.controller;

import com.saarthi.quiz.model.db.Questions;
import com.saarthi.quiz.service.QuestionsService;
import com.saarthi.quiz.service.impl.AsyncServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/*
 * Created by Barath.
 */

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuestionsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private AsyncServiceImpl asyncServiceImpl;
    /***
     *
     * @return
     *
     * health check URL
     */
    @GetMapping(value = "/health-check")
    public String healthCheck() {
        logger.info("call to healthCheck");
        return "App is up and running";
    }

    /***
     *
     * @param questionId
     * @param response
     * @return
     *
     * API to get question based on the ID
     */
    @GetMapping(value = "/api/questions/{question_id}")
    public Map<String, Object> getQuestionById(@PathVariable("question_id") Integer questionId, HttpServletResponse response) {
        logger.info("getQuestionById at :: " + questionsService.getTime());
        return questionsService.getQuestionById(questionId, response);
    }

    /***
     *
     * @param questions
     * @param response
     * @return
     *
     * API to create new Questions
     */
    @PostMapping(value = "/api/questions/")
    public Map<String, Object> createQuestion(@RequestBody Questions questions, HttpServletResponse response) {
        logger.info("createQuestion at :: " + questionsService.getTime());
        return questionsService.createQuestion(questions, response);
    }

    /***
     *
     * @param quizId
     * @param response
     * @return
     *
     *API to get all the Questions based on quiz ID
     */
    @GetMapping(value = "/api/quiz-questions/{quiz_id}")
    public Map<String, Object> getQuestionsByQuizId(@PathVariable("quiz_id") Integer quizId,
                                                    HttpServletResponse response) {
        logger.info("getQuestionsByQuizId at :: " + questionsService.getTime());
        return questionsService.getQuestionsByQuizId(quizId, response);
    }

    @PostMapping(value = "/{telegram_token}/telegram_webhook")
    public void telegramWebhook(@PathVariable(("telegram_token")) String telegramToken,
                                               @RequestBody Map<String, Object> requestBody,
                                               HttpServletResponse response) {
        logger.info("Request :: {}", requestBody);
        asyncServiceImpl.telegramWebhook(telegramToken, requestBody, response);
    }

    @GetMapping(value = "/api/send_quiz/{chat_id}/{telegram_token}/{quiz_id}")
    public Map<String, Object> sendQuiz(@PathVariable("chat_id") String chatIds,
                                        @PathVariable("telegram_token") String telegramToken,
                                        @PathVariable("quiz_id") Integer quizId,
                                        HttpServletResponse response) throws Exception {
        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = chatIds.split("~");
        for (String chatId : splitChat) {
            asyncServiceImpl.sendQuiz(chatId, telegramToken, quizId, response);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        return map;
    }
}
