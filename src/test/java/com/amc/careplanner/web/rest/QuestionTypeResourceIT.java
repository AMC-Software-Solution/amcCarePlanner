package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.QuestionType;
import com.amc.careplanner.repository.QuestionTypeRepository;
import com.amc.careplanner.service.QuestionTypeService;
import com.amc.careplanner.service.dto.QuestionTypeDTO;
import com.amc.careplanner.service.mapper.QuestionTypeMapper;
import com.amc.careplanner.service.dto.QuestionTypeCriteria;
import com.amc.careplanner.service.QuestionTypeQueryService;

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
 * Integration tests for the {@link QuestionTypeResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuestionTypeResourceIT {

    private static final String DEFAULT_QUESTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_TYPE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Autowired
    private QuestionTypeMapper questionTypeMapper;

    @Autowired
    private QuestionTypeService questionTypeService;

    @Autowired
    private QuestionTypeQueryService questionTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionTypeMockMvc;

    private QuestionType questionType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionType createEntity(EntityManager em) {
        QuestionType questionType = new QuestionType()
            .questionType(DEFAULT_QUESTION_TYPE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE);
        return questionType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionType createUpdatedEntity(EntityManager em) {
        QuestionType questionType = new QuestionType()
            .questionType(UPDATED_QUESTION_TYPE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE);
        return questionType;
    }

    @BeforeEach
    public void initTest() {
        questionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionType() throws Exception {
        int databaseSizeBeforeCreate = questionTypeRepository.findAll().size();
        // Create the QuestionType
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(questionType);
        restQuestionTypeMockMvc.perform(post("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionType in the database
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionType testQuestionType = questionTypeList.get(questionTypeList.size() - 1);
        assertThat(testQuestionType.getQuestionType()).isEqualTo(DEFAULT_QUESTION_TYPE);
        assertThat(testQuestionType.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createQuestionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionTypeRepository.findAll().size();

        // Create the QuestionType with an existing ID
        questionType.setId(1L);
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(questionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionTypeMockMvc.perform(post("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionType in the database
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuestionTypes() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList
        restQuestionTypeMockMvc.perform(get("/api/question-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionType").value(hasItem(DEFAULT_QUESTION_TYPE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getQuestionType() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get the questionType
        restQuestionTypeMockMvc.perform(get("/api/question-types/{id}", questionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionType.getId().intValue()))
            .andExpect(jsonPath("$.questionType").value(DEFAULT_QUESTION_TYPE))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)));
    }


    @Test
    @Transactional
    public void getQuestionTypesByIdFiltering() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        Long id = questionType.getId();

        defaultQuestionTypeShouldBeFound("id.equals=" + id);
        defaultQuestionTypeShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQuestionTypesByQuestionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where questionType equals to DEFAULT_QUESTION_TYPE
        defaultQuestionTypeShouldBeFound("questionType.equals=" + DEFAULT_QUESTION_TYPE);

        // Get all the questionTypeList where questionType equals to UPDATED_QUESTION_TYPE
        defaultQuestionTypeShouldNotBeFound("questionType.equals=" + UPDATED_QUESTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByQuestionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where questionType not equals to DEFAULT_QUESTION_TYPE
        defaultQuestionTypeShouldNotBeFound("questionType.notEquals=" + DEFAULT_QUESTION_TYPE);

        // Get all the questionTypeList where questionType not equals to UPDATED_QUESTION_TYPE
        defaultQuestionTypeShouldBeFound("questionType.notEquals=" + UPDATED_QUESTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByQuestionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where questionType in DEFAULT_QUESTION_TYPE or UPDATED_QUESTION_TYPE
        defaultQuestionTypeShouldBeFound("questionType.in=" + DEFAULT_QUESTION_TYPE + "," + UPDATED_QUESTION_TYPE);

        // Get all the questionTypeList where questionType equals to UPDATED_QUESTION_TYPE
        defaultQuestionTypeShouldNotBeFound("questionType.in=" + UPDATED_QUESTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByQuestionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where questionType is not null
        defaultQuestionTypeShouldBeFound("questionType.specified=true");

        // Get all the questionTypeList where questionType is null
        defaultQuestionTypeShouldNotBeFound("questionType.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionTypesByQuestionTypeContainsSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where questionType contains DEFAULT_QUESTION_TYPE
        defaultQuestionTypeShouldBeFound("questionType.contains=" + DEFAULT_QUESTION_TYPE);

        // Get all the questionTypeList where questionType contains UPDATED_QUESTION_TYPE
        defaultQuestionTypeShouldNotBeFound("questionType.contains=" + UPDATED_QUESTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByQuestionTypeNotContainsSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where questionType does not contain DEFAULT_QUESTION_TYPE
        defaultQuestionTypeShouldNotBeFound("questionType.doesNotContain=" + DEFAULT_QUESTION_TYPE);

        // Get all the questionTypeList where questionType does not contain UPDATED_QUESTION_TYPE
        defaultQuestionTypeShouldBeFound("questionType.doesNotContain=" + UPDATED_QUESTION_TYPE);
    }


    @Test
    @Transactional
    public void getAllQuestionTypesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultQuestionTypeShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionTypeList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultQuestionTypeShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultQuestionTypeShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionTypeList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultQuestionTypeShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultQuestionTypeShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the questionTypeList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultQuestionTypeShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where lastUpdatedDate is not null
        defaultQuestionTypeShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the questionTypeList where lastUpdatedDate is null
        defaultQuestionTypeShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultQuestionTypeShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionTypeList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultQuestionTypeShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultQuestionTypeShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionTypeList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultQuestionTypeShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultQuestionTypeShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionTypeList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultQuestionTypeShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionTypesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultQuestionTypeShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionTypeList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultQuestionTypeShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionTypeShouldBeFound(String filter) throws Exception {
        restQuestionTypeMockMvc.perform(get("/api/question-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionType").value(hasItem(DEFAULT_QUESTION_TYPE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));

        // Check, that the count call also returns 1
        restQuestionTypeMockMvc.perform(get("/api/question-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionTypeShouldNotBeFound(String filter) throws Exception {
        restQuestionTypeMockMvc.perform(get("/api/question-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionTypeMockMvc.perform(get("/api/question-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionType() throws Exception {
        // Get the questionType
        restQuestionTypeMockMvc.perform(get("/api/question-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionType() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        int databaseSizeBeforeUpdate = questionTypeRepository.findAll().size();

        // Update the questionType
        QuestionType updatedQuestionType = questionTypeRepository.findById(questionType.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionType are not directly saved in db
        em.detach(updatedQuestionType);
        updatedQuestionType
            .questionType(UPDATED_QUESTION_TYPE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE);
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(updatedQuestionType);

        restQuestionTypeMockMvc.perform(put("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionType in the database
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeUpdate);
        QuestionType testQuestionType = questionTypeList.get(questionTypeList.size() - 1);
        assertThat(testQuestionType.getQuestionType()).isEqualTo(UPDATED_QUESTION_TYPE);
        assertThat(testQuestionType.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionType() throws Exception {
        int databaseSizeBeforeUpdate = questionTypeRepository.findAll().size();

        // Create the QuestionType
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(questionType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionTypeMockMvc.perform(put("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionType in the database
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionType() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        int databaseSizeBeforeDelete = questionTypeRepository.findAll().size();

        // Delete the questionType
        restQuestionTypeMockMvc.perform(delete("/api/question-types/{id}", questionType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
