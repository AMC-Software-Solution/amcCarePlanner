package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.ClientService;
import com.amc.careplanner.web.rest.ClientResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ClientDTO;
import com.amc.careplanner.service.dto.CountryDTO;
import com.amc.careplanner.service.ext.ClientServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.utils.Constants;
import com.amc.careplanner.utils.RandomUtil;
import com.amc.careplanner.service.dto.ClientCriteria;
import com.amc.careplanner.s3.S3Service;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.service.ClientQueryService;

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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.Client}.
 */
@RestController
@RequestMapping("/api/v1")
public class ClientResourceExt extends ClientResource{

    private final Logger log = LoggerFactory.getLogger(ClientResourceExt.class);

    private static final String ENTITY_NAME = "client";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientServiceExt clientServiceExt;

    private final ClientQueryService clientQueryService;
    
    private final S3Service s3Service;

    public ClientResourceExt(ClientServiceExt clientServiceExt, ClientQueryService clientQueryService,S3Service s3Service) {
    	super(clientServiceExt,clientQueryService);
        this.clientServiceExt = clientServiceExt;
        this.clientQueryService = clientQueryService;
        this.s3Service = s3Service;
    }

    /**
     * {@code POST  /clients} : Create a new client.
     *
     * @param clientDTO the clientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientDTO, or with status {@code 400 (Bad Request)} if the client has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clients")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) throws URISyntaxException {
        log.debug("REST request to save Client : {}", clientDTO);
        if (clientDTO.getId() != null) {
            throw new BadRequestAlertException("A new client cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientDTO result = clientServiceExt.save(clientDTO);
        ClientDTO result2 = result;
        ClientDTO result3 = null;
  		if (clientDTO.getClientLogoContentType()!= null) {
  			String fileName = ENTITY_NAME + RandomUtil.generateRandomAlphaNum(10) + "-" + result.getId() + ".png";
  			String url = Constants.S3_ENDPOINT + fileName;
  			result.setClientLogoUrl(url);
  			byte[] imageBytes = CommonUtils.resize(CommonUtils.createImageFromBytes(clientDTO.getClientLogo()),
  					Constants.FULL_IMAGE_HEIGHT, Constants.FULL_IMAGE_WIDTH);
  			CommonUtils.uploadToS3(imageBytes, fileName, s3Service.getAmazonS3(),clientDTO.getClientLogoContentType());
  			result2 = clientServiceExt.save(result);
  			result2.setClientLogo(null);
  			result2.setClientLogoContentType(null);
  			 result3 = clientServiceExt.save(result2);
  		}
        
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result3);
    }

    /**
     * {@code PUT  /clients} : Updates an existing client.
     *
     * @param clientDTO the clientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientDTO,
     * or with status {@code 400 (Bad Request)} if the clientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clients")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ClientDTO> updateClient(@Valid @RequestBody ClientDTO clientDTO) throws URISyntaxException {
        log.debug("REST request to update Client : {}", clientDTO);
        if (clientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientDTO result = clientServiceExt.save(clientDTO);
        ClientDTO result2 = result;
        ClientDTO result3 = null;
  		if (clientDTO.getClientLogoContentType()!= null) {
  			String fileName = ENTITY_NAME + RandomUtil.generateRandomAlphaNum(10) + "-" + result.getId() + ".png";
  			String url = Constants.S3_ENDPOINT + fileName;
  			result.setClientLogoUrl(url);
  			byte[] imageBytes = CommonUtils.resize(CommonUtils.createImageFromBytes(clientDTO.getClientLogo()),
  					Constants.FULL_IMAGE_HEIGHT, Constants.FULL_IMAGE_WIDTH);
  			CommonUtils.uploadToS3(imageBytes, fileName, s3Service.getAmazonS3(),clientDTO.getClientLogoContentType());
  			result2 = clientServiceExt.save(result);
  			result2.setClientLogo(null);
  			result2.setClientLogoContentType(null);
  			 result3 = clientServiceExt.save(result2);
  		}
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientDTO.getId().toString()))
            .body(result3);
    }

    /**
     * {@code GET  /clients} : get all the clients.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clients in body.
     */
    @GetMapping("/clients")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<ClientDTO>> getAllClients(ClientCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Clients by criteria: {}", criteria);
        Page<ClientDTO> page = clientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clients/count} : count all the clients.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/clients/count")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Long> countClients(ClientCriteria criteria) {
        log.debug("REST request to count Clients by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /clients/:id} : get the "id" client.
     *
     * @param id the id of the clientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clients/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        Optional<ClientDTO> clientDTO = clientServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientDTO);
    }

    /**
     * {@code DELETE  /clients/:id} : delete the "id" client.
     *
     * @param id the id of the clientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clients/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
