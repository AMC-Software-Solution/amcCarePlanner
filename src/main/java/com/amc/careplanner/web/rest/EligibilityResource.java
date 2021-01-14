package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.EligibilityService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EligibilityDTO;
import com.amc.careplanner.service.dto.EligibilityCriteria;
import com.amc.careplanner.service.EligibilityQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Eligibility}.
 */
@RestController
@RequestMapping("/api")
public class EligibilityResource {

    private final Logger log = LoggerFactory.getLogger(EligibilityResource.class);

    private static final String ENTITY_NAME = "eligibility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EligibilityService eligibilityService;

    private final EligibilityQueryService eligibilityQueryService;

    public EligibilityResource(EligibilityService eligibilityService, EligibilityQueryService eligibilityQueryService) {
        this.eligibilityService = eligibilityService;
        this.eligibilityQueryService = eligibilityQueryService;
    }

    /**
     * {@code POST  /eligibilities} : Create a new eligibility.
     *
     * @param eligibilityDTO the eligibilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eligibilityDTO, or with status {@code 400 (Bad Request)} if the eligibility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eligibilities")
    public ResponseEntity<EligibilityDTO> createEligibility(@Valid @RequestBody EligibilityDTO eligibilityDTO) throws URISyntaxException {
        log.debug("REST request to save Eligibility : {}", eligibilityDTO);
        if (eligibilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new eligibility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EligibilityDTO result = eligibilityService.save(eligibilityDTO);
        return ResponseEntity.created(new URI("/api/eligibilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eligibilities} : Updates an existing eligibility.
     *
     * @param eligibilityDTO the eligibilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibilityDTO,
     * or with status {@code 400 (Bad Request)} if the eligibilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eligibilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eligibilities")
    public ResponseEntity<EligibilityDTO> updateEligibility(@Valid @RequestBody EligibilityDTO eligibilityDTO) throws URISyntaxException {
        log.debug("REST request to update Eligibility : {}", eligibilityDTO);
        if (eligibilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EligibilityDTO result = eligibilityService.save(eligibilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eligibilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eligibilities} : get all the eligibilities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eligibilities in body.
     */
    @GetMapping("/eligibilities")
    public ResponseEntity<List<EligibilityDTO>> getAllEligibilities(EligibilityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Eligibilities by criteria: {}", criteria);
        Page<EligibilityDTO> page = eligibilityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eligibilities/count} : count all the eligibilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/eligibilities/count")
    public ResponseEntity<Long> countEligibilities(EligibilityCriteria criteria) {
        log.debug("REST request to count Eligibilities by criteria: {}", criteria);
        return ResponseEntity.ok().body(eligibilityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /eligibilities/:id} : get the "id" eligibility.
     *
     * @param id the id of the eligibilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eligibilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eligibilities/{id}")
    public ResponseEntity<EligibilityDTO> getEligibility(@PathVariable Long id) {
        log.debug("REST request to get Eligibility : {}", id);
        Optional<EligibilityDTO> eligibilityDTO = eligibilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eligibilityDTO);
    }

    /**
     * {@code DELETE  /eligibilities/:id} : delete the "id" eligibility.
     *
     * @param id the id of the eligibilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eligibilities/{id}")
    public ResponseEntity<Void> deleteEligibility(@PathVariable Long id) {
        log.debug("REST request to delete Eligibility : {}", id);
        eligibilityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
