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
 * Criteria class for the {@link com.amc.careplanner.domain.Access} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.AccessResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /accesses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AccessCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter keySafeNumber;

    private StringFilter accessDetails;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter serviceUserId;

    public AccessCriteria() {
    }

    public AccessCriteria(AccessCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.keySafeNumber = other.keySafeNumber == null ? null : other.keySafeNumber.copy();
        this.accessDetails = other.accessDetails == null ? null : other.accessDetails.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
    }

    @Override
    public AccessCriteria copy() {
        return new AccessCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getKeySafeNumber() {
        return keySafeNumber;
    }

    public void setKeySafeNumber(StringFilter keySafeNumber) {
        this.keySafeNumber = keySafeNumber;
    }

    public StringFilter getAccessDetails() {
        return accessDetails;
    }

    public void setAccessDetails(StringFilter accessDetails) {
        this.accessDetails = accessDetails;
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
        final AccessCriteria that = (AccessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(keySafeNumber, that.keySafeNumber) &&
            Objects.equals(accessDetails, that.accessDetails) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(serviceUserId, that.serviceUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        keySafeNumber,
        accessDetails,
        lastUpdatedDate,
        clientId,
        serviceUserId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (keySafeNumber != null ? "keySafeNumber=" + keySafeNumber + ", " : "") +
                (accessDetails != null ? "accessDetails=" + accessDetails + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
            "}";
    }

}
