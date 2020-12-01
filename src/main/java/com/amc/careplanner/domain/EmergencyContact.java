package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A EmergencyContact.
 */
@Entity
@Table(name = "emergency_contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmergencyContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "contact_relationship", nullable = false)
    private String contactRelationship;

    @Column(name = "is_key_holder")
    private Boolean isKeyHolder;

    @Column(name = "info_sharing_consent_given")
    private Boolean infoSharingConsentGiven;

    @NotNull
    @Column(name = "preferred_contact_number", nullable = false)
    private String preferredContactNumber;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @ManyToOne
    @JsonIgnoreProperties(value = "emergencyContacts", allowSetters = true)
    private ServiceUser serviceUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EmergencyContact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactRelationship() {
        return contactRelationship;
    }

    public EmergencyContact contactRelationship(String contactRelationship) {
        this.contactRelationship = contactRelationship;
        return this;
    }

    public void setContactRelationship(String contactRelationship) {
        this.contactRelationship = contactRelationship;
    }

    public Boolean isIsKeyHolder() {
        return isKeyHolder;
    }

    public EmergencyContact isKeyHolder(Boolean isKeyHolder) {
        this.isKeyHolder = isKeyHolder;
        return this;
    }

    public void setIsKeyHolder(Boolean isKeyHolder) {
        this.isKeyHolder = isKeyHolder;
    }

    public Boolean isInfoSharingConsentGiven() {
        return infoSharingConsentGiven;
    }

    public EmergencyContact infoSharingConsentGiven(Boolean infoSharingConsentGiven) {
        this.infoSharingConsentGiven = infoSharingConsentGiven;
        return this;
    }

    public void setInfoSharingConsentGiven(Boolean infoSharingConsentGiven) {
        this.infoSharingConsentGiven = infoSharingConsentGiven;
    }

    public String getPreferredContactNumber() {
        return preferredContactNumber;
    }

    public EmergencyContact preferredContactNumber(String preferredContactNumber) {
        this.preferredContactNumber = preferredContactNumber;
        return this;
    }

    public void setPreferredContactNumber(String preferredContactNumber) {
        this.preferredContactNumber = preferredContactNumber;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public EmergencyContact fullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
        return this;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public EmergencyContact lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public EmergencyContact clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public EmergencyContact serviceUser(ServiceUser serviceUser) {
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
        if (!(o instanceof EmergencyContact)) {
            return false;
        }
        return id != null && id.equals(((EmergencyContact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmergencyContact{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contactRelationship='" + getContactRelationship() + "'" +
            ", isKeyHolder='" + isIsKeyHolder() + "'" +
            ", infoSharingConsentGiven='" + isInfoSharingConsentGiven() + "'" +
            ", preferredContactNumber='" + getPreferredContactNumber() + "'" +
            ", fullAddress='" + getFullAddress() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            "}";
    }
}
