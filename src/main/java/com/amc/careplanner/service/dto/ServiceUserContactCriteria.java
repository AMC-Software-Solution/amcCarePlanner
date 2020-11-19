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
 * Criteria class for the {@link com.amc.careplanner.domain.ServiceUserContact} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.ServiceUserContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-user-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceUserContactCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter address;

    private StringFilter cityOrTown;

    private StringFilter county;

    private StringFilter postCode;

    private StringFilter telephone;

    private StringFilter email;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter tenantId;

    private LongFilter serviceUserId;

    public ServiceUserContactCriteria() {
    }

    public ServiceUserContactCriteria(ServiceUserContactCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.cityOrTown = other.cityOrTown == null ? null : other.cityOrTown.copy();
        this.county = other.county == null ? null : other.county.copy();
        this.postCode = other.postCode == null ? null : other.postCode.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
    }

    @Override
    public ServiceUserContactCriteria copy() {
        return new ServiceUserContactCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getCityOrTown() {
        return cityOrTown;
    }

    public void setCityOrTown(StringFilter cityOrTown) {
        this.cityOrTown = cityOrTown;
    }

    public StringFilter getCounty() {
        return county;
    }

    public void setCounty(StringFilter county) {
        this.county = county;
    }

    public StringFilter getPostCode() {
        return postCode;
    }

    public void setPostCode(StringFilter postCode) {
        this.postCode = postCode;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LongFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(LongFilter tenantId) {
        this.tenantId = tenantId;
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
        final ServiceUserContactCriteria that = (ServiceUserContactCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(address, that.address) &&
            Objects.equals(cityOrTown, that.cityOrTown) &&
            Objects.equals(county, that.county) &&
            Objects.equals(postCode, that.postCode) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(serviceUserId, that.serviceUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        address,
        cityOrTown,
        county,
        postCode,
        telephone,
        email,
        lastUpdatedDate,
        tenantId,
        serviceUserId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceUserContactCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (cityOrTown != null ? "cityOrTown=" + cityOrTown + ", " : "") +
                (county != null ? "county=" + county + ", " : "") +
                (postCode != null ? "postCode=" + postCode + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
            "}";
    }

}
