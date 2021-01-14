package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.ExtraDataService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ExtraDataDTO;
import com.amc.careplanner.service.dto.ExtraDataCriteria;
import com.amc.careplanner.service.ExtraDataQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.ExtraData}.
 */
@RestController
@RequestMapping("/api")
public class ExtraDataResource {

    private final Logger log = LoggerFactory.getLogger(ExtraDataResource.class);

    private static final String ENTITY_NAME = "extraData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExtraDataService extraDataService;

    private final ExtraDataQueryService extraDataQueryService;

    public ExtraDataResource(ExtraDataService extraDataService, ExtraDataQueryService extraDataQueryService) {
        this.extraDataService = extraDataService;
        this.extraDataQueryService = extraDataQueryService;
    }

    /**
     * {@code POST  /extra-data} : Create a new extraData.
     *
     * @param extraDataDTO the extraDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new extraDataDTO, or with status {@code 400 (Bad Request)} if the extraData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/extra-data")
    public ResponseEntity<ExtraDataDTO> createExtraData(@Valid @RequestBody ExtraDataDTO extraDataDTO) throws URISyntaxException {
        log.debug("REST request to save ExtraData : {}", extraDataDTO);
        if (extraDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new extraData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExtraDataDTO result = extraDataService.save(extraDataDTO);
        return ResponseEntity.created(new URI("/api/extra-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /extra-data} : Updates an existing extraData.
     *
     * @param extraDataDTO the extraDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extraDataDTO,
     * or with status {@code 400 (Bad Request)} if the extraDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the extraDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/extra-data")
    public ResponseEntity<ExtraDataDTO> updateExtraData(@Valid @RequestBody ExtraDataDTO extraDataDTO) throws URISyntaxException {
        log.debug("REST request to update ExtraData : {}", extraDataDTO);
        if (extraDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExtraDataDTO result = extraDataService.save(extraDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, extraDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /extra-data} : get all the extraData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of extraData in body.
     */
    @GetMapping("/extra-data")
    public ResponseEntity<List<ExtraDataDTO>> getAllExtraData(ExtraDataCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ExtraData by criteria: {}", criteria);
        Page<ExtraDataDTO> page = extraDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /extra-data/count} : count all the extraData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/extra-data/count")
    public ResponseEntity<Long> countExtraData(ExtraDataCriteria criteria) {
        log.debug("REST request to count ExtraData by criteria: {}", criteria);
        return ResponseEntity.ok().body(extraDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /extra-data/:id} : get the "id" extraData.
     *
     * @param id the id of the extraDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the extraDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/extra-data/{id}")
    public ResponseEntity<ExtraDataDTO> getExtraData(@PathVariable Long id) {
        log.debug("REST request to get ExtraData : {}", id);
        Optional<ExtraDataDTO> extraDataDTO = extraDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extraDataDTO);
    }

    /**
     * {@code DELETE  /extra-data/:id} : delete the "id" extraData.
     *
     * @param id the id of the extraDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/extra-data/{id}")
    public ResponseEntity<Void> deleteExtraData(@PathVariable Long id) {
        log.debug("REST request to delete ExtraData : {}", id);
        extraDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
