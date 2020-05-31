package com.saarthi.quiz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.saarthi.quiz.model.db.Questions;
import com.saarthi.quiz.model.db.Quiz;
import com.saarthi.quiz.model.db.QuizSchedule;
import com.saarthi.quiz.repo.QuizRepository;
import com.saarthi.quiz.repo.QuizScheduleRepository;
import com.saarthi.quiz.service.QuestionsService;
import com.saarthi.quiz.service.QuizService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

/*
 * Created by Barath.
 */

@Service
public class QuizServiceImpl implements QuizService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private QuizScheduleRepository quizScheduleRepository;

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

    @Override
    public Map<String, Object> createQuizSchedule(QuizSchedule quizSchedule, HttpServletResponse response) {
        Map<String, Object> quizScheduleMap = null;
        try {
            QuizSchedule savedQuizSchedule = quizScheduleRepository.save(quizSchedule);
            ObjectMapper oMapper = new ObjectMapper();
            quizScheduleMap = oMapper.convertValue(savedQuizSchedule, Map.class);
            response.setStatus(201);
        } catch (Exception e) {
            logger.error("error in createQuizSchedule :: ", e);
            setError(response, quizScheduleMap, e);
        }
        return quizScheduleMap;
    }

    @Override
    public ResponseEntity<Resource> generatePdf(Integer quizId, HttpServletResponse response) throws Exception {
        ClassPathResource classPathResource1 = new ClassPathResource("static/1.pdf");
        ClassPathResource classPathResource2 = new ClassPathResource("static/2.pdf");

        InputStream inputStream1 = classPathResource1.getInputStream();
        InputStream inputStream2 = classPathResource2.getInputStream();
        File file1 = File.createTempFile("temp1", ".pdf");
        File file2 = File.createTempFile("temp2", ".pdf");
        try {
            FileUtils.copyInputStreamToFile(inputStream1, file1);
            FileUtils.copyInputStreamToFile(inputStream2, file2);
        } finally {
            IOUtils.closeQuietly(inputStream1);
            IOUtils.closeQuietly(inputStream2);
        }

        Document document = new Document();
        Rectangle envelope = new Rectangle(720, 405);
        envelope.setBorder(Rectangle.BOX);
        envelope.setBorderColor(BaseColor.BLACK);
        envelope.setBorderWidth(30);
        document.setPageSize(envelope);

        PdfWriter.getInstance(document, new FileOutputStream("dynamic.pdf"));

        document.open();
        Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Font font = FontFactory.getFont("classpath:static/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Map<String, Object> data = questionsService.getQuestionsByQuizId(quizId, response);
        if (data != null && !data.isEmpty()) {
            List<Questions> dbQuestions = (List<Questions>) data.get("questions");

            for (int i = 0; i < dbQuestions.size(); i++) {
                Questions dbq = dbQuestions.get(i);
                String questionStr = dbq.getName();
                String options = dbq.getOptions();
                Integer correctOption = Integer.valueOf(dbq.getCorrectOption());
                String explanation = dbq.getExplanation();

                addQuestions(headingFont, questionStr, font, document, i);
                addAnswer(headingFont, options, font, document, i);
                addCorrectOpt(headingFont, correctOption, font, document, i);
                addExplanation(headingFont, explanation, font, document, i);

                if (i != dbQuestions.size() - 1) {
                    Paragraph space = new Paragraph();
                    space.setSpacingAfter(40);
                    document.add(space);
                }
            }

            document.close();

            List<InputStream> list = new ArrayList<InputStream>();
            InputStream inputStreamOne = new FileInputStream(file1);
            InputStream inputStreamTwo = new FileInputStream(new File("dynamic.pdf"));
            InputStream inputStreamThree = new FileInputStream(file2);

            list.add(inputStreamOne);
            list.add(inputStreamTwo);
            list.add(inputStreamThree);

            File finalFile = new File("Final.pdf");
            OutputStream outputStream = new FileOutputStream(finalFile);

            mergePdf(list, outputStream);
            new File("dynamic.pdf").delete();

            HttpHeaders header = new HttpHeaders();
            String fileName = questionsService.getTime();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Final_" + fileName + ".pdf");
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");
            InputStreamResource resource = new InputStreamResource(new FileInputStream(finalFile));
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(finalFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return null;
    }

    public void addQuestions(Font headingFont, String questionStr, Font font, Document document, int i) throws Exception {
        Paragraph paraQuestion = new Paragraph("Question " + (i + 1), headingFont);
        paraQuestion.setSpacingAfter(5);
        Paragraph paraQuestion1 = new Paragraph(questionStr, font);
        paraQuestion1.setSpacingAfter(15);
        document.add(paraQuestion);
        document.add(paraQuestion1);
    }

    public void addAnswer(Font headingFont, String options, Font font, Document document, int i) throws Exception {
        Paragraph paraAns = new Paragraph("Options of question " + (i + 1), headingFont);
        paraAns.setSpacingAfter(5);
        String optRes = "";
        int c = 1;
        for (String o : options.split(",")) {
            optRes = optRes + c + ". " + o + "\n";
            c++;
        }
        Paragraph paraAns1 = new Paragraph(optRes, font);
        paraAns1.setSpacingAfter(15);
        document.add(paraAns);
        document.add(paraAns1);
    }

    public void addCorrectOpt(Font headingFont, Integer correctOption, Font font, Document document, int i)
            throws  Exception {
        Paragraph paraCorrectOpt = new Paragraph("Correct Option for " + (i + 1), headingFont);
        paraCorrectOpt.setSpacingAfter(5);
        ++correctOption;
        Paragraph paraCorrectOpt1 = new Paragraph(correctOption.toString(), font);
        paraCorrectOpt1.setSpacingAfter(15);
        document.add(paraCorrectOpt);
        document.add(paraCorrectOpt1);
    }

    public void addExplanation(Font headingFont, String explanation, Font font, Document document, int i)
            throws Exception {
        Paragraph paraExp = new Paragraph("Explanation for " + (i + 1), headingFont);
        paraExp.setSpacingAfter(5);
        document.add(paraExp);
        document.add(new Paragraph(explanation, font));
    }
    private void mergePdf(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
        Document document = new Document();
        try {
            // Open PdfCopy for to copy the merged document into outputStream
            PdfCopy copy = new PdfCopy(document, outputStream);

            document.open();

            for (InputStream file : list) {
                // Create Reader for each input stream
                PdfReader reader = new PdfReader(file);

                // Merge each pdf into merged document represented by copy
                copy.addDocument(reader);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen())
                document.close();
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
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
