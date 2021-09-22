package com.saarthi.quiz;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saarthi.quiz.model.db.Questions;
import com.saarthi.quiz.model.db.Quiz;
import com.saarthi.quiz.service.QuestionsService;
import com.saarthi.quiz.service.QuizService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuizApplication.class)
class QuizApplicationTests {

	MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private QuizService quizService;

	@MockBean
	private QuestionsService questionsService;

	// @Test
	void testFindAll() throws Exception {
		List<Quiz> quiz = new ArrayList<>();
		Quiz temp = new Quiz();
		temp.setId(1);
		temp.setName("quiz 1");
		temp.setDescription("quiz desc 1");
		quiz.add(temp);
		Iterable<Quiz> itrQuiz = quiz;
		Mockito.when(quizService.findAll()).thenReturn(itrQuiz);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quiz/get-all");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	// @Test
	void testGetQuizById() throws Exception {
		Quiz temp = new Quiz();
		temp.setId(1);
		temp.setName("quiz 1");
		temp.setDescription("quiz desc 1");
		ObjectMapper oMapper = new ObjectMapper();
		Map<String, Object> quizMap = oMapper.convertValue(temp, Map.class);
		Mockito.when(quizService.getQuizById(1, getResponse())).thenReturn(quizMap);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quiz/1");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	// @Test
	void testGetQuestionById() throws Exception {
		Questions questions = new Questions();
		questions.setId(1);
		questions.setName("question 1");
		questions.setOptions("a,b,c,d");
		questions.setCorrectOption(2);
		questions.setQuizId(1);
		questions.setPoints(10);
		ObjectMapper oMapper = new ObjectMapper();
		Map<String, Object> questionsMap = oMapper.convertValue(questions, Map.class);
		Mockito.when(questionsService.getQuestionsByQuizId(1, getResponse())).thenReturn(questionsMap);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/questions/1");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	HttpServletResponse getResponse() {
		return new HttpServletResponse() {
			@Override
			public void addCookie(Cookie cookie) {

			}

			@Override
			public boolean containsHeader(String s) {
				return false;
			}

			@Override
			public String encodeURL(String s) {
				return null;
			}

			@Override
			public String encodeRedirectURL(String s) {
				return null;
			}

			@Override
			public String encodeUrl(String s) {
				return null;
			}

			@Override
			public String encodeRedirectUrl(String s) {
				return null;
			}

			@Override
			public void sendError(int i, String s) throws IOException {

			}

			@Override
			public void sendError(int i) throws IOException {

			}

			@Override
			public void sendRedirect(String s) throws IOException {

			}

			@Override
			public void setDateHeader(String s, long l) {

			}

			@Override
			public void addDateHeader(String s, long l) {

			}

			@Override
			public void setHeader(String s, String s1) {

			}

			@Override
			public void addHeader(String s, String s1) {

			}

			@Override
			public void setIntHeader(String s, int i) {

			}

			@Override
			public void addIntHeader(String s, int i) {

			}

			@Override
			public void setStatus(int i) {

			}

			@Override
			public void setStatus(int i, String s) {

			}

			@Override
			public int getStatus() {
				return 0;
			}

			@Override
			public String getHeader(String s) {
				return null;
			}

			@Override
			public Collection<String> getHeaders(String s) {
				return null;
			}

			@Override
			public Collection<String> getHeaderNames() {
				return null;
			}

			@Override
			public String getCharacterEncoding() {
				return null;
			}

			@Override
			public String getContentType() {
				return null;
			}

			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				return null;
			}

			@Override
			public PrintWriter getWriter() throws IOException {
				return null;
			}

			@Override
			public void setCharacterEncoding(String s) {

			}

			@Override
			public void setContentLength(int i) {

			}

			@Override
			public void setContentLengthLong(long l) {

			}

			@Override
			public void setContentType(String s) {

			}

			@Override
			public void setBufferSize(int i) {

			}

			@Override
			public int getBufferSize() {
				return 0;
			}

			@Override
			public void flushBuffer() throws IOException {

			}

			@Override
			public void resetBuffer() {

			}

			@Override
			public boolean isCommitted() {
				return false;
			}

			@Override
			public void reset() {

			}

			@Override
			public void setLocale(Locale locale) {

			}

			@Override
			public Locale getLocale() {
				return null;
			}
		};
	}
}
