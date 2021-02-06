package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.MedicalContactService;
import com.amc.careplanner.web.rest.MedicalContactResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.MedicalContactDTO;
import com.amc.careplanner.service.ext.MedicalContactServiceExt;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.MedicalContactCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.MedicalContactQueryService;

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
    
    private final UserRepositoryExt userRepositoryExt;
    
	private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;


    public MedicalContactResourceExt(MedicalContactServiceExt medicalContactServiceExt, MedicalContactQueryService medicalContactQueryService, UserRepositoryExt userRepositoryExt, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
        super(medicalContactServiceExt,medicalContactQueryService);
    	this.medicalContactServiceExt = medicalContactServiceExt;
        this.medicalContactQueryService = medicalContactQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
    }

    /**
     * {@code POST  /medical-contacts} : Create a new medicalContact.
     *
     * @param medicalContactDTO the medicalContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalContactDTO, or with status {@code 400 (Bad Request)} if the medicalContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-medical-contact-by-client-id")
    public ResponseEntity<MedicalContactDTO> createMedicalContact(@Valid @RequestBody MedicalContactDTO medicalContactDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalContact : {}", medicalContactDTO);
        if (medicalContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      medicalContactDTO.setDateCreated(ZonedDateTime.now());
        medicalContactDTO.setLastUpdatedDate(ZonedDateTime.now());
        medicalContactDTO.setClientId(getClientIdFromLoggedInUser());
        MedicalContactDTO result = medicalContactServiceExt.save(medicalContactDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "createMedicalContact", "/api/v1/create-medical-contact-by-client-id",
        		result.getDoctorName() + " has just been created", "MedicalContact", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
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
    @PutMapping("/update-medical-contact-by-client-id")
    public ResponseEntity<MedicalContactDTO> updateMedicalContact(@Valid @RequestBody MedicalContactDTO medicalContactDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalContact : {}", medicalContactDTO);
        if (medicalContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (medicalContactDTO != null && medicalContactDTO.getClientId() != null && medicalContactDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        medicalContactDTO.setLastUpdatedDate(ZonedDateTime.now());
        MedicalContactDTO result = medicalContactServiceExt.save(medicalContactDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "updateMedicalContact", "/api/v1/update-medical-contact-by-client-id",
        		result.getDoctorName() + " has just been updated", "MedicalContact", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
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
    @GetMapping("/get-all-medical-contacts-by-client-id")
    public ResponseEntity<List<MedicalContactDTO>> getAllMedicalContacts(MedicalContactCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MedicalContacts by criteria: {}", criteria);
        MedicalContactCriteria medicalContactCriteria = new MedicalContactCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		medicalContactCriteria.setClientId(longFilterForClientId);
        Page<MedicalContactDTO> page = medicalContactQueryService.findByCriteria(medicalContactCriteria, pageable);
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
    @GetMapping("/get-medical-contact-by-client-id/{id}")
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
    @DeleteMapping("/delete-medical-contact-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteMedicalContact(@PathVariable Long id) {
        log.debug("REST request to delete MedicalContact : {}", id);
        medicalContactServiceExt.delete(id);
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
