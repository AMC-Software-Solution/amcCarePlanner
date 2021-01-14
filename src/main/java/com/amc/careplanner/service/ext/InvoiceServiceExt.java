package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Invoice;
import com.amc.careplanner.repository.InvoiceRepository;
import com.amc.careplanner.repository.ext.InvoiceRepositoryExt;
import com.amc.careplanner.service.InvoiceService;
import com.amc.careplanner.service.dto.InvoiceDTO;
import com.amc.careplanner.service.mapper.InvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Invoice}.
 */
@Service
@Transactional
public class InvoiceServiceExt extends InvoiceService{

    private final Logger log = LoggerFactory.getLogger(InvoiceServiceExt.class);

    private final InvoiceRepositoryExt invoiceRepositoryExt;

    private final InvoiceMapper invoiceMapper;

    public InvoiceServiceExt(InvoiceRepositoryExt invoiceRepositoryExt, InvoiceMapper invoiceMapper) {
        super(invoiceRepositoryExt,invoiceMapper);
    	this.invoiceRepositoryExt = invoiceRepositoryExt;
        this.invoiceMapper = invoiceMapper;
    }

    /**
     * Save a invoice.
     *
     * @param invoiceDTO the entity to save.
     * @return the persisted entity.
     */
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        log.debug("Request to save Invoice : {}", invoiceDTO);
        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        invoice = invoiceRepositoryExt.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    /**
     * Get all the invoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Invoices");
        return invoiceRepositoryExt.findAll(pageable)
            .map(invoiceMapper::toDto);
    }


    /**
     * Get one invoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceDTO> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepositoryExt.findById(id)
            .map(invoiceMapper::toDto);
    }

    /**
     * Delete the invoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepositoryExt.deleteById(id);
    }
}
