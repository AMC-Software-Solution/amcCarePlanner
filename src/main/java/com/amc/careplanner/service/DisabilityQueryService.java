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

import com.amc.careplanner.domain.Disability;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.DisabilityRepository;
import com.amc.careplanner.service.dto.DisabilityCriteria;
import com.amc.careplanner.service.dto.DisabilityDTO;
import com.amc.careplanner.service.mapper.DisabilityMapper;

/**
 * Service for executing complex queries for {@link Disability} entities in the database.
 * The main input is a {@link DisabilityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DisabilityDTO} or a {@link Page} of {@link DisabilityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DisabilityQueryService extends QueryService<Disability> {

    private final Logger log = LoggerFactory.getLogger(DisabilityQueryService.class);

    private final DisabilityRepository disabilityRepository;

    private final DisabilityMapper disabilityMapper;

    public DisabilityQueryService(DisabilityRepository disabilityRepository, DisabilityMapper disabilityMapper) {
        this.disabilityRepository = disabilityRepository;
        this.disabilityMapper = disabilityMapper;
    }

    /**
     * Return a {@link List} of {@link DisabilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DisabilityDTO> findByCriteria(DisabilityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Disability> specification = createSpecification(criteria);
        return disabilityMapper.toDto(disabilityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DisabilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DisabilityDTO> findByCriteria(DisabilityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Disability> specification = createSpecification(criteria);
        return disabilityRepository.findAll(specification, page)
            .map(disabilityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DisabilityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Disability> specification = createSpecification(criteria);
        return disabilityRepository.count(specification);
    }

    /**
     * Function to convert {@link DisabilityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Disability> createSpecification(DisabilityCriteria criteria) {
        Specification<Disability> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Disability_.id));
            }
            if (criteria.getIsDisabled() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDisabled(), Disability_.isDisabled));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Disability_.note));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Disability_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Disability_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), Disability_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), Disability_.hasExtraData));
            }
            if (criteria.getDisabilityTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisabilityTypeId(),
                    root -> root.join(Disability_.disabilityType, JoinType.LEFT).get(DisabilityType_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Disability_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
