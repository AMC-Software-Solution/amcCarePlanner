package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.CountryService;
import com.amc.careplanner.web.rest.CountryResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.CountryDTO;
import com.amc.careplanner.service.dto.EmployeeHolidayCriteria;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;
import com.amc.careplanner.service.ext.CountryServiceExt;
import com.amc.careplanner.service.dto.CountryCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.CountryQueryService;

import io.github.jhipster.service.filter.LongFilter;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.Country}.
 */
@RestController
@RequestMapping("/api/v1")
public class CountryResourceExt extends CountryResource{

    private final Logger log = LoggerFactory.getLogger(CountryResourceExt.class);

    private static final String ENTITY_NAME = "country";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryServiceExt countryServiceExt;

    private final CountryQueryService countryQueryService;
    
    private final UserRepositoryExt userRepositoryExt;

    public CountryResourceExt(CountryServiceExt countryServiceExt, CountryQueryService countryQueryService, UserRepositoryExt userRepositoryExt) {
    	super(countryServiceExt,countryQueryService);
        this.countryServiceExt = countryServiceExt;
        this.countryQueryService = countryQueryService;
        this.userRepositoryExt = userRepositoryExt;
    }

    /**
     * {@code POST  /countries} : Create a new country.
     *
     * @param countryDTO the countryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countryDTO, or with status {@code 400 (Bad Request)} if the country has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-country-by-client-id")
    public ResponseEntity<CountryDTO> createCountry(@Valid @RequestBody CountryDTO countryDTO) throws URISyntaxException {
        log.debug("REST request to save Country : {}", countryDTO);
        if (countryDTO.getId() != null) {
            throw new BadRequestAlertException("A new country cannot already have an ID", ENTITY_NAME, "idexists");
        }
//      countryDTO.setDateCreated(ZonedDateTime.now());
        countryDTO.setLastUpdatedDate(ZonedDateTime.now());
//        countryDTO.setClientId(getClientIdFromLoggedInUser());
        CountryDTO result = countryServiceExt.save(countryDTO);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /countries} : Updates an existing country.
     *
     * @param countryDTO the countryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryDTO,
     * or with status {@code 400 (Bad Request)} if the countryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-country-by-client-id")
    public ResponseEntity<CountryDTO> updateCountry(@Valid @RequestBody CountryDTO countryDTO) throws URISyntaxException {
        log.debug("REST request to update Country : {}", countryDTO);
        if (countryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
//        if (countryDTO != null && countryDTO.getClientId() != null && countryDTO.getClientId() != getClientIdFromLoggedInUser()) {
//      	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
//      }
        countryDTO.setLastUpdatedDate(ZonedDateTime.now());
        CountryDTO result = countryServiceExt.save(countryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /countries} : get all the countries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countries in body.
     */
    @GetMapping("/get-all-countries-by-client-id")
    public ResponseEntity<List<CountryDTO>> getAllCountries(CountryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Countries by criteria: {}", criteria);
        CountryCriteria countryCriteria = new CountryCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
//		countryCriteria.setClientId(longFilterForClientId);
        Page<CountryDTO> page = countryQueryService.findByCriteria(countryCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /countries/count} : count all the countries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/countries/count")
    public ResponseEntity<Long> countCountries(CountryCriteria criteria) {
        log.debug("REST request to count Countries by criteria: {}", criteria);
        return ResponseEntity.ok().body(countryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /countries/:id} : get the "id" country.
     *
     * @param id the id of the countryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-country-by-client-id/{id}")
    public ResponseEntity<CountryDTO> getCountry(@PathVariable Long id) {
        log.debug("REST request to get Country : {}", id);
        Optional<CountryDTO> countryDTO = countryServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(countryDTO);
    }

    /**
     * {@code DELETE  /countries/:id} : delete the "id" country.
     *
     * @param id the id of the countryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-country-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        log.debug("REST request to delete Country : {}", id);
        countryServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    private Long getClientIdFromLoggedInUser() {
    	Long clientId = 0L;
    	String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();
		
		if(loggedInAdminUser != null) {
			clientId = Long.valueOf(loggedInAdminUser.getLogin());
		}
		
		return clientId;
    }
}
