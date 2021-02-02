package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.EligibilityService;
import com.amc.careplanner.web.rest.EligibilityResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EligibilityDTO;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.ext.EligibilityServiceExt;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.service.dto.EligibilityCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.EligibilityQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Eligibility}.
 */
@RestController
@RequestMapping("/api/v1")
public class EligibilityResourceExt extends EligibilityResource{

    private final Logger log = LoggerFactory.getLogger(EligibilityResourceExt.class);

    private static final String ENTITY_NAME = "eligibility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EligibilityServiceExt eligibilityServiceExt;

    private final EligibilityQueryService eligibilityQueryService;
    
    private final UserRepositoryExt userRepositoryExt;
    
	private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;


    public EligibilityResourceExt(EligibilityServiceExt eligibilityServiceExt, EligibilityQueryService eligibilityQueryService, UserRepositoryExt userRepositoryExt, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
    	super(eligibilityServiceExt,eligibilityQueryService);
        this.eligibilityServiceExt = eligibilityServiceExt;
        this.eligibilityQueryService = eligibilityQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
    }

    /**
     * {@code POST  /eligibilities} : Create a new eligibility.
     *
     * @param eligibilityDTO the eligibilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eligibilityDTO, or with status {@code 400 (Bad Request)} if the eligibility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-eligibility-by-client-id")
    public ResponseEntity<EligibilityDTO> createEligibility(@Valid @RequestBody EligibilityDTO eligibilityDTO) throws URISyntaxException {
        log.debug("REST request to save Eligibility : {}", eligibilityDTO);
        if (eligibilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new eligibility cannot already have an ID", ENTITY_NAME, "idexists");
        }
//        eligibilityDTO.setDateCreated(ZonedDateTime.now());
        eligibilityDTO.setLastUpdatedDate(ZonedDateTime.now());
        eligibilityDTO.setClientId(getClientIdFromLoggedInUser());
        EligibilityDTO result = eligibilityServiceExt.save(eligibilityDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "createEligibility", "/api/v1/create-eligibility-by-client-id",
        		result.getNote() + " has just been created", "Eligibility", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.created(new URI("/api/eligibilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eligibilities} : Updates an existing eligibility.
     *
     * @param eligibilityDTO the eligibilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibilityDTO,
     * or with status {@code 400 (Bad Request)} if the eligibilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eligibilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-eligibility-by-client-id")
    public ResponseEntity<EligibilityDTO> updateEligibility(@Valid @RequestBody EligibilityDTO eligibilityDTO) throws URISyntaxException {
        log.debug("REST request to update Eligibility : {}", eligibilityDTO);
        if (eligibilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        if (eligibilityDTO != null && eligibilityDTO.getClientId() != null && eligibilityDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        eligibilityDTO.setLastUpdatedDate(ZonedDateTime.now());
        EligibilityDTO result = eligibilityServiceExt.save(eligibilityDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "updateEligibility", "/api/v1/update-eligibility-by-client-id",
        		result.getNote() + " has just been updated", "Eligibility", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eligibilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eligibilities} : get all the eligibilities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eligibilities in body.
     */
    @GetMapping("/get-all-eligibilities-by-client-id")
    public ResponseEntity<List<EligibilityDTO>> getAllEligibilities(EligibilityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Eligibilities by criteria: {}", criteria);
        EligibilityCriteria eligibilityCriteria = new EligibilityCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		eligibilityCriteria.setClientId(longFilterForClientId);
        Page<EligibilityDTO> page = eligibilityQueryService.findByCriteria(eligibilityCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    
    @GetMapping("/get-all- eligibilities-by-client-id-employee-id/{employeeId}")   
    public ResponseEntity< List< EligibilityDTO>> getAllEligibilitiesByEmployeeId(@PathVariable Long employeeId, Pageable pageable) {
        log.debug("REST request to get Eligibilities : {}", employeeId);
        Long loggedInClientId = getClientIdFromLoggedInUser();
        EligibilityCriteria  eligibilityCriteria = new  EligibilityCriteria();


        LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(loggedInClientId);
		eligibilityCriteria.setClientId(longFilterForClientId);

		LongFilter longFilterForEmployeeId = new LongFilter();
		longFilterForEmployeeId.setEquals(employeeId);
		eligibilityCriteria.setEmployeeId(longFilterForEmployeeId);


		 Page< EligibilityDTO> listOfPages = eligibilityQueryService.findByCriteria(eligibilityCriteria,pageable);
		 List < EligibilityDTO> listOfDTOs = listOfPages.getContent();
		 if (listOfDTOs != null && listOfDTOs.size() > 0) {
			 EligibilityDTO eligibilityDTO =  listOfDTOs.get(0);
        	if (eligibilityDTO.getClientId() != null && eligibilityDTO.getClientId() != loggedInClientId) {
	        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
	         }
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(listOfDTOs));
    }

    

    /**
     * {@code GET  /eligibilities/count} : count all the eligibilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/eligibilities/count")
    public ResponseEntity<Long> countEligibilities(EligibilityCriteria criteria) {
        log.debug("REST request to count Eligibilities by criteria: {}", criteria);
        return ResponseEntity.ok().body(eligibilityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /eligibilities/:id} : get the "id" eligibility.
     *
     * @param id the id of the eligibilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eligibilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-eligibility-by-client-id/{id}")
    public ResponseEntity<EligibilityDTO> getEligibility(@PathVariable Long id) {
        log.debug("REST request to get Eligibility : {}", id);
        Optional<EligibilityDTO> eligibilityDTO = eligibilityServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(eligibilityDTO);
    }

    /**
     * {@code DELETE  /eligibilities/:id} : delete the "id" eligibility.
     *
     * @param id the id of the eligibilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-eligibility-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteEligibility(@PathVariable Long id) {
        log.debug("REST request to delete Eligibility : {}", id);
        eligibilityServiceExt.delete(id);
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
