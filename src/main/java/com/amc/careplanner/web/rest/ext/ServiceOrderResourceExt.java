package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.ServiceOrderService;
import com.amc.careplanner.web.rest.ServiceOrderResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ServiceOrderDTO;
import com.amc.careplanner.service.ext.ServiceOrderServiceExt;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.ServiceOrderCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.ServiceOrderQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.ServiceOrder}.
 */
@RestController
@RequestMapping("/api/v1")
public class ServiceOrderResourceExt extends ServiceOrderResource{

    private final Logger log = LoggerFactory.getLogger(ServiceOrderResourceExt.class);

    private static final String ENTITY_NAME = "serviceOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOrderServiceExt serviceOrderServiceExt;

    private final ServiceOrderQueryService serviceOrderQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public ServiceOrderResourceExt(ServiceOrderServiceExt serviceOrderServiceExt, ServiceOrderQueryService serviceOrderQueryService, UserRepositoryExt userRepositoryExt) {
        super(serviceOrderServiceExt,serviceOrderQueryService);
    	this.serviceOrderServiceExt = serviceOrderServiceExt;
        this.serviceOrderQueryService = serviceOrderQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /service-orders} : Create a new serviceOrder.
     *
     * @param serviceOrderDTO the serviceOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderDTO, or with status {@code 400 (Bad Request)} if the serviceOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-service-order-by-client-id")
    public ResponseEntity<ServiceOrderDTO> createServiceOrder(@Valid @RequestBody ServiceOrderDTO serviceOrderDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceOrder : {}", serviceOrderDTO);
        if (serviceOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      serviceOrderDTO.setDateCreated(ZonedDateTime.now());
        serviceOrderDTO.setLastUpdatedDate(ZonedDateTime.now());
        serviceOrderDTO.setClientId(getClientIdFromLoggedInUser());
        ServiceOrderDTO result = serviceOrderServiceExt.save(serviceOrderDTO);
        return ResponseEntity.created(new URI("/api/service-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-orders} : Updates an existing serviceOrder.
     *
     * @param serviceOrderDTO the serviceOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-service-order-by-client-id")
    public ResponseEntity<ServiceOrderDTO> updateServiceOrder(@Valid @RequestBody ServiceOrderDTO serviceOrderDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceOrder : {}", serviceOrderDTO);
        if (serviceOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (serviceOrderDTO != null && serviceOrderDTO.getClientId() != null && serviceOrderDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        serviceOrderDTO.setLastUpdatedDate(ZonedDateTime.now());
        ServiceOrderDTO result = serviceOrderServiceExt.save(serviceOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-orders} : get all the serviceOrders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrders in body.
     */
    @GetMapping("/get-all-service-orders-by-client-id")
    public ResponseEntity<List<ServiceOrderDTO>> getAllServiceOrders(ServiceOrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceOrders by criteria: {}", criteria);
        ServiceOrderCriteria serviceOrderCriteria = new ServiceOrderCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		serviceOrderCriteria.setClientId(longFilterForClientId);
        Page<ServiceOrderDTO> page = serviceOrderQueryService.findByCriteria(serviceOrderCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-orders/count} : count all the serviceOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-orders/count")
    public ResponseEntity<Long> countServiceOrders(ServiceOrderCriteria criteria) {
        log.debug("REST request to count ServiceOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceOrderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-orders/:id} : get the "id" serviceOrder.
     *
     * @param id the id of the serviceOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-service-order-by-client-id/{id}")
    public ResponseEntity<ServiceOrderDTO> getServiceOrder(@PathVariable Long id) {
        log.debug("REST request to get ServiceOrder : {}", id);
        Optional<ServiceOrderDTO> serviceOrderDTO = serviceOrderServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderDTO);
    }

    /**
     * {@code DELETE  /service-orders/:id} : delete the "id" serviceOrder.
     *
     * @param id the id of the serviceOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-service-order-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteServiceOrder(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOrder : {}", id);
        serviceOrderServiceExt.delete(id);
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
