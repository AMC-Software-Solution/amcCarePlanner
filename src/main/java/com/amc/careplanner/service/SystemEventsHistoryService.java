package com.amc.careplanner.service;

import com.amc.careplanner.domain.SystemEventsHistory;
import com.amc.careplanner.repository.SystemEventsHistoryRepository;
import com.amc.careplanner.service.dto.SystemEventsHistoryDTO;
import com.amc.careplanner.service.mapper.SystemEventsHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SystemEventsHistory}.
 */
@Service
@Transactional
public class SystemEventsHistoryService {

    private final Logger log = LoggerFactory.getLogger(SystemEventsHistoryService.class);

    private final SystemEventsHistoryRepository systemEventsHistoryRepository;

    private final SystemEventsHistoryMapper systemEventsHistoryMapper;

    public SystemEventsHistoryService(SystemEventsHistoryRepository systemEventsHistoryRepository, SystemEventsHistoryMapper systemEventsHistoryMapper) {
        this.systemEventsHistoryRepository = systemEventsHistoryRepository;
        this.systemEventsHistoryMapper = systemEventsHistoryMapper;
    }

    /**
     * Save a systemEventsHistory.
     *
     * @param systemEventsHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public SystemEventsHistoryDTO save(SystemEventsHistoryDTO systemEventsHistoryDTO) {
        log.debug("Request to save SystemEventsHistory : {}", systemEventsHistoryDTO);
        SystemEventsHistory systemEventsHistory = systemEventsHistoryMapper.toEntity(systemEventsHistoryDTO);
        systemEventsHistory = systemEventsHistoryRepository.save(systemEventsHistory);
        return systemEventsHistoryMapper.toDto(systemEventsHistory);
    }

    /**
     * Get all the systemEventsHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemEventsHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemEventsHistories");
        return systemEventsHistoryRepository.findAll(pageable)
            .map(systemEventsHistoryMapper::toDto);
    }


    /**
     * Get one systemEventsHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SystemEventsHistoryDTO> findOne(Long id) {
        log.debug("Request to get SystemEventsHistory : {}", id);
        return systemEventsHistoryRepository.findById(id)
            .map(systemEventsHistoryMapper::toDto);
    }

    /**
     * Delete the systemEventsHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SystemEventsHistory : {}", id);
        systemEventsHistoryRepository.deleteById(id);
    }
}
