package com.amc.careplanner.service;

import com.amc.careplanner.domain.MedicalContact;
import com.amc.careplanner.repository.MedicalContactRepository;
import com.amc.careplanner.service.dto.MedicalContactDTO;
import com.amc.careplanner.service.mapper.MedicalContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MedicalContact}.
 */
@Service
@Transactional
public class MedicalContactService {

    private final Logger log = LoggerFactory.getLogger(MedicalContactService.class);

    private final MedicalContactRepository medicalContactRepository;

    private final MedicalContactMapper medicalContactMapper;

    public MedicalContactService(MedicalContactRepository medicalContactRepository, MedicalContactMapper medicalContactMapper) {
        this.medicalContactRepository = medicalContactRepository;
        this.medicalContactMapper = medicalContactMapper;
    }

    /**
     * Save a medicalContact.
     *
     * @param medicalContactDTO the entity to save.
     * @return the persisted entity.
     */
    public MedicalContactDTO save(MedicalContactDTO medicalContactDTO) {
        log.debug("Request to save MedicalContact : {}", medicalContactDTO);
        MedicalContact medicalContact = medicalContactMapper.toEntity(medicalContactDTO);
        medicalContact = medicalContactRepository.save(medicalContact);
        return medicalContactMapper.toDto(medicalContact);
    }

    /**
     * Get all the medicalContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MedicalContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalContacts");
        return medicalContactRepository.findAll(pageable)
            .map(medicalContactMapper::toDto);
    }


    /**
     * Get one medicalContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MedicalContactDTO> findOne(Long id) {
        log.debug("Request to get MedicalContact : {}", id);
        return medicalContactRepository.findById(id)
            .map(medicalContactMapper::toDto);
    }

    /**
     * Delete the medicalContact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MedicalContact : {}", id);
        medicalContactRepository.deleteById(id);
    }
}
