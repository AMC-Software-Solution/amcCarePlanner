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
 * Criteria class for the {@link com.amc.careplanner.domain.Tenant} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.TenantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tenants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TenantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tenantName;

    private StringFilter tenantDescription;

    private StringFilter tenantLogoUrl;

    private StringFilter tenantContactName;

    private StringFilter tenantPhone;

    private StringFilter tenantEmail;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    public TenantCriteria() {
    }

    public TenantCriteria(TenantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenantName = other.tenantName == null ? null : other.tenantName.copy();
        this.tenantDescription = other.tenantDescription == null ? null : other.tenantDescription.copy();
        this.tenantLogoUrl = other.tenantLogoUrl == null ? null : other.tenantLogoUrl.copy();
        this.tenantContactName = other.tenantContactName == null ? null : other.tenantContactName.copy();
        this.tenantPhone = other.tenantPhone == null ? null : other.tenantPhone.copy();
        this.tenantEmail = other.tenantEmail == null ? null : other.tenantEmail.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
    }

    @Override
    public TenantCriteria copy() {
        return new TenantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTenantName() {
        return tenantName;
    }

    public void setTenantName(StringFilter tenantName) {
        this.tenantName = tenantName;
    }

    public StringFilter getTenantDescription() {
        return tenantDescription;
    }

    public void setTenantDescription(StringFilter tenantDescription) {
        this.tenantDescription = tenantDescription;
    }

    public StringFilter getTenantLogoUrl() {
        return tenantLogoUrl;
    }

    public void setTenantLogoUrl(StringFilter tenantLogoUrl) {
        this.tenantLogoUrl = tenantLogoUrl;
    }

    public StringFilter getTenantContactName() {
        return tenantContactName;
    }

    public void setTenantContactName(StringFilter tenantContactName) {
        this.tenantContactName = tenantContactName;
    }

    public StringFilter getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(StringFilter tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public StringFilter getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(StringFilter tenantEmail) {
        this.tenantEmail = tenantEmail;
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
        final TenantCriteria that = (TenantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tenantName, that.tenantName) &&
            Objects.equals(tenantDescription, that.tenantDescription) &&
            Objects.equals(tenantLogoUrl, that.tenantLogoUrl) &&
            Objects.equals(tenantContactName, that.tenantContactName) &&
            Objects.equals(tenantPhone, that.tenantPhone) &&
            Objects.equals(tenantEmail, that.tenantEmail) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tenantName,
        tenantDescription,
        tenantLogoUrl,
        tenantContactName,
        tenantPhone,
        tenantEmail,
        createdDate,
        lastUpdatedDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TenantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tenantName != null ? "tenantName=" + tenantName + ", " : "") +
                (tenantDescription != null ? "tenantDescription=" + tenantDescription + ", " : "") +
                (tenantLogoUrl != null ? "tenantLogoUrl=" + tenantLogoUrl + ", " : "") +
                (tenantContactName != null ? "tenantContactName=" + tenantContactName + ", " : "") +
                (tenantPhone != null ? "tenantPhone=" + tenantPhone + ", " : "") +
                (tenantEmail != null ? "tenantEmail=" + tenantEmail + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
            "}";
    }

}
