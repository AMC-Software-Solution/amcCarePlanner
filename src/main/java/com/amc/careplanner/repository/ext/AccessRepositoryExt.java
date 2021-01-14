package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Access;
import com.amc.careplanner.repository.AccessRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Access entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccessRepositoryExt extends AccessRepository {
}