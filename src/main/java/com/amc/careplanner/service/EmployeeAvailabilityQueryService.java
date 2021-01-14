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

import com.amc.careplanner.domain.EmployeeAvailability;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.EmployeeAvailabilityRepository;
import com.amc.careplanner.service.dto.EmployeeAvailabilityCriteria;
import com.amc.careplanner.service.dto.EmployeeAvailabilityDTO;
import com.amc.careplanner.service.mapper.EmployeeAvailabilityMapper;

/**
 * Service for executing complex queries for {@link EmployeeAvailability} entities in the database.
 * The main input is a {@link EmployeeAvailabilityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeAvailabilityDTO} or a {@link Page} of {@link EmployeeAvailabilityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeAvailabilityQueryService extends QueryService<EmployeeAvailability> {

    private final Logger log = LoggerFactory.getLogger(EmployeeAvailabilityQueryService.class);

    private final EmployeeAvailabilityRepository employeeAvailabilityRepository;

    private final EmployeeAvailabilityMapper employeeAvailabilityMapper;

    public EmployeeAvailabilityQueryService(EmployeeAvailabilityRepository employeeAvailabilityRepository, EmployeeAvailabilityMapper employeeAvailabilityMapper) {
        this.employeeAvailabilityRepository = employeeAvailabilityRepository;
        this.employeeAvailabilityMapper = employeeAvailabilityMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeAvailabilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeAvailabilityDTO> findByCriteria(EmployeeAvailabilityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeAvailability> specification = createSpecification(criteria);
        return employeeAvailabilityMapper.toDto(employeeAvailabilityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeAvailabilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeAvailabilityDTO> findByCriteria(EmployeeAvailabilityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeAvailability> specification = createSpecification(criteria);
        return employeeAvailabilityRepository.findAll(specification, page)
            .map(employeeAvailabilityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeAvailabilityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeAvailability> specification = createSpecification(criteria);
        return employeeAvailabilityRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeAvailabilityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeAvailability> createSpecification(EmployeeAvailabilityCriteria criteria) {
        Specification<EmployeeAvailability> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeAvailability_.id));
            }
            if (criteria.getAvailableForWork() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableForWork(), EmployeeAvailability_.availableForWork));
            }
            if (criteria.getAvailableMonday() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableMonday(), EmployeeAvailability_.availableMonday));
            }
            if (criteria.getAvailableTuesday() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableTuesday(), EmployeeAvailability_.availableTuesday));
            }
            if (criteria.getAvailableWednesday() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableWednesday(), EmployeeAvailability_.availableWednesday));
            }
            if (criteria.getAvailableThursday() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableThursday(), EmployeeAvailability_.availableThursday));
            }
            if (criteria.getAvailableFriday() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableFriday(), EmployeeAvailability_.availableFriday));
            }
            if (criteria.getAvailableSaturday() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableSaturday(), EmployeeAvailability_.availableSaturday));
            }
            if (criteria.getAvailableSunday() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableSunday(), EmployeeAvailability_.availableSunday));
            }
            if (criteria.getPreferredShift() != null) {
                specification = specification.and(buildSpecification(criteria.getPreferredShift(), EmployeeAvailability_.preferredShift));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), EmployeeAvailability_.hasExtraData));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), EmployeeAvailability_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), EmployeeAvailability_.clientId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(EmployeeAvailability_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
