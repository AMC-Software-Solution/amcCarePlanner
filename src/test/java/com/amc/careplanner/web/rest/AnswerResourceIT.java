package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Answer;
import com.amc.careplanner.domain.Question;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.AnswerRepository;
import com.amc.careplanner.service.AnswerService;
import com.amc.careplanner.service.dto.AnswerDTO;
import com.amc.careplanner.service.mapper.AnswerMapper;
import com.amc.careplanner.service.dto.AnswerCriteria;
import com.amc.careplanner.service.AnswerQueryService;

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
 * Integration tests for the {@link AnswerResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AnswerResourceIT {

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

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
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerQueryService answerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnswerMockMvc;

    private Answer answer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Answer createEntity(EntityManager em) {
        Answer answer = new Answer()
            .answer(DEFAULT_ANSWER)
            .description(DEFAULT_DESCRIPTION)
            .attribute1(DEFAULT_ATTRIBUTE_1)
            .attribute2(DEFAULT_ATTRIBUTE_2)
            .attribute3(DEFAULT_ATTRIBUTE_3)
            .attribute4(DEFAULT_ATTRIBUTE_4)
            .attribute5(DEFAULT_ATTRIBUTE_5)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return answer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Answer createUpdatedEntity(EntityManager em) {
        Answer answer = new Answer()
            .answer(UPDATED_ANSWER)
            .description(UPDATED_DESCRIPTION)
            .attribute1(UPDATED_ATTRIBUTE_1)
            .attribute2(UPDATED_ATTRIBUTE_2)
            .attribute3(UPDATED_ATTRIBUTE_3)
            .attribute4(UPDATED_ATTRIBUTE_4)
            .attribute5(UPDATED_ATTRIBUTE_5)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return answer;
    }

    @BeforeEach
    public void initTest() {
        answer = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnswer() throws Exception {
        int databaseSizeBeforeCreate = answerRepository.findAll().size();
        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);
        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isCreated());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeCreate + 1);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testAnswer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAnswer.getAttribute1()).isEqualTo(DEFAULT_ATTRIBUTE_1);
        assertThat(testAnswer.getAttribute2()).isEqualTo(DEFAULT_ATTRIBUTE_2);
        assertThat(testAnswer.getAttribute3()).isEqualTo(DEFAULT_ATTRIBUTE_3);
        assertThat(testAnswer.getAttribute4()).isEqualTo(DEFAULT_ATTRIBUTE_4);
        assertThat(testAnswer.getAttribute5()).isEqualTo(DEFAULT_ATTRIBUTE_5);
        assertThat(testAnswer.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAnswer.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testAnswer.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testAnswer.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = answerRepository.findAll().size();

        // Create the Answer with an existing ID
        answer.setId(1L);
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = answerRepository.findAll().size();
        // set the field null
        answer.setClientId(null);

        // Create the Answer, which fails.
        AnswerDTO answerDTO = answerMapper.toDto(answer);


        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnswers() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attribute1").value(hasItem(DEFAULT_ATTRIBUTE_1)))
            .andExpect(jsonPath("$.[*].attribute2").value(hasItem(DEFAULT_ATTRIBUTE_2)))
            .andExpect(jsonPath("$.[*].attribute3").value(hasItem(DEFAULT_ATTRIBUTE_3)))
            .andExpect(jsonPath("$.[*].attribute4").value(hasItem(DEFAULT_ATTRIBUTE_4)))
            .andExpect(jsonPath("$.[*].attribute5").value(hasItem(DEFAULT_ATTRIBUTE_5)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get the answer
        restAnswerMockMvc.perform(get("/api/answers/{id}", answer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(answer.getId().intValue()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.attribute1").value(DEFAULT_ATTRIBUTE_1))
            .andExpect(jsonPath("$.attribute2").value(DEFAULT_ATTRIBUTE_2))
            .andExpect(jsonPath("$.attribute3").value(DEFAULT_ATTRIBUTE_3))
            .andExpect(jsonPath("$.attribute4").value(DEFAULT_ATTRIBUTE_4))
            .andExpect(jsonPath("$.attribute5").value(DEFAULT_ATTRIBUTE_5))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getAnswersByIdFiltering() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        Long id = answer.getId();

        defaultAnswerShouldBeFound("id.equals=" + id);
        defaultAnswerShouldNotBeFound("id.notEquals=" + id);

        defaultAnswerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnswerShouldNotBeFound("id.greaterThan=" + id);

        defaultAnswerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnswerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnswersByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer equals to DEFAULT_ANSWER
        defaultAnswerShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the answerList where answer equals to UPDATED_ANSWER
        defaultAnswerShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllAnswersByAnswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer not equals to DEFAULT_ANSWER
        defaultAnswerShouldNotBeFound("answer.notEquals=" + DEFAULT_ANSWER);

        // Get all the answerList where answer not equals to UPDATED_ANSWER
        defaultAnswerShouldBeFound("answer.notEquals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllAnswersByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultAnswerShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the answerList where answer equals to UPDATED_ANSWER
        defaultAnswerShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllAnswersByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer is not null
        defaultAnswerShouldBeFound("answer.specified=true");

        // Get all the answerList where answer is null
        defaultAnswerShouldNotBeFound("answer.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnswersByAnswerContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer contains DEFAULT_ANSWER
        defaultAnswerShouldBeFound("answer.contains=" + DEFAULT_ANSWER);

        // Get all the answerList where answer contains UPDATED_ANSWER
        defaultAnswerShouldNotBeFound("answer.contains=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllAnswersByAnswerNotContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer does not contain DEFAULT_ANSWER
        defaultAnswerShouldNotBeFound("answer.doesNotContain=" + DEFAULT_ANSWER);

        // Get all the answerList where answer does not contain UPDATED_ANSWER
        defaultAnswerShouldBeFound("answer.doesNotContain=" + UPDATED_ANSWER);
    }


    @Test
    @Transactional
    public void getAllAnswersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where description equals to DEFAULT_DESCRIPTION
        defaultAnswerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the answerList where description equals to UPDATED_DESCRIPTION
        defaultAnswerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAnswersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where description not equals to DEFAULT_DESCRIPTION
        defaultAnswerShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the answerList where description not equals to UPDATED_DESCRIPTION
        defaultAnswerShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAnswersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAnswerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the answerList where description equals to UPDATED_DESCRIPTION
        defaultAnswerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAnswersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where description is not null
        defaultAnswerShouldBeFound("description.specified=true");

        // Get all the answerList where description is null
        defaultAnswerShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnswersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where description contains DEFAULT_DESCRIPTION
        defaultAnswerShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the answerList where description contains UPDATED_DESCRIPTION
        defaultAnswerShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAnswersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where description does not contain DEFAULT_DESCRIPTION
        defaultAnswerShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the answerList where description does not contain UPDATED_DESCRIPTION
        defaultAnswerShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAnswersByAttribute1IsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute1 equals to DEFAULT_ATTRIBUTE_1
        defaultAnswerShouldBeFound("attribute1.equals=" + DEFAULT_ATTRIBUTE_1);

        // Get all the answerList where attribute1 equals to UPDATED_ATTRIBUTE_1
        defaultAnswerShouldNotBeFound("attribute1.equals=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute1 not equals to DEFAULT_ATTRIBUTE_1
        defaultAnswerShouldNotBeFound("attribute1.notEquals=" + DEFAULT_ATTRIBUTE_1);

        // Get all the answerList where attribute1 not equals to UPDATED_ATTRIBUTE_1
        defaultAnswerShouldBeFound("attribute1.notEquals=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute1IsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute1 in DEFAULT_ATTRIBUTE_1 or UPDATED_ATTRIBUTE_1
        defaultAnswerShouldBeFound("attribute1.in=" + DEFAULT_ATTRIBUTE_1 + "," + UPDATED_ATTRIBUTE_1);

        // Get all the answerList where attribute1 equals to UPDATED_ATTRIBUTE_1
        defaultAnswerShouldNotBeFound("attribute1.in=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute1IsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute1 is not null
        defaultAnswerShouldBeFound("attribute1.specified=true");

        // Get all the answerList where attribute1 is null
        defaultAnswerShouldNotBeFound("attribute1.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnswersByAttribute1ContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute1 contains DEFAULT_ATTRIBUTE_1
        defaultAnswerShouldBeFound("attribute1.contains=" + DEFAULT_ATTRIBUTE_1);

        // Get all the answerList where attribute1 contains UPDATED_ATTRIBUTE_1
        defaultAnswerShouldNotBeFound("attribute1.contains=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute1NotContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute1 does not contain DEFAULT_ATTRIBUTE_1
        defaultAnswerShouldNotBeFound("attribute1.doesNotContain=" + DEFAULT_ATTRIBUTE_1);

        // Get all the answerList where attribute1 does not contain UPDATED_ATTRIBUTE_1
        defaultAnswerShouldBeFound("attribute1.doesNotContain=" + UPDATED_ATTRIBUTE_1);
    }


    @Test
    @Transactional
    public void getAllAnswersByAttribute2IsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute2 equals to DEFAULT_ATTRIBUTE_2
        defaultAnswerShouldBeFound("attribute2.equals=" + DEFAULT_ATTRIBUTE_2);

        // Get all the answerList where attribute2 equals to UPDATED_ATTRIBUTE_2
        defaultAnswerShouldNotBeFound("attribute2.equals=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute2 not equals to DEFAULT_ATTRIBUTE_2
        defaultAnswerShouldNotBeFound("attribute2.notEquals=" + DEFAULT_ATTRIBUTE_2);

        // Get all the answerList where attribute2 not equals to UPDATED_ATTRIBUTE_2
        defaultAnswerShouldBeFound("attribute2.notEquals=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute2IsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute2 in DEFAULT_ATTRIBUTE_2 or UPDATED_ATTRIBUTE_2
        defaultAnswerShouldBeFound("attribute2.in=" + DEFAULT_ATTRIBUTE_2 + "," + UPDATED_ATTRIBUTE_2);

        // Get all the answerList where attribute2 equals to UPDATED_ATTRIBUTE_2
        defaultAnswerShouldNotBeFound("attribute2.in=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute2IsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute2 is not null
        defaultAnswerShouldBeFound("attribute2.specified=true");

        // Get all the answerList where attribute2 is null
        defaultAnswerShouldNotBeFound("attribute2.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnswersByAttribute2ContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute2 contains DEFAULT_ATTRIBUTE_2
        defaultAnswerShouldBeFound("attribute2.contains=" + DEFAULT_ATTRIBUTE_2);

        // Get all the answerList where attribute2 contains UPDATED_ATTRIBUTE_2
        defaultAnswerShouldNotBeFound("attribute2.contains=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute2NotContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute2 does not contain DEFAULT_ATTRIBUTE_2
        defaultAnswerShouldNotBeFound("attribute2.doesNotContain=" + DEFAULT_ATTRIBUTE_2);

        // Get all the answerList where attribute2 does not contain UPDATED_ATTRIBUTE_2
        defaultAnswerShouldBeFound("attribute2.doesNotContain=" + UPDATED_ATTRIBUTE_2);
    }


    @Test
    @Transactional
    public void getAllAnswersByAttribute3IsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute3 equals to DEFAULT_ATTRIBUTE_3
        defaultAnswerShouldBeFound("attribute3.equals=" + DEFAULT_ATTRIBUTE_3);

        // Get all the answerList where attribute3 equals to UPDATED_ATTRIBUTE_3
        defaultAnswerShouldNotBeFound("attribute3.equals=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute3 not equals to DEFAULT_ATTRIBUTE_3
        defaultAnswerShouldNotBeFound("attribute3.notEquals=" + DEFAULT_ATTRIBUTE_3);

        // Get all the answerList where attribute3 not equals to UPDATED_ATTRIBUTE_3
        defaultAnswerShouldBeFound("attribute3.notEquals=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute3IsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute3 in DEFAULT_ATTRIBUTE_3 or UPDATED_ATTRIBUTE_3
        defaultAnswerShouldBeFound("attribute3.in=" + DEFAULT_ATTRIBUTE_3 + "," + UPDATED_ATTRIBUTE_3);

        // Get all the answerList where attribute3 equals to UPDATED_ATTRIBUTE_3
        defaultAnswerShouldNotBeFound("attribute3.in=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute3IsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute3 is not null
        defaultAnswerShouldBeFound("attribute3.specified=true");

        // Get all the answerList where attribute3 is null
        defaultAnswerShouldNotBeFound("attribute3.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnswersByAttribute3ContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute3 contains DEFAULT_ATTRIBUTE_3
        defaultAnswerShouldBeFound("attribute3.contains=" + DEFAULT_ATTRIBUTE_3);

        // Get all the answerList where attribute3 contains UPDATED_ATTRIBUTE_3
        defaultAnswerShouldNotBeFound("attribute3.contains=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute3NotContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute3 does not contain DEFAULT_ATTRIBUTE_3
        defaultAnswerShouldNotBeFound("attribute3.doesNotContain=" + DEFAULT_ATTRIBUTE_3);

        // Get all the answerList where attribute3 does not contain UPDATED_ATTRIBUTE_3
        defaultAnswerShouldBeFound("attribute3.doesNotContain=" + UPDATED_ATTRIBUTE_3);
    }


    @Test
    @Transactional
    public void getAllAnswersByAttribute4IsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute4 equals to DEFAULT_ATTRIBUTE_4
        defaultAnswerShouldBeFound("attribute4.equals=" + DEFAULT_ATTRIBUTE_4);

        // Get all the answerList where attribute4 equals to UPDATED_ATTRIBUTE_4
        defaultAnswerShouldNotBeFound("attribute4.equals=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute4 not equals to DEFAULT_ATTRIBUTE_4
        defaultAnswerShouldNotBeFound("attribute4.notEquals=" + DEFAULT_ATTRIBUTE_4);

        // Get all the answerList where attribute4 not equals to UPDATED_ATTRIBUTE_4
        defaultAnswerShouldBeFound("attribute4.notEquals=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute4IsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute4 in DEFAULT_ATTRIBUTE_4 or UPDATED_ATTRIBUTE_4
        defaultAnswerShouldBeFound("attribute4.in=" + DEFAULT_ATTRIBUTE_4 + "," + UPDATED_ATTRIBUTE_4);

        // Get all the answerList where attribute4 equals to UPDATED_ATTRIBUTE_4
        defaultAnswerShouldNotBeFound("attribute4.in=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute4IsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute4 is not null
        defaultAnswerShouldBeFound("attribute4.specified=true");

        // Get all the answerList where attribute4 is null
        defaultAnswerShouldNotBeFound("attribute4.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnswersByAttribute4ContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute4 contains DEFAULT_ATTRIBUTE_4
        defaultAnswerShouldBeFound("attribute4.contains=" + DEFAULT_ATTRIBUTE_4);

        // Get all the answerList where attribute4 contains UPDATED_ATTRIBUTE_4
        defaultAnswerShouldNotBeFound("attribute4.contains=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute4NotContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute4 does not contain DEFAULT_ATTRIBUTE_4
        defaultAnswerShouldNotBeFound("attribute4.doesNotContain=" + DEFAULT_ATTRIBUTE_4);

        // Get all the answerList where attribute4 does not contain UPDATED_ATTRIBUTE_4
        defaultAnswerShouldBeFound("attribute4.doesNotContain=" + UPDATED_ATTRIBUTE_4);
    }


    @Test
    @Transactional
    public void getAllAnswersByAttribute5IsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute5 equals to DEFAULT_ATTRIBUTE_5
        defaultAnswerShouldBeFound("attribute5.equals=" + DEFAULT_ATTRIBUTE_5);

        // Get all the answerList where attribute5 equals to UPDATED_ATTRIBUTE_5
        defaultAnswerShouldNotBeFound("attribute5.equals=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute5 not equals to DEFAULT_ATTRIBUTE_5
        defaultAnswerShouldNotBeFound("attribute5.notEquals=" + DEFAULT_ATTRIBUTE_5);

        // Get all the answerList where attribute5 not equals to UPDATED_ATTRIBUTE_5
        defaultAnswerShouldBeFound("attribute5.notEquals=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute5IsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute5 in DEFAULT_ATTRIBUTE_5 or UPDATED_ATTRIBUTE_5
        defaultAnswerShouldBeFound("attribute5.in=" + DEFAULT_ATTRIBUTE_5 + "," + UPDATED_ATTRIBUTE_5);

        // Get all the answerList where attribute5 equals to UPDATED_ATTRIBUTE_5
        defaultAnswerShouldNotBeFound("attribute5.in=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute5IsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute5 is not null
        defaultAnswerShouldBeFound("attribute5.specified=true");

        // Get all the answerList where attribute5 is null
        defaultAnswerShouldNotBeFound("attribute5.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnswersByAttribute5ContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute5 contains DEFAULT_ATTRIBUTE_5
        defaultAnswerShouldBeFound("attribute5.contains=" + DEFAULT_ATTRIBUTE_5);

        // Get all the answerList where attribute5 contains UPDATED_ATTRIBUTE_5
        defaultAnswerShouldNotBeFound("attribute5.contains=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllAnswersByAttribute5NotContainsSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where attribute5 does not contain DEFAULT_ATTRIBUTE_5
        defaultAnswerShouldNotBeFound("attribute5.doesNotContain=" + DEFAULT_ATTRIBUTE_5);

        // Get all the answerList where attribute5 does not contain UPDATED_ATTRIBUTE_5
        defaultAnswerShouldBeFound("attribute5.doesNotContain=" + UPDATED_ATTRIBUTE_5);
    }


    @Test
    @Transactional
    public void getAllAnswersByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where createdDate equals to DEFAULT_CREATED_DATE
        defaultAnswerShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the answerList where createdDate equals to UPDATED_CREATED_DATE
        defaultAnswerShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultAnswerShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the answerList where createdDate not equals to UPDATED_CREATED_DATE
        defaultAnswerShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultAnswerShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the answerList where createdDate equals to UPDATED_CREATED_DATE
        defaultAnswerShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where createdDate is not null
        defaultAnswerShouldBeFound("createdDate.specified=true");

        // Get all the answerList where createdDate is null
        defaultAnswerShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswersByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultAnswerShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the answerList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultAnswerShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultAnswerShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the answerList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultAnswerShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where createdDate is less than DEFAULT_CREATED_DATE
        defaultAnswerShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the answerList where createdDate is less than UPDATED_CREATED_DATE
        defaultAnswerShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultAnswerShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the answerList where createdDate is greater than SMALLER_CREATED_DATE
        defaultAnswerShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllAnswersByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultAnswerShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the answerList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultAnswerShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultAnswerShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the answerList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultAnswerShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultAnswerShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the answerList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultAnswerShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where lastUpdatedDate is not null
        defaultAnswerShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the answerList where lastUpdatedDate is null
        defaultAnswerShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswersByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultAnswerShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the answerList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultAnswerShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultAnswerShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the answerList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultAnswerShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultAnswerShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the answerList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultAnswerShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAnswersByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultAnswerShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the answerList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultAnswerShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllAnswersByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where clientId equals to DEFAULT_CLIENT_ID
        defaultAnswerShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the answerList where clientId equals to UPDATED_CLIENT_ID
        defaultAnswerShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAnswersByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where clientId not equals to DEFAULT_CLIENT_ID
        defaultAnswerShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the answerList where clientId not equals to UPDATED_CLIENT_ID
        defaultAnswerShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAnswersByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultAnswerShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the answerList where clientId equals to UPDATED_CLIENT_ID
        defaultAnswerShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAnswersByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where clientId is not null
        defaultAnswerShouldBeFound("clientId.specified=true");

        // Get all the answerList where clientId is null
        defaultAnswerShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswersByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultAnswerShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the answerList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultAnswerShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAnswersByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultAnswerShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the answerList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultAnswerShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAnswersByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where clientId is less than DEFAULT_CLIENT_ID
        defaultAnswerShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the answerList where clientId is less than UPDATED_CLIENT_ID
        defaultAnswerShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllAnswersByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where clientId is greater than DEFAULT_CLIENT_ID
        defaultAnswerShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the answerList where clientId is greater than SMALLER_CLIENT_ID
        defaultAnswerShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllAnswersByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultAnswerShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the answerList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultAnswerShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllAnswersByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultAnswerShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the answerList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultAnswerShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllAnswersByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultAnswerShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the answerList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultAnswerShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllAnswersByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where hasExtraData is not null
        defaultAnswerShouldBeFound("hasExtraData.specified=true");

        // Get all the answerList where hasExtraData is null
        defaultAnswerShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswersByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);
        Question question = QuestionResourceIT.createEntity(em);
        em.persist(question);
        em.flush();
        answer.setQuestion(question);
        answerRepository.saveAndFlush(answer);
        Long questionId = question.getId();

        // Get all the answerList where question equals to questionId
        defaultAnswerShouldBeFound("questionId.equals=" + questionId);

        // Get all the answerList where question equals to questionId + 1
        defaultAnswerShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }


    @Test
    @Transactional
    public void getAllAnswersByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        answer.setServiceUser(serviceUser);
        answerRepository.saveAndFlush(answer);
        Long serviceUserId = serviceUser.getId();

        // Get all the answerList where serviceUser equals to serviceUserId
        defaultAnswerShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the answerList where serviceUser equals to serviceUserId + 1
        defaultAnswerShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }


    @Test
    @Transactional
    public void getAllAnswersByRecordedByIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);
        Employee recordedBy = EmployeeResourceIT.createEntity(em);
        em.persist(recordedBy);
        em.flush();
        answer.setRecordedBy(recordedBy);
        answerRepository.saveAndFlush(answer);
        Long recordedById = recordedBy.getId();

        // Get all the answerList where recordedBy equals to recordedById
        defaultAnswerShouldBeFound("recordedById.equals=" + recordedById);

        // Get all the answerList where recordedBy equals to recordedById + 1
        defaultAnswerShouldNotBeFound("recordedById.equals=" + (recordedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnswerShouldBeFound(String filter) throws Exception {
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attribute1").value(hasItem(DEFAULT_ATTRIBUTE_1)))
            .andExpect(jsonPath("$.[*].attribute2").value(hasItem(DEFAULT_ATTRIBUTE_2)))
            .andExpect(jsonPath("$.[*].attribute3").value(hasItem(DEFAULT_ATTRIBUTE_3)))
            .andExpect(jsonPath("$.[*].attribute4").value(hasItem(DEFAULT_ATTRIBUTE_4)))
            .andExpect(jsonPath("$.[*].attribute5").value(hasItem(DEFAULT_ATTRIBUTE_5)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restAnswerMockMvc.perform(get("/api/answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnswerShouldNotBeFound(String filter) throws Exception {
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnswerMockMvc.perform(get("/api/answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAnswer() throws Exception {
        // Get the answer
        restAnswerMockMvc.perform(get("/api/answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Update the answer
        Answer updatedAnswer = answerRepository.findById(answer.getId()).get();
        // Disconnect from session so that the updates on updatedAnswer are not directly saved in db
        em.detach(updatedAnswer);
        updatedAnswer
            .answer(UPDATED_ANSWER)
            .description(UPDATED_DESCRIPTION)
            .attribute1(UPDATED_ATTRIBUTE_1)
            .attribute2(UPDATED_ATTRIBUTE_2)
            .attribute3(UPDATED_ATTRIBUTE_3)
            .attribute4(UPDATED_ATTRIBUTE_4)
            .attribute5(UPDATED_ATTRIBUTE_5)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        AnswerDTO answerDTO = answerMapper.toDto(updatedAnswer);

        restAnswerMockMvc.perform(put("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isOk());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testAnswer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAnswer.getAttribute1()).isEqualTo(UPDATED_ATTRIBUTE_1);
        assertThat(testAnswer.getAttribute2()).isEqualTo(UPDATED_ATTRIBUTE_2);
        assertThat(testAnswer.getAttribute3()).isEqualTo(UPDATED_ATTRIBUTE_3);
        assertThat(testAnswer.getAttribute4()).isEqualTo(UPDATED_ATTRIBUTE_4);
        assertThat(testAnswer.getAttribute5()).isEqualTo(UPDATED_ATTRIBUTE_5);
        assertThat(testAnswer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAnswer.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testAnswer.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testAnswer.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingAnswer() throws Exception {
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerMockMvc.perform(put("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeDelete = answerRepository.findAll().size();

        // Delete the answer
        restAnswerMockMvc.perform(delete("/api/answers/{id}", answer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
