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

import com.amc.careplanner.domain.Answer;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.AnswerRepository;
import com.amc.careplanner.service.dto.AnswerCriteria;
import com.amc.careplanner.service.dto.AnswerDTO;
import com.amc.careplanner.service.mapper.AnswerMapper;

/**
 * Service for executing complex queries for {@link Answer} entities in the database.
 * The main input is a {@link AnswerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnswerDTO} or a {@link Page} of {@link AnswerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnswerQueryService extends QueryService<Answer> {

    private final Logger log = LoggerFactory.getLogger(AnswerQueryService.class);

    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    public AnswerQueryService(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    /**
     * Return a {@link List} of {@link AnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnswerDTO> findByCriteria(AnswerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Answer> specification = createSpecification(criteria);
        return answerMapper.toDto(answerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnswerDTO> findByCriteria(AnswerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Answer> specification = createSpecification(criteria);
        return answerRepository.findAll(specification, page)
            .map(answerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnswerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Answer> specification = createSpecification(criteria);
        return answerRepository.count(specification);
    }

    /**
     * Function to convert {@link AnswerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Answer> createSpecification(AnswerCriteria criteria) {
        Specification<Answer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Answer_.id));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), Answer_.answer));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Answer_.description));
            }
            if (criteria.getAttribute1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute1(), Answer_.attribute1));
            }
            if (criteria.getAttribute2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute2(), Answer_.attribute2));
            }
            if (criteria.getAttribute3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute3(), Answer_.attribute3));
            }
            if (criteria.getAttribute4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute4(), Answer_.attribute4));
            }
            if (criteria.getAttribute5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute5(), Answer_.attribute5));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Answer_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Answer_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), Answer_.tenantId));
            }
            if (criteria.getQuestionId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionId(),
                    root -> root.join(Answer_.question, JoinType.LEFT).get(Question_.id)));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(Answer_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
            if (criteria.getRecordedById() != null) {
                specification = specification.and(buildSpecification(criteria.getRecordedById(),
                    root -> root.join(Answer_.recordedBy, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
