package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.TravelMode;
import com.amc.careplanner.domain.enumeration.TravelStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Travel} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.TravelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /travels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TravelCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TravelMode
     */
    public static class TravelModeFilter extends Filter<TravelMode> {

        public TravelModeFilter() {
        }

        public TravelModeFilter(TravelModeFilter filter) {
            super(filter);
        }

        @Override
        public TravelModeFilter copy() {
            return new TravelModeFilter(this);
        }

    }
    /**
     * Class for filtering TravelStatus
     */
    public static class TravelStatusFilter extends Filter<TravelStatus> {

        public TravelStatusFilter() {
        }

        public TravelStatusFilter(TravelStatusFilter filter) {
            super(filter);
        }

        @Override
        public TravelStatusFilter copy() {
            return new TravelStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TravelModeFilter travelMode;

    private LongFilter distanceToDestination;

    private LongFilter timeToDestination;

    private LongFilter actualDistanceRequired;

    private LongFilter actualTimeRequired;

    private TravelStatusFilter travelStatus;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    private LongFilter taskId;

    public TravelCriteria() {
    }

    public TravelCriteria(TravelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.travelMode = other.travelMode == null ? null : other.travelMode.copy();
        this.distanceToDestination = other.distanceToDestination == null ? null : other.distanceToDestination.copy();
        this.timeToDestination = other.timeToDestination == null ? null : other.timeToDestination.copy();
        this.actualDistanceRequired = other.actualDistanceRequired == null ? null : other.actualDistanceRequired.copy();
        this.actualTimeRequired = other.actualTimeRequired == null ? null : other.actualTimeRequired.copy();
        this.travelStatus = other.travelStatus == null ? null : other.travelStatus.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
    }

    @Override
    public TravelCriteria copy() {
        return new TravelCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TravelModeFilter getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(TravelModeFilter travelMode) {
        this.travelMode = travelMode;
    }

    public LongFilter getDistanceToDestination() {
        return distanceToDestination;
    }

    public void setDistanceToDestination(LongFilter distanceToDestination) {
        this.distanceToDestination = distanceToDestination;
    }

    public LongFilter getTimeToDestination() {
        return timeToDestination;
    }

    public void setTimeToDestination(LongFilter timeToDestination) {
        this.timeToDestination = timeToDestination;
    }

    public LongFilter getActualDistanceRequired() {
        return actualDistanceRequired;
    }

    public void setActualDistanceRequired(LongFilter actualDistanceRequired) {
        this.actualDistanceRequired = actualDistanceRequired;
    }

    public LongFilter getActualTimeRequired() {
        return actualTimeRequired;
    }

    public void setActualTimeRequired(LongFilter actualTimeRequired) {
        this.actualTimeRequired = actualTimeRequired;
    }

    public TravelStatusFilter getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(TravelStatusFilter travelStatus) {
        this.travelStatus = travelStatus;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TravelCriteria that = (TravelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(travelMode, that.travelMode) &&
            Objects.equals(distanceToDestination, that.distanceToDestination) &&
            Objects.equals(timeToDestination, that.timeToDestination) &&
            Objects.equals(actualDistanceRequired, that.actualDistanceRequired) &&
            Objects.equals(actualTimeRequired, that.actualTimeRequired) &&
            Objects.equals(travelStatus, that.travelStatus) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData) &&
            Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        travelMode,
        distanceToDestination,
        timeToDestination,
        actualDistanceRequired,
        actualTimeRequired,
        travelStatus,
        createdDate,
        lastUpdatedDate,
        clientId,
        hasExtraData,
        taskId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TravelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (travelMode != null ? "travelMode=" + travelMode + ", " : "") +
                (distanceToDestination != null ? "distanceToDestination=" + distanceToDestination + ", " : "") +
                (timeToDestination != null ? "timeToDestination=" + timeToDestination + ", " : "") +
                (actualDistanceRequired != null ? "actualDistanceRequired=" + actualDistanceRequired + ", " : "") +
                (actualTimeRequired != null ? "actualTimeRequired=" + actualTimeRequired + ", " : "") +
                (travelStatus != null ? "travelStatus=" + travelStatus + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
            "}";
    }

}
