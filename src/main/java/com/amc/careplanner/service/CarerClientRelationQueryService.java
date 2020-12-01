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

import com.amc.careplanner.domain.CarerClientRelation;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.CarerClientRelationRepository;
import com.amc.careplanner.service.dto.CarerClientRelationCriteria;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;
import com.amc.careplanner.service.mapper.CarerClientRelationMapper;

/**
 * Service for executing complex queries for {@link CarerClientRelation} entities in the database.
 * The main input is a {@link CarerClientRelationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CarerClientRelationDTO} or a {@link Page} of {@link CarerClientRelationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CarerClientRelationQueryService extends QueryService<CarerClientRelation> {

    private final Logger log = LoggerFactory.getLogger(CarerClientRelationQueryService.class);

    private final CarerClientRelationRepository carerClientRelationRepository;

    private final CarerClientRelationMapper carerClientRelationMapper;

    public CarerClientRelationQueryService(CarerClientRelationRepository carerClientRelationRepository, CarerClientRelationMapper carerClientRelationMapper) {
        this.carerClientRelationRepository = carerClientRelationRepository;
        this.carerClientRelationMapper = carerClientRelationMapper;
    }

    /**
     * Return a {@link List} of {@link CarerClientRelationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CarerClientRelationDTO> findByCriteria(CarerClientRelationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CarerClientRelation> specification = createSpecification(criteria);
        return carerClientRelationMapper.toDto(carerClientRelationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CarerClientRelationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CarerClientRelationDTO> findByCriteria(CarerClientRelationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CarerClientRelation> specification = createSpecification(criteria);
        return carerClientRelationRepository.findAll(specification, page)
            .map(carerClientRelationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CarerClientRelationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CarerClientRelation> specification = createSpecification(criteria);
        return carerClientRelationRepository.count(specification);
    }

    /**
     * Function to convert {@link CarerClientRelationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CarerClientRelation> createSpecification(CarerClientRelationCriteria criteria) {
        Specification<CarerClientRelation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CarerClientRelation_.id));
            }
            if (criteria.getRelationType() != null) {
                specification = specification.and(buildSpecification(criteria.getRelationType(), CarerClientRelation_.relationType));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), CarerClientRelation_.reason));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), CarerClientRelation_.count));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), CarerClientRelation_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), CarerClientRelation_.clientId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(CarerClientRelation_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(CarerClientRelation_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
        }
        return specification;
    }
}
