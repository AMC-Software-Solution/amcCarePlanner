package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.ServiceOrder;
import com.amc.careplanner.domain.Currency;
import com.amc.careplanner.repository.ServiceOrderRepository;
import com.amc.careplanner.service.ServiceOrderService;
import com.amc.careplanner.service.dto.ServiceOrderDTO;
import com.amc.careplanner.service.mapper.ServiceOrderMapper;
import com.amc.careplanner.service.dto.ServiceOrderCriteria;
import com.amc.careplanner.service.ServiceOrderQueryService;

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
 * Integration tests for the {@link ServiceOrderResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceOrderResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_SERVICE_HOURLY_RATE = 1D;
    private static final Double UPDATED_SERVICE_HOURLY_RATE = 2D;
    private static final Double SMALLER_SERVICE_HOURLY_RATE = 1D - 1D;

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private ServiceOrderMapper serviceOrderMapper;

    @Autowired
    private ServiceOrderService serviceOrderService;

    @Autowired
    private ServiceOrderQueryService serviceOrderQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderMockMvc;

    private ServiceOrder serviceOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrder createEntity(EntityManager em) {
        ServiceOrder serviceOrder = new ServiceOrder()
            .title(DEFAULT_TITLE)
            .serviceDescription(DEFAULT_SERVICE_DESCRIPTION)
            .serviceHourlyRate(DEFAULT_SERVICE_HOURLY_RATE)
            .clientId(DEFAULT_CLIENT_ID)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE);
        return serviceOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrder createUpdatedEntity(EntityManager em) {
        ServiceOrder serviceOrder = new ServiceOrder()
            .title(UPDATED_TITLE)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .serviceHourlyRate(UPDATED_SERVICE_HOURLY_RATE)
            .clientId(UPDATED_CLIENT_ID)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE);
        return serviceOrder;
    }

    @BeforeEach
    public void initTest() {
        serviceOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOrder() throws Exception {
        int databaseSizeBeforeCreate = serviceOrderRepository.findAll().size();
        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);
        restServiceOrderMockMvc.perform(post("/api/service-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceOrder in the database
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOrder testServiceOrder = serviceOrderList.get(serviceOrderList.size() - 1);
        assertThat(testServiceOrder.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testServiceOrder.getServiceDescription()).isEqualTo(DEFAULT_SERVICE_DESCRIPTION);
        assertThat(testServiceOrder.getServiceHourlyRate()).isEqualTo(DEFAULT_SERVICE_HOURLY_RATE);
        assertThat(testServiceOrder.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testServiceOrder.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createServiceOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOrderRepository.findAll().size();

        // Create the ServiceOrder with an existing ID
        serviceOrder.setId(1L);
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderMockMvc.perform(post("/api/service-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOrderRepository.findAll().size();
        // set the field null
        serviceOrder.setTitle(null);

        // Create the ServiceOrder, which fails.
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);


        restServiceOrderMockMvc.perform(post("/api/service-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceHourlyRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOrderRepository.findAll().size();
        // set the field null
        serviceOrder.setServiceHourlyRate(null);

        // Create the ServiceOrder, which fails.
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);


        restServiceOrderMockMvc.perform(post("/api/service-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOrderRepository.findAll().size();
        // set the field null
        serviceOrder.setClientId(null);

        // Create the ServiceOrder, which fails.
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);


        restServiceOrderMockMvc.perform(post("/api/service-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceOrders() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList
        restServiceOrderMockMvc.perform(get("/api/service-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].serviceHourlyRate").value(hasItem(DEFAULT_SERVICE_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getServiceOrder() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get the serviceOrder
        restServiceOrderMockMvc.perform(get("/api/service-orders/{id}", serviceOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrder.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.serviceDescription").value(DEFAULT_SERVICE_DESCRIPTION))
            .andExpect(jsonPath("$.serviceHourlyRate").value(DEFAULT_SERVICE_HOURLY_RATE.doubleValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)));
    }


    @Test
    @Transactional
    public void getServiceOrdersByIdFiltering() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        Long id = serviceOrder.getId();

        defaultServiceOrderShouldBeFound("id.equals=" + id);
        defaultServiceOrderShouldNotBeFound("id.notEquals=" + id);

        defaultServiceOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceOrderShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceOrdersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where title equals to DEFAULT_TITLE
        defaultServiceOrderShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the serviceOrderList where title equals to UPDATED_TITLE
        defaultServiceOrderShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where title not equals to DEFAULT_TITLE
        defaultServiceOrderShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the serviceOrderList where title not equals to UPDATED_TITLE
        defaultServiceOrderShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultServiceOrderShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the serviceOrderList where title equals to UPDATED_TITLE
        defaultServiceOrderShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where title is not null
        defaultServiceOrderShouldBeFound("title.specified=true");

        // Get all the serviceOrderList where title is null
        defaultServiceOrderShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceOrdersByTitleContainsSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where title contains DEFAULT_TITLE
        defaultServiceOrderShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the serviceOrderList where title contains UPDATED_TITLE
        defaultServiceOrderShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where title does not contain DEFAULT_TITLE
        defaultServiceOrderShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the serviceOrderList where title does not contain UPDATED_TITLE
        defaultServiceOrderShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllServiceOrdersByServiceDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceDescription equals to DEFAULT_SERVICE_DESCRIPTION
        defaultServiceOrderShouldBeFound("serviceDescription.equals=" + DEFAULT_SERVICE_DESCRIPTION);

        // Get all the serviceOrderList where serviceDescription equals to UPDATED_SERVICE_DESCRIPTION
        defaultServiceOrderShouldNotBeFound("serviceDescription.equals=" + UPDATED_SERVICE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceDescription not equals to DEFAULT_SERVICE_DESCRIPTION
        defaultServiceOrderShouldNotBeFound("serviceDescription.notEquals=" + DEFAULT_SERVICE_DESCRIPTION);

        // Get all the serviceOrderList where serviceDescription not equals to UPDATED_SERVICE_DESCRIPTION
        defaultServiceOrderShouldBeFound("serviceDescription.notEquals=" + UPDATED_SERVICE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceDescription in DEFAULT_SERVICE_DESCRIPTION or UPDATED_SERVICE_DESCRIPTION
        defaultServiceOrderShouldBeFound("serviceDescription.in=" + DEFAULT_SERVICE_DESCRIPTION + "," + UPDATED_SERVICE_DESCRIPTION);

        // Get all the serviceOrderList where serviceDescription equals to UPDATED_SERVICE_DESCRIPTION
        defaultServiceOrderShouldNotBeFound("serviceDescription.in=" + UPDATED_SERVICE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceDescription is not null
        defaultServiceOrderShouldBeFound("serviceDescription.specified=true");

        // Get all the serviceOrderList where serviceDescription is null
        defaultServiceOrderShouldNotBeFound("serviceDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceOrdersByServiceDescriptionContainsSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceDescription contains DEFAULT_SERVICE_DESCRIPTION
        defaultServiceOrderShouldBeFound("serviceDescription.contains=" + DEFAULT_SERVICE_DESCRIPTION);

        // Get all the serviceOrderList where serviceDescription contains UPDATED_SERVICE_DESCRIPTION
        defaultServiceOrderShouldNotBeFound("serviceDescription.contains=" + UPDATED_SERVICE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceDescription does not contain DEFAULT_SERVICE_DESCRIPTION
        defaultServiceOrderShouldNotBeFound("serviceDescription.doesNotContain=" + DEFAULT_SERVICE_DESCRIPTION);

        // Get all the serviceOrderList where serviceDescription does not contain UPDATED_SERVICE_DESCRIPTION
        defaultServiceOrderShouldBeFound("serviceDescription.doesNotContain=" + UPDATED_SERVICE_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllServiceOrdersByServiceHourlyRateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceHourlyRate equals to DEFAULT_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldBeFound("serviceHourlyRate.equals=" + DEFAULT_SERVICE_HOURLY_RATE);

        // Get all the serviceOrderList where serviceHourlyRate equals to UPDATED_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldNotBeFound("serviceHourlyRate.equals=" + UPDATED_SERVICE_HOURLY_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceHourlyRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceHourlyRate not equals to DEFAULT_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldNotBeFound("serviceHourlyRate.notEquals=" + DEFAULT_SERVICE_HOURLY_RATE);

        // Get all the serviceOrderList where serviceHourlyRate not equals to UPDATED_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldBeFound("serviceHourlyRate.notEquals=" + UPDATED_SERVICE_HOURLY_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceHourlyRateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceHourlyRate in DEFAULT_SERVICE_HOURLY_RATE or UPDATED_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldBeFound("serviceHourlyRate.in=" + DEFAULT_SERVICE_HOURLY_RATE + "," + UPDATED_SERVICE_HOURLY_RATE);

        // Get all the serviceOrderList where serviceHourlyRate equals to UPDATED_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldNotBeFound("serviceHourlyRate.in=" + UPDATED_SERVICE_HOURLY_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceHourlyRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceHourlyRate is not null
        defaultServiceOrderShouldBeFound("serviceHourlyRate.specified=true");

        // Get all the serviceOrderList where serviceHourlyRate is null
        defaultServiceOrderShouldNotBeFound("serviceHourlyRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceHourlyRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceHourlyRate is greater than or equal to DEFAULT_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldBeFound("serviceHourlyRate.greaterThanOrEqual=" + DEFAULT_SERVICE_HOURLY_RATE);

        // Get all the serviceOrderList where serviceHourlyRate is greater than or equal to UPDATED_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldNotBeFound("serviceHourlyRate.greaterThanOrEqual=" + UPDATED_SERVICE_HOURLY_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceHourlyRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceHourlyRate is less than or equal to DEFAULT_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldBeFound("serviceHourlyRate.lessThanOrEqual=" + DEFAULT_SERVICE_HOURLY_RATE);

        // Get all the serviceOrderList where serviceHourlyRate is less than or equal to SMALLER_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldNotBeFound("serviceHourlyRate.lessThanOrEqual=" + SMALLER_SERVICE_HOURLY_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceHourlyRateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceHourlyRate is less than DEFAULT_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldNotBeFound("serviceHourlyRate.lessThan=" + DEFAULT_SERVICE_HOURLY_RATE);

        // Get all the serviceOrderList where serviceHourlyRate is less than UPDATED_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldBeFound("serviceHourlyRate.lessThan=" + UPDATED_SERVICE_HOURLY_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceHourlyRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceHourlyRate is greater than DEFAULT_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldNotBeFound("serviceHourlyRate.greaterThan=" + DEFAULT_SERVICE_HOURLY_RATE);

        // Get all the serviceOrderList where serviceHourlyRate is greater than SMALLER_SERVICE_HOURLY_RATE
        defaultServiceOrderShouldBeFound("serviceHourlyRate.greaterThan=" + SMALLER_SERVICE_HOURLY_RATE);
    }


    @Test
    @Transactional
    public void getAllServiceOrdersByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where clientId equals to DEFAULT_CLIENT_ID
        defaultServiceOrderShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the serviceOrderList where clientId equals to UPDATED_CLIENT_ID
        defaultServiceOrderShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where clientId not equals to DEFAULT_CLIENT_ID
        defaultServiceOrderShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the serviceOrderList where clientId not equals to UPDATED_CLIENT_ID
        defaultServiceOrderShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultServiceOrderShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the serviceOrderList where clientId equals to UPDATED_CLIENT_ID
        defaultServiceOrderShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where clientId is not null
        defaultServiceOrderShouldBeFound("clientId.specified=true");

        // Get all the serviceOrderList where clientId is null
        defaultServiceOrderShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultServiceOrderShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the serviceOrderList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultServiceOrderShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultServiceOrderShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the serviceOrderList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultServiceOrderShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where clientId is less than DEFAULT_CLIENT_ID
        defaultServiceOrderShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the serviceOrderList where clientId is less than UPDATED_CLIENT_ID
        defaultServiceOrderShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where clientId is greater than DEFAULT_CLIENT_ID
        defaultServiceOrderShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the serviceOrderList where clientId is greater than SMALLER_CLIENT_ID
        defaultServiceOrderShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllServiceOrdersByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceOrderShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceOrderList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceOrderShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultServiceOrderShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceOrderList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceOrderShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultServiceOrderShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the serviceOrderList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultServiceOrderShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where lastUpdatedDate is not null
        defaultServiceOrderShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the serviceOrderList where lastUpdatedDate is null
        defaultServiceOrderShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceOrderShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceOrderList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultServiceOrderShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultServiceOrderShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceOrderList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultServiceOrderShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultServiceOrderShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceOrderList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultServiceOrderShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultServiceOrderShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the serviceOrderList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultServiceOrderShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceOrdersByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);
        Currency currency = CurrencyResourceIT.createEntity(em);
        em.persist(currency);
        em.flush();
        serviceOrder.setCurrency(currency);
        serviceOrderRepository.saveAndFlush(serviceOrder);
        Long currencyId = currency.getId();

        // Get all the serviceOrderList where currency equals to currencyId
        defaultServiceOrderShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the serviceOrderList where currency equals to currencyId + 1
        defaultServiceOrderShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOrderShouldBeFound(String filter) throws Exception {
        restServiceOrderMockMvc.perform(get("/api/service-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].serviceHourlyRate").value(hasItem(DEFAULT_SERVICE_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));

        // Check, that the count call also returns 1
        restServiceOrderMockMvc.perform(get("/api/service-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOrderShouldNotBeFound(String filter) throws Exception {
        restServiceOrderMockMvc.perform(get("/api/service-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOrderMockMvc.perform(get("/api/service-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingServiceOrder() throws Exception {
        // Get the serviceOrder
        restServiceOrderMockMvc.perform(get("/api/service-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOrder() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        int databaseSizeBeforeUpdate = serviceOrderRepository.findAll().size();

        // Update the serviceOrder
        ServiceOrder updatedServiceOrder = serviceOrderRepository.findById(serviceOrder.getId()).get();
        // Disconnect from session so that the updates on updatedServiceOrder are not directly saved in db
        em.detach(updatedServiceOrder);
        updatedServiceOrder
            .title(UPDATED_TITLE)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .serviceHourlyRate(UPDATED_SERVICE_HOURLY_RATE)
            .clientId(UPDATED_CLIENT_ID)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE);
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(updatedServiceOrder);

        restServiceOrderMockMvc.perform(put("/api/service-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceOrder in the database
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeUpdate);
        ServiceOrder testServiceOrder = serviceOrderList.get(serviceOrderList.size() - 1);
        assertThat(testServiceOrder.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testServiceOrder.getServiceDescription()).isEqualTo(UPDATED_SERVICE_DESCRIPTION);
        assertThat(testServiceOrder.getServiceHourlyRate()).isEqualTo(UPDATED_SERVICE_HOURLY_RATE);
        assertThat(testServiceOrder.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testServiceOrder.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOrder() throws Exception {
        int databaseSizeBeforeUpdate = serviceOrderRepository.findAll().size();

        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc.perform(put("/api/service-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceOrder() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        int databaseSizeBeforeDelete = serviceOrderRepository.findAll().size();

        // Delete the serviceOrder
        restServiceOrderMockMvc.perform(delete("/api/service-orders/{id}", serviceOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
