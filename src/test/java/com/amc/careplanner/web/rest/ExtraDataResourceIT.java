package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.ExtraData;
import com.amc.careplanner.repository.ExtraDataRepository;
import com.amc.careplanner.service.ExtraDataService;
import com.amc.careplanner.service.dto.ExtraDataDTO;
import com.amc.careplanner.service.mapper.ExtraDataMapper;
import com.amc.careplanner.service.dto.ExtraDataCriteria;
import com.amc.careplanner.service.ExtraDataQueryService;

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

import com.amc.careplanner.domain.enumeration.DataType;
/**
 * Integration tests for the {@link ExtraDataResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExtraDataResourceIT {

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;
    private static final Long SMALLER_ENTITY_ID = 1L - 1L;

    private static final String DEFAULT_EXTRA_DATA_KEY = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_DATA_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_DATA_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_DATA_VALUE = "BBBBBBBBBB";

    private static final DataType DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE = DataType.STRING;
    private static final DataType UPDATED_EXTRA_DATA_VALUE_DATA_TYPE = DataType.BOOLEAN;

    private static final String DEFAULT_EXTRA_DATA_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_DATA_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXTRA_DATA_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXTRA_DATA_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EXTRA_DATA_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private ExtraDataRepository extraDataRepository;

    @Autowired
    private ExtraDataMapper extraDataMapper;

    @Autowired
    private ExtraDataService extraDataService;

    @Autowired
    private ExtraDataQueryService extraDataQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExtraDataMockMvc;

    private ExtraData extraData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtraData createEntity(EntityManager em) {
        ExtraData extraData = new ExtraData()
            .entityName(DEFAULT_ENTITY_NAME)
            .entityId(DEFAULT_ENTITY_ID)
            .extraDataKey(DEFAULT_EXTRA_DATA_KEY)
            .extraDataValue(DEFAULT_EXTRA_DATA_VALUE)
            .extraDataValueDataType(DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE)
            .extraDataDescription(DEFAULT_EXTRA_DATA_DESCRIPTION)
            .extraDataDate(DEFAULT_EXTRA_DATA_DATE)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return extraData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtraData createUpdatedEntity(EntityManager em) {
        ExtraData extraData = new ExtraData()
            .entityName(UPDATED_ENTITY_NAME)
            .entityId(UPDATED_ENTITY_ID)
            .extraDataKey(UPDATED_EXTRA_DATA_KEY)
            .extraDataValue(UPDATED_EXTRA_DATA_VALUE)
            .extraDataValueDataType(UPDATED_EXTRA_DATA_VALUE_DATA_TYPE)
            .extraDataDescription(UPDATED_EXTRA_DATA_DESCRIPTION)
            .extraDataDate(UPDATED_EXTRA_DATA_DATE)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return extraData;
    }

    @BeforeEach
    public void initTest() {
        extraData = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtraData() throws Exception {
        int databaseSizeBeforeCreate = extraDataRepository.findAll().size();
        // Create the ExtraData
        ExtraDataDTO extraDataDTO = extraDataMapper.toDto(extraData);
        restExtraDataMockMvc.perform(post("/api/extra-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraDataDTO)))
            .andExpect(status().isCreated());

        // Validate the ExtraData in the database
        List<ExtraData> extraDataList = extraDataRepository.findAll();
        assertThat(extraDataList).hasSize(databaseSizeBeforeCreate + 1);
        ExtraData testExtraData = extraDataList.get(extraDataList.size() - 1);
        assertThat(testExtraData.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testExtraData.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testExtraData.getExtraDataKey()).isEqualTo(DEFAULT_EXTRA_DATA_KEY);
        assertThat(testExtraData.getExtraDataValue()).isEqualTo(DEFAULT_EXTRA_DATA_VALUE);
        assertThat(testExtraData.getExtraDataValueDataType()).isEqualTo(DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE);
        assertThat(testExtraData.getExtraDataDescription()).isEqualTo(DEFAULT_EXTRA_DATA_DESCRIPTION);
        assertThat(testExtraData.getExtraDataDate()).isEqualTo(DEFAULT_EXTRA_DATA_DATE);
        assertThat(testExtraData.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createExtraDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extraDataRepository.findAll().size();

        // Create the ExtraData with an existing ID
        extraData.setId(1L);
        ExtraDataDTO extraDataDTO = extraDataMapper.toDto(extraData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtraDataMockMvc.perform(post("/api/extra-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtraData in the database
        List<ExtraData> extraDataList = extraDataRepository.findAll();
        assertThat(extraDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEntityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraDataRepository.findAll().size();
        // set the field null
        extraData.setEntityName(null);

        // Create the ExtraData, which fails.
        ExtraDataDTO extraDataDTO = extraDataMapper.toDto(extraData);


        restExtraDataMockMvc.perform(post("/api/extra-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraDataDTO)))
            .andExpect(status().isBadRequest());

        List<ExtraData> extraDataList = extraDataRepository.findAll();
        assertThat(extraDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraDataRepository.findAll().size();
        // set the field null
        extraData.setEntityId(null);

        // Create the ExtraData, which fails.
        ExtraDataDTO extraDataDTO = extraDataMapper.toDto(extraData);


        restExtraDataMockMvc.perform(post("/api/extra-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraDataDTO)))
            .andExpect(status().isBadRequest());

        List<ExtraData> extraDataList = extraDataRepository.findAll();
        assertThat(extraDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExtraData() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList
        restExtraDataMockMvc.perform(get("/api/extra-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extraData.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].extraDataKey").value(hasItem(DEFAULT_EXTRA_DATA_KEY)))
            .andExpect(jsonPath("$.[*].extraDataValue").value(hasItem(DEFAULT_EXTRA_DATA_VALUE)))
            .andExpect(jsonPath("$.[*].extraDataValueDataType").value(hasItem(DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].extraDataDescription").value(hasItem(DEFAULT_EXTRA_DATA_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].extraDataDate").value(hasItem(sameInstant(DEFAULT_EXTRA_DATA_DATE))))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getExtraData() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get the extraData
        restExtraDataMockMvc.perform(get("/api/extra-data/{id}", extraData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(extraData.getId().intValue()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.extraDataKey").value(DEFAULT_EXTRA_DATA_KEY))
            .andExpect(jsonPath("$.extraDataValue").value(DEFAULT_EXTRA_DATA_VALUE))
            .andExpect(jsonPath("$.extraDataValueDataType").value(DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.extraDataDescription").value(DEFAULT_EXTRA_DATA_DESCRIPTION))
            .andExpect(jsonPath("$.extraDataDate").value(sameInstant(DEFAULT_EXTRA_DATA_DATE)))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getExtraDataByIdFiltering() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        Long id = extraData.getId();

        defaultExtraDataShouldBeFound("id.equals=" + id);
        defaultExtraDataShouldNotBeFound("id.notEquals=" + id);

        defaultExtraDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExtraDataShouldNotBeFound("id.greaterThan=" + id);

        defaultExtraDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExtraDataShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllExtraDataByEntityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityName equals to DEFAULT_ENTITY_NAME
        defaultExtraDataShouldBeFound("entityName.equals=" + DEFAULT_ENTITY_NAME);

        // Get all the extraDataList where entityName equals to UPDATED_ENTITY_NAME
        defaultExtraDataShouldNotBeFound("entityName.equals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityName not equals to DEFAULT_ENTITY_NAME
        defaultExtraDataShouldNotBeFound("entityName.notEquals=" + DEFAULT_ENTITY_NAME);

        // Get all the extraDataList where entityName not equals to UPDATED_ENTITY_NAME
        defaultExtraDataShouldBeFound("entityName.notEquals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityNameIsInShouldWork() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityName in DEFAULT_ENTITY_NAME or UPDATED_ENTITY_NAME
        defaultExtraDataShouldBeFound("entityName.in=" + DEFAULT_ENTITY_NAME + "," + UPDATED_ENTITY_NAME);

        // Get all the extraDataList where entityName equals to UPDATED_ENTITY_NAME
        defaultExtraDataShouldNotBeFound("entityName.in=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityName is not null
        defaultExtraDataShouldBeFound("entityName.specified=true");

        // Get all the extraDataList where entityName is null
        defaultExtraDataShouldNotBeFound("entityName.specified=false");
    }
                @Test
    @Transactional
    public void getAllExtraDataByEntityNameContainsSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityName contains DEFAULT_ENTITY_NAME
        defaultExtraDataShouldBeFound("entityName.contains=" + DEFAULT_ENTITY_NAME);

        // Get all the extraDataList where entityName contains UPDATED_ENTITY_NAME
        defaultExtraDataShouldNotBeFound("entityName.contains=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityNameNotContainsSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityName does not contain DEFAULT_ENTITY_NAME
        defaultExtraDataShouldNotBeFound("entityName.doesNotContain=" + DEFAULT_ENTITY_NAME);

        // Get all the extraDataList where entityName does not contain UPDATED_ENTITY_NAME
        defaultExtraDataShouldBeFound("entityName.doesNotContain=" + UPDATED_ENTITY_NAME);
    }


    @Test
    @Transactional
    public void getAllExtraDataByEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityId equals to DEFAULT_ENTITY_ID
        defaultExtraDataShouldBeFound("entityId.equals=" + DEFAULT_ENTITY_ID);

        // Get all the extraDataList where entityId equals to UPDATED_ENTITY_ID
        defaultExtraDataShouldNotBeFound("entityId.equals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityId not equals to DEFAULT_ENTITY_ID
        defaultExtraDataShouldNotBeFound("entityId.notEquals=" + DEFAULT_ENTITY_ID);

        // Get all the extraDataList where entityId not equals to UPDATED_ENTITY_ID
        defaultExtraDataShouldBeFound("entityId.notEquals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityId in DEFAULT_ENTITY_ID or UPDATED_ENTITY_ID
        defaultExtraDataShouldBeFound("entityId.in=" + DEFAULT_ENTITY_ID + "," + UPDATED_ENTITY_ID);

        // Get all the extraDataList where entityId equals to UPDATED_ENTITY_ID
        defaultExtraDataShouldNotBeFound("entityId.in=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityId is not null
        defaultExtraDataShouldBeFound("entityId.specified=true");

        // Get all the extraDataList where entityId is null
        defaultExtraDataShouldNotBeFound("entityId.specified=false");
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityId is greater than or equal to DEFAULT_ENTITY_ID
        defaultExtraDataShouldBeFound("entityId.greaterThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the extraDataList where entityId is greater than or equal to UPDATED_ENTITY_ID
        defaultExtraDataShouldNotBeFound("entityId.greaterThanOrEqual=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityId is less than or equal to DEFAULT_ENTITY_ID
        defaultExtraDataShouldBeFound("entityId.lessThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the extraDataList where entityId is less than or equal to SMALLER_ENTITY_ID
        defaultExtraDataShouldNotBeFound("entityId.lessThanOrEqual=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityId is less than DEFAULT_ENTITY_ID
        defaultExtraDataShouldNotBeFound("entityId.lessThan=" + DEFAULT_ENTITY_ID);

        // Get all the extraDataList where entityId is less than UPDATED_ENTITY_ID
        defaultExtraDataShouldBeFound("entityId.lessThan=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllExtraDataByEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where entityId is greater than DEFAULT_ENTITY_ID
        defaultExtraDataShouldNotBeFound("entityId.greaterThan=" + DEFAULT_ENTITY_ID);

        // Get all the extraDataList where entityId is greater than SMALLER_ENTITY_ID
        defaultExtraDataShouldBeFound("entityId.greaterThan=" + SMALLER_ENTITY_ID);
    }


    @Test
    @Transactional
    public void getAllExtraDataByExtraDataKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataKey equals to DEFAULT_EXTRA_DATA_KEY
        defaultExtraDataShouldBeFound("extraDataKey.equals=" + DEFAULT_EXTRA_DATA_KEY);

        // Get all the extraDataList where extraDataKey equals to UPDATED_EXTRA_DATA_KEY
        defaultExtraDataShouldNotBeFound("extraDataKey.equals=" + UPDATED_EXTRA_DATA_KEY);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataKey not equals to DEFAULT_EXTRA_DATA_KEY
        defaultExtraDataShouldNotBeFound("extraDataKey.notEquals=" + DEFAULT_EXTRA_DATA_KEY);

        // Get all the extraDataList where extraDataKey not equals to UPDATED_EXTRA_DATA_KEY
        defaultExtraDataShouldBeFound("extraDataKey.notEquals=" + UPDATED_EXTRA_DATA_KEY);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataKeyIsInShouldWork() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataKey in DEFAULT_EXTRA_DATA_KEY or UPDATED_EXTRA_DATA_KEY
        defaultExtraDataShouldBeFound("extraDataKey.in=" + DEFAULT_EXTRA_DATA_KEY + "," + UPDATED_EXTRA_DATA_KEY);

        // Get all the extraDataList where extraDataKey equals to UPDATED_EXTRA_DATA_KEY
        defaultExtraDataShouldNotBeFound("extraDataKey.in=" + UPDATED_EXTRA_DATA_KEY);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataKey is not null
        defaultExtraDataShouldBeFound("extraDataKey.specified=true");

        // Get all the extraDataList where extraDataKey is null
        defaultExtraDataShouldNotBeFound("extraDataKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllExtraDataByExtraDataKeyContainsSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataKey contains DEFAULT_EXTRA_DATA_KEY
        defaultExtraDataShouldBeFound("extraDataKey.contains=" + DEFAULT_EXTRA_DATA_KEY);

        // Get all the extraDataList where extraDataKey contains UPDATED_EXTRA_DATA_KEY
        defaultExtraDataShouldNotBeFound("extraDataKey.contains=" + UPDATED_EXTRA_DATA_KEY);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataKeyNotContainsSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataKey does not contain DEFAULT_EXTRA_DATA_KEY
        defaultExtraDataShouldNotBeFound("extraDataKey.doesNotContain=" + DEFAULT_EXTRA_DATA_KEY);

        // Get all the extraDataList where extraDataKey does not contain UPDATED_EXTRA_DATA_KEY
        defaultExtraDataShouldBeFound("extraDataKey.doesNotContain=" + UPDATED_EXTRA_DATA_KEY);
    }


    @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueIsEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValue equals to DEFAULT_EXTRA_DATA_VALUE
        defaultExtraDataShouldBeFound("extraDataValue.equals=" + DEFAULT_EXTRA_DATA_VALUE);

        // Get all the extraDataList where extraDataValue equals to UPDATED_EXTRA_DATA_VALUE
        defaultExtraDataShouldNotBeFound("extraDataValue.equals=" + UPDATED_EXTRA_DATA_VALUE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValue not equals to DEFAULT_EXTRA_DATA_VALUE
        defaultExtraDataShouldNotBeFound("extraDataValue.notEquals=" + DEFAULT_EXTRA_DATA_VALUE);

        // Get all the extraDataList where extraDataValue not equals to UPDATED_EXTRA_DATA_VALUE
        defaultExtraDataShouldBeFound("extraDataValue.notEquals=" + UPDATED_EXTRA_DATA_VALUE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueIsInShouldWork() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValue in DEFAULT_EXTRA_DATA_VALUE or UPDATED_EXTRA_DATA_VALUE
        defaultExtraDataShouldBeFound("extraDataValue.in=" + DEFAULT_EXTRA_DATA_VALUE + "," + UPDATED_EXTRA_DATA_VALUE);

        // Get all the extraDataList where extraDataValue equals to UPDATED_EXTRA_DATA_VALUE
        defaultExtraDataShouldNotBeFound("extraDataValue.in=" + UPDATED_EXTRA_DATA_VALUE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValue is not null
        defaultExtraDataShouldBeFound("extraDataValue.specified=true");

        // Get all the extraDataList where extraDataValue is null
        defaultExtraDataShouldNotBeFound("extraDataValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueContainsSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValue contains DEFAULT_EXTRA_DATA_VALUE
        defaultExtraDataShouldBeFound("extraDataValue.contains=" + DEFAULT_EXTRA_DATA_VALUE);

        // Get all the extraDataList where extraDataValue contains UPDATED_EXTRA_DATA_VALUE
        defaultExtraDataShouldNotBeFound("extraDataValue.contains=" + UPDATED_EXTRA_DATA_VALUE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueNotContainsSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValue does not contain DEFAULT_EXTRA_DATA_VALUE
        defaultExtraDataShouldNotBeFound("extraDataValue.doesNotContain=" + DEFAULT_EXTRA_DATA_VALUE);

        // Get all the extraDataList where extraDataValue does not contain UPDATED_EXTRA_DATA_VALUE
        defaultExtraDataShouldBeFound("extraDataValue.doesNotContain=" + UPDATED_EXTRA_DATA_VALUE);
    }


    @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueDataTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValueDataType equals to DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE
        defaultExtraDataShouldBeFound("extraDataValueDataType.equals=" + DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE);

        // Get all the extraDataList where extraDataValueDataType equals to UPDATED_EXTRA_DATA_VALUE_DATA_TYPE
        defaultExtraDataShouldNotBeFound("extraDataValueDataType.equals=" + UPDATED_EXTRA_DATA_VALUE_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueDataTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValueDataType not equals to DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE
        defaultExtraDataShouldNotBeFound("extraDataValueDataType.notEquals=" + DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE);

        // Get all the extraDataList where extraDataValueDataType not equals to UPDATED_EXTRA_DATA_VALUE_DATA_TYPE
        defaultExtraDataShouldBeFound("extraDataValueDataType.notEquals=" + UPDATED_EXTRA_DATA_VALUE_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueDataTypeIsInShouldWork() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValueDataType in DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE or UPDATED_EXTRA_DATA_VALUE_DATA_TYPE
        defaultExtraDataShouldBeFound("extraDataValueDataType.in=" + DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE + "," + UPDATED_EXTRA_DATA_VALUE_DATA_TYPE);

        // Get all the extraDataList where extraDataValueDataType equals to UPDATED_EXTRA_DATA_VALUE_DATA_TYPE
        defaultExtraDataShouldNotBeFound("extraDataValueDataType.in=" + UPDATED_EXTRA_DATA_VALUE_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataValueDataTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataValueDataType is not null
        defaultExtraDataShouldBeFound("extraDataValueDataType.specified=true");

        // Get all the extraDataList where extraDataValueDataType is null
        defaultExtraDataShouldNotBeFound("extraDataValueDataType.specified=false");
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDescription equals to DEFAULT_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldBeFound("extraDataDescription.equals=" + DEFAULT_EXTRA_DATA_DESCRIPTION);

        // Get all the extraDataList where extraDataDescription equals to UPDATED_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldNotBeFound("extraDataDescription.equals=" + UPDATED_EXTRA_DATA_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDescription not equals to DEFAULT_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldNotBeFound("extraDataDescription.notEquals=" + DEFAULT_EXTRA_DATA_DESCRIPTION);

        // Get all the extraDataList where extraDataDescription not equals to UPDATED_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldBeFound("extraDataDescription.notEquals=" + UPDATED_EXTRA_DATA_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDescription in DEFAULT_EXTRA_DATA_DESCRIPTION or UPDATED_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldBeFound("extraDataDescription.in=" + DEFAULT_EXTRA_DATA_DESCRIPTION + "," + UPDATED_EXTRA_DATA_DESCRIPTION);

        // Get all the extraDataList where extraDataDescription equals to UPDATED_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldNotBeFound("extraDataDescription.in=" + UPDATED_EXTRA_DATA_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDescription is not null
        defaultExtraDataShouldBeFound("extraDataDescription.specified=true");

        // Get all the extraDataList where extraDataDescription is null
        defaultExtraDataShouldNotBeFound("extraDataDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllExtraDataByExtraDataDescriptionContainsSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDescription contains DEFAULT_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldBeFound("extraDataDescription.contains=" + DEFAULT_EXTRA_DATA_DESCRIPTION);

        // Get all the extraDataList where extraDataDescription contains UPDATED_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldNotBeFound("extraDataDescription.contains=" + UPDATED_EXTRA_DATA_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDescription does not contain DEFAULT_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldNotBeFound("extraDataDescription.doesNotContain=" + DEFAULT_EXTRA_DATA_DESCRIPTION);

        // Get all the extraDataList where extraDataDescription does not contain UPDATED_EXTRA_DATA_DESCRIPTION
        defaultExtraDataShouldBeFound("extraDataDescription.doesNotContain=" + UPDATED_EXTRA_DATA_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDateIsEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDate equals to DEFAULT_EXTRA_DATA_DATE
        defaultExtraDataShouldBeFound("extraDataDate.equals=" + DEFAULT_EXTRA_DATA_DATE);

        // Get all the extraDataList where extraDataDate equals to UPDATED_EXTRA_DATA_DATE
        defaultExtraDataShouldNotBeFound("extraDataDate.equals=" + UPDATED_EXTRA_DATA_DATE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDate not equals to DEFAULT_EXTRA_DATA_DATE
        defaultExtraDataShouldNotBeFound("extraDataDate.notEquals=" + DEFAULT_EXTRA_DATA_DATE);

        // Get all the extraDataList where extraDataDate not equals to UPDATED_EXTRA_DATA_DATE
        defaultExtraDataShouldBeFound("extraDataDate.notEquals=" + UPDATED_EXTRA_DATA_DATE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDateIsInShouldWork() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDate in DEFAULT_EXTRA_DATA_DATE or UPDATED_EXTRA_DATA_DATE
        defaultExtraDataShouldBeFound("extraDataDate.in=" + DEFAULT_EXTRA_DATA_DATE + "," + UPDATED_EXTRA_DATA_DATE);

        // Get all the extraDataList where extraDataDate equals to UPDATED_EXTRA_DATA_DATE
        defaultExtraDataShouldNotBeFound("extraDataDate.in=" + UPDATED_EXTRA_DATA_DATE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDate is not null
        defaultExtraDataShouldBeFound("extraDataDate.specified=true");

        // Get all the extraDataList where extraDataDate is null
        defaultExtraDataShouldNotBeFound("extraDataDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDate is greater than or equal to DEFAULT_EXTRA_DATA_DATE
        defaultExtraDataShouldBeFound("extraDataDate.greaterThanOrEqual=" + DEFAULT_EXTRA_DATA_DATE);

        // Get all the extraDataList where extraDataDate is greater than or equal to UPDATED_EXTRA_DATA_DATE
        defaultExtraDataShouldNotBeFound("extraDataDate.greaterThanOrEqual=" + UPDATED_EXTRA_DATA_DATE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDate is less than or equal to DEFAULT_EXTRA_DATA_DATE
        defaultExtraDataShouldBeFound("extraDataDate.lessThanOrEqual=" + DEFAULT_EXTRA_DATA_DATE);

        // Get all the extraDataList where extraDataDate is less than or equal to SMALLER_EXTRA_DATA_DATE
        defaultExtraDataShouldNotBeFound("extraDataDate.lessThanOrEqual=" + SMALLER_EXTRA_DATA_DATE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDateIsLessThanSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDate is less than DEFAULT_EXTRA_DATA_DATE
        defaultExtraDataShouldNotBeFound("extraDataDate.lessThan=" + DEFAULT_EXTRA_DATA_DATE);

        // Get all the extraDataList where extraDataDate is less than UPDATED_EXTRA_DATA_DATE
        defaultExtraDataShouldBeFound("extraDataDate.lessThan=" + UPDATED_EXTRA_DATA_DATE);
    }

    @Test
    @Transactional
    public void getAllExtraDataByExtraDataDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where extraDataDate is greater than DEFAULT_EXTRA_DATA_DATE
        defaultExtraDataShouldNotBeFound("extraDataDate.greaterThan=" + DEFAULT_EXTRA_DATA_DATE);

        // Get all the extraDataList where extraDataDate is greater than SMALLER_EXTRA_DATA_DATE
        defaultExtraDataShouldBeFound("extraDataDate.greaterThan=" + SMALLER_EXTRA_DATA_DATE);
    }


    @Test
    @Transactional
    public void getAllExtraDataByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultExtraDataShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the extraDataList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultExtraDataShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllExtraDataByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultExtraDataShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the extraDataList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultExtraDataShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllExtraDataByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultExtraDataShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the extraDataList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultExtraDataShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllExtraDataByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        // Get all the extraDataList where hasExtraData is not null
        defaultExtraDataShouldBeFound("hasExtraData.specified=true");

        // Get all the extraDataList where hasExtraData is null
        defaultExtraDataShouldNotBeFound("hasExtraData.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExtraDataShouldBeFound(String filter) throws Exception {
        restExtraDataMockMvc.perform(get("/api/extra-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extraData.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].extraDataKey").value(hasItem(DEFAULT_EXTRA_DATA_KEY)))
            .andExpect(jsonPath("$.[*].extraDataValue").value(hasItem(DEFAULT_EXTRA_DATA_VALUE)))
            .andExpect(jsonPath("$.[*].extraDataValueDataType").value(hasItem(DEFAULT_EXTRA_DATA_VALUE_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].extraDataDescription").value(hasItem(DEFAULT_EXTRA_DATA_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].extraDataDate").value(hasItem(sameInstant(DEFAULT_EXTRA_DATA_DATE))))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restExtraDataMockMvc.perform(get("/api/extra-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExtraDataShouldNotBeFound(String filter) throws Exception {
        restExtraDataMockMvc.perform(get("/api/extra-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExtraDataMockMvc.perform(get("/api/extra-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingExtraData() throws Exception {
        // Get the extraData
        restExtraDataMockMvc.perform(get("/api/extra-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtraData() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        int databaseSizeBeforeUpdate = extraDataRepository.findAll().size();

        // Update the extraData
        ExtraData updatedExtraData = extraDataRepository.findById(extraData.getId()).get();
        // Disconnect from session so that the updates on updatedExtraData are not directly saved in db
        em.detach(updatedExtraData);
        updatedExtraData
            .entityName(UPDATED_ENTITY_NAME)
            .entityId(UPDATED_ENTITY_ID)
            .extraDataKey(UPDATED_EXTRA_DATA_KEY)
            .extraDataValue(UPDATED_EXTRA_DATA_VALUE)
            .extraDataValueDataType(UPDATED_EXTRA_DATA_VALUE_DATA_TYPE)
            .extraDataDescription(UPDATED_EXTRA_DATA_DESCRIPTION)
            .extraDataDate(UPDATED_EXTRA_DATA_DATE)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        ExtraDataDTO extraDataDTO = extraDataMapper.toDto(updatedExtraData);

        restExtraDataMockMvc.perform(put("/api/extra-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraDataDTO)))
            .andExpect(status().isOk());

        // Validate the ExtraData in the database
        List<ExtraData> extraDataList = extraDataRepository.findAll();
        assertThat(extraDataList).hasSize(databaseSizeBeforeUpdate);
        ExtraData testExtraData = extraDataList.get(extraDataList.size() - 1);
        assertThat(testExtraData.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testExtraData.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testExtraData.getExtraDataKey()).isEqualTo(UPDATED_EXTRA_DATA_KEY);
        assertThat(testExtraData.getExtraDataValue()).isEqualTo(UPDATED_EXTRA_DATA_VALUE);
        assertThat(testExtraData.getExtraDataValueDataType()).isEqualTo(UPDATED_EXTRA_DATA_VALUE_DATA_TYPE);
        assertThat(testExtraData.getExtraDataDescription()).isEqualTo(UPDATED_EXTRA_DATA_DESCRIPTION);
        assertThat(testExtraData.getExtraDataDate()).isEqualTo(UPDATED_EXTRA_DATA_DATE);
        assertThat(testExtraData.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingExtraData() throws Exception {
        int databaseSizeBeforeUpdate = extraDataRepository.findAll().size();

        // Create the ExtraData
        ExtraDataDTO extraDataDTO = extraDataMapper.toDto(extraData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtraDataMockMvc.perform(put("/api/extra-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtraData in the database
        List<ExtraData> extraDataList = extraDataRepository.findAll();
        assertThat(extraDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExtraData() throws Exception {
        // Initialize the database
        extraDataRepository.saveAndFlush(extraData);

        int databaseSizeBeforeDelete = extraDataRepository.findAll().size();

        // Delete the extraData
        restExtraDataMockMvc.perform(delete("/api/extra-data/{id}", extraData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExtraData> extraDataList = extraDataRepository.findAll();
        assertThat(extraDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
