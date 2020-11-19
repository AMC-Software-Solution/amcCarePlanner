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

import com.amc.careplanner.domain.Tenant;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.TenantRepository;
import com.amc.careplanner.service.dto.TenantCriteria;
import com.amc.careplanner.service.dto.TenantDTO;
import com.amc.careplanner.service.mapper.TenantMapper;

/**
 * Service for executing complex queries for {@link Tenant} entities in the database.
 * The main input is a {@link TenantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TenantDTO} or a {@link Page} of {@link TenantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TenantQueryService extends QueryService<Tenant> {

    private final Logger log = LoggerFactory.getLogger(TenantQueryService.class);

    private final TenantRepository tenantRepository;

    private final TenantMapper tenantMapper;

    public TenantQueryService(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    /**
     * Return a {@link List} of {@link TenantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TenantDTO> findByCriteria(TenantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tenant> specification = createSpecification(criteria);
        return tenantMapper.toDto(tenantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TenantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TenantDTO> findByCriteria(TenantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tenant> specification = createSpecification(criteria);
        return tenantRepository.findAll(specification, page)
            .map(tenantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TenantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tenant> specification = createSpecification(criteria);
        return tenantRepository.count(specification);
    }

    /**
     * Function to convert {@link TenantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tenant> createSpecification(TenantCriteria criteria) {
        Specification<Tenant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tenant_.id));
            }
            if (criteria.getTenantName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantName(), Tenant_.tenantName));
            }
            if (criteria.getTenantDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantDescription(), Tenant_.tenantDescription));
            }
            if (criteria.getTenantLogoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantLogoUrl(), Tenant_.tenantLogoUrl));
            }
            if (criteria.getTenantContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantContactName(), Tenant_.tenantContactName));
            }
            if (criteria.getTenantPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantPhone(), Tenant_.tenantPhone));
            }
            if (criteria.getTenantEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantEmail(), Tenant_.tenantEmail));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Tenant_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Tenant_.lastUpdatedDate));
            }
        }
        return specification;
    }
}
