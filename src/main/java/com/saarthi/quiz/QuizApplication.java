package com.saarthi.quiz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.Executor;

/*
 * Created by Barath.
 */

@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration
@ComponentScan
@EnableAsync
public class QuizApplication {

	private static final Logger logger = LoggerFactory.getLogger(QuizApplication.class);

	public static void main(String[] args) {
		logger.info("Starting QUIZ API SERVICE");
		SpringApplication.run(QuizApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("quiz-api-services").enable(true)
				.apiInfo(apiInfo()).select().paths(PathSelectors.any()).build();
	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("quiz-api-services").description("quiz-api-services").contact("Barath K")
				.license("Apache License Version 2.0").version("1.0.0").build();
	}

	/**
	 * Enabling Executor
	 *
	 * @return
	 */
	@Bean(name = "threadPoolTaskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Quiz-");
		executor.initialize();
		return executor;
	}
}
