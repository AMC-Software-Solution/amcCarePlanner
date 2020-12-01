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

import com.amc.careplanner.domain.Communication;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.CommunicationRepository;
import com.amc.careplanner.service.dto.CommunicationCriteria;
import com.amc.careplanner.service.dto.CommunicationDTO;
import com.amc.careplanner.service.mapper.CommunicationMapper;

/**
 * Service for executing complex queries for {@link Communication} entities in the database.
 * The main input is a {@link CommunicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommunicationDTO} or a {@link Page} of {@link CommunicationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommunicationQueryService extends QueryService<Communication> {

    private final Logger log = LoggerFactory.getLogger(CommunicationQueryService.class);

    private final CommunicationRepository communicationRepository;

    private final CommunicationMapper communicationMapper;

    public CommunicationQueryService(CommunicationRepository communicationRepository, CommunicationMapper communicationMapper) {
        this.communicationRepository = communicationRepository;
        this.communicationMapper = communicationMapper;
    }

    /**
     * Return a {@link List} of {@link CommunicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommunicationDTO> findByCriteria(CommunicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Communication> specification = createSpecification(criteria);
        return communicationMapper.toDto(communicationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommunicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommunicationDTO> findByCriteria(CommunicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Communication> specification = createSpecification(criteria);
        return communicationRepository.findAll(specification, page)
            .map(communicationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommunicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Communication> specification = createSpecification(criteria);
        return communicationRepository.count(specification);
    }

    /**
     * Function to convert {@link CommunicationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Communication> createSpecification(CommunicationCriteria criteria) {
        Specification<Communication> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Communication_.id));
            }
            if (criteria.getCommunicationType() != null) {
                specification = specification.and(buildSpecification(criteria.getCommunicationType(), Communication_.communicationType));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Communication_.note));
            }
            if (criteria.getCommunicationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommunicationDate(), Communication_.communicationDate));
            }
            if (criteria.getAttachmentUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttachmentUrl(), Communication_.attachmentUrl));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Communication_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), Communication_.clientId));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(Communication_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
            if (criteria.getCommunicatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getCommunicatedById(),
                    root -> root.join(Communication_.communicatedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
