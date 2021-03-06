package com.amc.careplanner.service;

import com.amc.careplanner.domain.ServiceUserLocation;
import com.amc.careplanner.repository.ServiceUserLocationRepository;
import com.amc.careplanner.service.dto.ServiceUserLocationDTO;
import com.amc.careplanner.service.mapper.ServiceUserLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceUserLocation}.
 */
@Service
@Transactional
public class ServiceUserLocationService {

    private final Logger log = LoggerFactory.getLogger(ServiceUserLocationService.class);

    private final ServiceUserLocationRepository serviceUserLocationRepository;

    private final ServiceUserLocationMapper serviceUserLocationMapper;

    public ServiceUserLocationService(ServiceUserLocationRepository serviceUserLocationRepository, ServiceUserLocationMapper serviceUserLocationMapper) {
        this.serviceUserLocationRepository = serviceUserLocationRepository;
        this.serviceUserLocationMapper = serviceUserLocationMapper;
    }

    /**
     * Save a serviceUserLocation.
     *
     * @param serviceUserLocationDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceUserLocationDTO save(ServiceUserLocationDTO serviceUserLocationDTO) {
        log.debug("Request to save ServiceUserLocation : {}", serviceUserLocationDTO);
        ServiceUserLocation serviceUserLocation = serviceUserLocationMapper.toEntity(serviceUserLocationDTO);
        serviceUserLocation = serviceUserLocationRepository.save(serviceUserLocation);
        return serviceUserLocationMapper.toDto(serviceUserLocation);
    }

    /**
     * Get all the serviceUserLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceUserLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceUserLocations");
        return serviceUserLocationRepository.findAll(pageable)
            .map(serviceUserLocationMapper::toDto);
    }


    /**
     * Get one serviceUserLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceUserLocationDTO> findOne(Long id) {
        log.debug("Request to get ServiceUserLocation : {}", id);
        return serviceUserLocationRepository.findById(id)
            .map(serviceUserLocationMapper::toDto);
    }

    /**
     * Delete the serviceUserLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceUserLocation : {}", id);
        serviceUserLocationRepository.deleteById(id);
    }
}
