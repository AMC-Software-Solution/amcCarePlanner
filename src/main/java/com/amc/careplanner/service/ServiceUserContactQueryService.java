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

import com.amc.careplanner.domain.ServiceUserContact;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.ServiceUserContactRepository;
import com.amc.careplanner.service.dto.ServiceUserContactCriteria;
import com.amc.careplanner.service.dto.ServiceUserContactDTO;
import com.amc.careplanner.service.mapper.ServiceUserContactMapper;

/**
 * Service for executing complex queries for {@link ServiceUserContact} entities in the database.
 * The main input is a {@link ServiceUserContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceUserContactDTO} or a {@link Page} of {@link ServiceUserContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceUserContactQueryService extends QueryService<ServiceUserContact> {

    private final Logger log = LoggerFactory.getLogger(ServiceUserContactQueryService.class);

    private final ServiceUserContactRepository serviceUserContactRepository;

    private final ServiceUserContactMapper serviceUserContactMapper;

    public ServiceUserContactQueryService(ServiceUserContactRepository serviceUserContactRepository, ServiceUserContactMapper serviceUserContactMapper) {
        this.serviceUserContactRepository = serviceUserContactRepository;
        this.serviceUserContactMapper = serviceUserContactMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceUserContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceUserContactDTO> findByCriteria(ServiceUserContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceUserContact> specification = createSpecification(criteria);
        return serviceUserContactMapper.toDto(serviceUserContactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceUserContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceUserContactDTO> findByCriteria(ServiceUserContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceUserContact> specification = createSpecification(criteria);
        return serviceUserContactRepository.findAll(specification, page)
            .map(serviceUserContactMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceUserContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceUserContact> specification = createSpecification(criteria);
        return serviceUserContactRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceUserContactCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceUserContact> createSpecification(ServiceUserContactCriteria criteria) {
        Specification<ServiceUserContact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceUserContact_.id));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), ServiceUserContact_.address));
            }
            if (criteria.getCityOrTown() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCityOrTown(), ServiceUserContact_.cityOrTown));
            }
            if (criteria.getCounty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCounty(), ServiceUserContact_.county));
            }
            if (criteria.getPostCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostCode(), ServiceUserContact_.postCode));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), ServiceUserContact_.telephone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ServiceUserContact_.email));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), ServiceUserContact_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), ServiceUserContact_.tenantId));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(ServiceUserContact_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
        }
        return specification;
    }
}
