package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.EmployeeLocation;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.EmployeeLocationRepository;
import com.amc.careplanner.service.EmployeeLocationService;
import com.amc.careplanner.service.dto.EmployeeLocationDTO;
import com.amc.careplanner.service.mapper.EmployeeLocationMapper;
import com.amc.careplanner.service.dto.EmployeeLocationCriteria;
import com.amc.careplanner.service.EmployeeLocationQueryService;

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
 * Integration tests for the {@link EmployeeLocationResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeLocationResourceIT {

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

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
    private EmployeeLocationRepository employeeLocationRepository;

    @Autowired
    private EmployeeLocationMapper employeeLocationMapper;

    @Autowired
    private EmployeeLocationService employeeLocationService;

    @Autowired
    private EmployeeLocationQueryService employeeLocationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeLocationMockMvc;

    private EmployeeLocation employeeLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeLocation createEntity(EntityManager em) {
        EmployeeLocation employeeLocation = new EmployeeLocation()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return employeeLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeLocation createUpdatedEntity(EntityManager em) {
        EmployeeLocation employeeLocation = new EmployeeLocation()
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return employeeLocation;
    }

    @BeforeEach
    public void initTest() {
        employeeLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeLocation() throws Exception {
        int databaseSizeBeforeCreate = employeeLocationRepository.findAll().size();
        // Create the EmployeeLocation
        EmployeeLocationDTO employeeLocationDTO = employeeLocationMapper.toDto(employeeLocation);
        restEmployeeLocationMockMvc.perform(post("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeLocation in the database
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLocation testEmployeeLocation = employeeLocationList.get(employeeLocationList.size() - 1);
        assertThat(testEmployeeLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testEmployeeLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testEmployeeLocation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeeLocation.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testEmployeeLocation.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testEmployeeLocation.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createEmployeeLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeLocationRepository.findAll().size();

        // Create the EmployeeLocation with an existing ID
        employeeLocation.setId(1L);
        EmployeeLocationDTO employeeLocationDTO = employeeLocationMapper.toDto(employeeLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeLocationMockMvc.perform(post("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLocation in the database
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLocationRepository.findAll().size();
        // set the field null
        employeeLocation.setLatitude(null);

        // Create the EmployeeLocation, which fails.
        EmployeeLocationDTO employeeLocationDTO = employeeLocationMapper.toDto(employeeLocation);


        restEmployeeLocationMockMvc.perform(post("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocationDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLocationRepository.findAll().size();
        // set the field null
        employeeLocation.setLongitude(null);

        // Create the EmployeeLocation, which fails.
        EmployeeLocationDTO employeeLocationDTO = employeeLocationMapper.toDto(employeeLocation);


        restEmployeeLocationMockMvc.perform(post("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocationDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLocationRepository.findAll().size();
        // set the field null
        employeeLocation.setClientId(null);

        // Create the EmployeeLocation, which fails.
        EmployeeLocationDTO employeeLocationDTO = employeeLocationMapper.toDto(employeeLocation);


        restEmployeeLocationMockMvc.perform(post("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocationDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocations() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeLocation() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get the employeeLocation
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations/{id}", employeeLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeLocation.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getEmployeeLocationsByIdFiltering() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        Long id = employeeLocation.getId();

        defaultEmployeeLocationShouldBeFound("id.equals=" + id);
        defaultEmployeeLocationShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeLocationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeLocationShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeLocationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeLocationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeLocationsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude equals to DEFAULT_LATITUDE
        defaultEmployeeLocationShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the employeeLocationList where latitude equals to UPDATED_LATITUDE
        defaultEmployeeLocationShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude not equals to DEFAULT_LATITUDE
        defaultEmployeeLocationShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the employeeLocationList where latitude not equals to UPDATED_LATITUDE
        defaultEmployeeLocationShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultEmployeeLocationShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the employeeLocationList where latitude equals to UPDATED_LATITUDE
        defaultEmployeeLocationShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude is not null
        defaultEmployeeLocationShouldBeFound("latitude.specified=true");

        // Get all the employeeLocationList where latitude is null
        defaultEmployeeLocationShouldNotBeFound("latitude.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeLocationsByLatitudeContainsSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude contains DEFAULT_LATITUDE
        defaultEmployeeLocationShouldBeFound("latitude.contains=" + DEFAULT_LATITUDE);

        // Get all the employeeLocationList where latitude contains UPDATED_LATITUDE
        defaultEmployeeLocationShouldNotBeFound("latitude.contains=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLatitudeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude does not contain DEFAULT_LATITUDE
        defaultEmployeeLocationShouldNotBeFound("latitude.doesNotContain=" + DEFAULT_LATITUDE);

        // Get all the employeeLocationList where latitude does not contain UPDATED_LATITUDE
        defaultEmployeeLocationShouldBeFound("latitude.doesNotContain=" + UPDATED_LATITUDE);
    }


    @Test
    @Transactional
    public void getAllEmployeeLocationsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude equals to DEFAULT_LONGITUDE
        defaultEmployeeLocationShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the employeeLocationList where longitude equals to UPDATED_LONGITUDE
        defaultEmployeeLocationShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude not equals to DEFAULT_LONGITUDE
        defaultEmployeeLocationShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the employeeLocationList where longitude not equals to UPDATED_LONGITUDE
        defaultEmployeeLocationShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultEmployeeLocationShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the employeeLocationList where longitude equals to UPDATED_LONGITUDE
        defaultEmployeeLocationShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude is not null
        defaultEmployeeLocationShouldBeFound("longitude.specified=true");

        // Get all the employeeLocationList where longitude is null
        defaultEmployeeLocationShouldNotBeFound("longitude.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeLocationsByLongitudeContainsSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude contains DEFAULT_LONGITUDE
        defaultEmployeeLocationShouldBeFound("longitude.contains=" + DEFAULT_LONGITUDE);

        // Get all the employeeLocationList where longitude contains UPDATED_LONGITUDE
        defaultEmployeeLocationShouldNotBeFound("longitude.contains=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude does not contain DEFAULT_LONGITUDE
        defaultEmployeeLocationShouldNotBeFound("longitude.doesNotContain=" + DEFAULT_LONGITUDE);

        // Get all the employeeLocationList where longitude does not contain UPDATED_LONGITUDE
        defaultEmployeeLocationShouldBeFound("longitude.doesNotContain=" + UPDATED_LONGITUDE);
    }


    @Test
    @Transactional
    public void getAllEmployeeLocationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where createdDate equals to DEFAULT_CREATED_DATE
        defaultEmployeeLocationShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the employeeLocationList where createdDate equals to UPDATED_CREATED_DATE
        defaultEmployeeLocationShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultEmployeeLocationShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the employeeLocationList where createdDate not equals to UPDATED_CREATED_DATE
        defaultEmployeeLocationShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultEmployeeLocationShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the employeeLocationList where createdDate equals to UPDATED_CREATED_DATE
        defaultEmployeeLocationShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where createdDate is not null
        defaultEmployeeLocationShouldBeFound("createdDate.specified=true");

        // Get all the employeeLocationList where createdDate is null
        defaultEmployeeLocationShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultEmployeeLocationShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the employeeLocationList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultEmployeeLocationShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultEmployeeLocationShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the employeeLocationList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultEmployeeLocationShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where createdDate is less than DEFAULT_CREATED_DATE
        defaultEmployeeLocationShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the employeeLocationList where createdDate is less than UPDATED_CREATED_DATE
        defaultEmployeeLocationShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultEmployeeLocationShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the employeeLocationList where createdDate is greater than SMALLER_CREATED_DATE
        defaultEmployeeLocationShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeLocationsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeLocationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeLocationList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the employeeLocationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where lastUpdatedDate is not null
        defaultEmployeeLocationShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the employeeLocationList where lastUpdatedDate is null
        defaultEmployeeLocationShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeLocationList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeLocationList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeLocationList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeLocationList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultEmployeeLocationShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeLocationsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where clientId equals to DEFAULT_CLIENT_ID
        defaultEmployeeLocationShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the employeeLocationList where clientId equals to UPDATED_CLIENT_ID
        defaultEmployeeLocationShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where clientId not equals to DEFAULT_CLIENT_ID
        defaultEmployeeLocationShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the employeeLocationList where clientId not equals to UPDATED_CLIENT_ID
        defaultEmployeeLocationShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultEmployeeLocationShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the employeeLocationList where clientId equals to UPDATED_CLIENT_ID
        defaultEmployeeLocationShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where clientId is not null
        defaultEmployeeLocationShouldBeFound("clientId.specified=true");

        // Get all the employeeLocationList where clientId is null
        defaultEmployeeLocationShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultEmployeeLocationShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the employeeLocationList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultEmployeeLocationShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultEmployeeLocationShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the employeeLocationList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultEmployeeLocationShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where clientId is less than DEFAULT_CLIENT_ID
        defaultEmployeeLocationShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the employeeLocationList where clientId is less than UPDATED_CLIENT_ID
        defaultEmployeeLocationShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where clientId is greater than DEFAULT_CLIENT_ID
        defaultEmployeeLocationShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the employeeLocationList where clientId is greater than SMALLER_CLIENT_ID
        defaultEmployeeLocationShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeeLocationsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultEmployeeLocationShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the employeeLocationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeLocationShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultEmployeeLocationShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the employeeLocationList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeLocationShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultEmployeeLocationShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the employeeLocationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeLocationShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where hasExtraData is not null
        defaultEmployeeLocationShouldBeFound("hasExtraData.specified=true");

        // Get all the employeeLocationList where hasExtraData is null
        defaultEmployeeLocationShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeLocation.setEmployee(employee);
        employeeLocationRepository.saveAndFlush(employeeLocation);
        Long employeeId = employee.getId();

        // Get all the employeeLocationList where employee equals to employeeId
        defaultEmployeeLocationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeLocationList where employee equals to employeeId + 1
        defaultEmployeeLocationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeLocationShouldBeFound(String filter) throws Exception {
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeLocationShouldNotBeFound(String filter) throws Exception {
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLocation() throws Exception {
        // Get the employeeLocation
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLocation() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        int databaseSizeBeforeUpdate = employeeLocationRepository.findAll().size();

        // Update the employeeLocation
        EmployeeLocation updatedEmployeeLocation = employeeLocationRepository.findById(employeeLocation.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeLocation are not directly saved in db
        em.detach(updatedEmployeeLocation);
        updatedEmployeeLocation
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        EmployeeLocationDTO employeeLocationDTO = employeeLocationMapper.toDto(updatedEmployeeLocation);

        restEmployeeLocationMockMvc.perform(put("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocationDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeLocation in the database
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeLocation testEmployeeLocation = employeeLocationList.get(employeeLocationList.size() - 1);
        assertThat(testEmployeeLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEmployeeLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testEmployeeLocation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeeLocation.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testEmployeeLocation.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testEmployeeLocation.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeLocation() throws Exception {
        int databaseSizeBeforeUpdate = employeeLocationRepository.findAll().size();

        // Create the EmployeeLocation
        EmployeeLocationDTO employeeLocationDTO = employeeLocationMapper.toDto(employeeLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeLocationMockMvc.perform(put("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLocation in the database
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeLocation() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        int databaseSizeBeforeDelete = employeeLocationRepository.findAll().size();

        // Delete the employeeLocation
        restEmployeeLocationMockMvc.perform(delete("/api/employee-locations/{id}", employeeLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
