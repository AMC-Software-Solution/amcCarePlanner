package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.ServiceUserEvent;
import com.amc.careplanner.repository.ServiceUserEventRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceUserEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceUserEventRepositoryExt extends ServiceUserEventRepository{
}
