package com.amc.careplanner.service;

import com.amc.careplanner.domain.EmployeeDocument;
import com.amc.careplanner.repository.EmployeeDocumentRepository;
import com.amc.careplanner.service.dto.EmployeeDocumentDTO;
import com.amc.careplanner.service.mapper.EmployeeDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeDocument}.
 */
@Service
@Transactional
public class EmployeeDocumentService {

    private final Logger log = LoggerFactory.getLogger(EmployeeDocumentService.class);

    private final EmployeeDocumentRepository employeeDocumentRepository;

    private final EmployeeDocumentMapper employeeDocumentMapper;

    public EmployeeDocumentService(EmployeeDocumentRepository employeeDocumentRepository, EmployeeDocumentMapper employeeDocumentMapper) {
        this.employeeDocumentRepository = employeeDocumentRepository;
        this.employeeDocumentMapper = employeeDocumentMapper;
    }

    /**
     * Save a employeeDocument.
     *
     * @param employeeDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDocumentDTO save(EmployeeDocumentDTO employeeDocumentDTO) {
        log.debug("Request to save EmployeeDocument : {}", employeeDocumentDTO);
        EmployeeDocument employeeDocument = employeeDocumentMapper.toEntity(employeeDocumentDTO);
        employeeDocument = employeeDocumentRepository.save(employeeDocument);
        return employeeDocumentMapper.toDto(employeeDocument);
    }

    /**
     * Get all the employeeDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeDocuments");
        return employeeDocumentRepository.findAll(pageable)
            .map(employeeDocumentMapper::toDto);
    }


    /**
     * Get one employeeDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDocumentDTO> findOne(Long id) {
        log.debug("Request to get EmployeeDocument : {}", id);
        return employeeDocumentRepository.findById(id)
            .map(employeeDocumentMapper::toDto);
    }

    /**
     * Delete the employeeDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeDocument : {}", id);
        employeeDocumentRepository.deleteById(id);
    }
}
