package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.CarerClientRelation;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.CarerClientRelationRepository;
import com.amc.careplanner.service.CarerClientRelationService;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;
import com.amc.careplanner.service.mapper.CarerClientRelationMapper;
import com.amc.careplanner.service.dto.CarerClientRelationCriteria;
import com.amc.careplanner.service.CarerClientRelationQueryService;

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

import com.amc.careplanner.domain.enumeration.RelationType;
/**
 * Integration tests for the {@link CarerClientRelationResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CarerClientRelationResourceIT {

    private static final RelationType DEFAULT_RELATION_TYPE = RelationType.CLIENT_UNFAVOURS_EMPLOYEE;
    private static final RelationType UPDATED_RELATION_TYPE = RelationType.EMPLOYEE_VISITED_CLIENT_BEFORE;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;
    private static final Long SMALLER_COUNT = 1L - 1L;

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    @Autowired
    private CarerClientRelationRepository carerClientRelationRepository;

    @Autowired
    private CarerClientRelationMapper carerClientRelationMapper;

    @Autowired
    private CarerClientRelationService carerClientRelationService;

    @Autowired
    private CarerClientRelationQueryService carerClientRelationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarerClientRelationMockMvc;

    private CarerClientRelation carerClientRelation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarerClientRelation createEntity(EntityManager em) {
        CarerClientRelation carerClientRelation = new CarerClientRelation()
            .relationType(DEFAULT_RELATION_TYPE)
            .reason(DEFAULT_REASON)
            .count(DEFAULT_COUNT)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID);
        return carerClientRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarerClientRelation createUpdatedEntity(EntityManager em) {
        CarerClientRelation carerClientRelation = new CarerClientRelation()
            .relationType(UPDATED_RELATION_TYPE)
            .reason(UPDATED_REASON)
            .count(UPDATED_COUNT)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        return carerClientRelation;
    }

    @BeforeEach
    public void initTest() {
        carerClientRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarerClientRelation() throws Exception {
        int databaseSizeBeforeCreate = carerClientRelationRepository.findAll().size();
        // Create the CarerClientRelation
        CarerClientRelationDTO carerClientRelationDTO = carerClientRelationMapper.toDto(carerClientRelation);
        restCarerClientRelationMockMvc.perform(post("/api/carer-client-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerClientRelationDTO)))
            .andExpect(status().isCreated());

        // Validate the CarerClientRelation in the database
        List<CarerClientRelation> carerClientRelationList = carerClientRelationRepository.findAll();
        assertThat(carerClientRelationList).hasSize(databaseSizeBeforeCreate + 1);
        CarerClientRelation testCarerClientRelation = carerClientRelationList.get(carerClientRelationList.size() - 1);
        assertThat(testCarerClientRelation.getRelationType()).isEqualTo(DEFAULT_RELATION_TYPE);
        assertThat(testCarerClientRelation.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testCarerClientRelation.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testCarerClientRelation.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testCarerClientRelation.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void createCarerClientRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carerClientRelationRepository.findAll().size();

        // Create the CarerClientRelation with an existing ID
        carerClientRelation.setId(1L);
        CarerClientRelationDTO carerClientRelationDTO = carerClientRelationMapper.toDto(carerClientRelation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarerClientRelationMockMvc.perform(post("/api/carer-client-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerClientRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarerClientRelation in the database
        List<CarerClientRelation> carerClientRelationList = carerClientRelationRepository.findAll();
        assertThat(carerClientRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRelationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carerClientRelationRepository.findAll().size();
        // set the field null
        carerClientRelation.setRelationType(null);

        // Create the CarerClientRelation, which fails.
        CarerClientRelationDTO carerClientRelationDTO = carerClientRelationMapper.toDto(carerClientRelation);


        restCarerClientRelationMockMvc.perform(post("/api/carer-client-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerClientRelationDTO)))
            .andExpect(status().isBadRequest());

        List<CarerClientRelation> carerClientRelationList = carerClientRelationRepository.findAll();
        assertThat(carerClientRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = carerClientRelationRepository.findAll().size();
        // set the field null
        carerClientRelation.setClientId(null);

        // Create the CarerClientRelation, which fails.
        CarerClientRelationDTO carerClientRelationDTO = carerClientRelationMapper.toDto(carerClientRelation);


        restCarerClientRelationMockMvc.perform(post("/api/carer-client-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerClientRelationDTO)))
            .andExpect(status().isBadRequest());

        List<CarerClientRelation> carerClientRelationList = carerClientRelationRepository.findAll();
        assertThat(carerClientRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelations() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList
        restCarerClientRelationMockMvc.perform(get("/api/carer-client-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carerClientRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].relationType").value(hasItem(DEFAULT_RELATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getCarerClientRelation() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get the carerClientRelation
        restCarerClientRelationMockMvc.perform(get("/api/carer-client-relations/{id}", carerClientRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carerClientRelation.getId().intValue()))
            .andExpect(jsonPath("$.relationType").value(DEFAULT_RELATION_TYPE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getCarerClientRelationsByIdFiltering() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        Long id = carerClientRelation.getId();

        defaultCarerClientRelationShouldBeFound("id.equals=" + id);
        defaultCarerClientRelationShouldNotBeFound("id.notEquals=" + id);

        defaultCarerClientRelationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCarerClientRelationShouldNotBeFound("id.greaterThan=" + id);

        defaultCarerClientRelationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCarerClientRelationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCarerClientRelationsByRelationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where relationType equals to DEFAULT_RELATION_TYPE
        defaultCarerClientRelationShouldBeFound("relationType.equals=" + DEFAULT_RELATION_TYPE);

        // Get all the carerClientRelationList where relationType equals to UPDATED_RELATION_TYPE
        defaultCarerClientRelationShouldNotBeFound("relationType.equals=" + UPDATED_RELATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByRelationTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where relationType not equals to DEFAULT_RELATION_TYPE
        defaultCarerClientRelationShouldNotBeFound("relationType.notEquals=" + DEFAULT_RELATION_TYPE);

        // Get all the carerClientRelationList where relationType not equals to UPDATED_RELATION_TYPE
        defaultCarerClientRelationShouldBeFound("relationType.notEquals=" + UPDATED_RELATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByRelationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where relationType in DEFAULT_RELATION_TYPE or UPDATED_RELATION_TYPE
        defaultCarerClientRelationShouldBeFound("relationType.in=" + DEFAULT_RELATION_TYPE + "," + UPDATED_RELATION_TYPE);

        // Get all the carerClientRelationList where relationType equals to UPDATED_RELATION_TYPE
        defaultCarerClientRelationShouldNotBeFound("relationType.in=" + UPDATED_RELATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByRelationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where relationType is not null
        defaultCarerClientRelationShouldBeFound("relationType.specified=true");

        // Get all the carerClientRelationList where relationType is null
        defaultCarerClientRelationShouldNotBeFound("relationType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where reason equals to DEFAULT_REASON
        defaultCarerClientRelationShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the carerClientRelationList where reason equals to UPDATED_REASON
        defaultCarerClientRelationShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where reason not equals to DEFAULT_REASON
        defaultCarerClientRelationShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the carerClientRelationList where reason not equals to UPDATED_REASON
        defaultCarerClientRelationShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultCarerClientRelationShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the carerClientRelationList where reason equals to UPDATED_REASON
        defaultCarerClientRelationShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where reason is not null
        defaultCarerClientRelationShouldBeFound("reason.specified=true");

        // Get all the carerClientRelationList where reason is null
        defaultCarerClientRelationShouldNotBeFound("reason.specified=false");
    }
                @Test
    @Transactional
    public void getAllCarerClientRelationsByReasonContainsSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where reason contains DEFAULT_REASON
        defaultCarerClientRelationShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the carerClientRelationList where reason contains UPDATED_REASON
        defaultCarerClientRelationShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where reason does not contain DEFAULT_REASON
        defaultCarerClientRelationShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the carerClientRelationList where reason does not contain UPDATED_REASON
        defaultCarerClientRelationShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }


    @Test
    @Transactional
    public void getAllCarerClientRelationsByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where count equals to DEFAULT_COUNT
        defaultCarerClientRelationShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the carerClientRelationList where count equals to UPDATED_COUNT
        defaultCarerClientRelationShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where count not equals to DEFAULT_COUNT
        defaultCarerClientRelationShouldNotBeFound("count.notEquals=" + DEFAULT_COUNT);

        // Get all the carerClientRelationList where count not equals to UPDATED_COUNT
        defaultCarerClientRelationShouldBeFound("count.notEquals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByCountIsInShouldWork() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultCarerClientRelationShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the carerClientRelationList where count equals to UPDATED_COUNT
        defaultCarerClientRelationShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where count is not null
        defaultCarerClientRelationShouldBeFound("count.specified=true");

        // Get all the carerClientRelationList where count is null
        defaultCarerClientRelationShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where count is greater than or equal to DEFAULT_COUNT
        defaultCarerClientRelationShouldBeFound("count.greaterThanOrEqual=" + DEFAULT_COUNT);

        // Get all the carerClientRelationList where count is greater than or equal to UPDATED_COUNT
        defaultCarerClientRelationShouldNotBeFound("count.greaterThanOrEqual=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where count is less than or equal to DEFAULT_COUNT
        defaultCarerClientRelationShouldBeFound("count.lessThanOrEqual=" + DEFAULT_COUNT);

        // Get all the carerClientRelationList where count is less than or equal to SMALLER_COUNT
        defaultCarerClientRelationShouldNotBeFound("count.lessThanOrEqual=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where count is less than DEFAULT_COUNT
        defaultCarerClientRelationShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the carerClientRelationList where count is less than UPDATED_COUNT
        defaultCarerClientRelationShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where count is greater than DEFAULT_COUNT
        defaultCarerClientRelationShouldNotBeFound("count.greaterThan=" + DEFAULT_COUNT);

        // Get all the carerClientRelationList where count is greater than SMALLER_COUNT
        defaultCarerClientRelationShouldBeFound("count.greaterThan=" + SMALLER_COUNT);
    }


    @Test
    @Transactional
    public void getAllCarerClientRelationsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerClientRelationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerClientRelationList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the carerClientRelationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where lastUpdatedDate is not null
        defaultCarerClientRelationShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the carerClientRelationList where lastUpdatedDate is null
        defaultCarerClientRelationShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerClientRelationList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerClientRelationList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerClientRelationList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the carerClientRelationList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultCarerClientRelationShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllCarerClientRelationsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where clientId equals to DEFAULT_CLIENT_ID
        defaultCarerClientRelationShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the carerClientRelationList where clientId equals to UPDATED_CLIENT_ID
        defaultCarerClientRelationShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where clientId not equals to DEFAULT_CLIENT_ID
        defaultCarerClientRelationShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the carerClientRelationList where clientId not equals to UPDATED_CLIENT_ID
        defaultCarerClientRelationShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultCarerClientRelationShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the carerClientRelationList where clientId equals to UPDATED_CLIENT_ID
        defaultCarerClientRelationShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where clientId is not null
        defaultCarerClientRelationShouldBeFound("clientId.specified=true");

        // Get all the carerClientRelationList where clientId is null
        defaultCarerClientRelationShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultCarerClientRelationShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the carerClientRelationList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultCarerClientRelationShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultCarerClientRelationShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the carerClientRelationList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultCarerClientRelationShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where clientId is less than DEFAULT_CLIENT_ID
        defaultCarerClientRelationShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the carerClientRelationList where clientId is less than UPDATED_CLIENT_ID
        defaultCarerClientRelationShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCarerClientRelationsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        // Get all the carerClientRelationList where clientId is greater than DEFAULT_CLIENT_ID
        defaultCarerClientRelationShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the carerClientRelationList where clientId is greater than SMALLER_CLIENT_ID
        defaultCarerClientRelationShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllCarerClientRelationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        carerClientRelation.setEmployee(employee);
        carerClientRelationRepository.saveAndFlush(carerClientRelation);
        Long employeeId = employee.getId();

        // Get all the carerClientRelationList where employee equals to employeeId
        defaultCarerClientRelationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the carerClientRelationList where employee equals to employeeId + 1
        defaultCarerClientRelationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllCarerClientRelationsByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        carerClientRelation.setServiceUser(serviceUser);
        carerClientRelationRepository.saveAndFlush(carerClientRelation);
        Long serviceUserId = serviceUser.getId();

        // Get all the carerClientRelationList where serviceUser equals to serviceUserId
        defaultCarerClientRelationShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the carerClientRelationList where serviceUser equals to serviceUserId + 1
        defaultCarerClientRelationShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCarerClientRelationShouldBeFound(String filter) throws Exception {
        restCarerClientRelationMockMvc.perform(get("/api/carer-client-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carerClientRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].relationType").value(hasItem(DEFAULT_RELATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));

        // Check, that the count call also returns 1
        restCarerClientRelationMockMvc.perform(get("/api/carer-client-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCarerClientRelationShouldNotBeFound(String filter) throws Exception {
        restCarerClientRelationMockMvc.perform(get("/api/carer-client-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCarerClientRelationMockMvc.perform(get("/api/carer-client-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCarerClientRelation() throws Exception {
        // Get the carerClientRelation
        restCarerClientRelationMockMvc.perform(get("/api/carer-client-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarerClientRelation() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        int databaseSizeBeforeUpdate = carerClientRelationRepository.findAll().size();

        // Update the carerClientRelation
        CarerClientRelation updatedCarerClientRelation = carerClientRelationRepository.findById(carerClientRelation.getId()).get();
        // Disconnect from session so that the updates on updatedCarerClientRelation are not directly saved in db
        em.detach(updatedCarerClientRelation);
        updatedCarerClientRelation
            .relationType(UPDATED_RELATION_TYPE)
            .reason(UPDATED_REASON)
            .count(UPDATED_COUNT)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        CarerClientRelationDTO carerClientRelationDTO = carerClientRelationMapper.toDto(updatedCarerClientRelation);

        restCarerClientRelationMockMvc.perform(put("/api/carer-client-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerClientRelationDTO)))
            .andExpect(status().isOk());

        // Validate the CarerClientRelation in the database
        List<CarerClientRelation> carerClientRelationList = carerClientRelationRepository.findAll();
        assertThat(carerClientRelationList).hasSize(databaseSizeBeforeUpdate);
        CarerClientRelation testCarerClientRelation = carerClientRelationList.get(carerClientRelationList.size() - 1);
        assertThat(testCarerClientRelation.getRelationType()).isEqualTo(UPDATED_RELATION_TYPE);
        assertThat(testCarerClientRelation.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testCarerClientRelation.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testCarerClientRelation.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testCarerClientRelation.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCarerClientRelation() throws Exception {
        int databaseSizeBeforeUpdate = carerClientRelationRepository.findAll().size();

        // Create the CarerClientRelation
        CarerClientRelationDTO carerClientRelationDTO = carerClientRelationMapper.toDto(carerClientRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarerClientRelationMockMvc.perform(put("/api/carer-client-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carerClientRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarerClientRelation in the database
        List<CarerClientRelation> carerClientRelationList = carerClientRelationRepository.findAll();
        assertThat(carerClientRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarerClientRelation() throws Exception {
        // Initialize the database
        carerClientRelationRepository.saveAndFlush(carerClientRelation);

        int databaseSizeBeforeDelete = carerClientRelationRepository.findAll().size();

        // Delete the carerClientRelation
        restCarerClientRelationMockMvc.perform(delete("/api/carer-client-relations/{id}", carerClientRelation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarerClientRelation> carerClientRelationList = carerClientRelationRepository.findAll();
        assertThat(carerClientRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
