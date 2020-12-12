package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.EmployeeLocation;
import com.amc.careplanner.repository.EmployeeLocationRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeLocationRepositoryExt extends EmployeeLocationRepository{
}
