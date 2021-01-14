package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.Shift;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.EmployeeAvailability} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.EmployeeAvailabilityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-availabilities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeAvailabilityCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Shift
     */
    public static class ShiftFilter extends Filter<Shift> {

        public ShiftFilter() {
        }

        public ShiftFilter(ShiftFilter filter) {
            super(filter);
        }

        @Override
        public ShiftFilter copy() {
            return new ShiftFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter availableForWork;

    private BooleanFilter availableMonday;

    private BooleanFilter availableTuesday;

    private BooleanFilter availableWednesday;

    private BooleanFilter availableThursday;

    private BooleanFilter availableFriday;

    private BooleanFilter availableSaturday;

    private BooleanFilter availableSunday;

    private ShiftFilter preferredShift;

    private BooleanFilter hasExtraData;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter employeeId;

    public EmployeeAvailabilityCriteria() {
    }

    public EmployeeAvailabilityCriteria(EmployeeAvailabilityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.availableForWork = other.availableForWork == null ? null : other.availableForWork.copy();
        this.availableMonday = other.availableMonday == null ? null : other.availableMonday.copy();
        this.availableTuesday = other.availableTuesday == null ? null : other.availableTuesday.copy();
        this.availableWednesday = other.availableWednesday == null ? null : other.availableWednesday.copy();
        this.availableThursday = other.availableThursday == null ? null : other.availableThursday.copy();
        this.availableFriday = other.availableFriday == null ? null : other.availableFriday.copy();
        this.availableSaturday = other.availableSaturday == null ? null : other.availableSaturday.copy();
        this.availableSunday = other.availableSunday == null ? null : other.availableSunday.copy();
        this.preferredShift = other.preferredShift == null ? null : other.preferredShift.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public EmployeeAvailabilityCriteria copy() {
        return new EmployeeAvailabilityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getAvailableForWork() {
        return availableForWork;
    }

    public void setAvailableForWork(BooleanFilter availableForWork) {
        this.availableForWork = availableForWork;
    }

    public BooleanFilter getAvailableMonday() {
        return availableMonday;
    }

    public void setAvailableMonday(BooleanFilter availableMonday) {
        this.availableMonday = availableMonday;
    }

    public BooleanFilter getAvailableTuesday() {
        return availableTuesday;
    }

    public void setAvailableTuesday(BooleanFilter availableTuesday) {
        this.availableTuesday = availableTuesday;
    }

    public BooleanFilter getAvailableWednesday() {
        return availableWednesday;
    }

    public void setAvailableWednesday(BooleanFilter availableWednesday) {
        this.availableWednesday = availableWednesday;
    }

    public BooleanFilter getAvailableThursday() {
        return availableThursday;
    }

    public void setAvailableThursday(BooleanFilter availableThursday) {
        this.availableThursday = availableThursday;
    }

    public BooleanFilter getAvailableFriday() {
        return availableFriday;
    }

    public void setAvailableFriday(BooleanFilter availableFriday) {
        this.availableFriday = availableFriday;
    }

    public BooleanFilter getAvailableSaturday() {
        return availableSaturday;
    }

    public void setAvailableSaturday(BooleanFilter availableSaturday) {
        this.availableSaturday = availableSaturday;
    }

    public BooleanFilter getAvailableSunday() {
        return availableSunday;
    }

    public void setAvailableSunday(BooleanFilter availableSunday) {
        this.availableSunday = availableSunday;
    }

    public ShiftFilter getPreferredShift() {
        return preferredShift;
    }

    public void setPreferredShift(ShiftFilter preferredShift) {
        this.preferredShift = preferredShift;
    }

    public BooleanFilter getHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(BooleanFilter hasExtraData) {
        this.hasExtraData = hasExtraData;
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

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeAvailabilityCriteria that = (EmployeeAvailabilityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(availableForWork, that.availableForWork) &&
            Objects.equals(availableMonday, that.availableMonday) &&
            Objects.equals(availableTuesday, that.availableTuesday) &&
            Objects.equals(availableWednesday, that.availableWednesday) &&
            Objects.equals(availableThursday, that.availableThursday) &&
            Objects.equals(availableFriday, that.availableFriday) &&
            Objects.equals(availableSaturday, that.availableSaturday) &&
            Objects.equals(availableSunday, that.availableSunday) &&
            Objects.equals(preferredShift, that.preferredShift) &&
            Objects.equals(hasExtraData, that.hasExtraData) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        availableForWork,
        availableMonday,
        availableTuesday,
        availableWednesday,
        availableThursday,
        availableFriday,
        availableSaturday,
        availableSunday,
        preferredShift,
        hasExtraData,
        lastUpdatedDate,
        clientId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAvailabilityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (availableForWork != null ? "availableForWork=" + availableForWork + ", " : "") +
                (availableMonday != null ? "availableMonday=" + availableMonday + ", " : "") +
                (availableTuesday != null ? "availableTuesday=" + availableTuesday + ", " : "") +
                (availableWednesday != null ? "availableWednesday=" + availableWednesday + ", " : "") +
                (availableThursday != null ? "availableThursday=" + availableThursday + ", " : "") +
                (availableFriday != null ? "availableFriday=" + availableFriday + ", " : "") +
                (availableSaturday != null ? "availableSaturday=" + availableSaturday + ", " : "") +
                (availableSunday != null ? "availableSunday=" + availableSunday + ", " : "") +
                (preferredShift != null ? "preferredShift=" + preferredShift + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
