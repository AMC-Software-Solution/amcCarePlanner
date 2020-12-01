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

import com.amc.careplanner.domain.Task;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.TaskRepository;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.mapper.TaskMapper;

/**
 * Service for executing complex queries for {@link Task} entities in the database.
 * The main input is a {@link TaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskDTO} or a {@link Page} of {@link TaskDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskQueryService extends QueryService<Task> {

    private final Logger log = LoggerFactory.getLogger(TaskQueryService.class);

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskQueryService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    /**
     * Return a {@link List} of {@link TaskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskDTO> findByCriteria(TaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Task> specification = createSpecification(criteria);
        return taskMapper.toDto(taskRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TaskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskDTO> findByCriteria(TaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.findAll(specification, page)
            .map(taskMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Task> createSpecification(TaskCriteria criteria) {
        Specification<Task> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Task_.id));
            }
            if (criteria.getTaskName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaskName(), Task_.taskName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Task_.description));
            }
            if (criteria.getDateOfTask() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfTask(), Task_.dateOfTask));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartTime(), Task_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndTime(), Task_.endTime));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Task_.status));
            }
            if (criteria.getDateCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateCreated(), Task_.dateCreated));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Task_.lastUpdatedDate));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), Task_.clientId));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(Task_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
            if (criteria.getAssignedToId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssignedToId(),
                    root -> root.join(Task_.assignedTo, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getServiceOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceOrderId(),
                    root -> root.join(Task_.serviceOrder, JoinType.LEFT).get(ServiceOrder_.id)));
            }
            if (criteria.getCreatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedById(),
                    root -> root.join(Task_.createdBy, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getAllocatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getAllocatedById(),
                    root -> root.join(Task_.allocatedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
