package com.amc.careplanner.repository;

import com.amc.careplanner.domain.ServceUserDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServceUserDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServceUserDocumentRepository extends JpaRepository<ServceUserDocument, Long>, JpaSpecificationExecutor<ServceUserDocument> {
}
