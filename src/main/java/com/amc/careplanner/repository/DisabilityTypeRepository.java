package com.amc.careplanner.repository;

import com.amc.careplanner.domain.DisabilityType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DisabilityType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisabilityTypeRepository extends JpaRepository<DisabilityType, Long>, JpaSpecificationExecutor<DisabilityType> {
}
