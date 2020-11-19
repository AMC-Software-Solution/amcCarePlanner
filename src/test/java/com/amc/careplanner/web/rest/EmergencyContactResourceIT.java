package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.EmergencyContact;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.EmergencyContactRepository;
import com.amc.careplanner.service.EmergencyContactService;
import com.amc.careplanner.service.dto.EmergencyContactDTO;
import com.amc.careplanner.service.mapper.EmergencyContactMapper;
import com.amc.careplanner.service.dto.EmergencyContactCriteria;
import com.amc.careplanner.service.EmergencyContactQueryService;

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
 * Integration tests for the {@link EmergencyContactResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmergencyContactResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_RELATIONSHIP = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_RELATIONSHIP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_KEY_HOLDER = false;
    private static final Boolean UPDATED_IS_KEY_HOLDER = true;

    private static final Boolean DEFAULT_INFO_SHARING_CONSENT_GIVEN = false;
    private static final Boolean UPDATED_INFO_SHARING_CONSENT_GIVEN = true;

    private static final String DEFAULT_PREFERRED_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FULL_ADDRESS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Autowired
    private EmergencyContactMapper emergencyContactMapper;

    @Autowired
    private EmergencyContactService emergencyContactService;

    @Autowired
    private EmergencyContactQueryService emergencyContactQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmergencyContactMockMvc;

    private EmergencyContact emergencyContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmergencyContact createEntity(EntityManager em) {
        EmergencyContact emergencyContact = new EmergencyContact()
            .name(DEFAULT_NAME)
            .contactRelationship(DEFAULT_CONTACT_RELATIONSHIP)
            .isKeyHolder(DEFAULT_IS_KEY_HOLDER)
            .infoSharingConsentGiven(DEFAULT_INFO_SHARING_CONSENT_GIVEN)
            .preferredContactNumber(DEFAULT_PREFERRED_CONTACT_NUMBER)
            .fullAddress(DEFAULT_FULL_ADDRESS)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return emergencyContact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmergencyContact createUpdatedEntity(EntityManager em) {
        EmergencyContact emergencyContact = new EmergencyContact()
            .name(UPDATED_NAME)
            .contactRelationship(UPDATED_CONTACT_RELATIONSHIP)
            .isKeyHolder(UPDATED_IS_KEY_HOLDER)
            .infoSharingConsentGiven(UPDATED_INFO_SHARING_CONSENT_GIVEN)
            .preferredContactNumber(UPDATED_PREFERRED_CONTACT_NUMBER)
            .fullAddress(UPDATED_FULL_ADDRESS)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return emergencyContact;
    }

    @BeforeEach
    public void initTest() {
        emergencyContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmergencyContact() throws Exception {
        int databaseSizeBeforeCreate = emergencyContactRepository.findAll().size();
        // Create the EmergencyContact
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);
        restEmergencyContactMockMvc.perform(post("/api/emergency-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO)))
            .andExpect(status().isCreated());

        // Validate the EmergencyContact in the database
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeCreate + 1);
        EmergencyContact testEmergencyContact = emergencyContactList.get(emergencyContactList.size() - 1);
        assertThat(testEmergencyContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmergencyContact.getContactRelationship()).isEqualTo(DEFAULT_CONTACT_RELATIONSHIP);
        assertThat(testEmergencyContact.isIsKeyHolder()).isEqualTo(DEFAULT_IS_KEY_HOLDER);
        assertThat(testEmergencyContact.isInfoSharingConsentGiven()).isEqualTo(DEFAULT_INFO_SHARING_CONSENT_GIVEN);
        assertThat(testEmergencyContact.getPreferredContactNumber()).isEqualTo(DEFAULT_PREFERRED_CONTACT_NUMBER);
        assertThat(testEmergencyContact.getFullAddress()).isEqualTo(DEFAULT_FULL_ADDRESS);
        assertThat(testEmergencyContact.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testEmergencyContact.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createEmergencyContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emergencyContactRepository.findAll().size();

        // Create the EmergencyContact with an existing ID
        emergencyContact.setId(1L);
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmergencyContactMockMvc.perform(post("/api/emergency-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmergencyContact in the database
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = emergencyContactRepository.findAll().size();
        // set the field null
        emergencyContact.setName(null);

        // Create the EmergencyContact, which fails.
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);


        restEmergencyContactMockMvc.perform(post("/api/emergency-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO)))
            .andExpect(status().isBadRequest());

        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactRelationshipIsRequired() throws Exception {
        int databaseSizeBeforeTest = emergencyContactRepository.findAll().size();
        // set the field null
        emergencyContact.setContactRelationship(null);

        // Create the EmergencyContact, which fails.
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);


        restEmergencyContactMockMvc.perform(post("/api/emergency-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO)))
            .andExpect(status().isBadRequest());

        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreferredContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = emergencyContactRepository.findAll().size();
        // set the field null
        emergencyContact.setPreferredContactNumber(null);

        // Create the EmergencyContact, which fails.
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);


        restEmergencyContactMockMvc.perform(post("/api/emergency-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO)))
            .andExpect(status().isBadRequest());

        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = emergencyContactRepository.findAll().size();
        // set the field null
        emergencyContact.setTenantId(null);

        // Create the EmergencyContact, which fails.
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);


        restEmergencyContactMockMvc.perform(post("/api/emergency-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO)))
            .andExpect(status().isBadRequest());

        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmergencyContacts() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList
        restEmergencyContactMockMvc.perform(get("/api/emergency-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencyContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactRelationship").value(hasItem(DEFAULT_CONTACT_RELATIONSHIP)))
            .andExpect(jsonPath("$.[*].isKeyHolder").value(hasItem(DEFAULT_IS_KEY_HOLDER.booleanValue())))
            .andExpect(jsonPath("$.[*].infoSharingConsentGiven").value(hasItem(DEFAULT_INFO_SHARING_CONSENT_GIVEN.booleanValue())))
            .andExpect(jsonPath("$.[*].preferredContactNumber").value(hasItem(DEFAULT_PREFERRED_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].fullAddress").value(hasItem(DEFAULT_FULL_ADDRESS)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmergencyContact() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get the emergencyContact
        restEmergencyContactMockMvc.perform(get("/api/emergency-contacts/{id}", emergencyContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emergencyContact.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contactRelationship").value(DEFAULT_CONTACT_RELATIONSHIP))
            .andExpect(jsonPath("$.isKeyHolder").value(DEFAULT_IS_KEY_HOLDER.booleanValue()))
            .andExpect(jsonPath("$.infoSharingConsentGiven").value(DEFAULT_INFO_SHARING_CONSENT_GIVEN.booleanValue()))
            .andExpect(jsonPath("$.preferredContactNumber").value(DEFAULT_PREFERRED_CONTACT_NUMBER))
            .andExpect(jsonPath("$.fullAddress").value(DEFAULT_FULL_ADDRESS))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getEmergencyContactsByIdFiltering() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        Long id = emergencyContact.getId();

        defaultEmergencyContactShouldBeFound("id.equals=" + id);
        defaultEmergencyContactShouldNotBeFound("id.notEquals=" + id);

        defaultEmergencyContactShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmergencyContactShouldNotBeFound("id.greaterThan=" + id);

        defaultEmergencyContactShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmergencyContactShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmergencyContactsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where name equals to DEFAULT_NAME
        defaultEmergencyContactShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the emergencyContactList where name equals to UPDATED_NAME
        defaultEmergencyContactShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where name not equals to DEFAULT_NAME
        defaultEmergencyContactShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the emergencyContactList where name not equals to UPDATED_NAME
        defaultEmergencyContactShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEmergencyContactShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the emergencyContactList where name equals to UPDATED_NAME
        defaultEmergencyContactShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where name is not null
        defaultEmergencyContactShouldBeFound("name.specified=true");

        // Get all the emergencyContactList where name is null
        defaultEmergencyContactShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmergencyContactsByNameContainsSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where name contains DEFAULT_NAME
        defaultEmergencyContactShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the emergencyContactList where name contains UPDATED_NAME
        defaultEmergencyContactShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where name does not contain DEFAULT_NAME
        defaultEmergencyContactShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the emergencyContactList where name does not contain UPDATED_NAME
        defaultEmergencyContactShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllEmergencyContactsByContactRelationshipIsEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where contactRelationship equals to DEFAULT_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldBeFound("contactRelationship.equals=" + DEFAULT_CONTACT_RELATIONSHIP);

        // Get all the emergencyContactList where contactRelationship equals to UPDATED_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldNotBeFound("contactRelationship.equals=" + UPDATED_CONTACT_RELATIONSHIP);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByContactRelationshipIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where contactRelationship not equals to DEFAULT_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldNotBeFound("contactRelationship.notEquals=" + DEFAULT_CONTACT_RELATIONSHIP);

        // Get all the emergencyContactList where contactRelationship not equals to UPDATED_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldBeFound("contactRelationship.notEquals=" + UPDATED_CONTACT_RELATIONSHIP);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByContactRelationshipIsInShouldWork() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where contactRelationship in DEFAULT_CONTACT_RELATIONSHIP or UPDATED_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldBeFound("contactRelationship.in=" + DEFAULT_CONTACT_RELATIONSHIP + "," + UPDATED_CONTACT_RELATIONSHIP);

        // Get all the emergencyContactList where contactRelationship equals to UPDATED_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldNotBeFound("contactRelationship.in=" + UPDATED_CONTACT_RELATIONSHIP);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByContactRelationshipIsNullOrNotNull() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where contactRelationship is not null
        defaultEmergencyContactShouldBeFound("contactRelationship.specified=true");

        // Get all the emergencyContactList where contactRelationship is null
        defaultEmergencyContactShouldNotBeFound("contactRelationship.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmergencyContactsByContactRelationshipContainsSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where contactRelationship contains DEFAULT_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldBeFound("contactRelationship.contains=" + DEFAULT_CONTACT_RELATIONSHIP);

        // Get all the emergencyContactList where contactRelationship contains UPDATED_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldNotBeFound("contactRelationship.contains=" + UPDATED_CONTACT_RELATIONSHIP);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByContactRelationshipNotContainsSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where contactRelationship does not contain DEFAULT_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldNotBeFound("contactRelationship.doesNotContain=" + DEFAULT_CONTACT_RELATIONSHIP);

        // Get all the emergencyContactList where contactRelationship does not contain UPDATED_CONTACT_RELATIONSHIP
        defaultEmergencyContactShouldBeFound("contactRelationship.doesNotContain=" + UPDATED_CONTACT_RELATIONSHIP);
    }


    @Test
    @Transactional
    public void getAllEmergencyContactsByIsKeyHolderIsEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where isKeyHolder equals to DEFAULT_IS_KEY_HOLDER
        defaultEmergencyContactShouldBeFound("isKeyHolder.equals=" + DEFAULT_IS_KEY_HOLDER);

        // Get all the emergencyContactList where isKeyHolder equals to UPDATED_IS_KEY_HOLDER
        defaultEmergencyContactShouldNotBeFound("isKeyHolder.equals=" + UPDATED_IS_KEY_HOLDER);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByIsKeyHolderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where isKeyHolder not equals to DEFAULT_IS_KEY_HOLDER
        defaultEmergencyContactShouldNotBeFound("isKeyHolder.notEquals=" + DEFAULT_IS_KEY_HOLDER);

        // Get all the emergencyContactList where isKeyHolder not equals to UPDATED_IS_KEY_HOLDER
        defaultEmergencyContactShouldBeFound("isKeyHolder.notEquals=" + UPDATED_IS_KEY_HOLDER);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByIsKeyHolderIsInShouldWork() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where isKeyHolder in DEFAULT_IS_KEY_HOLDER or UPDATED_IS_KEY_HOLDER
        defaultEmergencyContactShouldBeFound("isKeyHolder.in=" + DEFAULT_IS_KEY_HOLDER + "," + UPDATED_IS_KEY_HOLDER);

        // Get all the emergencyContactList where isKeyHolder equals to UPDATED_IS_KEY_HOLDER
        defaultEmergencyContactShouldNotBeFound("isKeyHolder.in=" + UPDATED_IS_KEY_HOLDER);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByIsKeyHolderIsNullOrNotNull() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where isKeyHolder is not null
        defaultEmergencyContactShouldBeFound("isKeyHolder.specified=true");

        // Get all the emergencyContactList where isKeyHolder is null
        defaultEmergencyContactShouldNotBeFound("isKeyHolder.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByInfoSharingConsentGivenIsEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where infoSharingConsentGiven equals to DEFAULT_INFO_SHARING_CONSENT_GIVEN
        defaultEmergencyContactShouldBeFound("infoSharingConsentGiven.equals=" + DEFAULT_INFO_SHARING_CONSENT_GIVEN);

        // Get all the emergencyContactList where infoSharingConsentGiven equals to UPDATED_INFO_SHARING_CONSENT_GIVEN
        defaultEmergencyContactShouldNotBeFound("infoSharingConsentGiven.equals=" + UPDATED_INFO_SHARING_CONSENT_GIVEN);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByInfoSharingConsentGivenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where infoSharingConsentGiven not equals to DEFAULT_INFO_SHARING_CONSENT_GIVEN
        defaultEmergencyContactShouldNotBeFound("infoSharingConsentGiven.notEquals=" + DEFAULT_INFO_SHARING_CONSENT_GIVEN);

        // Get all the emergencyContactList where infoSharingConsentGiven not equals to UPDATED_INFO_SHARING_CONSENT_GIVEN
        defaultEmergencyContactShouldBeFound("infoSharingConsentGiven.notEquals=" + UPDATED_INFO_SHARING_CONSENT_GIVEN);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByInfoSharingConsentGivenIsInShouldWork() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where infoSharingConsentGiven in DEFAULT_INFO_SHARING_CONSENT_GIVEN or UPDATED_INFO_SHARING_CONSENT_GIVEN
        defaultEmergencyContactShouldBeFound("infoSharingConsentGiven.in=" + DEFAULT_INFO_SHARING_CONSENT_GIVEN + "," + UPDATED_INFO_SHARING_CONSENT_GIVEN);

        // Get all the emergencyContactList where infoSharingConsentGiven equals to UPDATED_INFO_SHARING_CONSENT_GIVEN
        defaultEmergencyContactShouldNotBeFound("infoSharingConsentGiven.in=" + UPDATED_INFO_SHARING_CONSENT_GIVEN);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByInfoSharingConsentGivenIsNullOrNotNull() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where infoSharingConsentGiven is not null
        defaultEmergencyContactShouldBeFound("infoSharingConsentGiven.specified=true");

        // Get all the emergencyContactList where infoSharingConsentGiven is null
        defaultEmergencyContactShouldNotBeFound("infoSharingConsentGiven.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByPreferredContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where preferredContactNumber equals to DEFAULT_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldBeFound("preferredContactNumber.equals=" + DEFAULT_PREFERRED_CONTACT_NUMBER);

        // Get all the emergencyContactList where preferredContactNumber equals to UPDATED_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldNotBeFound("preferredContactNumber.equals=" + UPDATED_PREFERRED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByPreferredContactNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where preferredContactNumber not equals to DEFAULT_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldNotBeFound("preferredContactNumber.notEquals=" + DEFAULT_PREFERRED_CONTACT_NUMBER);

        // Get all the emergencyContactList where preferredContactNumber not equals to UPDATED_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldBeFound("preferredContactNumber.notEquals=" + UPDATED_PREFERRED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByPreferredContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where preferredContactNumber in DEFAULT_PREFERRED_CONTACT_NUMBER or UPDATED_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldBeFound("preferredContactNumber.in=" + DEFAULT_PREFERRED_CONTACT_NUMBER + "," + UPDATED_PREFERRED_CONTACT_NUMBER);

        // Get all the emergencyContactList where preferredContactNumber equals to UPDATED_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldNotBeFound("preferredContactNumber.in=" + UPDATED_PREFERRED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByPreferredContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where preferredContactNumber is not null
        defaultEmergencyContactShouldBeFound("preferredContactNumber.specified=true");

        // Get all the emergencyContactList where preferredContactNumber is null
        defaultEmergencyContactShouldNotBeFound("preferredContactNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmergencyContactsByPreferredContactNumberContainsSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where preferredContactNumber contains DEFAULT_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldBeFound("preferredContactNumber.contains=" + DEFAULT_PREFERRED_CONTACT_NUMBER);

        // Get all the emergencyContactList where preferredContactNumber contains UPDATED_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldNotBeFound("preferredContactNumber.contains=" + UPDATED_PREFERRED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByPreferredContactNumberNotContainsSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where preferredContactNumber does not contain DEFAULT_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldNotBeFound("preferredContactNumber.doesNotContain=" + DEFAULT_PREFERRED_CONTACT_NUMBER);

        // Get all the emergencyContactList where preferredContactNumber does not contain UPDATED_PREFERRED_CONTACT_NUMBER
        defaultEmergencyContactShouldBeFound("preferredContactNumber.doesNotContain=" + UPDATED_PREFERRED_CONTACT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllEmergencyContactsByFullAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where fullAddress equals to DEFAULT_FULL_ADDRESS
        defaultEmergencyContactShouldBeFound("fullAddress.equals=" + DEFAULT_FULL_ADDRESS);

        // Get all the emergencyContactList where fullAddress equals to UPDATED_FULL_ADDRESS
        defaultEmergencyContactShouldNotBeFound("fullAddress.equals=" + UPDATED_FULL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByFullAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where fullAddress not equals to DEFAULT_FULL_ADDRESS
        defaultEmergencyContactShouldNotBeFound("fullAddress.notEquals=" + DEFAULT_FULL_ADDRESS);

        // Get all the emergencyContactList where fullAddress not equals to UPDATED_FULL_ADDRESS
        defaultEmergencyContactShouldBeFound("fullAddress.notEquals=" + UPDATED_FULL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByFullAddressIsInShouldWork() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where fullAddress in DEFAULT_FULL_ADDRESS or UPDATED_FULL_ADDRESS
        defaultEmergencyContactShouldBeFound("fullAddress.in=" + DEFAULT_FULL_ADDRESS + "," + UPDATED_FULL_ADDRESS);

        // Get all the emergencyContactList where fullAddress equals to UPDATED_FULL_ADDRESS
        defaultEmergencyContactShouldNotBeFound("fullAddress.in=" + UPDATED_FULL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByFullAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where fullAddress is not null
        defaultEmergencyContactShouldBeFound("fullAddress.specified=true");

        // Get all the emergencyContactList where fullAddress is null
        defaultEmergencyContactShouldNotBeFound("fullAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmergencyContactsByFullAddressContainsSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where fullAddress contains DEFAULT_FULL_ADDRESS
        defaultEmergencyContactShouldBeFound("fullAddress.contains=" + DEFAULT_FULL_ADDRESS);

        // Get all the emergencyContactList where fullAddress contains UPDATED_FULL_ADDRESS
        defaultEmergencyContactShouldNotBeFound("fullAddress.contains=" + UPDATED_FULL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByFullAddressNotContainsSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where fullAddress does not contain DEFAULT_FULL_ADDRESS
        defaultEmergencyContactShouldNotBeFound("fullAddress.doesNotContain=" + DEFAULT_FULL_ADDRESS);

        // Get all the emergencyContactList where fullAddress does not contain UPDATED_FULL_ADDRESS
        defaultEmergencyContactShouldBeFound("fullAddress.doesNotContain=" + UPDATED_FULL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllEmergencyContactsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmergencyContactShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the emergencyContactList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmergencyContactShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmergencyContactShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the emergencyContactList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultEmergencyContactShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultEmergencyContactShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the emergencyContactList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmergencyContactShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where lastUpdatedDate is not null
        defaultEmergencyContactShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the emergencyContactList where lastUpdatedDate is null
        defaultEmergencyContactShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmergencyContactShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the emergencyContactList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultEmergencyContactShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmergencyContactShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the emergencyContactList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultEmergencyContactShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultEmergencyContactShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the emergencyContactList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultEmergencyContactShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultEmergencyContactShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the emergencyContactList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultEmergencyContactShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmergencyContactsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where tenantId equals to DEFAULT_TENANT_ID
        defaultEmergencyContactShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the emergencyContactList where tenantId equals to UPDATED_TENANT_ID
        defaultEmergencyContactShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where tenantId not equals to DEFAULT_TENANT_ID
        defaultEmergencyContactShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the emergencyContactList where tenantId not equals to UPDATED_TENANT_ID
        defaultEmergencyContactShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultEmergencyContactShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the emergencyContactList where tenantId equals to UPDATED_TENANT_ID
        defaultEmergencyContactShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where tenantId is not null
        defaultEmergencyContactShouldBeFound("tenantId.specified=true");

        // Get all the emergencyContactList where tenantId is null
        defaultEmergencyContactShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultEmergencyContactShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the emergencyContactList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultEmergencyContactShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultEmergencyContactShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the emergencyContactList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultEmergencyContactShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where tenantId is less than DEFAULT_TENANT_ID
        defaultEmergencyContactShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the emergencyContactList where tenantId is less than UPDATED_TENANT_ID
        defaultEmergencyContactShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllEmergencyContactsByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList where tenantId is greater than DEFAULT_TENANT_ID
        defaultEmergencyContactShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the emergencyContactList where tenantId is greater than SMALLER_TENANT_ID
        defaultEmergencyContactShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllEmergencyContactsByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        emergencyContact.setServiceUser(serviceUser);
        emergencyContactRepository.saveAndFlush(emergencyContact);
        Long serviceUserId = serviceUser.getId();

        // Get all the emergencyContactList where serviceUser equals to serviceUserId
        defaultEmergencyContactShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the emergencyContactList where serviceUser equals to serviceUserId + 1
        defaultEmergencyContactShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmergencyContactShouldBeFound(String filter) throws Exception {
        restEmergencyContactMockMvc.perform(get("/api/emergency-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencyContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactRelationship").value(hasItem(DEFAULT_CONTACT_RELATIONSHIP)))
            .andExpect(jsonPath("$.[*].isKeyHolder").value(hasItem(DEFAULT_IS_KEY_HOLDER.booleanValue())))
            .andExpect(jsonPath("$.[*].infoSharingConsentGiven").value(hasItem(DEFAULT_INFO_SHARING_CONSENT_GIVEN.booleanValue())))
            .andExpect(jsonPath("$.[*].preferredContactNumber").value(hasItem(DEFAULT_PREFERRED_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].fullAddress").value(hasItem(DEFAULT_FULL_ADDRESS)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restEmergencyContactMockMvc.perform(get("/api/emergency-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmergencyContactShouldNotBeFound(String filter) throws Exception {
        restEmergencyContactMockMvc.perform(get("/api/emergency-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmergencyContactMockMvc.perform(get("/api/emergency-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmergencyContact() throws Exception {
        // Get the emergencyContact
        restEmergencyContactMockMvc.perform(get("/api/emergency-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmergencyContact() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        int databaseSizeBeforeUpdate = emergencyContactRepository.findAll().size();

        // Update the emergencyContact
        EmergencyContact updatedEmergencyContact = emergencyContactRepository.findById(emergencyContact.getId()).get();
        // Disconnect from session so that the updates on updatedEmergencyContact are not directly saved in db
        em.detach(updatedEmergencyContact);
        updatedEmergencyContact
            .name(UPDATED_NAME)
            .contactRelationship(UPDATED_CONTACT_RELATIONSHIP)
            .isKeyHolder(UPDATED_IS_KEY_HOLDER)
            .infoSharingConsentGiven(UPDATED_INFO_SHARING_CONSENT_GIVEN)
            .preferredContactNumber(UPDATED_PREFERRED_CONTACT_NUMBER)
            .fullAddress(UPDATED_FULL_ADDRESS)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(updatedEmergencyContact);

        restEmergencyContactMockMvc.perform(put("/api/emergency-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO)))
            .andExpect(status().isOk());

        // Validate the EmergencyContact in the database
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeUpdate);
        EmergencyContact testEmergencyContact = emergencyContactList.get(emergencyContactList.size() - 1);
        assertThat(testEmergencyContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmergencyContact.getContactRelationship()).isEqualTo(UPDATED_CONTACT_RELATIONSHIP);
        assertThat(testEmergencyContact.isIsKeyHolder()).isEqualTo(UPDATED_IS_KEY_HOLDER);
        assertThat(testEmergencyContact.isInfoSharingConsentGiven()).isEqualTo(UPDATED_INFO_SHARING_CONSENT_GIVEN);
        assertThat(testEmergencyContact.getPreferredContactNumber()).isEqualTo(UPDATED_PREFERRED_CONTACT_NUMBER);
        assertThat(testEmergencyContact.getFullAddress()).isEqualTo(UPDATED_FULL_ADDRESS);
        assertThat(testEmergencyContact.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testEmergencyContact.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmergencyContact() throws Exception {
        int databaseSizeBeforeUpdate = emergencyContactRepository.findAll().size();

        // Create the EmergencyContact
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmergencyContactMockMvc.perform(put("/api/emergency-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmergencyContact in the database
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmergencyContact() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        int databaseSizeBeforeDelete = emergencyContactRepository.findAll().size();

        // Delete the emergencyContact
        restEmergencyContactMockMvc.perform(delete("/api/emergency-contacts/{id}", emergencyContact.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
