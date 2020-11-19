package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.domain.Country;
import com.amc.careplanner.repository.EmployeeRepository;
import com.amc.careplanner.service.EmployeeService;
import com.amc.careplanner.service.dto.EmployeeDTO;
import com.amc.careplanner.service.mapper.EmployeeMapper;
import com.amc.careplanner.service.dto.EmployeeCriteria;
import com.amc.careplanner.service.EmployeeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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

import com.amc.careplanner.domain.enumeration.Title;
import com.amc.careplanner.domain.enumeration.Gender;
import com.amc.careplanner.domain.enumeration.TravelMode;
/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeResourceIT {

    private static final Title DEFAULT_TITLE = Title.MR;
    private static final Title UPDATED_TITLE = Title.MRS;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_INITIAL = "A";
    private static final String UPDATED_MIDDLE_INITIAL = "B";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREFERRED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_EMPLOYEE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIAL_SECURITY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_SECURITY_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_PIN_CODE = 1;
    private static final Integer UPDATED_PIN_CODE = 2;
    private static final Integer SMALLER_PIN_CODE = 1 - 1;

    private static final TravelMode DEFAULT_TRANSPORT_MODE = TravelMode.CAR;
    private static final TravelMode UPDATED_TRANSPORT_MODE = TravelMode.BUS;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_URL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeQueryService employeeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .middleInitial(DEFAULT_MIDDLE_INITIAL)
            .lastName(DEFAULT_LAST_NAME)
            .preferredName(DEFAULT_PREFERRED_NAME)
            .gender(DEFAULT_GENDER)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .socialSecurityNumber(DEFAULT_SOCIAL_SECURITY_NUMBER)
            .pinCode(DEFAULT_PIN_CODE)
            .transportMode(DEFAULT_TRANSPORT_MODE)
            .address(DEFAULT_ADDRESS)
            .county(DEFAULT_COUNTY)
            .postCode(DEFAULT_POST_CODE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .photoUrl(DEFAULT_PHOTO_URL)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleInitial(UPDATED_MIDDLE_INITIAL)
            .lastName(UPDATED_LAST_NAME)
            .preferredName(UPDATED_PREFERRED_NAME)
            .gender(UPDATED_GENDER)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .socialSecurityNumber(UPDATED_SOCIAL_SECURITY_NUMBER)
            .pinCode(UPDATED_PIN_CODE)
            .transportMode(UPDATED_TRANSPORT_MODE)
            .address(UPDATED_ADDRESS)
            .county(UPDATED_COUNTY)
            .postCode(UPDATED_POST_CODE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .photoUrl(UPDATED_PHOTO_URL)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployee.getMiddleInitial()).isEqualTo(DEFAULT_MIDDLE_INITIAL);
        assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployee.getPreferredName()).isEqualTo(DEFAULT_PREFERRED_NAME);
        assertThat(testEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEmployee.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployee.getSocialSecurityNumber()).isEqualTo(DEFAULT_SOCIAL_SECURITY_NUMBER);
        assertThat(testEmployee.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testEmployee.getTransportMode()).isEqualTo(DEFAULT_TRANSPORT_MODE);
        assertThat(testEmployee.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEmployee.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testEmployee.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testEmployee.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testEmployee.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEmployee.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testEmployee.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testEmployee.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testEmployee.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setTitle(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFirstName(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setLastName(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setGender(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmployeeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmployeeCode(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransportModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setTransportMode(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setAddress(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDateOfBirth(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setTenantId(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleInitial").value(hasItem(DEFAULT_MIDDLE_INITIAL)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].preferredName").value(hasItem(DEFAULT_PREFERRED_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE)))
            .andExpect(jsonPath("$.[*].socialSecurityNumber").value(hasItem(DEFAULT_SOCIAL_SECURITY_NUMBER)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].transportMode").value(hasItem(DEFAULT_TRANSPORT_MODE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleInitial").value(DEFAULT_MIDDLE_INITIAL))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.preferredName").value(DEFAULT_PREFERRED_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE))
            .andExpect(jsonPath("$.socialSecurityNumber").value(DEFAULT_SOCIAL_SECURITY_NUMBER))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE))
            .andExpect(jsonPath("$.transportMode").value(DEFAULT_TRANSPORT_MODE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.photoUrl").value(DEFAULT_PHOTO_URL))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where title equals to DEFAULT_TITLE
        defaultEmployeeShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the employeeList where title equals to UPDATED_TITLE
        defaultEmployeeShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where title not equals to DEFAULT_TITLE
        defaultEmployeeShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the employeeList where title not equals to UPDATED_TITLE
        defaultEmployeeShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultEmployeeShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the employeeList where title equals to UPDATED_TITLE
        defaultEmployeeShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where title is not null
        defaultEmployeeShouldBeFound("title.specified=true");

        // Get all the employeeList where title is null
        defaultEmployeeShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName equals to DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName not equals to DEFAULT_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName not equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName is not null
        defaultEmployeeShouldBeFound("firstName.specified=true");

        // Get all the employeeList where firstName is null
        defaultEmployeeShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName contains DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName contains UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName does not contain DEFAULT_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName does not contain UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByMiddleInitialIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleInitial equals to DEFAULT_MIDDLE_INITIAL
        defaultEmployeeShouldBeFound("middleInitial.equals=" + DEFAULT_MIDDLE_INITIAL);

        // Get all the employeeList where middleInitial equals to UPDATED_MIDDLE_INITIAL
        defaultEmployeeShouldNotBeFound("middleInitial.equals=" + UPDATED_MIDDLE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMiddleInitialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleInitial not equals to DEFAULT_MIDDLE_INITIAL
        defaultEmployeeShouldNotBeFound("middleInitial.notEquals=" + DEFAULT_MIDDLE_INITIAL);

        // Get all the employeeList where middleInitial not equals to UPDATED_MIDDLE_INITIAL
        defaultEmployeeShouldBeFound("middleInitial.notEquals=" + UPDATED_MIDDLE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMiddleInitialIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleInitial in DEFAULT_MIDDLE_INITIAL or UPDATED_MIDDLE_INITIAL
        defaultEmployeeShouldBeFound("middleInitial.in=" + DEFAULT_MIDDLE_INITIAL + "," + UPDATED_MIDDLE_INITIAL);

        // Get all the employeeList where middleInitial equals to UPDATED_MIDDLE_INITIAL
        defaultEmployeeShouldNotBeFound("middleInitial.in=" + UPDATED_MIDDLE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMiddleInitialIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleInitial is not null
        defaultEmployeeShouldBeFound("middleInitial.specified=true");

        // Get all the employeeList where middleInitial is null
        defaultEmployeeShouldNotBeFound("middleInitial.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByMiddleInitialContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleInitial contains DEFAULT_MIDDLE_INITIAL
        defaultEmployeeShouldBeFound("middleInitial.contains=" + DEFAULT_MIDDLE_INITIAL);

        // Get all the employeeList where middleInitial contains UPDATED_MIDDLE_INITIAL
        defaultEmployeeShouldNotBeFound("middleInitial.contains=" + UPDATED_MIDDLE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMiddleInitialNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleInitial does not contain DEFAULT_MIDDLE_INITIAL
        defaultEmployeeShouldNotBeFound("middleInitial.doesNotContain=" + DEFAULT_MIDDLE_INITIAL);

        // Get all the employeeList where middleInitial does not contain UPDATED_MIDDLE_INITIAL
        defaultEmployeeShouldBeFound("middleInitial.doesNotContain=" + UPDATED_MIDDLE_INITIAL);
    }


    @Test
    @Transactional
    public void getAllEmployeesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName equals to DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName not equals to DEFAULT_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName not equals to UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName is not null
        defaultEmployeeShouldBeFound("lastName.specified=true");

        // Get all the employeeList where lastName is null
        defaultEmployeeShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName contains DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName contains UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName does not contain DEFAULT_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName does not contain UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByPreferredNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where preferredName equals to DEFAULT_PREFERRED_NAME
        defaultEmployeeShouldBeFound("preferredName.equals=" + DEFAULT_PREFERRED_NAME);

        // Get all the employeeList where preferredName equals to UPDATED_PREFERRED_NAME
        defaultEmployeeShouldNotBeFound("preferredName.equals=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPreferredNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where preferredName not equals to DEFAULT_PREFERRED_NAME
        defaultEmployeeShouldNotBeFound("preferredName.notEquals=" + DEFAULT_PREFERRED_NAME);

        // Get all the employeeList where preferredName not equals to UPDATED_PREFERRED_NAME
        defaultEmployeeShouldBeFound("preferredName.notEquals=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPreferredNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where preferredName in DEFAULT_PREFERRED_NAME or UPDATED_PREFERRED_NAME
        defaultEmployeeShouldBeFound("preferredName.in=" + DEFAULT_PREFERRED_NAME + "," + UPDATED_PREFERRED_NAME);

        // Get all the employeeList where preferredName equals to UPDATED_PREFERRED_NAME
        defaultEmployeeShouldNotBeFound("preferredName.in=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPreferredNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where preferredName is not null
        defaultEmployeeShouldBeFound("preferredName.specified=true");

        // Get all the employeeList where preferredName is null
        defaultEmployeeShouldNotBeFound("preferredName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByPreferredNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where preferredName contains DEFAULT_PREFERRED_NAME
        defaultEmployeeShouldBeFound("preferredName.contains=" + DEFAULT_PREFERRED_NAME);

        // Get all the employeeList where preferredName contains UPDATED_PREFERRED_NAME
        defaultEmployeeShouldNotBeFound("preferredName.contains=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPreferredNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where preferredName does not contain DEFAULT_PREFERRED_NAME
        defaultEmployeeShouldNotBeFound("preferredName.doesNotContain=" + DEFAULT_PREFERRED_NAME);

        // Get all the employeeList where preferredName does not contain UPDATED_PREFERRED_NAME
        defaultEmployeeShouldBeFound("preferredName.doesNotContain=" + UPDATED_PREFERRED_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender equals to DEFAULT_GENDER
        defaultEmployeeShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the employeeList where gender equals to UPDATED_GENDER
        defaultEmployeeShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender not equals to DEFAULT_GENDER
        defaultEmployeeShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the employeeList where gender not equals to UPDATED_GENDER
        defaultEmployeeShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultEmployeeShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the employeeList where gender equals to UPDATED_GENDER
        defaultEmployeeShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender is not null
        defaultEmployeeShouldBeFound("gender.specified=true");

        // Get all the employeeList where gender is null
        defaultEmployeeShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode equals to DEFAULT_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.equals=" + DEFAULT_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode equals to UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.equals=" + UPDATED_EMPLOYEE_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode not equals to DEFAULT_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.notEquals=" + DEFAULT_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode not equals to UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.notEquals=" + UPDATED_EMPLOYEE_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode in DEFAULT_EMPLOYEE_CODE or UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.in=" + DEFAULT_EMPLOYEE_CODE + "," + UPDATED_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode equals to UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.in=" + UPDATED_EMPLOYEE_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode is not null
        defaultEmployeeShouldBeFound("employeeCode.specified=true");

        // Get all the employeeList where employeeCode is null
        defaultEmployeeShouldNotBeFound("employeeCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByEmployeeCodeContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode contains DEFAULT_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.contains=" + DEFAULT_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode contains UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.contains=" + UPDATED_EMPLOYEE_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode does not contain DEFAULT_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.doesNotContain=" + DEFAULT_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode does not contain UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.doesNotContain=" + UPDATED_EMPLOYEE_CODE);
    }


    @Test
    @Transactional
    public void getAllEmployeesBySocialSecurityNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where socialSecurityNumber equals to DEFAULT_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldBeFound("socialSecurityNumber.equals=" + DEFAULT_SOCIAL_SECURITY_NUMBER);

        // Get all the employeeList where socialSecurityNumber equals to UPDATED_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldNotBeFound("socialSecurityNumber.equals=" + UPDATED_SOCIAL_SECURITY_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySocialSecurityNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where socialSecurityNumber not equals to DEFAULT_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldNotBeFound("socialSecurityNumber.notEquals=" + DEFAULT_SOCIAL_SECURITY_NUMBER);

        // Get all the employeeList where socialSecurityNumber not equals to UPDATED_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldBeFound("socialSecurityNumber.notEquals=" + UPDATED_SOCIAL_SECURITY_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySocialSecurityNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where socialSecurityNumber in DEFAULT_SOCIAL_SECURITY_NUMBER or UPDATED_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldBeFound("socialSecurityNumber.in=" + DEFAULT_SOCIAL_SECURITY_NUMBER + "," + UPDATED_SOCIAL_SECURITY_NUMBER);

        // Get all the employeeList where socialSecurityNumber equals to UPDATED_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldNotBeFound("socialSecurityNumber.in=" + UPDATED_SOCIAL_SECURITY_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySocialSecurityNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where socialSecurityNumber is not null
        defaultEmployeeShouldBeFound("socialSecurityNumber.specified=true");

        // Get all the employeeList where socialSecurityNumber is null
        defaultEmployeeShouldNotBeFound("socialSecurityNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesBySocialSecurityNumberContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where socialSecurityNumber contains DEFAULT_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldBeFound("socialSecurityNumber.contains=" + DEFAULT_SOCIAL_SECURITY_NUMBER);

        // Get all the employeeList where socialSecurityNumber contains UPDATED_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldNotBeFound("socialSecurityNumber.contains=" + UPDATED_SOCIAL_SECURITY_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySocialSecurityNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where socialSecurityNumber does not contain DEFAULT_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldNotBeFound("socialSecurityNumber.doesNotContain=" + DEFAULT_SOCIAL_SECURITY_NUMBER);

        // Get all the employeeList where socialSecurityNumber does not contain UPDATED_SOCIAL_SECURITY_NUMBER
        defaultEmployeeShouldBeFound("socialSecurityNumber.doesNotContain=" + UPDATED_SOCIAL_SECURITY_NUMBER);
    }


    @Test
    @Transactional
    public void getAllEmployeesByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where pinCode equals to DEFAULT_PIN_CODE
        defaultEmployeeShouldBeFound("pinCode.equals=" + DEFAULT_PIN_CODE);

        // Get all the employeeList where pinCode equals to UPDATED_PIN_CODE
        defaultEmployeeShouldNotBeFound("pinCode.equals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPinCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where pinCode not equals to DEFAULT_PIN_CODE
        defaultEmployeeShouldNotBeFound("pinCode.notEquals=" + DEFAULT_PIN_CODE);

        // Get all the employeeList where pinCode not equals to UPDATED_PIN_CODE
        defaultEmployeeShouldBeFound("pinCode.notEquals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where pinCode in DEFAULT_PIN_CODE or UPDATED_PIN_CODE
        defaultEmployeeShouldBeFound("pinCode.in=" + DEFAULT_PIN_CODE + "," + UPDATED_PIN_CODE);

        // Get all the employeeList where pinCode equals to UPDATED_PIN_CODE
        defaultEmployeeShouldNotBeFound("pinCode.in=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where pinCode is not null
        defaultEmployeeShouldBeFound("pinCode.specified=true");

        // Get all the employeeList where pinCode is null
        defaultEmployeeShouldNotBeFound("pinCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByPinCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where pinCode is greater than or equal to DEFAULT_PIN_CODE
        defaultEmployeeShouldBeFound("pinCode.greaterThanOrEqual=" + DEFAULT_PIN_CODE);

        // Get all the employeeList where pinCode is greater than or equal to UPDATED_PIN_CODE
        defaultEmployeeShouldNotBeFound("pinCode.greaterThanOrEqual=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPinCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where pinCode is less than or equal to DEFAULT_PIN_CODE
        defaultEmployeeShouldBeFound("pinCode.lessThanOrEqual=" + DEFAULT_PIN_CODE);

        // Get all the employeeList where pinCode is less than or equal to SMALLER_PIN_CODE
        defaultEmployeeShouldNotBeFound("pinCode.lessThanOrEqual=" + SMALLER_PIN_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPinCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where pinCode is less than DEFAULT_PIN_CODE
        defaultEmployeeShouldNotBeFound("pinCode.lessThan=" + DEFAULT_PIN_CODE);

        // Get all the employeeList where pinCode is less than UPDATED_PIN_CODE
        defaultEmployeeShouldBeFound("pinCode.lessThan=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPinCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where pinCode is greater than DEFAULT_PIN_CODE
        defaultEmployeeShouldNotBeFound("pinCode.greaterThan=" + DEFAULT_PIN_CODE);

        // Get all the employeeList where pinCode is greater than SMALLER_PIN_CODE
        defaultEmployeeShouldBeFound("pinCode.greaterThan=" + SMALLER_PIN_CODE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByTransportModeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where transportMode equals to DEFAULT_TRANSPORT_MODE
        defaultEmployeeShouldBeFound("transportMode.equals=" + DEFAULT_TRANSPORT_MODE);

        // Get all the employeeList where transportMode equals to UPDATED_TRANSPORT_MODE
        defaultEmployeeShouldNotBeFound("transportMode.equals=" + UPDATED_TRANSPORT_MODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTransportModeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where transportMode not equals to DEFAULT_TRANSPORT_MODE
        defaultEmployeeShouldNotBeFound("transportMode.notEquals=" + DEFAULT_TRANSPORT_MODE);

        // Get all the employeeList where transportMode not equals to UPDATED_TRANSPORT_MODE
        defaultEmployeeShouldBeFound("transportMode.notEquals=" + UPDATED_TRANSPORT_MODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTransportModeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where transportMode in DEFAULT_TRANSPORT_MODE or UPDATED_TRANSPORT_MODE
        defaultEmployeeShouldBeFound("transportMode.in=" + DEFAULT_TRANSPORT_MODE + "," + UPDATED_TRANSPORT_MODE);

        // Get all the employeeList where transportMode equals to UPDATED_TRANSPORT_MODE
        defaultEmployeeShouldNotBeFound("transportMode.in=" + UPDATED_TRANSPORT_MODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTransportModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where transportMode is not null
        defaultEmployeeShouldBeFound("transportMode.specified=true");

        // Get all the employeeList where transportMode is null
        defaultEmployeeShouldNotBeFound("transportMode.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address equals to DEFAULT_ADDRESS
        defaultEmployeeShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the employeeList where address equals to UPDATED_ADDRESS
        defaultEmployeeShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address not equals to DEFAULT_ADDRESS
        defaultEmployeeShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the employeeList where address not equals to UPDATED_ADDRESS
        defaultEmployeeShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultEmployeeShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the employeeList where address equals to UPDATED_ADDRESS
        defaultEmployeeShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address is not null
        defaultEmployeeShouldBeFound("address.specified=true");

        // Get all the employeeList where address is null
        defaultEmployeeShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByAddressContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address contains DEFAULT_ADDRESS
        defaultEmployeeShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the employeeList where address contains UPDATED_ADDRESS
        defaultEmployeeShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address does not contain DEFAULT_ADDRESS
        defaultEmployeeShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the employeeList where address does not contain UPDATED_ADDRESS
        defaultEmployeeShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllEmployeesByCountyIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where county equals to DEFAULT_COUNTY
        defaultEmployeeShouldBeFound("county.equals=" + DEFAULT_COUNTY);

        // Get all the employeeList where county equals to UPDATED_COUNTY
        defaultEmployeeShouldNotBeFound("county.equals=" + UPDATED_COUNTY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCountyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where county not equals to DEFAULT_COUNTY
        defaultEmployeeShouldNotBeFound("county.notEquals=" + DEFAULT_COUNTY);

        // Get all the employeeList where county not equals to UPDATED_COUNTY
        defaultEmployeeShouldBeFound("county.notEquals=" + UPDATED_COUNTY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCountyIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where county in DEFAULT_COUNTY or UPDATED_COUNTY
        defaultEmployeeShouldBeFound("county.in=" + DEFAULT_COUNTY + "," + UPDATED_COUNTY);

        // Get all the employeeList where county equals to UPDATED_COUNTY
        defaultEmployeeShouldNotBeFound("county.in=" + UPDATED_COUNTY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCountyIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where county is not null
        defaultEmployeeShouldBeFound("county.specified=true");

        // Get all the employeeList where county is null
        defaultEmployeeShouldNotBeFound("county.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByCountyContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where county contains DEFAULT_COUNTY
        defaultEmployeeShouldBeFound("county.contains=" + DEFAULT_COUNTY);

        // Get all the employeeList where county contains UPDATED_COUNTY
        defaultEmployeeShouldNotBeFound("county.contains=" + UPDATED_COUNTY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCountyNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where county does not contain DEFAULT_COUNTY
        defaultEmployeeShouldNotBeFound("county.doesNotContain=" + DEFAULT_COUNTY);

        // Get all the employeeList where county does not contain UPDATED_COUNTY
        defaultEmployeeShouldBeFound("county.doesNotContain=" + UPDATED_COUNTY);
    }


    @Test
    @Transactional
    public void getAllEmployeesByPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where postCode equals to DEFAULT_POST_CODE
        defaultEmployeeShouldBeFound("postCode.equals=" + DEFAULT_POST_CODE);

        // Get all the employeeList where postCode equals to UPDATED_POST_CODE
        defaultEmployeeShouldNotBeFound("postCode.equals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPostCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where postCode not equals to DEFAULT_POST_CODE
        defaultEmployeeShouldNotBeFound("postCode.notEquals=" + DEFAULT_POST_CODE);

        // Get all the employeeList where postCode not equals to UPDATED_POST_CODE
        defaultEmployeeShouldBeFound("postCode.notEquals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where postCode in DEFAULT_POST_CODE or UPDATED_POST_CODE
        defaultEmployeeShouldBeFound("postCode.in=" + DEFAULT_POST_CODE + "," + UPDATED_POST_CODE);

        // Get all the employeeList where postCode equals to UPDATED_POST_CODE
        defaultEmployeeShouldNotBeFound("postCode.in=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where postCode is not null
        defaultEmployeeShouldBeFound("postCode.specified=true");

        // Get all the employeeList where postCode is null
        defaultEmployeeShouldNotBeFound("postCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByPostCodeContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where postCode contains DEFAULT_POST_CODE
        defaultEmployeeShouldBeFound("postCode.contains=" + DEFAULT_POST_CODE);

        // Get all the employeeList where postCode contains UPDATED_POST_CODE
        defaultEmployeeShouldNotBeFound("postCode.contains=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPostCodeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where postCode does not contain DEFAULT_POST_CODE
        defaultEmployeeShouldNotBeFound("postCode.doesNotContain=" + DEFAULT_POST_CODE);

        // Get all the employeeList where postCode does not contain UPDATED_POST_CODE
        defaultEmployeeShouldBeFound("postCode.doesNotContain=" + UPDATED_POST_CODE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultEmployeeShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the employeeList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultEmployeeShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultEmployeeShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the employeeList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultEmployeeShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultEmployeeShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the employeeList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultEmployeeShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is not null
        defaultEmployeeShouldBeFound("dateOfBirth.specified=true");

        // Get all the employeeList where dateOfBirth is null
        defaultEmployeeShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultEmployeeShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the employeeList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultEmployeeShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultEmployeeShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the employeeList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultEmployeeShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultEmployeeShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the employeeList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultEmployeeShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultEmployeeShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the employeeList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultEmployeeShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllEmployeesByPhotoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where photoUrl equals to DEFAULT_PHOTO_URL
        defaultEmployeeShouldBeFound("photoUrl.equals=" + DEFAULT_PHOTO_URL);

        // Get all the employeeList where photoUrl equals to UPDATED_PHOTO_URL
        defaultEmployeeShouldNotBeFound("photoUrl.equals=" + UPDATED_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPhotoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where photoUrl not equals to DEFAULT_PHOTO_URL
        defaultEmployeeShouldNotBeFound("photoUrl.notEquals=" + DEFAULT_PHOTO_URL);

        // Get all the employeeList where photoUrl not equals to UPDATED_PHOTO_URL
        defaultEmployeeShouldBeFound("photoUrl.notEquals=" + UPDATED_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPhotoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where photoUrl in DEFAULT_PHOTO_URL or UPDATED_PHOTO_URL
        defaultEmployeeShouldBeFound("photoUrl.in=" + DEFAULT_PHOTO_URL + "," + UPDATED_PHOTO_URL);

        // Get all the employeeList where photoUrl equals to UPDATED_PHOTO_URL
        defaultEmployeeShouldNotBeFound("photoUrl.in=" + UPDATED_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPhotoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where photoUrl is not null
        defaultEmployeeShouldBeFound("photoUrl.specified=true");

        // Get all the employeeList where photoUrl is null
        defaultEmployeeShouldNotBeFound("photoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByPhotoUrlContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where photoUrl contains DEFAULT_PHOTO_URL
        defaultEmployeeShouldBeFound("photoUrl.contains=" + DEFAULT_PHOTO_URL);

        // Get all the employeeList where photoUrl contains UPDATED_PHOTO_URL
        defaultEmployeeShouldNotBeFound("photoUrl.contains=" + UPDATED_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPhotoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where photoUrl does not contain DEFAULT_PHOTO_URL
        defaultEmployeeShouldNotBeFound("photoUrl.doesNotContain=" + DEFAULT_PHOTO_URL);

        // Get all the employeeList where photoUrl does not contain UPDATED_PHOTO_URL
        defaultEmployeeShouldBeFound("photoUrl.doesNotContain=" + UPDATED_PHOTO_URL);
    }


    @Test
    @Transactional
    public void getAllEmployeesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultEmployeeShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the employeeList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastUpdatedDate is not null
        defaultEmployeeShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the employeeList where lastUpdatedDate is null
        defaultEmployeeShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultEmployeeShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultEmployeeShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultEmployeeShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tenantId equals to DEFAULT_TENANT_ID
        defaultEmployeeShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the employeeList where tenantId equals to UPDATED_TENANT_ID
        defaultEmployeeShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tenantId not equals to DEFAULT_TENANT_ID
        defaultEmployeeShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the employeeList where tenantId not equals to UPDATED_TENANT_ID
        defaultEmployeeShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultEmployeeShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the employeeList where tenantId equals to UPDATED_TENANT_ID
        defaultEmployeeShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tenantId is not null
        defaultEmployeeShouldBeFound("tenantId.specified=true");

        // Get all the employeeList where tenantId is null
        defaultEmployeeShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultEmployeeShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the employeeList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultEmployeeShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultEmployeeShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the employeeList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultEmployeeShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tenantId is less than DEFAULT_TENANT_ID
        defaultEmployeeShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the employeeList where tenantId is less than UPDATED_TENANT_ID
        defaultEmployeeShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tenantId is greater than DEFAULT_TENANT_ID
        defaultEmployeeShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the employeeList where tenantId is greater than SMALLER_TENANT_ID
        defaultEmployeeShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        employee.setUser(user);
        employeeRepository.saveAndFlush(employee);
        Long userId = user.getId();

        // Get all the employeeList where user equals to userId
        defaultEmployeeShouldBeFound("userId.equals=" + userId);

        // Get all the employeeList where user equals to userId + 1
        defaultEmployeeShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Country nationality = CountryResourceIT.createEntity(em);
        em.persist(nationality);
        em.flush();
        employee.setNationality(nationality);
        employeeRepository.saveAndFlush(employee);
        Long nationalityId = nationality.getId();

        // Get all the employeeList where nationality equals to nationalityId
        defaultEmployeeShouldBeFound("nationalityId.equals=" + nationalityId);

        // Get all the employeeList where nationality equals to nationalityId + 1
        defaultEmployeeShouldNotBeFound("nationalityId.equals=" + (nationalityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleInitial").value(hasItem(DEFAULT_MIDDLE_INITIAL)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].preferredName").value(hasItem(DEFAULT_PREFERRED_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE)))
            .andExpect(jsonPath("$.[*].socialSecurityNumber").value(hasItem(DEFAULT_SOCIAL_SECURITY_NUMBER)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].transportMode").value(hasItem(DEFAULT_TRANSPORT_MODE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleInitial(UPDATED_MIDDLE_INITIAL)
            .lastName(UPDATED_LAST_NAME)
            .preferredName(UPDATED_PREFERRED_NAME)
            .gender(UPDATED_GENDER)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .socialSecurityNumber(UPDATED_SOCIAL_SECURITY_NUMBER)
            .pinCode(UPDATED_PIN_CODE)
            .transportMode(UPDATED_TRANSPORT_MODE)
            .address(UPDATED_ADDRESS)
            .county(UPDATED_COUNTY)
            .postCode(UPDATED_POST_CODE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .photoUrl(UPDATED_PHOTO_URL)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getMiddleInitial()).isEqualTo(UPDATED_MIDDLE_INITIAL);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getPreferredName()).isEqualTo(UPDATED_PREFERRED_NAME);
        assertThat(testEmployee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEmployee.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployee.getSocialSecurityNumber()).isEqualTo(UPDATED_SOCIAL_SECURITY_NUMBER);
        assertThat(testEmployee.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testEmployee.getTransportMode()).isEqualTo(UPDATED_TRANSPORT_MODE);
        assertThat(testEmployee.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEmployee.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testEmployee.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testEmployee.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testEmployee.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmployee.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testEmployee.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testEmployee.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testEmployee.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
