package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.EmployeeAvailabilityService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EmployeeAvailabilityDTO;
import com.amc.careplanner.service.dto.EmployeeAvailabilityCriteria;
import com.amc.careplanner.service.EmployeeAvailabilityQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.EmployeeAvailability}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeAvailabilityResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeAvailabilityResource.class);

    private static final String ENTITY_NAME = "employeeAvailability";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeAvailabilityService employeeAvailabilityService;

    private final EmployeeAvailabilityQueryService employeeAvailabilityQueryService;

    public EmployeeAvailabilityResource(EmployeeAvailabilityService employeeAvailabilityService, EmployeeAvailabilityQueryService employeeAvailabilityQueryService) {
        this.employeeAvailabilityService = employeeAvailabilityService;
        this.employeeAvailabilityQueryService = employeeAvailabilityQueryService;
    }

    /**
     * {@code POST  /employee-availabilities} : Create a new employeeAvailability.
     *
     * @param employeeAvailabilityDTO the employeeAvailabilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeAvailabilityDTO, or with status {@code 400 (Bad Request)} if the employeeAvailability has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-availabilities")
    public ResponseEntity<EmployeeAvailabilityDTO> createEmployeeAvailability(@RequestBody EmployeeAvailabilityDTO employeeAvailabilityDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeAvailability : {}", employeeAvailabilityDTO);
        if (employeeAvailabilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeAvailability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeAvailabilityDTO result = employeeAvailabilityService.save(employeeAvailabilityDTO);
        return ResponseEntity.created(new URI("/api/employee-availabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-availabilities} : Updates an existing employeeAvailability.
     *
     * @param employeeAvailabilityDTO the employeeAvailabilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeAvailabilityDTO,
     * or with status {@code 400 (Bad Request)} if the employeeAvailabilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeAvailabilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-availabilities")
    public ResponseEntity<EmployeeAvailabilityDTO> updateEmployeeAvailability(@RequestBody EmployeeAvailabilityDTO employeeAvailabilityDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeAvailability : {}", employeeAvailabilityDTO);
        if (employeeAvailabilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeAvailabilityDTO result = employeeAvailabilityService.save(employeeAvailabilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeAvailabilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-availabilities} : get all the employeeAvailabilities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeAvailabilities in body.
     */
    @GetMapping("/employee-availabilities")
    public ResponseEntity<List<EmployeeAvailabilityDTO>> getAllEmployeeAvailabilities(EmployeeAvailabilityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmployeeAvailabilities by criteria: {}", criteria);
        Page<EmployeeAvailabilityDTO> page = employeeAvailabilityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-availabilities/count} : count all the employeeAvailabilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-availabilities/count")
    public ResponseEntity<Long> countEmployeeAvailabilities(EmployeeAvailabilityCriteria criteria) {
        log.debug("REST request to count EmployeeAvailabilities by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeAvailabilityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-availabilities/:id} : get the "id" employeeAvailability.
     *
     * @param id the id of the employeeAvailabilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeAvailabilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-availabilities/{id}")
    public ResponseEntity<EmployeeAvailabilityDTO> getEmployeeAvailability(@PathVariable Long id) {
        log.debug("REST request to get EmployeeAvailability : {}", id);
        Optional<EmployeeAvailabilityDTO> employeeAvailabilityDTO = employeeAvailabilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeAvailabilityDTO);
    }

    /**
     * {@code DELETE  /employee-availabilities/:id} : delete the "id" employeeAvailability.
     *
     * @param id the id of the employeeAvailabilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-availabilities/{id}")
    public ResponseEntity<Void> deleteEmployeeAvailability(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeAvailability : {}", id);
        employeeAvailabilityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
