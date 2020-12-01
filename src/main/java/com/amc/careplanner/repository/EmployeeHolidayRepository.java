package com.amc.careplanner.repository;

import com.amc.careplanner.domain.EmployeeHoliday;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeHoliday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeHolidayRepository extends JpaRepository<EmployeeHoliday, Long>, JpaSpecificationExecutor<EmployeeHoliday> {
}
