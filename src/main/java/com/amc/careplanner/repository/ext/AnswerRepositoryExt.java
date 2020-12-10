package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Answer;
import com.amc.careplanner.repository.AnswerRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Answer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerRepositoryExt extends AnswerRepository{
	
}