package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.EqualityService;
import com.amc.careplanner.web.rest.EqualityResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EqualityDTO;
import com.amc.careplanner.service.ext.EqualityServiceExt;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.EqualityCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.EqualityQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Equality}.
 */
@RestController
@RequestMapping("/api/v1")
public class EqualityResourceExt extends EqualityResource{

    private final Logger log = LoggerFactory.getLogger(EqualityResourceExt.class);

    private static final String ENTITY_NAME = "equality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EqualityServiceExt equalityServiceExt;

    private final EqualityQueryService equalityQueryService;
    
    private final UserRepositoryExt userRepositoryExt;
    
	private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;


    public EqualityResourceExt(EqualityServiceExt equalityServiceExt, EqualityQueryService equalityQueryService, UserRepositoryExt userRepositoryExt, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
        super(equalityServiceExt,equalityQueryService);
    	this.equalityServiceExt = equalityServiceExt;
        this.equalityQueryService = equalityQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
    }

    /**
     * {@code POST  /equalities} : Create a new equality.
     *
     * @param equalityDTO the equalityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equalityDTO, or with status {@code 400 (Bad Request)} if the equality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-equality-by-client-id")
    public ResponseEntity<EqualityDTO> createEquality(@Valid @RequestBody EqualityDTO equalityDTO) throws URISyntaxException {
        log.debug("REST request to save Equality : {}", equalityDTO);
        if (equalityDTO.getId() != null) {
            throw new BadRequestAlertException("A new equality cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      equalityDTO.setDateCreated(ZonedDateTime.now());
        equalityDTO.setLastUpdatedDate(ZonedDateTime.now());
        equalityDTO.setClientId(getClientIdFromLoggedInUser());
        EqualityDTO result = equalityServiceExt.save(equalityDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "createEquality", "/api/v1/create-equality-by-client-id",
        		result.getId() + " has just been created", "Equality", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.created(new URI("/api/equalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equalities} : Updates an existing equality.
     *
     * @param equalityDTO the equalityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equalityDTO,
     * or with status {@code 400 (Bad Request)} if the equalityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equalityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-equality-by-client-id")
    public ResponseEntity<EqualityDTO> updateEquality(@Valid @RequestBody EqualityDTO equalityDTO) throws URISyntaxException {
        log.debug("REST request to update Equality : {}", equalityDTO);
        if (equalityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (equalityDTO != null && equalityDTO.getClientId() != null && equalityDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        equalityDTO.setLastUpdatedDate(ZonedDateTime.now());
        EqualityDTO result = equalityServiceExt.save(equalityDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "updateEquality", "/api/v1/update-equality-by-client-id",
        		result.getId() + " has just been updated", "Equality", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equalityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equalities} : get all the equalities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equalities in body.
     */
    @GetMapping("/get-all-equalities-by-client-id")
    public ResponseEntity<List<EqualityDTO>> getAllEqualities(EqualityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Equalities by criteria: {}", criteria);
        EqualityCriteria equalityCriteria = new EqualityCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		equalityCriteria.setClientId(longFilterForClientId);
        Page<EqualityDTO> page = equalityQueryService.findByCriteria(equalityCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equalities/count} : count all the equalities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/equalities/count")
    public ResponseEntity<Long> countEqualities(EqualityCriteria criteria) {
        log.debug("REST request to count Equalities by criteria: {}", criteria);
        return ResponseEntity.ok().body(equalityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /equalities/:id} : get the "id" equality.
     *
     * @param id the id of the equalityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equalityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-equality-by-client-id/{id}")
    public ResponseEntity<EqualityDTO> getEquality(@PathVariable Long id) {
        log.debug("REST request to get Equality : {}", id);
        Optional<EqualityDTO> equalityDTO = equalityServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(equalityDTO);
    }

    /**
     * {@code DELETE  /equalities/:id} : delete the "id" equality.
     *
     * @param id the id of the equalityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-equality-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteEquality(@PathVariable Long id) {
        log.debug("REST request to delete Equality : {}", id);
        equalityServiceExt.delete(id);
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
