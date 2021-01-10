package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.CommunicationService;
import com.amc.careplanner.web.rest.CommunicationResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.CommunicationDTO;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.ext.CommunicationServiceExt;
import com.amc.careplanner.service.dto.CarerClientRelationCriteria;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;
import com.amc.careplanner.service.dto.CommunicationCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.CommunicationQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Communication}.
 */
@RestController
@RequestMapping("/api/v1")
public class CommunicationResourceExt extends CommunicationResource{

    private final Logger log = LoggerFactory.getLogger(CommunicationResourceExt.class);

    private static final String ENTITY_NAME = "communication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommunicationServiceExt communicationServiceExt;

    private final CommunicationQueryService communicationQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public CommunicationResourceExt(CommunicationServiceExt communicationServiceExt, CommunicationQueryService communicationQueryService,  UserRepositoryExt userRepositoryExt) {
    	super(communicationServiceExt,communicationQueryService);
        this.communicationServiceExt = communicationServiceExt;
        this.communicationQueryService = communicationQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /communications} : Create a new communication.
     *
     * @param communicationDTO the communicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new communicationDTO, or with status {@code 400 (Bad Request)} if the communication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-communication-by-client-id")
    public ResponseEntity<CommunicationDTO> createCommunication(@Valid @RequestBody CommunicationDTO communicationDTO) throws URISyntaxException {
        log.debug("REST request to save Communication : {}", communicationDTO);
        if (communicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new communication cannot already have an ID", ENTITY_NAME, "idexists");
        }
//        communicationDTO.setDateCreated(ZonedDateTime.now());
        communicationDTO.setLastUpdatedDate(ZonedDateTime.now());
        communicationDTO.setClientId(getClientIdFromLoggedInUser());
        CommunicationDTO result = communicationServiceExt.save(communicationDTO);
        return ResponseEntity.created(new URI("/api/communications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /communications} : Updates an existing communication.
     *
     * @param communicationDTO the communicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communicationDTO,
     * or with status {@code 400 (Bad Request)} if the communicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the communicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-communication-by-client-id")
    public ResponseEntity<CommunicationDTO> updateCommunication(@Valid @RequestBody CommunicationDTO communicationDTO) throws URISyntaxException {
        log.debug("REST request to update Communication : {}", communicationDTO);
        if (communicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (communicationDTO != null && communicationDTO.getClientId() != null && communicationDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        communicationDTO.setLastUpdatedDate(ZonedDateTime.now());
        CommunicationDTO result = communicationServiceExt.save(communicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /communications} : get all the communications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of communications in body.
     */
    @GetMapping("/get-all-communications-by-client-id")
    public ResponseEntity<List<CommunicationDTO>> getAllCommunications(CommunicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Communications by criteria: {}", criteria);
        CommunicationCriteria communicationCriteria = new CommunicationCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		communicationCriteria.setClientId(longFilterForClientId);
        Page<CommunicationDTO> page = communicationQueryService.findByCriteria(communicationCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/get-all-communications-by-client-id-employee-id/{employeeId}")   
    public ResponseEntity< List<CommunicationDTO>> getAllCommunicationsByEmployeeId(@PathVariable Long employeeId, Pageable pageable) {
        log.debug("REST request to get Communication : {}", employeeId);
        Long loggedInClientId = getClientIdFromLoggedInUser();
        CommunicationCriteria communicationCriteria = new CommunicationCriteria();
       
		
        LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(loggedInClientId);
		communicationCriteria.setClientId(longFilterForClientId);
		
		LongFilter longFilterForEmployeeId = new LongFilter();
		longFilterForEmployeeId.setEquals(employeeId);
//		communicationCriteria.setEmployeeId(longFilterForEmployeeId);
		
		
		 Page<CommunicationDTO> listOfPages = communicationQueryService.findByCriteria(communicationCriteria,pageable);
		 List <CommunicationDTO> listOfDTOs = listOfPages.getContent();
		 if (listOfDTOs != null && listOfDTOs.size() > 0) {
			 CommunicationDTO communicationDTO =  listOfDTOs.get(0);
        	if (communicationDTO.getClientId() != null && communicationDTO.getClientId() != loggedInClientId) {
	        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
	         }
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(listOfDTOs));
    }

    /**
     * {@code GET  /communications/count} : count all the communications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/communications/count")
    public ResponseEntity<Long> countCommunications(CommunicationCriteria criteria) {
        log.debug("REST request to count Communications by criteria: {}", criteria);
        return ResponseEntity.ok().body(communicationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /communications/:id} : get the "id" communication.
     *
     * @param id the id of the communicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the communicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-communication-by-client-id/{id}")
    public ResponseEntity<CommunicationDTO> getCommunication(@PathVariable Long id) {
        log.debug("REST request to get Communication : {}", id);
        Optional<CommunicationDTO> communicationDTO = communicationServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(communicationDTO);
    }

    /**
     * {@code DELETE  /communications/:id} : delete the "id" communication.
     *
     * @param id the id of the communicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-communication-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteCommunication(@PathVariable Long id) {
        log.debug("REST request to delete Communication : {}", id);
        communicationServiceExt.delete(id);
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
