package com.amc.careplanner.repository;

import com.amc.careplanner.domain.ServiceUserContact;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceUserContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceUserContactRepository extends JpaRepository<ServiceUserContact, Long>, JpaSpecificationExecutor<ServiceUserContact> {
}
