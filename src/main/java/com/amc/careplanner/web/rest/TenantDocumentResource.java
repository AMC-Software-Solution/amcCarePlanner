package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.TenantDocumentService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.TenantDocumentDTO;
import com.amc.careplanner.service.dto.TenantDocumentCriteria;
import com.amc.careplanner.service.TenantDocumentQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.TenantDocument}.
 */
@RestController
@RequestMapping("/api")
public class TenantDocumentResource {

    private final Logger log = LoggerFactory.getLogger(TenantDocumentResource.class);

    private static final String ENTITY_NAME = "tenantDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenantDocumentService tenantDocumentService;

    private final TenantDocumentQueryService tenantDocumentQueryService;

    public TenantDocumentResource(TenantDocumentService tenantDocumentService, TenantDocumentQueryService tenantDocumentQueryService) {
        this.tenantDocumentService = tenantDocumentService;
        this.tenantDocumentQueryService = tenantDocumentQueryService;
    }

    /**
     * {@code POST  /tenant-documents} : Create a new tenantDocument.
     *
     * @param tenantDocumentDTO the tenantDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tenantDocumentDTO, or with status {@code 400 (Bad Request)} if the tenantDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tenant-documents")
    public ResponseEntity<TenantDocumentDTO> createTenantDocument(@Valid @RequestBody TenantDocumentDTO tenantDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save TenantDocument : {}", tenantDocumentDTO);
        if (tenantDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new tenantDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TenantDocumentDTO result = tenantDocumentService.save(tenantDocumentDTO);
        return ResponseEntity.created(new URI("/api/tenant-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tenant-documents} : Updates an existing tenantDocument.
     *
     * @param tenantDocumentDTO the tenantDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenantDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the tenantDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tenantDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tenant-documents")
    public ResponseEntity<TenantDocumentDTO> updateTenantDocument(@Valid @RequestBody TenantDocumentDTO tenantDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update TenantDocument : {}", tenantDocumentDTO);
        if (tenantDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TenantDocumentDTO result = tenantDocumentService.save(tenantDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenantDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tenant-documents} : get all the tenantDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tenantDocuments in body.
     */
    @GetMapping("/tenant-documents")
    public ResponseEntity<List<TenantDocumentDTO>> getAllTenantDocuments(TenantDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TenantDocuments by criteria: {}", criteria);
        Page<TenantDocumentDTO> page = tenantDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tenant-documents/count} : count all the tenantDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tenant-documents/count")
    public ResponseEntity<Long> countTenantDocuments(TenantDocumentCriteria criteria) {
        log.debug("REST request to count TenantDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(tenantDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tenant-documents/:id} : get the "id" tenantDocument.
     *
     * @param id the id of the tenantDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tenantDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tenant-documents/{id}")
    public ResponseEntity<TenantDocumentDTO> getTenantDocument(@PathVariable Long id) {
        log.debug("REST request to get TenantDocument : {}", id);
        Optional<TenantDocumentDTO> tenantDocumentDTO = tenantDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tenantDocumentDTO);
    }

    /**
     * {@code DELETE  /tenant-documents/:id} : delete the "id" tenantDocument.
     *
     * @param id the id of the tenantDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tenant-documents/{id}")
    public ResponseEntity<Void> deleteTenantDocument(@PathVariable Long id) {
        log.debug("REST request to delete TenantDocument : {}", id);
        tenantDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
