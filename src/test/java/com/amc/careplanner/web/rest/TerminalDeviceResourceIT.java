package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.TerminalDevice;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.TerminalDeviceRepository;
import com.amc.careplanner.service.TerminalDeviceService;
import com.amc.careplanner.service.dto.TerminalDeviceDTO;
import com.amc.careplanner.service.mapper.TerminalDeviceMapper;
import com.amc.careplanner.service.dto.TerminalDeviceCriteria;
import com.amc.careplanner.service.TerminalDeviceQueryService;

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
 * Integration tests for the {@link TerminalDeviceResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TerminalDeviceResourceIT {

    private static final String DEFAULT_DEVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_MODEL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REGISTERED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTERED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_REGISTERED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_IMEI = "AAAAAAAAAA";
    private static final String UPDATED_IMEI = "BBBBBBBBBB";

    private static final String DEFAULT_SIM_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SIM_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_USER_STARTED_USING_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_USER_STARTED_USING_FROM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_USER_STARTED_USING_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DEVICE_ON_LOCATION_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DEVICE_ON_LOCATION_FROM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DEVICE_ON_LOCATION_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_OPERATING_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_OPERATING_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNER_ENTITY_ID = 1L;
    private static final Long UPDATED_OWNER_ENTITY_ID = 2L;
    private static final Long SMALLER_OWNER_ENTITY_ID = 1L - 1L;

    private static final String DEFAULT_OWNER_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_ENTITY_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    @Autowired
    private TerminalDeviceRepository terminalDeviceRepository;

    @Autowired
    private TerminalDeviceMapper terminalDeviceMapper;

    @Autowired
    private TerminalDeviceService terminalDeviceService;

    @Autowired
    private TerminalDeviceQueryService terminalDeviceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerminalDeviceMockMvc;

    private TerminalDevice terminalDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerminalDevice createEntity(EntityManager em) {
        TerminalDevice terminalDevice = new TerminalDevice()
            .deviceName(DEFAULT_DEVICE_NAME)
            .deviceModel(DEFAULT_DEVICE_MODEL)
            .registeredDate(DEFAULT_REGISTERED_DATE)
            .imei(DEFAULT_IMEI)
            .simNumber(DEFAULT_SIM_NUMBER)
            .userStartedUsingFrom(DEFAULT_USER_STARTED_USING_FROM)
            .deviceOnLocationFrom(DEFAULT_DEVICE_ON_LOCATION_FROM)
            .operatingSystem(DEFAULT_OPERATING_SYSTEM)
            .note(DEFAULT_NOTE)
            .ownerEntityId(DEFAULT_OWNER_ENTITY_ID)
            .ownerEntityName(DEFAULT_OWNER_ENTITY_NAME)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID);
        return terminalDevice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerminalDevice createUpdatedEntity(EntityManager em) {
        TerminalDevice terminalDevice = new TerminalDevice()
            .deviceName(UPDATED_DEVICE_NAME)
            .deviceModel(UPDATED_DEVICE_MODEL)
            .registeredDate(UPDATED_REGISTERED_DATE)
            .imei(UPDATED_IMEI)
            .simNumber(UPDATED_SIM_NUMBER)
            .userStartedUsingFrom(UPDATED_USER_STARTED_USING_FROM)
            .deviceOnLocationFrom(UPDATED_DEVICE_ON_LOCATION_FROM)
            .operatingSystem(UPDATED_OPERATING_SYSTEM)
            .note(UPDATED_NOTE)
            .ownerEntityId(UPDATED_OWNER_ENTITY_ID)
            .ownerEntityName(UPDATED_OWNER_ENTITY_NAME)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        return terminalDevice;
    }

    @BeforeEach
    public void initTest() {
        terminalDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerminalDevice() throws Exception {
        int databaseSizeBeforeCreate = terminalDeviceRepository.findAll().size();
        // Create the TerminalDevice
        TerminalDeviceDTO terminalDeviceDTO = terminalDeviceMapper.toDto(terminalDevice);
        restTerminalDeviceMockMvc.perform(post("/api/terminal-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminalDeviceDTO)))
            .andExpect(status().isCreated());

        // Validate the TerminalDevice in the database
        List<TerminalDevice> terminalDeviceList = terminalDeviceRepository.findAll();
        assertThat(terminalDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        TerminalDevice testTerminalDevice = terminalDeviceList.get(terminalDeviceList.size() - 1);
        assertThat(testTerminalDevice.getDeviceName()).isEqualTo(DEFAULT_DEVICE_NAME);
        assertThat(testTerminalDevice.getDeviceModel()).isEqualTo(DEFAULT_DEVICE_MODEL);
        assertThat(testTerminalDevice.getRegisteredDate()).isEqualTo(DEFAULT_REGISTERED_DATE);
        assertThat(testTerminalDevice.getImei()).isEqualTo(DEFAULT_IMEI);
        assertThat(testTerminalDevice.getSimNumber()).isEqualTo(DEFAULT_SIM_NUMBER);
        assertThat(testTerminalDevice.getUserStartedUsingFrom()).isEqualTo(DEFAULT_USER_STARTED_USING_FROM);
        assertThat(testTerminalDevice.getDeviceOnLocationFrom()).isEqualTo(DEFAULT_DEVICE_ON_LOCATION_FROM);
        assertThat(testTerminalDevice.getOperatingSystem()).isEqualTo(DEFAULT_OPERATING_SYSTEM);
        assertThat(testTerminalDevice.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTerminalDevice.getOwnerEntityId()).isEqualTo(DEFAULT_OWNER_ENTITY_ID);
        assertThat(testTerminalDevice.getOwnerEntityName()).isEqualTo(DEFAULT_OWNER_ENTITY_NAME);
        assertThat(testTerminalDevice.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testTerminalDevice.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void createTerminalDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = terminalDeviceRepository.findAll().size();

        // Create the TerminalDevice with an existing ID
        terminalDevice.setId(1L);
        TerminalDeviceDTO terminalDeviceDTO = terminalDeviceMapper.toDto(terminalDevice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerminalDeviceMockMvc.perform(post("/api/terminal-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminalDeviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TerminalDevice in the database
        List<TerminalDevice> terminalDeviceList = terminalDeviceRepository.findAll();
        assertThat(terminalDeviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = terminalDeviceRepository.findAll().size();
        // set the field null
        terminalDevice.setClientId(null);

        // Create the TerminalDevice, which fails.
        TerminalDeviceDTO terminalDeviceDTO = terminalDeviceMapper.toDto(terminalDevice);


        restTerminalDeviceMockMvc.perform(post("/api/terminal-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminalDeviceDTO)))
            .andExpect(status().isBadRequest());

        List<TerminalDevice> terminalDeviceList = terminalDeviceRepository.findAll();
        assertThat(terminalDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTerminalDevices() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList
        restTerminalDeviceMockMvc.perform(get("/api/terminal-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terminalDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME)))
            .andExpect(jsonPath("$.[*].deviceModel").value(hasItem(DEFAULT_DEVICE_MODEL)))
            .andExpect(jsonPath("$.[*].registeredDate").value(hasItem(sameInstant(DEFAULT_REGISTERED_DATE))))
            .andExpect(jsonPath("$.[*].imei").value(hasItem(DEFAULT_IMEI)))
            .andExpect(jsonPath("$.[*].simNumber").value(hasItem(DEFAULT_SIM_NUMBER)))
            .andExpect(jsonPath("$.[*].userStartedUsingFrom").value(hasItem(sameInstant(DEFAULT_USER_STARTED_USING_FROM))))
            .andExpect(jsonPath("$.[*].deviceOnLocationFrom").value(hasItem(sameInstant(DEFAULT_DEVICE_ON_LOCATION_FROM))))
            .andExpect(jsonPath("$.[*].operatingSystem").value(hasItem(DEFAULT_OPERATING_SYSTEM)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].ownerEntityId").value(hasItem(DEFAULT_OWNER_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownerEntityName").value(hasItem(DEFAULT_OWNER_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTerminalDevice() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get the terminalDevice
        restTerminalDeviceMockMvc.perform(get("/api/terminal-devices/{id}", terminalDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(terminalDevice.getId().intValue()))
            .andExpect(jsonPath("$.deviceName").value(DEFAULT_DEVICE_NAME))
            .andExpect(jsonPath("$.deviceModel").value(DEFAULT_DEVICE_MODEL))
            .andExpect(jsonPath("$.registeredDate").value(sameInstant(DEFAULT_REGISTERED_DATE)))
            .andExpect(jsonPath("$.imei").value(DEFAULT_IMEI))
            .andExpect(jsonPath("$.simNumber").value(DEFAULT_SIM_NUMBER))
            .andExpect(jsonPath("$.userStartedUsingFrom").value(sameInstant(DEFAULT_USER_STARTED_USING_FROM)))
            .andExpect(jsonPath("$.deviceOnLocationFrom").value(sameInstant(DEFAULT_DEVICE_ON_LOCATION_FROM)))
            .andExpect(jsonPath("$.operatingSystem").value(DEFAULT_OPERATING_SYSTEM))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.ownerEntityId").value(DEFAULT_OWNER_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.ownerEntityName").value(DEFAULT_OWNER_ENTITY_NAME))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getTerminalDevicesByIdFiltering() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        Long id = terminalDevice.getId();

        defaultTerminalDeviceShouldBeFound("id.equals=" + id);
        defaultTerminalDeviceShouldNotBeFound("id.notEquals=" + id);

        defaultTerminalDeviceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTerminalDeviceShouldNotBeFound("id.greaterThan=" + id);

        defaultTerminalDeviceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTerminalDeviceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceName equals to DEFAULT_DEVICE_NAME
        defaultTerminalDeviceShouldBeFound("deviceName.equals=" + DEFAULT_DEVICE_NAME);

        // Get all the terminalDeviceList where deviceName equals to UPDATED_DEVICE_NAME
        defaultTerminalDeviceShouldNotBeFound("deviceName.equals=" + UPDATED_DEVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceName not equals to DEFAULT_DEVICE_NAME
        defaultTerminalDeviceShouldNotBeFound("deviceName.notEquals=" + DEFAULT_DEVICE_NAME);

        // Get all the terminalDeviceList where deviceName not equals to UPDATED_DEVICE_NAME
        defaultTerminalDeviceShouldBeFound("deviceName.notEquals=" + UPDATED_DEVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceNameIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceName in DEFAULT_DEVICE_NAME or UPDATED_DEVICE_NAME
        defaultTerminalDeviceShouldBeFound("deviceName.in=" + DEFAULT_DEVICE_NAME + "," + UPDATED_DEVICE_NAME);

        // Get all the terminalDeviceList where deviceName equals to UPDATED_DEVICE_NAME
        defaultTerminalDeviceShouldNotBeFound("deviceName.in=" + UPDATED_DEVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceName is not null
        defaultTerminalDeviceShouldBeFound("deviceName.specified=true");

        // Get all the terminalDeviceList where deviceName is null
        defaultTerminalDeviceShouldNotBeFound("deviceName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceNameContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceName contains DEFAULT_DEVICE_NAME
        defaultTerminalDeviceShouldBeFound("deviceName.contains=" + DEFAULT_DEVICE_NAME);

        // Get all the terminalDeviceList where deviceName contains UPDATED_DEVICE_NAME
        defaultTerminalDeviceShouldNotBeFound("deviceName.contains=" + UPDATED_DEVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceNameNotContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceName does not contain DEFAULT_DEVICE_NAME
        defaultTerminalDeviceShouldNotBeFound("deviceName.doesNotContain=" + DEFAULT_DEVICE_NAME);

        // Get all the terminalDeviceList where deviceName does not contain UPDATED_DEVICE_NAME
        defaultTerminalDeviceShouldBeFound("deviceName.doesNotContain=" + UPDATED_DEVICE_NAME);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceModelIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceModel equals to DEFAULT_DEVICE_MODEL
        defaultTerminalDeviceShouldBeFound("deviceModel.equals=" + DEFAULT_DEVICE_MODEL);

        // Get all the terminalDeviceList where deviceModel equals to UPDATED_DEVICE_MODEL
        defaultTerminalDeviceShouldNotBeFound("deviceModel.equals=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceModelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceModel not equals to DEFAULT_DEVICE_MODEL
        defaultTerminalDeviceShouldNotBeFound("deviceModel.notEquals=" + DEFAULT_DEVICE_MODEL);

        // Get all the terminalDeviceList where deviceModel not equals to UPDATED_DEVICE_MODEL
        defaultTerminalDeviceShouldBeFound("deviceModel.notEquals=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceModelIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceModel in DEFAULT_DEVICE_MODEL or UPDATED_DEVICE_MODEL
        defaultTerminalDeviceShouldBeFound("deviceModel.in=" + DEFAULT_DEVICE_MODEL + "," + UPDATED_DEVICE_MODEL);

        // Get all the terminalDeviceList where deviceModel equals to UPDATED_DEVICE_MODEL
        defaultTerminalDeviceShouldNotBeFound("deviceModel.in=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceModel is not null
        defaultTerminalDeviceShouldBeFound("deviceModel.specified=true");

        // Get all the terminalDeviceList where deviceModel is null
        defaultTerminalDeviceShouldNotBeFound("deviceModel.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceModelContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceModel contains DEFAULT_DEVICE_MODEL
        defaultTerminalDeviceShouldBeFound("deviceModel.contains=" + DEFAULT_DEVICE_MODEL);

        // Get all the terminalDeviceList where deviceModel contains UPDATED_DEVICE_MODEL
        defaultTerminalDeviceShouldNotBeFound("deviceModel.contains=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceModelNotContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceModel does not contain DEFAULT_DEVICE_MODEL
        defaultTerminalDeviceShouldNotBeFound("deviceModel.doesNotContain=" + DEFAULT_DEVICE_MODEL);

        // Get all the terminalDeviceList where deviceModel does not contain UPDATED_DEVICE_MODEL
        defaultTerminalDeviceShouldBeFound("deviceModel.doesNotContain=" + UPDATED_DEVICE_MODEL);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByRegisteredDateIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where registeredDate equals to DEFAULT_REGISTERED_DATE
        defaultTerminalDeviceShouldBeFound("registeredDate.equals=" + DEFAULT_REGISTERED_DATE);

        // Get all the terminalDeviceList where registeredDate equals to UPDATED_REGISTERED_DATE
        defaultTerminalDeviceShouldNotBeFound("registeredDate.equals=" + UPDATED_REGISTERED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByRegisteredDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where registeredDate not equals to DEFAULT_REGISTERED_DATE
        defaultTerminalDeviceShouldNotBeFound("registeredDate.notEquals=" + DEFAULT_REGISTERED_DATE);

        // Get all the terminalDeviceList where registeredDate not equals to UPDATED_REGISTERED_DATE
        defaultTerminalDeviceShouldBeFound("registeredDate.notEquals=" + UPDATED_REGISTERED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByRegisteredDateIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where registeredDate in DEFAULT_REGISTERED_DATE or UPDATED_REGISTERED_DATE
        defaultTerminalDeviceShouldBeFound("registeredDate.in=" + DEFAULT_REGISTERED_DATE + "," + UPDATED_REGISTERED_DATE);

        // Get all the terminalDeviceList where registeredDate equals to UPDATED_REGISTERED_DATE
        defaultTerminalDeviceShouldNotBeFound("registeredDate.in=" + UPDATED_REGISTERED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByRegisteredDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where registeredDate is not null
        defaultTerminalDeviceShouldBeFound("registeredDate.specified=true");

        // Get all the terminalDeviceList where registeredDate is null
        defaultTerminalDeviceShouldNotBeFound("registeredDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByRegisteredDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where registeredDate is greater than or equal to DEFAULT_REGISTERED_DATE
        defaultTerminalDeviceShouldBeFound("registeredDate.greaterThanOrEqual=" + DEFAULT_REGISTERED_DATE);

        // Get all the terminalDeviceList where registeredDate is greater than or equal to UPDATED_REGISTERED_DATE
        defaultTerminalDeviceShouldNotBeFound("registeredDate.greaterThanOrEqual=" + UPDATED_REGISTERED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByRegisteredDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where registeredDate is less than or equal to DEFAULT_REGISTERED_DATE
        defaultTerminalDeviceShouldBeFound("registeredDate.lessThanOrEqual=" + DEFAULT_REGISTERED_DATE);

        // Get all the terminalDeviceList where registeredDate is less than or equal to SMALLER_REGISTERED_DATE
        defaultTerminalDeviceShouldNotBeFound("registeredDate.lessThanOrEqual=" + SMALLER_REGISTERED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByRegisteredDateIsLessThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where registeredDate is less than DEFAULT_REGISTERED_DATE
        defaultTerminalDeviceShouldNotBeFound("registeredDate.lessThan=" + DEFAULT_REGISTERED_DATE);

        // Get all the terminalDeviceList where registeredDate is less than UPDATED_REGISTERED_DATE
        defaultTerminalDeviceShouldBeFound("registeredDate.lessThan=" + UPDATED_REGISTERED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByRegisteredDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where registeredDate is greater than DEFAULT_REGISTERED_DATE
        defaultTerminalDeviceShouldNotBeFound("registeredDate.greaterThan=" + DEFAULT_REGISTERED_DATE);

        // Get all the terminalDeviceList where registeredDate is greater than SMALLER_REGISTERED_DATE
        defaultTerminalDeviceShouldBeFound("registeredDate.greaterThan=" + SMALLER_REGISTERED_DATE);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByImeiIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where imei equals to DEFAULT_IMEI
        defaultTerminalDeviceShouldBeFound("imei.equals=" + DEFAULT_IMEI);

        // Get all the terminalDeviceList where imei equals to UPDATED_IMEI
        defaultTerminalDeviceShouldNotBeFound("imei.equals=" + UPDATED_IMEI);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByImeiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where imei not equals to DEFAULT_IMEI
        defaultTerminalDeviceShouldNotBeFound("imei.notEquals=" + DEFAULT_IMEI);

        // Get all the terminalDeviceList where imei not equals to UPDATED_IMEI
        defaultTerminalDeviceShouldBeFound("imei.notEquals=" + UPDATED_IMEI);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByImeiIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where imei in DEFAULT_IMEI or UPDATED_IMEI
        defaultTerminalDeviceShouldBeFound("imei.in=" + DEFAULT_IMEI + "," + UPDATED_IMEI);

        // Get all the terminalDeviceList where imei equals to UPDATED_IMEI
        defaultTerminalDeviceShouldNotBeFound("imei.in=" + UPDATED_IMEI);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByImeiIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where imei is not null
        defaultTerminalDeviceShouldBeFound("imei.specified=true");

        // Get all the terminalDeviceList where imei is null
        defaultTerminalDeviceShouldNotBeFound("imei.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalDevicesByImeiContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where imei contains DEFAULT_IMEI
        defaultTerminalDeviceShouldBeFound("imei.contains=" + DEFAULT_IMEI);

        // Get all the terminalDeviceList where imei contains UPDATED_IMEI
        defaultTerminalDeviceShouldNotBeFound("imei.contains=" + UPDATED_IMEI);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByImeiNotContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where imei does not contain DEFAULT_IMEI
        defaultTerminalDeviceShouldNotBeFound("imei.doesNotContain=" + DEFAULT_IMEI);

        // Get all the terminalDeviceList where imei does not contain UPDATED_IMEI
        defaultTerminalDeviceShouldBeFound("imei.doesNotContain=" + UPDATED_IMEI);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesBySimNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where simNumber equals to DEFAULT_SIM_NUMBER
        defaultTerminalDeviceShouldBeFound("simNumber.equals=" + DEFAULT_SIM_NUMBER);

        // Get all the terminalDeviceList where simNumber equals to UPDATED_SIM_NUMBER
        defaultTerminalDeviceShouldNotBeFound("simNumber.equals=" + UPDATED_SIM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesBySimNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where simNumber not equals to DEFAULT_SIM_NUMBER
        defaultTerminalDeviceShouldNotBeFound("simNumber.notEquals=" + DEFAULT_SIM_NUMBER);

        // Get all the terminalDeviceList where simNumber not equals to UPDATED_SIM_NUMBER
        defaultTerminalDeviceShouldBeFound("simNumber.notEquals=" + UPDATED_SIM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesBySimNumberIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where simNumber in DEFAULT_SIM_NUMBER or UPDATED_SIM_NUMBER
        defaultTerminalDeviceShouldBeFound("simNumber.in=" + DEFAULT_SIM_NUMBER + "," + UPDATED_SIM_NUMBER);

        // Get all the terminalDeviceList where simNumber equals to UPDATED_SIM_NUMBER
        defaultTerminalDeviceShouldNotBeFound("simNumber.in=" + UPDATED_SIM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesBySimNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where simNumber is not null
        defaultTerminalDeviceShouldBeFound("simNumber.specified=true");

        // Get all the terminalDeviceList where simNumber is null
        defaultTerminalDeviceShouldNotBeFound("simNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalDevicesBySimNumberContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where simNumber contains DEFAULT_SIM_NUMBER
        defaultTerminalDeviceShouldBeFound("simNumber.contains=" + DEFAULT_SIM_NUMBER);

        // Get all the terminalDeviceList where simNumber contains UPDATED_SIM_NUMBER
        defaultTerminalDeviceShouldNotBeFound("simNumber.contains=" + UPDATED_SIM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesBySimNumberNotContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where simNumber does not contain DEFAULT_SIM_NUMBER
        defaultTerminalDeviceShouldNotBeFound("simNumber.doesNotContain=" + DEFAULT_SIM_NUMBER);

        // Get all the terminalDeviceList where simNumber does not contain UPDATED_SIM_NUMBER
        defaultTerminalDeviceShouldBeFound("simNumber.doesNotContain=" + UPDATED_SIM_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByUserStartedUsingFromIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where userStartedUsingFrom equals to DEFAULT_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldBeFound("userStartedUsingFrom.equals=" + DEFAULT_USER_STARTED_USING_FROM);

        // Get all the terminalDeviceList where userStartedUsingFrom equals to UPDATED_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldNotBeFound("userStartedUsingFrom.equals=" + UPDATED_USER_STARTED_USING_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByUserStartedUsingFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where userStartedUsingFrom not equals to DEFAULT_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldNotBeFound("userStartedUsingFrom.notEquals=" + DEFAULT_USER_STARTED_USING_FROM);

        // Get all the terminalDeviceList where userStartedUsingFrom not equals to UPDATED_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldBeFound("userStartedUsingFrom.notEquals=" + UPDATED_USER_STARTED_USING_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByUserStartedUsingFromIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where userStartedUsingFrom in DEFAULT_USER_STARTED_USING_FROM or UPDATED_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldBeFound("userStartedUsingFrom.in=" + DEFAULT_USER_STARTED_USING_FROM + "," + UPDATED_USER_STARTED_USING_FROM);

        // Get all the terminalDeviceList where userStartedUsingFrom equals to UPDATED_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldNotBeFound("userStartedUsingFrom.in=" + UPDATED_USER_STARTED_USING_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByUserStartedUsingFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where userStartedUsingFrom is not null
        defaultTerminalDeviceShouldBeFound("userStartedUsingFrom.specified=true");

        // Get all the terminalDeviceList where userStartedUsingFrom is null
        defaultTerminalDeviceShouldNotBeFound("userStartedUsingFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByUserStartedUsingFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where userStartedUsingFrom is greater than or equal to DEFAULT_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldBeFound("userStartedUsingFrom.greaterThanOrEqual=" + DEFAULT_USER_STARTED_USING_FROM);

        // Get all the terminalDeviceList where userStartedUsingFrom is greater than or equal to UPDATED_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldNotBeFound("userStartedUsingFrom.greaterThanOrEqual=" + UPDATED_USER_STARTED_USING_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByUserStartedUsingFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where userStartedUsingFrom is less than or equal to DEFAULT_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldBeFound("userStartedUsingFrom.lessThanOrEqual=" + DEFAULT_USER_STARTED_USING_FROM);

        // Get all the terminalDeviceList where userStartedUsingFrom is less than or equal to SMALLER_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldNotBeFound("userStartedUsingFrom.lessThanOrEqual=" + SMALLER_USER_STARTED_USING_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByUserStartedUsingFromIsLessThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where userStartedUsingFrom is less than DEFAULT_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldNotBeFound("userStartedUsingFrom.lessThan=" + DEFAULT_USER_STARTED_USING_FROM);

        // Get all the terminalDeviceList where userStartedUsingFrom is less than UPDATED_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldBeFound("userStartedUsingFrom.lessThan=" + UPDATED_USER_STARTED_USING_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByUserStartedUsingFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where userStartedUsingFrom is greater than DEFAULT_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldNotBeFound("userStartedUsingFrom.greaterThan=" + DEFAULT_USER_STARTED_USING_FROM);

        // Get all the terminalDeviceList where userStartedUsingFrom is greater than SMALLER_USER_STARTED_USING_FROM
        defaultTerminalDeviceShouldBeFound("userStartedUsingFrom.greaterThan=" + SMALLER_USER_STARTED_USING_FROM);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceOnLocationFromIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceOnLocationFrom equals to DEFAULT_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldBeFound("deviceOnLocationFrom.equals=" + DEFAULT_DEVICE_ON_LOCATION_FROM);

        // Get all the terminalDeviceList where deviceOnLocationFrom equals to UPDATED_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldNotBeFound("deviceOnLocationFrom.equals=" + UPDATED_DEVICE_ON_LOCATION_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceOnLocationFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceOnLocationFrom not equals to DEFAULT_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldNotBeFound("deviceOnLocationFrom.notEquals=" + DEFAULT_DEVICE_ON_LOCATION_FROM);

        // Get all the terminalDeviceList where deviceOnLocationFrom not equals to UPDATED_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldBeFound("deviceOnLocationFrom.notEquals=" + UPDATED_DEVICE_ON_LOCATION_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceOnLocationFromIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceOnLocationFrom in DEFAULT_DEVICE_ON_LOCATION_FROM or UPDATED_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldBeFound("deviceOnLocationFrom.in=" + DEFAULT_DEVICE_ON_LOCATION_FROM + "," + UPDATED_DEVICE_ON_LOCATION_FROM);

        // Get all the terminalDeviceList where deviceOnLocationFrom equals to UPDATED_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldNotBeFound("deviceOnLocationFrom.in=" + UPDATED_DEVICE_ON_LOCATION_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceOnLocationFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceOnLocationFrom is not null
        defaultTerminalDeviceShouldBeFound("deviceOnLocationFrom.specified=true");

        // Get all the terminalDeviceList where deviceOnLocationFrom is null
        defaultTerminalDeviceShouldNotBeFound("deviceOnLocationFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceOnLocationFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceOnLocationFrom is greater than or equal to DEFAULT_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldBeFound("deviceOnLocationFrom.greaterThanOrEqual=" + DEFAULT_DEVICE_ON_LOCATION_FROM);

        // Get all the terminalDeviceList where deviceOnLocationFrom is greater than or equal to UPDATED_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldNotBeFound("deviceOnLocationFrom.greaterThanOrEqual=" + UPDATED_DEVICE_ON_LOCATION_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceOnLocationFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceOnLocationFrom is less than or equal to DEFAULT_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldBeFound("deviceOnLocationFrom.lessThanOrEqual=" + DEFAULT_DEVICE_ON_LOCATION_FROM);

        // Get all the terminalDeviceList where deviceOnLocationFrom is less than or equal to SMALLER_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldNotBeFound("deviceOnLocationFrom.lessThanOrEqual=" + SMALLER_DEVICE_ON_LOCATION_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceOnLocationFromIsLessThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceOnLocationFrom is less than DEFAULT_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldNotBeFound("deviceOnLocationFrom.lessThan=" + DEFAULT_DEVICE_ON_LOCATION_FROM);

        // Get all the terminalDeviceList where deviceOnLocationFrom is less than UPDATED_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldBeFound("deviceOnLocationFrom.lessThan=" + UPDATED_DEVICE_ON_LOCATION_FROM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByDeviceOnLocationFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where deviceOnLocationFrom is greater than DEFAULT_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldNotBeFound("deviceOnLocationFrom.greaterThan=" + DEFAULT_DEVICE_ON_LOCATION_FROM);

        // Get all the terminalDeviceList where deviceOnLocationFrom is greater than SMALLER_DEVICE_ON_LOCATION_FROM
        defaultTerminalDeviceShouldBeFound("deviceOnLocationFrom.greaterThan=" + SMALLER_DEVICE_ON_LOCATION_FROM);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByOperatingSystemIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where operatingSystem equals to DEFAULT_OPERATING_SYSTEM
        defaultTerminalDeviceShouldBeFound("operatingSystem.equals=" + DEFAULT_OPERATING_SYSTEM);

        // Get all the terminalDeviceList where operatingSystem equals to UPDATED_OPERATING_SYSTEM
        defaultTerminalDeviceShouldNotBeFound("operatingSystem.equals=" + UPDATED_OPERATING_SYSTEM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOperatingSystemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where operatingSystem not equals to DEFAULT_OPERATING_SYSTEM
        defaultTerminalDeviceShouldNotBeFound("operatingSystem.notEquals=" + DEFAULT_OPERATING_SYSTEM);

        // Get all the terminalDeviceList where operatingSystem not equals to UPDATED_OPERATING_SYSTEM
        defaultTerminalDeviceShouldBeFound("operatingSystem.notEquals=" + UPDATED_OPERATING_SYSTEM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOperatingSystemIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where operatingSystem in DEFAULT_OPERATING_SYSTEM or UPDATED_OPERATING_SYSTEM
        defaultTerminalDeviceShouldBeFound("operatingSystem.in=" + DEFAULT_OPERATING_SYSTEM + "," + UPDATED_OPERATING_SYSTEM);

        // Get all the terminalDeviceList where operatingSystem equals to UPDATED_OPERATING_SYSTEM
        defaultTerminalDeviceShouldNotBeFound("operatingSystem.in=" + UPDATED_OPERATING_SYSTEM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOperatingSystemIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where operatingSystem is not null
        defaultTerminalDeviceShouldBeFound("operatingSystem.specified=true");

        // Get all the terminalDeviceList where operatingSystem is null
        defaultTerminalDeviceShouldNotBeFound("operatingSystem.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalDevicesByOperatingSystemContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where operatingSystem contains DEFAULT_OPERATING_SYSTEM
        defaultTerminalDeviceShouldBeFound("operatingSystem.contains=" + DEFAULT_OPERATING_SYSTEM);

        // Get all the terminalDeviceList where operatingSystem contains UPDATED_OPERATING_SYSTEM
        defaultTerminalDeviceShouldNotBeFound("operatingSystem.contains=" + UPDATED_OPERATING_SYSTEM);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOperatingSystemNotContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where operatingSystem does not contain DEFAULT_OPERATING_SYSTEM
        defaultTerminalDeviceShouldNotBeFound("operatingSystem.doesNotContain=" + DEFAULT_OPERATING_SYSTEM);

        // Get all the terminalDeviceList where operatingSystem does not contain UPDATED_OPERATING_SYSTEM
        defaultTerminalDeviceShouldBeFound("operatingSystem.doesNotContain=" + UPDATED_OPERATING_SYSTEM);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where note equals to DEFAULT_NOTE
        defaultTerminalDeviceShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the terminalDeviceList where note equals to UPDATED_NOTE
        defaultTerminalDeviceShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where note not equals to DEFAULT_NOTE
        defaultTerminalDeviceShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the terminalDeviceList where note not equals to UPDATED_NOTE
        defaultTerminalDeviceShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTerminalDeviceShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the terminalDeviceList where note equals to UPDATED_NOTE
        defaultTerminalDeviceShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where note is not null
        defaultTerminalDeviceShouldBeFound("note.specified=true");

        // Get all the terminalDeviceList where note is null
        defaultTerminalDeviceShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalDevicesByNoteContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where note contains DEFAULT_NOTE
        defaultTerminalDeviceShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the terminalDeviceList where note contains UPDATED_NOTE
        defaultTerminalDeviceShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where note does not contain DEFAULT_NOTE
        defaultTerminalDeviceShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the terminalDeviceList where note does not contain UPDATED_NOTE
        defaultTerminalDeviceShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityId equals to DEFAULT_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldBeFound("ownerEntityId.equals=" + DEFAULT_OWNER_ENTITY_ID);

        // Get all the terminalDeviceList where ownerEntityId equals to UPDATED_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldNotBeFound("ownerEntityId.equals=" + UPDATED_OWNER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityId not equals to DEFAULT_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldNotBeFound("ownerEntityId.notEquals=" + DEFAULT_OWNER_ENTITY_ID);

        // Get all the terminalDeviceList where ownerEntityId not equals to UPDATED_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldBeFound("ownerEntityId.notEquals=" + UPDATED_OWNER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityId in DEFAULT_OWNER_ENTITY_ID or UPDATED_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldBeFound("ownerEntityId.in=" + DEFAULT_OWNER_ENTITY_ID + "," + UPDATED_OWNER_ENTITY_ID);

        // Get all the terminalDeviceList where ownerEntityId equals to UPDATED_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldNotBeFound("ownerEntityId.in=" + UPDATED_OWNER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityId is not null
        defaultTerminalDeviceShouldBeFound("ownerEntityId.specified=true");

        // Get all the terminalDeviceList where ownerEntityId is null
        defaultTerminalDeviceShouldNotBeFound("ownerEntityId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityId is greater than or equal to DEFAULT_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldBeFound("ownerEntityId.greaterThanOrEqual=" + DEFAULT_OWNER_ENTITY_ID);

        // Get all the terminalDeviceList where ownerEntityId is greater than or equal to UPDATED_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldNotBeFound("ownerEntityId.greaterThanOrEqual=" + UPDATED_OWNER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityId is less than or equal to DEFAULT_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldBeFound("ownerEntityId.lessThanOrEqual=" + DEFAULT_OWNER_ENTITY_ID);

        // Get all the terminalDeviceList where ownerEntityId is less than or equal to SMALLER_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldNotBeFound("ownerEntityId.lessThanOrEqual=" + SMALLER_OWNER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityId is less than DEFAULT_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldNotBeFound("ownerEntityId.lessThan=" + DEFAULT_OWNER_ENTITY_ID);

        // Get all the terminalDeviceList where ownerEntityId is less than UPDATED_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldBeFound("ownerEntityId.lessThan=" + UPDATED_OWNER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityId is greater than DEFAULT_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldNotBeFound("ownerEntityId.greaterThan=" + DEFAULT_OWNER_ENTITY_ID);

        // Get all the terminalDeviceList where ownerEntityId is greater than SMALLER_OWNER_ENTITY_ID
        defaultTerminalDeviceShouldBeFound("ownerEntityId.greaterThan=" + SMALLER_OWNER_ENTITY_ID);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityName equals to DEFAULT_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldBeFound("ownerEntityName.equals=" + DEFAULT_OWNER_ENTITY_NAME);

        // Get all the terminalDeviceList where ownerEntityName equals to UPDATED_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldNotBeFound("ownerEntityName.equals=" + UPDATED_OWNER_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityName not equals to DEFAULT_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldNotBeFound("ownerEntityName.notEquals=" + DEFAULT_OWNER_ENTITY_NAME);

        // Get all the terminalDeviceList where ownerEntityName not equals to UPDATED_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldBeFound("ownerEntityName.notEquals=" + UPDATED_OWNER_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityNameIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityName in DEFAULT_OWNER_ENTITY_NAME or UPDATED_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldBeFound("ownerEntityName.in=" + DEFAULT_OWNER_ENTITY_NAME + "," + UPDATED_OWNER_ENTITY_NAME);

        // Get all the terminalDeviceList where ownerEntityName equals to UPDATED_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldNotBeFound("ownerEntityName.in=" + UPDATED_OWNER_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityName is not null
        defaultTerminalDeviceShouldBeFound("ownerEntityName.specified=true");

        // Get all the terminalDeviceList where ownerEntityName is null
        defaultTerminalDeviceShouldNotBeFound("ownerEntityName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityNameContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityName contains DEFAULT_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldBeFound("ownerEntityName.contains=" + DEFAULT_OWNER_ENTITY_NAME);

        // Get all the terminalDeviceList where ownerEntityName contains UPDATED_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldNotBeFound("ownerEntityName.contains=" + UPDATED_OWNER_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByOwnerEntityNameNotContainsSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where ownerEntityName does not contain DEFAULT_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldNotBeFound("ownerEntityName.doesNotContain=" + DEFAULT_OWNER_ENTITY_NAME);

        // Get all the terminalDeviceList where ownerEntityName does not contain UPDATED_OWNER_ENTITY_NAME
        defaultTerminalDeviceShouldBeFound("ownerEntityName.doesNotContain=" + UPDATED_OWNER_ENTITY_NAME);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the terminalDeviceList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the terminalDeviceList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the terminalDeviceList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where lastUpdatedDate is not null
        defaultTerminalDeviceShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the terminalDeviceList where lastUpdatedDate is null
        defaultTerminalDeviceShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the terminalDeviceList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the terminalDeviceList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the terminalDeviceList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the terminalDeviceList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultTerminalDeviceShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where clientId equals to DEFAULT_CLIENT_ID
        defaultTerminalDeviceShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the terminalDeviceList where clientId equals to UPDATED_CLIENT_ID
        defaultTerminalDeviceShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where clientId not equals to DEFAULT_CLIENT_ID
        defaultTerminalDeviceShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the terminalDeviceList where clientId not equals to UPDATED_CLIENT_ID
        defaultTerminalDeviceShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultTerminalDeviceShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the terminalDeviceList where clientId equals to UPDATED_CLIENT_ID
        defaultTerminalDeviceShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where clientId is not null
        defaultTerminalDeviceShouldBeFound("clientId.specified=true");

        // Get all the terminalDeviceList where clientId is null
        defaultTerminalDeviceShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultTerminalDeviceShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the terminalDeviceList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultTerminalDeviceShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultTerminalDeviceShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the terminalDeviceList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultTerminalDeviceShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where clientId is less than DEFAULT_CLIENT_ID
        defaultTerminalDeviceShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the terminalDeviceList where clientId is less than UPDATED_CLIENT_ID
        defaultTerminalDeviceShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllTerminalDevicesByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        // Get all the terminalDeviceList where clientId is greater than DEFAULT_CLIENT_ID
        defaultTerminalDeviceShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the terminalDeviceList where clientId is greater than SMALLER_CLIENT_ID
        defaultTerminalDeviceShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllTerminalDevicesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        terminalDevice.setEmployee(employee);
        terminalDeviceRepository.saveAndFlush(terminalDevice);
        Long employeeId = employee.getId();

        // Get all the terminalDeviceList where employee equals to employeeId
        defaultTerminalDeviceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the terminalDeviceList where employee equals to employeeId + 1
        defaultTerminalDeviceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTerminalDeviceShouldBeFound(String filter) throws Exception {
        restTerminalDeviceMockMvc.perform(get("/api/terminal-devices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terminalDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME)))
            .andExpect(jsonPath("$.[*].deviceModel").value(hasItem(DEFAULT_DEVICE_MODEL)))
            .andExpect(jsonPath("$.[*].registeredDate").value(hasItem(sameInstant(DEFAULT_REGISTERED_DATE))))
            .andExpect(jsonPath("$.[*].imei").value(hasItem(DEFAULT_IMEI)))
            .andExpect(jsonPath("$.[*].simNumber").value(hasItem(DEFAULT_SIM_NUMBER)))
            .andExpect(jsonPath("$.[*].userStartedUsingFrom").value(hasItem(sameInstant(DEFAULT_USER_STARTED_USING_FROM))))
            .andExpect(jsonPath("$.[*].deviceOnLocationFrom").value(hasItem(sameInstant(DEFAULT_DEVICE_ON_LOCATION_FROM))))
            .andExpect(jsonPath("$.[*].operatingSystem").value(hasItem(DEFAULT_OPERATING_SYSTEM)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].ownerEntityId").value(hasItem(DEFAULT_OWNER_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownerEntityName").value(hasItem(DEFAULT_OWNER_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));

        // Check, that the count call also returns 1
        restTerminalDeviceMockMvc.perform(get("/api/terminal-devices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTerminalDeviceShouldNotBeFound(String filter) throws Exception {
        restTerminalDeviceMockMvc.perform(get("/api/terminal-devices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTerminalDeviceMockMvc.perform(get("/api/terminal-devices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTerminalDevice() throws Exception {
        // Get the terminalDevice
        restTerminalDeviceMockMvc.perform(get("/api/terminal-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerminalDevice() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        int databaseSizeBeforeUpdate = terminalDeviceRepository.findAll().size();

        // Update the terminalDevice
        TerminalDevice updatedTerminalDevice = terminalDeviceRepository.findById(terminalDevice.getId()).get();
        // Disconnect from session so that the updates on updatedTerminalDevice are not directly saved in db
        em.detach(updatedTerminalDevice);
        updatedTerminalDevice
            .deviceName(UPDATED_DEVICE_NAME)
            .deviceModel(UPDATED_DEVICE_MODEL)
            .registeredDate(UPDATED_REGISTERED_DATE)
            .imei(UPDATED_IMEI)
            .simNumber(UPDATED_SIM_NUMBER)
            .userStartedUsingFrom(UPDATED_USER_STARTED_USING_FROM)
            .deviceOnLocationFrom(UPDATED_DEVICE_ON_LOCATION_FROM)
            .operatingSystem(UPDATED_OPERATING_SYSTEM)
            .note(UPDATED_NOTE)
            .ownerEntityId(UPDATED_OWNER_ENTITY_ID)
            .ownerEntityName(UPDATED_OWNER_ENTITY_NAME)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        TerminalDeviceDTO terminalDeviceDTO = terminalDeviceMapper.toDto(updatedTerminalDevice);

        restTerminalDeviceMockMvc.perform(put("/api/terminal-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminalDeviceDTO)))
            .andExpect(status().isOk());

        // Validate the TerminalDevice in the database
        List<TerminalDevice> terminalDeviceList = terminalDeviceRepository.findAll();
        assertThat(terminalDeviceList).hasSize(databaseSizeBeforeUpdate);
        TerminalDevice testTerminalDevice = terminalDeviceList.get(terminalDeviceList.size() - 1);
        assertThat(testTerminalDevice.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testTerminalDevice.getDeviceModel()).isEqualTo(UPDATED_DEVICE_MODEL);
        assertThat(testTerminalDevice.getRegisteredDate()).isEqualTo(UPDATED_REGISTERED_DATE);
        assertThat(testTerminalDevice.getImei()).isEqualTo(UPDATED_IMEI);
        assertThat(testTerminalDevice.getSimNumber()).isEqualTo(UPDATED_SIM_NUMBER);
        assertThat(testTerminalDevice.getUserStartedUsingFrom()).isEqualTo(UPDATED_USER_STARTED_USING_FROM);
        assertThat(testTerminalDevice.getDeviceOnLocationFrom()).isEqualTo(UPDATED_DEVICE_ON_LOCATION_FROM);
        assertThat(testTerminalDevice.getOperatingSystem()).isEqualTo(UPDATED_OPERATING_SYSTEM);
        assertThat(testTerminalDevice.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTerminalDevice.getOwnerEntityId()).isEqualTo(UPDATED_OWNER_ENTITY_ID);
        assertThat(testTerminalDevice.getOwnerEntityName()).isEqualTo(UPDATED_OWNER_ENTITY_NAME);
        assertThat(testTerminalDevice.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testTerminalDevice.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTerminalDevice() throws Exception {
        int databaseSizeBeforeUpdate = terminalDeviceRepository.findAll().size();

        // Create the TerminalDevice
        TerminalDeviceDTO terminalDeviceDTO = terminalDeviceMapper.toDto(terminalDevice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerminalDeviceMockMvc.perform(put("/api/terminal-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminalDeviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TerminalDevice in the database
        List<TerminalDevice> terminalDeviceList = terminalDeviceRepository.findAll();
        assertThat(terminalDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTerminalDevice() throws Exception {
        // Initialize the database
        terminalDeviceRepository.saveAndFlush(terminalDevice);

        int databaseSizeBeforeDelete = terminalDeviceRepository.findAll().size();

        // Delete the terminalDevice
        restTerminalDeviceMockMvc.perform(delete("/api/terminal-devices/{id}", terminalDevice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TerminalDevice> terminalDeviceList = terminalDeviceRepository.findAll();
        assertThat(terminalDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
