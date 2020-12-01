package com.amc.careplanner.service;

import com.amc.careplanner.domain.EmployeeAvailability;
import com.amc.careplanner.repository.EmployeeAvailabilityRepository;
import com.amc.careplanner.service.dto.EmployeeAvailabilityDTO;
import com.amc.careplanner.service.mapper.EmployeeAvailabilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeAvailability}.
 */
@Service
@Transactional
public class EmployeeAvailabilityService {

    private final Logger log = LoggerFactory.getLogger(EmployeeAvailabilityService.class);

    private final EmployeeAvailabilityRepository employeeAvailabilityRepository;

    private final EmployeeAvailabilityMapper employeeAvailabilityMapper;

    public EmployeeAvailabilityService(EmployeeAvailabilityRepository employeeAvailabilityRepository, EmployeeAvailabilityMapper employeeAvailabilityMapper) {
        this.employeeAvailabilityRepository = employeeAvailabilityRepository;
        this.employeeAvailabilityMapper = employeeAvailabilityMapper;
    }

    /**
     * Save a employeeAvailability.
     *
     * @param employeeAvailabilityDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeAvailabilityDTO save(EmployeeAvailabilityDTO employeeAvailabilityDTO) {
        log.debug("Request to save EmployeeAvailability : {}", employeeAvailabilityDTO);
        EmployeeAvailability employeeAvailability = employeeAvailabilityMapper.toEntity(employeeAvailabilityDTO);
        employeeAvailability = employeeAvailabilityRepository.save(employeeAvailability);
        return employeeAvailabilityMapper.toDto(employeeAvailability);
    }

    /**
     * Get all the employeeAvailabilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeAvailabilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeAvailabilities");
        return employeeAvailabilityRepository.findAll(pageable)
            .map(employeeAvailabilityMapper::toDto);
    }


    /**
     * Get one employeeAvailability by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeAvailabilityDTO> findOne(Long id) {
        log.debug("Request to get EmployeeAvailability : {}", id);
        return employeeAvailabilityRepository.findById(id)
            .map(employeeAvailabilityMapper::toDto);
    }

    /**
     * Delete the employeeAvailability by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeAvailability : {}", id);
        employeeAvailabilityRepository.deleteById(id);
    }
}
