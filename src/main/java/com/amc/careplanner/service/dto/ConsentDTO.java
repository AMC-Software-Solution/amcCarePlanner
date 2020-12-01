package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Consent} entity.
 */
public class ConsentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String title;

    private String description;

    private Boolean giveConsent;

    private String arrangements;

    private String serviceUserSignature;

    @Lob
    private byte[] signatureImage;

    private String signatureImageContentType;
    private String signatureImageUrl;

    private ZonedDateTime consentDate;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long serviceUserId;

    private String serviceUserServiceUserCode;

    private Long witnessedById;

    private String witnessedByEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isGiveConsent() {
        return giveConsent;
    }

    public void setGiveConsent(Boolean giveConsent) {
        this.giveConsent = giveConsent;
    }

    public String getArrangements() {
        return arrangements;
    }

    public void setArrangements(String arrangements) {
        this.arrangements = arrangements;
    }

    public String getServiceUserSignature() {
        return serviceUserSignature;
    }

    public void setServiceUserSignature(String serviceUserSignature) {
        this.serviceUserSignature = serviceUserSignature;
    }

    public byte[] getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(byte[] signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getSignatureImageContentType() {
        return signatureImageContentType;
    }

    public void setSignatureImageContentType(String signatureImageContentType) {
        this.signatureImageContentType = signatureImageContentType;
    }

    public String getSignatureImageUrl() {
        return signatureImageUrl;
    }

    public void setSignatureImageUrl(String signatureImageUrl) {
        this.signatureImageUrl = signatureImageUrl;
    }

    public ZonedDateTime getConsentDate() {
        return consentDate;
    }

    public void setConsentDate(ZonedDateTime consentDate) {
        this.consentDate = consentDate;
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

    public Long getWitnessedById() {
        return witnessedById;
    }

    public void setWitnessedById(Long employeeId) {
        this.witnessedById = employeeId;
    }

    public String getWitnessedByEmployeeCode() {
        return witnessedByEmployeeCode;
    }

    public void setWitnessedByEmployeeCode(String employeeEmployeeCode) {
        this.witnessedByEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsentDTO)) {
            return false;
        }

        return id != null && id.equals(((ConsentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsentDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", giveConsent='" + isGiveConsent() + "'" +
            ", arrangements='" + getArrangements() + "'" +
            ", serviceUserSignature='" + getServiceUserSignature() + "'" +
            ", signatureImage='" + getSignatureImage() + "'" +
            ", signatureImageUrl='" + getSignatureImageUrl() + "'" +
            ", consentDate='" + getConsentDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            ", witnessedById=" + getWitnessedById() +
            ", witnessedByEmployeeCode='" + getWitnessedByEmployeeCode() + "'" +
            "}";
    }
}
