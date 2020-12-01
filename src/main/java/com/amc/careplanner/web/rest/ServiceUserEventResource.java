package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.ServiceUserEventService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ServiceUserEventDTO;
import com.amc.careplanner.service.dto.ServiceUserEventCriteria;
import com.amc.careplanner.service.ServiceUserEventQueryService;

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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.ServiceUserEvent}.
 */
@RestController
@RequestMapping("/api")
public class ServiceUserEventResource {

    private final Logger log = LoggerFactory.getLogger(ServiceUserEventResource.class);

    private static final String ENTITY_NAME = "serviceUserEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceUserEventService serviceUserEventService;

    private final ServiceUserEventQueryService serviceUserEventQueryService;

    public ServiceUserEventResource(ServiceUserEventService serviceUserEventService, ServiceUserEventQueryService serviceUserEventQueryService) {
        this.serviceUserEventService = serviceUserEventService;
        this.serviceUserEventQueryService = serviceUserEventQueryService;
    }

    /**
     * {@code POST  /service-user-events} : Create a new serviceUserEvent.
     *
     * @param serviceUserEventDTO the serviceUserEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceUserEventDTO, or with status {@code 400 (Bad Request)} if the serviceUserEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-user-events")
    public ResponseEntity<ServiceUserEventDTO> createServiceUserEvent(@Valid @RequestBody ServiceUserEventDTO serviceUserEventDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceUserEvent : {}", serviceUserEventDTO);
        if (serviceUserEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceUserEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceUserEventDTO result = serviceUserEventService.save(serviceUserEventDTO);
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
    @PutMapping("/service-user-events")
    public ResponseEntity<ServiceUserEventDTO> updateServiceUserEvent(@Valid @RequestBody ServiceUserEventDTO serviceUserEventDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceUserEvent : {}", serviceUserEventDTO);
        if (serviceUserEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceUserEventDTO result = serviceUserEventService.save(serviceUserEventDTO);
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
    @GetMapping("/service-user-events")
    public ResponseEntity<List<ServiceUserEventDTO>> getAllServiceUserEvents(ServiceUserEventCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceUserEvents by criteria: {}", criteria);
        Page<ServiceUserEventDTO> page = serviceUserEventQueryService.findByCriteria(criteria, pageable);
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
    @GetMapping("/service-user-events/{id}")
    public ResponseEntity<ServiceUserEventDTO> getServiceUserEvent(@PathVariable Long id) {
        log.debug("REST request to get ServiceUserEvent : {}", id);
        Optional<ServiceUserEventDTO> serviceUserEventDTO = serviceUserEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceUserEventDTO);
    }

    /**
     * {@code DELETE  /service-user-events/:id} : delete the "id" serviceUserEvent.
     *
     * @param id the id of the serviceUserEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-user-events/{id}")
    public ResponseEntity<Void> deleteServiceUserEvent(@PathVariable Long id) {
        log.debug("REST request to delete ServiceUserEvent : {}", id);
        serviceUserEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
