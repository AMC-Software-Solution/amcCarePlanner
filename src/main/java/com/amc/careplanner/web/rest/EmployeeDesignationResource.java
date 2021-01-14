package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.EmployeeDesignationService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EmployeeDesignationDTO;
import com.amc.careplanner.service.dto.EmployeeDesignationCriteria;
import com.amc.careplanner.service.EmployeeDesignationQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.EmployeeDesignation}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeDesignationResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeDesignationResource.class);

    private static final String ENTITY_NAME = "employeeDesignation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeDesignationService employeeDesignationService;

    private final EmployeeDesignationQueryService employeeDesignationQueryService;

    public EmployeeDesignationResource(EmployeeDesignationService employeeDesignationService, EmployeeDesignationQueryService employeeDesignationQueryService) {
        this.employeeDesignationService = employeeDesignationService;
        this.employeeDesignationQueryService = employeeDesignationQueryService;
    }

    /**
     * {@code POST  /employee-designations} : Create a new employeeDesignation.
     *
     * @param employeeDesignationDTO the employeeDesignationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDesignationDTO, or with status {@code 400 (Bad Request)} if the employeeDesignation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-designations")
    public ResponseEntity<EmployeeDesignationDTO> createEmployeeDesignation(@Valid @RequestBody EmployeeDesignationDTO employeeDesignationDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeDesignation : {}", employeeDesignationDTO);
        if (employeeDesignationDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeDesignation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeDesignationDTO result = employeeDesignationService.save(employeeDesignationDTO);
        return ResponseEntity.created(new URI("/api/employee-designations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-designations} : Updates an existing employeeDesignation.
     *
     * @param employeeDesignationDTO the employeeDesignationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDesignationDTO,
     * or with status {@code 400 (Bad Request)} if the employeeDesignationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDesignationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-designations")
    public ResponseEntity<EmployeeDesignationDTO> updateEmployeeDesignation(@Valid @RequestBody EmployeeDesignationDTO employeeDesignationDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeDesignation : {}", employeeDesignationDTO);
        if (employeeDesignationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeDesignationDTO result = employeeDesignationService.save(employeeDesignationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDesignationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-designations} : get all the employeeDesignations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeDesignations in body.
     */
    @GetMapping("/employee-designations")
    public ResponseEntity<List<EmployeeDesignationDTO>> getAllEmployeeDesignations(EmployeeDesignationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmployeeDesignations by criteria: {}", criteria);
        Page<EmployeeDesignationDTO> page = employeeDesignationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-designations/count} : count all the employeeDesignations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-designations/count")
    public ResponseEntity<Long> countEmployeeDesignations(EmployeeDesignationCriteria criteria) {
        log.debug("REST request to count EmployeeDesignations by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeDesignationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-designations/:id} : get the "id" employeeDesignation.
     *
     * @param id the id of the employeeDesignationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDesignationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-designations/{id}")
    public ResponseEntity<EmployeeDesignationDTO> getEmployeeDesignation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeDesignation : {}", id);
        Optional<EmployeeDesignationDTO> employeeDesignationDTO = employeeDesignationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDesignationDTO);
    }

    /**
     * {@code DELETE  /employee-designations/:id} : delete the "id" employeeDesignation.
     *
     * @param id the id of the employeeDesignationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-designations/{id}")
    public ResponseEntity<Void> deleteEmployeeDesignation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeDesignation : {}", id);
        employeeDesignationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
