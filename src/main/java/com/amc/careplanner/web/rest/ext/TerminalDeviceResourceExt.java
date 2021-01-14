package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.TerminalDeviceService;
import com.amc.careplanner.web.rest.TerminalDeviceResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.TerminalDeviceDTO;
import com.amc.careplanner.service.ext.TerminalDeviceServiceExt;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.dto.TerminalDeviceCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.TerminalDeviceQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.TerminalDevice}.
 */
@RestController
@RequestMapping("/api/v1")
public class TerminalDeviceResourceExt extends TerminalDeviceResource{

    private final Logger log = LoggerFactory.getLogger(TerminalDeviceResourceExt.class);

    private static final String ENTITY_NAME = "terminalDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerminalDeviceServiceExt terminalDeviceServiceExt;

    private final TerminalDeviceQueryService terminalDeviceQueryService;
    
    private final UserRepositoryExt userRepositoryExt;


    public TerminalDeviceResourceExt(TerminalDeviceServiceExt terminalDeviceServiceExt, TerminalDeviceQueryService terminalDeviceQueryService, UserRepositoryExt userRepositoryExt) {
        super(terminalDeviceServiceExt,terminalDeviceQueryService);
    	this.terminalDeviceServiceExt = terminalDeviceServiceExt;
        this.terminalDeviceQueryService = terminalDeviceQueryService;
        this.userRepositoryExt = userRepositoryExt;

    }

    /**
     * {@code POST  /terminal-devices} : Create a new terminalDevice.
     *
     * @param terminalDeviceDTO the terminalDeviceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terminalDeviceDTO, or with status {@code 400 (Bad Request)} if the terminalDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-terminal-device-by-client-id")
    public ResponseEntity<TerminalDeviceDTO> createTerminalDevice(@Valid @RequestBody TerminalDeviceDTO terminalDeviceDTO) throws URISyntaxException {
        log.debug("REST request to save TerminalDevice : {}", terminalDeviceDTO);
        if (terminalDeviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new terminalDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //terminalDeviceDTO.setDateCreated(ZonedDateTime.now());
        terminalDeviceDTO.setLastUpdatedDate(ZonedDateTime.now());
        terminalDeviceDTO.setClientId(getClientIdFromLoggedInUser());
        TerminalDeviceDTO result = terminalDeviceServiceExt.save(terminalDeviceDTO);
        return ResponseEntity.created(new URI("/api/terminal-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terminal-devices} : Updates an existing terminalDevice.
     *
     * @param terminalDeviceDTO the terminalDeviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terminalDeviceDTO,
     * or with status {@code 400 (Bad Request)} if the terminalDeviceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terminalDeviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-terminal-device-by-client-id")
    public ResponseEntity<TerminalDeviceDTO> updateTerminalDevice(@Valid @RequestBody TerminalDeviceDTO terminalDeviceDTO) throws URISyntaxException {
        log.debug("REST request to update TerminalDevice : {}", terminalDeviceDTO);
        if (terminalDeviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (terminalDeviceDTO != null && terminalDeviceDTO.getClientId() != null && terminalDeviceDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        terminalDeviceDTO.setLastUpdatedDate(ZonedDateTime.now());
        TerminalDeviceDTO result = terminalDeviceServiceExt.save(terminalDeviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terminalDeviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /terminal-devices} : get all the terminalDevices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terminalDevices in body.
     */
    @GetMapping("/get-all-terminal-devices-by-client-id")
    public ResponseEntity<List<TerminalDeviceDTO>> getAllTerminalDevices(TerminalDeviceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TerminalDevices by criteria: {}", criteria);
        TerminalDeviceCriteria terminalDeviceCriteria = new TerminalDeviceCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		terminalDeviceCriteria.setClientId(longFilterForClientId);
		Page<TerminalDeviceDTO> page = terminalDeviceQueryService.findByCriteria(terminalDeviceCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/get-all-terminal-devices-by-client-id-employee-id/{employeeId}")   
    public ResponseEntity< List<TerminalDeviceDTO>> getAllTerminalDevicesByEmployeeId(@PathVariable Long employeeId, Pageable pageable) {
        log.debug("REST request to get TerminalDevices : {}", employeeId);
        Long loggedInClientId = getClientIdFromLoggedInUser();
        TerminalDeviceCriteria terminalDeviceCriteria = new TerminalDeviceCriteria();
       
		
        LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(loggedInClientId);
		terminalDeviceCriteria.setClientId(longFilterForClientId);
		
		LongFilter longFilterForEmployeeId = new LongFilter();
		longFilterForEmployeeId.setEquals(employeeId);
		terminalDeviceCriteria.setEmployeeId(longFilterForEmployeeId);
		
		
		 Page<TerminalDeviceDTO> listOfPages = terminalDeviceQueryService.findByCriteria(terminalDeviceCriteria,pageable);
		 List <TerminalDeviceDTO> listOfDTOs = listOfPages.getContent();
		 if (listOfDTOs != null && listOfDTOs.size() > 0) {
			 TerminalDeviceDTO terminalDeviceDTO =  listOfDTOs.get(0);
        	if (terminalDeviceDTO.getClientId() != null && terminalDeviceDTO.getClientId() != loggedInClientId) {
	        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
	         }
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(listOfDTOs));
    }

    /**
     * {@code GET  /terminal-devices/count} : count all the terminalDevices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/terminal-devices/count")
    public ResponseEntity<Long> countTerminalDevices(TerminalDeviceCriteria criteria) {
        log.debug("REST request to count TerminalDevices by criteria: {}", criteria);
        return ResponseEntity.ok().body(terminalDeviceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /terminal-devices/:id} : get the "id" terminalDevice.
     *
     * @param id the id of the terminalDeviceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terminalDeviceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-terminal-device-by-client-id/{id}")
    public ResponseEntity<TerminalDeviceDTO> getTerminalDevice(@PathVariable Long id) {
        log.debug("REST request to get TerminalDevice : {}", id);
        Optional<TerminalDeviceDTO> terminalDeviceDTO = terminalDeviceServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(terminalDeviceDTO);
    }

    /**
     * {@code DELETE  /terminal-devices/:id} : delete the "id" terminalDevice.
     *
     * @param id the id of the terminalDeviceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-terminal-device-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteTerminalDevice(@PathVariable Long id) {
        log.debug("REST request to delete TerminalDevice : {}", id);
        terminalDeviceServiceExt.delete(id);
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
