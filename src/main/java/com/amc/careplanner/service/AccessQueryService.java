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

import com.amc.careplanner.domain.Access;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.AccessRepository;
import com.amc.careplanner.service.dto.AccessCriteria;
import com.amc.careplanner.service.dto.AccessDTO;
import com.amc.careplanner.service.mapper.AccessMapper;

/**
 * Service for executing complex queries for {@link Access} entities in the database.
 * The main input is a {@link AccessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccessDTO} or a {@link Page} of {@link AccessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccessQueryService extends QueryService<Access> {

    private final Logger log = LoggerFactory.getLogger(AccessQueryService.class);

    private final AccessRepository accessRepository;

    private final AccessMapper accessMapper;

    public AccessQueryService(AccessRepository accessRepository, AccessMapper accessMapper) {
        this.accessRepository = accessRepository;
        this.accessMapper = accessMapper;
    }

    /**
     * Return a {@link List} of {@link AccessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccessDTO> findByCriteria(AccessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Access> specification = createSpecification(criteria);
        return accessMapper.toDto(accessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccessDTO> findByCriteria(AccessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Access> specification = createSpecification(criteria);
        return accessRepository.findAll(specification, page)
            .map(accessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Access> specification = createSpecification(criteria);
        return accessRepository.count(specification);
    }

    /**
     * Function to convert {@link AccessCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Access> createSpecification(AccessCriteria criteria) {
        Specification<Access> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Access_.id));
            }
            if (criteria.getKeySafeNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeySafeNumber(), Access_.keySafeNumber));
            }
            if (criteria.getAccessDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccessDetails(), Access_.accessDetails));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Access_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), Access_.clientId));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(Access_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
        }
        return specification;
    }
}
