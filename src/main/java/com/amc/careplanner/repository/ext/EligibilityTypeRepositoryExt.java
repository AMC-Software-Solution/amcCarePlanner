package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.EligibilityType;
import com.amc.careplanner.repository.EligibilityTypeRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EligibilityType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EligibilityTypeRepositoryExt extends EligibilityTypeRepository {
}
