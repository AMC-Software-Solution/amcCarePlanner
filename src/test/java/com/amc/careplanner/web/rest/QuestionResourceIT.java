package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Question;
import com.amc.careplanner.repository.QuestionRepository;
import com.amc.careplanner.service.QuestionService;
import com.amc.careplanner.service.dto.QuestionDTO;
import com.amc.careplanner.service.mapper.QuestionMapper;
import com.amc.careplanner.service.dto.QuestionCriteria;
import com.amc.careplanner.service.QuestionQueryService;

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
 * Integration tests for the {@link QuestionResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuestionResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_3 = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_4 = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_5 = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_5 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OPTIONAL = false;
    private static final Boolean UPDATED_OPTIONAL = true;

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionQueryService questionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionMockMvc;

    private Question question;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createEntity(EntityManager em) {
        Question question = new Question()
            .question(DEFAULT_QUESTION)
            .description(DEFAULT_DESCRIPTION)
            .attribute1(DEFAULT_ATTRIBUTE_1)
            .attribute2(DEFAULT_ATTRIBUTE_2)
            .attribute3(DEFAULT_ATTRIBUTE_3)
            .attribute4(DEFAULT_ATTRIBUTE_4)
            .attribute5(DEFAULT_ATTRIBUTE_5)
            .optional(DEFAULT_OPTIONAL)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return question;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createUpdatedEntity(EntityManager em) {
        Question question = new Question()
            .question(UPDATED_QUESTION)
            .description(UPDATED_DESCRIPTION)
            .attribute1(UPDATED_ATTRIBUTE_1)
            .attribute2(UPDATED_ATTRIBUTE_2)
            .attribute3(UPDATED_ATTRIBUTE_3)
            .attribute4(UPDATED_ATTRIBUTE_4)
            .attribute5(UPDATED_ATTRIBUTE_5)
            .optional(UPDATED_OPTIONAL)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return question;
    }

    @BeforeEach
    public void initTest() {
        question = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestion() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();
        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isCreated());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate + 1);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testQuestion.getAttribute1()).isEqualTo(DEFAULT_ATTRIBUTE_1);
        assertThat(testQuestion.getAttribute2()).isEqualTo(DEFAULT_ATTRIBUTE_2);
        assertThat(testQuestion.getAttribute3()).isEqualTo(DEFAULT_ATTRIBUTE_3);
        assertThat(testQuestion.getAttribute4()).isEqualTo(DEFAULT_ATTRIBUTE_4);
        assertThat(testQuestion.getAttribute5()).isEqualTo(DEFAULT_ATTRIBUTE_5);
        assertThat(testQuestion.isOptional()).isEqualTo(DEFAULT_OPTIONAL);
        assertThat(testQuestion.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testQuestion.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // Create the Question with an existing ID
        question.setId(1L);
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionRepository.findAll().size();
        // set the field null
        question.setTenantId(null);

        // Create the Question, which fails.
        QuestionDTO questionDTO = questionMapper.toDto(question);


        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList
        restQuestionMockMvc.perform(get("/api/questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attribute1").value(hasItem(DEFAULT_ATTRIBUTE_1)))
            .andExpect(jsonPath("$.[*].attribute2").value(hasItem(DEFAULT_ATTRIBUTE_2)))
            .andExpect(jsonPath("$.[*].attribute3").value(hasItem(DEFAULT_ATTRIBUTE_3)))
            .andExpect(jsonPath("$.[*].attribute4").value(hasItem(DEFAULT_ATTRIBUTE_4)))
            .andExpect(jsonPath("$.[*].attribute5").value(hasItem(DEFAULT_ATTRIBUTE_5)))
            .andExpect(jsonPath("$.[*].optional").value(hasItem(DEFAULT_OPTIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(question.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.attribute1").value(DEFAULT_ATTRIBUTE_1))
            .andExpect(jsonPath("$.attribute2").value(DEFAULT_ATTRIBUTE_2))
            .andExpect(jsonPath("$.attribute3").value(DEFAULT_ATTRIBUTE_3))
            .andExpect(jsonPath("$.attribute4").value(DEFAULT_ATTRIBUTE_4))
            .andExpect(jsonPath("$.attribute5").value(DEFAULT_ATTRIBUTE_5))
            .andExpect(jsonPath("$.optional").value(DEFAULT_OPTIONAL.booleanValue()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getQuestionsByIdFiltering() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        Long id = question.getId();

        defaultQuestionShouldBeFound("id.equals=" + id);
        defaultQuestionShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQuestionsByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question equals to DEFAULT_QUESTION
        defaultQuestionShouldBeFound("question.equals=" + DEFAULT_QUESTION);

        // Get all the questionList where question equals to UPDATED_QUESTION
        defaultQuestionShouldNotBeFound("question.equals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllQuestionsByQuestionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question not equals to DEFAULT_QUESTION
        defaultQuestionShouldNotBeFound("question.notEquals=" + DEFAULT_QUESTION);

        // Get all the questionList where question not equals to UPDATED_QUESTION
        defaultQuestionShouldBeFound("question.notEquals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllQuestionsByQuestionIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question in DEFAULT_QUESTION or UPDATED_QUESTION
        defaultQuestionShouldBeFound("question.in=" + DEFAULT_QUESTION + "," + UPDATED_QUESTION);

        // Get all the questionList where question equals to UPDATED_QUESTION
        defaultQuestionShouldNotBeFound("question.in=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllQuestionsByQuestionIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question is not null
        defaultQuestionShouldBeFound("question.specified=true");

        // Get all the questionList where question is null
        defaultQuestionShouldNotBeFound("question.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionsByQuestionContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question contains DEFAULT_QUESTION
        defaultQuestionShouldBeFound("question.contains=" + DEFAULT_QUESTION);

        // Get all the questionList where question contains UPDATED_QUESTION
        defaultQuestionShouldNotBeFound("question.contains=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllQuestionsByQuestionNotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question does not contain DEFAULT_QUESTION
        defaultQuestionShouldNotBeFound("question.doesNotContain=" + DEFAULT_QUESTION);

        // Get all the questionList where question does not contain UPDATED_QUESTION
        defaultQuestionShouldBeFound("question.doesNotContain=" + UPDATED_QUESTION);
    }


    @Test
    @Transactional
    public void getAllQuestionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where description equals to DEFAULT_DESCRIPTION
        defaultQuestionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the questionList where description equals to UPDATED_DESCRIPTION
        defaultQuestionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuestionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where description not equals to DEFAULT_DESCRIPTION
        defaultQuestionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the questionList where description not equals to UPDATED_DESCRIPTION
        defaultQuestionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuestionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultQuestionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the questionList where description equals to UPDATED_DESCRIPTION
        defaultQuestionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuestionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where description is not null
        defaultQuestionShouldBeFound("description.specified=true");

        // Get all the questionList where description is null
        defaultQuestionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where description contains DEFAULT_DESCRIPTION
        defaultQuestionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the questionList where description contains UPDATED_DESCRIPTION
        defaultQuestionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuestionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where description does not contain DEFAULT_DESCRIPTION
        defaultQuestionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the questionList where description does not contain UPDATED_DESCRIPTION
        defaultQuestionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllQuestionsByAttribute1IsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute1 equals to DEFAULT_ATTRIBUTE_1
        defaultQuestionShouldBeFound("attribute1.equals=" + DEFAULT_ATTRIBUTE_1);

        // Get all the questionList where attribute1 equals to UPDATED_ATTRIBUTE_1
        defaultQuestionShouldNotBeFound("attribute1.equals=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute1 not equals to DEFAULT_ATTRIBUTE_1
        defaultQuestionShouldNotBeFound("attribute1.notEquals=" + DEFAULT_ATTRIBUTE_1);

        // Get all the questionList where attribute1 not equals to UPDATED_ATTRIBUTE_1
        defaultQuestionShouldBeFound("attribute1.notEquals=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute1IsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute1 in DEFAULT_ATTRIBUTE_1 or UPDATED_ATTRIBUTE_1
        defaultQuestionShouldBeFound("attribute1.in=" + DEFAULT_ATTRIBUTE_1 + "," + UPDATED_ATTRIBUTE_1);

        // Get all the questionList where attribute1 equals to UPDATED_ATTRIBUTE_1
        defaultQuestionShouldNotBeFound("attribute1.in=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute1IsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute1 is not null
        defaultQuestionShouldBeFound("attribute1.specified=true");

        // Get all the questionList where attribute1 is null
        defaultQuestionShouldNotBeFound("attribute1.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionsByAttribute1ContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute1 contains DEFAULT_ATTRIBUTE_1
        defaultQuestionShouldBeFound("attribute1.contains=" + DEFAULT_ATTRIBUTE_1);

        // Get all the questionList where attribute1 contains UPDATED_ATTRIBUTE_1
        defaultQuestionShouldNotBeFound("attribute1.contains=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute1NotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute1 does not contain DEFAULT_ATTRIBUTE_1
        defaultQuestionShouldNotBeFound("attribute1.doesNotContain=" + DEFAULT_ATTRIBUTE_1);

        // Get all the questionList where attribute1 does not contain UPDATED_ATTRIBUTE_1
        defaultQuestionShouldBeFound("attribute1.doesNotContain=" + UPDATED_ATTRIBUTE_1);
    }


    @Test
    @Transactional
    public void getAllQuestionsByAttribute2IsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute2 equals to DEFAULT_ATTRIBUTE_2
        defaultQuestionShouldBeFound("attribute2.equals=" + DEFAULT_ATTRIBUTE_2);

        // Get all the questionList where attribute2 equals to UPDATED_ATTRIBUTE_2
        defaultQuestionShouldNotBeFound("attribute2.equals=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute2 not equals to DEFAULT_ATTRIBUTE_2
        defaultQuestionShouldNotBeFound("attribute2.notEquals=" + DEFAULT_ATTRIBUTE_2);

        // Get all the questionList where attribute2 not equals to UPDATED_ATTRIBUTE_2
        defaultQuestionShouldBeFound("attribute2.notEquals=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute2IsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute2 in DEFAULT_ATTRIBUTE_2 or UPDATED_ATTRIBUTE_2
        defaultQuestionShouldBeFound("attribute2.in=" + DEFAULT_ATTRIBUTE_2 + "," + UPDATED_ATTRIBUTE_2);

        // Get all the questionList where attribute2 equals to UPDATED_ATTRIBUTE_2
        defaultQuestionShouldNotBeFound("attribute2.in=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute2IsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute2 is not null
        defaultQuestionShouldBeFound("attribute2.specified=true");

        // Get all the questionList where attribute2 is null
        defaultQuestionShouldNotBeFound("attribute2.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionsByAttribute2ContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute2 contains DEFAULT_ATTRIBUTE_2
        defaultQuestionShouldBeFound("attribute2.contains=" + DEFAULT_ATTRIBUTE_2);

        // Get all the questionList where attribute2 contains UPDATED_ATTRIBUTE_2
        defaultQuestionShouldNotBeFound("attribute2.contains=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute2NotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute2 does not contain DEFAULT_ATTRIBUTE_2
        defaultQuestionShouldNotBeFound("attribute2.doesNotContain=" + DEFAULT_ATTRIBUTE_2);

        // Get all the questionList where attribute2 does not contain UPDATED_ATTRIBUTE_2
        defaultQuestionShouldBeFound("attribute2.doesNotContain=" + UPDATED_ATTRIBUTE_2);
    }


    @Test
    @Transactional
    public void getAllQuestionsByAttribute3IsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute3 equals to DEFAULT_ATTRIBUTE_3
        defaultQuestionShouldBeFound("attribute3.equals=" + DEFAULT_ATTRIBUTE_3);

        // Get all the questionList where attribute3 equals to UPDATED_ATTRIBUTE_3
        defaultQuestionShouldNotBeFound("attribute3.equals=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute3 not equals to DEFAULT_ATTRIBUTE_3
        defaultQuestionShouldNotBeFound("attribute3.notEquals=" + DEFAULT_ATTRIBUTE_3);

        // Get all the questionList where attribute3 not equals to UPDATED_ATTRIBUTE_3
        defaultQuestionShouldBeFound("attribute3.notEquals=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute3IsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute3 in DEFAULT_ATTRIBUTE_3 or UPDATED_ATTRIBUTE_3
        defaultQuestionShouldBeFound("attribute3.in=" + DEFAULT_ATTRIBUTE_3 + "," + UPDATED_ATTRIBUTE_3);

        // Get all the questionList where attribute3 equals to UPDATED_ATTRIBUTE_3
        defaultQuestionShouldNotBeFound("attribute3.in=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute3IsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute3 is not null
        defaultQuestionShouldBeFound("attribute3.specified=true");

        // Get all the questionList where attribute3 is null
        defaultQuestionShouldNotBeFound("attribute3.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionsByAttribute3ContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute3 contains DEFAULT_ATTRIBUTE_3
        defaultQuestionShouldBeFound("attribute3.contains=" + DEFAULT_ATTRIBUTE_3);

        // Get all the questionList where attribute3 contains UPDATED_ATTRIBUTE_3
        defaultQuestionShouldNotBeFound("attribute3.contains=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute3NotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute3 does not contain DEFAULT_ATTRIBUTE_3
        defaultQuestionShouldNotBeFound("attribute3.doesNotContain=" + DEFAULT_ATTRIBUTE_3);

        // Get all the questionList where attribute3 does not contain UPDATED_ATTRIBUTE_3
        defaultQuestionShouldBeFound("attribute3.doesNotContain=" + UPDATED_ATTRIBUTE_3);
    }


    @Test
    @Transactional
    public void getAllQuestionsByAttribute4IsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute4 equals to DEFAULT_ATTRIBUTE_4
        defaultQuestionShouldBeFound("attribute4.equals=" + DEFAULT_ATTRIBUTE_4);

        // Get all the questionList where attribute4 equals to UPDATED_ATTRIBUTE_4
        defaultQuestionShouldNotBeFound("attribute4.equals=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute4 not equals to DEFAULT_ATTRIBUTE_4
        defaultQuestionShouldNotBeFound("attribute4.notEquals=" + DEFAULT_ATTRIBUTE_4);

        // Get all the questionList where attribute4 not equals to UPDATED_ATTRIBUTE_4
        defaultQuestionShouldBeFound("attribute4.notEquals=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute4IsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute4 in DEFAULT_ATTRIBUTE_4 or UPDATED_ATTRIBUTE_4
        defaultQuestionShouldBeFound("attribute4.in=" + DEFAULT_ATTRIBUTE_4 + "," + UPDATED_ATTRIBUTE_4);

        // Get all the questionList where attribute4 equals to UPDATED_ATTRIBUTE_4
        defaultQuestionShouldNotBeFound("attribute4.in=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute4IsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute4 is not null
        defaultQuestionShouldBeFound("attribute4.specified=true");

        // Get all the questionList where attribute4 is null
        defaultQuestionShouldNotBeFound("attribute4.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionsByAttribute4ContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute4 contains DEFAULT_ATTRIBUTE_4
        defaultQuestionShouldBeFound("attribute4.contains=" + DEFAULT_ATTRIBUTE_4);

        // Get all the questionList where attribute4 contains UPDATED_ATTRIBUTE_4
        defaultQuestionShouldNotBeFound("attribute4.contains=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute4NotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute4 does not contain DEFAULT_ATTRIBUTE_4
        defaultQuestionShouldNotBeFound("attribute4.doesNotContain=" + DEFAULT_ATTRIBUTE_4);

        // Get all the questionList where attribute4 does not contain UPDATED_ATTRIBUTE_4
        defaultQuestionShouldBeFound("attribute4.doesNotContain=" + UPDATED_ATTRIBUTE_4);
    }


    @Test
    @Transactional
    public void getAllQuestionsByAttribute5IsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute5 equals to DEFAULT_ATTRIBUTE_5
        defaultQuestionShouldBeFound("attribute5.equals=" + DEFAULT_ATTRIBUTE_5);

        // Get all the questionList where attribute5 equals to UPDATED_ATTRIBUTE_5
        defaultQuestionShouldNotBeFound("attribute5.equals=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute5 not equals to DEFAULT_ATTRIBUTE_5
        defaultQuestionShouldNotBeFound("attribute5.notEquals=" + DEFAULT_ATTRIBUTE_5);

        // Get all the questionList where attribute5 not equals to UPDATED_ATTRIBUTE_5
        defaultQuestionShouldBeFound("attribute5.notEquals=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute5IsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute5 in DEFAULT_ATTRIBUTE_5 or UPDATED_ATTRIBUTE_5
        defaultQuestionShouldBeFound("attribute5.in=" + DEFAULT_ATTRIBUTE_5 + "," + UPDATED_ATTRIBUTE_5);

        // Get all the questionList where attribute5 equals to UPDATED_ATTRIBUTE_5
        defaultQuestionShouldNotBeFound("attribute5.in=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute5IsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute5 is not null
        defaultQuestionShouldBeFound("attribute5.specified=true");

        // Get all the questionList where attribute5 is null
        defaultQuestionShouldNotBeFound("attribute5.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionsByAttribute5ContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute5 contains DEFAULT_ATTRIBUTE_5
        defaultQuestionShouldBeFound("attribute5.contains=" + DEFAULT_ATTRIBUTE_5);

        // Get all the questionList where attribute5 contains UPDATED_ATTRIBUTE_5
        defaultQuestionShouldNotBeFound("attribute5.contains=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllQuestionsByAttribute5NotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where attribute5 does not contain DEFAULT_ATTRIBUTE_5
        defaultQuestionShouldNotBeFound("attribute5.doesNotContain=" + DEFAULT_ATTRIBUTE_5);

        // Get all the questionList where attribute5 does not contain UPDATED_ATTRIBUTE_5
        defaultQuestionShouldBeFound("attribute5.doesNotContain=" + UPDATED_ATTRIBUTE_5);
    }


    @Test
    @Transactional
    public void getAllQuestionsByOptionalIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where optional equals to DEFAULT_OPTIONAL
        defaultQuestionShouldBeFound("optional.equals=" + DEFAULT_OPTIONAL);

        // Get all the questionList where optional equals to UPDATED_OPTIONAL
        defaultQuestionShouldNotBeFound("optional.equals=" + UPDATED_OPTIONAL);
    }

    @Test
    @Transactional
    public void getAllQuestionsByOptionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where optional not equals to DEFAULT_OPTIONAL
        defaultQuestionShouldNotBeFound("optional.notEquals=" + DEFAULT_OPTIONAL);

        // Get all the questionList where optional not equals to UPDATED_OPTIONAL
        defaultQuestionShouldBeFound("optional.notEquals=" + UPDATED_OPTIONAL);
    }

    @Test
    @Transactional
    public void getAllQuestionsByOptionalIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where optional in DEFAULT_OPTIONAL or UPDATED_OPTIONAL
        defaultQuestionShouldBeFound("optional.in=" + DEFAULT_OPTIONAL + "," + UPDATED_OPTIONAL);

        // Get all the questionList where optional equals to UPDATED_OPTIONAL
        defaultQuestionShouldNotBeFound("optional.in=" + UPDATED_OPTIONAL);
    }

    @Test
    @Transactional
    public void getAllQuestionsByOptionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where optional is not null
        defaultQuestionShouldBeFound("optional.specified=true");

        // Get all the questionList where optional is null
        defaultQuestionShouldNotBeFound("optional.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultQuestionShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultQuestionShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultQuestionShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultQuestionShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultQuestionShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the questionList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultQuestionShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastUpdatedDate is not null
        defaultQuestionShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the questionList where lastUpdatedDate is null
        defaultQuestionShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultQuestionShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultQuestionShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultQuestionShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultQuestionShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultQuestionShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultQuestionShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuestionsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultQuestionShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the questionList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultQuestionShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllQuestionsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where tenantId equals to DEFAULT_TENANT_ID
        defaultQuestionShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the questionList where tenantId equals to UPDATED_TENANT_ID
        defaultQuestionShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllQuestionsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where tenantId not equals to DEFAULT_TENANT_ID
        defaultQuestionShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the questionList where tenantId not equals to UPDATED_TENANT_ID
        defaultQuestionShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllQuestionsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultQuestionShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the questionList where tenantId equals to UPDATED_TENANT_ID
        defaultQuestionShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllQuestionsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where tenantId is not null
        defaultQuestionShouldBeFound("tenantId.specified=true");

        // Get all the questionList where tenantId is null
        defaultQuestionShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionsByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultQuestionShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the questionList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultQuestionShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllQuestionsByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultQuestionShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the questionList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultQuestionShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllQuestionsByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where tenantId is less than DEFAULT_TENANT_ID
        defaultQuestionShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the questionList where tenantId is less than UPDATED_TENANT_ID
        defaultQuestionShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllQuestionsByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where tenantId is greater than DEFAULT_TENANT_ID
        defaultQuestionShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the questionList where tenantId is greater than SMALLER_TENANT_ID
        defaultQuestionShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionShouldBeFound(String filter) throws Exception {
        restQuestionMockMvc.perform(get("/api/questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attribute1").value(hasItem(DEFAULT_ATTRIBUTE_1)))
            .andExpect(jsonPath("$.[*].attribute2").value(hasItem(DEFAULT_ATTRIBUTE_2)))
            .andExpect(jsonPath("$.[*].attribute3").value(hasItem(DEFAULT_ATTRIBUTE_3)))
            .andExpect(jsonPath("$.[*].attribute4").value(hasItem(DEFAULT_ATTRIBUTE_4)))
            .andExpect(jsonPath("$.[*].attribute5").value(hasItem(DEFAULT_ATTRIBUTE_5)))
            .andExpect(jsonPath("$.[*].optional").value(hasItem(DEFAULT_OPTIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restQuestionMockMvc.perform(get("/api/questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionShouldNotBeFound(String filter) throws Exception {
        restQuestionMockMvc.perform(get("/api/questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionMockMvc.perform(get("/api/questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuestion() throws Exception {
        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question
        Question updatedQuestion = questionRepository.findById(question.getId()).get();
        // Disconnect from session so that the updates on updatedQuestion are not directly saved in db
        em.detach(updatedQuestion);
        updatedQuestion
            .question(UPDATED_QUESTION)
            .description(UPDATED_DESCRIPTION)
            .attribute1(UPDATED_ATTRIBUTE_1)
            .attribute2(UPDATED_ATTRIBUTE_2)
            .attribute3(UPDATED_ATTRIBUTE_3)
            .attribute4(UPDATED_ATTRIBUTE_4)
            .attribute5(UPDATED_ATTRIBUTE_5)
            .optional(UPDATED_OPTIONAL)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        QuestionDTO questionDTO = questionMapper.toDto(updatedQuestion);

        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testQuestion.getAttribute1()).isEqualTo(UPDATED_ATTRIBUTE_1);
        assertThat(testQuestion.getAttribute2()).isEqualTo(UPDATED_ATTRIBUTE_2);
        assertThat(testQuestion.getAttribute3()).isEqualTo(UPDATED_ATTRIBUTE_3);
        assertThat(testQuestion.getAttribute4()).isEqualTo(UPDATED_ATTRIBUTE_4);
        assertThat(testQuestion.getAttribute5()).isEqualTo(UPDATED_ATTRIBUTE_5);
        assertThat(testQuestion.isOptional()).isEqualTo(UPDATED_OPTIONAL);
        assertThat(testQuestion.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testQuestion.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeDelete = questionRepository.findAll().size();

        // Delete the question
        restQuestionMockMvc.perform(delete("/api/questions/{id}", question.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
