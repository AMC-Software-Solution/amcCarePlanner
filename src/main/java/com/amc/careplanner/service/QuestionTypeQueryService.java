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

import com.amc.careplanner.domain.QuestionType;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.QuestionTypeRepository;
import com.amc.careplanner.service.dto.QuestionTypeCriteria;
import com.amc.careplanner.service.dto.QuestionTypeDTO;
import com.amc.careplanner.service.mapper.QuestionTypeMapper;

/**
 * Service for executing complex queries for {@link QuestionType} entities in the database.
 * The main input is a {@link QuestionTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionTypeDTO} or a {@link Page} of {@link QuestionTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionTypeQueryService extends QueryService<QuestionType> {

    private final Logger log = LoggerFactory.getLogger(QuestionTypeQueryService.class);

    private final QuestionTypeRepository questionTypeRepository;

    private final QuestionTypeMapper questionTypeMapper;

    public QuestionTypeQueryService(QuestionTypeRepository questionTypeRepository, QuestionTypeMapper questionTypeMapper) {
        this.questionTypeRepository = questionTypeRepository;
        this.questionTypeMapper = questionTypeMapper;
    }

    /**
     * Return a {@link List} of {@link QuestionTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionTypeDTO> findByCriteria(QuestionTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionType> specification = createSpecification(criteria);
        return questionTypeMapper.toDto(questionTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionTypeDTO> findByCriteria(QuestionTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionType> specification = createSpecification(criteria);
        return questionTypeRepository.findAll(specification, page)
            .map(questionTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionType> specification = createSpecification(criteria);
        return questionTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionType> createSpecification(QuestionTypeCriteria criteria) {
        Specification<QuestionType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionType_.id));
            }
            if (criteria.getQuestionType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestionType(), QuestionType_.questionType));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), QuestionType_.lastUpdatedDate));
            }
        }
        return specification;
    }
}
