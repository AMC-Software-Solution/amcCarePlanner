package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.SystemEventsHistoryService;
import com.amc.careplanner.web.rest.SystemEventsHistoryResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.SystemEventsHistoryDTO;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.SystemEventsHistoryCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.SystemEventsHistoryQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.SystemEventsHistory}.
 */
@RestController
@RequestMapping("/api/v1")
public class SystemEventsHistoryResourceExt extends SystemEventsHistoryResource{

    private final Logger log = LoggerFactory.getLogger(SystemEventsHistoryResourceExt.class);

    private static final String ENTITY_NAME = "systemEventsHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;

    private final SystemEventsHistoryQueryService systemEventsHistoryQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public SystemEventsHistoryResourceExt(SystemEventsHistoryServiceExt systemEventsHistoryServiceExt, SystemEventsHistoryQueryService systemEventsHistoryQueryService, UserRepositoryExt userRepositoryExt) {
        super(systemEventsHistoryServiceExt,systemEventsHistoryQueryService);
    	this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
        this.systemEventsHistoryQueryService = systemEventsHistoryQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /system-events-histories} : Create a new systemEventsHistory.
     *
     * @param systemEventsHistoryDTO the systemEventsHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemEventsHistoryDTO, or with status {@code 400 (Bad Request)} if the systemEventsHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-system-events-history-by-client-id")
    public ResponseEntity<SystemEventsHistoryDTO> createSystemEventsHistory(@Valid @RequestBody SystemEventsHistoryDTO systemEventsHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save SystemEventsHistory : {}", systemEventsHistoryDTO);
        if (systemEventsHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemEventsHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      systemEventsHistoryDTO.setDateCreated(ZonedDateTime.now());
//      systemEventsHistoryDTO.setLastUpdatedDate(ZonedDateTime.now());
        systemEventsHistoryDTO.setClientId(getClientIdFromLoggedInUser());
        SystemEventsHistoryDTO result = systemEventsHistoryServiceExt.save(systemEventsHistoryDTO);
        return ResponseEntity.created(new URI("/api/system-events-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-events-histories} : Updates an existing systemEventsHistory.
     *
     * @param systemEventsHistoryDTO the systemEventsHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemEventsHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the systemEventsHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemEventsHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-system-events-history-by-client-id")
    public ResponseEntity<SystemEventsHistoryDTO> updateSystemEventsHistory(@Valid @RequestBody SystemEventsHistoryDTO systemEventsHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update SystemEventsHistory : {}", systemEventsHistoryDTO);
        if (systemEventsHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
//        if (systemEventsHistoryDTO != null && systemEventsHistoryDTO.getClientId() != null && systemEventsHistoryDTO.getClientId() != getClientIdFromLoggedInUser()) {
//      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
//      }
//        systemEventsHistoryDTO.setLastUpdatedDate(ZonedDateTime.now());
        SystemEventsHistoryDTO result = systemEventsHistoryServiceExt.save(systemEventsHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemEventsHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /system-events-histories} : get all the systemEventsHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemEventsHistories in body.
     */
    @GetMapping("/get-all-system-events-histories-by-client-id")
    public ResponseEntity<List<SystemEventsHistoryDTO>> getAllSystemEventsHistories(SystemEventsHistoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SystemEventsHistories by criteria: {}", criteria);
        SystemEventsHistoryCriteria systemEventsHistoryCriteria = new SystemEventsHistoryCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		systemEventsHistoryCriteria.setClientId(longFilterForClientId);
        Page<SystemEventsHistoryDTO> page = systemEventsHistoryQueryService.findByCriteria(systemEventsHistoryCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-events-histories/count} : count all the systemEventsHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/system-events-histories/count")
    public ResponseEntity<Long> countSystemEventsHistories(SystemEventsHistoryCriteria criteria) {
        log.debug("REST request to count SystemEventsHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemEventsHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /system-events-histories/:id} : get the "id" systemEventsHistory.
     *
     * @param id the id of the systemEventsHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemEventsHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-system-events-history-by-client-id/{id}")
    public ResponseEntity<SystemEventsHistoryDTO> getSystemEventsHistory(@PathVariable Long id) {
        log.debug("REST request to get SystemEventsHistory : {}", id);
        Optional<SystemEventsHistoryDTO> systemEventsHistoryDTO = systemEventsHistoryServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemEventsHistoryDTO);
    }

    /**
     * {@code DELETE  /system-events-histories/:id} : delete the "id" systemEventsHistory.
     *
     * @param id the id of the systemEventsHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-system-events-history-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteSystemEventsHistory(@PathVariable Long id) {
        log.debug("REST request to delete SystemEventsHistory : {}", id);
        systemEventsHistoryServiceExt.delete(id);
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
