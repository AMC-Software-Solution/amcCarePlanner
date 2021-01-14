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
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Timesheet} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.TimesheetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /timesheets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TimesheetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LocalDateFilter timesheetDate;

    private StringFilter startTime;

    private StringFilter endTime;

    private IntegerFilter hoursWorked;

    private StringFilter breakStartTime;

    private StringFilter breakEndTime;

    private StringFilter note;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    private LongFilter taskId;

    private LongFilter serviceUserId;

    private LongFilter careProviderId;

    public TimesheetCriteria() {
    }

    public TimesheetCriteria(TimesheetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.timesheetDate = other.timesheetDate == null ? null : other.timesheetDate.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.hoursWorked = other.hoursWorked == null ? null : other.hoursWorked.copy();
        this.breakStartTime = other.breakStartTime == null ? null : other.breakStartTime.copy();
        this.breakEndTime = other.breakEndTime == null ? null : other.breakEndTime.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
        this.careProviderId = other.careProviderId == null ? null : other.careProviderId.copy();
    }

    @Override
    public TimesheetCriteria copy() {
        return new TimesheetCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getTimesheetDate() {
        return timesheetDate;
    }

    public void setTimesheetDate(LocalDateFilter timesheetDate) {
        this.timesheetDate = timesheetDate;
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

    public IntegerFilter getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(IntegerFilter hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public StringFilter getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(StringFilter breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public StringFilter getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(StringFilter breakEndTime) {
        this.breakEndTime = breakEndTime;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
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

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public LongFilter getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(LongFilter serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public LongFilter getCareProviderId() {
        return careProviderId;
    }

    public void setCareProviderId(LongFilter careProviderId) {
        this.careProviderId = careProviderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TimesheetCriteria that = (TimesheetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(timesheetDate, that.timesheetDate) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(hoursWorked, that.hoursWorked) &&
            Objects.equals(breakStartTime, that.breakStartTime) &&
            Objects.equals(breakEndTime, that.breakEndTime) &&
            Objects.equals(note, that.note) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(serviceUserId, that.serviceUserId) &&
            Objects.equals(careProviderId, that.careProviderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        timesheetDate,
        startTime,
        endTime,
        hoursWorked,
        breakStartTime,
        breakEndTime,
        note,
        createdDate,
        lastUpdatedDate,
        clientId,
        hasExtraData,
        taskId,
        serviceUserId,
        careProviderId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimesheetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (timesheetDate != null ? "timesheetDate=" + timesheetDate + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (hoursWorked != null ? "hoursWorked=" + hoursWorked + ", " : "") +
                (breakStartTime != null ? "breakStartTime=" + breakStartTime + ", " : "") +
                (breakEndTime != null ? "breakEndTime=" + breakEndTime + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
                (careProviderId != null ? "careProviderId=" + careProviderId + ", " : "") +
            "}";
    }

}
