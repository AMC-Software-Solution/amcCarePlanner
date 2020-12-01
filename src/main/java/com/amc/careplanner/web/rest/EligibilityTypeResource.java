package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.EligibilityTypeService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EligibilityTypeDTO;
import com.amc.careplanner.service.dto.EligibilityTypeCriteria;
import com.amc.careplanner.service.EligibilityTypeQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.EligibilityType}.
 */
@RestController
@RequestMapping("/api")
public class EligibilityTypeResource {

    private final Logger log = LoggerFactory.getLogger(EligibilityTypeResource.class);

    private static final String ENTITY_NAME = "eligibilityType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EligibilityTypeService eligibilityTypeService;

    private final EligibilityTypeQueryService eligibilityTypeQueryService;

    public EligibilityTypeResource(EligibilityTypeService eligibilityTypeService, EligibilityTypeQueryService eligibilityTypeQueryService) {
        this.eligibilityTypeService = eligibilityTypeService;
        this.eligibilityTypeQueryService = eligibilityTypeQueryService;
    }

    /**
     * {@code POST  /eligibility-types} : Create a new eligibilityType.
     *
     * @param eligibilityTypeDTO the eligibilityTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eligibilityTypeDTO, or with status {@code 400 (Bad Request)} if the eligibilityType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eligibility-types")
    public ResponseEntity<EligibilityTypeDTO> createEligibilityType(@Valid @RequestBody EligibilityTypeDTO eligibilityTypeDTO) throws URISyntaxException {
        log.debug("REST request to save EligibilityType : {}", eligibilityTypeDTO);
        if (eligibilityTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new eligibilityType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EligibilityTypeDTO result = eligibilityTypeService.save(eligibilityTypeDTO);
        return ResponseEntity.created(new URI("/api/eligibility-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eligibility-types} : Updates an existing eligibilityType.
     *
     * @param eligibilityTypeDTO the eligibilityTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibilityTypeDTO,
     * or with status {@code 400 (Bad Request)} if the eligibilityTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eligibilityTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eligibility-types")
    public ResponseEntity<EligibilityTypeDTO> updateEligibilityType(@Valid @RequestBody EligibilityTypeDTO eligibilityTypeDTO) throws URISyntaxException {
        log.debug("REST request to update EligibilityType : {}", eligibilityTypeDTO);
        if (eligibilityTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EligibilityTypeDTO result = eligibilityTypeService.save(eligibilityTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eligibilityTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eligibility-types} : get all the eligibilityTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eligibilityTypes in body.
     */
    @GetMapping("/eligibility-types")
    public ResponseEntity<List<EligibilityTypeDTO>> getAllEligibilityTypes(EligibilityTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EligibilityTypes by criteria: {}", criteria);
        Page<EligibilityTypeDTO> page = eligibilityTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eligibility-types/count} : count all the eligibilityTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/eligibility-types/count")
    public ResponseEntity<Long> countEligibilityTypes(EligibilityTypeCriteria criteria) {
        log.debug("REST request to count EligibilityTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(eligibilityTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /eligibility-types/:id} : get the "id" eligibilityType.
     *
     * @param id the id of the eligibilityTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eligibilityTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eligibility-types/{id}")
    public ResponseEntity<EligibilityTypeDTO> getEligibilityType(@PathVariable Long id) {
        log.debug("REST request to get EligibilityType : {}", id);
        Optional<EligibilityTypeDTO> eligibilityTypeDTO = eligibilityTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eligibilityTypeDTO);
    }

    /**
     * {@code DELETE  /eligibility-types/:id} : delete the "id" eligibilityType.
     *
     * @param id the id of the eligibilityTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eligibility-types/{id}")
    public ResponseEntity<Void> deleteEligibilityType(@PathVariable Long id) {
        log.debug("REST request to delete EligibilityType : {}", id);
        eligibilityTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
