package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.CarerClientRelation;
import com.amc.careplanner.repository.CarerClientRelationRepository;
import com.amc.careplanner.repository.ext.CarerClientRelationRepositoryExt;
import com.amc.careplanner.service.CarerClientRelationService;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;
import com.amc.careplanner.service.mapper.CarerClientRelationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CarerClientRelation}.
 */
@Service
@Transactional
public class CarerClientRelationServiceExt extends CarerClientRelationService{

    private final Logger log = LoggerFactory.getLogger(CarerClientRelationServiceExt.class);

    private final CarerClientRelationRepositoryExt carerClientRelationRepositoryExt;

    private final CarerClientRelationMapper carerClientRelationMapper;

    public CarerClientRelationServiceExt(CarerClientRelationRepositoryExt carerClientRelationRepositoryExt, CarerClientRelationMapper carerClientRelationMapper) {
    	super(carerClientRelationRepositoryExt,carerClientRelationMapper);
        this.carerClientRelationRepositoryExt = carerClientRelationRepositoryExt;
        this.carerClientRelationMapper = carerClientRelationMapper;
    }

    /**
     * Save a carerClientRelation.
     *
     * @param carerClientRelationDTO the entity to save.
     * @return the persisted entity.
     */
    public CarerClientRelationDTO save(CarerClientRelationDTO carerClientRelationDTO) {
        log.debug("Request to save CarerClientRelation : {}", carerClientRelationDTO);
        CarerClientRelation carerClientRelation = carerClientRelationMapper.toEntity(carerClientRelationDTO);
        carerClientRelation = carerClientRelationRepositoryExt.save(carerClientRelation);
        return carerClientRelationMapper.toDto(carerClientRelation);
    }

    /**
     * Get all the carerClientRelations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CarerClientRelationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarerClientRelations");
        return carerClientRelationRepositoryExt.findAll(pageable)
            .map(carerClientRelationMapper::toDto);
    }


    /**
     * Get one carerClientRelation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CarerClientRelationDTO> findOne(Long id) {
        log.debug("Request to get CarerClientRelation : {}", id);
        return carerClientRelationRepositoryExt.findById(id)
            .map(carerClientRelationMapper::toDto);
    }

    /**
     * Delete the carerClientRelation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CarerClientRelation : {}", id);
        carerClientRelationRepositoryExt.deleteById(id);
    }
}
