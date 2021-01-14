package com.amc.careplanner.repository;

import com.amc.careplanner.domain.Equality;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Equality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EqualityRepository extends JpaRepository<Equality, Long>, JpaSpecificationExecutor<Equality> {
}
