package com.amc.careplanner.service;

import com.amc.careplanner.domain.ServiceOrder;
import com.amc.careplanner.repository.ServiceOrderRepository;
import com.amc.careplanner.service.dto.ServiceOrderDTO;
import com.amc.careplanner.service.mapper.ServiceOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceOrder}.
 */
@Service
@Transactional
public class ServiceOrderService {

    private final Logger log = LoggerFactory.getLogger(ServiceOrderService.class);

    private final ServiceOrderRepository serviceOrderRepository;

    private final ServiceOrderMapper serviceOrderMapper;

    public ServiceOrderService(ServiceOrderRepository serviceOrderRepository, ServiceOrderMapper serviceOrderMapper) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceOrderMapper = serviceOrderMapper;
    }

    /**
     * Save a serviceOrder.
     *
     * @param serviceOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceOrderDTO save(ServiceOrderDTO serviceOrderDTO) {
        log.debug("Request to save ServiceOrder : {}", serviceOrderDTO);
        ServiceOrder serviceOrder = serviceOrderMapper.toEntity(serviceOrderDTO);
        serviceOrder = serviceOrderRepository.save(serviceOrder);
        return serviceOrderMapper.toDto(serviceOrder);
    }

    /**
     * Get all the serviceOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceOrders");
        return serviceOrderRepository.findAll(pageable)
            .map(serviceOrderMapper::toDto);
    }


    /**
     * Get one serviceOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceOrderDTO> findOne(Long id) {
        log.debug("Request to get ServiceOrder : {}", id);
        return serviceOrderRepository.findById(id)
            .map(serviceOrderMapper::toDto);
    }

    /**
     * Delete the serviceOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceOrder : {}", id);
        serviceOrderRepository.deleteById(id);
    }
}
