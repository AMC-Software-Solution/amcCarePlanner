package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.InvoiceStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.UUIDFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Invoice} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.InvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoiceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering InvoiceStatus
     */
    public static class InvoiceStatusFilter extends Filter<InvoiceStatus> {

        public InvoiceStatusFilter() {
        }

        public InvoiceStatusFilter(InvoiceStatusFilter filter) {
            super(filter);
        }

        @Override
        public InvoiceStatusFilter copy() {
            return new InvoiceStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter totalAmount;

    private StringFilter description;

    private UUIDFilter invoiceNumber;

    private ZonedDateTimeFilter generatedDate;

    private ZonedDateTimeFilter dueDate;

    private ZonedDateTimeFilter paymentDate;

    private InvoiceStatusFilter invoiceStatus;

    private DoubleFilter tax;

    private StringFilter attribute1;

    private StringFilter attribute2;

    private StringFilter attribute3;

    private StringFilter attribute4;

    private StringFilter attribute5;

    private StringFilter attribute6;

    private StringFilter attribute7;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter serviceOrderId;

    private LongFilter serviceUserId;

    public InvoiceCriteria() {
    }

    public InvoiceCriteria(InvoiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.invoiceNumber = other.invoiceNumber == null ? null : other.invoiceNumber.copy();
        this.generatedDate = other.generatedDate == null ? null : other.generatedDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.paymentDate = other.paymentDate == null ? null : other.paymentDate.copy();
        this.invoiceStatus = other.invoiceStatus == null ? null : other.invoiceStatus.copy();
        this.tax = other.tax == null ? null : other.tax.copy();
        this.attribute1 = other.attribute1 == null ? null : other.attribute1.copy();
        this.attribute2 = other.attribute2 == null ? null : other.attribute2.copy();
        this.attribute3 = other.attribute3 == null ? null : other.attribute3.copy();
        this.attribute4 = other.attribute4 == null ? null : other.attribute4.copy();
        this.attribute5 = other.attribute5 == null ? null : other.attribute5.copy();
        this.attribute6 = other.attribute6 == null ? null : other.attribute6.copy();
        this.attribute7 = other.attribute7 == null ? null : other.attribute7.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.serviceOrderId = other.serviceOrderId == null ? null : other.serviceOrderId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
    }

    @Override
    public InvoiceCriteria copy() {
        return new InvoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimalFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public UUIDFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(UUIDFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public ZonedDateTimeFilter getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(ZonedDateTimeFilter generatedDate) {
        this.generatedDate = generatedDate;
    }

    public ZonedDateTimeFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTimeFilter dueDate) {
        this.dueDate = dueDate;
    }

    public ZonedDateTimeFilter getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(ZonedDateTimeFilter paymentDate) {
        this.paymentDate = paymentDate;
    }

    public InvoiceStatusFilter getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatusFilter invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public DoubleFilter getTax() {
        return tax;
    }

    public void setTax(DoubleFilter tax) {
        this.tax = tax;
    }

    public StringFilter getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(StringFilter attribute1) {
        this.attribute1 = attribute1;
    }

    public StringFilter getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(StringFilter attribute2) {
        this.attribute2 = attribute2;
    }

    public StringFilter getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(StringFilter attribute3) {
        this.attribute3 = attribute3;
    }

    public StringFilter getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(StringFilter attribute4) {
        this.attribute4 = attribute4;
    }

    public StringFilter getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(StringFilter attribute5) {
        this.attribute5 = attribute5;
    }

    public StringFilter getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(StringFilter attribute6) {
        this.attribute6 = attribute6;
    }

    public StringFilter getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(StringFilter attribute7) {
        this.attribute7 = attribute7;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(LongFilter serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public LongFilter getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(LongFilter serviceUserId) {
        this.serviceUserId = serviceUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvoiceCriteria that = (InvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(description, that.description) &&
            Objects.equals(invoiceNumber, that.invoiceNumber) &&
            Objects.equals(generatedDate, that.generatedDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(paymentDate, that.paymentDate) &&
            Objects.equals(invoiceStatus, that.invoiceStatus) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(attribute1, that.attribute1) &&
            Objects.equals(attribute2, that.attribute2) &&
            Objects.equals(attribute3, that.attribute3) &&
            Objects.equals(attribute4, that.attribute4) &&
            Objects.equals(attribute5, that.attribute5) &&
            Objects.equals(attribute6, that.attribute6) &&
            Objects.equals(attribute7, that.attribute7) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(serviceOrderId, that.serviceOrderId) &&
            Objects.equals(serviceUserId, that.serviceUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalAmount,
        description,
        invoiceNumber,
        generatedDate,
        dueDate,
        paymentDate,
        invoiceStatus,
        tax,
        attribute1,
        attribute2,
        attribute3,
        attribute4,
        attribute5,
        attribute6,
        attribute7,
        lastUpdatedDate,
        clientId,
        serviceOrderId,
        serviceUserId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber + ", " : "") +
                (generatedDate != null ? "generatedDate=" + generatedDate + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (paymentDate != null ? "paymentDate=" + paymentDate + ", " : "") +
                (invoiceStatus != null ? "invoiceStatus=" + invoiceStatus + ", " : "") +
                (tax != null ? "tax=" + tax + ", " : "") +
                (attribute1 != null ? "attribute1=" + attribute1 + ", " : "") +
                (attribute2 != null ? "attribute2=" + attribute2 + ", " : "") +
                (attribute3 != null ? "attribute3=" + attribute3 + ", " : "") +
                (attribute4 != null ? "attribute4=" + attribute4 + ", " : "") +
                (attribute5 != null ? "attribute5=" + attribute5 + ", " : "") +
                (attribute6 != null ? "attribute6=" + attribute6 + ", " : "") +
                (attribute7 != null ? "attribute7=" + attribute7 + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (serviceOrderId != null ? "serviceOrderId=" + serviceOrderId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
            "}";
    }

}
