package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.QuestionType;
import com.amc.careplanner.repository.QuestionTypeRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuestionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionTypeRepositoryExt extends QuestionTypeRepository{
}
