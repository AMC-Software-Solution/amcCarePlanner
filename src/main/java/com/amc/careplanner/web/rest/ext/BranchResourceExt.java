package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.BranchService;
import com.amc.careplanner.web.rest.BranchResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.BranchDTO;
import com.amc.careplanner.service.dto.EmployeeCriteria;
import com.amc.careplanner.service.dto.EmployeeDTO;
import com.amc.careplanner.service.ext.BranchServiceExt;
import com.amc.careplanner.service.dto.BranchCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.BranchQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Branch}.
 */
@RestController
@RequestMapping("/api/v1")
public class BranchResourceExt extends BranchResource {

    private final Logger log = LoggerFactory.getLogger(BranchResourceExt.class);

    private static final String ENTITY_NAME = "branch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BranchServiceExt branchServiceExt;

    private final BranchQueryService branchQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public BranchResourceExt(BranchServiceExt branchServiceExt, BranchQueryService branchQueryService,UserRepositoryExt userRepositoryExt) {
    	super(branchServiceExt,branchQueryService);
        this.branchServiceExt = branchServiceExt;
        this.branchQueryService = branchQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /branches} : Create a new branch.
     *
     * @param branchDTO the branchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new branchDTO, or with status {@code 400 (Bad Request)} if the branch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create_branch_by_client_id")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<BranchDTO> createBranch(@Valid @RequestBody BranchDTO branchDTO) throws URISyntaxException {
        log.debug("REST request to save Branch : {}", branchDTO);
        if (branchDTO.getId() != null) {
            throw new BadRequestAlertException("A new branch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        branchDTO.setLastUpdatedDate(ZonedDateTime.now());
        BranchDTO result = branchServiceExt.save(branchDTO);
        return ResponseEntity.created(new URI("/v1/api/get_branches_by_client_id/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /branches} : Updates an existing branch.
     *
     * @param branchDTO the branchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated branchDTO,
     * or with status {@code 400 (Bad Request)} if the branchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the branchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update_branch_by_client_id")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<BranchDTO> updateBranch(@Valid @RequestBody BranchDTO branchDTO) throws URISyntaxException {
        log.debug("REST request to update Branch : {}", branchDTO);
        if (branchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BranchDTO result = branchServiceExt.save(branchDTO);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, branchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /branches} : get all the branches.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of branches in body.
     */
    @GetMapping("/get_branches_by_client_id")
    public ResponseEntity<List<BranchDTO>> getAllBranches(BranchCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Branches by criteria: {}", criteria);
        BranchCriteria branchCriteria = new BranchCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		branchCriteria.setClientId(longFilterForClientId);
        Page<BranchDTO> page = branchQueryService.findByCriteria(branchCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /branches/count} : count all the branches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/branches/count")
    public ResponseEntity<Long> countBranches(BranchCriteria criteria) {
        log.debug("REST request to count Branches by criteria: {}", criteria);
        return ResponseEntity.ok().body(branchQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /branches/:id} : get the "id" branch.
     *
     * @param id the id of the branchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the branchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get_branch_by_client_id/{id}")
    public ResponseEntity<BranchDTO> getBranch(@PathVariable Long id) {
        log.debug("REST request to get Branch : {}", id);
        BranchCriteria branchCriteria = new BranchCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		LongFilter longFilterForId = new LongFilter();
		longFilterForId.setEquals(id);
		branchCriteria.setClientId(longFilterForClientId);
		 List<BranchDTO> listOfBranches = branchQueryService.findByCriteria(branchCriteria);
		 BranchDTO branchDTO =listOfBranches.get(0);
        if (branchDTO != null && branchDTO.getClientId() != null && branchDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(branchDTO));
    }

    /**
     * {@code DELETE  /branches/:id} : delete the "id" branch.
     *
     * @param id the id of the branchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/branches/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        log.debug("REST request to delete Branch : {}", id);
        branchServiceExt.delete(id);
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
