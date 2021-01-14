package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Consent;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.ConsentRepository;
import com.amc.careplanner.service.ConsentService;
import com.amc.careplanner.service.dto.ConsentDTO;
import com.amc.careplanner.service.mapper.ConsentMapper;
import com.amc.careplanner.service.dto.ConsentCriteria;
import com.amc.careplanner.service.ConsentQueryService;

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
 * Integration tests for the {@link ConsentResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConsentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GIVE_CONSENT = false;
    private static final Boolean UPDATED_GIVE_CONSENT = true;

    private static final String DEFAULT_ARRANGEMENTS = "AAAAAAAAAA";
    private static final String UPDATED_ARRANGEMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_USER_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_USER_SIGNATURE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SIGNATURE_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SIGNATURE_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SIGNATURE_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SIGNATURE_IMAGE_URL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CONSENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CONSENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CONSENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

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
    private ConsentRepository consentRepository;

    @Autowired
    private ConsentMapper consentMapper;

    @Autowired
    private ConsentService consentService;

    @Autowired
    private ConsentQueryService consentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsentMockMvc;

    private Consent consent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consent createEntity(EntityManager em) {
        Consent consent = new Consent()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .giveConsent(DEFAULT_GIVE_CONSENT)
            .arrangements(DEFAULT_ARRANGEMENTS)
            .serviceUserSignature(DEFAULT_SERVICE_USER_SIGNATURE)
            .signatureImage(DEFAULT_SIGNATURE_IMAGE)
            .signatureImageContentType(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE)
            .signatureImageUrl(DEFAULT_SIGNATURE_IMAGE_URL)
            .consentDate(DEFAULT_CONSENT_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return consent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consent createUpdatedEntity(EntityManager em) {
        Consent consent = new Consent()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .giveConsent(UPDATED_GIVE_CONSENT)
            .arrangements(UPDATED_ARRANGEMENTS)
            .serviceUserSignature(UPDATED_SERVICE_USER_SIGNATURE)
            .signatureImage(UPDATED_SIGNATURE_IMAGE)
            .signatureImageContentType(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE)
            .signatureImageUrl(UPDATED_SIGNATURE_IMAGE_URL)
            .consentDate(UPDATED_CONSENT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return consent;
    }

    @BeforeEach
    public void initTest() {
        consent = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsent() throws Exception {
        int databaseSizeBeforeCreate = consentRepository.findAll().size();
        // Create the Consent
        ConsentDTO consentDTO = consentMapper.toDto(consent);
        restConsentMockMvc.perform(post("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consentDTO)))
            .andExpect(status().isCreated());

        // Validate the Consent in the database
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeCreate + 1);
        Consent testConsent = consentList.get(consentList.size() - 1);
        assertThat(testConsent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testConsent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConsent.isGiveConsent()).isEqualTo(DEFAULT_GIVE_CONSENT);
        assertThat(testConsent.getArrangements()).isEqualTo(DEFAULT_ARRANGEMENTS);
        assertThat(testConsent.getServiceUserSignature()).isEqualTo(DEFAULT_SERVICE_USER_SIGNATURE);
        assertThat(testConsent.getSignatureImage()).isEqualTo(DEFAULT_SIGNATURE_IMAGE);
        assertThat(testConsent.getSignatureImageContentType()).isEqualTo(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE);
        assertThat(testConsent.getSignatureImageUrl()).isEqualTo(DEFAULT_SIGNATURE_IMAGE_URL);
        assertThat(testConsent.getConsentDate()).isEqualTo(DEFAULT_CONSENT_DATE);
        assertThat(testConsent.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConsent.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testConsent.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testConsent.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createConsentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consentRepository.findAll().size();

        // Create the Consent with an existing ID
        consent.setId(1L);
        ConsentDTO consentDTO = consentMapper.toDto(consent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsentMockMvc.perform(post("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consent in the database
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = consentRepository.findAll().size();
        // set the field null
        consent.setTitle(null);

        // Create the Consent, which fails.
        ConsentDTO consentDTO = consentMapper.toDto(consent);


        restConsentMockMvc.perform(post("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consentDTO)))
            .andExpect(status().isBadRequest());

        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = consentRepository.findAll().size();
        // set the field null
        consent.setClientId(null);

        // Create the Consent, which fails.
        ConsentDTO consentDTO = consentMapper.toDto(consent);


        restConsentMockMvc.perform(post("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consentDTO)))
            .andExpect(status().isBadRequest());

        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsents() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList
        restConsentMockMvc.perform(get("/api/consents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consent.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].giveConsent").value(hasItem(DEFAULT_GIVE_CONSENT.booleanValue())))
            .andExpect(jsonPath("$.[*].arrangements").value(hasItem(DEFAULT_ARRANGEMENTS)))
            .andExpect(jsonPath("$.[*].serviceUserSignature").value(hasItem(DEFAULT_SERVICE_USER_SIGNATURE)))
            .andExpect(jsonPath("$.[*].signatureImageContentType").value(hasItem(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signatureImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE_IMAGE))))
            .andExpect(jsonPath("$.[*].signatureImageUrl").value(hasItem(DEFAULT_SIGNATURE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].consentDate").value(hasItem(sameInstant(DEFAULT_CONSENT_DATE))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConsent() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get the consent
        restConsentMockMvc.perform(get("/api/consents/{id}", consent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consent.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.giveConsent").value(DEFAULT_GIVE_CONSENT.booleanValue()))
            .andExpect(jsonPath("$.arrangements").value(DEFAULT_ARRANGEMENTS))
            .andExpect(jsonPath("$.serviceUserSignature").value(DEFAULT_SERVICE_USER_SIGNATURE))
            .andExpect(jsonPath("$.signatureImageContentType").value(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signatureImage").value(Base64Utils.encodeToString(DEFAULT_SIGNATURE_IMAGE)))
            .andExpect(jsonPath("$.signatureImageUrl").value(DEFAULT_SIGNATURE_IMAGE_URL))
            .andExpect(jsonPath("$.consentDate").value(sameInstant(DEFAULT_CONSENT_DATE)))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getConsentsByIdFiltering() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        Long id = consent.getId();

        defaultConsentShouldBeFound("id.equals=" + id);
        defaultConsentShouldNotBeFound("id.notEquals=" + id);

        defaultConsentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConsentShouldNotBeFound("id.greaterThan=" + id);

        defaultConsentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConsentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConsentsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where title equals to DEFAULT_TITLE
        defaultConsentShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the consentList where title equals to UPDATED_TITLE
        defaultConsentShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllConsentsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where title not equals to DEFAULT_TITLE
        defaultConsentShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the consentList where title not equals to UPDATED_TITLE
        defaultConsentShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllConsentsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultConsentShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the consentList where title equals to UPDATED_TITLE
        defaultConsentShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllConsentsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where title is not null
        defaultConsentShouldBeFound("title.specified=true");

        // Get all the consentList where title is null
        defaultConsentShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsentsByTitleContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where title contains DEFAULT_TITLE
        defaultConsentShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the consentList where title contains UPDATED_TITLE
        defaultConsentShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllConsentsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where title does not contain DEFAULT_TITLE
        defaultConsentShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the consentList where title does not contain UPDATED_TITLE
        defaultConsentShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllConsentsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where description equals to DEFAULT_DESCRIPTION
        defaultConsentShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the consentList where description equals to UPDATED_DESCRIPTION
        defaultConsentShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllConsentsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where description not equals to DEFAULT_DESCRIPTION
        defaultConsentShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the consentList where description not equals to UPDATED_DESCRIPTION
        defaultConsentShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllConsentsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultConsentShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the consentList where description equals to UPDATED_DESCRIPTION
        defaultConsentShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllConsentsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where description is not null
        defaultConsentShouldBeFound("description.specified=true");

        // Get all the consentList where description is null
        defaultConsentShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsentsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where description contains DEFAULT_DESCRIPTION
        defaultConsentShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the consentList where description contains UPDATED_DESCRIPTION
        defaultConsentShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllConsentsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where description does not contain DEFAULT_DESCRIPTION
        defaultConsentShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the consentList where description does not contain UPDATED_DESCRIPTION
        defaultConsentShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllConsentsByGiveConsentIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where giveConsent equals to DEFAULT_GIVE_CONSENT
        defaultConsentShouldBeFound("giveConsent.equals=" + DEFAULT_GIVE_CONSENT);

        // Get all the consentList where giveConsent equals to UPDATED_GIVE_CONSENT
        defaultConsentShouldNotBeFound("giveConsent.equals=" + UPDATED_GIVE_CONSENT);
    }

    @Test
    @Transactional
    public void getAllConsentsByGiveConsentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where giveConsent not equals to DEFAULT_GIVE_CONSENT
        defaultConsentShouldNotBeFound("giveConsent.notEquals=" + DEFAULT_GIVE_CONSENT);

        // Get all the consentList where giveConsent not equals to UPDATED_GIVE_CONSENT
        defaultConsentShouldBeFound("giveConsent.notEquals=" + UPDATED_GIVE_CONSENT);
    }

    @Test
    @Transactional
    public void getAllConsentsByGiveConsentIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where giveConsent in DEFAULT_GIVE_CONSENT or UPDATED_GIVE_CONSENT
        defaultConsentShouldBeFound("giveConsent.in=" + DEFAULT_GIVE_CONSENT + "," + UPDATED_GIVE_CONSENT);

        // Get all the consentList where giveConsent equals to UPDATED_GIVE_CONSENT
        defaultConsentShouldNotBeFound("giveConsent.in=" + UPDATED_GIVE_CONSENT);
    }

    @Test
    @Transactional
    public void getAllConsentsByGiveConsentIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where giveConsent is not null
        defaultConsentShouldBeFound("giveConsent.specified=true");

        // Get all the consentList where giveConsent is null
        defaultConsentShouldNotBeFound("giveConsent.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsentsByArrangementsIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where arrangements equals to DEFAULT_ARRANGEMENTS
        defaultConsentShouldBeFound("arrangements.equals=" + DEFAULT_ARRANGEMENTS);

        // Get all the consentList where arrangements equals to UPDATED_ARRANGEMENTS
        defaultConsentShouldNotBeFound("arrangements.equals=" + UPDATED_ARRANGEMENTS);
    }

    @Test
    @Transactional
    public void getAllConsentsByArrangementsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where arrangements not equals to DEFAULT_ARRANGEMENTS
        defaultConsentShouldNotBeFound("arrangements.notEquals=" + DEFAULT_ARRANGEMENTS);

        // Get all the consentList where arrangements not equals to UPDATED_ARRANGEMENTS
        defaultConsentShouldBeFound("arrangements.notEquals=" + UPDATED_ARRANGEMENTS);
    }

    @Test
    @Transactional
    public void getAllConsentsByArrangementsIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where arrangements in DEFAULT_ARRANGEMENTS or UPDATED_ARRANGEMENTS
        defaultConsentShouldBeFound("arrangements.in=" + DEFAULT_ARRANGEMENTS + "," + UPDATED_ARRANGEMENTS);

        // Get all the consentList where arrangements equals to UPDATED_ARRANGEMENTS
        defaultConsentShouldNotBeFound("arrangements.in=" + UPDATED_ARRANGEMENTS);
    }

    @Test
    @Transactional
    public void getAllConsentsByArrangementsIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where arrangements is not null
        defaultConsentShouldBeFound("arrangements.specified=true");

        // Get all the consentList where arrangements is null
        defaultConsentShouldNotBeFound("arrangements.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsentsByArrangementsContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where arrangements contains DEFAULT_ARRANGEMENTS
        defaultConsentShouldBeFound("arrangements.contains=" + DEFAULT_ARRANGEMENTS);

        // Get all the consentList where arrangements contains UPDATED_ARRANGEMENTS
        defaultConsentShouldNotBeFound("arrangements.contains=" + UPDATED_ARRANGEMENTS);
    }

    @Test
    @Transactional
    public void getAllConsentsByArrangementsNotContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where arrangements does not contain DEFAULT_ARRANGEMENTS
        defaultConsentShouldNotBeFound("arrangements.doesNotContain=" + DEFAULT_ARRANGEMENTS);

        // Get all the consentList where arrangements does not contain UPDATED_ARRANGEMENTS
        defaultConsentShouldBeFound("arrangements.doesNotContain=" + UPDATED_ARRANGEMENTS);
    }


    @Test
    @Transactional
    public void getAllConsentsByServiceUserSignatureIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where serviceUserSignature equals to DEFAULT_SERVICE_USER_SIGNATURE
        defaultConsentShouldBeFound("serviceUserSignature.equals=" + DEFAULT_SERVICE_USER_SIGNATURE);

        // Get all the consentList where serviceUserSignature equals to UPDATED_SERVICE_USER_SIGNATURE
        defaultConsentShouldNotBeFound("serviceUserSignature.equals=" + UPDATED_SERVICE_USER_SIGNATURE);
    }

    @Test
    @Transactional
    public void getAllConsentsByServiceUserSignatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where serviceUserSignature not equals to DEFAULT_SERVICE_USER_SIGNATURE
        defaultConsentShouldNotBeFound("serviceUserSignature.notEquals=" + DEFAULT_SERVICE_USER_SIGNATURE);

        // Get all the consentList where serviceUserSignature not equals to UPDATED_SERVICE_USER_SIGNATURE
        defaultConsentShouldBeFound("serviceUserSignature.notEquals=" + UPDATED_SERVICE_USER_SIGNATURE);
    }

    @Test
    @Transactional
    public void getAllConsentsByServiceUserSignatureIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where serviceUserSignature in DEFAULT_SERVICE_USER_SIGNATURE or UPDATED_SERVICE_USER_SIGNATURE
        defaultConsentShouldBeFound("serviceUserSignature.in=" + DEFAULT_SERVICE_USER_SIGNATURE + "," + UPDATED_SERVICE_USER_SIGNATURE);

        // Get all the consentList where serviceUserSignature equals to UPDATED_SERVICE_USER_SIGNATURE
        defaultConsentShouldNotBeFound("serviceUserSignature.in=" + UPDATED_SERVICE_USER_SIGNATURE);
    }

    @Test
    @Transactional
    public void getAllConsentsByServiceUserSignatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where serviceUserSignature is not null
        defaultConsentShouldBeFound("serviceUserSignature.specified=true");

        // Get all the consentList where serviceUserSignature is null
        defaultConsentShouldNotBeFound("serviceUserSignature.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsentsByServiceUserSignatureContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where serviceUserSignature contains DEFAULT_SERVICE_USER_SIGNATURE
        defaultConsentShouldBeFound("serviceUserSignature.contains=" + DEFAULT_SERVICE_USER_SIGNATURE);

        // Get all the consentList where serviceUserSignature contains UPDATED_SERVICE_USER_SIGNATURE
        defaultConsentShouldNotBeFound("serviceUserSignature.contains=" + UPDATED_SERVICE_USER_SIGNATURE);
    }

    @Test
    @Transactional
    public void getAllConsentsByServiceUserSignatureNotContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where serviceUserSignature does not contain DEFAULT_SERVICE_USER_SIGNATURE
        defaultConsentShouldNotBeFound("serviceUserSignature.doesNotContain=" + DEFAULT_SERVICE_USER_SIGNATURE);

        // Get all the consentList where serviceUserSignature does not contain UPDATED_SERVICE_USER_SIGNATURE
        defaultConsentShouldBeFound("serviceUserSignature.doesNotContain=" + UPDATED_SERVICE_USER_SIGNATURE);
    }


    @Test
    @Transactional
    public void getAllConsentsBySignatureImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where signatureImageUrl equals to DEFAULT_SIGNATURE_IMAGE_URL
        defaultConsentShouldBeFound("signatureImageUrl.equals=" + DEFAULT_SIGNATURE_IMAGE_URL);

        // Get all the consentList where signatureImageUrl equals to UPDATED_SIGNATURE_IMAGE_URL
        defaultConsentShouldNotBeFound("signatureImageUrl.equals=" + UPDATED_SIGNATURE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllConsentsBySignatureImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where signatureImageUrl not equals to DEFAULT_SIGNATURE_IMAGE_URL
        defaultConsentShouldNotBeFound("signatureImageUrl.notEquals=" + DEFAULT_SIGNATURE_IMAGE_URL);

        // Get all the consentList where signatureImageUrl not equals to UPDATED_SIGNATURE_IMAGE_URL
        defaultConsentShouldBeFound("signatureImageUrl.notEquals=" + UPDATED_SIGNATURE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllConsentsBySignatureImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where signatureImageUrl in DEFAULT_SIGNATURE_IMAGE_URL or UPDATED_SIGNATURE_IMAGE_URL
        defaultConsentShouldBeFound("signatureImageUrl.in=" + DEFAULT_SIGNATURE_IMAGE_URL + "," + UPDATED_SIGNATURE_IMAGE_URL);

        // Get all the consentList where signatureImageUrl equals to UPDATED_SIGNATURE_IMAGE_URL
        defaultConsentShouldNotBeFound("signatureImageUrl.in=" + UPDATED_SIGNATURE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllConsentsBySignatureImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where signatureImageUrl is not null
        defaultConsentShouldBeFound("signatureImageUrl.specified=true");

        // Get all the consentList where signatureImageUrl is null
        defaultConsentShouldNotBeFound("signatureImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsentsBySignatureImageUrlContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where signatureImageUrl contains DEFAULT_SIGNATURE_IMAGE_URL
        defaultConsentShouldBeFound("signatureImageUrl.contains=" + DEFAULT_SIGNATURE_IMAGE_URL);

        // Get all the consentList where signatureImageUrl contains UPDATED_SIGNATURE_IMAGE_URL
        defaultConsentShouldNotBeFound("signatureImageUrl.contains=" + UPDATED_SIGNATURE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllConsentsBySignatureImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where signatureImageUrl does not contain DEFAULT_SIGNATURE_IMAGE_URL
        defaultConsentShouldNotBeFound("signatureImageUrl.doesNotContain=" + DEFAULT_SIGNATURE_IMAGE_URL);

        // Get all the consentList where signatureImageUrl does not contain UPDATED_SIGNATURE_IMAGE_URL
        defaultConsentShouldBeFound("signatureImageUrl.doesNotContain=" + UPDATED_SIGNATURE_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllConsentsByConsentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where consentDate equals to DEFAULT_CONSENT_DATE
        defaultConsentShouldBeFound("consentDate.equals=" + DEFAULT_CONSENT_DATE);

        // Get all the consentList where consentDate equals to UPDATED_CONSENT_DATE
        defaultConsentShouldNotBeFound("consentDate.equals=" + UPDATED_CONSENT_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByConsentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where consentDate not equals to DEFAULT_CONSENT_DATE
        defaultConsentShouldNotBeFound("consentDate.notEquals=" + DEFAULT_CONSENT_DATE);

        // Get all the consentList where consentDate not equals to UPDATED_CONSENT_DATE
        defaultConsentShouldBeFound("consentDate.notEquals=" + UPDATED_CONSENT_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByConsentDateIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where consentDate in DEFAULT_CONSENT_DATE or UPDATED_CONSENT_DATE
        defaultConsentShouldBeFound("consentDate.in=" + DEFAULT_CONSENT_DATE + "," + UPDATED_CONSENT_DATE);

        // Get all the consentList where consentDate equals to UPDATED_CONSENT_DATE
        defaultConsentShouldNotBeFound("consentDate.in=" + UPDATED_CONSENT_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByConsentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where consentDate is not null
        defaultConsentShouldBeFound("consentDate.specified=true");

        // Get all the consentList where consentDate is null
        defaultConsentShouldNotBeFound("consentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsentsByConsentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where consentDate is greater than or equal to DEFAULT_CONSENT_DATE
        defaultConsentShouldBeFound("consentDate.greaterThanOrEqual=" + DEFAULT_CONSENT_DATE);

        // Get all the consentList where consentDate is greater than or equal to UPDATED_CONSENT_DATE
        defaultConsentShouldNotBeFound("consentDate.greaterThanOrEqual=" + UPDATED_CONSENT_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByConsentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where consentDate is less than or equal to DEFAULT_CONSENT_DATE
        defaultConsentShouldBeFound("consentDate.lessThanOrEqual=" + DEFAULT_CONSENT_DATE);

        // Get all the consentList where consentDate is less than or equal to SMALLER_CONSENT_DATE
        defaultConsentShouldNotBeFound("consentDate.lessThanOrEqual=" + SMALLER_CONSENT_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByConsentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where consentDate is less than DEFAULT_CONSENT_DATE
        defaultConsentShouldNotBeFound("consentDate.lessThan=" + DEFAULT_CONSENT_DATE);

        // Get all the consentList where consentDate is less than UPDATED_CONSENT_DATE
        defaultConsentShouldBeFound("consentDate.lessThan=" + UPDATED_CONSENT_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByConsentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where consentDate is greater than DEFAULT_CONSENT_DATE
        defaultConsentShouldNotBeFound("consentDate.greaterThan=" + DEFAULT_CONSENT_DATE);

        // Get all the consentList where consentDate is greater than SMALLER_CONSENT_DATE
        defaultConsentShouldBeFound("consentDate.greaterThan=" + SMALLER_CONSENT_DATE);
    }


    @Test
    @Transactional
    public void getAllConsentsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where createdDate equals to DEFAULT_CREATED_DATE
        defaultConsentShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the consentList where createdDate equals to UPDATED_CREATED_DATE
        defaultConsentShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultConsentShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the consentList where createdDate not equals to UPDATED_CREATED_DATE
        defaultConsentShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultConsentShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the consentList where createdDate equals to UPDATED_CREATED_DATE
        defaultConsentShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where createdDate is not null
        defaultConsentShouldBeFound("createdDate.specified=true");

        // Get all the consentList where createdDate is null
        defaultConsentShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsentsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultConsentShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the consentList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultConsentShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultConsentShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the consentList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultConsentShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where createdDate is less than DEFAULT_CREATED_DATE
        defaultConsentShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the consentList where createdDate is less than UPDATED_CREATED_DATE
        defaultConsentShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultConsentShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the consentList where createdDate is greater than SMALLER_CREATED_DATE
        defaultConsentShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllConsentsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultConsentShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the consentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultConsentShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultConsentShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the consentList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultConsentShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultConsentShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the consentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultConsentShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where lastUpdatedDate is not null
        defaultConsentShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the consentList where lastUpdatedDate is null
        defaultConsentShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsentsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultConsentShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the consentList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultConsentShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultConsentShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the consentList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultConsentShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultConsentShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the consentList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultConsentShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllConsentsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultConsentShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the consentList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultConsentShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllConsentsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where clientId equals to DEFAULT_CLIENT_ID
        defaultConsentShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the consentList where clientId equals to UPDATED_CLIENT_ID
        defaultConsentShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllConsentsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where clientId not equals to DEFAULT_CLIENT_ID
        defaultConsentShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the consentList where clientId not equals to UPDATED_CLIENT_ID
        defaultConsentShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllConsentsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultConsentShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the consentList where clientId equals to UPDATED_CLIENT_ID
        defaultConsentShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllConsentsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where clientId is not null
        defaultConsentShouldBeFound("clientId.specified=true");

        // Get all the consentList where clientId is null
        defaultConsentShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsentsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultConsentShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the consentList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultConsentShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllConsentsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultConsentShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the consentList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultConsentShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllConsentsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where clientId is less than DEFAULT_CLIENT_ID
        defaultConsentShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the consentList where clientId is less than UPDATED_CLIENT_ID
        defaultConsentShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllConsentsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where clientId is greater than DEFAULT_CLIENT_ID
        defaultConsentShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the consentList where clientId is greater than SMALLER_CLIENT_ID
        defaultConsentShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllConsentsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultConsentShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the consentList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultConsentShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllConsentsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultConsentShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the consentList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultConsentShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllConsentsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultConsentShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the consentList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultConsentShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllConsentsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        // Get all the consentList where hasExtraData is not null
        defaultConsentShouldBeFound("hasExtraData.specified=true");

        // Get all the consentList where hasExtraData is null
        defaultConsentShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsentsByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        consent.setServiceUser(serviceUser);
        consentRepository.saveAndFlush(consent);
        Long serviceUserId = serviceUser.getId();

        // Get all the consentList where serviceUser equals to serviceUserId
        defaultConsentShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the consentList where serviceUser equals to serviceUserId + 1
        defaultConsentShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }


    @Test
    @Transactional
    public void getAllConsentsByWitnessedByIsEqualToSomething() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);
        Employee witnessedBy = EmployeeResourceIT.createEntity(em);
        em.persist(witnessedBy);
        em.flush();
        consent.setWitnessedBy(witnessedBy);
        consentRepository.saveAndFlush(consent);
        Long witnessedById = witnessedBy.getId();

        // Get all the consentList where witnessedBy equals to witnessedById
        defaultConsentShouldBeFound("witnessedById.equals=" + witnessedById);

        // Get all the consentList where witnessedBy equals to witnessedById + 1
        defaultConsentShouldNotBeFound("witnessedById.equals=" + (witnessedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConsentShouldBeFound(String filter) throws Exception {
        restConsentMockMvc.perform(get("/api/consents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consent.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].giveConsent").value(hasItem(DEFAULT_GIVE_CONSENT.booleanValue())))
            .andExpect(jsonPath("$.[*].arrangements").value(hasItem(DEFAULT_ARRANGEMENTS)))
            .andExpect(jsonPath("$.[*].serviceUserSignature").value(hasItem(DEFAULT_SERVICE_USER_SIGNATURE)))
            .andExpect(jsonPath("$.[*].signatureImageContentType").value(hasItem(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signatureImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE_IMAGE))))
            .andExpect(jsonPath("$.[*].signatureImageUrl").value(hasItem(DEFAULT_SIGNATURE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].consentDate").value(hasItem(sameInstant(DEFAULT_CONSENT_DATE))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restConsentMockMvc.perform(get("/api/consents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConsentShouldNotBeFound(String filter) throws Exception {
        restConsentMockMvc.perform(get("/api/consents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConsentMockMvc.perform(get("/api/consents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingConsent() throws Exception {
        // Get the consent
        restConsentMockMvc.perform(get("/api/consents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsent() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        int databaseSizeBeforeUpdate = consentRepository.findAll().size();

        // Update the consent
        Consent updatedConsent = consentRepository.findById(consent.getId()).get();
        // Disconnect from session so that the updates on updatedConsent are not directly saved in db
        em.detach(updatedConsent);
        updatedConsent
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .giveConsent(UPDATED_GIVE_CONSENT)
            .arrangements(UPDATED_ARRANGEMENTS)
            .serviceUserSignature(UPDATED_SERVICE_USER_SIGNATURE)
            .signatureImage(UPDATED_SIGNATURE_IMAGE)
            .signatureImageContentType(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE)
            .signatureImageUrl(UPDATED_SIGNATURE_IMAGE_URL)
            .consentDate(UPDATED_CONSENT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        ConsentDTO consentDTO = consentMapper.toDto(updatedConsent);

        restConsentMockMvc.perform(put("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consentDTO)))
            .andExpect(status().isOk());

        // Validate the Consent in the database
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeUpdate);
        Consent testConsent = consentList.get(consentList.size() - 1);
        assertThat(testConsent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConsent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConsent.isGiveConsent()).isEqualTo(UPDATED_GIVE_CONSENT);
        assertThat(testConsent.getArrangements()).isEqualTo(UPDATED_ARRANGEMENTS);
        assertThat(testConsent.getServiceUserSignature()).isEqualTo(UPDATED_SERVICE_USER_SIGNATURE);
        assertThat(testConsent.getSignatureImage()).isEqualTo(UPDATED_SIGNATURE_IMAGE);
        assertThat(testConsent.getSignatureImageContentType()).isEqualTo(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
        assertThat(testConsent.getSignatureImageUrl()).isEqualTo(UPDATED_SIGNATURE_IMAGE_URL);
        assertThat(testConsent.getConsentDate()).isEqualTo(UPDATED_CONSENT_DATE);
        assertThat(testConsent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConsent.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testConsent.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testConsent.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingConsent() throws Exception {
        int databaseSizeBeforeUpdate = consentRepository.findAll().size();

        // Create the Consent
        ConsentDTO consentDTO = consentMapper.toDto(consent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsentMockMvc.perform(put("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consent in the database
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsent() throws Exception {
        // Initialize the database
        consentRepository.saveAndFlush(consent);

        int databaseSizeBeforeDelete = consentRepository.findAll().size();

        // Delete the consent
        restConsentMockMvc.perform(delete("/api/consents/{id}", consent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
