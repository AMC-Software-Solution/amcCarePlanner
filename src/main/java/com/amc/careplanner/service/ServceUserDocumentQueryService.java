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

import com.amc.careplanner.domain.ServceUserDocument;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.ServceUserDocumentRepository;
import com.amc.careplanner.service.dto.ServceUserDocumentCriteria;
import com.amc.careplanner.service.dto.ServceUserDocumentDTO;
import com.amc.careplanner.service.mapper.ServceUserDocumentMapper;

/**
 * Service for executing complex queries for {@link ServceUserDocument} entities in the database.
 * The main input is a {@link ServceUserDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServceUserDocumentDTO} or a {@link Page} of {@link ServceUserDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServceUserDocumentQueryService extends QueryService<ServceUserDocument> {

    private final Logger log = LoggerFactory.getLogger(ServceUserDocumentQueryService.class);

    private final ServceUserDocumentRepository servceUserDocumentRepository;

    private final ServceUserDocumentMapper servceUserDocumentMapper;

    public ServceUserDocumentQueryService(ServceUserDocumentRepository servceUserDocumentRepository, ServceUserDocumentMapper servceUserDocumentMapper) {
        this.servceUserDocumentRepository = servceUserDocumentRepository;
        this.servceUserDocumentMapper = servceUserDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link ServceUserDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServceUserDocumentDTO> findByCriteria(ServceUserDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServceUserDocument> specification = createSpecification(criteria);
        return servceUserDocumentMapper.toDto(servceUserDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServceUserDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServceUserDocumentDTO> findByCriteria(ServceUserDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServceUserDocument> specification = createSpecification(criteria);
        return servceUserDocumentRepository.findAll(specification, page)
            .map(servceUserDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServceUserDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServceUserDocument> specification = createSpecification(criteria);
        return servceUserDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link ServceUserDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServceUserDocument> createSpecification(ServceUserDocumentCriteria criteria) {
        Specification<ServceUserDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServceUserDocument_.id));
            }
            if (criteria.getDocumentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentName(), ServceUserDocument_.documentName));
            }
            if (criteria.getDocumentNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNumber(), ServceUserDocument_.documentNumber));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentStatus(), ServceUserDocument_.documentStatus));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), ServceUserDocument_.note));
            }
            if (criteria.getIssuedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuedDate(), ServceUserDocument_.issuedDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), ServceUserDocument_.expiryDate));
            }
            if (criteria.getUploadedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadedDate(), ServceUserDocument_.uploadedDate));
            }
            if (criteria.getDocumentFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentFileUrl(), ServceUserDocument_.documentFileUrl));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), ServceUserDocument_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), ServceUserDocument_.clientId));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerId(),
                    root -> root.join(ServceUserDocument_.owner, JoinType.LEFT).get(ServiceUser_.id)));
            }
            if (criteria.getApprovedById() != null) {
                specification = specification.and(buildSpecification(criteria.getApprovedById(),
                    root -> root.join(ServceUserDocument_.approvedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
