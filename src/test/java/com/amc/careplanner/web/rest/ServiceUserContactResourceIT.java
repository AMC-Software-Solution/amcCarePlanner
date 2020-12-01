package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.ServiceUserContact;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.ServiceUserContactRepository;
import com.amc.careplanner.service.ServiceUserContactService;
import com.amc.careplanner.service.dto.ServiceUserContactDTO;
import com.amc.careplanner.service.mapper.ServiceUserContactMapper;
import com.amc.careplanner.service.dto.ServiceUserContactCriteria;
import com.amc.careplanner.service.ServiceUserContactQueryService;

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
 * Integration tests for the {@link ServiceUserContactResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceUserContactResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_OR_TOWN = "AAAAAAAAAA";
    private static final String UPDATED_CITY_OR_TOWN = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    @Autowired
    private ServiceUserContactRepository serviceUserContactRepository;

    @Autowired
    private ServiceUserContactMapper serviceUserContactMapper;

    @Autowired
    private ServiceUserContactService serviceUserContactService;

    @Autowired
    private ServiceUserContactQueryService serviceUserContactQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceUserContactMockMvc;

    private ServiceUserContact serviceUserContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUserContact createEntity(EntityManager em) {
        ServiceUserContact serviceUserContact = new ServiceUserContact()
            .address(DEFAULT_ADDRESS)
            .cityOrTown(DEFAULT_CITY_OR_TOWN)
            .county(DEFAULT_COUNTY)
            .postCode(DEFAULT_POST_CODE)
            .telephone(DEFAULT_TELEPHONE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID);
        return serviceUserContact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUserContact createUpdatedEntity(EntityManager em) {
        ServiceUserContact serviceUserContact = new ServiceUserContact()
            .address(UPDATED_ADDRESS)
            .cityOrTown(UPDATED_CITY_OR_TOWN)
            .county(UPDATED_COUNTY)
            .postCode(UPDATED_POST_CODE)
            .telephone(UPDATED_TELEPHONE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        return serviceUserContact;
    }

    @BeforeEach
    public void initTest() {
        serviceUserContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceUserContact() throws Exception {
        int databaseSizeBeforeCreate = serviceUserContactRepository.findAll().size();
        // Create the ServiceUserContact
        ServiceUserContactDTO serviceUserContactDTO = serviceUserContactMapper.toDto(serviceUserContact);
        restServiceUserContactMockMvc.perform(post("/api/service-user-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserContactDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceUserContact in the database
        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceUserContact testServiceUserContact = serviceUserContactList.get(serviceUserContactList.size() - 1);
        assertThat(testServiceUserContact.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testServiceUserContact.getCityOrTown()).isEqualTo(DEFAULT_CITY_OR_TOWN);
        assertThat(testServiceUserContact.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testServiceUserContact.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testServiceUserContact.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testServiceUserContact.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testServiceUserContact.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void createServiceUserContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceUserContactRepository.findAll().size();

        // Create the ServiceUserContact with an existing ID
        serviceUserContact.setId(1L);
        ServiceUserContactDTO serviceUserContactDTO = serviceUserContactMapper.toDto(serviceUserContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceUserContactMockMvc.perform(post("/api/service-user-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUserContact in the database
        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserContactRepository.findAll().size();
        // set the field null
        serviceUserContact.setAddress(null);

        // Create the ServiceUserContact, which fails.
        ServiceUserContactDTO serviceUserContactDTO = serviceUserContactMapper.toDto(serviceUserContact);


        restServiceUserContactMockMvc.perform(post("/api/service-user-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserContactDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityOrTownIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserContactRepository.findAll().size();
        // set the field null
        serviceUserContact.setCityOrTown(null);

        // Create the ServiceUserContact, which fails.
        ServiceUserContactDTO serviceUserContactDTO = serviceUserContactMapper.toDto(serviceUserContact);


        restServiceUserContactMockMvc.perform(post("/api/service-user-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserContactDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserContactRepository.findAll().size();
        // set the field null
        serviceUserContact.setPostCode(null);

        // Create the ServiceUserContact, which fails.
        ServiceUserContactDTO serviceUserContactDTO = serviceUserContactMapper.toDto(serviceUserContact);


        restServiceUserContactMockMvc.perform(post("/api/service-user-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserContactDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserContactRepository.findAll().size();
        // set the field null
        serviceUserContact.setTelephone(null);

        // Create the ServiceUserContact, which fails.
        ServiceUserContactDTO serviceUserContactDTO = serviceUserContactMapper.toDto(serviceUserContact);


        restServiceUserContactMockMvc.perform(post("/api/service-user-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserContactDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceUserContactRepository.findAll().size();
        // set the field null
        serviceUserContact.setClientId(null);

        // Create the ServiceUserContact, which fails.
        ServiceUserContactDTO serviceUserContactDTO = serviceUserContactMapper.toDto(serviceUserContact);


        restServiceUserContactMockMvc.perform(post("/api/service-user-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserContactDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceUserContacts() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList
        restServiceUserContactMockMvc.perform(get("/api/service-user-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUserContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].cityOrTown").value(hasItem(DEFAULT_CITY_OR_TOWN)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getServiceUserContact() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get the serviceUserContact
        restServiceUserContactMockMvc.perform(get("/api/service-user-contacts/{id}", serviceUserContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceUserContact.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.cityOrTown").value(DEFAULT_CITY_OR_TOWN))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getServiceUserContactsByIdFiltering() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        Long id = serviceUserContact.getId();

        defaultServiceUserContactShouldBeFound("id.equals=" + id);
        defaultServiceUserContactShouldNotBeFound("id.notEquals=" + id);

        defaultServiceUserContactShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceUserContactShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceUserContactShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceUserContactShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceUserContactsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where address equals to DEFAULT_ADDRESS
        defaultServiceUserContactShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the serviceUserContactList where address equals to UPDATED_ADDRESS
        defaultServiceUserContactShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where address not equals to DEFAULT_ADDRESS
        defaultServiceUserContactShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the serviceUserContactList where address not equals to UPDATED_ADDRESS
        defaultServiceUserContactShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultServiceUserContactShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the serviceUserContactList where address equals to UPDATED_ADDRESS
        defaultServiceUserContactShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where address is not null
        defaultServiceUserContactShouldBeFound("address.specified=true");

        // Get all the serviceUserContactList where address is null
        defaultServiceUserContactShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUserContactsByAddressContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where address contains DEFAULT_ADDRESS
        defaultServiceUserContactShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the serviceUserContactList where address contains UPDATED_ADDRESS
        defaultServiceUserContactShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where address does not contain DEFAULT_ADDRESS
        defaultServiceUserContactShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the serviceUserContactList where address does not contain UPDATED_ADDRESS
        defaultServiceUserContactShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllServiceUserContactsByCityOrTownIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where cityOrTown equals to DEFAULT_CITY_OR_TOWN
        defaultServiceUserContactShouldBeFound("cityOrTown.equals=" + DEFAULT_CITY_OR_TOWN);

        // Get all the serviceUserContactList where cityOrTown equals to UPDATED_CITY_OR_TOWN
        defaultServiceUserContactShouldNotBeFound("cityOrTown.equals=" + UPDATED_CITY_OR_TOWN);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByCityOrTownIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where cityOrTown not equals to DEFAULT_CITY_OR_TOWN
        defaultServiceUserContactShouldNotBeFound("cityOrTown.notEquals=" + DEFAULT_CITY_OR_TOWN);

        // Get all the serviceUserContactList where cityOrTown not equals to UPDATED_CITY_OR_TOWN
        defaultServiceUserContactShouldBeFound("cityOrTown.notEquals=" + UPDATED_CITY_OR_TOWN);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByCityOrTownIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where cityOrTown in DEFAULT_CITY_OR_TOWN or UPDATED_CITY_OR_TOWN
        defaultServiceUserContactShouldBeFound("cityOrTown.in=" + DEFAULT_CITY_OR_TOWN + "," + UPDATED_CITY_OR_TOWN);

        // Get all the serviceUserContactList where cityOrTown equals to UPDATED_CITY_OR_TOWN
        defaultServiceUserContactShouldNotBeFound("cityOrTown.in=" + UPDATED_CITY_OR_TOWN);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByCityOrTownIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where cityOrTown is not null
        defaultServiceUserContactShouldBeFound("cityOrTown.specified=true");

        // Get all the serviceUserContactList where cityOrTown is null
        defaultServiceUserContactShouldNotBeFound("cityOrTown.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUserContactsByCityOrTownContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where cityOrTown contains DEFAULT_CITY_OR_TOWN
        defaultServiceUserContactShouldBeFound("cityOrTown.contains=" + DEFAULT_CITY_OR_TOWN);

        // Get all the serviceUserContactList where cityOrTown contains UPDATED_CITY_OR_TOWN
        defaultServiceUserContactShouldNotBeFound("cityOrTown.contains=" + UPDATED_CITY_OR_TOWN);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByCityOrTownNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where cityOrTown does not contain DEFAULT_CITY_OR_TOWN
        defaultServiceUserContactShouldNotBeFound("cityOrTown.doesNotContain=" + DEFAULT_CITY_OR_TOWN);

        // Get all the serviceUserContactList where cityOrTown does not contain UPDATED_CITY_OR_TOWN
        defaultServiceUserContactShouldBeFound("cityOrTown.doesNotContain=" + UPDATED_CITY_OR_TOWN);
    }


    @Test
    @Transactional
    public void getAllServiceUserContactsByCountyIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where county equals to DEFAULT_COUNTY
        defaultServiceUserContactShouldBeFound("county.equals=" + DEFAULT_COUNTY);

        // Get all the serviceUserContactList where county equals to UPDATED_COUNTY
        defaultServiceUserContactShouldNotBeFound("county.equals=" + UPDATED_COUNTY);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByCountyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where county not equals to DEFAULT_COUNTY
        defaultServiceUserContactShouldNotBeFound("county.notEquals=" + DEFAULT_COUNTY);

        // Get all the serviceUserContactList where county not equals to UPDATED_COUNTY
        defaultServiceUserContactShouldBeFound("county.notEquals=" + UPDATED_COUNTY);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByCountyIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where county in DEFAULT_COUNTY or UPDATED_COUNTY
        defaultServiceUserContactShouldBeFound("county.in=" + DEFAULT_COUNTY + "," + UPDATED_COUNTY);

        // Get all the serviceUserContactList where county equals to UPDATED_COUNTY
        defaultServiceUserContactShouldNotBeFound("county.in=" + UPDATED_COUNTY);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByCountyIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where county is not null
        defaultServiceUserContactShouldBeFound("county.specified=true");

        // Get all the serviceUserContactList where county is null
        defaultServiceUserContactShouldNotBeFound("county.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUserContactsByCountyContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where county contains DEFAULT_COUNTY
        defaultServiceUserContactShouldBeFound("county.contains=" + DEFAULT_COUNTY);

        // Get all the serviceUserContactList where county contains UPDATED_COUNTY
        defaultServiceUserContactShouldNotBeFound("county.contains=" + UPDATED_COUNTY);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByCountyNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where county does not contain DEFAULT_COUNTY
        defaultServiceUserContactShouldNotBeFound("county.doesNotContain=" + DEFAULT_COUNTY);

        // Get all the serviceUserContactList where county does not contain UPDATED_COUNTY
        defaultServiceUserContactShouldBeFound("county.doesNotContain=" + UPDATED_COUNTY);
    }


    @Test
    @Transactional
    public void getAllServiceUserContactsByPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where postCode equals to DEFAULT_POST_CODE
        defaultServiceUserContactShouldBeFound("postCode.equals=" + DEFAULT_POST_CODE);

        // Get all the serviceUserContactList where postCode equals to UPDATED_POST_CODE
        defaultServiceUserContactShouldNotBeFound("postCode.equals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByPostCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where postCode not equals to DEFAULT_POST_CODE
        defaultServiceUserContactShouldNotBeFound("postCode.notEquals=" + DEFAULT_POST_CODE);

        // Get all the serviceUserContactList where postCode not equals to UPDATED_POST_CODE
        defaultServiceUserContactShouldBeFound("postCode.notEquals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where postCode in DEFAULT_POST_CODE or UPDATED_POST_CODE
        defaultServiceUserContactShouldBeFound("postCode.in=" + DEFAULT_POST_CODE + "," + UPDATED_POST_CODE);

        // Get all the serviceUserContactList where postCode equals to UPDATED_POST_CODE
        defaultServiceUserContactShouldNotBeFound("postCode.in=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where postCode is not null
        defaultServiceUserContactShouldBeFound("postCode.specified=true");

        // Get all the serviceUserContactList where postCode is null
        defaultServiceUserContactShouldNotBeFound("postCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUserContactsByPostCodeContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where postCode contains DEFAULT_POST_CODE
        defaultServiceUserContactShouldBeFound("postCode.contains=" + DEFAULT_POST_CODE);

        // Get all the serviceUserContactList where postCode contains UPDATED_POST_CODE
        defaultServiceUserContactShouldNotBeFound("postCode.contains=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByPostCodeNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where postCode does not contain DEFAULT_POST_CODE
        defaultServiceUserContactShouldNotBeFound("postCode.doesNotContain=" + DEFAULT_POST_CODE);

        // Get all the serviceUserContactList where postCode does not contain UPDATED_POST_CODE
        defaultServiceUserContactShouldBeFound("postCode.doesNotContain=" + UPDATED_POST_CODE);
    }


    @Test
    @Transactional
    public void getAllServiceUserContactsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where telephone equals to DEFAULT_TELEPHONE
        defaultServiceUserContactShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the serviceUserContactList where telephone equals to UPDATED_TELEPHONE
        defaultServiceUserContactShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where telephone not equals to DEFAULT_TELEPHONE
        defaultServiceUserContactShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the serviceUserContactList where telephone not equals to UPDATED_TELEPHONE
        defaultServiceUserContactShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultServiceUserContactShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the serviceUserContactList where telephone equals to UPDATED_TELEPHONE
        defaultServiceUserContactShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where telephone is not null
        defaultServiceUserContactShouldBeFound("telephone.specified=true");

        // Get all the serviceUserContactList where telephone is null
        defaultServiceUserContactShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceUserContactsByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where telephone contains DEFAULT_TELEPHONE
        defaultServiceUserContactShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the serviceUserContactList where telephone contains UPDATED_TELEPHONE
        defaultServiceUserContactShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where telephone does not contain DEFAULT_TELEPHONE
        defaultServiceUserContactShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the serviceUserContactList where telephone does not contain UPDATED_TELEPHONE
        defaultServiceUserContactShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllServiceUserContactsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserContactShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserContactList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserContactShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserContactShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserContactList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserContactShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultServiceUserContactShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the serviceUserContactList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserContactShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where lastUpdatedDate is not null
        defaultServiceUserContactShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the serviceUserContactList where lastUpdatedDate is null
        defaultServiceUserContactShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserContactShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserContactList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultServiceUserContactShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserContactShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserContactList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultServiceUserContactShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserContactShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserContactList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultServiceUserContactShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultServiceUserContactShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceUserContactList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultServiceUserContactShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceUserContactsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where clientId equals to DEFAULT_CLIENT_ID
        defaultServiceUserContactShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserContactList where clientId equals to UPDATED_CLIENT_ID
        defaultServiceUserContactShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where clientId not equals to DEFAULT_CLIENT_ID
        defaultServiceUserContactShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserContactList where clientId not equals to UPDATED_CLIENT_ID
        defaultServiceUserContactShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultServiceUserContactShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the serviceUserContactList where clientId equals to UPDATED_CLIENT_ID
        defaultServiceUserContactShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where clientId is not null
        defaultServiceUserContactShouldBeFound("clientId.specified=true");

        // Get all the serviceUserContactList where clientId is null
        defaultServiceUserContactShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultServiceUserContactShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserContactList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultServiceUserContactShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultServiceUserContactShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserContactList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultServiceUserContactShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where clientId is less than DEFAULT_CLIENT_ID
        defaultServiceUserContactShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserContactList where clientId is less than UPDATED_CLIENT_ID
        defaultServiceUserContactShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceUserContactsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        // Get all the serviceUserContactList where clientId is greater than DEFAULT_CLIENT_ID
        defaultServiceUserContactShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the serviceUserContactList where clientId is greater than SMALLER_CLIENT_ID
        defaultServiceUserContactShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllServiceUserContactsByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        serviceUserContact.setServiceUser(serviceUser);
        serviceUserContactRepository.saveAndFlush(serviceUserContact);
        Long serviceUserId = serviceUser.getId();

        // Get all the serviceUserContactList where serviceUser equals to serviceUserId
        defaultServiceUserContactShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the serviceUserContactList where serviceUser equals to serviceUserId + 1
        defaultServiceUserContactShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceUserContactShouldBeFound(String filter) throws Exception {
        restServiceUserContactMockMvc.perform(get("/api/service-user-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUserContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].cityOrTown").value(hasItem(DEFAULT_CITY_OR_TOWN)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())));

        // Check, that the count call also returns 1
        restServiceUserContactMockMvc.perform(get("/api/service-user-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceUserContactShouldNotBeFound(String filter) throws Exception {
        restServiceUserContactMockMvc.perform(get("/api/service-user-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceUserContactMockMvc.perform(get("/api/service-user-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingServiceUserContact() throws Exception {
        // Get the serviceUserContact
        restServiceUserContactMockMvc.perform(get("/api/service-user-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceUserContact() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        int databaseSizeBeforeUpdate = serviceUserContactRepository.findAll().size();

        // Update the serviceUserContact
        ServiceUserContact updatedServiceUserContact = serviceUserContactRepository.findById(serviceUserContact.getId()).get();
        // Disconnect from session so that the updates on updatedServiceUserContact are not directly saved in db
        em.detach(updatedServiceUserContact);
        updatedServiceUserContact
            .address(UPDATED_ADDRESS)
            .cityOrTown(UPDATED_CITY_OR_TOWN)
            .county(UPDATED_COUNTY)
            .postCode(UPDATED_POST_CODE)
            .telephone(UPDATED_TELEPHONE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID);
        ServiceUserContactDTO serviceUserContactDTO = serviceUserContactMapper.toDto(updatedServiceUserContact);

        restServiceUserContactMockMvc.perform(put("/api/service-user-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserContactDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceUserContact in the database
        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeUpdate);
        ServiceUserContact testServiceUserContact = serviceUserContactList.get(serviceUserContactList.size() - 1);
        assertThat(testServiceUserContact.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testServiceUserContact.getCityOrTown()).isEqualTo(UPDATED_CITY_OR_TOWN);
        assertThat(testServiceUserContact.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testServiceUserContact.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testServiceUserContact.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testServiceUserContact.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testServiceUserContact.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceUserContact() throws Exception {
        int databaseSizeBeforeUpdate = serviceUserContactRepository.findAll().size();

        // Create the ServiceUserContact
        ServiceUserContactDTO serviceUserContactDTO = serviceUserContactMapper.toDto(serviceUserContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceUserContactMockMvc.perform(put("/api/service-user-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUserContact in the database
        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceUserContact() throws Exception {
        // Initialize the database
        serviceUserContactRepository.saveAndFlush(serviceUserContact);

        int databaseSizeBeforeDelete = serviceUserContactRepository.findAll().size();

        // Delete the serviceUserContact
        restServiceUserContactMockMvc.perform(delete("/api/service-user-contacts/{id}", serviceUserContact.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceUserContact> serviceUserContactList = serviceUserContactRepository.findAll();
        assertThat(serviceUserContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
