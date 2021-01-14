package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Timesheet;
import com.amc.careplanner.repository.TimesheetRepository;
import com.amc.careplanner.repository.ext.TimesheetRepositoryExt;
import com.amc.careplanner.service.TimesheetService;
import com.amc.careplanner.service.dto.TimesheetDTO;
import com.amc.careplanner.service.mapper.TimesheetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Timesheet}.
 */
@Service
@Transactional
public class TimesheetServiceExt extends TimesheetService{

    private final Logger log = LoggerFactory.getLogger(TimesheetServiceExt.class);

    private final TimesheetRepositoryExt timesheetRepositoryExt;

    private final TimesheetMapper timesheetMapper;

    public TimesheetServiceExt(TimesheetRepositoryExt timesheetRepositoryExt, TimesheetMapper timesheetMapper) {
        super(timesheetRepositoryExt,timesheetMapper);
    	this.timesheetRepositoryExt = timesheetRepositoryExt;
        this.timesheetMapper = timesheetMapper;
    }

    /**
     * Save a timesheet.
     *
     * @param timesheetDTO the entity to save.
     * @return the persisted entity.
     */
    public TimesheetDTO save(TimesheetDTO timesheetDTO) {
        log.debug("Request to save Timesheet : {}", timesheetDTO);
        Timesheet timesheet = timesheetMapper.toEntity(timesheetDTO);
        timesheet = timesheetRepositoryExt.save(timesheet);
        return timesheetMapper.toDto(timesheet);
    }

    /**
     * Get all the timesheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TimesheetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Timesheets");
        return timesheetRepositoryExt.findAll(pageable)
            .map(timesheetMapper::toDto);
    }


    /**
     * Get one timesheet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetDTO> findOne(Long id) {
        log.debug("Request to get Timesheet : {}", id);
        return timesheetRepositoryExt.findById(id)
            .map(timesheetMapper::toDto);
    }

    /**
     * Delete the timesheet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Timesheet : {}", id);
        timesheetRepositoryExt.deleteById(id);
    }
}
