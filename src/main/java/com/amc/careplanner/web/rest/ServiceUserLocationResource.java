package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.ServiceUserLocationService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ServiceUserLocationDTO;
import com.amc.careplanner.service.dto.ServiceUserLocationCriteria;
import com.amc.careplanner.service.ServiceUserLocationQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.ServiceUserLocation}.
 */
@RestController
@RequestMapping("/api")
public class ServiceUserLocationResource {

    private final Logger log = LoggerFactory.getLogger(ServiceUserLocationResource.class);

    private static final String ENTITY_NAME = "serviceUserLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceUserLocationService serviceUserLocationService;

    private final ServiceUserLocationQueryService serviceUserLocationQueryService;

    public ServiceUserLocationResource(ServiceUserLocationService serviceUserLocationService, ServiceUserLocationQueryService serviceUserLocationQueryService) {
        this.serviceUserLocationService = serviceUserLocationService;
        this.serviceUserLocationQueryService = serviceUserLocationQueryService;
    }

    /**
     * {@code POST  /service-user-locations} : Create a new serviceUserLocation.
     *
     * @param serviceUserLocationDTO the serviceUserLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceUserLocationDTO, or with status {@code 400 (Bad Request)} if the serviceUserLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-user-locations")
    public ResponseEntity<ServiceUserLocationDTO> createServiceUserLocation(@Valid @RequestBody ServiceUserLocationDTO serviceUserLocationDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceUserLocation : {}", serviceUserLocationDTO);
        if (serviceUserLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceUserLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceUserLocationDTO result = serviceUserLocationService.save(serviceUserLocationDTO);
        return ResponseEntity.created(new URI("/api/service-user-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-user-locations} : Updates an existing serviceUserLocation.
     *
     * @param serviceUserLocationDTO the serviceUserLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceUserLocationDTO,
     * or with status {@code 400 (Bad Request)} if the serviceUserLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceUserLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-user-locations")
    public ResponseEntity<ServiceUserLocationDTO> updateServiceUserLocation(@Valid @RequestBody ServiceUserLocationDTO serviceUserLocationDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceUserLocation : {}", serviceUserLocationDTO);
        if (serviceUserLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceUserLocationDTO result = serviceUserLocationService.save(serviceUserLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceUserLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-user-locations} : get all the serviceUserLocations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceUserLocations in body.
     */
    @GetMapping("/service-user-locations")
    public ResponseEntity<List<ServiceUserLocationDTO>> getAllServiceUserLocations(ServiceUserLocationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceUserLocations by criteria: {}", criteria);
        Page<ServiceUserLocationDTO> page = serviceUserLocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-user-locations/count} : count all the serviceUserLocations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-user-locations/count")
    public ResponseEntity<Long> countServiceUserLocations(ServiceUserLocationCriteria criteria) {
        log.debug("REST request to count ServiceUserLocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceUserLocationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-user-locations/:id} : get the "id" serviceUserLocation.
     *
     * @param id the id of the serviceUserLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceUserLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-user-locations/{id}")
    public ResponseEntity<ServiceUserLocationDTO> getServiceUserLocation(@PathVariable Long id) {
        log.debug("REST request to get ServiceUserLocation : {}", id);
        Optional<ServiceUserLocationDTO> serviceUserLocationDTO = serviceUserLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceUserLocationDTO);
    }

    /**
     * {@code DELETE  /service-user-locations/:id} : delete the "id" serviceUserLocation.
     *
     * @param id the id of the serviceUserLocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-user-locations/{id}")
    public ResponseEntity<Void> deleteServiceUserLocation(@PathVariable Long id) {
        log.debug("REST request to delete ServiceUserLocation : {}", id);
        serviceUserLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
