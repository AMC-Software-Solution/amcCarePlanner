package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.CommunicationService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.CommunicationDTO;
import com.amc.careplanner.service.dto.CommunicationCriteria;
import com.amc.careplanner.service.CommunicationQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Communication}.
 */
@RestController
@RequestMapping("/api")
public class CommunicationResource {

    private final Logger log = LoggerFactory.getLogger(CommunicationResource.class);

    private static final String ENTITY_NAME = "communication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommunicationService communicationService;

    private final CommunicationQueryService communicationQueryService;

    public CommunicationResource(CommunicationService communicationService, CommunicationQueryService communicationQueryService) {
        this.communicationService = communicationService;
        this.communicationQueryService = communicationQueryService;
    }

    /**
     * {@code POST  /communications} : Create a new communication.
     *
     * @param communicationDTO the communicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new communicationDTO, or with status {@code 400 (Bad Request)} if the communication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/communications")
    public ResponseEntity<CommunicationDTO> createCommunication(@Valid @RequestBody CommunicationDTO communicationDTO) throws URISyntaxException {
        log.debug("REST request to save Communication : {}", communicationDTO);
        if (communicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new communication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommunicationDTO result = communicationService.save(communicationDTO);
        return ResponseEntity.created(new URI("/api/communications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /communications} : Updates an existing communication.
     *
     * @param communicationDTO the communicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communicationDTO,
     * or with status {@code 400 (Bad Request)} if the communicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the communicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/communications")
    public ResponseEntity<CommunicationDTO> updateCommunication(@Valid @RequestBody CommunicationDTO communicationDTO) throws URISyntaxException {
        log.debug("REST request to update Communication : {}", communicationDTO);
        if (communicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommunicationDTO result = communicationService.save(communicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /communications} : get all the communications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of communications in body.
     */
    @GetMapping("/communications")
    public ResponseEntity<List<CommunicationDTO>> getAllCommunications(CommunicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Communications by criteria: {}", criteria);
        Page<CommunicationDTO> page = communicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /communications/count} : count all the communications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/communications/count")
    public ResponseEntity<Long> countCommunications(CommunicationCriteria criteria) {
        log.debug("REST request to count Communications by criteria: {}", criteria);
        return ResponseEntity.ok().body(communicationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /communications/:id} : get the "id" communication.
     *
     * @param id the id of the communicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the communicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/communications/{id}")
    public ResponseEntity<CommunicationDTO> getCommunication(@PathVariable Long id) {
        log.debug("REST request to get Communication : {}", id);
        Optional<CommunicationDTO> communicationDTO = communicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(communicationDTO);
    }

    /**
     * {@code DELETE  /communications/:id} : delete the "id" communication.
     *
     * @param id the id of the communicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/communications/{id}")
    public ResponseEntity<Void> deleteCommunication(@PathVariable Long id) {
        log.debug("REST request to delete Communication : {}", id);
        communicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
