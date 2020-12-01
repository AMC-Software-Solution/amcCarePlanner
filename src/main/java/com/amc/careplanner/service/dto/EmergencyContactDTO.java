package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.EmergencyContact} entity.
 */
public class EmergencyContactDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String contactRelationship;

    private Boolean isKeyHolder;

    private Boolean infoSharingConsentGiven;

    @NotNull
    private String preferredContactNumber;

    private String fullAddress;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long serviceUserId;

    private String serviceUserServiceUserCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactRelationship() {
        return contactRelationship;
    }

    public void setContactRelationship(String contactRelationship) {
        this.contactRelationship = contactRelationship;
    }

    public Boolean isIsKeyHolder() {
        return isKeyHolder;
    }

    public void setIsKeyHolder(Boolean isKeyHolder) {
        this.isKeyHolder = isKeyHolder;
    }

    public Boolean isInfoSharingConsentGiven() {
        return infoSharingConsentGiven;
    }

    public void setInfoSharingConsentGiven(Boolean infoSharingConsentGiven) {
        this.infoSharingConsentGiven = infoSharingConsentGiven;
    }

    public String getPreferredContactNumber() {
        return preferredContactNumber;
    }

    public void setPreferredContactNumber(String preferredContactNumber) {
        this.preferredContactNumber = preferredContactNumber;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmergencyContactDTO)) {
            return false;
        }

        return id != null && id.equals(((EmergencyContactDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmergencyContactDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contactRelationship='" + getContactRelationship() + "'" +
            ", isKeyHolder='" + isIsKeyHolder() + "'" +
            ", infoSharingConsentGiven='" + isInfoSharingConsentGiven() + "'" +
            ", preferredContactNumber='" + getPreferredContactNumber() + "'" +
            ", fullAddress='" + getFullAddress() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            "}";
    }
}
