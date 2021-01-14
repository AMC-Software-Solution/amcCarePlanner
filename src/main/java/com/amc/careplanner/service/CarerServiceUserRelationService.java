package com.amc.careplanner.service;

import com.amc.careplanner.domain.CarerServiceUserRelation;
import com.amc.careplanner.repository.CarerServiceUserRelationRepository;
import com.amc.careplanner.service.dto.CarerServiceUserRelationDTO;
import com.amc.careplanner.service.mapper.CarerServiceUserRelationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CarerServiceUserRelation}.
 */
@Service
@Transactional
public class CarerServiceUserRelationService {

    private final Logger log = LoggerFactory.getLogger(CarerServiceUserRelationService.class);

    private final CarerServiceUserRelationRepository carerServiceUserRelationRepository;

    private final CarerServiceUserRelationMapper carerServiceUserRelationMapper;

    public CarerServiceUserRelationService(CarerServiceUserRelationRepository carerServiceUserRelationRepository, CarerServiceUserRelationMapper carerServiceUserRelationMapper) {
        this.carerServiceUserRelationRepository = carerServiceUserRelationRepository;
        this.carerServiceUserRelationMapper = carerServiceUserRelationMapper;
    }

    /**
     * Save a carerServiceUserRelation.
     *
     * @param carerServiceUserRelationDTO the entity to save.
     * @return the persisted entity.
     */
    public CarerServiceUserRelationDTO save(CarerServiceUserRelationDTO carerServiceUserRelationDTO) {
        log.debug("Request to save CarerServiceUserRelation : {}", carerServiceUserRelationDTO);
        CarerServiceUserRelation carerServiceUserRelation = carerServiceUserRelationMapper.toEntity(carerServiceUserRelationDTO);
        carerServiceUserRelation = carerServiceUserRelationRepository.save(carerServiceUserRelation);
        return carerServiceUserRelationMapper.toDto(carerServiceUserRelation);
    }

    /**
     * Get all the carerServiceUserRelations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CarerServiceUserRelationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarerServiceUserRelations");
        return carerServiceUserRelationRepository.findAll(pageable)
            .map(carerServiceUserRelationMapper::toDto);
    }


    /**
     * Get one carerServiceUserRelation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CarerServiceUserRelationDTO> findOne(Long id) {
        log.debug("Request to get CarerServiceUserRelation : {}", id);
        return carerServiceUserRelationRepository.findById(id)
            .map(carerServiceUserRelationMapper::toDto);
    }

    /**
     * Delete the carerServiceUserRelation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CarerServiceUserRelation : {}", id);
        carerServiceUserRelationRepository.deleteById(id);
    }
}
