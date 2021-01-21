package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.ServiceUserGender;

import com.amc.careplanner.domain.enumeration.MaritalStatus;

import com.amc.careplanner.domain.enumeration.Religion;

/**
 * A Equality.
 */
@Entity
@Table(name = "equality")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Equality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private ServiceUserGender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "religion", nullable = false)
    private Religion religion;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "has_extra_data")
    private Boolean hasExtraData;

    @ManyToOne
    @JsonIgnoreProperties(value = "equalities", allowSetters = true)
    private Country nationality;

    @ManyToOne
    @JsonIgnoreProperties(value = "equalities", allowSetters = true)
    private ServiceUser serviceUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceUserGender getGender() {
        return gender;
    }

    public Equality gender(ServiceUserGender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(ServiceUserGender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Equality maritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Religion getReligion() {
        return religion;
    }

    public Equality religion(Religion religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Equality createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Equality lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public Equality clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public Equality hasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
        return this;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public Country getNationality() {
        return nationality;
    }

    public Equality nationality(Country country) {
        this.nationality = country;
        return this;
    }

    public void setNationality(Country country) {
        this.nationality = country;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public Equality serviceUser(ServiceUser serviceUser) {
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
        if (!(o instanceof Equality)) {
            return false;
        }
        return id != null && id.equals(((Equality) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equality{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", religion='" + getReligion() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
