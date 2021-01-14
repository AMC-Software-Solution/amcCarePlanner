package com.amc.careplanner.service;

import com.amc.careplanner.domain.Access;
import com.amc.careplanner.repository.AccessRepository;
import com.amc.careplanner.service.dto.AccessDTO;
import com.amc.careplanner.service.mapper.AccessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Access}.
 */
@Service
@Transactional
public class AccessService {

    private final Logger log = LoggerFactory.getLogger(AccessService.class);

    private final AccessRepository accessRepository;

    private final AccessMapper accessMapper;

    public AccessService(AccessRepository accessRepository, AccessMapper accessMapper) {
        this.accessRepository = accessRepository;
        this.accessMapper = accessMapper;
    }

    /**
     * Save a access.
     *
     * @param accessDTO the entity to save.
     * @return the persisted entity.
     */
    public AccessDTO save(AccessDTO accessDTO) {
        log.debug("Request to save Access : {}", accessDTO);
        Access access = accessMapper.toEntity(accessDTO);
        access = accessRepository.save(access);
        return accessMapper.toDto(access);
    }

    /**
     * Get all the accesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accesses");
        return accessRepository.findAll(pageable)
            .map(accessMapper::toDto);
    }


    /**
     * Get one access by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccessDTO> findOne(Long id) {
        log.debug("Request to get Access : {}", id);
        return accessRepository.findById(id)
            .map(accessMapper::toDto);
    }

    /**
     * Delete the access by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Access : {}", id);
        accessRepository.deleteById(id);
    }
}
