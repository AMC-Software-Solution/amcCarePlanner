package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.ServiceUserEventStatus;
import com.amc.careplanner.domain.enumeration.ServiceUserEventType;
import com.amc.careplanner.domain.enumeration.Priority;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.ServiceUserEvent} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.ServiceUserEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-user-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceUserEventCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ServiceUserEventStatus
     */
    public static class ServiceUserEventStatusFilter extends Filter<ServiceUserEventStatus> {

        public ServiceUserEventStatusFilter() {
        }

        public ServiceUserEventStatusFilter(ServiceUserEventStatusFilter filter) {
            super(filter);
        }

        @Override
        public ServiceUserEventStatusFilter copy() {
            return new ServiceUserEventStatusFilter(this);
        }

    }
    /**
     * Class for filtering ServiceUserEventType
     */
    public static class ServiceUserEventTypeFilter extends Filter<ServiceUserEventType> {

        public ServiceUserEventTypeFilter() {
        }

        public ServiceUserEventTypeFilter(ServiceUserEventTypeFilter filter) {
            super(filter);
        }

        @Override
        public ServiceUserEventTypeFilter copy() {
            return new ServiceUserEventTypeFilter(this);
        }

    }
    /**
     * Class for filtering Priority
     */
    public static class PriorityFilter extends Filter<Priority> {

        public PriorityFilter() {
        }

        public PriorityFilter(PriorityFilter filter) {
            super(filter);
        }

        @Override
        public PriorityFilter copy() {
            return new PriorityFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter eventTitle;

    private StringFilter description;

    private ServiceUserEventStatusFilter serviceUserEventStatus;

    private ServiceUserEventTypeFilter serviceUserEventType;

    private PriorityFilter priority;

    private StringFilter note;

    private ZonedDateTimeFilter dateOfEvent;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    private LongFilter reportedById;

    private LongFilter assignedToId;

    private LongFilter serviceUserId;

    public ServiceUserEventCriteria() {
    }

    public ServiceUserEventCriteria(ServiceUserEventCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eventTitle = other.eventTitle == null ? null : other.eventTitle.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.serviceUserEventStatus = other.serviceUserEventStatus == null ? null : other.serviceUserEventStatus.copy();
        this.serviceUserEventType = other.serviceUserEventType == null ? null : other.serviceUserEventType.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.dateOfEvent = other.dateOfEvent == null ? null : other.dateOfEvent.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
        this.reportedById = other.reportedById == null ? null : other.reportedById.copy();
        this.assignedToId = other.assignedToId == null ? null : other.assignedToId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
    }

    @Override
    public ServiceUserEventCriteria copy() {
        return new ServiceUserEventCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(StringFilter eventTitle) {
        this.eventTitle = eventTitle;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ServiceUserEventStatusFilter getServiceUserEventStatus() {
        return serviceUserEventStatus;
    }

    public void setServiceUserEventStatus(ServiceUserEventStatusFilter serviceUserEventStatus) {
        this.serviceUserEventStatus = serviceUserEventStatus;
    }

    public ServiceUserEventTypeFilter getServiceUserEventType() {
        return serviceUserEventType;
    }

    public void setServiceUserEventType(ServiceUserEventTypeFilter serviceUserEventType) {
        this.serviceUserEventType = serviceUserEventType;
    }

    public PriorityFilter getPriority() {
        return priority;
    }

    public void setPriority(PriorityFilter priority) {
        this.priority = priority;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public ZonedDateTimeFilter getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(ZonedDateTimeFilter dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
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

    public LongFilter getReportedById() {
        return reportedById;
    }

    public void setReportedById(LongFilter reportedById) {
        this.reportedById = reportedById;
    }

    public LongFilter getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(LongFilter assignedToId) {
        this.assignedToId = assignedToId;
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
        final ServiceUserEventCriteria that = (ServiceUserEventCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(eventTitle, that.eventTitle) &&
            Objects.equals(description, that.description) &&
            Objects.equals(serviceUserEventStatus, that.serviceUserEventStatus) &&
            Objects.equals(serviceUserEventType, that.serviceUserEventType) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(note, that.note) &&
            Objects.equals(dateOfEvent, that.dateOfEvent) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData) &&
            Objects.equals(reportedById, that.reportedById) &&
            Objects.equals(assignedToId, that.assignedToId) &&
            Objects.equals(serviceUserId, that.serviceUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        eventTitle,
        description,
        serviceUserEventStatus,
        serviceUserEventType,
        priority,
        note,
        dateOfEvent,
        createdDate,
        lastUpdatedDate,
        clientId,
        hasExtraData,
        reportedById,
        assignedToId,
        serviceUserId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceUserEventCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (eventTitle != null ? "eventTitle=" + eventTitle + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (serviceUserEventStatus != null ? "serviceUserEventStatus=" + serviceUserEventStatus + ", " : "") +
                (serviceUserEventType != null ? "serviceUserEventType=" + serviceUserEventType + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (dateOfEvent != null ? "dateOfEvent=" + dateOfEvent + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
                (reportedById != null ? "reportedById=" + reportedById + ", " : "") +
                (assignedToId != null ? "assignedToId=" + assignedToId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
            "}";
    }

}
