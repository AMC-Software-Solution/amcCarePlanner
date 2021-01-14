package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.RelationshipTypeService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.RelationshipTypeDTO;
import com.amc.careplanner.service.dto.RelationshipTypeCriteria;
import com.amc.careplanner.service.RelationshipTypeQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.RelationshipType}.
 */
@RestController
@RequestMapping("/api")
public class RelationshipTypeResource {

    private final Logger log = LoggerFactory.getLogger(RelationshipTypeResource.class);

    private static final String ENTITY_NAME = "relationshipType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelationshipTypeService relationshipTypeService;

    private final RelationshipTypeQueryService relationshipTypeQueryService;

    public RelationshipTypeResource(RelationshipTypeService relationshipTypeService, RelationshipTypeQueryService relationshipTypeQueryService) {
        this.relationshipTypeService = relationshipTypeService;
        this.relationshipTypeQueryService = relationshipTypeQueryService;
    }

    /**
     * {@code POST  /relationship-types} : Create a new relationshipType.
     *
     * @param relationshipTypeDTO the relationshipTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relationshipTypeDTO, or with status {@code 400 (Bad Request)} if the relationshipType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relationship-types")
    public ResponseEntity<RelationshipTypeDTO> createRelationshipType(@Valid @RequestBody RelationshipTypeDTO relationshipTypeDTO) throws URISyntaxException {
        log.debug("REST request to save RelationshipType : {}", relationshipTypeDTO);
        if (relationshipTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new relationshipType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RelationshipTypeDTO result = relationshipTypeService.save(relationshipTypeDTO);
        return ResponseEntity.created(new URI("/api/relationship-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relationship-types} : Updates an existing relationshipType.
     *
     * @param relationshipTypeDTO the relationshipTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relationshipTypeDTO,
     * or with status {@code 400 (Bad Request)} if the relationshipTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relationshipTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relationship-types")
    public ResponseEntity<RelationshipTypeDTO> updateRelationshipType(@Valid @RequestBody RelationshipTypeDTO relationshipTypeDTO) throws URISyntaxException {
        log.debug("REST request to update RelationshipType : {}", relationshipTypeDTO);
        if (relationshipTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RelationshipTypeDTO result = relationshipTypeService.save(relationshipTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relationshipTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /relationship-types} : get all the relationshipTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relationshipTypes in body.
     */
    @GetMapping("/relationship-types")
    public ResponseEntity<List<RelationshipTypeDTO>> getAllRelationshipTypes(RelationshipTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RelationshipTypes by criteria: {}", criteria);
        Page<RelationshipTypeDTO> page = relationshipTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /relationship-types/count} : count all the relationshipTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/relationship-types/count")
    public ResponseEntity<Long> countRelationshipTypes(RelationshipTypeCriteria criteria) {
        log.debug("REST request to count RelationshipTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(relationshipTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /relationship-types/:id} : get the "id" relationshipType.
     *
     * @param id the id of the relationshipTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relationshipTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relationship-types/{id}")
    public ResponseEntity<RelationshipTypeDTO> getRelationshipType(@PathVariable Long id) {
        log.debug("REST request to get RelationshipType : {}", id);
        Optional<RelationshipTypeDTO> relationshipTypeDTO = relationshipTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relationshipTypeDTO);
    }

    /**
     * {@code DELETE  /relationship-types/:id} : delete the "id" relationshipType.
     *
     * @param id the id of the relationshipTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relationship-types/{id}")
    public ResponseEntity<Void> deleteRelationshipType(@PathVariable Long id) {
        log.debug("REST request to delete RelationshipType : {}", id);
        relationshipTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
