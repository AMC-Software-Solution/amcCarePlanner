package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Timesheet;
import com.amc.careplanner.repository.TimesheetRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Timesheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimesheetRepositoryExt extends TimesheetRepository{
}
