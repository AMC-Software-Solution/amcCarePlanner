package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.ServiceOrder} entity.
 */
public class ServiceOrderDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String title;

    private String serviceDescription;

    @NotNull
    private Double serviceRate;

    @NotNull
    private Long tenantId;

    private ZonedDateTime lastUpdatedDate;

    
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

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
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
        if (!(o instanceof ServiceOrderDTO)) {
            return false;
        }

        return id != null && id.equals(((ServiceOrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", serviceDescription='" + getServiceDescription() + "'" +
            ", serviceRate=" + getServiceRate() +
            ", tenantId=" + getTenantId() +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
