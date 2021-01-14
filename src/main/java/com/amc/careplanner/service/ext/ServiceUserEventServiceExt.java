package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.ServiceUserEvent;
import com.amc.careplanner.repository.ServiceUserEventRepository;
import com.amc.careplanner.repository.ext.ServiceUserEventRepositoryExt;
import com.amc.careplanner.service.ServiceUserEventService;
import com.amc.careplanner.service.dto.ServiceUserEventDTO;
import com.amc.careplanner.service.mapper.ServiceUserEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceUserEvent}.
 */
@Service
@Transactional
public class ServiceUserEventServiceExt extends ServiceUserEventService{

    private final Logger log = LoggerFactory.getLogger(ServiceUserEventServiceExt.class);

    private final ServiceUserEventRepositoryExt serviceUserEventRepositoryExt;

    private final ServiceUserEventMapper serviceUserEventMapper;

    public ServiceUserEventServiceExt(ServiceUserEventRepositoryExt serviceUserEventRepositoryExt, ServiceUserEventMapper serviceUserEventMapper) {
        super(serviceUserEventRepositoryExt,serviceUserEventMapper);
    	this.serviceUserEventRepositoryExt = serviceUserEventRepositoryExt;
        this.serviceUserEventMapper = serviceUserEventMapper;
    }

    /**
     * Save a serviceUserEvent.
     *
     * @param serviceUserEventDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceUserEventDTO save(ServiceUserEventDTO serviceUserEventDTO) {
        log.debug("Request to save ServiceUserEvent : {}", serviceUserEventDTO);
        ServiceUserEvent serviceUserEvent = serviceUserEventMapper.toEntity(serviceUserEventDTO);
        serviceUserEvent = serviceUserEventRepositoryExt.save(serviceUserEvent);
        return serviceUserEventMapper.toDto(serviceUserEvent);
    }

    /**
     * Get all the serviceUserEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceUserEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceUserEvents");
        return serviceUserEventRepositoryExt.findAll(pageable)
            .map(serviceUserEventMapper::toDto);
    }


    /**
     * Get one serviceUserEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceUserEventDTO> findOne(Long id) {
        log.debug("Request to get ServiceUserEvent : {}", id);
        return serviceUserEventRepositoryExt.findById(id)
            .map(serviceUserEventMapper::toDto);
    }

    /**
     * Delete the serviceUserEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceUserEvent : {}", id);
        serviceUserEventRepositoryExt.deleteById(id);
    }
}
