package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.MedicalContactService;
import com.amc.careplanner.web.rest.MedicalContactResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.MedicalContactDTO;
import com.amc.careplanner.service.ext.MedicalContactServiceExt;
import com.amc.careplanner.service.dto.MedicalContactCriteria;
import com.amc.careplanner.service.MedicalContactQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.MedicalContact}.
 */
@RestController
@RequestMapping("/api/v1")
public class MedicalContactResourceExt extends MedicalContactResource{

    private final Logger log = LoggerFactory.getLogger(MedicalContactResourceExt.class);

    private static final String ENTITY_NAME = "medicalContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalContactServiceExt medicalContactServiceExt;

    private final MedicalContactQueryService medicalContactQueryService;

    public MedicalContactResourceExt(MedicalContactServiceExt medicalContactServiceExt, MedicalContactQueryService medicalContactQueryService) {
        super(medicalContactServiceExt,medicalContactQueryService);
    	this.medicalContactServiceExt = medicalContactServiceExt;
        this.medicalContactQueryService = medicalContactQueryService;
    }

    /**
     * {@code POST  /medical-contacts} : Create a new medicalContact.
     *
     * @param medicalContactDTO the medicalContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalContactDTO, or with status {@code 400 (Bad Request)} if the medicalContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-contacts")
    public ResponseEntity<MedicalContactDTO> createMedicalContact(@Valid @RequestBody MedicalContactDTO medicalContactDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalContact : {}", medicalContactDTO);
        if (medicalContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalContactDTO result = medicalContactServiceExt.save(medicalContactDTO);
        return ResponseEntity.created(new URI("/api/medical-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-contacts} : Updates an existing medicalContact.
     *
     * @param medicalContactDTO the medicalContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalContactDTO,
     * or with status {@code 400 (Bad Request)} if the medicalContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-contacts")
    public ResponseEntity<MedicalContactDTO> updateMedicalContact(@Valid @RequestBody MedicalContactDTO medicalContactDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalContact : {}", medicalContactDTO);
        if (medicalContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalContactDTO result = medicalContactServiceExt.save(medicalContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-contacts} : get all the medicalContacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalContacts in body.
     */
    @GetMapping("/medical-contacts")
    public ResponseEntity<List<MedicalContactDTO>> getAllMedicalContacts(MedicalContactCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MedicalContacts by criteria: {}", criteria);
        Page<MedicalContactDTO> page = medicalContactQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medical-contacts/count} : count all the medicalContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/medical-contacts/count")
    public ResponseEntity<Long> countMedicalContacts(MedicalContactCriteria criteria) {
        log.debug("REST request to count MedicalContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(medicalContactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /medical-contacts/:id} : get the "id" medicalContact.
     *
     * @param id the id of the medicalContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-contacts/{id}")
    public ResponseEntity<MedicalContactDTO> getMedicalContact(@PathVariable Long id) {
        log.debug("REST request to get MedicalContact : {}", id);
        Optional<MedicalContactDTO> medicalContactDTO = medicalContactServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalContactDTO);
    }

    /**
     * {@code DELETE  /medical-contacts/:id} : delete the "id" medicalContact.
     *
     * @param id the id of the medicalContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-contacts/{id}")
    public ResponseEntity<Void> deleteMedicalContact(@PathVariable Long id) {
        log.debug("REST request to delete MedicalContact : {}", id);
        medicalContactServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
