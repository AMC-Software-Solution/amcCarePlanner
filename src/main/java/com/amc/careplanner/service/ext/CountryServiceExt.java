package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Country;
import com.amc.careplanner.repository.CountryRepository;
import com.amc.careplanner.repository.ext.CountryRepositoryExt;
import com.amc.careplanner.service.CountryService;
import com.amc.careplanner.service.dto.CountryDTO;
import com.amc.careplanner.service.mapper.CountryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Country}.
 */
@Service
@Transactional
public class CountryServiceExt extends CountryService{

    private final Logger log = LoggerFactory.getLogger(CountryServiceExt.class);

    private final CountryRepositoryExt countryRepositoryExt;

    private final CountryMapper countryMapper;

    public CountryServiceExt(CountryRepositoryExt countryRepositoryExt, CountryMapper countryMapper) {
    	super(countryRepositoryExt,countryMapper);
        this.countryRepositoryExt = countryRepositoryExt;
        this.countryMapper = countryMapper;
    }

    /**
     * Save a country.
     *
     * @param countryDTO the entity to save.
     * @return the persisted entity.
     */
    public CountryDTO save(CountryDTO countryDTO) {
        log.debug("Request to save Country : {}", countryDTO);
        Country country = countryMapper.toEntity(countryDTO);
        country = countryRepositoryExt.save(country);
        return countryMapper.toDto(country);
    }

    /**
     * Get all the countries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CountryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Countries");
        return countryRepositoryExt.findAll(pageable)
            .map(countryMapper::toDto);
    }


    /**
     * Get one country by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CountryDTO> findOne(Long id) {
        log.debug("Request to get Country : {}", id);
        return countryRepositoryExt.findById(id)
            .map(countryMapper::toDto);
    }

    /**
     * Delete the country by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Country : {}", id);
        countryRepositoryExt.deleteById(id);
    }
}
