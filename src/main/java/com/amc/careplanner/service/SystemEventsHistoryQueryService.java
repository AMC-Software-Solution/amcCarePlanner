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

import com.amc.careplanner.domain.SystemEventsHistory;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.SystemEventsHistoryRepository;
import com.amc.careplanner.service.dto.SystemEventsHistoryCriteria;
import com.amc.careplanner.service.dto.SystemEventsHistoryDTO;
import com.amc.careplanner.service.mapper.SystemEventsHistoryMapper;

/**
 * Service for executing complex queries for {@link SystemEventsHistory} entities in the database.
 * The main input is a {@link SystemEventsHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SystemEventsHistoryDTO} or a {@link Page} of {@link SystemEventsHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemEventsHistoryQueryService extends QueryService<SystemEventsHistory> {

    private final Logger log = LoggerFactory.getLogger(SystemEventsHistoryQueryService.class);

    private final SystemEventsHistoryRepository systemEventsHistoryRepository;

    private final SystemEventsHistoryMapper systemEventsHistoryMapper;

    public SystemEventsHistoryQueryService(SystemEventsHistoryRepository systemEventsHistoryRepository, SystemEventsHistoryMapper systemEventsHistoryMapper) {
        this.systemEventsHistoryRepository = systemEventsHistoryRepository;
        this.systemEventsHistoryMapper = systemEventsHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link SystemEventsHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemEventsHistoryDTO> findByCriteria(SystemEventsHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SystemEventsHistory> specification = createSpecification(criteria);
        return systemEventsHistoryMapper.toDto(systemEventsHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SystemEventsHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemEventsHistoryDTO> findByCriteria(SystemEventsHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SystemEventsHistory> specification = createSpecification(criteria);
        return systemEventsHistoryRepository.findAll(specification, page)
            .map(systemEventsHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemEventsHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SystemEventsHistory> specification = createSpecification(criteria);
        return systemEventsHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link SystemEventsHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SystemEventsHistory> createSpecification(SystemEventsHistoryCriteria criteria) {
        Specification<SystemEventsHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SystemEventsHistory_.id));
            }
            if (criteria.getEventName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventName(), SystemEventsHistory_.eventName));
            }
            if (criteria.getEventDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventDate(), SystemEventsHistory_.eventDate));
            }
            if (criteria.getEventApi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventApi(), SystemEventsHistory_.eventApi));
            }
            if (criteria.getIpAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIpAddress(), SystemEventsHistory_.ipAddress));
            }
            if (criteria.getEventNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventNote(), SystemEventsHistory_.eventNote));
            }
            if (criteria.getEventEntityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventEntityName(), SystemEventsHistory_.eventEntityName));
            }
            if (criteria.getEventEntityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventEntityId(), SystemEventsHistory_.eventEntityId));
            }
            if (criteria.getIsSuspecious() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSuspecious(), SystemEventsHistory_.isSuspecious));
            }
            if (criteria.getCallerEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCallerEmail(), SystemEventsHistory_.callerEmail));
            }
            if (criteria.getCallerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCallerId(), SystemEventsHistory_.callerId));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), SystemEventsHistory_.clientId));
            }
            if (criteria.getTriggedById() != null) {
                specification = specification.and(buildSpecification(criteria.getTriggedById(),
                    root -> root.join(SystemEventsHistory_.triggedBy, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
