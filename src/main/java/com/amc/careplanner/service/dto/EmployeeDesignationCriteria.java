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

/**
 * Criteria class for the {@link com.amc.careplanner.domain.EmployeeDesignation} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.EmployeeDesignationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-designations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeDesignationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter designation;

    private StringFilter description;

    private StringFilter designationDate;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    public EmployeeDesignationCriteria() {
    }

    public EmployeeDesignationCriteria(EmployeeDesignationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.designationDate = other.designationDate == null ? null : other.designationDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
    }

    @Override
    public EmployeeDesignationCriteria copy() {
        return new EmployeeDesignationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getDesignationDate() {
        return designationDate;
    }

    public void setDesignationDate(StringFilter designationDate) {
        this.designationDate = designationDate;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeDesignationCriteria that = (EmployeeDesignationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(description, that.description) &&
            Objects.equals(designationDate, that.designationDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        designation,
        description,
        designationDate,
        clientId,
        hasExtraData
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDesignationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (designationDate != null ? "designationDate=" + designationDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
            "}";
    }

}
