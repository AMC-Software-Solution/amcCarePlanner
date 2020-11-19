package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Disability;
import com.amc.careplanner.domain.DisabilityType;
import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.DisabilityRepository;
import com.amc.careplanner.service.DisabilityService;
import com.amc.careplanner.service.dto.DisabilityDTO;
import com.amc.careplanner.service.mapper.DisabilityMapper;
import com.amc.careplanner.service.dto.DisabilityCriteria;
import com.amc.careplanner.service.DisabilityQueryService;

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
 * Integration tests for the {@link DisabilityResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DisabilityResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;
    private static final Long SMALLER_TENANT_ID = 1L - 1L;

    @Autowired
    private DisabilityRepository disabilityRepository;

    @Autowired
    private DisabilityMapper disabilityMapper;

    @Autowired
    private DisabilityService disabilityService;

    @Autowired
    private DisabilityQueryService disabilityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisabilityMockMvc;

    private Disability disability;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disability createEntity(EntityManager em) {
        Disability disability = new Disability()
            .note(DEFAULT_NOTE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .tenantId(DEFAULT_TENANT_ID);
        return disability;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disability createUpdatedEntity(EntityManager em) {
        Disability disability = new Disability()
            .note(UPDATED_NOTE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        return disability;
    }

    @BeforeEach
    public void initTest() {
        disability = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisability() throws Exception {
        int databaseSizeBeforeCreate = disabilityRepository.findAll().size();
        // Create the Disability
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(disability);
        restDisabilityMockMvc.perform(post("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Disability in the database
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeCreate + 1);
        Disability testDisability = disabilityList.get(disabilityList.size() - 1);
        assertThat(testDisability.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testDisability.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testDisability.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createDisabilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disabilityRepository.findAll().size();

        // Create the Disability with an existing ID
        disability.setId(1L);
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(disability);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisabilityMockMvc.perform(post("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disability in the database
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTenantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = disabilityRepository.findAll().size();
        // set the field null
        disability.setTenantId(null);

        // Create the Disability, which fails.
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(disability);


        restDisabilityMockMvc.perform(post("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isBadRequest());

        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDisabilities() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList
        restDisabilityMockMvc.perform(get("/api/disabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disability.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getDisability() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get the disability
        restDisabilityMockMvc.perform(get("/api/disabilities/{id}", disability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disability.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getDisabilitiesByIdFiltering() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        Long id = disability.getId();

        defaultDisabilityShouldBeFound("id.equals=" + id);
        defaultDisabilityShouldNotBeFound("id.notEquals=" + id);

        defaultDisabilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDisabilityShouldNotBeFound("id.greaterThan=" + id);

        defaultDisabilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDisabilityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note equals to DEFAULT_NOTE
        defaultDisabilityShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the disabilityList where note equals to UPDATED_NOTE
        defaultDisabilityShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note not equals to DEFAULT_NOTE
        defaultDisabilityShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the disabilityList where note not equals to UPDATED_NOTE
        defaultDisabilityShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultDisabilityShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the disabilityList where note equals to UPDATED_NOTE
        defaultDisabilityShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note is not null
        defaultDisabilityShouldBeFound("note.specified=true");

        // Get all the disabilityList where note is null
        defaultDisabilityShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllDisabilitiesByNoteContainsSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note contains DEFAULT_NOTE
        defaultDisabilityShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the disabilityList where note contains UPDATED_NOTE
        defaultDisabilityShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where note does not contain DEFAULT_NOTE
        defaultDisabilityShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the disabilityList where note does not contain UPDATED_NOTE
        defaultDisabilityShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is not null
        defaultDisabilityShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the disabilityList where lastUpdatedDate is null
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultDisabilityShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the disabilityList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultDisabilityShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where tenantId equals to DEFAULT_TENANT_ID
        defaultDisabilityShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the disabilityList where tenantId equals to UPDATED_TENANT_ID
        defaultDisabilityShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where tenantId not equals to DEFAULT_TENANT_ID
        defaultDisabilityShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the disabilityList where tenantId not equals to UPDATED_TENANT_ID
        defaultDisabilityShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultDisabilityShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the disabilityList where tenantId equals to UPDATED_TENANT_ID
        defaultDisabilityShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where tenantId is not null
        defaultDisabilityShouldBeFound("tenantId.specified=true");

        // Get all the disabilityList where tenantId is null
        defaultDisabilityShouldNotBeFound("tenantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByTenantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where tenantId is greater than or equal to DEFAULT_TENANT_ID
        defaultDisabilityShouldBeFound("tenantId.greaterThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the disabilityList where tenantId is greater than or equal to UPDATED_TENANT_ID
        defaultDisabilityShouldNotBeFound("tenantId.greaterThanOrEqual=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByTenantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where tenantId is less than or equal to DEFAULT_TENANT_ID
        defaultDisabilityShouldBeFound("tenantId.lessThanOrEqual=" + DEFAULT_TENANT_ID);

        // Get all the disabilityList where tenantId is less than or equal to SMALLER_TENANT_ID
        defaultDisabilityShouldNotBeFound("tenantId.lessThanOrEqual=" + SMALLER_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByTenantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where tenantId is less than DEFAULT_TENANT_ID
        defaultDisabilityShouldNotBeFound("tenantId.lessThan=" + DEFAULT_TENANT_ID);

        // Get all the disabilityList where tenantId is less than UPDATED_TENANT_ID
        defaultDisabilityShouldBeFound("tenantId.lessThan=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllDisabilitiesByTenantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        // Get all the disabilityList where tenantId is greater than DEFAULT_TENANT_ID
        defaultDisabilityShouldNotBeFound("tenantId.greaterThan=" + DEFAULT_TENANT_ID);

        // Get all the disabilityList where tenantId is greater than SMALLER_TENANT_ID
        defaultDisabilityShouldBeFound("tenantId.greaterThan=" + SMALLER_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByDisabilityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);
        DisabilityType disabilityType = DisabilityTypeResourceIT.createEntity(em);
        em.persist(disabilityType);
        em.flush();
        disability.setDisabilityType(disabilityType);
        disabilityRepository.saveAndFlush(disability);
        Long disabilityTypeId = disabilityType.getId();

        // Get all the disabilityList where disabilityType equals to disabilityTypeId
        defaultDisabilityShouldBeFound("disabilityTypeId.equals=" + disabilityTypeId);

        // Get all the disabilityList where disabilityType equals to disabilityTypeId + 1
        defaultDisabilityShouldNotBeFound("disabilityTypeId.equals=" + (disabilityTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllDisabilitiesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        disability.setEmployee(employee);
        disabilityRepository.saveAndFlush(disability);
        Long employeeId = employee.getId();

        // Get all the disabilityList where employee equals to employeeId
        defaultDisabilityShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the disabilityList where employee equals to employeeId + 1
        defaultDisabilityShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDisabilityShouldBeFound(String filter) throws Exception {
        restDisabilityMockMvc.perform(get("/api/disabilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disability.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())));

        // Check, that the count call also returns 1
        restDisabilityMockMvc.perform(get("/api/disabilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDisabilityShouldNotBeFound(String filter) throws Exception {
        restDisabilityMockMvc.perform(get("/api/disabilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisabilityMockMvc.perform(get("/api/disabilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDisability() throws Exception {
        // Get the disability
        restDisabilityMockMvc.perform(get("/api/disabilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisability() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        int databaseSizeBeforeUpdate = disabilityRepository.findAll().size();

        // Update the disability
        Disability updatedDisability = disabilityRepository.findById(disability.getId()).get();
        // Disconnect from session so that the updates on updatedDisability are not directly saved in db
        em.detach(updatedDisability);
        updatedDisability
            .note(UPDATED_NOTE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .tenantId(UPDATED_TENANT_ID);
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(updatedDisability);

        restDisabilityMockMvc.perform(put("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isOk());

        // Validate the Disability in the database
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeUpdate);
        Disability testDisability = disabilityList.get(disabilityList.size() - 1);
        assertThat(testDisability.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testDisability.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testDisability.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingDisability() throws Exception {
        int databaseSizeBeforeUpdate = disabilityRepository.findAll().size();

        // Create the Disability
        DisabilityDTO disabilityDTO = disabilityMapper.toDto(disability);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisabilityMockMvc.perform(put("/api/disabilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disability in the database
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisability() throws Exception {
        // Initialize the database
        disabilityRepository.saveAndFlush(disability);

        int databaseSizeBeforeDelete = disabilityRepository.findAll().size();

        // Delete the disability
        restDisabilityMockMvc.perform(delete("/api/disabilities/{id}", disability.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disability> disabilityList = disabilityRepository.findAll();
        assertThat(disabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
