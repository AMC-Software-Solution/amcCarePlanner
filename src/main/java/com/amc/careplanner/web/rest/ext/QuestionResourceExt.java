package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.QuestionService;
import com.amc.careplanner.web.rest.QuestionResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.QuestionDTO;
import com.amc.careplanner.service.ext.QuestionServiceExt;
import com.amc.careplanner.service.dto.QuestionCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.QuestionQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Question}.
 */
@RestController
@RequestMapping("/api/v1")
public class QuestionResourceExt extends  QuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionResourceExt.class);

    private static final String ENTITY_NAME = "question";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionServiceExt questionServiceExt;

    private final QuestionQueryService questionQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public QuestionResourceExt(QuestionServiceExt questionServiceExt, QuestionQueryService questionQueryService, UserRepositoryExt userRepositoryExt) {
        super(questionServiceExt,questionQueryService);
    	this.questionServiceExt = questionServiceExt;
        this.questionQueryService = questionQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }
    
    @PostMapping("/create-question-by-client-id")
    public ResponseEntity<QuestionDTO> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) throws URISyntaxException {
        log.debug("REST request to save Question in Ext : {}", questionDTO);
        if (questionDTO.getId() != null) {
            throw new BadRequestAlertException("A new question cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      questionDTO.setDateCreated(ZonedDateTime.now());
        questionDTO.setLastUpdatedDate(ZonedDateTime.now());
        questionDTO.setClientId(getClientIdFromLoggedInUser());
        QuestionDTO result = questionServiceExt.save(questionDTO);
        return ResponseEntity.created(new URI("/api/questions/" + result.getId()))
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
