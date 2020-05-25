package com.saarthi.quiz.repo;

import com.saarthi.quiz.model.db.TelegramQuestionMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramQuestionMappingRepository extends JpaRepository<TelegramQuestionMapping, Integer> {
}
