package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.MedicalContact;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.MedicalContactRepository;
import com.amc.careplanner.service.MedicalContactService;
import com.amc.careplanner.service.dto.MedicalContactDTO;
import com.amc.careplanner.service.mapper.MedicalContactMapper;
import com.amc.careplanner.service.dto.MedicalContactCriteria;
import com.amc.careplanner.service.MedicalContactQueryService;

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

/**
 * Integration tests for the {@link MedicalContactResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedicalContactResourceIT {

    private static final String DEFAULT_DOCTOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR_SURGERY = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_SURGERY = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_PHONE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_VISITED_DOCTOR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_VISITED_DOCTOR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_VISITED_DOCTOR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DISTRICT_NURSE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NURSE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_NURSE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NURSE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CARE_MANAGER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CARE_MANAGER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CARE_MANAGER_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CARE_MANAGER_PHONE = "BBBBBBBBBB";

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
    private MedicalContactRepository medicalContactRepository;

    @Autowired
    private MedicalContactMapper medicalContactMapper;

    @Autowired
    private MedicalContactService medicalContactService;

    @Autowired
    private MedicalContactQueryService medicalContactQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicalContactMockMvc;

    private MedicalContact medicalContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalContact createEntity(EntityManager em) {
        MedicalContact medicalContact = new MedicalContact()
            .doctorName(DEFAULT_DOCTOR_NAME)
            .doctorSurgery(DEFAULT_DOCTOR_SURGERY)
            .doctorAddress(DEFAULT_DOCTOR_ADDRESS)
            .doctorPhone(DEFAULT_DOCTOR_PHONE)
            .lastVisitedDoctor(DEFAULT_LAST_VISITED_DOCTOR)
            .districtNurseName(DEFAULT_DISTRICT_NURSE_NAME)
            .districtNursePhone(DEFAULT_DISTRICT_NURSE_PHONE)
            .careManagerName(DEFAULT_CARE_MANAGER_NAME)
            .careManagerPhone(DEFAULT_CARE_MANAGER_PHONE)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return medicalContact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalContact createUpdatedEntity(EntityManager em) {
        MedicalContact medicalContact = new MedicalContact()
            .doctorName(UPDATED_DOCTOR_NAME)
            .doctorSurgery(UPDATED_DOCTOR_SURGERY)
            .doctorAddress(UPDATED_DOCTOR_ADDRESS)
            .doctorPhone(UPDATED_DOCTOR_PHONE)
            .lastVisitedDoctor(UPDATED_LAST_VISITED_DOCTOR)
            .districtNurseName(UPDATED_DISTRICT_NURSE_NAME)
            .districtNursePhone(UPDATED_DISTRICT_NURSE_PHONE)
            .careManagerName(UPDATED_CARE_MANAGER_NAME)
            .careManagerPhone(UPDATED_CARE_MANAGER_PHONE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return medicalContact;
    }

    @BeforeEach
    public void initTest() {
        medicalContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalContact() throws Exception {
        int databaseSizeBeforeCreate = medicalContactRepository.findAll().size();
        // Create the MedicalContact
        MedicalContactDTO medicalContactDTO = medicalContactMapper.toDto(medicalContact);
        restMedicalContactMockMvc.perform(post("/api/medical-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalContactDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicalContact in the database
        List<MedicalContact> medicalContactList = medicalContactRepository.findAll();
        assertThat(medicalContactList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalContact testMedicalContact = medicalContactList.get(medicalContactList.size() - 1);
        assertThat(testMedicalContact.getDoctorName()).isEqualTo(DEFAULT_DOCTOR_NAME);
        assertThat(testMedicalContact.getDoctorSurgery()).isEqualTo(DEFAULT_DOCTOR_SURGERY);
        assertThat(testMedicalContact.getDoctorAddress()).isEqualTo(DEFAULT_DOCTOR_ADDRESS);
        assertThat(testMedicalContact.getDoctorPhone()).isEqualTo(DEFAULT_DOCTOR_PHONE);
        assertThat(testMedicalContact.getLastVisitedDoctor()).isEqualTo(DEFAULT_LAST_VISITED_DOCTOR);
        assertThat(testMedicalContact.getDistrictNurseName()).isEqualTo(DEFAULT_DISTRICT_NURSE_NAME);
        assertThat(testMedicalContact.getDistrictNursePhone()).isEqualTo(DEFAULT_DISTRICT_NURSE_PHONE);
        assertThat(testMedicalContact.getCareManagerName()).isEqualTo(DEFAULT_CARE_MANAGER_NAME);
        assertThat(testMedicalContact.getCareManagerPhone()).isEqualTo(DEFAULT_CARE_MANAGER_PHONE);
        assertThat(testMedicalContact.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMedicalContact.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testMedicalContact.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testMedicalContact.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createMedicalContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalContactRepository.findAll().size();

        // Create the MedicalContact with an existing ID
        medicalContact.setId(1L);
        MedicalContactDTO medicalContactDTO = medicalContactMapper.toDto(medicalContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalContactMockMvc.perform(post("/api/medical-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalContact in the database
        List<MedicalContact> medicalContactList = medicalContactRepository.findAll();
        assertThat(medicalContactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalContactRepository.findAll().size();
        // set the field null
        medicalContact.setClientId(null);

        // Create the MedicalContact, which fails.
        MedicalContactDTO medicalContactDTO = medicalContactMapper.toDto(medicalContact);


        restMedicalContactMockMvc.perform(post("/api/medical-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalContactDTO)))
            .andExpect(status().isBadRequest());

        List<MedicalContact> medicalContactList = medicalContactRepository.findAll();
        assertThat(medicalContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicalContacts() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList
        restMedicalContactMockMvc.perform(get("/api/medical-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].doctorName").value(hasItem(DEFAULT_DOCTOR_NAME)))
            .andExpect(jsonPath("$.[*].doctorSurgery").value(hasItem(DEFAULT_DOCTOR_SURGERY)))
            .andExpect(jsonPath("$.[*].doctorAddress").value(hasItem(DEFAULT_DOCTOR_ADDRESS)))
            .andExpect(jsonPath("$.[*].doctorPhone").value(hasItem(DEFAULT_DOCTOR_PHONE)))
            .andExpect(jsonPath("$.[*].lastVisitedDoctor").value(hasItem(sameInstant(DEFAULT_LAST_VISITED_DOCTOR))))
            .andExpect(jsonPath("$.[*].districtNurseName").value(hasItem(DEFAULT_DISTRICT_NURSE_NAME)))
            .andExpect(jsonPath("$.[*].districtNursePhone").value(hasItem(DEFAULT_DISTRICT_NURSE_PHONE)))
            .andExpect(jsonPath("$.[*].careManagerName").value(hasItem(DEFAULT_CARE_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].careManagerPhone").value(hasItem(DEFAULT_CARE_MANAGER_PHONE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMedicalContact() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get the medicalContact
        restMedicalContactMockMvc.perform(get("/api/medical-contacts/{id}", medicalContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicalContact.getId().intValue()))
            .andExpect(jsonPath("$.doctorName").value(DEFAULT_DOCTOR_NAME))
            .andExpect(jsonPath("$.doctorSurgery").value(DEFAULT_DOCTOR_SURGERY))
            .andExpect(jsonPath("$.doctorAddress").value(DEFAULT_DOCTOR_ADDRESS))
            .andExpect(jsonPath("$.doctorPhone").value(DEFAULT_DOCTOR_PHONE))
            .andExpect(jsonPath("$.lastVisitedDoctor").value(sameInstant(DEFAULT_LAST_VISITED_DOCTOR)))
            .andExpect(jsonPath("$.districtNurseName").value(DEFAULT_DISTRICT_NURSE_NAME))
            .andExpect(jsonPath("$.districtNursePhone").value(DEFAULT_DISTRICT_NURSE_PHONE))
            .andExpect(jsonPath("$.careManagerName").value(DEFAULT_CARE_MANAGER_NAME))
            .andExpect(jsonPath("$.careManagerPhone").value(DEFAULT_CARE_MANAGER_PHONE))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getMedicalContactsByIdFiltering() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        Long id = medicalContact.getId();

        defaultMedicalContactShouldBeFound("id.equals=" + id);
        defaultMedicalContactShouldNotBeFound("id.notEquals=" + id);

        defaultMedicalContactShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMedicalContactShouldNotBeFound("id.greaterThan=" + id);

        defaultMedicalContactShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMedicalContactShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorNameIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorName equals to DEFAULT_DOCTOR_NAME
        defaultMedicalContactShouldBeFound("doctorName.equals=" + DEFAULT_DOCTOR_NAME);

        // Get all the medicalContactList where doctorName equals to UPDATED_DOCTOR_NAME
        defaultMedicalContactShouldNotBeFound("doctorName.equals=" + UPDATED_DOCTOR_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorName not equals to DEFAULT_DOCTOR_NAME
        defaultMedicalContactShouldNotBeFound("doctorName.notEquals=" + DEFAULT_DOCTOR_NAME);

        // Get all the medicalContactList where doctorName not equals to UPDATED_DOCTOR_NAME
        defaultMedicalContactShouldBeFound("doctorName.notEquals=" + UPDATED_DOCTOR_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorNameIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorName in DEFAULT_DOCTOR_NAME or UPDATED_DOCTOR_NAME
        defaultMedicalContactShouldBeFound("doctorName.in=" + DEFAULT_DOCTOR_NAME + "," + UPDATED_DOCTOR_NAME);

        // Get all the medicalContactList where doctorName equals to UPDATED_DOCTOR_NAME
        defaultMedicalContactShouldNotBeFound("doctorName.in=" + UPDATED_DOCTOR_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorName is not null
        defaultMedicalContactShouldBeFound("doctorName.specified=true");

        // Get all the medicalContactList where doctorName is null
        defaultMedicalContactShouldNotBeFound("doctorName.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalContactsByDoctorNameContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorName contains DEFAULT_DOCTOR_NAME
        defaultMedicalContactShouldBeFound("doctorName.contains=" + DEFAULT_DOCTOR_NAME);

        // Get all the medicalContactList where doctorName contains UPDATED_DOCTOR_NAME
        defaultMedicalContactShouldNotBeFound("doctorName.contains=" + UPDATED_DOCTOR_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorNameNotContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorName does not contain DEFAULT_DOCTOR_NAME
        defaultMedicalContactShouldNotBeFound("doctorName.doesNotContain=" + DEFAULT_DOCTOR_NAME);

        // Get all the medicalContactList where doctorName does not contain UPDATED_DOCTOR_NAME
        defaultMedicalContactShouldBeFound("doctorName.doesNotContain=" + UPDATED_DOCTOR_NAME);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorSurgeryIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorSurgery equals to DEFAULT_DOCTOR_SURGERY
        defaultMedicalContactShouldBeFound("doctorSurgery.equals=" + DEFAULT_DOCTOR_SURGERY);

        // Get all the medicalContactList where doctorSurgery equals to UPDATED_DOCTOR_SURGERY
        defaultMedicalContactShouldNotBeFound("doctorSurgery.equals=" + UPDATED_DOCTOR_SURGERY);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorSurgeryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorSurgery not equals to DEFAULT_DOCTOR_SURGERY
        defaultMedicalContactShouldNotBeFound("doctorSurgery.notEquals=" + DEFAULT_DOCTOR_SURGERY);

        // Get all the medicalContactList where doctorSurgery not equals to UPDATED_DOCTOR_SURGERY
        defaultMedicalContactShouldBeFound("doctorSurgery.notEquals=" + UPDATED_DOCTOR_SURGERY);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorSurgeryIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorSurgery in DEFAULT_DOCTOR_SURGERY or UPDATED_DOCTOR_SURGERY
        defaultMedicalContactShouldBeFound("doctorSurgery.in=" + DEFAULT_DOCTOR_SURGERY + "," + UPDATED_DOCTOR_SURGERY);

        // Get all the medicalContactList where doctorSurgery equals to UPDATED_DOCTOR_SURGERY
        defaultMedicalContactShouldNotBeFound("doctorSurgery.in=" + UPDATED_DOCTOR_SURGERY);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorSurgeryIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorSurgery is not null
        defaultMedicalContactShouldBeFound("doctorSurgery.specified=true");

        // Get all the medicalContactList where doctorSurgery is null
        defaultMedicalContactShouldNotBeFound("doctorSurgery.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalContactsByDoctorSurgeryContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorSurgery contains DEFAULT_DOCTOR_SURGERY
        defaultMedicalContactShouldBeFound("doctorSurgery.contains=" + DEFAULT_DOCTOR_SURGERY);

        // Get all the medicalContactList where doctorSurgery contains UPDATED_DOCTOR_SURGERY
        defaultMedicalContactShouldNotBeFound("doctorSurgery.contains=" + UPDATED_DOCTOR_SURGERY);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorSurgeryNotContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorSurgery does not contain DEFAULT_DOCTOR_SURGERY
        defaultMedicalContactShouldNotBeFound("doctorSurgery.doesNotContain=" + DEFAULT_DOCTOR_SURGERY);

        // Get all the medicalContactList where doctorSurgery does not contain UPDATED_DOCTOR_SURGERY
        defaultMedicalContactShouldBeFound("doctorSurgery.doesNotContain=" + UPDATED_DOCTOR_SURGERY);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorAddress equals to DEFAULT_DOCTOR_ADDRESS
        defaultMedicalContactShouldBeFound("doctorAddress.equals=" + DEFAULT_DOCTOR_ADDRESS);

        // Get all the medicalContactList where doctorAddress equals to UPDATED_DOCTOR_ADDRESS
        defaultMedicalContactShouldNotBeFound("doctorAddress.equals=" + UPDATED_DOCTOR_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorAddress not equals to DEFAULT_DOCTOR_ADDRESS
        defaultMedicalContactShouldNotBeFound("doctorAddress.notEquals=" + DEFAULT_DOCTOR_ADDRESS);

        // Get all the medicalContactList where doctorAddress not equals to UPDATED_DOCTOR_ADDRESS
        defaultMedicalContactShouldBeFound("doctorAddress.notEquals=" + UPDATED_DOCTOR_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorAddressIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorAddress in DEFAULT_DOCTOR_ADDRESS or UPDATED_DOCTOR_ADDRESS
        defaultMedicalContactShouldBeFound("doctorAddress.in=" + DEFAULT_DOCTOR_ADDRESS + "," + UPDATED_DOCTOR_ADDRESS);

        // Get all the medicalContactList where doctorAddress equals to UPDATED_DOCTOR_ADDRESS
        defaultMedicalContactShouldNotBeFound("doctorAddress.in=" + UPDATED_DOCTOR_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorAddress is not null
        defaultMedicalContactShouldBeFound("doctorAddress.specified=true");

        // Get all the medicalContactList where doctorAddress is null
        defaultMedicalContactShouldNotBeFound("doctorAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalContactsByDoctorAddressContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorAddress contains DEFAULT_DOCTOR_ADDRESS
        defaultMedicalContactShouldBeFound("doctorAddress.contains=" + DEFAULT_DOCTOR_ADDRESS);

        // Get all the medicalContactList where doctorAddress contains UPDATED_DOCTOR_ADDRESS
        defaultMedicalContactShouldNotBeFound("doctorAddress.contains=" + UPDATED_DOCTOR_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorAddressNotContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorAddress does not contain DEFAULT_DOCTOR_ADDRESS
        defaultMedicalContactShouldNotBeFound("doctorAddress.doesNotContain=" + DEFAULT_DOCTOR_ADDRESS);

        // Get all the medicalContactList where doctorAddress does not contain UPDATED_DOCTOR_ADDRESS
        defaultMedicalContactShouldBeFound("doctorAddress.doesNotContain=" + UPDATED_DOCTOR_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorPhone equals to DEFAULT_DOCTOR_PHONE
        defaultMedicalContactShouldBeFound("doctorPhone.equals=" + DEFAULT_DOCTOR_PHONE);

        // Get all the medicalContactList where doctorPhone equals to UPDATED_DOCTOR_PHONE
        defaultMedicalContactShouldNotBeFound("doctorPhone.equals=" + UPDATED_DOCTOR_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorPhone not equals to DEFAULT_DOCTOR_PHONE
        defaultMedicalContactShouldNotBeFound("doctorPhone.notEquals=" + DEFAULT_DOCTOR_PHONE);

        // Get all the medicalContactList where doctorPhone not equals to UPDATED_DOCTOR_PHONE
        defaultMedicalContactShouldBeFound("doctorPhone.notEquals=" + UPDATED_DOCTOR_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorPhone in DEFAULT_DOCTOR_PHONE or UPDATED_DOCTOR_PHONE
        defaultMedicalContactShouldBeFound("doctorPhone.in=" + DEFAULT_DOCTOR_PHONE + "," + UPDATED_DOCTOR_PHONE);

        // Get all the medicalContactList where doctorPhone equals to UPDATED_DOCTOR_PHONE
        defaultMedicalContactShouldNotBeFound("doctorPhone.in=" + UPDATED_DOCTOR_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorPhone is not null
        defaultMedicalContactShouldBeFound("doctorPhone.specified=true");

        // Get all the medicalContactList where doctorPhone is null
        defaultMedicalContactShouldNotBeFound("doctorPhone.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalContactsByDoctorPhoneContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorPhone contains DEFAULT_DOCTOR_PHONE
        defaultMedicalContactShouldBeFound("doctorPhone.contains=" + DEFAULT_DOCTOR_PHONE);

        // Get all the medicalContactList where doctorPhone contains UPDATED_DOCTOR_PHONE
        defaultMedicalContactShouldNotBeFound("doctorPhone.contains=" + UPDATED_DOCTOR_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDoctorPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where doctorPhone does not contain DEFAULT_DOCTOR_PHONE
        defaultMedicalContactShouldNotBeFound("doctorPhone.doesNotContain=" + DEFAULT_DOCTOR_PHONE);

        // Get all the medicalContactList where doctorPhone does not contain UPDATED_DOCTOR_PHONE
        defaultMedicalContactShouldBeFound("doctorPhone.doesNotContain=" + UPDATED_DOCTOR_PHONE);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByLastVisitedDoctorIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastVisitedDoctor equals to DEFAULT_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldBeFound("lastVisitedDoctor.equals=" + DEFAULT_LAST_VISITED_DOCTOR);

        // Get all the medicalContactList where lastVisitedDoctor equals to UPDATED_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldNotBeFound("lastVisitedDoctor.equals=" + UPDATED_LAST_VISITED_DOCTOR);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastVisitedDoctorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastVisitedDoctor not equals to DEFAULT_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldNotBeFound("lastVisitedDoctor.notEquals=" + DEFAULT_LAST_VISITED_DOCTOR);

        // Get all the medicalContactList where lastVisitedDoctor not equals to UPDATED_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldBeFound("lastVisitedDoctor.notEquals=" + UPDATED_LAST_VISITED_DOCTOR);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastVisitedDoctorIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastVisitedDoctor in DEFAULT_LAST_VISITED_DOCTOR or UPDATED_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldBeFound("lastVisitedDoctor.in=" + DEFAULT_LAST_VISITED_DOCTOR + "," + UPDATED_LAST_VISITED_DOCTOR);

        // Get all the medicalContactList where lastVisitedDoctor equals to UPDATED_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldNotBeFound("lastVisitedDoctor.in=" + UPDATED_LAST_VISITED_DOCTOR);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastVisitedDoctorIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastVisitedDoctor is not null
        defaultMedicalContactShouldBeFound("lastVisitedDoctor.specified=true");

        // Get all the medicalContactList where lastVisitedDoctor is null
        defaultMedicalContactShouldNotBeFound("lastVisitedDoctor.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastVisitedDoctorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastVisitedDoctor is greater than or equal to DEFAULT_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldBeFound("lastVisitedDoctor.greaterThanOrEqual=" + DEFAULT_LAST_VISITED_DOCTOR);

        // Get all the medicalContactList where lastVisitedDoctor is greater than or equal to UPDATED_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldNotBeFound("lastVisitedDoctor.greaterThanOrEqual=" + UPDATED_LAST_VISITED_DOCTOR);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastVisitedDoctorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastVisitedDoctor is less than or equal to DEFAULT_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldBeFound("lastVisitedDoctor.lessThanOrEqual=" + DEFAULT_LAST_VISITED_DOCTOR);

        // Get all the medicalContactList where lastVisitedDoctor is less than or equal to SMALLER_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldNotBeFound("lastVisitedDoctor.lessThanOrEqual=" + SMALLER_LAST_VISITED_DOCTOR);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastVisitedDoctorIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastVisitedDoctor is less than DEFAULT_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldNotBeFound("lastVisitedDoctor.lessThan=" + DEFAULT_LAST_VISITED_DOCTOR);

        // Get all the medicalContactList where lastVisitedDoctor is less than UPDATED_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldBeFound("lastVisitedDoctor.lessThan=" + UPDATED_LAST_VISITED_DOCTOR);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastVisitedDoctorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastVisitedDoctor is greater than DEFAULT_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldNotBeFound("lastVisitedDoctor.greaterThan=" + DEFAULT_LAST_VISITED_DOCTOR);

        // Get all the medicalContactList where lastVisitedDoctor is greater than SMALLER_LAST_VISITED_DOCTOR
        defaultMedicalContactShouldBeFound("lastVisitedDoctor.greaterThan=" + SMALLER_LAST_VISITED_DOCTOR);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNurseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNurseName equals to DEFAULT_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldBeFound("districtNurseName.equals=" + DEFAULT_DISTRICT_NURSE_NAME);

        // Get all the medicalContactList where districtNurseName equals to UPDATED_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldNotBeFound("districtNurseName.equals=" + UPDATED_DISTRICT_NURSE_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNurseNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNurseName not equals to DEFAULT_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldNotBeFound("districtNurseName.notEquals=" + DEFAULT_DISTRICT_NURSE_NAME);

        // Get all the medicalContactList where districtNurseName not equals to UPDATED_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldBeFound("districtNurseName.notEquals=" + UPDATED_DISTRICT_NURSE_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNurseNameIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNurseName in DEFAULT_DISTRICT_NURSE_NAME or UPDATED_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldBeFound("districtNurseName.in=" + DEFAULT_DISTRICT_NURSE_NAME + "," + UPDATED_DISTRICT_NURSE_NAME);

        // Get all the medicalContactList where districtNurseName equals to UPDATED_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldNotBeFound("districtNurseName.in=" + UPDATED_DISTRICT_NURSE_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNurseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNurseName is not null
        defaultMedicalContactShouldBeFound("districtNurseName.specified=true");

        // Get all the medicalContactList where districtNurseName is null
        defaultMedicalContactShouldNotBeFound("districtNurseName.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNurseNameContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNurseName contains DEFAULT_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldBeFound("districtNurseName.contains=" + DEFAULT_DISTRICT_NURSE_NAME);

        // Get all the medicalContactList where districtNurseName contains UPDATED_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldNotBeFound("districtNurseName.contains=" + UPDATED_DISTRICT_NURSE_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNurseNameNotContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNurseName does not contain DEFAULT_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldNotBeFound("districtNurseName.doesNotContain=" + DEFAULT_DISTRICT_NURSE_NAME);

        // Get all the medicalContactList where districtNurseName does not contain UPDATED_DISTRICT_NURSE_NAME
        defaultMedicalContactShouldBeFound("districtNurseName.doesNotContain=" + UPDATED_DISTRICT_NURSE_NAME);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNursePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNursePhone equals to DEFAULT_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldBeFound("districtNursePhone.equals=" + DEFAULT_DISTRICT_NURSE_PHONE);

        // Get all the medicalContactList where districtNursePhone equals to UPDATED_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldNotBeFound("districtNursePhone.equals=" + UPDATED_DISTRICT_NURSE_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNursePhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNursePhone not equals to DEFAULT_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldNotBeFound("districtNursePhone.notEquals=" + DEFAULT_DISTRICT_NURSE_PHONE);

        // Get all the medicalContactList where districtNursePhone not equals to UPDATED_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldBeFound("districtNursePhone.notEquals=" + UPDATED_DISTRICT_NURSE_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNursePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNursePhone in DEFAULT_DISTRICT_NURSE_PHONE or UPDATED_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldBeFound("districtNursePhone.in=" + DEFAULT_DISTRICT_NURSE_PHONE + "," + UPDATED_DISTRICT_NURSE_PHONE);

        // Get all the medicalContactList where districtNursePhone equals to UPDATED_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldNotBeFound("districtNursePhone.in=" + UPDATED_DISTRICT_NURSE_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNursePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNursePhone is not null
        defaultMedicalContactShouldBeFound("districtNursePhone.specified=true");

        // Get all the medicalContactList where districtNursePhone is null
        defaultMedicalContactShouldNotBeFound("districtNursePhone.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNursePhoneContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNursePhone contains DEFAULT_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldBeFound("districtNursePhone.contains=" + DEFAULT_DISTRICT_NURSE_PHONE);

        // Get all the medicalContactList where districtNursePhone contains UPDATED_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldNotBeFound("districtNursePhone.contains=" + UPDATED_DISTRICT_NURSE_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByDistrictNursePhoneNotContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where districtNursePhone does not contain DEFAULT_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldNotBeFound("districtNursePhone.doesNotContain=" + DEFAULT_DISTRICT_NURSE_PHONE);

        // Get all the medicalContactList where districtNursePhone does not contain UPDATED_DISTRICT_NURSE_PHONE
        defaultMedicalContactShouldBeFound("districtNursePhone.doesNotContain=" + UPDATED_DISTRICT_NURSE_PHONE);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerName equals to DEFAULT_CARE_MANAGER_NAME
        defaultMedicalContactShouldBeFound("careManagerName.equals=" + DEFAULT_CARE_MANAGER_NAME);

        // Get all the medicalContactList where careManagerName equals to UPDATED_CARE_MANAGER_NAME
        defaultMedicalContactShouldNotBeFound("careManagerName.equals=" + UPDATED_CARE_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerName not equals to DEFAULT_CARE_MANAGER_NAME
        defaultMedicalContactShouldNotBeFound("careManagerName.notEquals=" + DEFAULT_CARE_MANAGER_NAME);

        // Get all the medicalContactList where careManagerName not equals to UPDATED_CARE_MANAGER_NAME
        defaultMedicalContactShouldBeFound("careManagerName.notEquals=" + UPDATED_CARE_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerNameIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerName in DEFAULT_CARE_MANAGER_NAME or UPDATED_CARE_MANAGER_NAME
        defaultMedicalContactShouldBeFound("careManagerName.in=" + DEFAULT_CARE_MANAGER_NAME + "," + UPDATED_CARE_MANAGER_NAME);

        // Get all the medicalContactList where careManagerName equals to UPDATED_CARE_MANAGER_NAME
        defaultMedicalContactShouldNotBeFound("careManagerName.in=" + UPDATED_CARE_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerName is not null
        defaultMedicalContactShouldBeFound("careManagerName.specified=true");

        // Get all the medicalContactList where careManagerName is null
        defaultMedicalContactShouldNotBeFound("careManagerName.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerNameContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerName contains DEFAULT_CARE_MANAGER_NAME
        defaultMedicalContactShouldBeFound("careManagerName.contains=" + DEFAULT_CARE_MANAGER_NAME);

        // Get all the medicalContactList where careManagerName contains UPDATED_CARE_MANAGER_NAME
        defaultMedicalContactShouldNotBeFound("careManagerName.contains=" + UPDATED_CARE_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerNameNotContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerName does not contain DEFAULT_CARE_MANAGER_NAME
        defaultMedicalContactShouldNotBeFound("careManagerName.doesNotContain=" + DEFAULT_CARE_MANAGER_NAME);

        // Get all the medicalContactList where careManagerName does not contain UPDATED_CARE_MANAGER_NAME
        defaultMedicalContactShouldBeFound("careManagerName.doesNotContain=" + UPDATED_CARE_MANAGER_NAME);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerPhone equals to DEFAULT_CARE_MANAGER_PHONE
        defaultMedicalContactShouldBeFound("careManagerPhone.equals=" + DEFAULT_CARE_MANAGER_PHONE);

        // Get all the medicalContactList where careManagerPhone equals to UPDATED_CARE_MANAGER_PHONE
        defaultMedicalContactShouldNotBeFound("careManagerPhone.equals=" + UPDATED_CARE_MANAGER_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerPhone not equals to DEFAULT_CARE_MANAGER_PHONE
        defaultMedicalContactShouldNotBeFound("careManagerPhone.notEquals=" + DEFAULT_CARE_MANAGER_PHONE);

        // Get all the medicalContactList where careManagerPhone not equals to UPDATED_CARE_MANAGER_PHONE
        defaultMedicalContactShouldBeFound("careManagerPhone.notEquals=" + UPDATED_CARE_MANAGER_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerPhone in DEFAULT_CARE_MANAGER_PHONE or UPDATED_CARE_MANAGER_PHONE
        defaultMedicalContactShouldBeFound("careManagerPhone.in=" + DEFAULT_CARE_MANAGER_PHONE + "," + UPDATED_CARE_MANAGER_PHONE);

        // Get all the medicalContactList where careManagerPhone equals to UPDATED_CARE_MANAGER_PHONE
        defaultMedicalContactShouldNotBeFound("careManagerPhone.in=" + UPDATED_CARE_MANAGER_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerPhone is not null
        defaultMedicalContactShouldBeFound("careManagerPhone.specified=true");

        // Get all the medicalContactList where careManagerPhone is null
        defaultMedicalContactShouldNotBeFound("careManagerPhone.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerPhoneContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerPhone contains DEFAULT_CARE_MANAGER_PHONE
        defaultMedicalContactShouldBeFound("careManagerPhone.contains=" + DEFAULT_CARE_MANAGER_PHONE);

        // Get all the medicalContactList where careManagerPhone contains UPDATED_CARE_MANAGER_PHONE
        defaultMedicalContactShouldNotBeFound("careManagerPhone.contains=" + UPDATED_CARE_MANAGER_PHONE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCareManagerPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where careManagerPhone does not contain DEFAULT_CARE_MANAGER_PHONE
        defaultMedicalContactShouldNotBeFound("careManagerPhone.doesNotContain=" + DEFAULT_CARE_MANAGER_PHONE);

        // Get all the medicalContactList where careManagerPhone does not contain UPDATED_CARE_MANAGER_PHONE
        defaultMedicalContactShouldBeFound("careManagerPhone.doesNotContain=" + UPDATED_CARE_MANAGER_PHONE);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where createdDate equals to DEFAULT_CREATED_DATE
        defaultMedicalContactShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the medicalContactList where createdDate equals to UPDATED_CREATED_DATE
        defaultMedicalContactShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultMedicalContactShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the medicalContactList where createdDate not equals to UPDATED_CREATED_DATE
        defaultMedicalContactShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultMedicalContactShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the medicalContactList where createdDate equals to UPDATED_CREATED_DATE
        defaultMedicalContactShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where createdDate is not null
        defaultMedicalContactShouldBeFound("createdDate.specified=true");

        // Get all the medicalContactList where createdDate is null
        defaultMedicalContactShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultMedicalContactShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the medicalContactList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultMedicalContactShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultMedicalContactShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the medicalContactList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultMedicalContactShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where createdDate is less than DEFAULT_CREATED_DATE
        defaultMedicalContactShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the medicalContactList where createdDate is less than UPDATED_CREATED_DATE
        defaultMedicalContactShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultMedicalContactShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the medicalContactList where createdDate is greater than SMALLER_CREATED_DATE
        defaultMedicalContactShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultMedicalContactShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the medicalContactList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultMedicalContactShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultMedicalContactShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the medicalContactList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultMedicalContactShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultMedicalContactShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the medicalContactList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultMedicalContactShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastUpdatedDate is not null
        defaultMedicalContactShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the medicalContactList where lastUpdatedDate is null
        defaultMedicalContactShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultMedicalContactShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the medicalContactList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultMedicalContactShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultMedicalContactShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the medicalContactList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultMedicalContactShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultMedicalContactShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the medicalContactList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultMedicalContactShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultMedicalContactShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the medicalContactList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultMedicalContactShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where clientId equals to DEFAULT_CLIENT_ID
        defaultMedicalContactShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the medicalContactList where clientId equals to UPDATED_CLIENT_ID
        defaultMedicalContactShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where clientId not equals to DEFAULT_CLIENT_ID
        defaultMedicalContactShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the medicalContactList where clientId not equals to UPDATED_CLIENT_ID
        defaultMedicalContactShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultMedicalContactShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the medicalContactList where clientId equals to UPDATED_CLIENT_ID
        defaultMedicalContactShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where clientId is not null
        defaultMedicalContactShouldBeFound("clientId.specified=true");

        // Get all the medicalContactList where clientId is null
        defaultMedicalContactShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultMedicalContactShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the medicalContactList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultMedicalContactShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultMedicalContactShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the medicalContactList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultMedicalContactShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where clientId is less than DEFAULT_CLIENT_ID
        defaultMedicalContactShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the medicalContactList where clientId is less than UPDATED_CLIENT_ID
        defaultMedicalContactShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where clientId is greater than DEFAULT_CLIENT_ID
        defaultMedicalContactShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the medicalContactList where clientId is greater than SMALLER_CLIENT_ID
        defaultMedicalContactShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllMedicalContactsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultMedicalContactShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the medicalContactList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultMedicalContactShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultMedicalContactShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the medicalContactList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultMedicalContactShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultMedicalContactShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the medicalContactList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultMedicalContactShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        // Get all the medicalContactList where hasExtraData is not null
        defaultMedicalContactShouldBeFound("hasExtraData.specified=true");

        // Get all the medicalContactList where hasExtraData is null
        defaultMedicalContactShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalContactsByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        medicalContact.setServiceUser(serviceUser);
        medicalContactRepository.saveAndFlush(medicalContact);
        Long serviceUserId = serviceUser.getId();

        // Get all the medicalContactList where serviceUser equals to serviceUserId
        defaultMedicalContactShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the medicalContactList where serviceUser equals to serviceUserId + 1
        defaultMedicalContactShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMedicalContactShouldBeFound(String filter) throws Exception {
        restMedicalContactMockMvc.perform(get("/api/medical-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].doctorName").value(hasItem(DEFAULT_DOCTOR_NAME)))
            .andExpect(jsonPath("$.[*].doctorSurgery").value(hasItem(DEFAULT_DOCTOR_SURGERY)))
            .andExpect(jsonPath("$.[*].doctorAddress").value(hasItem(DEFAULT_DOCTOR_ADDRESS)))
            .andExpect(jsonPath("$.[*].doctorPhone").value(hasItem(DEFAULT_DOCTOR_PHONE)))
            .andExpect(jsonPath("$.[*].lastVisitedDoctor").value(hasItem(sameInstant(DEFAULT_LAST_VISITED_DOCTOR))))
            .andExpect(jsonPath("$.[*].districtNurseName").value(hasItem(DEFAULT_DISTRICT_NURSE_NAME)))
            .andExpect(jsonPath("$.[*].districtNursePhone").value(hasItem(DEFAULT_DISTRICT_NURSE_PHONE)))
            .andExpect(jsonPath("$.[*].careManagerName").value(hasItem(DEFAULT_CARE_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].careManagerPhone").value(hasItem(DEFAULT_CARE_MANAGER_PHONE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restMedicalContactMockMvc.perform(get("/api/medical-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMedicalContactShouldNotBeFound(String filter) throws Exception {
        restMedicalContactMockMvc.perform(get("/api/medical-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMedicalContactMockMvc.perform(get("/api/medical-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalContact() throws Exception {
        // Get the medicalContact
        restMedicalContactMockMvc.perform(get("/api/medical-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalContact() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        int databaseSizeBeforeUpdate = medicalContactRepository.findAll().size();

        // Update the medicalContact
        MedicalContact updatedMedicalContact = medicalContactRepository.findById(medicalContact.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalContact are not directly saved in db
        em.detach(updatedMedicalContact);
        updatedMedicalContact
            .doctorName(UPDATED_DOCTOR_NAME)
            .doctorSurgery(UPDATED_DOCTOR_SURGERY)
            .doctorAddress(UPDATED_DOCTOR_ADDRESS)
            .doctorPhone(UPDATED_DOCTOR_PHONE)
            .lastVisitedDoctor(UPDATED_LAST_VISITED_DOCTOR)
            .districtNurseName(UPDATED_DISTRICT_NURSE_NAME)
            .districtNursePhone(UPDATED_DISTRICT_NURSE_PHONE)
            .careManagerName(UPDATED_CARE_MANAGER_NAME)
            .careManagerPhone(UPDATED_CARE_MANAGER_PHONE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        MedicalContactDTO medicalContactDTO = medicalContactMapper.toDto(updatedMedicalContact);

        restMedicalContactMockMvc.perform(put("/api/medical-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalContactDTO)))
            .andExpect(status().isOk());

        // Validate the MedicalContact in the database
        List<MedicalContact> medicalContactList = medicalContactRepository.findAll();
        assertThat(medicalContactList).hasSize(databaseSizeBeforeUpdate);
        MedicalContact testMedicalContact = medicalContactList.get(medicalContactList.size() - 1);
        assertThat(testMedicalContact.getDoctorName()).isEqualTo(UPDATED_DOCTOR_NAME);
        assertThat(testMedicalContact.getDoctorSurgery()).isEqualTo(UPDATED_DOCTOR_SURGERY);
        assertThat(testMedicalContact.getDoctorAddress()).isEqualTo(UPDATED_DOCTOR_ADDRESS);
        assertThat(testMedicalContact.getDoctorPhone()).isEqualTo(UPDATED_DOCTOR_PHONE);
        assertThat(testMedicalContact.getLastVisitedDoctor()).isEqualTo(UPDATED_LAST_VISITED_DOCTOR);
        assertThat(testMedicalContact.getDistrictNurseName()).isEqualTo(UPDATED_DISTRICT_NURSE_NAME);
        assertThat(testMedicalContact.getDistrictNursePhone()).isEqualTo(UPDATED_DISTRICT_NURSE_PHONE);
        assertThat(testMedicalContact.getCareManagerName()).isEqualTo(UPDATED_CARE_MANAGER_NAME);
        assertThat(testMedicalContact.getCareManagerPhone()).isEqualTo(UPDATED_CARE_MANAGER_PHONE);
        assertThat(testMedicalContact.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMedicalContact.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testMedicalContact.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testMedicalContact.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalContact() throws Exception {
        int databaseSizeBeforeUpdate = medicalContactRepository.findAll().size();

        // Create the MedicalContact
        MedicalContactDTO medicalContactDTO = medicalContactMapper.toDto(medicalContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalContactMockMvc.perform(put("/api/medical-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalContact in the database
        List<MedicalContact> medicalContactList = medicalContactRepository.findAll();
        assertThat(medicalContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalContact() throws Exception {
        // Initialize the database
        medicalContactRepository.saveAndFlush(medicalContact);

        int databaseSizeBeforeDelete = medicalContactRepository.findAll().size();

        // Delete the medicalContact
        restMedicalContactMockMvc.perform(delete("/api/medical-contacts/{id}", medicalContact.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalContact> medicalContactList = medicalContactRepository.findAll();
        assertThat(medicalContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
