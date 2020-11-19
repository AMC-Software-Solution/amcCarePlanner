package com.amc.careplanner.service;

import com.amc.careplanner.domain.ServiceUserEvent;
import com.amc.careplanner.repository.ServiceUserEventRepository;
import com.amc.careplanner.service.dto.ServiceUserEventDTO;
import com.amc.careplanner.service.mapper.ServiceUserEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceUserEvent}.
 */
@Service
@Transactional
public class ServiceUserEventService {

    private final Logger log = LoggerFactory.getLogger(ServiceUserEventService.class);

    private final ServiceUserEventRepository serviceUserEventRepository;

    private final ServiceUserEventMapper serviceUserEventMapper;

    public ServiceUserEventService(ServiceUserEventRepository serviceUserEventRepository, ServiceUserEventMapper serviceUserEventMapper) {
        this.serviceUserEventRepository = serviceUserEventRepository;
        this.serviceUserEventMapper = serviceUserEventMapper;
    }

    /**
     * Save a serviceUserEvent.
     *
     * @param serviceUserEventDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceUserEventDTO save(ServiceUserEventDTO serviceUserEventDTO) {
        log.debug("Request to save ServiceUserEvent : {}", serviceUserEventDTO);
        ServiceUserEvent serviceUserEvent = serviceUserEventMapper.toEntity(serviceUserEventDTO);
        serviceUserEvent = serviceUserEventRepository.save(serviceUserEvent);
        return serviceUserEventMapper.toDto(serviceUserEvent);
    }

    /**
     * Get all the serviceUserEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceUserEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceUserEvents");
        return serviceUserEventRepository.findAll(pageable)
            .map(serviceUserEventMapper::toDto);
    }


    /**
     * Get one serviceUserEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceUserEventDTO> findOne(Long id) {
        log.debug("Request to get ServiceUserEvent : {}", id);
        return serviceUserEventRepository.findById(id)
            .map(serviceUserEventMapper::toDto);
    }

    /**
     * Delete the serviceUserEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceUserEvent : {}", id);
        serviceUserEventRepository.deleteById(id);
    }
}
