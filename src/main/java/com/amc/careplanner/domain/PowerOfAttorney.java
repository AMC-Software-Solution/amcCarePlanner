package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PowerOfAttorney.
 */
@Entity
@Table(name = "power_of_attorney")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PowerOfAttorney implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "power_of_attorney_consent")
    private Boolean powerOfAttorneyConsent;

    @Column(name = "health_and_welfare")
    private Boolean healthAndWelfare;

    @Column(name = "health_and_welfare_name")
    private String healthAndWelfareName;

    @Column(name = "property_and_fin_affairs")
    private Boolean propertyAndFinAffairs;

    @Column(name = "property_and_fin_affairs_name")
    private String propertyAndFinAffairsName;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @OneToOne
    @JoinColumn(unique = true)
    private ServiceUser serviceUser;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee witnessedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPowerOfAttorneyConsent() {
        return powerOfAttorneyConsent;
    }

    public PowerOfAttorney powerOfAttorneyConsent(Boolean powerOfAttorneyConsent) {
        this.powerOfAttorneyConsent = powerOfAttorneyConsent;
        return this;
    }

    public void setPowerOfAttorneyConsent(Boolean powerOfAttorneyConsent) {
        this.powerOfAttorneyConsent = powerOfAttorneyConsent;
    }

    public Boolean isHealthAndWelfare() {
        return healthAndWelfare;
    }

    public PowerOfAttorney healthAndWelfare(Boolean healthAndWelfare) {
        this.healthAndWelfare = healthAndWelfare;
        return this;
    }

    public void setHealthAndWelfare(Boolean healthAndWelfare) {
        this.healthAndWelfare = healthAndWelfare;
    }

    public String getHealthAndWelfareName() {
        return healthAndWelfareName;
    }

    public PowerOfAttorney healthAndWelfareName(String healthAndWelfareName) {
        this.healthAndWelfareName = healthAndWelfareName;
        return this;
    }

    public void setHealthAndWelfareName(String healthAndWelfareName) {
        this.healthAndWelfareName = healthAndWelfareName;
    }

    public Boolean isPropertyAndFinAffairs() {
        return propertyAndFinAffairs;
    }

    public PowerOfAttorney propertyAndFinAffairs(Boolean propertyAndFinAffairs) {
        this.propertyAndFinAffairs = propertyAndFinAffairs;
        return this;
    }

    public void setPropertyAndFinAffairs(Boolean propertyAndFinAffairs) {
        this.propertyAndFinAffairs = propertyAndFinAffairs;
    }

    public String getPropertyAndFinAffairsName() {
        return propertyAndFinAffairsName;
    }

    public PowerOfAttorney propertyAndFinAffairsName(String propertyAndFinAffairsName) {
        this.propertyAndFinAffairsName = propertyAndFinAffairsName;
        return this;
    }

    public void setPropertyAndFinAffairsName(String propertyAndFinAffairsName) {
        this.propertyAndFinAffairsName = propertyAndFinAffairsName;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public PowerOfAttorney lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public PowerOfAttorney tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public PowerOfAttorney serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public Employee getWitnessedBy() {
        return witnessedBy;
    }

    public PowerOfAttorney witnessedBy(Employee employee) {
        this.witnessedBy = employee;
        return this;
    }

    public void setWitnessedBy(Employee employee) {
        this.witnessedBy = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PowerOfAttorney)) {
            return false;
        }
        return id != null && id.equals(((PowerOfAttorney) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerOfAttorney{" +
            "id=" + getId() +
            ", powerOfAttorneyConsent='" + isPowerOfAttorneyConsent() + "'" +
            ", healthAndWelfare='" + isHealthAndWelfare() + "'" +
            ", healthAndWelfareName='" + getHealthAndWelfareName() + "'" +
            ", propertyAndFinAffairs='" + isPropertyAndFinAffairs() + "'" +
            ", propertyAndFinAffairsName='" + getPropertyAndFinAffairsName() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
