package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.AccessService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.AccessDTO;
import com.amc.careplanner.service.dto.AccessCriteria;
import com.amc.careplanner.service.AccessQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Access}.
 */
@RestController
@RequestMapping("/api")
public class AccessResource {

    private final Logger log = LoggerFactory.getLogger(AccessResource.class);

    private static final String ENTITY_NAME = "access";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccessService accessService;

    private final AccessQueryService accessQueryService;

    public AccessResource(AccessService accessService, AccessQueryService accessQueryService) {
        this.accessService = accessService;
        this.accessQueryService = accessQueryService;
    }

    /**
     * {@code POST  /accesses} : Create a new access.
     *
     * @param accessDTO the accessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accessDTO, or with status {@code 400 (Bad Request)} if the access has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accesses")
    public ResponseEntity<AccessDTO> createAccess(@Valid @RequestBody AccessDTO accessDTO) throws URISyntaxException {
        log.debug("REST request to save Access : {}", accessDTO);
        if (accessDTO.getId() != null) {
            throw new BadRequestAlertException("A new access cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccessDTO result = accessService.save(accessDTO);
        return ResponseEntity.created(new URI("/api/accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accesses} : Updates an existing access.
     *
     * @param accessDTO the accessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accessDTO,
     * or with status {@code 400 (Bad Request)} if the accessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accesses")
    public ResponseEntity<AccessDTO> updateAccess(@Valid @RequestBody AccessDTO accessDTO) throws URISyntaxException {
        log.debug("REST request to update Access : {}", accessDTO);
        if (accessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccessDTO result = accessService.save(accessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /accesses} : get all the accesses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accesses in body.
     */
    @GetMapping("/accesses")
    public ResponseEntity<List<AccessDTO>> getAllAccesses(AccessCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Accesses by criteria: {}", criteria);
        Page<AccessDTO> page = accessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accesses/count} : count all the accesses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/accesses/count")
    public ResponseEntity<Long> countAccesses(AccessCriteria criteria) {
        log.debug("REST request to count Accesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(accessQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /accesses/:id} : get the "id" access.
     *
     * @param id the id of the accessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accesses/{id}")
    public ResponseEntity<AccessDTO> getAccess(@PathVariable Long id) {
        log.debug("REST request to get Access : {}", id);
        Optional<AccessDTO> accessDTO = accessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accessDTO);
    }

    /**
     * {@code DELETE  /accesses/:id} : delete the "id" access.
     *
     * @param id the id of the accessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accesses/{id}")
    public ResponseEntity<Void> deleteAccess(@PathVariable Long id) {
        log.debug("REST request to delete Access : {}", id);
        accessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
