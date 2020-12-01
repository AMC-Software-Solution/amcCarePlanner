package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.amc.careplanner.domain.enumeration.InvoiceStatus;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "description")
    private String description;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "invoice_number", length = 36, nullable = false, unique = true)
    private UUID invoiceNumber;

    @NotNull
    @Column(name = "generated_date", nullable = false)
    private ZonedDateTime generatedDate;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private ZonedDateTime dueDate;

    @Column(name = "payment_date")
    private ZonedDateTime paymentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_status", nullable = false)
    private InvoiceStatus invoiceStatus;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "attribute_1")
    private String attribute1;

    @Column(name = "attribute_2")
    private String attribute2;

    @Column(name = "attribute_3")
    private String attribute3;

    @Column(name = "attribute_4")
    private String attribute4;

    @Column(name = "attribute_5")
    private String attribute5;

    @Column(name = "attribute_6")
    private String attribute6;

    @Column(name = "attribute_7")
    private String attribute7;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private ServiceUser serviceUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Invoice totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public Invoice description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getInvoiceNumber() {
        return invoiceNumber;
    }

    public Invoice invoiceNumber(UUID invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(UUID invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public ZonedDateTime getGeneratedDate() {
        return generatedDate;
    }

    public Invoice generatedDate(ZonedDateTime generatedDate) {
        this.generatedDate = generatedDate;
        return this;
    }

    public void setGeneratedDate(ZonedDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public Invoice dueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public Invoice paymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public Invoice invoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
        return this;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Double getTax() {
        return tax;
    }

    public Invoice tax(Double tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public Invoice attribute1(String attribute1) {
        this.attribute1 = attribute1;
        return this;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public Invoice attribute2(String attribute2) {
        this.attribute2 = attribute2;
        return this;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public Invoice attribute3(String attribute3) {
        this.attribute3 = attribute3;
        return this;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public Invoice attribute4(String attribute4) {
        this.attribute4 = attribute4;
        return this;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public Invoice attribute5(String attribute5) {
        this.attribute5 = attribute5;
        return this;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getAttribute6() {
        return attribute6;
    }

    public Invoice attribute6(String attribute6) {
        this.attribute6 = attribute6;
        return this;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    public String getAttribute7() {
        return attribute7;
    }

    public Invoice attribute7(String attribute7) {
        this.attribute7 = attribute7;
        return this;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Invoice lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public Invoice clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public Invoice serviceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
        return this;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public Invoice serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", totalAmount=" + getTotalAmount() +
            ", description='" + getDescription() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", generatedDate='" + getGeneratedDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", invoiceStatus='" + getInvoiceStatus() + "'" +
            ", tax=" + getTax() +
            ", attribute1='" + getAttribute1() + "'" +
            ", attribute2='" + getAttribute2() + "'" +
            ", attribute3='" + getAttribute3() + "'" +
            ", attribute4='" + getAttribute4() + "'" +
            ", attribute5='" + getAttribute5() + "'" +
            ", attribute6='" + getAttribute6() + "'" +
            ", attribute7='" + getAttribute7() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            "}";
    }
}
