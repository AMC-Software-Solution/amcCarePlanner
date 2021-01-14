package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Client;
import com.amc.careplanner.repository.ClientRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepositoryExt extends ClientRepository{
}
