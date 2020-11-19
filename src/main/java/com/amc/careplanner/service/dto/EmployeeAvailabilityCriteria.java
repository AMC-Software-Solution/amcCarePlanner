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

    private BooleanFilter isAvailableForWork;

    private IntegerFilter minimumHoursPerWeek;

    private IntegerFilter maximumHoursPerWeek;

    private ShiftFilter leastPreferredShift;

    private LongFilter employeeId;

    public EmployeeAvailabilityCriteria() {
    }

    public EmployeeAvailabilityCriteria(EmployeeAvailabilityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isAvailableForWork = other.isAvailableForWork == null ? null : other.isAvailableForWork.copy();
        this.minimumHoursPerWeek = other.minimumHoursPerWeek == null ? null : other.minimumHoursPerWeek.copy();
        this.maximumHoursPerWeek = other.maximumHoursPerWeek == null ? null : other.maximumHoursPerWeek.copy();
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

    public BooleanFilter getIsAvailableForWork() {
        return isAvailableForWork;
    }

    public void setIsAvailableForWork(BooleanFilter isAvailableForWork) {
        this.isAvailableForWork = isAvailableForWork;
    }

    public IntegerFilter getMinimumHoursPerWeek() {
        return minimumHoursPerWeek;
    }

    public void setMinimumHoursPerWeek(IntegerFilter minimumHoursPerWeek) {
        this.minimumHoursPerWeek = minimumHoursPerWeek;
    }

    public IntegerFilter getMaximumHoursPerWeek() {
        return maximumHoursPerWeek;
    }

    public void setMaximumHoursPerWeek(IntegerFilter maximumHoursPerWeek) {
        this.maximumHoursPerWeek = maximumHoursPerWeek;
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
            Objects.equals(isAvailableForWork, that.isAvailableForWork) &&
            Objects.equals(minimumHoursPerWeek, that.minimumHoursPerWeek) &&
            Objects.equals(maximumHoursPerWeek, that.maximumHoursPerWeek) &&
            Objects.equals(leastPreferredShift, that.leastPreferredShift) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isAvailableForWork,
        minimumHoursPerWeek,
        maximumHoursPerWeek,
        leastPreferredShift,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAvailabilityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isAvailableForWork != null ? "isAvailableForWork=" + isAvailableForWork + ", " : "") +
                (minimumHoursPerWeek != null ? "minimumHoursPerWeek=" + minimumHoursPerWeek + ", " : "") +
                (maximumHoursPerWeek != null ? "maximumHoursPerWeek=" + maximumHoursPerWeek + ", " : "") +
                (leastPreferredShift != null ? "leastPreferredShift=" + leastPreferredShift + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
