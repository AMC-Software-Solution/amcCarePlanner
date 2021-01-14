package com.amc.careplanner.repository;

import com.amc.careplanner.domain.RelationshipType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RelationshipType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelationshipTypeRepository extends JpaRepository<RelationshipType, Long>, JpaSpecificationExecutor<RelationshipType> {
}
