package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Travel;
import com.amc.careplanner.domain.Task;
import com.amc.careplanner.repository.TravelRepository;
import com.amc.careplanner.service.TravelService;
import com.amc.careplanner.service.dto.TravelDTO;
import com.amc.careplanner.service.mapper.TravelMapper;
import com.amc.careplanner.service.dto.TravelCriteria;
import com.amc.careplanner.service.TravelQueryService;

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

import com.amc.careplanner.domain.enumeration.TravelMode;
/**
 * Integration tests for the {@link TravelResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TravelResourceIT {

    private static final TravelMode DEFAULT_TRAVEL_MODE = TravelMode.CAR;
    private static final TravelMode UPDATED_TRAVEL_MODE = TravelMode.BUS;

    private static final Long DEFAULT_DISTANCE_TO_DESTINATION = 1L;
    private static final Long UPDATED_DISTANCE_TO_DESTINATION = 2L;
    private static final Long SMALLER_DISTANCE_TO_DESTINATION = 1L - 1L;

    private static final Long DEFAULT_TIME_TO_DESTINATION = 1L;
    private static final Long UPDATED_TIME_TO_DESTINATION = 2L;
    private static final Long SMALLER_TIME_TO_DESTINATION = 1L - 1L;

    private static final Long DEFAULT_ACTUAL_DISTANCE_REQUIRED = 1L;
    private static final Long UPDATED_ACTUAL_DISTANCE_REQUIRED = 2L;
    private static final Long SMALLER_ACTUAL_DISTANCE_REQUIRED = 1L - 1L;

    private static final Long DEFAULT_ACTUAL_TIME_REQUIRED = 1L;
    private static final Long UPDATED_ACTUAL_TIME_REQUIRED = 2L;
    private static final Long SMALLER_ACTUAL_TIME_REQUIRED = 1L - 1L;

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private TravelMapper travelMapper;

    @Autowired
    private TravelService travelService;

    @Autowired
    private TravelQueryService travelQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTravelMockMvc;

    private Travel travel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Travel createEntity(EntityManager em) {
        Travel travel = new Travel()
            .travelMode(DEFAULT_TRAVEL_MODE)
            .distanceToDestination(DEFAULT_DISTANCE_TO_DESTINATION)
            .timeToDestination(DEFAULT_TIME_TO_DESTINATION)
            .actualDistanceRequired(DEFAULT_ACTUAL_DISTANCE_REQUIRED)
            .actualTimeRequired(DEFAULT_ACTUAL_TIME_REQUIRED)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return travel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Travel createUpdatedEntity(EntityManager em) {
        Travel travel = new Travel()
            .travelMode(UPDATED_TRAVEL_MODE)
            .distanceToDestination(UPDATED_DISTANCE_TO_DESTINATION)
            .timeToDestination(UPDATED_TIME_TO_DESTINATION)
            .actualDistanceRequired(UPDATED_ACTUAL_DISTANCE_REQUIRED)
            .actualTimeRequired(UPDATED_ACTUAL_TIME_REQUIRED)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return travel;
    }

    @BeforeEach
    public void initTest() {
        travel = createEntity(em);
    }

    @Test
    @Transactional
    public void createTravel() throws Exception {
        int databaseSizeBeforeCreate = travelRepository.findAll().size();
        // Create the Travel
        TravelDTO travelDTO = travelMapper.toDto(travel);
        restTravelMockMvc.perform(post("/api/travels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelDTO)))
            .andExpect(status().isCreated());

        // Validate the Travel in the database
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeCreate + 1);
        Travel testTravel = travelList.get(travelList.size() - 1);
        assertThat(testTravel.getTravelMode()).isEqualTo(DEFAULT_TRAVEL_MODE);
        assertThat(testTravel.getDistanceToDestination()).isEqualTo(DEFAULT_DISTANCE_TO_DESTINATION);
        assertThat(testTravel.getTimeToDestination()).isEqualTo(DEFAULT_TIME_TO_DESTINATION);
        assertThat(testTravel.getActualDistanceRequired()).isEqualTo(DEFAULT_ACTUAL_DISTANCE_REQUIRED);
        assertThat(testTravel.getActualTimeRequired()).isEqualTo(DEFAULT_ACTUAL_TIME_REQUIRED);
        assertThat(testTravel.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testTravel.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createTravelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = travelRepository.findAll().size();

        // Create the Travel with an existing ID
        travel.setId(1L);
        TravelDTO travelDTO = travelMapper.toDto(travel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTravelMockMvc.perform(post("/api/travels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Travel in the database
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTravelModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelRepository.findAll().size();
        // set the field null
        travel.setTravelMode(null);

        // Create the Travel, which fails.
        TravelDTO travelDTO = travelMapper.toDto(travel);


        restTravelMockMvc.perform(post("/api/travels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelDTO)))
            .andExpect(status().isBadRequest());

        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelRepository.findAll().size();
        // set the field null
        travel.setTenantId(null);

        // Create the Travel, which fails.
        TravelDTO travelDTO = travelMapper.toDto(travel);


        restTravelMockMvc.perform(post("/api/travels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelDTO)))
            .andExpect(status().isBadRequest());

        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTravels() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList
        restTravelMockMvc.perform(get("/api/travels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travel.getId().intValue())))
            .andExpect(jsonPath("$.[*].travelMode").value(hasItem(DEFAULT_TRAVEL_MODE.toString())))
            .andExpect(jsonPath("$.[*].distanceToDestination").value(hasItem(DEFAULT_DISTANCE_TO_DESTINATION.intValue())))
            .andExpect(jsonPath("$.[*].timeToDestination").value(hasItem(DEFAULT_TIME_TO_DESTINATION.intValue())))
            .andExpect(jsonPath("$.[*].actualDistanceRequired").value(hasItem(DEFAULT_ACTUAL_DISTANCE_REQUIRED.intValue())))
            .andExpect(jsonPath("$.[*].actualTimeRequired").value(hasItem(DEFAULT_ACTUAL_TIME_REQUIRED.intValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTravel() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get the travel
        restTravelMockMvc.perform(get("/api/travels/{id}", travel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(travel.getId().intValue()))
            .andExpect(jsonPath("$.travelMode").value(DEFAULT_TRAVEL_MODE.toString()))
            .andExpect(jsonPath("$.distanceToDestination").value(DEFAULT_DISTANCE_TO_DESTINATION.intValue()))
            .andExpect(jsonPath("$.timeToDestination").value(DEFAULT_TIME_TO_DESTINATION.intValue()))
            .andExpect(jsonPath("$.actualDistanceRequired").value(DEFAULT_ACTUAL_DISTANCE_REQUIRED.intValue()))
            .andExpect(jsonPath("$.actualTimeRequired").value(DEFAULT_ACTUAL_TIME_REQUIRED.intValue()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getTravelsByIdFiltering() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        Long id = travel.getId();

        defaultTravelShouldBeFound("id.equals=" + id);
        defaultTravelShouldNotBeFound("id.notEquals=" + id);

        defaultTravelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTravelShouldNotBeFound("id.greaterThan=" + id);

        defaultTravelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTravelShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTravelsByTravelModeIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where travelMode equals to DEFAULT_TRAVEL_MODE
        defaultTravelShouldBeFound("travelMode.equals=" + DEFAULT_TRAVEL_MODE);

        // Get all the travelList where travelMode equals to UPDATED_TRAVEL_MODE
        defaultTravelShouldNotBeFound("travelMode.equals=" + UPDATED_TRAVEL_MODE);
    }

    @Test
    @Transactional
    public void getAllTravelsByTravelModeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where travelMode not equals to DEFAULT_TRAVEL_MODE
        defaultTravelShouldNotBeFound("travelMode.notEquals=" + DEFAULT_TRAVEL_MODE);

        // Get all the travelList where travelMode not equals to UPDATED_TRAVEL_MODE
        defaultTravelShouldBeFound("travelMode.notEquals=" + UPDATED_TRAVEL_MODE);
    }

    @Test
    @Transactional
    public void getAllTravelsByTravelModeIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where travelMode in DEFAULT_TRAVEL_MODE or UPDATED_TRAVEL_MODE
        defaultTravelShouldBeFound("travelMode.in=" + DEFAULT_TRAVEL_MODE + "," + UPDATED_TRAVEL_MODE);

        // Get all the travelList where travelMode equals to UPDATED_TRAVEL_MODE
        defaultTravelShouldNotBeFound("travelMode.in=" + UPDATED_TRAVEL_MODE);
    }

    @Test
    @Transactional
    public void getAllTravelsByTravelModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where travelMode is not null
        defaultTravelShouldBeFound("travelMode.specified=true");

        // Get all the travelList where travelMode is null
        defaultTravelShouldNotBeFound("travelMode.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByDistanceToDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where distanceToDestination equals to DEFAULT_DISTANCE_TO_DESTINATION
        defaultTravelShouldBeFound("distanceToDestination.equals=" + DEFAULT_DISTANCE_TO_DESTINATION);

        // Get all the travelList where distanceToDestination equals to UPDATED_DISTANCE_TO_DESTINATION
        defaultTravelShouldNotBeFound("distanceToDestination.equals=" + UPDATED_DISTANCE_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByDistanceToDestinationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where distanceToDestination not equals to DEFAULT_DISTANCE_TO_DESTINATION
        defaultTravelShouldNotBeFound("distanceToDestination.notEquals=" + DEFAULT_DISTANCE_TO_DESTINATION);

        // Get all the travelList where distanceToDestination not equals to UPDATED_DISTANCE_TO_DESTINATION
        defaultTravelShouldBeFound("distanceToDestination.notEquals=" + UPDATED_DISTANCE_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByDistanceToDestinationIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where distanceToDestination in DEFAULT_DISTANCE_TO_DESTINATION or UPDATED_DISTANCE_TO_DESTINATION
        defaultTravelShouldBeFound("distanceToDestination.in=" + DEFAULT_DISTANCE_TO_DESTINATION + "," + UPDATED_DISTANCE_TO_DESTINATION);

        // Get all the travelList where distanceToDestination equals to UPDATED_DISTANCE_TO_DESTINATION
        defaultTravelShouldNotBeFound("distanceToDestination.in=" + UPDATED_DISTANCE_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByDistanceToDestinationIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where distanceToDestination is not null
        defaultTravelShouldBeFound("distanceToDestination.specified=true");

        // Get all the travelList where distanceToDestination is null
        defaultTravelShouldNotBeFound("distanceToDestination.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByDistanceToDestinationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where distanceToDestination is greater than or equal to DEFAULT_DISTANCE_TO_DESTINATION
        defaultTravelShouldBeFound("distanceToDestination.greaterThanOrEqual=" + DEFAULT_DISTANCE_TO_DESTINATION);

        // Get all the travelList where distanceToDestination is greater than or equal to UPDATED_DISTANCE_TO_DESTINATION
        defaultTravelShouldNotBeFound("distanceToDestination.greaterThanOrEqual=" + UPDATED_DISTANCE_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByDistanceToDestinationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where distanceToDestination is less than or equal to DEFAULT_DISTANCE_TO_DESTINATION
        defaultTravelShouldBeFound("distanceToDestination.lessThanOrEqual=" + DEFAULT_DISTANCE_TO_DESTINATION);

        // Get all the travelList where distanceToDestination is less than or equal to SMALLER_DISTANCE_TO_DESTINATION
        defaultTravelShouldNotBeFound("distanceToDestination.lessThanOrEqual=" + SMALLER_DISTANCE_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByDistanceToDestinationIsLessThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where distanceToDestination is less than DEFAULT_DISTANCE_TO_DESTINATION
        defaultTravelShouldNotBeFound("distanceToDestination.lessThan=" + DEFAULT_DISTANCE_TO_DESTINATION);

        // Get all the travelList where distanceToDestination is less than UPDATED_DISTANCE_TO_DESTINATION
        defaultTravelShouldBeFound("distanceToDestination.lessThan=" + UPDATED_DISTANCE_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByDistanceToDestinationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where distanceToDestination is greater than DEFAULT_DISTANCE_TO_DESTINATION
        defaultTravelShouldNotBeFound("distanceToDestination.greaterThan=" + DEFAULT_DISTANCE_TO_DESTINATION);

        // Get all the travelList where distanceToDestination is greater than SMALLER_DISTANCE_TO_DESTINATION
        defaultTravelShouldBeFound("distanceToDestination.greaterThan=" + SMALLER_DISTANCE_TO_DESTINATION);
    }


    @Test
    @Transactional
    public void getAllTravelsByTimeToDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where timeToDestination equals to DEFAULT_TIME_TO_DESTINATION
        defaultTravelShouldBeFound("timeToDestination.equals=" + DEFAULT_TIME_TO_DESTINATION);

        // Get all the travelList where timeToDestination equals to UPDATED_TIME_TO_DESTINATION
        defaultTravelShouldNotBeFound("timeToDestination.equals=" + UPDATED_TIME_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByTimeToDestinationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where timeToDestination not equals to DEFAULT_TIME_TO_DESTINATION
        defaultTravelShouldNotBeFound("timeToDestination.notEquals=" + DEFAULT_TIME_TO_DESTINATION);

        // Get all the travelList where timeToDestination not equals to UPDATED_TIME_TO_DESTINATION
        defaultTravelShouldBeFound("timeToDestination.notEquals=" + UPDATED_TIME_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByTimeToDestinationIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where timeToDestination in DEFAULT_TIME_TO_DESTINATION or UPDATED_TIME_TO_DESTINATION
        defaultTravelShouldBeFound("timeToDestination.in=" + DEFAULT_TIME_TO_DESTINATION + "," + UPDATED_TIME_TO_DESTINATION);

        // Get all the travelList where timeToDestination equals to UPDATED_TIME_TO_DESTINATION
        defaultTravelShouldNotBeFound("timeToDestination.in=" + UPDATED_TIME_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByTimeToDestinationIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where timeToDestination is not null
        defaultTravelShouldBeFound("timeToDestination.specified=true");

        // Get all the travelList where timeToDestination is null
        defaultTravelShouldNotBeFound("timeToDestination.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByTimeToDestinationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where timeToDestination is greater than or equal to DEFAULT_TIME_TO_DESTINATION
        defaultTravelShouldBeFound("timeToDestination.greaterThanOrEqual=" + DEFAULT_TIME_TO_DESTINATION);

        // Get all the travelList where timeToDestination is greater than or equal to UPDATED_TIME_TO_DESTINATION
        defaultTravelShouldNotBeFound("timeToDestination.greaterThanOrEqual=" + UPDATED_TIME_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByTimeToDestinationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where timeToDestination is less than or equal to DEFAULT_TIME_TO_DESTINATION
        defaultTravelShouldBeFound("timeToDestination.lessThanOrEqual=" + DEFAULT_TIME_TO_DESTINATION);

        // Get all the travelList where timeToDestination is less than or equal to SMALLER_TIME_TO_DESTINATION
        defaultTravelShouldNotBeFound("timeToDestination.lessThanOrEqual=" + SMALLER_TIME_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByTimeToDestinationIsLessThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where timeToDestination is less than DEFAULT_TIME_TO_DESTINATION
        defaultTravelShouldNotBeFound("timeToDestination.lessThan=" + DEFAULT_TIME_TO_DESTINATION);

        // Get all the travelList where timeToDestination is less than UPDATED_TIME_TO_DESTINATION
        defaultTravelShouldBeFound("timeToDestination.lessThan=" + UPDATED_TIME_TO_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTravelsByTimeToDestinationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where timeToDestination is greater than DEFAULT_TIME_TO_DESTINATION
        defaultTravelShouldNotBeFound("timeToDestination.greaterThan=" + DEFAULT_TIME_TO_DESTINATION);

        // Get all the travelList where timeToDestination is greater than SMALLER_TIME_TO_DESTINATION
        defaultTravelShouldBeFound("timeToDestination.greaterThan=" + SMALLER_TIME_TO_DESTINATION);
    }


    @Test
    @Transactional
    public void getAllTravelsByActualDistanceRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualDistanceRequired equals to DEFAULT_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldBeFound("actualDistanceRequired.equals=" + DEFAULT_ACTUAL_DISTANCE_REQUIRED);

        // Get all the travelList where actualDistanceRequired equals to UPDATED_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldNotBeFound("actualDistanceRequired.equals=" + UPDATED_ACTUAL_DISTANCE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualDistanceRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualDistanceRequired not equals to DEFAULT_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldNotBeFound("actualDistanceRequired.notEquals=" + DEFAULT_ACTUAL_DISTANCE_REQUIRED);

        // Get all the travelList where actualDistanceRequired not equals to UPDATED_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldBeFound("actualDistanceRequired.notEquals=" + UPDATED_ACTUAL_DISTANCE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualDistanceRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualDistanceRequired in DEFAULT_ACTUAL_DISTANCE_REQUIRED or UPDATED_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldBeFound("actualDistanceRequired.in=" + DEFAULT_ACTUAL_DISTANCE_REQUIRED + "," + UPDATED_ACTUAL_DISTANCE_REQUIRED);

        // Get all the travelList where actualDistanceRequired equals to UPDATED_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldNotBeFound("actualDistanceRequired.in=" + UPDATED_ACTUAL_DISTANCE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualDistanceRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualDistanceRequired is not null
        defaultTravelShouldBeFound("actualDistanceRequired.specified=true");

        // Get all the travelList where actualDistanceRequired is null
        defaultTravelShouldNotBeFound("actualDistanceRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByActualDistanceRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualDistanceRequired is greater than or equal to DEFAULT_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldBeFound("actualDistanceRequired.greaterThanOrEqual=" + DEFAULT_ACTUAL_DISTANCE_REQUIRED);

        // Get all the travelList where actualDistanceRequired is greater than or equal to UPDATED_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldNotBeFound("actualDistanceRequired.greaterThanOrEqual=" + UPDATED_ACTUAL_DISTANCE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualDistanceRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualDistanceRequired is less than or equal to DEFAULT_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldBeFound("actualDistanceRequired.lessThanOrEqual=" + DEFAULT_ACTUAL_DISTANCE_REQUIRED);

        // Get all the travelList where actualDistanceRequired is less than or equal to SMALLER_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldNotBeFound("actualDistanceRequired.lessThanOrEqual=" + SMALLER_ACTUAL_DISTANCE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualDistanceRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualDistanceRequired is less than DEFAULT_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldNotBeFound("actualDistanceRequired.lessThan=" + DEFAULT_ACTUAL_DISTANCE_REQUIRED);

        // Get all the travelList where actualDistanceRequired is less than UPDATED_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldBeFound("actualDistanceRequired.lessThan=" + UPDATED_ACTUAL_DISTANCE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualDistanceRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualDistanceRequired is greater than DEFAULT_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldNotBeFound("actualDistanceRequired.greaterThan=" + DEFAULT_ACTUAL_DISTANCE_REQUIRED);

        // Get all the travelList where actualDistanceRequired is greater than SMALLER_ACTUAL_DISTANCE_REQUIRED
        defaultTravelShouldBeFound("actualDistanceRequired.greaterThan=" + SMALLER_ACTUAL_DISTANCE_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllTravelsByActualTimeRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualTimeRequired equals to DEFAULT_ACTUAL_TIME_REQUIRED
        defaultTravelShouldBeFound("actualTimeRequired.equals=" + DEFAULT_ACTUAL_TIME_REQUIRED);

        // Get all the travelList where actualTimeRequired equals to UPDATED_ACTUAL_TIME_REQUIRED
        defaultTravelShouldNotBeFound("actualTimeRequired.equals=" + UPDATED_ACTUAL_TIME_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualTimeRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualTimeRequired not equals to DEFAULT_ACTUAL_TIME_REQUIRED
        defaultTravelShouldNotBeFound("actualTimeRequired.notEquals=" + DEFAULT_ACTUAL_TIME_REQUIRED);

        // Get all the travelList where actualTimeRequired not equals to UPDATED_ACTUAL_TIME_REQUIRED
        defaultTravelShouldBeFound("actualTimeRequired.notEquals=" + UPDATED_ACTUAL_TIME_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualTimeRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualTimeRequired in DEFAULT_ACTUAL_TIME_REQUIRED or UPDATED_ACTUAL_TIME_REQUIRED
        defaultTravelShouldBeFound("actualTimeRequired.in=" + DEFAULT_ACTUAL_TIME_REQUIRED + "," + UPDATED_ACTUAL_TIME_REQUIRED);

        // Get all the travelList where actualTimeRequired equals to UPDATED_ACTUAL_TIME_REQUIRED
        defaultTravelShouldNotBeFound("actualTimeRequired.in=" + UPDATED_ACTUAL_TIME_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualTimeRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualTimeRequired is not null
        defaultTravelShouldBeFound("actualTimeRequired.specified=true");

        // Get all the travelList where actualTimeRequired is null
        defaultTravelShouldNotBeFound("actualTimeRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByActualTimeRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualTimeRequired is greater than or equal to DEFAULT_ACTUAL_TIME_REQUIRED
        defaultTravelShouldBeFound("actualTimeRequired.greaterThanOrEqual=" + DEFAULT_ACTUAL_TIME_REQUIRED);

        // Get all the travelList where actualTimeRequired is greater than or equal to UPDATED_ACTUAL_TIME_REQUIRED
        defaultTravelShouldNotBeFound("actualTimeRequired.greaterThanOrEqual=" + UPDATED_ACTUAL_TIME_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualTimeRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualTimeRequired is less than or equal to DEFAULT_ACTUAL_TIME_REQUIRED
        defaultTravelShouldBeFound("actualTimeRequired.lessThanOrEqual=" + DEFAULT_ACTUAL_TIME_REQUIRED);

        // Get all the travelList where actualTimeRequired is less than or equal to SMALLER_ACTUAL_TIME_REQUIRED
        defaultTravelShouldNotBeFound("actualTimeRequired.lessThanOrEqual=" + SMALLER_ACTUAL_TIME_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualTimeRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualTimeRequired is less than DEFAULT_ACTUAL_TIME_REQUIRED
        defaultTravelShouldNotBeFound("actualTimeRequired.lessThan=" + DEFAULT_ACTUAL_TIME_REQUIRED);

        // Get all the travelList where actualTimeRequired is less than UPDATED_ACTUAL_TIME_REQUIRED
        defaultTravelShouldBeFound("actualTimeRequired.lessThan=" + UPDATED_ACTUAL_TIME_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllTravelsByActualTimeRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where actualTimeRequired is greater than DEFAULT_ACTUAL_TIME_REQUIRED
        defaultTravelShouldNotBeFound("actualTimeRequired.greaterThan=" + DEFAULT_ACTUAL_TIME_REQUIRED);

        // Get all the travelList where actualTimeRequired is greater than SMALLER_ACTUAL_TIME_REQUIRED
        defaultTravelShouldBeFound("actualTimeRequired.greaterThan=" + SMALLER_ACTUAL_TIME_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllTravelsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultTravelShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the travelList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTravelShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultTravelShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the travelList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultTravelShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultTravelShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the travelList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTravelShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where lastUpdatedDate is not null
        defaultTravelShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the travelList where lastUpdatedDate is null
        defaultTravelShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTravelShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the travelList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultTravelShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTravelShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the travelList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultTravelShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultTravelShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the travelList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultTravelShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultTravelShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the travelList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultTravelShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllTravelsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where tenantId equals to DEFAULT_TENANT_ID
        defaultTravelShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the travelList where tenantId equals to UPDATED_TENANT_ID
        defaultTravelShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where tenantId not equals to DEFAULT_TENANT_ID
        defaultTravelShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the travelList where tenantId not equals to UPDATED_TENANT_ID
        defaultTravelShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultTravelShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the travelList where tenantId equals to UPDATED_TENANT_ID
        defaultTravelShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where tenantId is not null
        defaultTravelShouldBeFound("tenantId.specified=true");

        // Get all the travelList where tenantId is null
        defaultTravelShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultTravelShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the travelList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultTravelShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultTravelShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the travelList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultTravelShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where tenantId is less than DEFAULT_TENANT_ID
        defaultTravelShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the travelList where tenantId is less than UPDATED_TENANT_ID
        defaultTravelShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where tenantId is greater than DEFAULT_TENANT_ID
        defaultTravelShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the travelList where tenantId is greater than SMALLER_TENANT_ID
        defaultTravelShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllTravelsByTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);
        Task task = TaskResourceIT.createEntity(em);
        em.persist(task);
        em.flush();
        travel.setTask(task);
        travelRepository.saveAndFlush(travel);
        Long taskId = task.getId();

        // Get all the travelList where task equals to taskId
        defaultTravelShouldBeFound("taskId.equals=" + taskId);

        // Get all the travelList where task equals to taskId + 1
        defaultTravelShouldNotBeFound("taskId.equals=" + (taskId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTravelShouldBeFound(String filter) throws Exception {
        restTravelMockMvc.perform(get("/api/travels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travel.getId().intValue())))
            .andExpect(jsonPath("$.[*].travelMode").value(hasItem(DEFAULT_TRAVEL_MODE.toString())))
            .andExpect(jsonPath("$.[*].distanceToDestination").value(hasItem(DEFAULT_DISTANCE_TO_DESTINATION.intValue())))
            .andExpect(jsonPath("$.[*].timeToDestination").value(hasItem(DEFAULT_TIME_TO_DESTINATION.intValue())))
            .andExpect(jsonPath("$.[*].actualDistanceRequired").value(hasItem(DEFAULT_ACTUAL_DISTANCE_REQUIRED.intValue())))
            .andExpect(jsonPath("$.[*].actualTimeRequired").value(hasItem(DEFAULT_ACTUAL_TIME_REQUIRED.intValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restTravelMockMvc.perform(get("/api/travels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTravelShouldNotBeFound(String filter) throws Exception {
        restTravelMockMvc.perform(get("/api/travels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTravelMockMvc.perform(get("/api/travels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTravel() throws Exception {
        // Get the travel
        restTravelMockMvc.perform(get("/api/travels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravel() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        int databaseSizeBeforeUpdate = travelRepository.findAll().size();

        // Update the travel
        Travel updatedTravel = travelRepository.findById(travel.getId()).get();
        // Disconnect from session so that the updates on updatedTravel are not directly saved in db
        em.detach(updatedTravel);
        updatedTravel
            .travelMode(UPDATED_TRAVEL_MODE)
            .distanceToDestination(UPDATED_DISTANCE_TO_DESTINATION)
            .timeToDestination(UPDATED_TIME_TO_DESTINATION)
            .actualDistanceRequired(UPDATED_ACTUAL_DISTANCE_REQUIRED)
            .actualTimeRequired(UPDATED_ACTUAL_TIME_REQUIRED)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        TravelDTO travelDTO = travelMapper.toDto(updatedTravel);

        restTravelMockMvc.perform(put("/api/travels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelDTO)))
            .andExpect(status().isOk());

        // Validate the Travel in the database
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeUpdate);
        Travel testTravel = travelList.get(travelList.size() - 1);
        assertThat(testTravel.getTravelMode()).isEqualTo(UPDATED_TRAVEL_MODE);
        assertThat(testTravel.getDistanceToDestination()).isEqualTo(UPDATED_DISTANCE_TO_DESTINATION);
        assertThat(testTravel.getTimeToDestination()).isEqualTo(UPDATED_TIME_TO_DESTINATION);
        assertThat(testTravel.getActualDistanceRequired()).isEqualTo(UPDATED_ACTUAL_DISTANCE_REQUIRED);
        assertThat(testTravel.getActualTimeRequired()).isEqualTo(UPDATED_ACTUAL_TIME_REQUIRED);
        assertThat(testTravel.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testTravel.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTravel() throws Exception {
        int databaseSizeBeforeUpdate = travelRepository.findAll().size();

        // Create the Travel
        TravelDTO travelDTO = travelMapper.toDto(travel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravelMockMvc.perform(put("/api/travels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Travel in the database
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTravel() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        int databaseSizeBeforeDelete = travelRepository.findAll().size();

        // Delete the travel
        restTravelMockMvc.perform(delete("/api/travels/{id}", travel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
