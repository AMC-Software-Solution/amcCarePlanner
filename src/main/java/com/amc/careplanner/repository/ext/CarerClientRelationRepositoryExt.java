package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.CarerClientRelation;
import com.amc.careplanner.repository.CarerClientRelationRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CarerClientRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarerClientRelationRepositoryExt extends CarerClientRelationRepository {
}
