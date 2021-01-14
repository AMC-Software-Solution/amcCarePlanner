package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.PowerOfAttorney;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.PowerOfAttorneyRepository;
import com.amc.careplanner.service.PowerOfAttorneyService;
import com.amc.careplanner.service.dto.PowerOfAttorneyDTO;
import com.amc.careplanner.service.mapper.PowerOfAttorneyMapper;
import com.amc.careplanner.service.dto.PowerOfAttorneyCriteria;
import com.amc.careplanner.service.PowerOfAttorneyQueryService;

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
 * Integration tests for the {@link PowerOfAttorneyResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PowerOfAttorneyResourceIT {

    private static final Boolean DEFAULT_POWER_OF_ATTORNEY_CONSENT = false;
    private static final Boolean UPDATED_POWER_OF_ATTORNEY_CONSENT = true;

    private static final Boolean DEFAULT_HEALTH_AND_WELFARE = false;
    private static final Boolean UPDATED_HEALTH_AND_WELFARE = true;

    private static final String DEFAULT_HEALTH_AND_WELFARE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HEALTH_AND_WELFARE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROPERTY_AND_FIN_AFFAIRS = false;
    private static final Boolean UPDATED_PROPERTY_AND_FIN_AFFAIRS = true;

    private static final String DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME = "BBBBBBBBBB";

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
    private PowerOfAttorneyRepository powerOfAttorneyRepository;

    @Autowired
    private PowerOfAttorneyMapper powerOfAttorneyMapper;

    @Autowired
    private PowerOfAttorneyService powerOfAttorneyService;

    @Autowired
    private PowerOfAttorneyQueryService powerOfAttorneyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPowerOfAttorneyMockMvc;

    private PowerOfAttorney powerOfAttorney;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerOfAttorney createEntity(EntityManager em) {
        PowerOfAttorney powerOfAttorney = new PowerOfAttorney()
            .powerOfAttorneyConsent(DEFAULT_POWER_OF_ATTORNEY_CONSENT)
            .healthAndWelfare(DEFAULT_HEALTH_AND_WELFARE)
            .healthAndWelfareName(DEFAULT_HEALTH_AND_WELFARE_NAME)
            .propertyAndFinAffairs(DEFAULT_PROPERTY_AND_FIN_AFFAIRS)
            .propertyAndFinAffairsName(DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return powerOfAttorney;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerOfAttorney createUpdatedEntity(EntityManager em) {
        PowerOfAttorney powerOfAttorney = new PowerOfAttorney()
            .powerOfAttorneyConsent(UPDATED_POWER_OF_ATTORNEY_CONSENT)
            .healthAndWelfare(UPDATED_HEALTH_AND_WELFARE)
            .healthAndWelfareName(UPDATED_HEALTH_AND_WELFARE_NAME)
            .propertyAndFinAffairs(UPDATED_PROPERTY_AND_FIN_AFFAIRS)
            .propertyAndFinAffairsName(UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return powerOfAttorney;
    }

    @BeforeEach
    public void initTest() {
        powerOfAttorney = createEntity(em);
    }

    @Test
    @Transactional
    public void createPowerOfAttorney() throws Exception {
        int databaseSizeBeforeCreate = powerOfAttorneyRepository.findAll().size();
        // Create the PowerOfAttorney
        PowerOfAttorneyDTO powerOfAttorneyDTO = powerOfAttorneyMapper.toDto(powerOfAttorney);
        restPowerOfAttorneyMockMvc.perform(post("/api/power-of-attorneys")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerOfAttorneyDTO)))
            .andExpect(status().isCreated());

        // Validate the PowerOfAttorney in the database
        List<PowerOfAttorney> powerOfAttorneyList = powerOfAttorneyRepository.findAll();
        assertThat(powerOfAttorneyList).hasSize(databaseSizeBeforeCreate + 1);
        PowerOfAttorney testPowerOfAttorney = powerOfAttorneyList.get(powerOfAttorneyList.size() - 1);
        assertThat(testPowerOfAttorney.isPowerOfAttorneyConsent()).isEqualTo(DEFAULT_POWER_OF_ATTORNEY_CONSENT);
        assertThat(testPowerOfAttorney.isHealthAndWelfare()).isEqualTo(DEFAULT_HEALTH_AND_WELFARE);
        assertThat(testPowerOfAttorney.getHealthAndWelfareName()).isEqualTo(DEFAULT_HEALTH_AND_WELFARE_NAME);
        assertThat(testPowerOfAttorney.isPropertyAndFinAffairs()).isEqualTo(DEFAULT_PROPERTY_AND_FIN_AFFAIRS);
        assertThat(testPowerOfAttorney.getPropertyAndFinAffairsName()).isEqualTo(DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME);
        assertThat(testPowerOfAttorney.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPowerOfAttorney.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testPowerOfAttorney.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testPowerOfAttorney.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createPowerOfAttorneyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = powerOfAttorneyRepository.findAll().size();

        // Create the PowerOfAttorney with an existing ID
        powerOfAttorney.setId(1L);
        PowerOfAttorneyDTO powerOfAttorneyDTO = powerOfAttorneyMapper.toDto(powerOfAttorney);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPowerOfAttorneyMockMvc.perform(post("/api/power-of-attorneys")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerOfAttorneyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PowerOfAttorney in the database
        List<PowerOfAttorney> powerOfAttorneyList = powerOfAttorneyRepository.findAll();
        assertThat(powerOfAttorneyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerOfAttorneyRepository.findAll().size();
        // set the field null
        powerOfAttorney.setClientId(null);

        // Create the PowerOfAttorney, which fails.
        PowerOfAttorneyDTO powerOfAttorneyDTO = powerOfAttorneyMapper.toDto(powerOfAttorney);


        restPowerOfAttorneyMockMvc.perform(post("/api/power-of-attorneys")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerOfAttorneyDTO)))
            .andExpect(status().isBadRequest());

        List<PowerOfAttorney> powerOfAttorneyList = powerOfAttorneyRepository.findAll();
        assertThat(powerOfAttorneyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneys() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList
        restPowerOfAttorneyMockMvc.perform(get("/api/power-of-attorneys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerOfAttorney.getId().intValue())))
            .andExpect(jsonPath("$.[*].powerOfAttorneyConsent").value(hasItem(DEFAULT_POWER_OF_ATTORNEY_CONSENT.booleanValue())))
            .andExpect(jsonPath("$.[*].healthAndWelfare").value(hasItem(DEFAULT_HEALTH_AND_WELFARE.booleanValue())))
            .andExpect(jsonPath("$.[*].healthAndWelfareName").value(hasItem(DEFAULT_HEALTH_AND_WELFARE_NAME)))
            .andExpect(jsonPath("$.[*].propertyAndFinAffairs").value(hasItem(DEFAULT_PROPERTY_AND_FIN_AFFAIRS.booleanValue())))
            .andExpect(jsonPath("$.[*].propertyAndFinAffairsName").value(hasItem(DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPowerOfAttorney() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get the powerOfAttorney
        restPowerOfAttorneyMockMvc.perform(get("/api/power-of-attorneys/{id}", powerOfAttorney.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(powerOfAttorney.getId().intValue()))
            .andExpect(jsonPath("$.powerOfAttorneyConsent").value(DEFAULT_POWER_OF_ATTORNEY_CONSENT.booleanValue()))
            .andExpect(jsonPath("$.healthAndWelfare").value(DEFAULT_HEALTH_AND_WELFARE.booleanValue()))
            .andExpect(jsonPath("$.healthAndWelfareName").value(DEFAULT_HEALTH_AND_WELFARE_NAME))
            .andExpect(jsonPath("$.propertyAndFinAffairs").value(DEFAULT_PROPERTY_AND_FIN_AFFAIRS.booleanValue()))
            .andExpect(jsonPath("$.propertyAndFinAffairsName").value(DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getPowerOfAttorneysByIdFiltering() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        Long id = powerOfAttorney.getId();

        defaultPowerOfAttorneyShouldBeFound("id.equals=" + id);
        defaultPowerOfAttorneyShouldNotBeFound("id.notEquals=" + id);

        defaultPowerOfAttorneyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPowerOfAttorneyShouldNotBeFound("id.greaterThan=" + id);

        defaultPowerOfAttorneyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPowerOfAttorneyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPowerOfAttorneyConsentIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where powerOfAttorneyConsent equals to DEFAULT_POWER_OF_ATTORNEY_CONSENT
        defaultPowerOfAttorneyShouldBeFound("powerOfAttorneyConsent.equals=" + DEFAULT_POWER_OF_ATTORNEY_CONSENT);

        // Get all the powerOfAttorneyList where powerOfAttorneyConsent equals to UPDATED_POWER_OF_ATTORNEY_CONSENT
        defaultPowerOfAttorneyShouldNotBeFound("powerOfAttorneyConsent.equals=" + UPDATED_POWER_OF_ATTORNEY_CONSENT);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPowerOfAttorneyConsentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where powerOfAttorneyConsent not equals to DEFAULT_POWER_OF_ATTORNEY_CONSENT
        defaultPowerOfAttorneyShouldNotBeFound("powerOfAttorneyConsent.notEquals=" + DEFAULT_POWER_OF_ATTORNEY_CONSENT);

        // Get all the powerOfAttorneyList where powerOfAttorneyConsent not equals to UPDATED_POWER_OF_ATTORNEY_CONSENT
        defaultPowerOfAttorneyShouldBeFound("powerOfAttorneyConsent.notEquals=" + UPDATED_POWER_OF_ATTORNEY_CONSENT);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPowerOfAttorneyConsentIsInShouldWork() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where powerOfAttorneyConsent in DEFAULT_POWER_OF_ATTORNEY_CONSENT or UPDATED_POWER_OF_ATTORNEY_CONSENT
        defaultPowerOfAttorneyShouldBeFound("powerOfAttorneyConsent.in=" + DEFAULT_POWER_OF_ATTORNEY_CONSENT + "," + UPDATED_POWER_OF_ATTORNEY_CONSENT);

        // Get all the powerOfAttorneyList where powerOfAttorneyConsent equals to UPDATED_POWER_OF_ATTORNEY_CONSENT
        defaultPowerOfAttorneyShouldNotBeFound("powerOfAttorneyConsent.in=" + UPDATED_POWER_OF_ATTORNEY_CONSENT);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPowerOfAttorneyConsentIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where powerOfAttorneyConsent is not null
        defaultPowerOfAttorneyShouldBeFound("powerOfAttorneyConsent.specified=true");

        // Get all the powerOfAttorneyList where powerOfAttorneyConsent is null
        defaultPowerOfAttorneyShouldNotBeFound("powerOfAttorneyConsent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfare equals to DEFAULT_HEALTH_AND_WELFARE
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfare.equals=" + DEFAULT_HEALTH_AND_WELFARE);

        // Get all the powerOfAttorneyList where healthAndWelfare equals to UPDATED_HEALTH_AND_WELFARE
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfare.equals=" + UPDATED_HEALTH_AND_WELFARE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfare not equals to DEFAULT_HEALTH_AND_WELFARE
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfare.notEquals=" + DEFAULT_HEALTH_AND_WELFARE);

        // Get all the powerOfAttorneyList where healthAndWelfare not equals to UPDATED_HEALTH_AND_WELFARE
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfare.notEquals=" + UPDATED_HEALTH_AND_WELFARE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareIsInShouldWork() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfare in DEFAULT_HEALTH_AND_WELFARE or UPDATED_HEALTH_AND_WELFARE
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfare.in=" + DEFAULT_HEALTH_AND_WELFARE + "," + UPDATED_HEALTH_AND_WELFARE);

        // Get all the powerOfAttorneyList where healthAndWelfare equals to UPDATED_HEALTH_AND_WELFARE
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfare.in=" + UPDATED_HEALTH_AND_WELFARE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfare is not null
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfare.specified=true");

        // Get all the powerOfAttorneyList where healthAndWelfare is null
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfare.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareNameIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfareName equals to DEFAULT_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfareName.equals=" + DEFAULT_HEALTH_AND_WELFARE_NAME);

        // Get all the powerOfAttorneyList where healthAndWelfareName equals to UPDATED_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfareName.equals=" + UPDATED_HEALTH_AND_WELFARE_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfareName not equals to DEFAULT_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfareName.notEquals=" + DEFAULT_HEALTH_AND_WELFARE_NAME);

        // Get all the powerOfAttorneyList where healthAndWelfareName not equals to UPDATED_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfareName.notEquals=" + UPDATED_HEALTH_AND_WELFARE_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareNameIsInShouldWork() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfareName in DEFAULT_HEALTH_AND_WELFARE_NAME or UPDATED_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfareName.in=" + DEFAULT_HEALTH_AND_WELFARE_NAME + "," + UPDATED_HEALTH_AND_WELFARE_NAME);

        // Get all the powerOfAttorneyList where healthAndWelfareName equals to UPDATED_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfareName.in=" + UPDATED_HEALTH_AND_WELFARE_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfareName is not null
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfareName.specified=true");

        // Get all the powerOfAttorneyList where healthAndWelfareName is null
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfareName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareNameContainsSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfareName contains DEFAULT_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfareName.contains=" + DEFAULT_HEALTH_AND_WELFARE_NAME);

        // Get all the powerOfAttorneyList where healthAndWelfareName contains UPDATED_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfareName.contains=" + UPDATED_HEALTH_AND_WELFARE_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHealthAndWelfareNameNotContainsSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where healthAndWelfareName does not contain DEFAULT_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldNotBeFound("healthAndWelfareName.doesNotContain=" + DEFAULT_HEALTH_AND_WELFARE_NAME);

        // Get all the powerOfAttorneyList where healthAndWelfareName does not contain UPDATED_HEALTH_AND_WELFARE_NAME
        defaultPowerOfAttorneyShouldBeFound("healthAndWelfareName.doesNotContain=" + UPDATED_HEALTH_AND_WELFARE_NAME);
    }


    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairs equals to DEFAULT_PROPERTY_AND_FIN_AFFAIRS
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairs.equals=" + DEFAULT_PROPERTY_AND_FIN_AFFAIRS);

        // Get all the powerOfAttorneyList where propertyAndFinAffairs equals to UPDATED_PROPERTY_AND_FIN_AFFAIRS
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairs.equals=" + UPDATED_PROPERTY_AND_FIN_AFFAIRS);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairs not equals to DEFAULT_PROPERTY_AND_FIN_AFFAIRS
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairs.notEquals=" + DEFAULT_PROPERTY_AND_FIN_AFFAIRS);

        // Get all the powerOfAttorneyList where propertyAndFinAffairs not equals to UPDATED_PROPERTY_AND_FIN_AFFAIRS
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairs.notEquals=" + UPDATED_PROPERTY_AND_FIN_AFFAIRS);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsIsInShouldWork() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairs in DEFAULT_PROPERTY_AND_FIN_AFFAIRS or UPDATED_PROPERTY_AND_FIN_AFFAIRS
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairs.in=" + DEFAULT_PROPERTY_AND_FIN_AFFAIRS + "," + UPDATED_PROPERTY_AND_FIN_AFFAIRS);

        // Get all the powerOfAttorneyList where propertyAndFinAffairs equals to UPDATED_PROPERTY_AND_FIN_AFFAIRS
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairs.in=" + UPDATED_PROPERTY_AND_FIN_AFFAIRS);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairs is not null
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairs.specified=true");

        // Get all the powerOfAttorneyList where propertyAndFinAffairs is null
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairs.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsNameIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName equals to DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairsName.equals=" + DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName equals to UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairsName.equals=" + UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName not equals to DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairsName.notEquals=" + DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName not equals to UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairsName.notEquals=" + UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsNameIsInShouldWork() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName in DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME or UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairsName.in=" + DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME + "," + UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName equals to UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairsName.in=" + UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName is not null
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairsName.specified=true");

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName is null
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairsName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsNameContainsSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName contains DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairsName.contains=" + DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName contains UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairsName.contains=" + UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByPropertyAndFinAffairsNameNotContainsSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName does not contain DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldNotBeFound("propertyAndFinAffairsName.doesNotContain=" + DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME);

        // Get all the powerOfAttorneyList where propertyAndFinAffairsName does not contain UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME
        defaultPowerOfAttorneyShouldBeFound("propertyAndFinAffairsName.doesNotContain=" + UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME);
    }


    @Test
    @Transactional
    public void getAllPowerOfAttorneysByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where createdDate equals to DEFAULT_CREATED_DATE
        defaultPowerOfAttorneyShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the powerOfAttorneyList where createdDate equals to UPDATED_CREATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the powerOfAttorneyList where createdDate not equals to UPDATED_CREATED_DATE
        defaultPowerOfAttorneyShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultPowerOfAttorneyShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the powerOfAttorneyList where createdDate equals to UPDATED_CREATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where createdDate is not null
        defaultPowerOfAttorneyShouldBeFound("createdDate.specified=true");

        // Get all the powerOfAttorneyList where createdDate is null
        defaultPowerOfAttorneyShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultPowerOfAttorneyShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the powerOfAttorneyList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultPowerOfAttorneyShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the powerOfAttorneyList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where createdDate is less than DEFAULT_CREATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the powerOfAttorneyList where createdDate is less than UPDATED_CREATED_DATE
        defaultPowerOfAttorneyShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the powerOfAttorneyList where createdDate is greater than SMALLER_CREATED_DATE
        defaultPowerOfAttorneyShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllPowerOfAttorneysByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the powerOfAttorneyList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the powerOfAttorneyList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the powerOfAttorneyList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where lastUpdatedDate is not null
        defaultPowerOfAttorneyShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the powerOfAttorneyList where lastUpdatedDate is null
        defaultPowerOfAttorneyShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the powerOfAttorneyList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the powerOfAttorneyList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the powerOfAttorneyList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the powerOfAttorneyList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultPowerOfAttorneyShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllPowerOfAttorneysByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where clientId equals to DEFAULT_CLIENT_ID
        defaultPowerOfAttorneyShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the powerOfAttorneyList where clientId equals to UPDATED_CLIENT_ID
        defaultPowerOfAttorneyShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where clientId not equals to DEFAULT_CLIENT_ID
        defaultPowerOfAttorneyShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the powerOfAttorneyList where clientId not equals to UPDATED_CLIENT_ID
        defaultPowerOfAttorneyShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultPowerOfAttorneyShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the powerOfAttorneyList where clientId equals to UPDATED_CLIENT_ID
        defaultPowerOfAttorneyShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where clientId is not null
        defaultPowerOfAttorneyShouldBeFound("clientId.specified=true");

        // Get all the powerOfAttorneyList where clientId is null
        defaultPowerOfAttorneyShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultPowerOfAttorneyShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the powerOfAttorneyList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultPowerOfAttorneyShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultPowerOfAttorneyShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the powerOfAttorneyList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultPowerOfAttorneyShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where clientId is less than DEFAULT_CLIENT_ID
        defaultPowerOfAttorneyShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the powerOfAttorneyList where clientId is less than UPDATED_CLIENT_ID
        defaultPowerOfAttorneyShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where clientId is greater than DEFAULT_CLIENT_ID
        defaultPowerOfAttorneyShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the powerOfAttorneyList where clientId is greater than SMALLER_CLIENT_ID
        defaultPowerOfAttorneyShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultPowerOfAttorneyShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the powerOfAttorneyList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultPowerOfAttorneyShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultPowerOfAttorneyShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the powerOfAttorneyList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultPowerOfAttorneyShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultPowerOfAttorneyShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the powerOfAttorneyList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultPowerOfAttorneyShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        // Get all the powerOfAttorneyList where hasExtraData is not null
        defaultPowerOfAttorneyShouldBeFound("hasExtraData.specified=true");

        // Get all the powerOfAttorneyList where hasExtraData is null
        defaultPowerOfAttorneyShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerOfAttorneysByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        powerOfAttorney.setServiceUser(serviceUser);
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);
        Long serviceUserId = serviceUser.getId();

        // Get all the powerOfAttorneyList where serviceUser equals to serviceUserId
        defaultPowerOfAttorneyShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the powerOfAttorneyList where serviceUser equals to serviceUserId + 1
        defaultPowerOfAttorneyShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }


    @Test
    @Transactional
    public void getAllPowerOfAttorneysByWitnessedByIsEqualToSomething() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);
        Employee witnessedBy = EmployeeResourceIT.createEntity(em);
        em.persist(witnessedBy);
        em.flush();
        powerOfAttorney.setWitnessedBy(witnessedBy);
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);
        Long witnessedById = witnessedBy.getId();

        // Get all the powerOfAttorneyList where witnessedBy equals to witnessedById
        defaultPowerOfAttorneyShouldBeFound("witnessedById.equals=" + witnessedById);

        // Get all the powerOfAttorneyList where witnessedBy equals to witnessedById + 1
        defaultPowerOfAttorneyShouldNotBeFound("witnessedById.equals=" + (witnessedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPowerOfAttorneyShouldBeFound(String filter) throws Exception {
        restPowerOfAttorneyMockMvc.perform(get("/api/power-of-attorneys?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerOfAttorney.getId().intValue())))
            .andExpect(jsonPath("$.[*].powerOfAttorneyConsent").value(hasItem(DEFAULT_POWER_OF_ATTORNEY_CONSENT.booleanValue())))
            .andExpect(jsonPath("$.[*].healthAndWelfare").value(hasItem(DEFAULT_HEALTH_AND_WELFARE.booleanValue())))
            .andExpect(jsonPath("$.[*].healthAndWelfareName").value(hasItem(DEFAULT_HEALTH_AND_WELFARE_NAME)))
            .andExpect(jsonPath("$.[*].propertyAndFinAffairs").value(hasItem(DEFAULT_PROPERTY_AND_FIN_AFFAIRS.booleanValue())))
            .andExpect(jsonPath("$.[*].propertyAndFinAffairsName").value(hasItem(DEFAULT_PROPERTY_AND_FIN_AFFAIRS_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restPowerOfAttorneyMockMvc.perform(get("/api/power-of-attorneys/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPowerOfAttorneyShouldNotBeFound(String filter) throws Exception {
        restPowerOfAttorneyMockMvc.perform(get("/api/power-of-attorneys?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPowerOfAttorneyMockMvc.perform(get("/api/power-of-attorneys/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPowerOfAttorney() throws Exception {
        // Get the powerOfAttorney
        restPowerOfAttorneyMockMvc.perform(get("/api/power-of-attorneys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePowerOfAttorney() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        int databaseSizeBeforeUpdate = powerOfAttorneyRepository.findAll().size();

        // Update the powerOfAttorney
        PowerOfAttorney updatedPowerOfAttorney = powerOfAttorneyRepository.findById(powerOfAttorney.getId()).get();
        // Disconnect from session so that the updates on updatedPowerOfAttorney are not directly saved in db
        em.detach(updatedPowerOfAttorney);
        updatedPowerOfAttorney
            .powerOfAttorneyConsent(UPDATED_POWER_OF_ATTORNEY_CONSENT)
            .healthAndWelfare(UPDATED_HEALTH_AND_WELFARE)
            .healthAndWelfareName(UPDATED_HEALTH_AND_WELFARE_NAME)
            .propertyAndFinAffairs(UPDATED_PROPERTY_AND_FIN_AFFAIRS)
            .propertyAndFinAffairsName(UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        PowerOfAttorneyDTO powerOfAttorneyDTO = powerOfAttorneyMapper.toDto(updatedPowerOfAttorney);

        restPowerOfAttorneyMockMvc.perform(put("/api/power-of-attorneys")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerOfAttorneyDTO)))
            .andExpect(status().isOk());

        // Validate the PowerOfAttorney in the database
        List<PowerOfAttorney> powerOfAttorneyList = powerOfAttorneyRepository.findAll();
        assertThat(powerOfAttorneyList).hasSize(databaseSizeBeforeUpdate);
        PowerOfAttorney testPowerOfAttorney = powerOfAttorneyList.get(powerOfAttorneyList.size() - 1);
        assertThat(testPowerOfAttorney.isPowerOfAttorneyConsent()).isEqualTo(UPDATED_POWER_OF_ATTORNEY_CONSENT);
        assertThat(testPowerOfAttorney.isHealthAndWelfare()).isEqualTo(UPDATED_HEALTH_AND_WELFARE);
        assertThat(testPowerOfAttorney.getHealthAndWelfareName()).isEqualTo(UPDATED_HEALTH_AND_WELFARE_NAME);
        assertThat(testPowerOfAttorney.isPropertyAndFinAffairs()).isEqualTo(UPDATED_PROPERTY_AND_FIN_AFFAIRS);
        assertThat(testPowerOfAttorney.getPropertyAndFinAffairsName()).isEqualTo(UPDATED_PROPERTY_AND_FIN_AFFAIRS_NAME);
        assertThat(testPowerOfAttorney.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPowerOfAttorney.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testPowerOfAttorney.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testPowerOfAttorney.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingPowerOfAttorney() throws Exception {
        int databaseSizeBeforeUpdate = powerOfAttorneyRepository.findAll().size();

        // Create the PowerOfAttorney
        PowerOfAttorneyDTO powerOfAttorneyDTO = powerOfAttorneyMapper.toDto(powerOfAttorney);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerOfAttorneyMockMvc.perform(put("/api/power-of-attorneys")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerOfAttorneyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PowerOfAttorney in the database
        List<PowerOfAttorney> powerOfAttorneyList = powerOfAttorneyRepository.findAll();
        assertThat(powerOfAttorneyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePowerOfAttorney() throws Exception {
        // Initialize the database
        powerOfAttorneyRepository.saveAndFlush(powerOfAttorney);

        int databaseSizeBeforeDelete = powerOfAttorneyRepository.findAll().size();

        // Delete the powerOfAttorney
        restPowerOfAttorneyMockMvc.perform(delete("/api/power-of-attorneys/{id}", powerOfAttorney.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PowerOfAttorney> powerOfAttorneyList = powerOfAttorneyRepository.findAll();
        assertThat(powerOfAttorneyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
