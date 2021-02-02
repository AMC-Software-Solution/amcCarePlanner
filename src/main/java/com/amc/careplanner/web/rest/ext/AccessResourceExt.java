package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.AccessService;
import com.amc.careplanner.web.rest.AccessResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.AccessDTO;
import com.amc.careplanner.service.ext.AccessServiceExt;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.service.dto.AccessCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.AccessQueryService;

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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.Access}.
 */
@RestController
@RequestMapping("/api/v1")
public class AccessResourceExt extends AccessResource {

    private final Logger log = LoggerFactory.getLogger(AccessResourceExt.class);

    private static final String ENTITY_NAME = "access";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccessServiceExt accessServiceExt;

    private final AccessQueryService accessQueryService;
    
    private final UserRepositoryExt userRepositoryExt;
    
	private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;


    public AccessResourceExt(AccessServiceExt accessServiceExt, AccessQueryService accessQueryService, UserRepositoryExt userRepositoryExt, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
        super(accessServiceExt,accessQueryService);
        this.accessServiceExt = accessServiceExt;
        this.accessQueryService = accessQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
    }
    
    @PostMapping("/create_access_by_client_id")
    public ResponseEntity<AccessDTO> createAccess(@Valid @RequestBody AccessDTO accessDTO) throws URISyntaxException {
        log.debug("REST request to save Access : {}", accessDTO);
        if (accessDTO.getId() != null) {
            throw new BadRequestAlertException("A new access cannot already have an ID", ENTITY_NAME, "idexists");
        }
//        accessDTO.setDateCreated(ZonedDateTime.now());
        accessDTO.setLastUpdatedDate(ZonedDateTime.now());
        accessDTO.setClientId(getClientIdFromLoggedInUser());
        AccessDTO result = accessServiceExt.save(accessDTO);
        return ResponseEntity.created(new URI("/api/accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
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
