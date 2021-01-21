package com.amc.careplanner.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.amc.careplanner.domain.Payroll;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.PayrollRepository;
import com.amc.careplanner.service.dto.PayrollCriteria;
import com.amc.careplanner.service.dto.PayrollDTO;
import com.amc.careplanner.service.mapper.PayrollMapper;

/**
 * Service for executing complex queries for {@link Payroll} entities in the database.
 * The main input is a {@link PayrollCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PayrollDTO} or a {@link Page} of {@link PayrollDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PayrollQueryService extends QueryService<Payroll> {

    private final Logger log = LoggerFactory.getLogger(PayrollQueryService.class);

    private final PayrollRepository payrollRepository;

    private final PayrollMapper payrollMapper;

    public PayrollQueryService(PayrollRepository payrollRepository, PayrollMapper payrollMapper) {
        this.payrollRepository = payrollRepository;
        this.payrollMapper = payrollMapper;
    }

    /**
     * Return a {@link List} of {@link PayrollDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PayrollDTO> findByCriteria(PayrollCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Payroll> specification = createSpecification(criteria);
        return payrollMapper.toDto(payrollRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PayrollDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PayrollDTO> findByCriteria(PayrollCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Payroll> specification = createSpecification(criteria);
        return payrollRepository.findAll(specification, page)
            .map(payrollMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PayrollCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Payroll> specification = createSpecification(criteria);
        return payrollRepository.count(specification);
    }

    /**
     * Function to convert {@link PayrollCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Payroll> createSpecification(PayrollCriteria criteria) {
        Specification<Payroll> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Payroll_.id));
            }
            if (criteria.getPaymentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentDate(), Payroll_.paymentDate));
            }
            if (criteria.getPayPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPayPeriod(), Payroll_.payPeriod));
            }
            if (criteria.getTotalHoursWorked() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalHoursWorked(), Payroll_.totalHoursWorked));
            }
            if (criteria.getGrossPay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrossPay(), Payroll_.grossPay));
            }
            if (criteria.getNetPay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNetPay(), Payroll_.netPay));
            }
            if (criteria.getTotalTax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalTax(), Payroll_.totalTax));
            }
            if (criteria.getPayrollStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPayrollStatus(), Payroll_.payrollStatus));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Payroll_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Payroll_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), Payroll_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), Payroll_.hasExtraData));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Payroll_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getTimesheetId() != null) {
                specification = specification.and(buildSpecification(criteria.getTimesheetId(),
                    root -> root.join(Payroll_.timesheet, JoinType.LEFT).get(Timesheet_.id)));
            }
        }
        return specification;
    }
}
