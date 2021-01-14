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

import com.amc.careplanner.domain.RelationshipType;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.RelationshipTypeRepository;
import com.amc.careplanner.service.dto.RelationshipTypeCriteria;
import com.amc.careplanner.service.dto.RelationshipTypeDTO;
import com.amc.careplanner.service.mapper.RelationshipTypeMapper;

/**
 * Service for executing complex queries for {@link RelationshipType} entities in the database.
 * The main input is a {@link RelationshipTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RelationshipTypeDTO} or a {@link Page} of {@link RelationshipTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RelationshipTypeQueryService extends QueryService<RelationshipType> {

    private final Logger log = LoggerFactory.getLogger(RelationshipTypeQueryService.class);

    private final RelationshipTypeRepository relationshipTypeRepository;

    private final RelationshipTypeMapper relationshipTypeMapper;

    public RelationshipTypeQueryService(RelationshipTypeRepository relationshipTypeRepository, RelationshipTypeMapper relationshipTypeMapper) {
        this.relationshipTypeRepository = relationshipTypeRepository;
        this.relationshipTypeMapper = relationshipTypeMapper;
    }

    /**
     * Return a {@link List} of {@link RelationshipTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RelationshipTypeDTO> findByCriteria(RelationshipTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RelationshipType> specification = createSpecification(criteria);
        return relationshipTypeMapper.toDto(relationshipTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RelationshipTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RelationshipTypeDTO> findByCriteria(RelationshipTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RelationshipType> specification = createSpecification(criteria);
        return relationshipTypeRepository.findAll(specification, page)
            .map(relationshipTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RelationshipTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RelationshipType> specification = createSpecification(criteria);
        return relationshipTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link RelationshipTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RelationshipType> createSpecification(RelationshipTypeCriteria criteria) {
        Specification<RelationshipType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RelationshipType_.id));
            }
            if (criteria.getRelationType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelationType(), RelationshipType_.relationType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), RelationshipType_.description));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), RelationshipType_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), RelationshipType_.hasExtraData));
            }
        }
        return specification;
    }
}
