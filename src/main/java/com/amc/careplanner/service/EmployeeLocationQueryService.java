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

import com.amc.careplanner.domain.EmployeeLocation;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.EmployeeLocationRepository;
import com.amc.careplanner.service.dto.EmployeeLocationCriteria;
import com.amc.careplanner.service.dto.EmployeeLocationDTO;
import com.amc.careplanner.service.mapper.EmployeeLocationMapper;

/**
 * Service for executing complex queries for {@link EmployeeLocation} entities in the database.
 * The main input is a {@link EmployeeLocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeLocationDTO} or a {@link Page} of {@link EmployeeLocationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeLocationQueryService extends QueryService<EmployeeLocation> {

    private final Logger log = LoggerFactory.getLogger(EmployeeLocationQueryService.class);

    private final EmployeeLocationRepository employeeLocationRepository;

    private final EmployeeLocationMapper employeeLocationMapper;

    public EmployeeLocationQueryService(EmployeeLocationRepository employeeLocationRepository, EmployeeLocationMapper employeeLocationMapper) {
        this.employeeLocationRepository = employeeLocationRepository;
        this.employeeLocationMapper = employeeLocationMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeLocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeLocationDTO> findByCriteria(EmployeeLocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeLocation> specification = createSpecification(criteria);
        return employeeLocationMapper.toDto(employeeLocationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeLocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeLocationDTO> findByCriteria(EmployeeLocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeLocation> specification = createSpecification(criteria);
        return employeeLocationRepository.findAll(specification, page)
            .map(employeeLocationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeLocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeLocation> specification = createSpecification(criteria);
        return employeeLocationRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeLocationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeLocation> createSpecification(EmployeeLocationCriteria criteria) {
        Specification<EmployeeLocation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeLocation_.id));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLatitude(), EmployeeLocation_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongitude(), EmployeeLocation_.longitude));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), EmployeeLocation_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), EmployeeLocation_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), EmployeeLocation_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), EmployeeLocation_.hasExtraData));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(EmployeeLocation_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
