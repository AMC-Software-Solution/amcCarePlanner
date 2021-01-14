package com.amc.careplanner.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.amc.careplanner.domain.enumeration.TaskStatus;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Task} entity.
 */
public class TaskDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String taskName;

    private String description;

    @NotNull
    private LocalDate dateOfTask;

    @NotNull
    private String startTime;

    private String endTime;

    private TaskStatus status;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;

    private Boolean hasExtraData;


    private Long serviceUserId;

    private String serviceUserServiceUserCode;

    private Long assignedToId;

    private String assignedToEmployeeCode;

    private Long serviceOrderId;

    private String serviceOrderTitle;

    private Long createdById;

    private String createdByEmployeeCode;

    private Long allocatedById;

    private String allocatedByEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfTask() {
        return dateOfTask;
    }

    public void setDateOfTask(LocalDate dateOfTask) {
        this.dateOfTask = dateOfTask;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
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

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long employeeId) {
        this.createdById = employeeId;
    }

    public String getCreatedByEmployeeCode() {
        return createdByEmployeeCode;
    }

    public void setCreatedByEmployeeCode(String employeeEmployeeCode) {
        this.createdByEmployeeCode = employeeEmployeeCode;
    }

    public Long getAllocatedById() {
        return allocatedById;
    }

    public void setAllocatedById(Long employeeId) {
        this.allocatedById = employeeId;
    }

    public String getAllocatedByEmployeeCode() {
        return allocatedByEmployeeCode;
    }

    public void setAllocatedByEmployeeCode(String employeeEmployeeCode) {
        this.allocatedByEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + getId() +
            ", taskName='" + getTaskName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateOfTask='" + getDateOfTask() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            ", assignedToId=" + getAssignedToId() +
            ", assignedToEmployeeCode='" + getAssignedToEmployeeCode() + "'" +
            ", serviceOrderId=" + getServiceOrderId() +
            ", serviceOrderTitle='" + getServiceOrderTitle() + "'" +
            ", createdById=" + getCreatedById() +
            ", createdByEmployeeCode='" + getCreatedByEmployeeCode() + "'" +
            ", allocatedById=" + getAllocatedById() +
            ", allocatedByEmployeeCode='" + getAllocatedByEmployeeCode() + "'" +
            "}";
    }
}
