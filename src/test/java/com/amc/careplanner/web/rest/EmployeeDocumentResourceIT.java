package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.EmployeeDocument;
import com.amc.careplanner.domain.DocumentType;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.EmployeeDocumentRepository;
import com.amc.careplanner.service.EmployeeDocumentService;
import com.amc.careplanner.service.dto.EmployeeDocumentDTO;
import com.amc.careplanner.service.mapper.EmployeeDocumentMapper;
import com.amc.careplanner.service.dto.EmployeeDocumentCriteria;
import com.amc.careplanner.service.EmployeeDocumentQueryService;

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
 * Integration tests for the {@link EmployeeDocumentResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeDocumentResourceIT {

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
    private EmployeeDocumentRepository employeeDocumentRepository;

    @Autowired
    private EmployeeDocumentMapper employeeDocumentMapper;

    @Autowired
    private EmployeeDocumentService employeeDocumentService;

    @Autowired
    private EmployeeDocumentQueryService employeeDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeDocumentMockMvc;

    private EmployeeDocument employeeDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDocument createEntity(EntityManager em) {
        EmployeeDocument employeeDocument = new EmployeeDocument()
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
        return employeeDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDocument createUpdatedEntity(EntityManager em) {
        EmployeeDocument employeeDocument = new EmployeeDocument()
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
        return employeeDocument;
    }

    @BeforeEach
    public void initTest() {
        employeeDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeDocument() throws Exception {
        int databaseSizeBeforeCreate = employeeDocumentRepository.findAll().size();
        // Create the EmployeeDocument
        EmployeeDocumentDTO employeeDocumentDTO = employeeDocumentMapper.toDto(employeeDocument);
        restEmployeeDocumentMockMvc.perform(post("/api/employee-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeDocument in the database
        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findAll();
        assertThat(employeeDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeDocument testEmployeeDocument = employeeDocumentList.get(employeeDocumentList.size() - 1);
        assertThat(testEmployeeDocument.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testEmployeeDocument.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testEmployeeDocument.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testEmployeeDocument.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testEmployeeDocument.getIssuedDate()).isEqualTo(DEFAULT_ISSUED_DATE);
        assertThat(testEmployeeDocument.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testEmployeeDocument.getUploadedDate()).isEqualTo(DEFAULT_UPLOADED_DATE);
        assertThat(testEmployeeDocument.getDocumentFile()).isEqualTo(DEFAULT_DOCUMENT_FILE);
        assertThat(testEmployeeDocument.getDocumentFileContentType()).isEqualTo(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE);
        assertThat(testEmployeeDocument.getDocumentFileUrl()).isEqualTo(DEFAULT_DOCUMENT_FILE_URL);
        assertThat(testEmployeeDocument.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeeDocument.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testEmployeeDocument.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testEmployeeDocument.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createEmployeeDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeDocumentRepository.findAll().size();

        // Create the EmployeeDocument with an existing ID
        employeeDocument.setId(1L);
        EmployeeDocumentDTO employeeDocumentDTO = employeeDocumentMapper.toDto(employeeDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeDocumentMockMvc.perform(post("/api/employee-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDocument in the database
        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findAll();
        assertThat(employeeDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeDocumentRepository.findAll().size();
        // set the field null
        employeeDocument.setDocumentName(null);

        // Create the EmployeeDocument, which fails.
        EmployeeDocumentDTO employeeDocumentDTO = employeeDocumentMapper.toDto(employeeDocument);


        restEmployeeDocumentMockMvc.perform(post("/api/employee-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findAll();
        assertThat(employeeDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeDocumentRepository.findAll().size();
        // set the field null
        employeeDocument.setClientId(null);

        // Create the EmployeeDocument, which fails.
        EmployeeDocumentDTO employeeDocumentDTO = employeeDocumentMapper.toDto(employeeDocument);


        restEmployeeDocumentMockMvc.perform(post("/api/employee-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findAll();
        assertThat(employeeDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocuments() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList
        restEmployeeDocumentMockMvc.perform(get("/api/employee-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDocument.getId().intValue())))
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
    public void getEmployeeDocument() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get the employeeDocument
        restEmployeeDocumentMockMvc.perform(get("/api/employee-documents/{id}", employeeDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeDocument.getId().intValue()))
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
    public void getEmployeeDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        Long id = employeeDocument.getId();

        defaultEmployeeDocumentShouldBeFound("id.equals=" + id);
        defaultEmployeeDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentName equals to DEFAULT_DOCUMENT_NAME
        defaultEmployeeDocumentShouldBeFound("documentName.equals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the employeeDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultEmployeeDocumentShouldNotBeFound("documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentName not equals to DEFAULT_DOCUMENT_NAME
        defaultEmployeeDocumentShouldNotBeFound("documentName.notEquals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the employeeDocumentList where documentName not equals to UPDATED_DOCUMENT_NAME
        defaultEmployeeDocumentShouldBeFound("documentName.notEquals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentName in DEFAULT_DOCUMENT_NAME or UPDATED_DOCUMENT_NAME
        defaultEmployeeDocumentShouldBeFound("documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME);

        // Get all the employeeDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultEmployeeDocumentShouldNotBeFound("documentName.in=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentName is not null
        defaultEmployeeDocumentShouldBeFound("documentName.specified=true");

        // Get all the employeeDocumentList where documentName is null
        defaultEmployeeDocumentShouldNotBeFound("documentName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNameContainsSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentName contains DEFAULT_DOCUMENT_NAME
        defaultEmployeeDocumentShouldBeFound("documentName.contains=" + DEFAULT_DOCUMENT_NAME);

        // Get all the employeeDocumentList where documentName contains UPDATED_DOCUMENT_NAME
        defaultEmployeeDocumentShouldNotBeFound("documentName.contains=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentName does not contain DEFAULT_DOCUMENT_NAME
        defaultEmployeeDocumentShouldNotBeFound("documentName.doesNotContain=" + DEFAULT_DOCUMENT_NAME);

        // Get all the employeeDocumentList where documentName does not contain UPDATED_DOCUMENT_NAME
        defaultEmployeeDocumentShouldBeFound("documentName.doesNotContain=" + UPDATED_DOCUMENT_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentNumber equals to DEFAULT_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldBeFound("documentNumber.equals=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the employeeDocumentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldNotBeFound("documentNumber.equals=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentNumber not equals to DEFAULT_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldNotBeFound("documentNumber.notEquals=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the employeeDocumentList where documentNumber not equals to UPDATED_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldBeFound("documentNumber.notEquals=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentNumber in DEFAULT_DOCUMENT_NUMBER or UPDATED_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldBeFound("documentNumber.in=" + DEFAULT_DOCUMENT_NUMBER + "," + UPDATED_DOCUMENT_NUMBER);

        // Get all the employeeDocumentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldNotBeFound("documentNumber.in=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentNumber is not null
        defaultEmployeeDocumentShouldBeFound("documentNumber.specified=true");

        // Get all the employeeDocumentList where documentNumber is null
        defaultEmployeeDocumentShouldNotBeFound("documentNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNumberContainsSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentNumber contains DEFAULT_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldBeFound("documentNumber.contains=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the employeeDocumentList where documentNumber contains UPDATED_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldNotBeFound("documentNumber.contains=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentNumber does not contain DEFAULT_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldNotBeFound("documentNumber.doesNotContain=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the employeeDocumentList where documentNumber does not contain UPDATED_DOCUMENT_NUMBER
        defaultEmployeeDocumentShouldBeFound("documentNumber.doesNotContain=" + UPDATED_DOCUMENT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultEmployeeDocumentShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the employeeDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultEmployeeDocumentShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultEmployeeDocumentShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the employeeDocumentList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultEmployeeDocumentShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultEmployeeDocumentShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the employeeDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultEmployeeDocumentShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentStatus is not null
        defaultEmployeeDocumentShouldBeFound("documentStatus.specified=true");

        // Get all the employeeDocumentList where documentStatus is null
        defaultEmployeeDocumentShouldNotBeFound("documentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where note equals to DEFAULT_NOTE
        defaultEmployeeDocumentShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the employeeDocumentList where note equals to UPDATED_NOTE
        defaultEmployeeDocumentShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where note not equals to DEFAULT_NOTE
        defaultEmployeeDocumentShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the employeeDocumentList where note not equals to UPDATED_NOTE
        defaultEmployeeDocumentShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultEmployeeDocumentShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the employeeDocumentList where note equals to UPDATED_NOTE
        defaultEmployeeDocumentShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where note is not null
        defaultEmployeeDocumentShouldBeFound("note.specified=true");

        // Get all the employeeDocumentList where note is null
        defaultEmployeeDocumentShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeDocumentsByNoteContainsSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where note contains DEFAULT_NOTE
        defaultEmployeeDocumentShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the employeeDocumentList where note contains UPDATED_NOTE
        defaultEmployeeDocumentShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where note does not contain DEFAULT_NOTE
        defaultEmployeeDocumentShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the employeeDocumentList where note does not contain UPDATED_NOTE
        defaultEmployeeDocumentShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByIssuedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where issuedDate equals to DEFAULT_ISSUED_DATE
        defaultEmployeeDocumentShouldBeFound("issuedDate.equals=" + DEFAULT_ISSUED_DATE);

        // Get all the employeeDocumentList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultEmployeeDocumentShouldNotBeFound("issuedDate.equals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByIssuedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where issuedDate not equals to DEFAULT_ISSUED_DATE
        defaultEmployeeDocumentShouldNotBeFound("issuedDate.notEquals=" + DEFAULT_ISSUED_DATE);

        // Get all the employeeDocumentList where issuedDate not equals to UPDATED_ISSUED_DATE
        defaultEmployeeDocumentShouldBeFound("issuedDate.notEquals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByIssuedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where issuedDate in DEFAULT_ISSUED_DATE or UPDATED_ISSUED_DATE
        defaultEmployeeDocumentShouldBeFound("issuedDate.in=" + DEFAULT_ISSUED_DATE + "," + UPDATED_ISSUED_DATE);

        // Get all the employeeDocumentList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultEmployeeDocumentShouldNotBeFound("issuedDate.in=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByIssuedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where issuedDate is not null
        defaultEmployeeDocumentShouldBeFound("issuedDate.specified=true");

        // Get all the employeeDocumentList where issuedDate is null
        defaultEmployeeDocumentShouldNotBeFound("issuedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByIssuedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where issuedDate is greater than or equal to DEFAULT_ISSUED_DATE
        defaultEmployeeDocumentShouldBeFound("issuedDate.greaterThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the employeeDocumentList where issuedDate is greater than or equal to UPDATED_ISSUED_DATE
        defaultEmployeeDocumentShouldNotBeFound("issuedDate.greaterThanOrEqual=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByIssuedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where issuedDate is less than or equal to DEFAULT_ISSUED_DATE
        defaultEmployeeDocumentShouldBeFound("issuedDate.lessThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the employeeDocumentList where issuedDate is less than or equal to SMALLER_ISSUED_DATE
        defaultEmployeeDocumentShouldNotBeFound("issuedDate.lessThanOrEqual=" + SMALLER_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByIssuedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where issuedDate is less than DEFAULT_ISSUED_DATE
        defaultEmployeeDocumentShouldNotBeFound("issuedDate.lessThan=" + DEFAULT_ISSUED_DATE);

        // Get all the employeeDocumentList where issuedDate is less than UPDATED_ISSUED_DATE
        defaultEmployeeDocumentShouldBeFound("issuedDate.lessThan=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByIssuedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where issuedDate is greater than DEFAULT_ISSUED_DATE
        defaultEmployeeDocumentShouldNotBeFound("issuedDate.greaterThan=" + DEFAULT_ISSUED_DATE);

        // Get all the employeeDocumentList where issuedDate is greater than SMALLER_ISSUED_DATE
        defaultEmployeeDocumentShouldBeFound("issuedDate.greaterThan=" + SMALLER_ISSUED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultEmployeeDocumentShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the employeeDocumentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultEmployeeDocumentShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where expiryDate not equals to DEFAULT_EXPIRY_DATE
        defaultEmployeeDocumentShouldNotBeFound("expiryDate.notEquals=" + DEFAULT_EXPIRY_DATE);

        // Get all the employeeDocumentList where expiryDate not equals to UPDATED_EXPIRY_DATE
        defaultEmployeeDocumentShouldBeFound("expiryDate.notEquals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultEmployeeDocumentShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the employeeDocumentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultEmployeeDocumentShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where expiryDate is not null
        defaultEmployeeDocumentShouldBeFound("expiryDate.specified=true");

        // Get all the employeeDocumentList where expiryDate is null
        defaultEmployeeDocumentShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where expiryDate is greater than or equal to DEFAULT_EXPIRY_DATE
        defaultEmployeeDocumentShouldBeFound("expiryDate.greaterThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the employeeDocumentList where expiryDate is greater than or equal to UPDATED_EXPIRY_DATE
        defaultEmployeeDocumentShouldNotBeFound("expiryDate.greaterThanOrEqual=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByExpiryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where expiryDate is less than or equal to DEFAULT_EXPIRY_DATE
        defaultEmployeeDocumentShouldBeFound("expiryDate.lessThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the employeeDocumentList where expiryDate is less than or equal to SMALLER_EXPIRY_DATE
        defaultEmployeeDocumentShouldNotBeFound("expiryDate.lessThanOrEqual=" + SMALLER_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where expiryDate is less than DEFAULT_EXPIRY_DATE
        defaultEmployeeDocumentShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the employeeDocumentList where expiryDate is less than UPDATED_EXPIRY_DATE
        defaultEmployeeDocumentShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByExpiryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where expiryDate is greater than DEFAULT_EXPIRY_DATE
        defaultEmployeeDocumentShouldNotBeFound("expiryDate.greaterThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the employeeDocumentList where expiryDate is greater than SMALLER_EXPIRY_DATE
        defaultEmployeeDocumentShouldBeFound("expiryDate.greaterThan=" + SMALLER_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByUploadedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where uploadedDate equals to DEFAULT_UPLOADED_DATE
        defaultEmployeeDocumentShouldBeFound("uploadedDate.equals=" + DEFAULT_UPLOADED_DATE);

        // Get all the employeeDocumentList where uploadedDate equals to UPDATED_UPLOADED_DATE
        defaultEmployeeDocumentShouldNotBeFound("uploadedDate.equals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByUploadedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where uploadedDate not equals to DEFAULT_UPLOADED_DATE
        defaultEmployeeDocumentShouldNotBeFound("uploadedDate.notEquals=" + DEFAULT_UPLOADED_DATE);

        // Get all the employeeDocumentList where uploadedDate not equals to UPDATED_UPLOADED_DATE
        defaultEmployeeDocumentShouldBeFound("uploadedDate.notEquals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByUploadedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where uploadedDate in DEFAULT_UPLOADED_DATE or UPDATED_UPLOADED_DATE
        defaultEmployeeDocumentShouldBeFound("uploadedDate.in=" + DEFAULT_UPLOADED_DATE + "," + UPDATED_UPLOADED_DATE);

        // Get all the employeeDocumentList where uploadedDate equals to UPDATED_UPLOADED_DATE
        defaultEmployeeDocumentShouldNotBeFound("uploadedDate.in=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByUploadedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where uploadedDate is not null
        defaultEmployeeDocumentShouldBeFound("uploadedDate.specified=true");

        // Get all the employeeDocumentList where uploadedDate is null
        defaultEmployeeDocumentShouldNotBeFound("uploadedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByUploadedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where uploadedDate is greater than or equal to DEFAULT_UPLOADED_DATE
        defaultEmployeeDocumentShouldBeFound("uploadedDate.greaterThanOrEqual=" + DEFAULT_UPLOADED_DATE);

        // Get all the employeeDocumentList where uploadedDate is greater than or equal to UPDATED_UPLOADED_DATE
        defaultEmployeeDocumentShouldNotBeFound("uploadedDate.greaterThanOrEqual=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByUploadedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where uploadedDate is less than or equal to DEFAULT_UPLOADED_DATE
        defaultEmployeeDocumentShouldBeFound("uploadedDate.lessThanOrEqual=" + DEFAULT_UPLOADED_DATE);

        // Get all the employeeDocumentList where uploadedDate is less than or equal to SMALLER_UPLOADED_DATE
        defaultEmployeeDocumentShouldNotBeFound("uploadedDate.lessThanOrEqual=" + SMALLER_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByUploadedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where uploadedDate is less than DEFAULT_UPLOADED_DATE
        defaultEmployeeDocumentShouldNotBeFound("uploadedDate.lessThan=" + DEFAULT_UPLOADED_DATE);

        // Get all the employeeDocumentList where uploadedDate is less than UPDATED_UPLOADED_DATE
        defaultEmployeeDocumentShouldBeFound("uploadedDate.lessThan=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByUploadedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where uploadedDate is greater than DEFAULT_UPLOADED_DATE
        defaultEmployeeDocumentShouldNotBeFound("uploadedDate.greaterThan=" + DEFAULT_UPLOADED_DATE);

        // Get all the employeeDocumentList where uploadedDate is greater than SMALLER_UPLOADED_DATE
        defaultEmployeeDocumentShouldBeFound("uploadedDate.greaterThan=" + SMALLER_UPLOADED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentFileUrl equals to DEFAULT_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldBeFound("documentFileUrl.equals=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the employeeDocumentList where documentFileUrl equals to UPDATED_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldNotBeFound("documentFileUrl.equals=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentFileUrl not equals to DEFAULT_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldNotBeFound("documentFileUrl.notEquals=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the employeeDocumentList where documentFileUrl not equals to UPDATED_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldBeFound("documentFileUrl.notEquals=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentFileUrl in DEFAULT_DOCUMENT_FILE_URL or UPDATED_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldBeFound("documentFileUrl.in=" + DEFAULT_DOCUMENT_FILE_URL + "," + UPDATED_DOCUMENT_FILE_URL);

        // Get all the employeeDocumentList where documentFileUrl equals to UPDATED_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldNotBeFound("documentFileUrl.in=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentFileUrl is not null
        defaultEmployeeDocumentShouldBeFound("documentFileUrl.specified=true");

        // Get all the employeeDocumentList where documentFileUrl is null
        defaultEmployeeDocumentShouldNotBeFound("documentFileUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentFileUrlContainsSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentFileUrl contains DEFAULT_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldBeFound("documentFileUrl.contains=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the employeeDocumentList where documentFileUrl contains UPDATED_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldNotBeFound("documentFileUrl.contains=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where documentFileUrl does not contain DEFAULT_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldNotBeFound("documentFileUrl.doesNotContain=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the employeeDocumentList where documentFileUrl does not contain UPDATED_DOCUMENT_FILE_URL
        defaultEmployeeDocumentShouldBeFound("documentFileUrl.doesNotContain=" + UPDATED_DOCUMENT_FILE_URL);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where createdDate equals to DEFAULT_CREATED_DATE
        defaultEmployeeDocumentShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the employeeDocumentList where createdDate equals to UPDATED_CREATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the employeeDocumentList where createdDate not equals to UPDATED_CREATED_DATE
        defaultEmployeeDocumentShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultEmployeeDocumentShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the employeeDocumentList where createdDate equals to UPDATED_CREATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where createdDate is not null
        defaultEmployeeDocumentShouldBeFound("createdDate.specified=true");

        // Get all the employeeDocumentList where createdDate is null
        defaultEmployeeDocumentShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultEmployeeDocumentShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the employeeDocumentList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultEmployeeDocumentShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the employeeDocumentList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where createdDate is less than DEFAULT_CREATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the employeeDocumentList where createdDate is less than UPDATED_CREATED_DATE
        defaultEmployeeDocumentShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the employeeDocumentList where createdDate is greater than SMALLER_CREATED_DATE
        defaultEmployeeDocumentShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeDocumentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeDocumentList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the employeeDocumentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where lastUpdatedDate is not null
        defaultEmployeeDocumentShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the employeeDocumentList where lastUpdatedDate is null
        defaultEmployeeDocumentShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeDocumentList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeDocumentList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeDocumentList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the employeeDocumentList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultEmployeeDocumentShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where clientId equals to DEFAULT_CLIENT_ID
        defaultEmployeeDocumentShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDocumentList where clientId equals to UPDATED_CLIENT_ID
        defaultEmployeeDocumentShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where clientId not equals to DEFAULT_CLIENT_ID
        defaultEmployeeDocumentShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDocumentList where clientId not equals to UPDATED_CLIENT_ID
        defaultEmployeeDocumentShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultEmployeeDocumentShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the employeeDocumentList where clientId equals to UPDATED_CLIENT_ID
        defaultEmployeeDocumentShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where clientId is not null
        defaultEmployeeDocumentShouldBeFound("clientId.specified=true");

        // Get all the employeeDocumentList where clientId is null
        defaultEmployeeDocumentShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultEmployeeDocumentShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDocumentList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultEmployeeDocumentShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultEmployeeDocumentShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDocumentList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultEmployeeDocumentShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where clientId is less than DEFAULT_CLIENT_ID
        defaultEmployeeDocumentShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDocumentList where clientId is less than UPDATED_CLIENT_ID
        defaultEmployeeDocumentShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where clientId is greater than DEFAULT_CLIENT_ID
        defaultEmployeeDocumentShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the employeeDocumentList where clientId is greater than SMALLER_CLIENT_ID
        defaultEmployeeDocumentShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultEmployeeDocumentShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the employeeDocumentList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeDocumentShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultEmployeeDocumentShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the employeeDocumentList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeDocumentShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultEmployeeDocumentShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the employeeDocumentList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEmployeeDocumentShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        // Get all the employeeDocumentList where hasExtraData is not null
        defaultEmployeeDocumentShouldBeFound("hasExtraData.specified=true");

        // Get all the employeeDocumentList where hasExtraData is null
        defaultEmployeeDocumentShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeDocumentsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);
        DocumentType documentType = DocumentTypeResourceIT.createEntity(em);
        em.persist(documentType);
        em.flush();
        employeeDocument.setDocumentType(documentType);
        employeeDocumentRepository.saveAndFlush(employeeDocument);
        Long documentTypeId = documentType.getId();

        // Get all the employeeDocumentList where documentType equals to documentTypeId
        defaultEmployeeDocumentShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the employeeDocumentList where documentType equals to documentTypeId + 1
        defaultEmployeeDocumentShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeDocument.setEmployee(employee);
        employeeDocumentRepository.saveAndFlush(employeeDocument);
        Long employeeId = employee.getId();

        // Get all the employeeDocumentList where employee equals to employeeId
        defaultEmployeeDocumentShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeDocumentList where employee equals to employeeId + 1
        defaultEmployeeDocumentShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeeDocumentsByApprovedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);
        Employee approvedBy = EmployeeResourceIT.createEntity(em);
        em.persist(approvedBy);
        em.flush();
        employeeDocument.setApprovedBy(approvedBy);
        employeeDocumentRepository.saveAndFlush(employeeDocument);
        Long approvedById = approvedBy.getId();

        // Get all the employeeDocumentList where approvedBy equals to approvedById
        defaultEmployeeDocumentShouldBeFound("approvedById.equals=" + approvedById);

        // Get all the employeeDocumentList where approvedBy equals to approvedById + 1
        defaultEmployeeDocumentShouldNotBeFound("approvedById.equals=" + (approvedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeDocumentShouldBeFound(String filter) throws Exception {
        restEmployeeDocumentMockMvc.perform(get("/api/employee-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDocument.getId().intValue())))
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
        restEmployeeDocumentMockMvc.perform(get("/api/employee-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeDocumentShouldNotBeFound(String filter) throws Exception {
        restEmployeeDocumentMockMvc.perform(get("/api/employee-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeDocumentMockMvc.perform(get("/api/employee-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeDocument() throws Exception {
        // Get the employeeDocument
        restEmployeeDocumentMockMvc.perform(get("/api/employee-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeDocument() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        int databaseSizeBeforeUpdate = employeeDocumentRepository.findAll().size();

        // Update the employeeDocument
        EmployeeDocument updatedEmployeeDocument = employeeDocumentRepository.findById(employeeDocument.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeDocument are not directly saved in db
        em.detach(updatedEmployeeDocument);
        updatedEmployeeDocument
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
        EmployeeDocumentDTO employeeDocumentDTO = employeeDocumentMapper.toDto(updatedEmployeeDocument);

        restEmployeeDocumentMockMvc.perform(put("/api/employee-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeDocument in the database
        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findAll();
        assertThat(employeeDocumentList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDocument testEmployeeDocument = employeeDocumentList.get(employeeDocumentList.size() - 1);
        assertThat(testEmployeeDocument.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testEmployeeDocument.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testEmployeeDocument.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testEmployeeDocument.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testEmployeeDocument.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testEmployeeDocument.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testEmployeeDocument.getUploadedDate()).isEqualTo(UPDATED_UPLOADED_DATE);
        assertThat(testEmployeeDocument.getDocumentFile()).isEqualTo(UPDATED_DOCUMENT_FILE);
        assertThat(testEmployeeDocument.getDocumentFileContentType()).isEqualTo(UPDATED_DOCUMENT_FILE_CONTENT_TYPE);
        assertThat(testEmployeeDocument.getDocumentFileUrl()).isEqualTo(UPDATED_DOCUMENT_FILE_URL);
        assertThat(testEmployeeDocument.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeeDocument.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testEmployeeDocument.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testEmployeeDocument.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeDocument() throws Exception {
        int databaseSizeBeforeUpdate = employeeDocumentRepository.findAll().size();

        // Create the EmployeeDocument
        EmployeeDocumentDTO employeeDocumentDTO = employeeDocumentMapper.toDto(employeeDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDocumentMockMvc.perform(put("/api/employee-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDocument in the database
        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findAll();
        assertThat(employeeDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeDocument() throws Exception {
        // Initialize the database
        employeeDocumentRepository.saveAndFlush(employeeDocument);

        int databaseSizeBeforeDelete = employeeDocumentRepository.findAll().size();

        // Delete the employeeDocument
        restEmployeeDocumentMockMvc.perform(delete("/api/employee-documents/{id}", employeeDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findAll();
        assertThat(employeeDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
