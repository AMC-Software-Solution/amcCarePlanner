package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.ClientDocument;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.ClientDocumentRepository;
import com.amc.careplanner.service.ClientDocumentService;
import com.amc.careplanner.service.dto.ClientDocumentDTO;
import com.amc.careplanner.service.mapper.ClientDocumentMapper;
import com.amc.careplanner.service.dto.ClientDocumentCriteria;
import com.amc.careplanner.service.ClientDocumentQueryService;

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

import com.amc.careplanner.domain.enumeration.ClientDocumentType;
import com.amc.careplanner.domain.enumeration.DocumentStatus;
/**
 * Integration tests for the {@link ClientDocumentResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER = "BBBBBBBBBB";

    private static final ClientDocumentType DEFAULT_DOCUMENT_TYPE = ClientDocumentType.POLICY;
    private static final ClientDocumentType UPDATED_DOCUMENT_TYPE = ClientDocumentType.PROCEDURE;

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
    private ClientDocumentRepository clientDocumentRepository;

    @Autowired
    private ClientDocumentMapper clientDocumentMapper;

    @Autowired
    private ClientDocumentService clientDocumentService;

    @Autowired
    private ClientDocumentQueryService clientDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientDocumentMockMvc;

    private ClientDocument clientDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientDocument createEntity(EntityManager em) {
        ClientDocument clientDocument = new ClientDocument()
            .documentName(DEFAULT_DOCUMENT_NAME)
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
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return clientDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientDocument createUpdatedEntity(EntityManager em) {
        ClientDocument clientDocument = new ClientDocument()
            .documentName(UPDATED_DOCUMENT_NAME)
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
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return clientDocument;
    }

    @BeforeEach
    public void initTest() {
        clientDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientDocument() throws Exception {
        int databaseSizeBeforeCreate = clientDocumentRepository.findAll().size();
        // Create the ClientDocument
        ClientDocumentDTO clientDocumentDTO = clientDocumentMapper.toDto(clientDocument);
        restClientDocumentMockMvc.perform(post("/api/client-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ClientDocument testClientDocument = clientDocumentList.get(clientDocumentList.size() - 1);
        assertThat(testClientDocument.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testClientDocument.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testClientDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testClientDocument.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testClientDocument.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testClientDocument.getIssuedDate()).isEqualTo(DEFAULT_ISSUED_DATE);
        assertThat(testClientDocument.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testClientDocument.getUploadedDate()).isEqualTo(DEFAULT_UPLOADED_DATE);
        assertThat(testClientDocument.getDocumentFile()).isEqualTo(DEFAULT_DOCUMENT_FILE);
        assertThat(testClientDocument.getDocumentFileContentType()).isEqualTo(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE);
        assertThat(testClientDocument.getDocumentFileUrl()).isEqualTo(DEFAULT_DOCUMENT_FILE_URL);
        assertThat(testClientDocument.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClientDocument.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testClientDocument.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientDocument.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createClientDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientDocumentRepository.findAll().size();

        // Create the ClientDocument with an existing ID
        clientDocument.setId(1L);
        ClientDocumentDTO clientDocumentDTO = clientDocumentMapper.toDto(clientDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientDocumentMockMvc.perform(post("/api/client-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDocumentRepository.findAll().size();
        // set the field null
        clientDocument.setDocumentName(null);

        // Create the ClientDocument, which fails.
        ClientDocumentDTO clientDocumentDTO = clientDocumentMapper.toDto(clientDocument);


        restClientDocumentMockMvc.perform(post("/api/client-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDocumentRepository.findAll().size();
        // set the field null
        clientDocument.setDocumentType(null);

        // Create the ClientDocument, which fails.
        ClientDocumentDTO clientDocumentDTO = clientDocumentMapper.toDto(clientDocument);


        restClientDocumentMockMvc.perform(post("/api/client-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDocumentRepository.findAll().size();
        // set the field null
        clientDocument.setClientId(null);

        // Create the ClientDocument, which fails.
        ClientDocumentDTO clientDocumentDTO = clientDocumentMapper.toDto(clientDocument);


        restClientDocumentMockMvc.perform(post("/api/client-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientDocuments() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList
        restClientDocumentMockMvc.perform(get("/api/client-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
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
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getClientDocument() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get the clientDocument
        restClientDocumentMockMvc.perform(get("/api/client-documents/{id}", clientDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME))
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
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getClientDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        Long id = clientDocument.getId();

        defaultClientDocumentShouldBeFound("id.equals=" + id);
        defaultClientDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultClientDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultClientDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentName equals to DEFAULT_DOCUMENT_NAME
        defaultClientDocumentShouldBeFound("documentName.equals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the clientDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultClientDocumentShouldNotBeFound("documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentName not equals to DEFAULT_DOCUMENT_NAME
        defaultClientDocumentShouldNotBeFound("documentName.notEquals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the clientDocumentList where documentName not equals to UPDATED_DOCUMENT_NAME
        defaultClientDocumentShouldBeFound("documentName.notEquals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentName in DEFAULT_DOCUMENT_NAME or UPDATED_DOCUMENT_NAME
        defaultClientDocumentShouldBeFound("documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME);

        // Get all the clientDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultClientDocumentShouldNotBeFound("documentName.in=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentName is not null
        defaultClientDocumentShouldBeFound("documentName.specified=true");

        // Get all the clientDocumentList where documentName is null
        defaultClientDocumentShouldNotBeFound("documentName.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNameContainsSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentName contains DEFAULT_DOCUMENT_NAME
        defaultClientDocumentShouldBeFound("documentName.contains=" + DEFAULT_DOCUMENT_NAME);

        // Get all the clientDocumentList where documentName contains UPDATED_DOCUMENT_NAME
        defaultClientDocumentShouldNotBeFound("documentName.contains=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentName does not contain DEFAULT_DOCUMENT_NAME
        defaultClientDocumentShouldNotBeFound("documentName.doesNotContain=" + DEFAULT_DOCUMENT_NAME);

        // Get all the clientDocumentList where documentName does not contain UPDATED_DOCUMENT_NAME
        defaultClientDocumentShouldBeFound("documentName.doesNotContain=" + UPDATED_DOCUMENT_NAME);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentNumber equals to DEFAULT_DOCUMENT_NUMBER
        defaultClientDocumentShouldBeFound("documentNumber.equals=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the clientDocumentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultClientDocumentShouldNotBeFound("documentNumber.equals=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentNumber not equals to DEFAULT_DOCUMENT_NUMBER
        defaultClientDocumentShouldNotBeFound("documentNumber.notEquals=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the clientDocumentList where documentNumber not equals to UPDATED_DOCUMENT_NUMBER
        defaultClientDocumentShouldBeFound("documentNumber.notEquals=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNumberIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentNumber in DEFAULT_DOCUMENT_NUMBER or UPDATED_DOCUMENT_NUMBER
        defaultClientDocumentShouldBeFound("documentNumber.in=" + DEFAULT_DOCUMENT_NUMBER + "," + UPDATED_DOCUMENT_NUMBER);

        // Get all the clientDocumentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultClientDocumentShouldNotBeFound("documentNumber.in=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentNumber is not null
        defaultClientDocumentShouldBeFound("documentNumber.specified=true");

        // Get all the clientDocumentList where documentNumber is null
        defaultClientDocumentShouldNotBeFound("documentNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNumberContainsSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentNumber contains DEFAULT_DOCUMENT_NUMBER
        defaultClientDocumentShouldBeFound("documentNumber.contains=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the clientDocumentList where documentNumber contains UPDATED_DOCUMENT_NUMBER
        defaultClientDocumentShouldNotBeFound("documentNumber.contains=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentNumberNotContainsSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentNumber does not contain DEFAULT_DOCUMENT_NUMBER
        defaultClientDocumentShouldNotBeFound("documentNumber.doesNotContain=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the clientDocumentList where documentNumber does not contain UPDATED_DOCUMENT_NUMBER
        defaultClientDocumentShouldBeFound("documentNumber.doesNotContain=" + UPDATED_DOCUMENT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentType equals to DEFAULT_DOCUMENT_TYPE
        defaultClientDocumentShouldBeFound("documentType.equals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the clientDocumentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultClientDocumentShouldNotBeFound("documentType.equals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentType not equals to DEFAULT_DOCUMENT_TYPE
        defaultClientDocumentShouldNotBeFound("documentType.notEquals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the clientDocumentList where documentType not equals to UPDATED_DOCUMENT_TYPE
        defaultClientDocumentShouldBeFound("documentType.notEquals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentType in DEFAULT_DOCUMENT_TYPE or UPDATED_DOCUMENT_TYPE
        defaultClientDocumentShouldBeFound("documentType.in=" + DEFAULT_DOCUMENT_TYPE + "," + UPDATED_DOCUMENT_TYPE);

        // Get all the clientDocumentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultClientDocumentShouldNotBeFound("documentType.in=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentType is not null
        defaultClientDocumentShouldBeFound("documentType.specified=true");

        // Get all the clientDocumentList where documentType is null
        defaultClientDocumentShouldNotBeFound("documentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultClientDocumentShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the clientDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultClientDocumentShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultClientDocumentShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the clientDocumentList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultClientDocumentShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultClientDocumentShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the clientDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultClientDocumentShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentStatus is not null
        defaultClientDocumentShouldBeFound("documentStatus.specified=true");

        // Get all the clientDocumentList where documentStatus is null
        defaultClientDocumentShouldNotBeFound("documentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where note equals to DEFAULT_NOTE
        defaultClientDocumentShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the clientDocumentList where note equals to UPDATED_NOTE
        defaultClientDocumentShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where note not equals to DEFAULT_NOTE
        defaultClientDocumentShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the clientDocumentList where note not equals to UPDATED_NOTE
        defaultClientDocumentShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultClientDocumentShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the clientDocumentList where note equals to UPDATED_NOTE
        defaultClientDocumentShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where note is not null
        defaultClientDocumentShouldBeFound("note.specified=true");

        // Get all the clientDocumentList where note is null
        defaultClientDocumentShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientDocumentsByNoteContainsSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where note contains DEFAULT_NOTE
        defaultClientDocumentShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the clientDocumentList where note contains UPDATED_NOTE
        defaultClientDocumentShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where note does not contain DEFAULT_NOTE
        defaultClientDocumentShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the clientDocumentList where note does not contain UPDATED_NOTE
        defaultClientDocumentShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByIssuedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where issuedDate equals to DEFAULT_ISSUED_DATE
        defaultClientDocumentShouldBeFound("issuedDate.equals=" + DEFAULT_ISSUED_DATE);

        // Get all the clientDocumentList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultClientDocumentShouldNotBeFound("issuedDate.equals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByIssuedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where issuedDate not equals to DEFAULT_ISSUED_DATE
        defaultClientDocumentShouldNotBeFound("issuedDate.notEquals=" + DEFAULT_ISSUED_DATE);

        // Get all the clientDocumentList where issuedDate not equals to UPDATED_ISSUED_DATE
        defaultClientDocumentShouldBeFound("issuedDate.notEquals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByIssuedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where issuedDate in DEFAULT_ISSUED_DATE or UPDATED_ISSUED_DATE
        defaultClientDocumentShouldBeFound("issuedDate.in=" + DEFAULT_ISSUED_DATE + "," + UPDATED_ISSUED_DATE);

        // Get all the clientDocumentList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultClientDocumentShouldNotBeFound("issuedDate.in=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByIssuedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where issuedDate is not null
        defaultClientDocumentShouldBeFound("issuedDate.specified=true");

        // Get all the clientDocumentList where issuedDate is null
        defaultClientDocumentShouldNotBeFound("issuedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByIssuedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where issuedDate is greater than or equal to DEFAULT_ISSUED_DATE
        defaultClientDocumentShouldBeFound("issuedDate.greaterThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the clientDocumentList where issuedDate is greater than or equal to UPDATED_ISSUED_DATE
        defaultClientDocumentShouldNotBeFound("issuedDate.greaterThanOrEqual=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByIssuedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where issuedDate is less than or equal to DEFAULT_ISSUED_DATE
        defaultClientDocumentShouldBeFound("issuedDate.lessThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the clientDocumentList where issuedDate is less than or equal to SMALLER_ISSUED_DATE
        defaultClientDocumentShouldNotBeFound("issuedDate.lessThanOrEqual=" + SMALLER_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByIssuedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where issuedDate is less than DEFAULT_ISSUED_DATE
        defaultClientDocumentShouldNotBeFound("issuedDate.lessThan=" + DEFAULT_ISSUED_DATE);

        // Get all the clientDocumentList where issuedDate is less than UPDATED_ISSUED_DATE
        defaultClientDocumentShouldBeFound("issuedDate.lessThan=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByIssuedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where issuedDate is greater than DEFAULT_ISSUED_DATE
        defaultClientDocumentShouldNotBeFound("issuedDate.greaterThan=" + DEFAULT_ISSUED_DATE);

        // Get all the clientDocumentList where issuedDate is greater than SMALLER_ISSUED_DATE
        defaultClientDocumentShouldBeFound("issuedDate.greaterThan=" + SMALLER_ISSUED_DATE);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultClientDocumentShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the clientDocumentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultClientDocumentShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where expiryDate not equals to DEFAULT_EXPIRY_DATE
        defaultClientDocumentShouldNotBeFound("expiryDate.notEquals=" + DEFAULT_EXPIRY_DATE);

        // Get all the clientDocumentList where expiryDate not equals to UPDATED_EXPIRY_DATE
        defaultClientDocumentShouldBeFound("expiryDate.notEquals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultClientDocumentShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the clientDocumentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultClientDocumentShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where expiryDate is not null
        defaultClientDocumentShouldBeFound("expiryDate.specified=true");

        // Get all the clientDocumentList where expiryDate is null
        defaultClientDocumentShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where expiryDate is greater than or equal to DEFAULT_EXPIRY_DATE
        defaultClientDocumentShouldBeFound("expiryDate.greaterThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the clientDocumentList where expiryDate is greater than or equal to UPDATED_EXPIRY_DATE
        defaultClientDocumentShouldNotBeFound("expiryDate.greaterThanOrEqual=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByExpiryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where expiryDate is less than or equal to DEFAULT_EXPIRY_DATE
        defaultClientDocumentShouldBeFound("expiryDate.lessThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the clientDocumentList where expiryDate is less than or equal to SMALLER_EXPIRY_DATE
        defaultClientDocumentShouldNotBeFound("expiryDate.lessThanOrEqual=" + SMALLER_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where expiryDate is less than DEFAULT_EXPIRY_DATE
        defaultClientDocumentShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the clientDocumentList where expiryDate is less than UPDATED_EXPIRY_DATE
        defaultClientDocumentShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByExpiryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where expiryDate is greater than DEFAULT_EXPIRY_DATE
        defaultClientDocumentShouldNotBeFound("expiryDate.greaterThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the clientDocumentList where expiryDate is greater than SMALLER_EXPIRY_DATE
        defaultClientDocumentShouldBeFound("expiryDate.greaterThan=" + SMALLER_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByUploadedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where uploadedDate equals to DEFAULT_UPLOADED_DATE
        defaultClientDocumentShouldBeFound("uploadedDate.equals=" + DEFAULT_UPLOADED_DATE);

        // Get all the clientDocumentList where uploadedDate equals to UPDATED_UPLOADED_DATE
        defaultClientDocumentShouldNotBeFound("uploadedDate.equals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByUploadedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where uploadedDate not equals to DEFAULT_UPLOADED_DATE
        defaultClientDocumentShouldNotBeFound("uploadedDate.notEquals=" + DEFAULT_UPLOADED_DATE);

        // Get all the clientDocumentList where uploadedDate not equals to UPDATED_UPLOADED_DATE
        defaultClientDocumentShouldBeFound("uploadedDate.notEquals=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByUploadedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where uploadedDate in DEFAULT_UPLOADED_DATE or UPDATED_UPLOADED_DATE
        defaultClientDocumentShouldBeFound("uploadedDate.in=" + DEFAULT_UPLOADED_DATE + "," + UPDATED_UPLOADED_DATE);

        // Get all the clientDocumentList where uploadedDate equals to UPDATED_UPLOADED_DATE
        defaultClientDocumentShouldNotBeFound("uploadedDate.in=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByUploadedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where uploadedDate is not null
        defaultClientDocumentShouldBeFound("uploadedDate.specified=true");

        // Get all the clientDocumentList where uploadedDate is null
        defaultClientDocumentShouldNotBeFound("uploadedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByUploadedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where uploadedDate is greater than or equal to DEFAULT_UPLOADED_DATE
        defaultClientDocumentShouldBeFound("uploadedDate.greaterThanOrEqual=" + DEFAULT_UPLOADED_DATE);

        // Get all the clientDocumentList where uploadedDate is greater than or equal to UPDATED_UPLOADED_DATE
        defaultClientDocumentShouldNotBeFound("uploadedDate.greaterThanOrEqual=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByUploadedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where uploadedDate is less than or equal to DEFAULT_UPLOADED_DATE
        defaultClientDocumentShouldBeFound("uploadedDate.lessThanOrEqual=" + DEFAULT_UPLOADED_DATE);

        // Get all the clientDocumentList where uploadedDate is less than or equal to SMALLER_UPLOADED_DATE
        defaultClientDocumentShouldNotBeFound("uploadedDate.lessThanOrEqual=" + SMALLER_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByUploadedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where uploadedDate is less than DEFAULT_UPLOADED_DATE
        defaultClientDocumentShouldNotBeFound("uploadedDate.lessThan=" + DEFAULT_UPLOADED_DATE);

        // Get all the clientDocumentList where uploadedDate is less than UPDATED_UPLOADED_DATE
        defaultClientDocumentShouldBeFound("uploadedDate.lessThan=" + UPDATED_UPLOADED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByUploadedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where uploadedDate is greater than DEFAULT_UPLOADED_DATE
        defaultClientDocumentShouldNotBeFound("uploadedDate.greaterThan=" + DEFAULT_UPLOADED_DATE);

        // Get all the clientDocumentList where uploadedDate is greater than SMALLER_UPLOADED_DATE
        defaultClientDocumentShouldBeFound("uploadedDate.greaterThan=" + SMALLER_UPLOADED_DATE);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentFileUrl equals to DEFAULT_DOCUMENT_FILE_URL
        defaultClientDocumentShouldBeFound("documentFileUrl.equals=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the clientDocumentList where documentFileUrl equals to UPDATED_DOCUMENT_FILE_URL
        defaultClientDocumentShouldNotBeFound("documentFileUrl.equals=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentFileUrl not equals to DEFAULT_DOCUMENT_FILE_URL
        defaultClientDocumentShouldNotBeFound("documentFileUrl.notEquals=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the clientDocumentList where documentFileUrl not equals to UPDATED_DOCUMENT_FILE_URL
        defaultClientDocumentShouldBeFound("documentFileUrl.notEquals=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentFileUrl in DEFAULT_DOCUMENT_FILE_URL or UPDATED_DOCUMENT_FILE_URL
        defaultClientDocumentShouldBeFound("documentFileUrl.in=" + DEFAULT_DOCUMENT_FILE_URL + "," + UPDATED_DOCUMENT_FILE_URL);

        // Get all the clientDocumentList where documentFileUrl equals to UPDATED_DOCUMENT_FILE_URL
        defaultClientDocumentShouldNotBeFound("documentFileUrl.in=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentFileUrl is not null
        defaultClientDocumentShouldBeFound("documentFileUrl.specified=true");

        // Get all the clientDocumentList where documentFileUrl is null
        defaultClientDocumentShouldNotBeFound("documentFileUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientDocumentsByDocumentFileUrlContainsSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentFileUrl contains DEFAULT_DOCUMENT_FILE_URL
        defaultClientDocumentShouldBeFound("documentFileUrl.contains=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the clientDocumentList where documentFileUrl contains UPDATED_DOCUMENT_FILE_URL
        defaultClientDocumentShouldNotBeFound("documentFileUrl.contains=" + UPDATED_DOCUMENT_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByDocumentFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where documentFileUrl does not contain DEFAULT_DOCUMENT_FILE_URL
        defaultClientDocumentShouldNotBeFound("documentFileUrl.doesNotContain=" + DEFAULT_DOCUMENT_FILE_URL);

        // Get all the clientDocumentList where documentFileUrl does not contain UPDATED_DOCUMENT_FILE_URL
        defaultClientDocumentShouldBeFound("documentFileUrl.doesNotContain=" + UPDATED_DOCUMENT_FILE_URL);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where createdDate equals to DEFAULT_CREATED_DATE
        defaultClientDocumentShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the clientDocumentList where createdDate equals to UPDATED_CREATED_DATE
        defaultClientDocumentShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultClientDocumentShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the clientDocumentList where createdDate not equals to UPDATED_CREATED_DATE
        defaultClientDocumentShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultClientDocumentShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the clientDocumentList where createdDate equals to UPDATED_CREATED_DATE
        defaultClientDocumentShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where createdDate is not null
        defaultClientDocumentShouldBeFound("createdDate.specified=true");

        // Get all the clientDocumentList where createdDate is null
        defaultClientDocumentShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultClientDocumentShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the clientDocumentList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultClientDocumentShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultClientDocumentShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the clientDocumentList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultClientDocumentShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where createdDate is less than DEFAULT_CREATED_DATE
        defaultClientDocumentShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the clientDocumentList where createdDate is less than UPDATED_CREATED_DATE
        defaultClientDocumentShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultClientDocumentShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the clientDocumentList where createdDate is greater than SMALLER_CREATED_DATE
        defaultClientDocumentShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultClientDocumentShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientDocumentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultClientDocumentShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultClientDocumentShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientDocumentList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultClientDocumentShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultClientDocumentShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the clientDocumentList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultClientDocumentShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where lastUpdatedDate is not null
        defaultClientDocumentShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the clientDocumentList where lastUpdatedDate is null
        defaultClientDocumentShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultClientDocumentShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientDocumentList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultClientDocumentShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultClientDocumentShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientDocumentList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultClientDocumentShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultClientDocumentShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientDocumentList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultClientDocumentShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultClientDocumentShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientDocumentList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultClientDocumentShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where clientId equals to DEFAULT_CLIENT_ID
        defaultClientDocumentShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the clientDocumentList where clientId equals to UPDATED_CLIENT_ID
        defaultClientDocumentShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where clientId not equals to DEFAULT_CLIENT_ID
        defaultClientDocumentShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the clientDocumentList where clientId not equals to UPDATED_CLIENT_ID
        defaultClientDocumentShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultClientDocumentShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the clientDocumentList where clientId equals to UPDATED_CLIENT_ID
        defaultClientDocumentShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where clientId is not null
        defaultClientDocumentShouldBeFound("clientId.specified=true");

        // Get all the clientDocumentList where clientId is null
        defaultClientDocumentShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultClientDocumentShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the clientDocumentList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultClientDocumentShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultClientDocumentShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the clientDocumentList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultClientDocumentShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where clientId is less than DEFAULT_CLIENT_ID
        defaultClientDocumentShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the clientDocumentList where clientId is less than UPDATED_CLIENT_ID
        defaultClientDocumentShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where clientId is greater than DEFAULT_CLIENT_ID
        defaultClientDocumentShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the clientDocumentList where clientId is greater than SMALLER_CLIENT_ID
        defaultClientDocumentShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultClientDocumentShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the clientDocumentList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultClientDocumentShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultClientDocumentShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the clientDocumentList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultClientDocumentShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultClientDocumentShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the clientDocumentList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultClientDocumentShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList where hasExtraData is not null
        defaultClientDocumentShouldBeFound("hasExtraData.specified=true");

        // Get all the clientDocumentList where hasExtraData is null
        defaultClientDocumentShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientDocumentsByUploadedByIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);
        Employee uploadedBy = EmployeeResourceIT.createEntity(em);
        em.persist(uploadedBy);
        em.flush();
        clientDocument.setUploadedBy(uploadedBy);
        clientDocumentRepository.saveAndFlush(clientDocument);
        Long uploadedById = uploadedBy.getId();

        // Get all the clientDocumentList where uploadedBy equals to uploadedById
        defaultClientDocumentShouldBeFound("uploadedById.equals=" + uploadedById);

        // Get all the clientDocumentList where uploadedBy equals to uploadedById + 1
        defaultClientDocumentShouldNotBeFound("uploadedById.equals=" + (uploadedById + 1));
    }


    @Test
    @Transactional
    public void getAllClientDocumentsByApprovedByIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);
        Employee approvedBy = EmployeeResourceIT.createEntity(em);
        em.persist(approvedBy);
        em.flush();
        clientDocument.setApprovedBy(approvedBy);
        clientDocumentRepository.saveAndFlush(clientDocument);
        Long approvedById = approvedBy.getId();

        // Get all the clientDocumentList where approvedBy equals to approvedById
        defaultClientDocumentShouldBeFound("approvedById.equals=" + approvedById);

        // Get all the clientDocumentList where approvedBy equals to approvedById + 1
        defaultClientDocumentShouldNotBeFound("approvedById.equals=" + (approvedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientDocumentShouldBeFound(String filter) throws Exception {
        restClientDocumentMockMvc.perform(get("/api/client-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
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
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restClientDocumentMockMvc.perform(get("/api/client-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientDocumentShouldNotBeFound(String filter) throws Exception {
        restClientDocumentMockMvc.perform(get("/api/client-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientDocumentMockMvc.perform(get("/api/client-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClientDocument() throws Exception {
        // Get the clientDocument
        restClientDocumentMockMvc.perform(get("/api/client-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientDocument() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();

        // Update the clientDocument
        ClientDocument updatedClientDocument = clientDocumentRepository.findById(clientDocument.getId()).get();
        // Disconnect from session so that the updates on updatedClientDocument are not directly saved in db
        em.detach(updatedClientDocument);
        updatedClientDocument
            .documentName(UPDATED_DOCUMENT_NAME)
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
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        ClientDocumentDTO clientDocumentDTO = clientDocumentMapper.toDto(updatedClientDocument);

        restClientDocumentMockMvc.perform(put("/api/client-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
        ClientDocument testClientDocument = clientDocumentList.get(clientDocumentList.size() - 1);
        assertThat(testClientDocument.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testClientDocument.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testClientDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testClientDocument.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testClientDocument.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testClientDocument.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testClientDocument.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testClientDocument.getUploadedDate()).isEqualTo(UPDATED_UPLOADED_DATE);
        assertThat(testClientDocument.getDocumentFile()).isEqualTo(UPDATED_DOCUMENT_FILE);
        assertThat(testClientDocument.getDocumentFileContentType()).isEqualTo(UPDATED_DOCUMENT_FILE_CONTENT_TYPE);
        assertThat(testClientDocument.getDocumentFileUrl()).isEqualTo(UPDATED_DOCUMENT_FILE_URL);
        assertThat(testClientDocument.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClientDocument.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testClientDocument.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientDocument.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingClientDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();

        // Create the ClientDocument
        ClientDocumentDTO clientDocumentDTO = clientDocumentMapper.toDto(clientDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientDocumentMockMvc.perform(put("/api/client-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientDocument() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        int databaseSizeBeforeDelete = clientDocumentRepository.findAll().size();

        // Delete the clientDocument
        restClientDocumentMockMvc.perform(delete("/api/client-documents/{id}", clientDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
