package com.saarthi.quiz.repo;

import com.saarthi.quiz.model.db.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Barath.
 */

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
