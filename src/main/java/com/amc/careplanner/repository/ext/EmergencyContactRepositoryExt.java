package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.EmergencyContact;
import com.amc.careplanner.repository.EmergencyContactRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmergencyContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmergencyContactRepositoryExt extends  EmergencyContactRepository{
}
