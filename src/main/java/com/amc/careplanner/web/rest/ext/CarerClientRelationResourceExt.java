package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.CarerClientRelationService;
import com.amc.careplanner.web.rest.CarerClientRelationResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.ext.CarerClientRelationServiceExt;
import com.amc.careplanner.service.dto.CarerClientRelationCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.CarerClientRelationQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.CarerClientRelation}.
 */
@RestController
@RequestMapping("/api/v1")
public class CarerClientRelationResourceExt extends CarerClientRelationResource{

    private final Logger log = LoggerFactory.getLogger(CarerClientRelationResourceExt.class);

    private static final String ENTITY_NAME = "carerClientRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarerClientRelationServiceExt carerClientRelationServiceExt;

    private final CarerClientRelationQueryService carerClientRelationQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public CarerClientRelationResourceExt(CarerClientRelationServiceExt carerClientRelationServiceExt, CarerClientRelationQueryService carerClientRelationQueryService, UserRepositoryExt userRepositoryExt) {
    	super(carerClientRelationServiceExt,carerClientRelationQueryService);
        this.carerClientRelationServiceExt = carerClientRelationServiceExt;
        this.carerClientRelationQueryService = carerClientRelationQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /carer-client-relations} : Create a new carerClientRelation.
     *
     * @param carerClientRelationDTO the carerClientRelationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carerClientRelationDTO, or with status {@code 400 (Bad Request)} if the carerClientRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-carer-client-relation-by-client-id")
    public ResponseEntity<CarerClientRelationDTO> createCarerClientRelation(@Valid @RequestBody CarerClientRelationDTO carerClientRelationDTO) throws URISyntaxException {
        log.debug("REST request to save CarerClientRelation : {}", carerClientRelationDTO);
        if (carerClientRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new carerClientRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //carerClientRelationDTO.setDateCreated(ZonedDateTime.now());
        carerClientRelationDTO.setLastUpdatedDate(ZonedDateTime.now());
        carerClientRelationDTO.setClientId(getClientIdFromLoggedInUser());
        CarerClientRelationDTO result = carerClientRelationServiceExt.save(carerClientRelationDTO);
        return ResponseEntity.created(new URI("/api/carer-client-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carer-client-relations} : Updates an existing carerClientRelation.
     *
     * @param carerClientRelationDTO the carerClientRelationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carerClientRelationDTO,
     * or with status {@code 400 (Bad Request)} if the carerClientRelationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carerClientRelationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-carer-client-relation-by-client-id")
    public ResponseEntity<CarerClientRelationDTO> updateCarerClientRelation(@Valid @RequestBody CarerClientRelationDTO carerClientRelationDTO) throws URISyntaxException {
        log.debug("REST request to update CarerClientRelation : {}", carerClientRelationDTO);
        if (carerClientRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (carerClientRelationDTO != null && carerClientRelationDTO.getClientId() != null && carerClientRelationDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        carerClientRelationDTO.setLastUpdatedDate(ZonedDateTime.now());
        CarerClientRelationDTO result = carerClientRelationServiceExt.save(carerClientRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carerClientRelationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carer-client-relations} : get all the carerClientRelations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carerClientRelations in body.
     */
    @GetMapping("/get-all-carer-client-relations-by-client-id")
    public ResponseEntity<List<CarerClientRelationDTO>> getAllCarerClientRelations(CarerClientRelationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CarerClientRelations by criteria: {}", criteria);
        CarerClientRelationCriteria carerClientRelationCriteria = new CarerClientRelationCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		carerClientRelationCriteria.setClientId(longFilterForClientId);
        Page< CarerClientRelationDTO> page =  carerClientRelationQueryService.findByCriteria( carerClientRelationCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carer-client-relations/count} : count all the carerClientRelations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/carer-client-relations/count")
    public ResponseEntity<Long> countCarerClientRelations(CarerClientRelationCriteria criteria) {
        log.debug("REST request to count CarerClientRelations by criteria: {}", criteria);
        return ResponseEntity.ok().body(carerClientRelationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /carer-client-relations/:id} : get the "id" carerClientRelation.
     *
     * @param id the id of the carerClientRelationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carerClientRelationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-carer-client-relation-by-client-id/{id}")
    public ResponseEntity<CarerClientRelationDTO> getCarerClientRelation(@PathVariable Long id) {
        log.debug("REST request to get CarerClientRelation : {}", id);
        Optional<CarerClientRelationDTO> carerClientRelationDTO = carerClientRelationServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(carerClientRelationDTO);
    }

    /**
     * {@code DELETE  /carer-client-relations/:id} : delete the "id" carerClientRelation.
     *
     * @param id the id of the carerClientRelationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-carer-client-relation-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteCarerClientRelation(@PathVariable Long id) {
        log.debug("REST request to delete CarerClientRelation : {}", id);
        carerClientRelationServiceExt.delete(id);
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
