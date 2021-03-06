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

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

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
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
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
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testEmployeeLocation.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testEmployeeLocation.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
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
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLocationRepository.findAll().size();
        // set the field null
        employeeLocation.setTenantId(null);

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
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
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
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
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
    public void getAllEmployeeLocationsByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultEmployeeLocationShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the employeeLocationList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultEmployeeLocationShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultEmployeeLocationShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the employeeLocationList where latitude is less than or equal to SMALLER_LATITUDE
        defaultEmployeeLocationShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude is less than DEFAULT_LATITUDE
        defaultEmployeeLocationShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the employeeLocationList where latitude is less than UPDATED_LATITUDE
        defaultEmployeeLocationShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where latitude is greater than DEFAULT_LATITUDE
        defaultEmployeeLocationShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the employeeLocationList where latitude is greater than SMALLER_LATITUDE
        defaultEmployeeLocationShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
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
    public void getAllEmployeeLocationsByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultEmployeeLocationShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the employeeLocationList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultEmployeeLocationShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultEmployeeLocationShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the employeeLocationList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultEmployeeLocationShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude is less than DEFAULT_LONGITUDE
        defaultEmployeeLocationShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the employeeLocationList where longitude is less than UPDATED_LONGITUDE
        defaultEmployeeLocationShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where longitude is greater than DEFAULT_LONGITUDE
        defaultEmployeeLocationShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the employeeLocationList where longitude is greater than SMALLER_LONGITUDE
        defaultEmployeeLocationShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
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
    public void getAllEmployeeLocationsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where tenantId equals to DEFAULT_TENANT_ID
        defaultEmployeeLocationShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the employeeLocationList where tenantId equals to UPDATED_TENANT_ID
        defaultEmployeeLocationShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where tenantId not equals to DEFAULT_TENANT_ID
        defaultEmployeeLocationShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the employeeLocationList where tenantId not equals to UPDATED_TENANT_ID
        defaultEmployeeLocationShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultEmployeeLocationShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the employeeLocationList where tenantId equals to UPDATED_TENANT_ID
        defaultEmployeeLocationShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where tenantId is not null
        defaultEmployeeLocationShouldBeFound("tenantId.specified=true");

        // Get all the employeeLocationList where tenantId is null
        defaultEmployeeLocationShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultEmployeeLocationShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the employeeLocationList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultEmployeeLocationShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultEmployeeLocationShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the employeeLocationList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultEmployeeLocationShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where tenantId is less than DEFAULT_TENANT_ID
        defaultEmployeeLocationShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the employeeLocationList where tenantId is less than UPDATED_TENANT_ID
        defaultEmployeeLocationShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocationsByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList where tenantId is greater than DEFAULT_TENANT_ID
        defaultEmployeeLocationShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the employeeLocationList where tenantId is greater than SMALLER_TENANT_ID
        defaultEmployeeLocationShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
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
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

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
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testEmployeeLocation.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testEmployeeLocation.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
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
