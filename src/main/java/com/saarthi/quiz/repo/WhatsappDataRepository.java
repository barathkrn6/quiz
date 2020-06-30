package com.saarthi.quiz.repo;

import com.saarthi.quiz.model.db.WhatsappData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhatsappDataRepository extends JpaRepository<WhatsappData, Integer> {
}
