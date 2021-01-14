package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Client;
import com.amc.careplanner.repository.ClientRepository;
import com.amc.careplanner.service.ClientService;
import com.amc.careplanner.service.dto.ClientDTO;
import com.amc.careplanner.service.mapper.ClientMapper;
import com.amc.careplanner.service.dto.ClientCriteria;
import com.amc.careplanner.service.ClientQueryService;

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
 * Integration tests for the {@link ClientResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientResourceIT {

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CLIENT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CLIENT_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CLIENT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CLIENT_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CLIENT_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_LOGO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientQueryService clientQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .clientName(DEFAULT_CLIENT_NAME)
            .clientDescription(DEFAULT_CLIENT_DESCRIPTION)
            .clientLogo(DEFAULT_CLIENT_LOGO)
            .clientLogoContentType(DEFAULT_CLIENT_LOGO_CONTENT_TYPE)
            .clientLogoUrl(DEFAULT_CLIENT_LOGO_URL)
            .clientContactName(DEFAULT_CLIENT_CONTACT_NAME)
            .clientPhone(DEFAULT_CLIENT_PHONE)
            .clientEmail(DEFAULT_CLIENT_EMAIL)
            .createdDate(DEFAULT_CREATED_DATE)
            .enabled(DEFAULT_ENABLED)
            .reason(DEFAULT_REASON)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .clientName(UPDATED_CLIENT_NAME)
            .clientDescription(UPDATED_CLIENT_DESCRIPTION)
            .clientLogo(UPDATED_CLIENT_LOGO)
            .clientLogoContentType(UPDATED_CLIENT_LOGO_CONTENT_TYPE)
            .clientLogoUrl(UPDATED_CLIENT_LOGO_URL)
            .clientContactName(UPDATED_CLIENT_CONTACT_NAME)
            .clientPhone(UPDATED_CLIENT_PHONE)
            .clientEmail(UPDATED_CLIENT_EMAIL)
            .createdDate(UPDATED_CREATED_DATE)
            .enabled(UPDATED_ENABLED)
            .reason(UPDATED_REASON)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testClient.getClientDescription()).isEqualTo(DEFAULT_CLIENT_DESCRIPTION);
        assertThat(testClient.getClientLogo()).isEqualTo(DEFAULT_CLIENT_LOGO);
        assertThat(testClient.getClientLogoContentType()).isEqualTo(DEFAULT_CLIENT_LOGO_CONTENT_TYPE);
        assertThat(testClient.getClientLogoUrl()).isEqualTo(DEFAULT_CLIENT_LOGO_URL);
        assertThat(testClient.getClientContactName()).isEqualTo(DEFAULT_CLIENT_CONTACT_NAME);
        assertThat(testClient.getClientPhone()).isEqualTo(DEFAULT_CLIENT_PHONE);
        assertThat(testClient.getClientEmail()).isEqualTo(DEFAULT_CLIENT_EMAIL);
        assertThat(testClient.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClient.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testClient.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testClient.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testClient.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);
        ClientDTO clientDTO = clientMapper.toDto(client);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setClientName(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].clientDescription").value(hasItem(DEFAULT_CLIENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].clientLogoContentType").value(hasItem(DEFAULT_CLIENT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].clientLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CLIENT_LOGO))))
            .andExpect(jsonPath("$.[*].clientLogoUrl").value(hasItem(DEFAULT_CLIENT_LOGO_URL)))
            .andExpect(jsonPath("$.[*].clientContactName").value(hasItem(DEFAULT_CLIENT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].clientPhone").value(hasItem(DEFAULT_CLIENT_PHONE)))
            .andExpect(jsonPath("$.[*].clientEmail").value(hasItem(DEFAULT_CLIENT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME))
            .andExpect(jsonPath("$.clientDescription").value(DEFAULT_CLIENT_DESCRIPTION))
            .andExpect(jsonPath("$.clientLogoContentType").value(DEFAULT_CLIENT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.clientLogo").value(Base64Utils.encodeToString(DEFAULT_CLIENT_LOGO)))
            .andExpect(jsonPath("$.clientLogoUrl").value(DEFAULT_CLIENT_LOGO_URL))
            .andExpect(jsonPath("$.clientContactName").value(DEFAULT_CLIENT_CONTACT_NAME))
            .andExpect(jsonPath("$.clientPhone").value(DEFAULT_CLIENT_PHONE))
            .andExpect(jsonPath("$.clientEmail").value(DEFAULT_CLIENT_EMAIL))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getClientsByIdFiltering() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        Long id = client.getId();

        defaultClientShouldBeFound("id.equals=" + id);
        defaultClientShouldNotBeFound("id.notEquals=" + id);

        defaultClientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.greaterThan=" + id);

        defaultClientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClientsByClientNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName equals to DEFAULT_CLIENT_NAME
        defaultClientShouldBeFound("clientName.equals=" + DEFAULT_CLIENT_NAME);

        // Get all the clientList where clientName equals to UPDATED_CLIENT_NAME
        defaultClientShouldNotBeFound("clientName.equals=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByClientNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName not equals to DEFAULT_CLIENT_NAME
        defaultClientShouldNotBeFound("clientName.notEquals=" + DEFAULT_CLIENT_NAME);

        // Get all the clientList where clientName not equals to UPDATED_CLIENT_NAME
        defaultClientShouldBeFound("clientName.notEquals=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByClientNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName in DEFAULT_CLIENT_NAME or UPDATED_CLIENT_NAME
        defaultClientShouldBeFound("clientName.in=" + DEFAULT_CLIENT_NAME + "," + UPDATED_CLIENT_NAME);

        // Get all the clientList where clientName equals to UPDATED_CLIENT_NAME
        defaultClientShouldNotBeFound("clientName.in=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByClientNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName is not null
        defaultClientShouldBeFound("clientName.specified=true");

        // Get all the clientList where clientName is null
        defaultClientShouldNotBeFound("clientName.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByClientNameContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName contains DEFAULT_CLIENT_NAME
        defaultClientShouldBeFound("clientName.contains=" + DEFAULT_CLIENT_NAME);

        // Get all the clientList where clientName contains UPDATED_CLIENT_NAME
        defaultClientShouldNotBeFound("clientName.contains=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByClientNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName does not contain DEFAULT_CLIENT_NAME
        defaultClientShouldNotBeFound("clientName.doesNotContain=" + DEFAULT_CLIENT_NAME);

        // Get all the clientList where clientName does not contain UPDATED_CLIENT_NAME
        defaultClientShouldBeFound("clientName.doesNotContain=" + UPDATED_CLIENT_NAME);
    }


    @Test
    @Transactional
    public void getAllClientsByClientDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientDescription equals to DEFAULT_CLIENT_DESCRIPTION
        defaultClientShouldBeFound("clientDescription.equals=" + DEFAULT_CLIENT_DESCRIPTION);

        // Get all the clientList where clientDescription equals to UPDATED_CLIENT_DESCRIPTION
        defaultClientShouldNotBeFound("clientDescription.equals=" + UPDATED_CLIENT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByClientDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientDescription not equals to DEFAULT_CLIENT_DESCRIPTION
        defaultClientShouldNotBeFound("clientDescription.notEquals=" + DEFAULT_CLIENT_DESCRIPTION);

        // Get all the clientList where clientDescription not equals to UPDATED_CLIENT_DESCRIPTION
        defaultClientShouldBeFound("clientDescription.notEquals=" + UPDATED_CLIENT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByClientDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientDescription in DEFAULT_CLIENT_DESCRIPTION or UPDATED_CLIENT_DESCRIPTION
        defaultClientShouldBeFound("clientDescription.in=" + DEFAULT_CLIENT_DESCRIPTION + "," + UPDATED_CLIENT_DESCRIPTION);

        // Get all the clientList where clientDescription equals to UPDATED_CLIENT_DESCRIPTION
        defaultClientShouldNotBeFound("clientDescription.in=" + UPDATED_CLIENT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByClientDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientDescription is not null
        defaultClientShouldBeFound("clientDescription.specified=true");

        // Get all the clientList where clientDescription is null
        defaultClientShouldNotBeFound("clientDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByClientDescriptionContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientDescription contains DEFAULT_CLIENT_DESCRIPTION
        defaultClientShouldBeFound("clientDescription.contains=" + DEFAULT_CLIENT_DESCRIPTION);

        // Get all the clientList where clientDescription contains UPDATED_CLIENT_DESCRIPTION
        defaultClientShouldNotBeFound("clientDescription.contains=" + UPDATED_CLIENT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClientsByClientDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientDescription does not contain DEFAULT_CLIENT_DESCRIPTION
        defaultClientShouldNotBeFound("clientDescription.doesNotContain=" + DEFAULT_CLIENT_DESCRIPTION);

        // Get all the clientList where clientDescription does not contain UPDATED_CLIENT_DESCRIPTION
        defaultClientShouldBeFound("clientDescription.doesNotContain=" + UPDATED_CLIENT_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllClientsByClientLogoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientLogoUrl equals to DEFAULT_CLIENT_LOGO_URL
        defaultClientShouldBeFound("clientLogoUrl.equals=" + DEFAULT_CLIENT_LOGO_URL);

        // Get all the clientList where clientLogoUrl equals to UPDATED_CLIENT_LOGO_URL
        defaultClientShouldNotBeFound("clientLogoUrl.equals=" + UPDATED_CLIENT_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllClientsByClientLogoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientLogoUrl not equals to DEFAULT_CLIENT_LOGO_URL
        defaultClientShouldNotBeFound("clientLogoUrl.notEquals=" + DEFAULT_CLIENT_LOGO_URL);

        // Get all the clientList where clientLogoUrl not equals to UPDATED_CLIENT_LOGO_URL
        defaultClientShouldBeFound("clientLogoUrl.notEquals=" + UPDATED_CLIENT_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllClientsByClientLogoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientLogoUrl in DEFAULT_CLIENT_LOGO_URL or UPDATED_CLIENT_LOGO_URL
        defaultClientShouldBeFound("clientLogoUrl.in=" + DEFAULT_CLIENT_LOGO_URL + "," + UPDATED_CLIENT_LOGO_URL);

        // Get all the clientList where clientLogoUrl equals to UPDATED_CLIENT_LOGO_URL
        defaultClientShouldNotBeFound("clientLogoUrl.in=" + UPDATED_CLIENT_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllClientsByClientLogoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientLogoUrl is not null
        defaultClientShouldBeFound("clientLogoUrl.specified=true");

        // Get all the clientList where clientLogoUrl is null
        defaultClientShouldNotBeFound("clientLogoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByClientLogoUrlContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientLogoUrl contains DEFAULT_CLIENT_LOGO_URL
        defaultClientShouldBeFound("clientLogoUrl.contains=" + DEFAULT_CLIENT_LOGO_URL);

        // Get all the clientList where clientLogoUrl contains UPDATED_CLIENT_LOGO_URL
        defaultClientShouldNotBeFound("clientLogoUrl.contains=" + UPDATED_CLIENT_LOGO_URL);
    }

    @Test
    @Transactional
    public void getAllClientsByClientLogoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientLogoUrl does not contain DEFAULT_CLIENT_LOGO_URL
        defaultClientShouldNotBeFound("clientLogoUrl.doesNotContain=" + DEFAULT_CLIENT_LOGO_URL);

        // Get all the clientList where clientLogoUrl does not contain UPDATED_CLIENT_LOGO_URL
        defaultClientShouldBeFound("clientLogoUrl.doesNotContain=" + UPDATED_CLIENT_LOGO_URL);
    }


    @Test
    @Transactional
    public void getAllClientsByClientContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientContactName equals to DEFAULT_CLIENT_CONTACT_NAME
        defaultClientShouldBeFound("clientContactName.equals=" + DEFAULT_CLIENT_CONTACT_NAME);

        // Get all the clientList where clientContactName equals to UPDATED_CLIENT_CONTACT_NAME
        defaultClientShouldNotBeFound("clientContactName.equals=" + UPDATED_CLIENT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByClientContactNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientContactName not equals to DEFAULT_CLIENT_CONTACT_NAME
        defaultClientShouldNotBeFound("clientContactName.notEquals=" + DEFAULT_CLIENT_CONTACT_NAME);

        // Get all the clientList where clientContactName not equals to UPDATED_CLIENT_CONTACT_NAME
        defaultClientShouldBeFound("clientContactName.notEquals=" + UPDATED_CLIENT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByClientContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientContactName in DEFAULT_CLIENT_CONTACT_NAME or UPDATED_CLIENT_CONTACT_NAME
        defaultClientShouldBeFound("clientContactName.in=" + DEFAULT_CLIENT_CONTACT_NAME + "," + UPDATED_CLIENT_CONTACT_NAME);

        // Get all the clientList where clientContactName equals to UPDATED_CLIENT_CONTACT_NAME
        defaultClientShouldNotBeFound("clientContactName.in=" + UPDATED_CLIENT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByClientContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientContactName is not null
        defaultClientShouldBeFound("clientContactName.specified=true");

        // Get all the clientList where clientContactName is null
        defaultClientShouldNotBeFound("clientContactName.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByClientContactNameContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientContactName contains DEFAULT_CLIENT_CONTACT_NAME
        defaultClientShouldBeFound("clientContactName.contains=" + DEFAULT_CLIENT_CONTACT_NAME);

        // Get all the clientList where clientContactName contains UPDATED_CLIENT_CONTACT_NAME
        defaultClientShouldNotBeFound("clientContactName.contains=" + UPDATED_CLIENT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByClientContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientContactName does not contain DEFAULT_CLIENT_CONTACT_NAME
        defaultClientShouldNotBeFound("clientContactName.doesNotContain=" + DEFAULT_CLIENT_CONTACT_NAME);

        // Get all the clientList where clientContactName does not contain UPDATED_CLIENT_CONTACT_NAME
        defaultClientShouldBeFound("clientContactName.doesNotContain=" + UPDATED_CLIENT_CONTACT_NAME);
    }


    @Test
    @Transactional
    public void getAllClientsByClientPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientPhone equals to DEFAULT_CLIENT_PHONE
        defaultClientShouldBeFound("clientPhone.equals=" + DEFAULT_CLIENT_PHONE);

        // Get all the clientList where clientPhone equals to UPDATED_CLIENT_PHONE
        defaultClientShouldNotBeFound("clientPhone.equals=" + UPDATED_CLIENT_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientsByClientPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientPhone not equals to DEFAULT_CLIENT_PHONE
        defaultClientShouldNotBeFound("clientPhone.notEquals=" + DEFAULT_CLIENT_PHONE);

        // Get all the clientList where clientPhone not equals to UPDATED_CLIENT_PHONE
        defaultClientShouldBeFound("clientPhone.notEquals=" + UPDATED_CLIENT_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientsByClientPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientPhone in DEFAULT_CLIENT_PHONE or UPDATED_CLIENT_PHONE
        defaultClientShouldBeFound("clientPhone.in=" + DEFAULT_CLIENT_PHONE + "," + UPDATED_CLIENT_PHONE);

        // Get all the clientList where clientPhone equals to UPDATED_CLIENT_PHONE
        defaultClientShouldNotBeFound("clientPhone.in=" + UPDATED_CLIENT_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientsByClientPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientPhone is not null
        defaultClientShouldBeFound("clientPhone.specified=true");

        // Get all the clientList where clientPhone is null
        defaultClientShouldNotBeFound("clientPhone.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByClientPhoneContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientPhone contains DEFAULT_CLIENT_PHONE
        defaultClientShouldBeFound("clientPhone.contains=" + DEFAULT_CLIENT_PHONE);

        // Get all the clientList where clientPhone contains UPDATED_CLIENT_PHONE
        defaultClientShouldNotBeFound("clientPhone.contains=" + UPDATED_CLIENT_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientsByClientPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientPhone does not contain DEFAULT_CLIENT_PHONE
        defaultClientShouldNotBeFound("clientPhone.doesNotContain=" + DEFAULT_CLIENT_PHONE);

        // Get all the clientList where clientPhone does not contain UPDATED_CLIENT_PHONE
        defaultClientShouldBeFound("clientPhone.doesNotContain=" + UPDATED_CLIENT_PHONE);
    }


    @Test
    @Transactional
    public void getAllClientsByClientEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientEmail equals to DEFAULT_CLIENT_EMAIL
        defaultClientShouldBeFound("clientEmail.equals=" + DEFAULT_CLIENT_EMAIL);

        // Get all the clientList where clientEmail equals to UPDATED_CLIENT_EMAIL
        defaultClientShouldNotBeFound("clientEmail.equals=" + UPDATED_CLIENT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByClientEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientEmail not equals to DEFAULT_CLIENT_EMAIL
        defaultClientShouldNotBeFound("clientEmail.notEquals=" + DEFAULT_CLIENT_EMAIL);

        // Get all the clientList where clientEmail not equals to UPDATED_CLIENT_EMAIL
        defaultClientShouldBeFound("clientEmail.notEquals=" + UPDATED_CLIENT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByClientEmailIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientEmail in DEFAULT_CLIENT_EMAIL or UPDATED_CLIENT_EMAIL
        defaultClientShouldBeFound("clientEmail.in=" + DEFAULT_CLIENT_EMAIL + "," + UPDATED_CLIENT_EMAIL);

        // Get all the clientList where clientEmail equals to UPDATED_CLIENT_EMAIL
        defaultClientShouldNotBeFound("clientEmail.in=" + UPDATED_CLIENT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByClientEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientEmail is not null
        defaultClientShouldBeFound("clientEmail.specified=true");

        // Get all the clientList where clientEmail is null
        defaultClientShouldNotBeFound("clientEmail.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByClientEmailContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientEmail contains DEFAULT_CLIENT_EMAIL
        defaultClientShouldBeFound("clientEmail.contains=" + DEFAULT_CLIENT_EMAIL);

        // Get all the clientList where clientEmail contains UPDATED_CLIENT_EMAIL
        defaultClientShouldNotBeFound("clientEmail.contains=" + UPDATED_CLIENT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByClientEmailNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientEmail does not contain DEFAULT_CLIENT_EMAIL
        defaultClientShouldNotBeFound("clientEmail.doesNotContain=" + DEFAULT_CLIENT_EMAIL);

        // Get all the clientList where clientEmail does not contain UPDATED_CLIENT_EMAIL
        defaultClientShouldBeFound("clientEmail.doesNotContain=" + UPDATED_CLIENT_EMAIL);
    }


    @Test
    @Transactional
    public void getAllClientsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where createdDate equals to DEFAULT_CREATED_DATE
        defaultClientShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the clientList where createdDate equals to UPDATED_CREATED_DATE
        defaultClientShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultClientShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the clientList where createdDate not equals to UPDATED_CREATED_DATE
        defaultClientShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultClientShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the clientList where createdDate equals to UPDATED_CREATED_DATE
        defaultClientShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where createdDate is not null
        defaultClientShouldBeFound("createdDate.specified=true");

        // Get all the clientList where createdDate is null
        defaultClientShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultClientShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the clientList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultClientShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultClientShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the clientList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultClientShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where createdDate is less than DEFAULT_CREATED_DATE
        defaultClientShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the clientList where createdDate is less than UPDATED_CREATED_DATE
        defaultClientShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultClientShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the clientList where createdDate is greater than SMALLER_CREATED_DATE
        defaultClientShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllClientsByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where enabled equals to DEFAULT_ENABLED
        defaultClientShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the clientList where enabled equals to UPDATED_ENABLED
        defaultClientShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllClientsByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where enabled not equals to DEFAULT_ENABLED
        defaultClientShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the clientList where enabled not equals to UPDATED_ENABLED
        defaultClientShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllClientsByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultClientShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the clientList where enabled equals to UPDATED_ENABLED
        defaultClientShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllClientsByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where enabled is not null
        defaultClientShouldBeFound("enabled.specified=true");

        // Get all the clientList where enabled is null
        defaultClientShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reason equals to DEFAULT_REASON
        defaultClientShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the clientList where reason equals to UPDATED_REASON
        defaultClientShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllClientsByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reason not equals to DEFAULT_REASON
        defaultClientShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the clientList where reason not equals to UPDATED_REASON
        defaultClientShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllClientsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultClientShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the clientList where reason equals to UPDATED_REASON
        defaultClientShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllClientsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reason is not null
        defaultClientShouldBeFound("reason.specified=true");

        // Get all the clientList where reason is null
        defaultClientShouldNotBeFound("reason.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByReasonContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reason contains DEFAULT_REASON
        defaultClientShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the clientList where reason contains UPDATED_REASON
        defaultClientShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllClientsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where reason does not contain DEFAULT_REASON
        defaultClientShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the clientList where reason does not contain UPDATED_REASON
        defaultClientShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }


    @Test
    @Transactional
    public void getAllClientsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultClientShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultClientShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultClientShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultClientShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultClientShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the clientList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultClientShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lastUpdatedDate is not null
        defaultClientShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the clientList where lastUpdatedDate is null
        defaultClientShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultClientShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultClientShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultClientShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultClientShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultClientShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultClientShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllClientsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultClientShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the clientList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultClientShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllClientsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultClientShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the clientList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultClientShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllClientsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultClientShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the clientList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultClientShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllClientsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultClientShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the clientList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultClientShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllClientsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where hasExtraData is not null
        defaultClientShouldBeFound("hasExtraData.specified=true");

        // Get all the clientList where hasExtraData is null
        defaultClientShouldNotBeFound("hasExtraData.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientShouldBeFound(String filter) throws Exception {
        restClientMockMvc.perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].clientDescription").value(hasItem(DEFAULT_CLIENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].clientLogoContentType").value(hasItem(DEFAULT_CLIENT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].clientLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CLIENT_LOGO))))
            .andExpect(jsonPath("$.[*].clientLogoUrl").value(hasItem(DEFAULT_CLIENT_LOGO_URL)))
            .andExpect(jsonPath("$.[*].clientContactName").value(hasItem(DEFAULT_CLIENT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].clientPhone").value(hasItem(DEFAULT_CLIENT_PHONE)))
            .andExpect(jsonPath("$.[*].clientEmail").value(hasItem(DEFAULT_CLIENT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restClientMockMvc.perform(get("/api/clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientShouldNotBeFound(String filter) throws Exception {
        restClientMockMvc.perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientMockMvc.perform(get("/api/clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .clientName(UPDATED_CLIENT_NAME)
            .clientDescription(UPDATED_CLIENT_DESCRIPTION)
            .clientLogo(UPDATED_CLIENT_LOGO)
            .clientLogoContentType(UPDATED_CLIENT_LOGO_CONTENT_TYPE)
            .clientLogoUrl(UPDATED_CLIENT_LOGO_URL)
            .clientContactName(UPDATED_CLIENT_CONTACT_NAME)
            .clientPhone(UPDATED_CLIENT_PHONE)
            .clientEmail(UPDATED_CLIENT_EMAIL)
            .createdDate(UPDATED_CREATED_DATE)
            .enabled(UPDATED_ENABLED)
            .reason(UPDATED_REASON)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        ClientDTO clientDTO = clientMapper.toDto(updatedClient);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testClient.getClientDescription()).isEqualTo(UPDATED_CLIENT_DESCRIPTION);
        assertThat(testClient.getClientLogo()).isEqualTo(UPDATED_CLIENT_LOGO);
        assertThat(testClient.getClientLogoContentType()).isEqualTo(UPDATED_CLIENT_LOGO_CONTENT_TYPE);
        assertThat(testClient.getClientLogoUrl()).isEqualTo(UPDATED_CLIENT_LOGO_URL);
        assertThat(testClient.getClientContactName()).isEqualTo(UPDATED_CLIENT_CONTACT_NAME);
        assertThat(testClient.getClientPhone()).isEqualTo(UPDATED_CLIENT_PHONE);
        assertThat(testClient.getClientEmail()).isEqualTo(UPDATED_CLIENT_EMAIL);
        assertThat(testClient.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClient.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testClient.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testClient.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testClient.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
