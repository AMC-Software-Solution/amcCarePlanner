package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.EmployeeHoliday;
import com.amc.careplanner.repository.EmployeeHolidayRepository;
import com.amc.careplanner.repository.ext.EmployeeHolidayRepositoryExt;
import com.amc.careplanner.service.EmployeeHolidayService;
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
public class EmployeeHolidayServiceExt extends EmployeeHolidayService {

    private final Logger log = LoggerFactory.getLogger(EmployeeHolidayServiceExt.class);

    private final EmployeeHolidayRepositoryExt employeeHolidayRepositoryExt;

    private final EmployeeHolidayMapper employeeHolidayMapper;

    public EmployeeHolidayServiceExt(EmployeeHolidayRepositoryExt employeeHolidayRepositoryExt, EmployeeHolidayMapper employeeHolidayMapper) {
    	super(employeeHolidayRepositoryExt,employeeHolidayMapper);
        this.employeeHolidayRepositoryExt = employeeHolidayRepositoryExt;
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
        employeeHoliday = employeeHolidayRepositoryExt.save(employeeHoliday);
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
        return employeeHolidayRepositoryExt.findAll(pageable)
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
        return employeeHolidayRepositoryExt.findById(id)
            .map(employeeHolidayMapper::toDto);
    }

    /**
     * Delete the employeeHoliday by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeHoliday : {}", id);
        employeeHolidayRepositoryExt.deleteById(id);
    }
}
