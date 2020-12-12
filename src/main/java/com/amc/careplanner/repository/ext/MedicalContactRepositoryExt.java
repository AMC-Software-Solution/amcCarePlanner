package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.MedicalContact;
import com.amc.careplanner.repository.MedicalContactRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MedicalContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalContactRepositoryExt extends MedicalContactRepository{
}
