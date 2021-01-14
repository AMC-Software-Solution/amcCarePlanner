package com.amc.careplanner.service;

import com.amc.careplanner.domain.EmployeeDesignation;
import com.amc.careplanner.repository.EmployeeDesignationRepository;
import com.amc.careplanner.service.dto.EmployeeDesignationDTO;
import com.amc.careplanner.service.mapper.EmployeeDesignationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeDesignation}.
 */
@Service
@Transactional
public class EmployeeDesignationService {

    private final Logger log = LoggerFactory.getLogger(EmployeeDesignationService.class);

    private final EmployeeDesignationRepository employeeDesignationRepository;

    private final EmployeeDesignationMapper employeeDesignationMapper;

    public EmployeeDesignationService(EmployeeDesignationRepository employeeDesignationRepository, EmployeeDesignationMapper employeeDesignationMapper) {
        this.employeeDesignationRepository = employeeDesignationRepository;
        this.employeeDesignationMapper = employeeDesignationMapper;
    }

    /**
     * Save a employeeDesignation.
     *
     * @param employeeDesignationDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDesignationDTO save(EmployeeDesignationDTO employeeDesignationDTO) {
        log.debug("Request to save EmployeeDesignation : {}", employeeDesignationDTO);
        EmployeeDesignation employeeDesignation = employeeDesignationMapper.toEntity(employeeDesignationDTO);
        employeeDesignation = employeeDesignationRepository.save(employeeDesignation);
        return employeeDesignationMapper.toDto(employeeDesignation);
    }

    /**
     * Get all the employeeDesignations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDesignationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeDesignations");
        return employeeDesignationRepository.findAll(pageable)
            .map(employeeDesignationMapper::toDto);
    }


    /**
     * Get one employeeDesignation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDesignationDTO> findOne(Long id) {
        log.debug("Request to get EmployeeDesignation : {}", id);
        return employeeDesignationRepository.findById(id)
            .map(employeeDesignationMapper::toDto);
    }

    /**
     * Delete the employeeDesignation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeDesignation : {}", id);
        employeeDesignationRepository.deleteById(id);
    }
}
