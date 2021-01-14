package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.amc.careplanner.domain.enumeration.Shift;

/**
 * A DTO for the {@link com.amc.careplanner.domain.EmployeeAvailability} entity.
 */
public class EmployeeAvailabilityDTO implements Serializable {
    
    private Long id;

    private Boolean availableForWork;

    private Boolean availableMonday;

    private Boolean availableTuesday;

    private Boolean availableWednesday;

    private Boolean availableThursday;

    private Boolean availableFriday;

    private Boolean availableSaturday;

    private Boolean availableSunday;

    private Shift preferredShift;

    private Boolean hasExtraData;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long employeeId;

    private String employeeEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAvailableForWork() {
        return availableForWork;
    }

    public void setAvailableForWork(Boolean availableForWork) {
        this.availableForWork = availableForWork;
    }

    public Boolean isAvailableMonday() {
        return availableMonday;
    }

    public void setAvailableMonday(Boolean availableMonday) {
        this.availableMonday = availableMonday;
    }

    public Boolean isAvailableTuesday() {
        return availableTuesday;
    }

    public void setAvailableTuesday(Boolean availableTuesday) {
        this.availableTuesday = availableTuesday;
    }

    public Boolean isAvailableWednesday() {
        return availableWednesday;
    }

    public void setAvailableWednesday(Boolean availableWednesday) {
        this.availableWednesday = availableWednesday;
    }

    public Boolean isAvailableThursday() {
        return availableThursday;
    }

    public void setAvailableThursday(Boolean availableThursday) {
        this.availableThursday = availableThursday;
    }

    public Boolean isAvailableFriday() {
        return availableFriday;
    }

    public void setAvailableFriday(Boolean availableFriday) {
        this.availableFriday = availableFriday;
    }

    public Boolean isAvailableSaturday() {
        return availableSaturday;
    }

    public void setAvailableSaturday(Boolean availableSaturday) {
        this.availableSaturday = availableSaturday;
    }

    public Boolean isAvailableSunday() {
        return availableSunday;
    }

    public void setAvailableSunday(Boolean availableSunday) {
        this.availableSunday = availableSunday;
    }

    public Shift getPreferredShift() {
        return preferredShift;
    }

    public void setPreferredShift(Shift preferredShift) {
        this.preferredShift = preferredShift;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
        if (!(o instanceof EmployeeAvailabilityDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeAvailabilityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAvailabilityDTO{" +
            "id=" + getId() +
            ", availableForWork='" + isAvailableForWork() + "'" +
            ", availableMonday='" + isAvailableMonday() + "'" +
            ", availableTuesday='" + isAvailableTuesday() + "'" +
            ", availableWednesday='" + isAvailableWednesday() + "'" +
            ", availableThursday='" + isAvailableThursday() + "'" +
            ", availableFriday='" + isAvailableFriday() + "'" +
            ", availableSaturday='" + isAvailableSaturday() + "'" +
            ", availableSunday='" + isAvailableSunday() + "'" +
            ", preferredShift='" + getPreferredShift() + "'" +
            ", hasExtraData='" + isHasExtraData() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", employeeId=" + getEmployeeId() +
            ", employeeEmployeeCode='" + getEmployeeEmployeeCode() + "'" +
            "}";
    }
}
