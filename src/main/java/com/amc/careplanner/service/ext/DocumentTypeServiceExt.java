package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.DocumentType;
import com.amc.careplanner.repository.DocumentTypeRepository;
import com.amc.careplanner.repository.ext.DocumentTypeRepositoryExt;
import com.amc.careplanner.service.DocumentTypeService;
import com.amc.careplanner.service.dto.DocumentTypeDTO;
import com.amc.careplanner.service.mapper.DocumentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentType}.
 */
@Service
@Transactional
public class DocumentTypeServiceExt extends DocumentTypeService{

    private final Logger log = LoggerFactory.getLogger(DocumentTypeServiceExt.class);

    private final DocumentTypeRepositoryExt documentTypeRepositoryExt;

    private final DocumentTypeMapper documentTypeMapper;

    public DocumentTypeServiceExt(DocumentTypeRepositoryExt documentTypeRepositoryExt, DocumentTypeMapper documentTypeMapper) {
    	super(documentTypeRepositoryExt,documentTypeMapper);
        this.documentTypeRepositoryExt = documentTypeRepositoryExt;
        this.documentTypeMapper = documentTypeMapper;
    }

    /**
     * Save a documentType.
     *
     * @param documentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentTypeDTO save(DocumentTypeDTO documentTypeDTO) {
        log.debug("Request to save DocumentType : {}", documentTypeDTO);
        DocumentType documentType = documentTypeMapper.toEntity(documentTypeDTO);
        documentType = documentTypeRepositoryExt.save(documentType);
        return documentTypeMapper.toDto(documentType);
    }

    /**
     * Get all the documentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentTypes");
        return documentTypeRepositoryExt.findAll(pageable)
            .map(documentTypeMapper::toDto);
    }


    /**
     * Get one documentType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentTypeDTO> findOne(Long id) {
        log.debug("Request to get DocumentType : {}", id);
        return documentTypeRepositoryExt.findById(id)
            .map(documentTypeMapper::toDto);
    }

    /**
     * Delete the documentType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentType : {}", id);
        documentTypeRepositoryExt.deleteById(id);
    }
}
