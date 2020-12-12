package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.PowerOfAttorney;
import com.amc.careplanner.repository.PowerOfAttorneyRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PowerOfAttorney entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PowerOfAttorneyRepositoryExt extends PowerOfAttorneyRepository{
}
