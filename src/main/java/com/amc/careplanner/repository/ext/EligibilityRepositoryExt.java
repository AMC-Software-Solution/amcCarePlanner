package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Eligibility;
import com.amc.careplanner.repository.EligibilityRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Eligibility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EligibilityRepositoryExt extends EligibilityRepository{
}
