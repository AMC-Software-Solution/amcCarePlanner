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

import com.amc.careplanner.domain.Travel;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.TravelRepository;
import com.amc.careplanner.service.dto.TravelCriteria;
import com.amc.careplanner.service.dto.TravelDTO;
import com.amc.careplanner.service.mapper.TravelMapper;

/**
 * Service for executing complex queries for {@link Travel} entities in the database.
 * The main input is a {@link TravelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TravelDTO} or a {@link Page} of {@link TravelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TravelQueryService extends QueryService<Travel> {

    private final Logger log = LoggerFactory.getLogger(TravelQueryService.class);

    private final TravelRepository travelRepository;

    private final TravelMapper travelMapper;

    public TravelQueryService(TravelRepository travelRepository, TravelMapper travelMapper) {
        this.travelRepository = travelRepository;
        this.travelMapper = travelMapper;
    }

    /**
     * Return a {@link List} of {@link TravelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TravelDTO> findByCriteria(TravelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Travel> specification = createSpecification(criteria);
        return travelMapper.toDto(travelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TravelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TravelDTO> findByCriteria(TravelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Travel> specification = createSpecification(criteria);
        return travelRepository.findAll(specification, page)
            .map(travelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TravelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Travel> specification = createSpecification(criteria);
        return travelRepository.count(specification);
    }

    /**
     * Function to convert {@link TravelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Travel> createSpecification(TravelCriteria criteria) {
        Specification<Travel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Travel_.id));
            }
            if (criteria.getTravelMode() != null) {
                specification = specification.and(buildSpecification(criteria.getTravelMode(), Travel_.travelMode));
            }
            if (criteria.getDistanceToDestination() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistanceToDestination(), Travel_.distanceToDestination));
            }
            if (criteria.getTimeToDestination() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeToDestination(), Travel_.timeToDestination));
            }
            if (criteria.getActualDistanceRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualDistanceRequired(), Travel_.actualDistanceRequired));
            }
            if (criteria.getActualTimeRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualTimeRequired(), Travel_.actualTimeRequired));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Travel_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), Travel_.tenantId));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskId(),
                    root -> root.join(Travel_.task, JoinType.LEFT).get(Task_.id)));
            }
        }
        return specification;
    }
}
