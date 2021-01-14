package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.EmployeeAvailability;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.EmployeeAvailabilityRepository;
import com.amc.careplanner.service.EmployeeAvailabilityService;
import com.amc.careplanner.service.dto.EmployeeAvailabilityDTO;
import com.amc.careplanner.service.mapper.EmployeeAvailabilityMapper;
import com.amc.careplanner.service.dto.EmployeeAvailabilityCriteria;
import com.amc.careplanner.service.EmployeeAvailabilityQueryService;

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

import com.amc.careplanner.domain.enumeration.Shift;
/**
 * Integration tests for the {@link EmployeeAvailabilityResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeAvailabilityResourceIT {

    private static final Boolean DEFAULT_AVAILABLE_FOR_WORK = false;
    private static final Boolean UPDATED_AVAILABLE_FOR_WORK = true;

    private static final Boolean DEFAULT_AVAILABLE_MONDAY = false;
    private static final Boolean UPDATED_AVAILABLE_MONDAY = true;

    private static final Boolean DEFAULT_AVAILABLE_TUESDAY = false;
    private static final Boolean UPDATED_AVAILABLE_TUESDAY = true;

    private static final Boolean DEFAULT_AVAILABLE_WEDNESDAY = false;
    private static final Boolean UPDATED_AVAILABLE_WEDNESDAY = true;

    private static final Boolean DEFAULT_AVAILABLE_THURSDAY = false;
    private static final Boolean UPDATED_AVAILABLE_THURSDAY = true;

    private static final Boolean DEFAULT_AVAILABLE_FRIDAY = false;
    private static final Boolean UPDATED_AVAILABLE_FRIDAY = true;

    private static final Boolean DEFAULT_AVAILABLE_SATURDAY = false;
    private static final Boolean UPDATED_AVAILABLE_SATURDAY = true;

    private static final Boolean DEFAULT_AVAILABLE_SUNDAY = false;
    private static final Boolean UPDATED_AVAILABLE_SUNDAY = true;

    private static final Shift DEFAULT_PREFERRED_SHIFT = Shift.MORNING_SHIFT;
    private static final Shift UPDATED_PREFERRED_SHIFT = Shift.AFTERNOON_SHIFT;

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    @Autowired
    private EmployeeAvailabilityRepository employeeAvailabilityRepository;

    @Autowired
    private EmployeeAvailabilityMapper employeeAvailabilityMapper;

    @Autowired
    private EmployeeAvailabilityService employeeAvailabilityService;

    @Autowired
    private EmployeeAvailabilityQueryService employeeAvailabilityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeAvailabilityMockMvc;

    private EmployeeAvailability employeeAvailability;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeAvailability createEntity(EntityManager em) {
        EmployeeAvailability employeeAvailability = new EmployeeAvailability()
            .availableForWork(DEFAULT_AVAILABLE_FOR_WORK)
            .availableMonday(DEFAULT_AVAILABLE_MONDAY)
            .availableTuesday(DEFAULT_AVAILABLE_TUESDAY)
            .availableWednesday(DEFAULT_AVAILABLE_WEDNESDAY)
            .availableThursday(DEFAULT_AVAILABLE_THURSDAY)
            .availableFriday(DEFAULT_AVAILABLE_FRIDAY)
            .availableSaturday(DEFAULT_AVAILABLE_SATURDAY)
            .availableSunday(DEFAULT_AVAILABLE_SUNDAY)
            .preferredShift(DEFAULT_PREFERRED_SHIFT)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID);
        return employeeAvailability;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeAvailability createUpdatedEntity(EntityManager em) {
        EmployeeAvailability employeeAvailability = new EmployeeAvailability()
            .availableForWork(UPDATED_AVAILABLE_FOR_WORK)
            .availableMonday(UPDATED_AVAILABLE_MONDAY)
            .availableTuesday(UPDATED_AVAILABLE_TUESDAY)
            .availableWednesday(UPDATED_AVAILABLE_WEDNESDAY)
            .availableThursday(UPDATED_AVAILABLE_THURSDAY)
            .availableFriday(UPDATED_AVAILABLE_FRIDAY)
            .availableSaturday(UPDATED_AVAILABLE_SATURDAY)
            .availableSunday(UPDATED_AVAILABLE_SUNDAY)
            .preferredShift(UPDATED_PREFERRED_SHIFT)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        return employeeAvailability;
    }

    @BeforeEach
    public void initTest() {
        employeeAvailability = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeAvailability() throws Exception {
        int databaseSizeBeforeCreate = employeeAvailabilityRepository.findAll().size();
        // Create the EmployeeAvailability
        EmployeeAvailabilityDTO employeeAvailabilityDTO = employeeAvailabilityMapper.toDto(employeeAvailability);
        restEmployeeAvailabilityMockMvc.perform(post("/api/employee-availabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAvailabilityDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeAvailability in the database
        List<EmployeeAvailability> employeeAvailabilityList = employeeAvailabilityRepository.findAll();
        assertThat(employeeAvailabilityList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeAvailability testEmployeeAvailability = employeeAvailabilityList.get(employeeAvailabilityList.size() - 1);
        assertThat(testEmployeeAvailability.isAvailableForWork()).isEqualTo(DEFAULT_AVAILABLE_FOR_WORK);
        assertThat(testEmployeeAvailability.isAvailableMonday()).isEqualTo(DEFAULT_AVAILABLE_MONDAY);
        assertThat(testEmployeeAvailability.isAvailableTuesday()).isEqualTo(DEFAULT_AVAILABLE_TUESDAY);
        assertThat(testEmployeeAvailability.isAvailableWednesday()).isEqualTo(DEFAULT_AVAILABLE_WEDNESDAY);
        assertThat(testEmployeeAvailability.isAvailableThursday()).isEqualTo(DEFAULT_AVAILABLE_THURSDAY);
        assertThat(testEmployeeAvailability.isAvailableFriday()).isEqualTo(DEFAULT_AVAILABLE_FRIDAY);
        assertThat(testEmployeeAvailability.isAvailableSaturday()).isEqualTo(DEFAULT_AVAILABLE_SATURDAY);
        assertThat(testEmployeeAvailability.isAvailableSunday()).isEqualTo(DEFAULT_AVAILABLE_SUNDAY);
        assertThat(testEmployeeAvailability.getPreferredShift()).isEqualTo(DEFAULT_PREFERRED_SHIFT);
        assertThat(testEmployeeAvailability.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
        assertThat(testEmployeeAvailability.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testEmployeeAvailability.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void createEmployeeAvailabilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeAvailabilityRepository.findAll().size();

        // Create the EmployeeAvailability with an existing ID
        employeeAvailability.setId(1L);
        EmployeeAvailabilityDTO employeeAvailabilityDTO = employeeAvailabilityMapper.toDto(employeeAvailability);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeAvailabilityMockMvc.perform(post("/api/employee-availabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAvailabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAvailability in the database
        List<EmployeeAvailability> employeeAvailabilityList = employeeAvailabilityRepository.findAll();
        assertThat(employeeAvailabilityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeAvailabilityRepository.findAll().size();
        // set the field null
        employeeAvailability.setClientId(null);

        // Create the EmployeeAvailability, which fails.
        EmployeeAvailabilityDTO employeeAvailabilityDTO = employeeAvailabilityMapper.toDto(employeeAvailability);


        restEmployeeAvailabilityMockMvc.perform(post("/api/employee-availabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAvailabilityDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeAvailability> employeeAvailabilityList = employeeAvailabilityRepository.findAll();
        assertThat(employeeAvailabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilities() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList
        restEmployeeAvailabilityMockMvc.perform(get("/api/employee-availabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeAvailability.getId().intValue())))
            .andExpect(jsonPath("$.[*].availableForWork").value(hasItem(DEFAULT_AVAILABLE_FOR_WORK.booleanValue())))
            .andExpect(jsonPath("$.[*].availableMonday").value(hasItem(DEFAULT_AVAILABLE_MONDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableTuesday").value(hasItem(DEFAULT_AVAILABLE_TUESDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableWednesday").value(hasItem(DEFAULT_AVAILABLE_WEDNESDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableThursday").value(hasItem(DEFAULT_AVAILABLE_THURSDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableFriday").value(hasItem(DEFAULT_AVAILABLE_FRIDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableSaturday").value(hasItem(DEFAULT_AVAILABLE_SATURDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableSunday").value(hasItem(DEFAULT_AVAILABLE_SUNDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].preferredShift").value(hasItem(DEFAULT_PREFERRED_SHIFT.toString())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeAvailability() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get the employeeAvailability
        restEmployeeAvailabilityMockMvc.perform(get("/api/employee-availabilities/{id}", employeeAvailability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeAvailability.getId().intValue()))
            .andExpect(jsonPath("$.availableForWork").value(DEFAULT_AVAILABLE_FOR_WORK.booleanValue()))
            .andExpect(jsonPath("$.availableMonday").value(DEFAULT_AVAILABLE_MONDAY.booleanValue()))
            .andExpect(jsonPath("$.availableTuesday").value(DEFAULT_AVAILABLE_TUESDAY.booleanValue()))
            .andExpect(jsonPath("$.availableWednesday").value(DEFAULT_AVAILABLE_WEDNESDAY.booleanValue()))
            .andExpect(jsonPath("$.availableThursday").value(DEFAULT_AVAILABLE_THURSDAY.booleanValue()))
            .andExpect(jsonPath("$.availableFriday").value(DEFAULT_AVAILABLE_FRIDAY.booleanValue()))
            .andExpect(jsonPath("$.availableSaturday").value(DEFAULT_AVAILABLE_SATURDAY.booleanValue()))
            .andExpect(jsonPath("$.availableSunday").value(DEFAULT_AVAILABLE_SUNDAY.booleanValue()))
            .andExpect(jsonPath("$.preferredShift").value(DEFAULT_PREFERRED_SHIFT.toString()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getEmployeeAvailabilitiesByIdFiltering() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        Long id = employeeAvailability.getId();

        defaultEmployeeAvailabilityShouldBeFound("id.equals=" + id);
        defaultEmployeeAvailabilityShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeAvailabilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeAvailabilityShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeAvailabilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeAvailabilityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableForWorkIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableForWork equals to DEFAULT_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldBeFound("availableForWork.equals=" + DEFAULT_AVAILABLE_FOR_WORK);

        // Get all the employeeAvailabilityList where availableForWork equals to UPDATED_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldNotBeFound("availableForWork.equals=" + UPDATED_AVAILABLE_FOR_WORK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableForWorkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableForWork not equals to DEFAULT_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldNotBeFound("availableForWork.notEquals=" + DEFAULT_AVAILABLE_FOR_WORK);

        // Get all the employeeAvailabilityList where availableForWork not equals to UPDATED_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldBeFound("availableForWork.notEquals=" + UPDATED_AVAILABLE_FOR_WORK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableForWorkIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableForWork in DEFAULT_AVAILABLE_FOR_WORK or UPDATED_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldBeFound("availableForWork.in=" + DEFAULT_AVAILABLE_FOR_WORK + "," + UPDATED_AVAILABLE_FOR_WORK);

        // Get all the employeeAvailabilityList where availableForWork equals to UPDATED_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldNotBeFound("availableForWork.in=" + UPDATED_AVAILABLE_FOR_WORK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableForWorkIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableForWork is not null
        defaultEmployeeAvailabilityShouldBeFound("availableForWork.specified=true");

        // Get all the employeeAvailabilityList where availableForWork is null
        defaultEmployeeAvailabilityShouldNotBeFound("availableForWork.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableMondayIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableMonday equals to DEFAULT_AVAILABLE_MONDAY
        defaultEmployeeAvailabilityShouldBeFound("availableMonday.equals=" + DEFAULT_AVAILABLE_MONDAY);

        // Get all the employeeAvailabilityList where availableMonday equals to UPDATED_AVAILABLE_MONDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableMonday.equals=" + UPDATED_AVAILABLE_MONDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableMondayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableMonday not equals to DEFAULT_AVAILABLE_MONDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableMonday.notEquals=" + DEFAULT_AVAILABLE_MONDAY);

        // Get all the employeeAvailabilityList where availableMonday not equals to UPDATED_AVAILABLE_MONDAY
        defaultEmployeeAvailabilityShouldBeFound("availableMonday.notEquals=" + UPDATED_AVAILABLE_MONDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableMondayIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableMonday in DEFAULT_AVAILABLE_MONDAY or UPDATED_AVAILABLE_MONDAY
        defaultEmployeeAvailabilityShouldBeFound("availableMonday.in=" + DEFAULT_AVAILABLE_MONDAY + "," + UPDATED_AVAILABLE_MONDAY);

        // Get all the employeeAvailabilityList where availableMonday equals to UPDATED_AVAILABLE_MONDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableMonday.in=" + UPDATED_AVAILABLE_MONDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableMondayIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableMonday is not null
        defaultEmployeeAvailabilityShouldBeFound("availableMonday.specified=true");

        // Get all the employeeAvailabilityList where availableMonday is null
        defaultEmployeeAvailabilityShouldNotBeFound("availableMonday.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableTuesdayIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableTuesday equals to DEFAULT_AVAILABLE_TUESDAY
        defaultEmployeeAvailabilityShouldBeFound("availableTuesday.equals=" + DEFAULT_AVAILABLE_TUESDAY);

        // Get all the employeeAvailabilityList where availableTuesday equals to UPDATED_AVAILABLE_TUESDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableTuesday.equals=" + UPDATED_AVAILABLE_TUESDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableTuesdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableTuesday not equals to DEFAULT_AVAILABLE_TUESDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableTuesday.notEquals=" + DEFAULT_AVAILABLE_TUESDAY);

        // Get all the employeeAvailabilityList where availableTuesday not equals to UPDATED_AVAILABLE_TUESDAY
        defaultEmployeeAvailabilityShouldBeFound("availableTuesday.notEquals=" + UPDATED_AVAILABLE_TUESDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableTuesdayIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableTuesday in DEFAULT_AVAILABLE_TUESDAY or UPDATED_AVAILABLE_TUESDAY
        defaultEmployeeAvailabilityShouldBeFound("availableTuesday.in=" + DEFAULT_AVAILABLE_TUESDAY + "," + UPDATED_AVAILABLE_TUESDAY);

        // Get all the employeeAvailabilityList where availableTuesday equals to UPDATED_AVAILABLE_TUESDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableTuesday.in=" + UPDATED_AVAILABLE_TUESDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableTuesdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableTuesday is not null
        defaultEmployeeAvailabilityShouldBeFound("availableTuesday.specified=true");

        // Get all the employeeAvailabilityList where availableTuesday is null
        defaultEmployeeAvailabilityShouldNotBeFound("availableTuesday.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableWednesdayIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableWednesday equals to DEFAULT_AVAILABLE_WEDNESDAY
        defaultEmployeeAvailabilityShouldBeFound("availableWednesday.equals=" + DEFAULT_AVAILABLE_WEDNESDAY);

        // Get all the employeeAvailabilityList where availableWednesday equals to UPDATED_AVAILABLE_WEDNESDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableWednesday.equals=" + UPDATED_AVAILABLE_WEDNESDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableWednesdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableWednesday not equals to DEFAULT_AVAILABLE_WEDNESDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableWednesday.notEquals=" + DEFAULT_AVAILABLE_WEDNESDAY);

        // Get all the employeeAvailabilityList where availableWednesday not equals to UPDATED_AVAILABLE_WEDNESDAY
        defaultEmployeeAvailabilityShouldBeFound("availableWednesday.notEquals=" + UPDATED_AVAILABLE_WEDNESDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableWednesdayIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableWednesday in DEFAULT_AVAILABLE_WEDNESDAY or UPDATED_AVAILABLE_WEDNESDAY
        defaultEmployeeAvailabilityShouldBeFound("availableWednesday.in=" + DEFAULT_AVAILABLE_WEDNESDAY + "," + UPDATED_AVAILABLE_WEDNESDAY);

        // Get all the employeeAvailabilityList where availableWednesday equals to UPDATED_AVAILABLE_WEDNESDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableWednesday.in=" + UPDATED_AVAILABLE_WEDNESDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableWednesdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableWednesday is not null
        defaultEmployeeAvailabilityShouldBeFound("availableWednesday.specified=true");

        // Get all the employeeAvailabilityList where availableWednesday is null
        defaultEmployeeAvailabilityShouldNotBeFound("availableWednesday.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableThursdayIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableThursday equals to DEFAULT_AVAILABLE_THURSDAY
        defaultEmployeeAvailabilityShouldBeFound("availableThursday.equals=" + DEFAULT_AVAILABLE_THURSDAY);

        // Get all the employeeAvailabilityList where availableThursday equals to UPDATED_AVAILABLE_THURSDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableThursday.equals=" + UPDATED_AVAILABLE_THURSDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableThursdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableThursday not equals to DEFAULT_AVAILABLE_THURSDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableThursday.notEquals=" + DEFAULT_AVAILABLE_THURSDAY);

        // Get all the employeeAvailabilityList where availableThursday not equals to UPDATED_AVAILABLE_THURSDAY
        defaultEmployeeAvailabilityShouldBeFound("availableThursday.notEquals=" + UPDATED_AVAILABLE_THURSDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableThursdayIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableThursday in DEFAULT_AVAILABLE_THURSDAY or UPDATED_AVAILABLE_THURSDAY
        defaultEmployeeAvailabilityShouldBeFound("availableThursday.in=" + DEFAULT_AVAILABLE_THURSDAY + "," + UPDATED_AVAILABLE_THURSDAY);

        // Get all the employeeAvailabilityList where availableThursday equals to UPDATED_AVAILABLE_THURSDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableThursday.in=" + UPDATED_AVAILABLE_THURSDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableThursdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableThursday is not null
        defaultEmployeeAvailabilityShouldBeFound("availableThursday.specified=true");

        // Get all the employeeAvailabilityList where availableThursday is null
        defaultEmployeeAvailabilityShouldNotBeFound("availableThursday.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableFridayIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableFriday equals to DEFAULT_AVAILABLE_FRIDAY
        defaultEmployeeAvailabilityShouldBeFound("availableFriday.equals=" + DEFAULT_AVAILABLE_FRIDAY);

        // Get all the employeeAvailabilityList where availableFriday equals to UPDATED_AVAILABLE_FRIDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableFriday.equals=" + UPDATED_AVAILABLE_FRIDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableFridayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableFriday not equals to DEFAULT_AVAILABLE_FRIDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableFriday.notEquals=" + DEFAULT_AVAILABLE_FRIDAY);

        // Get all the employeeAvailabilityList where availableFriday not equals to UPDATED_AVAILABLE_FRIDAY
        defaultEmployeeAvailabilityShouldBeFound("availableFriday.notEquals=" + UPDATED_AVAILABLE_FRIDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableFridayIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableFriday in DEFAULT_AVAILABLE_FRIDAY or UPDATED_AVAILABLE_FRIDAY
        defaultEmployeeAvailabilityShouldBeFound("availableFriday.in=" + DEFAULT_AVAILABLE_FRIDAY + "," + UPDATED_AVAILABLE_FRIDAY);

        // Get all the employeeAvailabilityList where availableFriday equals to UPDATED_AVAILABLE_FRIDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableFriday.in=" + UPDATED_AVAILABLE_FRIDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableFridayIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableFriday is not null
        defaultEmployeeAvailabilityShouldBeFound("availableFriday.specified=true");

        // Get all the employeeAvailabilityList where availableFriday is null
        defaultEmployeeAvailabilityShouldNotBeFound("availableFriday.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableSaturdayIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableSaturday equals to DEFAULT_AVAILABLE_SATURDAY
        defaultEmployeeAvailabilityShouldBeFound("availableSaturday.equals=" + DEFAULT_AVAILABLE_SATURDAY);

        // Get all the employeeAvailabilityList where availableSaturday equals to UPDATED_AVAILABLE_SATURDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableSaturday.equals=" + UPDATED_AVAILABLE_SATURDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableSaturdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableSaturday not equals to DEFAULT_AVAILABLE_SATURDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableSaturday.notEquals=" + DEFAULT_AVAILABLE_SATURDAY);

        // Get all the employeeAvailabilityList where availableSaturday not equals to UPDATED_AVAILABLE_SATURDAY
        defaultEmployeeAvailabilityShouldBeFound("availableSaturday.notEquals=" + UPDATED_AVAILABLE_SATURDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableSaturdayIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableSaturday in DEFAULT_AVAILABLE_SATURDAY or UPDATED_AVAILABLE_SATURDAY
        defaultEmployeeAvailabilityShouldBeFound("availableSaturday.in=" + DEFAULT_AVAILABLE_SATURDAY + "," + UPDATED_AVAILABLE_SATURDAY);

        // Get all the employeeAvailabilityList where availableSaturday equals to UPDATED_AVAILABLE_SATURDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableSaturday.in=" + UPDATED_AVAILABLE_SATURDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableSaturdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableSaturday is not null
        defaultEmployeeAvailabilityShouldBeFound("availableSaturday.specified=true");

        // Get all the employeeAvailabilityList where availableSaturday is null
        defaultEmployeeAvailabilityShouldNotBeFound("availableSaturday.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableSundayIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableSunday equals to DEFAULT_AVAILABLE_SUNDAY
        defaultEmployeeAvailabilityShouldBeFound("availableSunday.equals=" + DEFAULT_AVAILABLE_SUNDAY);

        // Get all the employeeAvailabilityList where availableSunday equals to UPDATED_AVAILABLE_SUNDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableSunday.equals=" + UPDATED_AVAILABLE_SUNDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableSundayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableSunday not equals to DEFAULT_AVAILABLE_SUNDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableSunday.notEquals=" + DEFAULT_AVAILABLE_SUNDAY);

        // Get all the employeeAvailabilityList where availableSunday not equals to UPDATED_AVAILABLE_SUNDAY
        defaultEmployeeAvailabilityShouldBeFound("availableSunday.notEquals=" + UPDATED_AVAILABLE_SUNDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableSundayIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableSunday in DEFAULT_AVAILABLE_SUNDAY or UPDATED_AVAILABLE_SUNDAY
        defaultEmployeeAvailabilityShouldBeFound("availableSunday.in=" + DEFAULT_AVAILABLE_SUNDAY + "," + UPDATED_AVAILABLE_SUNDAY);

        // Get all the employeeAvailabilityList where availableSunday equals to UPDATED_AVAILABLE_SUNDAY
        defaultEmployeeAvailabilityShouldNotBeFound("availableSunday.in=" + UPDATED_AVAILABLE_SUNDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByAvailableSundayIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where availableSunday is not null
        defaultEmployeeAvailabilityShouldBeFound("availableSunday.specified=true");

        // Get all the employeeAvailabilityList where availableSunday is null
        defaultEmployeeAvailabilityShouldNotBeFound("availableSunday.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByPreferredShiftIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where preferredShift equals to DEFAULT_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldBeFound("preferredShift.equals=" + DEFAULT_PREFERRED_SHIFT);

        // Get all the employeeAvailabilityList where preferredShift equals to UPDATED_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldNotBeFound("preferredShift.equals=" + UPDATED_PREFERRED_SHIFT);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByPreferredShiftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where preferredShift not equals to DEFAULT_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldNotBeFound("preferredShift.notEquals=" + DEFAULT_PREFERRED_SHIFT);

        // Get all the employeeAvailabilityList where preferredShift not equals to UPDATED_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldBeFound("preferredShift.notEquals=" + UPDATED_PREFERRED_SHIFT);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByPreferredShiftIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where preferredShift in DEFAULT_PREFERRED_SHIFT or UPDATED_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldBeFound("preferredShift.in=" + DEFAULT_PREFERRED_SHIFT + "," + UPDATED_PREFERRED_SHIFT);

        // Get all the employeeAvailabilityList where preferredShift equals to UPDATED_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldNotBeFound("preferredShift.in=" + UPDATED_PREFERRED_SHIFT);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByPreferredShiftIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where preferredShift is not null
        defaultEmployeeAvailabilityShouldBeFound("preferredShift.specified=true");

        // Get all the employeeAvailabilityList where preferredShift is null
        defaultEmployeeAvailabilityShouldNotBeFound("preferredShift.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultEmployeeAvailabilityShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the employeeAvailabilityList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeAvailabilityShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultEmployeeAvailabilityShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the employeeAvailabilityList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeAvailabilityShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultEmployeeAvailabilityShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the employeeAvailabilityList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeAvailabilityShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where hasExtraData is not null
        defaultEmployeeAvailabilityShouldBeFound("hasExtraData.specified=true");

        // Get all the employeeAvailabilityList where hasExtraData is null
        defaultEmployeeAvailabilityShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeAvailabilityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeAvailabilityList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the employeeAvailabilityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where lastUpdatedDate is not null
        defaultEmployeeAvailabilityShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the employeeAvailabilityList where lastUpdatedDate is null
        defaultEmployeeAvailabilityShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeAvailabilityList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeAvailabilityList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeAvailabilityList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeAvailabilityList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultEmployeeAvailabilityShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where clientId equals to DEFAULT_CLIENT_ID
        defaultEmployeeAvailabilityShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the employeeAvailabilityList where clientId equals to UPDATED_CLIENT_ID
        defaultEmployeeAvailabilityShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where clientId not equals to DEFAULT_CLIENT_ID
        defaultEmployeeAvailabilityShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the employeeAvailabilityList where clientId not equals to UPDATED_CLIENT_ID
        defaultEmployeeAvailabilityShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultEmployeeAvailabilityShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the employeeAvailabilityList where clientId equals to UPDATED_CLIENT_ID
        defaultEmployeeAvailabilityShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where clientId is not null
        defaultEmployeeAvailabilityShouldBeFound("clientId.specified=true");

        // Get all the employeeAvailabilityList where clientId is null
        defaultEmployeeAvailabilityShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultEmployeeAvailabilityShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the employeeAvailabilityList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultEmployeeAvailabilityShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultEmployeeAvailabilityShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the employeeAvailabilityList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultEmployeeAvailabilityShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where clientId is less than DEFAULT_CLIENT_ID
        defaultEmployeeAvailabilityShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the employeeAvailabilityList where clientId is less than UPDATED_CLIENT_ID
        defaultEmployeeAvailabilityShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where clientId is greater than DEFAULT_CLIENT_ID
        defaultEmployeeAvailabilityShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the employeeAvailabilityList where clientId is greater than SMALLER_CLIENT_ID
        defaultEmployeeAvailabilityShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeAvailability.setEmployee(employee);
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);
        Long employeeId = employee.getId();

        // Get all the employeeAvailabilityList where employee equals to employeeId
        defaultEmployeeAvailabilityShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeAvailabilityList where employee equals to employeeId + 1
        defaultEmployeeAvailabilityShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeAvailabilityShouldBeFound(String filter) throws Exception {
        restEmployeeAvailabilityMockMvc.perform(get("/api/employee-availabilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeAvailability.getId().intValue())))
            .andExpect(jsonPath("$.[*].availableForWork").value(hasItem(DEFAULT_AVAILABLE_FOR_WORK.booleanValue())))
            .andExpect(jsonPath("$.[*].availableMonday").value(hasItem(DEFAULT_AVAILABLE_MONDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableTuesday").value(hasItem(DEFAULT_AVAILABLE_TUESDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableWednesday").value(hasItem(DEFAULT_AVAILABLE_WEDNESDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableThursday").value(hasItem(DEFAULT_AVAILABLE_THURSDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableFriday").value(hasItem(DEFAULT_AVAILABLE_FRIDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableSaturday").value(hasItem(DEFAULT_AVAILABLE_SATURDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].availableSunday").value(hasItem(DEFAULT_AVAILABLE_SUNDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].preferredShift").value(hasItem(DEFAULT_PREFERRED_SHIFT.toString())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));

        // Check, that the count call also returns 1
        restEmployeeAvailabilityMockMvc.perform(get("/api/employee-availabilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeAvailabilityShouldNotBeFound(String filter) throws Exception {
        restEmployeeAvailabilityMockMvc.perform(get("/api/employee-availabilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeAvailabilityMockMvc.perform(get("/api/employee-availabilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeAvailability() throws Exception {
        // Get the employeeAvailability
        restEmployeeAvailabilityMockMvc.perform(get("/api/employee-availabilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeAvailability() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        int databaseSizeBeforeUpdate = employeeAvailabilityRepository.findAll().size();

        // Update the employeeAvailability
        EmployeeAvailability updatedEmployeeAvailability = employeeAvailabilityRepository.findById(employeeAvailability.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeAvailability are not directly saved in db
        em.detach(updatedEmployeeAvailability);
        updatedEmployeeAvailability
            .availableForWork(UPDATED_AVAILABLE_FOR_WORK)
            .availableMonday(UPDATED_AVAILABLE_MONDAY)
            .availableTuesday(UPDATED_AVAILABLE_TUESDAY)
            .availableWednesday(UPDATED_AVAILABLE_WEDNESDAY)
            .availableThursday(UPDATED_AVAILABLE_THURSDAY)
            .availableFriday(UPDATED_AVAILABLE_FRIDAY)
            .availableSaturday(UPDATED_AVAILABLE_SATURDAY)
            .availableSunday(UPDATED_AVAILABLE_SUNDAY)
            .preferredShift(UPDATED_PREFERRED_SHIFT)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        EmployeeAvailabilityDTO employeeAvailabilityDTO = employeeAvailabilityMapper.toDto(updatedEmployeeAvailability);

        restEmployeeAvailabilityMockMvc.perform(put("/api/employee-availabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAvailabilityDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeAvailability in the database
        List<EmployeeAvailability> employeeAvailabilityList = employeeAvailabilityRepository.findAll();
        assertThat(employeeAvailabilityList).hasSize(databaseSizeBeforeUpdate);
        EmployeeAvailability testEmployeeAvailability = employeeAvailabilityList.get(employeeAvailabilityList.size() - 1);
        assertThat(testEmployeeAvailability.isAvailableForWork()).isEqualTo(UPDATED_AVAILABLE_FOR_WORK);
        assertThat(testEmployeeAvailability.isAvailableMonday()).isEqualTo(UPDATED_AVAILABLE_MONDAY);
        assertThat(testEmployeeAvailability.isAvailableTuesday()).isEqualTo(UPDATED_AVAILABLE_TUESDAY);
        assertThat(testEmployeeAvailability.isAvailableWednesday()).isEqualTo(UPDATED_AVAILABLE_WEDNESDAY);
        assertThat(testEmployeeAvailability.isAvailableThursday()).isEqualTo(UPDATED_AVAILABLE_THURSDAY);
        assertThat(testEmployeeAvailability.isAvailableFriday()).isEqualTo(UPDATED_AVAILABLE_FRIDAY);
        assertThat(testEmployeeAvailability.isAvailableSaturday()).isEqualTo(UPDATED_AVAILABLE_SATURDAY);
        assertThat(testEmployeeAvailability.isAvailableSunday()).isEqualTo(UPDATED_AVAILABLE_SUNDAY);
        assertThat(testEmployeeAvailability.getPreferredShift()).isEqualTo(UPDATED_PREFERRED_SHIFT);
        assertThat(testEmployeeAvailability.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
        assertThat(testEmployeeAvailability.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testEmployeeAvailability.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeAvailability() throws Exception {
        int databaseSizeBeforeUpdate = employeeAvailabilityRepository.findAll().size();

        // Create the EmployeeAvailability
        EmployeeAvailabilityDTO employeeAvailabilityDTO = employeeAvailabilityMapper.toDto(employeeAvailability);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeAvailabilityMockMvc.perform(put("/api/employee-availabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAvailabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAvailability in the database
        List<EmployeeAvailability> employeeAvailabilityList = employeeAvailabilityRepository.findAll();
        assertThat(employeeAvailabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeAvailability() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        int databaseSizeBeforeDelete = employeeAvailabilityRepository.findAll().size();

        // Delete the employeeAvailability
        restEmployeeAvailabilityMockMvc.perform(delete("/api/employee-availabilities/{id}", employeeAvailability.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeAvailability> employeeAvailabilityList = employeeAvailabilityRepository.findAll();
        assertThat(employeeAvailabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
