package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.EligibilityType;
import com.amc.careplanner.repository.EligibilityTypeRepository;
import com.amc.careplanner.service.EligibilityTypeService;
import com.amc.careplanner.service.dto.EligibilityTypeDTO;
import com.amc.careplanner.service.mapper.EligibilityTypeMapper;
import com.amc.careplanner.service.dto.EligibilityTypeCriteria;
import com.amc.careplanner.service.EligibilityTypeQueryService;

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
 * Integration tests for the {@link EligibilityTypeResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EligibilityTypeResourceIT {

    private static final String DEFAULT_ELIGIBILITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ELIGIBILITY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private EligibilityTypeRepository eligibilityTypeRepository;

    @Autowired
    private EligibilityTypeMapper eligibilityTypeMapper;

    @Autowired
    private EligibilityTypeService eligibilityTypeService;

    @Autowired
    private EligibilityTypeQueryService eligibilityTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEligibilityTypeMockMvc;

    private EligibilityType eligibilityType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EligibilityType createEntity(EntityManager em) {
        EligibilityType eligibilityType = new EligibilityType()
            .eligibilityType(DEFAULT_ELIGIBILITY_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return eligibilityType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EligibilityType createUpdatedEntity(EntityManager em) {
        EligibilityType eligibilityType = new EligibilityType()
            .eligibilityType(UPDATED_ELIGIBILITY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return eligibilityType;
    }

    @BeforeEach
    public void initTest() {
        eligibilityType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEligibilityType() throws Exception {
        int databaseSizeBeforeCreate = eligibilityTypeRepository.findAll().size();
        // Create the EligibilityType
        EligibilityTypeDTO eligibilityTypeDTO = eligibilityTypeMapper.toDto(eligibilityType);
        restEligibilityTypeMockMvc.perform(post("/api/eligibility-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the EligibilityType in the database
        List<EligibilityType> eligibilityTypeList = eligibilityTypeRepository.findAll();
        assertThat(eligibilityTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EligibilityType testEligibilityType = eligibilityTypeList.get(eligibilityTypeList.size() - 1);
        assertThat(testEligibilityType.getEligibilityType()).isEqualTo(DEFAULT_ELIGIBILITY_TYPE);
        assertThat(testEligibilityType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEligibilityType.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createEligibilityTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eligibilityTypeRepository.findAll().size();

        // Create the EligibilityType with an existing ID
        eligibilityType.setId(1L);
        EligibilityTypeDTO eligibilityTypeDTO = eligibilityTypeMapper.toDto(eligibilityType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEligibilityTypeMockMvc.perform(post("/api/eligibility-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EligibilityType in the database
        List<EligibilityType> eligibilityTypeList = eligibilityTypeRepository.findAll();
        assertThat(eligibilityTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEligibilityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eligibilityTypeRepository.findAll().size();
        // set the field null
        eligibilityType.setEligibilityType(null);

        // Create the EligibilityType, which fails.
        EligibilityTypeDTO eligibilityTypeDTO = eligibilityTypeMapper.toDto(eligibilityType);


        restEligibilityTypeMockMvc.perform(post("/api/eligibility-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityTypeDTO)))
            .andExpect(status().isBadRequest());

        List<EligibilityType> eligibilityTypeList = eligibilityTypeRepository.findAll();
        assertThat(eligibilityTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypes() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList
        restEligibilityTypeMockMvc.perform(get("/api/eligibility-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibilityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].eligibilityType").value(hasItem(DEFAULT_ELIGIBILITY_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEligibilityType() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get the eligibilityType
        restEligibilityTypeMockMvc.perform(get("/api/eligibility-types/{id}", eligibilityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eligibilityType.getId().intValue()))
            .andExpect(jsonPath("$.eligibilityType").value(DEFAULT_ELIGIBILITY_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getEligibilityTypesByIdFiltering() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        Long id = eligibilityType.getId();

        defaultEligibilityTypeShouldBeFound("id.equals=" + id);
        defaultEligibilityTypeShouldNotBeFound("id.notEquals=" + id);

        defaultEligibilityTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEligibilityTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultEligibilityTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEligibilityTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEligibilityTypesByEligibilityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where eligibilityType equals to DEFAULT_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldBeFound("eligibilityType.equals=" + DEFAULT_ELIGIBILITY_TYPE);

        // Get all the eligibilityTypeList where eligibilityType equals to UPDATED_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldNotBeFound("eligibilityType.equals=" + UPDATED_ELIGIBILITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByEligibilityTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where eligibilityType not equals to DEFAULT_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldNotBeFound("eligibilityType.notEquals=" + DEFAULT_ELIGIBILITY_TYPE);

        // Get all the eligibilityTypeList where eligibilityType not equals to UPDATED_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldBeFound("eligibilityType.notEquals=" + UPDATED_ELIGIBILITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByEligibilityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where eligibilityType in DEFAULT_ELIGIBILITY_TYPE or UPDATED_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldBeFound("eligibilityType.in=" + DEFAULT_ELIGIBILITY_TYPE + "," + UPDATED_ELIGIBILITY_TYPE);

        // Get all the eligibilityTypeList where eligibilityType equals to UPDATED_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldNotBeFound("eligibilityType.in=" + UPDATED_ELIGIBILITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByEligibilityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where eligibilityType is not null
        defaultEligibilityTypeShouldBeFound("eligibilityType.specified=true");

        // Get all the eligibilityTypeList where eligibilityType is null
        defaultEligibilityTypeShouldNotBeFound("eligibilityType.specified=false");
    }
                @Test
    @Transactional
    public void getAllEligibilityTypesByEligibilityTypeContainsSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where eligibilityType contains DEFAULT_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldBeFound("eligibilityType.contains=" + DEFAULT_ELIGIBILITY_TYPE);

        // Get all the eligibilityTypeList where eligibilityType contains UPDATED_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldNotBeFound("eligibilityType.contains=" + UPDATED_ELIGIBILITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByEligibilityTypeNotContainsSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where eligibilityType does not contain DEFAULT_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldNotBeFound("eligibilityType.doesNotContain=" + DEFAULT_ELIGIBILITY_TYPE);

        // Get all the eligibilityTypeList where eligibilityType does not contain UPDATED_ELIGIBILITY_TYPE
        defaultEligibilityTypeShouldBeFound("eligibilityType.doesNotContain=" + UPDATED_ELIGIBILITY_TYPE);
    }


    @Test
    @Transactional
    public void getAllEligibilityTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where description equals to DEFAULT_DESCRIPTION
        defaultEligibilityTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the eligibilityTypeList where description equals to UPDATED_DESCRIPTION
        defaultEligibilityTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultEligibilityTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the eligibilityTypeList where description not equals to UPDATED_DESCRIPTION
        defaultEligibilityTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEligibilityTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the eligibilityTypeList where description equals to UPDATED_DESCRIPTION
        defaultEligibilityTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where description is not null
        defaultEligibilityTypeShouldBeFound("description.specified=true");

        // Get all the eligibilityTypeList where description is null
        defaultEligibilityTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllEligibilityTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where description contains DEFAULT_DESCRIPTION
        defaultEligibilityTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the eligibilityTypeList where description contains UPDATED_DESCRIPTION
        defaultEligibilityTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultEligibilityTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the eligibilityTypeList where description does not contain UPDATED_DESCRIPTION
        defaultEligibilityTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllEligibilityTypesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultEligibilityTypeShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the eligibilityTypeList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEligibilityTypeShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultEligibilityTypeShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the eligibilityTypeList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultEligibilityTypeShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultEligibilityTypeShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the eligibilityTypeList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultEligibilityTypeShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllEligibilityTypesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        // Get all the eligibilityTypeList where hasExtraData is not null
        defaultEligibilityTypeShouldBeFound("hasExtraData.specified=true");

        // Get all the eligibilityTypeList where hasExtraData is null
        defaultEligibilityTypeShouldNotBeFound("hasExtraData.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEligibilityTypeShouldBeFound(String filter) throws Exception {
        restEligibilityTypeMockMvc.perform(get("/api/eligibility-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibilityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].eligibilityType").value(hasItem(DEFAULT_ELIGIBILITY_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restEligibilityTypeMockMvc.perform(get("/api/eligibility-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEligibilityTypeShouldNotBeFound(String filter) throws Exception {
        restEligibilityTypeMockMvc.perform(get("/api/eligibility-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEligibilityTypeMockMvc.perform(get("/api/eligibility-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEligibilityType() throws Exception {
        // Get the eligibilityType
        restEligibilityTypeMockMvc.perform(get("/api/eligibility-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEligibilityType() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        int databaseSizeBeforeUpdate = eligibilityTypeRepository.findAll().size();

        // Update the eligibilityType
        EligibilityType updatedEligibilityType = eligibilityTypeRepository.findById(eligibilityType.getId()).get();
        // Disconnect from session so that the updates on updatedEligibilityType are not directly saved in db
        em.detach(updatedEligibilityType);
        updatedEligibilityType
            .eligibilityType(UPDATED_ELIGIBILITY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        EligibilityTypeDTO eligibilityTypeDTO = eligibilityTypeMapper.toDto(updatedEligibilityType);

        restEligibilityTypeMockMvc.perform(put("/api/eligibility-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityTypeDTO)))
            .andExpect(status().isOk());

        // Validate the EligibilityType in the database
        List<EligibilityType> eligibilityTypeList = eligibilityTypeRepository.findAll();
        assertThat(eligibilityTypeList).hasSize(databaseSizeBeforeUpdate);
        EligibilityType testEligibilityType = eligibilityTypeList.get(eligibilityTypeList.size() - 1);
        assertThat(testEligibilityType.getEligibilityType()).isEqualTo(UPDATED_ELIGIBILITY_TYPE);
        assertThat(testEligibilityType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEligibilityType.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingEligibilityType() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityTypeRepository.findAll().size();

        // Create the EligibilityType
        EligibilityTypeDTO eligibilityTypeDTO = eligibilityTypeMapper.toDto(eligibilityType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibilityTypeMockMvc.perform(put("/api/eligibility-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eligibilityTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EligibilityType in the database
        List<EligibilityType> eligibilityTypeList = eligibilityTypeRepository.findAll();
        assertThat(eligibilityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEligibilityType() throws Exception {
        // Initialize the database
        eligibilityTypeRepository.saveAndFlush(eligibilityType);

        int databaseSizeBeforeDelete = eligibilityTypeRepository.findAll().size();

        // Delete the eligibilityType
        restEligibilityTypeMockMvc.perform(delete("/api/eligibility-types/{id}", eligibilityType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EligibilityType> eligibilityTypeList = eligibilityTypeRepository.findAll();
        assertThat(eligibilityTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
