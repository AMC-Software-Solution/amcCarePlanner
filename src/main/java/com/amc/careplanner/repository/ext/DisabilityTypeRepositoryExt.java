package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.DisabilityType;
import com.amc.careplanner.repository.DisabilityTypeRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DisabilityType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisabilityTypeRepositoryExt extends DisabilityTypeRepository{
}
