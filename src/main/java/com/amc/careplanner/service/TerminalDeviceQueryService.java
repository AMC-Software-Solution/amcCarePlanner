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

import com.amc.careplanner.domain.TerminalDevice;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.TerminalDeviceRepository;
import com.amc.careplanner.service.dto.TerminalDeviceCriteria;
import com.amc.careplanner.service.dto.TerminalDeviceDTO;
import com.amc.careplanner.service.mapper.TerminalDeviceMapper;

/**
 * Service for executing complex queries for {@link TerminalDevice} entities in the database.
 * The main input is a {@link TerminalDeviceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TerminalDeviceDTO} or a {@link Page} of {@link TerminalDeviceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TerminalDeviceQueryService extends QueryService<TerminalDevice> {

    private final Logger log = LoggerFactory.getLogger(TerminalDeviceQueryService.class);

    private final TerminalDeviceRepository terminalDeviceRepository;

    private final TerminalDeviceMapper terminalDeviceMapper;

    public TerminalDeviceQueryService(TerminalDeviceRepository terminalDeviceRepository, TerminalDeviceMapper terminalDeviceMapper) {
        this.terminalDeviceRepository = terminalDeviceRepository;
        this.terminalDeviceMapper = terminalDeviceMapper;
    }

    /**
     * Return a {@link List} of {@link TerminalDeviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TerminalDeviceDTO> findByCriteria(TerminalDeviceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TerminalDevice> specification = createSpecification(criteria);
        return terminalDeviceMapper.toDto(terminalDeviceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TerminalDeviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TerminalDeviceDTO> findByCriteria(TerminalDeviceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TerminalDevice> specification = createSpecification(criteria);
        return terminalDeviceRepository.findAll(specification, page)
            .map(terminalDeviceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TerminalDeviceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TerminalDevice> specification = createSpecification(criteria);
        return terminalDeviceRepository.count(specification);
    }

    /**
     * Function to convert {@link TerminalDeviceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TerminalDevice> createSpecification(TerminalDeviceCriteria criteria) {
        Specification<TerminalDevice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TerminalDevice_.id));
            }
            if (criteria.getDeviceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeviceName(), TerminalDevice_.deviceName));
            }
            if (criteria.getDeviceModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeviceModel(), TerminalDevice_.deviceModel));
            }
            if (criteria.getRegisteredDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisteredDate(), TerminalDevice_.registeredDate));
            }
            if (criteria.getImei() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImei(), TerminalDevice_.imei));
            }
            if (criteria.getSimNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSimNumber(), TerminalDevice_.simNumber));
            }
            if (criteria.getUserStartedUsingFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserStartedUsingFrom(), TerminalDevice_.userStartedUsingFrom));
            }
            if (criteria.getDeviceOnLocationFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeviceOnLocationFrom(), TerminalDevice_.deviceOnLocationFrom));
            }
            if (criteria.getOperatingSystem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOperatingSystem(), TerminalDevice_.operatingSystem));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), TerminalDevice_.note));
            }
            if (criteria.getOwnerEntityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOwnerEntityId(), TerminalDevice_.ownerEntityId));
            }
            if (criteria.getOwnerEntityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwnerEntityName(), TerminalDevice_.ownerEntityName));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), TerminalDevice_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), TerminalDevice_.tenantId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(TerminalDevice_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
