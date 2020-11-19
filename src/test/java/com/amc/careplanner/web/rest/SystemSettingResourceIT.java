package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.SystemSetting;
import com.amc.careplanner.repository.SystemSettingRepository;
import com.amc.careplanner.service.SystemSettingService;
import com.amc.careplanner.service.dto.SystemSettingDTO;
import com.amc.careplanner.service.mapper.SystemSettingMapper;
import com.amc.careplanner.service.dto.SystemSettingCriteria;
import com.amc.careplanner.service.SystemSettingQueryService;

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
 * Integration tests for the {@link SystemSettingResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SystemSettingResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SETTING_ENABLED = false;
    private static final Boolean UPDATED_SETTING_ENABLED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    @Autowired
    private SystemSettingMapper systemSettingMapper;

    @Autowired
    private SystemSettingService systemSettingService;

    @Autowired
    private SystemSettingQueryService systemSettingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemSettingMockMvc;

    private SystemSetting systemSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSetting createEntity(EntityManager em) {
        SystemSetting systemSetting = new SystemSetting()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldValue(DEFAULT_FIELD_VALUE)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .settingEnabled(DEFAULT_SETTING_ENABLED)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return systemSetting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSetting createUpdatedEntity(EntityManager em) {
        SystemSetting systemSetting = new SystemSetting()
            .fieldName(UPDATED_FIELD_NAME)
            .fieldValue(UPDATED_FIELD_VALUE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .settingEnabled(UPDATED_SETTING_ENABLED)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return systemSetting;
    }

    @BeforeEach
    public void initTest() {
        systemSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemSetting() throws Exception {
        int databaseSizeBeforeCreate = systemSettingRepository.findAll().size();
        // Create the SystemSetting
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);
        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeCreate + 1);
        SystemSetting testSystemSetting = systemSettingList.get(systemSettingList.size() - 1);
        assertThat(testSystemSetting.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testSystemSetting.getFieldValue()).isEqualTo(DEFAULT_FIELD_VALUE);
        assertThat(testSystemSetting.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testSystemSetting.isSettingEnabled()).isEqualTo(DEFAULT_SETTING_ENABLED);
        assertThat(testSystemSetting.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSystemSetting.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSystemSetting.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createSystemSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemSettingRepository.findAll().size();

        // Create the SystemSetting with an existing ID
        systemSetting.setId(1L);
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setFieldName(null);

        // Create the SystemSetting, which fails.
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);


        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setFieldValue(null);

        // Create the SystemSetting, which fails.
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);


        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefaultValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setDefaultValue(null);

        // Create the SystemSetting, which fails.
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);


        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemSettings() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList
        restSystemSettingMockMvc.perform(get("/api/system-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldValue").value(hasItem(DEFAULT_FIELD_VALUE)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].settingEnabled").value(hasItem(DEFAULT_SETTING_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get the systemSetting
        restSystemSettingMockMvc.perform(get("/api/system-settings/{id}", systemSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemSetting.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.fieldValue").value(DEFAULT_FIELD_VALUE))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.settingEnabled").value(DEFAULT_SETTING_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getSystemSettingsByIdFiltering() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        Long id = systemSetting.getId();

        defaultSystemSettingShouldBeFound("id.equals=" + id);
        defaultSystemSettingShouldNotBeFound("id.notEquals=" + id);

        defaultSystemSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSystemSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultSystemSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSystemSettingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSystemSettingsByFieldNameIsEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldName equals to DEFAULT_FIELD_NAME
        defaultSystemSettingShouldBeFound("fieldName.equals=" + DEFAULT_FIELD_NAME);

        // Get all the systemSettingList where fieldName equals to UPDATED_FIELD_NAME
        defaultSystemSettingShouldNotBeFound("fieldName.equals=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByFieldNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldName not equals to DEFAULT_FIELD_NAME
        defaultSystemSettingShouldNotBeFound("fieldName.notEquals=" + DEFAULT_FIELD_NAME);

        // Get all the systemSettingList where fieldName not equals to UPDATED_FIELD_NAME
        defaultSystemSettingShouldBeFound("fieldName.notEquals=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByFieldNameIsInShouldWork() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldName in DEFAULT_FIELD_NAME or UPDATED_FIELD_NAME
        defaultSystemSettingShouldBeFound("fieldName.in=" + DEFAULT_FIELD_NAME + "," + UPDATED_FIELD_NAME);

        // Get all the systemSettingList where fieldName equals to UPDATED_FIELD_NAME
        defaultSystemSettingShouldNotBeFound("fieldName.in=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByFieldNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldName is not null
        defaultSystemSettingShouldBeFound("fieldName.specified=true");

        // Get all the systemSettingList where fieldName is null
        defaultSystemSettingShouldNotBeFound("fieldName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemSettingsByFieldNameContainsSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldName contains DEFAULT_FIELD_NAME
        defaultSystemSettingShouldBeFound("fieldName.contains=" + DEFAULT_FIELD_NAME);

        // Get all the systemSettingList where fieldName contains UPDATED_FIELD_NAME
        defaultSystemSettingShouldNotBeFound("fieldName.contains=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByFieldNameNotContainsSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldName does not contain DEFAULT_FIELD_NAME
        defaultSystemSettingShouldNotBeFound("fieldName.doesNotContain=" + DEFAULT_FIELD_NAME);

        // Get all the systemSettingList where fieldName does not contain UPDATED_FIELD_NAME
        defaultSystemSettingShouldBeFound("fieldName.doesNotContain=" + UPDATED_FIELD_NAME);
    }


    @Test
    @Transactional
    public void getAllSystemSettingsByFieldValueIsEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldValue equals to DEFAULT_FIELD_VALUE
        defaultSystemSettingShouldBeFound("fieldValue.equals=" + DEFAULT_FIELD_VALUE);

        // Get all the systemSettingList where fieldValue equals to UPDATED_FIELD_VALUE
        defaultSystemSettingShouldNotBeFound("fieldValue.equals=" + UPDATED_FIELD_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByFieldValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldValue not equals to DEFAULT_FIELD_VALUE
        defaultSystemSettingShouldNotBeFound("fieldValue.notEquals=" + DEFAULT_FIELD_VALUE);

        // Get all the systemSettingList where fieldValue not equals to UPDATED_FIELD_VALUE
        defaultSystemSettingShouldBeFound("fieldValue.notEquals=" + UPDATED_FIELD_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByFieldValueIsInShouldWork() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldValue in DEFAULT_FIELD_VALUE or UPDATED_FIELD_VALUE
        defaultSystemSettingShouldBeFound("fieldValue.in=" + DEFAULT_FIELD_VALUE + "," + UPDATED_FIELD_VALUE);

        // Get all the systemSettingList where fieldValue equals to UPDATED_FIELD_VALUE
        defaultSystemSettingShouldNotBeFound("fieldValue.in=" + UPDATED_FIELD_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByFieldValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldValue is not null
        defaultSystemSettingShouldBeFound("fieldValue.specified=true");

        // Get all the systemSettingList where fieldValue is null
        defaultSystemSettingShouldNotBeFound("fieldValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemSettingsByFieldValueContainsSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldValue contains DEFAULT_FIELD_VALUE
        defaultSystemSettingShouldBeFound("fieldValue.contains=" + DEFAULT_FIELD_VALUE);

        // Get all the systemSettingList where fieldValue contains UPDATED_FIELD_VALUE
        defaultSystemSettingShouldNotBeFound("fieldValue.contains=" + UPDATED_FIELD_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByFieldValueNotContainsSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where fieldValue does not contain DEFAULT_FIELD_VALUE
        defaultSystemSettingShouldNotBeFound("fieldValue.doesNotContain=" + DEFAULT_FIELD_VALUE);

        // Get all the systemSettingList where fieldValue does not contain UPDATED_FIELD_VALUE
        defaultSystemSettingShouldBeFound("fieldValue.doesNotContain=" + UPDATED_FIELD_VALUE);
    }


    @Test
    @Transactional
    public void getAllSystemSettingsByDefaultValueIsEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where defaultValue equals to DEFAULT_DEFAULT_VALUE
        defaultSystemSettingShouldBeFound("defaultValue.equals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the systemSettingList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultSystemSettingShouldNotBeFound("defaultValue.equals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByDefaultValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where defaultValue not equals to DEFAULT_DEFAULT_VALUE
        defaultSystemSettingShouldNotBeFound("defaultValue.notEquals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the systemSettingList where defaultValue not equals to UPDATED_DEFAULT_VALUE
        defaultSystemSettingShouldBeFound("defaultValue.notEquals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByDefaultValueIsInShouldWork() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where defaultValue in DEFAULT_DEFAULT_VALUE or UPDATED_DEFAULT_VALUE
        defaultSystemSettingShouldBeFound("defaultValue.in=" + DEFAULT_DEFAULT_VALUE + "," + UPDATED_DEFAULT_VALUE);

        // Get all the systemSettingList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultSystemSettingShouldNotBeFound("defaultValue.in=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByDefaultValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where defaultValue is not null
        defaultSystemSettingShouldBeFound("defaultValue.specified=true");

        // Get all the systemSettingList where defaultValue is null
        defaultSystemSettingShouldNotBeFound("defaultValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemSettingsByDefaultValueContainsSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where defaultValue contains DEFAULT_DEFAULT_VALUE
        defaultSystemSettingShouldBeFound("defaultValue.contains=" + DEFAULT_DEFAULT_VALUE);

        // Get all the systemSettingList where defaultValue contains UPDATED_DEFAULT_VALUE
        defaultSystemSettingShouldNotBeFound("defaultValue.contains=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByDefaultValueNotContainsSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where defaultValue does not contain DEFAULT_DEFAULT_VALUE
        defaultSystemSettingShouldNotBeFound("defaultValue.doesNotContain=" + DEFAULT_DEFAULT_VALUE);

        // Get all the systemSettingList where defaultValue does not contain UPDATED_DEFAULT_VALUE
        defaultSystemSettingShouldBeFound("defaultValue.doesNotContain=" + UPDATED_DEFAULT_VALUE);
    }


    @Test
    @Transactional
    public void getAllSystemSettingsBySettingEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where settingEnabled equals to DEFAULT_SETTING_ENABLED
        defaultSystemSettingShouldBeFound("settingEnabled.equals=" + DEFAULT_SETTING_ENABLED);

        // Get all the systemSettingList where settingEnabled equals to UPDATED_SETTING_ENABLED
        defaultSystemSettingShouldNotBeFound("settingEnabled.equals=" + UPDATED_SETTING_ENABLED);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsBySettingEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where settingEnabled not equals to DEFAULT_SETTING_ENABLED
        defaultSystemSettingShouldNotBeFound("settingEnabled.notEquals=" + DEFAULT_SETTING_ENABLED);

        // Get all the systemSettingList where settingEnabled not equals to UPDATED_SETTING_ENABLED
        defaultSystemSettingShouldBeFound("settingEnabled.notEquals=" + UPDATED_SETTING_ENABLED);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsBySettingEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where settingEnabled in DEFAULT_SETTING_ENABLED or UPDATED_SETTING_ENABLED
        defaultSystemSettingShouldBeFound("settingEnabled.in=" + DEFAULT_SETTING_ENABLED + "," + UPDATED_SETTING_ENABLED);

        // Get all the systemSettingList where settingEnabled equals to UPDATED_SETTING_ENABLED
        defaultSystemSettingShouldNotBeFound("settingEnabled.in=" + UPDATED_SETTING_ENABLED);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsBySettingEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where settingEnabled is not null
        defaultSystemSettingShouldBeFound("settingEnabled.specified=true");

        // Get all the systemSettingList where settingEnabled is null
        defaultSystemSettingShouldNotBeFound("settingEnabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where createdDate equals to DEFAULT_CREATED_DATE
        defaultSystemSettingShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the systemSettingList where createdDate equals to UPDATED_CREATED_DATE
        defaultSystemSettingShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultSystemSettingShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the systemSettingList where createdDate not equals to UPDATED_CREATED_DATE
        defaultSystemSettingShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultSystemSettingShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the systemSettingList where createdDate equals to UPDATED_CREATED_DATE
        defaultSystemSettingShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where createdDate is not null
        defaultSystemSettingShouldBeFound("createdDate.specified=true");

        // Get all the systemSettingList where createdDate is null
        defaultSystemSettingShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultSystemSettingShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the systemSettingList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultSystemSettingShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultSystemSettingShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the systemSettingList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultSystemSettingShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where createdDate is less than DEFAULT_CREATED_DATE
        defaultSystemSettingShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the systemSettingList where createdDate is less than UPDATED_CREATED_DATE
        defaultSystemSettingShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultSystemSettingShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the systemSettingList where createdDate is greater than SMALLER_CREATED_DATE
        defaultSystemSettingShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllSystemSettingsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where updatedDate equals to DEFAULT_UPDATED_DATE
        defaultSystemSettingShouldBeFound("updatedDate.equals=" + DEFAULT_UPDATED_DATE);

        // Get all the systemSettingList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultSystemSettingShouldNotBeFound("updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where updatedDate not equals to DEFAULT_UPDATED_DATE
        defaultSystemSettingShouldNotBeFound("updatedDate.notEquals=" + DEFAULT_UPDATED_DATE);

        // Get all the systemSettingList where updatedDate not equals to UPDATED_UPDATED_DATE
        defaultSystemSettingShouldBeFound("updatedDate.notEquals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where updatedDate in DEFAULT_UPDATED_DATE or UPDATED_UPDATED_DATE
        defaultSystemSettingShouldBeFound("updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE);

        // Get all the systemSettingList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultSystemSettingShouldNotBeFound("updatedDate.in=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where updatedDate is not null
        defaultSystemSettingShouldBeFound("updatedDate.specified=true");

        // Get all the systemSettingList where updatedDate is null
        defaultSystemSettingShouldNotBeFound("updatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where updatedDate is greater than or equal to DEFAULT_UPDATED_DATE
        defaultSystemSettingShouldBeFound("updatedDate.greaterThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the systemSettingList where updatedDate is greater than or equal to UPDATED_UPDATED_DATE
        defaultSystemSettingShouldNotBeFound("updatedDate.greaterThanOrEqual=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where updatedDate is less than or equal to DEFAULT_UPDATED_DATE
        defaultSystemSettingShouldBeFound("updatedDate.lessThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the systemSettingList where updatedDate is less than or equal to SMALLER_UPDATED_DATE
        defaultSystemSettingShouldNotBeFound("updatedDate.lessThanOrEqual=" + SMALLER_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where updatedDate is less than DEFAULT_UPDATED_DATE
        defaultSystemSettingShouldNotBeFound("updatedDate.lessThan=" + DEFAULT_UPDATED_DATE);

        // Get all the systemSettingList where updatedDate is less than UPDATED_UPDATED_DATE
        defaultSystemSettingShouldBeFound("updatedDate.lessThan=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where updatedDate is greater than DEFAULT_UPDATED_DATE
        defaultSystemSettingShouldNotBeFound("updatedDate.greaterThan=" + DEFAULT_UPDATED_DATE);

        // Get all the systemSettingList where updatedDate is greater than SMALLER_UPDATED_DATE
        defaultSystemSettingShouldBeFound("updatedDate.greaterThan=" + SMALLER_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllSystemSettingsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where tenantId equals to DEFAULT_TENANT_ID
        defaultSystemSettingShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the systemSettingList where tenantId equals to UPDATED_TENANT_ID
        defaultSystemSettingShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where tenantId not equals to DEFAULT_TENANT_ID
        defaultSystemSettingShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the systemSettingList where tenantId not equals to UPDATED_TENANT_ID
        defaultSystemSettingShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultSystemSettingShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the systemSettingList where tenantId equals to UPDATED_TENANT_ID
        defaultSystemSettingShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where tenantId is not null
        defaultSystemSettingShouldBeFound("tenantId.specified=true");

        // Get all the systemSettingList where tenantId is null
        defaultSystemSettingShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultSystemSettingShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the systemSettingList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultSystemSettingShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultSystemSettingShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the systemSettingList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultSystemSettingShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where tenantId is less than DEFAULT_TENANT_ID
        defaultSystemSettingShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the systemSettingList where tenantId is less than UPDATED_TENANT_ID
        defaultSystemSettingShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSystemSettingsByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList where tenantId is greater than DEFAULT_TENANT_ID
        defaultSystemSettingShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the systemSettingList where tenantId is greater than SMALLER_TENANT_ID
        defaultSystemSettingShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSystemSettingShouldBeFound(String filter) throws Exception {
        restSystemSettingMockMvc.perform(get("/api/system-settings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldValue").value(hasItem(DEFAULT_FIELD_VALUE)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].settingEnabled").value(hasItem(DEFAULT_SETTING_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restSystemSettingMockMvc.perform(get("/api/system-settings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSystemSettingShouldNotBeFound(String filter) throws Exception {
        restSystemSettingMockMvc.perform(get("/api/system-settings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemSettingMockMvc.perform(get("/api/system-settings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSystemSetting() throws Exception {
        // Get the systemSetting
        restSystemSettingMockMvc.perform(get("/api/system-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        int databaseSizeBeforeUpdate = systemSettingRepository.findAll().size();

        // Update the systemSetting
        SystemSetting updatedSystemSetting = systemSettingRepository.findById(systemSetting.getId()).get();
        // Disconnect from session so that the updates on updatedSystemSetting are not directly saved in db
        em.detach(updatedSystemSetting);
        updatedSystemSetting
            .fieldName(UPDATED_FIELD_NAME)
            .fieldValue(UPDATED_FIELD_VALUE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .settingEnabled(UPDATED_SETTING_ENABLED)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(updatedSystemSetting);

        restSystemSettingMockMvc.perform(put("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isOk());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeUpdate);
        SystemSetting testSystemSetting = systemSettingList.get(systemSettingList.size() - 1);
        assertThat(testSystemSetting.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testSystemSetting.getFieldValue()).isEqualTo(UPDATED_FIELD_VALUE);
        assertThat(testSystemSetting.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testSystemSetting.isSettingEnabled()).isEqualTo(UPDATED_SETTING_ENABLED);
        assertThat(testSystemSetting.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSystemSetting.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSystemSetting.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemSetting() throws Exception {
        int databaseSizeBeforeUpdate = systemSettingRepository.findAll().size();

        // Create the SystemSetting
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemSettingMockMvc.perform(put("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        int databaseSizeBeforeDelete = systemSettingRepository.findAll().size();

        // Delete the systemSetting
        restSystemSettingMockMvc.perform(delete("/api/system-settings/{id}", systemSetting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
