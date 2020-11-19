package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.TenantDocument;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.TenantDocumentRepository;
import com.amc.careplanner.service.TenantDocumentService;
import com.amc.careplanner.service.dto.TenantDocumentDTO;
import com.amc.careplanner.service.mapper.TenantDocumentMapper;
import com.amc.careplanner.service.dto.TenantDocumentCriteria;
import com.amc.careplanner.service.TenantDocumentQueryService;

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

import com.amc.careplanner.domain.enumeration.TenantDocumentType;
import com.amc.careplanner.domain.enumeration.DocumentStatus;
/**
 * Integration tests for the {@link TenantDocumentResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TenantDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER = "BBBBBBBBBB";

    private static final TenantDocumentType DEFAULT_DOCUMENT_TYPE = TenantDocumentType.POLICY;
    private static final TenantDocumentType UPDATED_DOCUMENT_TYPE = TenantDocumentType.PROCEDURE;

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

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private TenantDocumentRepository tenantDocumentRepository;

    @Autowired
    private TenantDocumentMapper tenantDocumentMapper;

    @Autowired
    private TenantDocumentService tenantDocumentService;

    @Autowired
    private TenantDocumentQueryService tenantDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTenantDocumentMockMvc;

    private TenantDocument tenantDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TenantDocument createEntity(EntityManager em) {
        TenantDocument tenantDocument = new TenantDocument()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .documentCode(DEFAULT_DOCUMENT_CODE)
            .documentNumber(DEFAULT_DOCUMENT_NUMBER)
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .note(DEFAULT_NOTE)
            .issuedDate(DEFAULT_ISSUED_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .uploadedDate(DEFAULT_UPLOADED_DATE)
            .documentFile(DEFAULT_DOCUMENT_FILE)
            .documentFileContentType(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE)
            .documentFileUrl(DEFAULT_DOCUMENT_FILE_URL)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return tenantDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TenantDocument createUpdatedEntity(EntityManager em) {
        TenantDocument tenantDocument = new TenantDocument()
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentCode(UPDATED_DOCUMENT_CODE)
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .note(UPDATED_NOTE)
            .issuedDate(UPDATED_ISSUED_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .documentFile(UPDATED_DOCUMENT_FILE)
            .documentFileContentType(UPDATED_DOCUMENT_FILE_CONTENT_TYPE)
            .documentFileUrl(UPDATED_DOCUMENT_FILE_URL)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return tenantDocument;
    }

    @BeforeEach
    public void initTest() {
        tenantDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createTenantDocument() throws Exception {
        int databaseSizeBeforeCreate = tenantDocumentRepository.findAll().size();
        // Create the TenantDocument
        TenantDocumentDTO tenantDocumentDTO = tenantDocumentMapper.toDto(tenantDocument);
        restTenantDocumentMockMvc.perform(post("/api/tenant-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantDocument in the database
        List<TenantDocument> tenantDocumentList = tenantDocumentRepository.findAll();
        assertThat(tenantDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        TenantDocument testTenantDocument = tenantDocumentList.get(tenantDocumentList.size() - 1);
        assertThat(testTenantDocument.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testTenantDocument.getDocumentCode()).isEqualTo(DEFAULT_DOCUMENT_CODE);
        assertThat(testTenantDocument.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testTenantDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testTenantDocument.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testTenantDocument.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTenantDocument.getIssuedDate()).isEqualTo(DEFAULT_ISSUED_DATE);
        assertThat(testTenantDocument.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testTenantDocument.getUploadedDate()).isEqualTo(DEFAULT_UPLOADED_DATE);
        assertThat(testTenantDocument.getDocumentFile()).isEqualTo(DEFAULT_DOCUMENT_FILE);
        assertThat(testTenantDocument.getDocumentFileContentType()).isEqualTo(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE);
        assertThat(testTenantDocument.getDocumentFileUrl()).isEqualTo(DEFAULT_DOCUMENT_FILE_URL);
        assertThat(testTenantDocument.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testTenantDocument.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createTenantDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tenantDocumentRepository.findAll().size();

        // Create the TenantDocument with an existing ID
        tenantDocument.setId(1L);
        TenantDocumentDTO tenantDocumentDTO = tenantDocumentMapper.toDto(tenantDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantDocumentMockMvc.perform(post("/api/tenant-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TenantDocument in the database
        List<TenantDocument> tenantDocumentList = tenantDocumentRepository.findAll();
        assertThat(tenantDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantDocumentRepository.findAll().size();
        // set the field null
        tenantDocument.setDocumentName(null);

        // Create the TenantDocument, which fails.
        TenantDocumentDTO tenantDocumentDTO = tenantDocumentMapper.toDto(tenantDocument);


        restTenantDocumentMockMvc.perform(post("/api/tenant-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<TenantDocument> tenantDocumentList = tenantDocumentRepository.findAll();
        assertThat(tenantDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantDocumentRepository.findAll().size();
        // set the field null
        tenantDocument.setDocumentCode(null);

        // Create the TenantDocument, which fails.
        TenantDocumentDTO tenantDocumentDTO = tenantDocumentMapper.toDto(tenantDocument);


        restTenantDocumentMockMvc.perform(post("/api/tenant-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<TenantDocument> tenantDocumentList = tenantDocumentRepository.findAll();
        assertThat(tenantDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantDocumentRepository.findAll().size();
        // set the field null
        tenantDocument.setDocumentType(null);

        // Create the TenantDocument, which fails.
        TenantDocumentDTO tenantDocumentDTO = tenantDocumentMapper.toDto(tenantDocument);


        restTenantDocumentMockMvc.perform(post("/api/tenant-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<TenantDocument> tenantDocumentList = tenantDocumentRepository.findAll();
        assertThat(tenantDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantDocumentRepository.findAll().size();
        // set the field null
        tenantDocument.setTenantId(null);

        // Create the TenantDocument, which fails.
        TenantDocumentDTO tenantDocumentDTO = tenantDocumentMapper.toDto(tenantDocument);


        restTenantDocumentMockMvc.perform(post("/api/tenant-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<TenantDocument> tenantDocumentList = tenantDocumentRepository.findAll();
        assertThat(tenantDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTenantDocuments() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList
        restTenantDocumentMockMvc.perform(get("/api/tenant-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenantDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].documentCode").value(hasItem(DEFAULT_DOCUMENT_CODE)))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(DEFAULT_ISSUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(sameInstant(DEFAULT_UPLOADED_DATE))))
            .andExpect(jsonPath("$.[*].documentFileContentType").value(hasItem(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_FILE))))
            .andExpect(jsonPath("$.[*].documentFileUrl").value(hasItem(DEFAULT_DOCUMENT_FILE_URL)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTenantDocument() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get the tenantDocument
        restTenantDocumentMockMvc.perform(get("/api/tenant-documents/{id}", tenantDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tenantDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME))
            .andExpect(jsonPath("$.documentCode").value(DEFAULT_DOCUMENT_CODE))
            .andExpect(jsonPath("$.documentNumber").value(DEFAULT_DOCUMENT_NUMBER))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.issuedDate").value(DEFAULT_ISSUED_DATE.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.uploadedDate").value(sameInstant(DEFAULT_UPLOADED_DATE)))
            .andExpect(jsonPath("$.documentFileContentType").value(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentFile").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_FILE)))
            .andExpect(jsonPath("$.documentFileUrl").value(DEFAULT_DOCUMENT_FILE_URL))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getTenantDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        Long id = tenantDocument.getId();

        defaultTenantDocumentShouldBeFound("id.equals=" + id);
        defaultTenantDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultTenantDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTenantDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultTenantDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTenantDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentName equals to DEFAULT_DOCUMENT_NAME
        defaultTenantDocumentShouldBeFound("documentName.equals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the tenantDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultTenantDocumentShouldNotBeFound("documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentName not equals to DEFAULT_DOCUMENT_NAME
        defaultTenantDocumentShouldNotBeFound("documentName.notEquals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the tenantDocumentList where documentName not equals to UPDATED_DOCUMENT_NAME
        defaultTenantDocumentShouldBeFound("documentName.notEquals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentName in DEFAULT_DOCUMENT_NAME or UPDATED_DOCUMENT_NAME
        defaultTenantDocumentShouldBeFound("documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME);

        // Get all the tenantDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultTenantDocumentShouldNotBeFound("documentName.in=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentName is not null
        defaultTenantDocumentShouldBeFound("documentName.specified=true");

        // Get all the tenantDocumentList where documentName is null
        defaultTenantDocumentShouldNotBeFound("documentName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNameContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentName contains DEFAULT_DOCUMENT_NAME
        defaultTenantDocumentShouldBeFound("documentName.contains=" + DEFAULT_DOCUMENT_NAME);

        // Get all the tenantDocumentList where documentName contains UPDATED_DOCUMENT_NAME
        defaultTenantDocumentShouldNotBeFound("documentName.contains=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNameNotContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentName does not contain DEFAULT_DOCUMENT_NAME
        defaultTenantDocumentShouldNotBeFound("documentName.doesNotContain=" + DEFAULT_DOCUMENT_NAME);

        // Get all the tenantDocumentList where documentName does not contain UPDATED_DOCUMENT_NAME
        defaultTenantDocumentShouldBeFound("documentName.doesNotContain=" + UPDATED_DOCUMENT_NAME);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentCode equals to DEFAULT_DOCUMENT_CODE
        defaultTenantDocumentShouldBeFound("documentCode.equals=" + DEFAULT_DOCUMENT_CODE);

        // Get all the tenantDocumentList where documentCode equals to UPDATED_DOCUMENT_CODE
        defaultTenantDocumentShouldNotBeFound("documentCode.equals=" + UPDATED_DOCUMENT_CODE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentCode not equals to DEFAULT_DOCUMENT_CODE
        defaultTenantDocumentShouldNotBeFound("documentCode.notEquals=" + DEFAULT_DOCUMENT_CODE);

        // Get all the tenantDocumentList where documentCode not equals to UPDATED_DOCUMENT_CODE
        defaultTenantDocumentShouldBeFound("documentCode.notEquals=" + UPDATED_DOCUMENT_CODE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentCodeIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentCode in DEFAULT_DOCUMENT_CODE or UPDATED_DOCUMENT_CODE
        defaultTenantDocumentShouldBeFound("documentCode.in=" + DEFAULT_DOCUMENT_CODE + "," + UPDATED_DOCUMENT_CODE);

        // Get all the tenantDocumentList where documentCode equals to UPDATED_DOCUMENT_CODE
        defaultTenantDocumentShouldNotBeFound("documentCode.in=" + UPDATED_DOCUMENT_CODE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentCode is not null
        defaultTenantDocumentShouldBeFound("documentCode.specified=true");

        // Get all the tenantDocumentList where documentCode is null
        defaultTenantDocumentShouldNotBeFound("documentCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentCodeContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentCode contains DEFAULT_DOCUMENT_CODE
        defaultTenantDocumentShouldBeFound("documentCode.contains=" + DEFAULT_DOCUMENT_CODE);

        // Get all the tenantDocumentList where documentCode contains UPDATED_DOCUMENT_CODE
        defaultTenantDocumentShouldNotBeFound("documentCode.contains=" + UPDATED_DOCUMENT_CODE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentCodeNotContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentCode does not contain DEFAULT_DOCUMENT_CODE
        defaultTenantDocumentShouldNotBeFound("documentCode.doesNotContain=" + DEFAULT_DOCUMENT_CODE);

        // Get all the tenantDocumentList where documentCode does not contain UPDATED_DOCUMENT_CODE
        defaultTenantDocumentShouldBeFound("documentCode.doesNotContain=" + UPDATED_DOCUMENT_CODE);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentNumber equals to DEFAULT_DOCUMENT_NUMBER
        defaultTenantDocumentShouldBeFound("documentNumber.equals=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the tenantDocumentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultTenantDocumentShouldNotBeFound("documentNumber.equals=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentNumber not equals to DEFAULT_DOCUMENT_NUMBER
        defaultTenantDocumentShouldNotBeFound("documentNumber.notEquals=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the tenantDocumentList where documentNumber not equals to UPDATED_DOCUMENT_NUMBER
        defaultTenantDocumentShouldBeFound("documentNumber.notEquals=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNumberIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentNumber in DEFAULT_DOCUMENT_NUMBER or UPDATED_DOCUMENT_NUMBER
        defaultTenantDocumentShouldBeFound("documentNumber.in=" + DEFAULT_DOCUMENT_NUMBER + "," + UPDATED_DOCUMENT_NUMBER);

        // Get all the tenantDocumentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultTenantDocumentShouldNotBeFound("documentNumber.in=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentNumber is not null
        defaultTenantDocumentShouldBeFound("documentNumber.specified=true");

        // Get all the tenantDocumentList where documentNumber is null
        defaultTenantDocumentShouldNotBeFound("documentNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNumberContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentNumber contains DEFAULT_DOCUMENT_NUMBER
        defaultTenantDocumentShouldBeFound("documentNumber.contains=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the tenantDocumentList where documentNumber contains UPDATED_DOCUMENT_NUMBER
        defaultTenantDocumentShouldNotBeFound("documentNumber.contains=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentNumberNotContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentNumber does not contain DEFAULT_DOCUMENT_NUMBER
        defaultTenantDocumentShouldNotBeFound("documentNumber.doesNotContain=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the tenantDocumentList where documentNumber does not contain UPDATED_DOCUMENT_NUMBER
        defaultTenantDocumentShouldBeFound("documentNumber.doesNotContain=" + UPDATED_DOCUMENT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentType equals to DEFAULT_DOCUMENT_TYPE
        defaultTenantDocumentShouldBeFound("documentType.equals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the tenantDocumentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultTenantDocumentShouldNotBeFound("documentType.equals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentType not equals to DEFAULT_DOCUMENT_TYPE
        defaultTenantDocumentShouldNotBeFound("documentType.notEquals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the tenantDocumentList where documentType not equals to UPDATED_DOCUMENT_TYPE
        defaultTenantDocumentShouldBeFound("documentType.notEquals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentType in DEFAULT_DOCUMENT_TYPE or UPDATED_DOCUMENT_TYPE
        defaultTenantDocumentShouldBeFound("documentType.in=" + DEFAULT_DOCUMENT_TYPE + "," + UPDATED_DOCUMENT_TYPE);

        // Get all the tenantDocumentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultTenantDocumentShouldNotBeFound("documentType.in=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentType is not null
        defaultTenantDocumentShouldBeFound("documentType.specified=true");

        // Get all the tenantDocumentList where documentType is null
        defaultTenantDocumentShouldNotBeFound("documentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultTenantDocumentShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the tenantDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultTenantDocumentShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultTenantDocumentShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the tenantDocumentList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultTenantDocumentShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultTenantDocumentShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the tenantDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultTenantDocumentShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentStatus is not null
        defaultTenantDocumentShouldBeFound("documentStatus.specified=true");

        // Get all the tenantDocumentList where documentStatus is null
        defaultTenantDocumentShouldNotBeFound("documentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where note equals to DEFAULT_NOTE
        defaultTenantDocumentShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the tenantDocumentList where note equals to UPDATED_NOTE
        defaultTenantDocumentShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where note not equals to DEFAULT_NOTE
        defaultTenantDocumentShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the tenantDocumentList where note not equals to UPDATED_NOTE
        defaultTenantDocumentShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTenantDocumentShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the tenantDocumentList where note equals to UPDATED_NOTE
        defaultTenantDocumentShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where note is not null
        defaultTenantDocumentShouldBeFound("note.specified=true");

        // Get all the tenantDocumentList where note is null
        defaultTenantDocumentShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantDocumentsByNoteContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where note contains DEFAULT_NOTE
        defaultTenantDocumentShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the tenantDocumentList where note contains UPDATED_NOTE
        defaultTenantDocumentShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where note does not contain DEFAULT_NOTE
        defaultTenantDocumentShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the tenantDocumentList where note does not contain UPDATED_NOTE
        defaultTenantDocumentShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByIssuedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where issuedDate equals to DEFAULT_ISSUED_DATE
        defaultTenantDocumentShouldBeFound("issuedDate.equals=" + DEFAULT_ISSUED_DATE);

        // Get all the tenantDocumentList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultTenantDocumentShouldNotBeFound("issuedDate.equals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByIssuedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where issuedDate not equals to DEFAULT_ISSUED_DATE
        defaultTenantDocumentShouldNotBeFound("issuedDate.notEquals=" + DEFAULT_ISSUED_DATE);

        // Get all the tenantDocumentList where issuedDate not equals to UPDATED_ISSUED_DATE
        defaultTenantDocumentShouldBeFound("issuedDate.notEquals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByIssuedDateIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where issuedDate in DEFAULT_ISSUED_DATE or UPDATED_ISSUED_DATE
        defaultTenantDocumentShouldBeFound("issuedDate.in=" + DEFAULT_ISSUED_DATE + "," + UPDATED_ISSUED_DATE);

        // Get all the tenantDocumentList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultTenantDocumentShouldNotBeFound("issuedDate.in=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByIssuedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where issuedDate is not null
        defaultTenantDocumentShouldBeFound("issuedDate.specified=true");

        // Get all the tenantDocumentList where issuedDate is null
        defaultTenantDocumentShouldNotBeFound("issuedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByIssuedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where issuedDate is greater than or equal to DEFAULT_ISSUED_DATE
        defaultTenantDocumentShouldBeFound("issuedDate.greaterThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the tenantDocumentList where issuedDate is greater than or equal to UPDATED_ISSUED_DATE
        defaultTenantDocumentShouldNotBeFound("issuedDate.greaterThanOrEqual=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByIssuedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where issuedDate is less than or equal to DEFAULT_ISSUED_DATE
        defaultTenantDocumentShouldBeFound("issuedDate.lessThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the tenantDocumentList where issuedDate is less than or equal to SMALLER_ISSUED_DATE
        defaultTenantDocumentShouldNotBeFound("issuedDate.lessThanOrEqual=" + SMALLER_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByIssuedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where issuedDate is less than DEFAULT_ISSUED_DATE
        defaultTenantDocumentShouldNotBeFound("issuedDate.lessThan=" + DEFAULT_ISSUED_DATE);

        // Get all the tenantDocumentList where issuedDate is less than UPDATED_ISSUED_DATE
        defaultTenantDocumentShouldBeFound("issuedDate.lessThan=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByIssuedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where issuedDate is greater than DEFAULT_ISSUED_DATE
        defaultTenantDocumentShouldNotBeFound("issuedDate.greaterThan=" + DEFAULT_ISSUED_DATE);

        // Get all the tenantDocumentList where issuedDate is greater than SMALLER_ISSUED_DATE
        defaultTenantDocumentShouldBeFound("issuedDate.greaterThan=" + SMALLER_ISSUED_DATE);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultTenantDocumentShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the tenantDocumentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultTenantDocumentShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where expiryDate not equals to DEFAULT_EXPIRY_DATE
        defaultTenantDocumentShouldNotBeFound("expiryDate.notEquals=" + DEFAULT_EXPIRY_DATE);

        // Get all the tenantDocumentList where expiryDate not equals to UPDATED_EXPIRY_DATE
        defaultTenantDocumentShouldBeFound("expiryDate.notEquals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultTenantDocumentShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the tenantDocumentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultTenantDocumentShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where expiryDate is not null
        defaultTenantDocumentShouldBeFound("expiryDate.specified=true");

        // Get all the tenantDocumentList where expiryDate is null
        defaultTenantDocumentShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where expiryDate is greater than or equal to DEFAULT_EXPIRY_DATE
        defaultTenantDocumentShouldBeFound("expiryDate.greaterThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the tenantDocumentList where expiryDate is greater than or equal to UPDATED_EXPIRY_DATE
        defaultTenantDocumentShouldNotBeFound("expiryDate.greaterThanOrEqual=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByExpiryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where expiryDate is less than or equal to DEFAULT_EXPIRY_DATE
        defaultTenantDocumentShouldBeFound("expiryDate.lessThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the tenantDocumentList where expiryDate is less than or equal to SMALLER_EXPIRY_DATE
        defaultTenantDocumentShouldNotBeFound("expiryDate.lessThanOrEqual=" + SMALLER_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where expiryDate is less than DEFAULT_EXPIRY_DATE
        defaultTenantDocumentShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the tenantDocumentList where expiryDate is less than UPDATED_EXPIRY_DATE
        defaultTenantDocumentShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByExpiryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where expiryDate is greater than DEFAULT_EXPIRY_DATE
        defaultTenantDocumentShouldNotBeFound("expiryDate.greaterThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the tenantDocumentList where expiryDate is greater than SMALLER_EXPIRY_DATE
        defaultTenantDocumentShouldBeFound("expiryDate.greaterThan=" + SMALLER_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByUploadedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where uploadedDate equals to DEFAULT_UPLOADED_DATE
        defaultTenantDocumentShouldBeFound("uploadedDate.equals=" + DEFAULT_UPLOADED_DATE);

        // Get all the tenantDocumentList where uploadedDate equals to UPDATED_UPLOADED_DATE
        defaultTenantDocumentShouldNotBeFound("uploadedDate.equals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByUploadedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where uploadedDate not equals to DEFAULT_UPLOADED_DATE
        defaultTenantDocumentShouldNotBeFound("uploadedDate.notEquals=" + DEFAULT_UPLOADED_DATE);

        // Get all the tenantDocumentList where uploadedDate not equals to UPDATED_UPLOADED_DATE
        defaultTenantDocumentShouldBeFound("uploadedDate.notEquals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByUploadedDateIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where uploadedDate in DEFAULT_UPLOADED_DATE or UPDATED_UPLOADED_DATE
        defaultTenantDocumentShouldBeFound("uploadedDate.in=" + DEFAULT_UPLOADED_DATE + "," + UPDATED_UPLOADED_DATE);

        // Get all the tenantDocumentList where uploadedDate equals to UPDATED_UPLOADED_DATE
        defaultTenantDocumentShouldNotBeFound("uploadedDate.in=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByUploadedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where uploadedDate is not null
        defaultTenantDocumentShouldBeFound("uploadedDate.specified=true");

        // Get all the tenantDocumentList where uploadedDate is null
        defaultTenantDocumentShouldNotBeFound("uploadedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByUploadedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where uploadedDate is greater than or equal to DEFAULT_UPLOADED_DATE
        defaultTenantDocumentShouldBeFound("uploadedDate.greaterThanOrEqual=" + DEFAULT_UPLOADED_DATE);

        // Get all the tenantDocumentList where uploadedDate is greater than or equal to UPDATED_UPLOADED_DATE
        defaultTenantDocumentShouldNotBeFound("uploadedDate.greaterThanOrEqual=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByUploadedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where uploadedDate is less than or equal to DEFAULT_UPLOADED_DATE
        defaultTenantDocumentShouldBeFound("uploadedDate.lessThanOrEqual=" + DEFAULT_UPLOADED_DATE);

        // Get all the tenantDocumentList where uploadedDate is less than or equal to SMALLER_UPLOADED_DATE
        defaultTenantDocumentShouldNotBeFound("uploadedDate.lessThanOrEqual=" + SMALLER_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByUploadedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where uploadedDate is less than DEFAULT_UPLOADED_DATE
        defaultTenantDocumentShouldNotBeFound("uploadedDate.lessThan=" + DEFAULT_UPLOADED_DATE);

        // Get all the tenantDocumentList where uploadedDate is less than UPDATED_UPLOADED_DATE
        defaultTenantDocumentShouldBeFound("uploadedDate.lessThan=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByUploadedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where uploadedDate is greater than DEFAULT_UPLOADED_DATE
        defaultTenantDocumentShouldNotBeFound("uploadedDate.greaterThan=" + DEFAULT_UPLOADED_DATE);

        // Get all the tenantDocumentList where uploadedDate is greater than SMALLER_UPLOADED_DATE
        defaultTenantDocumentShouldBeFound("uploadedDate.greaterThan=" + SMALLER_UPLOADED_DATE);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentFileUrl equals to DEFAULT_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldBeFound("documentFileUrl.equals=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the tenantDocumentList where documentFileUrl equals to UPDATED_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldNotBeFound("documentFileUrl.equals=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentFileUrl not equals to DEFAULT_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldNotBeFound("documentFileUrl.notEquals=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the tenantDocumentList where documentFileUrl not equals to UPDATED_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldBeFound("documentFileUrl.notEquals=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentFileUrl in DEFAULT_DOCUMENT_FILE_URL or UPDATED_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldBeFound("documentFileUrl.in=" + DEFAULT_DOCUMENT_FILE_URL + "," + UPDATED_DOCUMENT_FILE_URL);

        // Get all the tenantDocumentList where documentFileUrl equals to UPDATED_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldNotBeFound("documentFileUrl.in=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentFileUrl is not null
        defaultTenantDocumentShouldBeFound("documentFileUrl.specified=true");

        // Get all the tenantDocumentList where documentFileUrl is null
        defaultTenantDocumentShouldNotBeFound("documentFileUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentFileUrlContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentFileUrl contains DEFAULT_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldBeFound("documentFileUrl.contains=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the tenantDocumentList where documentFileUrl contains UPDATED_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldNotBeFound("documentFileUrl.contains=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByDocumentFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where documentFileUrl does not contain DEFAULT_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldNotBeFound("documentFileUrl.doesNotContain=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the tenantDocumentList where documentFileUrl does not contain UPDATED_DOCUMENT_FILE_URL
        defaultTenantDocumentShouldBeFound("documentFileUrl.doesNotContain=" + UPDATED_DOCUMENT_FILE_URL);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultTenantDocumentShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantDocumentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTenantDocumentShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultTenantDocumentShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantDocumentList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultTenantDocumentShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultTenantDocumentShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the tenantDocumentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultTenantDocumentShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where lastUpdatedDate is not null
        defaultTenantDocumentShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the tenantDocumentList where lastUpdatedDate is null
        defaultTenantDocumentShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTenantDocumentShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantDocumentList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultTenantDocumentShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultTenantDocumentShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantDocumentList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultTenantDocumentShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultTenantDocumentShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantDocumentList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultTenantDocumentShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultTenantDocumentShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the tenantDocumentList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultTenantDocumentShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where tenantId equals to DEFAULT_TENANT_ID
        defaultTenantDocumentShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the tenantDocumentList where tenantId equals to UPDATED_TENANT_ID
        defaultTenantDocumentShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where tenantId not equals to DEFAULT_TENANT_ID
        defaultTenantDocumentShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the tenantDocumentList where tenantId not equals to UPDATED_TENANT_ID
        defaultTenantDocumentShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultTenantDocumentShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the tenantDocumentList where tenantId equals to UPDATED_TENANT_ID
        defaultTenantDocumentShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where tenantId is not null
        defaultTenantDocumentShouldBeFound("tenantId.specified=true");

        // Get all the tenantDocumentList where tenantId is null
        defaultTenantDocumentShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultTenantDocumentShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the tenantDocumentList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultTenantDocumentShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultTenantDocumentShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the tenantDocumentList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultTenantDocumentShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where tenantId is less than DEFAULT_TENANT_ID
        defaultTenantDocumentShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the tenantDocumentList where tenantId is less than UPDATED_TENANT_ID
        defaultTenantDocumentShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTenantDocumentsByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        // Get all the tenantDocumentList where tenantId is greater than DEFAULT_TENANT_ID
        defaultTenantDocumentShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the tenantDocumentList where tenantId is greater than SMALLER_TENANT_ID
        defaultTenantDocumentShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByUploadedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);
        Employee uploadedBy = EmployeeResourceIT.createEntity(em);
        em.persist(uploadedBy);
        em.flush();
        tenantDocument.setUploadedBy(uploadedBy);
        tenantDocumentRepository.saveAndFlush(tenantDocument);
        Long uploadedById = uploadedBy.getId();

        // Get all the tenantDocumentList where uploadedBy equals to uploadedById
        defaultTenantDocumentShouldBeFound("uploadedById.equals=" + uploadedById);

        // Get all the tenantDocumentList where uploadedBy equals to uploadedById + 1
        defaultTenantDocumentShouldNotBeFound("uploadedById.equals=" + (uploadedById + 1));
    }


    @Test
    @Transactional
    public void getAllTenantDocumentsByApprovedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);
        Employee approvedBy = EmployeeResourceIT.createEntity(em);
        em.persist(approvedBy);
        em.flush();
        tenantDocument.setApprovedBy(approvedBy);
        tenantDocumentRepository.saveAndFlush(tenantDocument);
        Long approvedById = approvedBy.getId();

        // Get all the tenantDocumentList where approvedBy equals to approvedById
        defaultTenantDocumentShouldBeFound("approvedById.equals=" + approvedById);

        // Get all the tenantDocumentList where approvedBy equals to approvedById + 1
        defaultTenantDocumentShouldNotBeFound("approvedById.equals=" + (approvedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTenantDocumentShouldBeFound(String filter) throws Exception {
        restTenantDocumentMockMvc.perform(get("/api/tenant-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenantDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].documentCode").value(hasItem(DEFAULT_DOCUMENT_CODE)))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(DEFAULT_ISSUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(sameInstant(DEFAULT_UPLOADED_DATE))))
            .andExpect(jsonPath("$.[*].documentFileContentType").value(hasItem(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_FILE))))
            .andExpect(jsonPath("$.[*].documentFileUrl").value(hasItem(DEFAULT_DOCUMENT_FILE_URL)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restTenantDocumentMockMvc.perform(get("/api/tenant-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTenantDocumentShouldNotBeFound(String filter) throws Exception {
        restTenantDocumentMockMvc.perform(get("/api/tenant-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTenantDocumentMockMvc.perform(get("/api/tenant-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTenantDocument() throws Exception {
        // Get the tenantDocument
        restTenantDocumentMockMvc.perform(get("/api/tenant-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTenantDocument() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        int databaseSizeBeforeUpdate = tenantDocumentRepository.findAll().size();

        // Update the tenantDocument
        TenantDocument updatedTenantDocument = tenantDocumentRepository.findById(tenantDocument.getId()).get();
        // Disconnect from session so that the updates on updatedTenantDocument are not directly saved in db
        em.detach(updatedTenantDocument);
        updatedTenantDocument
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentCode(UPDATED_DOCUMENT_CODE)
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .note(UPDATED_NOTE)
            .issuedDate(UPDATED_ISSUED_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .documentFile(UPDATED_DOCUMENT_FILE)
            .documentFileContentType(UPDATED_DOCUMENT_FILE_CONTENT_TYPE)
            .documentFileUrl(UPDATED_DOCUMENT_FILE_URL)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        TenantDocumentDTO tenantDocumentDTO = tenantDocumentMapper.toDto(updatedTenantDocument);

        restTenantDocumentMockMvc.perform(put("/api/tenant-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the TenantDocument in the database
        List<TenantDocument> tenantDocumentList = tenantDocumentRepository.findAll();
        assertThat(tenantDocumentList).hasSize(databaseSizeBeforeUpdate);
        TenantDocument testTenantDocument = tenantDocumentList.get(tenantDocumentList.size() - 1);
        assertThat(testTenantDocument.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testTenantDocument.getDocumentCode()).isEqualTo(UPDATED_DOCUMENT_CODE);
        assertThat(testTenantDocument.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testTenantDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testTenantDocument.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testTenantDocument.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTenantDocument.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testTenantDocument.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testTenantDocument.getUploadedDate()).isEqualTo(UPDATED_UPLOADED_DATE);
        assertThat(testTenantDocument.getDocumentFile()).isEqualTo(UPDATED_DOCUMENT_FILE);
        assertThat(testTenantDocument.getDocumentFileContentType()).isEqualTo(UPDATED_DOCUMENT_FILE_CONTENT_TYPE);
        assertThat(testTenantDocument.getDocumentFileUrl()).isEqualTo(UPDATED_DOCUMENT_FILE_URL);
        assertThat(testTenantDocument.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testTenantDocument.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTenantDocument() throws Exception {
        int databaseSizeBeforeUpdate = tenantDocumentRepository.findAll().size();

        // Create the TenantDocument
        TenantDocumentDTO tenantDocumentDTO = tenantDocumentMapper.toDto(tenantDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenantDocumentMockMvc.perform(put("/api/tenant-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tenantDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TenantDocument in the database
        List<TenantDocument> tenantDocumentList = tenantDocumentRepository.findAll();
        assertThat(tenantDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTenantDocument() throws Exception {
        // Initialize the database
        tenantDocumentRepository.saveAndFlush(tenantDocument);

        int databaseSizeBeforeDelete = tenantDocumentRepository.findAll().size();

        // Delete the tenantDocument
        restTenantDocumentMockMvc.perform(delete("/api/tenant-documents/{id}", tenantDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TenantDocument> tenantDocumentList = tenantDocumentRepository.findAll();
        assertThat(tenantDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
