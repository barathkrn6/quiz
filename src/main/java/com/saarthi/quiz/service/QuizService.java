package com.saarthi.quiz.service;

import com.saarthi.quiz.model.db.Quiz;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*
 * Created by Barath.
 */

public interface QuizService {
    String getTime();

    Iterable<Quiz> findAll();

    Map<String, Object> createQuiz(Quiz quiz, HttpServletResponse response);

    Map<String, Object> getQuizById(Integer quizId, HttpServletResponse response);
}
