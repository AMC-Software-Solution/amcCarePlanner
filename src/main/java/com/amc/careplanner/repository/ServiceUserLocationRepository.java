package com.amc.careplanner.repository;

import com.amc.careplanner.domain.ServiceUserLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceUserLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceUserLocationRepository extends JpaRepository<ServiceUserLocation, Long>, JpaSpecificationExecutor<ServiceUserLocation> {
}
