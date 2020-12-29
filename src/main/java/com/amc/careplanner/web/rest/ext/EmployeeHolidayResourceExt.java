package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.EmployeeHolidayService;
import com.amc.careplanner.web.rest.EmployeeHolidayResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.ext.EmployeeHolidayServiceExt;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.EmployeeHolidayQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.EmployeeHoliday}.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeHolidayResourceExt extends EmployeeHolidayResource{

    private final Logger log = LoggerFactory.getLogger(EmployeeHolidayResourceExt.class);

    private static final String ENTITY_NAME = "employeeHoliday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeHolidayServiceExt employeeHolidayServiceExt;

    private final EmployeeHolidayQueryService employeeHolidayQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public EmployeeHolidayResourceExt(EmployeeHolidayServiceExt employeeHolidayServiceExt, EmployeeHolidayQueryService employeeHolidayQueryService, UserRepositoryExt userRepositoryExt) {
    	super(employeeHolidayServiceExt,employeeHolidayQueryService);
        this.employeeHolidayServiceExt = employeeHolidayServiceExt;
        this.employeeHolidayQueryService = employeeHolidayQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /employee-holidays} : Create a new employeeHoliday.
     *
     * @param employeeHolidayDTO the employeeHolidayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeHolidayDTO, or with status {@code 400 (Bad Request)} if the employeeHoliday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-employee-holiday-by-client-id")
    public ResponseEntity<EmployeeHolidayDTO> createEmployeeHoliday(@Valid @RequestBody EmployeeHolidayDTO employeeHolidayDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeHoliday : {}", employeeHolidayDTO);
        if (employeeHolidayDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeHoliday cannot already have an ID", ENTITY_NAME, "idexists");
        }
//        employeeHolidayDTO.setDateCreated(ZonedDateTime.now());
        employeeHolidayDTO.setLastUpdatedDate(ZonedDateTime.now());
        employeeHolidayDTO.setClientId(getClientIdFromLoggedInUser());
        EmployeeHolidayDTO result = employeeHolidayServiceExt.save(employeeHolidayDTO);
        return ResponseEntity.created(new URI("/api/employee-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-holidays} : Updates an existing employeeHoliday.
     *
     * @param employeeHolidayDTO the employeeHolidayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeHolidayDTO,
     * or with status {@code 400 (Bad Request)} if the employeeHolidayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeHolidayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-employee-holiday-by-client-id")
    public ResponseEntity<EmployeeHolidayDTO> updateEmployeeHoliday(@Valid @RequestBody EmployeeHolidayDTO employeeHolidayDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeHoliday : {}", employeeHolidayDTO);
        if (employeeHolidayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        if (employeeHolidayDTO != null && employeeHolidayDTO.getClientId() != null && employeeHolidayDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        employeeHolidayDTO.setLastUpdatedDate(ZonedDateTime.now());
        EmployeeHolidayDTO result = employeeHolidayServiceExt.save(employeeHolidayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeHolidayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-holidays} : get all the employeeHolidays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeHolidays in body.
     */
    @GetMapping("/employee-holidays")
    public ResponseEntity<List<EmployeeHolidayDTO>> getAllEmployeeHolidays(EmployeeHolidayCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmployeeHolidays by criteria: {}", criteria);
       EmployeeHolidayCriteria employeeHolidayCriteria = new EmployeeHolidayCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		employeeHolidayCriteria.setClientId(longFilterForClientId);
        Page<EmployeeHolidayDTO> page = employeeHolidayQueryService.findByCriteria(employeeHolidayCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-holidays/count} : count all the employeeHolidays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-holidays/count")
    public ResponseEntity<Long> countEmployeeHolidays(EmployeeHolidayCriteria criteria) {
        log.debug("REST request to count EmployeeHolidays by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeHolidayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-holidays/:id} : get the "id" employeeHoliday.
     *
     * @param id the id of the employeeHolidayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeHolidayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-holidays/{id}")
    public ResponseEntity<EmployeeHolidayDTO> getEmployeeHoliday(@PathVariable Long id) {
        log.debug("REST request to get EmployeeHoliday : {}", id);
        Optional<EmployeeHolidayDTO> employeeHolidayDTO = employeeHolidayServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeHolidayDTO);
    }

    /**
     * {@code DELETE  /employee-holidays/:id} : delete the "id" employeeHoliday.
     *
     * @param id the id of the employeeHolidayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-holidays/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteEmployeeHoliday(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeHoliday : {}", id);
        employeeHolidayServiceExt.delete(id);
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
