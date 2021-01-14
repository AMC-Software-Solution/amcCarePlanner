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

import com.amc.careplanner.domain.ClientDocument;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.ClientDocumentRepository;
import com.amc.careplanner.service.dto.ClientDocumentCriteria;
import com.amc.careplanner.service.dto.ClientDocumentDTO;
import com.amc.careplanner.service.mapper.ClientDocumentMapper;

/**
 * Service for executing complex queries for {@link ClientDocument} entities in the database.
 * The main input is a {@link ClientDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientDocumentDTO} or a {@link Page} of {@link ClientDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientDocumentQueryService extends QueryService<ClientDocument> {

    private final Logger log = LoggerFactory.getLogger(ClientDocumentQueryService.class);

    private final ClientDocumentRepository clientDocumentRepository;

    private final ClientDocumentMapper clientDocumentMapper;

    public ClientDocumentQueryService(ClientDocumentRepository clientDocumentRepository, ClientDocumentMapper clientDocumentMapper) {
        this.clientDocumentRepository = clientDocumentRepository;
        this.clientDocumentMapper = clientDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link ClientDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientDocumentDTO> findByCriteria(ClientDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClientDocument> specification = createSpecification(criteria);
        return clientDocumentMapper.toDto(clientDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClientDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDocumentDTO> findByCriteria(ClientDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClientDocument> specification = createSpecification(criteria);
        return clientDocumentRepository.findAll(specification, page)
            .map(clientDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClientDocument> specification = createSpecification(criteria);
        return clientDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link ClientDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClientDocument> createSpecification(ClientDocumentCriteria criteria) {
        Specification<ClientDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClientDocument_.id));
            }
            if (criteria.getDocumentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentName(), ClientDocument_.documentName));
            }
            if (criteria.getDocumentNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNumber(), ClientDocument_.documentNumber));
            }
            if (criteria.getDocumentType() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentType(), ClientDocument_.documentType));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentStatus(), ClientDocument_.documentStatus));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), ClientDocument_.note));
            }
            if (criteria.getIssuedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuedDate(), ClientDocument_.issuedDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), ClientDocument_.expiryDate));
            }
            if (criteria.getUploadedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadedDate(), ClientDocument_.uploadedDate));
            }
            if (criteria.getDocumentFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentFileUrl(), ClientDocument_.documentFileUrl));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ClientDocument_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), ClientDocument_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), ClientDocument_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), ClientDocument_.hasExtraData));
            }
            if (criteria.getUploadedById() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadedById(),
                    root -> root.join(ClientDocument_.uploadedBy, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getApprovedById() != null) {
                specification = specification.and(buildSpecification(criteria.getApprovedById(),
                    root -> root.join(ClientDocument_.approvedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
