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

import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.ServiceUserRepository;
import com.amc.careplanner.service.dto.ServiceUserCriteria;
import com.amc.careplanner.service.dto.ServiceUserDTO;
import com.amc.careplanner.service.mapper.ServiceUserMapper;

/**
 * Service for executing complex queries for {@link ServiceUser} entities in the database.
 * The main input is a {@link ServiceUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceUserDTO} or a {@link Page} of {@link ServiceUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceUserQueryService extends QueryService<ServiceUser> {

    private final Logger log = LoggerFactory.getLogger(ServiceUserQueryService.class);

    private final ServiceUserRepository serviceUserRepository;

    private final ServiceUserMapper serviceUserMapper;

    public ServiceUserQueryService(ServiceUserRepository serviceUserRepository, ServiceUserMapper serviceUserMapper) {
        this.serviceUserRepository = serviceUserRepository;
        this.serviceUserMapper = serviceUserMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceUserDTO> findByCriteria(ServiceUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceUser> specification = createSpecification(criteria);
        return serviceUserMapper.toDto(serviceUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceUserDTO> findByCriteria(ServiceUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceUser> specification = createSpecification(criteria);
        return serviceUserRepository.findAll(specification, page)
            .map(serviceUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceUser> specification = createSpecification(criteria);
        return serviceUserRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceUser> createSpecification(ServiceUserCriteria criteria) {
        Specification<ServiceUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceUser_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildSpecification(criteria.getTitle(), ServiceUser_.title));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), ServiceUser_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), ServiceUser_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), ServiceUser_.lastName));
            }
            if (criteria.getPreferredName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreferredName(), ServiceUser_.preferredName));
            }
            if (criteria.getServiceUserCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceUserCode(), ServiceUser_.serviceUserCode));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), ServiceUser_.dateOfBirth));
            }
            if (criteria.getLastVisitDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastVisitDate(), ServiceUser_.lastVisitDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ServiceUser_.startDate));
            }
            if (criteria.getSupportType() != null) {
                specification = specification.and(buildSpecification(criteria.getSupportType(), ServiceUser_.supportType));
            }
            if (criteria.getServiceUserCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserCategory(), ServiceUser_.serviceUserCategory));
            }
            if (criteria.getVulnerability() != null) {
                specification = specification.and(buildSpecification(criteria.getVulnerability(), ServiceUser_.vulnerability));
            }
            if (criteria.getServicePriority() != null) {
                specification = specification.and(buildSpecification(criteria.getServicePriority(), ServiceUser_.servicePriority));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildSpecification(criteria.getSource(), ServiceUser_.source));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ServiceUser_.status));
            }
            if (criteria.getFirstLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstLanguage(), ServiceUser_.firstLanguage));
            }
            if (criteria.getInterpreterRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getInterpreterRequired(), ServiceUser_.interpreterRequired));
            }
            if (criteria.getActivatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivatedDate(), ServiceUser_.activatedDate));
            }
            if (criteria.getProfilePhotoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfilePhotoUrl(), ServiceUser_.profilePhotoUrl));
            }
            if (criteria.getLastRecordedHeight() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastRecordedHeight(), ServiceUser_.lastRecordedHeight));
            }
            if (criteria.getLastRecordedWeight() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastRecordedWeight(), ServiceUser_.lastRecordedWeight));
            }
            if (criteria.getHasMedicalCondition() != null) {
                specification = specification.and(buildSpecification(criteria.getHasMedicalCondition(), ServiceUser_.hasMedicalCondition));
            }
            if (criteria.getMedicalConditionSummary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMedicalConditionSummary(), ServiceUser_.medicalConditionSummary));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), ServiceUser_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), ServiceUser_.tenantId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(ServiceUser_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getBranchId() != null) {
                specification = specification.and(buildSpecification(criteria.getBranchId(),
                    root -> root.join(ServiceUser_.branch, JoinType.LEFT).get(Branch_.id)));
            }
            if (criteria.getRegisteredById() != null) {
                specification = specification.and(buildSpecification(criteria.getRegisteredById(),
                    root -> root.join(ServiceUser_.registeredBy, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getActivatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getActivatedById(),
                    root -> root.join(ServiceUser_.activatedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
