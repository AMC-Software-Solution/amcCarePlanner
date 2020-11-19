package com.amc.careplanner.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Timesheet} entity.
 */
public class TimesheetDTO implements Serializable {
    
    private Long id;

    private String description;

    @NotNull
    private LocalDate timesheetDate;

    @NotNull
    private String startTime;

    @NotNull
    private String endTime;

    @NotNull
    private Integer hoursWorked;

    private String breakStartTime;

    private String breakEndTime;

    private String note;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long tenantId;


    private Long serviceOrderId;

    private String serviceOrderTitle;

    private Long serviceUserId;

    private String serviceUserServiceUserCode;

    private Long careProviderId;

    private String careProviderEmployeeCode;
    
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

    public LocalDate getTimesheetDate() {
        return timesheetDate;
    }

    public void setTimesheetDate(LocalDate timesheetDate) {
        this.timesheetDate = timesheetDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(String breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public String getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(String breakEndTime) {
        this.breakEndTime = breakEndTime;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(Long serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public String getServiceOrderTitle() {
        return serviceOrderTitle;
    }

    public void setServiceOrderTitle(String serviceOrderTitle) {
        this.serviceOrderTitle = serviceOrderTitle;
    }

    public Long getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(Long serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public String getServiceUserServiceUserCode() {
        return serviceUserServiceUserCode;
    }

    public void setServiceUserServiceUserCode(String serviceUserServiceUserCode) {
        this.serviceUserServiceUserCode = serviceUserServiceUserCode;
    }

    public Long getCareProviderId() {
        return careProviderId;
    }

    public void setCareProviderId(Long employeeId) {
        this.careProviderId = employeeId;
    }

    public String getCareProviderEmployeeCode() {
        return careProviderEmployeeCode;
    }

    public void setCareProviderEmployeeCode(String employeeEmployeeCode) {
        this.careProviderEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimesheetDTO)) {
            return false;
        }

        return id != null && id.equals(((TimesheetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimesheetDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", timesheetDate='" + getTimesheetDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", hoursWorked=" + getHoursWorked() +
            ", breakStartTime='" + getBreakStartTime() + "'" +
            ", breakEndTime='" + getBreakEndTime() + "'" +
            ", note='" + getNote() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            ", serviceOrderId=" + getServiceOrderId() +
            ", serviceOrderTitle='" + getServiceOrderTitle() + "'" +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            ", careProviderId=" + getCareProviderId() +
            ", careProviderEmployeeCode='" + getCareProviderEmployeeCode() + "'" +
            "}";
    }
}
