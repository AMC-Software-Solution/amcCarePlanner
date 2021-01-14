package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Travel;
import com.amc.careplanner.repository.TravelRepository;
import com.amc.careplanner.repository.ext.TravelRepositoryExt;
import com.amc.careplanner.service.TravelService;
import com.amc.careplanner.service.dto.TravelDTO;
import com.amc.careplanner.service.mapper.TravelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Travel}.
 */
@Service
@Transactional
public class TravelServiceExt extends TravelService{

    private final Logger log = LoggerFactory.getLogger(TravelServiceExt.class);

    private final TravelRepositoryExt travelRepositoryExt;

    private final TravelMapper travelMapper;

    public TravelServiceExt(TravelRepositoryExt travelRepositoryExt, TravelMapper travelMapper) {
        super(travelRepositoryExt,travelMapper);
    	this.travelRepositoryExt = travelRepositoryExt;
        this.travelMapper = travelMapper;
    }

    /**
     * Save a travel.
     *
     * @param travelDTO the entity to save.
     * @return the persisted entity.
     */
    public TravelDTO save(TravelDTO travelDTO) {
        log.debug("Request to save Travel : {}", travelDTO);
        Travel travel = travelMapper.toEntity(travelDTO);
        travel = travelRepositoryExt.save(travel);
        return travelMapper.toDto(travel);
    }

    /**
     * Get all the travels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TravelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Travels");
        return travelRepositoryExt.findAll(pageable)
            .map(travelMapper::toDto);
    }


    /**
     * Get one travel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TravelDTO> findOne(Long id) {
        log.debug("Request to get Travel : {}", id);
        return travelRepositoryExt.findById(id)
            .map(travelMapper::toDto);
    }

    /**
     * Delete the travel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Travel : {}", id);
        travelRepositoryExt.deleteById(id);
    }
}
