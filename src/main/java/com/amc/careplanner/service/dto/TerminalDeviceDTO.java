package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.TerminalDevice} entity.
 */
public class TerminalDeviceDTO implements Serializable {
    
    private Long id;

    private String deviceName;

    private String deviceModel;

    private ZonedDateTime registeredDate;

    private String imei;

    private String simNumber;

    private ZonedDateTime userStartedUsingFrom;

    private ZonedDateTime deviceOnLocationFrom;

    private String operatingSystem;

    private String note;

    private Long ownerEntityId;

    private String ownerEntityName;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long tenantId;


    private Long employeeId;

    private String employeeEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public ZonedDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(ZonedDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    public ZonedDateTime getUserStartedUsingFrom() {
        return userStartedUsingFrom;
    }

    public void setUserStartedUsingFrom(ZonedDateTime userStartedUsingFrom) {
        this.userStartedUsingFrom = userStartedUsingFrom;
    }

    public ZonedDateTime getDeviceOnLocationFrom() {
        return deviceOnLocationFrom;
    }

    public void setDeviceOnLocationFrom(ZonedDateTime deviceOnLocationFrom) {
        this.deviceOnLocationFrom = deviceOnLocationFrom;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getOwnerEntityId() {
        return ownerEntityId;
    }

    public void setOwnerEntityId(Long ownerEntityId) {
        this.ownerEntityId = ownerEntityId;
    }

    public String getOwnerEntityName() {
        return ownerEntityName;
    }

    public void setOwnerEntityName(String ownerEntityName) {
        this.ownerEntityName = ownerEntityName;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmployeeCode() {
        return employeeEmployeeCode;
    }

    public void setEmployeeEmployeeCode(String employeeEmployeeCode) {
        this.employeeEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TerminalDeviceDTO)) {
            return false;
        }

        return id != null && id.equals(((TerminalDeviceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TerminalDeviceDTO{" +
            "id=" + getId() +
            ", deviceName='" + getDeviceName() + "'" +
            ", deviceModel='" + getDeviceModel() + "'" +
            ", registeredDate='" + getRegisteredDate() + "'" +
            ", imei='" + getImei() + "'" +
            ", simNumber='" + getSimNumber() + "'" +
            ", userStartedUsingFrom='" + getUserStartedUsingFrom() + "'" +
            ", deviceOnLocationFrom='" + getDeviceOnLocationFrom() + "'" +
            ", operatingSystem='" + getOperatingSystem() + "'" +
            ", note='" + getNote() + "'" +
            ", ownerEntityId=" + getOwnerEntityId() +
            ", ownerEntityName='" + getOwnerEntityName() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            ", employeeId=" + getEmployeeId() +
            ", employeeEmployeeCode='" + getEmployeeEmployeeCode() + "'" +
            "}";
    }
}
