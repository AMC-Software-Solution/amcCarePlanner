package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.amc.careplanner.domain.enumeration.ServiceUserEventStatus;
import com.amc.careplanner.domain.enumeration.ServiceUserEventType;
import com.amc.careplanner.domain.enumeration.ServicePriority;

/**
 * A DTO for the {@link com.amc.careplanner.domain.ServiceUserEvent} entity.
 */
public class ServiceUserEventDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String eventTitle;

    private String description;

    private ServiceUserEventStatus serviceUserEventStatus;

    private ServiceUserEventType serviceUserEventType;

    private ServicePriority priority;

    private String note;

    @NotNull
    private ZonedDateTime dateOfEvent;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;

    private Boolean hasExtraData;


    private Long reportedById;

    private String reportedByEmployeeCode;

    private Long assignedToId;

    private String assignedToEmployeeCode;

    private Long serviceUserId;

    private String serviceUserServiceUserCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceUserEventStatus getServiceUserEventStatus() {
        return serviceUserEventStatus;
    }

    public void setServiceUserEventStatus(ServiceUserEventStatus serviceUserEventStatus) {
        this.serviceUserEventStatus = serviceUserEventStatus;
    }

    public ServiceUserEventType getServiceUserEventType() {
        return serviceUserEventType;
    }

    public void setServiceUserEventType(ServiceUserEventType serviceUserEventType) {
        this.serviceUserEventType = serviceUserEventType;
    }

    public ServicePriority getPriority() {
        return priority;
    }

    public void setPriority(ServicePriority priority) {
        this.priority = priority;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(ZonedDateTime dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
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

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public Long getReportedById() {
        return reportedById;
    }

    public void setReportedById(Long employeeId) {
        this.reportedById = employeeId;
    }

    public String getReportedByEmployeeCode() {
        return reportedByEmployeeCode;
    }

    public void setReportedByEmployeeCode(String employeeEmployeeCode) {
        this.reportedByEmployeeCode = employeeEmployeeCode;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long employeeId) {
        this.assignedToId = employeeId;
    }

    public String getAssignedToEmployeeCode() {
        return assignedToEmployeeCode;
    }

    public void setAssignedToEmployeeCode(String employeeEmployeeCode) {
        this.assignedToEmployeeCode = employeeEmployeeCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceUserEventDTO)) {
            return false;
        }

        return id != null && id.equals(((ServiceUserEventDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceUserEventDTO{" +
            "id=" + getId() +
            ", eventTitle='" + getEventTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", serviceUserEventStatus='" + getServiceUserEventStatus() + "'" +
            ", serviceUserEventType='" + getServiceUserEventType() + "'" +
            ", priority='" + getPriority() + "'" +
            ", note='" + getNote() + "'" +
            ", dateOfEvent='" + getDateOfEvent() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            ", reportedById=" + getReportedById() +
            ", reportedByEmployeeCode='" + getReportedByEmployeeCode() + "'" +
            ", assignedToId=" + getAssignedToId() +
            ", assignedToEmployeeCode='" + getAssignedToEmployeeCode() + "'" +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            "}";
    }
}
