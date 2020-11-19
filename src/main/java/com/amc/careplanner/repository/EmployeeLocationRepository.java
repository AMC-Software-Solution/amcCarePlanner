package com.amc.careplanner.repository;

import com.amc.careplanner.domain.EmployeeLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeLocationRepository extends JpaRepository<EmployeeLocation, Long>, JpaSpecificationExecutor<EmployeeLocation> {
}
