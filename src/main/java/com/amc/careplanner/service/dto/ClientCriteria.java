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
 * Criteria class for the {@link com.amc.careplanner.domain.Client} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.ClientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter clientName;

    private StringFilter clientDescription;

    private StringFilter clientLogoUrl;

    private StringFilter clientContactName;

    private StringFilter clientPhone;

    private StringFilter clientEmail;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    public ClientCriteria() {
    }

    public ClientCriteria(ClientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.clientName = other.clientName == null ? null : other.clientName.copy();
        this.clientDescription = other.clientDescription == null ? null : other.clientDescription.copy();
        this.clientLogoUrl = other.clientLogoUrl == null ? null : other.clientLogoUrl.copy();
        this.clientContactName = other.clientContactName == null ? null : other.clientContactName.copy();
        this.clientPhone = other.clientPhone == null ? null : other.clientPhone.copy();
        this.clientEmail = other.clientEmail == null ? null : other.clientEmail.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
    }

    @Override
    public ClientCriteria copy() {
        return new ClientCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClientName() {
        return clientName;
    }

    public void setClientName(StringFilter clientName) {
        this.clientName = clientName;
    }

    public StringFilter getClientDescription() {
        return clientDescription;
    }

    public void setClientDescription(StringFilter clientDescription) {
        this.clientDescription = clientDescription;
    }

    public StringFilter getClientLogoUrl() {
        return clientLogoUrl;
    }

    public void setClientLogoUrl(StringFilter clientLogoUrl) {
        this.clientLogoUrl = clientLogoUrl;
    }

    public StringFilter getClientContactName() {
        return clientContactName;
    }

    public void setClientContactName(StringFilter clientContactName) {
        this.clientContactName = clientContactName;
    }

    public StringFilter getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(StringFilter clientPhone) {
        this.clientPhone = clientPhone;
    }

    public StringFilter getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(StringFilter clientEmail) {
        this.clientEmail = clientEmail;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClientCriteria that = (ClientCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(clientName, that.clientName) &&
            Objects.equals(clientDescription, that.clientDescription) &&
            Objects.equals(clientLogoUrl, that.clientLogoUrl) &&
            Objects.equals(clientContactName, that.clientContactName) &&
            Objects.equals(clientPhone, that.clientPhone) &&
            Objects.equals(clientEmail, that.clientEmail) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        clientName,
        clientDescription,
        clientLogoUrl,
        clientContactName,
        clientPhone,
        clientEmail,
        createdDate,
        lastUpdatedDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (clientName != null ? "clientName=" + clientName + ", " : "") +
                (clientDescription != null ? "clientDescription=" + clientDescription + ", " : "") +
                (clientLogoUrl != null ? "clientLogoUrl=" + clientLogoUrl + ", " : "") +
                (clientContactName != null ? "clientContactName=" + clientContactName + ", " : "") +
                (clientPhone != null ? "clientPhone=" + clientPhone + ", " : "") +
                (clientEmail != null ? "clientEmail=" + clientEmail + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
            "}";
    }

}
