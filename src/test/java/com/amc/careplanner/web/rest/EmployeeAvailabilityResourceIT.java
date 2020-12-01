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
import java.util.List;

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

    private static final Boolean DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS = false;
    private static final Boolean UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS = true;

    private static final Integer DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS = 1;
    private static final Integer UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS = 2;
    private static final Integer SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS = 1 - 1;

    private static final Integer DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS = 1;
    private static final Integer UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS = 2;
    private static final Integer SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS = 1 - 1;

    private static final Boolean DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS = false;
    private static final Boolean UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS = true;

    private static final Integer DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS = 1;
    private static final Integer UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS = 2;
    private static final Integer SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS = 1 - 1;

    private static final Integer DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS = 1;
    private static final Integer UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS = 2;
    private static final Integer SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS = 1 - 1;

    private static final Shift DEFAULT_LEAST_PREFERRED_SHIFT = Shift.MORNING_SHIFT;
    private static final Shift UPDATED_LEAST_PREFERRED_SHIFT = Shift.AFTERNOON_SHIFT;

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
            .isAvailableForWorkWeekDays(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS)
            .minimumHoursPerWeekWeekDays(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS)
            .maximumHoursPerWeekWeekDays(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS)
            .isAvailableForWorkWeekEnds(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS)
            .minimumHoursPerWeekWeekEnds(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS)
            .maximumHoursPerWeekWeekEnds(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS)
            .leastPreferredShift(DEFAULT_LEAST_PREFERRED_SHIFT);
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
            .isAvailableForWorkWeekDays(UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS)
            .minimumHoursPerWeekWeekDays(UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS)
            .maximumHoursPerWeekWeekDays(UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS)
            .isAvailableForWorkWeekEnds(UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS)
            .minimumHoursPerWeekWeekEnds(UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS)
            .maximumHoursPerWeekWeekEnds(UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS)
            .leastPreferredShift(UPDATED_LEAST_PREFERRED_SHIFT);
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
        assertThat(testEmployeeAvailability.isIsAvailableForWorkWeekDays()).isEqualTo(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS);
        assertThat(testEmployeeAvailability.getMinimumHoursPerWeekWeekDays()).isEqualTo(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);
        assertThat(testEmployeeAvailability.getMaximumHoursPerWeekWeekDays()).isEqualTo(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);
        assertThat(testEmployeeAvailability.isIsAvailableForWorkWeekEnds()).isEqualTo(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS);
        assertThat(testEmployeeAvailability.getMinimumHoursPerWeekWeekEnds()).isEqualTo(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);
        assertThat(testEmployeeAvailability.getMaximumHoursPerWeekWeekEnds()).isEqualTo(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);
        assertThat(testEmployeeAvailability.getLeastPreferredShift()).isEqualTo(DEFAULT_LEAST_PREFERRED_SHIFT);
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
    public void getAllEmployeeAvailabilities() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList
        restEmployeeAvailabilityMockMvc.perform(get("/api/employee-availabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeAvailability.getId().intValue())))
            .andExpect(jsonPath("$.[*].isAvailableForWorkWeekDays").value(hasItem(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumHoursPerWeekWeekDays").value(hasItem(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS)))
            .andExpect(jsonPath("$.[*].maximumHoursPerWeekWeekDays").value(hasItem(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS)))
            .andExpect(jsonPath("$.[*].isAvailableForWorkWeekEnds").value(hasItem(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumHoursPerWeekWeekEnds").value(hasItem(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS)))
            .andExpect(jsonPath("$.[*].maximumHoursPerWeekWeekEnds").value(hasItem(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS)))
            .andExpect(jsonPath("$.[*].leastPreferredShift").value(hasItem(DEFAULT_LEAST_PREFERRED_SHIFT.toString())));
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
            .andExpect(jsonPath("$.isAvailableForWorkWeekDays").value(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS.booleanValue()))
            .andExpect(jsonPath("$.minimumHoursPerWeekWeekDays").value(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS))
            .andExpect(jsonPath("$.maximumHoursPerWeekWeekDays").value(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS))
            .andExpect(jsonPath("$.isAvailableForWorkWeekEnds").value(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS.booleanValue()))
            .andExpect(jsonPath("$.minimumHoursPerWeekWeekEnds").value(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS))
            .andExpect(jsonPath("$.maximumHoursPerWeekWeekEnds").value(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS))
            .andExpect(jsonPath("$.leastPreferredShift").value(DEFAULT_LEAST_PREFERRED_SHIFT.toString()));
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
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkWeekDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekDays equals to DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWorkWeekDays.equals=" + DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekDays equals to UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWorkWeekDays.equals=" + UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkWeekDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekDays not equals to DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWorkWeekDays.notEquals=" + DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekDays not equals to UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWorkWeekDays.notEquals=" + UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkWeekDaysIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekDays in DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS or UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWorkWeekDays.in=" + DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS + "," + UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekDays equals to UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWorkWeekDays.in=" + UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkWeekDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekDays is not null
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWorkWeekDays.specified=true");

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekDays is null
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWorkWeekDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays equals to DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekDays.equals=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays equals to UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekDays.equals=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays not equals to DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekDays.notEquals=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays not equals to UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekDays.notEquals=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekDaysIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays in DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS or UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekDays.in=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS + "," + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays equals to UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekDays.in=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is not null
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekDays.specified=true");

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is null
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is greater than or equal to DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekDays.greaterThanOrEqual=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is greater than or equal to UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekDays.greaterThanOrEqual=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is less than or equal to DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekDays.lessThanOrEqual=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is less than or equal to SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekDays.lessThanOrEqual=" + SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is less than DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekDays.lessThan=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is less than UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekDays.lessThan=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is greater than DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekDays.greaterThan=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekDays is greater than SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekDays.greaterThan=" + SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }


    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays equals to DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekDays.equals=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays equals to UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekDays.equals=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays not equals to DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekDays.notEquals=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays not equals to UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekDays.notEquals=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekDaysIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays in DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS or UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekDays.in=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS + "," + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays equals to UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekDays.in=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is not null
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekDays.specified=true");

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is null
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is greater than or equal to DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekDays.greaterThanOrEqual=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is greater than or equal to UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekDays.greaterThanOrEqual=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is less than or equal to DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekDays.lessThanOrEqual=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is less than or equal to SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekDays.lessThanOrEqual=" + SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is less than DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekDays.lessThan=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is less than UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekDays.lessThan=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is greater than DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekDays.greaterThan=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekDays is greater than SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekDays.greaterThan=" + SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);
    }


    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkWeekEndsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekEnds equals to DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWorkWeekEnds.equals=" + DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekEnds equals to UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWorkWeekEnds.equals=" + UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkWeekEndsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekEnds not equals to DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWorkWeekEnds.notEquals=" + DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekEnds not equals to UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWorkWeekEnds.notEquals=" + UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkWeekEndsIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekEnds in DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS or UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWorkWeekEnds.in=" + DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS + "," + UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekEnds equals to UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWorkWeekEnds.in=" + UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkWeekEndsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekEnds is not null
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWorkWeekEnds.specified=true");

        // Get all the employeeAvailabilityList where isAvailableForWorkWeekEnds is null
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWorkWeekEnds.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekEndsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds equals to DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekEnds.equals=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds equals to UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekEnds.equals=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekEndsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds not equals to DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekEnds.notEquals=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds not equals to UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekEnds.notEquals=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekEndsIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds in DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS or UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekEnds.in=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS + "," + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds equals to UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekEnds.in=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekEndsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is not null
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekEnds.specified=true");

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is null
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekEnds.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekEndsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is greater than or equal to DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekEnds.greaterThanOrEqual=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is greater than or equal to UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekEnds.greaterThanOrEqual=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekEndsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is less than or equal to DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekEnds.lessThanOrEqual=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is less than or equal to SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekEnds.lessThanOrEqual=" + SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekEndsIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is less than DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekEnds.lessThan=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is less than UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekEnds.lessThan=" + UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekWeekEndsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is greater than DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeekWeekEnds.greaterThan=" + DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where minimumHoursPerWeekWeekEnds is greater than SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeekWeekEnds.greaterThan=" + SMALLER_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }


    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekEndsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds equals to DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekEnds.equals=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds equals to UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekEnds.equals=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekEndsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds not equals to DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekEnds.notEquals=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds not equals to UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekEnds.notEquals=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekEndsIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds in DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS or UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekEnds.in=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS + "," + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds equals to UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekEnds.in=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekEndsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is not null
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekEnds.specified=true");

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is null
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekEnds.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekEndsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is greater than or equal to DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekEnds.greaterThanOrEqual=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is greater than or equal to UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekEnds.greaterThanOrEqual=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekEndsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is less than or equal to DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekEnds.lessThanOrEqual=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is less than or equal to SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekEnds.lessThanOrEqual=" + SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekEndsIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is less than DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekEnds.lessThan=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is less than UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekEnds.lessThan=" + UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekWeekEndsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is greater than DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeekWeekEnds.greaterThan=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);

        // Get all the employeeAvailabilityList where maximumHoursPerWeekWeekEnds is greater than SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeekWeekEnds.greaterThan=" + SMALLER_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);
    }


    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLeastPreferredShiftIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where leastPreferredShift equals to DEFAULT_LEAST_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldBeFound("leastPreferredShift.equals=" + DEFAULT_LEAST_PREFERRED_SHIFT);

        // Get all the employeeAvailabilityList where leastPreferredShift equals to UPDATED_LEAST_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldNotBeFound("leastPreferredShift.equals=" + UPDATED_LEAST_PREFERRED_SHIFT);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLeastPreferredShiftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where leastPreferredShift not equals to DEFAULT_LEAST_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldNotBeFound("leastPreferredShift.notEquals=" + DEFAULT_LEAST_PREFERRED_SHIFT);

        // Get all the employeeAvailabilityList where leastPreferredShift not equals to UPDATED_LEAST_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldBeFound("leastPreferredShift.notEquals=" + UPDATED_LEAST_PREFERRED_SHIFT);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLeastPreferredShiftIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where leastPreferredShift in DEFAULT_LEAST_PREFERRED_SHIFT or UPDATED_LEAST_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldBeFound("leastPreferredShift.in=" + DEFAULT_LEAST_PREFERRED_SHIFT + "," + UPDATED_LEAST_PREFERRED_SHIFT);

        // Get all the employeeAvailabilityList where leastPreferredShift equals to UPDATED_LEAST_PREFERRED_SHIFT
        defaultEmployeeAvailabilityShouldNotBeFound("leastPreferredShift.in=" + UPDATED_LEAST_PREFERRED_SHIFT);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByLeastPreferredShiftIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where leastPreferredShift is not null
        defaultEmployeeAvailabilityShouldBeFound("leastPreferredShift.specified=true");

        // Get all the employeeAvailabilityList where leastPreferredShift is null
        defaultEmployeeAvailabilityShouldNotBeFound("leastPreferredShift.specified=false");
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
            .andExpect(jsonPath("$.[*].isAvailableForWorkWeekDays").value(hasItem(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_DAYS.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumHoursPerWeekWeekDays").value(hasItem(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS)))
            .andExpect(jsonPath("$.[*].maximumHoursPerWeekWeekDays").value(hasItem(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS)))
            .andExpect(jsonPath("$.[*].isAvailableForWorkWeekEnds").value(hasItem(DEFAULT_IS_AVAILABLE_FOR_WORK_WEEK_ENDS.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumHoursPerWeekWeekEnds").value(hasItem(DEFAULT_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS)))
            .andExpect(jsonPath("$.[*].maximumHoursPerWeekWeekEnds").value(hasItem(DEFAULT_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS)))
            .andExpect(jsonPath("$.[*].leastPreferredShift").value(hasItem(DEFAULT_LEAST_PREFERRED_SHIFT.toString())));

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
            .isAvailableForWorkWeekDays(UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS)
            .minimumHoursPerWeekWeekDays(UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS)
            .maximumHoursPerWeekWeekDays(UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS)
            .isAvailableForWorkWeekEnds(UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS)
            .minimumHoursPerWeekWeekEnds(UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS)
            .maximumHoursPerWeekWeekEnds(UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS)
            .leastPreferredShift(UPDATED_LEAST_PREFERRED_SHIFT);
        EmployeeAvailabilityDTO employeeAvailabilityDTO = employeeAvailabilityMapper.toDto(updatedEmployeeAvailability);

        restEmployeeAvailabilityMockMvc.perform(put("/api/employee-availabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAvailabilityDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeAvailability in the database
        List<EmployeeAvailability> employeeAvailabilityList = employeeAvailabilityRepository.findAll();
        assertThat(employeeAvailabilityList).hasSize(databaseSizeBeforeUpdate);
        EmployeeAvailability testEmployeeAvailability = employeeAvailabilityList.get(employeeAvailabilityList.size() - 1);
        assertThat(testEmployeeAvailability.isIsAvailableForWorkWeekDays()).isEqualTo(UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_DAYS);
        assertThat(testEmployeeAvailability.getMinimumHoursPerWeekWeekDays()).isEqualTo(UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_DAYS);
        assertThat(testEmployeeAvailability.getMaximumHoursPerWeekWeekDays()).isEqualTo(UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_DAYS);
        assertThat(testEmployeeAvailability.isIsAvailableForWorkWeekEnds()).isEqualTo(UPDATED_IS_AVAILABLE_FOR_WORK_WEEK_ENDS);
        assertThat(testEmployeeAvailability.getMinimumHoursPerWeekWeekEnds()).isEqualTo(UPDATED_MINIMUM_HOURS_PER_WEEK_WEEK_ENDS);
        assertThat(testEmployeeAvailability.getMaximumHoursPerWeekWeekEnds()).isEqualTo(UPDATED_MAXIMUM_HOURS_PER_WEEK_WEEK_ENDS);
        assertThat(testEmployeeAvailability.getLeastPreferredShift()).isEqualTo(UPDATED_LEAST_PREFERRED_SHIFT);
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
