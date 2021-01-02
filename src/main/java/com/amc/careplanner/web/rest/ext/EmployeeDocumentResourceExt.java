package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.EmployeeDocumentService;
import com.amc.careplanner.web.rest.EmployeeDocumentResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EmployeeDocumentDTO;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.ext.EmployeeDocumentServiceExt;
import com.amc.careplanner.service.dto.EmployeeDocumentCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.EmployeeDocumentQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.EmployeeDocument}.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeDocumentResourceExt extends EmployeeDocumentResource{

    private final Logger log = LoggerFactory.getLogger(EmployeeDocumentResourceExt.class);

    private static final String ENTITY_NAME = "employeeDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeDocumentServiceExt employeeDocumentServiceExt;

    private final EmployeeDocumentQueryService employeeDocumentQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public EmployeeDocumentResourceExt(EmployeeDocumentServiceExt employeeDocumentServiceExt, EmployeeDocumentQueryService employeeDocumentQueryService, UserRepositoryExt userRepositoryExt) {
    	super(employeeDocumentServiceExt,employeeDocumentQueryService);
        this.employeeDocumentServiceExt = employeeDocumentServiceExt;
        this.employeeDocumentQueryService = employeeDocumentQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /employee-documents} : Create a new employeeDocument.
     *
     * @param employeeDocumentDTO the employeeDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDocumentDTO, or with status {@code 400 (Bad Request)} if the employeeDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-employee-document-by-client-id")
    public ResponseEntity<EmployeeDocumentDTO> createEmployeeDocument(@Valid @RequestBody EmployeeDocumentDTO employeeDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeDocument : {}", employeeDocumentDTO);
        if (employeeDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //employeeDocumentDTO.setDateCreated(ZonedDateTime.now());
        employeeDocumentDTO.setLastUpdatedDate(ZonedDateTime.now());
        employeeDocumentDTO.setClientId(getClientIdFromLoggedInUser());
        EmployeeDocumentDTO result = employeeDocumentServiceExt.save(employeeDocumentDTO);
        return ResponseEntity.created(new URI("/api/employee-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-documents} : Updates an existing employeeDocument.
     *
     * @param employeeDocumentDTO the employeeDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the employeeDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-employee-document-by-client-id")
    public ResponseEntity<EmployeeDocumentDTO> updateEmployeeDocument(@Valid @RequestBody EmployeeDocumentDTO employeeDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeDocument : {}", employeeDocumentDTO);
        if (employeeDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (employeeDocumentDTO != null && employeeDocumentDTO.getClientId() != null && employeeDocumentDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
   }
        employeeDocumentDTO.setLastUpdatedDate(ZonedDateTime.now());
        EmployeeDocumentDTO result = employeeDocumentServiceExt.save(employeeDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-documents} : get all the employeeDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeDocuments in body.
     */
    @GetMapping("/employee-documents")
    public ResponseEntity<List<EmployeeDocumentDTO>> getAllEmployeeDocuments(EmployeeDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmployeeDocuments by criteria: {}", criteria);
        EmployeeDocumentCriteria employeeDocumentCriteria = new EmployeeDocumentCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		employeeDocumentCriteria.setClientId(longFilterForClientId);
        Page<EmployeeDocumentDTO> page = employeeDocumentQueryService.findByCriteria(employeeDocumentCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-documents/count} : count all the employeeDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-documents/count")
    public ResponseEntity<Long> countEmployeeDocuments(EmployeeDocumentCriteria criteria) {
        log.debug("REST request to count EmployeeDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-documents/:id} : get the "id" employeeDocument.
     *
     * @param id the id of the employeeDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-documents/{id}")
    public ResponseEntity<EmployeeDocumentDTO> getEmployeeDocument(@PathVariable Long id) {
        log.debug("REST request to get EmployeeDocument : {}", id);
        Optional<EmployeeDocumentDTO> employeeDocumentDTO = employeeDocumentServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDocumentDTO);
    }

    /**
     * {@code DELETE  /employee-documents/:id} : delete the "id" employeeDocument.
     *
     * @param id the id of the employeeDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-employee-documents-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteEmployeeDocument(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeDocument : {}", id);
        employeeDocumentServiceExt.delete(id);
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
