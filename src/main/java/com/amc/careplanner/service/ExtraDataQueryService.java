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

import com.amc.careplanner.domain.ExtraData;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.ExtraDataRepository;
import com.amc.careplanner.service.dto.ExtraDataCriteria;
import com.amc.careplanner.service.dto.ExtraDataDTO;
import com.amc.careplanner.service.mapper.ExtraDataMapper;

/**
 * Service for executing complex queries for {@link ExtraData} entities in the database.
 * The main input is a {@link ExtraDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExtraDataDTO} or a {@link Page} of {@link ExtraDataDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExtraDataQueryService extends QueryService<ExtraData> {

    private final Logger log = LoggerFactory.getLogger(ExtraDataQueryService.class);

    private final ExtraDataRepository extraDataRepository;

    private final ExtraDataMapper extraDataMapper;

    public ExtraDataQueryService(ExtraDataRepository extraDataRepository, ExtraDataMapper extraDataMapper) {
        this.extraDataRepository = extraDataRepository;
        this.extraDataMapper = extraDataMapper;
    }

    /**
     * Return a {@link List} of {@link ExtraDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExtraDataDTO> findByCriteria(ExtraDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExtraData> specification = createSpecification(criteria);
        return extraDataMapper.toDto(extraDataRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExtraDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtraDataDTO> findByCriteria(ExtraDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExtraData> specification = createSpecification(criteria);
        return extraDataRepository.findAll(specification, page)
            .map(extraDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExtraDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExtraData> specification = createSpecification(criteria);
        return extraDataRepository.count(specification);
    }

    /**
     * Function to convert {@link ExtraDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExtraData> createSpecification(ExtraDataCriteria criteria) {
        Specification<ExtraData> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExtraData_.id));
            }
            if (criteria.getEntityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEntityName(), ExtraData_.entityName));
            }
            if (criteria.getEntityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntityId(), ExtraData_.entityId));
            }
            if (criteria.getExtraDataKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtraDataKey(), ExtraData_.extraDataKey));
            }
            if (criteria.getExtraDataValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtraDataValue(), ExtraData_.extraDataValue));
            }
            if (criteria.getExtraDataValueDataType() != null) {
                specification = specification.and(buildSpecification(criteria.getExtraDataValueDataType(), ExtraData_.extraDataValueDataType));
            }
            if (criteria.getExtraDataDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtraDataDescription(), ExtraData_.extraDataDescription));
            }
            if (criteria.getExtraDataDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExtraDataDate(), ExtraData_.extraDataDate));
            }
            if (criteria.getHasExtraData() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExtraData(), ExtraData_.hasExtraData));
            }
        }
        return specification;
    }
}
