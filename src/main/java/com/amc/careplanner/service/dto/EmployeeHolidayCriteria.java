package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.EmployeeHolidayType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.EmployeeHoliday} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.EmployeeHolidayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-holidays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeHolidayCriteria implements Serializable, Criteria {
    /**
     * Class for filtering EmployeeHolidayType
     */
    public static class EmployeeHolidayTypeFilter extends Filter<EmployeeHolidayType> {

        public EmployeeHolidayTypeFilter() {
        }

        public EmployeeHolidayTypeFilter(EmployeeHolidayTypeFilter filter) {
            super(filter);
        }

        @Override
        public EmployeeHolidayTypeFilter copy() {
            return new EmployeeHolidayTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private ZonedDateTimeFilter startDate;

    private ZonedDateTimeFilter endDate;

    private EmployeeHolidayTypeFilter employeeHolidayType;

    private ZonedDateTimeFilter approvedDate;

    private ZonedDateTimeFilter requestedDate;

    private BooleanFilter approved;

    private StringFilter note;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter tenantId;

    private LongFilter employeeId;

    private LongFilter approvedById;

    public EmployeeHolidayCriteria() {
    }

    public EmployeeHolidayCriteria(EmployeeHolidayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.employeeHolidayType = other.employeeHolidayType == null ? null : other.employeeHolidayType.copy();
        this.approvedDate = other.approvedDate == null ? null : other.approvedDate.copy();
        this.requestedDate = other.requestedDate == null ? null : other.requestedDate.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.approvedById = other.approvedById == null ? null : other.approvedById.copy();
    }

    @Override
    public EmployeeHolidayCriteria copy() {
        return new EmployeeHolidayCriteria(this);
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

    public ZonedDateTimeFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTimeFilter startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTimeFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTimeFilter endDate) {
        this.endDate = endDate;
    }

    public EmployeeHolidayTypeFilter getEmployeeHolidayType() {
        return employeeHolidayType;
    }

    public void setEmployeeHolidayType(EmployeeHolidayTypeFilter employeeHolidayType) {
        this.employeeHolidayType = employeeHolidayType;
    }

    public ZonedDateTimeFilter getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(ZonedDateTimeFilter approvedDate) {
        this.approvedDate = approvedDate;
    }

    public ZonedDateTimeFilter getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(ZonedDateTimeFilter requestedDate) {
        this.requestedDate = requestedDate;
    }

    public BooleanFilter getApproved() {
        return approved;
    }

    public void setApproved(BooleanFilter approved) {
        this.approved = approved;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LongFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(LongFilter tenantId) {
        this.tenantId = tenantId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getApprovedById() {
        return approvedById;
    }

    public void setApprovedById(LongFilter approvedById) {
        this.approvedById = approvedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeHolidayCriteria that = (EmployeeHolidayCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(employeeHolidayType, that.employeeHolidayType) &&
            Objects.equals(approvedDate, that.approvedDate) &&
            Objects.equals(requestedDate, that.requestedDate) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(note, that.note) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(approvedById, that.approvedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        startDate,
        endDate,
        employeeHolidayType,
        approvedDate,
        requestedDate,
        approved,
        note,
        lastUpdatedDate,
        tenantId,
        employeeId,
        approvedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeHolidayCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (employeeHolidayType != null ? "employeeHolidayType=" + employeeHolidayType + ", " : "") +
                (approvedDate != null ? "approvedDate=" + approvedDate + ", " : "") +
                (requestedDate != null ? "requestedDate=" + requestedDate + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (approvedById != null ? "approvedById=" + approvedById + ", " : "") +
            "}";
    }

}
