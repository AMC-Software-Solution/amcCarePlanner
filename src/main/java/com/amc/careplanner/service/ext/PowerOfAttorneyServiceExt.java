package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.PowerOfAttorney;
import com.amc.careplanner.repository.PowerOfAttorneyRepository;
import com.amc.careplanner.repository.ext.PowerOfAttorneyRepositoryExt;
import com.amc.careplanner.service.PowerOfAttorneyService;
import com.amc.careplanner.service.dto.PowerOfAttorneyDTO;
import com.amc.careplanner.service.mapper.PowerOfAttorneyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PowerOfAttorney}.
 */
@Service
@Transactional
public class PowerOfAttorneyServiceExt extends PowerOfAttorneyService{

    private final Logger log = LoggerFactory.getLogger(PowerOfAttorneyServiceExt.class);

    private final PowerOfAttorneyRepositoryExt powerOfAttorneyRepositoryExt;

    private final PowerOfAttorneyMapper powerOfAttorneyMapper;

    public PowerOfAttorneyServiceExt(PowerOfAttorneyRepositoryExt powerOfAttorneyRepositoryExt, PowerOfAttorneyMapper powerOfAttorneyMapper) {
        super(powerOfAttorneyRepositoryExt,powerOfAttorneyMapper);
    	this.powerOfAttorneyRepositoryExt = powerOfAttorneyRepositoryExt;
        this.powerOfAttorneyMapper = powerOfAttorneyMapper;
    }

    /**
     * Save a powerOfAttorney.
     *
     * @param powerOfAttorneyDTO the entity to save.
     * @return the persisted entity.
     */
    public PowerOfAttorneyDTO save(PowerOfAttorneyDTO powerOfAttorneyDTO) {
        log.debug("Request to save PowerOfAttorney : {}", powerOfAttorneyDTO);
        PowerOfAttorney powerOfAttorney = powerOfAttorneyMapper.toEntity(powerOfAttorneyDTO);
        powerOfAttorney = powerOfAttorneyRepositoryExt.save(powerOfAttorney);
        return powerOfAttorneyMapper.toDto(powerOfAttorney);
    }

    /**
     * Get all the powerOfAttorneys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PowerOfAttorneyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PowerOfAttorneys");
        return powerOfAttorneyRepositoryExt.findAll(pageable)
            .map(powerOfAttorneyMapper::toDto);
    }


    /**
     * Get one powerOfAttorney by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PowerOfAttorneyDTO> findOne(Long id) {
        log.debug("Request to get PowerOfAttorney : {}", id);
        return powerOfAttorneyRepositoryExt.findById(id)
            .map(powerOfAttorneyMapper::toDto);
    }

    /**
     * Delete the powerOfAttorney by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PowerOfAttorney : {}", id);
        powerOfAttorneyRepositoryExt.deleteById(id);
    }
}
