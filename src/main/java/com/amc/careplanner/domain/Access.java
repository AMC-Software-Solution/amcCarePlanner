package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Access.
 */
@Entity
@Table(name = "access")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Access implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "key_safe_number", nullable = false)
    private String keySafeNumber;

    @Column(name = "access_details")
    private String accessDetails;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @ManyToOne
    @JsonIgnoreProperties(value = "accesses", allowSetters = true)
    private ServiceUser serviceUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeySafeNumber() {
        return keySafeNumber;
    }

    public Access keySafeNumber(String keySafeNumber) {
        this.keySafeNumber = keySafeNumber;
        return this;
    }

    public void setKeySafeNumber(String keySafeNumber) {
        this.keySafeNumber = keySafeNumber;
    }

    public String getAccessDetails() {
        return accessDetails;
    }

    public Access accessDetails(String accessDetails) {
        this.accessDetails = accessDetails;
        return this;
    }

    public void setAccessDetails(String accessDetails) {
        this.accessDetails = accessDetails;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Access lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public Access clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public Access serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Access)) {
            return false;
        }
        return id != null && id.equals(((Access) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Access{" +
            "id=" + getId() +
            ", keySafeNumber='" + getKeySafeNumber() + "'" +
            ", accessDetails='" + getAccessDetails() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            "}";
    }
}
