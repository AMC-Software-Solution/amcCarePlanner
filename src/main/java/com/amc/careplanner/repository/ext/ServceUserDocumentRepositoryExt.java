package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.ServceUserDocument;
import com.amc.careplanner.repository.ServceUserDocumentRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServceUserDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServceUserDocumentRepositoryExt extends ServceUserDocumentRepository{
}
