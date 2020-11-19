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

import com.amc.careplanner.domain.EligibilityType;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.EligibilityTypeRepository;
import com.amc.careplanner.service.dto.EligibilityTypeCriteria;
import com.amc.careplanner.service.dto.EligibilityTypeDTO;
import com.amc.careplanner.service.mapper.EligibilityTypeMapper;

/**
 * Service for executing complex queries for {@link EligibilityType} entities in the database.
 * The main input is a {@link EligibilityTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EligibilityTypeDTO} or a {@link Page} of {@link EligibilityTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EligibilityTypeQueryService extends QueryService<EligibilityType> {

    private final Logger log = LoggerFactory.getLogger(EligibilityTypeQueryService.class);

    private final EligibilityTypeRepository eligibilityTypeRepository;

    private final EligibilityTypeMapper eligibilityTypeMapper;

    public EligibilityTypeQueryService(EligibilityTypeRepository eligibilityTypeRepository, EligibilityTypeMapper eligibilityTypeMapper) {
        this.eligibilityTypeRepository = eligibilityTypeRepository;
        this.eligibilityTypeMapper = eligibilityTypeMapper;
    }

    /**
     * Return a {@link List} of {@link EligibilityTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EligibilityTypeDTO> findByCriteria(EligibilityTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EligibilityType> specification = createSpecification(criteria);
        return eligibilityTypeMapper.toDto(eligibilityTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EligibilityTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EligibilityTypeDTO> findByCriteria(EligibilityTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EligibilityType> specification = createSpecification(criteria);
        return eligibilityTypeRepository.findAll(specification, page)
            .map(eligibilityTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EligibilityTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EligibilityType> specification = createSpecification(criteria);
        return eligibilityTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link EligibilityTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EligibilityType> createSpecification(EligibilityTypeCriteria criteria) {
        Specification<EligibilityType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EligibilityType_.id));
            }
            if (criteria.getEligibilityType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEligibilityType(), EligibilityType_.eligibilityType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), EligibilityType_.description));
            }
        }
        return specification;
    }
}
