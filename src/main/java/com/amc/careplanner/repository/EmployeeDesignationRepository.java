package com.amc.careplanner.repository;

import com.amc.careplanner.domain.EmployeeDesignation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeDesignation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeDesignationRepository extends JpaRepository<EmployeeDesignation, Long>, JpaSpecificationExecutor<EmployeeDesignation> {
}
