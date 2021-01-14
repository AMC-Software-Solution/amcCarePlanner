package com.amc.careplanner.service;

import com.amc.careplanner.domain.Consent;
import com.amc.careplanner.repository.ConsentRepository;
import com.amc.careplanner.service.dto.ConsentDTO;
import com.amc.careplanner.service.mapper.ConsentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Consent}.
 */
@Service
@Transactional
public class ConsentService {

    private final Logger log = LoggerFactory.getLogger(ConsentService.class);

    private final ConsentRepository consentRepository;

    private final ConsentMapper consentMapper;

    public ConsentService(ConsentRepository consentRepository, ConsentMapper consentMapper) {
        this.consentRepository = consentRepository;
        this.consentMapper = consentMapper;
    }

    /**
     * Save a consent.
     *
     * @param consentDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsentDTO save(ConsentDTO consentDTO) {
        log.debug("Request to save Consent : {}", consentDTO);
        Consent consent = consentMapper.toEntity(consentDTO);
        consent = consentRepository.save(consent);
        return consentMapper.toDto(consent);
    }

    /**
     * Get all the consents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consents");
        return consentRepository.findAll(pageable)
            .map(consentMapper::toDto);
    }


    /**
     * Get one consent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConsentDTO> findOne(Long id) {
        log.debug("Request to get Consent : {}", id);
        return consentRepository.findById(id)
            .map(consentMapper::toDto);
    }

    /**
     * Delete the consent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Consent : {}", id);
        consentRepository.deleteById(id);
    }
}
