package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Client} entity.
 */
public class ClientDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String clientName;

    private String clientDescription;

    @Lob
    private byte[] clientLogo;

    private String clientLogoContentType;
    private String clientLogoUrl;

    private String clientContactName;

    private String clientPhone;

    private String clientEmail;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastUpdatedDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientDescription() {
        return clientDescription;
    }

    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
    }

    public byte[] getClientLogo() {
        return clientLogo;
    }

    public void setClientLogo(byte[] clientLogo) {
        this.clientLogo = clientLogo;
    }

    public String getClientLogoContentType() {
        return clientLogoContentType;
    }

    public void setClientLogoContentType(String clientLogoContentType) {
        this.clientLogoContentType = clientLogoContentType;
    }

    public String getClientLogoUrl() {
        return clientLogoUrl;
    }

    public void setClientLogoUrl(String clientLogoUrl) {
        this.clientLogoUrl = clientLogoUrl;
    }

    public String getClientContactName() {
        return clientContactName;
    }

    public void setClientContactName(String clientContactName) {
        this.clientContactName = clientContactName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        return id != null && id.equals(((ClientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", clientDescription='" + getClientDescription() + "'" +
            ", clientLogo='" + getClientLogo() + "'" +
            ", clientLogoUrl='" + getClientLogoUrl() + "'" +
            ", clientContactName='" + getClientContactName() + "'" +
            ", clientPhone='" + getClientPhone() + "'" +
            ", clientEmail='" + getClientEmail() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
