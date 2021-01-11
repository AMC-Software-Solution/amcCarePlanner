package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.ServiceUserLocationService;
import com.amc.careplanner.web.rest.ServiceUserLocationResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ServiceUserLocationDTO;
import com.amc.careplanner.service.ext.ServiceUserLocationServiceExt;
import com.amc.careplanner.service.dto.CarerClientRelationCriteria;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.ServiceUserLocationCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.ServiceUserLocationQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.ServiceUserLocation}.
 */
@RestController
@RequestMapping("/api/v1")
public class ServiceUserLocationResourceExt extends ServiceUserLocationResource{

    private final Logger log = LoggerFactory.getLogger(ServiceUserLocationResourceExt.class);

    private static final String ENTITY_NAME = "serviceUserLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceUserLocationServiceExt serviceUserLocationServiceExt;

    private final ServiceUserLocationQueryService serviceUserLocationQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public ServiceUserLocationResourceExt(ServiceUserLocationServiceExt serviceUserLocationServiceExt, ServiceUserLocationQueryService serviceUserLocationQueryService, UserRepositoryExt userRepositoryExt) {
        super(serviceUserLocationServiceExt,serviceUserLocationQueryService);
    	this.serviceUserLocationServiceExt = serviceUserLocationServiceExt;
        this.serviceUserLocationQueryService = serviceUserLocationQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /service-user-locations} : Create a new serviceUserLocation.
     *
     * @param serviceUserLocationDTO the serviceUserLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceUserLocationDTO, or with status {@code 400 (Bad Request)} if the serviceUserLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-service-user-location-by-client-id")
    public ResponseEntity<ServiceUserLocationDTO> createServiceUserLocation(@Valid @RequestBody ServiceUserLocationDTO serviceUserLocationDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceUserLocation : {}", serviceUserLocationDTO);
        if (serviceUserLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceUserLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      serviceUserLocationDTO.setDateCreated(ZonedDateTime.now());
        serviceUserLocationDTO.setLastUpdatedDate(ZonedDateTime.now());
        serviceUserLocationDTO.setClientId(getClientIdFromLoggedInUser());
        ServiceUserLocationDTO result = serviceUserLocationServiceExt.save(serviceUserLocationDTO);
        return ResponseEntity.created(new URI("/api/service-user-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-user-locations} : Updates an existing serviceUserLocation.
     *
     * @param serviceUserLocationDTO the serviceUserLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceUserLocationDTO,
     * or with status {@code 400 (Bad Request)} if the serviceUserLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceUserLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-service-user-location-by-client-id")
    public ResponseEntity<ServiceUserLocationDTO> updateServiceUserLocation(@Valid @RequestBody ServiceUserLocationDTO serviceUserLocationDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceUserLocation : {}", serviceUserLocationDTO);
        if (serviceUserLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (serviceUserLocationDTO != null && serviceUserLocationDTO.getClientId() != null && serviceUserLocationDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        serviceUserLocationDTO.setLastUpdatedDate(ZonedDateTime.now());
        ServiceUserLocationDTO result = serviceUserLocationServiceExt.save(serviceUserLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceUserLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-user-locations} : get all the serviceUserLocations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceUserLocations in body.
     */
    @GetMapping("/get-all-service-user-locations-by-client-id")
    public ResponseEntity<List<ServiceUserLocationDTO>> getAllServiceUserLocations(ServiceUserLocationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceUserLocations by criteria: {}", criteria);
        ServiceUserLocationCriteria serviceUserLocationCriteria = new ServiceUserLocationCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		serviceUserLocationCriteria.setClientId(longFilterForClientId);
        Page<ServiceUserLocationDTO> page = serviceUserLocationQueryService.findByCriteria(serviceUserLocationCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/get-all-service-user-locations-by-client-id-employee-id/{employeeId}")   
    public ResponseEntity< List<ServiceUserLocationDTO>> getAllServiceUserLocationsByEmployeeId(@PathVariable Long employeeId, Pageable pageable) {
        log.debug("REST request to get ServiceUserLocations : {}", employeeId);
        Long loggedInClientId = getClientIdFromLoggedInUser();
        ServiceUserLocationCriteria serviceUserLocationCriteria = new ServiceUserLocationCriteria();
       
		
        LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(loggedInClientId);
		serviceUserLocationCriteria.setClientId(longFilterForClientId);
		
		LongFilter longFilterForEmployeeId = new LongFilter();
		longFilterForEmployeeId.setEquals(employeeId);
		serviceUserLocationCriteria.setEmployeeId(longFilterForEmployeeId);
		
		
		 Page<ServiceUserLocationDTO> listOfPages = serviceUserLocationQueryService.findByCriteria(serviceUserLocationCriteria,pageable);
		 List <ServiceUserLocationDTO> listOfDTOs = listOfPages.getContent();
		 if (listOfDTOs != null && listOfDTOs.size() > 0) {
			 ServiceUserLocationDTO serviceUserLocationDTO =  listOfDTOs.get(0);
        	if (serviceUserLocationDTO.getClientId() != null && serviceUserLocationDTO.getClientId() != loggedInClientId) {
	        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
	         }
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(listOfDTOs));
    }

    /**
     * {@code GET  /service-user-locations/count} : count all the serviceUserLocations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-user-locations/count")
    public ResponseEntity<Long> countServiceUserLocations(ServiceUserLocationCriteria criteria) {
        log.debug("REST request to count ServiceUserLocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceUserLocationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-user-locations/:id} : get the "id" serviceUserLocation.
     *
     * @param id the id of the serviceUserLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceUserLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-service-user-location-ny-client-id/{id}")
    public ResponseEntity<ServiceUserLocationDTO> getServiceUserLocation(@PathVariable Long id) {
        log.debug("REST request to get ServiceUserLocation : {}", id);
        Optional<ServiceUserLocationDTO> serviceUserLocationDTO = serviceUserLocationServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceUserLocationDTO);
    }

    /**
     * {@code DELETE  /service-user-locations/:id} : delete the "id" serviceUserLocation.
     *
     * @param id the id of the serviceUserLocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-service-user-location-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteServiceUserLocation(@PathVariable Long id) {
        log.debug("REST request to delete ServiceUserLocation : {}", id);
        serviceUserLocationServiceExt.delete(id);
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
