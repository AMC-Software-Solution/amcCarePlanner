package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Disability;
import com.amc.careplanner.domain.DisabilityType;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.DisabilityRepository;
import com.amc.careplanner.service.DisabilityService;
import com.amc.careplanner.service.dto.DisabilityDTO;
import com.amc.careplanner.service.mapper.DisabilityMapper;
import com.amc.careplanner.service.dto.DisabilityCriteria;
import com.amc.careplanner.service.DisabilityQueryService;

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
 * Integration tests for the {@link DisabilityResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DisabilityResourceIT {

    private static final Boolean DEFAULT_IS_DISABLED = false;
    private static final Boolean UPDATED_IS_DISABLED = true;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

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
    private DisabilityRepository disabilityRepository;

    @Autowired
    private DisabilityMapper disabilityMapper;

    @Autowired
    private DisabilityService disabilityService;

    @Autowired
    private DisabilityQueryService disabilityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisabilityMockMvc;

    private Disability disability;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disability createEntity(EntityManager em) {
        Disability disability = new Disability()
            .isDisabled(DEFAULT_IS_DISABLED)
            .note(DEFAULT_NOTE)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return disability;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disability createUpdatedEntity(EntityManager em) {
        Disability disability = new Disability()
            .isDisabled(UPDATED_IS_DISABLED)
            .note(UPDATED_NOTE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return disability;
    }

    @BeforeEach
    public void initTest() {
        disability = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisability() throws Exception {
        int databaseSizeBeforeCreate = disabilityRepository.findAll().size();
        // Create the Disability
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(disability);
        restDisabilityMockMvc.perform(post("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Disability in the database
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeCreate + 1);
        Disability testDisability = disabilityList.get(disabilityList.size() - 1);
        assertThat(testDisability.isIsDisabled()).isEqualTo(DEFAULT_IS_DISABLED);
        assertThat(testDisability.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testDisability.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDisability.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testDisability.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testDisability.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createDisabilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disabilityRepository.findAll().size();

        // Create the Disability with an existing ID
        disability.setId(1L);
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(disability);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisabilityMockMvc.perform(post("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disability in the database
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = disabilityRepository.findAll().size();
        // set the field null
        disability.setClientId(null);

        // Create the Disability, which fails.
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(disability);


        restDisabilityMockMvc.perform(post("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isBadRequest());

        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDisabilities() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList
        restDisabilityMockMvc.perform(get("/api/disabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disability.getId().intValue())))
            .andExpect(jsonPath("$.[*].isDisabled").value(hasItem(DEFAULT_IS_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDisability() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get the disability
        restDisabilityMockMvc.perform(get("/api/disabilities/{id}", disability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disability.getId().intValue()))
            .andExpect(jsonPath("$.isDisabled").value(DEFAULT_IS_DISABLED.booleanValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getDisabilitiesByIdFiltering() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        Long id = disability.getId();

        defaultDisabilityShouldBeFound("id.equals=" + id);
        defaultDisabilityShouldNotBeFound("id.notEquals=" + id);

        defaultDisabilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDisabilityShouldNotBeFound("id.greaterThan=" + id);

        defaultDisabilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDisabilityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByIsDisabledIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where isDisabled equals to DEFAULT_IS_DISABLED
        defaultDisabilityShouldBeFound("isDisabled.equals=" + DEFAULT_IS_DISABLED);

        // Get all the disabilityList where isDisabled equals to UPDATED_IS_DISABLED
        defaultDisabilityShouldNotBeFound("isDisabled.equals=" + UPDATED_IS_DISABLED);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByIsDisabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where isDisabled not equals to DEFAULT_IS_DISABLED
        defaultDisabilityShouldNotBeFound("isDisabled.notEquals=" + DEFAULT_IS_DISABLED);

        // Get all the disabilityList where isDisabled not equals to UPDATED_IS_DISABLED
        defaultDisabilityShouldBeFound("isDisabled.notEquals=" + UPDATED_IS_DISABLED);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByIsDisabledIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where isDisabled in DEFAULT_IS_DISABLED or UPDATED_IS_DISABLED
        defaultDisabilityShouldBeFound("isDisabled.in=" + DEFAULT_IS_DISABLED + "," + UPDATED_IS_DISABLED);

        // Get all the disabilityList where isDisabled equals to UPDATED_IS_DISABLED
        defaultDisabilityShouldNotBeFound("isDisabled.in=" + UPDATED_IS_DISABLED);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByIsDisabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where isDisabled is not null
        defaultDisabilityShouldBeFound("isDisabled.specified=true");

        // Get all the disabilityList where isDisabled is null
        defaultDisabilityShouldNotBeFound("isDisabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note equals to DEFAULT_NOTE
        defaultDisabilityShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the disabilityList where note equals to UPDATED_NOTE
        defaultDisabilityShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note not equals to DEFAULT_NOTE
        defaultDisabilityShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the disabilityList where note not equals to UPDATED_NOTE
        defaultDisabilityShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultDisabilityShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the disabilityList where note equals to UPDATED_NOTE
        defaultDisabilityShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note is not null
        defaultDisabilityShouldBeFound("note.specified=true");

        // Get all the disabilityList where note is null
        defaultDisabilityShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllDisabilitiesByNoteContainsSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note contains DEFAULT_NOTE
        defaultDisabilityShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the disabilityList where note contains UPDATED_NOTE
        defaultDisabilityShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note does not contain DEFAULT_NOTE
        defaultDisabilityShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the disabilityList where note does not contain UPDATED_NOTE
        defaultDisabilityShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where createdDate equals to DEFAULT_CREATED_DATE
        defaultDisabilityShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the disabilityList where createdDate equals to UPDATED_CREATED_DATE
        defaultDisabilityShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultDisabilityShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the disabilityList where createdDate not equals to UPDATED_CREATED_DATE
        defaultDisabilityShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultDisabilityShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the disabilityList where createdDate equals to UPDATED_CREATED_DATE
        defaultDisabilityShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where createdDate is not null
        defaultDisabilityShouldBeFound("createdDate.specified=true");

        // Get all the disabilityList where createdDate is null
        defaultDisabilityShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultDisabilityShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the disabilityList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultDisabilityShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultDisabilityShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the disabilityList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultDisabilityShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where createdDate is less than DEFAULT_CREATED_DATE
        defaultDisabilityShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the disabilityList where createdDate is less than UPDATED_CREATED_DATE
        defaultDisabilityShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultDisabilityShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the disabilityList where createdDate is greater than SMALLER_CREATED_DATE
        defaultDisabilityShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is not null
        defaultDisabilityShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the disabilityList where lastUpdatedDate is null
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where clientId equals to DEFAULT_CLIENT_ID
        defaultDisabilityShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the disabilityList where clientId equals to UPDATED_CLIENT_ID
        defaultDisabilityShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where clientId not equals to DEFAULT_CLIENT_ID
        defaultDisabilityShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the disabilityList where clientId not equals to UPDATED_CLIENT_ID
        defaultDisabilityShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultDisabilityShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the disabilityList where clientId equals to UPDATED_CLIENT_ID
        defaultDisabilityShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where clientId is not null
        defaultDisabilityShouldBeFound("clientId.specified=true");

        // Get all the disabilityList where clientId is null
        defaultDisabilityShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultDisabilityShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the disabilityList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultDisabilityShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultDisabilityShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the disabilityList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultDisabilityShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where clientId is less than DEFAULT_CLIENT_ID
        defaultDisabilityShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the disabilityList where clientId is less than UPDATED_CLIENT_ID
        defaultDisabilityShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where clientId is greater than DEFAULT_CLIENT_ID
        defaultDisabilityShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the disabilityList where clientId is greater than SMALLER_CLIENT_ID
        defaultDisabilityShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultDisabilityShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the disabilityList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultDisabilityShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultDisabilityShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the disabilityList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultDisabilityShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultDisabilityShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the disabilityList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultDisabilityShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where hasExtraData is not null
        defaultDisabilityShouldBeFound("hasExtraData.specified=true");

        // Get all the disabilityList where hasExtraData is null
        defaultDisabilityShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByDisabilityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);
        DisabilityType disabilityType = DisabilityTypeResourceIT.createEntity(em);
        em.persist(disabilityType);
        em.flush();
        disability.setDisabilityType(disabilityType);
        disabilityRepository.saveAndFlush(disability);
        Long disabilityTypeId = disabilityType.getId();

        // Get all the disabilityList where disabilityType equals to disabilityTypeId
        defaultDisabilityShouldBeFound("disabilityTypeId.equals=" + disabilityTypeId);

        // Get all the disabilityList where disabilityType equals to disabilityTypeId + 1
        defaultDisabilityShouldNotBeFound("disabilityTypeId.equals=" + (disabilityTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        disability.setEmployee(employee);
        disabilityRepository.saveAndFlush(disability);
        Long employeeId = employee.getId();

        // Get all the disabilityList where employee equals to employeeId
        defaultDisabilityShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the disabilityList where employee equals to employeeId + 1
        defaultDisabilityShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDisabilityShouldBeFound(String filter) throws Exception {
        restDisabilityMockMvc.perform(get("/api/disabilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disability.getId().intValue())))
            .andExpect(jsonPath("$.[*].isDisabled").value(hasItem(DEFAULT_IS_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restDisabilityMockMvc.perform(get("/api/disabilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDisabilityShouldNotBeFound(String filter) throws Exception {
        restDisabilityMockMvc.perform(get("/api/disabilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisabilityMockMvc.perform(get("/api/disabilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDisability() throws Exception {
        // Get the disability
        restDisabilityMockMvc.perform(get("/api/disabilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisability() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        int databaseSizeBeforeUpdate = disabilityRepository.findAll().size();

        // Update the disability
        Disability updatedDisability = disabilityRepository.findById(disability.getId()).get();
        // Disconnect from session so that the updates on updatedDisability are not directly saved in db
        em.detach(updatedDisability);
        updatedDisability
            .isDisabled(UPDATED_IS_DISABLED)
            .note(UPDATED_NOTE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(updatedDisability);

        restDisabilityMockMvc.perform(put("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isOk());

        // Validate the Disability in the database
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeUpdate);
        Disability testDisability = disabilityList.get(disabilityList.size() - 1);
        assertThat(testDisability.isIsDisabled()).isEqualTo(UPDATED_IS_DISABLED);
        assertThat(testDisability.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testDisability.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDisability.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testDisability.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testDisability.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingDisability() throws Exception {
        int databaseSizeBeforeUpdate = disabilityRepository.findAll().size();

        // Create the Disability
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(disability);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisabilityMockMvc.perform(put("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disability in the database
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisability() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        int databaseSizeBeforeDelete = disabilityRepository.findAll().size();

        // Delete the disability
        restDisabilityMockMvc.perform(delete("/api/disabilities/{id}", disability.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
