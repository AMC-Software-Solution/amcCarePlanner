package com.amc.careplanner.service;

import com.amc.careplanner.domain.TenantDocument;
import com.amc.careplanner.repository.TenantDocumentRepository;
import com.amc.careplanner.service.dto.TenantDocumentDTO;
import com.amc.careplanner.service.mapper.TenantDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TenantDocument}.
 */
@Service
@Transactional
public class TenantDocumentService {

    private final Logger log = LoggerFactory.getLogger(TenantDocumentService.class);

    private final TenantDocumentRepository tenantDocumentRepository;

    private final TenantDocumentMapper tenantDocumentMapper;

    public TenantDocumentService(TenantDocumentRepository tenantDocumentRepository, TenantDocumentMapper tenantDocumentMapper) {
        this.tenantDocumentRepository = tenantDocumentRepository;
        this.tenantDocumentMapper = tenantDocumentMapper;
    }

    /**
     * Save a tenantDocument.
     *
     * @param tenantDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public TenantDocumentDTO save(TenantDocumentDTO tenantDocumentDTO) {
        log.debug("Request to save TenantDocument : {}", tenantDocumentDTO);
        TenantDocument tenantDocument = tenantDocumentMapper.toEntity(tenantDocumentDTO);
        tenantDocument = tenantDocumentRepository.save(tenantDocument);
        return tenantDocumentMapper.toDto(tenantDocument);
    }

    /**
     * Get all the tenantDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TenantDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TenantDocuments");
        return tenantDocumentRepository.findAll(pageable)
            .map(tenantDocumentMapper::toDto);
    }


    /**
     * Get one tenantDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TenantDocumentDTO> findOne(Long id) {
        log.debug("Request to get TenantDocument : {}", id);
        return tenantDocumentRepository.findById(id)
            .map(tenantDocumentMapper::toDto);
    }

    /**
     * Delete the tenantDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TenantDocument : {}", id);
        tenantDocumentRepository.deleteById(id);
    }
}
