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
 * Criteria class for the {@link com.amc.careplanner.domain.Eligibility} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.EligibilityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /eligibilities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EligibilityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter note;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter tenantId;

    private LongFilter eligibilityTypeId;

    private LongFilter employeeId;

    public EligibilityCriteria() {
    }

    public EligibilityCriteria(EligibilityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.eligibilityTypeId = other.eligibilityTypeId == null ? null : other.eligibilityTypeId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public EligibilityCriteria copy() {
        return new EligibilityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getEligibilityTypeId() {
        return eligibilityTypeId;
    }

    public void setEligibilityTypeId(LongFilter eligibilityTypeId) {
        this.eligibilityTypeId = eligibilityTypeId;
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
        final EligibilityCriteria that = (EligibilityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(note, that.note) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(eligibilityTypeId, that.eligibilityTypeId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        note,
        lastUpdatedDate,
        tenantId,
        eligibilityTypeId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EligibilityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (eligibilityTypeId != null ? "eligibilityTypeId=" + eligibilityTypeId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
