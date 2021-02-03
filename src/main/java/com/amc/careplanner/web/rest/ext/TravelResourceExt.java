package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.TravelService;
import com.amc.careplanner.web.rest.TravelResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.TravelDTO;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.service.ext.TravelServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.TravelCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.TravelQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Travel}.
 */
@RestController
@RequestMapping("/api/v1")
public class TravelResourceExt extends TravelResource{

    private final Logger log = LoggerFactory.getLogger(TravelResourceExt.class);

    private static final String ENTITY_NAME = "travel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TravelServiceExt travelServiceExt;

    private final TravelQueryService travelQueryService;
    
    private final UserRepositoryExt userRepositoryExt;
    
    private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;

    public TravelResourceExt(TravelServiceExt travelServiceExt, TravelQueryService travelQueryService, UserRepositoryExt userRepositoryExt, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
        super(travelServiceExt,travelQueryService);
    	this.travelServiceExt = travelServiceExt;
        this.travelQueryService = travelQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
    }

    /**
     * {@code POST  /travels} : Create a new travel.
     *
     * @param travelDTO the travelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new travelDTO, or with status {@code 400 (Bad Request)} if the travel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-travel-by-client-id")
    public ResponseEntity<TravelDTO> createTravel(@Valid @RequestBody TravelDTO travelDTO) throws URISyntaxException {
        log.debug("REST request to save Travel : {}", travelDTO);
        if (travelDTO.getId() != null) {
            throw new BadRequestAlertException("A new travel cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      travelDTO.setDateCreated(ZonedDateTime.now());
        travelDTO.setLastUpdatedDate(ZonedDateTime.now());
        travelDTO.setClientId(getClientIdFromLoggedInUser());
        TravelDTO result = travelServiceExt.save(travelDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "createTravel", "/api/v1/create-travel-by-client-id",
        		result.getTravelMode() + " has just been created", "Travel", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.created(new URI("/api/travels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /travels} : Updates an existing travel.
     *
     * @param travelDTO the travelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travelDTO,
     * or with status {@code 400 (Bad Request)} if the travelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the travelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-travel-by-client-id")
    public ResponseEntity<TravelDTO> updateTravel(@Valid @RequestBody TravelDTO travelDTO) throws URISyntaxException {
        log.debug("REST request to update Travel : {}", travelDTO);
        if (travelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (travelDTO != null && travelDTO.getClientId() != null && travelDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        travelDTO.setLastUpdatedDate(ZonedDateTime.now());
        TravelDTO result = travelServiceExt.save(travelDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "updateTravel", "/api/v1/update-travel-by-client-id",
        		result.getTravelMode() + " has just been created", "Travel", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, travelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /travels} : get all the travels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of travels in body.
     */
    @GetMapping("/get-all-travels-by-client-id")
    public ResponseEntity<List<TravelDTO>> getAllTravels(TravelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Travels by criteria: {}", criteria);
        TravelCriteria travelCriteria = new TravelCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		travelCriteria.setClientId(longFilterForClientId);
        Page<TravelDTO> page = travelQueryService.findByCriteria(travelCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /travels/count} : count all the travels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/travels/count")
    public ResponseEntity<Long> countTravels(TravelCriteria criteria) {
        log.debug("REST request to count Travels by criteria: {}", criteria);
        return ResponseEntity.ok().body(travelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /travels/:id} : get the "id" travel.
     *
     * @param id the id of the travelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the travelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-travel-by-client-d/{id}")
    public ResponseEntity<TravelDTO> getTravel(@PathVariable Long id) {
        log.debug("REST request to get Travel : {}", id);
        Optional<TravelDTO> travelDTO = travelServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(travelDTO);
    }

    /**
     * {@code DELETE  /travels/:id} : delete the "id" travel.
     *
     * @param id the id of the travelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-travel-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteTravel(@PathVariable Long id) {
        log.debug("REST request to delete Travel : {}", id);
        travelServiceExt.delete(id);
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
