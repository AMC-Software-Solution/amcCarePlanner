package com.amc.careplanner.service;

import com.amc.careplanner.domain.ServceUserDocument;
import com.amc.careplanner.repository.ServceUserDocumentRepository;
import com.amc.careplanner.service.dto.ServceUserDocumentDTO;
import com.amc.careplanner.service.mapper.ServceUserDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServceUserDocument}.
 */
@Service
@Transactional
public class ServceUserDocumentService {

    private final Logger log = LoggerFactory.getLogger(ServceUserDocumentService.class);

    private final ServceUserDocumentRepository servceUserDocumentRepository;

    private final ServceUserDocumentMapper servceUserDocumentMapper;

    public ServceUserDocumentService(ServceUserDocumentRepository servceUserDocumentRepository, ServceUserDocumentMapper servceUserDocumentMapper) {
        this.servceUserDocumentRepository = servceUserDocumentRepository;
        this.servceUserDocumentMapper = servceUserDocumentMapper;
    }

    /**
     * Save a servceUserDocument.
     *
     * @param servceUserDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public ServceUserDocumentDTO save(ServceUserDocumentDTO servceUserDocumentDTO) {
        log.debug("Request to save ServceUserDocument : {}", servceUserDocumentDTO);
        ServceUserDocument servceUserDocument = servceUserDocumentMapper.toEntity(servceUserDocumentDTO);
        servceUserDocument = servceUserDocumentRepository.save(servceUserDocument);
        return servceUserDocumentMapper.toDto(servceUserDocument);
    }

    /**
     * Get all the servceUserDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServceUserDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServceUserDocuments");
        return servceUserDocumentRepository.findAll(pageable)
            .map(servceUserDocumentMapper::toDto);
    }


    /**
     * Get one servceUserDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServceUserDocumentDTO> findOne(Long id) {
        log.debug("Request to get ServceUserDocument : {}", id);
        return servceUserDocumentRepository.findById(id)
            .map(servceUserDocumentMapper::toDto);
    }

    /**
     * Delete the servceUserDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServceUserDocument : {}", id);
        servceUserDocumentRepository.deleteById(id);
    }
}
