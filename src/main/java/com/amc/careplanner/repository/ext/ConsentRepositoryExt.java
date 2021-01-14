package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Consent;
import com.amc.careplanner.repository.ConsentRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Consent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsentRepositoryExt extends ConsentRepository{
}
