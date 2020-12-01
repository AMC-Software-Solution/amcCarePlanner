package com.amc.careplanner.service;

import com.amc.careplanner.domain.EmployeeHoliday;
import com.amc.careplanner.repository.EmployeeHolidayRepository;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.mapper.EmployeeHolidayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeHoliday}.
 */
@Service
@Transactional
public class EmployeeHolidayService {

    private final Logger log = LoggerFactory.getLogger(EmployeeHolidayService.class);

    private final EmployeeHolidayRepository employeeHolidayRepository;

    private final EmployeeHolidayMapper employeeHolidayMapper;

    public EmployeeHolidayService(EmployeeHolidayRepository employeeHolidayRepository, EmployeeHolidayMapper employeeHolidayMapper) {
        this.employeeHolidayRepository = employeeHolidayRepository;
        this.employeeHolidayMapper = employeeHolidayMapper;
    }

    /**
     * Save a employeeHoliday.
     *
     * @param employeeHolidayDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeHolidayDTO save(EmployeeHolidayDTO employeeHolidayDTO) {
        log.debug("Request to save EmployeeHoliday : {}", employeeHolidayDTO);
        EmployeeHoliday employeeHoliday = employeeHolidayMapper.toEntity(employeeHolidayDTO);
        employeeHoliday = employeeHolidayRepository.save(employeeHoliday);
        return employeeHolidayMapper.toDto(employeeHoliday);
    }

    /**
     * Get all the employeeHolidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeHolidayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeHolidays");
        return employeeHolidayRepository.findAll(pageable)
            .map(employeeHolidayMapper::toDto);
    }


    /**
     * Get one employeeHoliday by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeHolidayDTO> findOne(Long id) {
        log.debug("Request to get EmployeeHoliday : {}", id);
        return employeeHolidayRepository.findById(id)
            .map(employeeHolidayMapper::toDto);
    }

    /**
     * Delete the employeeHoliday by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeHoliday : {}", id);
        employeeHolidayRepository.deleteById(id);
    }
}
