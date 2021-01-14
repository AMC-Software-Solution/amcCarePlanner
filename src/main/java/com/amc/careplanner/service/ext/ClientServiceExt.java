package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Client;
import com.amc.careplanner.repository.ClientRepository;
import com.amc.careplanner.repository.ext.ClientRepositoryExt;
import com.amc.careplanner.service.ClientService;
import com.amc.careplanner.service.dto.ClientDTO;
import com.amc.careplanner.service.mapper.ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientServiceExt  extends ClientService{

    private final Logger log = LoggerFactory.getLogger(ClientServiceExt.class);

    private final ClientRepositoryExt clientRepositoryExt;

    private final ClientMapper clientMapper;

    public ClientServiceExt(ClientRepositoryExt clientRepositoryExt, ClientMapper clientMapper) {
    	super(clientRepositoryExt,clientMapper);
        this.clientRepositoryExt = clientRepositoryExt;
        this.clientMapper = clientMapper;
    }

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepositoryExt.save(client);
        return clientMapper.toDto(client);
    }

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepositoryExt.findAll(pageable)
            .map(clientMapper::toDto);
    }


    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientDTO> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepositoryExt.findById(id)
            .map(clientMapper::toDto);
    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepositoryExt.deleteById(id);
    }
}
