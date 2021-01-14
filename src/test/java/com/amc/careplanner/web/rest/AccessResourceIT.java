package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Access;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.AccessRepository;
import com.amc.careplanner.service.AccessService;
import com.amc.careplanner.service.dto.AccessDTO;
import com.amc.careplanner.service.mapper.AccessMapper;
import com.amc.careplanner.service.dto.AccessCriteria;
import com.amc.careplanner.service.AccessQueryService;

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
 * Integration tests for the {@link AccessResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccessResourceIT {

    private static final String DEFAULT_KEY_SAFE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_KEY_SAFE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_DETAILS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private AccessMapper accessMapper;

    @Autowired
    private AccessService accessService;

    @Autowired
    private AccessQueryService accessQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccessMockMvc;

    private Access access;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Access createEntity(EntityManager em) {
        Access access = new Access()
            .keySafeNumber(DEFAULT_KEY_SAFE_NUMBER)
            .accessDetails(DEFAULT_ACCESS_DETAILS)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return access;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Access createUpdatedEntity(EntityManager em) {
        Access access = new Access()
            .keySafeNumber(UPDATED_KEY_SAFE_NUMBER)
            .accessDetails(UPDATED_ACCESS_DETAILS)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return access;
    }

    @BeforeEach
    public void initTest() {
        access = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccess() throws Exception {
        int databaseSizeBeforeCreate = accessRepository.findAll().size();
        // Create the Access
        AccessDTO accessDTO = accessMapper.toDto(access);
        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isCreated());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeCreate + 1);
        Access testAccess = accessList.get(accessList.size() - 1);
        assertThat(testAccess.getKeySafeNumber()).isEqualTo(DEFAULT_KEY_SAFE_NUMBER);
        assertThat(testAccess.getAccessDetails()).isEqualTo(DEFAULT_ACCESS_DETAILS);
        assertThat(testAccess.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAccess.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testAccess.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testAccess.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accessRepository.findAll().size();

        // Create the Access with an existing ID
        access.setId(1L);
        AccessDTO accessDTO = accessMapper.toDto(access);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeySafeNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = accessRepository.findAll().size();
        // set the field null
        access.setKeySafeNumber(null);

        // Create the Access, which fails.
        AccessDTO accessDTO = accessMapper.toDto(access);


        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isBadRequest());

        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = accessRepository.findAll().size();
        // set the field null
        access.setClientId(null);

        // Create the Access, which fails.
        AccessDTO accessDTO = accessMapper.toDto(access);


        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isBadRequest());

        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccesses() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList
        restAccessMockMvc.perform(get("/api/accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(access.getId().intValue())))
            .andExpect(jsonPath("$.[*].keySafeNumber").value(hasItem(DEFAULT_KEY_SAFE_NUMBER)))
            .andExpect(jsonPath("$.[*].accessDetails").value(hasItem(DEFAULT_ACCESS_DETAILS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAccess() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get the access
        restAccessMockMvc.perform(get("/api/accesses/{id}", access.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(access.getId().intValue()))
            .andExpect(jsonPath("$.keySafeNumber").value(DEFAULT_KEY_SAFE_NUMBER))
            .andExpect(jsonPath("$.accessDetails").value(DEFAULT_ACCESS_DETAILS))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getAccessesByIdFiltering() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        Long id = access.getId();

        defaultAccessShouldBeFound("id.equals=" + id);
        defaultAccessShouldNotBeFound("id.notEquals=" + id);

        defaultAccessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccessShouldNotBeFound("id.greaterThan=" + id);

        defaultAccessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccessShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber equals to DEFAULT_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.equals=" + DEFAULT_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber equals to UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.equals=" + UPDATED_KEY_SAFE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber not equals to DEFAULT_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.notEquals=" + DEFAULT_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber not equals to UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.notEquals=" + UPDATED_KEY_SAFE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber in DEFAULT_KEY_SAFE_NUMBER or UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.in=" + DEFAULT_KEY_SAFE_NUMBER + "," + UPDATED_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber equals to UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.in=" + UPDATED_KEY_SAFE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber is not null
        defaultAccessShouldBeFound("keySafeNumber.specified=true");

        // Get all the accessList where keySafeNumber is null
        defaultAccessShouldNotBeFound("keySafeNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberContainsSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber contains DEFAULT_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.contains=" + DEFAULT_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber contains UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.contains=" + UPDATED_KEY_SAFE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberNotContainsSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber does not contain DEFAULT_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.doesNotContain=" + DEFAULT_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber does not contain UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.doesNotContain=" + UPDATED_KEY_SAFE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails equals to DEFAULT_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.equals=" + DEFAULT_ACCESS_DETAILS);

        // Get all the accessList where accessDetails equals to UPDATED_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.equals=" + UPDATED_ACCESS_DETAILS);
    }

    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails not equals to DEFAULT_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.notEquals=" + DEFAULT_ACCESS_DETAILS);

        // Get all the accessList where accessDetails not equals to UPDATED_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.notEquals=" + UPDATED_ACCESS_DETAILS);
    }

    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails in DEFAULT_ACCESS_DETAILS or UPDATED_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.in=" + DEFAULT_ACCESS_DETAILS + "," + UPDATED_ACCESS_DETAILS);

        // Get all the accessList where accessDetails equals to UPDATED_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.in=" + UPDATED_ACCESS_DETAILS);
    }

    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails is not null
        defaultAccessShouldBeFound("accessDetails.specified=true");

        // Get all the accessList where accessDetails is null
        defaultAccessShouldNotBeFound("accessDetails.specified=false");
    }
                @Test
    @Transactional
    public void getAllAccessesByAccessDetailsContainsSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails contains DEFAULT_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.contains=" + DEFAULT_ACCESS_DETAILS);

        // Get all the accessList where accessDetails contains UPDATED_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.contains=" + UPDATED_ACCESS_DETAILS);
    }

    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails does not contain DEFAULT_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.doesNotContain=" + DEFAULT_ACCESS_DETAILS);

        // Get all the accessList where accessDetails does not contain UPDATED_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.doesNotContain=" + UPDATED_ACCESS_DETAILS);
    }


    @Test
    @Transactional
    public void getAllAccessesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where createdDate equals to DEFAULT_CREATED_DATE
        defaultAccessShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the accessList where createdDate equals to UPDATED_CREATED_DATE
        defaultAccessShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultAccessShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the accessList where createdDate not equals to UPDATED_CREATED_DATE
        defaultAccessShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultAccessShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the accessList where createdDate equals to UPDATED_CREATED_DATE
        defaultAccessShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where createdDate is not null
        defaultAccessShouldBeFound("createdDate.specified=true");

        // Get all the accessList where createdDate is null
        defaultAccessShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccessesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultAccessShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the accessList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultAccessShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultAccessShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the accessList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultAccessShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where createdDate is less than DEFAULT_CREATED_DATE
        defaultAccessShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the accessList where createdDate is less than UPDATED_CREATED_DATE
        defaultAccessShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultAccessShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the accessList where createdDate is greater than SMALLER_CREATED_DATE
        defaultAccessShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is not null
        defaultAccessShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the accessList where lastUpdatedDate is null
        defaultAccessShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllAccessesByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where clientId equals to DEFAULT_CLIENT_ID
        defaultAccessShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the accessList where clientId equals to UPDATED_CLIENT_ID
        defaultAccessShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where clientId not equals to DEFAULT_CLIENT_ID
        defaultAccessShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the accessList where clientId not equals to UPDATED_CLIENT_ID
        defaultAccessShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultAccessShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the accessList where clientId equals to UPDATED_CLIENT_ID
        defaultAccessShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where clientId is not null
        defaultAccessShouldBeFound("clientId.specified=true");

        // Get all the accessList where clientId is null
        defaultAccessShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccessesByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultAccessShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the accessList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultAccessShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultAccessShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the accessList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultAccessShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where clientId is less than DEFAULT_CLIENT_ID
        defaultAccessShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the accessList where clientId is less than UPDATED_CLIENT_ID
        defaultAccessShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where clientId is greater than DEFAULT_CLIENT_ID
        defaultAccessShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the accessList where clientId is greater than SMALLER_CLIENT_ID
        defaultAccessShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllAccessesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultAccessShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the accessList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultAccessShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllAccessesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultAccessShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the accessList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultAccessShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllAccessesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultAccessShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the accessList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultAccessShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllAccessesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where hasExtraData is not null
        defaultAccessShouldBeFound("hasExtraData.specified=true");

        // Get all the accessList where hasExtraData is null
        defaultAccessShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccessesByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        access.setServiceUser(serviceUser);
        accessRepository.saveAndFlush(access);
        Long serviceUserId = serviceUser.getId();

        // Get all the accessList where serviceUser equals to serviceUserId
        defaultAccessShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the accessList where serviceUser equals to serviceUserId + 1
        defaultAccessShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccessShouldBeFound(String filter) throws Exception {
        restAccessMockMvc.perform(get("/api/accesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(access.getId().intValue())))
            .andExpect(jsonPath("$.[*].keySafeNumber").value(hasItem(DEFAULT_KEY_SAFE_NUMBER)))
            .andExpect(jsonPath("$.[*].accessDetails").value(hasItem(DEFAULT_ACCESS_DETAILS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restAccessMockMvc.perform(get("/api/accesses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccessShouldNotBeFound(String filter) throws Exception {
        restAccessMockMvc.perform(get("/api/accesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccessMockMvc.perform(get("/api/accesses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAccess() throws Exception {
        // Get the access
        restAccessMockMvc.perform(get("/api/accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccess() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        int databaseSizeBeforeUpdate = accessRepository.findAll().size();

        // Update the access
        Access updatedAccess = accessRepository.findById(access.getId()).get();
        // Disconnect from session so that the updates on updatedAccess are not directly saved in db
        em.detach(updatedAccess);
        updatedAccess
            .keySafeNumber(UPDATED_KEY_SAFE_NUMBER)
            .accessDetails(UPDATED_ACCESS_DETAILS)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        AccessDTO accessDTO = accessMapper.toDto(updatedAccess);

        restAccessMockMvc.perform(put("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isOk());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeUpdate);
        Access testAccess = accessList.get(accessList.size() - 1);
        assertThat(testAccess.getKeySafeNumber()).isEqualTo(UPDATED_KEY_SAFE_NUMBER);
        assertThat(testAccess.getAccessDetails()).isEqualTo(UPDATED_ACCESS_DETAILS);
        assertThat(testAccess.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAccess.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testAccess.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testAccess.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingAccess() throws Exception {
        int databaseSizeBeforeUpdate = accessRepository.findAll().size();

        // Create the Access
        AccessDTO accessDTO = accessMapper.toDto(access);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccessMockMvc.perform(put("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccess() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        int databaseSizeBeforeDelete = accessRepository.findAll().size();

        // Delete the access
        restAccessMockMvc.perform(delete("/api/accesses/{id}", access.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
