package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.ServiceUserLocation;
import com.amc.careplanner.repository.ServiceUserLocationRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceUserLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceUserLocationRepositoryExt extends ServiceUserLocationRepository{
}
