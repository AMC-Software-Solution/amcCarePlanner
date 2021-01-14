package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Communication;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.CommunicationRepository;
import com.amc.careplanner.service.CommunicationService;
import com.amc.careplanner.service.dto.CommunicationDTO;
import com.amc.careplanner.service.mapper.CommunicationMapper;
import com.amc.careplanner.service.dto.CommunicationCriteria;
import com.amc.careplanner.service.CommunicationQueryService;

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

import com.amc.careplanner.domain.enumeration.CommunicationType;
/**
 * Integration tests for the {@link CommunicationResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommunicationResourceIT {

    private static final CommunicationType DEFAULT_COMMUNICATION_TYPE = CommunicationType.EMAIL;
    private static final CommunicationType UPDATED_COMMUNICATION_TYPE = CommunicationType.SMS;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COMMUNICATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMMUNICATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_COMMUNICATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ATTACHMENT_URL = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT_URL = "BBBBBBBBBB";

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
    private CommunicationRepository communicationRepository;

    @Autowired
    private CommunicationMapper communicationMapper;

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    private CommunicationQueryService communicationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommunicationMockMvc;

    private Communication communication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Communication createEntity(EntityManager em) {
        Communication communication = new Communication()
            .communicationType(DEFAULT_COMMUNICATION_TYPE)
            .note(DEFAULT_NOTE)
            .communicationDate(DEFAULT_COMMUNICATION_DATE)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE)
            .attachmentUrl(DEFAULT_ATTACHMENT_URL)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return communication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Communication createUpdatedEntity(EntityManager em) {
        Communication communication = new Communication()
            .communicationType(UPDATED_COMMUNICATION_TYPE)
            .note(UPDATED_NOTE)
            .communicationDate(UPDATED_COMMUNICATION_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .attachmentUrl(UPDATED_ATTACHMENT_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return communication;
    }

    @BeforeEach
    public void initTest() {
        communication = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommunication() throws Exception {
        int databaseSizeBeforeCreate = communicationRepository.findAll().size();
        // Create the Communication
        CommunicationDTO communicationDTO = communicationMapper.toDto(communication);
        restCommunicationMockMvc.perform(post("/api/communications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationDTO)))
            .andExpect(status().isCreated());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeCreate + 1);
        Communication testCommunication = communicationList.get(communicationList.size() - 1);
        assertThat(testCommunication.getCommunicationType()).isEqualTo(DEFAULT_COMMUNICATION_TYPE);
        assertThat(testCommunication.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testCommunication.getCommunicationDate()).isEqualTo(DEFAULT_COMMUNICATION_DATE);
        assertThat(testCommunication.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testCommunication.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCommunication.getAttachmentUrl()).isEqualTo(DEFAULT_ATTACHMENT_URL);
        assertThat(testCommunication.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCommunication.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testCommunication.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testCommunication.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createCommunicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communicationRepository.findAll().size();

        // Create the Communication with an existing ID
        communication.setId(1L);
        CommunicationDTO communicationDTO = communicationMapper.toDto(communication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunicationMockMvc.perform(post("/api/communications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCommunicationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = communicationRepository.findAll().size();
        // set the field null
        communication.setCommunicationType(null);

        // Create the Communication, which fails.
        CommunicationDTO communicationDTO = communicationMapper.toDto(communication);


        restCommunicationMockMvc.perform(post("/api/communications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationDTO)))
            .andExpect(status().isBadRequest());

        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommunicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = communicationRepository.findAll().size();
        // set the field null
        communication.setCommunicationDate(null);

        // Create the Communication, which fails.
        CommunicationDTO communicationDTO = communicationMapper.toDto(communication);


        restCommunicationMockMvc.perform(post("/api/communications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationDTO)))
            .andExpect(status().isBadRequest());

        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = communicationRepository.findAll().size();
        // set the field null
        communication.setClientId(null);

        // Create the Communication, which fails.
        CommunicationDTO communicationDTO = communicationMapper.toDto(communication);


        restCommunicationMockMvc.perform(post("/api/communications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationDTO)))
            .andExpect(status().isBadRequest());

        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommunications() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList
        restCommunicationMockMvc.perform(get("/api/communications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communication.getId().intValue())))
            .andExpect(jsonPath("$.[*].communicationType").value(hasItem(DEFAULT_COMMUNICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].communicationDate").value(hasItem(sameInstant(DEFAULT_COMMUNICATION_DATE))))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].attachmentUrl").value(hasItem(DEFAULT_ATTACHMENT_URL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get the communication
        restCommunicationMockMvc.perform(get("/api/communications/{id}", communication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(communication.getId().intValue()))
            .andExpect(jsonPath("$.communicationType").value(DEFAULT_COMMUNICATION_TYPE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.communicationDate").value(sameInstant(DEFAULT_COMMUNICATION_DATE)))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.attachmentUrl").value(DEFAULT_ATTACHMENT_URL))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getCommunicationsByIdFiltering() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        Long id = communication.getId();

        defaultCommunicationShouldBeFound("id.equals=" + id);
        defaultCommunicationShouldNotBeFound("id.notEquals=" + id);

        defaultCommunicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommunicationShouldNotBeFound("id.greaterThan=" + id);

        defaultCommunicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommunicationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationType equals to DEFAULT_COMMUNICATION_TYPE
        defaultCommunicationShouldBeFound("communicationType.equals=" + DEFAULT_COMMUNICATION_TYPE);

        // Get all the communicationList where communicationType equals to UPDATED_COMMUNICATION_TYPE
        defaultCommunicationShouldNotBeFound("communicationType.equals=" + UPDATED_COMMUNICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationType not equals to DEFAULT_COMMUNICATION_TYPE
        defaultCommunicationShouldNotBeFound("communicationType.notEquals=" + DEFAULT_COMMUNICATION_TYPE);

        // Get all the communicationList where communicationType not equals to UPDATED_COMMUNICATION_TYPE
        defaultCommunicationShouldBeFound("communicationType.notEquals=" + UPDATED_COMMUNICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationType in DEFAULT_COMMUNICATION_TYPE or UPDATED_COMMUNICATION_TYPE
        defaultCommunicationShouldBeFound("communicationType.in=" + DEFAULT_COMMUNICATION_TYPE + "," + UPDATED_COMMUNICATION_TYPE);

        // Get all the communicationList where communicationType equals to UPDATED_COMMUNICATION_TYPE
        defaultCommunicationShouldNotBeFound("communicationType.in=" + UPDATED_COMMUNICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationType is not null
        defaultCommunicationShouldBeFound("communicationType.specified=true");

        // Get all the communicationList where communicationType is null
        defaultCommunicationShouldNotBeFound("communicationType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunicationsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where note equals to DEFAULT_NOTE
        defaultCommunicationShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the communicationList where note equals to UPDATED_NOTE
        defaultCommunicationShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where note not equals to DEFAULT_NOTE
        defaultCommunicationShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the communicationList where note not equals to UPDATED_NOTE
        defaultCommunicationShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultCommunicationShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the communicationList where note equals to UPDATED_NOTE
        defaultCommunicationShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where note is not null
        defaultCommunicationShouldBeFound("note.specified=true");

        // Get all the communicationList where note is null
        defaultCommunicationShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommunicationsByNoteContainsSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where note contains DEFAULT_NOTE
        defaultCommunicationShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the communicationList where note contains UPDATED_NOTE
        defaultCommunicationShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where note does not contain DEFAULT_NOTE
        defaultCommunicationShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the communicationList where note does not contain UPDATED_NOTE
        defaultCommunicationShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationDate equals to DEFAULT_COMMUNICATION_DATE
        defaultCommunicationShouldBeFound("communicationDate.equals=" + DEFAULT_COMMUNICATION_DATE);

        // Get all the communicationList where communicationDate equals to UPDATED_COMMUNICATION_DATE
        defaultCommunicationShouldNotBeFound("communicationDate.equals=" + UPDATED_COMMUNICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationDate not equals to DEFAULT_COMMUNICATION_DATE
        defaultCommunicationShouldNotBeFound("communicationDate.notEquals=" + DEFAULT_COMMUNICATION_DATE);

        // Get all the communicationList where communicationDate not equals to UPDATED_COMMUNICATION_DATE
        defaultCommunicationShouldBeFound("communicationDate.notEquals=" + UPDATED_COMMUNICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationDateIsInShouldWork() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationDate in DEFAULT_COMMUNICATION_DATE or UPDATED_COMMUNICATION_DATE
        defaultCommunicationShouldBeFound("communicationDate.in=" + DEFAULT_COMMUNICATION_DATE + "," + UPDATED_COMMUNICATION_DATE);

        // Get all the communicationList where communicationDate equals to UPDATED_COMMUNICATION_DATE
        defaultCommunicationShouldNotBeFound("communicationDate.in=" + UPDATED_COMMUNICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationDate is not null
        defaultCommunicationShouldBeFound("communicationDate.specified=true");

        // Get all the communicationList where communicationDate is null
        defaultCommunicationShouldNotBeFound("communicationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationDate is greater than or equal to DEFAULT_COMMUNICATION_DATE
        defaultCommunicationShouldBeFound("communicationDate.greaterThanOrEqual=" + DEFAULT_COMMUNICATION_DATE);

        // Get all the communicationList where communicationDate is greater than or equal to UPDATED_COMMUNICATION_DATE
        defaultCommunicationShouldNotBeFound("communicationDate.greaterThanOrEqual=" + UPDATED_COMMUNICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationDate is less than or equal to DEFAULT_COMMUNICATION_DATE
        defaultCommunicationShouldBeFound("communicationDate.lessThanOrEqual=" + DEFAULT_COMMUNICATION_DATE);

        // Get all the communicationList where communicationDate is less than or equal to SMALLER_COMMUNICATION_DATE
        defaultCommunicationShouldNotBeFound("communicationDate.lessThanOrEqual=" + SMALLER_COMMUNICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationDate is less than DEFAULT_COMMUNICATION_DATE
        defaultCommunicationShouldNotBeFound("communicationDate.lessThan=" + DEFAULT_COMMUNICATION_DATE);

        // Get all the communicationList where communicationDate is less than UPDATED_COMMUNICATION_DATE
        defaultCommunicationShouldBeFound("communicationDate.lessThan=" + UPDATED_COMMUNICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCommunicationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where communicationDate is greater than DEFAULT_COMMUNICATION_DATE
        defaultCommunicationShouldNotBeFound("communicationDate.greaterThan=" + DEFAULT_COMMUNICATION_DATE);

        // Get all the communicationList where communicationDate is greater than SMALLER_COMMUNICATION_DATE
        defaultCommunicationShouldBeFound("communicationDate.greaterThan=" + SMALLER_COMMUNICATION_DATE);
    }


    @Test
    @Transactional
    public void getAllCommunicationsByAttachmentUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where attachmentUrl equals to DEFAULT_ATTACHMENT_URL
        defaultCommunicationShouldBeFound("attachmentUrl.equals=" + DEFAULT_ATTACHMENT_URL);

        // Get all the communicationList where attachmentUrl equals to UPDATED_ATTACHMENT_URL
        defaultCommunicationShouldNotBeFound("attachmentUrl.equals=" + UPDATED_ATTACHMENT_URL);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByAttachmentUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where attachmentUrl not equals to DEFAULT_ATTACHMENT_URL
        defaultCommunicationShouldNotBeFound("attachmentUrl.notEquals=" + DEFAULT_ATTACHMENT_URL);

        // Get all the communicationList where attachmentUrl not equals to UPDATED_ATTACHMENT_URL
        defaultCommunicationShouldBeFound("attachmentUrl.notEquals=" + UPDATED_ATTACHMENT_URL);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByAttachmentUrlIsInShouldWork() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where attachmentUrl in DEFAULT_ATTACHMENT_URL or UPDATED_ATTACHMENT_URL
        defaultCommunicationShouldBeFound("attachmentUrl.in=" + DEFAULT_ATTACHMENT_URL + "," + UPDATED_ATTACHMENT_URL);

        // Get all the communicationList where attachmentUrl equals to UPDATED_ATTACHMENT_URL
        defaultCommunicationShouldNotBeFound("attachmentUrl.in=" + UPDATED_ATTACHMENT_URL);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByAttachmentUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where attachmentUrl is not null
        defaultCommunicationShouldBeFound("attachmentUrl.specified=true");

        // Get all the communicationList where attachmentUrl is null
        defaultCommunicationShouldNotBeFound("attachmentUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommunicationsByAttachmentUrlContainsSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where attachmentUrl contains DEFAULT_ATTACHMENT_URL
        defaultCommunicationShouldBeFound("attachmentUrl.contains=" + DEFAULT_ATTACHMENT_URL);

        // Get all the communicationList where attachmentUrl contains UPDATED_ATTACHMENT_URL
        defaultCommunicationShouldNotBeFound("attachmentUrl.contains=" + UPDATED_ATTACHMENT_URL);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByAttachmentUrlNotContainsSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where attachmentUrl does not contain DEFAULT_ATTACHMENT_URL
        defaultCommunicationShouldNotBeFound("attachmentUrl.doesNotContain=" + DEFAULT_ATTACHMENT_URL);

        // Get all the communicationList where attachmentUrl does not contain UPDATED_ATTACHMENT_URL
        defaultCommunicationShouldBeFound("attachmentUrl.doesNotContain=" + UPDATED_ATTACHMENT_URL);
    }


    @Test
    @Transactional
    public void getAllCommunicationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where createdDate equals to DEFAULT_CREATED_DATE
        defaultCommunicationShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the communicationList where createdDate equals to UPDATED_CREATED_DATE
        defaultCommunicationShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultCommunicationShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the communicationList where createdDate not equals to UPDATED_CREATED_DATE
        defaultCommunicationShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultCommunicationShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the communicationList where createdDate equals to UPDATED_CREATED_DATE
        defaultCommunicationShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where createdDate is not null
        defaultCommunicationShouldBeFound("createdDate.specified=true");

        // Get all the communicationList where createdDate is null
        defaultCommunicationShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultCommunicationShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the communicationList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultCommunicationShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultCommunicationShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the communicationList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultCommunicationShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where createdDate is less than DEFAULT_CREATED_DATE
        defaultCommunicationShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the communicationList where createdDate is less than UPDATED_CREATED_DATE
        defaultCommunicationShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultCommunicationShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the communicationList where createdDate is greater than SMALLER_CREATED_DATE
        defaultCommunicationShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllCommunicationsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultCommunicationShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the communicationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCommunicationShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultCommunicationShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the communicationList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultCommunicationShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultCommunicationShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the communicationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCommunicationShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where lastUpdatedDate is not null
        defaultCommunicationShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the communicationList where lastUpdatedDate is null
        defaultCommunicationShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunicationsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultCommunicationShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the communicationList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultCommunicationShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultCommunicationShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the communicationList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultCommunicationShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultCommunicationShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the communicationList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultCommunicationShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultCommunicationShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the communicationList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultCommunicationShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllCommunicationsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where clientId equals to DEFAULT_CLIENT_ID
        defaultCommunicationShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the communicationList where clientId equals to UPDATED_CLIENT_ID
        defaultCommunicationShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where clientId not equals to DEFAULT_CLIENT_ID
        defaultCommunicationShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the communicationList where clientId not equals to UPDATED_CLIENT_ID
        defaultCommunicationShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultCommunicationShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the communicationList where clientId equals to UPDATED_CLIENT_ID
        defaultCommunicationShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where clientId is not null
        defaultCommunicationShouldBeFound("clientId.specified=true");

        // Get all the communicationList where clientId is null
        defaultCommunicationShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunicationsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultCommunicationShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the communicationList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultCommunicationShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultCommunicationShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the communicationList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultCommunicationShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where clientId is less than DEFAULT_CLIENT_ID
        defaultCommunicationShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the communicationList where clientId is less than UPDATED_CLIENT_ID
        defaultCommunicationShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where clientId is greater than DEFAULT_CLIENT_ID
        defaultCommunicationShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the communicationList where clientId is greater than SMALLER_CLIENT_ID
        defaultCommunicationShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllCommunicationsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultCommunicationShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the communicationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultCommunicationShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultCommunicationShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the communicationList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultCommunicationShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultCommunicationShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the communicationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultCommunicationShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCommunicationsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList where hasExtraData is not null
        defaultCommunicationShouldBeFound("hasExtraData.specified=true");

        // Get all the communicationList where hasExtraData is null
        defaultCommunicationShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunicationsByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        communication.setServiceUser(serviceUser);
        communicationRepository.saveAndFlush(communication);
        Long serviceUserId = serviceUser.getId();

        // Get all the communicationList where serviceUser equals to serviceUserId
        defaultCommunicationShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the communicationList where serviceUser equals to serviceUserId + 1
        defaultCommunicationShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunicationsByCommunicatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);
        Employee communicatedBy = EmployeeResourceIT.createEntity(em);
        em.persist(communicatedBy);
        em.flush();
        communication.setCommunicatedBy(communicatedBy);
        communicationRepository.saveAndFlush(communication);
        Long communicatedById = communicatedBy.getId();

        // Get all the communicationList where communicatedBy equals to communicatedById
        defaultCommunicationShouldBeFound("communicatedById.equals=" + communicatedById);

        // Get all the communicationList where communicatedBy equals to communicatedById + 1
        defaultCommunicationShouldNotBeFound("communicatedById.equals=" + (communicatedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommunicationShouldBeFound(String filter) throws Exception {
        restCommunicationMockMvc.perform(get("/api/communications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communication.getId().intValue())))
            .andExpect(jsonPath("$.[*].communicationType").value(hasItem(DEFAULT_COMMUNICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].communicationDate").value(hasItem(sameInstant(DEFAULT_COMMUNICATION_DATE))))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].attachmentUrl").value(hasItem(DEFAULT_ATTACHMENT_URL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restCommunicationMockMvc.perform(get("/api/communications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommunicationShouldNotBeFound(String filter) throws Exception {
        restCommunicationMockMvc.perform(get("/api/communications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommunicationMockMvc.perform(get("/api/communications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCommunication() throws Exception {
        // Get the communication
        restCommunicationMockMvc.perform(get("/api/communications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();

        // Update the communication
        Communication updatedCommunication = communicationRepository.findById(communication.getId()).get();
        // Disconnect from session so that the updates on updatedCommunication are not directly saved in db
        em.detach(updatedCommunication);
        updatedCommunication
            .communicationType(UPDATED_COMMUNICATION_TYPE)
            .note(UPDATED_NOTE)
            .communicationDate(UPDATED_COMMUNICATION_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .attachmentUrl(UPDATED_ATTACHMENT_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        CommunicationDTO communicationDTO = communicationMapper.toDto(updatedCommunication);

        restCommunicationMockMvc.perform(put("/api/communications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationDTO)))
            .andExpect(status().isOk());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
        Communication testCommunication = communicationList.get(communicationList.size() - 1);
        assertThat(testCommunication.getCommunicationType()).isEqualTo(UPDATED_COMMUNICATION_TYPE);
        assertThat(testCommunication.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testCommunication.getCommunicationDate()).isEqualTo(UPDATED_COMMUNICATION_DATE);
        assertThat(testCommunication.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testCommunication.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCommunication.getAttachmentUrl()).isEqualTo(UPDATED_ATTACHMENT_URL);
        assertThat(testCommunication.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCommunication.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testCommunication.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testCommunication.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingCommunication() throws Exception {
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();

        // Create the Communication
        CommunicationDTO communicationDTO = communicationMapper.toDto(communication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunicationMockMvc.perform(put("/api/communications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        int databaseSizeBeforeDelete = communicationRepository.findAll().size();

        // Delete the communication
        restCommunicationMockMvc.perform(delete("/api/communications/{id}", communication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
