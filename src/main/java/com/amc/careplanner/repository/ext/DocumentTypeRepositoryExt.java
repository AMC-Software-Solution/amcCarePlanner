package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.DocumentType;
import com.amc.careplanner.repository.DocumentTypeRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTypeRepositoryExt extends  DocumentTypeRepository{
}
