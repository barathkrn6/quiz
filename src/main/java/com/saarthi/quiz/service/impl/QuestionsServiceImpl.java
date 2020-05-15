package com.saarthi.quiz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saarthi.quiz.model.db.Questions;
import com.saarthi.quiz.repo.QuestionsRepository;
import com.saarthi.quiz.service.QuestionsService;
import com.saarthi.quiz.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
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
            data = getQuestions(quizId, response);
        } catch (Exception e) {
            logger.error("error in getQuestionsByQuizId :: ", e);
            setError(response, data, e);
        }
        return data;
    }

    public Map<String, Object> getQuestions(Integer quizId, HttpServletResponse response) {
        Map<String, Object> quizData = quizService.getQuizById(quizId, response);
        if (quizData.isEmpty()) {
            return quizData;
        } else {
            List<Questions> dbData = questionsRepository.findAllByQuizId(quizId);
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("name", quizData.get("name"));
            data.put("description", quizData.get("description"));
            data.put("questions", dbData);
            return data;
        }
    }

    @Override
    public Map<String, Object> sendQuiz(String chatId, String telegramToken, Integer quizId,
                                        HttpServletResponse response) {
        logger.info("Inside sendQuiz");
        Map<String, Object> data = null;
        try {
            data = getQuestions(quizId, response);
            if (data != null && !data.isEmpty()) {
                logger.info("Data :: {}", data);
                List<Questions> dbQuestions = (List<Questions>) data.get("questions");

                int i = 0;
                for (Questions dbq : dbQuestions) {
                    i++;
                    Map<String, Object> postObject = new LinkedHashMap<>();

                    postObject.put("chat_id", chatId);
                    postObject.put("question", dbq.getName());
                    postObject.put("options", Arrays.asList(dbq.getOptions().split(",")));
                    postObject.put("is_anonymous", false);
                    postObject.put("type", "quiz");
                    postObject.put("correct_option_id", dbq.getCorrectOption());
                    // postObject.put("open_period", 10);

                    makePostCall(postObject, telegramToken);
                    // Thread.sleep(120000);
                    if (i == 10) {
                        break;
                    }
                    Thread.sleep(60000);
                    // Thread.sleep(1000);
                }
            } else {
                data = new LinkedHashMap<>();
            }
        } catch (Exception e) {
            logger.error("error in getQuestionsByQuizId :: ", e);
            setError(response, data, e);
        }
        return data;
    }

    public void makePostCall(Map<String, Object> postObject, String telegramToken) throws Exception {
        logger.info("Request :: {}", postObject);
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "https://api.telegram.org/bot" + telegramToken + "/sendPoll";
        logger.info("baseUrl :: {}", baseUrl);
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapperObj = new ObjectMapper();
        String jsonReq = mapperObj.writeValueAsString(postObject);
        HttpEntity<String> request = new HttpEntity<String>(jsonReq, headers);

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);
        logger.info("Posted question response :: {}", responseEntityStr.getBody());
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
