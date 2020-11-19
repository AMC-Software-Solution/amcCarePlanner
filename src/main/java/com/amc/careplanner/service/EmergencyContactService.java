package com.amc.careplanner.service;

import com.amc.careplanner.domain.EmergencyContact;
import com.amc.careplanner.repository.EmergencyContactRepository;
import com.amc.careplanner.service.dto.EmergencyContactDTO;
import com.amc.careplanner.service.mapper.EmergencyContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmergencyContact}.
 */
@Service
@Transactional
public class EmergencyContactService {

    private final Logger log = LoggerFactory.getLogger(EmergencyContactService.class);

    private final EmergencyContactRepository emergencyContactRepository;

    private final EmergencyContactMapper emergencyContactMapper;

    public EmergencyContactService(EmergencyContactRepository emergencyContactRepository, EmergencyContactMapper emergencyContactMapper) {
        this.emergencyContactRepository = emergencyContactRepository;
        this.emergencyContactMapper = emergencyContactMapper;
    }

    /**
     * Save a emergencyContact.
     *
     * @param emergencyContactDTO the entity to save.
     * @return the persisted entity.
     */
    public EmergencyContactDTO save(EmergencyContactDTO emergencyContactDTO) {
        log.debug("Request to save EmergencyContact : {}", emergencyContactDTO);
        EmergencyContact emergencyContact = emergencyContactMapper.toEntity(emergencyContactDTO);
        emergencyContact = emergencyContactRepository.save(emergencyContact);
        return emergencyContactMapper.toDto(emergencyContact);
    }

    /**
     * Get all the emergencyContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmergencyContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmergencyContacts");
        return emergencyContactRepository.findAll(pageable)
            .map(emergencyContactMapper::toDto);
    }


    /**
     * Get one emergencyContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmergencyContactDTO> findOne(Long id) {
        log.debug("Request to get EmergencyContact : {}", id);
        return emergencyContactRepository.findById(id)
            .map(emergencyContactMapper::toDto);
    }

    /**
     * Delete the emergencyContact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmergencyContact : {}", id);
        emergencyContactRepository.deleteById(id);
    }
}
