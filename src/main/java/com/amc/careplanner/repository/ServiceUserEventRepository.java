package com.amc.careplanner.repository;

import com.amc.careplanner.domain.ServiceUserEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceUserEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceUserEventRepository extends JpaRepository<ServiceUserEvent, Long>, JpaSpecificationExecutor<ServiceUserEvent> {
}
