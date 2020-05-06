package com.saarthi.quiz.service;

import com.saarthi.quiz.model.db.Questions;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*
 * Created by Barath.
 */

public interface QuestionsService {
    String getTime();

    Map<String, Object> getQuestionById(Integer questionId, HttpServletResponse response);

    Map<String, Object> createQuestion(Questions questions, HttpServletResponse response);

    Map<String, Object> getQuestionsByQuizId(Integer quizId, HttpServletResponse response);

    Map<String, Object> sendQuiz(String chatId, String telegramToken, Integer quizId, HttpServletResponse response);
}
