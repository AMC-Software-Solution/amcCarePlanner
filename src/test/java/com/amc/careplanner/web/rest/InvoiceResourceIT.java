package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Invoice;
import com.amc.careplanner.domain.ServiceOrder;
import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.InvoiceRepository;
import com.amc.careplanner.service.InvoiceService;
import com.amc.careplanner.service.dto.InvoiceDTO;
import com.amc.careplanner.service.mapper.InvoiceMapper;
import com.amc.careplanner.service.dto.InvoiceCriteria;
import com.amc.careplanner.service.InvoiceQueryService;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.amc.careplanner.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amc.careplanner.domain.enumeration.InvoiceStatus;
/**
 * Integration tests for the {@link InvoiceResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceResourceIT {

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_INVOICE_NUMBER = UUID.randomUUID();
    private static final UUID UPDATED_INVOICE_NUMBER = UUID.randomUUID();

    private static final ZonedDateTime DEFAULT_GENERATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_GENERATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_GENERATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_PAYMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PAYMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_PAYMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final InvoiceStatus DEFAULT_INVOICE_STATUS = InvoiceStatus.CREATED;
    private static final InvoiceStatus UPDATED_INVOICE_STATUS = InvoiceStatus.PAID;

    private static final Double DEFAULT_TAX = 1D;
    private static final Double UPDATED_TAX = 2D;
    private static final Double SMALLER_TAX = 1D - 1D;

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

    private static final String DEFAULT_ATTRIBUTE_6 = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_6 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_7 = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_7 = "BBBBBBBBBB";

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
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceQueryService invoiceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .description(DEFAULT_DESCRIPTION)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .generatedDate(DEFAULT_GENERATED_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .invoiceStatus(DEFAULT_INVOICE_STATUS)
            .tax(DEFAULT_TAX)
            .attribute1(DEFAULT_ATTRIBUTE_1)
            .attribute2(DEFAULT_ATTRIBUTE_2)
            .attribute3(DEFAULT_ATTRIBUTE_3)
            .attribute4(DEFAULT_ATTRIBUTE_4)
            .attribute5(DEFAULT_ATTRIBUTE_5)
            .attribute6(DEFAULT_ATTRIBUTE_6)
            .attribute7(DEFAULT_ATTRIBUTE_7)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return invoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createUpdatedEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .generatedDate(UPDATED_GENERATED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .invoiceStatus(UPDATED_INVOICE_STATUS)
            .tax(UPDATED_TAX)
            .attribute1(UPDATED_ATTRIBUTE_1)
            .attribute2(UPDATED_ATTRIBUTE_2)
            .attribute3(UPDATED_ATTRIBUTE_3)
            .attribute4(UPDATED_ATTRIBUTE_4)
            .attribute5(UPDATED_ATTRIBUTE_5)
            .attribute6(UPDATED_ATTRIBUTE_6)
            .attribute7(UPDATED_ATTRIBUTE_7)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return invoice;
    }

    @BeforeEach
    public void initTest() {
        invoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();
        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testInvoice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInvoice.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testInvoice.getGeneratedDate()).isEqualTo(DEFAULT_GENERATED_DATE);
        assertThat(testInvoice.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testInvoice.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testInvoice.getInvoiceStatus()).isEqualTo(DEFAULT_INVOICE_STATUS);
        assertThat(testInvoice.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testInvoice.getAttribute1()).isEqualTo(DEFAULT_ATTRIBUTE_1);
        assertThat(testInvoice.getAttribute2()).isEqualTo(DEFAULT_ATTRIBUTE_2);
        assertThat(testInvoice.getAttribute3()).isEqualTo(DEFAULT_ATTRIBUTE_3);
        assertThat(testInvoice.getAttribute4()).isEqualTo(DEFAULT_ATTRIBUTE_4);
        assertThat(testInvoice.getAttribute5()).isEqualTo(DEFAULT_ATTRIBUTE_5);
        assertThat(testInvoice.getAttribute6()).isEqualTo(DEFAULT_ATTRIBUTE_6);
        assertThat(testInvoice.getAttribute7()).isEqualTo(DEFAULT_ATTRIBUTE_7);
        assertThat(testInvoice.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInvoice.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testInvoice.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testInvoice.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice with an existing ID
        invoice.setId(1L);
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setTotalAmount(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvoiceNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setInvoiceNumber(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneratedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setGeneratedDate(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setDueDate(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvoiceStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setInvoiceStatus(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setClientId(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].generatedDate").value(hasItem(sameInstant(DEFAULT_GENERATED_DATE))))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(sameInstant(DEFAULT_PAYMENT_DATE))))
            .andExpect(jsonPath("$.[*].invoiceStatus").value(hasItem(DEFAULT_INVOICE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].attribute1").value(hasItem(DEFAULT_ATTRIBUTE_1)))
            .andExpect(jsonPath("$.[*].attribute2").value(hasItem(DEFAULT_ATTRIBUTE_2)))
            .andExpect(jsonPath("$.[*].attribute3").value(hasItem(DEFAULT_ATTRIBUTE_3)))
            .andExpect(jsonPath("$.[*].attribute4").value(hasItem(DEFAULT_ATTRIBUTE_4)))
            .andExpect(jsonPath("$.[*].attribute5").value(hasItem(DEFAULT_ATTRIBUTE_5)))
            .andExpect(jsonPath("$.[*].attribute6").value(hasItem(DEFAULT_ATTRIBUTE_6)))
            .andExpect(jsonPath("$.[*].attribute7").value(hasItem(DEFAULT_ATTRIBUTE_7)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER.toString()))
            .andExpect(jsonPath("$.generatedDate").value(sameInstant(DEFAULT_GENERATED_DATE)))
            .andExpect(jsonPath("$.dueDate").value(sameInstant(DEFAULT_DUE_DATE)))
            .andExpect(jsonPath("$.paymentDate").value(sameInstant(DEFAULT_PAYMENT_DATE)))
            .andExpect(jsonPath("$.invoiceStatus").value(DEFAULT_INVOICE_STATUS.toString()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.attribute1").value(DEFAULT_ATTRIBUTE_1))
            .andExpect(jsonPath("$.attribute2").value(DEFAULT_ATTRIBUTE_2))
            .andExpect(jsonPath("$.attribute3").value(DEFAULT_ATTRIBUTE_3))
            .andExpect(jsonPath("$.attribute4").value(DEFAULT_ATTRIBUTE_4))
            .andExpect(jsonPath("$.attribute5").value(DEFAULT_ATTRIBUTE_5))
            .andExpect(jsonPath("$.attribute6").value(DEFAULT_ATTRIBUTE_6))
            .andExpect(jsonPath("$.attribute7").value(DEFAULT_ATTRIBUTE_7))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getInvoicesByIdFiltering() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        Long id = invoice.getId();

        defaultInvoiceShouldBeFound("id.equals=" + id);
        defaultInvoiceShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvoicesByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultInvoiceShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultInvoiceShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where totalAmount not equals to DEFAULT_TOTAL_AMOUNT
        defaultInvoiceShouldNotBeFound("totalAmount.notEquals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceList where totalAmount not equals to UPDATED_TOTAL_AMOUNT
        defaultInvoiceShouldBeFound("totalAmount.notEquals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultInvoiceShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the invoiceList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultInvoiceShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where totalAmount is not null
        defaultInvoiceShouldBeFound("totalAmount.specified=true");

        // Get all the invoiceList where totalAmount is null
        defaultInvoiceShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where totalAmount is greater than or equal to DEFAULT_TOTAL_AMOUNT
        defaultInvoiceShouldBeFound("totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceList where totalAmount is greater than or equal to UPDATED_TOTAL_AMOUNT
        defaultInvoiceShouldNotBeFound("totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where totalAmount is less than or equal to DEFAULT_TOTAL_AMOUNT
        defaultInvoiceShouldBeFound("totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceList where totalAmount is less than or equal to SMALLER_TOTAL_AMOUNT
        defaultInvoiceShouldNotBeFound("totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where totalAmount is less than DEFAULT_TOTAL_AMOUNT
        defaultInvoiceShouldNotBeFound("totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceList where totalAmount is less than UPDATED_TOTAL_AMOUNT
        defaultInvoiceShouldBeFound("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where totalAmount is greater than DEFAULT_TOTAL_AMOUNT
        defaultInvoiceShouldNotBeFound("totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceList where totalAmount is greater than SMALLER_TOTAL_AMOUNT
        defaultInvoiceShouldBeFound("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllInvoicesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where description equals to DEFAULT_DESCRIPTION
        defaultInvoiceShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the invoiceList where description equals to UPDATED_DESCRIPTION
        defaultInvoiceShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where description not equals to DEFAULT_DESCRIPTION
        defaultInvoiceShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the invoiceList where description not equals to UPDATED_DESCRIPTION
        defaultInvoiceShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultInvoiceShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the invoiceList where description equals to UPDATED_DESCRIPTION
        defaultInvoiceShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where description is not null
        defaultInvoiceShouldBeFound("description.specified=true");

        // Get all the invoiceList where description is null
        defaultInvoiceShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where description contains DEFAULT_DESCRIPTION
        defaultInvoiceShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the invoiceList where description contains UPDATED_DESCRIPTION
        defaultInvoiceShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where description does not contain DEFAULT_DESCRIPTION
        defaultInvoiceShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the invoiceList where description does not contain UPDATED_DESCRIPTION
        defaultInvoiceShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber equals to DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoiceNumber.equals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoiceNumber.equals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber not equals to DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoiceNumber.notEquals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoiceNumber not equals to UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoiceNumber.notEquals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber in DEFAULT_INVOICE_NUMBER or UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoiceNumber.in=" + DEFAULT_INVOICE_NUMBER + "," + UPDATED_INVOICE_NUMBER);

        // Get all the invoiceList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoiceNumber.in=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber is not null
        defaultInvoiceShouldBeFound("invoiceNumber.specified=true");

        // Get all the invoiceList where invoiceNumber is null
        defaultInvoiceShouldNotBeFound("invoiceNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByGeneratedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where generatedDate equals to DEFAULT_GENERATED_DATE
        defaultInvoiceShouldBeFound("generatedDate.equals=" + DEFAULT_GENERATED_DATE);

        // Get all the invoiceList where generatedDate equals to UPDATED_GENERATED_DATE
        defaultInvoiceShouldNotBeFound("generatedDate.equals=" + UPDATED_GENERATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByGeneratedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where generatedDate not equals to DEFAULT_GENERATED_DATE
        defaultInvoiceShouldNotBeFound("generatedDate.notEquals=" + DEFAULT_GENERATED_DATE);

        // Get all the invoiceList where generatedDate not equals to UPDATED_GENERATED_DATE
        defaultInvoiceShouldBeFound("generatedDate.notEquals=" + UPDATED_GENERATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByGeneratedDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where generatedDate in DEFAULT_GENERATED_DATE or UPDATED_GENERATED_DATE
        defaultInvoiceShouldBeFound("generatedDate.in=" + DEFAULT_GENERATED_DATE + "," + UPDATED_GENERATED_DATE);

        // Get all the invoiceList where generatedDate equals to UPDATED_GENERATED_DATE
        defaultInvoiceShouldNotBeFound("generatedDate.in=" + UPDATED_GENERATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByGeneratedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where generatedDate is not null
        defaultInvoiceShouldBeFound("generatedDate.specified=true");

        // Get all the invoiceList where generatedDate is null
        defaultInvoiceShouldNotBeFound("generatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByGeneratedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where generatedDate is greater than or equal to DEFAULT_GENERATED_DATE
        defaultInvoiceShouldBeFound("generatedDate.greaterThanOrEqual=" + DEFAULT_GENERATED_DATE);

        // Get all the invoiceList where generatedDate is greater than or equal to UPDATED_GENERATED_DATE
        defaultInvoiceShouldNotBeFound("generatedDate.greaterThanOrEqual=" + UPDATED_GENERATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByGeneratedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where generatedDate is less than or equal to DEFAULT_GENERATED_DATE
        defaultInvoiceShouldBeFound("generatedDate.lessThanOrEqual=" + DEFAULT_GENERATED_DATE);

        // Get all the invoiceList where generatedDate is less than or equal to SMALLER_GENERATED_DATE
        defaultInvoiceShouldNotBeFound("generatedDate.lessThanOrEqual=" + SMALLER_GENERATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByGeneratedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where generatedDate is less than DEFAULT_GENERATED_DATE
        defaultInvoiceShouldNotBeFound("generatedDate.lessThan=" + DEFAULT_GENERATED_DATE);

        // Get all the invoiceList where generatedDate is less than UPDATED_GENERATED_DATE
        defaultInvoiceShouldBeFound("generatedDate.lessThan=" + UPDATED_GENERATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByGeneratedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where generatedDate is greater than DEFAULT_GENERATED_DATE
        defaultInvoiceShouldNotBeFound("generatedDate.greaterThan=" + DEFAULT_GENERATED_DATE);

        // Get all the invoiceList where generatedDate is greater than SMALLER_GENERATED_DATE
        defaultInvoiceShouldBeFound("generatedDate.greaterThan=" + SMALLER_GENERATED_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate equals to DEFAULT_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate equals to UPDATED_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate not equals to DEFAULT_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.notEquals=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate not equals to UPDATED_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.notEquals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the invoiceList where dueDate equals to UPDATED_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is not null
        defaultInvoiceShouldBeFound("dueDate.specified=true");

        // Get all the invoiceList where dueDate is null
        defaultInvoiceShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is less than DEFAULT_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is less than UPDATED_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is greater than DEFAULT_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is greater than SMALLER_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByPaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paymentDate equals to DEFAULT_PAYMENT_DATE
        defaultInvoiceShouldBeFound("paymentDate.equals=" + DEFAULT_PAYMENT_DATE);

        // Get all the invoiceList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultInvoiceShouldNotBeFound("paymentDate.equals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paymentDate not equals to DEFAULT_PAYMENT_DATE
        defaultInvoiceShouldNotBeFound("paymentDate.notEquals=" + DEFAULT_PAYMENT_DATE);

        // Get all the invoiceList where paymentDate not equals to UPDATED_PAYMENT_DATE
        defaultInvoiceShouldBeFound("paymentDate.notEquals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paymentDate in DEFAULT_PAYMENT_DATE or UPDATED_PAYMENT_DATE
        defaultInvoiceShouldBeFound("paymentDate.in=" + DEFAULT_PAYMENT_DATE + "," + UPDATED_PAYMENT_DATE);

        // Get all the invoiceList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultInvoiceShouldNotBeFound("paymentDate.in=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paymentDate is not null
        defaultInvoiceShouldBeFound("paymentDate.specified=true");

        // Get all the invoiceList where paymentDate is null
        defaultInvoiceShouldNotBeFound("paymentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paymentDate is greater than or equal to DEFAULT_PAYMENT_DATE
        defaultInvoiceShouldBeFound("paymentDate.greaterThanOrEqual=" + DEFAULT_PAYMENT_DATE);

        // Get all the invoiceList where paymentDate is greater than or equal to UPDATED_PAYMENT_DATE
        defaultInvoiceShouldNotBeFound("paymentDate.greaterThanOrEqual=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paymentDate is less than or equal to DEFAULT_PAYMENT_DATE
        defaultInvoiceShouldBeFound("paymentDate.lessThanOrEqual=" + DEFAULT_PAYMENT_DATE);

        // Get all the invoiceList where paymentDate is less than or equal to SMALLER_PAYMENT_DATE
        defaultInvoiceShouldNotBeFound("paymentDate.lessThanOrEqual=" + SMALLER_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paymentDate is less than DEFAULT_PAYMENT_DATE
        defaultInvoiceShouldNotBeFound("paymentDate.lessThan=" + DEFAULT_PAYMENT_DATE);

        // Get all the invoiceList where paymentDate is less than UPDATED_PAYMENT_DATE
        defaultInvoiceShouldBeFound("paymentDate.lessThan=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paymentDate is greater than DEFAULT_PAYMENT_DATE
        defaultInvoiceShouldNotBeFound("paymentDate.greaterThan=" + DEFAULT_PAYMENT_DATE);

        // Get all the invoiceList where paymentDate is greater than SMALLER_PAYMENT_DATE
        defaultInvoiceShouldBeFound("paymentDate.greaterThan=" + SMALLER_PAYMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceStatus equals to DEFAULT_INVOICE_STATUS
        defaultInvoiceShouldBeFound("invoiceStatus.equals=" + DEFAULT_INVOICE_STATUS);

        // Get all the invoiceList where invoiceStatus equals to UPDATED_INVOICE_STATUS
        defaultInvoiceShouldNotBeFound("invoiceStatus.equals=" + UPDATED_INVOICE_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceStatus not equals to DEFAULT_INVOICE_STATUS
        defaultInvoiceShouldNotBeFound("invoiceStatus.notEquals=" + DEFAULT_INVOICE_STATUS);

        // Get all the invoiceList where invoiceStatus not equals to UPDATED_INVOICE_STATUS
        defaultInvoiceShouldBeFound("invoiceStatus.notEquals=" + UPDATED_INVOICE_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceStatusIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceStatus in DEFAULT_INVOICE_STATUS or UPDATED_INVOICE_STATUS
        defaultInvoiceShouldBeFound("invoiceStatus.in=" + DEFAULT_INVOICE_STATUS + "," + UPDATED_INVOICE_STATUS);

        // Get all the invoiceList where invoiceStatus equals to UPDATED_INVOICE_STATUS
        defaultInvoiceShouldNotBeFound("invoiceStatus.in=" + UPDATED_INVOICE_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceStatus is not null
        defaultInvoiceShouldBeFound("invoiceStatus.specified=true");

        // Get all the invoiceList where invoiceStatus is null
        defaultInvoiceShouldNotBeFound("invoiceStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where tax equals to DEFAULT_TAX
        defaultInvoiceShouldBeFound("tax.equals=" + DEFAULT_TAX);

        // Get all the invoiceList where tax equals to UPDATED_TAX
        defaultInvoiceShouldNotBeFound("tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where tax not equals to DEFAULT_TAX
        defaultInvoiceShouldNotBeFound("tax.notEquals=" + DEFAULT_TAX);

        // Get all the invoiceList where tax not equals to UPDATED_TAX
        defaultInvoiceShouldBeFound("tax.notEquals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where tax in DEFAULT_TAX or UPDATED_TAX
        defaultInvoiceShouldBeFound("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX);

        // Get all the invoiceList where tax equals to UPDATED_TAX
        defaultInvoiceShouldNotBeFound("tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where tax is not null
        defaultInvoiceShouldBeFound("tax.specified=true");

        // Get all the invoiceList where tax is null
        defaultInvoiceShouldNotBeFound("tax.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where tax is greater than or equal to DEFAULT_TAX
        defaultInvoiceShouldBeFound("tax.greaterThanOrEqual=" + DEFAULT_TAX);

        // Get all the invoiceList where tax is greater than or equal to UPDATED_TAX
        defaultInvoiceShouldNotBeFound("tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where tax is less than or equal to DEFAULT_TAX
        defaultInvoiceShouldBeFound("tax.lessThanOrEqual=" + DEFAULT_TAX);

        // Get all the invoiceList where tax is less than or equal to SMALLER_TAX
        defaultInvoiceShouldNotBeFound("tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where tax is less than DEFAULT_TAX
        defaultInvoiceShouldNotBeFound("tax.lessThan=" + DEFAULT_TAX);

        // Get all the invoiceList where tax is less than UPDATED_TAX
        defaultInvoiceShouldBeFound("tax.lessThan=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where tax is greater than DEFAULT_TAX
        defaultInvoiceShouldNotBeFound("tax.greaterThan=" + DEFAULT_TAX);

        // Get all the invoiceList where tax is greater than SMALLER_TAX
        defaultInvoiceShouldBeFound("tax.greaterThan=" + SMALLER_TAX);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAttribute1IsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute1 equals to DEFAULT_ATTRIBUTE_1
        defaultInvoiceShouldBeFound("attribute1.equals=" + DEFAULT_ATTRIBUTE_1);

        // Get all the invoiceList where attribute1 equals to UPDATED_ATTRIBUTE_1
        defaultInvoiceShouldNotBeFound("attribute1.equals=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute1 not equals to DEFAULT_ATTRIBUTE_1
        defaultInvoiceShouldNotBeFound("attribute1.notEquals=" + DEFAULT_ATTRIBUTE_1);

        // Get all the invoiceList where attribute1 not equals to UPDATED_ATTRIBUTE_1
        defaultInvoiceShouldBeFound("attribute1.notEquals=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute1IsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute1 in DEFAULT_ATTRIBUTE_1 or UPDATED_ATTRIBUTE_1
        defaultInvoiceShouldBeFound("attribute1.in=" + DEFAULT_ATTRIBUTE_1 + "," + UPDATED_ATTRIBUTE_1);

        // Get all the invoiceList where attribute1 equals to UPDATED_ATTRIBUTE_1
        defaultInvoiceShouldNotBeFound("attribute1.in=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute1IsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute1 is not null
        defaultInvoiceShouldBeFound("attribute1.specified=true");

        // Get all the invoiceList where attribute1 is null
        defaultInvoiceShouldNotBeFound("attribute1.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAttribute1ContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute1 contains DEFAULT_ATTRIBUTE_1
        defaultInvoiceShouldBeFound("attribute1.contains=" + DEFAULT_ATTRIBUTE_1);

        // Get all the invoiceList where attribute1 contains UPDATED_ATTRIBUTE_1
        defaultInvoiceShouldNotBeFound("attribute1.contains=" + UPDATED_ATTRIBUTE_1);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute1NotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute1 does not contain DEFAULT_ATTRIBUTE_1
        defaultInvoiceShouldNotBeFound("attribute1.doesNotContain=" + DEFAULT_ATTRIBUTE_1);

        // Get all the invoiceList where attribute1 does not contain UPDATED_ATTRIBUTE_1
        defaultInvoiceShouldBeFound("attribute1.doesNotContain=" + UPDATED_ATTRIBUTE_1);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAttribute2IsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute2 equals to DEFAULT_ATTRIBUTE_2
        defaultInvoiceShouldBeFound("attribute2.equals=" + DEFAULT_ATTRIBUTE_2);

        // Get all the invoiceList where attribute2 equals to UPDATED_ATTRIBUTE_2
        defaultInvoiceShouldNotBeFound("attribute2.equals=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute2 not equals to DEFAULT_ATTRIBUTE_2
        defaultInvoiceShouldNotBeFound("attribute2.notEquals=" + DEFAULT_ATTRIBUTE_2);

        // Get all the invoiceList where attribute2 not equals to UPDATED_ATTRIBUTE_2
        defaultInvoiceShouldBeFound("attribute2.notEquals=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute2IsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute2 in DEFAULT_ATTRIBUTE_2 or UPDATED_ATTRIBUTE_2
        defaultInvoiceShouldBeFound("attribute2.in=" + DEFAULT_ATTRIBUTE_2 + "," + UPDATED_ATTRIBUTE_2);

        // Get all the invoiceList where attribute2 equals to UPDATED_ATTRIBUTE_2
        defaultInvoiceShouldNotBeFound("attribute2.in=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute2IsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute2 is not null
        defaultInvoiceShouldBeFound("attribute2.specified=true");

        // Get all the invoiceList where attribute2 is null
        defaultInvoiceShouldNotBeFound("attribute2.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAttribute2ContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute2 contains DEFAULT_ATTRIBUTE_2
        defaultInvoiceShouldBeFound("attribute2.contains=" + DEFAULT_ATTRIBUTE_2);

        // Get all the invoiceList where attribute2 contains UPDATED_ATTRIBUTE_2
        defaultInvoiceShouldNotBeFound("attribute2.contains=" + UPDATED_ATTRIBUTE_2);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute2NotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute2 does not contain DEFAULT_ATTRIBUTE_2
        defaultInvoiceShouldNotBeFound("attribute2.doesNotContain=" + DEFAULT_ATTRIBUTE_2);

        // Get all the invoiceList where attribute2 does not contain UPDATED_ATTRIBUTE_2
        defaultInvoiceShouldBeFound("attribute2.doesNotContain=" + UPDATED_ATTRIBUTE_2);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAttribute3IsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute3 equals to DEFAULT_ATTRIBUTE_3
        defaultInvoiceShouldBeFound("attribute3.equals=" + DEFAULT_ATTRIBUTE_3);

        // Get all the invoiceList where attribute3 equals to UPDATED_ATTRIBUTE_3
        defaultInvoiceShouldNotBeFound("attribute3.equals=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute3 not equals to DEFAULT_ATTRIBUTE_3
        defaultInvoiceShouldNotBeFound("attribute3.notEquals=" + DEFAULT_ATTRIBUTE_3);

        // Get all the invoiceList where attribute3 not equals to UPDATED_ATTRIBUTE_3
        defaultInvoiceShouldBeFound("attribute3.notEquals=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute3IsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute3 in DEFAULT_ATTRIBUTE_3 or UPDATED_ATTRIBUTE_3
        defaultInvoiceShouldBeFound("attribute3.in=" + DEFAULT_ATTRIBUTE_3 + "," + UPDATED_ATTRIBUTE_3);

        // Get all the invoiceList where attribute3 equals to UPDATED_ATTRIBUTE_3
        defaultInvoiceShouldNotBeFound("attribute3.in=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute3IsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute3 is not null
        defaultInvoiceShouldBeFound("attribute3.specified=true");

        // Get all the invoiceList where attribute3 is null
        defaultInvoiceShouldNotBeFound("attribute3.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAttribute3ContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute3 contains DEFAULT_ATTRIBUTE_3
        defaultInvoiceShouldBeFound("attribute3.contains=" + DEFAULT_ATTRIBUTE_3);

        // Get all the invoiceList where attribute3 contains UPDATED_ATTRIBUTE_3
        defaultInvoiceShouldNotBeFound("attribute3.contains=" + UPDATED_ATTRIBUTE_3);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute3NotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute3 does not contain DEFAULT_ATTRIBUTE_3
        defaultInvoiceShouldNotBeFound("attribute3.doesNotContain=" + DEFAULT_ATTRIBUTE_3);

        // Get all the invoiceList where attribute3 does not contain UPDATED_ATTRIBUTE_3
        defaultInvoiceShouldBeFound("attribute3.doesNotContain=" + UPDATED_ATTRIBUTE_3);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAttribute4IsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute4 equals to DEFAULT_ATTRIBUTE_4
        defaultInvoiceShouldBeFound("attribute4.equals=" + DEFAULT_ATTRIBUTE_4);

        // Get all the invoiceList where attribute4 equals to UPDATED_ATTRIBUTE_4
        defaultInvoiceShouldNotBeFound("attribute4.equals=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute4 not equals to DEFAULT_ATTRIBUTE_4
        defaultInvoiceShouldNotBeFound("attribute4.notEquals=" + DEFAULT_ATTRIBUTE_4);

        // Get all the invoiceList where attribute4 not equals to UPDATED_ATTRIBUTE_4
        defaultInvoiceShouldBeFound("attribute4.notEquals=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute4IsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute4 in DEFAULT_ATTRIBUTE_4 or UPDATED_ATTRIBUTE_4
        defaultInvoiceShouldBeFound("attribute4.in=" + DEFAULT_ATTRIBUTE_4 + "," + UPDATED_ATTRIBUTE_4);

        // Get all the invoiceList where attribute4 equals to UPDATED_ATTRIBUTE_4
        defaultInvoiceShouldNotBeFound("attribute4.in=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute4IsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute4 is not null
        defaultInvoiceShouldBeFound("attribute4.specified=true");

        // Get all the invoiceList where attribute4 is null
        defaultInvoiceShouldNotBeFound("attribute4.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAttribute4ContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute4 contains DEFAULT_ATTRIBUTE_4
        defaultInvoiceShouldBeFound("attribute4.contains=" + DEFAULT_ATTRIBUTE_4);

        // Get all the invoiceList where attribute4 contains UPDATED_ATTRIBUTE_4
        defaultInvoiceShouldNotBeFound("attribute4.contains=" + UPDATED_ATTRIBUTE_4);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute4NotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute4 does not contain DEFAULT_ATTRIBUTE_4
        defaultInvoiceShouldNotBeFound("attribute4.doesNotContain=" + DEFAULT_ATTRIBUTE_4);

        // Get all the invoiceList where attribute4 does not contain UPDATED_ATTRIBUTE_4
        defaultInvoiceShouldBeFound("attribute4.doesNotContain=" + UPDATED_ATTRIBUTE_4);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAttribute5IsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute5 equals to DEFAULT_ATTRIBUTE_5
        defaultInvoiceShouldBeFound("attribute5.equals=" + DEFAULT_ATTRIBUTE_5);

        // Get all the invoiceList where attribute5 equals to UPDATED_ATTRIBUTE_5
        defaultInvoiceShouldNotBeFound("attribute5.equals=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute5 not equals to DEFAULT_ATTRIBUTE_5
        defaultInvoiceShouldNotBeFound("attribute5.notEquals=" + DEFAULT_ATTRIBUTE_5);

        // Get all the invoiceList where attribute5 not equals to UPDATED_ATTRIBUTE_5
        defaultInvoiceShouldBeFound("attribute5.notEquals=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute5IsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute5 in DEFAULT_ATTRIBUTE_5 or UPDATED_ATTRIBUTE_5
        defaultInvoiceShouldBeFound("attribute5.in=" + DEFAULT_ATTRIBUTE_5 + "," + UPDATED_ATTRIBUTE_5);

        // Get all the invoiceList where attribute5 equals to UPDATED_ATTRIBUTE_5
        defaultInvoiceShouldNotBeFound("attribute5.in=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute5IsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute5 is not null
        defaultInvoiceShouldBeFound("attribute5.specified=true");

        // Get all the invoiceList where attribute5 is null
        defaultInvoiceShouldNotBeFound("attribute5.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAttribute5ContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute5 contains DEFAULT_ATTRIBUTE_5
        defaultInvoiceShouldBeFound("attribute5.contains=" + DEFAULT_ATTRIBUTE_5);

        // Get all the invoiceList where attribute5 contains UPDATED_ATTRIBUTE_5
        defaultInvoiceShouldNotBeFound("attribute5.contains=" + UPDATED_ATTRIBUTE_5);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute5NotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute5 does not contain DEFAULT_ATTRIBUTE_5
        defaultInvoiceShouldNotBeFound("attribute5.doesNotContain=" + DEFAULT_ATTRIBUTE_5);

        // Get all the invoiceList where attribute5 does not contain UPDATED_ATTRIBUTE_5
        defaultInvoiceShouldBeFound("attribute5.doesNotContain=" + UPDATED_ATTRIBUTE_5);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAttribute6IsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute6 equals to DEFAULT_ATTRIBUTE_6
        defaultInvoiceShouldBeFound("attribute6.equals=" + DEFAULT_ATTRIBUTE_6);

        // Get all the invoiceList where attribute6 equals to UPDATED_ATTRIBUTE_6
        defaultInvoiceShouldNotBeFound("attribute6.equals=" + UPDATED_ATTRIBUTE_6);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute6IsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute6 not equals to DEFAULT_ATTRIBUTE_6
        defaultInvoiceShouldNotBeFound("attribute6.notEquals=" + DEFAULT_ATTRIBUTE_6);

        // Get all the invoiceList where attribute6 not equals to UPDATED_ATTRIBUTE_6
        defaultInvoiceShouldBeFound("attribute6.notEquals=" + UPDATED_ATTRIBUTE_6);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute6IsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute6 in DEFAULT_ATTRIBUTE_6 or UPDATED_ATTRIBUTE_6
        defaultInvoiceShouldBeFound("attribute6.in=" + DEFAULT_ATTRIBUTE_6 + "," + UPDATED_ATTRIBUTE_6);

        // Get all the invoiceList where attribute6 equals to UPDATED_ATTRIBUTE_6
        defaultInvoiceShouldNotBeFound("attribute6.in=" + UPDATED_ATTRIBUTE_6);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute6IsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute6 is not null
        defaultInvoiceShouldBeFound("attribute6.specified=true");

        // Get all the invoiceList where attribute6 is null
        defaultInvoiceShouldNotBeFound("attribute6.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAttribute6ContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute6 contains DEFAULT_ATTRIBUTE_6
        defaultInvoiceShouldBeFound("attribute6.contains=" + DEFAULT_ATTRIBUTE_6);

        // Get all the invoiceList where attribute6 contains UPDATED_ATTRIBUTE_6
        defaultInvoiceShouldNotBeFound("attribute6.contains=" + UPDATED_ATTRIBUTE_6);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute6NotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute6 does not contain DEFAULT_ATTRIBUTE_6
        defaultInvoiceShouldNotBeFound("attribute6.doesNotContain=" + DEFAULT_ATTRIBUTE_6);

        // Get all the invoiceList where attribute6 does not contain UPDATED_ATTRIBUTE_6
        defaultInvoiceShouldBeFound("attribute6.doesNotContain=" + UPDATED_ATTRIBUTE_6);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAttribute7IsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute7 equals to DEFAULT_ATTRIBUTE_7
        defaultInvoiceShouldBeFound("attribute7.equals=" + DEFAULT_ATTRIBUTE_7);

        // Get all the invoiceList where attribute7 equals to UPDATED_ATTRIBUTE_7
        defaultInvoiceShouldNotBeFound("attribute7.equals=" + UPDATED_ATTRIBUTE_7);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute7IsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute7 not equals to DEFAULT_ATTRIBUTE_7
        defaultInvoiceShouldNotBeFound("attribute7.notEquals=" + DEFAULT_ATTRIBUTE_7);

        // Get all the invoiceList where attribute7 not equals to UPDATED_ATTRIBUTE_7
        defaultInvoiceShouldBeFound("attribute7.notEquals=" + UPDATED_ATTRIBUTE_7);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute7IsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute7 in DEFAULT_ATTRIBUTE_7 or UPDATED_ATTRIBUTE_7
        defaultInvoiceShouldBeFound("attribute7.in=" + DEFAULT_ATTRIBUTE_7 + "," + UPDATED_ATTRIBUTE_7);

        // Get all the invoiceList where attribute7 equals to UPDATED_ATTRIBUTE_7
        defaultInvoiceShouldNotBeFound("attribute7.in=" + UPDATED_ATTRIBUTE_7);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute7IsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute7 is not null
        defaultInvoiceShouldBeFound("attribute7.specified=true");

        // Get all the invoiceList where attribute7 is null
        defaultInvoiceShouldNotBeFound("attribute7.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAttribute7ContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute7 contains DEFAULT_ATTRIBUTE_7
        defaultInvoiceShouldBeFound("attribute7.contains=" + DEFAULT_ATTRIBUTE_7);

        // Get all the invoiceList where attribute7 contains UPDATED_ATTRIBUTE_7
        defaultInvoiceShouldNotBeFound("attribute7.contains=" + UPDATED_ATTRIBUTE_7);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAttribute7NotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where attribute7 does not contain DEFAULT_ATTRIBUTE_7
        defaultInvoiceShouldNotBeFound("attribute7.doesNotContain=" + DEFAULT_ATTRIBUTE_7);

        // Get all the invoiceList where attribute7 does not contain UPDATED_ATTRIBUTE_7
        defaultInvoiceShouldBeFound("attribute7.doesNotContain=" + UPDATED_ATTRIBUTE_7);
    }


    @Test
    @Transactional
    public void getAllInvoicesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where createdDate equals to DEFAULT_CREATED_DATE
        defaultInvoiceShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceList where createdDate equals to UPDATED_CREATED_DATE
        defaultInvoiceShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultInvoiceShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceList where createdDate not equals to UPDATED_CREATED_DATE
        defaultInvoiceShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultInvoiceShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the invoiceList where createdDate equals to UPDATED_CREATED_DATE
        defaultInvoiceShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where createdDate is not null
        defaultInvoiceShouldBeFound("createdDate.specified=true");

        // Get all the invoiceList where createdDate is null
        defaultInvoiceShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultInvoiceShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultInvoiceShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultInvoiceShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultInvoiceShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where createdDate is less than DEFAULT_CREATED_DATE
        defaultInvoiceShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceList where createdDate is less than UPDATED_CREATED_DATE
        defaultInvoiceShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultInvoiceShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceList where createdDate is greater than SMALLER_CREATED_DATE
        defaultInvoiceShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultInvoiceShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the invoiceList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultInvoiceShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultInvoiceShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the invoiceList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultInvoiceShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultInvoiceShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the invoiceList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultInvoiceShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where lastUpdatedDate is not null
        defaultInvoiceShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the invoiceList where lastUpdatedDate is null
        defaultInvoiceShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultInvoiceShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the invoiceList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultInvoiceShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultInvoiceShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the invoiceList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultInvoiceShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultInvoiceShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the invoiceList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultInvoiceShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultInvoiceShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the invoiceList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultInvoiceShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where clientId equals to DEFAULT_CLIENT_ID
        defaultInvoiceShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the invoiceList where clientId equals to UPDATED_CLIENT_ID
        defaultInvoiceShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where clientId not equals to DEFAULT_CLIENT_ID
        defaultInvoiceShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the invoiceList where clientId not equals to UPDATED_CLIENT_ID
        defaultInvoiceShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultInvoiceShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the invoiceList where clientId equals to UPDATED_CLIENT_ID
        defaultInvoiceShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where clientId is not null
        defaultInvoiceShouldBeFound("clientId.specified=true");

        // Get all the invoiceList where clientId is null
        defaultInvoiceShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultInvoiceShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the invoiceList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultInvoiceShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultInvoiceShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the invoiceList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultInvoiceShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where clientId is less than DEFAULT_CLIENT_ID
        defaultInvoiceShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the invoiceList where clientId is less than UPDATED_CLIENT_ID
        defaultInvoiceShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where clientId is greater than DEFAULT_CLIENT_ID
        defaultInvoiceShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the invoiceList where clientId is greater than SMALLER_CLIENT_ID
        defaultInvoiceShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllInvoicesByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultInvoiceShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the invoiceList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultInvoiceShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllInvoicesByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultInvoiceShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the invoiceList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultInvoiceShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllInvoicesByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultInvoiceShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the invoiceList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultInvoiceShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllInvoicesByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where hasExtraData is not null
        defaultInvoiceShouldBeFound("hasExtraData.specified=true");

        // Get all the invoiceList where hasExtraData is null
        defaultInvoiceShouldNotBeFound("hasExtraData.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByServiceOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        ServiceOrder serviceOrder = ServiceOrderResourceIT.createEntity(em);
        em.persist(serviceOrder);
        em.flush();
        invoice.setServiceOrder(serviceOrder);
        invoiceRepository.saveAndFlush(invoice);
        Long serviceOrderId = serviceOrder.getId();

        // Get all the invoiceList where serviceOrder equals to serviceOrderId
        defaultInvoiceShouldBeFound("serviceOrderId.equals=" + serviceOrderId);

        // Get all the invoiceList where serviceOrder equals to serviceOrderId + 1
        defaultInvoiceShouldNotBeFound("serviceOrderId.equals=" + (serviceOrderId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByServiceUserIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        ServiceUser serviceUser = ServiceUserResourceIT.createEntity(em);
        em.persist(serviceUser);
        em.flush();
        invoice.setServiceUser(serviceUser);
        invoiceRepository.saveAndFlush(invoice);
        Long serviceUserId = serviceUser.getId();

        // Get all the invoiceList where serviceUser equals to serviceUserId
        defaultInvoiceShouldBeFound("serviceUserId.equals=" + serviceUserId);

        // Get all the invoiceList where serviceUser equals to serviceUserId + 1
        defaultInvoiceShouldNotBeFound("serviceUserId.equals=" + (serviceUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceShouldBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].generatedDate").value(hasItem(sameInstant(DEFAULT_GENERATED_DATE))))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(sameInstant(DEFAULT_PAYMENT_DATE))))
            .andExpect(jsonPath("$.[*].invoiceStatus").value(hasItem(DEFAULT_INVOICE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].attribute1").value(hasItem(DEFAULT_ATTRIBUTE_1)))
            .andExpect(jsonPath("$.[*].attribute2").value(hasItem(DEFAULT_ATTRIBUTE_2)))
            .andExpect(jsonPath("$.[*].attribute3").value(hasItem(DEFAULT_ATTRIBUTE_3)))
            .andExpect(jsonPath("$.[*].attribute4").value(hasItem(DEFAULT_ATTRIBUTE_4)))
            .andExpect(jsonPath("$.[*].attribute5").value(hasItem(DEFAULT_ATTRIBUTE_5)))
            .andExpect(jsonPath("$.[*].attribute6").value(hasItem(DEFAULT_ATTRIBUTE_6)))
            .andExpect(jsonPath("$.[*].attribute7").value(hasItem(DEFAULT_ATTRIBUTE_7)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceShouldNotBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).get();
        // Disconnect from session so that the updates on updatedInvoice are not directly saved in db
        em.detach(updatedInvoice);
        updatedInvoice
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .generatedDate(UPDATED_GENERATED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .invoiceStatus(UPDATED_INVOICE_STATUS)
            .tax(UPDATED_TAX)
            .attribute1(UPDATED_ATTRIBUTE_1)
            .attribute2(UPDATED_ATTRIBUTE_2)
            .attribute3(UPDATED_ATTRIBUTE_3)
            .attribute4(UPDATED_ATTRIBUTE_4)
            .attribute5(UPDATED_ATTRIBUTE_5)
            .attribute6(UPDATED_ATTRIBUTE_6)
            .attribute7(UPDATED_ATTRIBUTE_7)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(updatedInvoice);

        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testInvoice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoice.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testInvoice.getGeneratedDate()).isEqualTo(UPDATED_GENERATED_DATE);
        assertThat(testInvoice.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testInvoice.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testInvoice.getInvoiceStatus()).isEqualTo(UPDATED_INVOICE_STATUS);
        assertThat(testInvoice.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testInvoice.getAttribute1()).isEqualTo(UPDATED_ATTRIBUTE_1);
        assertThat(testInvoice.getAttribute2()).isEqualTo(UPDATED_ATTRIBUTE_2);
        assertThat(testInvoice.getAttribute3()).isEqualTo(UPDATED_ATTRIBUTE_3);
        assertThat(testInvoice.getAttribute4()).isEqualTo(UPDATED_ATTRIBUTE_4);
        assertThat(testInvoice.getAttribute5()).isEqualTo(UPDATED_ATTRIBUTE_5);
        assertThat(testInvoice.getAttribute6()).isEqualTo(UPDATED_ATTRIBUTE_6);
        assertThat(testInvoice.getAttribute7()).isEqualTo(UPDATED_ATTRIBUTE_7);
        assertThat(testInvoice.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInvoice.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testInvoice.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testInvoice.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Delete the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
