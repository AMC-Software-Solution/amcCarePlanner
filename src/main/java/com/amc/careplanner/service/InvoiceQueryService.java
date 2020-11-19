package com.amc.careplanner.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.amc.careplanner.domain.Invoice;
import com.amc.careplanner.domain.*; // for static metamodels
import com.amc.careplanner.repository.InvoiceRepository;
import com.amc.careplanner.service.dto.InvoiceCriteria;
import com.amc.careplanner.service.dto.InvoiceDTO;
import com.amc.careplanner.service.mapper.InvoiceMapper;

/**
 * Service for executing complex queries for {@link Invoice} entities in the database.
 * The main input is a {@link InvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoiceDTO} or a {@link Page} of {@link InvoiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceQueryService extends QueryService<Invoice> {

    private final Logger log = LoggerFactory.getLogger(InvoiceQueryService.class);

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    public InvoiceQueryService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    /**
     * Return a {@link List} of {@link InvoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceDTO> findByCriteria(InvoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceMapper.toDto(invoiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InvoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceDTO> findByCriteria(InvoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.findAll(specification, page)
            .map(invoiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Invoice> createSpecification(InvoiceCriteria criteria) {
        Specification<Invoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Invoice_.id));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), Invoice_.totalAmount));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Invoice_.description));
            }
            if (criteria.getInvoiceNumber() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceNumber(), Invoice_.invoiceNumber));
            }
            if (criteria.getGeneratedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGeneratedDate(), Invoice_.generatedDate));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), Invoice_.dueDate));
            }
            if (criteria.getPaymentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentDate(), Invoice_.paymentDate));
            }
            if (criteria.getInvoiceStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceStatus(), Invoice_.invoiceStatus));
            }
            if (criteria.getTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTax(), Invoice_.tax));
            }
            if (criteria.getAttribute1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute1(), Invoice_.attribute1));
            }
            if (criteria.getAttribute2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute2(), Invoice_.attribute2));
            }
            if (criteria.getAttribute3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute3(), Invoice_.attribute3));
            }
            if (criteria.getAttribute4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute4(), Invoice_.attribute4));
            }
            if (criteria.getAttribute5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute5(), Invoice_.attribute5));
            }
            if (criteria.getAttribute6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute6(), Invoice_.attribute6));
            }
            if (criteria.getAttribute7() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribute7(), Invoice_.attribute7));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Invoice_.lastUpdatedDate));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenantId(), Invoice_.tenantId));
            }
            if (criteria.getServiceOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceOrderId(),
                    root -> root.join(Invoice_.serviceOrder, JoinType.LEFT).get(ServiceOrder_.id)));
            }
            if (criteria.getServiceUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceUserId(),
                    root -> root.join(Invoice_.serviceUser, JoinType.LEFT).get(ServiceUser_.id)));
            }
        }
        return specification;
    }
}
