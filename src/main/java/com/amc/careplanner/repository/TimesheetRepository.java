package com.amc.careplanner.repository;

import com.amc.careplanner.domain.Timesheet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Timesheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long>, JpaSpecificationExecutor<Timesheet> {
}
