package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Travel;
import com.amc.careplanner.repository.TravelRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Travel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TravelRepositoryExt extends TravelRepository{
}
