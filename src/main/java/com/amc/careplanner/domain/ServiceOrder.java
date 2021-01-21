package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ServiceOrder.
 */
@Entity
@Table(name = "service_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "service_description")
    private String serviceDescription;

    @NotNull
    @Column(name = "service_hourly_rate", nullable = false)
    private String serviceHourlyRate;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @Column(name = "has_extra_data")
    private Boolean hasExtraData;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceOrders", allowSetters = true)
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ServiceOrder title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public ServiceOrder serviceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
        return this;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceHourlyRate() {
        return serviceHourlyRate;
    }

    public ServiceOrder serviceHourlyRate(String serviceHourlyRate) {
        this.serviceHourlyRate = serviceHourlyRate;
        return this;
    }

    public void setServiceHourlyRate(String serviceHourlyRate) {
        this.serviceHourlyRate = serviceHourlyRate;
    }

    public Long getClientId() {
        return clientId;
    }

    public ServiceOrder clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ServiceOrder createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public ServiceOrder lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public ServiceOrder hasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
        return this;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public Currency getCurrency() {
        return currency;
    }

    public ServiceOrder currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrder)) {
            return false;
        }
        return id != null && id.equals(((ServiceOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrder{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", serviceDescription='" + getServiceDescription() + "'" +
            ", serviceHourlyRate='" + getServiceHourlyRate() + "'" +
            ", clientId=" + getClientId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
