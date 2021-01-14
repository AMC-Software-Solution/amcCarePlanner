package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.EmployeeDesignation;
import com.amc.careplanner.repository.EmployeeDesignationRepository;
import com.amc.careplanner.service.EmployeeDesignationService;
import com.amc.careplanner.service.dto.EmployeeDesignationDTO;
import com.amc.careplanner.service.mapper.EmployeeDesignationMapper;
import com.amc.careplanner.service.dto.EmployeeDesignationCriteria;
import com.amc.careplanner.service.EmployeeDesignationQueryService;

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

/**
 * Integration tests for the {@link EmployeeDesignationResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeDesignationResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION_DATE = "BBBBBBBBBB";

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private EmployeeDesignationRepository employeeDesignationRepository;

    @Autowired
    private EmployeeDesignationMapper employeeDesignationMapper;

    @Autowired
    private EmployeeDesignationService employeeDesignationService;

    @Autowired
    private EmployeeDesignationQueryService employeeDesignationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeDesignationMockMvc;

    private EmployeeDesignation employeeDesignation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDesignation createEntity(EntityManager em) {
        EmployeeDesignation employeeDesignation = new EmployeeDesignation()
            .designation(DEFAULT_DESIGNATION)
            .description(DEFAULT_DESCRIPTION)
            .designationDate(DEFAULT_DESIGNATION_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return employeeDesignation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDesignation createUpdatedEntity(EntityManager em) {
        EmployeeDesignation employeeDesignation = new EmployeeDesignation()
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .designationDate(UPDATED_DESIGNATION_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return employeeDesignation;
    }

    @BeforeEach
    public void initTest() {
        employeeDesignation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeDesignation() throws Exception {
        int databaseSizeBeforeCreate = employeeDesignationRepository.findAll().size();
        // Create the EmployeeDesignation
        EmployeeDesignationDTO employeeDesignationDTO = employeeDesignationMapper.toDto(employeeDesignation);
        restEmployeeDesignationMockMvc.perform(post("/api/employee-designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDesignationDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeDesignation in the database
        List<EmployeeDesignation> employeeDesignationList = employeeDesignationRepository.findAll();
        assertThat(employeeDesignationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeDesignation testEmployeeDesignation = employeeDesignationList.get(employeeDesignationList.size() - 1);
        assertThat(testEmployeeDesignation.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testEmployeeDesignation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEmployeeDesignation.getDesignationDate()).isEqualTo(DEFAULT_DESIGNATION_DATE);
        assertThat(testEmployeeDesignation.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testEmployeeDesignation.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createEmployeeDesignationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeDesignationRepository.findAll().size();

        // Create the EmployeeDesignation with an existing ID
        employeeDesignation.setId(1L);
        EmployeeDesignationDTO employeeDesignationDTO = employeeDesignationMapper.toDto(employeeDesignation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeDesignationMockMvc.perform(post("/api/employee-designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDesignationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDesignation in the database
        List<EmployeeDesignation> employeeDesignationList = employeeDesignationRepository.findAll();
        assertThat(employeeDesignationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeDesignationRepository.findAll().size();
        // set the field null
        employeeDesignation.setDesignation(null);

        // Create the EmployeeDesignation, which fails.
        EmployeeDesignationDTO employeeDesignationDTO = employeeDesignationMapper.toDto(employeeDesignation);


        restEmployeeDesignationMockMvc.perform(post("/api/employee-designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDesignationDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeDesignation> employeeDesignationList = employeeDesignationRepository.findAll();
        assertThat(employeeDesignationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeDesignationRepository.findAll().size();
        // set the field null
        employeeDesignation.setClientId(null);

        // Create the EmployeeDesignation, which fails.
        EmployeeDesignationDTO employeeDesignationDTO = employeeDesignationMapper.toDto(employeeDesignation);


        restEmployeeDesignationMockMvc.perform(post("/api/employee-designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDesignationDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeDesignation> employeeDesignationList = employeeDesignationRepository.findAll();
        assertThat(employeeDesignationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignations() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList
        restEmployeeDesignationMockMvc.perform(get("/api/employee-designations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDesignation.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].designationDate").value(hasItem(DEFAULT_DESIGNATION_DATE)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeDesignation() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get the employeeDesignation
        restEmployeeDesignationMockMvc.perform(get("/api/employee-designations/{id}", employeeDesignation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeDesignation.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.designationDate").value(DEFAULT_DESIGNATION_DATE))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getEmployeeDesignationsByIdFiltering() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        Long id = employeeDesignation.getId();

        defaultEmployeeDesignationShouldBeFound("id.equals=" + id);
        defaultEmployeeDesignationShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeDesignationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeDesignationShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeDesignationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeDesignationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designation equals to DEFAULT_DESIGNATION
        defaultEmployeeDesignationShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the employeeDesignationList where designation equals to UPDATED_DESIGNATION
        defaultEmployeeDesignationShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designation not equals to DEFAULT_DESIGNATION
        defaultEmployeeDesignationShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the employeeDesignationList where designation not equals to UPDATED_DESIGNATION
        defaultEmployeeDesignationShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultEmployeeDesignationShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the employeeDesignationList where designation equals to UPDATED_DESIGNATION
        defaultEmployeeDesignationShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designation is not null
        defaultEmployeeDesignationShouldBeFound("designation.specified=true");

        // Get all the employeeDesignationList where designation is null
        defaultEmployeeDesignationShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designation contains DEFAULT_DESIGNATION
        defaultEmployeeDesignationShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the employeeDesignationList where designation contains UPDATED_DESIGNATION
        defaultEmployeeDesignationShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designation does not contain DEFAULT_DESIGNATION
        defaultEmployeeDesignationShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the employeeDesignationList where designation does not contain UPDATED_DESIGNATION
        defaultEmployeeDesignationShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }


    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where description equals to DEFAULT_DESCRIPTION
        defaultEmployeeDesignationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDesignationList where description equals to UPDATED_DESCRIPTION
        defaultEmployeeDesignationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where description not equals to DEFAULT_DESCRIPTION
        defaultEmployeeDesignationShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDesignationList where description not equals to UPDATED_DESCRIPTION
        defaultEmployeeDesignationShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEmployeeDesignationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the employeeDesignationList where description equals to UPDATED_DESCRIPTION
        defaultEmployeeDesignationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where description is not null
        defaultEmployeeDesignationShouldBeFound("description.specified=true");

        // Get all the employeeDesignationList where description is null
        defaultEmployeeDesignationShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeDesignationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where description contains DEFAULT_DESCRIPTION
        defaultEmployeeDesignationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDesignationList where description contains UPDATED_DESCRIPTION
        defaultEmployeeDesignationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where description does not contain DEFAULT_DESCRIPTION
        defaultEmployeeDesignationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDesignationList where description does not contain UPDATED_DESCRIPTION
        defaultEmployeeDesignationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designationDate equals to DEFAULT_DESIGNATION_DATE
        defaultEmployeeDesignationShouldBeFound("designationDate.equals=" + DEFAULT_DESIGNATION_DATE);

        // Get all the employeeDesignationList where designationDate equals to UPDATED_DESIGNATION_DATE
        defaultEmployeeDesignationShouldNotBeFound("designationDate.equals=" + UPDATED_DESIGNATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designationDate not equals to DEFAULT_DESIGNATION_DATE
        defaultEmployeeDesignationShouldNotBeFound("designationDate.notEquals=" + DEFAULT_DESIGNATION_DATE);

        // Get all the employeeDesignationList where designationDate not equals to UPDATED_DESIGNATION_DATE
        defaultEmployeeDesignationShouldBeFound("designationDate.notEquals=" + UPDATED_DESIGNATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designationDate in DEFAULT_DESIGNATION_DATE or UPDATED_DESIGNATION_DATE
        defaultEmployeeDesignationShouldBeFound("designationDate.in=" + DEFAULT_DESIGNATION_DATE + "," + UPDATED_DESIGNATION_DATE);

        // Get all the employeeDesignationList where designationDate equals to UPDATED_DESIGNATION_DATE
        defaultEmployeeDesignationShouldNotBeFound("designationDate.in=" + UPDATED_DESIGNATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designationDate is not null
        defaultEmployeeDesignationShouldBeFound("designationDate.specified=true");

        // Get all the employeeDesignationList where designationDate is null
        defaultEmployeeDesignationShouldNotBeFound("designationDate.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationDateContainsSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designationDate contains DEFAULT_DESIGNATION_DATE
        defaultEmployeeDesignationShouldBeFound("designationDate.contains=" + DEFAULT_DESIGNATION_DATE);

        // Get all the employeeDesignationList where designationDate contains UPDATED_DESIGNATION_DATE
        defaultEmployeeDesignationShouldNotBeFound("designationDate.contains=" + UPDATED_DESIGNATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByDesignationDateNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where designationDate does not contain DEFAULT_DESIGNATION_DATE
        defaultEmployeeDesignationShouldNotBeFound("designationDate.doesNotContain=" + DEFAULT_DESIGNATION_DATE);

        // Get all the employeeDesignationList where designationDate does not contain UPDATED_DESIGNATION_DATE
        defaultEmployeeDesignationShouldBeFound("designationDate.doesNotContain=" + UPDATED_DESIGNATION_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeDesignationsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where clientId equals to DEFAULT_CLIENT_ID
        defaultEmployeeDesignationShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDesignationList where clientId equals to UPDATED_CLIENT_ID
        defaultEmployeeDesignationShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where clientId not equals to DEFAULT_CLIENT_ID
        defaultEmployeeDesignationShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDesignationList where clientId not equals to UPDATED_CLIENT_ID
        defaultEmployeeDesignationShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultEmployeeDesignationShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the employeeDesignationList where clientId equals to UPDATED_CLIENT_ID
        defaultEmployeeDesignationShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where clientId is not null
        defaultEmployeeDesignationShouldBeFound("clientId.specified=true");

        // Get all the employeeDesignationList where clientId is null
        defaultEmployeeDesignationShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultEmployeeDesignationShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDesignationList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultEmployeeDesignationShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultEmployeeDesignationShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDesignationList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultEmployeeDesignationShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where clientId is less than DEFAULT_CLIENT_ID
        defaultEmployeeDesignationShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDesignationList where clientId is less than UPDATED_CLIENT_ID
        defaultEmployeeDesignationShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where clientId is greater than DEFAULT_CLIENT_ID
        defaultEmployeeDesignationShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDesignationList where clientId is greater than SMALLER_CLIENT_ID
        defaultEmployeeDesignationShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeeDesignationsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultEmployeeDesignationShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the employeeDesignationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeDesignationShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultEmployeeDesignationShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the employeeDesignationList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeDesignationShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultEmployeeDesignationShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the employeeDesignationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeDesignationShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeDesignationsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        // Get all the employeeDesignationList where hasExtraData is not null
        defaultEmployeeDesignationShouldBeFound("hasExtraData.specified=true");

        // Get all the employeeDesignationList where hasExtraData is null
        defaultEmployeeDesignationShouldNotBeFound("hasExtraData.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeDesignationShouldBeFound(String filter) throws Exception {
        restEmployeeDesignationMockMvc.perform(get("/api/employee-designations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDesignation.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].designationDate").value(hasItem(DEFAULT_DESIGNATION_DATE)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restEmployeeDesignationMockMvc.perform(get("/api/employee-designations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeDesignationShouldNotBeFound(String filter) throws Exception {
        restEmployeeDesignationMockMvc.perform(get("/api/employee-designations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeDesignationMockMvc.perform(get("/api/employee-designations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeDesignation() throws Exception {
        // Get the employeeDesignation
        restEmployeeDesignationMockMvc.perform(get("/api/employee-designations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeDesignation() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        int databaseSizeBeforeUpdate = employeeDesignationRepository.findAll().size();

        // Update the employeeDesignation
        EmployeeDesignation updatedEmployeeDesignation = employeeDesignationRepository.findById(employeeDesignation.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeDesignation are not directly saved in db
        em.detach(updatedEmployeeDesignation);
        updatedEmployeeDesignation
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .designationDate(UPDATED_DESIGNATION_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        EmployeeDesignationDTO employeeDesignationDTO = employeeDesignationMapper.toDto(updatedEmployeeDesignation);

        restEmployeeDesignationMockMvc.perform(put("/api/employee-designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDesignationDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeDesignation in the database
        List<EmployeeDesignation> employeeDesignationList = employeeDesignationRepository.findAll();
        assertThat(employeeDesignationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDesignation testEmployeeDesignation = employeeDesignationList.get(employeeDesignationList.size() - 1);
        assertThat(testEmployeeDesignation.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployeeDesignation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmployeeDesignation.getDesignationDate()).isEqualTo(UPDATED_DESIGNATION_DATE);
        assertThat(testEmployeeDesignation.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testEmployeeDesignation.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeDesignation() throws Exception {
        int databaseSizeBeforeUpdate = employeeDesignationRepository.findAll().size();

        // Create the EmployeeDesignation
        EmployeeDesignationDTO employeeDesignationDTO = employeeDesignationMapper.toDto(employeeDesignation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDesignationMockMvc.perform(put("/api/employee-designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDesignationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDesignation in the database
        List<EmployeeDesignation> employeeDesignationList = employeeDesignationRepository.findAll();
        assertThat(employeeDesignationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeDesignation() throws Exception {
        // Initialize the database
        employeeDesignationRepository.saveAndFlush(employeeDesignation);

        int databaseSizeBeforeDelete = employeeDesignationRepository.findAll().size();

        // Delete the employeeDesignation
        restEmployeeDesignationMockMvc.perform(delete("/api/employee-designations/{id}", employeeDesignation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeDesignation> employeeDesignationList = employeeDesignationRepository.findAll();
        assertThat(employeeDesignationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
