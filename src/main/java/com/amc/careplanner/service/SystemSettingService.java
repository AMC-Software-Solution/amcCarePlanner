package com.amc.careplanner.service;

import com.amc.careplanner.domain.SystemSetting;
import com.amc.careplanner.repository.SystemSettingRepository;
import com.amc.careplanner.service.dto.SystemSettingDTO;
import com.amc.careplanner.service.mapper.SystemSettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SystemSetting}.
 */
@Service
@Transactional
public class SystemSettingService {

    private final Logger log = LoggerFactory.getLogger(SystemSettingService.class);

    private final SystemSettingRepository systemSettingRepository;

    private final SystemSettingMapper systemSettingMapper;

    public SystemSettingService(SystemSettingRepository systemSettingRepository, SystemSettingMapper systemSettingMapper) {
        this.systemSettingRepository = systemSettingRepository;
        this.systemSettingMapper = systemSettingMapper;
    }

    /**
     * Save a systemSetting.
     *
     * @param systemSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public SystemSettingDTO save(SystemSettingDTO systemSettingDTO) {
        log.debug("Request to save SystemSetting : {}", systemSettingDTO);
        SystemSetting systemSetting = systemSettingMapper.toEntity(systemSettingDTO);
        systemSetting = systemSettingRepository.save(systemSetting);
        return systemSettingMapper.toDto(systemSetting);
    }

    /**
     * Get all the systemSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemSettings");
        return systemSettingRepository.findAll(pageable)
            .map(systemSettingMapper::toDto);
    }


    /**
     * Get one systemSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SystemSettingDTO> findOne(Long id) {
        log.debug("Request to get SystemSetting : {}", id);
        return systemSettingRepository.findById(id)
            .map(systemSettingMapper::toDto);
    }

    /**
     * Delete the systemSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SystemSetting : {}", id);
        systemSettingRepository.deleteById(id);
    }
}
