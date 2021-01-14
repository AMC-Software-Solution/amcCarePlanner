package com.amc.careplanner.repository;

import com.amc.careplanner.domain.QuestionType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuestionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long>, JpaSpecificationExecutor<QuestionType> {
}
