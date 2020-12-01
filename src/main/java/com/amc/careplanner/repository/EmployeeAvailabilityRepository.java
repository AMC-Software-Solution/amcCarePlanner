package com.amc.careplanner.repository;

import com.amc.careplanner.domain.EmployeeAvailability;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeAvailability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeAvailabilityRepository extends JpaRepository<EmployeeAvailability, Long>, JpaSpecificationExecutor<EmployeeAvailability> {
}
