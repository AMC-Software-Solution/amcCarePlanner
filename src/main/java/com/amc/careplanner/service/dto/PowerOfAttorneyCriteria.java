package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.PowerOfAttorney} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.PowerOfAttorneyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /power-of-attorneys?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PowerOfAttorneyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter powerOfAttorneyConsent;

    private BooleanFilter healthAndWelfare;

    private StringFilter healthAndWelfareName;

    private BooleanFilter propertyAndFinAffairs;

    private StringFilter propertyAndFinAffairsName;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    private LongFilter serviceUserId;

    private LongFilter witnessedById;

    public PowerOfAttorneyCriteria() {
    }

    public PowerOfAttorneyCriteria(PowerOfAttorneyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.powerOfAttorneyConsent = other.powerOfAttorneyConsent == null ? null : other.powerOfAttorneyConsent.copy();
        this.healthAndWelfare = other.healthAndWelfare == null ? null : other.healthAndWelfare.copy();
        this.healthAndWelfareName = other.healthAndWelfareName == null ? null : other.healthAndWelfareName.copy();
        this.propertyAndFinAffairs = other.propertyAndFinAffairs == null ? null : other.propertyAndFinAffairs.copy();
        this.propertyAndFinAffairsName = other.propertyAndFinAffairsName == null ? null : other.propertyAndFinAffairsName.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
        this.witnessedById = other.witnessedById == null ? null : other.witnessedById.copy();
    }

    @Override
    public PowerOfAttorneyCriteria copy() {
        return new PowerOfAttorneyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getPowerOfAttorneyConsent() {
        return powerOfAttorneyConsent;
    }

    public void setPowerOfAttorneyConsent(BooleanFilter powerOfAttorneyConsent) {
        this.powerOfAttorneyConsent = powerOfAttorneyConsent;
    }

    public BooleanFilter getHealthAndWelfare() {
        return healthAndWelfare;
    }

    public void setHealthAndWelfare(BooleanFilter healthAndWelfare) {
        this.healthAndWelfare = healthAndWelfare;
    }

    public StringFilter getHealthAndWelfareName() {
        return healthAndWelfareName;
    }

    public void setHealthAndWelfareName(StringFilter healthAndWelfareName) {
        this.healthAndWelfareName = healthAndWelfareName;
    }

    public BooleanFilter getPropertyAndFinAffairs() {
        return propertyAndFinAffairs;
    }

    public void setPropertyAndFinAffairs(BooleanFilter propertyAndFinAffairs) {
        this.propertyAndFinAffairs = propertyAndFinAffairs;
    }

    public StringFilter getPropertyAndFinAffairsName() {
        return propertyAndFinAffairsName;
    }

    public void setPropertyAndFinAffairsName(StringFilter propertyAndFinAffairsName) {
        this.propertyAndFinAffairsName = propertyAndFinAffairsName;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public BooleanFilter getHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(BooleanFilter hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public LongFilter getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(LongFilter serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public LongFilter getWitnessedById() {
        return witnessedById;
    }

    public void setWitnessedById(LongFilter witnessedById) {
        this.witnessedById = witnessedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PowerOfAttorneyCriteria that = (PowerOfAttorneyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(powerOfAttorneyConsent, that.powerOfAttorneyConsent) &&
            Objects.equals(healthAndWelfare, that.healthAndWelfare) &&
            Objects.equals(healthAndWelfareName, that.healthAndWelfareName) &&
            Objects.equals(propertyAndFinAffairs, that.propertyAndFinAffairs) &&
            Objects.equals(propertyAndFinAffairsName, that.propertyAndFinAffairsName) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData) &&
            Objects.equals(serviceUserId, that.serviceUserId) &&
            Objects.equals(witnessedById, that.witnessedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        powerOfAttorneyConsent,
        healthAndWelfare,
        healthAndWelfareName,
        propertyAndFinAffairs,
        propertyAndFinAffairsName,
        createdDate,
        lastUpdatedDate,
        clientId,
        hasExtraData,
        serviceUserId,
        witnessedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerOfAttorneyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (powerOfAttorneyConsent != null ? "powerOfAttorneyConsent=" + powerOfAttorneyConsent + ", " : "") +
                (healthAndWelfare != null ? "healthAndWelfare=" + healthAndWelfare + ", " : "") +
                (healthAndWelfareName != null ? "healthAndWelfareName=" + healthAndWelfareName + ", " : "") +
                (propertyAndFinAffairs != null ? "propertyAndFinAffairs=" + propertyAndFinAffairs + ", " : "") +
                (propertyAndFinAffairsName != null ? "propertyAndFinAffairsName=" + propertyAndFinAffairsName + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
                (witnessedById != null ? "witnessedById=" + witnessedById + ", " : "") +
            "}";
    }

}
