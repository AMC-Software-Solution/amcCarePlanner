package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Disability;
import com.amc.careplanner.repository.DisabilityRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Disability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisabilityRepositoryExt extends  DisabilityRepository{
}
