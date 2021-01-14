package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Country;
import com.amc.careplanner.repository.CountryRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Country entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryRepositoryExt extends  CountryRepository{
}
