package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.CarerServiceUserRelationService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.CarerServiceUserRelationDTO;
import com.amc.careplanner.service.dto.CarerServiceUserRelationCriteria;
import com.amc.careplanner.service.CarerServiceUserRelationQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.CarerServiceUserRelation}.
 */
@RestController
@RequestMapping("/api")
public class CarerServiceUserRelationResource {

    private final Logger log = LoggerFactory.getLogger(CarerServiceUserRelationResource.class);

    private static final String ENTITY_NAME = "carerServiceUserRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarerServiceUserRelationService carerServiceUserRelationService;

    private final CarerServiceUserRelationQueryService carerServiceUserRelationQueryService;

    public CarerServiceUserRelationResource(CarerServiceUserRelationService carerServiceUserRelationService, CarerServiceUserRelationQueryService carerServiceUserRelationQueryService) {
        this.carerServiceUserRelationService = carerServiceUserRelationService;
        this.carerServiceUserRelationQueryService = carerServiceUserRelationQueryService;
    }

    /**
     * {@code POST  /carer-service-user-relations} : Create a new carerServiceUserRelation.
     *
     * @param carerServiceUserRelationDTO the carerServiceUserRelationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carerServiceUserRelationDTO, or with status {@code 400 (Bad Request)} if the carerServiceUserRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carer-service-user-relations")
    public ResponseEntity<CarerServiceUserRelationDTO> createCarerServiceUserRelation(@Valid @RequestBody CarerServiceUserRelationDTO carerServiceUserRelationDTO) throws URISyntaxException {
        log.debug("REST request to save CarerServiceUserRelation : {}", carerServiceUserRelationDTO);
        if (carerServiceUserRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new carerServiceUserRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarerServiceUserRelationDTO result = carerServiceUserRelationService.save(carerServiceUserRelationDTO);
        return ResponseEntity.created(new URI("/api/carer-service-user-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carer-service-user-relations} : Updates an existing carerServiceUserRelation.
     *
     * @param carerServiceUserRelationDTO the carerServiceUserRelationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carerServiceUserRelationDTO,
     * or with status {@code 400 (Bad Request)} if the carerServiceUserRelationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carerServiceUserRelationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carer-service-user-relations")
    public ResponseEntity<CarerServiceUserRelationDTO> updateCarerServiceUserRelation(@Valid @RequestBody CarerServiceUserRelationDTO carerServiceUserRelationDTO) throws URISyntaxException {
        log.debug("REST request to update CarerServiceUserRelation : {}", carerServiceUserRelationDTO);
        if (carerServiceUserRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarerServiceUserRelationDTO result = carerServiceUserRelationService.save(carerServiceUserRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carerServiceUserRelationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carer-service-user-relations} : get all the carerServiceUserRelations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carerServiceUserRelations in body.
     */
    @GetMapping("/carer-service-user-relations")
    public ResponseEntity<List<CarerServiceUserRelationDTO>> getAllCarerServiceUserRelations(CarerServiceUserRelationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CarerServiceUserRelations by criteria: {}", criteria);
        Page<CarerServiceUserRelationDTO> page = carerServiceUserRelationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carer-service-user-relations/count} : count all the carerServiceUserRelations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/carer-service-user-relations/count")
    public ResponseEntity<Long> countCarerServiceUserRelations(CarerServiceUserRelationCriteria criteria) {
        log.debug("REST request to count CarerServiceUserRelations by criteria: {}", criteria);
        return ResponseEntity.ok().body(carerServiceUserRelationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /carer-service-user-relations/:id} : get the "id" carerServiceUserRelation.
     *
     * @param id the id of the carerServiceUserRelationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carerServiceUserRelationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carer-service-user-relations/{id}")
    public ResponseEntity<CarerServiceUserRelationDTO> getCarerServiceUserRelation(@PathVariable Long id) {
        log.debug("REST request to get CarerServiceUserRelation : {}", id);
        Optional<CarerServiceUserRelationDTO> carerServiceUserRelationDTO = carerServiceUserRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carerServiceUserRelationDTO);
    }

    /**
     * {@code DELETE  /carer-service-user-relations/:id} : delete the "id" carerServiceUserRelation.
     *
     * @param id the id of the carerServiceUserRelationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carer-service-user-relations/{id}")
    public ResponseEntity<Void> deleteCarerServiceUserRelation(@PathVariable Long id) {
        log.debug("REST request to delete CarerServiceUserRelation : {}", id);
        carerServiceUserRelationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
