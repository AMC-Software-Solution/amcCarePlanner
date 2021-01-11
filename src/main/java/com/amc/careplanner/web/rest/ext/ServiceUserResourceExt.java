package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.ServiceUserService;
import com.amc.careplanner.web.rest.ServiceUserResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ServiceUserDTO;
import com.amc.careplanner.service.ext.ServiceUserServiceExt;
import com.amc.careplanner.service.dto.CarerClientRelationCriteria;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.ServiceUserCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.ServiceUserQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.ServiceUser}.
 */
@RestController
@RequestMapping("/api/v1")
public class ServiceUserResourceExt extends ServiceUserResource{

    private final Logger log = LoggerFactory.getLogger(ServiceUserResourceExt.class);

    private static final String ENTITY_NAME = "serviceUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceUserServiceExt serviceUserServiceExt;

    private final ServiceUserQueryService serviceUserQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public ServiceUserResourceExt(ServiceUserServiceExt serviceUserServiceExt, ServiceUserQueryService serviceUserQueryService, UserRepositoryExt userRepositoryExt) {
        super(serviceUserServiceExt,serviceUserQueryService);
    	this.serviceUserServiceExt = serviceUserServiceExt;
        this.serviceUserQueryService = serviceUserQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /service-users} : Create a new serviceUser.
     *
     * @param serviceUserDTO the serviceUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceUserDTO, or with status {@code 400 (Bad Request)} if the serviceUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-service-user-by-client-id")
    public ResponseEntity<ServiceUserDTO> createServiceUser(@Valid @RequestBody ServiceUserDTO serviceUserDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceUser : {}", serviceUserDTO);
        if (serviceUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      serviceUserDTO.setDateCreated(ZonedDateTime.now());
        serviceUserDTO.setLastUpdatedDate(ZonedDateTime.now());
        serviceUserDTO.setClientId(getClientIdFromLoggedInUser());
        ServiceUserDTO result = serviceUserServiceExt.save(serviceUserDTO);
        return ResponseEntity.created(new URI("/api/service-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-users} : Updates an existing serviceUser.
     *
     * @param serviceUserDTO the serviceUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceUserDTO,
     * or with status {@code 400 (Bad Request)} if the serviceUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-service-user-by-client-id")
    public ResponseEntity<ServiceUserDTO> updateServiceUser(@Valid @RequestBody ServiceUserDTO serviceUserDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceUser : {}", serviceUserDTO);
        if (serviceUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (serviceUserDTO != null && serviceUserDTO.getClientId() != null && serviceUserDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        serviceUserDTO.setLastUpdatedDate(ZonedDateTime.now());
        ServiceUserDTO result = serviceUserServiceExt.save(serviceUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-users} : get all the serviceUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceUsers in body.
     */
    @GetMapping("/get-all-service-users-by-client-id")
    public ResponseEntity<List<ServiceUserDTO>> getAllServiceUsers(ServiceUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceUsers by criteria: {}", criteria);
        ServiceUserCriteria serviceUserCriteria = new ServiceUserCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		serviceUserCriteria.setClientId(longFilterForClientId);
        Page<ServiceUserDTO> page = serviceUserQueryService.findByCriteria(serviceUserCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/get-all-service-users-by-client-id-employee-id/{employeeId}")   
    public ResponseEntity< List<ServiceUserDTO>> getAllServiceUsersByEmployeeId(@PathVariable Long employeeId, Pageable pageable) {
        log.debug("REST request to get ServiceUsers : {}", employeeId);
        Long loggedInClientId = getClientIdFromLoggedInUser();
        ServiceUserCriteria serviceUserCriteria = new ServiceUserCriteria();
       
		
        LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(loggedInClientId);
		serviceUserCriteria.setClientId(longFilterForClientId);
		
		LongFilter longFilterForEmployeeId = new LongFilter();
		longFilterForEmployeeId.setEquals(employeeId);
//		serviceUserCriteria.setEmployeeId(longFilterForEmployeeId);
		
		
		 Page<ServiceUserDTO> listOfPages = serviceUserQueryService.findByCriteria(serviceUserCriteria,pageable);
		 List <ServiceUserDTO> listOfDTOs = listOfPages.getContent();
		 if (listOfDTOs != null && listOfDTOs.size() > 0) {
			 ServiceUserDTO serviceUserDTO =  listOfDTOs.get(0);
        	if (serviceUserDTO.getClientId() != null && serviceUserDTO.getClientId() != loggedInClientId) {
	        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
	         }
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(listOfDTOs));
    }

    /**
     * {@code GET  /service-users/count} : count all the serviceUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-users/count")
    public ResponseEntity<Long> countServiceUsers(ServiceUserCriteria criteria) {
        log.debug("REST request to count ServiceUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-users/:id} : get the "id" serviceUser.
     *
     * @param id the id of the serviceUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-service-user-by-client-id/{id}")
    public ResponseEntity<ServiceUserDTO> getServiceUser(@PathVariable Long id) {
        log.debug("REST request to get ServiceUser : {}", id);
        Optional<ServiceUserDTO> serviceUserDTO = serviceUserServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceUserDTO);
    }

    /**
     * {@code DELETE  /service-users/:id} : delete the "id" serviceUser.
     *
     * @param id the id of the serviceUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-service-user-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteServiceUser(@PathVariable Long id) {
        log.debug("REST request to delete ServiceUser : {}", id);
        serviceUserServiceExt.delete(id);
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
