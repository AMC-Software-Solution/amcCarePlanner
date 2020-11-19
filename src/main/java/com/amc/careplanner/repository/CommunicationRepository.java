package com.amc.careplanner.repository;

import com.amc.careplanner.domain.Communication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Communication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Long>, JpaSpecificationExecutor<Communication> {
}
