package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.ServceUserDocumentService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ServceUserDocumentDTO;
import com.amc.careplanner.service.dto.ServceUserDocumentCriteria;
import com.amc.careplanner.service.ServceUserDocumentQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.ServceUserDocument}.
 */
@RestController
@RequestMapping("/api")
public class ServceUserDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ServceUserDocumentResource.class);

    private static final String ENTITY_NAME = "servceUserDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServceUserDocumentService servceUserDocumentService;

    private final ServceUserDocumentQueryService servceUserDocumentQueryService;

    public ServceUserDocumentResource(ServceUserDocumentService servceUserDocumentService, ServceUserDocumentQueryService servceUserDocumentQueryService) {
        this.servceUserDocumentService = servceUserDocumentService;
        this.servceUserDocumentQueryService = servceUserDocumentQueryService;
    }

    /**
     * {@code POST  /servce-user-documents} : Create a new servceUserDocument.
     *
     * @param servceUserDocumentDTO the servceUserDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servceUserDocumentDTO, or with status {@code 400 (Bad Request)} if the servceUserDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servce-user-documents")
    public ResponseEntity<ServceUserDocumentDTO> createServceUserDocument(@Valid @RequestBody ServceUserDocumentDTO servceUserDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save ServceUserDocument : {}", servceUserDocumentDTO);
        if (servceUserDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new servceUserDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServceUserDocumentDTO result = servceUserDocumentService.save(servceUserDocumentDTO);
        return ResponseEntity.created(new URI("/api/servce-user-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servce-user-documents} : Updates an existing servceUserDocument.
     *
     * @param servceUserDocumentDTO the servceUserDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servceUserDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the servceUserDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servceUserDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servce-user-documents")
    public ResponseEntity<ServceUserDocumentDTO> updateServceUserDocument(@Valid @RequestBody ServceUserDocumentDTO servceUserDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update ServceUserDocument : {}", servceUserDocumentDTO);
        if (servceUserDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServceUserDocumentDTO result = servceUserDocumentService.save(servceUserDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servceUserDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /servce-user-documents} : get all the servceUserDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servceUserDocuments in body.
     */
    @GetMapping("/servce-user-documents")
    public ResponseEntity<List<ServceUserDocumentDTO>> getAllServceUserDocuments(ServceUserDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServceUserDocuments by criteria: {}", criteria);
        Page<ServceUserDocumentDTO> page = servceUserDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servce-user-documents/count} : count all the servceUserDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/servce-user-documents/count")
    public ResponseEntity<Long> countServceUserDocuments(ServceUserDocumentCriteria criteria) {
        log.debug("REST request to count ServceUserDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(servceUserDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /servce-user-documents/:id} : get the "id" servceUserDocument.
     *
     * @param id the id of the servceUserDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servceUserDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servce-user-documents/{id}")
    public ResponseEntity<ServceUserDocumentDTO> getServceUserDocument(@PathVariable Long id) {
        log.debug("REST request to get ServceUserDocument : {}", id);
        Optional<ServceUserDocumentDTO> servceUserDocumentDTO = servceUserDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servceUserDocumentDTO);
    }

    /**
     * {@code DELETE  /servce-user-documents/:id} : delete the "id" servceUserDocument.
     *
     * @param id the id of the servceUserDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servce-user-documents/{id}")
    public ResponseEntity<Void> deleteServceUserDocument(@PathVariable Long id) {
        log.debug("REST request to delete ServceUserDocument : {}", id);
        servceUserDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
