package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Invoice;
import com.amc.careplanner.repository.InvoiceRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Invoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepositoryExt extends InvoiceRepository{
}
