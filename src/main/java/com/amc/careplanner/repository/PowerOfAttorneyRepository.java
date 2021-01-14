package com.amc.careplanner.repository;

import com.amc.careplanner.domain.PowerOfAttorney;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PowerOfAttorney entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PowerOfAttorneyRepository extends JpaRepository<PowerOfAttorney, Long>, JpaSpecificationExecutor<PowerOfAttorney> {
}
