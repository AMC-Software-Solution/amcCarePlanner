package com.amc.careplanner.repository;

import com.amc.careplanner.domain.MedicalContact;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MedicalContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalContactRepository extends JpaRepository<MedicalContact, Long>, JpaSpecificationExecutor<MedicalContact> {
}
