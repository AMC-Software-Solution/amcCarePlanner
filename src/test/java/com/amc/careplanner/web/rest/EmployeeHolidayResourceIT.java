package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.EmployeeHoliday;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.EmployeeHolidayRepository;
import com.amc.careplanner.service.EmployeeHolidayService;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.mapper.EmployeeHolidayMapper;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.EmployeeHolidayQueryService;

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

import com.amc.careplanner.domain.enumeration.EmployeeHolidayType;
/**
 * Integration tests for the {@link EmployeeHolidayResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeHolidayResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final EmployeeHolidayType DEFAULT_EMPLOYEE_HOLIDAY_TYPE = EmployeeHolidayType.ANNUAL_HOLIDAY;
    private static final EmployeeHolidayType UPDATED_EMPLOYEE_HOLIDAY_TYPE = EmployeeHolidayType.PUBLIC_HOLIDAY;

    private static final ZonedDateTime DEFAULT_APPROVED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_APPROVED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_APPROVED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_REQUESTED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REQUESTED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_REQUESTED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private EmployeeHolidayRepository employeeHolidayRepository;

    @Autowired
    private EmployeeHolidayMapper employeeHolidayMapper;

    @Autowired
    private EmployeeHolidayService employeeHolidayService;

    @Autowired
    private EmployeeHolidayQueryService employeeHolidayQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeHolidayMockMvc;

    private EmployeeHoliday employeeHoliday;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeHoliday createEntity(EntityManager em) {
        EmployeeHoliday employeeHoliday = new EmployeeHoliday()
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .employeeHolidayType(DEFAULT_EMPLOYEE_HOLIDAY_TYPE)
            .approvedDate(DEFAULT_APPROVED_DATE)
            .requestedDate(DEFAULT_REQUESTED_DATE)
            .approved(DEFAULT_APPROVED)
            .note(DEFAULT_NOTE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return employeeHoliday;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeHoliday createUpdatedEntity(EntityManager em) {
        EmployeeHoliday employeeHoliday = new EmployeeHoliday()
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .employeeHolidayType(UPDATED_EMPLOYEE_HOLIDAY_TYPE)
            .approvedDate(UPDATED_APPROVED_DATE)
            .requestedDate(UPDATED_REQUESTED_DATE)
            .approved(UPDATED_APPROVED)
            .note(UPDATED_NOTE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return employeeHoliday;
    }

    @BeforeEach
    public void initTest() {
        employeeHoliday = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeHoliday() throws Exception {
        int databaseSizeBeforeCreate = employeeHolidayRepository.findAll().size();
        // Create the EmployeeHoliday
        EmployeeHolidayDTO employeeHolidayDTO = employeeHolidayMapper.toDto(employeeHoliday);
        restEmployeeHolidayMockMvc.perform(post("/api/employee-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeHolidayDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeHoliday in the database
        List<EmployeeHoliday> employeeHolidayList = employeeHolidayRepository.findAll();
        assertThat(employeeHolidayList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeHoliday testEmployeeHoliday = employeeHolidayList.get(employeeHolidayList.size() - 1);
        assertThat(testEmployeeHoliday.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEmployeeHoliday.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEmployeeHoliday.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEmployeeHoliday.getEmployeeHolidayType()).isEqualTo(DEFAULT_EMPLOYEE_HOLIDAY_TYPE);
        assertThat(testEmployeeHoliday.getApprovedDate()).isEqualTo(DEFAULT_APPROVED_DATE);
        assertThat(testEmployeeHoliday.getRequestedDate()).isEqualTo(DEFAULT_REQUESTED_DATE);
        assertThat(testEmployeeHoliday.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testEmployeeHoliday.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testEmployeeHoliday.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testEmployeeHoliday.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createEmployeeHolidayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeHolidayRepository.findAll().size();

        // Create the EmployeeHoliday with an existing ID
        employeeHoliday.setId(1L);
        EmployeeHolidayDTO employeeHolidayDTO = employeeHolidayMapper.toDto(employeeHoliday);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeHolidayMockMvc.perform(post("/api/employee-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeHolidayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeHoliday in the database
        List<EmployeeHoliday> employeeHolidayList = employeeHolidayRepository.findAll();
        assertThat(employeeHolidayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeHolidayRepository.findAll().size();
        // set the field null
        employeeHoliday.setDescription(null);

        // Create the EmployeeHoliday, which fails.
        EmployeeHolidayDTO employeeHolidayDTO = employeeHolidayMapper.toDto(employeeHoliday);


        restEmployeeHolidayMockMvc.perform(post("/api/employee-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeHolidayDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeHoliday> employeeHolidayList = employeeHolidayRepository.findAll();
        assertThat(employeeHolidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeHolidayRepository.findAll().size();
        // set the field null
        employeeHoliday.setTenantId(null);

        // Create the EmployeeHoliday, which fails.
        EmployeeHolidayDTO employeeHolidayDTO = employeeHolidayMapper.toDto(employeeHoliday);


        restEmployeeHolidayMockMvc.perform(post("/api/employee-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeHolidayDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeHoliday> employeeHolidayList = employeeHolidayRepository.findAll();
        assertThat(employeeHolidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidays() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList
        restEmployeeHolidayMockMvc.perform(get("/api/employee-holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeHoliday.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].employeeHolidayType").value(hasItem(DEFAULT_EMPLOYEE_HOLIDAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].approvedDate").value(hasItem(sameInstant(DEFAULT_APPROVED_DATE))))
            .andExpect(jsonPath("$.[*].requestedDate").value(hasItem(sameInstant(DEFAULT_REQUESTED_DATE))))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeHoliday() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get the employeeHoliday
        restEmployeeHolidayMockMvc.perform(get("/api/employee-holidays/{id}", employeeHoliday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeHoliday.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.employeeHolidayType").value(DEFAULT_EMPLOYEE_HOLIDAY_TYPE.toString()))
            .andExpect(jsonPath("$.approvedDate").value(sameInstant(DEFAULT_APPROVED_DATE)))
            .andExpect(jsonPath("$.requestedDate").value(sameInstant(DEFAULT_REQUESTED_DATE)))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getEmployeeHolidaysByIdFiltering() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        Long id = employeeHoliday.getId();

        defaultEmployeeHolidayShouldBeFound("id.equals=" + id);
        defaultEmployeeHolidayShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeHolidayShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeHolidayShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeHolidayShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeHolidayShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where description equals to DEFAULT_DESCRIPTION
        defaultEmployeeHolidayShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the employeeHolidayList where description equals to UPDATED_DESCRIPTION
        defaultEmployeeHolidayShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where description not equals to DEFAULT_DESCRIPTION
        defaultEmployeeHolidayShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the employeeHolidayList where description not equals to UPDATED_DESCRIPTION
        defaultEmployeeHolidayShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEmployeeHolidayShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the employeeHolidayList where description equals to UPDATED_DESCRIPTION
        defaultEmployeeHolidayShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where description is not null
        defaultEmployeeHolidayShouldBeFound("description.specified=true");

        // Get all the employeeHolidayList where description is null
        defaultEmployeeHolidayShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeHolidaysByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where description contains DEFAULT_DESCRIPTION
        defaultEmployeeHolidayShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the employeeHolidayList where description contains UPDATED_DESCRIPTION
        defaultEmployeeHolidayShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where description does not contain DEFAULT_DESCRIPTION
        defaultEmployeeHolidayShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the employeeHolidayList where description does not contain UPDATED_DESCRIPTION
        defaultEmployeeHolidayShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where startDate equals to DEFAULT_START_DATE
        defaultEmployeeHolidayShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the employeeHolidayList where startDate equals to UPDATED_START_DATE
        defaultEmployeeHolidayShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where startDate not equals to DEFAULT_START_DATE
        defaultEmployeeHolidayShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the employeeHolidayList where startDate not equals to UPDATED_START_DATE
        defaultEmployeeHolidayShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultEmployeeHolidayShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the employeeHolidayList where startDate equals to UPDATED_START_DATE
        defaultEmployeeHolidayShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where startDate is not null
        defaultEmployeeHolidayShouldBeFound("startDate.specified=true");

        // Get all the employeeHolidayList where startDate is null
        defaultEmployeeHolidayShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultEmployeeHolidayShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the employeeHolidayList where startDate is greater than or equal to UPDATED_START_DATE
        defaultEmployeeHolidayShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where startDate is less than or equal to DEFAULT_START_DATE
        defaultEmployeeHolidayShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the employeeHolidayList where startDate is less than or equal to SMALLER_START_DATE
        defaultEmployeeHolidayShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where startDate is less than DEFAULT_START_DATE
        defaultEmployeeHolidayShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the employeeHolidayList where startDate is less than UPDATED_START_DATE
        defaultEmployeeHolidayShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where startDate is greater than DEFAULT_START_DATE
        defaultEmployeeHolidayShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the employeeHolidayList where startDate is greater than SMALLER_START_DATE
        defaultEmployeeHolidayShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where endDate equals to DEFAULT_END_DATE
        defaultEmployeeHolidayShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the employeeHolidayList where endDate equals to UPDATED_END_DATE
        defaultEmployeeHolidayShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where endDate not equals to DEFAULT_END_DATE
        defaultEmployeeHolidayShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the employeeHolidayList where endDate not equals to UPDATED_END_DATE
        defaultEmployeeHolidayShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultEmployeeHolidayShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the employeeHolidayList where endDate equals to UPDATED_END_DATE
        defaultEmployeeHolidayShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where endDate is not null
        defaultEmployeeHolidayShouldBeFound("endDate.specified=true");

        // Get all the employeeHolidayList where endDate is null
        defaultEmployeeHolidayShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultEmployeeHolidayShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the employeeHolidayList where endDate is greater than or equal to UPDATED_END_DATE
        defaultEmployeeHolidayShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where endDate is less than or equal to DEFAULT_END_DATE
        defaultEmployeeHolidayShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the employeeHolidayList where endDate is less than or equal to SMALLER_END_DATE
        defaultEmployeeHolidayShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where endDate is less than DEFAULT_END_DATE
        defaultEmployeeHolidayShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the employeeHolidayList where endDate is less than UPDATED_END_DATE
        defaultEmployeeHolidayShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where endDate is greater than DEFAULT_END_DATE
        defaultEmployeeHolidayShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the employeeHolidayList where endDate is greater than SMALLER_END_DATE
        defaultEmployeeHolidayShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEmployeeHolidayTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where employeeHolidayType equals to DEFAULT_EMPLOYEE_HOLIDAY_TYPE
        defaultEmployeeHolidayShouldBeFound("employeeHolidayType.equals=" + DEFAULT_EMPLOYEE_HOLIDAY_TYPE);

        // Get all the employeeHolidayList where employeeHolidayType equals to UPDATED_EMPLOYEE_HOLIDAY_TYPE
        defaultEmployeeHolidayShouldNotBeFound("employeeHolidayType.equals=" + UPDATED_EMPLOYEE_HOLIDAY_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEmployeeHolidayTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where employeeHolidayType not equals to DEFAULT_EMPLOYEE_HOLIDAY_TYPE
        defaultEmployeeHolidayShouldNotBeFound("employeeHolidayType.notEquals=" + DEFAULT_EMPLOYEE_HOLIDAY_TYPE);

        // Get all the employeeHolidayList where employeeHolidayType not equals to UPDATED_EMPLOYEE_HOLIDAY_TYPE
        defaultEmployeeHolidayShouldBeFound("employeeHolidayType.notEquals=" + UPDATED_EMPLOYEE_HOLIDAY_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEmployeeHolidayTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where employeeHolidayType in DEFAULT_EMPLOYEE_HOLIDAY_TYPE or UPDATED_EMPLOYEE_HOLIDAY_TYPE
        defaultEmployeeHolidayShouldBeFound("employeeHolidayType.in=" + DEFAULT_EMPLOYEE_HOLIDAY_TYPE + "," + UPDATED_EMPLOYEE_HOLIDAY_TYPE);

        // Get all the employeeHolidayList where employeeHolidayType equals to UPDATED_EMPLOYEE_HOLIDAY_TYPE
        defaultEmployeeHolidayShouldNotBeFound("employeeHolidayType.in=" + UPDATED_EMPLOYEE_HOLIDAY_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEmployeeHolidayTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where employeeHolidayType is not null
        defaultEmployeeHolidayShouldBeFound("employeeHolidayType.specified=true");

        // Get all the employeeHolidayList where employeeHolidayType is null
        defaultEmployeeHolidayShouldNotBeFound("employeeHolidayType.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approvedDate equals to DEFAULT_APPROVED_DATE
        defaultEmployeeHolidayShouldBeFound("approvedDate.equals=" + DEFAULT_APPROVED_DATE);

        // Get all the employeeHolidayList where approvedDate equals to UPDATED_APPROVED_DATE
        defaultEmployeeHolidayShouldNotBeFound("approvedDate.equals=" + UPDATED_APPROVED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approvedDate not equals to DEFAULT_APPROVED_DATE
        defaultEmployeeHolidayShouldNotBeFound("approvedDate.notEquals=" + DEFAULT_APPROVED_DATE);

        // Get all the employeeHolidayList where approvedDate not equals to UPDATED_APPROVED_DATE
        defaultEmployeeHolidayShouldBeFound("approvedDate.notEquals=" + UPDATED_APPROVED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approvedDate in DEFAULT_APPROVED_DATE or UPDATED_APPROVED_DATE
        defaultEmployeeHolidayShouldBeFound("approvedDate.in=" + DEFAULT_APPROVED_DATE + "," + UPDATED_APPROVED_DATE);

        // Get all the employeeHolidayList where approvedDate equals to UPDATED_APPROVED_DATE
        defaultEmployeeHolidayShouldNotBeFound("approvedDate.in=" + UPDATED_APPROVED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approvedDate is not null
        defaultEmployeeHolidayShouldBeFound("approvedDate.specified=true");

        // Get all the employeeHolidayList where approvedDate is null
        defaultEmployeeHolidayShouldNotBeFound("approvedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approvedDate is greater than or equal to DEFAULT_APPROVED_DATE
        defaultEmployeeHolidayShouldBeFound("approvedDate.greaterThanOrEqual=" + DEFAULT_APPROVED_DATE);

        // Get all the employeeHolidayList where approvedDate is greater than or equal to UPDATED_APPROVED_DATE
        defaultEmployeeHolidayShouldNotBeFound("approvedDate.greaterThanOrEqual=" + UPDATED_APPROVED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approvedDate is less than or equal to DEFAULT_APPROVED_DATE
        defaultEmployeeHolidayShouldBeFound("approvedDate.lessThanOrEqual=" + DEFAULT_APPROVED_DATE);

        // Get all the employeeHolidayList where approvedDate is less than or equal to SMALLER_APPROVED_DATE
        defaultEmployeeHolidayShouldNotBeFound("approvedDate.lessThanOrEqual=" + SMALLER_APPROVED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approvedDate is less than DEFAULT_APPROVED_DATE
        defaultEmployeeHolidayShouldNotBeFound("approvedDate.lessThan=" + DEFAULT_APPROVED_DATE);

        // Get all the employeeHolidayList where approvedDate is less than UPDATED_APPROVED_DATE
        defaultEmployeeHolidayShouldBeFound("approvedDate.lessThan=" + UPDATED_APPROVED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approvedDate is greater than DEFAULT_APPROVED_DATE
        defaultEmployeeHolidayShouldNotBeFound("approvedDate.greaterThan=" + DEFAULT_APPROVED_DATE);

        // Get all the employeeHolidayList where approvedDate is greater than SMALLER_APPROVED_DATE
        defaultEmployeeHolidayShouldBeFound("approvedDate.greaterThan=" + SMALLER_APPROVED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByRequestedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where requestedDate equals to DEFAULT_REQUESTED_DATE
        defaultEmployeeHolidayShouldBeFound("requestedDate.equals=" + DEFAULT_REQUESTED_DATE);

        // Get all the employeeHolidayList where requestedDate equals to UPDATED_REQUESTED_DATE
        defaultEmployeeHolidayShouldNotBeFound("requestedDate.equals=" + UPDATED_REQUESTED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByRequestedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where requestedDate not equals to DEFAULT_REQUESTED_DATE
        defaultEmployeeHolidayShouldNotBeFound("requestedDate.notEquals=" + DEFAULT_REQUESTED_DATE);

        // Get all the employeeHolidayList where requestedDate not equals to UPDATED_REQUESTED_DATE
        defaultEmployeeHolidayShouldBeFound("requestedDate.notEquals=" + UPDATED_REQUESTED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByRequestedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where requestedDate in DEFAULT_REQUESTED_DATE or UPDATED_REQUESTED_DATE
        defaultEmployeeHolidayShouldBeFound("requestedDate.in=" + DEFAULT_REQUESTED_DATE + "," + UPDATED_REQUESTED_DATE);

        // Get all the employeeHolidayList where requestedDate equals to UPDATED_REQUESTED_DATE
        defaultEmployeeHolidayShouldNotBeFound("requestedDate.in=" + UPDATED_REQUESTED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByRequestedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where requestedDate is not null
        defaultEmployeeHolidayShouldBeFound("requestedDate.specified=true");

        // Get all the employeeHolidayList where requestedDate is null
        defaultEmployeeHolidayShouldNotBeFound("requestedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByRequestedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where requestedDate is greater than or equal to DEFAULT_REQUESTED_DATE
        defaultEmployeeHolidayShouldBeFound("requestedDate.greaterThanOrEqual=" + DEFAULT_REQUESTED_DATE);

        // Get all the employeeHolidayList where requestedDate is greater than or equal to UPDATED_REQUESTED_DATE
        defaultEmployeeHolidayShouldNotBeFound("requestedDate.greaterThanOrEqual=" + UPDATED_REQUESTED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByRequestedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where requestedDate is less than or equal to DEFAULT_REQUESTED_DATE
        defaultEmployeeHolidayShouldBeFound("requestedDate.lessThanOrEqual=" + DEFAULT_REQUESTED_DATE);

        // Get all the employeeHolidayList where requestedDate is less than or equal to SMALLER_REQUESTED_DATE
        defaultEmployeeHolidayShouldNotBeFound("requestedDate.lessThanOrEqual=" + SMALLER_REQUESTED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByRequestedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where requestedDate is less than DEFAULT_REQUESTED_DATE
        defaultEmployeeHolidayShouldNotBeFound("requestedDate.lessThan=" + DEFAULT_REQUESTED_DATE);

        // Get all the employeeHolidayList where requestedDate is less than UPDATED_REQUESTED_DATE
        defaultEmployeeHolidayShouldBeFound("requestedDate.lessThan=" + UPDATED_REQUESTED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByRequestedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where requestedDate is greater than DEFAULT_REQUESTED_DATE
        defaultEmployeeHolidayShouldNotBeFound("requestedDate.greaterThan=" + DEFAULT_REQUESTED_DATE);

        // Get all the employeeHolidayList where requestedDate is greater than SMALLER_REQUESTED_DATE
        defaultEmployeeHolidayShouldBeFound("requestedDate.greaterThan=" + SMALLER_REQUESTED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approved equals to DEFAULT_APPROVED
        defaultEmployeeHolidayShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the employeeHolidayList where approved equals to UPDATED_APPROVED
        defaultEmployeeHolidayShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approved not equals to DEFAULT_APPROVED
        defaultEmployeeHolidayShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the employeeHolidayList where approved not equals to UPDATED_APPROVED
        defaultEmployeeHolidayShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultEmployeeHolidayShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the employeeHolidayList where approved equals to UPDATED_APPROVED
        defaultEmployeeHolidayShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where approved is not null
        defaultEmployeeHolidayShouldBeFound("approved.specified=true");

        // Get all the employeeHolidayList where approved is null
        defaultEmployeeHolidayShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where note equals to DEFAULT_NOTE
        defaultEmployeeHolidayShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the employeeHolidayList where note equals to UPDATED_NOTE
        defaultEmployeeHolidayShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where note not equals to DEFAULT_NOTE
        defaultEmployeeHolidayShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the employeeHolidayList where note not equals to UPDATED_NOTE
        defaultEmployeeHolidayShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultEmployeeHolidayShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the employeeHolidayList where note equals to UPDATED_NOTE
        defaultEmployeeHolidayShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where note is not null
        defaultEmployeeHolidayShouldBeFound("note.specified=true");

        // Get all the employeeHolidayList where note is null
        defaultEmployeeHolidayShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeHolidaysByNoteContainsSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where note contains DEFAULT_NOTE
        defaultEmployeeHolidayShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the employeeHolidayList where note contains UPDATED_NOTE
        defaultEmployeeHolidayShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where note does not contain DEFAULT_NOTE
        defaultEmployeeHolidayShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the employeeHolidayList where note does not contain UPDATED_NOTE
        defaultEmployeeHolidayShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeHolidayList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeHolidayList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the employeeHolidayList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where lastUpdatedDate is not null
        defaultEmployeeHolidayShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the employeeHolidayList where lastUpdatedDate is null
        defaultEmployeeHolidayShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeHolidayList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeHolidayList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeHolidayList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeHolidayList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultEmployeeHolidayShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where tenantId equals to DEFAULT_TENANT_ID
        defaultEmployeeHolidayShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the employeeHolidayList where tenantId equals to UPDATED_TENANT_ID
        defaultEmployeeHolidayShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where tenantId not equals to DEFAULT_TENANT_ID
        defaultEmployeeHolidayShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the employeeHolidayList where tenantId not equals to UPDATED_TENANT_ID
        defaultEmployeeHolidayShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultEmployeeHolidayShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the employeeHolidayList where tenantId equals to UPDATED_TENANT_ID
        defaultEmployeeHolidayShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where tenantId is not null
        defaultEmployeeHolidayShouldBeFound("tenantId.specified=true");

        // Get all the employeeHolidayList where tenantId is null
        defaultEmployeeHolidayShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultEmployeeHolidayShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the employeeHolidayList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultEmployeeHolidayShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultEmployeeHolidayShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the employeeHolidayList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultEmployeeHolidayShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where tenantId is less than DEFAULT_TENANT_ID
        defaultEmployeeHolidayShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the employeeHolidayList where tenantId is less than UPDATED_TENANT_ID
        defaultEmployeeHolidayShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeHolidaysByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        // Get all the employeeHolidayList where tenantId is greater than DEFAULT_TENANT_ID
        defaultEmployeeHolidayShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the employeeHolidayList where tenantId is greater than SMALLER_TENANT_ID
        defaultEmployeeHolidayShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeHoliday.setEmployee(employee);
        employeeHolidayRepository.saveAndFlush(employeeHoliday);
        Long employeeId = employee.getId();

        // Get all the employeeHolidayList where employee equals to employeeId
        defaultEmployeeHolidayShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeHolidayList where employee equals to employeeId + 1
        defaultEmployeeHolidayShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeeHolidaysByApprovedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);
        Employee approvedBy = EmployeeResourceIT.createEntity(em);
        em.persist(approvedBy);
        em.flush();
        employeeHoliday.setApprovedBy(approvedBy);
        employeeHolidayRepository.saveAndFlush(employeeHoliday);
        Long approvedById = approvedBy.getId();

        // Get all the employeeHolidayList where approvedBy equals to approvedById
        defaultEmployeeHolidayShouldBeFound("approvedById.equals=" + approvedById);

        // Get all the employeeHolidayList where approvedBy equals to approvedById + 1
        defaultEmployeeHolidayShouldNotBeFound("approvedById.equals=" + (approvedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeHolidayShouldBeFound(String filter) throws Exception {
        restEmployeeHolidayMockMvc.perform(get("/api/employee-holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeHoliday.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].employeeHolidayType").value(hasItem(DEFAULT_EMPLOYEE_HOLIDAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].approvedDate").value(hasItem(sameInstant(DEFAULT_APPROVED_DATE))))
            .andExpect(jsonPath("$.[*].requestedDate").value(hasItem(sameInstant(DEFAULT_REQUESTED_DATE))))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restEmployeeHolidayMockMvc.perform(get("/api/employee-holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeHolidayShouldNotBeFound(String filter) throws Exception {
        restEmployeeHolidayMockMvc.perform(get("/api/employee-holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeHolidayMockMvc.perform(get("/api/employee-holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeHoliday() throws Exception {
        // Get the employeeHoliday
        restEmployeeHolidayMockMvc.perform(get("/api/employee-holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeHoliday() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        int databaseSizeBeforeUpdate = employeeHolidayRepository.findAll().size();

        // Update the employeeHoliday
        EmployeeHoliday updatedEmployeeHoliday = employeeHolidayRepository.findById(employeeHoliday.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeHoliday are not directly saved in db
        em.detach(updatedEmployeeHoliday);
        updatedEmployeeHoliday
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .employeeHolidayType(UPDATED_EMPLOYEE_HOLIDAY_TYPE)
            .approvedDate(UPDATED_APPROVED_DATE)
            .requestedDate(UPDATED_REQUESTED_DATE)
            .approved(UPDATED_APPROVED)
            .note(UPDATED_NOTE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        EmployeeHolidayDTO employeeHolidayDTO = employeeHolidayMapper.toDto(updatedEmployeeHoliday);

        restEmployeeHolidayMockMvc.perform(put("/api/employee-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeHolidayDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeHoliday in the database
        List<EmployeeHoliday> employeeHolidayList = employeeHolidayRepository.findAll();
        assertThat(employeeHolidayList).hasSize(databaseSizeBeforeUpdate);
        EmployeeHoliday testEmployeeHoliday = employeeHolidayList.get(employeeHolidayList.size() - 1);
        assertThat(testEmployeeHoliday.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmployeeHoliday.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEmployeeHoliday.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEmployeeHoliday.getEmployeeHolidayType()).isEqualTo(UPDATED_EMPLOYEE_HOLIDAY_TYPE);
        assertThat(testEmployeeHoliday.getApprovedDate()).isEqualTo(UPDATED_APPROVED_DATE);
        assertThat(testEmployeeHoliday.getRequestedDate()).isEqualTo(UPDATED_REQUESTED_DATE);
        assertThat(testEmployeeHoliday.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testEmployeeHoliday.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testEmployeeHoliday.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testEmployeeHoliday.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeHoliday() throws Exception {
        int databaseSizeBeforeUpdate = employeeHolidayRepository.findAll().size();

        // Create the EmployeeHoliday
        EmployeeHolidayDTO employeeHolidayDTO = employeeHolidayMapper.toDto(employeeHoliday);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeHolidayMockMvc.perform(put("/api/employee-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeHolidayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeHoliday in the database
        List<EmployeeHoliday> employeeHolidayList = employeeHolidayRepository.findAll();
        assertThat(employeeHolidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeHoliday() throws Exception {
        // Initialize the database
        employeeHolidayRepository.saveAndFlush(employeeHoliday);

        int databaseSizeBeforeDelete = employeeHolidayRepository.findAll().size();

        // Delete the employeeHoliday
        restEmployeeHolidayMockMvc.perform(delete("/api/employee-holidays/{id}", employeeHoliday.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeHoliday> employeeHolidayList = employeeHolidayRepository.findAll();
        assertThat(employeeHolidayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
