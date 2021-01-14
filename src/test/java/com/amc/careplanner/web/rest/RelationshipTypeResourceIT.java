package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.RelationshipType;
import com.amc.careplanner.repository.RelationshipTypeRepository;
import com.amc.careplanner.service.RelationshipTypeService;
import com.amc.careplanner.service.dto.RelationshipTypeDTO;
import com.amc.careplanner.service.mapper.RelationshipTypeMapper;
import com.amc.careplanner.service.dto.RelationshipTypeCriteria;
import com.amc.careplanner.service.RelationshipTypeQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RelationshipTypeResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RelationshipTypeResourceIT {

    private static final String DEFAULT_RELATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RELATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private RelationshipTypeRepository relationshipTypeRepository;

    @Autowired
    private RelationshipTypeMapper relationshipTypeMapper;

    @Autowired
    private RelationshipTypeService relationshipTypeService;

    @Autowired
    private RelationshipTypeQueryService relationshipTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelationshipTypeMockMvc;

    private RelationshipType relationshipType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelationshipType createEntity(EntityManager em) {
        RelationshipType relationshipType = new RelationshipType()
            .relationType(DEFAULT_RELATION_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return relationshipType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelationshipType createUpdatedEntity(EntityManager em) {
        RelationshipType relationshipType = new RelationshipType()
            .relationType(UPDATED_RELATION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return relationshipType;
    }

    @BeforeEach
    public void initTest() {
        relationshipType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelationshipType() throws Exception {
        int databaseSizeBeforeCreate = relationshipTypeRepository.findAll().size();
        // Create the RelationshipType
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);
        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getRelationType()).isEqualTo(DEFAULT_RELATION_TYPE);
        assertThat(testRelationshipType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRelationshipType.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testRelationshipType.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createRelationshipTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationshipTypeRepository.findAll().size();

        // Create the RelationshipType with an existing ID
        relationshipType.setId(1L);
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRelationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipTypeRepository.findAll().size();
        // set the field null
        relationshipType.setRelationType(null);

        // Create the RelationshipType, which fails.
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);


        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO)))
            .andExpect(status().isBadRequest());

        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipTypeRepository.findAll().size();
        // set the field null
        relationshipType.setClientId(null);

        // Create the RelationshipType, which fails.
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);


        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO)))
            .andExpect(status().isBadRequest());

        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypes() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationshipType.getId().intValue())))
            .andExpect(jsonPath("$.[*].relationType").value(hasItem(DEFAULT_RELATION_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get the relationshipType
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types/{id}", relationshipType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relationshipType.getId().intValue()))
            .andExpect(jsonPath("$.relationType").value(DEFAULT_RELATION_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getRelationshipTypesByIdFiltering() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        Long id = relationshipType.getId();

        defaultRelationshipTypeShouldBeFound("id.equals=" + id);
        defaultRelationshipTypeShouldNotBeFound("id.notEquals=" + id);

        defaultRelationshipTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRelationshipTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultRelationshipTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRelationshipTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRelationshipTypesByRelationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where relationType equals to DEFAULT_RELATION_TYPE
        defaultRelationshipTypeShouldBeFound("relationType.equals=" + DEFAULT_RELATION_TYPE);

        // Get all the relationshipTypeList where relationType equals to UPDATED_RELATION_TYPE
        defaultRelationshipTypeShouldNotBeFound("relationType.equals=" + UPDATED_RELATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByRelationTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where relationType not equals to DEFAULT_RELATION_TYPE
        defaultRelationshipTypeShouldNotBeFound("relationType.notEquals=" + DEFAULT_RELATION_TYPE);

        // Get all the relationshipTypeList where relationType not equals to UPDATED_RELATION_TYPE
        defaultRelationshipTypeShouldBeFound("relationType.notEquals=" + UPDATED_RELATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByRelationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where relationType in DEFAULT_RELATION_TYPE or UPDATED_RELATION_TYPE
        defaultRelationshipTypeShouldBeFound("relationType.in=" + DEFAULT_RELATION_TYPE + "," + UPDATED_RELATION_TYPE);

        // Get all the relationshipTypeList where relationType equals to UPDATED_RELATION_TYPE
        defaultRelationshipTypeShouldNotBeFound("relationType.in=" + UPDATED_RELATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByRelationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where relationType is not null
        defaultRelationshipTypeShouldBeFound("relationType.specified=true");

        // Get all the relationshipTypeList where relationType is null
        defaultRelationshipTypeShouldNotBeFound("relationType.specified=false");
    }
                @Test
    @Transactional
    public void getAllRelationshipTypesByRelationTypeContainsSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where relationType contains DEFAULT_RELATION_TYPE
        defaultRelationshipTypeShouldBeFound("relationType.contains=" + DEFAULT_RELATION_TYPE);

        // Get all the relationshipTypeList where relationType contains UPDATED_RELATION_TYPE
        defaultRelationshipTypeShouldNotBeFound("relationType.contains=" + UPDATED_RELATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByRelationTypeNotContainsSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where relationType does not contain DEFAULT_RELATION_TYPE
        defaultRelationshipTypeShouldNotBeFound("relationType.doesNotContain=" + DEFAULT_RELATION_TYPE);

        // Get all the relationshipTypeList where relationType does not contain UPDATED_RELATION_TYPE
        defaultRelationshipTypeShouldBeFound("relationType.doesNotContain=" + UPDATED_RELATION_TYPE);
    }


    @Test
    @Transactional
    public void getAllRelationshipTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where description equals to DEFAULT_DESCRIPTION
        defaultRelationshipTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the relationshipTypeList where description equals to UPDATED_DESCRIPTION
        defaultRelationshipTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultRelationshipTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the relationshipTypeList where description not equals to UPDATED_DESCRIPTION
        defaultRelationshipTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRelationshipTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the relationshipTypeList where description equals to UPDATED_DESCRIPTION
        defaultRelationshipTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where description is not null
        defaultRelationshipTypeShouldBeFound("description.specified=true");

        // Get all the relationshipTypeList where description is null
        defaultRelationshipTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllRelationshipTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where description contains DEFAULT_DESCRIPTION
        defaultRelationshipTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the relationshipTypeList where description contains UPDATED_DESCRIPTION
        defaultRelationshipTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultRelationshipTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the relationshipTypeList where description does not contain UPDATED_DESCRIPTION
        defaultRelationshipTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllRelationshipTypesByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where clientId equals to DEFAULT_CLIENT_ID
        defaultRelationshipTypeShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the relationshipTypeList where clientId equals to UPDATED_CLIENT_ID
        defaultRelationshipTypeShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where clientId not equals to DEFAULT_CLIENT_ID
        defaultRelationshipTypeShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the relationshipTypeList where clientId not equals to UPDATED_CLIENT_ID
        defaultRelationshipTypeShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultRelationshipTypeShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the relationshipTypeList where clientId equals to UPDATED_CLIENT_ID
        defaultRelationshipTypeShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where clientId is not null
        defaultRelationshipTypeShouldBeFound("clientId.specified=true");

        // Get all the relationshipTypeList where clientId is null
        defaultRelationshipTypeShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultRelationshipTypeShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the relationshipTypeList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultRelationshipTypeShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultRelationshipTypeShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the relationshipTypeList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultRelationshipTypeShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where clientId is less than DEFAULT_CLIENT_ID
        defaultRelationshipTypeShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the relationshipTypeList where clientId is less than UPDATED_CLIENT_ID
        defaultRelationshipTypeShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where clientId is greater than DEFAULT_CLIENT_ID
        defaultRelationshipTypeShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the relationshipTypeList where clientId is greater than SMALLER_CLIENT_ID
        defaultRelationshipTypeShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllRelationshipTypesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultRelationshipTypeShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the relationshipTypeList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultRelationshipTypeShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultRelationshipTypeShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the relationshipTypeList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultRelationshipTypeShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultRelationshipTypeShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the relationshipTypeList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultRelationshipTypeShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList where hasExtraData is not null
        defaultRelationshipTypeShouldBeFound("hasExtraData.specified=true");

        // Get all the relationshipTypeList where hasExtraData is null
        defaultRelationshipTypeShouldNotBeFound("hasExtraData.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRelationshipTypeShouldBeFound(String filter) throws Exception {
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationshipType.getId().intValue())))
            .andExpect(jsonPath("$.[*].relationType").value(hasItem(DEFAULT_RELATION_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRelationshipTypeShouldNotBeFound(String filter) throws Exception {
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRelationshipType() throws Exception {
        // Get the relationshipType
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();

        // Update the relationshipType
        RelationshipType updatedRelationshipType = relationshipTypeRepository.findById(relationshipType.getId()).get();
        // Disconnect from session so that the updates on updatedRelationshipType are not directly saved in db
        em.detach(updatedRelationshipType);
        updatedRelationshipType
            .relationType(UPDATED_RELATION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(updatedRelationshipType);

        restRelationshipTypeMockMvc.perform(put("/api/relationship-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO)))
            .andExpect(status().isOk());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getRelationType()).isEqualTo(UPDATED_RELATION_TYPE);
        assertThat(testRelationshipType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRelationshipType.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testRelationshipType.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();

        // Create the RelationshipType
        RelationshipTypeDTO relationshipTypeDTO = relationshipTypeMapper.toDto(relationshipType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelationshipTypeMockMvc.perform(put("/api/relationship-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(relationshipTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        int databaseSizeBeforeDelete = relationshipTypeRepository.findAll().size();

        // Delete the relationshipType
        restRelationshipTypeMockMvc.perform(delete("/api/relationship-types/{id}", relationshipType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
