package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.EmployeeHoliday;
import com.amc.careplanner.repository.EmployeeHolidayRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeHoliday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeHolidayRepositoryExt extends  EmployeeHolidayRepository{
}
