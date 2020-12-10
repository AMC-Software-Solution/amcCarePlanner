package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.ConsentService;
import com.amc.careplanner.web.rest.ConsentResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ConsentDTO;
import com.amc.careplanner.service.ext.ConsentServiceExt;
import com.amc.careplanner.service.dto.ConsentCriteria;
import com.amc.careplanner.service.ConsentQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Consent}.
 */
@RestController
@RequestMapping("/api/v1")
public class ConsentResourceExt extends ConsentResource{

    private final Logger log = LoggerFactory.getLogger(ConsentResourceExt.class);

    private static final String ENTITY_NAME = "consent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsentServiceExt consentServiceExt;

    private final ConsentQueryService consentQueryService;

    public ConsentResourceExt(ConsentServiceExt consentServiceExt, ConsentQueryService consentQueryService) {
    	super(consentServiceExt,consentQueryService);
        this.consentServiceExt = consentServiceExt;
        this.consentQueryService = consentQueryService;
    }

    /**
     * {@code POST  /consents} : Create a new consent.
     *
     * @param consentDTO the consentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consentDTO, or with status {@code 400 (Bad Request)} if the consent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consents")
    public ResponseEntity<ConsentDTO> createConsent(@Valid @RequestBody ConsentDTO consentDTO) throws URISyntaxException {
        log.debug("REST request to save Consent : {}", consentDTO);
        if (consentDTO.getId() != null) {
            throw new BadRequestAlertException("A new consent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsentDTO result = consentServiceExt.save(consentDTO);
        return ResponseEntity.created(new URI("/api/consents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consents} : Updates an existing consent.
     *
     * @param consentDTO the consentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consentDTO,
     * or with status {@code 400 (Bad Request)} if the consentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consents")
    public ResponseEntity<ConsentDTO> updateConsent(@Valid @RequestBody ConsentDTO consentDTO) throws URISyntaxException {
        log.debug("REST request to update Consent : {}", consentDTO);
        if (consentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsentDTO result = consentServiceExt.save(consentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consents} : get all the consents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consents in body.
     */
    @GetMapping("/consents")
    public ResponseEntity<List<ConsentDTO>> getAllConsents(ConsentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Consents by criteria: {}", criteria);
        Page<ConsentDTO> page = consentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consents/count} : count all the consents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/consents/count")
    public ResponseEntity<Long> countConsents(ConsentCriteria criteria) {
        log.debug("REST request to count Consents by criteria: {}", criteria);
        return ResponseEntity.ok().body(consentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /consents/:id} : get the "id" consent.
     *
     * @param id the id of the consentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consents/{id}")
    public ResponseEntity<ConsentDTO> getConsent(@PathVariable Long id) {
        log.debug("REST request to get Consent : {}", id);
        Optional<ConsentDTO> consentDTO = consentServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(consentDTO);
    }

    /**
     * {@code DELETE  /consents/:id} : delete the "id" consent.
     *
     * @param id the id of the consentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consents/{id}")
    public ResponseEntity<Void> deleteConsent(@PathVariable Long id) {
        log.debug("REST request to delete Consent : {}", id);
        consentServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
