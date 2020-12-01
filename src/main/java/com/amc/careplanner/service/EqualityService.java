package com.amc.careplanner.service;

import com.amc.careplanner.domain.Equality;
import com.amc.careplanner.repository.EqualityRepository;
import com.amc.careplanner.service.dto.EqualityDTO;
import com.amc.careplanner.service.mapper.EqualityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Equality}.
 */
@Service
@Transactional
public class EqualityService {

    private final Logger log = LoggerFactory.getLogger(EqualityService.class);

    private final EqualityRepository equalityRepository;

    private final EqualityMapper equalityMapper;

    public EqualityService(EqualityRepository equalityRepository, EqualityMapper equalityMapper) {
        this.equalityRepository = equalityRepository;
        this.equalityMapper = equalityMapper;
    }

    /**
     * Save a equality.
     *
     * @param equalityDTO the entity to save.
     * @return the persisted entity.
     */
    public EqualityDTO save(EqualityDTO equalityDTO) {
        log.debug("Request to save Equality : {}", equalityDTO);
        Equality equality = equalityMapper.toEntity(equalityDTO);
        equality = equalityRepository.save(equality);
        return equalityMapper.toDto(equality);
    }

    /**
     * Get all the equalities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EqualityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equalities");
        return equalityRepository.findAll(pageable)
            .map(equalityMapper::toDto);
    }


    /**
     * Get one equality by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EqualityDTO> findOne(Long id) {
        log.debug("Request to get Equality : {}", id);
        return equalityRepository.findById(id)
            .map(equalityMapper::toDto);
    }

    /**
     * Delete the equality by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Equality : {}", id);
        equalityRepository.deleteById(id);
    }
}
