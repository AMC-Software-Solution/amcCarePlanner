package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.AnswerService;
import com.amc.careplanner.web.rest.AnswerResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.AnswerDTO;
import com.amc.careplanner.service.dto.CarerClientRelationCriteria;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;
import com.amc.careplanner.service.dto.EmployeeDocumentCriteria;
import com.amc.careplanner.service.dto.EmployeeDocumentDTO;
import com.amc.careplanner.service.ext.AnswerServiceExt;
import com.amc.careplanner.service.dto.AnswerCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.AnswerQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Answer}.
 */
@RestController
@RequestMapping("/api/v1")
public class AnswerResourceExt extends AnswerResource{

    private final Logger log = LoggerFactory.getLogger(AnswerResourceExt.class);

    private static final String ENTITY_NAME = "answer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerServiceExt answerServiceExt;

    private final AnswerQueryService answerQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public AnswerResourceExt(AnswerServiceExt answerServiceExt, AnswerQueryService answerQueryService, UserRepositoryExt userRepositoryExt) {
    	super(answerServiceExt,answerQueryService);
        this.answerServiceExt = answerServiceExt;
        this.answerQueryService = answerQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /answers} : Create a new answer.
     *
     * @param answerDTO the answerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerDTO, or with status {@code 400 (Bad Request)} if the answer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-answer-by-client-id")
    public ResponseEntity<AnswerDTO> createAnswer(@Valid @RequestBody AnswerDTO answerDTO) throws URISyntaxException {
        log.debug("REST request to save Answer : {}", answerDTO);
        if (answerDTO.getId() != null) {
            throw new BadRequestAlertException("A new answer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
      //answerDTO.setDateCreated(ZonedDateTime.now());
        answerDTO.setLastUpdatedDate(ZonedDateTime.now());
        answerDTO.setClientId(getClientIdFromLoggedInUser());
        AnswerDTO result = answerServiceExt.save(answerDTO);
        return ResponseEntity.created(new URI("/api/answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answers} : Updates an existing answer.
     *
     * @param answerDTO the answerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerDTO,
     * or with status {@code 400 (Bad Request)} if the answerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-answer-by-client-id")
    public ResponseEntity<AnswerDTO> updateAnswer(@Valid @RequestBody AnswerDTO answerDTO) throws URISyntaxException {
        log.debug("REST request to update Answer : {}", answerDTO);
        if (answerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (answerDTO != null && answerDTO.getClientId() != null && answerDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
 }
        answerDTO.setLastUpdatedDate(ZonedDateTime.now());
        AnswerDTO result = answerServiceExt.save(answerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /answers} : get all the answers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answers in body.
     */
    @GetMapping("/get-all-answers-by-client-id")
    public ResponseEntity<List<AnswerDTO>> getAllAnswers(AnswerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Answers by criteria: {}", criteria);
        AnswerCriteria answerCriteria = new AnswerCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		answerCriteria.setClientId(longFilterForClientId);
        Page<AnswerDTO> page = answerQueryService.findByCriteria(answerCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/get-all-answers-by-client-id-employee-id/{employeeId}")   
    public ResponseEntity< List<AnswerDTO>> getAllanswersByEmployeeId(@PathVariable Long employeeId, Pageable pageable) {
        log.debug("REST request to get Answers : {}", employeeId);
        Long loggedInClientId = getClientIdFromLoggedInUser();
        AnswerCriteria answerCriteria = new AnswerCriteria();
       
		
        LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(loggedInClientId);
		answerCriteria.setClientId(longFilterForClientId);
		
		LongFilter longFilterForEmployeeId = new LongFilter();
		longFilterForEmployeeId.setEquals(employeeId);
    	//answerCriteria.setEmployeeId(longFilterForEmployeeId);
		
		
		 Page<AnswerDTO> listOfPages = answerQueryService.findByCriteria(answerCriteria,pageable);
		 List <AnswerDTO> listOfDTOs = listOfPages.getContent();
		 if (listOfDTOs != null && listOfDTOs.size() > 0) {
			 AnswerDTO answerDTO =  listOfDTOs.get(0);
        	if (answerDTO.getClientId() != null && answerDTO.getClientId() != loggedInClientId) {
	        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
	         }
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(listOfDTOs));
    }

    /**
     * {@code GET  /answers/count} : count all the answers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/answers/count")
    public ResponseEntity<Long> countAnswers(AnswerCriteria criteria) {
        log.debug("REST request to count Answers by criteria: {}", criteria);
        return ResponseEntity.ok().body(answerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /answers/:id} : get the "id" answer.
     *
     * @param id the id of the answerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-answer-by-client-id/{id}")
    public ResponseEntity<AnswerDTO> getAnswer(@PathVariable Long id) {
        log.debug("REST request to get Answer : {}", id);
        Optional<AnswerDTO> answerDTO = answerServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(answerDTO);
    }

    /**
     * {@code DELETE  /answers/:id} : delete the "id" answer.
     *
     * @param id the id of the answerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-answer-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        log.debug("REST request to delete Answer : {}", id);
        answerServiceExt.delete(id);
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
