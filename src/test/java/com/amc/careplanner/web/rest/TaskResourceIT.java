package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Task;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.domain.ServiceOrder;
import com.amc.careplanner.repository.TaskRepository;
import com.amc.careplanner.service.TaskService;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.mapper.TaskMapper;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.TaskQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.amc.careplanner.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amc.careplanner.domain.enumeration.TaskStatus;
/**
 * Integration tests for the {@link TaskResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskResourceIT {

    private static final String DEFAULT_TASK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TASK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_TASK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_TASK = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_TASK = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_END_TIME = "BBBBBBBBBB";

    private static final TaskStatus DEFAULT_STATUS = TaskStatus.ASSSIGNED;
    private static final TaskStatus UPDATED_STATUS = TaskStatus.INPROGRESS;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskQueryService taskQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskMockMvc;

    private Task task;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .taskName(DEFAULT_TASK_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dateOfTask(DEFAULT_DATE_OF_TASK)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .status(DEFAULT_STATUS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return task;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity(EntityManager em) {
        Task task = new Task()
            .taskName(UPDATED_TASK_NAME)
            .description(UPDATED_DESCRIPTION)
            .dateOfTask(UPDATED_DATE_OF_TASK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return task;
    }

    @BeforeEach
    public void initTest() {
        task = createEntity(em);
    }

    @Test
    @Transactional
    public void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();
        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTaskName()).isEqualTo(DEFAULT_TASK_NAME);
        assertThat(testTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTask.getDateOfTask()).isEqualTo(DEFAULT_DATE_OF_TASK);
        assertThat(testTask.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTask.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTask.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTask.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTask.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testTask.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task with an existing ID
        task.setId(1L);
        TaskDTO taskDTO = taskMapper.toDto(task);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTaskNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setTaskName(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);


        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfTaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setDateOfTask(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);


        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setStartTime(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);


        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setTenantId(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);


        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskName").value(hasItem(DEFAULT_TASK_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateOfTask").value(hasItem(DEFAULT_DATE_OF_TASK.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(sameInstant(DEFAULT_DATE_CREATED))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.taskName").value(DEFAULT_TASK_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateOfTask").value(DEFAULT_DATE_OF_TASK.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dateCreated").value(sameInstant(DEFAULT_DATE_CREATED)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getTasksByIdFiltering() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        Long id = task.getId();

        defaultTaskShouldBeFound("id.equals=" + id);
        defaultTaskShouldNotBeFound("id.notEquals=" + id);

        defaultTaskShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTaskShouldNotBeFound("id.greaterThan=" + id);

        defaultTaskShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTaskShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTasksByTaskNameIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where taskName equals to DEFAULT_TASK_NAME
        defaultTaskShouldBeFound("taskName.equals=" + DEFAULT_TASK_NAME);

        // Get all the taskList where taskName equals to UPDATED_TASK_NAME
        defaultTaskShouldNotBeFound("taskName.equals=" + UPDATED_TASK_NAME);
    }

    @Test
    @Transactional
    public void getAllTasksByTaskNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where taskName not equals to DEFAULT_TASK_NAME
        defaultTaskShouldNotBeFound("taskName.notEquals=" + DEFAULT_TASK_NAME);

        // Get all the taskList where taskName not equals to UPDATED_TASK_NAME
        defaultTaskShouldBeFound("taskName.notEquals=" + UPDATED_TASK_NAME);
    }

    @Test
    @Transactional
    public void getAllTasksByTaskNameIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where taskName in DEFAULT_TASK_NAME or UPDATED_TASK_NAME
        defaultTaskShouldBeFound("taskName.in=" + DEFAULT_TASK_NAME + "," + UPDATED_TASK_NAME);

        // Get all the taskList where taskName equals to UPDATED_TASK_NAME
        defaultTaskShouldNotBeFound("taskName.in=" + UPDATED_TASK_NAME);
    }

    @Test
    @Transactional
    public void getAllTasksByTaskNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where taskName is not null
        defaultTaskShouldBeFound("taskName.specified=true");

        // Get all the taskList where taskName is null
        defaultTaskShouldNotBeFound("taskName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTasksByTaskNameContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where taskName contains DEFAULT_TASK_NAME
        defaultTaskShouldBeFound("taskName.contains=" + DEFAULT_TASK_NAME);

        // Get all the taskList where taskName contains UPDATED_TASK_NAME
        defaultTaskShouldNotBeFound("taskName.contains=" + UPDATED_TASK_NAME);
    }

    @Test
    @Transactional
    public void getAllTasksByTaskNameNotContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where taskName does not contain DEFAULT_TASK_NAME
        defaultTaskShouldNotBeFound("taskName.doesNotContain=" + DEFAULT_TASK_NAME);

        // Get all the taskList where taskName does not contain UPDATED_TASK_NAME
        defaultTaskShouldBeFound("taskName.doesNotContain=" + UPDATED_TASK_NAME);
    }


    @Test
    @Transactional
    public void getAllTasksByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description equals to DEFAULT_DESCRIPTION
        defaultTaskShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the taskList where description equals to UPDATED_DESCRIPTION
        defaultTaskShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTasksByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description not equals to DEFAULT_DESCRIPTION
        defaultTaskShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the taskList where description not equals to UPDATED_DESCRIPTION
        defaultTaskShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTasksByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTaskShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the taskList where description equals to UPDATED_DESCRIPTION
        defaultTaskShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTasksByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description is not null
        defaultTaskShouldBeFound("description.specified=true");

        // Get all the taskList where description is null
        defaultTaskShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTasksByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description contains DEFAULT_DESCRIPTION
        defaultTaskShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the taskList where description contains UPDATED_DESCRIPTION
        defaultTaskShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTasksByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description does not contain DEFAULT_DESCRIPTION
        defaultTaskShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the taskList where description does not contain UPDATED_DESCRIPTION
        defaultTaskShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTasksByDateOfTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateOfTask equals to DEFAULT_DATE_OF_TASK
        defaultTaskShouldBeFound("dateOfTask.equals=" + DEFAULT_DATE_OF_TASK);

        // Get all the taskList where dateOfTask equals to UPDATED_DATE_OF_TASK
        defaultTaskShouldNotBeFound("dateOfTask.equals=" + UPDATED_DATE_OF_TASK);
    }

    @Test
    @Transactional
    public void getAllTasksByDateOfTaskIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateOfTask not equals to DEFAULT_DATE_OF_TASK
        defaultTaskShouldNotBeFound("dateOfTask.notEquals=" + DEFAULT_DATE_OF_TASK);

        // Get all the taskList where dateOfTask not equals to UPDATED_DATE_OF_TASK
        defaultTaskShouldBeFound("dateOfTask.notEquals=" + UPDATED_DATE_OF_TASK);
    }

    @Test
    @Transactional
    public void getAllTasksByDateOfTaskIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateOfTask in DEFAULT_DATE_OF_TASK or UPDATED_DATE_OF_TASK
        defaultTaskShouldBeFound("dateOfTask.in=" + DEFAULT_DATE_OF_TASK + "," + UPDATED_DATE_OF_TASK);

        // Get all the taskList where dateOfTask equals to UPDATED_DATE_OF_TASK
        defaultTaskShouldNotBeFound("dateOfTask.in=" + UPDATED_DATE_OF_TASK);
    }

    @Test
    @Transactional
    public void getAllTasksByDateOfTaskIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateOfTask is not null
        defaultTaskShouldBeFound("dateOfTask.specified=true");

        // Get all the taskList where dateOfTask is null
        defaultTaskShouldNotBeFound("dateOfTask.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByDateOfTaskIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateOfTask is greater than or equal to DEFAULT_DATE_OF_TASK
        defaultTaskShouldBeFound("dateOfTask.greaterThanOrEqual=" + DEFAULT_DATE_OF_TASK);

        // Get all the taskList where dateOfTask is greater than or equal to UPDATED_DATE_OF_TASK
        defaultTaskShouldNotBeFound("dateOfTask.greaterThanOrEqual=" + UPDATED_DATE_OF_TASK);
    }

    @Test
    @Transactional
    public void getAllTasksByDateOfTaskIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateOfTask is less than or equal to DEFAULT_DATE_OF_TASK
        defaultTaskShouldBeFound("dateOfTask.lessThanOrEqual=" + DEFAULT_DATE_OF_TASK);

        // Get all the taskList where dateOfTask is less than or equal to SMALLER_DATE_OF_TASK
        defaultTaskShouldNotBeFound("dateOfTask.lessThanOrEqual=" + SMALLER_DATE_OF_TASK);
    }

    @Test
    @Transactional
    public void getAllTasksByDateOfTaskIsLessThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateOfTask is less than DEFAULT_DATE_OF_TASK
        defaultTaskShouldNotBeFound("dateOfTask.lessThan=" + DEFAULT_DATE_OF_TASK);

        // Get all the taskList where dateOfTask is less than UPDATED_DATE_OF_TASK
        defaultTaskShouldBeFound("dateOfTask.lessThan=" + UPDATED_DATE_OF_TASK);
    }

    @Test
    @Transactional
    public void getAllTasksByDateOfTaskIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateOfTask is greater than DEFAULT_DATE_OF_TASK
        defaultTaskShouldNotBeFound("dateOfTask.greaterThan=" + DEFAULT_DATE_OF_TASK);

        // Get all the taskList where dateOfTask is greater than SMALLER_DATE_OF_TASK
        defaultTaskShouldBeFound("dateOfTask.greaterThan=" + SMALLER_DATE_OF_TASK);
    }


    @Test
    @Transactional
    public void getAllTasksByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startTime equals to DEFAULT_START_TIME
        defaultTaskShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the taskList where startTime equals to UPDATED_START_TIME
        defaultTaskShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTasksByStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startTime not equals to DEFAULT_START_TIME
        defaultTaskShouldNotBeFound("startTime.notEquals=" + DEFAULT_START_TIME);

        // Get all the taskList where startTime not equals to UPDATED_START_TIME
        defaultTaskShouldBeFound("startTime.notEquals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTasksByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultTaskShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the taskList where startTime equals to UPDATED_START_TIME
        defaultTaskShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTasksByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startTime is not null
        defaultTaskShouldBeFound("startTime.specified=true");

        // Get all the taskList where startTime is null
        defaultTaskShouldNotBeFound("startTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllTasksByStartTimeContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startTime contains DEFAULT_START_TIME
        defaultTaskShouldBeFound("startTime.contains=" + DEFAULT_START_TIME);

        // Get all the taskList where startTime contains UPDATED_START_TIME
        defaultTaskShouldNotBeFound("startTime.contains=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTasksByStartTimeNotContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startTime does not contain DEFAULT_START_TIME
        defaultTaskShouldNotBeFound("startTime.doesNotContain=" + DEFAULT_START_TIME);

        // Get all the taskList where startTime does not contain UPDATED_START_TIME
        defaultTaskShouldBeFound("startTime.doesNotContain=" + UPDATED_START_TIME);
    }


    @Test
    @Transactional
    public void getAllTasksByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endTime equals to DEFAULT_END_TIME
        defaultTaskShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the taskList where endTime equals to UPDATED_END_TIME
        defaultTaskShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTasksByEndTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endTime not equals to DEFAULT_END_TIME
        defaultTaskShouldNotBeFound("endTime.notEquals=" + DEFAULT_END_TIME);

        // Get all the taskList where endTime not equals to UPDATED_END_TIME
        defaultTaskShouldBeFound("endTime.notEquals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTasksByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultTaskShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the taskList where endTime equals to UPDATED_END_TIME
        defaultTaskShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTasksByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endTime is not null
        defaultTaskShouldBeFound("endTime.specified=true");

        // Get all the taskList where endTime is null
        defaultTaskShouldNotBeFound("endTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllTasksByEndTimeContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endTime contains DEFAULT_END_TIME
        defaultTaskShouldBeFound("endTime.contains=" + DEFAULT_END_TIME);

        // Get all the taskList where endTime contains UPDATED_END_TIME
        defaultTaskShouldNotBeFound("endTime.contains=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTasksByEndTimeNotContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endTime does not contain DEFAULT_END_TIME
        defaultTaskShouldNotBeFound("endTime.doesNotContain=" + DEFAULT_END_TIME);

        // Get all the taskList where endTime does not contain UPDATED_END_TIME
        defaultTaskShouldBeFound("endTime.doesNotContain=" + UPDATED_END_TIME);
    }


    @Test
    @Transactional
    public void getAllTasksByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where status equals to DEFAULT_STATUS
        defaultTaskShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the taskList where status equals to UPDATED_STATUS
        defaultTaskShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTasksByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where status not equals to DEFAULT_STATUS
        defaultTaskShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the taskList where status not equals to UPDATED_STATUS
        defaultTaskShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTasksByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTaskShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the taskList where status equals to UPDATED_STATUS
        defaultTaskShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTasksByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where status is not null
        defaultTaskShouldBeFound("status.specified=true");

        // Get all the taskList where status is null
        defaultTaskShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByDateCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateCreated equals to DEFAULT_DATE_CREATED
        defaultTaskShouldBeFound("dateCreated.equals=" + DEFAULT_DATE_CREATED);

        // Get all the taskList where dateCreated equals to UPDATED_DATE_CREATED
        defaultTaskShouldNotBeFound("dateCreated.equals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllTasksByDateCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateCreated not equals to DEFAULT_DATE_CREATED
        defaultTaskShouldNotBeFound("dateCreated.notEquals=" + DEFAULT_DATE_CREATED);

        // Get all the taskList where dateCreated not equals to UPDATED_DATE_CREATED
        defaultTaskShouldBeFound("dateCreated.notEquals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllTasksByDateCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateCreated in DEFAULT_DATE_CREATED or UPDATED_DATE_CREATED
        defaultTaskShouldBeFound("dateCreated.in=" + DEFAULT_DATE_CREATED + "," + UPDATED_DATE_CREATED);

        // Get all the taskList where dateCreated equals to UPDATED_DATE_CREATED
        defaultTaskShouldNotBeFound("dateCreated.in=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllTasksByDateCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateCreated is not null
        defaultTaskShouldBeFound("dateCreated.specified=true");

        // Get all the taskList where dateCreated is null
        defaultTaskShouldNotBeFound("dateCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByDateCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateCreated is greater than or equal to DEFAULT_DATE_CREATED
        defaultTaskShouldBeFound("dateCreated.greaterThanOrEqual=" + DEFAULT_DATE_CREATED);

        // Get all the taskList where dateCreated is greater than or equal to UPDATED_DATE_CREATED
        defaultTaskShouldNotBeFound("dateCreated.greaterThanOrEqual=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllTasksByDateCreatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateCreated is less than or equal to DEFAULT_DATE_CREATED
        defaultTaskShouldBeFound("dateCreated.lessThanOrEqual=" + DEFAULT_DATE_CREATED);

        // Get all the taskList where dateCreated is less than or equal to SMALLER_DATE_CREATED
        defaultTaskShouldNotBeFound("dateCreated.lessThanOrEqual=" + SMALLER_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllTasksByDateCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateCreated is less than DEFAULT_DATE_CREATED
        defaultTaskShouldNotBeFound("dateCreated.lessThan=" + DEFAULT_DATE_CREATED);

        // Get all the taskList where dateCreated is less than UPDATED_DATE_CREATED
        defaultTaskShouldBeFound("dateCreated.lessThan=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllTasksByDateCreatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateCreated is greater than DEFAULT_DATE_CREATED
        defaultTaskShouldNotBeFound("dateCreated.greaterThan=" + DEFAULT_DATE_CREATED);

        // Get all the taskList where dateCreated is greater than SMALLER_DATE_CREATED
        defaultTaskShouldBeFound("dateCreated.greaterThan=" + SMALLER_DATE_CREATED);
    }


    @Test
    @Transactional
    public void getAllTasksByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultTaskShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the taskList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTaskShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTasksByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultTaskShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the taskList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultTaskShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTasksByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultTaskShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the taskList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTaskShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTasksByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where lastUpdatedDate is not null
        defaultTaskShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the taskList where lastUpdatedDate is null
        defaultTaskShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTaskShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the taskList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultTaskShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTasksByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTaskShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the taskList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultTaskShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTasksByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultTaskShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the taskList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultTaskShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTasksByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultTaskShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the taskList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultTaskShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllTasksByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where tenantId equals to DEFAULT_TENANT_ID
        defaultTaskShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the taskList where tenantId equals to UPDATED_TENANT_ID
        defaultTaskShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTasksByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where tenantId not equals to DEFAULT_TENANT_ID
        defaultTaskShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the taskList where tenantId not equals to UPDATED_TENANT_ID
        defaultTaskShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTasksByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultTaskShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the taskList where tenantId equals to UPDATED_TENANT_ID
        defaultTaskShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTasksByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where tenantId is not null
        defaultTaskShouldBeFound("tenantId.specified=true");

        // Get all the taskList where tenantId is null
        defaultTaskShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultTaskShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the taskList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultTaskShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTasksByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultTaskShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the taskList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultTaskShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTasksByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where tenantId is less than DEFAULT_TENANT_ID
        defaultTaskShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the taskList where tenantId is less than UPDATED_TENANT_ID
        defaultTaskShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTasksByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where tenantId is greater than DEFAULT_TENANT_ID
        defaultTaskShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the taskList where tenantId is greater than SMALLER_TENANT_ID
        defaultTaskShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllTasksByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        task.setServiceUser(serviceUser);
        taskRepository.saveAndFlush(task);
        Long serviceUserId = serviceUser.getId();

        // Get all the taskList where serviceUser equals to serviceUserId
        defaultTaskShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the taskList where serviceUser equals to serviceUserId + 1
        defaultTaskShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }


    @Test
    @Transactional
    public void getAllTasksByAssignedToIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        Employee assignedTo = EmployeeResourceIT.createEntity(em);
        em.persist(assignedTo);
        em.flush();
        task.setAssignedTo(assignedTo);
        taskRepository.saveAndFlush(task);
        Long assignedToId = assignedTo.getId();

        // Get all the taskList where assignedTo equals to assignedToId
        defaultTaskShouldBeFound("assignedToId.equals=" + assignedToId);

        // Get all the taskList where assignedTo equals to assignedToId + 1
        defaultTaskShouldNotBeFound("assignedToId.equals=" + (assignedToId + 1));
    }


    @Test
    @Transactional
    public void getAllTasksByServiceOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        ServiceOrder serviceOrder = ServiceOrderResourceIT.createEntity(em);
        em.persist(serviceOrder);
        em.flush();
        task.setServiceOrder(serviceOrder);
        taskRepository.saveAndFlush(task);
        Long serviceOrderId = serviceOrder.getId();

        // Get all the taskList where serviceOrder equals to serviceOrderId
        defaultTaskShouldBeFound("serviceOrderId.equals=" + serviceOrderId);

        // Get all the taskList where serviceOrder equals to serviceOrderId + 1
        defaultTaskShouldNotBeFound("serviceOrderId.equals=" + (serviceOrderId + 1));
    }


    @Test
    @Transactional
    public void getAllTasksByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        Employee createdBy = EmployeeResourceIT.createEntity(em);
        em.persist(createdBy);
        em.flush();
        task.setCreatedBy(createdBy);
        taskRepository.saveAndFlush(task);
        Long createdById = createdBy.getId();

        // Get all the taskList where createdBy equals to createdById
        defaultTaskShouldBeFound("createdById.equals=" + createdById);

        // Get all the taskList where createdBy equals to createdById + 1
        defaultTaskShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }


    @Test
    @Transactional
    public void getAllTasksByAllocatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        Employee allocatedBy = EmployeeResourceIT.createEntity(em);
        em.persist(allocatedBy);
        em.flush();
        task.setAllocatedBy(allocatedBy);
        taskRepository.saveAndFlush(task);
        Long allocatedById = allocatedBy.getId();

        // Get all the taskList where allocatedBy equals to allocatedById
        defaultTaskShouldBeFound("allocatedById.equals=" + allocatedById);

        // Get all the taskList where allocatedBy equals to allocatedById + 1
        defaultTaskShouldNotBeFound("allocatedById.equals=" + (allocatedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTaskShouldBeFound(String filter) throws Exception {
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskName").value(hasItem(DEFAULT_TASK_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateOfTask").value(hasItem(DEFAULT_DATE_OF_TASK.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(sameInstant(DEFAULT_DATE_CREATED))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restTaskMockMvc.perform(get("/api/tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTaskShouldNotBeFound(String filter) throws Exception {
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaskMockMvc.perform(get("/api/tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).get();
        // Disconnect from session so that the updates on updatedTask are not directly saved in db
        em.detach(updatedTask);
        updatedTask
            .taskName(UPDATED_TASK_NAME)
            .description(UPDATED_DESCRIPTION)
            .dateOfTask(UPDATED_DATE_OF_TASK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        TaskDTO taskDTO = taskMapper.toDto(updatedTask);

        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTask.getDateOfTask()).isEqualTo(UPDATED_DATE_OF_TASK);
        assertThat(testTask.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTask.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTask.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTask.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testTask.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Delete the task
        restTaskMockMvc.perform(delete("/api/tasks/{id}", task.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
