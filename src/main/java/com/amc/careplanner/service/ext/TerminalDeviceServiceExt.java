package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.TerminalDevice;
import com.amc.careplanner.repository.TerminalDeviceRepository;
import com.amc.careplanner.repository.ext.TerminalDeviceRepositoryExt;
import com.amc.careplanner.service.TerminalDeviceService;
import com.amc.careplanner.service.dto.TerminalDeviceDTO;
import com.amc.careplanner.service.mapper.TerminalDeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TerminalDevice}.
 */
@Service
@Transactional
public class TerminalDeviceServiceExt extends TerminalDeviceService{

    private final Logger log = LoggerFactory.getLogger(TerminalDeviceServiceExt.class);

    private final TerminalDeviceRepositoryExt terminalDeviceRepositoryExt;

    private final TerminalDeviceMapper terminalDeviceMapper;

    public TerminalDeviceServiceExt(TerminalDeviceRepositoryExt terminalDeviceRepositoryExt, TerminalDeviceMapper terminalDeviceMapper) {
        super(terminalDeviceRepositoryExt,terminalDeviceMapper);
    	this.terminalDeviceRepositoryExt = terminalDeviceRepositoryExt;
        this.terminalDeviceMapper = terminalDeviceMapper;
    }

    /**
     * Save a terminalDevice.
     *
     * @param terminalDeviceDTO the entity to save.
     * @return the persisted entity.
     */
    public TerminalDeviceDTO save(TerminalDeviceDTO terminalDeviceDTO) {
        log.debug("Request to save TerminalDevice : {}", terminalDeviceDTO);
        TerminalDevice terminalDevice = terminalDeviceMapper.toEntity(terminalDeviceDTO);
        terminalDevice = terminalDeviceRepositoryExt.save(terminalDevice);
        return terminalDeviceMapper.toDto(terminalDevice);
    }

    /**
     * Get all the terminalDevices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TerminalDeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TerminalDevices");
        return terminalDeviceRepositoryExt.findAll(pageable)
            .map(terminalDeviceMapper::toDto);
    }


    /**
     * Get one terminalDevice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TerminalDeviceDTO> findOne(Long id) {
        log.debug("Request to get TerminalDevice : {}", id);
        return terminalDeviceRepositoryExt.findById(id)
            .map(terminalDeviceMapper::toDto);
    }

    /**
     * Delete the terminalDevice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TerminalDevice : {}", id);
        terminalDeviceRepositoryExt.deleteById(id);
    }
}
