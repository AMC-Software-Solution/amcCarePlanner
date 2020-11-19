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

import com.amc.careplanner.domain.Equality;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.EqualityRepository;
import com.amc.careplanner.service.dto.EqualityCriteria;
import com.amc.careplanner.service.dto.EqualityDTO;
import com.amc.careplanner.service.mapper.EqualityMapper;

/**
 * Service for executing complex queries for {@link Equality} entities in the database.
 * The main input is a {@link EqualityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EqualityDTO} or a {@link Page} of {@link EqualityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EqualityQueryService extends QueryService<Equality> {

    private final Logger log = LoggerFactory.getLogger(EqualityQueryService.class);

    private final EqualityRepository equalityRepository;

    private final EqualityMapper equalityMapper;

    public EqualityQueryService(EqualityRepository equalityRepository, EqualityMapper equalityMapper) {
        this.equalityRepository = equalityRepository;
        this.equalityMapper = equalityMapper;
    }

    /**
     * Return a {@link List} of {@link EqualityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EqualityDTO> findByCriteria(EqualityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Equality> specification = createSpecification(criteria);
        return equalityMapper.toDto(equalityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EqualityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EqualityDTO> findByCriteria(EqualityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Equality> specification = createSpecification(criteria);
        return equalityRepository.findAll(specification, page)
            .map(equalityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EqualityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Equality> specification = createSpecification(criteria);
        return equalityRepository.count(specification);
    }

    /**
     * Function to convert {@link EqualityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Equality> createSpecification(EqualityCriteria criteria) {
        Specification<Equality> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Equality_.id));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Equality_.gender));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getMaritalStatus(), Equality_.maritalStatus));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildSpecification(criteria.getReligion(), Equality_.religion));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Equality_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), Equality_.tenantId));
            }
            if (criteria.getNationalityId() != null) {
                specification = specification.and(buildSpecification(criteria.getNationalityId(),
                    root -> root.join(Equality_.nationality, JoinType.LEFT).get(Country_.id)));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(Equality_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
        }
        return specification;
    }
}
