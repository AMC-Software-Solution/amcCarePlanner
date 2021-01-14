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

import com.amc.careplanner.domain.EmergencyContact;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.EmergencyContactRepository;
import com.amc.careplanner.service.dto.EmergencyContactCriteria;
import com.amc.careplanner.service.dto.EmergencyContactDTO;
import com.amc.careplanner.service.mapper.EmergencyContactMapper;

/**
 * Service for executing complex queries for {@link EmergencyContact} entities in the database.
 * The main input is a {@link EmergencyContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmergencyContactDTO} or a {@link Page} of {@link EmergencyContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmergencyContactQueryService extends QueryService<EmergencyContact> {

    private final Logger log = LoggerFactory.getLogger(EmergencyContactQueryService.class);

    private final EmergencyContactRepository emergencyContactRepository;

    private final EmergencyContactMapper emergencyContactMapper;

    public EmergencyContactQueryService(EmergencyContactRepository emergencyContactRepository, EmergencyContactMapper emergencyContactMapper) {
        this.emergencyContactRepository = emergencyContactRepository;
        this.emergencyContactMapper = emergencyContactMapper;
    }

    /**
     * Return a {@link List} of {@link EmergencyContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmergencyContactDTO> findByCriteria(EmergencyContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmergencyContact> specification = createSpecification(criteria);
        return emergencyContactMapper.toDto(emergencyContactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmergencyContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmergencyContactDTO> findByCriteria(EmergencyContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmergencyContact> specification = createSpecification(criteria);
        return emergencyContactRepository.findAll(specification, page)
            .map(emergencyContactMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmergencyContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmergencyContact> specification = createSpecification(criteria);
        return emergencyContactRepository.count(specification);
    }

    /**
     * Function to convert {@link EmergencyContactCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmergencyContact> createSpecification(EmergencyContactCriteria criteria) {
        Specification<EmergencyContact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmergencyContact_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EmergencyContact_.name));
            }
            if (criteria.getContactRelationship() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactRelationship(), EmergencyContact_.contactRelationship));
            }
            if (criteria.getIsKeyHolder() != null) {
                specification = specification.and(buildSpecification(criteria.getIsKeyHolder(), EmergencyContact_.isKeyHolder));
            }
            if (criteria.getInfoSharingConsentGiven() != null) {
                specification = specification.and(buildSpecification(criteria.getInfoSharingConsentGiven(), EmergencyContact_.infoSharingConsentGiven));
            }
            if (criteria.getPreferredContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreferredContactNumber(), EmergencyContact_.preferredContactNumber));
            }
            if (criteria.getFullAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullAddress(), EmergencyContact_.fullAddress));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), EmergencyContact_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), EmergencyContact_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), EmergencyContact_.clientId));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), EmergencyContact_.hasExtraData));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(EmergencyContact_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
        }
        return specification;
    }
}
