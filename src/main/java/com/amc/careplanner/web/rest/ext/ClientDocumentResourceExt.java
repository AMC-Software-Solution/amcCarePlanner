package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.ClientDocumentService;
import com.amc.careplanner.web.rest.ClientDocumentResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.ClientDocumentDTO;
import com.amc.careplanner.service.ext.ClientDocumentServiceExt;
import com.amc.careplanner.service.dto.ClientDocumentCriteria;
import com.amc.careplanner.service.ClientDocumentQueryService;

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
 * REST controller for managing {@link com.amc.careplanner.domain.ClientDocument}.
 */
@RestController
@RequestMapping("/api/v1")
public class ClientDocumentResourceExt extends ClientDocumentResource{

    private final Logger log = LoggerFactory.getLogger(ClientDocumentResourceExt.class);

    private static final String ENTITY_NAME = "clientDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientDocumentServiceExt clientDocumentServiceExt;

    private final ClientDocumentQueryService clientDocumentQueryService;

    public ClientDocumentResourceExt(ClientDocumentServiceExt clientDocumentServiceExt, ClientDocumentQueryService clientDocumentQueryService) {
    	super(clientDocumentServiceExt,clientDocumentQueryService);
        this.clientDocumentServiceExt = clientDocumentServiceExt;
        this.clientDocumentQueryService = clientDocumentQueryService;
    }

    /**
     * {@code POST  /client-documents} : Create a new clientDocument.
     *
     * @param clientDocumentDTO the clientDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientDocumentDTO, or with status {@code 400 (Bad Request)} if the clientDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-documents")
    public ResponseEntity<ClientDocumentDTO> createClientDocument(@Valid @RequestBody ClientDocumentDTO clientDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save ClientDocument : {}", clientDocumentDTO);
        if (clientDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientDocumentDTO result = clientDocumentServiceExt.save(clientDocumentDTO);
        return ResponseEntity.created(new URI("/api/client-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-documents} : Updates an existing clientDocument.
     *
     * @param clientDocumentDTO the clientDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the clientDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-documents")
    public ResponseEntity<ClientDocumentDTO> updateClientDocument(@Valid @RequestBody ClientDocumentDTO clientDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update ClientDocument : {}", clientDocumentDTO);
        if (clientDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientDocumentDTO result = clientDocumentServiceExt.save(clientDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-documents} : get all the clientDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientDocuments in body.
     */
    @GetMapping("/client-documents")
    public ResponseEntity<List<ClientDocumentDTO>> getAllClientDocuments(ClientDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClientDocuments by criteria: {}", criteria);
        Page<ClientDocumentDTO> page = clientDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-documents/count} : count all the clientDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/client-documents/count")
    public ResponseEntity<Long> countClientDocuments(ClientDocumentCriteria criteria) {
        log.debug("REST request to count ClientDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /client-documents/:id} : get the "id" clientDocument.
     *
     * @param id the id of the clientDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-documents/{id}")
    public ResponseEntity<ClientDocumentDTO> getClientDocument(@PathVariable Long id) {
        log.debug("REST request to get ClientDocument : {}", id);
        Optional<ClientDocumentDTO> clientDocumentDTO = clientDocumentServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientDocumentDTO);
    }

    /**
     * {@code DELETE  /client-documents/:id} : delete the "id" clientDocument.
     *
     * @param id the id of the clientDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-documents/{id}")
    public ResponseEntity<Void> deleteClientDocument(@PathVariable Long id) {
        log.debug("REST request to delete ClientDocument : {}", id);
        clientDocumentServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
