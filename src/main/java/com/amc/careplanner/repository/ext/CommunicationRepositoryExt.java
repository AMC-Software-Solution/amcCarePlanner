package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Communication;
import com.amc.careplanner.repository.CommunicationRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Communication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunicationRepositoryExt extends  CommunicationRepository{
}
