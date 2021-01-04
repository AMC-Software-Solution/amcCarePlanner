package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.ServiceUserContactService;
import com.amc.careplanner.web.rest.ServiceUserContactResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ServiceUserContactDTO;
import com.amc.careplanner.service.ext.ServiceUserContactServiceExt;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.ServiceUserContactCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.ServiceUserContactQueryService;

import io.github.jhipster.service.filter.LongFilter;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.ServiceUserContact}.
 */
@RestController
@RequestMapping("/api/v1")
public class ServiceUserContactResourceExt extends ServiceUserContactResource{

    private final Logger log = LoggerFactory.getLogger(ServiceUserContactResourceExt.class);

    private static final String ENTITY_NAME = "serviceUserContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceUserContactServiceExt serviceUserContactServiceExt;

    private final ServiceUserContactQueryService serviceUserContactQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public ServiceUserContactResourceExt(ServiceUserContactServiceExt serviceUserContactServiceExt, ServiceUserContactQueryService serviceUserContactQueryService, UserRepositoryExt userRepositoryExt) {
        super(serviceUserContactServiceExt,serviceUserContactQueryService);
    	this.serviceUserContactServiceExt = serviceUserContactServiceExt;
        this.serviceUserContactQueryService = serviceUserContactQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /service-user-contacts} : Create a new serviceUserContact.
     *
     * @param serviceUserContactDTO the serviceUserContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceUserContactDTO, or with status {@code 400 (Bad Request)} if the serviceUserContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-service-user-contact-by-client-id")
    public ResponseEntity<ServiceUserContactDTO> createServiceUserContact(@Valid @RequestBody ServiceUserContactDTO serviceUserContactDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceUserContact : {}", serviceUserContactDTO);
        if (serviceUserContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceUserContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      serviceUserContactDTO.setDateCreated(ZonedDateTime.now());
        serviceUserContactDTO.setLastUpdatedDate(ZonedDateTime.now());
        serviceUserContactDTO.setClientId(getClientIdFromLoggedInUser());
        ServiceUserContactDTO result = serviceUserContactServiceExt.save(serviceUserContactDTO);
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
    @PutMapping("/update-service-user-contact-by-cliebt-id")
    public ResponseEntity<ServiceUserContactDTO> updateServiceUserContact(@Valid @RequestBody ServiceUserContactDTO serviceUserContactDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceUserContact : {}", serviceUserContactDTO);
        if (serviceUserContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (serviceUserContactDTO != null && serviceUserContactDTO.getClientId() != null && serviceUserContactDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        serviceUserContactDTO.setLastUpdatedDate(ZonedDateTime.now());
        ServiceUserContactDTO result = serviceUserContactServiceExt.save(serviceUserContactDTO);
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
        log.debug("REST request to get EmployeeHolidays by criteria: {}", criteria);
        ServiceUserContactCriteria serviceUserContactCriteria = new ServiceUserContactCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		serviceUserContactCriteria.setClientId(longFilterForClientId);
        Page<ServiceUserContactDTO> page = serviceUserContactQueryService.findByCriteria(serviceUserContactCriteria, pageable);
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
    @GetMapping("/get-service-user-contact-by-client-id/{id}")
    public ResponseEntity<ServiceUserContactDTO> getServiceUserContact(@PathVariable Long id) {
        log.debug("REST request to get ServiceUserContact : {}", id);
        Optional<ServiceUserContactDTO> serviceUserContactDTO = serviceUserContactServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceUserContactDTO);
    }

    /**
     * {@code DELETE  /service-user-contacts/:id} : delete the "id" serviceUserContact.
     *
     * @param id the id of the serviceUserContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-service-user-contact-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteServiceUserContact(@PathVariable Long id) {
        log.debug("REST request to delete ServiceUserContact : {}", id);
        serviceUserContactServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    private Long getClientIdFromLoggedInUser() {
    	Long clientId = 0L;
    	String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();
		
		if(loggedInAdminUser != null) {
			clientId = Long.valueOf(loggedInAdminUser.getLogin());
		}
		
		return clientId;
    }
}
