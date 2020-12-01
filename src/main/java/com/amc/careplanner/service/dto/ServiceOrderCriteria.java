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
 * Criteria class for the {@link com.amc.careplanner.domain.ServiceOrder} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.ServiceOrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceOrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter serviceDescription;

    private DoubleFilter serviceHourlyRate;

    private LongFilter clientId;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter currencyId;

    public ServiceOrderCriteria() {
    }

    public ServiceOrderCriteria(ServiceOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.serviceDescription = other.serviceDescription == null ? null : other.serviceDescription.copy();
        this.serviceHourlyRate = other.serviceHourlyRate == null ? null : other.serviceHourlyRate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
    }

    @Override
    public ServiceOrderCriteria copy() {
        return new ServiceOrderCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(StringFilter serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public DoubleFilter getServiceHourlyRate() {
        return serviceHourlyRate;
    }

    public void setServiceHourlyRate(DoubleFilter serviceHourlyRate) {
        this.serviceHourlyRate = serviceHourlyRate;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceOrderCriteria that = (ServiceOrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(serviceDescription, that.serviceDescription) &&
            Objects.equals(serviceHourlyRate, that.serviceHourlyRate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        serviceDescription,
        serviceHourlyRate,
        clientId,
        lastUpdatedDate,
        currencyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (serviceDescription != null ? "serviceDescription=" + serviceDescription + ", " : "") +
                (serviceHourlyRate != null ? "serviceHourlyRate=" + serviceHourlyRate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}
