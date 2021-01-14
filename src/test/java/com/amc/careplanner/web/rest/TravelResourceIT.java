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
import com.amc.careplanner.domain.enumeration.TravelStatus;
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

    private static final TravelStatus DEFAULT_TRAVEL_STATUS = TravelStatus.BOOKED;
    private static final TravelStatus UPDATED_TRAVEL_STATUS = TravelStatus.ENROUTE;

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
            .travelStatus(DEFAULT_TRAVEL_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
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
            .travelStatus(UPDATED_TRAVEL_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
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
        assertThat(testTravel.getTravelStatus()).isEqualTo(DEFAULT_TRAVEL_STATUS);
        assertThat(testTravel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTravel.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testTravel.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testTravel.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
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
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelRepository.findAll().size();
        // set the field null
        travel.setClientId(null);

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
            .andExpect(jsonPath("$.[*].travelStatus").value(hasItem(DEFAULT_TRAVEL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
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
            .andExpect(jsonPath("$.travelStatus").value(DEFAULT_TRAVEL_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
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
    public void getAllTravelsByTravelStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where travelStatus equals to DEFAULT_TRAVEL_STATUS
        defaultTravelShouldBeFound("travelStatus.equals=" + DEFAULT_TRAVEL_STATUS);

        // Get all the travelList where travelStatus equals to UPDATED_TRAVEL_STATUS
        defaultTravelShouldNotBeFound("travelStatus.equals=" + UPDATED_TRAVEL_STATUS);
    }

    @Test
    @Transactional
    public void getAllTravelsByTravelStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where travelStatus not equals to DEFAULT_TRAVEL_STATUS
        defaultTravelShouldNotBeFound("travelStatus.notEquals=" + DEFAULT_TRAVEL_STATUS);

        // Get all the travelList where travelStatus not equals to UPDATED_TRAVEL_STATUS
        defaultTravelShouldBeFound("travelStatus.notEquals=" + UPDATED_TRAVEL_STATUS);
    }

    @Test
    @Transactional
    public void getAllTravelsByTravelStatusIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where travelStatus in DEFAULT_TRAVEL_STATUS or UPDATED_TRAVEL_STATUS
        defaultTravelShouldBeFound("travelStatus.in=" + DEFAULT_TRAVEL_STATUS + "," + UPDATED_TRAVEL_STATUS);

        // Get all the travelList where travelStatus equals to UPDATED_TRAVEL_STATUS
        defaultTravelShouldNotBeFound("travelStatus.in=" + UPDATED_TRAVEL_STATUS);
    }

    @Test
    @Transactional
    public void getAllTravelsByTravelStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where travelStatus is not null
        defaultTravelShouldBeFound("travelStatus.specified=true");

        // Get all the travelList where travelStatus is null
        defaultTravelShouldNotBeFound("travelStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultTravelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the travelList where createdDate equals to UPDATED_CREATED_DATE
        defaultTravelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultTravelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the travelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultTravelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultTravelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the travelList where createdDate equals to UPDATED_CREATED_DATE
        defaultTravelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where createdDate is not null
        defaultTravelShouldBeFound("createdDate.specified=true");

        // Get all the travelList where createdDate is null
        defaultTravelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultTravelShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the travelList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultTravelShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultTravelShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the travelList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultTravelShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where createdDate is less than DEFAULT_CREATED_DATE
        defaultTravelShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the travelList where createdDate is less than UPDATED_CREATED_DATE
        defaultTravelShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTravelsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultTravelShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the travelList where createdDate is greater than SMALLER_CREATED_DATE
        defaultTravelShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
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
    public void getAllTravelsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where clientId equals to DEFAULT_CLIENT_ID
        defaultTravelShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the travelList where clientId equals to UPDATED_CLIENT_ID
        defaultTravelShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where clientId not equals to DEFAULT_CLIENT_ID
        defaultTravelShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the travelList where clientId not equals to UPDATED_CLIENT_ID
        defaultTravelShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultTravelShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the travelList where clientId equals to UPDATED_CLIENT_ID
        defaultTravelShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where clientId is not null
        defaultTravelShouldBeFound("clientId.specified=true");

        // Get all the travelList where clientId is null
        defaultTravelShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTravelsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultTravelShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the travelList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultTravelShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultTravelShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the travelList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultTravelShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where clientId is less than DEFAULT_CLIENT_ID
        defaultTravelShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the travelList where clientId is less than UPDATED_CLIENT_ID
        defaultTravelShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTravelsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where clientId is greater than DEFAULT_CLIENT_ID
        defaultTravelShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the travelList where clientId is greater than SMALLER_CLIENT_ID
        defaultTravelShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllTravelsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultTravelShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the travelList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultTravelShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllTravelsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultTravelShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the travelList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultTravelShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllTravelsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultTravelShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the travelList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultTravelShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllTravelsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList where hasExtraData is not null
        defaultTravelShouldBeFound("hasExtraData.specified=true");

        // Get all the travelList where hasExtraData is null
        defaultTravelShouldNotBeFound("hasExtraData.specified=false");
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
            .andExpect(jsonPath("$.[*].travelStatus").value(hasItem(DEFAULT_TRAVEL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

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
            .travelStatus(UPDATED_TRAVEL_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
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
        assertThat(testTravel.getTravelStatus()).isEqualTo(UPDATED_TRAVEL_STATUS);
        assertThat(testTravel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTravel.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testTravel.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testTravel.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
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
