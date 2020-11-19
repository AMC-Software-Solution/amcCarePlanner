package com.amc.careplanner.repository;

import com.amc.careplanner.domain.Tenant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tenant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long>, JpaSpecificationExecutor<Tenant> {
}
