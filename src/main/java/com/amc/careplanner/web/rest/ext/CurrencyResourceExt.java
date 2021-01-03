package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.CurrencyService;
import com.amc.careplanner.web.rest.CurrencyResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.CurrencyDTO;
import com.amc.careplanner.service.ext.CurrencyServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.utils.Constants;
import com.amc.careplanner.utils.RandomUtil;
import com.amc.careplanner.service.dto.ClientDTO;
import com.amc.careplanner.service.dto.CurrencyCriteria;
import com.amc.careplanner.s3.S3Service;
import com.amc.careplanner.service.CurrencyQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.Currency}.
 */
@RestController
@RequestMapping("/api/v1")
public class CurrencyResourceExt extends CurrencyResource{

    private final Logger log = LoggerFactory.getLogger(CurrencyResourceExt.class);

    private static final String ENTITY_NAME = "currency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrencyServiceExt currencyServiceExt;

    private final CurrencyQueryService currencyQueryService;
    
    private final S3Service s3Service;

    public CurrencyResourceExt(CurrencyServiceExt currencyServiceExt, CurrencyQueryService currencyQueryService, S3Service s3Service) {
    	super(currencyServiceExt,currencyQueryService);
        this.currencyServiceExt = currencyServiceExt;
        this.currencyQueryService = currencyQueryService;
        this.s3Service = s3Service;
    }

    /**
     * {@code POST  /currencies} : Create a new currency.
     *
     * @param currencyDTO the currencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currencyDTO, or with status {@code 400 (Bad Request)} if the currency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/currencies")
    public ResponseEntity<CurrencyDTO> createCurrency(@Valid @RequestBody CurrencyDTO currencyDTO) throws URISyntaxException {
        log.debug("REST request to save Currency : {}", currencyDTO);
        if (currencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new currency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrencyDTO result = currencyServiceExt.save(currencyDTO);
        CurrencyDTO result2 = result;
        CurrencyDTO result3 = null;
  		if (currencyDTO.getCurrencyLogoContentType()!= null) {
  			String fileName = ENTITY_NAME + RandomUtil.generateRandomAlphaNum(10) + "-" + result.getId() + ".png";
  			String url = Constants.S3_ENDPOINT + fileName;
  			result.setCurrencyLogoUrl(url);
  			byte[] imageBytes = CommonUtils.resize(CommonUtils.createImageFromBytes(currencyDTO.getCurrencyLogo()),
  					Constants.FULL_IMAGE_HEIGHT, Constants.FULL_IMAGE_WIDTH);
  			CommonUtils.uploadToS3(imageBytes, fileName, s3Service.getAmazonS3(),currencyDTO.getCurrencyLogoContentType());
  			result2 = currencyServiceExt.save(result);
  			result2.setCurrencyLogo(null);
  			result2.setCurrencyLogoContentType(null);
  			 result3 = currencyServiceExt.save(result2);
  		}	 
     	return ResponseEntity.created(new URI("/api/currencies/" + result.getId()))
  		            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
  		            .body(result3);
    }

    /**
     * {@code PUT  /currencies} : Updates an existing currency.
     *
     * @param currencyDTO the currencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyDTO,
     * or with status {@code 400 (Bad Request)} if the currencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/currencies")
    public ResponseEntity<CurrencyDTO> updateCurrency(@Valid @RequestBody CurrencyDTO currencyDTO) throws URISyntaxException {
        log.debug("REST request to update Currency : {}", currencyDTO);
        if (currencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrencyDTO result = currencyServiceExt.save(currencyDTO);
        CurrencyDTO result2 = result;
        CurrencyDTO result3 = null;
  		if (currencyDTO.getCurrencyLogoContentType()!= null) {
  			String fileName = ENTITY_NAME + RandomUtil.generateRandomAlphaNum(10) + "-" + result.getId() + ".png";
  			String url = Constants.S3_ENDPOINT + fileName;
  			result.setCurrencyLogoUrl(url);
  			byte[] imageBytes = CommonUtils.resize(CommonUtils.createImageFromBytes(currencyDTO.getCurrencyLogo()),
  					Constants.FULL_IMAGE_HEIGHT, Constants.FULL_IMAGE_WIDTH);
  			CommonUtils.uploadToS3(imageBytes, fileName, s3Service.getAmazonS3(),currencyDTO.getCurrencyLogoContentType());
  			result2 = currencyServiceExt.save(result);
  			result2.setCurrencyLogo(null);
  			result2.setCurrencyLogoContentType(null);
  			 result3 = currencyServiceExt.save(result2);
  		}
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /currencies} : get all the currencies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currencies in body.
     */
    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies(CurrencyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Currencies by criteria: {}", criteria);
        Page<CurrencyDTO> page = currencyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /currencies/count} : count all the currencies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/currencies/count")
    public ResponseEntity<Long> countCurrencies(CurrencyCriteria criteria) {
        log.debug("REST request to count Currencies by criteria: {}", criteria);
        return ResponseEntity.ok().body(currencyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /currencies/:id} : get the "id" currency.
     *
     * @param id the id of the currencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/currencies/{id}")
    public ResponseEntity<CurrencyDTO> getCurrency(@PathVariable Long id) {
        log.debug("REST request to get Currency : {}", id);
        Optional<CurrencyDTO> currencyDTO = currencyServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(currencyDTO);
    }

    /**
     * {@code DELETE  /currencies/:id} : delete the "id" currency.
     *
     * @param id the id of the currencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/currencies/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        log.debug("REST request to delete Currency : {}", id);
        currencyServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
