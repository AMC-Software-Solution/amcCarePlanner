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

import com.amc.careplanner.domain.EmployeeDesignation;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.EmployeeDesignationRepository;
import com.amc.careplanner.service.dto.EmployeeDesignationCriteria;
import com.amc.careplanner.service.dto.EmployeeDesignationDTO;
import com.amc.careplanner.service.mapper.EmployeeDesignationMapper;

/**
 * Service for executing complex queries for {@link EmployeeDesignation} entities in the database.
 * The main input is a {@link EmployeeDesignationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDesignationDTO} or a {@link Page} of {@link EmployeeDesignationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeDesignationQueryService extends QueryService<EmployeeDesignation> {

    private final Logger log = LoggerFactory.getLogger(EmployeeDesignationQueryService.class);

    private final EmployeeDesignationRepository employeeDesignationRepository;

    private final EmployeeDesignationMapper employeeDesignationMapper;

    public EmployeeDesignationQueryService(EmployeeDesignationRepository employeeDesignationRepository, EmployeeDesignationMapper employeeDesignationMapper) {
        this.employeeDesignationRepository = employeeDesignationRepository;
        this.employeeDesignationMapper = employeeDesignationMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeDesignationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDesignationDTO> findByCriteria(EmployeeDesignationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeDesignation> specification = createSpecification(criteria);
        return employeeDesignationMapper.toDto(employeeDesignationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeDesignationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDesignationDTO> findByCriteria(EmployeeDesignationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeDesignation> specification = createSpecification(criteria);
        return employeeDesignationRepository.findAll(specification, page)
            .map(employeeDesignationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeDesignationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeDesignation> specification = createSpecification(criteria);
        return employeeDesignationRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeDesignationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeDesignation> createSpecification(EmployeeDesignationCriteria criteria) {
        Specification<EmployeeDesignation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeDesignation_.id));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), EmployeeDesignation_.designation));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), EmployeeDesignation_.description));
            }
            if (criteria.getDesignationDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignationDate(), EmployeeDesignation_.designationDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), EmployeeDesignation_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), EmployeeDesignation_.hasExtraData));
            }
        }
        return specification;
    }
}
