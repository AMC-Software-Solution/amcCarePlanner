package com.amc.careplanner.repository;

import com.amc.careplanner.domain.EmployeeDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long>, JpaSpecificationExecutor<EmployeeDocument> {
}
