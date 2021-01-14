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
 * Criteria class for the {@link com.amc.careplanner.domain.MedicalContact} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.MedicalContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /medical-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MedicalContactCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter doctorName;

    private StringFilter doctorSurgery;

    private StringFilter doctorAddress;

    private StringFilter doctorPhone;

    private ZonedDateTimeFilter lastVisitedDoctor;

    private StringFilter districtNurseName;

    private StringFilter districtNursePhone;

    private StringFilter careManagerName;

    private StringFilter careManagerPhone;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    private LongFilter serviceUserId;

    public MedicalContactCriteria() {
    }

    public MedicalContactCriteria(MedicalContactCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.doctorName = other.doctorName == null ? null : other.doctorName.copy();
        this.doctorSurgery = other.doctorSurgery == null ? null : other.doctorSurgery.copy();
        this.doctorAddress = other.doctorAddress == null ? null : other.doctorAddress.copy();
        this.doctorPhone = other.doctorPhone == null ? null : other.doctorPhone.copy();
        this.lastVisitedDoctor = other.lastVisitedDoctor == null ? null : other.lastVisitedDoctor.copy();
        this.districtNurseName = other.districtNurseName == null ? null : other.districtNurseName.copy();
        this.districtNursePhone = other.districtNursePhone == null ? null : other.districtNursePhone.copy();
        this.careManagerName = other.careManagerName == null ? null : other.careManagerName.copy();
        this.careManagerPhone = other.careManagerPhone == null ? null : other.careManagerPhone.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
    }

    @Override
    public MedicalContactCriteria copy() {
        return new MedicalContactCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(StringFilter doctorName) {
        this.doctorName = doctorName;
    }

    public StringFilter getDoctorSurgery() {
        return doctorSurgery;
    }

    public void setDoctorSurgery(StringFilter doctorSurgery) {
        this.doctorSurgery = doctorSurgery;
    }

    public StringFilter getDoctorAddress() {
        return doctorAddress;
    }

    public void setDoctorAddress(StringFilter doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public StringFilter getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(StringFilter doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public ZonedDateTimeFilter getLastVisitedDoctor() {
        return lastVisitedDoctor;
    }

    public void setLastVisitedDoctor(ZonedDateTimeFilter lastVisitedDoctor) {
        this.lastVisitedDoctor = lastVisitedDoctor;
    }

    public StringFilter getDistrictNurseName() {
        return districtNurseName;
    }

    public void setDistrictNurseName(StringFilter districtNurseName) {
        this.districtNurseName = districtNurseName;
    }

    public StringFilter getDistrictNursePhone() {
        return districtNursePhone;
    }

    public void setDistrictNursePhone(StringFilter districtNursePhone) {
        this.districtNursePhone = districtNursePhone;
    }

    public StringFilter getCareManagerName() {
        return careManagerName;
    }

    public void setCareManagerName(StringFilter careManagerName) {
        this.careManagerName = careManagerName;
    }

    public StringFilter getCareManagerPhone() {
        return careManagerPhone;
    }

    public void setCareManagerPhone(StringFilter careManagerPhone) {
        this.careManagerPhone = careManagerPhone;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MedicalContactCriteria that = (MedicalContactCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(doctorName, that.doctorName) &&
            Objects.equals(doctorSurgery, that.doctorSurgery) &&
            Objects.equals(doctorAddress, that.doctorAddress) &&
            Objects.equals(doctorPhone, that.doctorPhone) &&
            Objects.equals(lastVisitedDoctor, that.lastVisitedDoctor) &&
            Objects.equals(districtNurseName, that.districtNurseName) &&
            Objects.equals(districtNursePhone, that.districtNursePhone) &&
            Objects.equals(careManagerName, that.careManagerName) &&
            Objects.equals(careManagerPhone, that.careManagerPhone) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData) &&
            Objects.equals(serviceUserId, that.serviceUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        doctorName,
        doctorSurgery,
        doctorAddress,
        doctorPhone,
        lastVisitedDoctor,
        districtNurseName,
        districtNursePhone,
        careManagerName,
        careManagerPhone,
        createdDate,
        lastUpdatedDate,
        clientId,
        hasExtraData,
        serviceUserId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalContactCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (doctorName != null ? "doctorName=" + doctorName + ", " : "") +
                (doctorSurgery != null ? "doctorSurgery=" + doctorSurgery + ", " : "") +
                (doctorAddress != null ? "doctorAddress=" + doctorAddress + ", " : "") +
                (doctorPhone != null ? "doctorPhone=" + doctorPhone + ", " : "") +
                (lastVisitedDoctor != null ? "lastVisitedDoctor=" + lastVisitedDoctor + ", " : "") +
                (districtNurseName != null ? "districtNurseName=" + districtNurseName + ", " : "") +
                (districtNursePhone != null ? "districtNursePhone=" + districtNursePhone + ", " : "") +
                (careManagerName != null ? "careManagerName=" + careManagerName + ", " : "") +
                (careManagerPhone != null ? "careManagerPhone=" + careManagerPhone + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
            "}";
    }

}
