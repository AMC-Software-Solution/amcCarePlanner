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

import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.EmployeeRepository;
import com.amc.careplanner.service.dto.EmployeeCriteria;
import com.amc.careplanner.service.dto.EmployeeDTO;
import com.amc.careplanner.service.mapper.EmployeeMapper;

/**
 * Service for executing complex queries for {@link Employee} entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDTO} or a {@link Page} of {@link EmployeeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private final Logger log = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeQueryService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByCriteria(EmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeMapper.toDto(employeeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findByCriteria(EmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification, page)
            .map(employeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employee_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildSpecification(criteria.getTitle(), Employee_.title));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Employee_.firstName));
            }
            if (criteria.getMiddleInitial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleInitial(), Employee_.middleInitial));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Employee_.lastName));
            }
            if (criteria.getPreferredName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreferredName(), Employee_.preferredName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Employee_.gender));
            }
            if (criteria.getEmployeeCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeCode(), Employee_.employeeCode));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Employee_.email));
            }
            if (criteria.getNationalInsuranceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationalInsuranceNumber(), Employee_.nationalInsuranceNumber));
            }
            if (criteria.getEmployeeContractType() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeContractType(), Employee_.employeeContractType));
            }
            if (criteria.getPinCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPinCode(), Employee_.pinCode));
            }
            if (criteria.getTransportMode() != null) {
                specification = specification.and(buildSpecification(criteria.getTransportMode(), Employee_.transportMode));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Employee_.address));
            }
            if (criteria.getCounty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCounty(), Employee_.county));
            }
            if (criteria.getPostCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostCode(), Employee_.postCode));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Employee_.dateOfBirth));
            }
            if (criteria.getPhotoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhotoUrl(), Employee_.photoUrl));
            }
            if (criteria.getAcruedHolidayHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcruedHolidayHours(), Employee_.acruedHolidayHours));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Employee_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), Employee_.clientId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Employee_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getNationalityId() != null) {
                specification = specification.and(buildSpecification(criteria.getNationalityId(),
                    root -> root.join(Employee_.nationality, JoinType.LEFT).get(Country_.id)));
            }
        }
        return specification;
    }
}
