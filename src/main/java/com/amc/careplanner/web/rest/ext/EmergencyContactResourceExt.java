package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.EmergencyContactService;
import com.amc.careplanner.web.rest.EmergencyContactResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EmergencyContactDTO;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.ext.EmergencyContactServiceExt;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.service.dto.EmergencyContactCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.EmergencyContactQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.EmergencyContact}.
 */
@RestController
@RequestMapping("/api/v1")
public class EmergencyContactResourceExt extends EmergencyContactResource{

    private final Logger log = LoggerFactory.getLogger(EmergencyContactResourceExt.class);

    private static final String ENTITY_NAME = "emergencyContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmergencyContactServiceExt emergencyContactServiceExt;

    private final EmergencyContactQueryService emergencyContactQueryService;

    private final UserRepositoryExt userRepositoryExt;
    
	private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;


    public EmergencyContactResourceExt(EmergencyContactServiceExt emergencyContactServiceExt, EmergencyContactQueryService emergencyContactQueryService, UserRepositoryExt userRepositoryExt, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
    	super(emergencyContactServiceExt,emergencyContactQueryService);
        this.emergencyContactServiceExt = emergencyContactServiceExt;
        this.emergencyContactQueryService = emergencyContactQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
    }

    /**
     * {@code POST  /emergency-contacts} : Create a new emergencyContact.
     *
     * @param emergencyContactDTO the emergencyContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emergencyContactDTO, or with status {@code 400 (Bad Request)} if the emergencyContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-emergency-contact-by-client-id")
    public ResponseEntity<EmergencyContactDTO> createEmergencyContact(@Valid @RequestBody EmergencyContactDTO emergencyContactDTO) throws URISyntaxException {
        log.debug("REST request to save EmergencyContact : {}", emergencyContactDTO);
        if (emergencyContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new emergencyContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      emergencyContactDTO.setDateCreated(ZonedDateTime.now());
        emergencyContactDTO.setLastUpdatedDate(ZonedDateTime.now());
        emergencyContactDTO.setClientId(getClientIdFromLoggedInUser());
        EmergencyContactDTO result = emergencyContactServiceExt.save(emergencyContactDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "createEmergencyContact", "/api/v1/create-emergency-contact-by-client-id",
        		result.getName() + " has just been created", "EmergencyContact", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.created(new URI("/api/emergency-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emergency-contacts} : Updates an existing emergencyContact.
     *
     * @param emergencyContactDTO the emergencyContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emergencyContactDTO,
     * or with status {@code 400 (Bad Request)} if the emergencyContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emergencyContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-emergency-contact-by-client-id")
    public ResponseEntity<EmergencyContactDTO> updateEmergencyContact(@Valid @RequestBody EmergencyContactDTO emergencyContactDTO) throws URISyntaxException {
        log.debug("REST request to update EmergencyContact : {}", emergencyContactDTO);
        if (emergencyContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (emergencyContactDTO != null && emergencyContactDTO.getClientId() != null && emergencyContactDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        emergencyContactDTO.setLastUpdatedDate(ZonedDateTime.now());
        EmergencyContactDTO result = emergencyContactServiceExt.save(emergencyContactDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "updateEmergencyContact", "/api/v1/update-emergency-contact-by-client-id",
        		result.getName() + " has just been updated", "EmergencyContact", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emergencyContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emergency-contacts} : get all the emergencyContacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emergencyContacts in body.
     */
    @GetMapping("/get-all-emergency-contacts-by-client-id")
    public ResponseEntity<List<EmergencyContactDTO>> getAllEmergencyContacts(EmergencyContactCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmergencyContacts by criteria: {}", criteria);
        EmergencyContactCriteria emergencyCriteria = new EmergencyContactCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		emergencyCriteria.setClientId(longFilterForClientId);
        Page<EmergencyContactDTO> page = emergencyContactQueryService.findByCriteria(emergencyCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emergency-contacts/count} : count all the emergencyContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emergency-contacts/count")
    public ResponseEntity<Long> countEmergencyContacts(EmergencyContactCriteria criteria) {
        log.debug("REST request to count EmergencyContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(emergencyContactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emergency-contacts/:id} : get the "id" emergencyContact.
     *
     * @param id the id of the emergencyContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emergencyContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-emergency-contact-by-client-id/{id}")
    public ResponseEntity<EmergencyContactDTO> getEmergencyContact(@PathVariable Long id) {
        log.debug("REST request to get EmergencyContact : {}", id);
        Optional<EmergencyContactDTO> emergencyContactDTO = emergencyContactServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(emergencyContactDTO);
    }

    /**
     * {@code DELETE  /emergency-contacts/:id} : delete the "id" emergencyContact.
     *
     * @param id the id of the emergencyContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-emergency-contact-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteEmergencyContact(@PathVariable Long id) {
        log.debug("REST request to delete EmergencyContact : {}", id);
        emergencyContactServiceExt.delete(id);
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
