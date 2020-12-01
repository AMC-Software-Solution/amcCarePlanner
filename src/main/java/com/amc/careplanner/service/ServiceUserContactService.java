package com.amc.careplanner.service;

import com.amc.careplanner.domain.ServiceUserContact;
import com.amc.careplanner.repository.ServiceUserContactRepository;
import com.amc.careplanner.service.dto.ServiceUserContactDTO;
import com.amc.careplanner.service.mapper.ServiceUserContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceUserContact}.
 */
@Service
@Transactional
public class ServiceUserContactService {

    private final Logger log = LoggerFactory.getLogger(ServiceUserContactService.class);

    private final ServiceUserContactRepository serviceUserContactRepository;

    private final ServiceUserContactMapper serviceUserContactMapper;

    public ServiceUserContactService(ServiceUserContactRepository serviceUserContactRepository, ServiceUserContactMapper serviceUserContactMapper) {
        this.serviceUserContactRepository = serviceUserContactRepository;
        this.serviceUserContactMapper = serviceUserContactMapper;
    }

    /**
     * Save a serviceUserContact.
     *
     * @param serviceUserContactDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceUserContactDTO save(ServiceUserContactDTO serviceUserContactDTO) {
        log.debug("Request to save ServiceUserContact : {}", serviceUserContactDTO);
        ServiceUserContact serviceUserContact = serviceUserContactMapper.toEntity(serviceUserContactDTO);
        serviceUserContact = serviceUserContactRepository.save(serviceUserContact);
        return serviceUserContactMapper.toDto(serviceUserContact);
    }

    /**
     * Get all the serviceUserContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceUserContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceUserContacts");
        return serviceUserContactRepository.findAll(pageable)
            .map(serviceUserContactMapper::toDto);
    }


    /**
     * Get one serviceUserContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceUserContactDTO> findOne(Long id) {
        log.debug("Request to get ServiceUserContact : {}", id);
        return serviceUserContactRepository.findById(id)
            .map(serviceUserContactMapper::toDto);
    }

    /**
     * Delete the serviceUserContact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceUserContact : {}", id);
        serviceUserContactRepository.deleteById(id);
    }
}
