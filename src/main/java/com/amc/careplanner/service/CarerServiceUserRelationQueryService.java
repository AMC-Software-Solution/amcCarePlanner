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

import com.amc.careplanner.domain.CarerServiceUserRelation;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.CarerServiceUserRelationRepository;
import com.amc.careplanner.service.dto.CarerServiceUserRelationCriteria;
import com.amc.careplanner.service.dto.CarerServiceUserRelationDTO;
import com.amc.careplanner.service.mapper.CarerServiceUserRelationMapper;

/**
 * Service for executing complex queries for {@link CarerServiceUserRelation} entities in the database.
 * The main input is a {@link CarerServiceUserRelationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CarerServiceUserRelationDTO} or a {@link Page} of {@link CarerServiceUserRelationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CarerServiceUserRelationQueryService extends QueryService<CarerServiceUserRelation> {

    private final Logger log = LoggerFactory.getLogger(CarerServiceUserRelationQueryService.class);

    private final CarerServiceUserRelationRepository carerServiceUserRelationRepository;

    private final CarerServiceUserRelationMapper carerServiceUserRelationMapper;

    public CarerServiceUserRelationQueryService(CarerServiceUserRelationRepository carerServiceUserRelationRepository, CarerServiceUserRelationMapper carerServiceUserRelationMapper) {
        this.carerServiceUserRelationRepository = carerServiceUserRelationRepository;
        this.carerServiceUserRelationMapper = carerServiceUserRelationMapper;
    }

    /**
     * Return a {@link List} of {@link CarerServiceUserRelationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CarerServiceUserRelationDTO> findByCriteria(CarerServiceUserRelationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CarerServiceUserRelation> specification = createSpecification(criteria);
        return carerServiceUserRelationMapper.toDto(carerServiceUserRelationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CarerServiceUserRelationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CarerServiceUserRelationDTO> findByCriteria(CarerServiceUserRelationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CarerServiceUserRelation> specification = createSpecification(criteria);
        return carerServiceUserRelationRepository.findAll(specification, page)
            .map(carerServiceUserRelationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CarerServiceUserRelationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CarerServiceUserRelation> specification = createSpecification(criteria);
        return carerServiceUserRelationRepository.count(specification);
    }

    /**
     * Function to convert {@link CarerServiceUserRelationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CarerServiceUserRelation> createSpecification(CarerServiceUserRelationCriteria criteria) {
        Specification<CarerServiceUserRelation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CarerServiceUserRelation_.id));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), CarerServiceUserRelation_.reason));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), CarerServiceUserRelation_.count));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CarerServiceUserRelation_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), CarerServiceUserRelation_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), CarerServiceUserRelation_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), CarerServiceUserRelation_.hasExtraData));
            }
            if (criteria.getRelationTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRelationTypeId(),
                    root -> root.join(CarerServiceUserRelation_.relationType, JoinType.LEFT).get(RelationshipType_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(CarerServiceUserRelation_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(CarerServiceUserRelation_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
        }
        return specification;
    }
}
