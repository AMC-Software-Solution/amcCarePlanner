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

import com.amc.careplanner.domain.Timesheet;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.TimesheetRepository;
import com.amc.careplanner.service.dto.TimesheetCriteria;
import com.amc.careplanner.service.dto.TimesheetDTO;
import com.amc.careplanner.service.mapper.TimesheetMapper;

/**
 * Service for executing complex queries for {@link Timesheet} entities in the database.
 * The main input is a {@link TimesheetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TimesheetDTO} or a {@link Page} of {@link TimesheetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TimesheetQueryService extends QueryService<Timesheet> {

    private final Logger log = LoggerFactory.getLogger(TimesheetQueryService.class);

    private final TimesheetRepository timesheetRepository;

    private final TimesheetMapper timesheetMapper;

    public TimesheetQueryService(TimesheetRepository timesheetRepository, TimesheetMapper timesheetMapper) {
        this.timesheetRepository = timesheetRepository;
        this.timesheetMapper = timesheetMapper;
    }

    /**
     * Return a {@link List} of {@link TimesheetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TimesheetDTO> findByCriteria(TimesheetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Timesheet> specification = createSpecification(criteria);
        return timesheetMapper.toDto(timesheetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TimesheetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TimesheetDTO> findByCriteria(TimesheetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Timesheet> specification = createSpecification(criteria);
        return timesheetRepository.findAll(specification, page)
            .map(timesheetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TimesheetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Timesheet> specification = createSpecification(criteria);
        return timesheetRepository.count(specification);
    }

    /**
     * Function to convert {@link TimesheetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Timesheet> createSpecification(TimesheetCriteria criteria) {
        Specification<Timesheet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Timesheet_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Timesheet_.description));
            }
            if (criteria.getTimesheetDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimesheetDate(), Timesheet_.timesheetDate));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartTime(), Timesheet_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndTime(), Timesheet_.endTime));
            }
            if (criteria.getHoursWorked() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoursWorked(), Timesheet_.hoursWorked));
            }
            if (criteria.getBreakStartTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBreakStartTime(), Timesheet_.breakStartTime));
            }
            if (criteria.getBreakEndTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBreakEndTime(), Timesheet_.breakEndTime));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Timesheet_.note));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Timesheet_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), Timesheet_.tenantId));
            }
            if (criteria.getServiceOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceOrderId(),
                    root -> root.join(Timesheet_.serviceOrder, JoinType.LEFT).get(ServiceOrder_.id)));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(Timesheet_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
            if (criteria.getCareProviderId() != null) {
                specification = specification.and(buildSpecification(criteria.getCareProviderId(),
                    root -> root.join(Timesheet_.careProvider, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
