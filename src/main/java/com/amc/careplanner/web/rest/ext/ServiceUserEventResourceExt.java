package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.ServiceUserEventService;
import com.amc.careplanner.web.rest.ServiceUserEventResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ServiceUserEventDTO;
import com.amc.careplanner.service.ext.ServiceUserEventServiceExt;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.ServiceUserEventCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.ServiceUserEventQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.ServiceUserEvent}.
 */
@RestController
@RequestMapping("/api/v1")
public class ServiceUserEventResourceExt extends ServiceUserEventResource{

    private final Logger log = LoggerFactory.getLogger(ServiceUserEventResourceExt.class);

    private static final String ENTITY_NAME = "serviceUserEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceUserEventServiceExt serviceUserEventServiceExt;

    private final ServiceUserEventQueryService serviceUserEventQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public ServiceUserEventResourceExt(ServiceUserEventServiceExt serviceUserEventServiceExt, ServiceUserEventQueryService serviceUserEventQueryService, UserRepositoryExt userRepositoryExt) {
        super(serviceUserEventServiceExt,serviceUserEventQueryService);
    	this.serviceUserEventServiceExt = serviceUserEventServiceExt;
        this.serviceUserEventQueryService = serviceUserEventQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /service-user-events} : Create a new serviceUserEvent.
     *
     * @param serviceUserEventDTO the serviceUserEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceUserEventDTO, or with status {@code 400 (Bad Request)} if the serviceUserEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-service-user-event-by-client-id")
    public ResponseEntity<ServiceUserEventDTO> createServiceUserEvent(@Valid @RequestBody ServiceUserEventDTO serviceUserEventDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceUserEvent : {}", serviceUserEventDTO);
        if (serviceUserEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceUserEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      serviceUserEventDTO.setDateCreated(ZonedDateTime.now());
        serviceUserEventDTO.setLastUpdatedDate(ZonedDateTime.now());
        serviceUserEventDTO.setClientId(getClientIdFromLoggedInUser());
        ServiceUserEventDTO result = serviceUserEventServiceExt.save(serviceUserEventDTO);
        return ResponseEntity.created(new URI("/api/service-user-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-user-events} : Updates an existing serviceUserEvent.
     *
     * @param serviceUserEventDTO the serviceUserEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceUserEventDTO,
     * or with status {@code 400 (Bad Request)} if the serviceUserEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceUserEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-service-user-event-by-client-id")
    public ResponseEntity<ServiceUserEventDTO> updateServiceUserEvent(@Valid @RequestBody ServiceUserEventDTO serviceUserEventDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceUserEvent : {}", serviceUserEventDTO);
        if (serviceUserEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (serviceUserEventDTO != null && serviceUserEventDTO.getClientId() != null && serviceUserEventDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        serviceUserEventDTO.setLastUpdatedDate(ZonedDateTime.now());
        ServiceUserEventDTO result = serviceUserEventServiceExt.save(serviceUserEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceUserEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-user-events} : get all the serviceUserEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceUserEvents in body.
     */
    @GetMapping("/get-all-service-user-events-by-client-id")
    public ResponseEntity<List<ServiceUserEventDTO>> getAllServiceUserEvents(ServiceUserEventCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceUserEvents by criteria: {}", criteria);
        ServiceUserEventCriteria serviceUserEventCriteria = new ServiceUserEventCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		serviceUserEventCriteria.setClientId(longFilterForClientId);
        Page<ServiceUserEventDTO> page = serviceUserEventQueryService.findByCriteria(serviceUserEventCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-user-events/count} : count all the serviceUserEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-user-events/count")
    public ResponseEntity<Long> countServiceUserEvents(ServiceUserEventCriteria criteria) {
        log.debug("REST request to count ServiceUserEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceUserEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-user-events/:id} : get the "id" serviceUserEvent.
     *
     * @param id the id of the serviceUserEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceUserEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-service-user-event-by-client-id/{id}")
    public ResponseEntity<ServiceUserEventDTO> getServiceUserEvent(@PathVariable Long id) {
        log.debug("REST request to get ServiceUserEvent : {}", id);
        Optional<ServiceUserEventDTO> serviceUserEventDTO = serviceUserEventServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceUserEventDTO);
    }

    /**
     * {@code DELETE  /service-user-events/:id} : delete the "id" serviceUserEvent.
     *
     * @param id the id of the serviceUserEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-service-user-event-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteServiceUserEvent(@PathVariable Long id) {
        log.debug("REST request to delete ServiceUserEvent : {}", id);
        serviceUserEventServiceExt.delete(id);
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
