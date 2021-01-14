package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.DisabilityType;
import com.amc.careplanner.repository.DisabilityTypeRepository;
import com.amc.careplanner.service.DisabilityTypeService;
import com.amc.careplanner.service.dto.DisabilityTypeDTO;
import com.amc.careplanner.service.mapper.DisabilityTypeMapper;
import com.amc.careplanner.service.dto.DisabilityTypeCriteria;
import com.amc.careplanner.service.DisabilityTypeQueryService;

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
 * Integration tests for the {@link DisabilityTypeResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DisabilityTypeResourceIT {

    private static final String DEFAULT_DISABILITY = "AAAAAAAAAA";
    private static final String UPDATED_DISABILITY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private DisabilityTypeRepository disabilityTypeRepository;

    @Autowired
    private DisabilityTypeMapper disabilityTypeMapper;

    @Autowired
    private DisabilityTypeService disabilityTypeService;

    @Autowired
    private DisabilityTypeQueryService disabilityTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisabilityTypeMockMvc;

    private DisabilityType disabilityType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisabilityType createEntity(EntityManager em) {
        DisabilityType disabilityType = new DisabilityType()
            .disability(DEFAULT_DISABILITY)
            .description(DEFAULT_DESCRIPTION)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return disabilityType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisabilityType createUpdatedEntity(EntityManager em) {
        DisabilityType disabilityType = new DisabilityType()
            .disability(UPDATED_DISABILITY)
            .description(UPDATED_DESCRIPTION)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return disabilityType;
    }

    @BeforeEach
    public void initTest() {
        disabilityType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisabilityType() throws Exception {
        int databaseSizeBeforeCreate = disabilityTypeRepository.findAll().size();
        // Create the DisabilityType
        DisabilityTypeDTO disabilityTypeDTO = disabilityTypeMapper.toDto(disabilityType);
        restDisabilityTypeMockMvc.perform(post("/api/disability-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DisabilityType in the database
        List<DisabilityType> disabilityTypeList = disabilityTypeRepository.findAll();
        assertThat(disabilityTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DisabilityType testDisabilityType = disabilityTypeList.get(disabilityTypeList.size() - 1);
        assertThat(testDisabilityType.getDisability()).isEqualTo(DEFAULT_DISABILITY);
        assertThat(testDisabilityType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDisabilityType.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createDisabilityTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disabilityTypeRepository.findAll().size();

        // Create the DisabilityType with an existing ID
        disabilityType.setId(1L);
        DisabilityTypeDTO disabilityTypeDTO = disabilityTypeMapper.toDto(disabilityType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisabilityTypeMockMvc.perform(post("/api/disability-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DisabilityType in the database
        List<DisabilityType> disabilityTypeList = disabilityTypeRepository.findAll();
        assertThat(disabilityTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDisabilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = disabilityTypeRepository.findAll().size();
        // set the field null
        disabilityType.setDisability(null);

        // Create the DisabilityType, which fails.
        DisabilityTypeDTO disabilityTypeDTO = disabilityTypeMapper.toDto(disabilityType);


        restDisabilityTypeMockMvc.perform(post("/api/disability-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DisabilityType> disabilityTypeList = disabilityTypeRepository.findAll();
        assertThat(disabilityTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypes() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList
        restDisabilityTypeMockMvc.perform(get("/api/disability-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disabilityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].disability").value(hasItem(DEFAULT_DISABILITY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDisabilityType() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get the disabilityType
        restDisabilityTypeMockMvc.perform(get("/api/disability-types/{id}", disabilityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disabilityType.getId().intValue()))
            .andExpect(jsonPath("$.disability").value(DEFAULT_DISABILITY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getDisabilityTypesByIdFiltering() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        Long id = disabilityType.getId();

        defaultDisabilityTypeShouldBeFound("id.equals=" + id);
        defaultDisabilityTypeShouldNotBeFound("id.notEquals=" + id);

        defaultDisabilityTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDisabilityTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultDisabilityTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDisabilityTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDisabilityTypesByDisabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where disability equals to DEFAULT_DISABILITY
        defaultDisabilityTypeShouldBeFound("disability.equals=" + DEFAULT_DISABILITY);

        // Get all the disabilityTypeList where disability equals to UPDATED_DISABILITY
        defaultDisabilityTypeShouldNotBeFound("disability.equals=" + UPDATED_DISABILITY);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByDisabilityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where disability not equals to DEFAULT_DISABILITY
        defaultDisabilityTypeShouldNotBeFound("disability.notEquals=" + DEFAULT_DISABILITY);

        // Get all the disabilityTypeList where disability not equals to UPDATED_DISABILITY
        defaultDisabilityTypeShouldBeFound("disability.notEquals=" + UPDATED_DISABILITY);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByDisabilityIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where disability in DEFAULT_DISABILITY or UPDATED_DISABILITY
        defaultDisabilityTypeShouldBeFound("disability.in=" + DEFAULT_DISABILITY + "," + UPDATED_DISABILITY);

        // Get all the disabilityTypeList where disability equals to UPDATED_DISABILITY
        defaultDisabilityTypeShouldNotBeFound("disability.in=" + UPDATED_DISABILITY);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByDisabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where disability is not null
        defaultDisabilityTypeShouldBeFound("disability.specified=true");

        // Get all the disabilityTypeList where disability is null
        defaultDisabilityTypeShouldNotBeFound("disability.specified=false");
    }
                @Test
    @Transactional
    public void getAllDisabilityTypesByDisabilityContainsSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where disability contains DEFAULT_DISABILITY
        defaultDisabilityTypeShouldBeFound("disability.contains=" + DEFAULT_DISABILITY);

        // Get all the disabilityTypeList where disability contains UPDATED_DISABILITY
        defaultDisabilityTypeShouldNotBeFound("disability.contains=" + UPDATED_DISABILITY);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByDisabilityNotContainsSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where disability does not contain DEFAULT_DISABILITY
        defaultDisabilityTypeShouldNotBeFound("disability.doesNotContain=" + DEFAULT_DISABILITY);

        // Get all the disabilityTypeList where disability does not contain UPDATED_DISABILITY
        defaultDisabilityTypeShouldBeFound("disability.doesNotContain=" + UPDATED_DISABILITY);
    }


    @Test
    @Transactional
    public void getAllDisabilityTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where description equals to DEFAULT_DESCRIPTION
        defaultDisabilityTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the disabilityTypeList where description equals to UPDATED_DESCRIPTION
        defaultDisabilityTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultDisabilityTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the disabilityTypeList where description not equals to UPDATED_DESCRIPTION
        defaultDisabilityTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDisabilityTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the disabilityTypeList where description equals to UPDATED_DESCRIPTION
        defaultDisabilityTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where description is not null
        defaultDisabilityTypeShouldBeFound("description.specified=true");

        // Get all the disabilityTypeList where description is null
        defaultDisabilityTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllDisabilityTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where description contains DEFAULT_DESCRIPTION
        defaultDisabilityTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the disabilityTypeList where description contains UPDATED_DESCRIPTION
        defaultDisabilityTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultDisabilityTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the disabilityTypeList where description does not contain UPDATED_DESCRIPTION
        defaultDisabilityTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllDisabilityTypesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultDisabilityTypeShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the disabilityTypeList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultDisabilityTypeShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultDisabilityTypeShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the disabilityTypeList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultDisabilityTypeShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultDisabilityTypeShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the disabilityTypeList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultDisabilityTypeShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllDisabilityTypesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        // Get all the disabilityTypeList where hasExtraData is not null
        defaultDisabilityTypeShouldBeFound("hasExtraData.specified=true");

        // Get all the disabilityTypeList where hasExtraData is null
        defaultDisabilityTypeShouldNotBeFound("hasExtraData.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDisabilityTypeShouldBeFound(String filter) throws Exception {
        restDisabilityTypeMockMvc.perform(get("/api/disability-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disabilityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].disability").value(hasItem(DEFAULT_DISABILITY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restDisabilityTypeMockMvc.perform(get("/api/disability-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDisabilityTypeShouldNotBeFound(String filter) throws Exception {
        restDisabilityTypeMockMvc.perform(get("/api/disability-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisabilityTypeMockMvc.perform(get("/api/disability-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDisabilityType() throws Exception {
        // Get the disabilityType
        restDisabilityTypeMockMvc.perform(get("/api/disability-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisabilityType() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        int databaseSizeBeforeUpdate = disabilityTypeRepository.findAll().size();

        // Update the disabilityType
        DisabilityType updatedDisabilityType = disabilityTypeRepository.findById(disabilityType.getId()).get();
        // Disconnect from session so that the updates on updatedDisabilityType are not directly saved in db
        em.detach(updatedDisabilityType);
        updatedDisabilityType
            .disability(UPDATED_DISABILITY)
            .description(UPDATED_DESCRIPTION)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        DisabilityTypeDTO disabilityTypeDTO = disabilityTypeMapper.toDto(updatedDisabilityType);

        restDisabilityTypeMockMvc.perform(put("/api/disability-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DisabilityType in the database
        List<DisabilityType> disabilityTypeList = disabilityTypeRepository.findAll();
        assertThat(disabilityTypeList).hasSize(databaseSizeBeforeUpdate);
        DisabilityType testDisabilityType = disabilityTypeList.get(disabilityTypeList.size() - 1);
        assertThat(testDisabilityType.getDisability()).isEqualTo(UPDATED_DISABILITY);
        assertThat(testDisabilityType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDisabilityType.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingDisabilityType() throws Exception {
        int databaseSizeBeforeUpdate = disabilityTypeRepository.findAll().size();

        // Create the DisabilityType
        DisabilityTypeDTO disabilityTypeDTO = disabilityTypeMapper.toDto(disabilityType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisabilityTypeMockMvc.perform(put("/api/disability-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DisabilityType in the database
        List<DisabilityType> disabilityTypeList = disabilityTypeRepository.findAll();
        assertThat(disabilityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisabilityType() throws Exception {
        // Initialize the database
        disabilityTypeRepository.saveAndFlush(disabilityType);

        int databaseSizeBeforeDelete = disabilityTypeRepository.findAll().size();

        // Delete the disabilityType
        restDisabilityTypeMockMvc.perform(delete("/api/disability-types/{id}", disabilityType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DisabilityType> disabilityTypeList = disabilityTypeRepository.findAll();
        assertThat(disabilityTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
