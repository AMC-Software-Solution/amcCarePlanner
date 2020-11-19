package com.amc.careplanner.service;

import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.ServiceUserRepository;
import com.amc.careplanner.service.dto.ServiceUserDTO;
import com.amc.careplanner.service.mapper.ServiceUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceUser}.
 */
@Service
@Transactional
public class ServiceUserService {

    private final Logger log = LoggerFactory.getLogger(ServiceUserService.class);

    private final ServiceUserRepository serviceUserRepository;

    private final ServiceUserMapper serviceUserMapper;

    public ServiceUserService(ServiceUserRepository serviceUserRepository, ServiceUserMapper serviceUserMapper) {
        this.serviceUserRepository = serviceUserRepository;
        this.serviceUserMapper = serviceUserMapper;
    }

    /**
     * Save a serviceUser.
     *
     * @param serviceUserDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceUserDTO save(ServiceUserDTO serviceUserDTO) {
        log.debug("Request to save ServiceUser : {}", serviceUserDTO);
        ServiceUser serviceUser = serviceUserMapper.toEntity(serviceUserDTO);
        serviceUser = serviceUserRepository.save(serviceUser);
        return serviceUserMapper.toDto(serviceUser);
    }

    /**
     * Get all the serviceUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceUsers");
        return serviceUserRepository.findAll(pageable)
            .map(serviceUserMapper::toDto);
    }


    /**
     * Get one serviceUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceUserDTO> findOne(Long id) {
        log.debug("Request to get ServiceUser : {}", id);
        return serviceUserRepository.findById(id)
            .map(serviceUserMapper::toDto);
    }

    /**
     * Delete the serviceUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceUser : {}", id);
        serviceUserRepository.deleteById(id);
    }
}
