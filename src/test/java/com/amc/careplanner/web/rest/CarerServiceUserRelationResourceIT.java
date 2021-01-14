package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.CarerServiceUserRelation;
import com.amc.careplanner.domain.RelationshipType;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.CarerServiceUserRelationRepository;
import com.amc.careplanner.service.CarerServiceUserRelationService;
import com.amc.careplanner.service.dto.CarerServiceUserRelationDTO;
import com.amc.careplanner.service.mapper.CarerServiceUserRelationMapper;
import com.amc.careplanner.service.dto.CarerServiceUserRelationCriteria;
import com.amc.careplanner.service.CarerServiceUserRelationQueryService;

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
 * Integration tests for the {@link CarerServiceUserRelationResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CarerServiceUserRelationResourceIT {

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;
    private static final Long SMALLER_COUNT = 1L - 1L;

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
    private CarerServiceUserRelationRepository carerServiceUserRelationRepository;

    @Autowired
    private CarerServiceUserRelationMapper carerServiceUserRelationMapper;

    @Autowired
    private CarerServiceUserRelationService carerServiceUserRelationService;

    @Autowired
    private CarerServiceUserRelationQueryService carerServiceUserRelationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarerServiceUserRelationMockMvc;

    private CarerServiceUserRelation carerServiceUserRelation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarerServiceUserRelation createEntity(EntityManager em) {
        CarerServiceUserRelation carerServiceUserRelation = new CarerServiceUserRelation()
            .reason(DEFAULT_REASON)
            .count(DEFAULT_COUNT)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return carerServiceUserRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarerServiceUserRelation createUpdatedEntity(EntityManager em) {
        CarerServiceUserRelation carerServiceUserRelation = new CarerServiceUserRelation()
            .reason(UPDATED_REASON)
            .count(UPDATED_COUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return carerServiceUserRelation;
    }

    @BeforeEach
    public void initTest() {
        carerServiceUserRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarerServiceUserRelation() throws Exception {
        int databaseSizeBeforeCreate = carerServiceUserRelationRepository.findAll().size();
        // Create the CarerServiceUserRelation
        CarerServiceUserRelationDTO carerServiceUserRelationDTO = carerServiceUserRelationMapper.toDto(carerServiceUserRelation);
        restCarerServiceUserRelationMockMvc.perform(post("/api/carer-service-user-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerServiceUserRelationDTO)))
            .andExpect(status().isCreated());

        // Validate the CarerServiceUserRelation in the database
        List<CarerServiceUserRelation> carerServiceUserRelationList = carerServiceUserRelationRepository.findAll();
        assertThat(carerServiceUserRelationList).hasSize(databaseSizeBeforeCreate + 1);
        CarerServiceUserRelation testCarerServiceUserRelation = carerServiceUserRelationList.get(carerServiceUserRelationList.size() - 1);
        assertThat(testCarerServiceUserRelation.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testCarerServiceUserRelation.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testCarerServiceUserRelation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCarerServiceUserRelation.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testCarerServiceUserRelation.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testCarerServiceUserRelation.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createCarerServiceUserRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carerServiceUserRelationRepository.findAll().size();

        // Create the CarerServiceUserRelation with an existing ID
        carerServiceUserRelation.setId(1L);
        CarerServiceUserRelationDTO carerServiceUserRelationDTO = carerServiceUserRelationMapper.toDto(carerServiceUserRelation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarerServiceUserRelationMockMvc.perform(post("/api/carer-service-user-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerServiceUserRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarerServiceUserRelation in the database
        List<CarerServiceUserRelation> carerServiceUserRelationList = carerServiceUserRelationRepository.findAll();
        assertThat(carerServiceUserRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = carerServiceUserRelationRepository.findAll().size();
        // set the field null
        carerServiceUserRelation.setClientId(null);

        // Create the CarerServiceUserRelation, which fails.
        CarerServiceUserRelationDTO carerServiceUserRelationDTO = carerServiceUserRelationMapper.toDto(carerServiceUserRelation);


        restCarerServiceUserRelationMockMvc.perform(post("/api/carer-service-user-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerServiceUserRelationDTO)))
            .andExpect(status().isBadRequest());

        List<CarerServiceUserRelation> carerServiceUserRelationList = carerServiceUserRelationRepository.findAll();
        assertThat(carerServiceUserRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelations() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList
        restCarerServiceUserRelationMockMvc.perform(get("/api/carer-service-user-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carerServiceUserRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCarerServiceUserRelation() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get the carerServiceUserRelation
        restCarerServiceUserRelationMockMvc.perform(get("/api/carer-service-user-relations/{id}", carerServiceUserRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carerServiceUserRelation.getId().intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getCarerServiceUserRelationsByIdFiltering() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        Long id = carerServiceUserRelation.getId();

        defaultCarerServiceUserRelationShouldBeFound("id.equals=" + id);
        defaultCarerServiceUserRelationShouldNotBeFound("id.notEquals=" + id);

        defaultCarerServiceUserRelationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCarerServiceUserRelationShouldNotBeFound("id.greaterThan=" + id);

        defaultCarerServiceUserRelationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCarerServiceUserRelationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where reason equals to DEFAULT_REASON
        defaultCarerServiceUserRelationShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the carerServiceUserRelationList where reason equals to UPDATED_REASON
        defaultCarerServiceUserRelationShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where reason not equals to DEFAULT_REASON
        defaultCarerServiceUserRelationShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the carerServiceUserRelationList where reason not equals to UPDATED_REASON
        defaultCarerServiceUserRelationShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultCarerServiceUserRelationShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the carerServiceUserRelationList where reason equals to UPDATED_REASON
        defaultCarerServiceUserRelationShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where reason is not null
        defaultCarerServiceUserRelationShouldBeFound("reason.specified=true");

        // Get all the carerServiceUserRelationList where reason is null
        defaultCarerServiceUserRelationShouldNotBeFound("reason.specified=false");
    }
                @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByReasonContainsSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where reason contains DEFAULT_REASON
        defaultCarerServiceUserRelationShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the carerServiceUserRelationList where reason contains UPDATED_REASON
        defaultCarerServiceUserRelationShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where reason does not contain DEFAULT_REASON
        defaultCarerServiceUserRelationShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the carerServiceUserRelationList where reason does not contain UPDATED_REASON
        defaultCarerServiceUserRelationShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }


    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where count equals to DEFAULT_COUNT
        defaultCarerServiceUserRelationShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the carerServiceUserRelationList where count equals to UPDATED_COUNT
        defaultCarerServiceUserRelationShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where count not equals to DEFAULT_COUNT
        defaultCarerServiceUserRelationShouldNotBeFound("count.notEquals=" + DEFAULT_COUNT);

        // Get all the carerServiceUserRelationList where count not equals to UPDATED_COUNT
        defaultCarerServiceUserRelationShouldBeFound("count.notEquals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCountIsInShouldWork() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultCarerServiceUserRelationShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the carerServiceUserRelationList where count equals to UPDATED_COUNT
        defaultCarerServiceUserRelationShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where count is not null
        defaultCarerServiceUserRelationShouldBeFound("count.specified=true");

        // Get all the carerServiceUserRelationList where count is null
        defaultCarerServiceUserRelationShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where count is greater than or equal to DEFAULT_COUNT
        defaultCarerServiceUserRelationShouldBeFound("count.greaterThanOrEqual=" + DEFAULT_COUNT);

        // Get all the carerServiceUserRelationList where count is greater than or equal to UPDATED_COUNT
        defaultCarerServiceUserRelationShouldNotBeFound("count.greaterThanOrEqual=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where count is less than or equal to DEFAULT_COUNT
        defaultCarerServiceUserRelationShouldBeFound("count.lessThanOrEqual=" + DEFAULT_COUNT);

        // Get all the carerServiceUserRelationList where count is less than or equal to SMALLER_COUNT
        defaultCarerServiceUserRelationShouldNotBeFound("count.lessThanOrEqual=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where count is less than DEFAULT_COUNT
        defaultCarerServiceUserRelationShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the carerServiceUserRelationList where count is less than UPDATED_COUNT
        defaultCarerServiceUserRelationShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where count is greater than DEFAULT_COUNT
        defaultCarerServiceUserRelationShouldNotBeFound("count.greaterThan=" + DEFAULT_COUNT);

        // Get all the carerServiceUserRelationList where count is greater than SMALLER_COUNT
        defaultCarerServiceUserRelationShouldBeFound("count.greaterThan=" + SMALLER_COUNT);
    }


    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where createdDate equals to DEFAULT_CREATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the carerServiceUserRelationList where createdDate equals to UPDATED_CREATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the carerServiceUserRelationList where createdDate not equals to UPDATED_CREATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the carerServiceUserRelationList where createdDate equals to UPDATED_CREATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where createdDate is not null
        defaultCarerServiceUserRelationShouldBeFound("createdDate.specified=true");

        // Get all the carerServiceUserRelationList where createdDate is null
        defaultCarerServiceUserRelationShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the carerServiceUserRelationList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the carerServiceUserRelationList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where createdDate is less than DEFAULT_CREATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the carerServiceUserRelationList where createdDate is less than UPDATED_CREATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the carerServiceUserRelationList where createdDate is greater than SMALLER_CREATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerServiceUserRelationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerServiceUserRelationList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the carerServiceUserRelationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where lastUpdatedDate is not null
        defaultCarerServiceUserRelationShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the carerServiceUserRelationList where lastUpdatedDate is null
        defaultCarerServiceUserRelationShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerServiceUserRelationList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerServiceUserRelationList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerServiceUserRelationList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerServiceUserRelationList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultCarerServiceUserRelationShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where clientId equals to DEFAULT_CLIENT_ID
        defaultCarerServiceUserRelationShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the carerServiceUserRelationList where clientId equals to UPDATED_CLIENT_ID
        defaultCarerServiceUserRelationShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where clientId not equals to DEFAULT_CLIENT_ID
        defaultCarerServiceUserRelationShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the carerServiceUserRelationList where clientId not equals to UPDATED_CLIENT_ID
        defaultCarerServiceUserRelationShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultCarerServiceUserRelationShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the carerServiceUserRelationList where clientId equals to UPDATED_CLIENT_ID
        defaultCarerServiceUserRelationShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where clientId is not null
        defaultCarerServiceUserRelationShouldBeFound("clientId.specified=true");

        // Get all the carerServiceUserRelationList where clientId is null
        defaultCarerServiceUserRelationShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultCarerServiceUserRelationShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the carerServiceUserRelationList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultCarerServiceUserRelationShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultCarerServiceUserRelationShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the carerServiceUserRelationList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultCarerServiceUserRelationShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where clientId is less than DEFAULT_CLIENT_ID
        defaultCarerServiceUserRelationShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the carerServiceUserRelationList where clientId is less than UPDATED_CLIENT_ID
        defaultCarerServiceUserRelationShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where clientId is greater than DEFAULT_CLIENT_ID
        defaultCarerServiceUserRelationShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the carerServiceUserRelationList where clientId is greater than SMALLER_CLIENT_ID
        defaultCarerServiceUserRelationShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultCarerServiceUserRelationShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the carerServiceUserRelationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultCarerServiceUserRelationShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultCarerServiceUserRelationShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the carerServiceUserRelationList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultCarerServiceUserRelationShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultCarerServiceUserRelationShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the carerServiceUserRelationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultCarerServiceUserRelationShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        // Get all the carerServiceUserRelationList where hasExtraData is not null
        defaultCarerServiceUserRelationShouldBeFound("hasExtraData.specified=true");

        // Get all the carerServiceUserRelationList where hasExtraData is null
        defaultCarerServiceUserRelationShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByRelationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);
        RelationshipType relationType = RelationshipTypeResourceIT.createEntity(em);
        em.persist(relationType);
        em.flush();
        carerServiceUserRelation.setRelationType(relationType);
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);
        Long relationTypeId = relationType.getId();

        // Get all the carerServiceUserRelationList where relationType equals to relationTypeId
        defaultCarerServiceUserRelationShouldBeFound("relationTypeId.equals=" + relationTypeId);

        // Get all the carerServiceUserRelationList where relationType equals to relationTypeId + 1
        defaultCarerServiceUserRelationShouldNotBeFound("relationTypeId.equals=" + (relationTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        carerServiceUserRelation.setEmployee(employee);
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);
        Long employeeId = employee.getId();

        // Get all the carerServiceUserRelationList where employee equals to employeeId
        defaultCarerServiceUserRelationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the carerServiceUserRelationList where employee equals to employeeId + 1
        defaultCarerServiceUserRelationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllCarerServiceUserRelationsByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        carerServiceUserRelation.setServiceUser(serviceUser);
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);
        Long serviceUserId = serviceUser.getId();

        // Get all the carerServiceUserRelationList where serviceUser equals to serviceUserId
        defaultCarerServiceUserRelationShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the carerServiceUserRelationList where serviceUser equals to serviceUserId + 1
        defaultCarerServiceUserRelationShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCarerServiceUserRelationShouldBeFound(String filter) throws Exception {
        restCarerServiceUserRelationMockMvc.perform(get("/api/carer-service-user-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carerServiceUserRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restCarerServiceUserRelationMockMvc.perform(get("/api/carer-service-user-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCarerServiceUserRelationShouldNotBeFound(String filter) throws Exception {
        restCarerServiceUserRelationMockMvc.perform(get("/api/carer-service-user-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCarerServiceUserRelationMockMvc.perform(get("/api/carer-service-user-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCarerServiceUserRelation() throws Exception {
        // Get the carerServiceUserRelation
        restCarerServiceUserRelationMockMvc.perform(get("/api/carer-service-user-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarerServiceUserRelation() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        int databaseSizeBeforeUpdate = carerServiceUserRelationRepository.findAll().size();

        // Update the carerServiceUserRelation
        CarerServiceUserRelation updatedCarerServiceUserRelation = carerServiceUserRelationRepository.findById(carerServiceUserRelation.getId()).get();
        // Disconnect from session so that the updates on updatedCarerServiceUserRelation are not directly saved in db
        em.detach(updatedCarerServiceUserRelation);
        updatedCarerServiceUserRelation
            .reason(UPDATED_REASON)
            .count(UPDATED_COUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        CarerServiceUserRelationDTO carerServiceUserRelationDTO = carerServiceUserRelationMapper.toDto(updatedCarerServiceUserRelation);

        restCarerServiceUserRelationMockMvc.perform(put("/api/carer-service-user-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerServiceUserRelationDTO)))
            .andExpect(status().isOk());

        // Validate the CarerServiceUserRelation in the database
        List<CarerServiceUserRelation> carerServiceUserRelationList = carerServiceUserRelationRepository.findAll();
        assertThat(carerServiceUserRelationList).hasSize(databaseSizeBeforeUpdate);
        CarerServiceUserRelation testCarerServiceUserRelation = carerServiceUserRelationList.get(carerServiceUserRelationList.size() - 1);
        assertThat(testCarerServiceUserRelation.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testCarerServiceUserRelation.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testCarerServiceUserRelation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCarerServiceUserRelation.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testCarerServiceUserRelation.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testCarerServiceUserRelation.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingCarerServiceUserRelation() throws Exception {
        int databaseSizeBeforeUpdate = carerServiceUserRelationRepository.findAll().size();

        // Create the CarerServiceUserRelation
        CarerServiceUserRelationDTO carerServiceUserRelationDTO = carerServiceUserRelationMapper.toDto(carerServiceUserRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarerServiceUserRelationMockMvc.perform(put("/api/carer-service-user-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerServiceUserRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarerServiceUserRelation in the database
        List<CarerServiceUserRelation> carerServiceUserRelationList = carerServiceUserRelationRepository.findAll();
        assertThat(carerServiceUserRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarerServiceUserRelation() throws Exception {
        // Initialize the database
        carerServiceUserRelationRepository.saveAndFlush(carerServiceUserRelation);

        int databaseSizeBeforeDelete = carerServiceUserRelationRepository.findAll().size();

        // Delete the carerServiceUserRelation
        restCarerServiceUserRelationMockMvc.perform(delete("/api/carer-service-user-relations/{id}", carerServiceUserRelation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarerServiceUserRelation> carerServiceUserRelationList = carerServiceUserRelationRepository.findAll();
        assertThat(carerServiceUserRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
