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
 * Criteria class for the {@link com.amc.careplanner.domain.Disability} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.DisabilityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /disabilities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DisabilityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter note;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter disabilityTypeId;

    private LongFilter employeeId;

    public DisabilityCriteria() {
    }

    public DisabilityCriteria(DisabilityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.disabilityTypeId = other.disabilityTypeId == null ? null : other.disabilityTypeId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public DisabilityCriteria copy() {
        return new DisabilityCriteria(this);
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

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getDisabilityTypeId() {
        return disabilityTypeId;
    }

    public void setDisabilityTypeId(LongFilter disabilityTypeId) {
        this.disabilityTypeId = disabilityTypeId;
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
        final DisabilityCriteria that = (DisabilityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(note, that.note) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(disabilityTypeId, that.disabilityTypeId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        note,
        lastUpdatedDate,
        clientId,
        disabilityTypeId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisabilityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (disabilityTypeId != null ? "disabilityTypeId=" + disabilityTypeId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
