package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.EmployeeLocationService;
import com.amc.careplanner.web.rest.EmployeeLocationResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EmployeeLocationDTO;
import com.amc.careplanner.service.ext.EmployeeLocationServiceExt;
import com.amc.careplanner.service.dto.EmployeeLocationCriteria;
import com.amc.careplanner.service.EmployeeLocationQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.EmployeeLocation}.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeLocationResourceExt extends EmployeeLocationResource{

    private final Logger log = LoggerFactory.getLogger(EmployeeLocationResourceExt.class);

    private static final String ENTITY_NAME = "employeeLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeLocationServiceExt employeeLocationServiceExt;

    private final EmployeeLocationQueryService employeeLocationQueryService;

    public EmployeeLocationResourceExt(EmployeeLocationServiceExt employeeLocationServiceExt, EmployeeLocationQueryService employeeLocationQueryService) {
        super(employeeLocationServiceExt,employeeLocationQueryService);
    	this.employeeLocationServiceExt = employeeLocationServiceExt;
        this.employeeLocationQueryService = employeeLocationQueryService;
    }

    /**
     * {@code POST  /employee-locations} : Create a new employeeLocation.
     *
     * @param employeeLocationDTO the employeeLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeLocationDTO, or with status {@code 400 (Bad Request)} if the employeeLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-locations")
    public ResponseEntity<EmployeeLocationDTO> createEmployeeLocation(@Valid @RequestBody EmployeeLocationDTO employeeLocationDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeLocation : {}", employeeLocationDTO);
        if (employeeLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeLocationDTO result = employeeLocationServiceExt.save(employeeLocationDTO);
        return ResponseEntity.created(new URI("/api/employee-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-locations} : Updates an existing employeeLocation.
     *
     * @param employeeLocationDTO the employeeLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeLocationDTO,
     * or with status {@code 400 (Bad Request)} if the employeeLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-locations")
    public ResponseEntity<EmployeeLocationDTO> updateEmployeeLocation(@Valid @RequestBody EmployeeLocationDTO employeeLocationDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeLocation : {}", employeeLocationDTO);
        if (employeeLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeLocationDTO result = employeeLocationServiceExt.save(employeeLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-locations} : get all the employeeLocations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeLocations in body.
     */
    @GetMapping("/employee-locations")
    public ResponseEntity<List<EmployeeLocationDTO>> getAllEmployeeLocations(EmployeeLocationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmployeeLocations by criteria: {}", criteria);
        Page<EmployeeLocationDTO> page = employeeLocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-locations/count} : count all the employeeLocations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-locations/count")
    public ResponseEntity<Long> countEmployeeLocations(EmployeeLocationCriteria criteria) {
        log.debug("REST request to count EmployeeLocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeLocationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-locations/:id} : get the "id" employeeLocation.
     *
     * @param id the id of the employeeLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-locations/{id}")
    public ResponseEntity<EmployeeLocationDTO> getEmployeeLocation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLocation : {}", id);
        Optional<EmployeeLocationDTO> employeeLocationDTO = employeeLocationServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeLocationDTO);
    }

    /**
     * {@code DELETE  /employee-locations/:id} : delete the "id" employeeLocation.
     *
     * @param id the id of the employeeLocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-locations/{id}")
    public ResponseEntity<Void> deleteEmployeeLocation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLocation : {}", id);
        employeeLocationServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
