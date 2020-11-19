package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.PowerOfAttorneyService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.PowerOfAttorneyDTO;
import com.amc.careplanner.service.dto.PowerOfAttorneyCriteria;
import com.amc.careplanner.service.PowerOfAttorneyQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.PowerOfAttorney}.
 */
@RestController
@RequestMapping("/api")
public class PowerOfAttorneyResource {

    private final Logger log = LoggerFactory.getLogger(PowerOfAttorneyResource.class);

    private static final String ENTITY_NAME = "powerOfAttorney";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PowerOfAttorneyService powerOfAttorneyService;

    private final PowerOfAttorneyQueryService powerOfAttorneyQueryService;

    public PowerOfAttorneyResource(PowerOfAttorneyService powerOfAttorneyService, PowerOfAttorneyQueryService powerOfAttorneyQueryService) {
        this.powerOfAttorneyService = powerOfAttorneyService;
        this.powerOfAttorneyQueryService = powerOfAttorneyQueryService;
    }

    /**
     * {@code POST  /power-of-attorneys} : Create a new powerOfAttorney.
     *
     * @param powerOfAttorneyDTO the powerOfAttorneyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new powerOfAttorneyDTO, or with status {@code 400 (Bad Request)} if the powerOfAttorney has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/power-of-attorneys")
    public ResponseEntity<PowerOfAttorneyDTO> createPowerOfAttorney(@Valid @RequestBody PowerOfAttorneyDTO powerOfAttorneyDTO) throws URISyntaxException {
        log.debug("REST request to save PowerOfAttorney : {}", powerOfAttorneyDTO);
        if (powerOfAttorneyDTO.getId() != null) {
            throw new BadRequestAlertException("A new powerOfAttorney cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PowerOfAttorneyDTO result = powerOfAttorneyService.save(powerOfAttorneyDTO);
        return ResponseEntity.created(new URI("/api/power-of-attorneys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /power-of-attorneys} : Updates an existing powerOfAttorney.
     *
     * @param powerOfAttorneyDTO the powerOfAttorneyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated powerOfAttorneyDTO,
     * or with status {@code 400 (Bad Request)} if the powerOfAttorneyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the powerOfAttorneyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/power-of-attorneys")
    public ResponseEntity<PowerOfAttorneyDTO> updatePowerOfAttorney(@Valid @RequestBody PowerOfAttorneyDTO powerOfAttorneyDTO) throws URISyntaxException {
        log.debug("REST request to update PowerOfAttorney : {}", powerOfAttorneyDTO);
        if (powerOfAttorneyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PowerOfAttorneyDTO result = powerOfAttorneyService.save(powerOfAttorneyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, powerOfAttorneyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /power-of-attorneys} : get all the powerOfAttorneys.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of powerOfAttorneys in body.
     */
    @GetMapping("/power-of-attorneys")
    public ResponseEntity<List<PowerOfAttorneyDTO>> getAllPowerOfAttorneys(PowerOfAttorneyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PowerOfAttorneys by criteria: {}", criteria);
        Page<PowerOfAttorneyDTO> page = powerOfAttorneyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /power-of-attorneys/count} : count all the powerOfAttorneys.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/power-of-attorneys/count")
    public ResponseEntity<Long> countPowerOfAttorneys(PowerOfAttorneyCriteria criteria) {
        log.debug("REST request to count PowerOfAttorneys by criteria: {}", criteria);
        return ResponseEntity.ok().body(powerOfAttorneyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /power-of-attorneys/:id} : get the "id" powerOfAttorney.
     *
     * @param id the id of the powerOfAttorneyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the powerOfAttorneyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/power-of-attorneys/{id}")
    public ResponseEntity<PowerOfAttorneyDTO> getPowerOfAttorney(@PathVariable Long id) {
        log.debug("REST request to get PowerOfAttorney : {}", id);
        Optional<PowerOfAttorneyDTO> powerOfAttorneyDTO = powerOfAttorneyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(powerOfAttorneyDTO);
    }

    /**
     * {@code DELETE  /power-of-attorneys/:id} : delete the "id" powerOfAttorney.
     *
     * @param id the id of the powerOfAttorneyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/power-of-attorneys/{id}")
    public ResponseEntity<Void> deletePowerOfAttorney(@PathVariable Long id) {
        log.debug("REST request to delete PowerOfAttorney : {}", id);
        powerOfAttorneyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
