package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Country;
import com.amc.careplanner.repository.CountryRepository;
import com.amc.careplanner.service.CountryService;
import com.amc.careplanner.service.dto.CountryDTO;
import com.amc.careplanner.service.mapper.CountryMapper;
import com.amc.careplanner.service.dto.CountryCriteria;
import com.amc.careplanner.service.CountryQueryService;

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
 * Integration tests for the {@link CountryResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountryResourceIT {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_ISO_CODE = "AAAAAA";
    private static final String UPDATED_COUNTRY_ISO_CODE = "BBBBBB";

    private static final String DEFAULT_COUNTRY_FLAG_URL = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_FLAG_URL = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CALLING_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CALLING_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNTRY_TEL_DIGIT_LENGTH = 1;
    private static final Integer UPDATED_COUNTRY_TEL_DIGIT_LENGTH = 2;
    private static final Integer SMALLER_COUNTRY_TEL_DIGIT_LENGTH = 1 - 1;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryQueryService countryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryMockMvc;

    private Country country;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createEntity(EntityManager em) {
        Country country = new Country()
            .countryName(DEFAULT_COUNTRY_NAME)
            .countryIsoCode(DEFAULT_COUNTRY_ISO_CODE)
            .countryFlagUrl(DEFAULT_COUNTRY_FLAG_URL)
            .countryCallingCode(DEFAULT_COUNTRY_CALLING_CODE)
            .countryTelDigitLength(DEFAULT_COUNTRY_TEL_DIGIT_LENGTH)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return country;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createUpdatedEntity(EntityManager em) {
        Country country = new Country()
            .countryName(UPDATED_COUNTRY_NAME)
            .countryIsoCode(UPDATED_COUNTRY_ISO_CODE)
            .countryFlagUrl(UPDATED_COUNTRY_FLAG_URL)
            .countryCallingCode(UPDATED_COUNTRY_CALLING_CODE)
            .countryTelDigitLength(UPDATED_COUNTRY_TEL_DIGIT_LENGTH)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return country;
    }

    @BeforeEach
    public void initTest() {
        country = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();
        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountry.getCountryIsoCode()).isEqualTo(DEFAULT_COUNTRY_ISO_CODE);
        assertThat(testCountry.getCountryFlagUrl()).isEqualTo(DEFAULT_COUNTRY_FLAG_URL);
        assertThat(testCountry.getCountryCallingCode()).isEqualTo(DEFAULT_COUNTRY_CALLING_CODE);
        assertThat(testCountry.getCountryTelDigitLength()).isEqualTo(DEFAULT_COUNTRY_TEL_DIGIT_LENGTH);
        assertThat(testCountry.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCountry.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testCountry.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country with an existing ID
        country.setId(1L);
        CountryDTO countryDTO = countryMapper.toDto(country);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCountryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCountryName(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);


        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryIsoCode").value(hasItem(DEFAULT_COUNTRY_ISO_CODE)))
            .andExpect(jsonPath("$.[*].countryFlagUrl").value(hasItem(DEFAULT_COUNTRY_FLAG_URL)))
            .andExpect(jsonPath("$.[*].countryCallingCode").value(hasItem(DEFAULT_COUNTRY_CALLING_CODE)))
            .andExpect(jsonPath("$.[*].countryTelDigitLength").value(hasItem(DEFAULT_COUNTRY_TEL_DIGIT_LENGTH)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.countryIsoCode").value(DEFAULT_COUNTRY_ISO_CODE))
            .andExpect(jsonPath("$.countryFlagUrl").value(DEFAULT_COUNTRY_FLAG_URL))
            .andExpect(jsonPath("$.countryCallingCode").value(DEFAULT_COUNTRY_CALLING_CODE))
            .andExpect(jsonPath("$.countryTelDigitLength").value(DEFAULT_COUNTRY_TEL_DIGIT_LENGTH))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getCountriesByIdFiltering() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        Long id = country.getId();

        defaultCountryShouldBeFound("id.equals=" + id);
        defaultCountryShouldNotBeFound("id.notEquals=" + id);

        defaultCountryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCountryShouldNotBeFound("id.greaterThan=" + id);

        defaultCountryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCountryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCountriesByCountryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName equals to DEFAULT_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.equals=" + DEFAULT_COUNTRY_NAME);

        // Get all the countryList where countryName equals to UPDATED_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.equals=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName not equals to DEFAULT_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.notEquals=" + DEFAULT_COUNTRY_NAME);

        // Get all the countryList where countryName not equals to UPDATED_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.notEquals=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName in DEFAULT_COUNTRY_NAME or UPDATED_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.in=" + DEFAULT_COUNTRY_NAME + "," + UPDATED_COUNTRY_NAME);

        // Get all the countryList where countryName equals to UPDATED_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.in=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName is not null
        defaultCountryShouldBeFound("countryName.specified=true");

        // Get all the countryList where countryName is null
        defaultCountryShouldNotBeFound("countryName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByCountryNameContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName contains DEFAULT_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.contains=" + DEFAULT_COUNTRY_NAME);

        // Get all the countryList where countryName contains UPDATED_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.contains=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName does not contain DEFAULT_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.doesNotContain=" + DEFAULT_COUNTRY_NAME);

        // Get all the countryList where countryName does not contain UPDATED_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.doesNotContain=" + UPDATED_COUNTRY_NAME);
    }


    @Test
    @Transactional
    public void getAllCountriesByCountryIsoCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryIsoCode equals to DEFAULT_COUNTRY_ISO_CODE
        defaultCountryShouldBeFound("countryIsoCode.equals=" + DEFAULT_COUNTRY_ISO_CODE);

        // Get all the countryList where countryIsoCode equals to UPDATED_COUNTRY_ISO_CODE
        defaultCountryShouldNotBeFound("countryIsoCode.equals=" + UPDATED_COUNTRY_ISO_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryIsoCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryIsoCode not equals to DEFAULT_COUNTRY_ISO_CODE
        defaultCountryShouldNotBeFound("countryIsoCode.notEquals=" + DEFAULT_COUNTRY_ISO_CODE);

        // Get all the countryList where countryIsoCode not equals to UPDATED_COUNTRY_ISO_CODE
        defaultCountryShouldBeFound("countryIsoCode.notEquals=" + UPDATED_COUNTRY_ISO_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryIsoCodeIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryIsoCode in DEFAULT_COUNTRY_ISO_CODE or UPDATED_COUNTRY_ISO_CODE
        defaultCountryShouldBeFound("countryIsoCode.in=" + DEFAULT_COUNTRY_ISO_CODE + "," + UPDATED_COUNTRY_ISO_CODE);

        // Get all the countryList where countryIsoCode equals to UPDATED_COUNTRY_ISO_CODE
        defaultCountryShouldNotBeFound("countryIsoCode.in=" + UPDATED_COUNTRY_ISO_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryIsoCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryIsoCode is not null
        defaultCountryShouldBeFound("countryIsoCode.specified=true");

        // Get all the countryList where countryIsoCode is null
        defaultCountryShouldNotBeFound("countryIsoCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByCountryIsoCodeContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryIsoCode contains DEFAULT_COUNTRY_ISO_CODE
        defaultCountryShouldBeFound("countryIsoCode.contains=" + DEFAULT_COUNTRY_ISO_CODE);

        // Get all the countryList where countryIsoCode contains UPDATED_COUNTRY_ISO_CODE
        defaultCountryShouldNotBeFound("countryIsoCode.contains=" + UPDATED_COUNTRY_ISO_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryIsoCodeNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryIsoCode does not contain DEFAULT_COUNTRY_ISO_CODE
        defaultCountryShouldNotBeFound("countryIsoCode.doesNotContain=" + DEFAULT_COUNTRY_ISO_CODE);

        // Get all the countryList where countryIsoCode does not contain UPDATED_COUNTRY_ISO_CODE
        defaultCountryShouldBeFound("countryIsoCode.doesNotContain=" + UPDATED_COUNTRY_ISO_CODE);
    }


    @Test
    @Transactional
    public void getAllCountriesByCountryFlagUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryFlagUrl equals to DEFAULT_COUNTRY_FLAG_URL
        defaultCountryShouldBeFound("countryFlagUrl.equals=" + DEFAULT_COUNTRY_FLAG_URL);

        // Get all the countryList where countryFlagUrl equals to UPDATED_COUNTRY_FLAG_URL
        defaultCountryShouldNotBeFound("countryFlagUrl.equals=" + UPDATED_COUNTRY_FLAG_URL);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryFlagUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryFlagUrl not equals to DEFAULT_COUNTRY_FLAG_URL
        defaultCountryShouldNotBeFound("countryFlagUrl.notEquals=" + DEFAULT_COUNTRY_FLAG_URL);

        // Get all the countryList where countryFlagUrl not equals to UPDATED_COUNTRY_FLAG_URL
        defaultCountryShouldBeFound("countryFlagUrl.notEquals=" + UPDATED_COUNTRY_FLAG_URL);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryFlagUrlIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryFlagUrl in DEFAULT_COUNTRY_FLAG_URL or UPDATED_COUNTRY_FLAG_URL
        defaultCountryShouldBeFound("countryFlagUrl.in=" + DEFAULT_COUNTRY_FLAG_URL + "," + UPDATED_COUNTRY_FLAG_URL);

        // Get all the countryList where countryFlagUrl equals to UPDATED_COUNTRY_FLAG_URL
        defaultCountryShouldNotBeFound("countryFlagUrl.in=" + UPDATED_COUNTRY_FLAG_URL);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryFlagUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryFlagUrl is not null
        defaultCountryShouldBeFound("countryFlagUrl.specified=true");

        // Get all the countryList where countryFlagUrl is null
        defaultCountryShouldNotBeFound("countryFlagUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByCountryFlagUrlContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryFlagUrl contains DEFAULT_COUNTRY_FLAG_URL
        defaultCountryShouldBeFound("countryFlagUrl.contains=" + DEFAULT_COUNTRY_FLAG_URL);

        // Get all the countryList where countryFlagUrl contains UPDATED_COUNTRY_FLAG_URL
        defaultCountryShouldNotBeFound("countryFlagUrl.contains=" + UPDATED_COUNTRY_FLAG_URL);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryFlagUrlNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryFlagUrl does not contain DEFAULT_COUNTRY_FLAG_URL
        defaultCountryShouldNotBeFound("countryFlagUrl.doesNotContain=" + DEFAULT_COUNTRY_FLAG_URL);

        // Get all the countryList where countryFlagUrl does not contain UPDATED_COUNTRY_FLAG_URL
        defaultCountryShouldBeFound("countryFlagUrl.doesNotContain=" + UPDATED_COUNTRY_FLAG_URL);
    }


    @Test
    @Transactional
    public void getAllCountriesByCountryCallingCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCallingCode equals to DEFAULT_COUNTRY_CALLING_CODE
        defaultCountryShouldBeFound("countryCallingCode.equals=" + DEFAULT_COUNTRY_CALLING_CODE);

        // Get all the countryList where countryCallingCode equals to UPDATED_COUNTRY_CALLING_CODE
        defaultCountryShouldNotBeFound("countryCallingCode.equals=" + UPDATED_COUNTRY_CALLING_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryCallingCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCallingCode not equals to DEFAULT_COUNTRY_CALLING_CODE
        defaultCountryShouldNotBeFound("countryCallingCode.notEquals=" + DEFAULT_COUNTRY_CALLING_CODE);

        // Get all the countryList where countryCallingCode not equals to UPDATED_COUNTRY_CALLING_CODE
        defaultCountryShouldBeFound("countryCallingCode.notEquals=" + UPDATED_COUNTRY_CALLING_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryCallingCodeIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCallingCode in DEFAULT_COUNTRY_CALLING_CODE or UPDATED_COUNTRY_CALLING_CODE
        defaultCountryShouldBeFound("countryCallingCode.in=" + DEFAULT_COUNTRY_CALLING_CODE + "," + UPDATED_COUNTRY_CALLING_CODE);

        // Get all the countryList where countryCallingCode equals to UPDATED_COUNTRY_CALLING_CODE
        defaultCountryShouldNotBeFound("countryCallingCode.in=" + UPDATED_COUNTRY_CALLING_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryCallingCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCallingCode is not null
        defaultCountryShouldBeFound("countryCallingCode.specified=true");

        // Get all the countryList where countryCallingCode is null
        defaultCountryShouldNotBeFound("countryCallingCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByCountryCallingCodeContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCallingCode contains DEFAULT_COUNTRY_CALLING_CODE
        defaultCountryShouldBeFound("countryCallingCode.contains=" + DEFAULT_COUNTRY_CALLING_CODE);

        // Get all the countryList where countryCallingCode contains UPDATED_COUNTRY_CALLING_CODE
        defaultCountryShouldNotBeFound("countryCallingCode.contains=" + UPDATED_COUNTRY_CALLING_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryCallingCodeNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCallingCode does not contain DEFAULT_COUNTRY_CALLING_CODE
        defaultCountryShouldNotBeFound("countryCallingCode.doesNotContain=" + DEFAULT_COUNTRY_CALLING_CODE);

        // Get all the countryList where countryCallingCode does not contain UPDATED_COUNTRY_CALLING_CODE
        defaultCountryShouldBeFound("countryCallingCode.doesNotContain=" + UPDATED_COUNTRY_CALLING_CODE);
    }


    @Test
    @Transactional
    public void getAllCountriesByCountryTelDigitLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelDigitLength equals to DEFAULT_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldBeFound("countryTelDigitLength.equals=" + DEFAULT_COUNTRY_TEL_DIGIT_LENGTH);

        // Get all the countryList where countryTelDigitLength equals to UPDATED_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldNotBeFound("countryTelDigitLength.equals=" + UPDATED_COUNTRY_TEL_DIGIT_LENGTH);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTelDigitLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelDigitLength not equals to DEFAULT_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldNotBeFound("countryTelDigitLength.notEquals=" + DEFAULT_COUNTRY_TEL_DIGIT_LENGTH);

        // Get all the countryList where countryTelDigitLength not equals to UPDATED_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldBeFound("countryTelDigitLength.notEquals=" + UPDATED_COUNTRY_TEL_DIGIT_LENGTH);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTelDigitLengthIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelDigitLength in DEFAULT_COUNTRY_TEL_DIGIT_LENGTH or UPDATED_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldBeFound("countryTelDigitLength.in=" + DEFAULT_COUNTRY_TEL_DIGIT_LENGTH + "," + UPDATED_COUNTRY_TEL_DIGIT_LENGTH);

        // Get all the countryList where countryTelDigitLength equals to UPDATED_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldNotBeFound("countryTelDigitLength.in=" + UPDATED_COUNTRY_TEL_DIGIT_LENGTH);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTelDigitLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelDigitLength is not null
        defaultCountryShouldBeFound("countryTelDigitLength.specified=true");

        // Get all the countryList where countryTelDigitLength is null
        defaultCountryShouldNotBeFound("countryTelDigitLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTelDigitLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelDigitLength is greater than or equal to DEFAULT_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldBeFound("countryTelDigitLength.greaterThanOrEqual=" + DEFAULT_COUNTRY_TEL_DIGIT_LENGTH);

        // Get all the countryList where countryTelDigitLength is greater than or equal to UPDATED_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldNotBeFound("countryTelDigitLength.greaterThanOrEqual=" + UPDATED_COUNTRY_TEL_DIGIT_LENGTH);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTelDigitLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelDigitLength is less than or equal to DEFAULT_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldBeFound("countryTelDigitLength.lessThanOrEqual=" + DEFAULT_COUNTRY_TEL_DIGIT_LENGTH);

        // Get all the countryList where countryTelDigitLength is less than or equal to SMALLER_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldNotBeFound("countryTelDigitLength.lessThanOrEqual=" + SMALLER_COUNTRY_TEL_DIGIT_LENGTH);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTelDigitLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelDigitLength is less than DEFAULT_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldNotBeFound("countryTelDigitLength.lessThan=" + DEFAULT_COUNTRY_TEL_DIGIT_LENGTH);

        // Get all the countryList where countryTelDigitLength is less than UPDATED_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldBeFound("countryTelDigitLength.lessThan=" + UPDATED_COUNTRY_TEL_DIGIT_LENGTH);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTelDigitLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelDigitLength is greater than DEFAULT_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldNotBeFound("countryTelDigitLength.greaterThan=" + DEFAULT_COUNTRY_TEL_DIGIT_LENGTH);

        // Get all the countryList where countryTelDigitLength is greater than SMALLER_COUNTRY_TEL_DIGIT_LENGTH
        defaultCountryShouldBeFound("countryTelDigitLength.greaterThan=" + SMALLER_COUNTRY_TEL_DIGIT_LENGTH);
    }


    @Test
    @Transactional
    public void getAllCountriesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createdDate equals to DEFAULT_CREATED_DATE
        defaultCountryShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the countryList where createdDate equals to UPDATED_CREATED_DATE
        defaultCountryShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultCountryShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the countryList where createdDate not equals to UPDATED_CREATED_DATE
        defaultCountryShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultCountryShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the countryList where createdDate equals to UPDATED_CREATED_DATE
        defaultCountryShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createdDate is not null
        defaultCountryShouldBeFound("createdDate.specified=true");

        // Get all the countryList where createdDate is null
        defaultCountryShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultCountryShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the countryList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultCountryShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultCountryShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the countryList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultCountryShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createdDate is less than DEFAULT_CREATED_DATE
        defaultCountryShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the countryList where createdDate is less than UPDATED_CREATED_DATE
        defaultCountryShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultCountryShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the countryList where createdDate is greater than SMALLER_CREATED_DATE
        defaultCountryShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllCountriesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultCountryShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the countryList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCountryShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultCountryShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the countryList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultCountryShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultCountryShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the countryList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCountryShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lastUpdatedDate is not null
        defaultCountryShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the countryList where lastUpdatedDate is null
        defaultCountryShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultCountryShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the countryList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultCountryShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultCountryShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the countryList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultCountryShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultCountryShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the countryList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultCountryShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCountriesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultCountryShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the countryList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultCountryShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllCountriesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultCountryShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the countryList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultCountryShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCountriesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultCountryShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the countryList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultCountryShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCountriesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultCountryShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the countryList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultCountryShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCountriesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where hasExtraData is not null
        defaultCountryShouldBeFound("hasExtraData.specified=true");

        // Get all the countryList where hasExtraData is null
        defaultCountryShouldNotBeFound("hasExtraData.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountryShouldBeFound(String filter) throws Exception {
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryIsoCode").value(hasItem(DEFAULT_COUNTRY_ISO_CODE)))
            .andExpect(jsonPath("$.[*].countryFlagUrl").value(hasItem(DEFAULT_COUNTRY_FLAG_URL)))
            .andExpect(jsonPath("$.[*].countryCallingCode").value(hasItem(DEFAULT_COUNTRY_CALLING_CODE)))
            .andExpect(jsonPath("$.[*].countryTelDigitLength").value(hasItem(DEFAULT_COUNTRY_TEL_DIGIT_LENGTH)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restCountryMockMvc.perform(get("/api/countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountryShouldNotBeFound(String filter) throws Exception {
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountryMockMvc.perform(get("/api/countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getId()).get();
        // Disconnect from session so that the updates on updatedCountry are not directly saved in db
        em.detach(updatedCountry);
        updatedCountry
            .countryName(UPDATED_COUNTRY_NAME)
            .countryIsoCode(UPDATED_COUNTRY_ISO_CODE)
            .countryFlagUrl(UPDATED_COUNTRY_FLAG_URL)
            .countryCallingCode(UPDATED_COUNTRY_CALLING_CODE)
            .countryTelDigitLength(UPDATED_COUNTRY_TEL_DIGIT_LENGTH)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        CountryDTO countryDTO = countryMapper.toDto(updatedCountry);

        restCountryMockMvc.perform(put("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountry.getCountryIsoCode()).isEqualTo(UPDATED_COUNTRY_ISO_CODE);
        assertThat(testCountry.getCountryFlagUrl()).isEqualTo(UPDATED_COUNTRY_FLAG_URL);
        assertThat(testCountry.getCountryCallingCode()).isEqualTo(UPDATED_COUNTRY_CALLING_CODE);
        assertThat(testCountry.getCountryTelDigitLength()).isEqualTo(UPDATED_COUNTRY_TEL_DIGIT_LENGTH);
        assertThat(testCountry.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCountry.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testCountry.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc.perform(put("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Delete the country
        restCountryMockMvc.perform(delete("/api/countries/{id}", country.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
