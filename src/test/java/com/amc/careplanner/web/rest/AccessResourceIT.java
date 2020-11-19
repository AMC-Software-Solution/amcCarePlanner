package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Access;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.AccessRepository;
import com.amc.careplanner.service.AccessService;
import com.amc.careplanner.service.dto.AccessDTO;
import com.amc.careplanner.service.mapper.AccessMapper;
import com.amc.careplanner.service.dto.AccessCriteria;
import com.amc.careplanner.service.AccessQueryService;

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
 * Integration tests for the {@link AccessResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccessResourceIT {

    private static final String DEFAULT_KEY_SAFE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_KEY_SAFE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_DETAILS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private AccessMapper accessMapper;

    @Autowired
    private AccessService accessService;

    @Autowired
    private AccessQueryService accessQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccessMockMvc;

    private Access access;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Access createEntity(EntityManager em) {
        Access access = new Access()
            .keySafeNumber(DEFAULT_KEY_SAFE_NUMBER)
            .accessDetails(DEFAULT_ACCESS_DETAILS)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return access;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Access createUpdatedEntity(EntityManager em) {
        Access access = new Access()
            .keySafeNumber(UPDATED_KEY_SAFE_NUMBER)
            .accessDetails(UPDATED_ACCESS_DETAILS)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return access;
    }

    @BeforeEach
    public void initTest() {
        access = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccess() throws Exception {
        int databaseSizeBeforeCreate = accessRepository.findAll().size();
        // Create the Access
        AccessDTO accessDTO = accessMapper.toDto(access);
        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isCreated());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeCreate + 1);
        Access testAccess = accessList.get(accessList.size() - 1);
        assertThat(testAccess.getKeySafeNumber()).isEqualTo(DEFAULT_KEY_SAFE_NUMBER);
        assertThat(testAccess.getAccessDetails()).isEqualTo(DEFAULT_ACCESS_DETAILS);
        assertThat(testAccess.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testAccess.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accessRepository.findAll().size();

        // Create the Access with an existing ID
        access.setId(1L);
        AccessDTO accessDTO = accessMapper.toDto(access);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeySafeNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = accessRepository.findAll().size();
        // set the field null
        access.setKeySafeNumber(null);

        // Create the Access, which fails.
        AccessDTO accessDTO = accessMapper.toDto(access);


        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isBadRequest());

        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = accessRepository.findAll().size();
        // set the field null
        access.setTenantId(null);

        // Create the Access, which fails.
        AccessDTO accessDTO = accessMapper.toDto(access);


        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isBadRequest());

        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccesses() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList
        restAccessMockMvc.perform(get("/api/accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(access.getId().intValue())))
            .andExpect(jsonPath("$.[*].keySafeNumber").value(hasItem(DEFAULT_KEY_SAFE_NUMBER)))
            .andExpect(jsonPath("$.[*].accessDetails").value(hasItem(DEFAULT_ACCESS_DETAILS)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getAccess() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get the access
        restAccessMockMvc.perform(get("/api/accesses/{id}", access.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(access.getId().intValue()))
            .andExpect(jsonPath("$.keySafeNumber").value(DEFAULT_KEY_SAFE_NUMBER))
            .andExpect(jsonPath("$.accessDetails").value(DEFAULT_ACCESS_DETAILS))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getAccessesByIdFiltering() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        Long id = access.getId();

        defaultAccessShouldBeFound("id.equals=" + id);
        defaultAccessShouldNotBeFound("id.notEquals=" + id);

        defaultAccessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccessShouldNotBeFound("id.greaterThan=" + id);

        defaultAccessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccessShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber equals to DEFAULT_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.equals=" + DEFAULT_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber equals to UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.equals=" + UPDATED_KEY_SAFE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber not equals to DEFAULT_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.notEquals=" + DEFAULT_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber not equals to UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.notEquals=" + UPDATED_KEY_SAFE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber in DEFAULT_KEY_SAFE_NUMBER or UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.in=" + DEFAULT_KEY_SAFE_NUMBER + "," + UPDATED_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber equals to UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.in=" + UPDATED_KEY_SAFE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber is not null
        defaultAccessShouldBeFound("keySafeNumber.specified=true");

        // Get all the accessList where keySafeNumber is null
        defaultAccessShouldNotBeFound("keySafeNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberContainsSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber contains DEFAULT_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.contains=" + DEFAULT_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber contains UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.contains=" + UPDATED_KEY_SAFE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccessesByKeySafeNumberNotContainsSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where keySafeNumber does not contain DEFAULT_KEY_SAFE_NUMBER
        defaultAccessShouldNotBeFound("keySafeNumber.doesNotContain=" + DEFAULT_KEY_SAFE_NUMBER);

        // Get all the accessList where keySafeNumber does not contain UPDATED_KEY_SAFE_NUMBER
        defaultAccessShouldBeFound("keySafeNumber.doesNotContain=" + UPDATED_KEY_SAFE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails equals to DEFAULT_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.equals=" + DEFAULT_ACCESS_DETAILS);

        // Get all the accessList where accessDetails equals to UPDATED_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.equals=" + UPDATED_ACCESS_DETAILS);
    }

    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails not equals to DEFAULT_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.notEquals=" + DEFAULT_ACCESS_DETAILS);

        // Get all the accessList where accessDetails not equals to UPDATED_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.notEquals=" + UPDATED_ACCESS_DETAILS);
    }

    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails in DEFAULT_ACCESS_DETAILS or UPDATED_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.in=" + DEFAULT_ACCESS_DETAILS + "," + UPDATED_ACCESS_DETAILS);

        // Get all the accessList where accessDetails equals to UPDATED_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.in=" + UPDATED_ACCESS_DETAILS);
    }

    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails is not null
        defaultAccessShouldBeFound("accessDetails.specified=true");

        // Get all the accessList where accessDetails is null
        defaultAccessShouldNotBeFound("accessDetails.specified=false");
    }
                @Test
    @Transactional
    public void getAllAccessesByAccessDetailsContainsSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails contains DEFAULT_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.contains=" + DEFAULT_ACCESS_DETAILS);

        // Get all the accessList where accessDetails contains UPDATED_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.contains=" + UPDATED_ACCESS_DETAILS);
    }

    @Test
    @Transactional
    public void getAllAccessesByAccessDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where accessDetails does not contain DEFAULT_ACCESS_DETAILS
        defaultAccessShouldNotBeFound("accessDetails.doesNotContain=" + DEFAULT_ACCESS_DETAILS);

        // Get all the accessList where accessDetails does not contain UPDATED_ACCESS_DETAILS
        defaultAccessShouldBeFound("accessDetails.doesNotContain=" + UPDATED_ACCESS_DETAILS);
    }


    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is not null
        defaultAccessShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the accessList where lastUpdatedDate is null
        defaultAccessShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAccessesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultAccessShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the accessList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultAccessShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllAccessesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where tenantId equals to DEFAULT_TENANT_ID
        defaultAccessShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the accessList where tenantId equals to UPDATED_TENANT_ID
        defaultAccessShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where tenantId not equals to DEFAULT_TENANT_ID
        defaultAccessShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the accessList where tenantId not equals to UPDATED_TENANT_ID
        defaultAccessShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultAccessShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the accessList where tenantId equals to UPDATED_TENANT_ID
        defaultAccessShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where tenantId is not null
        defaultAccessShouldBeFound("tenantId.specified=true");

        // Get all the accessList where tenantId is null
        defaultAccessShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccessesByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultAccessShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the accessList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultAccessShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultAccessShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the accessList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultAccessShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where tenantId is less than DEFAULT_TENANT_ID
        defaultAccessShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the accessList where tenantId is less than UPDATED_TENANT_ID
        defaultAccessShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAccessesByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accessList where tenantId is greater than DEFAULT_TENANT_ID
        defaultAccessShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the accessList where tenantId is greater than SMALLER_TENANT_ID
        defaultAccessShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllAccessesByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        access.setServiceUser(serviceUser);
        accessRepository.saveAndFlush(access);
        Long serviceUserId = serviceUser.getId();

        // Get all the accessList where serviceUser equals to serviceUserId
        defaultAccessShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the accessList where serviceUser equals to serviceUserId + 1
        defaultAccessShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccessShouldBeFound(String filter) throws Exception {
        restAccessMockMvc.perform(get("/api/accesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(access.getId().intValue())))
            .andExpect(jsonPath("$.[*].keySafeNumber").value(hasItem(DEFAULT_KEY_SAFE_NUMBER)))
            .andExpect(jsonPath("$.[*].accessDetails").value(hasItem(DEFAULT_ACCESS_DETAILS)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restAccessMockMvc.perform(get("/api/accesses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccessShouldNotBeFound(String filter) throws Exception {
        restAccessMockMvc.perform(get("/api/accesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccessMockMvc.perform(get("/api/accesses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAccess() throws Exception {
        // Get the access
        restAccessMockMvc.perform(get("/api/accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccess() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        int databaseSizeBeforeUpdate = accessRepository.findAll().size();

        // Update the access
        Access updatedAccess = accessRepository.findById(access.getId()).get();
        // Disconnect from session so that the updates on updatedAccess are not directly saved in db
        em.detach(updatedAccess);
        updatedAccess
            .keySafeNumber(UPDATED_KEY_SAFE_NUMBER)
            .accessDetails(UPDATED_ACCESS_DETAILS)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        AccessDTO accessDTO = accessMapper.toDto(updatedAccess);

        restAccessMockMvc.perform(put("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isOk());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeUpdate);
        Access testAccess = accessList.get(accessList.size() - 1);
        assertThat(testAccess.getKeySafeNumber()).isEqualTo(UPDATED_KEY_SAFE_NUMBER);
        assertThat(testAccess.getAccessDetails()).isEqualTo(UPDATED_ACCESS_DETAILS);
        assertThat(testAccess.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testAccess.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAccess() throws Exception {
        int databaseSizeBeforeUpdate = accessRepository.findAll().size();

        // Create the Access
        AccessDTO accessDTO = accessMapper.toDto(access);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccessMockMvc.perform(put("/api/accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccess() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        int databaseSizeBeforeDelete = accessRepository.findAll().size();

        // Delete the access
        restAccessMockMvc.perform(delete("/api/accesses/{id}", access.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
