package com.amc.careplanner.service;

import com.amc.careplanner.domain.ExtraData;
import com.amc.careplanner.repository.ExtraDataRepository;
import com.amc.careplanner.service.dto.ExtraDataDTO;
import com.amc.careplanner.service.mapper.ExtraDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExtraData}.
 */
@Service
@Transactional
public class ExtraDataService {

    private final Logger log = LoggerFactory.getLogger(ExtraDataService.class);

    private final ExtraDataRepository extraDataRepository;

    private final ExtraDataMapper extraDataMapper;

    public ExtraDataService(ExtraDataRepository extraDataRepository, ExtraDataMapper extraDataMapper) {
        this.extraDataRepository = extraDataRepository;
        this.extraDataMapper = extraDataMapper;
    }

    /**
     * Save a extraData.
     *
     * @param extraDataDTO the entity to save.
     * @return the persisted entity.
     */
    public ExtraDataDTO save(ExtraDataDTO extraDataDTO) {
        log.debug("Request to save ExtraData : {}", extraDataDTO);
        ExtraData extraData = extraDataMapper.toEntity(extraDataDTO);
        extraData = extraDataRepository.save(extraData);
        return extraDataMapper.toDto(extraData);
    }

    /**
     * Get all the extraData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtraDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExtraData");
        return extraDataRepository.findAll(pageable)
            .map(extraDataMapper::toDto);
    }


    /**
     * Get one extraData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExtraDataDTO> findOne(Long id) {
        log.debug("Request to get ExtraData : {}", id);
        return extraDataRepository.findById(id)
            .map(extraDataMapper::toDto);
    }

    /**
     * Delete the extraData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExtraData : {}", id);
        extraDataRepository.deleteById(id);
    }
}
