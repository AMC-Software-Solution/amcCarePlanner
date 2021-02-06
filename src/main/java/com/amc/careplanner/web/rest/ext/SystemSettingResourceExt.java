package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.SystemSettingService;
import com.amc.careplanner.web.rest.SystemSettingResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.SystemSettingDTO;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.service.ext.SystemSettingServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.dto.SystemSettingCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.SystemSettingQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.SystemSetting}.
 */
@RestController
@RequestMapping("/api/v1")
public class SystemSettingResourceExt extends SystemSettingResource{

    private final Logger log = LoggerFactory.getLogger(SystemSettingResourceExt.class);

    private static final String ENTITY_NAME = "systemSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemSettingServiceExt systemSettingServiceExt;

    private final SystemSettingQueryService systemSettingQueryService;
    
    private final UserRepositoryExt userRepositoryExt;
    
	private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;


    public SystemSettingResourceExt(SystemSettingServiceExt systemSettingServiceExt, SystemSettingQueryService systemSettingQueryService, UserRepositoryExt userRepositoryExt, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
        super(systemSettingServiceExt,systemSettingQueryService);
    	this.systemSettingServiceExt = systemSettingServiceExt;
        this.systemSettingQueryService = systemSettingQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
    }

    /**
     * {@code POST  /system-settings} : Create a new systemSetting.
     *
     * @param systemSettingDTO the systemSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemSettingDTO, or with status {@code 400 (Bad Request)} if the systemSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-system-setting-by-client-id")
    public ResponseEntity<SystemSettingDTO> createSystemSetting(@Valid @RequestBody SystemSettingDTO systemSettingDTO) throws URISyntaxException {
        log.debug("REST request to save SystemSetting : {}", systemSettingDTO);
        if (systemSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      systemSettingDTO.setDateCreated(ZonedDateTime.now());
//        systemSettingDTO.setLastUpdatedDate(ZonedDateTime.now());
        systemSettingDTO.setClientId(getClientIdFromLoggedInUser());
        SystemSettingDTO result = systemSettingServiceExt.save(systemSettingDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "createSystemSetting", "/api/v1/create-system-setting-by-client-id",
        		result.getFieldName() + " has just been created", " SystemSetting", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.created(new URI("/api/system-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-settings} : Updates an existing systemSetting.
     *
     * @param systemSettingDTO the systemSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemSettingDTO,
     * or with status {@code 400 (Bad Request)} if the systemSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-system-setting-by-client-id")
    public ResponseEntity<SystemSettingDTO> updateSystemSetting(@Valid @RequestBody SystemSettingDTO systemSettingDTO) throws URISyntaxException {
        log.debug("REST request to update SystemSetting : {}", systemSettingDTO);
        if (systemSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (systemSettingDTO != null && systemSettingDTO.getClientId() != null && systemSettingDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
//        systemSettingDTO.setLastUpdatedDate(ZonedDateTime.now());
        SystemSettingDTO result = systemSettingServiceExt.save(systemSettingDTO);
        String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "updateSystemSetting", "/api/v1/update-system-setting-by-client-id",
        		result.getFieldName() + " has just been updated", " SystemSetting", result.getId(), loggedInAdminUser.getId(),
        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /system-settings} : get all the systemSettings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemSettings in body.
     */
    @GetMapping("/get-all-system-settings-by-client-id")
    public ResponseEntity<List<SystemSettingDTO>> getAllSystemSettings(SystemSettingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SystemSettings by criteria: {}", criteria);
        SystemSettingCriteria systemSettingCriteria = new SystemSettingCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		systemSettingCriteria.setClientId(longFilterForClientId);
        Page<SystemSettingDTO> page = systemSettingQueryService.findByCriteria(systemSettingCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-settings/count} : count all the systemSettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/system-settings/count")
    public ResponseEntity<Long> countSystemSettings(SystemSettingCriteria criteria) {
        log.debug("REST request to count SystemSettings by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemSettingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /system-settings/:id} : get the "id" systemSetting.
     *
     * @param id the id of the systemSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-system-setting-by-client-id/{id}")
    public ResponseEntity<SystemSettingDTO> getSystemSetting(@PathVariable Long id) {
        log.debug("REST request to get SystemSetting : {}", id);
        Optional<SystemSettingDTO> systemSettingDTO = systemSettingServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemSettingDTO);
    }

    /**
     * {@code DELETE  /system-settings/:id} : delete the "id" systemSetting.
     *
     * @param id the id of the systemSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-system-setting-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteSystemSetting(@PathVariable Long id) {
        log.debug("REST request to delete SystemSetting : {}", id);
        systemSettingServiceExt.delete(id);
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
