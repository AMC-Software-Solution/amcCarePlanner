package com.amc.careplanner.repository;

import com.amc.careplanner.domain.CarerServiceUserRelation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CarerServiceUserRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarerServiceUserRelationRepository extends JpaRepository<CarerServiceUserRelation, Long>, JpaSpecificationExecutor<CarerServiceUserRelation> {
}
