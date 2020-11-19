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

import com.amc.careplanner.domain.DisabilityType;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.DisabilityTypeRepository;
import com.amc.careplanner.service.dto.DisabilityTypeCriteria;
import com.amc.careplanner.service.dto.DisabilityTypeDTO;
import com.amc.careplanner.service.mapper.DisabilityTypeMapper;

/**
 * Service for executing complex queries for {@link DisabilityType} entities in the database.
 * The main input is a {@link DisabilityTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DisabilityTypeDTO} or a {@link Page} of {@link DisabilityTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DisabilityTypeQueryService extends QueryService<DisabilityType> {

    private final Logger log = LoggerFactory.getLogger(DisabilityTypeQueryService.class);

    private final DisabilityTypeRepository disabilityTypeRepository;

    private final DisabilityTypeMapper disabilityTypeMapper;

    public DisabilityTypeQueryService(DisabilityTypeRepository disabilityTypeRepository, DisabilityTypeMapper disabilityTypeMapper) {
        this.disabilityTypeRepository = disabilityTypeRepository;
        this.disabilityTypeMapper = disabilityTypeMapper;
    }

    /**
     * Return a {@link List} of {@link DisabilityTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DisabilityTypeDTO> findByCriteria(DisabilityTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DisabilityType> specification = createSpecification(criteria);
        return disabilityTypeMapper.toDto(disabilityTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DisabilityTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DisabilityTypeDTO> findByCriteria(DisabilityTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DisabilityType> specification = createSpecification(criteria);
        return disabilityTypeRepository.findAll(specification, page)
            .map(disabilityTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DisabilityTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DisabilityType> specification = createSpecification(criteria);
        return disabilityTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link DisabilityTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DisabilityType> createSpecification(DisabilityTypeCriteria criteria) {
        Specification<DisabilityType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DisabilityType_.id));
            }
            if (criteria.getDisability() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisability(), DisabilityType_.disability));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), DisabilityType_.description));
            }
        }
        return specification;
    }
}
