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

import com.amc.careplanner.domain.Consent;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.ConsentRepository;
import com.amc.careplanner.service.dto.ConsentCriteria;
import com.amc.careplanner.service.dto.ConsentDTO;
import com.amc.careplanner.service.mapper.ConsentMapper;

/**
 * Service for executing complex queries for {@link Consent} entities in the database.
 * The main input is a {@link ConsentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConsentDTO} or a {@link Page} of {@link ConsentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConsentQueryService extends QueryService<Consent> {

    private final Logger log = LoggerFactory.getLogger(ConsentQueryService.class);

    private final ConsentRepository consentRepository;

    private final ConsentMapper consentMapper;

    public ConsentQueryService(ConsentRepository consentRepository, ConsentMapper consentMapper) {
        this.consentRepository = consentRepository;
        this.consentMapper = consentMapper;
    }

    /**
     * Return a {@link List} of {@link ConsentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConsentDTO> findByCriteria(ConsentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Consent> specification = createSpecification(criteria);
        return consentMapper.toDto(consentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConsentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsentDTO> findByCriteria(ConsentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Consent> specification = createSpecification(criteria);
        return consentRepository.findAll(specification, page)
            .map(consentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConsentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Consent> specification = createSpecification(criteria);
        return consentRepository.count(specification);
    }

    /**
     * Function to convert {@link ConsentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Consent> createSpecification(ConsentCriteria criteria) {
        Specification<Consent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Consent_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Consent_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Consent_.description));
            }
            if (criteria.getGiveConsent() != null) {
                specification = specification.and(buildSpecification(criteria.getGiveConsent(), Consent_.giveConsent));
            }
            if (criteria.getArrangements() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArrangements(), Consent_.arrangements));
            }
            if (criteria.getServiceUserSignature() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceUserSignature(), Consent_.serviceUserSignature));
            }
            if (criteria.getSignatureImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSignatureImageUrl(), Consent_.signatureImageUrl));
            }
            if (criteria.getConsentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConsentDate(), Consent_.consentDate));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Consent_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Consent_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), Consent_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), Consent_.hasExtraData));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(Consent_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
            if (criteria.getWitnessedById() != null) {
                specification = specification.and(buildSpecification(criteria.getWitnessedById(),
                    root -> root.join(Consent_.witnessedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
