package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.DisabilityService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.DisabilityDTO;
import com.amc.careplanner.service.dto.DisabilityCriteria;
import com.amc.careplanner.service.DisabilityQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Disability}.
 */
@RestController
@RequestMapping("/api")
public class DisabilityResource {

    private final Logger log = LoggerFactory.getLogger(DisabilityResource.class);

    private static final String ENTITY_NAME = "disability";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisabilityService disabilityService;

    private final DisabilityQueryService disabilityQueryService;

    public DisabilityResource(DisabilityService disabilityService, DisabilityQueryService disabilityQueryService) {
        this.disabilityService = disabilityService;
        this.disabilityQueryService = disabilityQueryService;
    }

    /**
     * {@code POST  /disabilities} : Create a new disability.
     *
     * @param disabilityDTO the disabilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disabilityDTO, or with status {@code 400 (Bad Request)} if the disability has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disabilities")
    public ResponseEntity<DisabilityDTO> createDisability(@Valid @RequestBody DisabilityDTO disabilityDTO) throws URISyntaxException {
        log.debug("REST request to save Disability : {}", disabilityDTO);
        if (disabilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new disability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisabilityDTO result = disabilityService.save(disabilityDTO);
        return ResponseEntity.created(new URI("/api/disabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disabilities} : Updates an existing disability.
     *
     * @param disabilityDTO the disabilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disabilityDTO,
     * or with status {@code 400 (Bad Request)} if the disabilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disabilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disabilities")
    public ResponseEntity<DisabilityDTO> updateDisability(@Valid @RequestBody DisabilityDTO disabilityDTO) throws URISyntaxException {
        log.debug("REST request to update Disability : {}", disabilityDTO);
        if (disabilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisabilityDTO result = disabilityService.save(disabilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disabilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /disabilities} : get all the disabilities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disabilities in body.
     */
    @GetMapping("/disabilities")
    public ResponseEntity<List<DisabilityDTO>> getAllDisabilities(DisabilityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Disabilities by criteria: {}", criteria);
        Page<DisabilityDTO> page = disabilityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disabilities/count} : count all the disabilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/disabilities/count")
    public ResponseEntity<Long> countDisabilities(DisabilityCriteria criteria) {
        log.debug("REST request to count Disabilities by criteria: {}", criteria);
        return ResponseEntity.ok().body(disabilityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /disabilities/:id} : get the "id" disability.
     *
     * @param id the id of the disabilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disabilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disabilities/{id}")
    public ResponseEntity<DisabilityDTO> getDisability(@PathVariable Long id) {
        log.debug("REST request to get Disability : {}", id);
        Optional<DisabilityDTO> disabilityDTO = disabilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disabilityDTO);
    }

    /**
     * {@code DELETE  /disabilities/:id} : delete the "id" disability.
     *
     * @param id the id of the disabilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disabilities/{id}")
    public ResponseEntity<Void> deleteDisability(@PathVariable Long id) {
        log.debug("REST request to delete Disability : {}", id);
        disabilityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
