package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.ServiceOrder;
import com.amc.careplanner.repository.ServiceOrderRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderRepositoryExt extends ServiceOrderRepository{
}
