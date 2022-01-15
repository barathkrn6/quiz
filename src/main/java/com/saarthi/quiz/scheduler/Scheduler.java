package com.saarthi.quiz.scheduler;

import com.saarthi.quiz.model.db.QuizSchedule;
import com.saarthi.quiz.repo.QuizScheduleRepository;
import com.saarthi.quiz.service.QuestionsService;
import com.saarthi.quiz.service.impl.AsyncServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    @Scheduled(cron = "${quiz1:0 30 6 * * ?}")
    @Transactional
    public void sendQuiz() {
        logger.info("Scheduler triggred");

        // QuizSchedule quizScheduled = quizScheduleRepository.getOne(secheduledQuizId);
        java.util.Date dateNow = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(dateNow);
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        QuizSchedule quizScheduled = quizScheduleRepository.getQuizOnDate(date, "12-pm-test");
        logger.info("Query for date :: {}", dateStr);
        logger.info("Query for name :: {}", "12-pm-test");
        logger.info("quizScheduled :: {}", quizScheduled);

        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            asyncServiceImpl.sendQuiz(chatId, quizScheduled.getToken(), quizScheduled.getQuizId(), null);
        }
    }

    @Scheduled(cron = "${quizAlert1:0 25 6 * * ?}")
    @Transactional
    public void sendAlertEn() throws Exception {
        logger.info("Scheduler triggred alert");

        java.util.Date dateNow = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(dateNow);
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        QuizSchedule quizScheduled = quizScheduleRepository.getQuizOnDate(date, "12-pm-test");
        logger.info("Query for date :: {}", dateStr);
        logger.info("Query for name :: {}", "12-pm-test");
        logger.info("quizScheduled :: {}", quizScheduled);

        String message = "\uD83D\uDC49Saarthi App Quiz Alert \uD83D\uDC48\n" +
                "Time: 12 pm\n" +
                "Subject: General Awareness";

        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            questionsService.sendQuizAlert(chatId, quizScheduled.getToken(), message);
        }
    }

    @Scheduled(cron = "${quizAlert1:0 25 6 * * ?}")
    @Transactional
    public void sendAlertHi() throws Exception {
        logger.info("Scheduler triggred alert");

        java.util.Date dateNow = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(dateNow);
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        QuizSchedule quizScheduled = quizScheduleRepository.getQuizOnDate(date, "12-pm-test");
        logger.info("Query for date :: {}", dateStr);
        logger.info("Query for name :: {}", "12-pm-test");
        logger.info("quizScheduled :: {}", quizScheduled);

        String message = "\uD83D\uDC49सारथी ऐप क्विज़ अलर्ट \uD83D\uDC48\n" +
                "समय: दोपहर 12 बजे\n" +
                "विषय: सामान्य जागरूकता";

        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            questionsService.sendQuizAlert(chatId, quizScheduled.getToken(), message);
        }
    }

    @Scheduled(cron = "${quiz2:0 30 8 * * ?}")
    @Transactional
    public void sendQuiz1() {
        logger.info("Scheduler triggred");

        // QuizSchedule quizScheduled = quizScheduleRepository.getOne(secheduledQuizId);
        java.util.Date dateNow = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(dateNow);
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        QuizSchedule quizScheduled = quizScheduleRepository.getQuizOnDate(date, "2-pm-test");
        logger.info("Query for date :: {}", dateStr);
        logger.info("Query for name :: {}", "2-pm-test");
        logger.info("quizScheduled :: {}", quizScheduled);

        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            asyncServiceImpl.sendQuiz(chatId, quizScheduled.getToken(), quizScheduled.getQuizId(), null);
        }
    }

    @Scheduled(cron = "${quizAlert2:0 25 8 * * ?}")
    @Transactional
    public void sendAlertEn1() throws Exception {
        logger.info("Scheduler triggred alert");

        java.util.Date dateNow = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(dateNow);
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        QuizSchedule quizScheduled = quizScheduleRepository.getQuizOnDate(date, "2-pm-test");
        logger.info("Query for date :: {}", dateStr);
        logger.info("Query for name :: {}", "2-pm-test");
        logger.info("quizScheduled :: {}", quizScheduled);

        String message = "\uD83D\uDC49Saarthi App Quiz Alert \uD83D\uDC48\n" +
                "Time: 2 pm\n" +
                "Subject: General Awareness";

        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            questionsService.sendQuizAlert(chatId, quizScheduled.getToken(), message);
        }
    }

    @Scheduled(cron = "${quizAlert2:0 25 8 * * ?}")
    @Transactional
    public void sendAlertHi1() throws Exception {
        logger.info("Scheduler triggred alert");

        java.util.Date dateNow = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(dateNow);
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        QuizSchedule quizScheduled = quizScheduleRepository.getQuizOnDate(date, "2-pm-test");
        logger.info("Query for date :: {}", dateStr);
        logger.info("Query for name :: {}", "2-pm-test");
        logger.info("quizScheduled :: {}", quizScheduled);

        String message = "\uD83D\uDC49सारथी ऐप क्विज़ अलर्ट \uD83D\uDC48\n" +
                "समय: दोपहर 2 बजे\n" +
                "विषय: सामान्य जागरूकता";

        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            questionsService.sendQuizAlert(chatId, quizScheduled.getToken(), message);
        }
    }

    @Scheduled(cron = "${quiz3:0 30 10 * * ?}")
    @Transactional
    public void sendQuiz2() {
        logger.info("Scheduler triggred");

        // QuizSchedule quizScheduled = quizScheduleRepository.getOne(secheduledQuizId);
        java.util.Date dateNow = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(dateNow);
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        QuizSchedule quizScheduled = quizScheduleRepository.getQuizOnDate(date, "4-pm-test");
        logger.info("Query for date :: {}", dateStr);
        logger.info("Query for name :: {}", "4-pm-test");
        logger.info("quizScheduled :: {}", quizScheduled);

        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            asyncServiceImpl.sendQuiz(chatId, quizScheduled.getToken(), quizScheduled.getQuizId(), null);
        }
    }

    @Scheduled(cron = "${quizAlert3:0 25 10 * * ?}")
    @Transactional
    public void sendAlertEn2() throws Exception {
        logger.info("Scheduler triggred alert");

        java.util.Date dateNow = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(dateNow);
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        QuizSchedule quizScheduled = quizScheduleRepository.getQuizOnDate(date, "4-pm-test");
        logger.info("Query for date :: {}", dateStr);
        logger.info("Query for name :: {}", "4-pm-test");
        logger.info("quizScheduled :: {}", quizScheduled);

        String message = "\uD83D\uDC49Saarthi App Quiz Alert \uD83D\uDC48\n" +
                "Time: 4 pm\n" +
                "Subject: General Awareness";

        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            questionsService.sendQuizAlert(chatId, quizScheduled.getToken(), message);
        }
    }

    @Scheduled(cron = "${quizAlert3:0 25 10 * * ?}")
    @Transactional
    public void sendAlertHi2() throws Exception {
        logger.info("Scheduler triggred alert");

        java.util.Date dateNow = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(dateNow);
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        QuizSchedule quizScheduled = quizScheduleRepository.getQuizOnDate(date, "4-pm-test");
        logger.info("Query for date :: {}", dateStr);
        logger.info("Query for name :: {}", "4-pm-test");
        logger.info("quizScheduled :: {}", quizScheduled);

        String message = "\uD83D\uDC49सारथी ऐप क्विज़ अलर्ट \uD83D\uDC48\n" +
                "समय: दोपहर 4 बजे\n" +
                "विषय: सामान्य जागरूकता";

        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            questionsService.sendQuizAlert(chatId, quizScheduled.getToken(), message);
        }
    }

    // @Scheduled(cron = "${morning.ten.am.quiz.alert:0 45 13 * * ?}")
    // @Transactional
    /*public void sendAlert() throws Exception {
        logger.info("Scheduler triggred alert");

        QuizSchedule quizScheduled = quizScheduleRepository.getOne(secheduledQuizId);
        logger.info("sendQuiz at :: " + questionsService.getTime());
        String[] splitChat = quizScheduled.getChatId().split("~");
        for (String chatId : splitChat) {
            questionsService.sendQuizAlert(chatId, quizScheduled.getToken(), "");
        }
    }*/

    @Scheduled(fixedRate = 600000)
    public void healthCheck() throws Exception {
        logger.info("Scheduler healthCheck");
        Date localTime = new Date();
        DateFormat converter = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
        converter.setTimeZone(TimeZone.getTimeZone("GMT"));
        logger.info("local time : " + localTime);;
        logger.info("time in GMT : " + converter.format(localTime));

        RestTemplate template = new RestTemplate();
        final String url = "https://barath-quiz-telegram.herokuapp.com/health-check";
        URI uri = new URI(url);
        ResponseEntity<String> result = template.getForEntity(uri, String.class);
        logger.info(result.getBody());
    }
}
