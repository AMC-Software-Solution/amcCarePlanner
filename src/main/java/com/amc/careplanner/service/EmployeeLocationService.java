package com.amc.careplanner.service;

import com.amc.careplanner.domain.EmployeeLocation;
import com.amc.careplanner.repository.EmployeeLocationRepository;
import com.amc.careplanner.service.dto.EmployeeLocationDTO;
import com.amc.careplanner.service.mapper.EmployeeLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeLocation}.
 */
@Service
@Transactional
public class EmployeeLocationService {

    private final Logger log = LoggerFactory.getLogger(EmployeeLocationService.class);

    private final EmployeeLocationRepository employeeLocationRepository;

    private final EmployeeLocationMapper employeeLocationMapper;

    public EmployeeLocationService(EmployeeLocationRepository employeeLocationRepository, EmployeeLocationMapper employeeLocationMapper) {
        this.employeeLocationRepository = employeeLocationRepository;
        this.employeeLocationMapper = employeeLocationMapper;
    }

    /**
     * Save a employeeLocation.
     *
     * @param employeeLocationDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeLocationDTO save(EmployeeLocationDTO employeeLocationDTO) {
        log.debug("Request to save EmployeeLocation : {}", employeeLocationDTO);
        EmployeeLocation employeeLocation = employeeLocationMapper.toEntity(employeeLocationDTO);
        employeeLocation = employeeLocationRepository.save(employeeLocation);
        return employeeLocationMapper.toDto(employeeLocation);
    }

    /**
     * Get all the employeeLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeLocations");
        return employeeLocationRepository.findAll(pageable)
            .map(employeeLocationMapper::toDto);
    }


    /**
     * Get one employeeLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeLocationDTO> findOne(Long id) {
        log.debug("Request to get EmployeeLocation : {}", id);
        return employeeLocationRepository.findById(id)
            .map(employeeLocationMapper::toDto);
    }

    /**
     * Delete the employeeLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeLocation : {}", id);
        employeeLocationRepository.deleteById(id);
    }
}
