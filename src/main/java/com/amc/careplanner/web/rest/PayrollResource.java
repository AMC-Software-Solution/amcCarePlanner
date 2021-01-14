package com.amc.careplanner.web.rest;

import com.amc.careplanner.service.PayrollService;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.PayrollDTO;
import com.amc.careplanner.service.dto.PayrollCriteria;
import com.amc.careplanner.service.PayrollQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.Payroll}.
 */
@RestController
@RequestMapping("/api")
public class PayrollResource {

    private final Logger log = LoggerFactory.getLogger(PayrollResource.class);

    private static final String ENTITY_NAME = "payroll";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayrollService payrollService;

    private final PayrollQueryService payrollQueryService;

    public PayrollResource(PayrollService payrollService, PayrollQueryService payrollQueryService) {
        this.payrollService = payrollService;
        this.payrollQueryService = payrollQueryService;
    }

    /**
     * {@code POST  /payrolls} : Create a new payroll.
     *
     * @param payrollDTO the payrollDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payrollDTO, or with status {@code 400 (Bad Request)} if the payroll has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payrolls")
    public ResponseEntity<PayrollDTO> createPayroll(@Valid @RequestBody PayrollDTO payrollDTO) throws URISyntaxException {
        log.debug("REST request to save Payroll : {}", payrollDTO);
        if (payrollDTO.getId() != null) {
            throw new BadRequestAlertException("A new payroll cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollDTO result = payrollService.save(payrollDTO);
        return ResponseEntity.created(new URI("/api/payrolls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payrolls} : Updates an existing payroll.
     *
     * @param payrollDTO the payrollDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollDTO,
     * or with status {@code 400 (Bad Request)} if the payrollDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payrollDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payrolls")
    public ResponseEntity<PayrollDTO> updatePayroll(@Valid @RequestBody PayrollDTO payrollDTO) throws URISyntaxException {
        log.debug("REST request to update Payroll : {}", payrollDTO);
        if (payrollDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PayrollDTO result = payrollService.save(payrollDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payrolls} : get all the payrolls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payrolls in body.
     */
    @GetMapping("/payrolls")
    public ResponseEntity<List<PayrollDTO>> getAllPayrolls(PayrollCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Payrolls by criteria: {}", criteria);
        Page<PayrollDTO> page = payrollQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payrolls/count} : count all the payrolls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payrolls/count")
    public ResponseEntity<Long> countPayrolls(PayrollCriteria criteria) {
        log.debug("REST request to count Payrolls by criteria: {}", criteria);
        return ResponseEntity.ok().body(payrollQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payrolls/:id} : get the "id" payroll.
     *
     * @param id the id of the payrollDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payrollDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payrolls/{id}")
    public ResponseEntity<PayrollDTO> getPayroll(@PathVariable Long id) {
        log.debug("REST request to get Payroll : {}", id);
        Optional<PayrollDTO> payrollDTO = payrollService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payrollDTO);
    }

    /**
     * {@code DELETE  /payrolls/:id} : delete the "id" payroll.
     *
     * @param id the id of the payrollDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payrolls/{id}")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {
        log.debug("REST request to delete Payroll : {}", id);
        payrollService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
