package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.ClientDocument;
import com.amc.careplanner.repository.ClientDocumentRepository;
import com.amc.careplanner.repository.ext.ClientDocumentRepositoryExt;
import com.amc.careplanner.service.ClientDocumentService;
import com.amc.careplanner.service.dto.ClientDocumentDTO;
import com.amc.careplanner.service.mapper.ClientDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClientDocument}.
 */
@Service
@Transactional
public class ClientDocumentServiceExt extends ClientDocumentService{

    private final Logger log = LoggerFactory.getLogger(ClientDocumentServiceExt.class);

    private final ClientDocumentRepositoryExt clientDocumentRepositoryExt;

    private final ClientDocumentMapper clientDocumentMapper;

    public ClientDocumentServiceExt(ClientDocumentRepositoryExt clientDocumentRepositoryExt, ClientDocumentMapper clientDocumentMapper) {
    	super(clientDocumentRepositoryExt,clientDocumentMapper);
        this.clientDocumentRepositoryExt = clientDocumentRepositoryExt;
        this.clientDocumentMapper = clientDocumentMapper;
    }

    /**
     * Save a clientDocument.
     *
     * @param clientDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientDocumentDTO save(ClientDocumentDTO clientDocumentDTO) {
        log.debug("Request to save ClientDocument : {}", clientDocumentDTO);
        ClientDocument clientDocument = clientDocumentMapper.toEntity(clientDocumentDTO);
        clientDocument = clientDocumentRepositoryExt.save(clientDocument);
        return clientDocumentMapper.toDto(clientDocument);
    }

    /**
     * Get all the clientDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientDocuments");
        return clientDocumentRepositoryExt.findAll(pageable)
            .map(clientDocumentMapper::toDto);
    }


    /**
     * Get one clientDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientDocumentDTO> findOne(Long id) {
        log.debug("Request to get ClientDocument : {}", id);
        return clientDocumentRepositoryExt.findById(id)
            .map(clientDocumentMapper::toDto);
    }

    /**
     * Delete the clientDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientDocument : {}", id);
        clientDocumentRepositoryExt.deleteById(id);
    }
}
