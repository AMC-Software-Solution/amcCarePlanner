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

import com.amc.careplanner.domain.EmployeeDocument;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.EmployeeDocumentRepository;
import com.amc.careplanner.service.dto.EmployeeDocumentCriteria;
import com.amc.careplanner.service.dto.EmployeeDocumentDTO;
import com.amc.careplanner.service.mapper.EmployeeDocumentMapper;

/**
 * Service for executing complex queries for {@link EmployeeDocument} entities in the database.
 * The main input is a {@link EmployeeDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDocumentDTO} or a {@link Page} of {@link EmployeeDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeDocumentQueryService extends QueryService<EmployeeDocument> {

    private final Logger log = LoggerFactory.getLogger(EmployeeDocumentQueryService.class);

    private final EmployeeDocumentRepository employeeDocumentRepository;

    private final EmployeeDocumentMapper employeeDocumentMapper;

    public EmployeeDocumentQueryService(EmployeeDocumentRepository employeeDocumentRepository, EmployeeDocumentMapper employeeDocumentMapper) {
        this.employeeDocumentRepository = employeeDocumentRepository;
        this.employeeDocumentMapper = employeeDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDocumentDTO> findByCriteria(EmployeeDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeDocument> specification = createSpecification(criteria);
        return employeeDocumentMapper.toDto(employeeDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDocumentDTO> findByCriteria(EmployeeDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeDocument> specification = createSpecification(criteria);
        return employeeDocumentRepository.findAll(specification, page)
            .map(employeeDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeDocument> specification = createSpecification(criteria);
        return employeeDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeDocument> createSpecification(EmployeeDocumentCriteria criteria) {
        Specification<EmployeeDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeDocument_.id));
            }
            if (criteria.getDocumentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentName(), EmployeeDocument_.documentName));
            }
            if (criteria.getDocumentNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNumber(), EmployeeDocument_.documentNumber));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentStatus(), EmployeeDocument_.documentStatus));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), EmployeeDocument_.note));
            }
            if (criteria.getIssuedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuedDate(), EmployeeDocument_.issuedDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), EmployeeDocument_.expiryDate));
            }
            if (criteria.getUploadedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadedDate(), EmployeeDocument_.uploadedDate));
            }
            if (criteria.getDocumentFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentFileUrl(), EmployeeDocument_.documentFileUrl));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), EmployeeDocument_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), EmployeeDocument_.clientId));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(EmployeeDocument_.documentType, JoinType.LEFT).get(DocumentType_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(EmployeeDocument_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getApprovedById() != null) {
                specification = specification.and(buildSpecification(criteria.getApprovedById(),
                    root -> root.join(EmployeeDocument_.approvedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
