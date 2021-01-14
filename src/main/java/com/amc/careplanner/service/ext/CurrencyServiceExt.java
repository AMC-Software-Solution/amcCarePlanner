package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Currency;
import com.amc.careplanner.repository.CurrencyRepository;
import com.amc.careplanner.repository.ext.CurrencyRepositoryExt;
import com.amc.careplanner.service.CurrencyService;
import com.amc.careplanner.service.dto.CurrencyDTO;
import com.amc.careplanner.service.mapper.CurrencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Currency}.
 */
@Service
@Transactional
public class CurrencyServiceExt extends CurrencyService{

    private final Logger log = LoggerFactory.getLogger(CurrencyServiceExt.class);

    private final CurrencyRepositoryExt currencyRepositoryExt;

    private final CurrencyMapper currencyMapper;

    public CurrencyServiceExt(CurrencyRepositoryExt currencyRepositoryExt, CurrencyMapper currencyMapper) {
    	super(currencyRepositoryExt,currencyMapper);
        this.currencyRepositoryExt = currencyRepositoryExt;
        this.currencyMapper = currencyMapper;
    }

    /**
     * Save a currency.
     *
     * @param currencyDTO the entity to save.
     * @return the persisted entity.
     */
    public CurrencyDTO save(CurrencyDTO currencyDTO) {
        log.debug("Request to save Currency : {}", currencyDTO);
        Currency currency = currencyMapper.toEntity(currencyDTO);
        currency = currencyRepositoryExt.save(currency);
        return currencyMapper.toDto(currency);
    }

    /**
     * Get all the currencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CurrencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Currencies");
        return currencyRepositoryExt.findAll(pageable)
            .map(currencyMapper::toDto);
    }


    /**
     * Get one currency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CurrencyDTO> findOne(Long id) {
        log.debug("Request to get Currency : {}", id);
        return currencyRepositoryExt.findById(id)
            .map(currencyMapper::toDto);
    }

    /**
     * Delete the currency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Currency : {}", id);
        currencyRepositoryExt.deleteById(id);
    }
}
