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

import com.amc.careplanner.domain.TenantDocument;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.TenantDocumentRepository;
import com.amc.careplanner.service.dto.TenantDocumentCriteria;
import com.amc.careplanner.service.dto.TenantDocumentDTO;
import com.amc.careplanner.service.mapper.TenantDocumentMapper;

/**
 * Service for executing complex queries for {@link TenantDocument} entities in the database.
 * The main input is a {@link TenantDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TenantDocumentDTO} or a {@link Page} of {@link TenantDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TenantDocumentQueryService extends QueryService<TenantDocument> {

    private final Logger log = LoggerFactory.getLogger(TenantDocumentQueryService.class);

    private final TenantDocumentRepository tenantDocumentRepository;

    private final TenantDocumentMapper tenantDocumentMapper;

    public TenantDocumentQueryService(TenantDocumentRepository tenantDocumentRepository, TenantDocumentMapper tenantDocumentMapper) {
        this.tenantDocumentRepository = tenantDocumentRepository;
        this.tenantDocumentMapper = tenantDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link TenantDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TenantDocumentDTO> findByCriteria(TenantDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TenantDocument> specification = createSpecification(criteria);
        return tenantDocumentMapper.toDto(tenantDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TenantDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TenantDocumentDTO> findByCriteria(TenantDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TenantDocument> specification = createSpecification(criteria);
        return tenantDocumentRepository.findAll(specification, page)
            .map(tenantDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TenantDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TenantDocument> specification = createSpecification(criteria);
        return tenantDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link TenantDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TenantDocument> createSpecification(TenantDocumentCriteria criteria) {
        Specification<TenantDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TenantDocument_.id));
            }
            if (criteria.getDocumentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentName(), TenantDocument_.documentName));
            }
            if (criteria.getDocumentCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentCode(), TenantDocument_.documentCode));
            }
            if (criteria.getDocumentNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNumber(), TenantDocument_.documentNumber));
            }
            if (criteria.getDocumentType() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentType(), TenantDocument_.documentType));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentStatus(), TenantDocument_.documentStatus));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), TenantDocument_.note));
            }
            if (criteria.getIssuedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuedDate(), TenantDocument_.issuedDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), TenantDocument_.expiryDate));
            }
            if (criteria.getUploadedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadedDate(), TenantDocument_.uploadedDate));
            }
            if (criteria.getDocumentFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentFileUrl(), TenantDocument_.documentFileUrl));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), TenantDocument_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), TenantDocument_.tenantId));
            }
            if (criteria.getUploadedById() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadedById(),
                    root -> root.join(TenantDocument_.uploadedBy, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getApprovedById() != null) {
                specification = specification.and(buildSpecification(criteria.getApprovedById(),
                    root -> root.join(TenantDocument_.approvedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
