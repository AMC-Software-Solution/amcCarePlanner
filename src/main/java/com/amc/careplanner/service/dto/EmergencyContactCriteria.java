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
 * Criteria class for the {@link com.amc.careplanner.domain.EmergencyContact} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.EmergencyContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emergency-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmergencyContactCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter contactRelationship;

    private BooleanFilter isKeyHolder;

    private BooleanFilter infoSharingConsentGiven;

    private StringFilter preferredContactNumber;

    private StringFilter fullAddress;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter serviceUserId;

    public EmergencyContactCriteria() {
    }

    public EmergencyContactCriteria(EmergencyContactCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.contactRelationship = other.contactRelationship == null ? null : other.contactRelationship.copy();
        this.isKeyHolder = other.isKeyHolder == null ? null : other.isKeyHolder.copy();
        this.infoSharingConsentGiven = other.infoSharingConsentGiven == null ? null : other.infoSharingConsentGiven.copy();
        this.preferredContactNumber = other.preferredContactNumber == null ? null : other.preferredContactNumber.copy();
        this.fullAddress = other.fullAddress == null ? null : other.fullAddress.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
    }

    @Override
    public EmergencyContactCriteria copy() {
        return new EmergencyContactCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getContactRelationship() {
        return contactRelationship;
    }

    public void setContactRelationship(StringFilter contactRelationship) {
        this.contactRelationship = contactRelationship;
    }

    public BooleanFilter getIsKeyHolder() {
        return isKeyHolder;
    }

    public void setIsKeyHolder(BooleanFilter isKeyHolder) {
        this.isKeyHolder = isKeyHolder;
    }

    public BooleanFilter getInfoSharingConsentGiven() {
        return infoSharingConsentGiven;
    }

    public void setInfoSharingConsentGiven(BooleanFilter infoSharingConsentGiven) {
        this.infoSharingConsentGiven = infoSharingConsentGiven;
    }

    public StringFilter getPreferredContactNumber() {
        return preferredContactNumber;
    }

    public void setPreferredContactNumber(StringFilter preferredContactNumber) {
        this.preferredContactNumber = preferredContactNumber;
    }

    public StringFilter getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(StringFilter fullAddress) {
        this.fullAddress = fullAddress;
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

    public LongFilter getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(LongFilter serviceUserId) {
        this.serviceUserId = serviceUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmergencyContactCriteria that = (EmergencyContactCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(contactRelationship, that.contactRelationship) &&
            Objects.equals(isKeyHolder, that.isKeyHolder) &&
            Objects.equals(infoSharingConsentGiven, that.infoSharingConsentGiven) &&
            Objects.equals(preferredContactNumber, that.preferredContactNumber) &&
            Objects.equals(fullAddress, that.fullAddress) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(serviceUserId, that.serviceUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        contactRelationship,
        isKeyHolder,
        infoSharingConsentGiven,
        preferredContactNumber,
        fullAddress,
        lastUpdatedDate,
        clientId,
        serviceUserId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmergencyContactCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (contactRelationship != null ? "contactRelationship=" + contactRelationship + ", " : "") +
                (isKeyHolder != null ? "isKeyHolder=" + isKeyHolder + ", " : "") +
                (infoSharingConsentGiven != null ? "infoSharingConsentGiven=" + infoSharingConsentGiven + ", " : "") +
                (preferredContactNumber != null ? "preferredContactNumber=" + preferredContactNumber + ", " : "") +
                (fullAddress != null ? "fullAddress=" + fullAddress + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
            "}";
    }

}
