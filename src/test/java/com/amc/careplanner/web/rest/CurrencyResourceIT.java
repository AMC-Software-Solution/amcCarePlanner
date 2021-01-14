package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Currency;
import com.amc.careplanner.repository.CurrencyRepository;
import com.amc.careplanner.service.CurrencyService;
import com.amc.careplanner.service.dto.CurrencyDTO;
import com.amc.careplanner.service.mapper.CurrencyMapper;
import com.amc.careplanner.service.dto.CurrencyCriteria;
import com.amc.careplanner.service.CurrencyQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CurrencyResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CurrencyResourceIT {

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_ISO_CODE = "AAA";
    private static final String UPDATED_CURRENCY_ISO_CODE = "BBB";

    private static final String DEFAULT_CURRENCY_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_SYMBOL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CURRENCY_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CURRENCY_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CURRENCY_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CURRENCY_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CURRENCY_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_LOGO_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyQueryService currencyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrencyMockMvc;

    private Currency currency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createEntity(EntityManager em) {
        Currency currency = new Currency()
            .currency(DEFAULT_CURRENCY)
            .currencyIsoCode(DEFAULT_CURRENCY_ISO_CODE)
            .currencySymbol(DEFAULT_CURRENCY_SYMBOL)
            .currencyLogo(DEFAULT_CURRENCY_LOGO)
            .currencyLogoContentType(DEFAULT_CURRENCY_LOGO_CONTENT_TYPE)
            .currencyLogoUrl(DEFAULT_CURRENCY_LOGO_URL)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return currency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createUpdatedEntity(EntityManager em) {
        Currency currency = new Currency()
            .currency(UPDATED_CURRENCY)
            .currencyIsoCode(UPDATED_CURRENCY_ISO_CODE)
            .currencySymbol(UPDATED_CURRENCY_SYMBOL)
            .currencyLogo(UPDATED_CURRENCY_LOGO)
            .currencyLogoContentType(UPDATED_CURRENCY_LOGO_CONTENT_TYPE)
            .currencyLogoUrl(UPDATED_CURRENCY_LOGO_URL)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return currency;
    }

    @BeforeEach
    public void initTest() {
        currency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrency() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();
        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate + 1);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testCurrency.getCurrencyIsoCode()).isEqualTo(DEFAULT_CURRENCY_ISO_CODE);
        assertThat(testCurrency.getCurrencySymbol()).isEqualTo(DEFAULT_CURRENCY_SYMBOL);
        assertThat(testCurrency.getCurrencyLogo()).isEqualTo(DEFAULT_CURRENCY_LOGO);
        assertThat(testCurrency.getCurrencyLogoContentType()).isEqualTo(DEFAULT_CURRENCY_LOGO_CONTENT_TYPE);
        assertThat(testCurrency.getCurrencyLogoUrl()).isEqualTo(DEFAULT_CURRENCY_LOGO_URL);
        assertThat(testCurrency.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency with an existing ID
        currency.setId(1L);
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setCurrency(null);

        // Create the Currency, which fails.
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);


        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencies() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].currencyIsoCode").value(hasItem(DEFAULT_CURRENCY_ISO_CODE)))
            .andExpect(jsonPath("$.[*].currencySymbol").value(hasItem(DEFAULT_CURRENCY_SYMBOL)))
            .andExpect(jsonPath("$.[*].currencyLogoContentType").value(hasItem(DEFAULT_CURRENCY_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].currencyLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CURRENCY_LOGO))))
            .andExpect(jsonPath("$.[*].currencyLogoUrl").value(hasItem(DEFAULT_CURRENCY_LOGO_URL)))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currency.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.currencyIsoCode").value(DEFAULT_CURRENCY_ISO_CODE))
            .andExpect(jsonPath("$.currencySymbol").value(DEFAULT_CURRENCY_SYMBOL))
            .andExpect(jsonPath("$.currencyLogoContentType").value(DEFAULT_CURRENCY_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.currencyLogo").value(Base64Utils.encodeToString(DEFAULT_CURRENCY_LOGO)))
            .andExpect(jsonPath("$.currencyLogoUrl").value(DEFAULT_CURRENCY_LOGO_URL))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getCurrenciesByIdFiltering() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        Long id = currency.getId();

        defaultCurrencyShouldBeFound("id.equals=" + id);
        defaultCurrencyShouldNotBeFound("id.notEquals=" + id);

        defaultCurrencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.greaterThan=" + id);

        defaultCurrencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currency equals to DEFAULT_CURRENCY
        defaultCurrencyShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the currencyList where currency equals to UPDATED_CURRENCY
        defaultCurrencyShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currency not equals to DEFAULT_CURRENCY
        defaultCurrencyShouldNotBeFound("currency.notEquals=" + DEFAULT_CURRENCY);

        // Get all the currencyList where currency not equals to UPDATED_CURRENCY
        defaultCurrencyShouldBeFound("currency.notEquals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultCurrencyShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the currencyList where currency equals to UPDATED_CURRENCY
        defaultCurrencyShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currency is not null
        defaultCurrencyShouldBeFound("currency.specified=true");

        // Get all the currencyList where currency is null
        defaultCurrencyShouldNotBeFound("currency.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByCurrencyContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currency contains DEFAULT_CURRENCY
        defaultCurrencyShouldBeFound("currency.contains=" + DEFAULT_CURRENCY);

        // Get all the currencyList where currency contains UPDATED_CURRENCY
        defaultCurrencyShouldNotBeFound("currency.contains=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currency does not contain DEFAULT_CURRENCY
        defaultCurrencyShouldNotBeFound("currency.doesNotContain=" + DEFAULT_CURRENCY);

        // Get all the currencyList where currency does not contain UPDATED_CURRENCY
        defaultCurrencyShouldBeFound("currency.doesNotContain=" + UPDATED_CURRENCY);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsoCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyIsoCode equals to DEFAULT_CURRENCY_ISO_CODE
        defaultCurrencyShouldBeFound("currencyIsoCode.equals=" + DEFAULT_CURRENCY_ISO_CODE);

        // Get all the currencyList where currencyIsoCode equals to UPDATED_CURRENCY_ISO_CODE
        defaultCurrencyShouldNotBeFound("currencyIsoCode.equals=" + UPDATED_CURRENCY_ISO_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsoCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyIsoCode not equals to DEFAULT_CURRENCY_ISO_CODE
        defaultCurrencyShouldNotBeFound("currencyIsoCode.notEquals=" + DEFAULT_CURRENCY_ISO_CODE);

        // Get all the currencyList where currencyIsoCode not equals to UPDATED_CURRENCY_ISO_CODE
        defaultCurrencyShouldBeFound("currencyIsoCode.notEquals=" + UPDATED_CURRENCY_ISO_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsoCodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyIsoCode in DEFAULT_CURRENCY_ISO_CODE or UPDATED_CURRENCY_ISO_CODE
        defaultCurrencyShouldBeFound("currencyIsoCode.in=" + DEFAULT_CURRENCY_ISO_CODE + "," + UPDATED_CURRENCY_ISO_CODE);

        // Get all the currencyList where currencyIsoCode equals to UPDATED_CURRENCY_ISO_CODE
        defaultCurrencyShouldNotBeFound("currencyIsoCode.in=" + UPDATED_CURRENCY_ISO_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsoCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyIsoCode is not null
        defaultCurrencyShouldBeFound("currencyIsoCode.specified=true");

        // Get all the currencyList where currencyIsoCode is null
        defaultCurrencyShouldNotBeFound("currencyIsoCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsoCodeContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyIsoCode contains DEFAULT_CURRENCY_ISO_CODE
        defaultCurrencyShouldBeFound("currencyIsoCode.contains=" + DEFAULT_CURRENCY_ISO_CODE);

        // Get all the currencyList where currencyIsoCode contains UPDATED_CURRENCY_ISO_CODE
        defaultCurrencyShouldNotBeFound("currencyIsoCode.contains=" + UPDATED_CURRENCY_ISO_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyIsoCodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyIsoCode does not contain DEFAULT_CURRENCY_ISO_CODE
        defaultCurrencyShouldNotBeFound("currencyIsoCode.doesNotContain=" + DEFAULT_CURRENCY_ISO_CODE);

        // Get all the currencyList where currencyIsoCode does not contain UPDATED_CURRENCY_ISO_CODE
        defaultCurrencyShouldBeFound("currencyIsoCode.doesNotContain=" + UPDATED_CURRENCY_ISO_CODE);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySymbolIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySymbol equals to DEFAULT_CURRENCY_SYMBOL
        defaultCurrencyShouldBeFound("currencySymbol.equals=" + DEFAULT_CURRENCY_SYMBOL);

        // Get all the currencyList where currencySymbol equals to UPDATED_CURRENCY_SYMBOL
        defaultCurrencyShouldNotBeFound("currencySymbol.equals=" + UPDATED_CURRENCY_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySymbolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySymbol not equals to DEFAULT_CURRENCY_SYMBOL
        defaultCurrencyShouldNotBeFound("currencySymbol.notEquals=" + DEFAULT_CURRENCY_SYMBOL);

        // Get all the currencyList where currencySymbol not equals to UPDATED_CURRENCY_SYMBOL
        defaultCurrencyShouldBeFound("currencySymbol.notEquals=" + UPDATED_CURRENCY_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySymbolIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySymbol in DEFAULT_CURRENCY_SYMBOL or UPDATED_CURRENCY_SYMBOL
        defaultCurrencyShouldBeFound("currencySymbol.in=" + DEFAULT_CURRENCY_SYMBOL + "," + UPDATED_CURRENCY_SYMBOL);

        // Get all the currencyList where currencySymbol equals to UPDATED_CURRENCY_SYMBOL
        defaultCurrencyShouldNotBeFound("currencySymbol.in=" + UPDATED_CURRENCY_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySymbolIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySymbol is not null
        defaultCurrencyShouldBeFound("currencySymbol.specified=true");

        // Get all the currencyList where currencySymbol is null
        defaultCurrencyShouldNotBeFound("currencySymbol.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByCurrencySymbolContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySymbol contains DEFAULT_CURRENCY_SYMBOL
        defaultCurrencyShouldBeFound("currencySymbol.contains=" + DEFAULT_CURRENCY_SYMBOL);

        // Get all the currencyList where currencySymbol contains UPDATED_CURRENCY_SYMBOL
        defaultCurrencyShouldNotBeFound("currencySymbol.contains=" + UPDATED_CURRENCY_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySymbolNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySymbol does not contain DEFAULT_CURRENCY_SYMBOL
        defaultCurrencyShouldNotBeFound("currencySymbol.doesNotContain=" + DEFAULT_CURRENCY_SYMBOL);

        // Get all the currencyList where currencySymbol does not contain UPDATED_CURRENCY_SYMBOL
        defaultCurrencyShouldBeFound("currencySymbol.doesNotContain=" + UPDATED_CURRENCY_SYMBOL);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyLogoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyLogoUrl equals to DEFAULT_CURRENCY_LOGO_URL
        defaultCurrencyShouldBeFound("currencyLogoUrl.equals=" + DEFAULT_CURRENCY_LOGO_URL);

        // Get all the currencyList where currencyLogoUrl equals to UPDATED_CURRENCY_LOGO_URL
        defaultCurrencyShouldNotBeFound("currencyLogoUrl.equals=" + UPDATED_CURRENCY_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyLogoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyLogoUrl not equals to DEFAULT_CURRENCY_LOGO_URL
        defaultCurrencyShouldNotBeFound("currencyLogoUrl.notEquals=" + DEFAULT_CURRENCY_LOGO_URL);

        // Get all the currencyList where currencyLogoUrl not equals to UPDATED_CURRENCY_LOGO_URL
        defaultCurrencyShouldBeFound("currencyLogoUrl.notEquals=" + UPDATED_CURRENCY_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyLogoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyLogoUrl in DEFAULT_CURRENCY_LOGO_URL or UPDATED_CURRENCY_LOGO_URL
        defaultCurrencyShouldBeFound("currencyLogoUrl.in=" + DEFAULT_CURRENCY_LOGO_URL + "," + UPDATED_CURRENCY_LOGO_URL);

        // Get all the currencyList where currencyLogoUrl equals to UPDATED_CURRENCY_LOGO_URL
        defaultCurrencyShouldNotBeFound("currencyLogoUrl.in=" + UPDATED_CURRENCY_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyLogoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyLogoUrl is not null
        defaultCurrencyShouldBeFound("currencyLogoUrl.specified=true");

        // Get all the currencyList where currencyLogoUrl is null
        defaultCurrencyShouldNotBeFound("currencyLogoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByCurrencyLogoUrlContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyLogoUrl contains DEFAULT_CURRENCY_LOGO_URL
        defaultCurrencyShouldBeFound("currencyLogoUrl.contains=" + DEFAULT_CURRENCY_LOGO_URL);

        // Get all the currencyList where currencyLogoUrl contains UPDATED_CURRENCY_LOGO_URL
        defaultCurrencyShouldNotBeFound("currencyLogoUrl.contains=" + UPDATED_CURRENCY_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyLogoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyLogoUrl does not contain DEFAULT_CURRENCY_LOGO_URL
        defaultCurrencyShouldNotBeFound("currencyLogoUrl.doesNotContain=" + DEFAULT_CURRENCY_LOGO_URL);

        // Get all the currencyList where currencyLogoUrl does not contain UPDATED_CURRENCY_LOGO_URL
        defaultCurrencyShouldBeFound("currencyLogoUrl.doesNotContain=" + UPDATED_CURRENCY_LOGO_URL);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultCurrencyShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the currencyList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultCurrencyShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultCurrencyShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the currencyList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultCurrencyShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultCurrencyShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the currencyList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultCurrencyShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where hasExtraData is not null
        defaultCurrencyShouldBeFound("hasExtraData.specified=true");

        // Get all the currencyList where hasExtraData is null
        defaultCurrencyShouldNotBeFound("hasExtraData.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyShouldBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].currencyIsoCode").value(hasItem(DEFAULT_CURRENCY_ISO_CODE)))
            .andExpect(jsonPath("$.[*].currencySymbol").value(hasItem(DEFAULT_CURRENCY_SYMBOL)))
            .andExpect(jsonPath("$.[*].currencyLogoContentType").value(hasItem(DEFAULT_CURRENCY_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].currencyLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CURRENCY_LOGO))))
            .andExpect(jsonPath("$.[*].currencyLogoUrl").value(hasItem(DEFAULT_CURRENCY_LOGO_URL)))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyShouldNotBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCurrency() throws Exception {
        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency
        Currency updatedCurrency = currencyRepository.findById(currency.getId()).get();
        // Disconnect from session so that the updates on updatedCurrency are not directly saved in db
        em.detach(updatedCurrency);
        updatedCurrency
            .currency(UPDATED_CURRENCY)
            .currencyIsoCode(UPDATED_CURRENCY_ISO_CODE)
            .currencySymbol(UPDATED_CURRENCY_SYMBOL)
            .currencyLogo(UPDATED_CURRENCY_LOGO)
            .currencyLogoContentType(UPDATED_CURRENCY_LOGO_CONTENT_TYPE)
            .currencyLogoUrl(UPDATED_CURRENCY_LOGO_URL)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        CurrencyDTO currencyDTO = currencyMapper.toDto(updatedCurrency);

        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testCurrency.getCurrencyIsoCode()).isEqualTo(UPDATED_CURRENCY_ISO_CODE);
        assertThat(testCurrency.getCurrencySymbol()).isEqualTo(UPDATED_CURRENCY_SYMBOL);
        assertThat(testCurrency.getCurrencyLogo()).isEqualTo(UPDATED_CURRENCY_LOGO);
        assertThat(testCurrency.getCurrencyLogoContentType()).isEqualTo(UPDATED_CURRENCY_LOGO_CONTENT_TYPE);
        assertThat(testCurrency.getCurrencyLogoUrl()).isEqualTo(UPDATED_CURRENCY_LOGO_URL);
        assertThat(testCurrency.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeDelete = currencyRepository.findAll().size();

        // Delete the currency
        restCurrencyMockMvc.perform(delete("/api/currencies/{id}", currency.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
