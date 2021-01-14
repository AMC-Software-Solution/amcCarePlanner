package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.TerminalDeviceService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.TerminalDeviceDTO;
import com.amc.careplanner.service.dto.TerminalDeviceCriteria;
import com.amc.careplanner.service.TerminalDeviceQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.TerminalDevice}.
 */
@RestController
@RequestMapping("/api")
public class TerminalDeviceResource {

    private final Logger log = LoggerFactory.getLogger(TerminalDeviceResource.class);

    private static final String ENTITY_NAME = "terminalDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerminalDeviceService terminalDeviceService;

    private final TerminalDeviceQueryService terminalDeviceQueryService;

    public TerminalDeviceResource(TerminalDeviceService terminalDeviceService, TerminalDeviceQueryService terminalDeviceQueryService) {
        this.terminalDeviceService = terminalDeviceService;
        this.terminalDeviceQueryService = terminalDeviceQueryService;
    }

    /**
     * {@code POST  /terminal-devices} : Create a new terminalDevice.
     *
     * @param terminalDeviceDTO the terminalDeviceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terminalDeviceDTO, or with status {@code 400 (Bad Request)} if the terminalDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/terminal-devices")
    public ResponseEntity<TerminalDeviceDTO> createTerminalDevice(@Valid @RequestBody TerminalDeviceDTO terminalDeviceDTO) throws URISyntaxException {
        log.debug("REST request to save TerminalDevice : {}", terminalDeviceDTO);
        if (terminalDeviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new terminalDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TerminalDeviceDTO result = terminalDeviceService.save(terminalDeviceDTO);
        return ResponseEntity.created(new URI("/api/terminal-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terminal-devices} : Updates an existing terminalDevice.
     *
     * @param terminalDeviceDTO the terminalDeviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terminalDeviceDTO,
     * or with status {@code 400 (Bad Request)} if the terminalDeviceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terminalDeviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/terminal-devices")
    public ResponseEntity<TerminalDeviceDTO> updateTerminalDevice(@Valid @RequestBody TerminalDeviceDTO terminalDeviceDTO) throws URISyntaxException {
        log.debug("REST request to update TerminalDevice : {}", terminalDeviceDTO);
        if (terminalDeviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TerminalDeviceDTO result = terminalDeviceService.save(terminalDeviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terminalDeviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /terminal-devices} : get all the terminalDevices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terminalDevices in body.
     */
    @GetMapping("/terminal-devices")
    public ResponseEntity<List<TerminalDeviceDTO>> getAllTerminalDevices(TerminalDeviceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TerminalDevices by criteria: {}", criteria);
        Page<TerminalDeviceDTO> page = terminalDeviceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /terminal-devices/count} : count all the terminalDevices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/terminal-devices/count")
    public ResponseEntity<Long> countTerminalDevices(TerminalDeviceCriteria criteria) {
        log.debug("REST request to count TerminalDevices by criteria: {}", criteria);
        return ResponseEntity.ok().body(terminalDeviceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /terminal-devices/:id} : get the "id" terminalDevice.
     *
     * @param id the id of the terminalDeviceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terminalDeviceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/terminal-devices/{id}")
    public ResponseEntity<TerminalDeviceDTO> getTerminalDevice(@PathVariable Long id) {
        log.debug("REST request to get TerminalDevice : {}", id);
        Optional<TerminalDeviceDTO> terminalDeviceDTO = terminalDeviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(terminalDeviceDTO);
    }

    /**
     * {@code DELETE  /terminal-devices/:id} : delete the "id" terminalDevice.
     *
     * @param id the id of the terminalDeviceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/terminal-devices/{id}")
    public ResponseEntity<Void> deleteTerminalDevice(@PathVariable Long id) {
        log.debug("REST request to delete TerminalDevice : {}", id);
        terminalDeviceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
