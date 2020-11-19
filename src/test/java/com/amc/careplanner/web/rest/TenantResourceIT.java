package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Tenant;
import com.amc.careplanner.repository.TenantRepository;
import com.amc.careplanner.service.TenantService;
import com.amc.careplanner.service.dto.TenantDTO;
import com.amc.careplanner.service.mapper.TenantMapper;
import com.amc.careplanner.service.dto.TenantCriteria;
import com.amc.careplanner.service.TenantQueryService;

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
 * Integration tests for the {@link TenantResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TenantResourceIT {

    private static final String DEFAULT_TENANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_TENANT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TENANT_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TENANT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TENANT_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TENANT_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_LOGO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantQueryService tenantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTenantMockMvc;

    private Tenant tenant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenant createEntity(EntityManager em) {
        Tenant tenant = new Tenant()
            .tenantName(DEFAULT_TENANT_NAME)
            .tenantDescription(DEFAULT_TENANT_DESCRIPTION)
            .tenantLogo(DEFAULT_TENANT_LOGO)
            .tenantLogoContentType(DEFAULT_TENANT_LOGO_CONTENT_TYPE)
            .tenantLogoUrl(DEFAULT_TENANT_LOGO_URL)
            .tenantContactName(DEFAULT_TENANT_CONTACT_NAME)
            .tenantPhone(DEFAULT_TENANT_PHONE)
            .tenantEmail(DEFAULT_TENANT_EMAIL)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE);
        return tenant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenant createUpdatedEntity(EntityManager em) {
        Tenant tenant = new Tenant()
            .tenantName(UPDATED_TENANT_NAME)
            .tenantDescription(UPDATED_TENANT_DESCRIPTION)
            .tenantLogo(UPDATED_TENANT_LOGO)
            .tenantLogoContentType(UPDATED_TENANT_LOGO_CONTENT_TYPE)
            .tenantLogoUrl(UPDATED_TENANT_LOGO_URL)
            .tenantContactName(UPDATED_TENANT_CONTACT_NAME)
            .tenantPhone(UPDATED_TENANT_PHONE)
            .tenantEmail(UPDATED_TENANT_EMAIL)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE);
        return tenant;
    }

    @BeforeEach
    public void initTest() {
        tenant = createEntity(em);
    }

    @Test
    @Transactional
    public void createTenant() throws Exception {
        int databaseSizeBeforeCreate = tenantRepository.findAll().size();
        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);
        restTenantMockMvc.perform(post("/api/tenants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDTO)))
            .andExpect(status().isCreated());

        // Validate the Tenant in the database
        List<Tenant> tenantList = tenantRepository.findAll();
        assertThat(tenantList).hasSize(databaseSizeBeforeCreate + 1);
        Tenant testTenant = tenantList.get(tenantList.size() - 1);
        assertThat(testTenant.getTenantName()).isEqualTo(DEFAULT_TENANT_NAME);
        assertThat(testTenant.getTenantDescription()).isEqualTo(DEFAULT_TENANT_DESCRIPTION);
        assertThat(testTenant.getTenantLogo()).isEqualTo(DEFAULT_TENANT_LOGO);
        assertThat(testTenant.getTenantLogoContentType()).isEqualTo(DEFAULT_TENANT_LOGO_CONTENT_TYPE);
        assertThat(testTenant.getTenantLogoUrl()).isEqualTo(DEFAULT_TENANT_LOGO_URL);
        assertThat(testTenant.getTenantContactName()).isEqualTo(DEFAULT_TENANT_CONTACT_NAME);
        assertThat(testTenant.getTenantPhone()).isEqualTo(DEFAULT_TENANT_PHONE);
        assertThat(testTenant.getTenantEmail()).isEqualTo(DEFAULT_TENANT_EMAIL);
        assertThat(testTenant.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTenant.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createTenantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tenantRepository.findAll().size();

        // Create the Tenant with an existing ID
        tenant.setId(1L);
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantMockMvc.perform(post("/api/tenants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        List<Tenant> tenantList = tenantRepository.findAll();
        assertThat(tenantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTenantNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantRepository.findAll().size();
        // set the field null
        tenant.setTenantName(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);


        restTenantMockMvc.perform(post("/api/tenants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        List<Tenant> tenantList = tenantRepository.findAll();
        assertThat(tenantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTenants() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList
        restTenantMockMvc.perform(get("/api/tenants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenant.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenantName").value(hasItem(DEFAULT_TENANT_NAME)))
            .andExpect(jsonPath("$.[*].tenantDescription").value(hasItem(DEFAULT_TENANT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].tenantLogoContentType").value(hasItem(DEFAULT_TENANT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].tenantLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_TENANT_LOGO))))
            .andExpect(jsonPath("$.[*].tenantLogoUrl").value(hasItem(DEFAULT_TENANT_LOGO_URL)))
            .andExpect(jsonPath("$.[*].tenantContactName").value(hasItem(DEFAULT_TENANT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].tenantPhone").value(hasItem(DEFAULT_TENANT_PHONE)))
            .andExpect(jsonPath("$.[*].tenantEmail").value(hasItem(DEFAULT_TENANT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getTenant() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get the tenant
        restTenantMockMvc.perform(get("/api/tenants/{id}", tenant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tenant.getId().intValue()))
            .andExpect(jsonPath("$.tenantName").value(DEFAULT_TENANT_NAME))
            .andExpect(jsonPath("$.tenantDescription").value(DEFAULT_TENANT_DESCRIPTION))
            .andExpect(jsonPath("$.tenantLogoContentType").value(DEFAULT_TENANT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.tenantLogo").value(Base64Utils.encodeToString(DEFAULT_TENANT_LOGO)))
            .andExpect(jsonPath("$.tenantLogoUrl").value(DEFAULT_TENANT_LOGO_URL))
            .andExpect(jsonPath("$.tenantContactName").value(DEFAULT_TENANT_CONTACT_NAME))
            .andExpect(jsonPath("$.tenantPhone").value(DEFAULT_TENANT_PHONE))
            .andExpect(jsonPath("$.tenantEmail").value(DEFAULT_TENANT_EMAIL))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)));
    }


    @Test
    @Transactional
    public void getTenantsByIdFiltering() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        Long id = tenant.getId();

        defaultTenantShouldBeFound("id.equals=" + id);
        defaultTenantShouldNotBeFound("id.notEquals=" + id);

        defaultTenantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTenantShouldNotBeFound("id.greaterThan=" + id);

        defaultTenantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTenantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTenantsByTenantNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantName equals to DEFAULT_TENANT_NAME
        defaultTenantShouldBeFound("tenantName.equals=" + DEFAULT_TENANT_NAME);

        // Get all the tenantList where tenantName equals to UPDATED_TENANT_NAME
        defaultTenantShouldNotBeFound("tenantName.equals=" + UPDATED_TENANT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantName not equals to DEFAULT_TENANT_NAME
        defaultTenantShouldNotBeFound("tenantName.notEquals=" + DEFAULT_TENANT_NAME);

        // Get all the tenantList where tenantName not equals to UPDATED_TENANT_NAME
        defaultTenantShouldBeFound("tenantName.notEquals=" + UPDATED_TENANT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantNameIsInShouldWork() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantName in DEFAULT_TENANT_NAME or UPDATED_TENANT_NAME
        defaultTenantShouldBeFound("tenantName.in=" + DEFAULT_TENANT_NAME + "," + UPDATED_TENANT_NAME);

        // Get all the tenantList where tenantName equals to UPDATED_TENANT_NAME
        defaultTenantShouldNotBeFound("tenantName.in=" + UPDATED_TENANT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantName is not null
        defaultTenantShouldBeFound("tenantName.specified=true");

        // Get all the tenantList where tenantName is null
        defaultTenantShouldNotBeFound("tenantName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantsByTenantNameContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantName contains DEFAULT_TENANT_NAME
        defaultTenantShouldBeFound("tenantName.contains=" + DEFAULT_TENANT_NAME);

        // Get all the tenantList where tenantName contains UPDATED_TENANT_NAME
        defaultTenantShouldNotBeFound("tenantName.contains=" + UPDATED_TENANT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantNameNotContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantName does not contain DEFAULT_TENANT_NAME
        defaultTenantShouldNotBeFound("tenantName.doesNotContain=" + DEFAULT_TENANT_NAME);

        // Get all the tenantList where tenantName does not contain UPDATED_TENANT_NAME
        defaultTenantShouldBeFound("tenantName.doesNotContain=" + UPDATED_TENANT_NAME);
    }


    @Test
    @Transactional
    public void getAllTenantsByTenantDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantDescription equals to DEFAULT_TENANT_DESCRIPTION
        defaultTenantShouldBeFound("tenantDescription.equals=" + DEFAULT_TENANT_DESCRIPTION);

        // Get all the tenantList where tenantDescription equals to UPDATED_TENANT_DESCRIPTION
        defaultTenantShouldNotBeFound("tenantDescription.equals=" + UPDATED_TENANT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantDescription not equals to DEFAULT_TENANT_DESCRIPTION
        defaultTenantShouldNotBeFound("tenantDescription.notEquals=" + DEFAULT_TENANT_DESCRIPTION);

        // Get all the tenantList where tenantDescription not equals to UPDATED_TENANT_DESCRIPTION
        defaultTenantShouldBeFound("tenantDescription.notEquals=" + UPDATED_TENANT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantDescription in DEFAULT_TENANT_DESCRIPTION or UPDATED_TENANT_DESCRIPTION
        defaultTenantShouldBeFound("tenantDescription.in=" + DEFAULT_TENANT_DESCRIPTION + "," + UPDATED_TENANT_DESCRIPTION);

        // Get all the tenantList where tenantDescription equals to UPDATED_TENANT_DESCRIPTION
        defaultTenantShouldNotBeFound("tenantDescription.in=" + UPDATED_TENANT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantDescription is not null
        defaultTenantShouldBeFound("tenantDescription.specified=true");

        // Get all the tenantList where tenantDescription is null
        defaultTenantShouldNotBeFound("tenantDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantsByTenantDescriptionContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantDescription contains DEFAULT_TENANT_DESCRIPTION
        defaultTenantShouldBeFound("tenantDescription.contains=" + DEFAULT_TENANT_DESCRIPTION);

        // Get all the tenantList where tenantDescription contains UPDATED_TENANT_DESCRIPTION
        defaultTenantShouldNotBeFound("tenantDescription.contains=" + UPDATED_TENANT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantDescription does not contain DEFAULT_TENANT_DESCRIPTION
        defaultTenantShouldNotBeFound("tenantDescription.doesNotContain=" + DEFAULT_TENANT_DESCRIPTION);

        // Get all the tenantList where tenantDescription does not contain UPDATED_TENANT_DESCRIPTION
        defaultTenantShouldBeFound("tenantDescription.doesNotContain=" + UPDATED_TENANT_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTenantsByTenantLogoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantLogoUrl equals to DEFAULT_TENANT_LOGO_URL
        defaultTenantShouldBeFound("tenantLogoUrl.equals=" + DEFAULT_TENANT_LOGO_URL);

        // Get all the tenantList where tenantLogoUrl equals to UPDATED_TENANT_LOGO_URL
        defaultTenantShouldNotBeFound("tenantLogoUrl.equals=" + UPDATED_TENANT_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantLogoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantLogoUrl not equals to DEFAULT_TENANT_LOGO_URL
        defaultTenantShouldNotBeFound("tenantLogoUrl.notEquals=" + DEFAULT_TENANT_LOGO_URL);

        // Get all the tenantList where tenantLogoUrl not equals to UPDATED_TENANT_LOGO_URL
        defaultTenantShouldBeFound("tenantLogoUrl.notEquals=" + UPDATED_TENANT_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantLogoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantLogoUrl in DEFAULT_TENANT_LOGO_URL or UPDATED_TENANT_LOGO_URL
        defaultTenantShouldBeFound("tenantLogoUrl.in=" + DEFAULT_TENANT_LOGO_URL + "," + UPDATED_TENANT_LOGO_URL);

        // Get all the tenantList where tenantLogoUrl equals to UPDATED_TENANT_LOGO_URL
        defaultTenantShouldNotBeFound("tenantLogoUrl.in=" + UPDATED_TENANT_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantLogoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantLogoUrl is not null
        defaultTenantShouldBeFound("tenantLogoUrl.specified=true");

        // Get all the tenantList where tenantLogoUrl is null
        defaultTenantShouldNotBeFound("tenantLogoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantsByTenantLogoUrlContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantLogoUrl contains DEFAULT_TENANT_LOGO_URL
        defaultTenantShouldBeFound("tenantLogoUrl.contains=" + DEFAULT_TENANT_LOGO_URL);

        // Get all the tenantList where tenantLogoUrl contains UPDATED_TENANT_LOGO_URL
        defaultTenantShouldNotBeFound("tenantLogoUrl.contains=" + UPDATED_TENANT_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantLogoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantLogoUrl does not contain DEFAULT_TENANT_LOGO_URL
        defaultTenantShouldNotBeFound("tenantLogoUrl.doesNotContain=" + DEFAULT_TENANT_LOGO_URL);

        // Get all the tenantList where tenantLogoUrl does not contain UPDATED_TENANT_LOGO_URL
        defaultTenantShouldBeFound("tenantLogoUrl.doesNotContain=" + UPDATED_TENANT_LOGO_URL);
    }


    @Test
    @Transactional
    public void getAllTenantsByTenantContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantContactName equals to DEFAULT_TENANT_CONTACT_NAME
        defaultTenantShouldBeFound("tenantContactName.equals=" + DEFAULT_TENANT_CONTACT_NAME);

        // Get all the tenantList where tenantContactName equals to UPDATED_TENANT_CONTACT_NAME
        defaultTenantShouldNotBeFound("tenantContactName.equals=" + UPDATED_TENANT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantContactNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantContactName not equals to DEFAULT_TENANT_CONTACT_NAME
        defaultTenantShouldNotBeFound("tenantContactName.notEquals=" + DEFAULT_TENANT_CONTACT_NAME);

        // Get all the tenantList where tenantContactName not equals to UPDATED_TENANT_CONTACT_NAME
        defaultTenantShouldBeFound("tenantContactName.notEquals=" + UPDATED_TENANT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantContactName in DEFAULT_TENANT_CONTACT_NAME or UPDATED_TENANT_CONTACT_NAME
        defaultTenantShouldBeFound("tenantContactName.in=" + DEFAULT_TENANT_CONTACT_NAME + "," + UPDATED_TENANT_CONTACT_NAME);

        // Get all the tenantList where tenantContactName equals to UPDATED_TENANT_CONTACT_NAME
        defaultTenantShouldNotBeFound("tenantContactName.in=" + UPDATED_TENANT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantContactName is not null
        defaultTenantShouldBeFound("tenantContactName.specified=true");

        // Get all the tenantList where tenantContactName is null
        defaultTenantShouldNotBeFound("tenantContactName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantsByTenantContactNameContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantContactName contains DEFAULT_TENANT_CONTACT_NAME
        defaultTenantShouldBeFound("tenantContactName.contains=" + DEFAULT_TENANT_CONTACT_NAME);

        // Get all the tenantList where tenantContactName contains UPDATED_TENANT_CONTACT_NAME
        defaultTenantShouldNotBeFound("tenantContactName.contains=" + UPDATED_TENANT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantContactName does not contain DEFAULT_TENANT_CONTACT_NAME
        defaultTenantShouldNotBeFound("tenantContactName.doesNotContain=" + DEFAULT_TENANT_CONTACT_NAME);

        // Get all the tenantList where tenantContactName does not contain UPDATED_TENANT_CONTACT_NAME
        defaultTenantShouldBeFound("tenantContactName.doesNotContain=" + UPDATED_TENANT_CONTACT_NAME);
    }


    @Test
    @Transactional
    public void getAllTenantsByTenantPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantPhone equals to DEFAULT_TENANT_PHONE
        defaultTenantShouldBeFound("tenantPhone.equals=" + DEFAULT_TENANT_PHONE);

        // Get all the tenantList where tenantPhone equals to UPDATED_TENANT_PHONE
        defaultTenantShouldNotBeFound("tenantPhone.equals=" + UPDATED_TENANT_PHONE);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantPhone not equals to DEFAULT_TENANT_PHONE
        defaultTenantShouldNotBeFound("tenantPhone.notEquals=" + DEFAULT_TENANT_PHONE);

        // Get all the tenantList where tenantPhone not equals to UPDATED_TENANT_PHONE
        defaultTenantShouldBeFound("tenantPhone.notEquals=" + UPDATED_TENANT_PHONE);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantPhone in DEFAULT_TENANT_PHONE or UPDATED_TENANT_PHONE
        defaultTenantShouldBeFound("tenantPhone.in=" + DEFAULT_TENANT_PHONE + "," + UPDATED_TENANT_PHONE);

        // Get all the tenantList where tenantPhone equals to UPDATED_TENANT_PHONE
        defaultTenantShouldNotBeFound("tenantPhone.in=" + UPDATED_TENANT_PHONE);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantPhone is not null
        defaultTenantShouldBeFound("tenantPhone.specified=true");

        // Get all the tenantList where tenantPhone is null
        defaultTenantShouldNotBeFound("tenantPhone.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantsByTenantPhoneContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantPhone contains DEFAULT_TENANT_PHONE
        defaultTenantShouldBeFound("tenantPhone.contains=" + DEFAULT_TENANT_PHONE);

        // Get all the tenantList where tenantPhone contains UPDATED_TENANT_PHONE
        defaultTenantShouldNotBeFound("tenantPhone.contains=" + UPDATED_TENANT_PHONE);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantPhone does not contain DEFAULT_TENANT_PHONE
        defaultTenantShouldNotBeFound("tenantPhone.doesNotContain=" + DEFAULT_TENANT_PHONE);

        // Get all the tenantList where tenantPhone does not contain UPDATED_TENANT_PHONE
        defaultTenantShouldBeFound("tenantPhone.doesNotContain=" + UPDATED_TENANT_PHONE);
    }


    @Test
    @Transactional
    public void getAllTenantsByTenantEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantEmail equals to DEFAULT_TENANT_EMAIL
        defaultTenantShouldBeFound("tenantEmail.equals=" + DEFAULT_TENANT_EMAIL);

        // Get all the tenantList where tenantEmail equals to UPDATED_TENANT_EMAIL
        defaultTenantShouldNotBeFound("tenantEmail.equals=" + UPDATED_TENANT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantEmail not equals to DEFAULT_TENANT_EMAIL
        defaultTenantShouldNotBeFound("tenantEmail.notEquals=" + DEFAULT_TENANT_EMAIL);

        // Get all the tenantList where tenantEmail not equals to UPDATED_TENANT_EMAIL
        defaultTenantShouldBeFound("tenantEmail.notEquals=" + UPDATED_TENANT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantEmailIsInShouldWork() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantEmail in DEFAULT_TENANT_EMAIL or UPDATED_TENANT_EMAIL
        defaultTenantShouldBeFound("tenantEmail.in=" + DEFAULT_TENANT_EMAIL + "," + UPDATED_TENANT_EMAIL);

        // Get all the tenantList where tenantEmail equals to UPDATED_TENANT_EMAIL
        defaultTenantShouldNotBeFound("tenantEmail.in=" + UPDATED_TENANT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantEmail is not null
        defaultTenantShouldBeFound("tenantEmail.specified=true");

        // Get all the tenantList where tenantEmail is null
        defaultTenantShouldNotBeFound("tenantEmail.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantsByTenantEmailContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantEmail contains DEFAULT_TENANT_EMAIL
        defaultTenantShouldBeFound("tenantEmail.contains=" + DEFAULT_TENANT_EMAIL);

        // Get all the tenantList where tenantEmail contains UPDATED_TENANT_EMAIL
        defaultTenantShouldNotBeFound("tenantEmail.contains=" + UPDATED_TENANT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTenantsByTenantEmailNotContainsSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where tenantEmail does not contain DEFAULT_TENANT_EMAIL
        defaultTenantShouldNotBeFound("tenantEmail.doesNotContain=" + DEFAULT_TENANT_EMAIL);

        // Get all the tenantList where tenantEmail does not contain UPDATED_TENANT_EMAIL
        defaultTenantShouldBeFound("tenantEmail.doesNotContain=" + UPDATED_TENANT_EMAIL);
    }


    @Test
    @Transactional
    public void getAllTenantsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where createdDate equals to DEFAULT_CREATED_DATE
        defaultTenantShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the tenantList where createdDate equals to UPDATED_CREATED_DATE
        defaultTenantShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultTenantShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the tenantList where createdDate not equals to UPDATED_CREATED_DATE
        defaultTenantShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultTenantShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the tenantList where createdDate equals to UPDATED_CREATED_DATE
        defaultTenantShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where createdDate is not null
        defaultTenantShouldBeFound("createdDate.specified=true");

        // Get all the tenantList where createdDate is null
        defaultTenantShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTenantsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultTenantShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the tenantList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultTenantShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultTenantShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the tenantList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultTenantShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where createdDate is less than DEFAULT_CREATED_DATE
        defaultTenantShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the tenantList where createdDate is less than UPDATED_CREATED_DATE
        defaultTenantShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultTenantShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the tenantList where createdDate is greater than SMALLER_CREATED_DATE
        defaultTenantShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllTenantsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultTenantShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTenantShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultTenantShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultTenantShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultTenantShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the tenantList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTenantShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where lastUpdatedDate is not null
        defaultTenantShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the tenantList where lastUpdatedDate is null
        defaultTenantShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTenantsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTenantShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultTenantShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTenantShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultTenantShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultTenantShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultTenantShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultTenantShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultTenantShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTenantShouldBeFound(String filter) throws Exception {
        restTenantMockMvc.perform(get("/api/tenants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenant.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenantName").value(hasItem(DEFAULT_TENANT_NAME)))
            .andExpect(jsonPath("$.[*].tenantDescription").value(hasItem(DEFAULT_TENANT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].tenantLogoContentType").value(hasItem(DEFAULT_TENANT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].tenantLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_TENANT_LOGO))))
            .andExpect(jsonPath("$.[*].tenantLogoUrl").value(hasItem(DEFAULT_TENANT_LOGO_URL)))
            .andExpect(jsonPath("$.[*].tenantContactName").value(hasItem(DEFAULT_TENANT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].tenantPhone").value(hasItem(DEFAULT_TENANT_PHONE)))
            .andExpect(jsonPath("$.[*].tenantEmail").value(hasItem(DEFAULT_TENANT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));

        // Check, that the count call also returns 1
        restTenantMockMvc.perform(get("/api/tenants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTenantShouldNotBeFound(String filter) throws Exception {
        restTenantMockMvc.perform(get("/api/tenants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTenantMockMvc.perform(get("/api/tenants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTenant() throws Exception {
        // Get the tenant
        restTenantMockMvc.perform(get("/api/tenants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTenant() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        int databaseSizeBeforeUpdate = tenantRepository.findAll().size();

        // Update the tenant
        Tenant updatedTenant = tenantRepository.findById(tenant.getId()).get();
        // Disconnect from session so that the updates on updatedTenant are not directly saved in db
        em.detach(updatedTenant);
        updatedTenant
            .tenantName(UPDATED_TENANT_NAME)
            .tenantDescription(UPDATED_TENANT_DESCRIPTION)
            .tenantLogo(UPDATED_TENANT_LOGO)
            .tenantLogoContentType(UPDATED_TENANT_LOGO_CONTENT_TYPE)
            .tenantLogoUrl(UPDATED_TENANT_LOGO_URL)
            .tenantContactName(UPDATED_TENANT_CONTACT_NAME)
            .tenantPhone(UPDATED_TENANT_PHONE)
            .tenantEmail(UPDATED_TENANT_EMAIL)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE);
        TenantDTO tenantDTO = tenantMapper.toDto(updatedTenant);

        restTenantMockMvc.perform(put("/api/tenants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDTO)))
            .andExpect(status().isOk());

        // Validate the Tenant in the database
        List<Tenant> tenantList = tenantRepository.findAll();
        assertThat(tenantList).hasSize(databaseSizeBeforeUpdate);
        Tenant testTenant = tenantList.get(tenantList.size() - 1);
        assertThat(testTenant.getTenantName()).isEqualTo(UPDATED_TENANT_NAME);
        assertThat(testTenant.getTenantDescription()).isEqualTo(UPDATED_TENANT_DESCRIPTION);
        assertThat(testTenant.getTenantLogo()).isEqualTo(UPDATED_TENANT_LOGO);
        assertThat(testTenant.getTenantLogoContentType()).isEqualTo(UPDATED_TENANT_LOGO_CONTENT_TYPE);
        assertThat(testTenant.getTenantLogoUrl()).isEqualTo(UPDATED_TENANT_LOGO_URL);
        assertThat(testTenant.getTenantContactName()).isEqualTo(UPDATED_TENANT_CONTACT_NAME);
        assertThat(testTenant.getTenantPhone()).isEqualTo(UPDATED_TENANT_PHONE);
        assertThat(testTenant.getTenantEmail()).isEqualTo(UPDATED_TENANT_EMAIL);
        assertThat(testTenant.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTenant.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTenant() throws Exception {
        int databaseSizeBeforeUpdate = tenantRepository.findAll().size();

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenantMockMvc.perform(put("/api/tenants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        List<Tenant> tenantList = tenantRepository.findAll();
        assertThat(tenantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTenant() throws Exception {
        // Initialize the database
        tenantRepository.saveAndFlush(tenant);

        int databaseSizeBeforeDelete = tenantRepository.findAll().size();

        // Delete the tenant
        restTenantMockMvc.perform(delete("/api/tenants/{id}", tenant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tenant> tenantList = tenantRepository.findAll();
        assertThat(tenantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
