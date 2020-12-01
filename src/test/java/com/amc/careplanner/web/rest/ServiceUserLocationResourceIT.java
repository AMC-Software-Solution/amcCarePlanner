package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.ServiceUserLocation;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.ServiceUserLocationRepository;
import com.amc.careplanner.service.ServiceUserLocationService;
import com.amc.careplanner.service.dto.ServiceUserLocationDTO;
import com.amc.careplanner.service.mapper.ServiceUserLocationMapper;
import com.amc.careplanner.service.dto.ServiceUserLocationCriteria;
import com.amc.careplanner.service.ServiceUserLocationQueryService;

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
 * Integration tests for the {@link ServiceUserLocationResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceUserLocationResourceIT {

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    @Autowired
    private ServiceUserLocationRepository serviceUserLocationRepository;

    @Autowired
    private ServiceUserLocationMapper serviceUserLocationMapper;

    @Autowired
    private ServiceUserLocationService serviceUserLocationService;

    @Autowired
    private ServiceUserLocationQueryService serviceUserLocationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceUserLocationMockMvc;

    private ServiceUserLocation serviceUserLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUserLocation createEntity(EntityManager em) {
        ServiceUserLocation serviceUserLocation = new ServiceUserLocation()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID);
        return serviceUserLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUserLocation createUpdatedEntity(EntityManager em) {
        ServiceUserLocation serviceUserLocation = new ServiceUserLocation()
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        return serviceUserLocation;
    }

    @BeforeEach
    public void initTest() {
        serviceUserLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceUserLocation() throws Exception {
        int databaseSizeBeforeCreate = serviceUserLocationRepository.findAll().size();
        // Create the ServiceUserLocation
        ServiceUserLocationDTO serviceUserLocationDTO = serviceUserLocationMapper.toDto(serviceUserLocation);
        restServiceUserLocationMockMvc.perform(post("/api/service-user-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceUserLocation in the database
        List<ServiceUserLocation> serviceUserLocationList = serviceUserLocationRepository.findAll();
        assertThat(serviceUserLocationList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceUserLocation testServiceUserLocation = serviceUserLocationList.get(serviceUserLocationList.size() - 1);
        assertThat(testServiceUserLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testServiceUserLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testServiceUserLocation.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testServiceUserLocation.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void createServiceUserLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceUserLocationRepository.findAll().size();

        // Create the ServiceUserLocation with an existing ID
        serviceUserLocation.setId(1L);
        ServiceUserLocationDTO serviceUserLocationDTO = serviceUserLocationMapper.toDto(serviceUserLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceUserLocationMockMvc.perform(post("/api/service-user-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUserLocation in the database
        List<ServiceUserLocation> serviceUserLocationList = serviceUserLocationRepository.findAll();
        assertThat(serviceUserLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserLocationRepository.findAll().size();
        // set the field null
        serviceUserLocation.setLatitude(null);

        // Create the ServiceUserLocation, which fails.
        ServiceUserLocationDTO serviceUserLocationDTO = serviceUserLocationMapper.toDto(serviceUserLocation);


        restServiceUserLocationMockMvc.perform(post("/api/service-user-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserLocationDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserLocation> serviceUserLocationList = serviceUserLocationRepository.findAll();
        assertThat(serviceUserLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserLocationRepository.findAll().size();
        // set the field null
        serviceUserLocation.setLongitude(null);

        // Create the ServiceUserLocation, which fails.
        ServiceUserLocationDTO serviceUserLocationDTO = serviceUserLocationMapper.toDto(serviceUserLocation);


        restServiceUserLocationMockMvc.perform(post("/api/service-user-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserLocationDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserLocation> serviceUserLocationList = serviceUserLocationRepository.findAll();
        assertThat(serviceUserLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserLocationRepository.findAll().size();
        // set the field null
        serviceUserLocation.setClientId(null);

        // Create the ServiceUserLocation, which fails.
        ServiceUserLocationDTO serviceUserLocationDTO = serviceUserLocationMapper.toDto(serviceUserLocation);


        restServiceUserLocationMockMvc.perform(post("/api/service-user-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserLocationDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserLocation> serviceUserLocationList = serviceUserLocationRepository.findAll();
        assertThat(serviceUserLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocations() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList
        restServiceUserLocationMockMvc.perform(get("/api/service-user-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUserLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getServiceUserLocation() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get the serviceUserLocation
        restServiceUserLocationMockMvc.perform(get("/api/service-user-locations/{id}", serviceUserLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceUserLocation.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getServiceUserLocationsByIdFiltering() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        Long id = serviceUserLocation.getId();

        defaultServiceUserLocationShouldBeFound("id.equals=" + id);
        defaultServiceUserLocationShouldNotBeFound("id.notEquals=" + id);

        defaultServiceUserLocationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceUserLocationShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceUserLocationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceUserLocationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceUserLocationsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where latitude equals to DEFAULT_LATITUDE
        defaultServiceUserLocationShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the serviceUserLocationList where latitude equals to UPDATED_LATITUDE
        defaultServiceUserLocationShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where latitude not equals to DEFAULT_LATITUDE
        defaultServiceUserLocationShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the serviceUserLocationList where latitude not equals to UPDATED_LATITUDE
        defaultServiceUserLocationShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultServiceUserLocationShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the serviceUserLocationList where latitude equals to UPDATED_LATITUDE
        defaultServiceUserLocationShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where latitude is not null
        defaultServiceUserLocationShouldBeFound("latitude.specified=true");

        // Get all the serviceUserLocationList where latitude is null
        defaultServiceUserLocationShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultServiceUserLocationShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the serviceUserLocationList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultServiceUserLocationShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultServiceUserLocationShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the serviceUserLocationList where latitude is less than or equal to SMALLER_LATITUDE
        defaultServiceUserLocationShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where latitude is less than DEFAULT_LATITUDE
        defaultServiceUserLocationShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the serviceUserLocationList where latitude is less than UPDATED_LATITUDE
        defaultServiceUserLocationShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where latitude is greater than DEFAULT_LATITUDE
        defaultServiceUserLocationShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the serviceUserLocationList where latitude is greater than SMALLER_LATITUDE
        defaultServiceUserLocationShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }


    @Test
    @Transactional
    public void getAllServiceUserLocationsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where longitude equals to DEFAULT_LONGITUDE
        defaultServiceUserLocationShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the serviceUserLocationList where longitude equals to UPDATED_LONGITUDE
        defaultServiceUserLocationShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where longitude not equals to DEFAULT_LONGITUDE
        defaultServiceUserLocationShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the serviceUserLocationList where longitude not equals to UPDATED_LONGITUDE
        defaultServiceUserLocationShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultServiceUserLocationShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the serviceUserLocationList where longitude equals to UPDATED_LONGITUDE
        defaultServiceUserLocationShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where longitude is not null
        defaultServiceUserLocationShouldBeFound("longitude.specified=true");

        // Get all the serviceUserLocationList where longitude is null
        defaultServiceUserLocationShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultServiceUserLocationShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the serviceUserLocationList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultServiceUserLocationShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultServiceUserLocationShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the serviceUserLocationList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultServiceUserLocationShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where longitude is less than DEFAULT_LONGITUDE
        defaultServiceUserLocationShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the serviceUserLocationList where longitude is less than UPDATED_LONGITUDE
        defaultServiceUserLocationShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where longitude is greater than DEFAULT_LONGITUDE
        defaultServiceUserLocationShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the serviceUserLocationList where longitude is greater than SMALLER_LONGITUDE
        defaultServiceUserLocationShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }


    @Test
    @Transactional
    public void getAllServiceUserLocationsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserLocationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserLocationList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the serviceUserLocationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where lastUpdatedDate is not null
        defaultServiceUserLocationShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the serviceUserLocationList where lastUpdatedDate is null
        defaultServiceUserLocationShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserLocationList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserLocationList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserLocationList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserLocationList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultServiceUserLocationShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceUserLocationsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where clientId equals to DEFAULT_CLIENT_ID
        defaultServiceUserLocationShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserLocationList where clientId equals to UPDATED_CLIENT_ID
        defaultServiceUserLocationShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where clientId not equals to DEFAULT_CLIENT_ID
        defaultServiceUserLocationShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserLocationList where clientId not equals to UPDATED_CLIENT_ID
        defaultServiceUserLocationShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultServiceUserLocationShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the serviceUserLocationList where clientId equals to UPDATED_CLIENT_ID
        defaultServiceUserLocationShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where clientId is not null
        defaultServiceUserLocationShouldBeFound("clientId.specified=true");

        // Get all the serviceUserLocationList where clientId is null
        defaultServiceUserLocationShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultServiceUserLocationShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserLocationList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultServiceUserLocationShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultServiceUserLocationShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserLocationList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultServiceUserLocationShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where clientId is less than DEFAULT_CLIENT_ID
        defaultServiceUserLocationShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserLocationList where clientId is less than UPDATED_CLIENT_ID
        defaultServiceUserLocationShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserLocationsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        // Get all the serviceUserLocationList where clientId is greater than DEFAULT_CLIENT_ID
        defaultServiceUserLocationShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserLocationList where clientId is greater than SMALLER_CLIENT_ID
        defaultServiceUserLocationShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllServiceUserLocationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        serviceUserLocation.setEmployee(employee);
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);
        Long employeeId = employee.getId();

        // Get all the serviceUserLocationList where employee equals to employeeId
        defaultServiceUserLocationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the serviceUserLocationList where employee equals to employeeId + 1
        defaultServiceUserLocationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceUserLocationShouldBeFound(String filter) throws Exception {
        restServiceUserLocationMockMvc.perform(get("/api/service-user-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUserLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));

        // Check, that the count call also returns 1
        restServiceUserLocationMockMvc.perform(get("/api/service-user-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceUserLocationShouldNotBeFound(String filter) throws Exception {
        restServiceUserLocationMockMvc.perform(get("/api/service-user-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceUserLocationMockMvc.perform(get("/api/service-user-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingServiceUserLocation() throws Exception {
        // Get the serviceUserLocation
        restServiceUserLocationMockMvc.perform(get("/api/service-user-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceUserLocation() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        int databaseSizeBeforeUpdate = serviceUserLocationRepository.findAll().size();

        // Update the serviceUserLocation
        ServiceUserLocation updatedServiceUserLocation = serviceUserLocationRepository.findById(serviceUserLocation.getId()).get();
        // Disconnect from session so that the updates on updatedServiceUserLocation are not directly saved in db
        em.detach(updatedServiceUserLocation);
        updatedServiceUserLocation
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        ServiceUserLocationDTO serviceUserLocationDTO = serviceUserLocationMapper.toDto(updatedServiceUserLocation);

        restServiceUserLocationMockMvc.perform(put("/api/service-user-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserLocationDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceUserLocation in the database
        List<ServiceUserLocation> serviceUserLocationList = serviceUserLocationRepository.findAll();
        assertThat(serviceUserLocationList).hasSize(databaseSizeBeforeUpdate);
        ServiceUserLocation testServiceUserLocation = serviceUserLocationList.get(serviceUserLocationList.size() - 1);
        assertThat(testServiceUserLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testServiceUserLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testServiceUserLocation.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testServiceUserLocation.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceUserLocation() throws Exception {
        int databaseSizeBeforeUpdate = serviceUserLocationRepository.findAll().size();

        // Create the ServiceUserLocation
        ServiceUserLocationDTO serviceUserLocationDTO = serviceUserLocationMapper.toDto(serviceUserLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceUserLocationMockMvc.perform(put("/api/service-user-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUserLocation in the database
        List<ServiceUserLocation> serviceUserLocationList = serviceUserLocationRepository.findAll();
        assertThat(serviceUserLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceUserLocation() throws Exception {
        // Initialize the database
        serviceUserLocationRepository.saveAndFlush(serviceUserLocation);

        int databaseSizeBeforeDelete = serviceUserLocationRepository.findAll().size();

        // Delete the serviceUserLocation
        restServiceUserLocationMockMvc.perform(delete("/api/service-user-locations/{id}", serviceUserLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceUserLocation> serviceUserLocationList = serviceUserLocationRepository.findAll();
        assertThat(serviceUserLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
