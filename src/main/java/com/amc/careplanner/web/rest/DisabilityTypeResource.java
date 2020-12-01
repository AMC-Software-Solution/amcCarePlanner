package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.DisabilityTypeService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.DisabilityTypeDTO;
import com.amc.careplanner.service.dto.DisabilityTypeCriteria;
import com.amc.careplanner.service.DisabilityTypeQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.DisabilityType}.
 */
@RestController
@RequestMapping("/api")
public class DisabilityTypeResource {

    private final Logger log = LoggerFactory.getLogger(DisabilityTypeResource.class);

    private static final String ENTITY_NAME = "disabilityType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisabilityTypeService disabilityTypeService;

    private final DisabilityTypeQueryService disabilityTypeQueryService;

    public DisabilityTypeResource(DisabilityTypeService disabilityTypeService, DisabilityTypeQueryService disabilityTypeQueryService) {
        this.disabilityTypeService = disabilityTypeService;
        this.disabilityTypeQueryService = disabilityTypeQueryService;
    }

    /**
     * {@code POST  /disability-types} : Create a new disabilityType.
     *
     * @param disabilityTypeDTO the disabilityTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disabilityTypeDTO, or with status {@code 400 (Bad Request)} if the disabilityType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disability-types")
    public ResponseEntity<DisabilityTypeDTO> createDisabilityType(@Valid @RequestBody DisabilityTypeDTO disabilityTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DisabilityType : {}", disabilityTypeDTO);
        if (disabilityTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new disabilityType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisabilityTypeDTO result = disabilityTypeService.save(disabilityTypeDTO);
        return ResponseEntity.created(new URI("/api/disability-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disability-types} : Updates an existing disabilityType.
     *
     * @param disabilityTypeDTO the disabilityTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disabilityTypeDTO,
     * or with status {@code 400 (Bad Request)} if the disabilityTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disabilityTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disability-types")
    public ResponseEntity<DisabilityTypeDTO> updateDisabilityType(@Valid @RequestBody DisabilityTypeDTO disabilityTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DisabilityType : {}", disabilityTypeDTO);
        if (disabilityTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisabilityTypeDTO result = disabilityTypeService.save(disabilityTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disabilityTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /disability-types} : get all the disabilityTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disabilityTypes in body.
     */
    @GetMapping("/disability-types")
    public ResponseEntity<List<DisabilityTypeDTO>> getAllDisabilityTypes(DisabilityTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DisabilityTypes by criteria: {}", criteria);
        Page<DisabilityTypeDTO> page = disabilityTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disability-types/count} : count all the disabilityTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/disability-types/count")
    public ResponseEntity<Long> countDisabilityTypes(DisabilityTypeCriteria criteria) {
        log.debug("REST request to count DisabilityTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(disabilityTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /disability-types/:id} : get the "id" disabilityType.
     *
     * @param id the id of the disabilityTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disabilityTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disability-types/{id}")
    public ResponseEntity<DisabilityTypeDTO> getDisabilityType(@PathVariable Long id) {
        log.debug("REST request to get DisabilityType : {}", id);
        Optional<DisabilityTypeDTO> disabilityTypeDTO = disabilityTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disabilityTypeDTO);
    }

    /**
     * {@code DELETE  /disability-types/:id} : delete the "id" disabilityType.
     *
     * @param id the id of the disabilityTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disability-types/{id}")
    public ResponseEntity<Void> deleteDisabilityType(@PathVariable Long id) {
        log.debug("REST request to delete DisabilityType : {}", id);
        disabilityTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
