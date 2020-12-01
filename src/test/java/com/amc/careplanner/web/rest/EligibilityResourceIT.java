package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Eligibility;
import com.amc.careplanner.domain.EligibilityType;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.EligibilityRepository;
import com.amc.careplanner.service.EligibilityService;
import com.amc.careplanner.service.dto.EligibilityDTO;
import com.amc.careplanner.service.mapper.EligibilityMapper;
import com.amc.careplanner.service.dto.EligibilityCriteria;
import com.amc.careplanner.service.EligibilityQueryService;

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
 * Integration tests for the {@link EligibilityResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EligibilityResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    @Autowired
    private EligibilityRepository eligibilityRepository;

    @Autowired
    private EligibilityMapper eligibilityMapper;

    @Autowired
    private EligibilityService eligibilityService;

    @Autowired
    private EligibilityQueryService eligibilityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEligibilityMockMvc;

    private Eligibility eligibility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eligibility createEntity(EntityManager em) {
        Eligibility eligibility = new Eligibility()
            .note(DEFAULT_NOTE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID);
        return eligibility;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eligibility createUpdatedEntity(EntityManager em) {
        Eligibility eligibility = new Eligibility()
            .note(UPDATED_NOTE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        return eligibility;
    }

    @BeforeEach
    public void initTest() {
        eligibility = createEntity(em);
    }

    @Test
    @Transactional
    public void createEligibility() throws Exception {
        int databaseSizeBeforeCreate = eligibilityRepository.findAll().size();
        // Create the Eligibility
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);
        restEligibilityMockMvc.perform(post("/api/eligibilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeCreate + 1);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testEligibility.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testEligibility.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void createEligibilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eligibilityRepository.findAll().size();

        // Create the Eligibility with an existing ID
        eligibility.setId(1L);
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEligibilityMockMvc.perform(post("/api/eligibilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = eligibilityRepository.findAll().size();
        // set the field null
        eligibility.setClientId(null);

        // Create the Eligibility, which fails.
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);


        restEligibilityMockMvc.perform(post("/api/eligibilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO)))
            .andExpect(status().isBadRequest());

        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEligibilities() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList
        restEligibilityMockMvc.perform(get("/api/eligibilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getEligibility() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get the eligibility
        restEligibilityMockMvc.perform(get("/api/eligibilities/{id}", eligibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eligibility.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getEligibilitiesByIdFiltering() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        Long id = eligibility.getId();

        defaultEligibilityShouldBeFound("id.equals=" + id);
        defaultEligibilityShouldNotBeFound("id.notEquals=" + id);

        defaultEligibilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEligibilityShouldNotBeFound("id.greaterThan=" + id);

        defaultEligibilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEligibilityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEligibilitiesByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where note equals to DEFAULT_NOTE
        defaultEligibilityShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the eligibilityList where note equals to UPDATED_NOTE
        defaultEligibilityShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where note not equals to DEFAULT_NOTE
        defaultEligibilityShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the eligibilityList where note not equals to UPDATED_NOTE
        defaultEligibilityShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultEligibilityShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the eligibilityList where note equals to UPDATED_NOTE
        defaultEligibilityShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where note is not null
        defaultEligibilityShouldBeFound("note.specified=true");

        // Get all the eligibilityList where note is null
        defaultEligibilityShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllEligibilitiesByNoteContainsSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where note contains DEFAULT_NOTE
        defaultEligibilityShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the eligibilityList where note contains UPDATED_NOTE
        defaultEligibilityShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where note does not contain DEFAULT_NOTE
        defaultEligibilityShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the eligibilityList where note does not contain UPDATED_NOTE
        defaultEligibilityShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllEligibilitiesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultEligibilityShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the eligibilityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEligibilityShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultEligibilityShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the eligibilityList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultEligibilityShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultEligibilityShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the eligibilityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEligibilityShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where lastUpdatedDate is not null
        defaultEligibilityShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the eligibilityList where lastUpdatedDate is null
        defaultEligibilityShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEligibilityShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the eligibilityList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultEligibilityShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEligibilityShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the eligibilityList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultEligibilityShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultEligibilityShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the eligibilityList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultEligibilityShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultEligibilityShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the eligibilityList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultEligibilityShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEligibilitiesByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where clientId equals to DEFAULT_CLIENT_ID
        defaultEligibilityShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the eligibilityList where clientId equals to UPDATED_CLIENT_ID
        defaultEligibilityShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where clientId not equals to DEFAULT_CLIENT_ID
        defaultEligibilityShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the eligibilityList where clientId not equals to UPDATED_CLIENT_ID
        defaultEligibilityShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultEligibilityShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the eligibilityList where clientId equals to UPDATED_CLIENT_ID
        defaultEligibilityShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where clientId is not null
        defaultEligibilityShouldBeFound("clientId.specified=true");

        // Get all the eligibilityList where clientId is null
        defaultEligibilityShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultEligibilityShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the eligibilityList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultEligibilityShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultEligibilityShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the eligibilityList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultEligibilityShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where clientId is less than DEFAULT_CLIENT_ID
        defaultEligibilityShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the eligibilityList where clientId is less than UPDATED_CLIENT_ID
        defaultEligibilityShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilitiesByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where clientId is greater than DEFAULT_CLIENT_ID
        defaultEligibilityShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the eligibilityList where clientId is greater than SMALLER_CLIENT_ID
        defaultEligibilityShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllEligibilitiesByEligibilityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);
        EligibilityType eligibilityType = EligibilityTypeResourceIT.createEntity(em);
        em.persist(eligibilityType);
        em.flush();
        eligibility.setEligibilityType(eligibilityType);
        eligibilityRepository.saveAndFlush(eligibility);
        Long eligibilityTypeId = eligibilityType.getId();

        // Get all the eligibilityList where eligibilityType equals to eligibilityTypeId
        defaultEligibilityShouldBeFound("eligibilityTypeId.equals=" + eligibilityTypeId);

        // Get all the eligibilityList where eligibilityType equals to eligibilityTypeId + 1
        defaultEligibilityShouldNotBeFound("eligibilityTypeId.equals=" + (eligibilityTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllEligibilitiesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        eligibility.setEmployee(employee);
        eligibilityRepository.saveAndFlush(eligibility);
        Long employeeId = employee.getId();

        // Get all the eligibilityList where employee equals to employeeId
        defaultEligibilityShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the eligibilityList where employee equals to employeeId + 1
        defaultEligibilityShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEligibilityShouldBeFound(String filter) throws Exception {
        restEligibilityMockMvc.perform(get("/api/eligibilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));

        // Check, that the count call also returns 1
        restEligibilityMockMvc.perform(get("/api/eligibilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEligibilityShouldNotBeFound(String filter) throws Exception {
        restEligibilityMockMvc.perform(get("/api/eligibilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEligibilityMockMvc.perform(get("/api/eligibilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEligibility() throws Exception {
        // Get the eligibility
        restEligibilityMockMvc.perform(get("/api/eligibilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEligibility() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();

        // Update the eligibility
        Eligibility updatedEligibility = eligibilityRepository.findById(eligibility.getId()).get();
        // Disconnect from session so that the updates on updatedEligibility are not directly saved in db
        em.detach(updatedEligibility);
        updatedEligibility
            .note(UPDATED_NOTE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(updatedEligibility);

        restEligibilityMockMvc.perform(put("/api/eligibilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO)))
            .andExpect(status().isOk());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testEligibility.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testEligibility.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();

        // Create the Eligibility
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibilityMockMvc.perform(put("/api/eligibilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEligibility() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeDelete = eligibilityRepository.findAll().size();

        // Delete the eligibility
        restEligibilityMockMvc.perform(delete("/api/eligibilities/{id}", eligibility.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
