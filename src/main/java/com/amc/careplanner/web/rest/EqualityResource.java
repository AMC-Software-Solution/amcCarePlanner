package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.EqualityService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EqualityDTO;
import com.amc.careplanner.service.dto.EqualityCriteria;
import com.amc.careplanner.service.EqualityQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Equality}.
 */
@RestController
@RequestMapping("/api")
public class EqualityResource {

    private final Logger log = LoggerFactory.getLogger(EqualityResource.class);

    private static final String ENTITY_NAME = "equality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EqualityService equalityService;

    private final EqualityQueryService equalityQueryService;

    public EqualityResource(EqualityService equalityService, EqualityQueryService equalityQueryService) {
        this.equalityService = equalityService;
        this.equalityQueryService = equalityQueryService;
    }

    /**
     * {@code POST  /equalities} : Create a new equality.
     *
     * @param equalityDTO the equalityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equalityDTO, or with status {@code 400 (Bad Request)} if the equality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equalities")
    public ResponseEntity<EqualityDTO> createEquality(@Valid @RequestBody EqualityDTO equalityDTO) throws URISyntaxException {
        log.debug("REST request to save Equality : {}", equalityDTO);
        if (equalityDTO.getId() != null) {
            throw new BadRequestAlertException("A new equality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EqualityDTO result = equalityService.save(equalityDTO);
        return ResponseEntity.created(new URI("/api/equalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equalities} : Updates an existing equality.
     *
     * @param equalityDTO the equalityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equalityDTO,
     * or with status {@code 400 (Bad Request)} if the equalityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equalityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equalities")
    public ResponseEntity<EqualityDTO> updateEquality(@Valid @RequestBody EqualityDTO equalityDTO) throws URISyntaxException {
        log.debug("REST request to update Equality : {}", equalityDTO);
        if (equalityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EqualityDTO result = equalityService.save(equalityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equalityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equalities} : get all the equalities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equalities in body.
     */
    @GetMapping("/equalities")
    public ResponseEntity<List<EqualityDTO>> getAllEqualities(EqualityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Equalities by criteria: {}", criteria);
        Page<EqualityDTO> page = equalityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equalities/count} : count all the equalities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/equalities/count")
    public ResponseEntity<Long> countEqualities(EqualityCriteria criteria) {
        log.debug("REST request to count Equalities by criteria: {}", criteria);
        return ResponseEntity.ok().body(equalityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /equalities/:id} : get the "id" equality.
     *
     * @param id the id of the equalityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equalityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equalities/{id}")
    public ResponseEntity<EqualityDTO> getEquality(@PathVariable Long id) {
        log.debug("REST request to get Equality : {}", id);
        Optional<EqualityDTO> equalityDTO = equalityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equalityDTO);
    }

    /**
     * {@code DELETE  /equalities/:id} : delete the "id" equality.
     *
     * @param id the id of the equalityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equalities/{id}")
    public ResponseEntity<Void> deleteEquality(@PathVariable Long id) {
        log.debug("REST request to delete Equality : {}", id);
        equalityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
