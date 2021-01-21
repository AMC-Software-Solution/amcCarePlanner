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

import com.amc.careplanner.domain.ServiceOrder;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.ServiceOrderRepository;
import com.amc.careplanner.service.dto.ServiceOrderCriteria;
import com.amc.careplanner.service.dto.ServiceOrderDTO;
import com.amc.careplanner.service.mapper.ServiceOrderMapper;

/**
 * Service for executing complex queries for {@link ServiceOrder} entities in the database.
 * The main input is a {@link ServiceOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceOrderDTO} or a {@link Page} of {@link ServiceOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceOrderQueryService extends QueryService<ServiceOrder> {

    private final Logger log = LoggerFactory.getLogger(ServiceOrderQueryService.class);

    private final ServiceOrderRepository serviceOrderRepository;

    private final ServiceOrderMapper serviceOrderMapper;

    public ServiceOrderQueryService(ServiceOrderRepository serviceOrderRepository, ServiceOrderMapper serviceOrderMapper) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceOrderMapper = serviceOrderMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceOrderDTO> findByCriteria(ServiceOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceOrder> specification = createSpecification(criteria);
        return serviceOrderMapper.toDto(serviceOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOrderDTO> findByCriteria(ServiceOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceOrder> specification = createSpecification(criteria);
        return serviceOrderRepository.findAll(specification, page)
            .map(serviceOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceOrder> specification = createSpecification(criteria);
        return serviceOrderRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceOrder> createSpecification(ServiceOrderCriteria criteria) {
        Specification<ServiceOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceOrder_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), ServiceOrder_.title));
            }
            if (criteria.getServiceDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceDescription(), ServiceOrder_.serviceDescription));
            }
            if (criteria.getServiceHourlyRate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceHourlyRate(), ServiceOrder_.serviceHourlyRate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), ServiceOrder_.clientId));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ServiceOrder_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), ServiceOrder_.lastUpdatedDate));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), ServiceOrder_.hasExtraData));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(ServiceOrder_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
