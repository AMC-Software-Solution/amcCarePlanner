package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.TravelService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.TravelDTO;
import com.amc.careplanner.service.dto.TravelCriteria;
import com.amc.careplanner.service.TravelQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Travel}.
 */
@RestController
@RequestMapping("/api")
public class TravelResource {

    private final Logger log = LoggerFactory.getLogger(TravelResource.class);

    private static final String ENTITY_NAME = "travel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TravelService travelService;

    private final TravelQueryService travelQueryService;

    public TravelResource(TravelService travelService, TravelQueryService travelQueryService) {
        this.travelService = travelService;
        this.travelQueryService = travelQueryService;
    }

    /**
     * {@code POST  /travels} : Create a new travel.
     *
     * @param travelDTO the travelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new travelDTO, or with status {@code 400 (Bad Request)} if the travel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/travels")
    public ResponseEntity<TravelDTO> createTravel(@Valid @RequestBody TravelDTO travelDTO) throws URISyntaxException {
        log.debug("REST request to save Travel : {}", travelDTO);
        if (travelDTO.getId() != null) {
            throw new BadRequestAlertException("A new travel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TravelDTO result = travelService.save(travelDTO);
        return ResponseEntity.created(new URI("/api/travels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /travels} : Updates an existing travel.
     *
     * @param travelDTO the travelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travelDTO,
     * or with status {@code 400 (Bad Request)} if the travelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the travelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/travels")
    public ResponseEntity<TravelDTO> updateTravel(@Valid @RequestBody TravelDTO travelDTO) throws URISyntaxException {
        log.debug("REST request to update Travel : {}", travelDTO);
        if (travelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TravelDTO result = travelService.save(travelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, travelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /travels} : get all the travels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of travels in body.
     */
    @GetMapping("/travels")
    public ResponseEntity<List<TravelDTO>> getAllTravels(TravelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Travels by criteria: {}", criteria);
        Page<TravelDTO> page = travelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /travels/count} : count all the travels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/travels/count")
    public ResponseEntity<Long> countTravels(TravelCriteria criteria) {
        log.debug("REST request to count Travels by criteria: {}", criteria);
        return ResponseEntity.ok().body(travelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /travels/:id} : get the "id" travel.
     *
     * @param id the id of the travelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the travelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/travels/{id}")
    public ResponseEntity<TravelDTO> getTravel(@PathVariable Long id) {
        log.debug("REST request to get Travel : {}", id);
        Optional<TravelDTO> travelDTO = travelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(travelDTO);
    }

    /**
     * {@code DELETE  /travels/:id} : delete the "id" travel.
     *
     * @param id the id of the travelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/travels/{id}")
    public ResponseEntity<Void> deleteTravel(@PathVariable Long id) {
        log.debug("REST request to delete Travel : {}", id);
        travelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
