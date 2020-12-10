package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.DisabilityType;
import com.amc.careplanner.repository.DisabilityTypeRepository;
import com.amc.careplanner.repository.ext.DisabilityTypeRepositoryExt;
import com.amc.careplanner.service.DisabilityTypeService;
import com.amc.careplanner.service.dto.DisabilityTypeDTO;
import com.amc.careplanner.service.mapper.DisabilityTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DisabilityType}.
 */
@Service
@Transactional
public class DisabilityTypeServiceExt extends DisabilityTypeService{

    private final Logger log = LoggerFactory.getLogger(DisabilityTypeServiceExt.class);

    private final DisabilityTypeRepositoryExt disabilityTypeRepositoryExt;

    private final DisabilityTypeMapper disabilityTypeMapper;

    public DisabilityTypeServiceExt(DisabilityTypeRepositoryExt disabilityTypeRepositoryExt, DisabilityTypeMapper disabilityTypeMapper) {
    	super(disabilityTypeRepositoryExt,disabilityTypeMapper);
        this.disabilityTypeRepositoryExt = disabilityTypeRepositoryExt;
        this.disabilityTypeMapper = disabilityTypeMapper;
    }

    /**
     * Save a disabilityType.
     *
     * @param disabilityTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public DisabilityTypeDTO save(DisabilityTypeDTO disabilityTypeDTO) {
        log.debug("Request to save DisabilityType : {}", disabilityTypeDTO);
        DisabilityType disabilityType = disabilityTypeMapper.toEntity(disabilityTypeDTO);
        disabilityType = disabilityTypeRepositoryExt.save(disabilityType);
        return disabilityTypeMapper.toDto(disabilityType);
    }

    /**
     * Get all the disabilityTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DisabilityTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DisabilityTypes");
        return disabilityTypeRepositoryExt.findAll(pageable)
            .map(disabilityTypeMapper::toDto);
    }


    /**
     * Get one disabilityType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DisabilityTypeDTO> findOne(Long id) {
        log.debug("Request to get DisabilityType : {}", id);
        return disabilityTypeRepositoryExt.findById(id)
            .map(disabilityTypeMapper::toDto);
    }

    /**
     * Delete the disabilityType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DisabilityType : {}", id);
        disabilityTypeRepositoryExt.deleteById(id);
    }
}
