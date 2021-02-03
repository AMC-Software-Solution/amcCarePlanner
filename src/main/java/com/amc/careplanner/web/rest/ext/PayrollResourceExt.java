package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.PayrollService;
import com.amc.careplanner.web.rest.PayrollResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.PayrollDTO;
import com.amc.careplanner.service.ext.PayrollServiceExt;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.PayrollCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.PayrollQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Payroll}.
 */
@RestController
@RequestMapping("/api/v1")
public class PayrollResourceExt extends PayrollResource{

    private final Logger log = LoggerFactory.getLogger(PayrollResourceExt.class);

    private static final String ENTITY_NAME = "payroll";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayrollServiceExt payrollServiceExt;

    private final PayrollQueryService payrollQueryService;
    
    private final UserRepositoryExt userRepositoryExt;
    
    private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;

    public PayrollResourceExt(PayrollServiceExt payrollServiceExt, PayrollQueryService payrollQueryService, UserRepositoryExt userRepositoryExt, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
        super(payrollServiceExt,payrollQueryService);
    	this.payrollServiceExt = payrollServiceExt;
        this.payrollQueryService = payrollQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
        
    }

    /**
     * {@code POST  /payrolls} : Create a new payroll.
     *
     * @param payrollDTO the payrollDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payrollDTO, or with status {@code 400 (Bad Request)} if the payroll has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-payroll-by-client-id")
    public ResponseEntity<PayrollDTO> createPayroll(@Valid @RequestBody PayrollDTO payrollDTO) throws URISyntaxException {
        log.debug("REST request to save Payroll : {}", payrollDTO);
        if (payrollDTO.getId() != null) {
            throw new BadRequestAlertException("A new payroll cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      payrollDTO.setDateCreated(ZonedDateTime.now());
        payrollDTO.setLastUpdatedDate(ZonedDateTime.now());
        payrollDTO.setClientId(getClientIdFromLoggedInUser());
        PayrollDTO result = payrollServiceExt.save(payrollDTO);
        
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "createPayroll", "/api/v1/create-payroll-by-client-id",
        		result.getPaymentDate() + " has just been created", "Payroll", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.created(new URI("/api/payrolls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payrolls} : Updates an existing payroll.
     *
     * @param payrollDTO the payrollDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollDTO,
     * or with status {@code 400 (Bad Request)} if the payrollDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payrollDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-payroll-by-client-id")
    public ResponseEntity<PayrollDTO> updatePayroll(@Valid @RequestBody PayrollDTO payrollDTO) throws URISyntaxException {
        log.debug("REST request to update Payroll : {}", payrollDTO);
        if (payrollDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (payrollDTO != null && payrollDTO.getClientId() != null && payrollDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        payrollDTO.setLastUpdatedDate(ZonedDateTime.now());
        PayrollDTO result = payrollServiceExt.save(payrollDTO);
        
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "updatePayroll", "/api/v1/update-payroll-by-client-id",
        		result.getPaymentDate() + " has just been created", "Payroll", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payrolls} : get all the payrolls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payrolls in body.
     */
    @GetMapping("/get-all-payrolls-by-client-id")
    public ResponseEntity<List<PayrollDTO>> getAllPayrolls(PayrollCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Payrolls by criteria: {}", criteria);
        PayrollCriteria payrollCriteria = new PayrollCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		payrollCriteria.setClientId(longFilterForClientId);
        Page<PayrollDTO> page = payrollQueryService.findByCriteria(payrollCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/get-all-payrolls-by-client-id-employee-id/{employeeId}")   
    public ResponseEntity< List<PayrollDTO>> getAllPayrollsByEmployeeId(@PathVariable Long employeeId, Pageable pageable) {
        log.debug("REST request to get Payrolls : {}", employeeId);
        Long loggedInClientId = getClientIdFromLoggedInUser();
        PayrollCriteria payrollCriteria = new PayrollCriteria();


        LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(loggedInClientId);
		payrollCriteria.setClientId(longFilterForClientId);

		LongFilter longFilterForEmployeeId = new LongFilter();
		longFilterForEmployeeId.setEquals(employeeId);
		payrollCriteria.setEmployeeId(longFilterForEmployeeId);


		 Page< PayrollDTO> listOfPages = payrollQueryService.findByCriteria(payrollCriteria,pageable);
		 List < PayrollDTO> listOfDTOs = listOfPages.getContent();
		 if (listOfDTOs != null && listOfDTOs.size() > 0) {
			 PayrollDTO payrollDTO =  listOfDTOs.get(0);
        	if (payrollDTO.getClientId() != null && payrollDTO.getClientId() != loggedInClientId) {
	        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
	         }
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(listOfDTOs));
    }


    /**
     * {@code GET  /payrolls/count} : count all the payrolls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payrolls/count")
    public ResponseEntity<Long> countPayrolls(PayrollCriteria criteria) {
        log.debug("REST request to count Payrolls by criteria: {}", criteria);
        return ResponseEntity.ok().body(payrollQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payrolls/:id} : get the "id" payroll.
     *
     * @param id the id of the payrollDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payrollDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-payroll-by-client-id/{id}")
    public ResponseEntity<PayrollDTO> getPayroll(@PathVariable Long id) {
        log.debug("REST request to get Payroll : {}", id);
        Optional<PayrollDTO> payrollDTO = payrollServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(payrollDTO);
    }

    /**
     * {@code DELETE  /payrolls/:id} : delete the "id" payroll.
     *
     * @param id the id of the payrollDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-payroll-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {
        log.debug("REST request to delete Payroll : {}", id);
        payrollServiceExt.delete(id);
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
