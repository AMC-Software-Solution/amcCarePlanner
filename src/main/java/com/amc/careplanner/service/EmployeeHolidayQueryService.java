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

import com.amc.careplanner.domain.EmployeeHoliday;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.EmployeeHolidayRepository;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.mapper.EmployeeHolidayMapper;

/**
 * Service for executing complex queries for {@link EmployeeHoliday} entities in the database.
 * The main input is a {@link EmployeeHolidayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeHolidayDTO} or a {@link Page} of {@link EmployeeHolidayDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeHolidayQueryService extends QueryService<EmployeeHoliday> {

    private final Logger log = LoggerFactory.getLogger(EmployeeHolidayQueryService.class);

    private final EmployeeHolidayRepository employeeHolidayRepository;

    private final EmployeeHolidayMapper employeeHolidayMapper;

    public EmployeeHolidayQueryService(EmployeeHolidayRepository employeeHolidayRepository, EmployeeHolidayMapper employeeHolidayMapper) {
        this.employeeHolidayRepository = employeeHolidayRepository;
        this.employeeHolidayMapper = employeeHolidayMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeHolidayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeHolidayDTO> findByCriteria(EmployeeHolidayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeHoliday> specification = createSpecification(criteria);
        return employeeHolidayMapper.toDto(employeeHolidayRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeHolidayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeHolidayDTO> findByCriteria(EmployeeHolidayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeHoliday> specification = createSpecification(criteria);
        return employeeHolidayRepository.findAll(specification, page)
            .map(employeeHolidayMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeHolidayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeHoliday> specification = createSpecification(criteria);
        return employeeHolidayRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeHolidayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeHoliday> createSpecification(EmployeeHolidayCriteria criteria) {
        Specification<EmployeeHoliday> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeHoliday_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), EmployeeHoliday_.description));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), EmployeeHoliday_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), EmployeeHoliday_.endDate));
            }
            if (criteria.getEmployeeHolidayType() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeHolidayType(), EmployeeHoliday_.employeeHolidayType));
            }
            if (criteria.getApprovedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApprovedDate(), EmployeeHoliday_.approvedDate));
            }
            if (criteria.getRequestedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestedDate(), EmployeeHoliday_.requestedDate));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), EmployeeHoliday_.approved));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), EmployeeHoliday_.note));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), EmployeeHoliday_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), EmployeeHoliday_.clientId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(EmployeeHoliday_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getApprovedById() != null) {
                specification = specification.and(buildSpecification(criteria.getApprovedById(),
                    root -> root.join(EmployeeHoliday_.approvedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
