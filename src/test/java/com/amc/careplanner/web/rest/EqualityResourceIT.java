package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Equality;
import com.amc.careplanner.domain.Country;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.EqualityRepository;
import com.amc.careplanner.service.EqualityService;
import com.amc.careplanner.service.dto.EqualityDTO;
import com.amc.careplanner.service.mapper.EqualityMapper;
import com.amc.careplanner.service.dto.EqualityCriteria;
import com.amc.careplanner.service.EqualityQueryService;

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

import com.amc.careplanner.domain.enumeration.Gender;
import com.amc.careplanner.domain.enumeration.MaritalStatus;
import com.amc.careplanner.domain.enumeration.Religion;
/**
 * Integration tests for the {@link EqualityResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EqualityResourceIT {

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.SINGLE;

    private static final Religion DEFAULT_RELIGION = Religion.MUSLIM;
    private static final Religion UPDATED_RELIGION = Religion.CHRISTIANITY;

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
    private EqualityRepository equalityRepository;

    @Autowired
    private EqualityMapper equalityMapper;

    @Autowired
    private EqualityService equalityService;

    @Autowired
    private EqualityQueryService equalityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEqualityMockMvc;

    private Equality equality;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equality createEntity(EntityManager em) {
        Equality equality = new Equality()
            .gender(DEFAULT_GENDER)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .religion(DEFAULT_RELIGION)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return equality;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equality createUpdatedEntity(EntityManager em) {
        Equality equality = new Equality()
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .religion(UPDATED_RELIGION)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return equality;
    }

    @BeforeEach
    public void initTest() {
        equality = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquality() throws Exception {
        int databaseSizeBeforeCreate = equalityRepository.findAll().size();
        // Create the Equality
        EqualityDTO equalityDTO = equalityMapper.toDto(equality);
        restEqualityMockMvc.perform(post("/api/equalities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equalityDTO)))
            .andExpect(status().isCreated());

        // Validate the Equality in the database
        List<Equality> equalityList = equalityRepository.findAll();
        assertThat(equalityList).hasSize(databaseSizeBeforeCreate + 1);
        Equality testEquality = equalityList.get(equalityList.size() - 1);
        assertThat(testEquality.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEquality.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testEquality.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testEquality.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEquality.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testEquality.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testEquality.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createEqualityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equalityRepository.findAll().size();

        // Create the Equality with an existing ID
        equality.setId(1L);
        EqualityDTO equalityDTO = equalityMapper.toDto(equality);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEqualityMockMvc.perform(post("/api/equalities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equalityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equality in the database
        List<Equality> equalityList = equalityRepository.findAll();
        assertThat(equalityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = equalityRepository.findAll().size();
        // set the field null
        equality.setGender(null);

        // Create the Equality, which fails.
        EqualityDTO equalityDTO = equalityMapper.toDto(equality);


        restEqualityMockMvc.perform(post("/api/equalities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equalityDTO)))
            .andExpect(status().isBadRequest());

        List<Equality> equalityList = equalityRepository.findAll();
        assertThat(equalityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaritalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = equalityRepository.findAll().size();
        // set the field null
        equality.setMaritalStatus(null);

        // Create the Equality, which fails.
        EqualityDTO equalityDTO = equalityMapper.toDto(equality);


        restEqualityMockMvc.perform(post("/api/equalities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equalityDTO)))
            .andExpect(status().isBadRequest());

        List<Equality> equalityList = equalityRepository.findAll();
        assertThat(equalityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReligionIsRequired() throws Exception {
        int databaseSizeBeforeTest = equalityRepository.findAll().size();
        // set the field null
        equality.setReligion(null);

        // Create the Equality, which fails.
        EqualityDTO equalityDTO = equalityMapper.toDto(equality);


        restEqualityMockMvc.perform(post("/api/equalities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equalityDTO)))
            .andExpect(status().isBadRequest());

        List<Equality> equalityList = equalityRepository.findAll();
        assertThat(equalityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = equalityRepository.findAll().size();
        // set the field null
        equality.setClientId(null);

        // Create the Equality, which fails.
        EqualityDTO equalityDTO = equalityMapper.toDto(equality);


        restEqualityMockMvc.perform(post("/api/equalities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equalityDTO)))
            .andExpect(status().isBadRequest());

        List<Equality> equalityList = equalityRepository.findAll();
        assertThat(equalityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEqualities() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList
        restEqualityMockMvc.perform(get("/api/equalities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equality.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEquality() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get the equality
        restEqualityMockMvc.perform(get("/api/equalities/{id}", equality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equality.getId().intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getEqualitiesByIdFiltering() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        Long id = equality.getId();

        defaultEqualityShouldBeFound("id.equals=" + id);
        defaultEqualityShouldNotBeFound("id.notEquals=" + id);

        defaultEqualityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEqualityShouldNotBeFound("id.greaterThan=" + id);

        defaultEqualityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEqualityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEqualitiesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where gender equals to DEFAULT_GENDER
        defaultEqualityShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the equalityList where gender equals to UPDATED_GENDER
        defaultEqualityShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where gender not equals to DEFAULT_GENDER
        defaultEqualityShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the equalityList where gender not equals to UPDATED_GENDER
        defaultEqualityShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultEqualityShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the equalityList where gender equals to UPDATED_GENDER
        defaultEqualityShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where gender is not null
        defaultEqualityShouldBeFound("gender.specified=true");

        // Get all the equalityList where gender is null
        defaultEqualityShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllEqualitiesByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultEqualityShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the equalityList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEqualityShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByMaritalStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where maritalStatus not equals to DEFAULT_MARITAL_STATUS
        defaultEqualityShouldNotBeFound("maritalStatus.notEquals=" + DEFAULT_MARITAL_STATUS);

        // Get all the equalityList where maritalStatus not equals to UPDATED_MARITAL_STATUS
        defaultEqualityShouldBeFound("maritalStatus.notEquals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultEqualityShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the equalityList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEqualityShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where maritalStatus is not null
        defaultEqualityShouldBeFound("maritalStatus.specified=true");

        // Get all the equalityList where maritalStatus is null
        defaultEqualityShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEqualitiesByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where religion equals to DEFAULT_RELIGION
        defaultEqualityShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the equalityList where religion equals to UPDATED_RELIGION
        defaultEqualityShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByReligionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where religion not equals to DEFAULT_RELIGION
        defaultEqualityShouldNotBeFound("religion.notEquals=" + DEFAULT_RELIGION);

        // Get all the equalityList where religion not equals to UPDATED_RELIGION
        defaultEqualityShouldBeFound("religion.notEquals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultEqualityShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the equalityList where religion equals to UPDATED_RELIGION
        defaultEqualityShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where religion is not null
        defaultEqualityShouldBeFound("religion.specified=true");

        // Get all the equalityList where religion is null
        defaultEqualityShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    public void getAllEqualitiesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where createdDate equals to DEFAULT_CREATED_DATE
        defaultEqualityShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the equalityList where createdDate equals to UPDATED_CREATED_DATE
        defaultEqualityShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultEqualityShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the equalityList where createdDate not equals to UPDATED_CREATED_DATE
        defaultEqualityShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultEqualityShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the equalityList where createdDate equals to UPDATED_CREATED_DATE
        defaultEqualityShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where createdDate is not null
        defaultEqualityShouldBeFound("createdDate.specified=true");

        // Get all the equalityList where createdDate is null
        defaultEqualityShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEqualitiesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultEqualityShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the equalityList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultEqualityShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultEqualityShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the equalityList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultEqualityShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where createdDate is less than DEFAULT_CREATED_DATE
        defaultEqualityShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the equalityList where createdDate is less than UPDATED_CREATED_DATE
        defaultEqualityShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultEqualityShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the equalityList where createdDate is greater than SMALLER_CREATED_DATE
        defaultEqualityShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEqualitiesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultEqualityShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the equalityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEqualityShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultEqualityShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the equalityList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultEqualityShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultEqualityShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the equalityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEqualityShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where lastUpdatedDate is not null
        defaultEqualityShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the equalityList where lastUpdatedDate is null
        defaultEqualityShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEqualitiesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEqualityShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the equalityList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultEqualityShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEqualityShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the equalityList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultEqualityShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultEqualityShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the equalityList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultEqualityShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultEqualityShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the equalityList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultEqualityShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEqualitiesByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where clientId equals to DEFAULT_CLIENT_ID
        defaultEqualityShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the equalityList where clientId equals to UPDATED_CLIENT_ID
        defaultEqualityShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where clientId not equals to DEFAULT_CLIENT_ID
        defaultEqualityShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the equalityList where clientId not equals to UPDATED_CLIENT_ID
        defaultEqualityShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultEqualityShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the equalityList where clientId equals to UPDATED_CLIENT_ID
        defaultEqualityShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where clientId is not null
        defaultEqualityShouldBeFound("clientId.specified=true");

        // Get all the equalityList where clientId is null
        defaultEqualityShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEqualitiesByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultEqualityShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the equalityList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultEqualityShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultEqualityShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the equalityList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultEqualityShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where clientId is less than DEFAULT_CLIENT_ID
        defaultEqualityShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the equalityList where clientId is less than UPDATED_CLIENT_ID
        defaultEqualityShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where clientId is greater than DEFAULT_CLIENT_ID
        defaultEqualityShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the equalityList where clientId is greater than SMALLER_CLIENT_ID
        defaultEqualityShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllEqualitiesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultEqualityShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the equalityList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEqualityShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultEqualityShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the equalityList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultEqualityShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultEqualityShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the equalityList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEqualityShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEqualitiesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        // Get all the equalityList where hasExtraData is not null
        defaultEqualityShouldBeFound("hasExtraData.specified=true");

        // Get all the equalityList where hasExtraData is null
        defaultEqualityShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllEqualitiesByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);
        Country nationality = CountryResourceIT.createEntity(em);
        em.persist(nationality);
        em.flush();
        equality.setNationality(nationality);
        equalityRepository.saveAndFlush(equality);
        Long nationalityId = nationality.getId();

        // Get all the equalityList where nationality equals to nationalityId
        defaultEqualityShouldBeFound("nationalityId.equals=" + nationalityId);

        // Get all the equalityList where nationality equals to nationalityId + 1
        defaultEqualityShouldNotBeFound("nationalityId.equals=" + (nationalityId + 1));
    }


    @Test
    @Transactional
    public void getAllEqualitiesByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        equality.setServiceUser(serviceUser);
        equalityRepository.saveAndFlush(equality);
        Long serviceUserId = serviceUser.getId();

        // Get all the equalityList where serviceUser equals to serviceUserId
        defaultEqualityShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the equalityList where serviceUser equals to serviceUserId + 1
        defaultEqualityShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEqualityShouldBeFound(String filter) throws Exception {
        restEqualityMockMvc.perform(get("/api/equalities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equality.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restEqualityMockMvc.perform(get("/api/equalities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEqualityShouldNotBeFound(String filter) throws Exception {
        restEqualityMockMvc.perform(get("/api/equalities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEqualityMockMvc.perform(get("/api/equalities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEquality() throws Exception {
        // Get the equality
        restEqualityMockMvc.perform(get("/api/equalities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquality() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        int databaseSizeBeforeUpdate = equalityRepository.findAll().size();

        // Update the equality
        Equality updatedEquality = equalityRepository.findById(equality.getId()).get();
        // Disconnect from session so that the updates on updatedEquality are not directly saved in db
        em.detach(updatedEquality);
        updatedEquality
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .religion(UPDATED_RELIGION)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        EqualityDTO equalityDTO = equalityMapper.toDto(updatedEquality);

        restEqualityMockMvc.perform(put("/api/equalities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equalityDTO)))
            .andExpect(status().isOk());

        // Validate the Equality in the database
        List<Equality> equalityList = equalityRepository.findAll();
        assertThat(equalityList).hasSize(databaseSizeBeforeUpdate);
        Equality testEquality = equalityList.get(equalityList.size() - 1);
        assertThat(testEquality.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEquality.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEquality.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testEquality.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEquality.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testEquality.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testEquality.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingEquality() throws Exception {
        int databaseSizeBeforeUpdate = equalityRepository.findAll().size();

        // Create the Equality
        EqualityDTO equalityDTO = equalityMapper.toDto(equality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEqualityMockMvc.perform(put("/api/equalities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equalityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equality in the database
        List<Equality> equalityList = equalityRepository.findAll();
        assertThat(equalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquality() throws Exception {
        // Initialize the database
        equalityRepository.saveAndFlush(equality);

        int databaseSizeBeforeDelete = equalityRepository.findAll().size();

        // Delete the equality
        restEqualityMockMvc.perform(delete("/api/equalities/{id}", equality.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equality> equalityList = equalityRepository.findAll();
        assertThat(equalityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
