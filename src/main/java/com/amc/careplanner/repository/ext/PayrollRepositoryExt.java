package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Payroll;
import com.amc.careplanner.repository.PayrollRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Payroll entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollRepositoryExt extends PayrollRepository{
}
