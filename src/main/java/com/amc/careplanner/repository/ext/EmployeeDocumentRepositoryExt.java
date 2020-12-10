package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.EmployeeDocument;
import com.amc.careplanner.repository.EmployeeDocumentRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeDocumentRepositoryExt extends  EmployeeDocumentRepository{
}
