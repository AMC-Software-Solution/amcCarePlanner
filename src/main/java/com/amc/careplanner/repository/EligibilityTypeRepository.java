package com.amc.careplanner.repository;

import com.amc.careplanner.domain.EligibilityType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EligibilityType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EligibilityTypeRepository extends JpaRepository<EligibilityType, Long>, JpaSpecificationExecutor<EligibilityType> {
}
