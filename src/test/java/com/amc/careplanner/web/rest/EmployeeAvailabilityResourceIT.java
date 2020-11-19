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

    private static final Boolean DEFAULT_IS_AVAILABLE_FOR_WORK = false;
    private static final Boolean UPDATED_IS_AVAILABLE_FOR_WORK = true;

    private static final Integer DEFAULT_MINIMUM_HOURS_PER_WEEK = 1;
    private static final Integer UPDATED_MINIMUM_HOURS_PER_WEEK = 2;
    private static final Integer SMALLER_MINIMUM_HOURS_PER_WEEK = 1 - 1;

    private static final Integer DEFAULT_MAXIMUM_HOURS_PER_WEEK = 1;
    private static final Integer UPDATED_MAXIMUM_HOURS_PER_WEEK = 2;
    private static final Integer SMALLER_MAXIMUM_HOURS_PER_WEEK = 1 - 1;

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
            .isAvailableForWork(DEFAULT_IS_AVAILABLE_FOR_WORK)
            .minimumHoursPerWeek(DEFAULT_MINIMUM_HOURS_PER_WEEK)
            .maximumHoursPerWeek(DEFAULT_MAXIMUM_HOURS_PER_WEEK)
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
            .isAvailableForWork(UPDATED_IS_AVAILABLE_FOR_WORK)
            .minimumHoursPerWeek(UPDATED_MINIMUM_HOURS_PER_WEEK)
            .maximumHoursPerWeek(UPDATED_MAXIMUM_HOURS_PER_WEEK)
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
        assertThat(testEmployeeAvailability.isIsAvailableForWork()).isEqualTo(DEFAULT_IS_AVAILABLE_FOR_WORK);
        assertThat(testEmployeeAvailability.getMinimumHoursPerWeek()).isEqualTo(DEFAULT_MINIMUM_HOURS_PER_WEEK);
        assertThat(testEmployeeAvailability.getMaximumHoursPerWeek()).isEqualTo(DEFAULT_MAXIMUM_HOURS_PER_WEEK);
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
            .andExpect(jsonPath("$.[*].isAvailableForWork").value(hasItem(DEFAULT_IS_AVAILABLE_FOR_WORK.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumHoursPerWeek").value(hasItem(DEFAULT_MINIMUM_HOURS_PER_WEEK)))
            .andExpect(jsonPath("$.[*].maximumHoursPerWeek").value(hasItem(DEFAULT_MAXIMUM_HOURS_PER_WEEK)))
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
            .andExpect(jsonPath("$.isAvailableForWork").value(DEFAULT_IS_AVAILABLE_FOR_WORK.booleanValue()))
            .andExpect(jsonPath("$.minimumHoursPerWeek").value(DEFAULT_MINIMUM_HOURS_PER_WEEK))
            .andExpect(jsonPath("$.maximumHoursPerWeek").value(DEFAULT_MAXIMUM_HOURS_PER_WEEK))
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
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWork equals to DEFAULT_IS_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWork.equals=" + DEFAULT_IS_AVAILABLE_FOR_WORK);

        // Get all the employeeAvailabilityList where isAvailableForWork equals to UPDATED_IS_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWork.equals=" + UPDATED_IS_AVAILABLE_FOR_WORK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWork not equals to DEFAULT_IS_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWork.notEquals=" + DEFAULT_IS_AVAILABLE_FOR_WORK);

        // Get all the employeeAvailabilityList where isAvailableForWork not equals to UPDATED_IS_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWork.notEquals=" + UPDATED_IS_AVAILABLE_FOR_WORK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWork in DEFAULT_IS_AVAILABLE_FOR_WORK or UPDATED_IS_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWork.in=" + DEFAULT_IS_AVAILABLE_FOR_WORK + "," + UPDATED_IS_AVAILABLE_FOR_WORK);

        // Get all the employeeAvailabilityList where isAvailableForWork equals to UPDATED_IS_AVAILABLE_FOR_WORK
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWork.in=" + UPDATED_IS_AVAILABLE_FOR_WORK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByIsAvailableForWorkIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where isAvailableForWork is not null
        defaultEmployeeAvailabilityShouldBeFound("isAvailableForWork.specified=true");

        // Get all the employeeAvailabilityList where isAvailableForWork is null
        defaultEmployeeAvailabilityShouldNotBeFound("isAvailableForWork.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek equals to DEFAULT_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeek.equals=" + DEFAULT_MINIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek equals to UPDATED_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeek.equals=" + UPDATED_MINIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek not equals to DEFAULT_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeek.notEquals=" + DEFAULT_MINIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek not equals to UPDATED_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeek.notEquals=" + UPDATED_MINIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek in DEFAULT_MINIMUM_HOURS_PER_WEEK or UPDATED_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeek.in=" + DEFAULT_MINIMUM_HOURS_PER_WEEK + "," + UPDATED_MINIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek equals to UPDATED_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeek.in=" + UPDATED_MINIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is not null
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeek.specified=true");

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is null
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeek.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is greater than or equal to DEFAULT_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeek.greaterThanOrEqual=" + DEFAULT_MINIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is greater than or equal to UPDATED_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeek.greaterThanOrEqual=" + UPDATED_MINIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is less than or equal to DEFAULT_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeek.lessThanOrEqual=" + DEFAULT_MINIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is less than or equal to SMALLER_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeek.lessThanOrEqual=" + SMALLER_MINIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is less than DEFAULT_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeek.lessThan=" + DEFAULT_MINIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is less than UPDATED_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeek.lessThan=" + UPDATED_MINIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMinimumHoursPerWeekIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is greater than DEFAULT_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("minimumHoursPerWeek.greaterThan=" + DEFAULT_MINIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where minimumHoursPerWeek is greater than SMALLER_MINIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("minimumHoursPerWeek.greaterThan=" + SMALLER_MINIMUM_HOURS_PER_WEEK);
    }


    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek equals to DEFAULT_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeek.equals=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek equals to UPDATED_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeek.equals=" + UPDATED_MAXIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek not equals to DEFAULT_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeek.notEquals=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek not equals to UPDATED_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeek.notEquals=" + UPDATED_MAXIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek in DEFAULT_MAXIMUM_HOURS_PER_WEEK or UPDATED_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeek.in=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK + "," + UPDATED_MAXIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek equals to UPDATED_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeek.in=" + UPDATED_MAXIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is not null
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeek.specified=true");

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is null
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeek.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is greater than or equal to DEFAULT_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeek.greaterThanOrEqual=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is greater than or equal to UPDATED_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeek.greaterThanOrEqual=" + UPDATED_MAXIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is less than or equal to DEFAULT_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeek.lessThanOrEqual=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is less than or equal to SMALLER_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeek.lessThanOrEqual=" + SMALLER_MAXIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is less than DEFAULT_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeek.lessThan=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is less than UPDATED_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeek.lessThan=" + UPDATED_MAXIMUM_HOURS_PER_WEEK);
    }

    @Test
    @Transactional
    public void getAllEmployeeAvailabilitiesByMaximumHoursPerWeekIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeAvailabilityRepository.saveAndFlush(employeeAvailability);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is greater than DEFAULT_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldNotBeFound("maximumHoursPerWeek.greaterThan=" + DEFAULT_MAXIMUM_HOURS_PER_WEEK);

        // Get all the employeeAvailabilityList where maximumHoursPerWeek is greater than SMALLER_MAXIMUM_HOURS_PER_WEEK
        defaultEmployeeAvailabilityShouldBeFound("maximumHoursPerWeek.greaterThan=" + SMALLER_MAXIMUM_HOURS_PER_WEEK);
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
            .andExpect(jsonPath("$.[*].isAvailableForWork").value(hasItem(DEFAULT_IS_AVAILABLE_FOR_WORK.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumHoursPerWeek").value(hasItem(DEFAULT_MINIMUM_HOURS_PER_WEEK)))
            .andExpect(jsonPath("$.[*].maximumHoursPerWeek").value(hasItem(DEFAULT_MAXIMUM_HOURS_PER_WEEK)))
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
            .isAvailableForWork(UPDATED_IS_AVAILABLE_FOR_WORK)
            .minimumHoursPerWeek(UPDATED_MINIMUM_HOURS_PER_WEEK)
            .maximumHoursPerWeek(UPDATED_MAXIMUM_HOURS_PER_WEEK)
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
        assertThat(testEmployeeAvailability.isIsAvailableForWork()).isEqualTo(UPDATED_IS_AVAILABLE_FOR_WORK);
        assertThat(testEmployeeAvailability.getMinimumHoursPerWeek()).isEqualTo(UPDATED_MINIMUM_HOURS_PER_WEEK);
        assertThat(testEmployeeAvailability.getMaximumHoursPerWeek()).isEqualTo(UPDATED_MAXIMUM_HOURS_PER_WEEK);
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
