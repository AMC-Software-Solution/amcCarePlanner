package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Equality;
import com.amc.careplanner.repository.EqualityRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Equality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EqualityRepositoryExt extends EqualityRepository{
}
