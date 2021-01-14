package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.ServiceUserContact;
import com.amc.careplanner.repository.ServiceUserContactRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceUserContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceUserContactRepositoryExt extends ServiceUserContactRepository{
}
