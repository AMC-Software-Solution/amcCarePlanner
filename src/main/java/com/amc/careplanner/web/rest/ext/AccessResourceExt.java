package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.AccessService;
import com.amc.careplanner.web.rest.AccessResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.AccessDTO;
import com.amc.careplanner.service.ext.AccessServiceExt;
import com.amc.careplanner.service.dto.AccessCriteria;
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

    public AccessResourceExt(AccessServiceExt accessServiceExt, AccessQueryService accessQueryService) {
        super(accessServiceExt,accessQueryService);
        this.accessServiceExt = accessServiceExt;
        this.accessQueryService = accessQueryService;
    }
    
    @PostMapping("/accesses")
    public ResponseEntity<AccessDTO> createAccess(@Valid @RequestBody AccessDTO accessDTO) throws URISyntaxException {
        log.debug("REST request to save Access : {}", accessDTO);
        if (accessDTO.getId() != null) {
            throw new BadRequestAlertException("A new access cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccessDTO result = accessServiceExt.save(accessDTO);
        return ResponseEntity.created(new URI("/api/accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
}
