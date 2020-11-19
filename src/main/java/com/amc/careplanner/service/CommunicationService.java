package com.amc.careplanner.service;

import com.amc.careplanner.domain.Communication;
import com.amc.careplanner.repository.CommunicationRepository;
import com.amc.careplanner.service.dto.CommunicationDTO;
import com.amc.careplanner.service.mapper.CommunicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Communication}.
 */
@Service
@Transactional
public class CommunicationService {

    private final Logger log = LoggerFactory.getLogger(CommunicationService.class);

    private final CommunicationRepository communicationRepository;

    private final CommunicationMapper communicationMapper;

    public CommunicationService(CommunicationRepository communicationRepository, CommunicationMapper communicationMapper) {
        this.communicationRepository = communicationRepository;
        this.communicationMapper = communicationMapper;
    }

    /**
     * Save a communication.
     *
     * @param communicationDTO the entity to save.
     * @return the persisted entity.
     */
    public CommunicationDTO save(CommunicationDTO communicationDTO) {
        log.debug("Request to save Communication : {}", communicationDTO);
        Communication communication = communicationMapper.toEntity(communicationDTO);
        communication = communicationRepository.save(communication);
        return communicationMapper.toDto(communication);
    }

    /**
     * Get all the communications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommunicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Communications");
        return communicationRepository.findAll(pageable)
            .map(communicationMapper::toDto);
    }


    /**
     * Get one communication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommunicationDTO> findOne(Long id) {
        log.debug("Request to get Communication : {}", id);
        return communicationRepository.findById(id)
            .map(communicationMapper::toDto);
    }

    /**
     * Delete the communication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Communication : {}", id);
        communicationRepository.deleteById(id);
    }
}
