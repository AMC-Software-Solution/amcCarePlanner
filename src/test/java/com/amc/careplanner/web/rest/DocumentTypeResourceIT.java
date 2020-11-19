package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.DocumentType;
import com.amc.careplanner.repository.DocumentTypeRepository;
import com.amc.careplanner.service.DocumentTypeService;
import com.amc.careplanner.service.dto.DocumentTypeDTO;
import com.amc.careplanner.service.mapper.DocumentTypeMapper;
import com.amc.careplanner.service.dto.DocumentTypeCriteria;
import com.amc.careplanner.service.DocumentTypeQueryService;

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
 * Integration tests for the {@link DocumentTypeResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocumentTypeResourceIT {

    private static final String DEFAULT_DOCUMENT_TYPE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TYPE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private DocumentTypeMapper documentTypeMapper;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private DocumentTypeQueryService documentTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentTypeMockMvc;

    private DocumentType documentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentType createEntity(EntityManager em) {
        DocumentType documentType = new DocumentType()
            .documentTypeTitle(DEFAULT_DOCUMENT_TYPE_TITLE)
            .documentTypeCode(DEFAULT_DOCUMENT_TYPE_CODE)
            .documentTypeDescription(DEFAULT_DOCUMENT_TYPE_DESCRIPTION)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE);
        return documentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentType createUpdatedEntity(EntityManager em) {
        DocumentType documentType = new DocumentType()
            .documentTypeTitle(UPDATED_DOCUMENT_TYPE_TITLE)
            .documentTypeCode(UPDATED_DOCUMENT_TYPE_CODE)
            .documentTypeDescription(UPDATED_DOCUMENT_TYPE_DESCRIPTION)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE);
        return documentType;
    }

    @BeforeEach
    public void initTest() {
        documentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentType() throws Exception {
        int databaseSizeBeforeCreate = documentTypeRepository.findAll().size();
        // Create the DocumentType
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);
        restDocumentTypeMockMvc.perform(post("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentType testDocumentType = documentTypeList.get(documentTypeList.size() - 1);
        assertThat(testDocumentType.getDocumentTypeTitle()).isEqualTo(DEFAULT_DOCUMENT_TYPE_TITLE);
        assertThat(testDocumentType.getDocumentTypeCode()).isEqualTo(DEFAULT_DOCUMENT_TYPE_CODE);
        assertThat(testDocumentType.getDocumentTypeDescription()).isEqualTo(DEFAULT_DOCUMENT_TYPE_DESCRIPTION);
        assertThat(testDocumentType.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createDocumentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentTypeRepository.findAll().size();

        // Create the DocumentType with an existing ID
        documentType.setId(1L);
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTypeMockMvc.perform(post("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentTypeTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentTypeRepository.findAll().size();
        // set the field null
        documentType.setDocumentTypeTitle(null);

        // Create the DocumentType, which fails.
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);


        restDocumentTypeMockMvc.perform(post("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentTypeRepository.findAll().size();
        // set the field null
        documentType.setDocumentTypeCode(null);

        // Create the DocumentType, which fails.
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);


        restDocumentTypeMockMvc.perform(post("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentTypes() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList
        restDocumentTypeMockMvc.perform(get("/api/document-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentTypeTitle").value(hasItem(DEFAULT_DOCUMENT_TYPE_TITLE)))
            .andExpect(jsonPath("$.[*].documentTypeCode").value(hasItem(DEFAULT_DOCUMENT_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].documentTypeDescription").value(hasItem(DEFAULT_DOCUMENT_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getDocumentType() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get the documentType
        restDocumentTypeMockMvc.perform(get("/api/document-types/{id}", documentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentType.getId().intValue()))
            .andExpect(jsonPath("$.documentTypeTitle").value(DEFAULT_DOCUMENT_TYPE_TITLE))
            .andExpect(jsonPath("$.documentTypeCode").value(DEFAULT_DOCUMENT_TYPE_CODE))
            .andExpect(jsonPath("$.documentTypeDescription").value(DEFAULT_DOCUMENT_TYPE_DESCRIPTION))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)));
    }


    @Test
    @Transactional
    public void getDocumentTypesByIdFiltering() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        Long id = documentType.getId();

        defaultDocumentTypeShouldBeFound("id.equals=" + id);
        defaultDocumentTypeShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeTitle equals to DEFAULT_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldBeFound("documentTypeTitle.equals=" + DEFAULT_DOCUMENT_TYPE_TITLE);

        // Get all the documentTypeList where documentTypeTitle equals to UPDATED_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldNotBeFound("documentTypeTitle.equals=" + UPDATED_DOCUMENT_TYPE_TITLE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeTitle not equals to DEFAULT_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldNotBeFound("documentTypeTitle.notEquals=" + DEFAULT_DOCUMENT_TYPE_TITLE);

        // Get all the documentTypeList where documentTypeTitle not equals to UPDATED_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldBeFound("documentTypeTitle.notEquals=" + UPDATED_DOCUMENT_TYPE_TITLE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeTitleIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeTitle in DEFAULT_DOCUMENT_TYPE_TITLE or UPDATED_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldBeFound("documentTypeTitle.in=" + DEFAULT_DOCUMENT_TYPE_TITLE + "," + UPDATED_DOCUMENT_TYPE_TITLE);

        // Get all the documentTypeList where documentTypeTitle equals to UPDATED_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldNotBeFound("documentTypeTitle.in=" + UPDATED_DOCUMENT_TYPE_TITLE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeTitle is not null
        defaultDocumentTypeShouldBeFound("documentTypeTitle.specified=true");

        // Get all the documentTypeList where documentTypeTitle is null
        defaultDocumentTypeShouldNotBeFound("documentTypeTitle.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeTitleContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeTitle contains DEFAULT_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldBeFound("documentTypeTitle.contains=" + DEFAULT_DOCUMENT_TYPE_TITLE);

        // Get all the documentTypeList where documentTypeTitle contains UPDATED_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldNotBeFound("documentTypeTitle.contains=" + UPDATED_DOCUMENT_TYPE_TITLE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeTitleNotContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeTitle does not contain DEFAULT_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldNotBeFound("documentTypeTitle.doesNotContain=" + DEFAULT_DOCUMENT_TYPE_TITLE);

        // Get all the documentTypeList where documentTypeTitle does not contain UPDATED_DOCUMENT_TYPE_TITLE
        defaultDocumentTypeShouldBeFound("documentTypeTitle.doesNotContain=" + UPDATED_DOCUMENT_TYPE_TITLE);
    }


    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeCode equals to DEFAULT_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldBeFound("documentTypeCode.equals=" + DEFAULT_DOCUMENT_TYPE_CODE);

        // Get all the documentTypeList where documentTypeCode equals to UPDATED_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldNotBeFound("documentTypeCode.equals=" + UPDATED_DOCUMENT_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeCode not equals to DEFAULT_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldNotBeFound("documentTypeCode.notEquals=" + DEFAULT_DOCUMENT_TYPE_CODE);

        // Get all the documentTypeList where documentTypeCode not equals to UPDATED_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldBeFound("documentTypeCode.notEquals=" + UPDATED_DOCUMENT_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeCode in DEFAULT_DOCUMENT_TYPE_CODE or UPDATED_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldBeFound("documentTypeCode.in=" + DEFAULT_DOCUMENT_TYPE_CODE + "," + UPDATED_DOCUMENT_TYPE_CODE);

        // Get all the documentTypeList where documentTypeCode equals to UPDATED_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldNotBeFound("documentTypeCode.in=" + UPDATED_DOCUMENT_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeCode is not null
        defaultDocumentTypeShouldBeFound("documentTypeCode.specified=true");

        // Get all the documentTypeList where documentTypeCode is null
        defaultDocumentTypeShouldNotBeFound("documentTypeCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeCodeContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeCode contains DEFAULT_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldBeFound("documentTypeCode.contains=" + DEFAULT_DOCUMENT_TYPE_CODE);

        // Get all the documentTypeList where documentTypeCode contains UPDATED_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldNotBeFound("documentTypeCode.contains=" + UPDATED_DOCUMENT_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeCode does not contain DEFAULT_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldNotBeFound("documentTypeCode.doesNotContain=" + DEFAULT_DOCUMENT_TYPE_CODE);

        // Get all the documentTypeList where documentTypeCode does not contain UPDATED_DOCUMENT_TYPE_CODE
        defaultDocumentTypeShouldBeFound("documentTypeCode.doesNotContain=" + UPDATED_DOCUMENT_TYPE_CODE);
    }


    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeDescription equals to DEFAULT_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldBeFound("documentTypeDescription.equals=" + DEFAULT_DOCUMENT_TYPE_DESCRIPTION);

        // Get all the documentTypeList where documentTypeDescription equals to UPDATED_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("documentTypeDescription.equals=" + UPDATED_DOCUMENT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeDescription not equals to DEFAULT_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("documentTypeDescription.notEquals=" + DEFAULT_DOCUMENT_TYPE_DESCRIPTION);

        // Get all the documentTypeList where documentTypeDescription not equals to UPDATED_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldBeFound("documentTypeDescription.notEquals=" + UPDATED_DOCUMENT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeDescription in DEFAULT_DOCUMENT_TYPE_DESCRIPTION or UPDATED_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldBeFound("documentTypeDescription.in=" + DEFAULT_DOCUMENT_TYPE_DESCRIPTION + "," + UPDATED_DOCUMENT_TYPE_DESCRIPTION);

        // Get all the documentTypeList where documentTypeDescription equals to UPDATED_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("documentTypeDescription.in=" + UPDATED_DOCUMENT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeDescription is not null
        defaultDocumentTypeShouldBeFound("documentTypeDescription.specified=true");

        // Get all the documentTypeList where documentTypeDescription is null
        defaultDocumentTypeShouldNotBeFound("documentTypeDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeDescription contains DEFAULT_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldBeFound("documentTypeDescription.contains=" + DEFAULT_DOCUMENT_TYPE_DESCRIPTION);

        // Get all the documentTypeList where documentTypeDescription contains UPDATED_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("documentTypeDescription.contains=" + UPDATED_DOCUMENT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where documentTypeDescription does not contain DEFAULT_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("documentTypeDescription.doesNotContain=" + DEFAULT_DOCUMENT_TYPE_DESCRIPTION);

        // Get all the documentTypeList where documentTypeDescription does not contain UPDATED_DOCUMENT_TYPE_DESCRIPTION
        defaultDocumentTypeShouldBeFound("documentTypeDescription.doesNotContain=" + UPDATED_DOCUMENT_TYPE_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllDocumentTypesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultDocumentTypeShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the documentTypeList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultDocumentTypeShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultDocumentTypeShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the documentTypeList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultDocumentTypeShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultDocumentTypeShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the documentTypeList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultDocumentTypeShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where lastUpdatedDate is not null
        defaultDocumentTypeShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the documentTypeList where lastUpdatedDate is null
        defaultDocumentTypeShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultDocumentTypeShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the documentTypeList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultDocumentTypeShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultDocumentTypeShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the documentTypeList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultDocumentTypeShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultDocumentTypeShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the documentTypeList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultDocumentTypeShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultDocumentTypeShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the documentTypeList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultDocumentTypeShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentTypeShouldBeFound(String filter) throws Exception {
        restDocumentTypeMockMvc.perform(get("/api/document-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentTypeTitle").value(hasItem(DEFAULT_DOCUMENT_TYPE_TITLE)))
            .andExpect(jsonPath("$.[*].documentTypeCode").value(hasItem(DEFAULT_DOCUMENT_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].documentTypeDescription").value(hasItem(DEFAULT_DOCUMENT_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));

        // Check, that the count call also returns 1
        restDocumentTypeMockMvc.perform(get("/api/document-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentTypeShouldNotBeFound(String filter) throws Exception {
        restDocumentTypeMockMvc.perform(get("/api/document-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentTypeMockMvc.perform(get("/api/document-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentType() throws Exception {
        // Get the documentType
        restDocumentTypeMockMvc.perform(get("/api/document-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentType() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();

        // Update the documentType
        DocumentType updatedDocumentType = documentTypeRepository.findById(documentType.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentType are not directly saved in db
        em.detach(updatedDocumentType);
        updatedDocumentType
            .documentTypeTitle(UPDATED_DOCUMENT_TYPE_TITLE)
            .documentTypeCode(UPDATED_DOCUMENT_TYPE_CODE)
            .documentTypeDescription(UPDATED_DOCUMENT_TYPE_DESCRIPTION)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE);
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(updatedDocumentType);

        restDocumentTypeMockMvc.perform(put("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
        DocumentType testDocumentType = documentTypeList.get(documentTypeList.size() - 1);
        assertThat(testDocumentType.getDocumentTypeTitle()).isEqualTo(UPDATED_DOCUMENT_TYPE_TITLE);
        assertThat(testDocumentType.getDocumentTypeCode()).isEqualTo(UPDATED_DOCUMENT_TYPE_CODE);
        assertThat(testDocumentType.getDocumentTypeDescription()).isEqualTo(UPDATED_DOCUMENT_TYPE_DESCRIPTION);
        assertThat(testDocumentType.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();

        // Create the DocumentType
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTypeMockMvc.perform(put("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentType() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        int databaseSizeBeforeDelete = documentTypeRepository.findAll().size();

        // Delete the documentType
        restDocumentTypeMockMvc.perform(delete("/api/document-types/{id}", documentType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
