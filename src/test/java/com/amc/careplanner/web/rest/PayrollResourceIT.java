package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Payroll;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.domain.Timesheet;
import com.amc.careplanner.repository.PayrollRepository;
import com.amc.careplanner.service.PayrollService;
import com.amc.careplanner.service.dto.PayrollDTO;
import com.amc.careplanner.service.mapper.PayrollMapper;
import com.amc.careplanner.service.dto.PayrollCriteria;
import com.amc.careplanner.service.PayrollQueryService;

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

import com.amc.careplanner.domain.enumeration.PayrollStatus;
/**
 * Integration tests for the {@link PayrollResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PayrollResourceIT {

    private static final ZonedDateTime DEFAULT_PAYMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PAYMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_PAYMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_PAY_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_PAY_PERIOD = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_HOURS_WORKED = 1;
    private static final Integer UPDATED_TOTAL_HOURS_WORKED = 2;
    private static final Integer SMALLER_TOTAL_HOURS_WORKED = 1 - 1;

    private static final String DEFAULT_GROSS_PAY = "AAAAAAAAAA";
    private static final String UPDATED_GROSS_PAY = "BBBBBBBBBB";

    private static final String DEFAULT_NET_PAY = "AAAAAAAAAA";
    private static final String UPDATED_NET_PAY = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_TAX = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_TAX = "BBBBBBBBBB";

    private static final PayrollStatus DEFAULT_PAYROLL_STATUS = PayrollStatus.CREATED;
    private static final PayrollStatus UPDATED_PAYROLL_STATUS = PayrollStatus.PROCESSING;

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
    private PayrollRepository payrollRepository;

    @Autowired
    private PayrollMapper payrollMapper;

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private PayrollQueryService payrollQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayrollMockMvc;

    private Payroll payroll;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payroll createEntity(EntityManager em) {
        Payroll payroll = new Payroll()
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .payPeriod(DEFAULT_PAY_PERIOD)
            .totalHoursWorked(DEFAULT_TOTAL_HOURS_WORKED)
            .grossPay(DEFAULT_GROSS_PAY)
            .netPay(DEFAULT_NET_PAY)
            .totalTax(DEFAULT_TOTAL_TAX)
            .payrollStatus(DEFAULT_PAYROLL_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return payroll;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payroll createUpdatedEntity(EntityManager em) {
        Payroll payroll = new Payroll()
            .paymentDate(UPDATED_PAYMENT_DATE)
            .payPeriod(UPDATED_PAY_PERIOD)
            .totalHoursWorked(UPDATED_TOTAL_HOURS_WORKED)
            .grossPay(UPDATED_GROSS_PAY)
            .netPay(UPDATED_NET_PAY)
            .totalTax(UPDATED_TOTAL_TAX)
            .payrollStatus(UPDATED_PAYROLL_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return payroll;
    }

    @BeforeEach
    public void initTest() {
        payroll = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayroll() throws Exception {
        int databaseSizeBeforeCreate = payrollRepository.findAll().size();
        // Create the Payroll
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);
        restPayrollMockMvc.perform(post("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isCreated());

        // Validate the Payroll in the database
        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeCreate + 1);
        Payroll testPayroll = payrollList.get(payrollList.size() - 1);
        assertThat(testPayroll.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPayroll.getPayPeriod()).isEqualTo(DEFAULT_PAY_PERIOD);
        assertThat(testPayroll.getTotalHoursWorked()).isEqualTo(DEFAULT_TOTAL_HOURS_WORKED);
        assertThat(testPayroll.getGrossPay()).isEqualTo(DEFAULT_GROSS_PAY);
        assertThat(testPayroll.getNetPay()).isEqualTo(DEFAULT_NET_PAY);
        assertThat(testPayroll.getTotalTax()).isEqualTo(DEFAULT_TOTAL_TAX);
        assertThat(testPayroll.getPayrollStatus()).isEqualTo(DEFAULT_PAYROLL_STATUS);
        assertThat(testPayroll.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayroll.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testPayroll.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testPayroll.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createPayrollWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollRepository.findAll().size();

        // Create the Payroll with an existing ID
        payroll.setId(1L);
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollMockMvc.perform(post("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payroll in the database
        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = payrollRepository.findAll().size();
        // set the field null
        payroll.setPaymentDate(null);

        // Create the Payroll, which fails.
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);


        restPayrollMockMvc.perform(post("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isBadRequest());

        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = payrollRepository.findAll().size();
        // set the field null
        payroll.setPayPeriod(null);

        // Create the Payroll, which fails.
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);


        restPayrollMockMvc.perform(post("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isBadRequest());

        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalHoursWorkedIsRequired() throws Exception {
        int databaseSizeBeforeTest = payrollRepository.findAll().size();
        // set the field null
        payroll.setTotalHoursWorked(null);

        // Create the Payroll, which fails.
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);


        restPayrollMockMvc.perform(post("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isBadRequest());

        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrossPayIsRequired() throws Exception {
        int databaseSizeBeforeTest = payrollRepository.findAll().size();
        // set the field null
        payroll.setGrossPay(null);

        // Create the Payroll, which fails.
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);


        restPayrollMockMvc.perform(post("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isBadRequest());

        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNetPayIsRequired() throws Exception {
        int databaseSizeBeforeTest = payrollRepository.findAll().size();
        // set the field null
        payroll.setNetPay(null);

        // Create the Payroll, which fails.
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);


        restPayrollMockMvc.perform(post("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isBadRequest());

        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayrollStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = payrollRepository.findAll().size();
        // set the field null
        payroll.setPayrollStatus(null);

        // Create the Payroll, which fails.
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);


        restPayrollMockMvc.perform(post("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isBadRequest());

        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = payrollRepository.findAll().size();
        // set the field null
        payroll.setClientId(null);

        // Create the Payroll, which fails.
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);


        restPayrollMockMvc.perform(post("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isBadRequest());

        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayrolls() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList
        restPayrollMockMvc.perform(get("/api/payrolls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payroll.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(sameInstant(DEFAULT_PAYMENT_DATE))))
            .andExpect(jsonPath("$.[*].payPeriod").value(hasItem(DEFAULT_PAY_PERIOD)))
            .andExpect(jsonPath("$.[*].totalHoursWorked").value(hasItem(DEFAULT_TOTAL_HOURS_WORKED)))
            .andExpect(jsonPath("$.[*].grossPay").value(hasItem(DEFAULT_GROSS_PAY)))
            .andExpect(jsonPath("$.[*].netPay").value(hasItem(DEFAULT_NET_PAY)))
            .andExpect(jsonPath("$.[*].totalTax").value(hasItem(DEFAULT_TOTAL_TAX)))
            .andExpect(jsonPath("$.[*].payrollStatus").value(hasItem(DEFAULT_PAYROLL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPayroll() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get the payroll
        restPayrollMockMvc.perform(get("/api/payrolls/{id}", payroll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payroll.getId().intValue()))
            .andExpect(jsonPath("$.paymentDate").value(sameInstant(DEFAULT_PAYMENT_DATE)))
            .andExpect(jsonPath("$.payPeriod").value(DEFAULT_PAY_PERIOD))
            .andExpect(jsonPath("$.totalHoursWorked").value(DEFAULT_TOTAL_HOURS_WORKED))
            .andExpect(jsonPath("$.grossPay").value(DEFAULT_GROSS_PAY))
            .andExpect(jsonPath("$.netPay").value(DEFAULT_NET_PAY))
            .andExpect(jsonPath("$.totalTax").value(DEFAULT_TOTAL_TAX))
            .andExpect(jsonPath("$.payrollStatus").value(DEFAULT_PAYROLL_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getPayrollsByIdFiltering() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        Long id = payroll.getId();

        defaultPayrollShouldBeFound("id.equals=" + id);
        defaultPayrollShouldNotBeFound("id.notEquals=" + id);

        defaultPayrollShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPayrollShouldNotBeFound("id.greaterThan=" + id);

        defaultPayrollShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPayrollShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPayrollsByPaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where paymentDate equals to DEFAULT_PAYMENT_DATE
        defaultPayrollShouldBeFound("paymentDate.equals=" + DEFAULT_PAYMENT_DATE);

        // Get all the payrollList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultPayrollShouldNotBeFound("paymentDate.equals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPaymentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where paymentDate not equals to DEFAULT_PAYMENT_DATE
        defaultPayrollShouldNotBeFound("paymentDate.notEquals=" + DEFAULT_PAYMENT_DATE);

        // Get all the payrollList where paymentDate not equals to UPDATED_PAYMENT_DATE
        defaultPayrollShouldBeFound("paymentDate.notEquals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where paymentDate in DEFAULT_PAYMENT_DATE or UPDATED_PAYMENT_DATE
        defaultPayrollShouldBeFound("paymentDate.in=" + DEFAULT_PAYMENT_DATE + "," + UPDATED_PAYMENT_DATE);

        // Get all the payrollList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultPayrollShouldNotBeFound("paymentDate.in=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where paymentDate is not null
        defaultPayrollShouldBeFound("paymentDate.specified=true");

        // Get all the payrollList where paymentDate is null
        defaultPayrollShouldNotBeFound("paymentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPayrollsByPaymentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where paymentDate is greater than or equal to DEFAULT_PAYMENT_DATE
        defaultPayrollShouldBeFound("paymentDate.greaterThanOrEqual=" + DEFAULT_PAYMENT_DATE);

        // Get all the payrollList where paymentDate is greater than or equal to UPDATED_PAYMENT_DATE
        defaultPayrollShouldNotBeFound("paymentDate.greaterThanOrEqual=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPaymentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where paymentDate is less than or equal to DEFAULT_PAYMENT_DATE
        defaultPayrollShouldBeFound("paymentDate.lessThanOrEqual=" + DEFAULT_PAYMENT_DATE);

        // Get all the payrollList where paymentDate is less than or equal to SMALLER_PAYMENT_DATE
        defaultPayrollShouldNotBeFound("paymentDate.lessThanOrEqual=" + SMALLER_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPaymentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where paymentDate is less than DEFAULT_PAYMENT_DATE
        defaultPayrollShouldNotBeFound("paymentDate.lessThan=" + DEFAULT_PAYMENT_DATE);

        // Get all the payrollList where paymentDate is less than UPDATED_PAYMENT_DATE
        defaultPayrollShouldBeFound("paymentDate.lessThan=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPaymentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where paymentDate is greater than DEFAULT_PAYMENT_DATE
        defaultPayrollShouldNotBeFound("paymentDate.greaterThan=" + DEFAULT_PAYMENT_DATE);

        // Get all the payrollList where paymentDate is greater than SMALLER_PAYMENT_DATE
        defaultPayrollShouldBeFound("paymentDate.greaterThan=" + SMALLER_PAYMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllPayrollsByPayPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payPeriod equals to DEFAULT_PAY_PERIOD
        defaultPayrollShouldBeFound("payPeriod.equals=" + DEFAULT_PAY_PERIOD);

        // Get all the payrollList where payPeriod equals to UPDATED_PAY_PERIOD
        defaultPayrollShouldNotBeFound("payPeriod.equals=" + UPDATED_PAY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPayPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payPeriod not equals to DEFAULT_PAY_PERIOD
        defaultPayrollShouldNotBeFound("payPeriod.notEquals=" + DEFAULT_PAY_PERIOD);

        // Get all the payrollList where payPeriod not equals to UPDATED_PAY_PERIOD
        defaultPayrollShouldBeFound("payPeriod.notEquals=" + UPDATED_PAY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPayPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payPeriod in DEFAULT_PAY_PERIOD or UPDATED_PAY_PERIOD
        defaultPayrollShouldBeFound("payPeriod.in=" + DEFAULT_PAY_PERIOD + "," + UPDATED_PAY_PERIOD);

        // Get all the payrollList where payPeriod equals to UPDATED_PAY_PERIOD
        defaultPayrollShouldNotBeFound("payPeriod.in=" + UPDATED_PAY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPayPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payPeriod is not null
        defaultPayrollShouldBeFound("payPeriod.specified=true");

        // Get all the payrollList where payPeriod is null
        defaultPayrollShouldNotBeFound("payPeriod.specified=false");
    }
                @Test
    @Transactional
    public void getAllPayrollsByPayPeriodContainsSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payPeriod contains DEFAULT_PAY_PERIOD
        defaultPayrollShouldBeFound("payPeriod.contains=" + DEFAULT_PAY_PERIOD);

        // Get all the payrollList where payPeriod contains UPDATED_PAY_PERIOD
        defaultPayrollShouldNotBeFound("payPeriod.contains=" + UPDATED_PAY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPayPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payPeriod does not contain DEFAULT_PAY_PERIOD
        defaultPayrollShouldNotBeFound("payPeriod.doesNotContain=" + DEFAULT_PAY_PERIOD);

        // Get all the payrollList where payPeriod does not contain UPDATED_PAY_PERIOD
        defaultPayrollShouldBeFound("payPeriod.doesNotContain=" + UPDATED_PAY_PERIOD);
    }


    @Test
    @Transactional
    public void getAllPayrollsByTotalHoursWorkedIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalHoursWorked equals to DEFAULT_TOTAL_HOURS_WORKED
        defaultPayrollShouldBeFound("totalHoursWorked.equals=" + DEFAULT_TOTAL_HOURS_WORKED);

        // Get all the payrollList where totalHoursWorked equals to UPDATED_TOTAL_HOURS_WORKED
        defaultPayrollShouldNotBeFound("totalHoursWorked.equals=" + UPDATED_TOTAL_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalHoursWorkedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalHoursWorked not equals to DEFAULT_TOTAL_HOURS_WORKED
        defaultPayrollShouldNotBeFound("totalHoursWorked.notEquals=" + DEFAULT_TOTAL_HOURS_WORKED);

        // Get all the payrollList where totalHoursWorked not equals to UPDATED_TOTAL_HOURS_WORKED
        defaultPayrollShouldBeFound("totalHoursWorked.notEquals=" + UPDATED_TOTAL_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalHoursWorkedIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalHoursWorked in DEFAULT_TOTAL_HOURS_WORKED or UPDATED_TOTAL_HOURS_WORKED
        defaultPayrollShouldBeFound("totalHoursWorked.in=" + DEFAULT_TOTAL_HOURS_WORKED + "," + UPDATED_TOTAL_HOURS_WORKED);

        // Get all the payrollList where totalHoursWorked equals to UPDATED_TOTAL_HOURS_WORKED
        defaultPayrollShouldNotBeFound("totalHoursWorked.in=" + UPDATED_TOTAL_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalHoursWorkedIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalHoursWorked is not null
        defaultPayrollShouldBeFound("totalHoursWorked.specified=true");

        // Get all the payrollList where totalHoursWorked is null
        defaultPayrollShouldNotBeFound("totalHoursWorked.specified=false");
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalHoursWorkedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalHoursWorked is greater than or equal to DEFAULT_TOTAL_HOURS_WORKED
        defaultPayrollShouldBeFound("totalHoursWorked.greaterThanOrEqual=" + DEFAULT_TOTAL_HOURS_WORKED);

        // Get all the payrollList where totalHoursWorked is greater than or equal to UPDATED_TOTAL_HOURS_WORKED
        defaultPayrollShouldNotBeFound("totalHoursWorked.greaterThanOrEqual=" + UPDATED_TOTAL_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalHoursWorkedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalHoursWorked is less than or equal to DEFAULT_TOTAL_HOURS_WORKED
        defaultPayrollShouldBeFound("totalHoursWorked.lessThanOrEqual=" + DEFAULT_TOTAL_HOURS_WORKED);

        // Get all the payrollList where totalHoursWorked is less than or equal to SMALLER_TOTAL_HOURS_WORKED
        defaultPayrollShouldNotBeFound("totalHoursWorked.lessThanOrEqual=" + SMALLER_TOTAL_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalHoursWorkedIsLessThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalHoursWorked is less than DEFAULT_TOTAL_HOURS_WORKED
        defaultPayrollShouldNotBeFound("totalHoursWorked.lessThan=" + DEFAULT_TOTAL_HOURS_WORKED);

        // Get all the payrollList where totalHoursWorked is less than UPDATED_TOTAL_HOURS_WORKED
        defaultPayrollShouldBeFound("totalHoursWorked.lessThan=" + UPDATED_TOTAL_HOURS_WORKED);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalHoursWorkedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalHoursWorked is greater than DEFAULT_TOTAL_HOURS_WORKED
        defaultPayrollShouldNotBeFound("totalHoursWorked.greaterThan=" + DEFAULT_TOTAL_HOURS_WORKED);

        // Get all the payrollList where totalHoursWorked is greater than SMALLER_TOTAL_HOURS_WORKED
        defaultPayrollShouldBeFound("totalHoursWorked.greaterThan=" + SMALLER_TOTAL_HOURS_WORKED);
    }


    @Test
    @Transactional
    public void getAllPayrollsByGrossPayIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where grossPay equals to DEFAULT_GROSS_PAY
        defaultPayrollShouldBeFound("grossPay.equals=" + DEFAULT_GROSS_PAY);

        // Get all the payrollList where grossPay equals to UPDATED_GROSS_PAY
        defaultPayrollShouldNotBeFound("grossPay.equals=" + UPDATED_GROSS_PAY);
    }

    @Test
    @Transactional
    public void getAllPayrollsByGrossPayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where grossPay not equals to DEFAULT_GROSS_PAY
        defaultPayrollShouldNotBeFound("grossPay.notEquals=" + DEFAULT_GROSS_PAY);

        // Get all the payrollList where grossPay not equals to UPDATED_GROSS_PAY
        defaultPayrollShouldBeFound("grossPay.notEquals=" + UPDATED_GROSS_PAY);
    }

    @Test
    @Transactional
    public void getAllPayrollsByGrossPayIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where grossPay in DEFAULT_GROSS_PAY or UPDATED_GROSS_PAY
        defaultPayrollShouldBeFound("grossPay.in=" + DEFAULT_GROSS_PAY + "," + UPDATED_GROSS_PAY);

        // Get all the payrollList where grossPay equals to UPDATED_GROSS_PAY
        defaultPayrollShouldNotBeFound("grossPay.in=" + UPDATED_GROSS_PAY);
    }

    @Test
    @Transactional
    public void getAllPayrollsByGrossPayIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where grossPay is not null
        defaultPayrollShouldBeFound("grossPay.specified=true");

        // Get all the payrollList where grossPay is null
        defaultPayrollShouldNotBeFound("grossPay.specified=false");
    }
                @Test
    @Transactional
    public void getAllPayrollsByGrossPayContainsSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where grossPay contains DEFAULT_GROSS_PAY
        defaultPayrollShouldBeFound("grossPay.contains=" + DEFAULT_GROSS_PAY);

        // Get all the payrollList where grossPay contains UPDATED_GROSS_PAY
        defaultPayrollShouldNotBeFound("grossPay.contains=" + UPDATED_GROSS_PAY);
    }

    @Test
    @Transactional
    public void getAllPayrollsByGrossPayNotContainsSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where grossPay does not contain DEFAULT_GROSS_PAY
        defaultPayrollShouldNotBeFound("grossPay.doesNotContain=" + DEFAULT_GROSS_PAY);

        // Get all the payrollList where grossPay does not contain UPDATED_GROSS_PAY
        defaultPayrollShouldBeFound("grossPay.doesNotContain=" + UPDATED_GROSS_PAY);
    }


    @Test
    @Transactional
    public void getAllPayrollsByNetPayIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where netPay equals to DEFAULT_NET_PAY
        defaultPayrollShouldBeFound("netPay.equals=" + DEFAULT_NET_PAY);

        // Get all the payrollList where netPay equals to UPDATED_NET_PAY
        defaultPayrollShouldNotBeFound("netPay.equals=" + UPDATED_NET_PAY);
    }

    @Test
    @Transactional
    public void getAllPayrollsByNetPayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where netPay not equals to DEFAULT_NET_PAY
        defaultPayrollShouldNotBeFound("netPay.notEquals=" + DEFAULT_NET_PAY);

        // Get all the payrollList where netPay not equals to UPDATED_NET_PAY
        defaultPayrollShouldBeFound("netPay.notEquals=" + UPDATED_NET_PAY);
    }

    @Test
    @Transactional
    public void getAllPayrollsByNetPayIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where netPay in DEFAULT_NET_PAY or UPDATED_NET_PAY
        defaultPayrollShouldBeFound("netPay.in=" + DEFAULT_NET_PAY + "," + UPDATED_NET_PAY);

        // Get all the payrollList where netPay equals to UPDATED_NET_PAY
        defaultPayrollShouldNotBeFound("netPay.in=" + UPDATED_NET_PAY);
    }

    @Test
    @Transactional
    public void getAllPayrollsByNetPayIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where netPay is not null
        defaultPayrollShouldBeFound("netPay.specified=true");

        // Get all the payrollList where netPay is null
        defaultPayrollShouldNotBeFound("netPay.specified=false");
    }
                @Test
    @Transactional
    public void getAllPayrollsByNetPayContainsSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where netPay contains DEFAULT_NET_PAY
        defaultPayrollShouldBeFound("netPay.contains=" + DEFAULT_NET_PAY);

        // Get all the payrollList where netPay contains UPDATED_NET_PAY
        defaultPayrollShouldNotBeFound("netPay.contains=" + UPDATED_NET_PAY);
    }

    @Test
    @Transactional
    public void getAllPayrollsByNetPayNotContainsSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where netPay does not contain DEFAULT_NET_PAY
        defaultPayrollShouldNotBeFound("netPay.doesNotContain=" + DEFAULT_NET_PAY);

        // Get all the payrollList where netPay does not contain UPDATED_NET_PAY
        defaultPayrollShouldBeFound("netPay.doesNotContain=" + UPDATED_NET_PAY);
    }


    @Test
    @Transactional
    public void getAllPayrollsByTotalTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalTax equals to DEFAULT_TOTAL_TAX
        defaultPayrollShouldBeFound("totalTax.equals=" + DEFAULT_TOTAL_TAX);

        // Get all the payrollList where totalTax equals to UPDATED_TOTAL_TAX
        defaultPayrollShouldNotBeFound("totalTax.equals=" + UPDATED_TOTAL_TAX);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalTax not equals to DEFAULT_TOTAL_TAX
        defaultPayrollShouldNotBeFound("totalTax.notEquals=" + DEFAULT_TOTAL_TAX);

        // Get all the payrollList where totalTax not equals to UPDATED_TOTAL_TAX
        defaultPayrollShouldBeFound("totalTax.notEquals=" + UPDATED_TOTAL_TAX);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalTaxIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalTax in DEFAULT_TOTAL_TAX or UPDATED_TOTAL_TAX
        defaultPayrollShouldBeFound("totalTax.in=" + DEFAULT_TOTAL_TAX + "," + UPDATED_TOTAL_TAX);

        // Get all the payrollList where totalTax equals to UPDATED_TOTAL_TAX
        defaultPayrollShouldNotBeFound("totalTax.in=" + UPDATED_TOTAL_TAX);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalTax is not null
        defaultPayrollShouldBeFound("totalTax.specified=true");

        // Get all the payrollList where totalTax is null
        defaultPayrollShouldNotBeFound("totalTax.specified=false");
    }
                @Test
    @Transactional
    public void getAllPayrollsByTotalTaxContainsSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalTax contains DEFAULT_TOTAL_TAX
        defaultPayrollShouldBeFound("totalTax.contains=" + DEFAULT_TOTAL_TAX);

        // Get all the payrollList where totalTax contains UPDATED_TOTAL_TAX
        defaultPayrollShouldNotBeFound("totalTax.contains=" + UPDATED_TOTAL_TAX);
    }

    @Test
    @Transactional
    public void getAllPayrollsByTotalTaxNotContainsSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where totalTax does not contain DEFAULT_TOTAL_TAX
        defaultPayrollShouldNotBeFound("totalTax.doesNotContain=" + DEFAULT_TOTAL_TAX);

        // Get all the payrollList where totalTax does not contain UPDATED_TOTAL_TAX
        defaultPayrollShouldBeFound("totalTax.doesNotContain=" + UPDATED_TOTAL_TAX);
    }


    @Test
    @Transactional
    public void getAllPayrollsByPayrollStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payrollStatus equals to DEFAULT_PAYROLL_STATUS
        defaultPayrollShouldBeFound("payrollStatus.equals=" + DEFAULT_PAYROLL_STATUS);

        // Get all the payrollList where payrollStatus equals to UPDATED_PAYROLL_STATUS
        defaultPayrollShouldNotBeFound("payrollStatus.equals=" + UPDATED_PAYROLL_STATUS);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPayrollStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payrollStatus not equals to DEFAULT_PAYROLL_STATUS
        defaultPayrollShouldNotBeFound("payrollStatus.notEquals=" + DEFAULT_PAYROLL_STATUS);

        // Get all the payrollList where payrollStatus not equals to UPDATED_PAYROLL_STATUS
        defaultPayrollShouldBeFound("payrollStatus.notEquals=" + UPDATED_PAYROLL_STATUS);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPayrollStatusIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payrollStatus in DEFAULT_PAYROLL_STATUS or UPDATED_PAYROLL_STATUS
        defaultPayrollShouldBeFound("payrollStatus.in=" + DEFAULT_PAYROLL_STATUS + "," + UPDATED_PAYROLL_STATUS);

        // Get all the payrollList where payrollStatus equals to UPDATED_PAYROLL_STATUS
        defaultPayrollShouldNotBeFound("payrollStatus.in=" + UPDATED_PAYROLL_STATUS);
    }

    @Test
    @Transactional
    public void getAllPayrollsByPayrollStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where payrollStatus is not null
        defaultPayrollShouldBeFound("payrollStatus.specified=true");

        // Get all the payrollList where payrollStatus is null
        defaultPayrollShouldNotBeFound("payrollStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllPayrollsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where createdDate equals to DEFAULT_CREATED_DATE
        defaultPayrollShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the payrollList where createdDate equals to UPDATED_CREATED_DATE
        defaultPayrollShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultPayrollShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the payrollList where createdDate not equals to UPDATED_CREATED_DATE
        defaultPayrollShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultPayrollShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the payrollList where createdDate equals to UPDATED_CREATED_DATE
        defaultPayrollShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where createdDate is not null
        defaultPayrollShouldBeFound("createdDate.specified=true");

        // Get all the payrollList where createdDate is null
        defaultPayrollShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPayrollsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultPayrollShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the payrollList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultPayrollShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultPayrollShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the payrollList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultPayrollShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where createdDate is less than DEFAULT_CREATED_DATE
        defaultPayrollShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the payrollList where createdDate is less than UPDATED_CREATED_DATE
        defaultPayrollShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultPayrollShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the payrollList where createdDate is greater than SMALLER_CREATED_DATE
        defaultPayrollShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllPayrollsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultPayrollShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the payrollList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultPayrollShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultPayrollShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the payrollList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultPayrollShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultPayrollShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the payrollList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultPayrollShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where lastUpdatedDate is not null
        defaultPayrollShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the payrollList where lastUpdatedDate is null
        defaultPayrollShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPayrollsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultPayrollShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the payrollList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultPayrollShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultPayrollShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the payrollList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultPayrollShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultPayrollShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the payrollList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultPayrollShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPayrollsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultPayrollShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the payrollList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultPayrollShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllPayrollsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where clientId equals to DEFAULT_CLIENT_ID
        defaultPayrollShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the payrollList where clientId equals to UPDATED_CLIENT_ID
        defaultPayrollShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPayrollsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where clientId not equals to DEFAULT_CLIENT_ID
        defaultPayrollShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the payrollList where clientId not equals to UPDATED_CLIENT_ID
        defaultPayrollShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPayrollsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultPayrollShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the payrollList where clientId equals to UPDATED_CLIENT_ID
        defaultPayrollShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPayrollsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where clientId is not null
        defaultPayrollShouldBeFound("clientId.specified=true");

        // Get all the payrollList where clientId is null
        defaultPayrollShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPayrollsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultPayrollShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the payrollList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultPayrollShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPayrollsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultPayrollShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the payrollList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultPayrollShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPayrollsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where clientId is less than DEFAULT_CLIENT_ID
        defaultPayrollShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the payrollList where clientId is less than UPDATED_CLIENT_ID
        defaultPayrollShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllPayrollsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where clientId is greater than DEFAULT_CLIENT_ID
        defaultPayrollShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the payrollList where clientId is greater than SMALLER_CLIENT_ID
        defaultPayrollShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllPayrollsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultPayrollShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the payrollList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultPayrollShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllPayrollsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultPayrollShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the payrollList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultPayrollShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllPayrollsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultPayrollShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the payrollList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultPayrollShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllPayrollsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        // Get all the payrollList where hasExtraData is not null
        defaultPayrollShouldBeFound("hasExtraData.specified=true");

        // Get all the payrollList where hasExtraData is null
        defaultPayrollShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllPayrollsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        payroll.setEmployee(employee);
        payrollRepository.saveAndFlush(payroll);
        Long employeeId = employee.getId();

        // Get all the payrollList where employee equals to employeeId
        defaultPayrollShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the payrollList where employee equals to employeeId + 1
        defaultPayrollShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllPayrollsByTimesheetIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);
        Timesheet timesheet = TimesheetResourceIT.createEntity(em);
        em.persist(timesheet);
        em.flush();
        payroll.setTimesheet(timesheet);
        payrollRepository.saveAndFlush(payroll);
        Long timesheetId = timesheet.getId();

        // Get all the payrollList where timesheet equals to timesheetId
        defaultPayrollShouldBeFound("timesheetId.equals=" + timesheetId);

        // Get all the payrollList where timesheet equals to timesheetId + 1
        defaultPayrollShouldNotBeFound("timesheetId.equals=" + (timesheetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPayrollShouldBeFound(String filter) throws Exception {
        restPayrollMockMvc.perform(get("/api/payrolls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payroll.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(sameInstant(DEFAULT_PAYMENT_DATE))))
            .andExpect(jsonPath("$.[*].payPeriod").value(hasItem(DEFAULT_PAY_PERIOD)))
            .andExpect(jsonPath("$.[*].totalHoursWorked").value(hasItem(DEFAULT_TOTAL_HOURS_WORKED)))
            .andExpect(jsonPath("$.[*].grossPay").value(hasItem(DEFAULT_GROSS_PAY)))
            .andExpect(jsonPath("$.[*].netPay").value(hasItem(DEFAULT_NET_PAY)))
            .andExpect(jsonPath("$.[*].totalTax").value(hasItem(DEFAULT_TOTAL_TAX)))
            .andExpect(jsonPath("$.[*].payrollStatus").value(hasItem(DEFAULT_PAYROLL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restPayrollMockMvc.perform(get("/api/payrolls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPayrollShouldNotBeFound(String filter) throws Exception {
        restPayrollMockMvc.perform(get("/api/payrolls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPayrollMockMvc.perform(get("/api/payrolls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPayroll() throws Exception {
        // Get the payroll
        restPayrollMockMvc.perform(get("/api/payrolls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayroll() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        int databaseSizeBeforeUpdate = payrollRepository.findAll().size();

        // Update the payroll
        Payroll updatedPayroll = payrollRepository.findById(payroll.getId()).get();
        // Disconnect from session so that the updates on updatedPayroll are not directly saved in db
        em.detach(updatedPayroll);
        updatedPayroll
            .paymentDate(UPDATED_PAYMENT_DATE)
            .payPeriod(UPDATED_PAY_PERIOD)
            .totalHoursWorked(UPDATED_TOTAL_HOURS_WORKED)
            .grossPay(UPDATED_GROSS_PAY)
            .netPay(UPDATED_NET_PAY)
            .totalTax(UPDATED_TOTAL_TAX)
            .payrollStatus(UPDATED_PAYROLL_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        PayrollDTO payrollDTO = payrollMapper.toDto(updatedPayroll);

        restPayrollMockMvc.perform(put("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isOk());

        // Validate the Payroll in the database
        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeUpdate);
        Payroll testPayroll = payrollList.get(payrollList.size() - 1);
        assertThat(testPayroll.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPayroll.getPayPeriod()).isEqualTo(UPDATED_PAY_PERIOD);
        assertThat(testPayroll.getTotalHoursWorked()).isEqualTo(UPDATED_TOTAL_HOURS_WORKED);
        assertThat(testPayroll.getGrossPay()).isEqualTo(UPDATED_GROSS_PAY);
        assertThat(testPayroll.getNetPay()).isEqualTo(UPDATED_NET_PAY);
        assertThat(testPayroll.getTotalTax()).isEqualTo(UPDATED_TOTAL_TAX);
        assertThat(testPayroll.getPayrollStatus()).isEqualTo(UPDATED_PAYROLL_STATUS);
        assertThat(testPayroll.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayroll.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testPayroll.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testPayroll.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingPayroll() throws Exception {
        int databaseSizeBeforeUpdate = payrollRepository.findAll().size();

        // Create the Payroll
        PayrollDTO payrollDTO = payrollMapper.toDto(payroll);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollMockMvc.perform(put("/api/payrolls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payroll in the database
        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayroll() throws Exception {
        // Initialize the database
        payrollRepository.saveAndFlush(payroll);

        int databaseSizeBeforeDelete = payrollRepository.findAll().size();

        // Delete the payroll
        restPayrollMockMvc.perform(delete("/api/payrolls/{id}", payroll.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payroll> payrollList = payrollRepository.findAll();
        assertThat(payrollList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
