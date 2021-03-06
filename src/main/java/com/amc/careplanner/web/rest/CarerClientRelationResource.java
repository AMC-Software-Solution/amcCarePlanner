package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.CarerClientRelationService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;
import com.amc.careplanner.service.dto.CarerClientRelationCriteria;
import com.amc.careplanner.service.CarerClientRelationQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.CarerClientRelation}.
 */
@RestController
@RequestMapping("/api")
public class CarerClientRelationResource {

    private final Logger log = LoggerFactory.getLogger(CarerClientRelationResource.class);

    private static final String ENTITY_NAME = "carerClientRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarerClientRelationService carerClientRelationService;

    private final CarerClientRelationQueryService carerClientRelationQueryService;

    public CarerClientRelationResource(CarerClientRelationService carerClientRelationService, CarerClientRelationQueryService carerClientRelationQueryService) {
        this.carerClientRelationService = carerClientRelationService;
        this.carerClientRelationQueryService = carerClientRelationQueryService;
    }

    /**
     * {@code POST  /carer-client-relations} : Create a new carerClientRelation.
     *
     * @param carerClientRelationDTO the carerClientRelationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carerClientRelationDTO, or with status {@code 400 (Bad Request)} if the carerClientRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carer-client-relations")
    public ResponseEntity<CarerClientRelationDTO> createCarerClientRelation(@Valid @RequestBody CarerClientRelationDTO carerClientRelationDTO) throws URISyntaxException {
        log.debug("REST request to save CarerClientRelation : {}", carerClientRelationDTO);
        if (carerClientRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new carerClientRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarerClientRelationDTO result = carerClientRelationService.save(carerClientRelationDTO);
        return ResponseEntity.created(new URI("/api/carer-client-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carer-client-relations} : Updates an existing carerClientRelation.
     *
     * @param carerClientRelationDTO the carerClientRelationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carerClientRelationDTO,
     * or with status {@code 400 (Bad Request)} if the carerClientRelationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carerClientRelationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carer-client-relations")
    public ResponseEntity<CarerClientRelationDTO> updateCarerClientRelation(@Valid @RequestBody CarerClientRelationDTO carerClientRelationDTO) throws URISyntaxException {
        log.debug("REST request to update CarerClientRelation : {}", carerClientRelationDTO);
        if (carerClientRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarerClientRelationDTO result = carerClientRelationService.save(carerClientRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carerClientRelationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carer-client-relations} : get all the carerClientRelations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carerClientRelations in body.
     */
    @GetMapping("/carer-client-relations")
    public ResponseEntity<List<CarerClientRelationDTO>> getAllCarerClientRelations(CarerClientRelationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CarerClientRelations by criteria: {}", criteria);
        Page<CarerClientRelationDTO> page = carerClientRelationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carer-client-relations/count} : count all the carerClientRelations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/carer-client-relations/count")
    public ResponseEntity<Long> countCarerClientRelations(CarerClientRelationCriteria criteria) {
        log.debug("REST request to count CarerClientRelations by criteria: {}", criteria);
        return ResponseEntity.ok().body(carerClientRelationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /carer-client-relations/:id} : get the "id" carerClientRelation.
     *
     * @param id the id of the carerClientRelationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carerClientRelationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carer-client-relations/{id}")
    public ResponseEntity<CarerClientRelationDTO> getCarerClientRelation(@PathVariable Long id) {
        log.debug("REST request to get CarerClientRelation : {}", id);
        Optional<CarerClientRelationDTO> carerClientRelationDTO = carerClientRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carerClientRelationDTO);
    }

    /**
     * {@code DELETE  /carer-client-relations/:id} : delete the "id" carerClientRelation.
     *
     * @param id the id of the carerClientRelationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carer-client-relations/{id}")
    public ResponseEntity<Void> deleteCarerClientRelation(@PathVariable Long id) {
        log.debug("REST request to delete CarerClientRelation : {}", id);
        carerClientRelationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
