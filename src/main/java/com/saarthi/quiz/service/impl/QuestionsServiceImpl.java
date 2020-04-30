package com.saarthi.quiz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saarthi.quiz.model.db.Questions;
import com.saarthi.quiz.repo.QuestionsRepository;
import com.saarthi.quiz.service.QuestionsService;
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
public class QuestionsServiceImpl implements QuestionsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private QuizService quizService;

    /***
     *
     * @return
     *
     * function to get Current Time
     */
    @Override
    public String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    /***
     *
     * @param questionId
     * @param response
     * @return
     *
     * Function to get question based in question ID
     */
    @Override
    public Map<String, Object> getQuestionById(Integer questionId, HttpServletResponse response) {
        Map<String, Object> questionMap = null;
        try {
            Optional<Questions> question = questionsRepository.findById(questionId);
            if (question.isPresent()) {
                ObjectMapper oMapper = new ObjectMapper();
                questionMap = oMapper.convertValue(question.get(), Map.class);
            } else {
                response.setStatus(404);
                questionMap = new LinkedHashMap<>();
            }
        } catch (Exception e) {
            logger.error("error in question :: {}", e);
            setError(response, questionMap, e);
        }
        return questionMap;
    }

    /***
     *
     * @param questions
     * @param response
     * @return
     *
     * Function to create question
     */
    @Override
    public Map<String, Object> createQuestion(Questions questions, HttpServletResponse response) {
        Map<String, Object> questionMap = null;
        try {
            Questions savedQuestions = questionsRepository.save(questions);
            ObjectMapper oMapper = new ObjectMapper();
            questionMap = oMapper.convertValue(savedQuestions, Map.class);
            response.setStatus(201);
        } catch (Exception e) {
            logger.error("error in createQuiz :: ", e);
            setError(response, questionMap, e);
        }
        return questionMap;
    }

    /***
     *
     * @param quizId
     * @param response
     * @return
     *
     *Function to get all the questions based on quiz ID
     */
    @Override
    public Map<String, Object> getQuestionsByQuizId(Integer quizId, HttpServletResponse response) {
        Map<String, Object> data = null;
        try {
            Map<String, Object> quizData = quizService.getQuizById(quizId, response);
            if (quizData.isEmpty()) {
                return quizData;
            } else {
                List<Questions> dbData = questionsRepository.findAllByQuizId(quizId);
                data = new LinkedHashMap<>();
                data.put("name", quizData.get("name"));
                data.put("description", quizData.get("description"));
                data.put("questions", dbData);
            }
        } catch (Exception e) {
            logger.error("error in getQuestionsByQuizId :: ", e);
            setError(response, data, e);
        }
        return data;
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
