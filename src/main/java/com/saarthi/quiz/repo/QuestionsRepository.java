package com.saarthi.quiz.repo;

import com.saarthi.quiz.model.db.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Created by Barath.
 */

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {
    /**
     *
     * @param quizId
     * @return
     *
     * Custom query to find all the questions based on quiz ID
     */
    List<Questions> findAllByQuizId(Integer quizId);
}
