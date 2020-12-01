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
 * Criteria class for the {@link com.amc.careplanner.domain.TerminalDevice} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.TerminalDeviceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /terminal-devices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TerminalDeviceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter deviceName;

    private StringFilter deviceModel;

    private ZonedDateTimeFilter registeredDate;

    private StringFilter imei;

    private StringFilter simNumber;

    private ZonedDateTimeFilter userStartedUsingFrom;

    private ZonedDateTimeFilter deviceOnLocationFrom;

    private StringFilter operatingSystem;

    private StringFilter note;

    private LongFilter ownerEntityId;

    private StringFilter ownerEntityName;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter employeeId;

    public TerminalDeviceCriteria() {
    }

    public TerminalDeviceCriteria(TerminalDeviceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.deviceName = other.deviceName == null ? null : other.deviceName.copy();
        this.deviceModel = other.deviceModel == null ? null : other.deviceModel.copy();
        this.registeredDate = other.registeredDate == null ? null : other.registeredDate.copy();
        this.imei = other.imei == null ? null : other.imei.copy();
        this.simNumber = other.simNumber == null ? null : other.simNumber.copy();
        this.userStartedUsingFrom = other.userStartedUsingFrom == null ? null : other.userStartedUsingFrom.copy();
        this.deviceOnLocationFrom = other.deviceOnLocationFrom == null ? null : other.deviceOnLocationFrom.copy();
        this.operatingSystem = other.operatingSystem == null ? null : other.operatingSystem.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.ownerEntityId = other.ownerEntityId == null ? null : other.ownerEntityId.copy();
        this.ownerEntityName = other.ownerEntityName == null ? null : other.ownerEntityName.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public TerminalDeviceCriteria copy() {
        return new TerminalDeviceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(StringFilter deviceName) {
        this.deviceName = deviceName;
    }

    public StringFilter getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(StringFilter deviceModel) {
        this.deviceModel = deviceModel;
    }

    public ZonedDateTimeFilter getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(ZonedDateTimeFilter registeredDate) {
        this.registeredDate = registeredDate;
    }

    public StringFilter getImei() {
        return imei;
    }

    public void setImei(StringFilter imei) {
        this.imei = imei;
    }

    public StringFilter getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(StringFilter simNumber) {
        this.simNumber = simNumber;
    }

    public ZonedDateTimeFilter getUserStartedUsingFrom() {
        return userStartedUsingFrom;
    }

    public void setUserStartedUsingFrom(ZonedDateTimeFilter userStartedUsingFrom) {
        this.userStartedUsingFrom = userStartedUsingFrom;
    }

    public ZonedDateTimeFilter getDeviceOnLocationFrom() {
        return deviceOnLocationFrom;
    }

    public void setDeviceOnLocationFrom(ZonedDateTimeFilter deviceOnLocationFrom) {
        this.deviceOnLocationFrom = deviceOnLocationFrom;
    }

    public StringFilter getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(StringFilter operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public LongFilter getOwnerEntityId() {
        return ownerEntityId;
    }

    public void setOwnerEntityId(LongFilter ownerEntityId) {
        this.ownerEntityId = ownerEntityId;
    }

    public StringFilter getOwnerEntityName() {
        return ownerEntityName;
    }

    public void setOwnerEntityName(StringFilter ownerEntityName) {
        this.ownerEntityName = ownerEntityName;
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

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TerminalDeviceCriteria that = (TerminalDeviceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(deviceName, that.deviceName) &&
            Objects.equals(deviceModel, that.deviceModel) &&
            Objects.equals(registeredDate, that.registeredDate) &&
            Objects.equals(imei, that.imei) &&
            Objects.equals(simNumber, that.simNumber) &&
            Objects.equals(userStartedUsingFrom, that.userStartedUsingFrom) &&
            Objects.equals(deviceOnLocationFrom, that.deviceOnLocationFrom) &&
            Objects.equals(operatingSystem, that.operatingSystem) &&
            Objects.equals(note, that.note) &&
            Objects.equals(ownerEntityId, that.ownerEntityId) &&
            Objects.equals(ownerEntityName, that.ownerEntityName) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        deviceName,
        deviceModel,
        registeredDate,
        imei,
        simNumber,
        userStartedUsingFrom,
        deviceOnLocationFrom,
        operatingSystem,
        note,
        ownerEntityId,
        ownerEntityName,
        lastUpdatedDate,
        clientId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TerminalDeviceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (deviceName != null ? "deviceName=" + deviceName + ", " : "") +
                (deviceModel != null ? "deviceModel=" + deviceModel + ", " : "") +
                (registeredDate != null ? "registeredDate=" + registeredDate + ", " : "") +
                (imei != null ? "imei=" + imei + ", " : "") +
                (simNumber != null ? "simNumber=" + simNumber + ", " : "") +
                (userStartedUsingFrom != null ? "userStartedUsingFrom=" + userStartedUsingFrom + ", " : "") +
                (deviceOnLocationFrom != null ? "deviceOnLocationFrom=" + deviceOnLocationFrom + ", " : "") +
                (operatingSystem != null ? "operatingSystem=" + operatingSystem + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (ownerEntityId != null ? "ownerEntityId=" + ownerEntityId + ", " : "") +
                (ownerEntityName != null ? "ownerEntityName=" + ownerEntityName + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
