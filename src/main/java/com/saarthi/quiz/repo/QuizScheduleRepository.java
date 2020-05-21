package com.saarthi.quiz.repo;

import com.saarthi.quiz.model.db.QuizSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Barath.
 */
public interface QuizScheduleRepository extends JpaRepository<QuizSchedule, Integer> {
}
