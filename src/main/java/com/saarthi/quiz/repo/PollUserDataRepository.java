package com.saarthi.quiz.repo;

import com.saarthi.quiz.model.db.PollUserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollUserDataRepository extends JpaRepository<PollUserData, Integer> {
}
