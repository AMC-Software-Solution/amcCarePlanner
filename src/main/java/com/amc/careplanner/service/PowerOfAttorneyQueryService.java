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

import com.amc.careplanner.domain.PowerOfAttorney;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.PowerOfAttorneyRepository;
import com.amc.careplanner.service.dto.PowerOfAttorneyCriteria;
import com.amc.careplanner.service.dto.PowerOfAttorneyDTO;
import com.amc.careplanner.service.mapper.PowerOfAttorneyMapper;

/**
 * Service for executing complex queries for {@link PowerOfAttorney} entities in the database.
 * The main input is a {@link PowerOfAttorneyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PowerOfAttorneyDTO} or a {@link Page} of {@link PowerOfAttorneyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PowerOfAttorneyQueryService extends QueryService<PowerOfAttorney> {

    private final Logger log = LoggerFactory.getLogger(PowerOfAttorneyQueryService.class);

    private final PowerOfAttorneyRepository powerOfAttorneyRepository;

    private final PowerOfAttorneyMapper powerOfAttorneyMapper;

    public PowerOfAttorneyQueryService(PowerOfAttorneyRepository powerOfAttorneyRepository, PowerOfAttorneyMapper powerOfAttorneyMapper) {
        this.powerOfAttorneyRepository = powerOfAttorneyRepository;
        this.powerOfAttorneyMapper = powerOfAttorneyMapper;
    }

    /**
     * Return a {@link List} of {@link PowerOfAttorneyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PowerOfAttorneyDTO> findByCriteria(PowerOfAttorneyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PowerOfAttorney> specification = createSpecification(criteria);
        return powerOfAttorneyMapper.toDto(powerOfAttorneyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PowerOfAttorneyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PowerOfAttorneyDTO> findByCriteria(PowerOfAttorneyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PowerOfAttorney> specification = createSpecification(criteria);
        return powerOfAttorneyRepository.findAll(specification, page)
            .map(powerOfAttorneyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PowerOfAttorneyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PowerOfAttorney> specification = createSpecification(criteria);
        return powerOfAttorneyRepository.count(specification);
    }

    /**
     * Function to convert {@link PowerOfAttorneyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PowerOfAttorney> createSpecification(PowerOfAttorneyCriteria criteria) {
        Specification<PowerOfAttorney> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PowerOfAttorney_.id));
            }
            if (criteria.getPowerOfAttorneyConsent() != null) {
                specification = specification.and(buildSpecification(criteria.getPowerOfAttorneyConsent(), PowerOfAttorney_.powerOfAttorneyConsent));
            }
            if (criteria.getHealthAndWelfare() != null) {
                specification = specification.and(buildSpecification(criteria.getHealthAndWelfare(), PowerOfAttorney_.healthAndWelfare));
            }
            if (criteria.getHealthAndWelfareName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHealthAndWelfareName(), PowerOfAttorney_.healthAndWelfareName));
            }
            if (criteria.getPropertyAndFinAffairs() != null) {
                specification = specification.and(buildSpecification(criteria.getPropertyAndFinAffairs(), PowerOfAttorney_.propertyAndFinAffairs));
            }
            if (criteria.getPropertyAndFinAffairsName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPropertyAndFinAffairsName(), PowerOfAttorney_.propertyAndFinAffairsName));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), PowerOfAttorney_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), PowerOfAttorney_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), PowerOfAttorney_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), PowerOfAttorney_.hasExtraData));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(PowerOfAttorney_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
            if (criteria.getWitnessedById() != null) {
                specification = specification.and(buildSpecification(criteria.getWitnessedById(),
                    root -> root.join(PowerOfAttorney_.witnessedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
