package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.TaskStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Task} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.TaskResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tasks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TaskStatus
     */
    public static class TaskStatusFilter extends Filter<TaskStatus> {

        public TaskStatusFilter() {
        }

        public TaskStatusFilter(TaskStatusFilter filter) {
            super(filter);
        }

        @Override
        public TaskStatusFilter copy() {
            return new TaskStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter taskName;

    private StringFilter description;

    private LocalDateFilter dateOfTask;

    private StringFilter startTime;

    private StringFilter endTime;

    private TaskStatusFilter status;

    private ZonedDateTimeFilter dateCreated;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter serviceUserId;

    private LongFilter assignedToId;

    private LongFilter serviceOrderId;

    private LongFilter createdById;

    private LongFilter allocatedById;

    public TaskCriteria() {
    }

    public TaskCriteria(TaskCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taskName = other.taskName == null ? null : other.taskName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.dateOfTask = other.dateOfTask == null ? null : other.dateOfTask.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.dateCreated = other.dateCreated == null ? null : other.dateCreated.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
        this.assignedToId = other.assignedToId == null ? null : other.assignedToId.copy();
        this.serviceOrderId = other.serviceOrderId == null ? null : other.serviceOrderId.copy();
        this.createdById = other.createdById == null ? null : other.createdById.copy();
        this.allocatedById = other.allocatedById == null ? null : other.allocatedById.copy();
    }

    @Override
    public TaskCriteria copy() {
        return new TaskCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTaskName() {
        return taskName;
    }

    public void setTaskName(StringFilter taskName) {
        this.taskName = taskName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getDateOfTask() {
        return dateOfTask;
    }

    public void setDateOfTask(LocalDateFilter dateOfTask) {
        this.dateOfTask = dateOfTask;
    }

    public StringFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(StringFilter startTime) {
        this.startTime = startTime;
    }

    public StringFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(StringFilter endTime) {
        this.endTime = endTime;
    }

    public TaskStatusFilter getStatus() {
        return status;
    }

    public void setStatus(TaskStatusFilter status) {
        this.status = status;
    }

    public ZonedDateTimeFilter getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTimeFilter dateCreated) {
        this.dateCreated = dateCreated;
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

    public LongFilter getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(LongFilter serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public LongFilter getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(LongFilter assignedToId) {
        this.assignedToId = assignedToId;
    }

    public LongFilter getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(LongFilter serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public LongFilter getCreatedById() {
        return createdById;
    }

    public void setCreatedById(LongFilter createdById) {
        this.createdById = createdById;
    }

    public LongFilter getAllocatedById() {
        return allocatedById;
    }

    public void setAllocatedById(LongFilter allocatedById) {
        this.allocatedById = allocatedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskCriteria that = (TaskCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(taskName, that.taskName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(dateOfTask, that.dateOfTask) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(status, that.status) &&
            Objects.equals(dateCreated, that.dateCreated) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(serviceUserId, that.serviceUserId) &&
            Objects.equals(assignedToId, that.assignedToId) &&
            Objects.equals(serviceOrderId, that.serviceOrderId) &&
            Objects.equals(createdById, that.createdById) &&
            Objects.equals(allocatedById, that.allocatedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        taskName,
        description,
        dateOfTask,
        startTime,
        endTime,
        status,
        dateCreated,
        lastUpdatedDate,
        clientId,
        serviceUserId,
        assignedToId,
        serviceOrderId,
        createdById,
        allocatedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taskName != null ? "taskName=" + taskName + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (dateOfTask != null ? "dateOfTask=" + dateOfTask + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (dateCreated != null ? "dateCreated=" + dateCreated + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
                (assignedToId != null ? "assignedToId=" + assignedToId + ", " : "") +
                (serviceOrderId != null ? "serviceOrderId=" + serviceOrderId + ", " : "") +
                (createdById != null ? "createdById=" + createdById + ", " : "") +
                (allocatedById != null ? "allocatedById=" + allocatedById + ", " : "") +
            "}";
    }

}
