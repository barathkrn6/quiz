package com.saarthi.quiz.repo;

import com.saarthi.quiz.model.db.QuizSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

/*
 * Created by Barath.
 */
public interface QuizScheduleRepository extends JpaRepository<QuizSchedule, Integer> {

    @Query("SELECT c FROM QuizSchedule c where c.scheduledDate = :scheduled_date and c.name = :name")
    QuizSchedule getQuizOnDate(@Param("scheduled_date") Date scheduledDate, @Param("name") String name);
}
