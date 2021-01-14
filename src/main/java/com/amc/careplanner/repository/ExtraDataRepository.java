package com.amc.careplanner.repository;

import com.amc.careplanner.domain.ExtraData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExtraData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraDataRepository extends JpaRepository<ExtraData, Long>, JpaSpecificationExecutor<ExtraData> {
}
