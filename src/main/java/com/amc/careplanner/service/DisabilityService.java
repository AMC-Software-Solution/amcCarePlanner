package com.amc.careplanner.service;

import com.amc.careplanner.domain.Disability;
import com.amc.careplanner.repository.DisabilityRepository;
import com.amc.careplanner.service.dto.DisabilityDTO;
import com.amc.careplanner.service.mapper.DisabilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Disability}.
 */
@Service
@Transactional
public class DisabilityService {

    private final Logger log = LoggerFactory.getLogger(DisabilityService.class);

    private final DisabilityRepository disabilityRepository;

    private final DisabilityMapper disabilityMapper;

    public DisabilityService(DisabilityRepository disabilityRepository, DisabilityMapper disabilityMapper) {
        this.disabilityRepository = disabilityRepository;
        this.disabilityMapper = disabilityMapper;
    }

    /**
     * Save a disability.
     *
     * @param disabilityDTO the entity to save.
     * @return the persisted entity.
     */
    public DisabilityDTO save(DisabilityDTO disabilityDTO) {
        log.debug("Request to save Disability : {}", disabilityDTO);
        Disability disability = disabilityMapper.toEntity(disabilityDTO);
        disability = disabilityRepository.save(disability);
        return disabilityMapper.toDto(disability);
    }

    /**
     * Get all the disabilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DisabilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Disabilities");
        return disabilityRepository.findAll(pageable)
            .map(disabilityMapper::toDto);
    }


    /**
     * Get one disability by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DisabilityDTO> findOne(Long id) {
        log.debug("Request to get Disability : {}", id);
        return disabilityRepository.findById(id)
            .map(disabilityMapper::toDto);
    }

    /**
     * Delete the disability by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Disability : {}", id);
        disabilityRepository.deleteById(id);
    }
}
