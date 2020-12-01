package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.amc.careplanner.domain.enumeration.EmployeeHolidayType;

/**
 * A DTO for the {@link com.amc.careplanner.domain.EmployeeHoliday} entity.
 */
public class EmployeeHolidayDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String description;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private EmployeeHolidayType employeeHolidayType;

    private ZonedDateTime approvedDate;

    private ZonedDateTime requestedDate;

    private Boolean approved;

    private String note;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long employeeId;

    private String employeeEmployeeCode;

    private Long approvedById;

    private String approvedByEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public EmployeeHolidayType getEmployeeHolidayType() {
        return employeeHolidayType;
    }

    public void setEmployeeHolidayType(EmployeeHolidayType employeeHolidayType) {
        this.employeeHolidayType = employeeHolidayType;
    }

    public ZonedDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(ZonedDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public ZonedDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(ZonedDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Long getApprovedById() {
        return approvedById;
    }

    public void setApprovedById(Long employeeId) {
        this.approvedById = employeeId;
    }

    public String getApprovedByEmployeeCode() {
        return approvedByEmployeeCode;
    }

    public void setApprovedByEmployeeCode(String employeeEmployeeCode) {
        this.approvedByEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeHolidayDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeHolidayDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeHolidayDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", employeeHolidayType='" + getEmployeeHolidayType() + "'" +
            ", approvedDate='" + getApprovedDate() + "'" +
            ", requestedDate='" + getRequestedDate() + "'" +
            ", approved='" + isApproved() + "'" +
            ", note='" + getNote() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", employeeId=" + getEmployeeId() +
            ", employeeEmployeeCode='" + getEmployeeEmployeeCode() + "'" +
            ", approvedById=" + getApprovedById() +
            ", approvedByEmployeeCode='" + getApprovedByEmployeeCode() + "'" +
            "}";
    }
}
