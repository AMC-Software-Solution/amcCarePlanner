package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.ServiceOrder;
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

    private static final Double DEFAULT_SERVICE_RATE = 1D;
    private static final Double UPDATED_SERVICE_RATE = 2D;
    private static final Double SMALLER_SERVICE_RATE = 1D - 1D;

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

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
            .serviceRate(DEFAULT_SERVICE_RATE)
            .tenantId(DEFAULT_TENANT_ID)
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
            .serviceRate(UPDATED_SERVICE_RATE)
            .tenantId(UPDATED_TENANT_ID)
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
        assertThat(testServiceOrder.getServiceRate()).isEqualTo(DEFAULT_SERVICE_RATE);
        assertThat(testServiceOrder.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
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
    public void checkServiceRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOrderRepository.findAll().size();
        // set the field null
        serviceOrder.setServiceRate(null);

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
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOrderRepository.findAll().size();
        // set the field null
        serviceOrder.setTenantId(null);

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
            .andExpect(jsonPath("$.[*].serviceRate").value(hasItem(DEFAULT_SERVICE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())))
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
            .andExpect(jsonPath("$.serviceRate").value(DEFAULT_SERVICE_RATE.doubleValue()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()))
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
    public void getAllServiceOrdersByServiceRateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceRate equals to DEFAULT_SERVICE_RATE
        defaultServiceOrderShouldBeFound("serviceRate.equals=" + DEFAULT_SERVICE_RATE);

        // Get all the serviceOrderList where serviceRate equals to UPDATED_SERVICE_RATE
        defaultServiceOrderShouldNotBeFound("serviceRate.equals=" + UPDATED_SERVICE_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceRate not equals to DEFAULT_SERVICE_RATE
        defaultServiceOrderShouldNotBeFound("serviceRate.notEquals=" + DEFAULT_SERVICE_RATE);

        // Get all the serviceOrderList where serviceRate not equals to UPDATED_SERVICE_RATE
        defaultServiceOrderShouldBeFound("serviceRate.notEquals=" + UPDATED_SERVICE_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceRateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceRate in DEFAULT_SERVICE_RATE or UPDATED_SERVICE_RATE
        defaultServiceOrderShouldBeFound("serviceRate.in=" + DEFAULT_SERVICE_RATE + "," + UPDATED_SERVICE_RATE);

        // Get all the serviceOrderList where serviceRate equals to UPDATED_SERVICE_RATE
        defaultServiceOrderShouldNotBeFound("serviceRate.in=" + UPDATED_SERVICE_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceRate is not null
        defaultServiceOrderShouldBeFound("serviceRate.specified=true");

        // Get all the serviceOrderList where serviceRate is null
        defaultServiceOrderShouldNotBeFound("serviceRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceRate is greater than or equal to DEFAULT_SERVICE_RATE
        defaultServiceOrderShouldBeFound("serviceRate.greaterThanOrEqual=" + DEFAULT_SERVICE_RATE);

        // Get all the serviceOrderList where serviceRate is greater than or equal to UPDATED_SERVICE_RATE
        defaultServiceOrderShouldNotBeFound("serviceRate.greaterThanOrEqual=" + UPDATED_SERVICE_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceRate is less than or equal to DEFAULT_SERVICE_RATE
        defaultServiceOrderShouldBeFound("serviceRate.lessThanOrEqual=" + DEFAULT_SERVICE_RATE);

        // Get all the serviceOrderList where serviceRate is less than or equal to SMALLER_SERVICE_RATE
        defaultServiceOrderShouldNotBeFound("serviceRate.lessThanOrEqual=" + SMALLER_SERVICE_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceRateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceRate is less than DEFAULT_SERVICE_RATE
        defaultServiceOrderShouldNotBeFound("serviceRate.lessThan=" + DEFAULT_SERVICE_RATE);

        // Get all the serviceOrderList where serviceRate is less than UPDATED_SERVICE_RATE
        defaultServiceOrderShouldBeFound("serviceRate.lessThan=" + UPDATED_SERVICE_RATE);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByServiceRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceRate is greater than DEFAULT_SERVICE_RATE
        defaultServiceOrderShouldNotBeFound("serviceRate.greaterThan=" + DEFAULT_SERVICE_RATE);

        // Get all the serviceOrderList where serviceRate is greater than SMALLER_SERVICE_RATE
        defaultServiceOrderShouldBeFound("serviceRate.greaterThan=" + SMALLER_SERVICE_RATE);
    }


    @Test
    @Transactional
    public void getAllServiceOrdersByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tenantId equals to DEFAULT_TENANT_ID
        defaultServiceOrderShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the serviceOrderList where tenantId equals to UPDATED_TENANT_ID
        defaultServiceOrderShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tenantId not equals to DEFAULT_TENANT_ID
        defaultServiceOrderShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the serviceOrderList where tenantId not equals to UPDATED_TENANT_ID
        defaultServiceOrderShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultServiceOrderShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the serviceOrderList where tenantId equals to UPDATED_TENANT_ID
        defaultServiceOrderShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tenantId is not null
        defaultServiceOrderShouldBeFound("tenantId.specified=true");

        // Get all the serviceOrderList where tenantId is null
        defaultServiceOrderShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultServiceOrderShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the serviceOrderList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultServiceOrderShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultServiceOrderShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the serviceOrderList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultServiceOrderShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tenantId is less than DEFAULT_TENANT_ID
        defaultServiceOrderShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the serviceOrderList where tenantId is less than UPDATED_TENANT_ID
        defaultServiceOrderShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllServiceOrdersByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tenantId is greater than DEFAULT_TENANT_ID
        defaultServiceOrderShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the serviceOrderList where tenantId is greater than SMALLER_TENANT_ID
        defaultServiceOrderShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
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
            .andExpect(jsonPath("$.[*].serviceRate").value(hasItem(DEFAULT_SERVICE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())))
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
            .serviceRate(UPDATED_SERVICE_RATE)
            .tenantId(UPDATED_TENANT_ID)
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
        assertThat(testServiceOrder.getServiceRate()).isEqualTo(UPDATED_SERVICE_RATE);
        assertThat(testServiceOrder.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
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
