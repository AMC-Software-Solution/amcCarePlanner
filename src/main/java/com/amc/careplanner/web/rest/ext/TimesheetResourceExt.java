package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.TimesheetService;
import com.amc.careplanner.web.rest.TimesheetResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.TimesheetDTO;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.service.ext.TimesheetServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.dto.TimesheetCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.TimesheetQueryService;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.Timesheet}.
 */
@RestController
@RequestMapping("/api/v1")
public class TimesheetResourceExt extends TimesheetResource{

    private final Logger log = LoggerFactory.getLogger(TimesheetResourceExt.class);

    private static final String ENTITY_NAME = "timesheet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimesheetServiceExt timesheetServiceExt;

    private final TimesheetQueryService timesheetQueryService;
    
    private final UserRepositoryExt userRepositoryExt;
    
    private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;

    public TimesheetResourceExt(TimesheetServiceExt timesheetServiceExt, TimesheetQueryService timesheetQueryService, UserRepositoryExt userRepositoryExt, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
        super(timesheetServiceExt,timesheetQueryService);
    	this.timesheetServiceExt = timesheetServiceExt;
        this.timesheetQueryService = timesheetQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
    }

    /**
     * {@code POST  /timesheets} : Create a new timesheet.
     *
     * @param timesheetDTO the timesheetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timesheetDTO, or with status {@code 400 (Bad Request)} if the timesheet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-timesheet-by-client-id")
    public ResponseEntity<TimesheetDTO> createTimesheet(@Valid @RequestBody TimesheetDTO timesheetDTO) throws URISyntaxException {
        log.debug("REST request to save Timesheet : {}", timesheetDTO);
        if (timesheetDTO.getId() != null) {
            throw new BadRequestAlertException("A new timesheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //timesheetDTO.setDateCreated(ZonedDateTime.now());
        timesheetDTO.setLastUpdatedDate(ZonedDateTime.now());
        timesheetDTO.setClientId(getClientIdFromLoggedInUser());
        TimesheetDTO result = timesheetServiceExt.save(timesheetDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "createTimesheet", "/api/v1/create-timesheet-by-client-id",
        		result.getTimesheetDate() + " has just been created", "Timesheet", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.created(new URI("/api/timesheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /timesheets} : Updates an existing timesheet.
     *
     * @param timesheetDTO the timesheetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timesheetDTO,
     * or with status {@code 400 (Bad Request)} if the timesheetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timesheetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-timesheet-by-client-id")
    public ResponseEntity<TimesheetDTO> updateTimesheet(@Valid @RequestBody TimesheetDTO timesheetDTO) throws URISyntaxException {
        log.debug("REST request to update Timesheet : {}", timesheetDTO);
        if (timesheetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (timesheetDTO != null && timesheetDTO.getClientId() != null && timesheetDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        timesheetDTO.setLastUpdatedDate(ZonedDateTime.now());
        TimesheetDTO result = timesheetServiceExt.save(timesheetDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "updateTimesheet", "/api/v1/update-timesheet-by-client-id",
        		result.getTimesheetDate() + " has just been created", "Timesheet", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timesheetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /timesheets} : get all the timesheets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timesheets in body.
     */
    @GetMapping("/get-all-timesheets-by-client-id")
    public ResponseEntity<List<TimesheetDTO>> getAllTimesheets(TimesheetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Timesheets by criteria: {}", criteria);
        TimesheetCriteria timesheetCriteria = new TimesheetCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		timesheetCriteria.setClientId(longFilterForClientId);
        Page<TimesheetDTO> page = timesheetQueryService.findByCriteria(timesheetCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/get-all-timesheets-by-client-id-employee-id/{employeeId}")   
    public ResponseEntity< List<TimesheetDTO>> getAllTimesheetsByEmployeeId(@PathVariable Long employeeId, Pageable pageable) {
        log.debug("REST request to get Timesheets : {}", employeeId);
        Long loggedInClientId = getClientIdFromLoggedInUser();
        TimesheetCriteria timesheetCriteria = new TimesheetCriteria();
       
		
        LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(loggedInClientId);
		timesheetCriteria.setClientId(longFilterForClientId);
		
		LongFilter longFilterForEmployeeId = new LongFilter();
		longFilterForEmployeeId.setEquals(employeeId);
//		timesheetCriteria.setEmployeeId(longFilterForEmployeeId);
		
		
		 Page<TimesheetDTO> listOfPages = timesheetQueryService.findByCriteria(timesheetCriteria,pageable);
		 List <TimesheetDTO> listOfDTOs = listOfPages.getContent();
		 if (listOfDTOs != null && listOfDTOs.size() > 0) {
			 TimesheetDTO timesheetDTO =  listOfDTOs.get(0);
        	if (timesheetDTO.getClientId() != null && timesheetDTO.getClientId() != loggedInClientId) {
	        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
	         }
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(listOfDTOs));
    }

    /**
     * {@code GET  /timesheets/count} : count all the timesheets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/timesheets/count")
    public ResponseEntity<Long> countTimesheets(TimesheetCriteria criteria) {
        log.debug("REST request to count Timesheets by criteria: {}", criteria);
        return ResponseEntity.ok().body(timesheetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /timesheets/:id} : get the "id" timesheet.
     *
     * @param id the id of the timesheetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timesheetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-timesheet-by-client-id/{id}")
    public ResponseEntity<TimesheetDTO> getTimesheet(@PathVariable Long id) {
        log.debug("REST request to get Timesheet : {}", id);
        Optional<TimesheetDTO> timesheetDTO = timesheetServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(timesheetDTO);
    }

    /**
     * {@code DELETE  /timesheets/:id} : delete the "id" timesheet.
     *
     * @param id the id of the timesheetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-timesheet-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteTimesheet(@PathVariable Long id) {
        log.debug("REST request to delete Timesheet : {}", id);
        timesheetServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    private Long getClientIdFromLoggedInUser() {
    	Long clientId = 0L;
    	String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();
		
		if(loggedInAdminUser != null) {
			clientId = Long.valueOf(loggedInAdminUser.getLogin());
		}
		
		return clientId;
    }
    
}
