package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.EmployeeAvailability;
import com.amc.careplanner.repository.EmployeeAvailabilityRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeAvailability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeAvailabilityRepositoryExt extends EmployeeAvailabilityRepository{
}
