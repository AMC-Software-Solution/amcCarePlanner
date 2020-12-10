package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.ClientDocument;
import com.amc.careplanner.repository.ClientDocumentRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClientDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientDocumentRepositoryExt extends ClientDocumentRepository{
}
