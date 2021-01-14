package com.amc.careplanner.repository;

import com.amc.careplanner.domain.Disability;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Disability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisabilityRepository extends JpaRepository<Disability, Long>, JpaSpecificationExecutor<Disability> {
}
