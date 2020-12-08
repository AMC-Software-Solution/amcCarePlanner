package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Question;
import com.amc.careplanner.repository.QuestionRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepositoryExt extends QuestionRepository {
}
