package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.SystemEventsHistoryService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.SystemEventsHistoryDTO;
import com.amc.careplanner.service.dto.SystemEventsHistoryCriteria;
import com.amc.careplanner.service.SystemEventsHistoryQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.SystemEventsHistory}.
 */
@RestController
@RequestMapping("/api")
public class SystemEventsHistoryResource {

    private final Logger log = LoggerFactory.getLogger(SystemEventsHistoryResource.class);

    private static final String ENTITY_NAME = "systemEventsHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemEventsHistoryService systemEventsHistoryService;

    private final SystemEventsHistoryQueryService systemEventsHistoryQueryService;

    public SystemEventsHistoryResource(SystemEventsHistoryService systemEventsHistoryService, SystemEventsHistoryQueryService systemEventsHistoryQueryService) {
        this.systemEventsHistoryService = systemEventsHistoryService;
        this.systemEventsHistoryQueryService = systemEventsHistoryQueryService;
    }

    /**
     * {@code POST  /system-events-histories} : Create a new systemEventsHistory.
     *
     * @param systemEventsHistoryDTO the systemEventsHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemEventsHistoryDTO, or with status {@code 400 (Bad Request)} if the systemEventsHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/system-events-histories")
    public ResponseEntity<SystemEventsHistoryDTO> createSystemEventsHistory(@Valid @RequestBody SystemEventsHistoryDTO systemEventsHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save SystemEventsHistory : {}", systemEventsHistoryDTO);
        if (systemEventsHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemEventsHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemEventsHistoryDTO result = systemEventsHistoryService.save(systemEventsHistoryDTO);
        return ResponseEntity.created(new URI("/api/system-events-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-events-histories} : Updates an existing systemEventsHistory.
     *
     * @param systemEventsHistoryDTO the systemEventsHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemEventsHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the systemEventsHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemEventsHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/system-events-histories")
    public ResponseEntity<SystemEventsHistoryDTO> updateSystemEventsHistory(@Valid @RequestBody SystemEventsHistoryDTO systemEventsHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update SystemEventsHistory : {}", systemEventsHistoryDTO);
        if (systemEventsHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemEventsHistoryDTO result = systemEventsHistoryService.save(systemEventsHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemEventsHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /system-events-histories} : get all the systemEventsHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemEventsHistories in body.
     */
    @GetMapping("/system-events-histories")
    public ResponseEntity<List<SystemEventsHistoryDTO>> getAllSystemEventsHistories(SystemEventsHistoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SystemEventsHistories by criteria: {}", criteria);
        Page<SystemEventsHistoryDTO> page = systemEventsHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-events-histories/count} : count all the systemEventsHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/system-events-histories/count")
    public ResponseEntity<Long> countSystemEventsHistories(SystemEventsHistoryCriteria criteria) {
        log.debug("REST request to count SystemEventsHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemEventsHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /system-events-histories/:id} : get the "id" systemEventsHistory.
     *
     * @param id the id of the systemEventsHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemEventsHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-events-histories/{id}")
    public ResponseEntity<SystemEventsHistoryDTO> getSystemEventsHistory(@PathVariable Long id) {
        log.debug("REST request to get SystemEventsHistory : {}", id);
        Optional<SystemEventsHistoryDTO> systemEventsHistoryDTO = systemEventsHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemEventsHistoryDTO);
    }

    /**
     * {@code DELETE  /system-events-histories/:id} : delete the "id" systemEventsHistory.
     *
     * @param id the id of the systemEventsHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/system-events-histories/{id}")
    public ResponseEntity<Void> deleteSystemEventsHistory(@PathVariable Long id) {
        log.debug("REST request to delete SystemEventsHistory : {}", id);
        systemEventsHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
