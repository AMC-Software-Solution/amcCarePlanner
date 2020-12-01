package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.domain.Branch;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.ServiceUserRepository;
import com.amc.careplanner.service.ServiceUserService;
import com.amc.careplanner.service.dto.ServiceUserDTO;
import com.amc.careplanner.service.mapper.ServiceUserMapper;
import com.amc.careplanner.service.dto.ServiceUserCriteria;
import com.amc.careplanner.service.ServiceUserQueryService;

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
import com.amc.careplanner.domain.enumeration.SupportType;
import com.amc.careplanner.domain.enumeration.ServiceUserCategory;
import com.amc.careplanner.domain.enumeration.Vulnerability;
import com.amc.careplanner.domain.enumeration.ServicePriority;
import com.amc.careplanner.domain.enumeration.Source;
import com.amc.careplanner.domain.enumeration.ServiceUserStatus;
/**
 * Integration tests for the {@link ServiceUserResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceUserResourceIT {

    private static final Title DEFAULT_TITLE = Title.MR;
    private static final Title UPDATED_TITLE = Title.MRS;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREFERRED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_USER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_USER_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_LAST_VISIT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_VISIT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_VISIT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final SupportType DEFAULT_SUPPORT_TYPE = SupportType.COMPLEX_CARE_LIVE_IN;
    private static final SupportType UPDATED_SUPPORT_TYPE = SupportType.DOMICILIARY_CARE;

    private static final ServiceUserCategory DEFAULT_SERVICE_USER_CATEGORY = ServiceUserCategory.HIV_AIDS;
    private static final ServiceUserCategory UPDATED_SERVICE_USER_CATEGORY = ServiceUserCategory.LEARNING_DISABILITIES;

    private static final Vulnerability DEFAULT_VULNERABILITY = Vulnerability.HIV_AIDS;
    private static final Vulnerability UPDATED_VULNERABILITY = Vulnerability.LEARNING_DISABILITIES;

    private static final ServicePriority DEFAULT_SERVICE_PRIORITY = ServicePriority.HIGH;
    private static final ServicePriority UPDATED_SERVICE_PRIORITY = ServicePriority.LOW;

    private static final Source DEFAULT_SOURCE = Source.PRIVATE_SERVICE_USER;
    private static final Source UPDATED_SOURCE = Source.SOCIAL_SERVICES_REFERRAL;

    private static final ServiceUserStatus DEFAULT_STATUS = ServiceUserStatus.ACTIVE;
    private static final ServiceUserStatus UPDATED_STATUS = ServiceUserStatus.DEACTIVE;

    private static final String DEFAULT_FIRST_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_LANGUAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INTERPRETER_REQUIRED = false;
    private static final Boolean UPDATED_INTERPRETER_REQUIRED = true;

    private static final ZonedDateTime DEFAULT_ACTIVATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTIVATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ACTIVATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final byte[] DEFAULT_PROFILE_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROFILE_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PROFILE_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROFILE_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PROFILE_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PHOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_RECORDED_HEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_LAST_RECORDED_HEIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_RECORDED_WEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_LAST_RECORDED_WEIGHT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_MEDICAL_CONDITION = false;
    private static final Boolean UPDATED_HAS_MEDICAL_CONDITION = true;

    private static final String DEFAULT_MEDICAL_CONDITION_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_CONDITION_SUMMARY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    @Autowired
    private ServiceUserRepository serviceUserRepository;

    @Autowired
    private ServiceUserMapper serviceUserMapper;

    @Autowired
    private ServiceUserService serviceUserService;

    @Autowired
    private ServiceUserQueryService serviceUserQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceUserMockMvc;

    private ServiceUser serviceUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUser createEntity(EntityManager em) {
        ServiceUser serviceUser = new ServiceUser()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .preferredName(DEFAULT_PREFERRED_NAME)
            .email(DEFAULT_EMAIL)
            .serviceUserCode(DEFAULT_SERVICE_USER_CODE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .lastVisitDate(DEFAULT_LAST_VISIT_DATE)
            .startDate(DEFAULT_START_DATE)
            .supportType(DEFAULT_SUPPORT_TYPE)
            .serviceUserCategory(DEFAULT_SERVICE_USER_CATEGORY)
            .vulnerability(DEFAULT_VULNERABILITY)
            .servicePriority(DEFAULT_SERVICE_PRIORITY)
            .source(DEFAULT_SOURCE)
            .status(DEFAULT_STATUS)
            .firstLanguage(DEFAULT_FIRST_LANGUAGE)
            .interpreterRequired(DEFAULT_INTERPRETER_REQUIRED)
            .activatedDate(DEFAULT_ACTIVATED_DATE)
            .profilePhoto(DEFAULT_PROFILE_PHOTO)
            .profilePhotoContentType(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE)
            .profilePhotoUrl(DEFAULT_PROFILE_PHOTO_URL)
            .lastRecordedHeight(DEFAULT_LAST_RECORDED_HEIGHT)
            .lastRecordedWeight(DEFAULT_LAST_RECORDED_WEIGHT)
            .hasMedicalCondition(DEFAULT_HAS_MEDICAL_CONDITION)
            .medicalConditionSummary(DEFAULT_MEDICAL_CONDITION_SUMMARY)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID);
        return serviceUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUser createUpdatedEntity(EntityManager em) {
        ServiceUser serviceUser = new ServiceUser()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .preferredName(UPDATED_PREFERRED_NAME)
            .email(UPDATED_EMAIL)
            .serviceUserCode(UPDATED_SERVICE_USER_CODE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .lastVisitDate(UPDATED_LAST_VISIT_DATE)
            .startDate(UPDATED_START_DATE)
            .supportType(UPDATED_SUPPORT_TYPE)
            .serviceUserCategory(UPDATED_SERVICE_USER_CATEGORY)
            .vulnerability(UPDATED_VULNERABILITY)
            .servicePriority(UPDATED_SERVICE_PRIORITY)
            .source(UPDATED_SOURCE)
            .status(UPDATED_STATUS)
            .firstLanguage(UPDATED_FIRST_LANGUAGE)
            .interpreterRequired(UPDATED_INTERPRETER_REQUIRED)
            .activatedDate(UPDATED_ACTIVATED_DATE)
            .profilePhoto(UPDATED_PROFILE_PHOTO)
            .profilePhotoContentType(UPDATED_PROFILE_PHOTO_CONTENT_TYPE)
            .profilePhotoUrl(UPDATED_PROFILE_PHOTO_URL)
            .lastRecordedHeight(UPDATED_LAST_RECORDED_HEIGHT)
            .lastRecordedWeight(UPDATED_LAST_RECORDED_WEIGHT)
            .hasMedicalCondition(UPDATED_HAS_MEDICAL_CONDITION)
            .medicalConditionSummary(UPDATED_MEDICAL_CONDITION_SUMMARY)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        return serviceUser;
    }

    @BeforeEach
    public void initTest() {
        serviceUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceUser() throws Exception {
        int databaseSizeBeforeCreate = serviceUserRepository.findAll().size();
        // Create the ServiceUser
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);
        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceUser in the database
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceUser testServiceUser = serviceUserList.get(serviceUserList.size() - 1);
        assertThat(testServiceUser.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testServiceUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testServiceUser.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testServiceUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testServiceUser.getPreferredName()).isEqualTo(DEFAULT_PREFERRED_NAME);
        assertThat(testServiceUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testServiceUser.getServiceUserCode()).isEqualTo(DEFAULT_SERVICE_USER_CODE);
        assertThat(testServiceUser.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testServiceUser.getLastVisitDate()).isEqualTo(DEFAULT_LAST_VISIT_DATE);
        assertThat(testServiceUser.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testServiceUser.getSupportType()).isEqualTo(DEFAULT_SUPPORT_TYPE);
        assertThat(testServiceUser.getServiceUserCategory()).isEqualTo(DEFAULT_SERVICE_USER_CATEGORY);
        assertThat(testServiceUser.getVulnerability()).isEqualTo(DEFAULT_VULNERABILITY);
        assertThat(testServiceUser.getServicePriority()).isEqualTo(DEFAULT_SERVICE_PRIORITY);
        assertThat(testServiceUser.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testServiceUser.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testServiceUser.getFirstLanguage()).isEqualTo(DEFAULT_FIRST_LANGUAGE);
        assertThat(testServiceUser.isInterpreterRequired()).isEqualTo(DEFAULT_INTERPRETER_REQUIRED);
        assertThat(testServiceUser.getActivatedDate()).isEqualTo(DEFAULT_ACTIVATED_DATE);
        assertThat(testServiceUser.getProfilePhoto()).isEqualTo(DEFAULT_PROFILE_PHOTO);
        assertThat(testServiceUser.getProfilePhotoContentType()).isEqualTo(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE);
        assertThat(testServiceUser.getProfilePhotoUrl()).isEqualTo(DEFAULT_PROFILE_PHOTO_URL);
        assertThat(testServiceUser.getLastRecordedHeight()).isEqualTo(DEFAULT_LAST_RECORDED_HEIGHT);
        assertThat(testServiceUser.getLastRecordedWeight()).isEqualTo(DEFAULT_LAST_RECORDED_WEIGHT);
        assertThat(testServiceUser.isHasMedicalCondition()).isEqualTo(DEFAULT_HAS_MEDICAL_CONDITION);
        assertThat(testServiceUser.getMedicalConditionSummary()).isEqualTo(DEFAULT_MEDICAL_CONDITION_SUMMARY);
        assertThat(testServiceUser.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testServiceUser.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void createServiceUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceUserRepository.findAll().size();

        // Create the ServiceUser with an existing ID
        serviceUser.setId(1L);
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUser in the database
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setTitle(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setFirstName(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setLastName(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceUserCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setServiceUserCode(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setDateOfBirth(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setStartDate(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSupportTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setSupportType(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceUserCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setServiceUserCategory(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVulnerabilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setVulnerability(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServicePriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setServicePriority(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setSource(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setStatus(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserRepository.findAll().size();
        // set the field null
        serviceUser.setClientId(null);

        // Create the ServiceUser, which fails.
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);


        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceUsers() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList
        restServiceUserMockMvc.perform(get("/api/service-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].preferredName").value(hasItem(DEFAULT_PREFERRED_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].serviceUserCode").value(hasItem(DEFAULT_SERVICE_USER_CODE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].lastVisitDate").value(hasItem(sameInstant(DEFAULT_LAST_VISIT_DATE))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].supportType").value(hasItem(DEFAULT_SUPPORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].serviceUserCategory").value(hasItem(DEFAULT_SERVICE_USER_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].vulnerability").value(hasItem(DEFAULT_VULNERABILITY.toString())))
            .andExpect(jsonPath("$.[*].servicePriority").value(hasItem(DEFAULT_SERVICE_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].firstLanguage").value(hasItem(DEFAULT_FIRST_LANGUAGE)))
            .andExpect(jsonPath("$.[*].interpreterRequired").value(hasItem(DEFAULT_INTERPRETER_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].activatedDate").value(hasItem(sameInstant(DEFAULT_ACTIVATED_DATE))))
            .andExpect(jsonPath("$.[*].profilePhotoContentType").value(hasItem(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profilePhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_PHOTO))))
            .andExpect(jsonPath("$.[*].profilePhotoUrl").value(hasItem(DEFAULT_PROFILE_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].lastRecordedHeight").value(hasItem(DEFAULT_LAST_RECORDED_HEIGHT)))
            .andExpect(jsonPath("$.[*].lastRecordedWeight").value(hasItem(DEFAULT_LAST_RECORDED_WEIGHT)))
            .andExpect(jsonPath("$.[*].hasMedicalCondition").value(hasItem(DEFAULT_HAS_MEDICAL_CONDITION.booleanValue())))
            .andExpect(jsonPath("$.[*].medicalConditionSummary").value(hasItem(DEFAULT_MEDICAL_CONDITION_SUMMARY)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getServiceUser() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get the serviceUser
        restServiceUserMockMvc.perform(get("/api/service-users/{id}", serviceUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceUser.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.preferredName").value(DEFAULT_PREFERRED_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.serviceUserCode").value(DEFAULT_SERVICE_USER_CODE))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.lastVisitDate").value(sameInstant(DEFAULT_LAST_VISIT_DATE)))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.supportType").value(DEFAULT_SUPPORT_TYPE.toString()))
            .andExpect(jsonPath("$.serviceUserCategory").value(DEFAULT_SERVICE_USER_CATEGORY.toString()))
            .andExpect(jsonPath("$.vulnerability").value(DEFAULT_VULNERABILITY.toString()))
            .andExpect(jsonPath("$.servicePriority").value(DEFAULT_SERVICE_PRIORITY.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.firstLanguage").value(DEFAULT_FIRST_LANGUAGE))
            .andExpect(jsonPath("$.interpreterRequired").value(DEFAULT_INTERPRETER_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.activatedDate").value(sameInstant(DEFAULT_ACTIVATED_DATE)))
            .andExpect(jsonPath("$.profilePhotoContentType").value(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.profilePhoto").value(Base64Utils.encodeToString(DEFAULT_PROFILE_PHOTO)))
            .andExpect(jsonPath("$.profilePhotoUrl").value(DEFAULT_PROFILE_PHOTO_URL))
            .andExpect(jsonPath("$.lastRecordedHeight").value(DEFAULT_LAST_RECORDED_HEIGHT))
            .andExpect(jsonPath("$.lastRecordedWeight").value(DEFAULT_LAST_RECORDED_WEIGHT))
            .andExpect(jsonPath("$.hasMedicalCondition").value(DEFAULT_HAS_MEDICAL_CONDITION.booleanValue()))
            .andExpect(jsonPath("$.medicalConditionSummary").value(DEFAULT_MEDICAL_CONDITION_SUMMARY))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getServiceUsersByIdFiltering() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        Long id = serviceUser.getId();

        defaultServiceUserShouldBeFound("id.equals=" + id);
        defaultServiceUserShouldNotBeFound("id.notEquals=" + id);

        defaultServiceUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceUserShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where title equals to DEFAULT_TITLE
        defaultServiceUserShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the serviceUserList where title equals to UPDATED_TITLE
        defaultServiceUserShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where title not equals to DEFAULT_TITLE
        defaultServiceUserShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the serviceUserList where title not equals to UPDATED_TITLE
        defaultServiceUserShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultServiceUserShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the serviceUserList where title equals to UPDATED_TITLE
        defaultServiceUserShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where title is not null
        defaultServiceUserShouldBeFound("title.specified=true");

        // Get all the serviceUserList where title is null
        defaultServiceUserShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstName equals to DEFAULT_FIRST_NAME
        defaultServiceUserShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the serviceUserList where firstName equals to UPDATED_FIRST_NAME
        defaultServiceUserShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstName not equals to DEFAULT_FIRST_NAME
        defaultServiceUserShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the serviceUserList where firstName not equals to UPDATED_FIRST_NAME
        defaultServiceUserShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultServiceUserShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the serviceUserList where firstName equals to UPDATED_FIRST_NAME
        defaultServiceUserShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstName is not null
        defaultServiceUserShouldBeFound("firstName.specified=true");

        // Get all the serviceUserList where firstName is null
        defaultServiceUserShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstName contains DEFAULT_FIRST_NAME
        defaultServiceUserShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the serviceUserList where firstName contains UPDATED_FIRST_NAME
        defaultServiceUserShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstName does not contain DEFAULT_FIRST_NAME
        defaultServiceUserShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the serviceUserList where firstName does not contain UPDATED_FIRST_NAME
        defaultServiceUserShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultServiceUserShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the serviceUserList where middleName equals to UPDATED_MIDDLE_NAME
        defaultServiceUserShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultServiceUserShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the serviceUserList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultServiceUserShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultServiceUserShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the serviceUserList where middleName equals to UPDATED_MIDDLE_NAME
        defaultServiceUserShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where middleName is not null
        defaultServiceUserShouldBeFound("middleName.specified=true");

        // Get all the serviceUserList where middleName is null
        defaultServiceUserShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where middleName contains DEFAULT_MIDDLE_NAME
        defaultServiceUserShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the serviceUserList where middleName contains UPDATED_MIDDLE_NAME
        defaultServiceUserShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultServiceUserShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the serviceUserList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultServiceUserShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastName equals to DEFAULT_LAST_NAME
        defaultServiceUserShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the serviceUserList where lastName equals to UPDATED_LAST_NAME
        defaultServiceUserShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastName not equals to DEFAULT_LAST_NAME
        defaultServiceUserShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the serviceUserList where lastName not equals to UPDATED_LAST_NAME
        defaultServiceUserShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultServiceUserShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the serviceUserList where lastName equals to UPDATED_LAST_NAME
        defaultServiceUserShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastName is not null
        defaultServiceUserShouldBeFound("lastName.specified=true");

        // Get all the serviceUserList where lastName is null
        defaultServiceUserShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastName contains DEFAULT_LAST_NAME
        defaultServiceUserShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the serviceUserList where lastName contains UPDATED_LAST_NAME
        defaultServiceUserShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastName does not contain DEFAULT_LAST_NAME
        defaultServiceUserShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the serviceUserList where lastName does not contain UPDATED_LAST_NAME
        defaultServiceUserShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByPreferredNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where preferredName equals to DEFAULT_PREFERRED_NAME
        defaultServiceUserShouldBeFound("preferredName.equals=" + DEFAULT_PREFERRED_NAME);

        // Get all the serviceUserList where preferredName equals to UPDATED_PREFERRED_NAME
        defaultServiceUserShouldNotBeFound("preferredName.equals=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByPreferredNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where preferredName not equals to DEFAULT_PREFERRED_NAME
        defaultServiceUserShouldNotBeFound("preferredName.notEquals=" + DEFAULT_PREFERRED_NAME);

        // Get all the serviceUserList where preferredName not equals to UPDATED_PREFERRED_NAME
        defaultServiceUserShouldBeFound("preferredName.notEquals=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByPreferredNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where preferredName in DEFAULT_PREFERRED_NAME or UPDATED_PREFERRED_NAME
        defaultServiceUserShouldBeFound("preferredName.in=" + DEFAULT_PREFERRED_NAME + "," + UPDATED_PREFERRED_NAME);

        // Get all the serviceUserList where preferredName equals to UPDATED_PREFERRED_NAME
        defaultServiceUserShouldNotBeFound("preferredName.in=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByPreferredNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where preferredName is not null
        defaultServiceUserShouldBeFound("preferredName.specified=true");

        // Get all the serviceUserList where preferredName is null
        defaultServiceUserShouldNotBeFound("preferredName.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByPreferredNameContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where preferredName contains DEFAULT_PREFERRED_NAME
        defaultServiceUserShouldBeFound("preferredName.contains=" + DEFAULT_PREFERRED_NAME);

        // Get all the serviceUserList where preferredName contains UPDATED_PREFERRED_NAME
        defaultServiceUserShouldNotBeFound("preferredName.contains=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByPreferredNameNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where preferredName does not contain DEFAULT_PREFERRED_NAME
        defaultServiceUserShouldNotBeFound("preferredName.doesNotContain=" + DEFAULT_PREFERRED_NAME);

        // Get all the serviceUserList where preferredName does not contain UPDATED_PREFERRED_NAME
        defaultServiceUserShouldBeFound("preferredName.doesNotContain=" + UPDATED_PREFERRED_NAME);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where email equals to DEFAULT_EMAIL
        defaultServiceUserShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the serviceUserList where email equals to UPDATED_EMAIL
        defaultServiceUserShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where email not equals to DEFAULT_EMAIL
        defaultServiceUserShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the serviceUserList where email not equals to UPDATED_EMAIL
        defaultServiceUserShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultServiceUserShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the serviceUserList where email equals to UPDATED_EMAIL
        defaultServiceUserShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where email is not null
        defaultServiceUserShouldBeFound("email.specified=true");

        // Get all the serviceUserList where email is null
        defaultServiceUserShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByEmailContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where email contains DEFAULT_EMAIL
        defaultServiceUserShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the serviceUserList where email contains UPDATED_EMAIL
        defaultServiceUserShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where email does not contain DEFAULT_EMAIL
        defaultServiceUserShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the serviceUserList where email does not contain UPDATED_EMAIL
        defaultServiceUserShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCode equals to DEFAULT_SERVICE_USER_CODE
        defaultServiceUserShouldBeFound("serviceUserCode.equals=" + DEFAULT_SERVICE_USER_CODE);

        // Get all the serviceUserList where serviceUserCode equals to UPDATED_SERVICE_USER_CODE
        defaultServiceUserShouldNotBeFound("serviceUserCode.equals=" + UPDATED_SERVICE_USER_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCode not equals to DEFAULT_SERVICE_USER_CODE
        defaultServiceUserShouldNotBeFound("serviceUserCode.notEquals=" + DEFAULT_SERVICE_USER_CODE);

        // Get all the serviceUserList where serviceUserCode not equals to UPDATED_SERVICE_USER_CODE
        defaultServiceUserShouldBeFound("serviceUserCode.notEquals=" + UPDATED_SERVICE_USER_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCodeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCode in DEFAULT_SERVICE_USER_CODE or UPDATED_SERVICE_USER_CODE
        defaultServiceUserShouldBeFound("serviceUserCode.in=" + DEFAULT_SERVICE_USER_CODE + "," + UPDATED_SERVICE_USER_CODE);

        // Get all the serviceUserList where serviceUserCode equals to UPDATED_SERVICE_USER_CODE
        defaultServiceUserShouldNotBeFound("serviceUserCode.in=" + UPDATED_SERVICE_USER_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCode is not null
        defaultServiceUserShouldBeFound("serviceUserCode.specified=true");

        // Get all the serviceUserList where serviceUserCode is null
        defaultServiceUserShouldNotBeFound("serviceUserCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCodeContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCode contains DEFAULT_SERVICE_USER_CODE
        defaultServiceUserShouldBeFound("serviceUserCode.contains=" + DEFAULT_SERVICE_USER_CODE);

        // Get all the serviceUserList where serviceUserCode contains UPDATED_SERVICE_USER_CODE
        defaultServiceUserShouldNotBeFound("serviceUserCode.contains=" + UPDATED_SERVICE_USER_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCodeNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCode does not contain DEFAULT_SERVICE_USER_CODE
        defaultServiceUserShouldNotBeFound("serviceUserCode.doesNotContain=" + DEFAULT_SERVICE_USER_CODE);

        // Get all the serviceUserList where serviceUserCode does not contain UPDATED_SERVICE_USER_CODE
        defaultServiceUserShouldBeFound("serviceUserCode.doesNotContain=" + UPDATED_SERVICE_USER_CODE);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultServiceUserShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the serviceUserList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultServiceUserShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultServiceUserShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the serviceUserList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultServiceUserShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultServiceUserShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the serviceUserList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultServiceUserShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where dateOfBirth is not null
        defaultServiceUserShouldBeFound("dateOfBirth.specified=true");

        // Get all the serviceUserList where dateOfBirth is null
        defaultServiceUserShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultServiceUserShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the serviceUserList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultServiceUserShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultServiceUserShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the serviceUserList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultServiceUserShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultServiceUserShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the serviceUserList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultServiceUserShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultServiceUserShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the serviceUserList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultServiceUserShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByLastVisitDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastVisitDate equals to DEFAULT_LAST_VISIT_DATE
        defaultServiceUserShouldBeFound("lastVisitDate.equals=" + DEFAULT_LAST_VISIT_DATE);

        // Get all the serviceUserList where lastVisitDate equals to UPDATED_LAST_VISIT_DATE
        defaultServiceUserShouldNotBeFound("lastVisitDate.equals=" + UPDATED_LAST_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastVisitDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastVisitDate not equals to DEFAULT_LAST_VISIT_DATE
        defaultServiceUserShouldNotBeFound("lastVisitDate.notEquals=" + DEFAULT_LAST_VISIT_DATE);

        // Get all the serviceUserList where lastVisitDate not equals to UPDATED_LAST_VISIT_DATE
        defaultServiceUserShouldBeFound("lastVisitDate.notEquals=" + UPDATED_LAST_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastVisitDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastVisitDate in DEFAULT_LAST_VISIT_DATE or UPDATED_LAST_VISIT_DATE
        defaultServiceUserShouldBeFound("lastVisitDate.in=" + DEFAULT_LAST_VISIT_DATE + "," + UPDATED_LAST_VISIT_DATE);

        // Get all the serviceUserList where lastVisitDate equals to UPDATED_LAST_VISIT_DATE
        defaultServiceUserShouldNotBeFound("lastVisitDate.in=" + UPDATED_LAST_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastVisitDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastVisitDate is not null
        defaultServiceUserShouldBeFound("lastVisitDate.specified=true");

        // Get all the serviceUserList where lastVisitDate is null
        defaultServiceUserShouldNotBeFound("lastVisitDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastVisitDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastVisitDate is greater than or equal to DEFAULT_LAST_VISIT_DATE
        defaultServiceUserShouldBeFound("lastVisitDate.greaterThanOrEqual=" + DEFAULT_LAST_VISIT_DATE);

        // Get all the serviceUserList where lastVisitDate is greater than or equal to UPDATED_LAST_VISIT_DATE
        defaultServiceUserShouldNotBeFound("lastVisitDate.greaterThanOrEqual=" + UPDATED_LAST_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastVisitDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastVisitDate is less than or equal to DEFAULT_LAST_VISIT_DATE
        defaultServiceUserShouldBeFound("lastVisitDate.lessThanOrEqual=" + DEFAULT_LAST_VISIT_DATE);

        // Get all the serviceUserList where lastVisitDate is less than or equal to SMALLER_LAST_VISIT_DATE
        defaultServiceUserShouldNotBeFound("lastVisitDate.lessThanOrEqual=" + SMALLER_LAST_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastVisitDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastVisitDate is less than DEFAULT_LAST_VISIT_DATE
        defaultServiceUserShouldNotBeFound("lastVisitDate.lessThan=" + DEFAULT_LAST_VISIT_DATE);

        // Get all the serviceUserList where lastVisitDate is less than UPDATED_LAST_VISIT_DATE
        defaultServiceUserShouldBeFound("lastVisitDate.lessThan=" + UPDATED_LAST_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastVisitDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastVisitDate is greater than DEFAULT_LAST_VISIT_DATE
        defaultServiceUserShouldNotBeFound("lastVisitDate.greaterThan=" + DEFAULT_LAST_VISIT_DATE);

        // Get all the serviceUserList where lastVisitDate is greater than SMALLER_LAST_VISIT_DATE
        defaultServiceUserShouldBeFound("lastVisitDate.greaterThan=" + SMALLER_LAST_VISIT_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where startDate equals to DEFAULT_START_DATE
        defaultServiceUserShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the serviceUserList where startDate equals to UPDATED_START_DATE
        defaultServiceUserShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where startDate not equals to DEFAULT_START_DATE
        defaultServiceUserShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the serviceUserList where startDate not equals to UPDATED_START_DATE
        defaultServiceUserShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultServiceUserShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the serviceUserList where startDate equals to UPDATED_START_DATE
        defaultServiceUserShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where startDate is not null
        defaultServiceUserShouldBeFound("startDate.specified=true");

        // Get all the serviceUserList where startDate is null
        defaultServiceUserShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultServiceUserShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the serviceUserList where startDate is greater than or equal to UPDATED_START_DATE
        defaultServiceUserShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where startDate is less than or equal to DEFAULT_START_DATE
        defaultServiceUserShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the serviceUserList where startDate is less than or equal to SMALLER_START_DATE
        defaultServiceUserShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where startDate is less than DEFAULT_START_DATE
        defaultServiceUserShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the serviceUserList where startDate is less than UPDATED_START_DATE
        defaultServiceUserShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where startDate is greater than DEFAULT_START_DATE
        defaultServiceUserShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the serviceUserList where startDate is greater than SMALLER_START_DATE
        defaultServiceUserShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceUsersBySupportTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where supportType equals to DEFAULT_SUPPORT_TYPE
        defaultServiceUserShouldBeFound("supportType.equals=" + DEFAULT_SUPPORT_TYPE);

        // Get all the serviceUserList where supportType equals to UPDATED_SUPPORT_TYPE
        defaultServiceUserShouldNotBeFound("supportType.equals=" + UPDATED_SUPPORT_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersBySupportTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where supportType not equals to DEFAULT_SUPPORT_TYPE
        defaultServiceUserShouldNotBeFound("supportType.notEquals=" + DEFAULT_SUPPORT_TYPE);

        // Get all the serviceUserList where supportType not equals to UPDATED_SUPPORT_TYPE
        defaultServiceUserShouldBeFound("supportType.notEquals=" + UPDATED_SUPPORT_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersBySupportTypeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where supportType in DEFAULT_SUPPORT_TYPE or UPDATED_SUPPORT_TYPE
        defaultServiceUserShouldBeFound("supportType.in=" + DEFAULT_SUPPORT_TYPE + "," + UPDATED_SUPPORT_TYPE);

        // Get all the serviceUserList where supportType equals to UPDATED_SUPPORT_TYPE
        defaultServiceUserShouldNotBeFound("supportType.in=" + UPDATED_SUPPORT_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersBySupportTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where supportType is not null
        defaultServiceUserShouldBeFound("supportType.specified=true");

        // Get all the serviceUserList where supportType is null
        defaultServiceUserShouldNotBeFound("supportType.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCategory equals to DEFAULT_SERVICE_USER_CATEGORY
        defaultServiceUserShouldBeFound("serviceUserCategory.equals=" + DEFAULT_SERVICE_USER_CATEGORY);

        // Get all the serviceUserList where serviceUserCategory equals to UPDATED_SERVICE_USER_CATEGORY
        defaultServiceUserShouldNotBeFound("serviceUserCategory.equals=" + UPDATED_SERVICE_USER_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCategory not equals to DEFAULT_SERVICE_USER_CATEGORY
        defaultServiceUserShouldNotBeFound("serviceUserCategory.notEquals=" + DEFAULT_SERVICE_USER_CATEGORY);

        // Get all the serviceUserList where serviceUserCategory not equals to UPDATED_SERVICE_USER_CATEGORY
        defaultServiceUserShouldBeFound("serviceUserCategory.notEquals=" + UPDATED_SERVICE_USER_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCategory in DEFAULT_SERVICE_USER_CATEGORY or UPDATED_SERVICE_USER_CATEGORY
        defaultServiceUserShouldBeFound("serviceUserCategory.in=" + DEFAULT_SERVICE_USER_CATEGORY + "," + UPDATED_SERVICE_USER_CATEGORY);

        // Get all the serviceUserList where serviceUserCategory equals to UPDATED_SERVICE_USER_CATEGORY
        defaultServiceUserShouldNotBeFound("serviceUserCategory.in=" + UPDATED_SERVICE_USER_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServiceUserCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where serviceUserCategory is not null
        defaultServiceUserShouldBeFound("serviceUserCategory.specified=true");

        // Get all the serviceUserList where serviceUserCategory is null
        defaultServiceUserShouldNotBeFound("serviceUserCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByVulnerabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where vulnerability equals to DEFAULT_VULNERABILITY
        defaultServiceUserShouldBeFound("vulnerability.equals=" + DEFAULT_VULNERABILITY);

        // Get all the serviceUserList where vulnerability equals to UPDATED_VULNERABILITY
        defaultServiceUserShouldNotBeFound("vulnerability.equals=" + UPDATED_VULNERABILITY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByVulnerabilityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where vulnerability not equals to DEFAULT_VULNERABILITY
        defaultServiceUserShouldNotBeFound("vulnerability.notEquals=" + DEFAULT_VULNERABILITY);

        // Get all the serviceUserList where vulnerability not equals to UPDATED_VULNERABILITY
        defaultServiceUserShouldBeFound("vulnerability.notEquals=" + UPDATED_VULNERABILITY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByVulnerabilityIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where vulnerability in DEFAULT_VULNERABILITY or UPDATED_VULNERABILITY
        defaultServiceUserShouldBeFound("vulnerability.in=" + DEFAULT_VULNERABILITY + "," + UPDATED_VULNERABILITY);

        // Get all the serviceUserList where vulnerability equals to UPDATED_VULNERABILITY
        defaultServiceUserShouldNotBeFound("vulnerability.in=" + UPDATED_VULNERABILITY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByVulnerabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where vulnerability is not null
        defaultServiceUserShouldBeFound("vulnerability.specified=true");

        // Get all the serviceUserList where vulnerability is null
        defaultServiceUserShouldNotBeFound("vulnerability.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServicePriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where servicePriority equals to DEFAULT_SERVICE_PRIORITY
        defaultServiceUserShouldBeFound("servicePriority.equals=" + DEFAULT_SERVICE_PRIORITY);

        // Get all the serviceUserList where servicePriority equals to UPDATED_SERVICE_PRIORITY
        defaultServiceUserShouldNotBeFound("servicePriority.equals=" + UPDATED_SERVICE_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServicePriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where servicePriority not equals to DEFAULT_SERVICE_PRIORITY
        defaultServiceUserShouldNotBeFound("servicePriority.notEquals=" + DEFAULT_SERVICE_PRIORITY);

        // Get all the serviceUserList where servicePriority not equals to UPDATED_SERVICE_PRIORITY
        defaultServiceUserShouldBeFound("servicePriority.notEquals=" + UPDATED_SERVICE_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServicePriorityIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where servicePriority in DEFAULT_SERVICE_PRIORITY or UPDATED_SERVICE_PRIORITY
        defaultServiceUserShouldBeFound("servicePriority.in=" + DEFAULT_SERVICE_PRIORITY + "," + UPDATED_SERVICE_PRIORITY);

        // Get all the serviceUserList where servicePriority equals to UPDATED_SERVICE_PRIORITY
        defaultServiceUserShouldNotBeFound("servicePriority.in=" + UPDATED_SERVICE_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByServicePriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where servicePriority is not null
        defaultServiceUserShouldBeFound("servicePriority.specified=true");

        // Get all the serviceUserList where servicePriority is null
        defaultServiceUserShouldNotBeFound("servicePriority.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where source equals to DEFAULT_SOURCE
        defaultServiceUserShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the serviceUserList where source equals to UPDATED_SOURCE
        defaultServiceUserShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where source not equals to DEFAULT_SOURCE
        defaultServiceUserShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the serviceUserList where source not equals to UPDATED_SOURCE
        defaultServiceUserShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultServiceUserShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the serviceUserList where source equals to UPDATED_SOURCE
        defaultServiceUserShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where source is not null
        defaultServiceUserShouldBeFound("source.specified=true");

        // Get all the serviceUserList where source is null
        defaultServiceUserShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where status equals to DEFAULT_STATUS
        defaultServiceUserShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the serviceUserList where status equals to UPDATED_STATUS
        defaultServiceUserShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where status not equals to DEFAULT_STATUS
        defaultServiceUserShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the serviceUserList where status not equals to UPDATED_STATUS
        defaultServiceUserShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultServiceUserShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the serviceUserList where status equals to UPDATED_STATUS
        defaultServiceUserShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where status is not null
        defaultServiceUserShouldBeFound("status.specified=true");

        // Get all the serviceUserList where status is null
        defaultServiceUserShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstLanguage equals to DEFAULT_FIRST_LANGUAGE
        defaultServiceUserShouldBeFound("firstLanguage.equals=" + DEFAULT_FIRST_LANGUAGE);

        // Get all the serviceUserList where firstLanguage equals to UPDATED_FIRST_LANGUAGE
        defaultServiceUserShouldNotBeFound("firstLanguage.equals=" + UPDATED_FIRST_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstLanguageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstLanguage not equals to DEFAULT_FIRST_LANGUAGE
        defaultServiceUserShouldNotBeFound("firstLanguage.notEquals=" + DEFAULT_FIRST_LANGUAGE);

        // Get all the serviceUserList where firstLanguage not equals to UPDATED_FIRST_LANGUAGE
        defaultServiceUserShouldBeFound("firstLanguage.notEquals=" + UPDATED_FIRST_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstLanguage in DEFAULT_FIRST_LANGUAGE or UPDATED_FIRST_LANGUAGE
        defaultServiceUserShouldBeFound("firstLanguage.in=" + DEFAULT_FIRST_LANGUAGE + "," + UPDATED_FIRST_LANGUAGE);

        // Get all the serviceUserList where firstLanguage equals to UPDATED_FIRST_LANGUAGE
        defaultServiceUserShouldNotBeFound("firstLanguage.in=" + UPDATED_FIRST_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstLanguage is not null
        defaultServiceUserShouldBeFound("firstLanguage.specified=true");

        // Get all the serviceUserList where firstLanguage is null
        defaultServiceUserShouldNotBeFound("firstLanguage.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByFirstLanguageContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstLanguage contains DEFAULT_FIRST_LANGUAGE
        defaultServiceUserShouldBeFound("firstLanguage.contains=" + DEFAULT_FIRST_LANGUAGE);

        // Get all the serviceUserList where firstLanguage contains UPDATED_FIRST_LANGUAGE
        defaultServiceUserShouldNotBeFound("firstLanguage.contains=" + UPDATED_FIRST_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByFirstLanguageNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where firstLanguage does not contain DEFAULT_FIRST_LANGUAGE
        defaultServiceUserShouldNotBeFound("firstLanguage.doesNotContain=" + DEFAULT_FIRST_LANGUAGE);

        // Get all the serviceUserList where firstLanguage does not contain UPDATED_FIRST_LANGUAGE
        defaultServiceUserShouldBeFound("firstLanguage.doesNotContain=" + UPDATED_FIRST_LANGUAGE);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByInterpreterRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where interpreterRequired equals to DEFAULT_INTERPRETER_REQUIRED
        defaultServiceUserShouldBeFound("interpreterRequired.equals=" + DEFAULT_INTERPRETER_REQUIRED);

        // Get all the serviceUserList where interpreterRequired equals to UPDATED_INTERPRETER_REQUIRED
        defaultServiceUserShouldNotBeFound("interpreterRequired.equals=" + UPDATED_INTERPRETER_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByInterpreterRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where interpreterRequired not equals to DEFAULT_INTERPRETER_REQUIRED
        defaultServiceUserShouldNotBeFound("interpreterRequired.notEquals=" + DEFAULT_INTERPRETER_REQUIRED);

        // Get all the serviceUserList where interpreterRequired not equals to UPDATED_INTERPRETER_REQUIRED
        defaultServiceUserShouldBeFound("interpreterRequired.notEquals=" + UPDATED_INTERPRETER_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByInterpreterRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where interpreterRequired in DEFAULT_INTERPRETER_REQUIRED or UPDATED_INTERPRETER_REQUIRED
        defaultServiceUserShouldBeFound("interpreterRequired.in=" + DEFAULT_INTERPRETER_REQUIRED + "," + UPDATED_INTERPRETER_REQUIRED);

        // Get all the serviceUserList where interpreterRequired equals to UPDATED_INTERPRETER_REQUIRED
        defaultServiceUserShouldNotBeFound("interpreterRequired.in=" + UPDATED_INTERPRETER_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByInterpreterRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where interpreterRequired is not null
        defaultServiceUserShouldBeFound("interpreterRequired.specified=true");

        // Get all the serviceUserList where interpreterRequired is null
        defaultServiceUserShouldNotBeFound("interpreterRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByActivatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where activatedDate equals to DEFAULT_ACTIVATED_DATE
        defaultServiceUserShouldBeFound("activatedDate.equals=" + DEFAULT_ACTIVATED_DATE);

        // Get all the serviceUserList where activatedDate equals to UPDATED_ACTIVATED_DATE
        defaultServiceUserShouldNotBeFound("activatedDate.equals=" + UPDATED_ACTIVATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByActivatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where activatedDate not equals to DEFAULT_ACTIVATED_DATE
        defaultServiceUserShouldNotBeFound("activatedDate.notEquals=" + DEFAULT_ACTIVATED_DATE);

        // Get all the serviceUserList where activatedDate not equals to UPDATED_ACTIVATED_DATE
        defaultServiceUserShouldBeFound("activatedDate.notEquals=" + UPDATED_ACTIVATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByActivatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where activatedDate in DEFAULT_ACTIVATED_DATE or UPDATED_ACTIVATED_DATE
        defaultServiceUserShouldBeFound("activatedDate.in=" + DEFAULT_ACTIVATED_DATE + "," + UPDATED_ACTIVATED_DATE);

        // Get all the serviceUserList where activatedDate equals to UPDATED_ACTIVATED_DATE
        defaultServiceUserShouldNotBeFound("activatedDate.in=" + UPDATED_ACTIVATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByActivatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where activatedDate is not null
        defaultServiceUserShouldBeFound("activatedDate.specified=true");

        // Get all the serviceUserList where activatedDate is null
        defaultServiceUserShouldNotBeFound("activatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByActivatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where activatedDate is greater than or equal to DEFAULT_ACTIVATED_DATE
        defaultServiceUserShouldBeFound("activatedDate.greaterThanOrEqual=" + DEFAULT_ACTIVATED_DATE);

        // Get all the serviceUserList where activatedDate is greater than or equal to UPDATED_ACTIVATED_DATE
        defaultServiceUserShouldNotBeFound("activatedDate.greaterThanOrEqual=" + UPDATED_ACTIVATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByActivatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where activatedDate is less than or equal to DEFAULT_ACTIVATED_DATE
        defaultServiceUserShouldBeFound("activatedDate.lessThanOrEqual=" + DEFAULT_ACTIVATED_DATE);

        // Get all the serviceUserList where activatedDate is less than or equal to SMALLER_ACTIVATED_DATE
        defaultServiceUserShouldNotBeFound("activatedDate.lessThanOrEqual=" + SMALLER_ACTIVATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByActivatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where activatedDate is less than DEFAULT_ACTIVATED_DATE
        defaultServiceUserShouldNotBeFound("activatedDate.lessThan=" + DEFAULT_ACTIVATED_DATE);

        // Get all the serviceUserList where activatedDate is less than UPDATED_ACTIVATED_DATE
        defaultServiceUserShouldBeFound("activatedDate.lessThan=" + UPDATED_ACTIVATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByActivatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where activatedDate is greater than DEFAULT_ACTIVATED_DATE
        defaultServiceUserShouldNotBeFound("activatedDate.greaterThan=" + DEFAULT_ACTIVATED_DATE);

        // Get all the serviceUserList where activatedDate is greater than SMALLER_ACTIVATED_DATE
        defaultServiceUserShouldBeFound("activatedDate.greaterThan=" + SMALLER_ACTIVATED_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByProfilePhotoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where profilePhotoUrl equals to DEFAULT_PROFILE_PHOTO_URL
        defaultServiceUserShouldBeFound("profilePhotoUrl.equals=" + DEFAULT_PROFILE_PHOTO_URL);

        // Get all the serviceUserList where profilePhotoUrl equals to UPDATED_PROFILE_PHOTO_URL
        defaultServiceUserShouldNotBeFound("profilePhotoUrl.equals=" + UPDATED_PROFILE_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByProfilePhotoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where profilePhotoUrl not equals to DEFAULT_PROFILE_PHOTO_URL
        defaultServiceUserShouldNotBeFound("profilePhotoUrl.notEquals=" + DEFAULT_PROFILE_PHOTO_URL);

        // Get all the serviceUserList where profilePhotoUrl not equals to UPDATED_PROFILE_PHOTO_URL
        defaultServiceUserShouldBeFound("profilePhotoUrl.notEquals=" + UPDATED_PROFILE_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByProfilePhotoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where profilePhotoUrl in DEFAULT_PROFILE_PHOTO_URL or UPDATED_PROFILE_PHOTO_URL
        defaultServiceUserShouldBeFound("profilePhotoUrl.in=" + DEFAULT_PROFILE_PHOTO_URL + "," + UPDATED_PROFILE_PHOTO_URL);

        // Get all the serviceUserList where profilePhotoUrl equals to UPDATED_PROFILE_PHOTO_URL
        defaultServiceUserShouldNotBeFound("profilePhotoUrl.in=" + UPDATED_PROFILE_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByProfilePhotoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where profilePhotoUrl is not null
        defaultServiceUserShouldBeFound("profilePhotoUrl.specified=true");

        // Get all the serviceUserList where profilePhotoUrl is null
        defaultServiceUserShouldNotBeFound("profilePhotoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByProfilePhotoUrlContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where profilePhotoUrl contains DEFAULT_PROFILE_PHOTO_URL
        defaultServiceUserShouldBeFound("profilePhotoUrl.contains=" + DEFAULT_PROFILE_PHOTO_URL);

        // Get all the serviceUserList where profilePhotoUrl contains UPDATED_PROFILE_PHOTO_URL
        defaultServiceUserShouldNotBeFound("profilePhotoUrl.contains=" + UPDATED_PROFILE_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByProfilePhotoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where profilePhotoUrl does not contain DEFAULT_PROFILE_PHOTO_URL
        defaultServiceUserShouldNotBeFound("profilePhotoUrl.doesNotContain=" + DEFAULT_PROFILE_PHOTO_URL);

        // Get all the serviceUserList where profilePhotoUrl does not contain UPDATED_PROFILE_PHOTO_URL
        defaultServiceUserShouldBeFound("profilePhotoUrl.doesNotContain=" + UPDATED_PROFILE_PHOTO_URL);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedHeight equals to DEFAULT_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldBeFound("lastRecordedHeight.equals=" + DEFAULT_LAST_RECORDED_HEIGHT);

        // Get all the serviceUserList where lastRecordedHeight equals to UPDATED_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedHeight.equals=" + UPDATED_LAST_RECORDED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedHeight not equals to DEFAULT_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedHeight.notEquals=" + DEFAULT_LAST_RECORDED_HEIGHT);

        // Get all the serviceUserList where lastRecordedHeight not equals to UPDATED_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldBeFound("lastRecordedHeight.notEquals=" + UPDATED_LAST_RECORDED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedHeightIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedHeight in DEFAULT_LAST_RECORDED_HEIGHT or UPDATED_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldBeFound("lastRecordedHeight.in=" + DEFAULT_LAST_RECORDED_HEIGHT + "," + UPDATED_LAST_RECORDED_HEIGHT);

        // Get all the serviceUserList where lastRecordedHeight equals to UPDATED_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedHeight.in=" + UPDATED_LAST_RECORDED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedHeight is not null
        defaultServiceUserShouldBeFound("lastRecordedHeight.specified=true");

        // Get all the serviceUserList where lastRecordedHeight is null
        defaultServiceUserShouldNotBeFound("lastRecordedHeight.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedHeightContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedHeight contains DEFAULT_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldBeFound("lastRecordedHeight.contains=" + DEFAULT_LAST_RECORDED_HEIGHT);

        // Get all the serviceUserList where lastRecordedHeight contains UPDATED_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedHeight.contains=" + UPDATED_LAST_RECORDED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedHeightNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedHeight does not contain DEFAULT_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedHeight.doesNotContain=" + DEFAULT_LAST_RECORDED_HEIGHT);

        // Get all the serviceUserList where lastRecordedHeight does not contain UPDATED_LAST_RECORDED_HEIGHT
        defaultServiceUserShouldBeFound("lastRecordedHeight.doesNotContain=" + UPDATED_LAST_RECORDED_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedWeight equals to DEFAULT_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldBeFound("lastRecordedWeight.equals=" + DEFAULT_LAST_RECORDED_WEIGHT);

        // Get all the serviceUserList where lastRecordedWeight equals to UPDATED_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedWeight.equals=" + UPDATED_LAST_RECORDED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedWeight not equals to DEFAULT_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedWeight.notEquals=" + DEFAULT_LAST_RECORDED_WEIGHT);

        // Get all the serviceUserList where lastRecordedWeight not equals to UPDATED_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldBeFound("lastRecordedWeight.notEquals=" + UPDATED_LAST_RECORDED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedWeightIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedWeight in DEFAULT_LAST_RECORDED_WEIGHT or UPDATED_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldBeFound("lastRecordedWeight.in=" + DEFAULT_LAST_RECORDED_WEIGHT + "," + UPDATED_LAST_RECORDED_WEIGHT);

        // Get all the serviceUserList where lastRecordedWeight equals to UPDATED_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedWeight.in=" + UPDATED_LAST_RECORDED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedWeight is not null
        defaultServiceUserShouldBeFound("lastRecordedWeight.specified=true");

        // Get all the serviceUserList where lastRecordedWeight is null
        defaultServiceUserShouldNotBeFound("lastRecordedWeight.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedWeightContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedWeight contains DEFAULT_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldBeFound("lastRecordedWeight.contains=" + DEFAULT_LAST_RECORDED_WEIGHT);

        // Get all the serviceUserList where lastRecordedWeight contains UPDATED_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedWeight.contains=" + UPDATED_LAST_RECORDED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastRecordedWeightNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastRecordedWeight does not contain DEFAULT_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldNotBeFound("lastRecordedWeight.doesNotContain=" + DEFAULT_LAST_RECORDED_WEIGHT);

        // Get all the serviceUserList where lastRecordedWeight does not contain UPDATED_LAST_RECORDED_WEIGHT
        defaultServiceUserShouldBeFound("lastRecordedWeight.doesNotContain=" + UPDATED_LAST_RECORDED_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByHasMedicalConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where hasMedicalCondition equals to DEFAULT_HAS_MEDICAL_CONDITION
        defaultServiceUserShouldBeFound("hasMedicalCondition.equals=" + DEFAULT_HAS_MEDICAL_CONDITION);

        // Get all the serviceUserList where hasMedicalCondition equals to UPDATED_HAS_MEDICAL_CONDITION
        defaultServiceUserShouldNotBeFound("hasMedicalCondition.equals=" + UPDATED_HAS_MEDICAL_CONDITION);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByHasMedicalConditionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where hasMedicalCondition not equals to DEFAULT_HAS_MEDICAL_CONDITION
        defaultServiceUserShouldNotBeFound("hasMedicalCondition.notEquals=" + DEFAULT_HAS_MEDICAL_CONDITION);

        // Get all the serviceUserList where hasMedicalCondition not equals to UPDATED_HAS_MEDICAL_CONDITION
        defaultServiceUserShouldBeFound("hasMedicalCondition.notEquals=" + UPDATED_HAS_MEDICAL_CONDITION);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByHasMedicalConditionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where hasMedicalCondition in DEFAULT_HAS_MEDICAL_CONDITION or UPDATED_HAS_MEDICAL_CONDITION
        defaultServiceUserShouldBeFound("hasMedicalCondition.in=" + DEFAULT_HAS_MEDICAL_CONDITION + "," + UPDATED_HAS_MEDICAL_CONDITION);

        // Get all the serviceUserList where hasMedicalCondition equals to UPDATED_HAS_MEDICAL_CONDITION
        defaultServiceUserShouldNotBeFound("hasMedicalCondition.in=" + UPDATED_HAS_MEDICAL_CONDITION);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByHasMedicalConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where hasMedicalCondition is not null
        defaultServiceUserShouldBeFound("hasMedicalCondition.specified=true");

        // Get all the serviceUserList where hasMedicalCondition is null
        defaultServiceUserShouldNotBeFound("hasMedicalCondition.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByMedicalConditionSummaryIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where medicalConditionSummary equals to DEFAULT_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldBeFound("medicalConditionSummary.equals=" + DEFAULT_MEDICAL_CONDITION_SUMMARY);

        // Get all the serviceUserList where medicalConditionSummary equals to UPDATED_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldNotBeFound("medicalConditionSummary.equals=" + UPDATED_MEDICAL_CONDITION_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByMedicalConditionSummaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where medicalConditionSummary not equals to DEFAULT_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldNotBeFound("medicalConditionSummary.notEquals=" + DEFAULT_MEDICAL_CONDITION_SUMMARY);

        // Get all the serviceUserList where medicalConditionSummary not equals to UPDATED_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldBeFound("medicalConditionSummary.notEquals=" + UPDATED_MEDICAL_CONDITION_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByMedicalConditionSummaryIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where medicalConditionSummary in DEFAULT_MEDICAL_CONDITION_SUMMARY or UPDATED_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldBeFound("medicalConditionSummary.in=" + DEFAULT_MEDICAL_CONDITION_SUMMARY + "," + UPDATED_MEDICAL_CONDITION_SUMMARY);

        // Get all the serviceUserList where medicalConditionSummary equals to UPDATED_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldNotBeFound("medicalConditionSummary.in=" + UPDATED_MEDICAL_CONDITION_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByMedicalConditionSummaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where medicalConditionSummary is not null
        defaultServiceUserShouldBeFound("medicalConditionSummary.specified=true");

        // Get all the serviceUserList where medicalConditionSummary is null
        defaultServiceUserShouldNotBeFound("medicalConditionSummary.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUsersByMedicalConditionSummaryContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where medicalConditionSummary contains DEFAULT_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldBeFound("medicalConditionSummary.contains=" + DEFAULT_MEDICAL_CONDITION_SUMMARY);

        // Get all the serviceUserList where medicalConditionSummary contains UPDATED_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldNotBeFound("medicalConditionSummary.contains=" + UPDATED_MEDICAL_CONDITION_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByMedicalConditionSummaryNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where medicalConditionSummary does not contain DEFAULT_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldNotBeFound("medicalConditionSummary.doesNotContain=" + DEFAULT_MEDICAL_CONDITION_SUMMARY);

        // Get all the serviceUserList where medicalConditionSummary does not contain UPDATED_MEDICAL_CONDITION_SUMMARY
        defaultServiceUserShouldBeFound("medicalConditionSummary.doesNotContain=" + UPDATED_MEDICAL_CONDITION_SUMMARY);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultServiceUserShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the serviceUserList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastUpdatedDate is not null
        defaultServiceUserShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the serviceUserList where lastUpdatedDate is null
        defaultServiceUserShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultServiceUserShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultServiceUserShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultServiceUserShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where clientId equals to DEFAULT_CLIENT_ID
        defaultServiceUserShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserList where clientId equals to UPDATED_CLIENT_ID
        defaultServiceUserShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where clientId not equals to DEFAULT_CLIENT_ID
        defaultServiceUserShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserList where clientId not equals to UPDATED_CLIENT_ID
        defaultServiceUserShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultServiceUserShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the serviceUserList where clientId equals to UPDATED_CLIENT_ID
        defaultServiceUserShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where clientId is not null
        defaultServiceUserShouldBeFound("clientId.specified=true");

        // Get all the serviceUserList where clientId is null
        defaultServiceUserShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUsersByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultServiceUserShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultServiceUserShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultServiceUserShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultServiceUserShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where clientId is less than DEFAULT_CLIENT_ID
        defaultServiceUserShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserList where clientId is less than UPDATED_CLIENT_ID
        defaultServiceUserShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUsersByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList where clientId is greater than DEFAULT_CLIENT_ID
        defaultServiceUserShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserList where clientId is greater than SMALLER_CLIENT_ID
        defaultServiceUserShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllServiceUsersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        serviceUser.setUser(user);
        serviceUserRepository.saveAndFlush(serviceUser);
        Long userId = user.getId();

        // Get all the serviceUserList where user equals to userId
        defaultServiceUserShouldBeFound("userId.equals=" + userId);

        // Get all the serviceUserList where user equals to userId + 1
        defaultServiceUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceUsersByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);
        Branch branch = BranchResourceIT.createEntity(em);
        em.persist(branch);
        em.flush();
        serviceUser.setBranch(branch);
        serviceUserRepository.saveAndFlush(serviceUser);
        Long branchId = branch.getId();

        // Get all the serviceUserList where branch equals to branchId
        defaultServiceUserShouldBeFound("branchId.equals=" + branchId);

        // Get all the serviceUserList where branch equals to branchId + 1
        defaultServiceUserShouldNotBeFound("branchId.equals=" + (branchId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceUsersByRegisteredByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);
        Employee registeredBy = EmployeeResourceIT.createEntity(em);
        em.persist(registeredBy);
        em.flush();
        serviceUser.setRegisteredBy(registeredBy);
        serviceUserRepository.saveAndFlush(serviceUser);
        Long registeredById = registeredBy.getId();

        // Get all the serviceUserList where registeredBy equals to registeredById
        defaultServiceUserShouldBeFound("registeredById.equals=" + registeredById);

        // Get all the serviceUserList where registeredBy equals to registeredById + 1
        defaultServiceUserShouldNotBeFound("registeredById.equals=" + (registeredById + 1));
    }


    @Test
    @Transactional
    public void getAllServiceUsersByActivatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);
        Employee activatedBy = EmployeeResourceIT.createEntity(em);
        em.persist(activatedBy);
        em.flush();
        serviceUser.setActivatedBy(activatedBy);
        serviceUserRepository.saveAndFlush(serviceUser);
        Long activatedById = activatedBy.getId();

        // Get all the serviceUserList where activatedBy equals to activatedById
        defaultServiceUserShouldBeFound("activatedById.equals=" + activatedById);

        // Get all the serviceUserList where activatedBy equals to activatedById + 1
        defaultServiceUserShouldNotBeFound("activatedById.equals=" + (activatedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceUserShouldBeFound(String filter) throws Exception {
        restServiceUserMockMvc.perform(get("/api/service-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].preferredName").value(hasItem(DEFAULT_PREFERRED_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].serviceUserCode").value(hasItem(DEFAULT_SERVICE_USER_CODE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].lastVisitDate").value(hasItem(sameInstant(DEFAULT_LAST_VISIT_DATE))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].supportType").value(hasItem(DEFAULT_SUPPORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].serviceUserCategory").value(hasItem(DEFAULT_SERVICE_USER_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].vulnerability").value(hasItem(DEFAULT_VULNERABILITY.toString())))
            .andExpect(jsonPath("$.[*].servicePriority").value(hasItem(DEFAULT_SERVICE_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].firstLanguage").value(hasItem(DEFAULT_FIRST_LANGUAGE)))
            .andExpect(jsonPath("$.[*].interpreterRequired").value(hasItem(DEFAULT_INTERPRETER_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].activatedDate").value(hasItem(sameInstant(DEFAULT_ACTIVATED_DATE))))
            .andExpect(jsonPath("$.[*].profilePhotoContentType").value(hasItem(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profilePhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_PHOTO))))
            .andExpect(jsonPath("$.[*].profilePhotoUrl").value(hasItem(DEFAULT_PROFILE_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].lastRecordedHeight").value(hasItem(DEFAULT_LAST_RECORDED_HEIGHT)))
            .andExpect(jsonPath("$.[*].lastRecordedWeight").value(hasItem(DEFAULT_LAST_RECORDED_WEIGHT)))
            .andExpect(jsonPath("$.[*].hasMedicalCondition").value(hasItem(DEFAULT_HAS_MEDICAL_CONDITION.booleanValue())))
            .andExpect(jsonPath("$.[*].medicalConditionSummary").value(hasItem(DEFAULT_MEDICAL_CONDITION_SUMMARY)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));

        // Check, that the count call also returns 1
        restServiceUserMockMvc.perform(get("/api/service-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceUserShouldNotBeFound(String filter) throws Exception {
        restServiceUserMockMvc.perform(get("/api/service-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceUserMockMvc.perform(get("/api/service-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingServiceUser() throws Exception {
        // Get the serviceUser
        restServiceUserMockMvc.perform(get("/api/service-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceUser() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        int databaseSizeBeforeUpdate = serviceUserRepository.findAll().size();

        // Update the serviceUser
        ServiceUser updatedServiceUser = serviceUserRepository.findById(serviceUser.getId()).get();
        // Disconnect from session so that the updates on updatedServiceUser are not directly saved in db
        em.detach(updatedServiceUser);
        updatedServiceUser
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .preferredName(UPDATED_PREFERRED_NAME)
            .email(UPDATED_EMAIL)
            .serviceUserCode(UPDATED_SERVICE_USER_CODE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .lastVisitDate(UPDATED_LAST_VISIT_DATE)
            .startDate(UPDATED_START_DATE)
            .supportType(UPDATED_SUPPORT_TYPE)
            .serviceUserCategory(UPDATED_SERVICE_USER_CATEGORY)
            .vulnerability(UPDATED_VULNERABILITY)
            .servicePriority(UPDATED_SERVICE_PRIORITY)
            .source(UPDATED_SOURCE)
            .status(UPDATED_STATUS)
            .firstLanguage(UPDATED_FIRST_LANGUAGE)
            .interpreterRequired(UPDATED_INTERPRETER_REQUIRED)
            .activatedDate(UPDATED_ACTIVATED_DATE)
            .profilePhoto(UPDATED_PROFILE_PHOTO)
            .profilePhotoContentType(UPDATED_PROFILE_PHOTO_CONTENT_TYPE)
            .profilePhotoUrl(UPDATED_PROFILE_PHOTO_URL)
            .lastRecordedHeight(UPDATED_LAST_RECORDED_HEIGHT)
            .lastRecordedWeight(UPDATED_LAST_RECORDED_WEIGHT)
            .hasMedicalCondition(UPDATED_HAS_MEDICAL_CONDITION)
            .medicalConditionSummary(UPDATED_MEDICAL_CONDITION_SUMMARY)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(updatedServiceUser);

        restServiceUserMockMvc.perform(put("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceUser in the database
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeUpdate);
        ServiceUser testServiceUser = serviceUserList.get(serviceUserList.size() - 1);
        assertThat(testServiceUser.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testServiceUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testServiceUser.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testServiceUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testServiceUser.getPreferredName()).isEqualTo(UPDATED_PREFERRED_NAME);
        assertThat(testServiceUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testServiceUser.getServiceUserCode()).isEqualTo(UPDATED_SERVICE_USER_CODE);
        assertThat(testServiceUser.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testServiceUser.getLastVisitDate()).isEqualTo(UPDATED_LAST_VISIT_DATE);
        assertThat(testServiceUser.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testServiceUser.getSupportType()).isEqualTo(UPDATED_SUPPORT_TYPE);
        assertThat(testServiceUser.getServiceUserCategory()).isEqualTo(UPDATED_SERVICE_USER_CATEGORY);
        assertThat(testServiceUser.getVulnerability()).isEqualTo(UPDATED_VULNERABILITY);
        assertThat(testServiceUser.getServicePriority()).isEqualTo(UPDATED_SERVICE_PRIORITY);
        assertThat(testServiceUser.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testServiceUser.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testServiceUser.getFirstLanguage()).isEqualTo(UPDATED_FIRST_LANGUAGE);
        assertThat(testServiceUser.isInterpreterRequired()).isEqualTo(UPDATED_INTERPRETER_REQUIRED);
        assertThat(testServiceUser.getActivatedDate()).isEqualTo(UPDATED_ACTIVATED_DATE);
        assertThat(testServiceUser.getProfilePhoto()).isEqualTo(UPDATED_PROFILE_PHOTO);
        assertThat(testServiceUser.getProfilePhotoContentType()).isEqualTo(UPDATED_PROFILE_PHOTO_CONTENT_TYPE);
        assertThat(testServiceUser.getProfilePhotoUrl()).isEqualTo(UPDATED_PROFILE_PHOTO_URL);
        assertThat(testServiceUser.getLastRecordedHeight()).isEqualTo(UPDATED_LAST_RECORDED_HEIGHT);
        assertThat(testServiceUser.getLastRecordedWeight()).isEqualTo(UPDATED_LAST_RECORDED_WEIGHT);
        assertThat(testServiceUser.isHasMedicalCondition()).isEqualTo(UPDATED_HAS_MEDICAL_CONDITION);
        assertThat(testServiceUser.getMedicalConditionSummary()).isEqualTo(UPDATED_MEDICAL_CONDITION_SUMMARY);
        assertThat(testServiceUser.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testServiceUser.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceUser() throws Exception {
        int databaseSizeBeforeUpdate = serviceUserRepository.findAll().size();

        // Create the ServiceUser
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceUserMockMvc.perform(put("/api/service-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUser in the database
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceUser() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        int databaseSizeBeforeDelete = serviceUserRepository.findAll().size();

        // Delete the serviceUser
        restServiceUserMockMvc.perform(delete("/api/service-users/{id}", serviceUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
