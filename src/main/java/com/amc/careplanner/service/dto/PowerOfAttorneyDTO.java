package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.PowerOfAttorney} entity.
 */
public class PowerOfAttorneyDTO implements Serializable {
    
    private Long id;

    private Boolean powerOfAttorneyConsent;

    private Boolean healthAndWelfare;

    private String healthAndWelfareName;

    private Boolean propertyAndFinAffairs;

    private String propertyAndFinAffairsName;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;

    private Boolean hasExtraData;


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

    public Boolean isPowerOfAttorneyConsent() {
        return powerOfAttorneyConsent;
    }

    public void setPowerOfAttorneyConsent(Boolean powerOfAttorneyConsent) {
        this.powerOfAttorneyConsent = powerOfAttorneyConsent;
    }

    public Boolean isHealthAndWelfare() {
        return healthAndWelfare;
    }

    public void setHealthAndWelfare(Boolean healthAndWelfare) {
        this.healthAndWelfare = healthAndWelfare;
    }

    public String getHealthAndWelfareName() {
        return healthAndWelfareName;
    }

    public void setHealthAndWelfareName(String healthAndWelfareName) {
        this.healthAndWelfareName = healthAndWelfareName;
    }

    public Boolean isPropertyAndFinAffairs() {
        return propertyAndFinAffairs;
    }

    public void setPropertyAndFinAffairs(Boolean propertyAndFinAffairs) {
        this.propertyAndFinAffairs = propertyAndFinAffairs;
    }

    public String getPropertyAndFinAffairsName() {
        return propertyAndFinAffairsName;
    }

    public void setPropertyAndFinAffairsName(String propertyAndFinAffairsName) {
        this.propertyAndFinAffairsName = propertyAndFinAffairsName;
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
        if (!(o instanceof PowerOfAttorneyDTO)) {
            return false;
        }

        return id != null && id.equals(((PowerOfAttorneyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerOfAttorneyDTO{" +
            "id=" + getId() +
            ", powerOfAttorneyConsent='" + isPowerOfAttorneyConsent() + "'" +
            ", healthAndWelfare='" + isHealthAndWelfare() + "'" +
            ", healthAndWelfareName='" + getHealthAndWelfareName() + "'" +
            ", propertyAndFinAffairs='" + isPropertyAndFinAffairs() + "'" +
            ", propertyAndFinAffairsName='" + getPropertyAndFinAffairsName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            ", witnessedById=" + getWitnessedById() +
            ", witnessedByEmployeeCode='" + getWitnessedByEmployeeCode() + "'" +
            "}";
    }
}
