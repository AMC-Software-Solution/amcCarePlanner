package com.amc.careplanner.service;

import com.amc.careplanner.domain.RelationshipType;
import com.amc.careplanner.repository.RelationshipTypeRepository;
import com.amc.careplanner.service.dto.RelationshipTypeDTO;
import com.amc.careplanner.service.mapper.RelationshipTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RelationshipType}.
 */
@Service
@Transactional
public class RelationshipTypeService {

    private final Logger log = LoggerFactory.getLogger(RelationshipTypeService.class);

    private final RelationshipTypeRepository relationshipTypeRepository;

    private final RelationshipTypeMapper relationshipTypeMapper;

    public RelationshipTypeService(RelationshipTypeRepository relationshipTypeRepository, RelationshipTypeMapper relationshipTypeMapper) {
        this.relationshipTypeRepository = relationshipTypeRepository;
        this.relationshipTypeMapper = relationshipTypeMapper;
    }

    /**
     * Save a relationshipType.
     *
     * @param relationshipTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public RelationshipTypeDTO save(RelationshipTypeDTO relationshipTypeDTO) {
        log.debug("Request to save RelationshipType : {}", relationshipTypeDTO);
        RelationshipType relationshipType = relationshipTypeMapper.toEntity(relationshipTypeDTO);
        relationshipType = relationshipTypeRepository.save(relationshipType);
        return relationshipTypeMapper.toDto(relationshipType);
    }

    /**
     * Get all the relationshipTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RelationshipTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelationshipTypes");
        return relationshipTypeRepository.findAll(pageable)
            .map(relationshipTypeMapper::toDto);
    }


    /**
     * Get one relationshipType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RelationshipTypeDTO> findOne(Long id) {
        log.debug("Request to get RelationshipType : {}", id);
        return relationshipTypeRepository.findById(id)
            .map(relationshipTypeMapper::toDto);
    }

    /**
     * Delete the relationshipType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RelationshipType : {}", id);
        relationshipTypeRepository.deleteById(id);
    }
}
