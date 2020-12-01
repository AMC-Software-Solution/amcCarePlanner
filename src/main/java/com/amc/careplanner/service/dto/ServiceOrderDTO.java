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
    private Double serviceHourlyRate;

    @NotNull
    private Long clientId;

    private ZonedDateTime lastUpdatedDate;


    private Long currencyId;

    private String currencyCurrency;
    
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

    public Double getServiceHourlyRate() {
        return serviceHourlyRate;
    }

    public void setServiceHourlyRate(Double serviceHourlyRate) {
        this.serviceHourlyRate = serviceHourlyRate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCurrency() {
        return currencyCurrency;
    }

    public void setCurrencyCurrency(String currencyCurrency) {
        this.currencyCurrency = currencyCurrency;
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
            ", serviceHourlyRate=" + getServiceHourlyRate() +
            ", clientId=" + getClientId() +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", currencyId=" + getCurrencyId() +
            ", currencyCurrency='" + getCurrencyCurrency() + "'" +
            "}";
    }
}
