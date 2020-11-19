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

import com.amc.careplanner.domain.MedicalContact;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.MedicalContactRepository;
import com.amc.careplanner.service.dto.MedicalContactCriteria;
import com.amc.careplanner.service.dto.MedicalContactDTO;
import com.amc.careplanner.service.mapper.MedicalContactMapper;

/**
 * Service for executing complex queries for {@link MedicalContact} entities in the database.
 * The main input is a {@link MedicalContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MedicalContactDTO} or a {@link Page} of {@link MedicalContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MedicalContactQueryService extends QueryService<MedicalContact> {

    private final Logger log = LoggerFactory.getLogger(MedicalContactQueryService.class);

    private final MedicalContactRepository medicalContactRepository;

    private final MedicalContactMapper medicalContactMapper;

    public MedicalContactQueryService(MedicalContactRepository medicalContactRepository, MedicalContactMapper medicalContactMapper) {
        this.medicalContactRepository = medicalContactRepository;
        this.medicalContactMapper = medicalContactMapper;
    }

    /**
     * Return a {@link List} of {@link MedicalContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MedicalContactDTO> findByCriteria(MedicalContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MedicalContact> specification = createSpecification(criteria);
        return medicalContactMapper.toDto(medicalContactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MedicalContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MedicalContactDTO> findByCriteria(MedicalContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MedicalContact> specification = createSpecification(criteria);
        return medicalContactRepository.findAll(specification, page)
            .map(medicalContactMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MedicalContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MedicalContact> specification = createSpecification(criteria);
        return medicalContactRepository.count(specification);
    }

    /**
     * Function to convert {@link MedicalContactCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MedicalContact> createSpecification(MedicalContactCriteria criteria) {
        Specification<MedicalContact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MedicalContact_.id));
            }
            if (criteria.getDoctorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDoctorName(), MedicalContact_.doctorName));
            }
            if (criteria.getDoctorSurgery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDoctorSurgery(), MedicalContact_.doctorSurgery));
            }
            if (criteria.getDoctorAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDoctorAddress(), MedicalContact_.doctorAddress));
            }
            if (criteria.getDoctorPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDoctorPhone(), MedicalContact_.doctorPhone));
            }
            if (criteria.getLastVisitedDoctor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastVisitedDoctor(), MedicalContact_.lastVisitedDoctor));
            }
            if (criteria.getDistrictNurseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrictNurseName(), MedicalContact_.districtNurseName));
            }
            if (criteria.getDistrictNursePhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrictNursePhone(), MedicalContact_.districtNursePhone));
            }
            if (criteria.getCareManagerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCareManagerName(), MedicalContact_.careManagerName));
            }
            if (criteria.getCareManagerPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCareManagerPhone(), MedicalContact_.careManagerPhone));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), MedicalContact_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), MedicalContact_.tenantId));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(MedicalContact_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
        }
        return specification;
    }
}
