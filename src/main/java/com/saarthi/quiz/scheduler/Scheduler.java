package com.saarthi.quiz.scheduler;

import com.saarthi.quiz.model.db.QuizSchedule;
import com.saarthi.quiz.repo.QuizScheduleRepository;
import com.saarthi.quiz.service.QuestionsService;
import com.saarthi.quiz.service.impl.AsyncServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Component
public class Scheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AsyncServiceImpl asyncServiceImpl;

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private QuizScheduleRepository quizScheduleRepository;

    @Value("${morning.ten.am.quiz_id}")
    private Integer secheduledQuizId;

    @Scheduled(cron = "${morning.ten.am.quiz:0 0 10 * * ?}")
    @Transactional
    public void sendQuiz() {
        logger.info("Scheduler triggred");

        QuizSchedule quizScheduled = quizScheduleRepository.getOne(secheduledQuizId);
        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            asyncServiceImpl.sendQuiz(chatId, quizScheduled.getToken(), quizScheduled.getQuizId(), null);
        }
    }
}
