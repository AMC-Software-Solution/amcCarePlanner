package com.amc.careplanner.service;

import com.amc.careplanner.domain.EligibilityType;
import com.amc.careplanner.repository.EligibilityTypeRepository;
import com.amc.careplanner.service.dto.EligibilityTypeDTO;
import com.amc.careplanner.service.mapper.EligibilityTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EligibilityType}.
 */
@Service
@Transactional
public class EligibilityTypeService {

    private final Logger log = LoggerFactory.getLogger(EligibilityTypeService.class);

    private final EligibilityTypeRepository eligibilityTypeRepository;

    private final EligibilityTypeMapper eligibilityTypeMapper;

    public EligibilityTypeService(EligibilityTypeRepository eligibilityTypeRepository, EligibilityTypeMapper eligibilityTypeMapper) {
        this.eligibilityTypeRepository = eligibilityTypeRepository;
        this.eligibilityTypeMapper = eligibilityTypeMapper;
    }

    /**
     * Save a eligibilityType.
     *
     * @param eligibilityTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public EligibilityTypeDTO save(EligibilityTypeDTO eligibilityTypeDTO) {
        log.debug("Request to save EligibilityType : {}", eligibilityTypeDTO);
        EligibilityType eligibilityType = eligibilityTypeMapper.toEntity(eligibilityTypeDTO);
        eligibilityType = eligibilityTypeRepository.save(eligibilityType);
        return eligibilityTypeMapper.toDto(eligibilityType);
    }

    /**
     * Get all the eligibilityTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EligibilityTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EligibilityTypes");
        return eligibilityTypeRepository.findAll(pageable)
            .map(eligibilityTypeMapper::toDto);
    }


    /**
     * Get one eligibilityType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EligibilityTypeDTO> findOne(Long id) {
        log.debug("Request to get EligibilityType : {}", id);
        return eligibilityTypeRepository.findById(id)
            .map(eligibilityTypeMapper::toDto);
    }

    /**
     * Delete the eligibilityType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EligibilityType : {}", id);
        eligibilityTypeRepository.deleteById(id);
    }
}
