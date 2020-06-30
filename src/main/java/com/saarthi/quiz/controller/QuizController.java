package com.saarthi.quiz.controller;

import com.saarthi.quiz.model.db.Quiz;
import com.saarthi.quiz.model.db.QuizSchedule;
import com.saarthi.quiz.service.QuestionsService;
import com.saarthi.quiz.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/*
 * Created by Barath.
 */

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/quiz")
public class QuizController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuizService quizService;

    /***
     *
     * @return
     *
     * API to get all the quiz
     */
    @GetMapping(value = "/get-all")
    public Iterable<Quiz> getAllQuiz() {
        logger.info("getAllQuiz at :: " + quizService.getTime());
        return quizService.findAll();
    }

    /***
     *
     * @param quiz
     * @param response
     * @return
     *
     * API to create quiz
     */
    @PostMapping(value = "/")
    public Map<String, Object> createQuiz(@RequestBody Quiz quiz, HttpServletResponse response) {
        logger.info("createQuiz at :: " + quizService.getTime());
        return quizService.createQuiz(quiz, response);
    }

    /***
     *
     * @param quizId
     * @param response
     * @return
     *
     * API to get quiz based on ID
     */
    @GetMapping(value = "/{quiz_id}")
    public Map<String, Object> getQuizById(@PathVariable("quiz_id") Integer quizId, HttpServletResponse response) {
        logger.info("getQuizById at :: " + quizService.getTime());
        return quizService.getQuizById(quizId, response);
    }

    @PostMapping(value = "/create_quiz_schedule")
    public Map<String, Object> createQuizSchedule(@RequestBody QuizSchedule quizSchedule,
                                                  HttpServletResponse response) {
        logger.info("createQuizSchedule at :: " + quizService.getTime());
        return quizService.createQuizSchedule(quizSchedule, response);
    }

    @GetMapping(value = "/generate_pdf/{quiz_id}")
    public ResponseEntity<Resource> generatePdf(@PathVariable("quiz_id") Integer quizId, HttpServletResponse response)
            throws Exception {
        logger.info("generatePdf at :: " + quizService.getTime());
        return quizService.generatePdf(quizId, response);
    }

    @PostMapping(value = "/upload/whatsapp_data/{group_name}")
    public Map<String, Object> uploadWhatsAppData(@PathVariable("group_name") String groupName,
                                   @RequestParam("file") MultipartFile multipartFile, HttpServletResponse response) {
        logger.info("uploadWhatsAppData at :: {}", quizService.getTime());
        return quizService.uploadWhatsAppData(groupName.toUpperCase().trim(), multipartFile, response);
    }
}
