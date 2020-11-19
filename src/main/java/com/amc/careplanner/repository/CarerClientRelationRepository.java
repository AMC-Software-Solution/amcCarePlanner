package com.amc.careplanner.repository;

import com.amc.careplanner.domain.CarerClientRelation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CarerClientRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarerClientRelationRepository extends JpaRepository<CarerClientRelation, Long>, JpaSpecificationExecutor<CarerClientRelation> {
}
