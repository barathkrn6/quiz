package com.saarthi.quiz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saarthi.quiz.model.db.Quiz;
import com.saarthi.quiz.repo.QuizRepository;
import com.saarthi.quiz.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Created by Barath.
 */

@Service
public class QuizServiceImpl implements QuizService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    /***
     *
     * @return
     *
     * Function to get all quiz
     */
    @Override
    public Iterable<Quiz> findAll() {
        return quizRepository.findAll();
    }

    /***
     *
     * @param quiz
     * @param response
     * @return
     *
     * Function to create quiz
     */
    @Override
    public Map<String, Object> createQuiz(Quiz quiz, HttpServletResponse response) {
        Map<String, Object> quizMap = null;
        try {
            Quiz savedQuiz = quizRepository.save(quiz);
            ObjectMapper oMapper = new ObjectMapper();
            quizMap = oMapper.convertValue(savedQuiz, Map.class);
            response.setStatus(201);
        } catch (Exception e) {
            logger.error("error in createQuiz :: ", e);
            setError(response, quizMap, e);
        }
        return quizMap;
    }

    /***
     *
     * @param quizId
     * @param response
     * @return
     *
     * Function to get quiz based on quiz ID
     */
    @Override
    public Map<String, Object> getQuizById(Integer quizId, HttpServletResponse response) {
        Map<String, Object> quizMap = null;
        try {
            Optional<Quiz> quiz = quizRepository.findById(quizId);
            if (quiz.isPresent()) {
                ObjectMapper oMapper = new ObjectMapper();
                quizMap = oMapper.convertValue(quiz.get(), Map.class);
            } else {
                response.setStatus(404);
                quizMap = new LinkedHashMap<>();
            }
        } catch (Exception e) {
            logger.error("error in getQuizById :: ", e);
            setError(response, quizMap, e);
        }
        return quizMap;
    }

    /***
     *
     * @param response
     * @param data
     * @param e
     *
     * set error on failure
     */
    public void setError(HttpServletResponse response, Map<String, Object> data, Exception e) {
        response.setStatus(400);
        data = new LinkedHashMap<>();
        data.put("status", "Failure");
        data.put("reason", e.getMessage());
    }
}
