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

    private BooleanFilter isAvailableForWorkWeekDays;

    private IntegerFilter minimumHoursPerWeekWeekDays;

    private IntegerFilter maximumHoursPerWeekWeekDays;

    private BooleanFilter isAvailableForWorkWeekEnds;

    private IntegerFilter minimumHoursPerWeekWeekEnds;

    private IntegerFilter maximumHoursPerWeekWeekEnds;

    private ShiftFilter leastPreferredShift;

    private LongFilter employeeId;

    public EmployeeAvailabilityCriteria() {
    }

    public EmployeeAvailabilityCriteria(EmployeeAvailabilityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isAvailableForWorkWeekDays = other.isAvailableForWorkWeekDays == null ? null : other.isAvailableForWorkWeekDays.copy();
        this.minimumHoursPerWeekWeekDays = other.minimumHoursPerWeekWeekDays == null ? null : other.minimumHoursPerWeekWeekDays.copy();
        this.maximumHoursPerWeekWeekDays = other.maximumHoursPerWeekWeekDays == null ? null : other.maximumHoursPerWeekWeekDays.copy();
        this.isAvailableForWorkWeekEnds = other.isAvailableForWorkWeekEnds == null ? null : other.isAvailableForWorkWeekEnds.copy();
        this.minimumHoursPerWeekWeekEnds = other.minimumHoursPerWeekWeekEnds == null ? null : other.minimumHoursPerWeekWeekEnds.copy();
        this.maximumHoursPerWeekWeekEnds = other.maximumHoursPerWeekWeekEnds == null ? null : other.maximumHoursPerWeekWeekEnds.copy();
        this.leastPreferredShift = other.leastPreferredShift == null ? null : other.leastPreferredShift.copy();
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

    public BooleanFilter getIsAvailableForWorkWeekDays() {
        return isAvailableForWorkWeekDays;
    }

    public void setIsAvailableForWorkWeekDays(BooleanFilter isAvailableForWorkWeekDays) {
        this.isAvailableForWorkWeekDays = isAvailableForWorkWeekDays;
    }

    public IntegerFilter getMinimumHoursPerWeekWeekDays() {
        return minimumHoursPerWeekWeekDays;
    }

    public void setMinimumHoursPerWeekWeekDays(IntegerFilter minimumHoursPerWeekWeekDays) {
        this.minimumHoursPerWeekWeekDays = minimumHoursPerWeekWeekDays;
    }

    public IntegerFilter getMaximumHoursPerWeekWeekDays() {
        return maximumHoursPerWeekWeekDays;
    }

    public void setMaximumHoursPerWeekWeekDays(IntegerFilter maximumHoursPerWeekWeekDays) {
        this.maximumHoursPerWeekWeekDays = maximumHoursPerWeekWeekDays;
    }

    public BooleanFilter getIsAvailableForWorkWeekEnds() {
        return isAvailableForWorkWeekEnds;
    }

    public void setIsAvailableForWorkWeekEnds(BooleanFilter isAvailableForWorkWeekEnds) {
        this.isAvailableForWorkWeekEnds = isAvailableForWorkWeekEnds;
    }

    public IntegerFilter getMinimumHoursPerWeekWeekEnds() {
        return minimumHoursPerWeekWeekEnds;
    }

    public void setMinimumHoursPerWeekWeekEnds(IntegerFilter minimumHoursPerWeekWeekEnds) {
        this.minimumHoursPerWeekWeekEnds = minimumHoursPerWeekWeekEnds;
    }

    public IntegerFilter getMaximumHoursPerWeekWeekEnds() {
        return maximumHoursPerWeekWeekEnds;
    }

    public void setMaximumHoursPerWeekWeekEnds(IntegerFilter maximumHoursPerWeekWeekEnds) {
        this.maximumHoursPerWeekWeekEnds = maximumHoursPerWeekWeekEnds;
    }

    public ShiftFilter getLeastPreferredShift() {
        return leastPreferredShift;
    }

    public void setLeastPreferredShift(ShiftFilter leastPreferredShift) {
        this.leastPreferredShift = leastPreferredShift;
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
            Objects.equals(isAvailableForWorkWeekDays, that.isAvailableForWorkWeekDays) &&
            Objects.equals(minimumHoursPerWeekWeekDays, that.minimumHoursPerWeekWeekDays) &&
            Objects.equals(maximumHoursPerWeekWeekDays, that.maximumHoursPerWeekWeekDays) &&
            Objects.equals(isAvailableForWorkWeekEnds, that.isAvailableForWorkWeekEnds) &&
            Objects.equals(minimumHoursPerWeekWeekEnds, that.minimumHoursPerWeekWeekEnds) &&
            Objects.equals(maximumHoursPerWeekWeekEnds, that.maximumHoursPerWeekWeekEnds) &&
            Objects.equals(leastPreferredShift, that.leastPreferredShift) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isAvailableForWorkWeekDays,
        minimumHoursPerWeekWeekDays,
        maximumHoursPerWeekWeekDays,
        isAvailableForWorkWeekEnds,
        minimumHoursPerWeekWeekEnds,
        maximumHoursPerWeekWeekEnds,
        leastPreferredShift,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAvailabilityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isAvailableForWorkWeekDays != null ? "isAvailableForWorkWeekDays=" + isAvailableForWorkWeekDays + ", " : "") +
                (minimumHoursPerWeekWeekDays != null ? "minimumHoursPerWeekWeekDays=" + minimumHoursPerWeekWeekDays + ", " : "") +
                (maximumHoursPerWeekWeekDays != null ? "maximumHoursPerWeekWeekDays=" + maximumHoursPerWeekWeekDays + ", " : "") +
                (isAvailableForWorkWeekEnds != null ? "isAvailableForWorkWeekEnds=" + isAvailableForWorkWeekEnds + ", " : "") +
                (minimumHoursPerWeekWeekEnds != null ? "minimumHoursPerWeekWeekEnds=" + minimumHoursPerWeekWeekEnds + ", " : "") +
                (maximumHoursPerWeekWeekEnds != null ? "maximumHoursPerWeekWeekEnds=" + maximumHoursPerWeekWeekEnds + ", " : "") +
                (leastPreferredShift != null ? "leastPreferredShift=" + leastPreferredShift + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
