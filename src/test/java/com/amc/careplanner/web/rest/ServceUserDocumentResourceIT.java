package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.ServceUserDocument;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.ServceUserDocumentRepository;
import com.amc.careplanner.service.ServceUserDocumentService;
import com.amc.careplanner.service.dto.ServceUserDocumentDTO;
import com.amc.careplanner.service.mapper.ServceUserDocumentMapper;
import com.amc.careplanner.service.dto.ServceUserDocumentCriteria;
import com.amc.careplanner.service.ServceUserDocumentQueryService;

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
import java.time.LocalDate;
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

import com.amc.careplanner.domain.enumeration.DocumentStatus;
/**
 * Integration tests for the {@link ServceUserDocumentResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServceUserDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER = "BBBBBBBBBB";

    private static final DocumentStatus DEFAULT_DOCUMENT_STATUS = DocumentStatus.EXPIRED;
    private static final DocumentStatus UPDATED_DOCUMENT_STATUS = DocumentStatus.ACTIVE;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ISSUED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ISSUED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXPIRY_DATE = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_UPLOADED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPLOADED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_UPLOADED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final byte[] DEFAULT_DOCUMENT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DOCUMENT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_FILE_URL = "BBBBBBBBBB";

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
    private ServceUserDocumentRepository servceUserDocumentRepository;

    @Autowired
    private ServceUserDocumentMapper servceUserDocumentMapper;

    @Autowired
    private ServceUserDocumentService servceUserDocumentService;

    @Autowired
    private ServceUserDocumentQueryService servceUserDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServceUserDocumentMockMvc;

    private ServceUserDocument servceUserDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServceUserDocument createEntity(EntityManager em) {
        ServceUserDocument servceUserDocument = new ServceUserDocument()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .documentNumber(DEFAULT_DOCUMENT_NUMBER)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .note(DEFAULT_NOTE)
            .issuedDate(DEFAULT_ISSUED_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .uploadedDate(DEFAULT_UPLOADED_DATE)
            .documentFile(DEFAULT_DOCUMENT_FILE)
            .documentFileContentType(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE)
            .documentFileUrl(DEFAULT_DOCUMENT_FILE_URL)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return servceUserDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServceUserDocument createUpdatedEntity(EntityManager em) {
        ServceUserDocument servceUserDocument = new ServceUserDocument()
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .note(UPDATED_NOTE)
            .issuedDate(UPDATED_ISSUED_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .documentFile(UPDATED_DOCUMENT_FILE)
            .documentFileContentType(UPDATED_DOCUMENT_FILE_CONTENT_TYPE)
            .documentFileUrl(UPDATED_DOCUMENT_FILE_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return servceUserDocument;
    }

    @BeforeEach
    public void initTest() {
        servceUserDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createServceUserDocument() throws Exception {
        int databaseSizeBeforeCreate = servceUserDocumentRepository.findAll().size();
        // Create the ServceUserDocument
        ServceUserDocumentDTO servceUserDocumentDTO = servceUserDocumentMapper.toDto(servceUserDocument);
        restServceUserDocumentMockMvc.perform(post("/api/servce-user-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servceUserDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the ServceUserDocument in the database
        List<ServceUserDocument> servceUserDocumentList = servceUserDocumentRepository.findAll();
        assertThat(servceUserDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ServceUserDocument testServceUserDocument = servceUserDocumentList.get(servceUserDocumentList.size() - 1);
        assertThat(testServceUserDocument.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testServceUserDocument.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testServceUserDocument.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testServceUserDocument.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testServceUserDocument.getIssuedDate()).isEqualTo(DEFAULT_ISSUED_DATE);
        assertThat(testServceUserDocument.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testServceUserDocument.getUploadedDate()).isEqualTo(DEFAULT_UPLOADED_DATE);
        assertThat(testServceUserDocument.getDocumentFile()).isEqualTo(DEFAULT_DOCUMENT_FILE);
        assertThat(testServceUserDocument.getDocumentFileContentType()).isEqualTo(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE);
        assertThat(testServceUserDocument.getDocumentFileUrl()).isEqualTo(DEFAULT_DOCUMENT_FILE_URL);
        assertThat(testServceUserDocument.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testServceUserDocument.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testServceUserDocument.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testServceUserDocument.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createServceUserDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servceUserDocumentRepository.findAll().size();

        // Create the ServceUserDocument with an existing ID
        servceUserDocument.setId(1L);
        ServceUserDocumentDTO servceUserDocumentDTO = servceUserDocumentMapper.toDto(servceUserDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServceUserDocumentMockMvc.perform(post("/api/servce-user-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servceUserDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServceUserDocument in the database
        List<ServceUserDocument> servceUserDocumentList = servceUserDocumentRepository.findAll();
        assertThat(servceUserDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = servceUserDocumentRepository.findAll().size();
        // set the field null
        servceUserDocument.setDocumentName(null);

        // Create the ServceUserDocument, which fails.
        ServceUserDocumentDTO servceUserDocumentDTO = servceUserDocumentMapper.toDto(servceUserDocument);


        restServceUserDocumentMockMvc.perform(post("/api/servce-user-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servceUserDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ServceUserDocument> servceUserDocumentList = servceUserDocumentRepository.findAll();
        assertThat(servceUserDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = servceUserDocumentRepository.findAll().size();
        // set the field null
        servceUserDocument.setClientId(null);

        // Create the ServceUserDocument, which fails.
        ServceUserDocumentDTO servceUserDocumentDTO = servceUserDocumentMapper.toDto(servceUserDocument);


        restServceUserDocumentMockMvc.perform(post("/api/servce-user-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servceUserDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ServceUserDocument> servceUserDocumentList = servceUserDocumentRepository.findAll();
        assertThat(servceUserDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServceUserDocuments() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList
        restServceUserDocumentMockMvc.perform(get("/api/servce-user-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servceUserDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(DEFAULT_ISSUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(sameInstant(DEFAULT_UPLOADED_DATE))))
            .andExpect(jsonPath("$.[*].documentFileContentType").value(hasItem(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_FILE))))
            .andExpect(jsonPath("$.[*].documentFileUrl").value(hasItem(DEFAULT_DOCUMENT_FILE_URL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getServceUserDocument() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get the servceUserDocument
        restServceUserDocumentMockMvc.perform(get("/api/servce-user-documents/{id}", servceUserDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servceUserDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME))
            .andExpect(jsonPath("$.documentNumber").value(DEFAULT_DOCUMENT_NUMBER))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.issuedDate").value(DEFAULT_ISSUED_DATE.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.uploadedDate").value(sameInstant(DEFAULT_UPLOADED_DATE)))
            .andExpect(jsonPath("$.documentFileContentType").value(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentFile").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_FILE)))
            .andExpect(jsonPath("$.documentFileUrl").value(DEFAULT_DOCUMENT_FILE_URL))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getServceUserDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        Long id = servceUserDocument.getId();

        defaultServceUserDocumentShouldBeFound("id.equals=" + id);
        defaultServceUserDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultServceUserDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServceUserDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultServceUserDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServceUserDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentName equals to DEFAULT_DOCUMENT_NAME
        defaultServceUserDocumentShouldBeFound("documentName.equals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the servceUserDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultServceUserDocumentShouldNotBeFound("documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentName not equals to DEFAULT_DOCUMENT_NAME
        defaultServceUserDocumentShouldNotBeFound("documentName.notEquals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the servceUserDocumentList where documentName not equals to UPDATED_DOCUMENT_NAME
        defaultServceUserDocumentShouldBeFound("documentName.notEquals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentName in DEFAULT_DOCUMENT_NAME or UPDATED_DOCUMENT_NAME
        defaultServceUserDocumentShouldBeFound("documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME);

        // Get all the servceUserDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultServceUserDocumentShouldNotBeFound("documentName.in=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentName is not null
        defaultServceUserDocumentShouldBeFound("documentName.specified=true");

        // Get all the servceUserDocumentList where documentName is null
        defaultServceUserDocumentShouldNotBeFound("documentName.specified=false");
    }
                @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNameContainsSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentName contains DEFAULT_DOCUMENT_NAME
        defaultServceUserDocumentShouldBeFound("documentName.contains=" + DEFAULT_DOCUMENT_NAME);

        // Get all the servceUserDocumentList where documentName contains UPDATED_DOCUMENT_NAME
        defaultServceUserDocumentShouldNotBeFound("documentName.contains=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNameNotContainsSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentName does not contain DEFAULT_DOCUMENT_NAME
        defaultServceUserDocumentShouldNotBeFound("documentName.doesNotContain=" + DEFAULT_DOCUMENT_NAME);

        // Get all the servceUserDocumentList where documentName does not contain UPDATED_DOCUMENT_NAME
        defaultServceUserDocumentShouldBeFound("documentName.doesNotContain=" + UPDATED_DOCUMENT_NAME);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentNumber equals to DEFAULT_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldBeFound("documentNumber.equals=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the servceUserDocumentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldNotBeFound("documentNumber.equals=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentNumber not equals to DEFAULT_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldNotBeFound("documentNumber.notEquals=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the servceUserDocumentList where documentNumber not equals to UPDATED_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldBeFound("documentNumber.notEquals=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNumberIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentNumber in DEFAULT_DOCUMENT_NUMBER or UPDATED_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldBeFound("documentNumber.in=" + DEFAULT_DOCUMENT_NUMBER + "," + UPDATED_DOCUMENT_NUMBER);

        // Get all the servceUserDocumentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldNotBeFound("documentNumber.in=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentNumber is not null
        defaultServceUserDocumentShouldBeFound("documentNumber.specified=true");

        // Get all the servceUserDocumentList where documentNumber is null
        defaultServceUserDocumentShouldNotBeFound("documentNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNumberContainsSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentNumber contains DEFAULT_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldBeFound("documentNumber.contains=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the servceUserDocumentList where documentNumber contains UPDATED_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldNotBeFound("documentNumber.contains=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentNumberNotContainsSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentNumber does not contain DEFAULT_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldNotBeFound("documentNumber.doesNotContain=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the servceUserDocumentList where documentNumber does not contain UPDATED_DOCUMENT_NUMBER
        defaultServceUserDocumentShouldBeFound("documentNumber.doesNotContain=" + UPDATED_DOCUMENT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultServceUserDocumentShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the servceUserDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultServceUserDocumentShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultServceUserDocumentShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the servceUserDocumentList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultServceUserDocumentShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultServceUserDocumentShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the servceUserDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultServceUserDocumentShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentStatus is not null
        defaultServceUserDocumentShouldBeFound("documentStatus.specified=true");

        // Get all the servceUserDocumentList where documentStatus is null
        defaultServceUserDocumentShouldNotBeFound("documentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where note equals to DEFAULT_NOTE
        defaultServceUserDocumentShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the servceUserDocumentList where note equals to UPDATED_NOTE
        defaultServceUserDocumentShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where note not equals to DEFAULT_NOTE
        defaultServceUserDocumentShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the servceUserDocumentList where note not equals to UPDATED_NOTE
        defaultServceUserDocumentShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultServceUserDocumentShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the servceUserDocumentList where note equals to UPDATED_NOTE
        defaultServceUserDocumentShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where note is not null
        defaultServceUserDocumentShouldBeFound("note.specified=true");

        // Get all the servceUserDocumentList where note is null
        defaultServceUserDocumentShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllServceUserDocumentsByNoteContainsSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where note contains DEFAULT_NOTE
        defaultServceUserDocumentShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the servceUserDocumentList where note contains UPDATED_NOTE
        defaultServceUserDocumentShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where note does not contain DEFAULT_NOTE
        defaultServceUserDocumentShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the servceUserDocumentList where note does not contain UPDATED_NOTE
        defaultServceUserDocumentShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByIssuedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where issuedDate equals to DEFAULT_ISSUED_DATE
        defaultServceUserDocumentShouldBeFound("issuedDate.equals=" + DEFAULT_ISSUED_DATE);

        // Get all the servceUserDocumentList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultServceUserDocumentShouldNotBeFound("issuedDate.equals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByIssuedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where issuedDate not equals to DEFAULT_ISSUED_DATE
        defaultServceUserDocumentShouldNotBeFound("issuedDate.notEquals=" + DEFAULT_ISSUED_DATE);

        // Get all the servceUserDocumentList where issuedDate not equals to UPDATED_ISSUED_DATE
        defaultServceUserDocumentShouldBeFound("issuedDate.notEquals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByIssuedDateIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where issuedDate in DEFAULT_ISSUED_DATE or UPDATED_ISSUED_DATE
        defaultServceUserDocumentShouldBeFound("issuedDate.in=" + DEFAULT_ISSUED_DATE + "," + UPDATED_ISSUED_DATE);

        // Get all the servceUserDocumentList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultServceUserDocumentShouldNotBeFound("issuedDate.in=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByIssuedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where issuedDate is not null
        defaultServceUserDocumentShouldBeFound("issuedDate.specified=true");

        // Get all the servceUserDocumentList where issuedDate is null
        defaultServceUserDocumentShouldNotBeFound("issuedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByIssuedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where issuedDate is greater than or equal to DEFAULT_ISSUED_DATE
        defaultServceUserDocumentShouldBeFound("issuedDate.greaterThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the servceUserDocumentList where issuedDate is greater than or equal to UPDATED_ISSUED_DATE
        defaultServceUserDocumentShouldNotBeFound("issuedDate.greaterThanOrEqual=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByIssuedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where issuedDate is less than or equal to DEFAULT_ISSUED_DATE
        defaultServceUserDocumentShouldBeFound("issuedDate.lessThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the servceUserDocumentList where issuedDate is less than or equal to SMALLER_ISSUED_DATE
        defaultServceUserDocumentShouldNotBeFound("issuedDate.lessThanOrEqual=" + SMALLER_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByIssuedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where issuedDate is less than DEFAULT_ISSUED_DATE
        defaultServceUserDocumentShouldNotBeFound("issuedDate.lessThan=" + DEFAULT_ISSUED_DATE);

        // Get all the servceUserDocumentList where issuedDate is less than UPDATED_ISSUED_DATE
        defaultServceUserDocumentShouldBeFound("issuedDate.lessThan=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByIssuedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where issuedDate is greater than DEFAULT_ISSUED_DATE
        defaultServceUserDocumentShouldNotBeFound("issuedDate.greaterThan=" + DEFAULT_ISSUED_DATE);

        // Get all the servceUserDocumentList where issuedDate is greater than SMALLER_ISSUED_DATE
        defaultServceUserDocumentShouldBeFound("issuedDate.greaterThan=" + SMALLER_ISSUED_DATE);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultServceUserDocumentShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the servceUserDocumentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultServceUserDocumentShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where expiryDate not equals to DEFAULT_EXPIRY_DATE
        defaultServceUserDocumentShouldNotBeFound("expiryDate.notEquals=" + DEFAULT_EXPIRY_DATE);

        // Get all the servceUserDocumentList where expiryDate not equals to UPDATED_EXPIRY_DATE
        defaultServceUserDocumentShouldBeFound("expiryDate.notEquals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultServceUserDocumentShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the servceUserDocumentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultServceUserDocumentShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where expiryDate is not null
        defaultServceUserDocumentShouldBeFound("expiryDate.specified=true");

        // Get all the servceUserDocumentList where expiryDate is null
        defaultServceUserDocumentShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where expiryDate is greater than or equal to DEFAULT_EXPIRY_DATE
        defaultServceUserDocumentShouldBeFound("expiryDate.greaterThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the servceUserDocumentList where expiryDate is greater than or equal to UPDATED_EXPIRY_DATE
        defaultServceUserDocumentShouldNotBeFound("expiryDate.greaterThanOrEqual=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByExpiryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where expiryDate is less than or equal to DEFAULT_EXPIRY_DATE
        defaultServceUserDocumentShouldBeFound("expiryDate.lessThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the servceUserDocumentList where expiryDate is less than or equal to SMALLER_EXPIRY_DATE
        defaultServceUserDocumentShouldNotBeFound("expiryDate.lessThanOrEqual=" + SMALLER_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where expiryDate is less than DEFAULT_EXPIRY_DATE
        defaultServceUserDocumentShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the servceUserDocumentList where expiryDate is less than UPDATED_EXPIRY_DATE
        defaultServceUserDocumentShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByExpiryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where expiryDate is greater than DEFAULT_EXPIRY_DATE
        defaultServceUserDocumentShouldNotBeFound("expiryDate.greaterThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the servceUserDocumentList where expiryDate is greater than SMALLER_EXPIRY_DATE
        defaultServceUserDocumentShouldBeFound("expiryDate.greaterThan=" + SMALLER_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByUploadedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where uploadedDate equals to DEFAULT_UPLOADED_DATE
        defaultServceUserDocumentShouldBeFound("uploadedDate.equals=" + DEFAULT_UPLOADED_DATE);

        // Get all the servceUserDocumentList where uploadedDate equals to UPDATED_UPLOADED_DATE
        defaultServceUserDocumentShouldNotBeFound("uploadedDate.equals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByUploadedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where uploadedDate not equals to DEFAULT_UPLOADED_DATE
        defaultServceUserDocumentShouldNotBeFound("uploadedDate.notEquals=" + DEFAULT_UPLOADED_DATE);

        // Get all the servceUserDocumentList where uploadedDate not equals to UPDATED_UPLOADED_DATE
        defaultServceUserDocumentShouldBeFound("uploadedDate.notEquals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByUploadedDateIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where uploadedDate in DEFAULT_UPLOADED_DATE or UPDATED_UPLOADED_DATE
        defaultServceUserDocumentShouldBeFound("uploadedDate.in=" + DEFAULT_UPLOADED_DATE + "," + UPDATED_UPLOADED_DATE);

        // Get all the servceUserDocumentList where uploadedDate equals to UPDATED_UPLOADED_DATE
        defaultServceUserDocumentShouldNotBeFound("uploadedDate.in=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByUploadedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where uploadedDate is not null
        defaultServceUserDocumentShouldBeFound("uploadedDate.specified=true");

        // Get all the servceUserDocumentList where uploadedDate is null
        defaultServceUserDocumentShouldNotBeFound("uploadedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByUploadedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where uploadedDate is greater than or equal to DEFAULT_UPLOADED_DATE
        defaultServceUserDocumentShouldBeFound("uploadedDate.greaterThanOrEqual=" + DEFAULT_UPLOADED_DATE);

        // Get all the servceUserDocumentList where uploadedDate is greater than or equal to UPDATED_UPLOADED_DATE
        defaultServceUserDocumentShouldNotBeFound("uploadedDate.greaterThanOrEqual=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByUploadedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where uploadedDate is less than or equal to DEFAULT_UPLOADED_DATE
        defaultServceUserDocumentShouldBeFound("uploadedDate.lessThanOrEqual=" + DEFAULT_UPLOADED_DATE);

        // Get all the servceUserDocumentList where uploadedDate is less than or equal to SMALLER_UPLOADED_DATE
        defaultServceUserDocumentShouldNotBeFound("uploadedDate.lessThanOrEqual=" + SMALLER_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByUploadedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where uploadedDate is less than DEFAULT_UPLOADED_DATE
        defaultServceUserDocumentShouldNotBeFound("uploadedDate.lessThan=" + DEFAULT_UPLOADED_DATE);

        // Get all the servceUserDocumentList where uploadedDate is less than UPDATED_UPLOADED_DATE
        defaultServceUserDocumentShouldBeFound("uploadedDate.lessThan=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByUploadedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where uploadedDate is greater than DEFAULT_UPLOADED_DATE
        defaultServceUserDocumentShouldNotBeFound("uploadedDate.greaterThan=" + DEFAULT_UPLOADED_DATE);

        // Get all the servceUserDocumentList where uploadedDate is greater than SMALLER_UPLOADED_DATE
        defaultServceUserDocumentShouldBeFound("uploadedDate.greaterThan=" + SMALLER_UPLOADED_DATE);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentFileUrl equals to DEFAULT_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldBeFound("documentFileUrl.equals=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the servceUserDocumentList where documentFileUrl equals to UPDATED_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldNotBeFound("documentFileUrl.equals=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentFileUrl not equals to DEFAULT_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldNotBeFound("documentFileUrl.notEquals=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the servceUserDocumentList where documentFileUrl not equals to UPDATED_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldBeFound("documentFileUrl.notEquals=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentFileUrl in DEFAULT_DOCUMENT_FILE_URL or UPDATED_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldBeFound("documentFileUrl.in=" + DEFAULT_DOCUMENT_FILE_URL + "," + UPDATED_DOCUMENT_FILE_URL);

        // Get all the servceUserDocumentList where documentFileUrl equals to UPDATED_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldNotBeFound("documentFileUrl.in=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentFileUrl is not null
        defaultServceUserDocumentShouldBeFound("documentFileUrl.specified=true");

        // Get all the servceUserDocumentList where documentFileUrl is null
        defaultServceUserDocumentShouldNotBeFound("documentFileUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentFileUrlContainsSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentFileUrl contains DEFAULT_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldBeFound("documentFileUrl.contains=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the servceUserDocumentList where documentFileUrl contains UPDATED_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldNotBeFound("documentFileUrl.contains=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByDocumentFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where documentFileUrl does not contain DEFAULT_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldNotBeFound("documentFileUrl.doesNotContain=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the servceUserDocumentList where documentFileUrl does not contain UPDATED_DOCUMENT_FILE_URL
        defaultServceUserDocumentShouldBeFound("documentFileUrl.doesNotContain=" + UPDATED_DOCUMENT_FILE_URL);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where createdDate equals to DEFAULT_CREATED_DATE
        defaultServceUserDocumentShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the servceUserDocumentList where createdDate equals to UPDATED_CREATED_DATE
        defaultServceUserDocumentShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultServceUserDocumentShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the servceUserDocumentList where createdDate not equals to UPDATED_CREATED_DATE
        defaultServceUserDocumentShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultServceUserDocumentShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the servceUserDocumentList where createdDate equals to UPDATED_CREATED_DATE
        defaultServceUserDocumentShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where createdDate is not null
        defaultServceUserDocumentShouldBeFound("createdDate.specified=true");

        // Get all the servceUserDocumentList where createdDate is null
        defaultServceUserDocumentShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultServceUserDocumentShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the servceUserDocumentList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultServceUserDocumentShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultServceUserDocumentShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the servceUserDocumentList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultServceUserDocumentShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where createdDate is less than DEFAULT_CREATED_DATE
        defaultServceUserDocumentShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the servceUserDocumentList where createdDate is less than UPDATED_CREATED_DATE
        defaultServceUserDocumentShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultServceUserDocumentShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the servceUserDocumentList where createdDate is greater than SMALLER_CREATED_DATE
        defaultServceUserDocumentShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the servceUserDocumentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the servceUserDocumentList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the servceUserDocumentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where lastUpdatedDate is not null
        defaultServceUserDocumentShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the servceUserDocumentList where lastUpdatedDate is null
        defaultServceUserDocumentShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the servceUserDocumentList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the servceUserDocumentList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the servceUserDocumentList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the servceUserDocumentList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultServceUserDocumentShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where clientId equals to DEFAULT_CLIENT_ID
        defaultServceUserDocumentShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the servceUserDocumentList where clientId equals to UPDATED_CLIENT_ID
        defaultServceUserDocumentShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where clientId not equals to DEFAULT_CLIENT_ID
        defaultServceUserDocumentShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the servceUserDocumentList where clientId not equals to UPDATED_CLIENT_ID
        defaultServceUserDocumentShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultServceUserDocumentShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the servceUserDocumentList where clientId equals to UPDATED_CLIENT_ID
        defaultServceUserDocumentShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where clientId is not null
        defaultServceUserDocumentShouldBeFound("clientId.specified=true");

        // Get all the servceUserDocumentList where clientId is null
        defaultServceUserDocumentShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultServceUserDocumentShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the servceUserDocumentList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultServceUserDocumentShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultServceUserDocumentShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the servceUserDocumentList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultServceUserDocumentShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where clientId is less than DEFAULT_CLIENT_ID
        defaultServceUserDocumentShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the servceUserDocumentList where clientId is less than UPDATED_CLIENT_ID
        defaultServceUserDocumentShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where clientId is greater than DEFAULT_CLIENT_ID
        defaultServceUserDocumentShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the servceUserDocumentList where clientId is greater than SMALLER_CLIENT_ID
        defaultServceUserDocumentShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultServceUserDocumentShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the servceUserDocumentList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultServceUserDocumentShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultServceUserDocumentShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the servceUserDocumentList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultServceUserDocumentShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultServceUserDocumentShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the servceUserDocumentList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultServceUserDocumentShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        // Get all the servceUserDocumentList where hasExtraData is not null
        defaultServceUserDocumentShouldBeFound("hasExtraData.specified=true");

        // Get all the servceUserDocumentList where hasExtraData is null
        defaultServceUserDocumentShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllServceUserDocumentsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);
        ServiceUser owner = ServiceUserResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        servceUserDocument.setOwner(owner);
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);
        Long ownerId = owner.getId();

        // Get all the servceUserDocumentList where owner equals to ownerId
        defaultServceUserDocumentShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the servceUserDocumentList where owner equals to ownerId + 1
        defaultServceUserDocumentShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllServceUserDocumentsByApprovedByIsEqualToSomething() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);
        Employee approvedBy = EmployeeResourceIT.createEntity(em);
        em.persist(approvedBy);
        em.flush();
        servceUserDocument.setApprovedBy(approvedBy);
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);
        Long approvedById = approvedBy.getId();

        // Get all the servceUserDocumentList where approvedBy equals to approvedById
        defaultServceUserDocumentShouldBeFound("approvedById.equals=" + approvedById);

        // Get all the servceUserDocumentList where approvedBy equals to approvedById + 1
        defaultServceUserDocumentShouldNotBeFound("approvedById.equals=" + (approvedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServceUserDocumentShouldBeFound(String filter) throws Exception {
        restServceUserDocumentMockMvc.perform(get("/api/servce-user-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servceUserDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(DEFAULT_ISSUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(sameInstant(DEFAULT_UPLOADED_DATE))))
            .andExpect(jsonPath("$.[*].documentFileContentType").value(hasItem(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_FILE))))
            .andExpect(jsonPath("$.[*].documentFileUrl").value(hasItem(DEFAULT_DOCUMENT_FILE_URL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restServceUserDocumentMockMvc.perform(get("/api/servce-user-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServceUserDocumentShouldNotBeFound(String filter) throws Exception {
        restServceUserDocumentMockMvc.perform(get("/api/servce-user-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServceUserDocumentMockMvc.perform(get("/api/servce-user-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingServceUserDocument() throws Exception {
        // Get the servceUserDocument
        restServceUserDocumentMockMvc.perform(get("/api/servce-user-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServceUserDocument() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        int databaseSizeBeforeUpdate = servceUserDocumentRepository.findAll().size();

        // Update the servceUserDocument
        ServceUserDocument updatedServceUserDocument = servceUserDocumentRepository.findById(servceUserDocument.getId()).get();
        // Disconnect from session so that the updates on updatedServceUserDocument are not directly saved in db
        em.detach(updatedServceUserDocument);
        updatedServceUserDocument
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .note(UPDATED_NOTE)
            .issuedDate(UPDATED_ISSUED_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .documentFile(UPDATED_DOCUMENT_FILE)
            .documentFileContentType(UPDATED_DOCUMENT_FILE_CONTENT_TYPE)
            .documentFileUrl(UPDATED_DOCUMENT_FILE_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        ServceUserDocumentDTO servceUserDocumentDTO = servceUserDocumentMapper.toDto(updatedServceUserDocument);

        restServceUserDocumentMockMvc.perform(put("/api/servce-user-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servceUserDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the ServceUserDocument in the database
        List<ServceUserDocument> servceUserDocumentList = servceUserDocumentRepository.findAll();
        assertThat(servceUserDocumentList).hasSize(databaseSizeBeforeUpdate);
        ServceUserDocument testServceUserDocument = servceUserDocumentList.get(servceUserDocumentList.size() - 1);
        assertThat(testServceUserDocument.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testServceUserDocument.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testServceUserDocument.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testServceUserDocument.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testServceUserDocument.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testServceUserDocument.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testServceUserDocument.getUploadedDate()).isEqualTo(UPDATED_UPLOADED_DATE);
        assertThat(testServceUserDocument.getDocumentFile()).isEqualTo(UPDATED_DOCUMENT_FILE);
        assertThat(testServceUserDocument.getDocumentFileContentType()).isEqualTo(UPDATED_DOCUMENT_FILE_CONTENT_TYPE);
        assertThat(testServceUserDocument.getDocumentFileUrl()).isEqualTo(UPDATED_DOCUMENT_FILE_URL);
        assertThat(testServceUserDocument.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testServceUserDocument.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testServceUserDocument.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testServceUserDocument.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingServceUserDocument() throws Exception {
        int databaseSizeBeforeUpdate = servceUserDocumentRepository.findAll().size();

        // Create the ServceUserDocument
        ServceUserDocumentDTO servceUserDocumentDTO = servceUserDocumentMapper.toDto(servceUserDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServceUserDocumentMockMvc.perform(put("/api/servce-user-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servceUserDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServceUserDocument in the database
        List<ServceUserDocument> servceUserDocumentList = servceUserDocumentRepository.findAll();
        assertThat(servceUserDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServceUserDocument() throws Exception {
        // Initialize the database
        servceUserDocumentRepository.saveAndFlush(servceUserDocument);

        int databaseSizeBeforeDelete = servceUserDocumentRepository.findAll().size();

        // Delete the servceUserDocument
        restServceUserDocumentMockMvc.perform(delete("/api/servce-user-documents/{id}", servceUserDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServceUserDocument> servceUserDocumentList = servceUserDocumentRepository.findAll();
        assertThat(servceUserDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
