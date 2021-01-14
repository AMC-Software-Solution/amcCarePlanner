package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.ServiceUserContactService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ServiceUserContactDTO;
import com.amc.careplanner.service.dto.ServiceUserContactCriteria;
import com.amc.careplanner.service.ServiceUserContactQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.ServiceUserContact}.
 */
@RestController
@RequestMapping("/api")
public class ServiceUserContactResource {

    private final Logger log = LoggerFactory.getLogger(ServiceUserContactResource.class);

    private static final String ENTITY_NAME = "serviceUserContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceUserContactService serviceUserContactService;

    private final ServiceUserContactQueryService serviceUserContactQueryService;

    public ServiceUserContactResource(ServiceUserContactService serviceUserContactService, ServiceUserContactQueryService serviceUserContactQueryService) {
        this.serviceUserContactService = serviceUserContactService;
        this.serviceUserContactQueryService = serviceUserContactQueryService;
    }

    /**
     * {@code POST  /service-user-contacts} : Create a new serviceUserContact.
     *
     * @param serviceUserContactDTO the serviceUserContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceUserContactDTO, or with status {@code 400 (Bad Request)} if the serviceUserContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-user-contacts")
    public ResponseEntity<ServiceUserContactDTO> createServiceUserContact(@Valid @RequestBody ServiceUserContactDTO serviceUserContactDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceUserContact : {}", serviceUserContactDTO);
        if (serviceUserContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceUserContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceUserContactDTO result = serviceUserContactService.save(serviceUserContactDTO);
        return ResponseEntity.created(new URI("/api/service-user-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-user-contacts} : Updates an existing serviceUserContact.
     *
     * @param serviceUserContactDTO the serviceUserContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceUserContactDTO,
     * or with status {@code 400 (Bad Request)} if the serviceUserContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceUserContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-user-contacts")
    public ResponseEntity<ServiceUserContactDTO> updateServiceUserContact(@Valid @RequestBody ServiceUserContactDTO serviceUserContactDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceUserContact : {}", serviceUserContactDTO);
        if (serviceUserContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceUserContactDTO result = serviceUserContactService.save(serviceUserContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceUserContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-user-contacts} : get all the serviceUserContacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceUserContacts in body.
     */
    @GetMapping("/service-user-contacts")
    public ResponseEntity<List<ServiceUserContactDTO>> getAllServiceUserContacts(ServiceUserContactCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceUserContacts by criteria: {}", criteria);
        Page<ServiceUserContactDTO> page = serviceUserContactQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-user-contacts/count} : count all the serviceUserContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-user-contacts/count")
    public ResponseEntity<Long> countServiceUserContacts(ServiceUserContactCriteria criteria) {
        log.debug("REST request to count ServiceUserContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceUserContactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-user-contacts/:id} : get the "id" serviceUserContact.
     *
     * @param id the id of the serviceUserContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceUserContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-user-contacts/{id}")
    public ResponseEntity<ServiceUserContactDTO> getServiceUserContact(@PathVariable Long id) {
        log.debug("REST request to get ServiceUserContact : {}", id);
        Optional<ServiceUserContactDTO> serviceUserContactDTO = serviceUserContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceUserContactDTO);
    }

    /**
     * {@code DELETE  /service-user-contacts/:id} : delete the "id" serviceUserContact.
     *
     * @param id the id of the serviceUserContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-user-contacts/{id}")
    public ResponseEntity<Void> deleteServiceUserContact(@PathVariable Long id) {
        log.debug("REST request to delete ServiceUserContact : {}", id);
        serviceUserContactService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
