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
 * Criteria class for the {@link com.amc.careplanner.domain.SystemEventsHistory} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.SystemEventsHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /system-events-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SystemEventsHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter eventName;

    private ZonedDateTimeFilter eventDate;

    private StringFilter eventApi;

    private StringFilter ipAddress;

    private StringFilter eventNote;

    private StringFilter eventEntityName;

    private LongFilter eventEntityId;

    private BooleanFilter isSuspecious;

    private StringFilter callerEmail;

    private LongFilter callerId;

    private LongFilter clientId;

    private LongFilter triggedById;

    public SystemEventsHistoryCriteria() {
    }

    public SystemEventsHistoryCriteria(SystemEventsHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eventName = other.eventName == null ? null : other.eventName.copy();
        this.eventDate = other.eventDate == null ? null : other.eventDate.copy();
        this.eventApi = other.eventApi == null ? null : other.eventApi.copy();
        this.ipAddress = other.ipAddress == null ? null : other.ipAddress.copy();
        this.eventNote = other.eventNote == null ? null : other.eventNote.copy();
        this.eventEntityName = other.eventEntityName == null ? null : other.eventEntityName.copy();
        this.eventEntityId = other.eventEntityId == null ? null : other.eventEntityId.copy();
        this.isSuspecious = other.isSuspecious == null ? null : other.isSuspecious.copy();
        this.callerEmail = other.callerEmail == null ? null : other.callerEmail.copy();
        this.callerId = other.callerId == null ? null : other.callerId.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.triggedById = other.triggedById == null ? null : other.triggedById.copy();
    }

    @Override
    public SystemEventsHistoryCriteria copy() {
        return new SystemEventsHistoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEventName() {
        return eventName;
    }

    public void setEventName(StringFilter eventName) {
        this.eventName = eventName;
    }

    public ZonedDateTimeFilter getEventDate() {
        return eventDate;
    }

    public void setEventDate(ZonedDateTimeFilter eventDate) {
        this.eventDate = eventDate;
    }

    public StringFilter getEventApi() {
        return eventApi;
    }

    public void setEventApi(StringFilter eventApi) {
        this.eventApi = eventApi;
    }

    public StringFilter getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(StringFilter ipAddress) {
        this.ipAddress = ipAddress;
    }

    public StringFilter getEventNote() {
        return eventNote;
    }

    public void setEventNote(StringFilter eventNote) {
        this.eventNote = eventNote;
    }

    public StringFilter getEventEntityName() {
        return eventEntityName;
    }

    public void setEventEntityName(StringFilter eventEntityName) {
        this.eventEntityName = eventEntityName;
    }

    public LongFilter getEventEntityId() {
        return eventEntityId;
    }

    public void setEventEntityId(LongFilter eventEntityId) {
        this.eventEntityId = eventEntityId;
    }

    public BooleanFilter getIsSuspecious() {
        return isSuspecious;
    }

    public void setIsSuspecious(BooleanFilter isSuspecious) {
        this.isSuspecious = isSuspecious;
    }

    public StringFilter getCallerEmail() {
        return callerEmail;
    }

    public void setCallerEmail(StringFilter callerEmail) {
        this.callerEmail = callerEmail;
    }

    public LongFilter getCallerId() {
        return callerId;
    }

    public void setCallerId(LongFilter callerId) {
        this.callerId = callerId;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getTriggedById() {
        return triggedById;
    }

    public void setTriggedById(LongFilter triggedById) {
        this.triggedById = triggedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SystemEventsHistoryCriteria that = (SystemEventsHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(eventName, that.eventName) &&
            Objects.equals(eventDate, that.eventDate) &&
            Objects.equals(eventApi, that.eventApi) &&
            Objects.equals(ipAddress, that.ipAddress) &&
            Objects.equals(eventNote, that.eventNote) &&
            Objects.equals(eventEntityName, that.eventEntityName) &&
            Objects.equals(eventEntityId, that.eventEntityId) &&
            Objects.equals(isSuspecious, that.isSuspecious) &&
            Objects.equals(callerEmail, that.callerEmail) &&
            Objects.equals(callerId, that.callerId) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(triggedById, that.triggedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        eventName,
        eventDate,
        eventApi,
        ipAddress,
        eventNote,
        eventEntityName,
        eventEntityId,
        isSuspecious,
        callerEmail,
        callerId,
        clientId,
        triggedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemEventsHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (eventName != null ? "eventName=" + eventName + ", " : "") +
                (eventDate != null ? "eventDate=" + eventDate + ", " : "") +
                (eventApi != null ? "eventApi=" + eventApi + ", " : "") +
                (ipAddress != null ? "ipAddress=" + ipAddress + ", " : "") +
                (eventNote != null ? "eventNote=" + eventNote + ", " : "") +
                (eventEntityName != null ? "eventEntityName=" + eventEntityName + ", " : "") +
                (eventEntityId != null ? "eventEntityId=" + eventEntityId + ", " : "") +
                (isSuspecious != null ? "isSuspecious=" + isSuspecious + ", " : "") +
                (callerEmail != null ? "callerEmail=" + callerEmail + ", " : "") +
                (callerId != null ? "callerId=" + callerId + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (triggedById != null ? "triggedById=" + triggedById + ", " : "") +
            "}";
    }

}
