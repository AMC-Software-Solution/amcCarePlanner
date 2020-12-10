package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Disability;
import com.amc.careplanner.repository.DisabilityRepository;
import com.amc.careplanner.repository.ext.DisabilityRepositoryExt;
import com.amc.careplanner.service.DisabilityService;
import com.amc.careplanner.service.dto.DisabilityDTO;
import com.amc.careplanner.service.mapper.DisabilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Disability}.
 */
@Service
@Transactional
public class DisabilityServiceExt extends DisabilityService{

    private final Logger log = LoggerFactory.getLogger(DisabilityServiceExt.class);

    private final DisabilityRepositoryExt disabilityRepositoryExt;

    private final DisabilityMapper disabilityMapper;

    public DisabilityServiceExt(DisabilityRepositoryExt disabilityRepositoryExt, DisabilityMapper disabilityMapper) {
    	super(disabilityRepositoryExt,disabilityMapper);
        this.disabilityRepositoryExt = disabilityRepositoryExt;
        this.disabilityMapper = disabilityMapper;
    }

    /**
     * Save a disability.
     *
     * @param disabilityDTO the entity to save.
     * @return the persisted entity.
     */
    public DisabilityDTO save(DisabilityDTO disabilityDTO) {
        log.debug("Request to save Disability : {}", disabilityDTO);
        Disability disability = disabilityMapper.toEntity(disabilityDTO);
        disability = disabilityRepositoryExt.save(disability);
        return disabilityMapper.toDto(disability);
    }

    /**
     * Get all the disabilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DisabilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Disabilities");
        return disabilityRepositoryExt.findAll(pageable)
            .map(disabilityMapper::toDto);
    }


    /**
     * Get one disability by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DisabilityDTO> findOne(Long id) {
        log.debug("Request to get Disability : {}", id);
        return disabilityRepositoryExt.findById(id)
            .map(disabilityMapper::toDto);
    }

    /**
     * Delete the disability by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Disability : {}", id);
        disabilityRepositoryExt.deleteById(id);
    }
}
