package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.TaskService;
import com.amc.careplanner.web.rest.TaskResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.ext.TaskServiceExt;
import com.amc.careplanner.service.dto.BranchCriteria;
import com.amc.careplanner.service.dto.BranchDTO;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.TaskQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.Task}.
 */
@RestController
@RequestMapping("/api/v1")
public class TaskResourceExt extends TaskResource{

    private final Logger log = LoggerFactory.getLogger(TaskResourceExt.class);

    private static final String ENTITY_NAME = "task";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskServiceExt taskServiceExt;

    private final TaskQueryService taskQueryService;

    private final UserRepositoryExt userRepositoryExt;

    
    public TaskResourceExt(TaskServiceExt taskServiceExt, TaskQueryService taskQueryService, UserRepositoryExt userRepositoryExt) {
        super(taskServiceExt,taskQueryService);
    	this.taskServiceExt = taskServiceExt;
        this.taskQueryService = taskQueryService;
        this.userRepositoryExt = userRepositoryExt;

    }

    /**
     * {@code POST  /tasks} : Create a new task.
     *
     * @param taskDTO the taskDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskDTO, or with status {@code 400 (Bad Request)} if the task has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create_task_by_client_id")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {
        log.debug("REST request to save Task : {}", taskDTO);
        if (taskDTO.getId() != null) {
            throw new BadRequestAlertException("A new task cannot already have an ID", ENTITY_NAME, "idexists");
        }
        taskDTO.setDateCreated(ZonedDateTime.now());
        taskDTO.setLastUpdatedDate(ZonedDateTime.now());
        taskDTO.setClientId(getClientIdFromLoggedInUser());
        TaskDTO result = taskServiceExt.save(taskDTO);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks} : Updates an existing task.
     *
     * @param taskDTO the taskDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskDTO,
     * or with status {@code 400 (Bad Request)} if the taskDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update_task_by_client_id")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {
        log.debug("REST request to update Task : {}", taskDTO);
        if (taskDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        if (taskDTO != null && taskDTO.getClientId() != null && taskDTO.getClientId() != getClientIdFromLoggedInUser()) {
      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
      }
        taskDTO.setLastUpdatedDate(ZonedDateTime.now());
        TaskDTO result = taskServiceExt.save(taskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tasks} : get all the tasks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasks in body.
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasks(TaskCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tasks by criteria: {}", criteria);
        TaskCriteria taskCriteria = new TaskCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		taskCriteria.setClientId(longFilterForClientId);
        Page<TaskDTO> page = taskQueryService.findByCriteria(taskCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tasks/count} : count all the tasks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tasks/count")
    public ResponseEntity<Long> countTasks(TaskCriteria criteria) {
        log.debug("REST request to count Tasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tasks/:id} : get the "id" task.
     *
     * @param id the id of the taskDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        log.debug("REST request to get Task : {}", id);
        Optional<TaskDTO> taskDTO = taskServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskDTO);
    }

    /**
     * {@code DELETE  /tasks/:id} : delete the "id" task.
     *
     * @param id the id of the taskDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tasks/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.debug("REST request to delete Task : {}", id);
        taskServiceExt.delete(id);
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
