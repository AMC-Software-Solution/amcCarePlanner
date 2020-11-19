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

import com.amc.careplanner.domain.ServiceUserEvent;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.ServiceUserEventRepository;
import com.amc.careplanner.service.dto.ServiceUserEventCriteria;
import com.amc.careplanner.service.dto.ServiceUserEventDTO;
import com.amc.careplanner.service.mapper.ServiceUserEventMapper;

/**
 * Service for executing complex queries for {@link ServiceUserEvent} entities in the database.
 * The main input is a {@link ServiceUserEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceUserEventDTO} or a {@link Page} of {@link ServiceUserEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceUserEventQueryService extends QueryService<ServiceUserEvent> {

    private final Logger log = LoggerFactory.getLogger(ServiceUserEventQueryService.class);

    private final ServiceUserEventRepository serviceUserEventRepository;

    private final ServiceUserEventMapper serviceUserEventMapper;

    public ServiceUserEventQueryService(ServiceUserEventRepository serviceUserEventRepository, ServiceUserEventMapper serviceUserEventMapper) {
        this.serviceUserEventRepository = serviceUserEventRepository;
        this.serviceUserEventMapper = serviceUserEventMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceUserEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceUserEventDTO> findByCriteria(ServiceUserEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceUserEvent> specification = createSpecification(criteria);
        return serviceUserEventMapper.toDto(serviceUserEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceUserEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceUserEventDTO> findByCriteria(ServiceUserEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceUserEvent> specification = createSpecification(criteria);
        return serviceUserEventRepository.findAll(specification, page)
            .map(serviceUserEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceUserEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceUserEvent> specification = createSpecification(criteria);
        return serviceUserEventRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceUserEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceUserEvent> createSpecification(ServiceUserEventCriteria criteria) {
        Specification<ServiceUserEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceUserEvent_.id));
            }
            if (criteria.getEventTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventTitle(), ServiceUserEvent_.eventTitle));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ServiceUserEvent_.description));
            }
            if (criteria.getServiceUserEventStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserEventStatus(), ServiceUserEvent_.serviceUserEventStatus));
            }
            if (criteria.getServiceUserEventType() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserEventType(), ServiceUserEvent_.serviceUserEventType));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), ServiceUserEvent_.priority));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), ServiceUserEvent_.note));
            }
            if (criteria.getDateOfEvent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfEvent(), ServiceUserEvent_.dateOfEvent));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), ServiceUserEvent_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), ServiceUserEvent_.tenantId));
            }
            if (criteria.getReportedById() != null) {
                specification = specification.and(buildSpecification(criteria.getReportedById(),
                    root -> root.join(ServiceUserEvent_.reportedBy, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getAssignedToId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssignedToId(),
                    root -> root.join(ServiceUserEvent_.assignedTo, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(ServiceUserEvent_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
        }
        return specification;
    }
}
