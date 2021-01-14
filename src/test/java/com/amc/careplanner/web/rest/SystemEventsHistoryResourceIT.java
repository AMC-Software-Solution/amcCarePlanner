package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.SystemEventsHistory;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.SystemEventsHistoryRepository;
import com.amc.careplanner.service.SystemEventsHistoryService;
import com.amc.careplanner.service.dto.SystemEventsHistoryDTO;
import com.amc.careplanner.service.mapper.SystemEventsHistoryMapper;
import com.amc.careplanner.service.dto.SystemEventsHistoryCriteria;
import com.amc.careplanner.service.SystemEventsHistoryQueryService;

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

/**
 * Integration tests for the {@link SystemEventsHistoryResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SystemEventsHistoryResourceIT {

    private static final String DEFAULT_EVENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EVENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EVENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_EVENT_API = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_API = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_ENTITY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_EVENT_ENTITY_ID = 1L;
    private static final Long UPDATED_EVENT_ENTITY_ID = 2L;
    private static final Long SMALLER_EVENT_ENTITY_ID = 1L - 1L;

    private static final Boolean DEFAULT_IS_SUSPECIOUS = false;
    private static final Boolean UPDATED_IS_SUSPECIOUS = true;

    private static final String DEFAULT_CALLER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CALLER_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_CALLER_ID = 1L;
    private static final Long UPDATED_CALLER_ID = 2L;
    private static final Long SMALLER_CALLER_ID = 1L - 1L;

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    @Autowired
    private SystemEventsHistoryRepository systemEventsHistoryRepository;

    @Autowired
    private SystemEventsHistoryMapper systemEventsHistoryMapper;

    @Autowired
    private SystemEventsHistoryService systemEventsHistoryService;

    @Autowired
    private SystemEventsHistoryQueryService systemEventsHistoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemEventsHistoryMockMvc;

    private SystemEventsHistory systemEventsHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemEventsHistory createEntity(EntityManager em) {
        SystemEventsHistory systemEventsHistory = new SystemEventsHistory()
            .eventName(DEFAULT_EVENT_NAME)
            .eventDate(DEFAULT_EVENT_DATE)
            .eventApi(DEFAULT_EVENT_API)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .eventNote(DEFAULT_EVENT_NOTE)
            .eventEntityName(DEFAULT_EVENT_ENTITY_NAME)
            .eventEntityId(DEFAULT_EVENT_ENTITY_ID)
            .isSuspecious(DEFAULT_IS_SUSPECIOUS)
            .callerEmail(DEFAULT_CALLER_EMAIL)
            .callerId(DEFAULT_CALLER_ID)
            .clientId(DEFAULT_CLIENT_ID);
        return systemEventsHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemEventsHistory createUpdatedEntity(EntityManager em) {
        SystemEventsHistory systemEventsHistory = new SystemEventsHistory()
            .eventName(UPDATED_EVENT_NAME)
            .eventDate(UPDATED_EVENT_DATE)
            .eventApi(UPDATED_EVENT_API)
            .ipAddress(UPDATED_IP_ADDRESS)
            .eventNote(UPDATED_EVENT_NOTE)
            .eventEntityName(UPDATED_EVENT_ENTITY_NAME)
            .eventEntityId(UPDATED_EVENT_ENTITY_ID)
            .isSuspecious(UPDATED_IS_SUSPECIOUS)
            .callerEmail(UPDATED_CALLER_EMAIL)
            .callerId(UPDATED_CALLER_ID)
            .clientId(UPDATED_CLIENT_ID);
        return systemEventsHistory;
    }

    @BeforeEach
    public void initTest() {
        systemEventsHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemEventsHistory() throws Exception {
        int databaseSizeBeforeCreate = systemEventsHistoryRepository.findAll().size();
        // Create the SystemEventsHistory
        SystemEventsHistoryDTO systemEventsHistoryDTO = systemEventsHistoryMapper.toDto(systemEventsHistory);
        restSystemEventsHistoryMockMvc.perform(post("/api/system-events-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemEventsHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemEventsHistory in the database
        List<SystemEventsHistory> systemEventsHistoryList = systemEventsHistoryRepository.findAll();
        assertThat(systemEventsHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        SystemEventsHistory testSystemEventsHistory = systemEventsHistoryList.get(systemEventsHistoryList.size() - 1);
        assertThat(testSystemEventsHistory.getEventName()).isEqualTo(DEFAULT_EVENT_NAME);
        assertThat(testSystemEventsHistory.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
        assertThat(testSystemEventsHistory.getEventApi()).isEqualTo(DEFAULT_EVENT_API);
        assertThat(testSystemEventsHistory.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testSystemEventsHistory.getEventNote()).isEqualTo(DEFAULT_EVENT_NOTE);
        assertThat(testSystemEventsHistory.getEventEntityName()).isEqualTo(DEFAULT_EVENT_ENTITY_NAME);
        assertThat(testSystemEventsHistory.getEventEntityId()).isEqualTo(DEFAULT_EVENT_ENTITY_ID);
        assertThat(testSystemEventsHistory.isIsSuspecious()).isEqualTo(DEFAULT_IS_SUSPECIOUS);
        assertThat(testSystemEventsHistory.getCallerEmail()).isEqualTo(DEFAULT_CALLER_EMAIL);
        assertThat(testSystemEventsHistory.getCallerId()).isEqualTo(DEFAULT_CALLER_ID);
        assertThat(testSystemEventsHistory.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void createSystemEventsHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemEventsHistoryRepository.findAll().size();

        // Create the SystemEventsHistory with an existing ID
        systemEventsHistory.setId(1L);
        SystemEventsHistoryDTO systemEventsHistoryDTO = systemEventsHistoryMapper.toDto(systemEventsHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemEventsHistoryMockMvc.perform(post("/api/system-events-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemEventsHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemEventsHistory in the database
        List<SystemEventsHistory> systemEventsHistoryList = systemEventsHistoryRepository.findAll();
        assertThat(systemEventsHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEventNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemEventsHistoryRepository.findAll().size();
        // set the field null
        systemEventsHistory.setEventName(null);

        // Create the SystemEventsHistory, which fails.
        SystemEventsHistoryDTO systemEventsHistoryDTO = systemEventsHistoryMapper.toDto(systemEventsHistory);


        restSystemEventsHistoryMockMvc.perform(post("/api/system-events-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemEventsHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<SystemEventsHistory> systemEventsHistoryList = systemEventsHistoryRepository.findAll();
        assertThat(systemEventsHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistories() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList
        restSystemEventsHistoryMockMvc.perform(get("/api/system-events-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemEventsHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME)))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(sameInstant(DEFAULT_EVENT_DATE))))
            .andExpect(jsonPath("$.[*].eventApi").value(hasItem(DEFAULT_EVENT_API)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].eventNote").value(hasItem(DEFAULT_EVENT_NOTE)))
            .andExpect(jsonPath("$.[*].eventEntityName").value(hasItem(DEFAULT_EVENT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].eventEntityId").value(hasItem(DEFAULT_EVENT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].isSuspecious").value(hasItem(DEFAULT_IS_SUSPECIOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].callerEmail").value(hasItem(DEFAULT_CALLER_EMAIL)))
            .andExpect(jsonPath("$.[*].callerId").value(hasItem(DEFAULT_CALLER_ID.intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getSystemEventsHistory() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get the systemEventsHistory
        restSystemEventsHistoryMockMvc.perform(get("/api/system-events-histories/{id}", systemEventsHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemEventsHistory.getId().intValue()))
            .andExpect(jsonPath("$.eventName").value(DEFAULT_EVENT_NAME))
            .andExpect(jsonPath("$.eventDate").value(sameInstant(DEFAULT_EVENT_DATE)))
            .andExpect(jsonPath("$.eventApi").value(DEFAULT_EVENT_API))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.eventNote").value(DEFAULT_EVENT_NOTE))
            .andExpect(jsonPath("$.eventEntityName").value(DEFAULT_EVENT_ENTITY_NAME))
            .andExpect(jsonPath("$.eventEntityId").value(DEFAULT_EVENT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.isSuspecious").value(DEFAULT_IS_SUSPECIOUS.booleanValue()))
            .andExpect(jsonPath("$.callerEmail").value(DEFAULT_CALLER_EMAIL))
            .andExpect(jsonPath("$.callerId").value(DEFAULT_CALLER_ID.intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getSystemEventsHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        Long id = systemEventsHistory.getId();

        defaultSystemEventsHistoryShouldBeFound("id.equals=" + id);
        defaultSystemEventsHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultSystemEventsHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSystemEventsHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultSystemEventsHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSystemEventsHistoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNameIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventName equals to DEFAULT_EVENT_NAME
        defaultSystemEventsHistoryShouldBeFound("eventName.equals=" + DEFAULT_EVENT_NAME);

        // Get all the systemEventsHistoryList where eventName equals to UPDATED_EVENT_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventName.equals=" + UPDATED_EVENT_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventName not equals to DEFAULT_EVENT_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventName.notEquals=" + DEFAULT_EVENT_NAME);

        // Get all the systemEventsHistoryList where eventName not equals to UPDATED_EVENT_NAME
        defaultSystemEventsHistoryShouldBeFound("eventName.notEquals=" + UPDATED_EVENT_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNameIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventName in DEFAULT_EVENT_NAME or UPDATED_EVENT_NAME
        defaultSystemEventsHistoryShouldBeFound("eventName.in=" + DEFAULT_EVENT_NAME + "," + UPDATED_EVENT_NAME);

        // Get all the systemEventsHistoryList where eventName equals to UPDATED_EVENT_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventName.in=" + UPDATED_EVENT_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventName is not null
        defaultSystemEventsHistoryShouldBeFound("eventName.specified=true");

        // Get all the systemEventsHistoryList where eventName is null
        defaultSystemEventsHistoryShouldNotBeFound("eventName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNameContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventName contains DEFAULT_EVENT_NAME
        defaultSystemEventsHistoryShouldBeFound("eventName.contains=" + DEFAULT_EVENT_NAME);

        // Get all the systemEventsHistoryList where eventName contains UPDATED_EVENT_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventName.contains=" + UPDATED_EVENT_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNameNotContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventName does not contain DEFAULT_EVENT_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventName.doesNotContain=" + DEFAULT_EVENT_NAME);

        // Get all the systemEventsHistoryList where eventName does not contain UPDATED_EVENT_NAME
        defaultSystemEventsHistoryShouldBeFound("eventName.doesNotContain=" + UPDATED_EVENT_NAME);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventDateIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventDate equals to DEFAULT_EVENT_DATE
        defaultSystemEventsHistoryShouldBeFound("eventDate.equals=" + DEFAULT_EVENT_DATE);

        // Get all the systemEventsHistoryList where eventDate equals to UPDATED_EVENT_DATE
        defaultSystemEventsHistoryShouldNotBeFound("eventDate.equals=" + UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventDate not equals to DEFAULT_EVENT_DATE
        defaultSystemEventsHistoryShouldNotBeFound("eventDate.notEquals=" + DEFAULT_EVENT_DATE);

        // Get all the systemEventsHistoryList where eventDate not equals to UPDATED_EVENT_DATE
        defaultSystemEventsHistoryShouldBeFound("eventDate.notEquals=" + UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventDateIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventDate in DEFAULT_EVENT_DATE or UPDATED_EVENT_DATE
        defaultSystemEventsHistoryShouldBeFound("eventDate.in=" + DEFAULT_EVENT_DATE + "," + UPDATED_EVENT_DATE);

        // Get all the systemEventsHistoryList where eventDate equals to UPDATED_EVENT_DATE
        defaultSystemEventsHistoryShouldNotBeFound("eventDate.in=" + UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventDate is not null
        defaultSystemEventsHistoryShouldBeFound("eventDate.specified=true");

        // Get all the systemEventsHistoryList where eventDate is null
        defaultSystemEventsHistoryShouldNotBeFound("eventDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventDate is greater than or equal to DEFAULT_EVENT_DATE
        defaultSystemEventsHistoryShouldBeFound("eventDate.greaterThanOrEqual=" + DEFAULT_EVENT_DATE);

        // Get all the systemEventsHistoryList where eventDate is greater than or equal to UPDATED_EVENT_DATE
        defaultSystemEventsHistoryShouldNotBeFound("eventDate.greaterThanOrEqual=" + UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventDate is less than or equal to DEFAULT_EVENT_DATE
        defaultSystemEventsHistoryShouldBeFound("eventDate.lessThanOrEqual=" + DEFAULT_EVENT_DATE);

        // Get all the systemEventsHistoryList where eventDate is less than or equal to SMALLER_EVENT_DATE
        defaultSystemEventsHistoryShouldNotBeFound("eventDate.lessThanOrEqual=" + SMALLER_EVENT_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventDateIsLessThanSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventDate is less than DEFAULT_EVENT_DATE
        defaultSystemEventsHistoryShouldNotBeFound("eventDate.lessThan=" + DEFAULT_EVENT_DATE);

        // Get all the systemEventsHistoryList where eventDate is less than UPDATED_EVENT_DATE
        defaultSystemEventsHistoryShouldBeFound("eventDate.lessThan=" + UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventDate is greater than DEFAULT_EVENT_DATE
        defaultSystemEventsHistoryShouldNotBeFound("eventDate.greaterThan=" + DEFAULT_EVENT_DATE);

        // Get all the systemEventsHistoryList where eventDate is greater than SMALLER_EVENT_DATE
        defaultSystemEventsHistoryShouldBeFound("eventDate.greaterThan=" + SMALLER_EVENT_DATE);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventApiIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventApi equals to DEFAULT_EVENT_API
        defaultSystemEventsHistoryShouldBeFound("eventApi.equals=" + DEFAULT_EVENT_API);

        // Get all the systemEventsHistoryList where eventApi equals to UPDATED_EVENT_API
        defaultSystemEventsHistoryShouldNotBeFound("eventApi.equals=" + UPDATED_EVENT_API);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventApiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventApi not equals to DEFAULT_EVENT_API
        defaultSystemEventsHistoryShouldNotBeFound("eventApi.notEquals=" + DEFAULT_EVENT_API);

        // Get all the systemEventsHistoryList where eventApi not equals to UPDATED_EVENT_API
        defaultSystemEventsHistoryShouldBeFound("eventApi.notEquals=" + UPDATED_EVENT_API);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventApiIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventApi in DEFAULT_EVENT_API or UPDATED_EVENT_API
        defaultSystemEventsHistoryShouldBeFound("eventApi.in=" + DEFAULT_EVENT_API + "," + UPDATED_EVENT_API);

        // Get all the systemEventsHistoryList where eventApi equals to UPDATED_EVENT_API
        defaultSystemEventsHistoryShouldNotBeFound("eventApi.in=" + UPDATED_EVENT_API);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventApiIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventApi is not null
        defaultSystemEventsHistoryShouldBeFound("eventApi.specified=true");

        // Get all the systemEventsHistoryList where eventApi is null
        defaultSystemEventsHistoryShouldNotBeFound("eventApi.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventApiContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventApi contains DEFAULT_EVENT_API
        defaultSystemEventsHistoryShouldBeFound("eventApi.contains=" + DEFAULT_EVENT_API);

        // Get all the systemEventsHistoryList where eventApi contains UPDATED_EVENT_API
        defaultSystemEventsHistoryShouldNotBeFound("eventApi.contains=" + UPDATED_EVENT_API);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventApiNotContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventApi does not contain DEFAULT_EVENT_API
        defaultSystemEventsHistoryShouldNotBeFound("eventApi.doesNotContain=" + DEFAULT_EVENT_API);

        // Get all the systemEventsHistoryList where eventApi does not contain UPDATED_EVENT_API
        defaultSystemEventsHistoryShouldBeFound("eventApi.doesNotContain=" + UPDATED_EVENT_API);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIpAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where ipAddress equals to DEFAULT_IP_ADDRESS
        defaultSystemEventsHistoryShouldBeFound("ipAddress.equals=" + DEFAULT_IP_ADDRESS);

        // Get all the systemEventsHistoryList where ipAddress equals to UPDATED_IP_ADDRESS
        defaultSystemEventsHistoryShouldNotBeFound("ipAddress.equals=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIpAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where ipAddress not equals to DEFAULT_IP_ADDRESS
        defaultSystemEventsHistoryShouldNotBeFound("ipAddress.notEquals=" + DEFAULT_IP_ADDRESS);

        // Get all the systemEventsHistoryList where ipAddress not equals to UPDATED_IP_ADDRESS
        defaultSystemEventsHistoryShouldBeFound("ipAddress.notEquals=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIpAddressIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where ipAddress in DEFAULT_IP_ADDRESS or UPDATED_IP_ADDRESS
        defaultSystemEventsHistoryShouldBeFound("ipAddress.in=" + DEFAULT_IP_ADDRESS + "," + UPDATED_IP_ADDRESS);

        // Get all the systemEventsHistoryList where ipAddress equals to UPDATED_IP_ADDRESS
        defaultSystemEventsHistoryShouldNotBeFound("ipAddress.in=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIpAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where ipAddress is not null
        defaultSystemEventsHistoryShouldBeFound("ipAddress.specified=true");

        // Get all the systemEventsHistoryList where ipAddress is null
        defaultSystemEventsHistoryShouldNotBeFound("ipAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIpAddressContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where ipAddress contains DEFAULT_IP_ADDRESS
        defaultSystemEventsHistoryShouldBeFound("ipAddress.contains=" + DEFAULT_IP_ADDRESS);

        // Get all the systemEventsHistoryList where ipAddress contains UPDATED_IP_ADDRESS
        defaultSystemEventsHistoryShouldNotBeFound("ipAddress.contains=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIpAddressNotContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where ipAddress does not contain DEFAULT_IP_ADDRESS
        defaultSystemEventsHistoryShouldNotBeFound("ipAddress.doesNotContain=" + DEFAULT_IP_ADDRESS);

        // Get all the systemEventsHistoryList where ipAddress does not contain UPDATED_IP_ADDRESS
        defaultSystemEventsHistoryShouldBeFound("ipAddress.doesNotContain=" + UPDATED_IP_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventNote equals to DEFAULT_EVENT_NOTE
        defaultSystemEventsHistoryShouldBeFound("eventNote.equals=" + DEFAULT_EVENT_NOTE);

        // Get all the systemEventsHistoryList where eventNote equals to UPDATED_EVENT_NOTE
        defaultSystemEventsHistoryShouldNotBeFound("eventNote.equals=" + UPDATED_EVENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventNote not equals to DEFAULT_EVENT_NOTE
        defaultSystemEventsHistoryShouldNotBeFound("eventNote.notEquals=" + DEFAULT_EVENT_NOTE);

        // Get all the systemEventsHistoryList where eventNote not equals to UPDATED_EVENT_NOTE
        defaultSystemEventsHistoryShouldBeFound("eventNote.notEquals=" + UPDATED_EVENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNoteIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventNote in DEFAULT_EVENT_NOTE or UPDATED_EVENT_NOTE
        defaultSystemEventsHistoryShouldBeFound("eventNote.in=" + DEFAULT_EVENT_NOTE + "," + UPDATED_EVENT_NOTE);

        // Get all the systemEventsHistoryList where eventNote equals to UPDATED_EVENT_NOTE
        defaultSystemEventsHistoryShouldNotBeFound("eventNote.in=" + UPDATED_EVENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventNote is not null
        defaultSystemEventsHistoryShouldBeFound("eventNote.specified=true");

        // Get all the systemEventsHistoryList where eventNote is null
        defaultSystemEventsHistoryShouldNotBeFound("eventNote.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNoteContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventNote contains DEFAULT_EVENT_NOTE
        defaultSystemEventsHistoryShouldBeFound("eventNote.contains=" + DEFAULT_EVENT_NOTE);

        // Get all the systemEventsHistoryList where eventNote contains UPDATED_EVENT_NOTE
        defaultSystemEventsHistoryShouldNotBeFound("eventNote.contains=" + UPDATED_EVENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventNoteNotContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventNote does not contain DEFAULT_EVENT_NOTE
        defaultSystemEventsHistoryShouldNotBeFound("eventNote.doesNotContain=" + DEFAULT_EVENT_NOTE);

        // Get all the systemEventsHistoryList where eventNote does not contain UPDATED_EVENT_NOTE
        defaultSystemEventsHistoryShouldBeFound("eventNote.doesNotContain=" + UPDATED_EVENT_NOTE);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityName equals to DEFAULT_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldBeFound("eventEntityName.equals=" + DEFAULT_EVENT_ENTITY_NAME);

        // Get all the systemEventsHistoryList where eventEntityName equals to UPDATED_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityName.equals=" + UPDATED_EVENT_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityName not equals to DEFAULT_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityName.notEquals=" + DEFAULT_EVENT_ENTITY_NAME);

        // Get all the systemEventsHistoryList where eventEntityName not equals to UPDATED_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldBeFound("eventEntityName.notEquals=" + UPDATED_EVENT_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityNameIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityName in DEFAULT_EVENT_ENTITY_NAME or UPDATED_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldBeFound("eventEntityName.in=" + DEFAULT_EVENT_ENTITY_NAME + "," + UPDATED_EVENT_ENTITY_NAME);

        // Get all the systemEventsHistoryList where eventEntityName equals to UPDATED_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityName.in=" + UPDATED_EVENT_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityName is not null
        defaultSystemEventsHistoryShouldBeFound("eventEntityName.specified=true");

        // Get all the systemEventsHistoryList where eventEntityName is null
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityNameContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityName contains DEFAULT_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldBeFound("eventEntityName.contains=" + DEFAULT_EVENT_ENTITY_NAME);

        // Get all the systemEventsHistoryList where eventEntityName contains UPDATED_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityName.contains=" + UPDATED_EVENT_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityNameNotContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityName does not contain DEFAULT_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityName.doesNotContain=" + DEFAULT_EVENT_ENTITY_NAME);

        // Get all the systemEventsHistoryList where eventEntityName does not contain UPDATED_EVENT_ENTITY_NAME
        defaultSystemEventsHistoryShouldBeFound("eventEntityName.doesNotContain=" + UPDATED_EVENT_ENTITY_NAME);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityId equals to DEFAULT_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldBeFound("eventEntityId.equals=" + DEFAULT_EVENT_ENTITY_ID);

        // Get all the systemEventsHistoryList where eventEntityId equals to UPDATED_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityId.equals=" + UPDATED_EVENT_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityId not equals to DEFAULT_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityId.notEquals=" + DEFAULT_EVENT_ENTITY_ID);

        // Get all the systemEventsHistoryList where eventEntityId not equals to UPDATED_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldBeFound("eventEntityId.notEquals=" + UPDATED_EVENT_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityId in DEFAULT_EVENT_ENTITY_ID or UPDATED_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldBeFound("eventEntityId.in=" + DEFAULT_EVENT_ENTITY_ID + "," + UPDATED_EVENT_ENTITY_ID);

        // Get all the systemEventsHistoryList where eventEntityId equals to UPDATED_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityId.in=" + UPDATED_EVENT_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityId is not null
        defaultSystemEventsHistoryShouldBeFound("eventEntityId.specified=true");

        // Get all the systemEventsHistoryList where eventEntityId is null
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityId.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityId is greater than or equal to DEFAULT_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldBeFound("eventEntityId.greaterThanOrEqual=" + DEFAULT_EVENT_ENTITY_ID);

        // Get all the systemEventsHistoryList where eventEntityId is greater than or equal to UPDATED_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityId.greaterThanOrEqual=" + UPDATED_EVENT_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityId is less than or equal to DEFAULT_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldBeFound("eventEntityId.lessThanOrEqual=" + DEFAULT_EVENT_ENTITY_ID);

        // Get all the systemEventsHistoryList where eventEntityId is less than or equal to SMALLER_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityId.lessThanOrEqual=" + SMALLER_EVENT_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityId is less than DEFAULT_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityId.lessThan=" + DEFAULT_EVENT_ENTITY_ID);

        // Get all the systemEventsHistoryList where eventEntityId is less than UPDATED_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldBeFound("eventEntityId.lessThan=" + UPDATED_EVENT_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByEventEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where eventEntityId is greater than DEFAULT_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldNotBeFound("eventEntityId.greaterThan=" + DEFAULT_EVENT_ENTITY_ID);

        // Get all the systemEventsHistoryList where eventEntityId is greater than SMALLER_EVENT_ENTITY_ID
        defaultSystemEventsHistoryShouldBeFound("eventEntityId.greaterThan=" + SMALLER_EVENT_ENTITY_ID);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIsSuspeciousIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where isSuspecious equals to DEFAULT_IS_SUSPECIOUS
        defaultSystemEventsHistoryShouldBeFound("isSuspecious.equals=" + DEFAULT_IS_SUSPECIOUS);

        // Get all the systemEventsHistoryList where isSuspecious equals to UPDATED_IS_SUSPECIOUS
        defaultSystemEventsHistoryShouldNotBeFound("isSuspecious.equals=" + UPDATED_IS_SUSPECIOUS);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIsSuspeciousIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where isSuspecious not equals to DEFAULT_IS_SUSPECIOUS
        defaultSystemEventsHistoryShouldNotBeFound("isSuspecious.notEquals=" + DEFAULT_IS_SUSPECIOUS);

        // Get all the systemEventsHistoryList where isSuspecious not equals to UPDATED_IS_SUSPECIOUS
        defaultSystemEventsHistoryShouldBeFound("isSuspecious.notEquals=" + UPDATED_IS_SUSPECIOUS);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIsSuspeciousIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where isSuspecious in DEFAULT_IS_SUSPECIOUS or UPDATED_IS_SUSPECIOUS
        defaultSystemEventsHistoryShouldBeFound("isSuspecious.in=" + DEFAULT_IS_SUSPECIOUS + "," + UPDATED_IS_SUSPECIOUS);

        // Get all the systemEventsHistoryList where isSuspecious equals to UPDATED_IS_SUSPECIOUS
        defaultSystemEventsHistoryShouldNotBeFound("isSuspecious.in=" + UPDATED_IS_SUSPECIOUS);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByIsSuspeciousIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where isSuspecious is not null
        defaultSystemEventsHistoryShouldBeFound("isSuspecious.specified=true");

        // Get all the systemEventsHistoryList where isSuspecious is null
        defaultSystemEventsHistoryShouldNotBeFound("isSuspecious.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerEmail equals to DEFAULT_CALLER_EMAIL
        defaultSystemEventsHistoryShouldBeFound("callerEmail.equals=" + DEFAULT_CALLER_EMAIL);

        // Get all the systemEventsHistoryList where callerEmail equals to UPDATED_CALLER_EMAIL
        defaultSystemEventsHistoryShouldNotBeFound("callerEmail.equals=" + UPDATED_CALLER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerEmail not equals to DEFAULT_CALLER_EMAIL
        defaultSystemEventsHistoryShouldNotBeFound("callerEmail.notEquals=" + DEFAULT_CALLER_EMAIL);

        // Get all the systemEventsHistoryList where callerEmail not equals to UPDATED_CALLER_EMAIL
        defaultSystemEventsHistoryShouldBeFound("callerEmail.notEquals=" + UPDATED_CALLER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerEmailIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerEmail in DEFAULT_CALLER_EMAIL or UPDATED_CALLER_EMAIL
        defaultSystemEventsHistoryShouldBeFound("callerEmail.in=" + DEFAULT_CALLER_EMAIL + "," + UPDATED_CALLER_EMAIL);

        // Get all the systemEventsHistoryList where callerEmail equals to UPDATED_CALLER_EMAIL
        defaultSystemEventsHistoryShouldNotBeFound("callerEmail.in=" + UPDATED_CALLER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerEmail is not null
        defaultSystemEventsHistoryShouldBeFound("callerEmail.specified=true");

        // Get all the systemEventsHistoryList where callerEmail is null
        defaultSystemEventsHistoryShouldNotBeFound("callerEmail.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerEmailContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerEmail contains DEFAULT_CALLER_EMAIL
        defaultSystemEventsHistoryShouldBeFound("callerEmail.contains=" + DEFAULT_CALLER_EMAIL);

        // Get all the systemEventsHistoryList where callerEmail contains UPDATED_CALLER_EMAIL
        defaultSystemEventsHistoryShouldNotBeFound("callerEmail.contains=" + UPDATED_CALLER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerEmailNotContainsSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerEmail does not contain DEFAULT_CALLER_EMAIL
        defaultSystemEventsHistoryShouldNotBeFound("callerEmail.doesNotContain=" + DEFAULT_CALLER_EMAIL);

        // Get all the systemEventsHistoryList where callerEmail does not contain UPDATED_CALLER_EMAIL
        defaultSystemEventsHistoryShouldBeFound("callerEmail.doesNotContain=" + UPDATED_CALLER_EMAIL);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerId equals to DEFAULT_CALLER_ID
        defaultSystemEventsHistoryShouldBeFound("callerId.equals=" + DEFAULT_CALLER_ID);

        // Get all the systemEventsHistoryList where callerId equals to UPDATED_CALLER_ID
        defaultSystemEventsHistoryShouldNotBeFound("callerId.equals=" + UPDATED_CALLER_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerId not equals to DEFAULT_CALLER_ID
        defaultSystemEventsHistoryShouldNotBeFound("callerId.notEquals=" + DEFAULT_CALLER_ID);

        // Get all the systemEventsHistoryList where callerId not equals to UPDATED_CALLER_ID
        defaultSystemEventsHistoryShouldBeFound("callerId.notEquals=" + UPDATED_CALLER_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerIdIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerId in DEFAULT_CALLER_ID or UPDATED_CALLER_ID
        defaultSystemEventsHistoryShouldBeFound("callerId.in=" + DEFAULT_CALLER_ID + "," + UPDATED_CALLER_ID);

        // Get all the systemEventsHistoryList where callerId equals to UPDATED_CALLER_ID
        defaultSystemEventsHistoryShouldNotBeFound("callerId.in=" + UPDATED_CALLER_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerId is not null
        defaultSystemEventsHistoryShouldBeFound("callerId.specified=true");

        // Get all the systemEventsHistoryList where callerId is null
        defaultSystemEventsHistoryShouldNotBeFound("callerId.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerId is greater than or equal to DEFAULT_CALLER_ID
        defaultSystemEventsHistoryShouldBeFound("callerId.greaterThanOrEqual=" + DEFAULT_CALLER_ID);

        // Get all the systemEventsHistoryList where callerId is greater than or equal to UPDATED_CALLER_ID
        defaultSystemEventsHistoryShouldNotBeFound("callerId.greaterThanOrEqual=" + UPDATED_CALLER_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerId is less than or equal to DEFAULT_CALLER_ID
        defaultSystemEventsHistoryShouldBeFound("callerId.lessThanOrEqual=" + DEFAULT_CALLER_ID);

        // Get all the systemEventsHistoryList where callerId is less than or equal to SMALLER_CALLER_ID
        defaultSystemEventsHistoryShouldNotBeFound("callerId.lessThanOrEqual=" + SMALLER_CALLER_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerId is less than DEFAULT_CALLER_ID
        defaultSystemEventsHistoryShouldNotBeFound("callerId.lessThan=" + DEFAULT_CALLER_ID);

        // Get all the systemEventsHistoryList where callerId is less than UPDATED_CALLER_ID
        defaultSystemEventsHistoryShouldBeFound("callerId.lessThan=" + UPDATED_CALLER_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByCallerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where callerId is greater than DEFAULT_CALLER_ID
        defaultSystemEventsHistoryShouldNotBeFound("callerId.greaterThan=" + DEFAULT_CALLER_ID);

        // Get all the systemEventsHistoryList where callerId is greater than SMALLER_CALLER_ID
        defaultSystemEventsHistoryShouldBeFound("callerId.greaterThan=" + SMALLER_CALLER_ID);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where clientId equals to DEFAULT_CLIENT_ID
        defaultSystemEventsHistoryShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the systemEventsHistoryList where clientId equals to UPDATED_CLIENT_ID
        defaultSystemEventsHistoryShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where clientId not equals to DEFAULT_CLIENT_ID
        defaultSystemEventsHistoryShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the systemEventsHistoryList where clientId not equals to UPDATED_CLIENT_ID
        defaultSystemEventsHistoryShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultSystemEventsHistoryShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the systemEventsHistoryList where clientId equals to UPDATED_CLIENT_ID
        defaultSystemEventsHistoryShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where clientId is not null
        defaultSystemEventsHistoryShouldBeFound("clientId.specified=true");

        // Get all the systemEventsHistoryList where clientId is null
        defaultSystemEventsHistoryShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultSystemEventsHistoryShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the systemEventsHistoryList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultSystemEventsHistoryShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultSystemEventsHistoryShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the systemEventsHistoryList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultSystemEventsHistoryShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where clientId is less than DEFAULT_CLIENT_ID
        defaultSystemEventsHistoryShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the systemEventsHistoryList where clientId is less than UPDATED_CLIENT_ID
        defaultSystemEventsHistoryShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        // Get all the systemEventsHistoryList where clientId is greater than DEFAULT_CLIENT_ID
        defaultSystemEventsHistoryShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the systemEventsHistoryList where clientId is greater than SMALLER_CLIENT_ID
        defaultSystemEventsHistoryShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllSystemEventsHistoriesByTriggedByIsEqualToSomething() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);
        User triggedBy = UserResourceIT.createEntity(em);
        em.persist(triggedBy);
        em.flush();
        systemEventsHistory.setTriggedBy(triggedBy);
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);
        Long triggedById = triggedBy.getId();

        // Get all the systemEventsHistoryList where triggedBy equals to triggedById
        defaultSystemEventsHistoryShouldBeFound("triggedById.equals=" + triggedById);

        // Get all the systemEventsHistoryList where triggedBy equals to triggedById + 1
        defaultSystemEventsHistoryShouldNotBeFound("triggedById.equals=" + (triggedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSystemEventsHistoryShouldBeFound(String filter) throws Exception {
        restSystemEventsHistoryMockMvc.perform(get("/api/system-events-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemEventsHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME)))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(sameInstant(DEFAULT_EVENT_DATE))))
            .andExpect(jsonPath("$.[*].eventApi").value(hasItem(DEFAULT_EVENT_API)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].eventNote").value(hasItem(DEFAULT_EVENT_NOTE)))
            .andExpect(jsonPath("$.[*].eventEntityName").value(hasItem(DEFAULT_EVENT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].eventEntityId").value(hasItem(DEFAULT_EVENT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].isSuspecious").value(hasItem(DEFAULT_IS_SUSPECIOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].callerEmail").value(hasItem(DEFAULT_CALLER_EMAIL)))
            .andExpect(jsonPath("$.[*].callerId").value(hasItem(DEFAULT_CALLER_ID.intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));

        // Check, that the count call also returns 1
        restSystemEventsHistoryMockMvc.perform(get("/api/system-events-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSystemEventsHistoryShouldNotBeFound(String filter) throws Exception {
        restSystemEventsHistoryMockMvc.perform(get("/api/system-events-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemEventsHistoryMockMvc.perform(get("/api/system-events-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSystemEventsHistory() throws Exception {
        // Get the systemEventsHistory
        restSystemEventsHistoryMockMvc.perform(get("/api/system-events-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemEventsHistory() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        int databaseSizeBeforeUpdate = systemEventsHistoryRepository.findAll().size();

        // Update the systemEventsHistory
        SystemEventsHistory updatedSystemEventsHistory = systemEventsHistoryRepository.findById(systemEventsHistory.getId()).get();
        // Disconnect from session so that the updates on updatedSystemEventsHistory are not directly saved in db
        em.detach(updatedSystemEventsHistory);
        updatedSystemEventsHistory
            .eventName(UPDATED_EVENT_NAME)
            .eventDate(UPDATED_EVENT_DATE)
            .eventApi(UPDATED_EVENT_API)
            .ipAddress(UPDATED_IP_ADDRESS)
            .eventNote(UPDATED_EVENT_NOTE)
            .eventEntityName(UPDATED_EVENT_ENTITY_NAME)
            .eventEntityId(UPDATED_EVENT_ENTITY_ID)
            .isSuspecious(UPDATED_IS_SUSPECIOUS)
            .callerEmail(UPDATED_CALLER_EMAIL)
            .callerId(UPDATED_CALLER_ID)
            .clientId(UPDATED_CLIENT_ID);
        SystemEventsHistoryDTO systemEventsHistoryDTO = systemEventsHistoryMapper.toDto(updatedSystemEventsHistory);

        restSystemEventsHistoryMockMvc.perform(put("/api/system-events-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemEventsHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the SystemEventsHistory in the database
        List<SystemEventsHistory> systemEventsHistoryList = systemEventsHistoryRepository.findAll();
        assertThat(systemEventsHistoryList).hasSize(databaseSizeBeforeUpdate);
        SystemEventsHistory testSystemEventsHistory = systemEventsHistoryList.get(systemEventsHistoryList.size() - 1);
        assertThat(testSystemEventsHistory.getEventName()).isEqualTo(UPDATED_EVENT_NAME);
        assertThat(testSystemEventsHistory.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testSystemEventsHistory.getEventApi()).isEqualTo(UPDATED_EVENT_API);
        assertThat(testSystemEventsHistory.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testSystemEventsHistory.getEventNote()).isEqualTo(UPDATED_EVENT_NOTE);
        assertThat(testSystemEventsHistory.getEventEntityName()).isEqualTo(UPDATED_EVENT_ENTITY_NAME);
        assertThat(testSystemEventsHistory.getEventEntityId()).isEqualTo(UPDATED_EVENT_ENTITY_ID);
        assertThat(testSystemEventsHistory.isIsSuspecious()).isEqualTo(UPDATED_IS_SUSPECIOUS);
        assertThat(testSystemEventsHistory.getCallerEmail()).isEqualTo(UPDATED_CALLER_EMAIL);
        assertThat(testSystemEventsHistory.getCallerId()).isEqualTo(UPDATED_CALLER_ID);
        assertThat(testSystemEventsHistory.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemEventsHistory() throws Exception {
        int databaseSizeBeforeUpdate = systemEventsHistoryRepository.findAll().size();

        // Create the SystemEventsHistory
        SystemEventsHistoryDTO systemEventsHistoryDTO = systemEventsHistoryMapper.toDto(systemEventsHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemEventsHistoryMockMvc.perform(put("/api/system-events-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemEventsHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemEventsHistory in the database
        List<SystemEventsHistory> systemEventsHistoryList = systemEventsHistoryRepository.findAll();
        assertThat(systemEventsHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystemEventsHistory() throws Exception {
        // Initialize the database
        systemEventsHistoryRepository.saveAndFlush(systemEventsHistory);

        int databaseSizeBeforeDelete = systemEventsHistoryRepository.findAll().size();

        // Delete the systemEventsHistory
        restSystemEventsHistoryMockMvc.perform(delete("/api/system-events-histories/{id}", systemEventsHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemEventsHistory> systemEventsHistoryList = systemEventsHistoryRepository.findAll();
        assertThat(systemEventsHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
