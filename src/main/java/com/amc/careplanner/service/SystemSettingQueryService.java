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

import com.amc.careplanner.domain.SystemSetting;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.SystemSettingRepository;
import com.amc.careplanner.service.dto.SystemSettingCriteria;
import com.amc.careplanner.service.dto.SystemSettingDTO;
import com.amc.careplanner.service.mapper.SystemSettingMapper;

/**
 * Service for executing complex queries for {@link SystemSetting} entities in the database.
 * The main input is a {@link SystemSettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SystemSettingDTO} or a {@link Page} of {@link SystemSettingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemSettingQueryService extends QueryService<SystemSetting> {

    private final Logger log = LoggerFactory.getLogger(SystemSettingQueryService.class);

    private final SystemSettingRepository systemSettingRepository;

    private final SystemSettingMapper systemSettingMapper;

    public SystemSettingQueryService(SystemSettingRepository systemSettingRepository, SystemSettingMapper systemSettingMapper) {
        this.systemSettingRepository = systemSettingRepository;
        this.systemSettingMapper = systemSettingMapper;
    }

    /**
     * Return a {@link List} of {@link SystemSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemSettingDTO> findByCriteria(SystemSettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SystemSetting> specification = createSpecification(criteria);
        return systemSettingMapper.toDto(systemSettingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SystemSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemSettingDTO> findByCriteria(SystemSettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SystemSetting> specification = createSpecification(criteria);
        return systemSettingRepository.findAll(specification, page)
            .map(systemSettingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemSettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SystemSetting> specification = createSpecification(criteria);
        return systemSettingRepository.count(specification);
    }

    /**
     * Function to convert {@link SystemSettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SystemSetting> createSpecification(SystemSettingCriteria criteria) {
        Specification<SystemSetting> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SystemSetting_.id));
            }
            if (criteria.getFieldName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldName(), SystemSetting_.fieldName));
            }
            if (criteria.getFieldValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldValue(), SystemSetting_.fieldValue));
            }
            if (criteria.getDefaultValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultValue(), SystemSetting_.defaultValue));
            }
            if (criteria.getSettingEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getSettingEnabled(), SystemSetting_.settingEnabled));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SystemSetting_.createdDate));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), SystemSetting_.updatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), SystemSetting_.tenantId));
            }
        }
        return specification;
    }
}
