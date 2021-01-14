package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.CommunicationType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Communication} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.CommunicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /communications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommunicationCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CommunicationType
     */
    public static class CommunicationTypeFilter extends Filter<CommunicationType> {

        public CommunicationTypeFilter() {
        }

        public CommunicationTypeFilter(CommunicationTypeFilter filter) {
            super(filter);
        }

        @Override
        public CommunicationTypeFilter copy() {
            return new CommunicationTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private CommunicationTypeFilter communicationType;

    private StringFilter note;

    private ZonedDateTimeFilter communicationDate;

    private StringFilter attachmentUrl;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    private LongFilter serviceUserId;

    private LongFilter communicatedById;

    public CommunicationCriteria() {
    }

    public CommunicationCriteria(CommunicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.communicationType = other.communicationType == null ? null : other.communicationType.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.communicationDate = other.communicationDate == null ? null : other.communicationDate.copy();
        this.attachmentUrl = other.attachmentUrl == null ? null : other.attachmentUrl.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
        this.communicatedById = other.communicatedById == null ? null : other.communicatedById.copy();
    }

    @Override
    public CommunicationCriteria copy() {
        return new CommunicationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public CommunicationTypeFilter getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(CommunicationTypeFilter communicationType) {
        this.communicationType = communicationType;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public ZonedDateTimeFilter getCommunicationDate() {
        return communicationDate;
    }

    public void setCommunicationDate(ZonedDateTimeFilter communicationDate) {
        this.communicationDate = communicationDate;
    }

    public StringFilter getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(StringFilter attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
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

    public LongFilter getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(LongFilter serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public LongFilter getCommunicatedById() {
        return communicatedById;
    }

    public void setCommunicatedById(LongFilter communicatedById) {
        this.communicatedById = communicatedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommunicationCriteria that = (CommunicationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(communicationType, that.communicationType) &&
            Objects.equals(note, that.note) &&
            Objects.equals(communicationDate, that.communicationDate) &&
            Objects.equals(attachmentUrl, that.attachmentUrl) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData) &&
            Objects.equals(serviceUserId, that.serviceUserId) &&
            Objects.equals(communicatedById, that.communicatedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        communicationType,
        note,
        communicationDate,
        attachmentUrl,
        createdDate,
        lastUpdatedDate,
        clientId,
        hasExtraData,
        serviceUserId,
        communicatedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommunicationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (communicationType != null ? "communicationType=" + communicationType + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (communicationDate != null ? "communicationDate=" + communicationDate + ", " : "") +
                (attachmentUrl != null ? "attachmentUrl=" + attachmentUrl + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
                (communicatedById != null ? "communicatedById=" + communicatedById + ", " : "") +
            "}";
    }

}
