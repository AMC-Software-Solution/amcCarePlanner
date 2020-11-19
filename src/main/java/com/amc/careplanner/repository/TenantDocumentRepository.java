package com.amc.careplanner.repository;

import com.amc.careplanner.domain.TenantDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TenantDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantDocumentRepository extends JpaRepository<TenantDocument, Long>, JpaSpecificationExecutor<TenantDocument> {
}
