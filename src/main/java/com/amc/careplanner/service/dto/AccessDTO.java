package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Access} entity.
 */
public class AccessDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String keySafeNumber;

    private String accessDetails;

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

    public String getKeySafeNumber() {
        return keySafeNumber;
    }

    public void setKeySafeNumber(String keySafeNumber) {
        this.keySafeNumber = keySafeNumber;
    }

    public String getAccessDetails() {
        return accessDetails;
    }

    public void setAccessDetails(String accessDetails) {
        this.accessDetails = accessDetails;
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
        if (!(o instanceof AccessDTO)) {
            return false;
        }

        return id != null && id.equals(((AccessDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccessDTO{" +
            "id=" + getId() +
            ", keySafeNumber='" + getKeySafeNumber() + "'" +
            ", accessDetails='" + getAccessDetails() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            "}";
    }
}
