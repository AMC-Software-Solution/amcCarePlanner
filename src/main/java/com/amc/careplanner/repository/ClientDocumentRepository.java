package com.amc.careplanner.repository;

import com.amc.careplanner.domain.ClientDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClientDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientDocumentRepository extends JpaRepository<ClientDocument, Long>, JpaSpecificationExecutor<ClientDocument> {
}
