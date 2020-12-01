package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Timesheet;
import com.amc.careplanner.domain.ServiceOrder;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.TimesheetRepository;
import com.amc.careplanner.service.TimesheetService;
import com.amc.careplanner.service.dto.TimesheetDTO;
import com.amc.careplanner.service.mapper.TimesheetMapper;
import com.amc.careplanner.service.dto.TimesheetCriteria;
import com.amc.careplanner.service.TimesheetQueryService;

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
import java.time.LocalDate;
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
 * Integration tests for the {@link TimesheetResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TimesheetResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TIMESHEET_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESHEET_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TIMESHEET_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_END_TIME = "BBBBBBBBBB";

    private static final Integer DEFAULT_HOURS_WORKED = 1;
    private static final Integer UPDATED_HOURS_WORKED = 2;
    private static final Integer SMALLER_HOURS_WORKED = 1 - 1;

    private static final String DEFAULT_BREAK_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_BREAK_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_BREAK_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_BREAK_END_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private TimesheetMapper timesheetMapper;

    @Autowired
    private TimesheetService timesheetService;

    @Autowired
    private TimesheetQueryService timesheetQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimesheetMockMvc;

    private Timesheet timesheet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timesheet createEntity(EntityManager em) {
        Timesheet timesheet = new Timesheet()
            .description(DEFAULT_DESCRIPTION)
            .timesheetDate(DEFAULT_TIMESHEET_DATE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .hoursWorked(DEFAULT_HOURS_WORKED)
            .breakStartTime(DEFAULT_BREAK_START_TIME)
            .breakEndTime(DEFAULT_BREAK_END_TIME)
            .note(DEFAULT_NOTE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID);
        return timesheet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timesheet createUpdatedEntity(EntityManager em) {
        Timesheet timesheet = new Timesheet()
            .description(UPDATED_DESCRIPTION)
            .timesheetDate(UPDATED_TIMESHEET_DATE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .hoursWorked(UPDATED_HOURS_WORKED)
            .breakStartTime(UPDATED_BREAK_START_TIME)
            .breakEndTime(UPDATED_BREAK_END_TIME)
            .note(UPDATED_NOTE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        return timesheet;
    }

    @BeforeEach
    public void initTest() {
        timesheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimesheet() throws Exception {
        int databaseSizeBeforeCreate = timesheetRepository.findAll().size();
        // Create the Timesheet
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);
        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isCreated());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeCreate + 1);
        Timesheet testTimesheet = timesheetList.get(timesheetList.size() - 1);
        assertThat(testTimesheet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTimesheet.getTimesheetDate()).isEqualTo(DEFAULT_TIMESHEET_DATE);
        assertThat(testTimesheet.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTimesheet.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTimesheet.getHoursWorked()).isEqualTo(DEFAULT_HOURS_WORKED);
        assertThat(testTimesheet.getBreakStartTime()).isEqualTo(DEFAULT_BREAK_START_TIME);
        assertThat(testTimesheet.getBreakEndTime()).isEqualTo(DEFAULT_BREAK_END_TIME);
        assertThat(testTimesheet.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTimesheet.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testTimesheet.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void createTimesheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timesheetRepository.findAll().size();

        // Create the Timesheet with an existing ID
        timesheet.setId(1L);
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTimesheetDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = timesheetRepository.findAll().size();
        // set the field null
        timesheet.setTimesheetDate(null);

        // Create the Timesheet, which fails.
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);


        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isBadRequest());

        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timesheetRepository.findAll().size();
        // set the field null
        timesheet.setStartTime(null);

        // Create the Timesheet, which fails.
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);


        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isBadRequest());

        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timesheetRepository.findAll().size();
        // set the field null
        timesheet.setEndTime(null);

        // Create the Timesheet, which fails.
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);


        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isBadRequest());

        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoursWorkedIsRequired() throws Exception {
        int databaseSizeBeforeTest = timesheetRepository.findAll().size();
        // set the field null
        timesheet.setHoursWorked(null);

        // Create the Timesheet, which fails.
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);


        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isBadRequest());

        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = timesheetRepository.findAll().size();
        // set the field null
        timesheet.setClientId(null);

        // Create the Timesheet, which fails.
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);


        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isBadRequest());

        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimesheets() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList
        restTimesheetMockMvc.perform(get("/api/timesheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timesheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timesheetDate").value(hasItem(DEFAULT_TIMESHEET_DATE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].hoursWorked").value(hasItem(DEFAULT_HOURS_WORKED)))
            .andExpect(jsonPath("$.[*].breakStartTime").value(hasItem(DEFAULT_BREAK_START_TIME)))
            .andExpect(jsonPath("$.[*].breakEndTime").value(hasItem(DEFAULT_BREAK_END_TIME)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get the timesheet
        restTimesheetMockMvc.perform(get("/api/timesheets/{id}", timesheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timesheet.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.timesheetDate").value(DEFAULT_TIMESHEET_DATE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME))
            .andExpect(jsonPath("$.hoursWorked").value(DEFAULT_HOURS_WORKED))
            .andExpect(jsonPath("$.breakStartTime").value(DEFAULT_BREAK_START_TIME))
            .andExpect(jsonPath("$.breakEndTime").value(DEFAULT_BREAK_END_TIME))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getTimesheetsByIdFiltering() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        Long id = timesheet.getId();

        defaultTimesheetShouldBeFound("id.equals=" + id);
        defaultTimesheetShouldNotBeFound("id.notEquals=" + id);

        defaultTimesheetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTimesheetShouldNotBeFound("id.greaterThan=" + id);

        defaultTimesheetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTimesheetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where description equals to DEFAULT_DESCRIPTION
        defaultTimesheetShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the timesheetList where description equals to UPDATED_DESCRIPTION
        defaultTimesheetShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where description not equals to DEFAULT_DESCRIPTION
        defaultTimesheetShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the timesheetList where description not equals to UPDATED_DESCRIPTION
        defaultTimesheetShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTimesheetShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the timesheetList where description equals to UPDATED_DESCRIPTION
        defaultTimesheetShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where description is not null
        defaultTimesheetShouldBeFound("description.specified=true");

        // Get all the timesheetList where description is null
        defaultTimesheetShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTimesheetsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where description contains DEFAULT_DESCRIPTION
        defaultTimesheetShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the timesheetList where description contains UPDATED_DESCRIPTION
        defaultTimesheetShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where description does not contain DEFAULT_DESCRIPTION
        defaultTimesheetShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the timesheetList where description does not contain UPDATED_DESCRIPTION
        defaultTimesheetShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByTimesheetDateIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where timesheetDate equals to DEFAULT_TIMESHEET_DATE
        defaultTimesheetShouldBeFound("timesheetDate.equals=" + DEFAULT_TIMESHEET_DATE);

        // Get all the timesheetList where timesheetDate equals to UPDATED_TIMESHEET_DATE
        defaultTimesheetShouldNotBeFound("timesheetDate.equals=" + UPDATED_TIMESHEET_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByTimesheetDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where timesheetDate not equals to DEFAULT_TIMESHEET_DATE
        defaultTimesheetShouldNotBeFound("timesheetDate.notEquals=" + DEFAULT_TIMESHEET_DATE);

        // Get all the timesheetList where timesheetDate not equals to UPDATED_TIMESHEET_DATE
        defaultTimesheetShouldBeFound("timesheetDate.notEquals=" + UPDATED_TIMESHEET_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByTimesheetDateIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where timesheetDate in DEFAULT_TIMESHEET_DATE or UPDATED_TIMESHEET_DATE
        defaultTimesheetShouldBeFound("timesheetDate.in=" + DEFAULT_TIMESHEET_DATE + "," + UPDATED_TIMESHEET_DATE);

        // Get all the timesheetList where timesheetDate equals to UPDATED_TIMESHEET_DATE
        defaultTimesheetShouldNotBeFound("timesheetDate.in=" + UPDATED_TIMESHEET_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByTimesheetDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where timesheetDate is not null
        defaultTimesheetShouldBeFound("timesheetDate.specified=true");

        // Get all the timesheetList where timesheetDate is null
        defaultTimesheetShouldNotBeFound("timesheetDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTimesheetsByTimesheetDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where timesheetDate is greater than or equal to DEFAULT_TIMESHEET_DATE
        defaultTimesheetShouldBeFound("timesheetDate.greaterThanOrEqual=" + DEFAULT_TIMESHEET_DATE);

        // Get all the timesheetList where timesheetDate is greater than or equal to UPDATED_TIMESHEET_DATE
        defaultTimesheetShouldNotBeFound("timesheetDate.greaterThanOrEqual=" + UPDATED_TIMESHEET_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByTimesheetDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where timesheetDate is less than or equal to DEFAULT_TIMESHEET_DATE
        defaultTimesheetShouldBeFound("timesheetDate.lessThanOrEqual=" + DEFAULT_TIMESHEET_DATE);

        // Get all the timesheetList where timesheetDate is less than or equal to SMALLER_TIMESHEET_DATE
        defaultTimesheetShouldNotBeFound("timesheetDate.lessThanOrEqual=" + SMALLER_TIMESHEET_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByTimesheetDateIsLessThanSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where timesheetDate is less than DEFAULT_TIMESHEET_DATE
        defaultTimesheetShouldNotBeFound("timesheetDate.lessThan=" + DEFAULT_TIMESHEET_DATE);

        // Get all the timesheetList where timesheetDate is less than UPDATED_TIMESHEET_DATE
        defaultTimesheetShouldBeFound("timesheetDate.lessThan=" + UPDATED_TIMESHEET_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByTimesheetDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where timesheetDate is greater than DEFAULT_TIMESHEET_DATE
        defaultTimesheetShouldNotBeFound("timesheetDate.greaterThan=" + DEFAULT_TIMESHEET_DATE);

        // Get all the timesheetList where timesheetDate is greater than SMALLER_TIMESHEET_DATE
        defaultTimesheetShouldBeFound("timesheetDate.greaterThan=" + SMALLER_TIMESHEET_DATE);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where startTime equals to DEFAULT_START_TIME
        defaultTimesheetShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the timesheetList where startTime equals to UPDATED_START_TIME
        defaultTimesheetShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where startTime not equals to DEFAULT_START_TIME
        defaultTimesheetShouldNotBeFound("startTime.notEquals=" + DEFAULT_START_TIME);

        // Get all the timesheetList where startTime not equals to UPDATED_START_TIME
        defaultTimesheetShouldBeFound("startTime.notEquals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultTimesheetShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the timesheetList where startTime equals to UPDATED_START_TIME
        defaultTimesheetShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where startTime is not null
        defaultTimesheetShouldBeFound("startTime.specified=true");

        // Get all the timesheetList where startTime is null
        defaultTimesheetShouldNotBeFound("startTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllTimesheetsByStartTimeContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where startTime contains DEFAULT_START_TIME
        defaultTimesheetShouldBeFound("startTime.contains=" + DEFAULT_START_TIME);

        // Get all the timesheetList where startTime contains UPDATED_START_TIME
        defaultTimesheetShouldNotBeFound("startTime.contains=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByStartTimeNotContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where startTime does not contain DEFAULT_START_TIME
        defaultTimesheetShouldNotBeFound("startTime.doesNotContain=" + DEFAULT_START_TIME);

        // Get all the timesheetList where startTime does not contain UPDATED_START_TIME
        defaultTimesheetShouldBeFound("startTime.doesNotContain=" + UPDATED_START_TIME);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where endTime equals to DEFAULT_END_TIME
        defaultTimesheetShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the timesheetList where endTime equals to UPDATED_END_TIME
        defaultTimesheetShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByEndTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where endTime not equals to DEFAULT_END_TIME
        defaultTimesheetShouldNotBeFound("endTime.notEquals=" + DEFAULT_END_TIME);

        // Get all the timesheetList where endTime not equals to UPDATED_END_TIME
        defaultTimesheetShouldBeFound("endTime.notEquals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultTimesheetShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the timesheetList where endTime equals to UPDATED_END_TIME
        defaultTimesheetShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where endTime is not null
        defaultTimesheetShouldBeFound("endTime.specified=true");

        // Get all the timesheetList where endTime is null
        defaultTimesheetShouldNotBeFound("endTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllTimesheetsByEndTimeContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where endTime contains DEFAULT_END_TIME
        defaultTimesheetShouldBeFound("endTime.contains=" + DEFAULT_END_TIME);

        // Get all the timesheetList where endTime contains UPDATED_END_TIME
        defaultTimesheetShouldNotBeFound("endTime.contains=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByEndTimeNotContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where endTime does not contain DEFAULT_END_TIME
        defaultTimesheetShouldNotBeFound("endTime.doesNotContain=" + DEFAULT_END_TIME);

        // Get all the timesheetList where endTime does not contain UPDATED_END_TIME
        defaultTimesheetShouldBeFound("endTime.doesNotContain=" + UPDATED_END_TIME);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByHoursWorkedIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where hoursWorked equals to DEFAULT_HOURS_WORKED
        defaultTimesheetShouldBeFound("hoursWorked.equals=" + DEFAULT_HOURS_WORKED);

        // Get all the timesheetList where hoursWorked equals to UPDATED_HOURS_WORKED
        defaultTimesheetShouldNotBeFound("hoursWorked.equals=" + UPDATED_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByHoursWorkedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where hoursWorked not equals to DEFAULT_HOURS_WORKED
        defaultTimesheetShouldNotBeFound("hoursWorked.notEquals=" + DEFAULT_HOURS_WORKED);

        // Get all the timesheetList where hoursWorked not equals to UPDATED_HOURS_WORKED
        defaultTimesheetShouldBeFound("hoursWorked.notEquals=" + UPDATED_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByHoursWorkedIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where hoursWorked in DEFAULT_HOURS_WORKED or UPDATED_HOURS_WORKED
        defaultTimesheetShouldBeFound("hoursWorked.in=" + DEFAULT_HOURS_WORKED + "," + UPDATED_HOURS_WORKED);

        // Get all the timesheetList where hoursWorked equals to UPDATED_HOURS_WORKED
        defaultTimesheetShouldNotBeFound("hoursWorked.in=" + UPDATED_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByHoursWorkedIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where hoursWorked is not null
        defaultTimesheetShouldBeFound("hoursWorked.specified=true");

        // Get all the timesheetList where hoursWorked is null
        defaultTimesheetShouldNotBeFound("hoursWorked.specified=false");
    }

    @Test
    @Transactional
    public void getAllTimesheetsByHoursWorkedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where hoursWorked is greater than or equal to DEFAULT_HOURS_WORKED
        defaultTimesheetShouldBeFound("hoursWorked.greaterThanOrEqual=" + DEFAULT_HOURS_WORKED);

        // Get all the timesheetList where hoursWorked is greater than or equal to UPDATED_HOURS_WORKED
        defaultTimesheetShouldNotBeFound("hoursWorked.greaterThanOrEqual=" + UPDATED_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByHoursWorkedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where hoursWorked is less than or equal to DEFAULT_HOURS_WORKED
        defaultTimesheetShouldBeFound("hoursWorked.lessThanOrEqual=" + DEFAULT_HOURS_WORKED);

        // Get all the timesheetList where hoursWorked is less than or equal to SMALLER_HOURS_WORKED
        defaultTimesheetShouldNotBeFound("hoursWorked.lessThanOrEqual=" + SMALLER_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByHoursWorkedIsLessThanSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where hoursWorked is less than DEFAULT_HOURS_WORKED
        defaultTimesheetShouldNotBeFound("hoursWorked.lessThan=" + DEFAULT_HOURS_WORKED);

        // Get all the timesheetList where hoursWorked is less than UPDATED_HOURS_WORKED
        defaultTimesheetShouldBeFound("hoursWorked.lessThan=" + UPDATED_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByHoursWorkedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where hoursWorked is greater than DEFAULT_HOURS_WORKED
        defaultTimesheetShouldNotBeFound("hoursWorked.greaterThan=" + DEFAULT_HOURS_WORKED);

        // Get all the timesheetList where hoursWorked is greater than SMALLER_HOURS_WORKED
        defaultTimesheetShouldBeFound("hoursWorked.greaterThan=" + SMALLER_HOURS_WORKED);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByBreakStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakStartTime equals to DEFAULT_BREAK_START_TIME
        defaultTimesheetShouldBeFound("breakStartTime.equals=" + DEFAULT_BREAK_START_TIME);

        // Get all the timesheetList where breakStartTime equals to UPDATED_BREAK_START_TIME
        defaultTimesheetShouldNotBeFound("breakStartTime.equals=" + UPDATED_BREAK_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByBreakStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakStartTime not equals to DEFAULT_BREAK_START_TIME
        defaultTimesheetShouldNotBeFound("breakStartTime.notEquals=" + DEFAULT_BREAK_START_TIME);

        // Get all the timesheetList where breakStartTime not equals to UPDATED_BREAK_START_TIME
        defaultTimesheetShouldBeFound("breakStartTime.notEquals=" + UPDATED_BREAK_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByBreakStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakStartTime in DEFAULT_BREAK_START_TIME or UPDATED_BREAK_START_TIME
        defaultTimesheetShouldBeFound("breakStartTime.in=" + DEFAULT_BREAK_START_TIME + "," + UPDATED_BREAK_START_TIME);

        // Get all the timesheetList where breakStartTime equals to UPDATED_BREAK_START_TIME
        defaultTimesheetShouldNotBeFound("breakStartTime.in=" + UPDATED_BREAK_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByBreakStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakStartTime is not null
        defaultTimesheetShouldBeFound("breakStartTime.specified=true");

        // Get all the timesheetList where breakStartTime is null
        defaultTimesheetShouldNotBeFound("breakStartTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllTimesheetsByBreakStartTimeContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakStartTime contains DEFAULT_BREAK_START_TIME
        defaultTimesheetShouldBeFound("breakStartTime.contains=" + DEFAULT_BREAK_START_TIME);

        // Get all the timesheetList where breakStartTime contains UPDATED_BREAK_START_TIME
        defaultTimesheetShouldNotBeFound("breakStartTime.contains=" + UPDATED_BREAK_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByBreakStartTimeNotContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakStartTime does not contain DEFAULT_BREAK_START_TIME
        defaultTimesheetShouldNotBeFound("breakStartTime.doesNotContain=" + DEFAULT_BREAK_START_TIME);

        // Get all the timesheetList where breakStartTime does not contain UPDATED_BREAK_START_TIME
        defaultTimesheetShouldBeFound("breakStartTime.doesNotContain=" + UPDATED_BREAK_START_TIME);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByBreakEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakEndTime equals to DEFAULT_BREAK_END_TIME
        defaultTimesheetShouldBeFound("breakEndTime.equals=" + DEFAULT_BREAK_END_TIME);

        // Get all the timesheetList where breakEndTime equals to UPDATED_BREAK_END_TIME
        defaultTimesheetShouldNotBeFound("breakEndTime.equals=" + UPDATED_BREAK_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByBreakEndTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakEndTime not equals to DEFAULT_BREAK_END_TIME
        defaultTimesheetShouldNotBeFound("breakEndTime.notEquals=" + DEFAULT_BREAK_END_TIME);

        // Get all the timesheetList where breakEndTime not equals to UPDATED_BREAK_END_TIME
        defaultTimesheetShouldBeFound("breakEndTime.notEquals=" + UPDATED_BREAK_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByBreakEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakEndTime in DEFAULT_BREAK_END_TIME or UPDATED_BREAK_END_TIME
        defaultTimesheetShouldBeFound("breakEndTime.in=" + DEFAULT_BREAK_END_TIME + "," + UPDATED_BREAK_END_TIME);

        // Get all the timesheetList where breakEndTime equals to UPDATED_BREAK_END_TIME
        defaultTimesheetShouldNotBeFound("breakEndTime.in=" + UPDATED_BREAK_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByBreakEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakEndTime is not null
        defaultTimesheetShouldBeFound("breakEndTime.specified=true");

        // Get all the timesheetList where breakEndTime is null
        defaultTimesheetShouldNotBeFound("breakEndTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllTimesheetsByBreakEndTimeContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakEndTime contains DEFAULT_BREAK_END_TIME
        defaultTimesheetShouldBeFound("breakEndTime.contains=" + DEFAULT_BREAK_END_TIME);

        // Get all the timesheetList where breakEndTime contains UPDATED_BREAK_END_TIME
        defaultTimesheetShouldNotBeFound("breakEndTime.contains=" + UPDATED_BREAK_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByBreakEndTimeNotContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where breakEndTime does not contain DEFAULT_BREAK_END_TIME
        defaultTimesheetShouldNotBeFound("breakEndTime.doesNotContain=" + DEFAULT_BREAK_END_TIME);

        // Get all the timesheetList where breakEndTime does not contain UPDATED_BREAK_END_TIME
        defaultTimesheetShouldBeFound("breakEndTime.doesNotContain=" + UPDATED_BREAK_END_TIME);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where note equals to DEFAULT_NOTE
        defaultTimesheetShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the timesheetList where note equals to UPDATED_NOTE
        defaultTimesheetShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where note not equals to DEFAULT_NOTE
        defaultTimesheetShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the timesheetList where note not equals to UPDATED_NOTE
        defaultTimesheetShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTimesheetShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the timesheetList where note equals to UPDATED_NOTE
        defaultTimesheetShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where note is not null
        defaultTimesheetShouldBeFound("note.specified=true");

        // Get all the timesheetList where note is null
        defaultTimesheetShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllTimesheetsByNoteContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where note contains DEFAULT_NOTE
        defaultTimesheetShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the timesheetList where note contains UPDATED_NOTE
        defaultTimesheetShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where note does not contain DEFAULT_NOTE
        defaultTimesheetShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the timesheetList where note does not contain UPDATED_NOTE
        defaultTimesheetShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultTimesheetShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the timesheetList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTimesheetShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultTimesheetShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the timesheetList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultTimesheetShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultTimesheetShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the timesheetList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTimesheetShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where lastUpdatedDate is not null
        defaultTimesheetShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the timesheetList where lastUpdatedDate is null
        defaultTimesheetShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTimesheetsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTimesheetShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the timesheetList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultTimesheetShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTimesheetShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the timesheetList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultTimesheetShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultTimesheetShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the timesheetList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultTimesheetShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultTimesheetShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the timesheetList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultTimesheetShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where clientId equals to DEFAULT_CLIENT_ID
        defaultTimesheetShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the timesheetList where clientId equals to UPDATED_CLIENT_ID
        defaultTimesheetShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where clientId not equals to DEFAULT_CLIENT_ID
        defaultTimesheetShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the timesheetList where clientId not equals to UPDATED_CLIENT_ID
        defaultTimesheetShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultTimesheetShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the timesheetList where clientId equals to UPDATED_CLIENT_ID
        defaultTimesheetShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where clientId is not null
        defaultTimesheetShouldBeFound("clientId.specified=true");

        // Get all the timesheetList where clientId is null
        defaultTimesheetShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTimesheetsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultTimesheetShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the timesheetList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultTimesheetShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultTimesheetShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the timesheetList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultTimesheetShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where clientId is less than DEFAULT_CLIENT_ID
        defaultTimesheetShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the timesheetList where clientId is less than UPDATED_CLIENT_ID
        defaultTimesheetShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTimesheetsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList where clientId is greater than DEFAULT_CLIENT_ID
        defaultTimesheetShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the timesheetList where clientId is greater than SMALLER_CLIENT_ID
        defaultTimesheetShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllTimesheetsByServiceOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);
        ServiceOrder serviceOrder = ServiceOrderResourceIT.createEntity(em);
        em.persist(serviceOrder);
        em.flush();
        timesheet.setServiceOrder(serviceOrder);
        timesheetRepository.saveAndFlush(timesheet);
        Long serviceOrderId = serviceOrder.getId();

        // Get all the timesheetList where serviceOrder equals to serviceOrderId
        defaultTimesheetShouldBeFound("serviceOrderId.equals=" + serviceOrderId);

        // Get all the timesheetList where serviceOrder equals to serviceOrderId + 1
        defaultTimesheetShouldNotBeFound("serviceOrderId.equals=" + (serviceOrderId + 1));
    }


    @Test
    @Transactional
    public void getAllTimesheetsByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        timesheet.setServiceUser(serviceUser);
        timesheetRepository.saveAndFlush(timesheet);
        Long serviceUserId = serviceUser.getId();

        // Get all the timesheetList where serviceUser equals to serviceUserId
        defaultTimesheetShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the timesheetList where serviceUser equals to serviceUserId + 1
        defaultTimesheetShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }


    @Test
    @Transactional
    public void getAllTimesheetsByCareProviderIsEqualToSomething() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);
        Employee careProvider = EmployeeResourceIT.createEntity(em);
        em.persist(careProvider);
        em.flush();
        timesheet.setCareProvider(careProvider);
        timesheetRepository.saveAndFlush(timesheet);
        Long careProviderId = careProvider.getId();

        // Get all the timesheetList where careProvider equals to careProviderId
        defaultTimesheetShouldBeFound("careProviderId.equals=" + careProviderId);

        // Get all the timesheetList where careProvider equals to careProviderId + 1
        defaultTimesheetShouldNotBeFound("careProviderId.equals=" + (careProviderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTimesheetShouldBeFound(String filter) throws Exception {
        restTimesheetMockMvc.perform(get("/api/timesheets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timesheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timesheetDate").value(hasItem(DEFAULT_TIMESHEET_DATE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].hoursWorked").value(hasItem(DEFAULT_HOURS_WORKED)))
            .andExpect(jsonPath("$.[*].breakStartTime").value(hasItem(DEFAULT_BREAK_START_TIME)))
            .andExpect(jsonPath("$.[*].breakEndTime").value(hasItem(DEFAULT_BREAK_END_TIME)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));

        // Check, that the count call also returns 1
        restTimesheetMockMvc.perform(get("/api/timesheets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTimesheetShouldNotBeFound(String filter) throws Exception {
        restTimesheetMockMvc.perform(get("/api/timesheets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTimesheetMockMvc.perform(get("/api/timesheets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTimesheet() throws Exception {
        // Get the timesheet
        restTimesheetMockMvc.perform(get("/api/timesheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        int databaseSizeBeforeUpdate = timesheetRepository.findAll().size();

        // Update the timesheet
        Timesheet updatedTimesheet = timesheetRepository.findById(timesheet.getId()).get();
        // Disconnect from session so that the updates on updatedTimesheet are not directly saved in db
        em.detach(updatedTimesheet);
        updatedTimesheet
            .description(UPDATED_DESCRIPTION)
            .timesheetDate(UPDATED_TIMESHEET_DATE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .hoursWorked(UPDATED_HOURS_WORKED)
            .breakStartTime(UPDATED_BREAK_START_TIME)
            .breakEndTime(UPDATED_BREAK_END_TIME)
            .note(UPDATED_NOTE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(updatedTimesheet);

        restTimesheetMockMvc.perform(put("/api/timesheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isOk());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeUpdate);
        Timesheet testTimesheet = timesheetList.get(timesheetList.size() - 1);
        assertThat(testTimesheet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTimesheet.getTimesheetDate()).isEqualTo(UPDATED_TIMESHEET_DATE);
        assertThat(testTimesheet.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimesheet.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTimesheet.getHoursWorked()).isEqualTo(UPDATED_HOURS_WORKED);
        assertThat(testTimesheet.getBreakStartTime()).isEqualTo(UPDATED_BREAK_START_TIME);
        assertThat(testTimesheet.getBreakEndTime()).isEqualTo(UPDATED_BREAK_END_TIME);
        assertThat(testTimesheet.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTimesheet.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testTimesheet.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTimesheet() throws Exception {
        int databaseSizeBeforeUpdate = timesheetRepository.findAll().size();

        // Create the Timesheet
        TimesheetDTO timesheetDTO = timesheetMapper.toDto(timesheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimesheetMockMvc.perform(put("/api/timesheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timesheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        int databaseSizeBeforeDelete = timesheetRepository.findAll().size();

        // Delete the timesheet
        restTimesheetMockMvc.perform(delete("/api/timesheets/{id}", timesheet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
