package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.ServiceUserEvent;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.ServiceUserEventRepository;
import com.amc.careplanner.service.ServiceUserEventService;
import com.amc.careplanner.service.dto.ServiceUserEventDTO;
import com.amc.careplanner.service.mapper.ServiceUserEventMapper;
import com.amc.careplanner.service.dto.ServiceUserEventCriteria;
import com.amc.careplanner.service.ServiceUserEventQueryService;

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

import com.amc.careplanner.domain.enumeration.ServiceUserEventStatus;
import com.amc.careplanner.domain.enumeration.ServiceUserEventType;
import com.amc.careplanner.domain.enumeration.ServicePriority;
/**
 * Integration tests for the {@link ServiceUserEventResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceUserEventResourceIT {

    private static final String DEFAULT_EVENT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ServiceUserEventStatus DEFAULT_SERVICE_USER_EVENT_STATUS = ServiceUserEventStatus.REPORTED;
    private static final ServiceUserEventStatus UPDATED_SERVICE_USER_EVENT_STATUS = ServiceUserEventStatus.UNDER_INVESTIGATION;

    private static final ServiceUserEventType DEFAULT_SERVICE_USER_EVENT_TYPE = ServiceUserEventType.ACCIDENT;
    private static final ServiceUserEventType UPDATED_SERVICE_USER_EVENT_TYPE = ServiceUserEventType.INCIDENT;

    private static final ServicePriority DEFAULT_PRIORITY = ServicePriority.HIGH;
    private static final ServicePriority UPDATED_PRIORITY = ServicePriority.LOW;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_OF_EVENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_EVENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_OF_EVENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private ServiceUserEventRepository serviceUserEventRepository;

    @Autowired
    private ServiceUserEventMapper serviceUserEventMapper;

    @Autowired
    private ServiceUserEventService serviceUserEventService;

    @Autowired
    private ServiceUserEventQueryService serviceUserEventQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceUserEventMockMvc;

    private ServiceUserEvent serviceUserEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUserEvent createEntity(EntityManager em) {
        ServiceUserEvent serviceUserEvent = new ServiceUserEvent()
            .eventTitle(DEFAULT_EVENT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .serviceUserEventStatus(DEFAULT_SERVICE_USER_EVENT_STATUS)
            .serviceUserEventType(DEFAULT_SERVICE_USER_EVENT_TYPE)
            .priority(DEFAULT_PRIORITY)
            .note(DEFAULT_NOTE)
            .dateOfEvent(DEFAULT_DATE_OF_EVENT)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return serviceUserEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUserEvent createUpdatedEntity(EntityManager em) {
        ServiceUserEvent serviceUserEvent = new ServiceUserEvent()
            .eventTitle(UPDATED_EVENT_TITLE)
            .description(UPDATED_DESCRIPTION)
            .serviceUserEventStatus(UPDATED_SERVICE_USER_EVENT_STATUS)
            .serviceUserEventType(UPDATED_SERVICE_USER_EVENT_TYPE)
            .priority(UPDATED_PRIORITY)
            .note(UPDATED_NOTE)
            .dateOfEvent(UPDATED_DATE_OF_EVENT)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return serviceUserEvent;
    }

    @BeforeEach
    public void initTest() {
        serviceUserEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceUserEvent() throws Exception {
        int databaseSizeBeforeCreate = serviceUserEventRepository.findAll().size();
        // Create the ServiceUserEvent
        ServiceUserEventDTO serviceUserEventDTO = serviceUserEventMapper.toDto(serviceUserEvent);
        restServiceUserEventMockMvc.perform(post("/api/service-user-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserEventDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceUserEvent in the database
        List<ServiceUserEvent> serviceUserEventList = serviceUserEventRepository.findAll();
        assertThat(serviceUserEventList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceUserEvent testServiceUserEvent = serviceUserEventList.get(serviceUserEventList.size() - 1);
        assertThat(testServiceUserEvent.getEventTitle()).isEqualTo(DEFAULT_EVENT_TITLE);
        assertThat(testServiceUserEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServiceUserEvent.getServiceUserEventStatus()).isEqualTo(DEFAULT_SERVICE_USER_EVENT_STATUS);
        assertThat(testServiceUserEvent.getServiceUserEventType()).isEqualTo(DEFAULT_SERVICE_USER_EVENT_TYPE);
        assertThat(testServiceUserEvent.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testServiceUserEvent.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testServiceUserEvent.getDateOfEvent()).isEqualTo(DEFAULT_DATE_OF_EVENT);
        assertThat(testServiceUserEvent.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testServiceUserEvent.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createServiceUserEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceUserEventRepository.findAll().size();

        // Create the ServiceUserEvent with an existing ID
        serviceUserEvent.setId(1L);
        ServiceUserEventDTO serviceUserEventDTO = serviceUserEventMapper.toDto(serviceUserEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceUserEventMockMvc.perform(post("/api/service-user-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUserEvent in the database
        List<ServiceUserEvent> serviceUserEventList = serviceUserEventRepository.findAll();
        assertThat(serviceUserEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEventTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserEventRepository.findAll().size();
        // set the field null
        serviceUserEvent.setEventTitle(null);

        // Create the ServiceUserEvent, which fails.
        ServiceUserEventDTO serviceUserEventDTO = serviceUserEventMapper.toDto(serviceUserEvent);


        restServiceUserEventMockMvc.perform(post("/api/service-user-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserEventDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserEvent> serviceUserEventList = serviceUserEventRepository.findAll();
        assertThat(serviceUserEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfEventIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserEventRepository.findAll().size();
        // set the field null
        serviceUserEvent.setDateOfEvent(null);

        // Create the ServiceUserEvent, which fails.
        ServiceUserEventDTO serviceUserEventDTO = serviceUserEventMapper.toDto(serviceUserEvent);


        restServiceUserEventMockMvc.perform(post("/api/service-user-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserEventDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserEvent> serviceUserEventList = serviceUserEventRepository.findAll();
        assertThat(serviceUserEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserEventRepository.findAll().size();
        // set the field null
        serviceUserEvent.setTenantId(null);

        // Create the ServiceUserEvent, which fails.
        ServiceUserEventDTO serviceUserEventDTO = serviceUserEventMapper.toDto(serviceUserEvent);


        restServiceUserEventMockMvc.perform(post("/api/service-user-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserEventDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserEvent> serviceUserEventList = serviceUserEventRepository.findAll();
        assertThat(serviceUserEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceUserEvents() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList
        restServiceUserEventMockMvc.perform(get("/api/service-user-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUserEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventTitle").value(hasItem(DEFAULT_EVENT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].serviceUserEventStatus").value(hasItem(DEFAULT_SERVICE_USER_EVENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].serviceUserEventType").value(hasItem(DEFAULT_SERVICE_USER_EVENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].dateOfEvent").value(hasItem(sameInstant(DEFAULT_DATE_OF_EVENT))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getServiceUserEvent() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get the serviceUserEvent
        restServiceUserEventMockMvc.perform(get("/api/service-user-events/{id}", serviceUserEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceUserEvent.getId().intValue()))
            .andExpect(jsonPath("$.eventTitle").value(DEFAULT_EVENT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.serviceUserEventStatus").value(DEFAULT_SERVICE_USER_EVENT_STATUS.toString()))
            .andExpect(jsonPath("$.serviceUserEventType").value(DEFAULT_SERVICE_USER_EVENT_TYPE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.dateOfEvent").value(sameInstant(DEFAULT_DATE_OF_EVENT)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getServiceUserEventsByIdFiltering() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        Long id = serviceUserEvent.getId();

        defaultServiceUserEventShouldBeFound("id.equals=" + id);
        defaultServiceUserEventShouldNotBeFound("id.notEquals=" + id);

        defaultServiceUserEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceUserEventShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceUserEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceUserEventShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceUserEventsByEventTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where eventTitle equals to DEFAULT_EVENT_TITLE
        defaultServiceUserEventShouldBeFound("eventTitle.equals=" + DEFAULT_EVENT_TITLE);

        // Get all the serviceUserEventList where eventTitle equals to UPDATED_EVENT_TITLE
        defaultServiceUserEventShouldNotBeFound("eventTitle.equals=" + UPDATED_EVENT_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByEventTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where eventTitle not equals to DEFAULT_EVENT_TITLE
        defaultServiceUserEventShouldNotBeFound("eventTitle.notEquals=" + DEFAULT_EVENT_TITLE);

        // Get all the serviceUserEventList where eventTitle not equals to UPDATED_EVENT_TITLE
        defaultServiceUserEventShouldBeFound("eventTitle.notEquals=" + UPDATED_EVENT_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByEventTitleIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where eventTitle in DEFAULT_EVENT_TITLE or UPDATED_EVENT_TITLE
        defaultServiceUserEventShouldBeFound("eventTitle.in=" + DEFAULT_EVENT_TITLE + "," + UPDATED_EVENT_TITLE);

        // Get all the serviceUserEventList where eventTitle equals to UPDATED_EVENT_TITLE
        defaultServiceUserEventShouldNotBeFound("eventTitle.in=" + UPDATED_EVENT_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByEventTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where eventTitle is not null
        defaultServiceUserEventShouldBeFound("eventTitle.specified=true");

        // Get all the serviceUserEventList where eventTitle is null
        defaultServiceUserEventShouldNotBeFound("eventTitle.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUserEventsByEventTitleContainsSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where eventTitle contains DEFAULT_EVENT_TITLE
        defaultServiceUserEventShouldBeFound("eventTitle.contains=" + DEFAULT_EVENT_TITLE);

        // Get all the serviceUserEventList where eventTitle contains UPDATED_EVENT_TITLE
        defaultServiceUserEventShouldNotBeFound("eventTitle.contains=" + UPDATED_EVENT_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByEventTitleNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where eventTitle does not contain DEFAULT_EVENT_TITLE
        defaultServiceUserEventShouldNotBeFound("eventTitle.doesNotContain=" + DEFAULT_EVENT_TITLE);

        // Get all the serviceUserEventList where eventTitle does not contain UPDATED_EVENT_TITLE
        defaultServiceUserEventShouldBeFound("eventTitle.doesNotContain=" + UPDATED_EVENT_TITLE);
    }


    @Test
    @Transactional
    public void getAllServiceUserEventsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where description equals to DEFAULT_DESCRIPTION
        defaultServiceUserEventShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the serviceUserEventList where description equals to UPDATED_DESCRIPTION
        defaultServiceUserEventShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where description not equals to DEFAULT_DESCRIPTION
        defaultServiceUserEventShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the serviceUserEventList where description not equals to UPDATED_DESCRIPTION
        defaultServiceUserEventShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultServiceUserEventShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the serviceUserEventList where description equals to UPDATED_DESCRIPTION
        defaultServiceUserEventShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where description is not null
        defaultServiceUserEventShouldBeFound("description.specified=true");

        // Get all the serviceUserEventList where description is null
        defaultServiceUserEventShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUserEventsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where description contains DEFAULT_DESCRIPTION
        defaultServiceUserEventShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the serviceUserEventList where description contains UPDATED_DESCRIPTION
        defaultServiceUserEventShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where description does not contain DEFAULT_DESCRIPTION
        defaultServiceUserEventShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the serviceUserEventList where description does not contain UPDATED_DESCRIPTION
        defaultServiceUserEventShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllServiceUserEventsByServiceUserEventStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where serviceUserEventStatus equals to DEFAULT_SERVICE_USER_EVENT_STATUS
        defaultServiceUserEventShouldBeFound("serviceUserEventStatus.equals=" + DEFAULT_SERVICE_USER_EVENT_STATUS);

        // Get all the serviceUserEventList where serviceUserEventStatus equals to UPDATED_SERVICE_USER_EVENT_STATUS
        defaultServiceUserEventShouldNotBeFound("serviceUserEventStatus.equals=" + UPDATED_SERVICE_USER_EVENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByServiceUserEventStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where serviceUserEventStatus not equals to DEFAULT_SERVICE_USER_EVENT_STATUS
        defaultServiceUserEventShouldNotBeFound("serviceUserEventStatus.notEquals=" + DEFAULT_SERVICE_USER_EVENT_STATUS);

        // Get all the serviceUserEventList where serviceUserEventStatus not equals to UPDATED_SERVICE_USER_EVENT_STATUS
        defaultServiceUserEventShouldBeFound("serviceUserEventStatus.notEquals=" + UPDATED_SERVICE_USER_EVENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByServiceUserEventStatusIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where serviceUserEventStatus in DEFAULT_SERVICE_USER_EVENT_STATUS or UPDATED_SERVICE_USER_EVENT_STATUS
        defaultServiceUserEventShouldBeFound("serviceUserEventStatus.in=" + DEFAULT_SERVICE_USER_EVENT_STATUS + "," + UPDATED_SERVICE_USER_EVENT_STATUS);

        // Get all the serviceUserEventList where serviceUserEventStatus equals to UPDATED_SERVICE_USER_EVENT_STATUS
        defaultServiceUserEventShouldNotBeFound("serviceUserEventStatus.in=" + UPDATED_SERVICE_USER_EVENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByServiceUserEventStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where serviceUserEventStatus is not null
        defaultServiceUserEventShouldBeFound("serviceUserEventStatus.specified=true");

        // Get all the serviceUserEventList where serviceUserEventStatus is null
        defaultServiceUserEventShouldNotBeFound("serviceUserEventStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByServiceUserEventTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where serviceUserEventType equals to DEFAULT_SERVICE_USER_EVENT_TYPE
        defaultServiceUserEventShouldBeFound("serviceUserEventType.equals=" + DEFAULT_SERVICE_USER_EVENT_TYPE);

        // Get all the serviceUserEventList where serviceUserEventType equals to UPDATED_SERVICE_USER_EVENT_TYPE
        defaultServiceUserEventShouldNotBeFound("serviceUserEventType.equals=" + UPDATED_SERVICE_USER_EVENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByServiceUserEventTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where serviceUserEventType not equals to DEFAULT_SERVICE_USER_EVENT_TYPE
        defaultServiceUserEventShouldNotBeFound("serviceUserEventType.notEquals=" + DEFAULT_SERVICE_USER_EVENT_TYPE);

        // Get all the serviceUserEventList where serviceUserEventType not equals to UPDATED_SERVICE_USER_EVENT_TYPE
        defaultServiceUserEventShouldBeFound("serviceUserEventType.notEquals=" + UPDATED_SERVICE_USER_EVENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByServiceUserEventTypeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where serviceUserEventType in DEFAULT_SERVICE_USER_EVENT_TYPE or UPDATED_SERVICE_USER_EVENT_TYPE
        defaultServiceUserEventShouldBeFound("serviceUserEventType.in=" + DEFAULT_SERVICE_USER_EVENT_TYPE + "," + UPDATED_SERVICE_USER_EVENT_TYPE);

        // Get all the serviceUserEventList where serviceUserEventType equals to UPDATED_SERVICE_USER_EVENT_TYPE
        defaultServiceUserEventShouldNotBeFound("serviceUserEventType.in=" + UPDATED_SERVICE_USER_EVENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByServiceUserEventTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where serviceUserEventType is not null
        defaultServiceUserEventShouldBeFound("serviceUserEventType.specified=true");

        // Get all the serviceUserEventList where serviceUserEventType is null
        defaultServiceUserEventShouldNotBeFound("serviceUserEventType.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where priority equals to DEFAULT_PRIORITY
        defaultServiceUserEventShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the serviceUserEventList where priority equals to UPDATED_PRIORITY
        defaultServiceUserEventShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where priority not equals to DEFAULT_PRIORITY
        defaultServiceUserEventShouldNotBeFound("priority.notEquals=" + DEFAULT_PRIORITY);

        // Get all the serviceUserEventList where priority not equals to UPDATED_PRIORITY
        defaultServiceUserEventShouldBeFound("priority.notEquals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultServiceUserEventShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the serviceUserEventList where priority equals to UPDATED_PRIORITY
        defaultServiceUserEventShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where priority is not null
        defaultServiceUserEventShouldBeFound("priority.specified=true");

        // Get all the serviceUserEventList where priority is null
        defaultServiceUserEventShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where note equals to DEFAULT_NOTE
        defaultServiceUserEventShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the serviceUserEventList where note equals to UPDATED_NOTE
        defaultServiceUserEventShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where note not equals to DEFAULT_NOTE
        defaultServiceUserEventShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the serviceUserEventList where note not equals to UPDATED_NOTE
        defaultServiceUserEventShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultServiceUserEventShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the serviceUserEventList where note equals to UPDATED_NOTE
        defaultServiceUserEventShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where note is not null
        defaultServiceUserEventShouldBeFound("note.specified=true");

        // Get all the serviceUserEventList where note is null
        defaultServiceUserEventShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUserEventsByNoteContainsSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where note contains DEFAULT_NOTE
        defaultServiceUserEventShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the serviceUserEventList where note contains UPDATED_NOTE
        defaultServiceUserEventShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where note does not contain DEFAULT_NOTE
        defaultServiceUserEventShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the serviceUserEventList where note does not contain UPDATED_NOTE
        defaultServiceUserEventShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllServiceUserEventsByDateOfEventIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where dateOfEvent equals to DEFAULT_DATE_OF_EVENT
        defaultServiceUserEventShouldBeFound("dateOfEvent.equals=" + DEFAULT_DATE_OF_EVENT);

        // Get all the serviceUserEventList where dateOfEvent equals to UPDATED_DATE_OF_EVENT
        defaultServiceUserEventShouldNotBeFound("dateOfEvent.equals=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDateOfEventIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where dateOfEvent not equals to DEFAULT_DATE_OF_EVENT
        defaultServiceUserEventShouldNotBeFound("dateOfEvent.notEquals=" + DEFAULT_DATE_OF_EVENT);

        // Get all the serviceUserEventList where dateOfEvent not equals to UPDATED_DATE_OF_EVENT
        defaultServiceUserEventShouldBeFound("dateOfEvent.notEquals=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDateOfEventIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where dateOfEvent in DEFAULT_DATE_OF_EVENT or UPDATED_DATE_OF_EVENT
        defaultServiceUserEventShouldBeFound("dateOfEvent.in=" + DEFAULT_DATE_OF_EVENT + "," + UPDATED_DATE_OF_EVENT);

        // Get all the serviceUserEventList where dateOfEvent equals to UPDATED_DATE_OF_EVENT
        defaultServiceUserEventShouldNotBeFound("dateOfEvent.in=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDateOfEventIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where dateOfEvent is not null
        defaultServiceUserEventShouldBeFound("dateOfEvent.specified=true");

        // Get all the serviceUserEventList where dateOfEvent is null
        defaultServiceUserEventShouldNotBeFound("dateOfEvent.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDateOfEventIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where dateOfEvent is greater than or equal to DEFAULT_DATE_OF_EVENT
        defaultServiceUserEventShouldBeFound("dateOfEvent.greaterThanOrEqual=" + DEFAULT_DATE_OF_EVENT);

        // Get all the serviceUserEventList where dateOfEvent is greater than or equal to UPDATED_DATE_OF_EVENT
        defaultServiceUserEventShouldNotBeFound("dateOfEvent.greaterThanOrEqual=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDateOfEventIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where dateOfEvent is less than or equal to DEFAULT_DATE_OF_EVENT
        defaultServiceUserEventShouldBeFound("dateOfEvent.lessThanOrEqual=" + DEFAULT_DATE_OF_EVENT);

        // Get all the serviceUserEventList where dateOfEvent is less than or equal to SMALLER_DATE_OF_EVENT
        defaultServiceUserEventShouldNotBeFound("dateOfEvent.lessThanOrEqual=" + SMALLER_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDateOfEventIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where dateOfEvent is less than DEFAULT_DATE_OF_EVENT
        defaultServiceUserEventShouldNotBeFound("dateOfEvent.lessThan=" + DEFAULT_DATE_OF_EVENT);

        // Get all the serviceUserEventList where dateOfEvent is less than UPDATED_DATE_OF_EVENT
        defaultServiceUserEventShouldBeFound("dateOfEvent.lessThan=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByDateOfEventIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where dateOfEvent is greater than DEFAULT_DATE_OF_EVENT
        defaultServiceUserEventShouldNotBeFound("dateOfEvent.greaterThan=" + DEFAULT_DATE_OF_EVENT);

        // Get all the serviceUserEventList where dateOfEvent is greater than SMALLER_DATE_OF_EVENT
        defaultServiceUserEventShouldBeFound("dateOfEvent.greaterThan=" + SMALLER_DATE_OF_EVENT);
    }


    @Test
    @Transactional
    public void getAllServiceUserEventsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserEventShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserEventList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserEventShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserEventShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserEventList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserEventShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultServiceUserEventShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the serviceUserEventList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserEventShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where lastUpdatedDate is not null
        defaultServiceUserEventShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the serviceUserEventList where lastUpdatedDate is null
        defaultServiceUserEventShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserEventShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserEventList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserEventShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserEventShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserEventList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultServiceUserEventShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserEventShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserEventList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultServiceUserEventShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserEventShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserEventList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultServiceUserEventShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceUserEventsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where tenantId equals to DEFAULT_TENANT_ID
        defaultServiceUserEventShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the serviceUserEventList where tenantId equals to UPDATED_TENANT_ID
        defaultServiceUserEventShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where tenantId not equals to DEFAULT_TENANT_ID
        defaultServiceUserEventShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the serviceUserEventList where tenantId not equals to UPDATED_TENANT_ID
        defaultServiceUserEventShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultServiceUserEventShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the serviceUserEventList where tenantId equals to UPDATED_TENANT_ID
        defaultServiceUserEventShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where tenantId is not null
        defaultServiceUserEventShouldBeFound("tenantId.specified=true");

        // Get all the serviceUserEventList where tenantId is null
        defaultServiceUserEventShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultServiceUserEventShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the serviceUserEventList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultServiceUserEventShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultServiceUserEventShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the serviceUserEventList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultServiceUserEventShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where tenantId is less than DEFAULT_TENANT_ID
        defaultServiceUserEventShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the serviceUserEventList where tenantId is less than UPDATED_TENANT_ID
        defaultServiceUserEventShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserEventsByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        // Get all the serviceUserEventList where tenantId is greater than DEFAULT_TENANT_ID
        defaultServiceUserEventShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the serviceUserEventList where tenantId is greater than SMALLER_TENANT_ID
        defaultServiceUserEventShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllServiceUserEventsByReportedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);
        Employee reportedBy = EmployeeResourceIT.createEntity(em);
        em.persist(reportedBy);
        em.flush();
        serviceUserEvent.setReportedBy(reportedBy);
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);
        Long reportedById = reportedBy.getId();

        // Get all the serviceUserEventList where reportedBy equals to reportedById
        defaultServiceUserEventShouldBeFound("reportedById.equals=" + reportedById);

        // Get all the serviceUserEventList where reportedBy equals to reportedById + 1
        defaultServiceUserEventShouldNotBeFound("reportedById.equals=" + (reportedById + 1));
    }


    @Test
    @Transactional
    public void getAllServiceUserEventsByAssignedToIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);
        Employee assignedTo = EmployeeResourceIT.createEntity(em);
        em.persist(assignedTo);
        em.flush();
        serviceUserEvent.setAssignedTo(assignedTo);
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);
        Long assignedToId = assignedTo.getId();

        // Get all the serviceUserEventList where assignedTo equals to assignedToId
        defaultServiceUserEventShouldBeFound("assignedToId.equals=" + assignedToId);

        // Get all the serviceUserEventList where assignedTo equals to assignedToId + 1
        defaultServiceUserEventShouldNotBeFound("assignedToId.equals=" + (assignedToId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceUserEventsByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        serviceUserEvent.setServiceUser(serviceUser);
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);
        Long serviceUserId = serviceUser.getId();

        // Get all the serviceUserEventList where serviceUser equals to serviceUserId
        defaultServiceUserEventShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the serviceUserEventList where serviceUser equals to serviceUserId + 1
        defaultServiceUserEventShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceUserEventShouldBeFound(String filter) throws Exception {
        restServiceUserEventMockMvc.perform(get("/api/service-user-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUserEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventTitle").value(hasItem(DEFAULT_EVENT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].serviceUserEventStatus").value(hasItem(DEFAULT_SERVICE_USER_EVENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].serviceUserEventType").value(hasItem(DEFAULT_SERVICE_USER_EVENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].dateOfEvent").value(hasItem(sameInstant(DEFAULT_DATE_OF_EVENT))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restServiceUserEventMockMvc.perform(get("/api/service-user-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceUserEventShouldNotBeFound(String filter) throws Exception {
        restServiceUserEventMockMvc.perform(get("/api/service-user-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceUserEventMockMvc.perform(get("/api/service-user-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingServiceUserEvent() throws Exception {
        // Get the serviceUserEvent
        restServiceUserEventMockMvc.perform(get("/api/service-user-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceUserEvent() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        int databaseSizeBeforeUpdate = serviceUserEventRepository.findAll().size();

        // Update the serviceUserEvent
        ServiceUserEvent updatedServiceUserEvent = serviceUserEventRepository.findById(serviceUserEvent.getId()).get();
        // Disconnect from session so that the updates on updatedServiceUserEvent are not directly saved in db
        em.detach(updatedServiceUserEvent);
        updatedServiceUserEvent
            .eventTitle(UPDATED_EVENT_TITLE)
            .description(UPDATED_DESCRIPTION)
            .serviceUserEventStatus(UPDATED_SERVICE_USER_EVENT_STATUS)
            .serviceUserEventType(UPDATED_SERVICE_USER_EVENT_TYPE)
            .priority(UPDATED_PRIORITY)
            .note(UPDATED_NOTE)
            .dateOfEvent(UPDATED_DATE_OF_EVENT)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        ServiceUserEventDTO serviceUserEventDTO = serviceUserEventMapper.toDto(updatedServiceUserEvent);

        restServiceUserEventMockMvc.perform(put("/api/service-user-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserEventDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceUserEvent in the database
        List<ServiceUserEvent> serviceUserEventList = serviceUserEventRepository.findAll();
        assertThat(serviceUserEventList).hasSize(databaseSizeBeforeUpdate);
        ServiceUserEvent testServiceUserEvent = serviceUserEventList.get(serviceUserEventList.size() - 1);
        assertThat(testServiceUserEvent.getEventTitle()).isEqualTo(UPDATED_EVENT_TITLE);
        assertThat(testServiceUserEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServiceUserEvent.getServiceUserEventStatus()).isEqualTo(UPDATED_SERVICE_USER_EVENT_STATUS);
        assertThat(testServiceUserEvent.getServiceUserEventType()).isEqualTo(UPDATED_SERVICE_USER_EVENT_TYPE);
        assertThat(testServiceUserEvent.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testServiceUserEvent.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testServiceUserEvent.getDateOfEvent()).isEqualTo(UPDATED_DATE_OF_EVENT);
        assertThat(testServiceUserEvent.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testServiceUserEvent.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceUserEvent() throws Exception {
        int databaseSizeBeforeUpdate = serviceUserEventRepository.findAll().size();

        // Create the ServiceUserEvent
        ServiceUserEventDTO serviceUserEventDTO = serviceUserEventMapper.toDto(serviceUserEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceUserEventMockMvc.perform(put("/api/service-user-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUserEvent in the database
        List<ServiceUserEvent> serviceUserEventList = serviceUserEventRepository.findAll();
        assertThat(serviceUserEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceUserEvent() throws Exception {
        // Initialize the database
        serviceUserEventRepository.saveAndFlush(serviceUserEvent);

        int databaseSizeBeforeDelete = serviceUserEventRepository.findAll().size();

        // Delete the serviceUserEvent
        restServiceUserEventMockMvc.perform(delete("/api/service-user-events/{id}", serviceUserEvent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceUserEvent> serviceUserEventList = serviceUserEventRepository.findAll();
        assertThat(serviceUserEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
