package com.saarthi.quiz.service.impl;

import com.saarthi.quiz.service.QuestionsService;
import com.saarthi.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncServiceImpl {

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private QuizService quizService;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Map<String, Object>> sendQuiz(String chatId, String telegramToken, Integer quizId,
                                                          HttpServletResponse response) {
        return CompletableFuture.completedFuture(questionsService.sendQuiz(chatId, telegramToken, quizId,
                response));
    }

    public CompletableFuture<Map<String, Object>> telegramWebhook(String telegramToken,
                                                                  Map<String, Object> requestBody,
                                                                  HttpServletResponse response) {
        return CompletableFuture.completedFuture(questionsService.telegramWebhook(telegramToken, requestBody, response));
    }
}
