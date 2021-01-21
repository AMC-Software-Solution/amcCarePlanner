package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import com.amc.careplanner.domain.enumeration.InvoiceStatus;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Invoice} entity.
 */
public class InvoiceDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String totalAmount;

    private String description;

    @NotNull
    private UUID invoiceNumber;

    @NotNull
    private ZonedDateTime generatedDate;

    @NotNull
    private ZonedDateTime dueDate;

    private ZonedDateTime paymentDate;

    @NotNull
    private InvoiceStatus invoiceStatus;

    private String tax;

    private String attribute1;

    private String attribute2;

    private String attribute3;

    private String attribute4;

    private String attribute5;

    private String attribute6;

    private String attribute7;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;

    private Boolean hasExtraData;


    private Long serviceOrderId;

    private String serviceOrderTitle;

    private Long serviceUserId;

    private String serviceUserServiceUserCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(UUID invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public ZonedDateTime getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(ZonedDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public Long getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(Long serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public String getServiceOrderTitle() {
        return serviceOrderTitle;
    }

    public void setServiceOrderTitle(String serviceOrderTitle) {
        this.serviceOrderTitle = serviceOrderTitle;
    }

    public Long getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(Long serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public String getServiceUserServiceUserCode() {
        return serviceUserServiceUserCode;
    }

    public void setServiceUserServiceUserCode(String serviceUserServiceUserCode) {
        this.serviceUserServiceUserCode = serviceUserServiceUserCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceDTO)) {
            return false;
        }

        return id != null && id.equals(((InvoiceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceDTO{" +
            "id=" + getId() +
            ", totalAmount='" + getTotalAmount() + "'" +
            ", description='" + getDescription() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", generatedDate='" + getGeneratedDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", invoiceStatus='" + getInvoiceStatus() + "'" +
            ", tax='" + getTax() + "'" +
            ", attribute1='" + getAttribute1() + "'" +
            ", attribute2='" + getAttribute2() + "'" +
            ", attribute3='" + getAttribute3() + "'" +
            ", attribute4='" + getAttribute4() + "'" +
            ", attribute5='" + getAttribute5() + "'" +
            ", attribute6='" + getAttribute6() + "'" +
            ", attribute7='" + getAttribute7() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            ", serviceOrderId=" + getServiceOrderId() +
            ", serviceOrderTitle='" + getServiceOrderTitle() + "'" +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            "}";
    }
}
