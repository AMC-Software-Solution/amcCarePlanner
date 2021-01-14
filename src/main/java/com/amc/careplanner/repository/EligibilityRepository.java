package com.amc.careplanner.repository;

import com.amc.careplanner.domain.Eligibility;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Eligibility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EligibilityRepository extends JpaRepository<Eligibility, Long>, JpaSpecificationExecutor<Eligibility> {
}
