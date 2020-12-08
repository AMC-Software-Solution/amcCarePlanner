package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.EmployeeRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepositoryExt extends EmployeeRepository {

}
